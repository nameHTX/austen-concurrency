package com.austen.threadExample.interrupt;

/**
 * @author Austen.Huang
 * @date 2020/8/4
 *
 * 这是错误方法示范，通过修改running来终止线程
 */
public class Test1 {
    static Thread thread = null;
    public  static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        myThread();

        //如果主线程不睡眠情况下，且myThread的run方法是个空方法，该程序无法停止。
        //因为myThread的run为空的情况下
        //thread = new Thread() {
        //    @Override
        //    public void run() {
        //        while (running) {
        //        }
        //    }
        //};
        //jvm将线程优化成
        //boolean temp = running;
        //thread = new Thread() {
        //    @Override
        //    public void run() {
        //        while (temp) {
        //        }
        //    }
        //};
        //如果不睡眠的情况下，run跑之前，主线程80%会先执行running = false

        Thread.sleep(100);
        running = false;
    }

    public static void myThread() {
        thread = new Thread() {
            @Override
            public void run() {
                while (running) {
//                    System.out.println("-------------");
                }
            }
        };
        thread.start();
    }
}
