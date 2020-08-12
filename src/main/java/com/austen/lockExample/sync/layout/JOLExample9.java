package com.austen.lockExample.sync.layout;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 *
 * 设置：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 * 偏向批量撤销
 *
 * 如果锁在同一个线程的同一个对象一直膨胀，jvm对这个线程的这个对象的偏向锁进行批量重偏向
 * （因为锁的膨胀很耗费资源）
 * 第20个包括第20个开始，都重偏向第二个线程，之前的都是轻量级锁
 *
 *
 * 原理：以class为单位，为每个class维护一个偏向锁撤销计数器（epoch），
 * 每次该class的对象发生偏向撤销操作时，该计数器+1，
 * 当这个值达到重偏向阈值（默认20）时，JVM就认为该class的偏向锁有问题，
 * 因此会进行批量重偏向。每个class对象会有一个对应的epoch字段。
 * 每个处于偏向锁状态对象的mark word中也有该字段，其初始值为创建该对象时，
 * class中的epoch的值。每次发生批量重偏向时，就将该值+1，同时遍历JVM中所有线程的栈，
 * 找到该class所有正处于加锁状态的偏向锁，将其epoch字段改为新值。
 * 下次获得锁时，发现当前对象的epoch值和class的epoch不相等，
 * 那就算当前已经偏向了其他线程，也不会执行撤销操作，
 * 而是直接通过CAS操作将其mark word的Thread Id改成当前线程Id
 */
public class JOLExample9 {
    static List<A> list = new ArrayList<A>();
    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> {
            for (int i=0;i<40;i++){
                A a = new A();
                synchronized (a){
                    out.println("111111");
                    list.add(a);
                }
            }

        });
        t1.start();
        t1.join();
        out.println("before t2");
        //偏向
        out.println(ClassLayout.parseInstance(list.get(1)).toPrintable());
        Thread t2 = new Thread() {
            int k=0;
            @Override
            public void run() {
                for(A a:list){
                   synchronized (a){
                       System.out.println("22222");
                       if (k==25){
                           out.println("t2 ing");
                           //轻量锁
                           out.println(ClassLayout.parseInstance(a).toPrintable());
                           out.println(ClassLayout.parseInstance(list.get(18)).toPrintable());
                           out.println(ClassLayout.parseInstance(list.get(19)).toPrintable());
                       }
                   }
                    k++;
                }

            }
        };
        t2.start();
    }
}