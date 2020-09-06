package com.jmeterx.async;

import java.io.*;
import java.util.Map;
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
        public AsyncFile(String f) {
            this.wcnt = 0;
            this.path = f;
        }

        public boolean open() {
            File dir = (new File(this.path)).getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            System.out.println("dir = " + dir);
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
                this.file.close();
                this.file = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private QueueThread writerThread = null;
    private ConcurrentHashMap<String, AsyncFile> filesHashMap = null;

    public AsyncFileUtils() {
        this.filesHashMap = new ConcurrentHashMap();
        this.writerThread = new QueueThread("AsyncFileUtils");
        this.writerThread.start();
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
        this.asyncCloseAll();
        this.writerThread.safeStop();
    }

    private void asyncCloseAll() {
        for(Map.Entry<String, AsyncFile> entry:
                this.filesHashMap.entrySet()) {
            AsyncFile w = entry.getValue();
            if (w == null) {
                continue;
            }

            this.writerThread.add(()->{
                if (w.file != null) {
                    w.close();
                }

                System.out.println("close " + w.path);
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
            if (w.file != null) {
                w.close();
            }

            System.out.println("close " + fpath);
        });

        this.filesHashMap.remove(fpath);
    }

    private void asyncWriteFile(String fpath, String data) {
        AsyncFile w = this.getWriteFile(fpath);
        if (w == null || w.file == null) {
            return;
        }

        this.writerThread.add(()->{
            if (w.file == null) {
                return;
            }

            try {
                w.wcnt += 1;
                w.file.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //System.out.println("write " + data);
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