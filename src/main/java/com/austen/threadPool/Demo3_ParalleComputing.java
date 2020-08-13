package com.austen.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 *
 * 判断是否是质数，两种实现方式效率对比
 */
public class Demo3_ParalleComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //第一种实现方式
        long start = System.currentTimeMillis();
        List<Integer> results = getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        //第二种实现方式
        final int cupCoreNum = 8;
        ExecutorService service = Executors.newFixedThreadPool(cupCoreNum);
        MyTask myTask1 = new MyTask(1, 50000);
        MyTask myTask2 = new MyTask(50001, 100000);
        MyTask myTask3 = new MyTask(100001, 150000);
        MyTask myTask4 = new MyTask(150001, 200000);

        Future<List<Integer>> f1 = service.submit(myTask1);
        Future<List<Integer>> f2 = service.submit(myTask2);
        Future<List<Integer>> f3 = service.submit(myTask3);
        Future<List<Integer>> f4 = service.submit(myTask4);

        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        end = System.currentTimeMillis();
        System.out.println(end - start);
        service.shutdown();
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;

        public MyTask(int startPos, int endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> r = getPrime(startPos, endPos);
            return r;
        }
    }

    static boolean isPrime(int num) {
        for (int i = 2; i < Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i) && i != 1) {
                results.add(i);
            }
        }
        return results;
    }
}
