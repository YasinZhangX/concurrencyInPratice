package com.github.yasinzhangx.cancelthread.interrupt;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author Yasin Zhang
 */
public class PrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {
            System.out.println("get interrupted exception");
        }
    }

    public void cancel() {
        interrupt();
    }

}
