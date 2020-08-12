package com.austen.lockExample.sync.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Austen.Huang
 * @date 2020/8/11
 *
 * 证明轻量级锁
 *
 * 轻量锁：0 0000 0 00
 *
 *  轻量级锁：ptr_to_lock_record:62 00
 *
 * 不加jvm关闭偏向延迟标识。a先是无锁，然后sync中变成轻量级锁，然后无锁(释放锁)
 *
 */
public class JOLExample5 {

    private static A a;

    public static void main(String[] args) {
        a = new A();
        System.out.println("before lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        sync();
        System.out.println("after lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }

    public static void sync() {
        synchronized (a) {
            System.out.println("lock ing");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }
    }
}
