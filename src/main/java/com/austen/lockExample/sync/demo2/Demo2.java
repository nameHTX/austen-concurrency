package com.austen.lockExample.sync.demo2;

import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 *
 * 字符串当锁。因为锁的都是常量池中的同一个hello。所以他们是同一把锁
 * 所以t2等t1执行后才能执行
 */
public class Demo2 {
    String s1 = "hello";
    String s2 = "hello";

    public void test1() {
        synchronized (s1) {
            System.out.println("t1 start ...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 end ...");
        }
    }

    public void test2() {
        synchronized (s2) {
            System.out.println("t2 start ...");
        }
    }

    public static void main(String[] args) {
        Demo2 demo = new Demo2();
        new Thread(demo::test1, "test1").start();
        new Thread(demo::test2, "test2").start();
    }
}
