package com.austen.threadExample.interrupt;

/**
 * @author Austen.Huang
 * @date 2020/8/4
 * <p>
 * interrupt函数的作用及使用
 * <p>
 * --用来优雅中断线程的函数
 * --jvm不推荐直接停止一个线程，一定要让一个线程执行完
 * --假设现在在阻塞->解阻塞
 * --假设线程是while(flag)->flag=false
 */
public class Test {
    static Thread thread = null;

    public static void main(String[] args) throws InterruptedException {
        myThread();
        Thread.sleep(10);
        thread.interrupt();
    }

    public static void myThread() {
        thread = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        System.out.println("sleep的情况下，中断了线程抛这个错误");
                        return;
                    }
                    System.out.println("-------");
                }
            }
        };
        thread.start();
    }
}
