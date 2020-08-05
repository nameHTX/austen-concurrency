关于高并发方面的学习
一、线程
    1)interrupt();
        参考：austen-concurrency->com.austen.com.austen.threadExample.interrupt
        --用来优雅停止线程的函数
        --jvm不推荐直接停止一个线程，一定要让一个线程执行完
        --假设现在在阻塞->解阻塞
        --假设线程是while(flag)->flag=false
    		
二、锁
    1)synchronized
        参考：austen-concurrency->com.austen.com.austen.lockExample.sync
        -- 实现一把锁
        --偏向锁
        --轻量级锁
        --重量级锁
            