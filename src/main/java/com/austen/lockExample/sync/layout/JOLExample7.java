package com.austen.lockExample.sync.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 *
 * 重量级锁：ptr_to_lock_record:62 10
 *
 * 注意：调用wait立即变成重量级锁
 */
public class JOLExample7 {
    static A a;

    public static void main(String[] args) throws Exception {
        //Thread.sleep(5000);
        a = new A();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());//无锁

        Thread t1 = new Thread(() -> {
            synchronized (a) {
                try {
                    Thread.sleep(5000);
                    System.out.println("t1 release");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread.sleep(1000);
        System.out.println("t1 lock ing");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());//轻量锁
        sync();
        System.out.println("after lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());//重量锁

        System.gc();
        System.out.println("after gc()");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());//无锁---gc
    }

    public static void sync() throws InterruptedException {
        synchronized (a) {
            System.out.println("t1 main lock");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());//重量锁
        }
    }
}
