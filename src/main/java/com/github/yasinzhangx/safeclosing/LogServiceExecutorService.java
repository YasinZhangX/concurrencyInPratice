package com.github.yasinzhangx.safeclosing;

import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 * @author Yasin Zhang
 */
public class LogServiceExecutorService {

    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final PrintWriter writer;

    private final long TIMEOUT = 10L;
    private final TimeUnit UNIT = TimeUnit.SECONDS;

    public LogServiceExecutorService() {
        writer = new PrintWriter(System.out);
    }

    public void start() { }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(TIMEOUT, UNIT);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) {
        try {
            exec.execute(() -> writer.println(msg));
        } catch (RejectedExecutionException ignore) {}
    }

}
