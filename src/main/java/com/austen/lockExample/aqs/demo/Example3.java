package com.austen.lockExample.aqs.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Austen.Huang
 * @date 2020/8/13
 *
 * 使用场景
 * 1、让线程都准备好一起执行
 */
public class Example3 {

    public static void main(String[] args){

        ExecutorService service = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(15);
        for (int i = 0; i < 15; i++) {
            final int finalI = i;
            service.execute(() -> {
                System.out.println(finalI+"---before");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(finalI+"---end");
            });
        }
        System.out.println("end");
        service.shutdown();
    }
}
