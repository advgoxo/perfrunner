package com.jmeterx.async;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncFileUtils {
    private static AsyncFileUtils gFileUtils;
    static {
        gFileUtils = new AsyncFileUtils();
    }

    private class AsyncFile {
        public int wcnt;
        public String path;
        public BufferedOutputStream file;
        public long lastFlush;
        public AsyncFile(String f) {
            this.wcnt = 0;
            this.path = f;
            this.lastFlush = System.currentTimeMillis();
        }

        public boolean open() {
            File dir = (new File(this.path)).getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileOutputStream f = null;
            try {
                f = new FileOutputStream(this.path, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            this.file = new BufferedOutputStream(f);
            return true;
        }

        public void close() {
            try {
                if (this.file != null) {
                    this.file.close();
                    this.file = null;
                }
                System.out.println("close " + this.path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void flush() {
            try {
                if (this.file == null) {
                    return;
                }

                this.file.flush();
                if (this.wcnt > 0) {
                    this.wcnt = 0;
                    this.lastFlush = System.currentTimeMillis();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean canClose() {
            long now =System.currentTimeMillis();
            if (this.wcnt <= 0 &&
                    now - this.lastFlush >= 300000) {
                return true;
            }

            return false;
        }

        public void write(byte[] data) {
            if (this.file == null) {
                return;
            }

            try {
                this.wcnt += 1;
                this.file.write(data);
                if (this.wcnt % 512 == 0) {
                    this.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Timer timer = null;
    private QueueThread writerThread = null;
    private ConcurrentHashMap<String, AsyncFile> filesHashMap = null;

    public AsyncFileUtils() {
        this.timer = new Timer();
        this.filesHashMap = new ConcurrentHashMap();
        this.writerThread = new QueueThread("AsyncFileUtils");
        this.writerThread.start();

        AsyncFileUtils me = this;
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    me.asyncFlushAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },3000,10000);
    }

    private AsyncFile getWriteFile(String fpath) {
        AsyncFile w = this.filesHashMap.get(fpath);
        if (w != null) {
            return w;
        }

        AsyncFile f = new AsyncFile(fpath);
        if (!f.open()) {
            return null;
        }

        synchronized (this.filesHashMap) {
            if (this.filesHashMap.containsKey(fpath)) {
                f.close();
            }

            this.filesHashMap.put(fpath, f);
        }

        System.out.println("open " + fpath);
        return this.filesHashMap.get(fpath);
    }

    private void safeStop() {
        this.timer.cancel();
        this.asyncCloseAll();
        this.writerThread.safeStop();
    }

    private void asyncFlushAll() {
        List<AsyncFile> closes = new ArrayList<>();
        for(Map.Entry<String, AsyncFile> entry:
                this.filesHashMap.entrySet()) {
            AsyncFile w = entry.getValue();
            if (w == null) {
                continue;
            }

            if (w.canClose()) {
                closes.add(w);
                continue;
            }

            this.writerThread.add(()->{
                w.flush();
            });
        }

        for (AsyncFile w : closes) {
            this.writerThread.add(()->{
                w.close();
            });

            this.filesHashMap.remove(w.path);
        }
    }

    private void asyncCloseAll() {
        for(Map.Entry<String, AsyncFile> entry:
                this.filesHashMap.entrySet()) {
            AsyncFile w = entry.getValue();
            if (w == null) {
                continue;
            }

            this.writerThread.add(()->{
                w.close();
            });
        }

        this.filesHashMap.clear();
    }

    private void asyncCloseFile(String fpath) {
        AsyncFile w = this.getWriteFile(fpath);
        if (w == null || w.file == null) {
            return;
        }

        this.writerThread.add(()->{
            w.close();
        });

        this.filesHashMap.remove(fpath);
    }

    private void asyncWriteFile(String fpath, String data) {
        AsyncFile w = this.getWriteFile(fpath);
        if (w == null || w.file == null) {
            return;
        }

        this.writerThread.add(()->{
            w.write(data.getBytes());
        });
    }

    public  static void stop() {
        gFileUtils.safeStop();
    }

    public  static void closeAll() {
        gFileUtils.asyncCloseAll();
    }

    public static String getAbsFile(String fpath){
        return (new File(fpath)).getAbsolutePath();
    }

    public  static void closeFile(String fpath) {
        gFileUtils.asyncCloseFile(getAbsFile(fpath));
    }

    public  static void write(String fpath, String data) {
        gFileUtils.asyncWriteFile(getAbsFile(fpath), data);
    }

    public  static void writeLine(String fpath, String data) {
        gFileUtils.asyncWriteFile(getAbsFile(fpath), data + "\n");
    }
}