package com.austen.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/13
 *
 * 根据CPU核心数创建线程数，哪个线程空闲了，就去拿新的任务（工作窃取）
 */
public class Demo7_WorkStealingPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newWorkStealingPool();
        //打印线程数
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));

        TimeUnit.SECONDS.sleep(90);
    }

    static class R implements Runnable {
        int time;

        public R(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
