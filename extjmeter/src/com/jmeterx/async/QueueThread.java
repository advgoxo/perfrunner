package com.jmeterx.async;


import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueThread extends Thread {
    private String name;
    private boolean running;
    private ConcurrentLinkedQueue<Runnable> queue;

    {
        queue = new ConcurrentLinkedQueue<Runnable>();
    }

    public QueueThread(String name) {
        this.name = name;
        this.running = true;
    }

    public void safeStop() {
        this.running = false;
        this.interrupt();
        this.interrupt();
        this.interrupt();
    }

    public void add(Runnable r) {
        this.queue.offer(r);
    }


    private int runQueue(boolean all) {
        int count = 0;
        Runnable r = null;
        do {
            r  = this.queue.poll();
            if (r != null) {
                r.run();
                count++;
            }
        } while (r != null && all);

        return count;
    }

    public void run() {
        while (!this.isInterrupted() && this.running) {
            int r = this.runQueue(false);
            if (r > 0) {
                continue;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
        }

        this.runQueue(true);
        System.out.println(this.name + " finish.");
    }
}
