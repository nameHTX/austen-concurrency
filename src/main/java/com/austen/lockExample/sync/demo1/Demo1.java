package com.austen.lockExample.sync.demo1;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 *
 * 修饰对象，锁对象
 * 锁对象对象头可以记录锁的信息
 */
public class Demo1 {
    private int count = 10;
    private Object object = new Object();

    public void test() {
        synchronized (object) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
