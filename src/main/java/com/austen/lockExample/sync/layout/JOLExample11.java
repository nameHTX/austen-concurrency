package com.austen.lockExample.sync.layout;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 *
 * 测试批量撤销
 *
 * 设置：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class JOLExample11 {
    static List<A> list = new ArrayList<A>();

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread() {
            @Override
            public void run() {

                for (int i = 0; i < 100; i++) {
                    A a = new A();
                    synchronized (a) {
                        list.add(a);
                    }
                }
                System.out.println(" add end --------");

                if (list.size() == 100) {
                    A a = list.get(60);
                    synchronized (a) {
                        try {
                            this.currentThread().sleep(1000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
        t1.start();
        //t1.join();
        Thread.sleep(5000);


        System.out.println("before t2   1:" + ClassLayout.parseInstance(list.get(1)).toPrintable());

        System.out.println("before t2   60:" + ClassLayout.parseInstance(list.get(60)).toPrintable());


        Thread t2 = new Thread() {
            int k = 0;

            @Override
            public void run() {
                for (A a : list) {
                    synchronized (a) {
                        // System.out.println("22222");
                        if (k == 18 || k == 26) {
                            //轻量锁
                            System.out.println("t2 ing  ------ K=" + k + "  |" + ClassLayout.parseInstance(a).toPrintable());

                            /**
                             * 当K 为 20 时， 60 的 epoch 会变为  01 原因：
                             *       当批量偏向到达阈值时，会进行如下操作：
                             *           1.会修改Class类对象的中 epoch 字段，为: 01 【没有找到是哪一个字段，无法证明】
                             *           2.会扫描该Class的所有对象：
                             *                   A：如果当前Class的对象，在同步块中，则修改 epoch 的值与 Class类对象中的 epoch 字段相等
                             *                   B：如果当前Class的对象，不在同步块中则不进行修改
                             *
                             */
                            System.out.println("t2 ing  ------ K=" + k + "  60:" + ClassLayout.parseInstance(list.get(60)).toPrintable());
                        }
                    }
                    k++;
                    if (k == 50) {
                        System.out.println(" t2 end -------------------");
                        A a1 = list.get(80);
                        synchronized (a1) {
                            try {
                                this.currentThread().sleep(1000000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        };
        t2.start();
        //t2.join();

        Thread.sleep(5000);
        System.out.println("main ing  ----  80:" + ClassLayout.parseInstance(list.get(80)).toPrintable());

        Thread t3 = new Thread() {
            int k = 0;

            @Override
            public void run() {
                for (A a : list) {
                    k++;
                    if (k > 20) {
                        synchronized (a) {
                            if (k == 26 || k == 45) {
                                //轻量锁
                                System.out.println("T3 ing  ------ K=" + k + "  |" + ClassLayout.parseInstance(a).toPrintable());
                                /**
                                 * 26 偏向     45 轻量
                                 * 当T3 线程 批量撤销 偏向锁的对象 超过阈值：20 时： （问题： 批量撤销总次数阈值为 40 ，这里为什么是 20 ？  因为 T2 线程 在进行重偏向时， 已撤销了20次【前20个对象】，这里只剩下 20 次）
                                 *      会扫描对正在同步块中的对象 。对所有对象进行撤销 偏向锁 ，全部置为 轻量级锁 ，因此 当K 为 45时，已发生批量 撤销， 80 已置为 轻量级锁
                                 */
                                System.out.println("T3 ing  ------ K=" + k + "  80:" + ClassLayout.parseInstance(list.get(80)).toPrintable());
                            }
                        }
                        if (k == 45) {
                            return;
                        }
                    }

                }

            }
        };
        t3.start();
        t3.join();
        // 轻量级锁
        System.out.println("main ing  ----  80:" + ClassLayout.parseInstance(list.get(80)).toPrintable());
    }
}