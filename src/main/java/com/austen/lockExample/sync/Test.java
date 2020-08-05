package com.austen.lockExample.sync;

/**
 * @author Austen.Huang
 * @date 2020/8/5
 */
public class Test {
    private Object o = new Object();

    public static void main(String[] args) {
        Test test = new Test();
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

        Thread thread2 = new Thread(() -> {
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
        thread2.setName("t2");

        thread.start();
        thread2.start();
    }

    public void sync() throws InterruptedException{
        synchronized (o){
            System.out.println(Thread.currentThread().getName());
        }
    }
}
