package com.austen.lockExample.aqs.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Austen.Huang
 * @date 2020/8/13
 *
 * 使用场景
 * 1、让线程都准备好一起执行
 * 2、某个线程需要等其他线程执行完
 */
public class Example1 {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(15);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalI = i;
            service.execute(() -> {
                System.out.println(finalI+"---before");
                countDownLatch.countDown();
                System.out.println(finalI+"---end");
            });
        }
        countDownLatch.await();
        //等待2秒钟后，如果线程未执行完，也继续等待
        //countDownLatch.await(2, TimeUnit.SECONDS);
        System.out.println("end");
        service.shutdown();
    }
}
