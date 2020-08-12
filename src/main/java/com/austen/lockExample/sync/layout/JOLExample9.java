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