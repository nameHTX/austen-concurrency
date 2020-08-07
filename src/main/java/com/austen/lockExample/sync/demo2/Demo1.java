package com.austen.lockExample.sync.demo2;

import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 *
 * t2可以执行。因为sync锁的是堆里的实例。demo.o = new Object()后，对象变了。他们不是同一把锁
 */
public class Demo1 {
    Object o = new Object();

    public void test() {
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        Demo1 demo = new Demo1();
        new Thread(demo::test, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(demo::test, "t2");
        demo.o = new Object();
        //t2能否执行
        //可以执行。因为sync锁的是堆里的实例。demo.o = new Object()后，对象变了。他们不是同一把锁
        t2.start();
    }
}
