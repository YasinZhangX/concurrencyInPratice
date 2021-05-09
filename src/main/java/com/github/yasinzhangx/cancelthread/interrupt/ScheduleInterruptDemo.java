package com.github.yasinzhangx.cancelthread.interrupt;

import java.util.concurrent.*;

/**
 * @author Yasin Zhang
 */
public class ScheduleInterruptDemo {

    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    public static void timedRun(final Runnable r,
                                long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() throws InterruptedException {
                if (t != null) {
                    throw launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(taskThread::interrupt, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

    private static final ExecutorService taskExec = Executors.newFixedThreadPool(10);

    public static void timedRunByFuture(Runnable r,
                                        long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);

        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // 任务将被取消
        } catch (ExecutionException e) {
            // 任务异常，重新抛出
            throw launderThrowable(e);
        } finally {
            // 任务已经结束，取消操作无影响
            // 任务正在运行，则直接中断
            task.cancel(true);
        }
    }

    private static InterruptedException launderThrowable(Throwable t) {
        InterruptedException e = new InterruptedException();
        e.setStackTrace(t.getStackTrace());
        return e;
    }

}
