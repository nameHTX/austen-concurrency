package com.austen.lockExample.sync.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 *
 * 设置：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 * 单个锁可重偏向的情况（几率性）
 *
 * 经过分析可以知道，偏向锁偏向的线程都是偏向t1
 *
 */
public class JOLExample10 {

    static A a;

    public static void main(String[] args) throws InterruptedException {
        a = new A();
        System.out.println("无锁" + ClassLayout.parseInstance(a).toPrintable());
        synchronized (a) {
            System.out.println("t1");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());//偏向锁
        }


        Thread t1 = new Thread(() -> {
            synchronized (a) {
                System.out.println("t2");
                System.out.println(ClassLayout.parseInstance(a).toPrintable());//轻量锁
            }
        });

        t1.start();
        t1.join();


        Thread t2 = new Thread(() -> {
            synchronized (a) {
                System.out.println("t3");
                System.out.println(ClassLayout.parseInstance(a).toPrintable());//轻量锁
            }
        });


        t2.start();
    }
}