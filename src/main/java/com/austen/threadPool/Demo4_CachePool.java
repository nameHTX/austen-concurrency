package com.austen.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/13
 *
 * 缓存线程池
 *
 * 初始线程池没有线程，每次使用新增一个，最多Integer.MAX_VALUE条。未使用的线程默认60（keepAliveTime）秒删除。
 */
public class Demo4_CachePool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);

        TimeUnit.SECONDS.sleep(60);
        System.out.println(service);

        TimeUnit.SECONDS.sleep(1);
        System.out.println(service);
    }
}
