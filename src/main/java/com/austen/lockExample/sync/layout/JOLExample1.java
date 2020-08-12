package com.austen.lockExample.sync.layout;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author Austen.Huang
 * @date 2020/8/10
 * 获取虚拟机信息及对象头信息
 */
public class JOLExample1 {

    private static A a = new A();

    public static void main(String[] args) {


        /**
         * # Running 64-bit HotSpot VM.
         * # Using compressed oop with 3-bit shift.
         * # Using compressed klass with 3-bit shift.
         * # Objects are 8 bytes aligned.
         * # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         * # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         */
        System.out.println(VM.current().details());


        /**
         * com.austen.lockExample.sync.layout.A object internals:
         *  OFFSET  SIZE      TYPE DESCRIPTION                               VALUE
         *       0     4           (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4           (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4           (object header)                           92 c3 00 f8 (10010010 11000011 00000000 11111000) (-134167662)
         *      12     1   boolean A.flag                                    false
         *      13     3           (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 3 bytes external = 3 bytes total
         */
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
