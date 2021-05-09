package com.github.yasinzhangx.cancelthread.interrupt.socketinterrupt.futureway;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * @author Yasin Zhang
 */
public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
