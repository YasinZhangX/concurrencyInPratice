package com.github.yasinzhangx.cancelthread.interrupt;

import com.github.yasinzhangx.cancelthread.volatileway.PrimeGenerator;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Yasin Zhang
 */
public class Main {

    private static volatile boolean needMorePrime = true;

    public static void main(String[] args) throws InterruptedException {
        aSecondOfPrimes();
    }

    private static void aSecondOfPrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<>(1);
        PrimeProducer producer = new PrimeProducer(primes);

        producer.start();

        new Thread(new Waiter()).start();

        try {
            while (needMorePrime) {
                System.out.println(primes.take());
            }
        } finally {
            producer.cancel();
        }
    }

    static class Waiter implements Runnable {
        public void run() {
            try {
                try {
                    SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                needMorePrime = false;
            }
        }
    }

}
