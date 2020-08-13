package com.austen.threadPool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Austen.Huang
 * @date 2020/8/13
 *
 * 切分任务，ForkJoin自动启线程去执行
 */
public class Demo8_ForkJoinPool {

    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
    }

    /**
     * 只fork不join，就是只做任务切分，不返回结果
     * 底层就是Runnable
     */
    static class AddTask extends RecursiveAction {
        int start, end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println(sum);
            } else {
                int middle = start + (end - start) / 2;
                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }

    /**
     * 会返回结果
     * 底层就是Callable
     */
    static class AddTask1 extends RecursiveTask<Long> {
        int start, end;

        public AddTask1(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }
            int middle = start + (end - start) / 2;
            AddTask1 subTask1 = new AddTask1(start, middle);
            AddTask1 subTask2 = new AddTask1(middle, end);
            subTask1.fork();
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        forkJoinPool.execute(task);

        //用Task1时开启
        //long result = task.join();
        //System.out.println(result);

        TimeUnit.SECONDS.sleep(10);
    }
}
