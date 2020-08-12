package com.austen.lockExample.sync.layout;

import java.util.concurrent.CountDownLatch;

/**
 * @author Austen.Huang
 * @date 2020/8/11
 * <p>
 * 性能对比：轻量级锁跟重量级锁
 *
 * 轻量锁：
 * 重量锁：3741ms
 * JOLExample4中：轻量锁：2414ms,偏向锁：179ms
 */
public class JOLExample6 {

    static CountDownLatch countDownLatch = new CountDownLatch(100000000);

    public static void main(String[] args) throws InterruptedException {
        final C c = new C();

        long start = System.currentTimeMillis();

        //调用同步方法1000000000L 来计算1000000000L的++，对比重量锁和轻量级锁的性能

        //重量级锁
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (countDownLatch.getCount() > 0) {
                    c.parse();
                }
            }).start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println(String.format("%sms", end - start));
    }
}
