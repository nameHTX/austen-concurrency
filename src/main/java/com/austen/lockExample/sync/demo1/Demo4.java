package com.austen.lockExample.sync.demo1;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 * <p>
 * 修饰静态方法 锁的是类对象。
 */
public class Demo4 {
    private static int count = 10;

    public synchronized static void test() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void test2() {
        synchronized (Demo4.class) {
            count--;
        }
    }
}
