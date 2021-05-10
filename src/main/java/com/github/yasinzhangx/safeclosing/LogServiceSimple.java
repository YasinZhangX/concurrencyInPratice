package com.github.yasinzhangx.safeclosing;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Yasin Zhang
 */
public class LogServiceSimple {

    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    private boolean isShutdown;
    private int reservations;

    public LogServiceSimple() {
        this.queue = new LinkedBlockingQueue<>(100);
        loggerThread = new LoggerThread();
        writer = new PrintWriter(System.out);
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("shutdown");
            }
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogServiceSimple.this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }

                        String msg = queue.take();
                        synchronized (LogServiceSimple.this) {
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {
                        /* retry */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }

}
