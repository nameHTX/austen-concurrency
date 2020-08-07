package com.austen.lockExample.sync.demo1;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 *
 * 修饰this,this指Demo2的实例。直接锁类的字节码
 */
public class Demo2 {
    private int count = 10;

    public void test() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
