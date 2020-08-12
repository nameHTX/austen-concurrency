package com.austen.lockExample.sync.layout;

/**
 * @author Austen.Huang
 * @date 2020/8/11
 *
 * 比较轻量级锁跟偏向锁时间的效率问题
 *
 * 关闭偏向锁延迟的情况下就是偏向锁：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 * 结果：
 *  轻量锁：2414ms
 *  偏向锁：179ms
 */
public class JOLExample4 {

    public static void main(String[] args) {
        B b = new B();
        long start = System.currentTimeMillis();
        //调用同步方法1000000000L 来计算1000000000L的++，对比偏向锁和轻量级锁的性能
        //如果不出意外，结果灰常明显
        for (int i = 0; i < 100000000L; i++) {
            b.parse();
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("%sms", end - start));
    }
}
