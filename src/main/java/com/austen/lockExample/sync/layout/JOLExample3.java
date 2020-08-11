package com.austen.lockExample.sync.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Austen.Huang
 * @date 2020/8/10
 * <p>
 * 证明偏向锁状态下的对象头
 *
 * jvm启动时，未防止多次处理偏向锁升级，所以jvm延迟开启偏向锁
 * 要大于4秒后才启动偏向锁。我们可以关闭偏向锁延迟
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           92 c3 00 f8 (10010010 11000011 00000000 11111000) (-134167662)
 *      12     4        (loss due to the next object alignment)
 *
 *  所以偏向锁是 0 0000 1 01
 */
public class JOLExample3 {

    public static void main(String[] args) {
        A a = new A();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a) {
            System.out.println("lock ing");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        System.out.println("after lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
