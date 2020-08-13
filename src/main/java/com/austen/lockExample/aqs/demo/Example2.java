package com.austen.lockExample.aqs.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/13
 *
 * 使用场景：
 * 只允许N个线程一起执行
 *
 * 注意多测试tryAcquire的超时时间跟获取的个数，跟test的sleep时间有关
 */
public class Example2 {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalI = i;
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    test(finalI);
                    semaphore.release();
                    /*if(semaphore.tryAcquire(15)){
                        test(finalI);
                        semaphore.release(15);
                    }*/
                    /*if(semaphore.tryAcquire(2, TimeUnit.SECONDS)){
                        test(finalI);
                        semaphore.release();
                    }*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        service.shutdown();
    }

    static void test(int k) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(k + "-------" + System.currentTimeMillis());
    }
}
