package com.github.yasinzhangx.concurrentest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Yasin Zhang
 */
public class BoundedBufferTest {

    @DisplayName("BoundedBuffer 基本测试1")
    @Test
    void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Assertions.assertTrue(bb.isEmpty());
        Assertions.assertFalse(bb.isFull());
    }

    @DisplayName("BoundedBuffer 基本测试2")
    @Test
    void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        Assertions.assertTrue(bb.isFull());
        Assertions.assertFalse(bb.isEmpty());
    }

    @DisplayName("测试阻塞行为以及对中断的响应性")
    @Test
    void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread(() -> {
            try {
                int unused = bb.take();
                Assertions.fail();  // 执行到这里表示错误
            } catch (InterruptedException ignored) {}
        });

        try {
            taker.start();
            Thread.sleep(1000);
            taker.interrupt();
            taker.join(1000);
            Assertions.assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            Assertions.fail();
        }
    }

}
