package com.github.yasinzhangx.executor;

import java.util.concurrent.*;

/**
 * @author Yasin Zhang
 */
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 首先我们创建了一个线程池
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(3, 3, 0L,
                                    TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<>());

        // futureTask 我们叫做线程任务，构造器的入参是 Callable
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(3000);
            // 返回一句话
            return "我是子线程"+Thread.currentThread().getName();
        });

        // 把任务提交到线程池中，线程池会分配线程帮我们执行任务
        executor.submit(futureTask);

        // 得到任务执行的结果
        String result = futureTask.get();
        System.out.println(result);
    }
}
