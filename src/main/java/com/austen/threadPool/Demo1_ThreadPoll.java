package com.austen.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 *
 * 线程池如何配置合理线程数
 * （1）CPU密集型
 *    CPU密集型的意思就是该任务需要大量运算，而没有阻塞，CPU一直全速运行。
 *    CPU密集型任务只有在真正的多核CPU上才可能得到加速（通过多线程）。
 *    CPU密集型任务配置尽可能少的线程数。
 *    CPU密集型线程数配置公式：CPU核数+1个线程的线程池
 * （2）IO密集型
 *    IO密集型，即该任务需要大量的IO，即大量的阻塞。
 *    在单线程上运行IO密集型任务会导致浪费大量的CPU运算能力浪费在等待。
 *    所以IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上，这种加速主要利用了被浪费掉的阻塞时间。
 *    第一种配置方式：
 *    由于IO密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程。
 *    配置公式：CPU核数 * 2。
 *    第二种配置方式：
 *    IO密集型时，大部分线程都阻塞，故需要多配置线程数。
 *    配置公式：CPU核数 / 1 – 阻塞系数（0.8~0.9之间）
 *    比如：8核 / (1 – 0.9) = 80个线程数
 *
 * 固定大小线程池  FixedThreadPool
 */
public class Demo1_ThreadPoll {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MICROSECONDS.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);

        //把线程设置为关闭状态，但是不关闭现在线程池中的任务
        service.shutdown();
        //直接关闭线程池，池中若存在任务会报错
        //service.shutdownNow();

        //是否被终止
        System.out.println(service.isTerminated());
        //是否是关闭状态
        System.out.println(service.isShutdown());
        System.out.println(service);

        TimeUnit.SECONDS.sleep(5);
        System.out.println(service.isTerminated());


    }
}
