package com.austen.lockExample.sync.layout;

import com.austen.utils.HashUtil;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author Austen.Huang
 * @date 2020/8/10
 *
 * 证明对象头前56个bit存的是hashCode
 * 证明无锁状态下的对象头
 *
 * 小端模式下-数据高的字节保存在高地址当中，
 * 所以转换出来的16进制跟object header的二进制的位置是颠倒的
 *
 * 例如：
 * jvm-----------0x30a3107a
 * after hash
 * com.austen.lockExample.sync.layout.A object internals:
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           01 7a 10 a3 (00000001 01111010 00010000 10100011) (-1559201279)
 *       4     4        (object header)                           30 00 00 00 (00110000 00000000 00000000 00000000) (48)
 *       8     4        (object header)                           92 c3 00 f8 (10010010 11000011 00000000 11111000) (-134167662)
 *      12     4        (loss due to the next object alignment)
 *
 *  01111010--7a
 *  00010000--10
 *  10100011--a3
 *  00110000--30
 *  剩下的：00000001  unused->0 gc->0000 biased(偏向)->0 lock(对象的状态)->01
 *
 *  无锁：unused:25 hash:31 unused:1 age:4 0 01
 */
public class JOLExample2 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        A a = new A();
        System.out.println("before hash");
        //没有计算HASHCODe之前的对象头
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        //JVM计算的hashCode,转16进制
        System.out.println("jvm-----------0x" + Integer.toHexString(a.hashCode()));
        //将对象头的hashcode转为16进制并输出
        HashUtil.countHash(a);
        System.out.println("after hash");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
