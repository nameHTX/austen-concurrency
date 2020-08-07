package com.austen.lockExample.sync.demo2;

import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 * <p>
 * test2是test1的优化
 * 因为同步块中语句越少越好。粒度更小
 */
public class Demo3 {
    int count = 0;

    public synchronized void test1() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            count++;
        }
    }
}
