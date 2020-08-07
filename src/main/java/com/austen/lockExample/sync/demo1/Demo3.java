package com.austen.lockExample.sync.demo1;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 *
 * 等同于synchronized(this)
 */
public class Demo3 {
    private int count = 10;

    public synchronized void test() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
