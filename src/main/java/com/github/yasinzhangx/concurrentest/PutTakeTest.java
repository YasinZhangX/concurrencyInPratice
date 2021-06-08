package com.github.yasinzhangx.concurrentest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Yasin Zhang
 */
public class PutTakeTest {

    private static PutTakeTestCase testcase;

    @BeforeAll
    static void init() {
        testcase = new PutTakeTestCase(10, 10, 100000);
    }

    @Test
    public void test() {
        testcase.testPutTake();
    }

}
