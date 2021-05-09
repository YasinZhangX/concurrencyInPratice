package com.github.yasinzhangx.cancelthread.volatileway;

import java.math.BigInteger;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Yasin Zhang
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<BigInteger> result = aSecondOfPrimes();
        for (BigInteger p : result) {
            System.out.println(p);
        }
    }

    private static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }

}
