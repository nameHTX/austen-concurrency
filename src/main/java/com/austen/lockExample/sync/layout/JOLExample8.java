package com.austen.lockExample.sync.layout;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 * 一旦对象进行hashCode计算，就不能变成偏向锁
 */
public class JOLExample8 {
   static A a;
    public static void main(String[] args) throws Exception {
        Thread.sleep(5000);
        a= new A();
        a.hashCode();
        System.out.println("befor lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a){
            System.out.println("lock ing");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        System.out.println("after lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
