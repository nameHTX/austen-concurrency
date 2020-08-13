package com.austen.lockExample.sync.test;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 *
 * 轻量级锁（因为没有设置偏向锁延迟为0）
 */
public class Test2 {

    public static void main(String[] args) {
        Test2 test = new Test2();
        test.start();
    }

    public void start() {
        Thread thread = new Thread(() -> {
            while (true){
                try{
                    //睡眠是为了得到CPU的资源
                    Thread.sleep(500);
                    sync();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        thread.setName("t1");

        thread.start();
    }

    public synchronized void sync() throws InterruptedException{
        System.out.println(Thread.currentThread().getName());
    }
}
