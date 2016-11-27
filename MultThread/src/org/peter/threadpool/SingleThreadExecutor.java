package org.peter.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peter-pc on 16-11-27.
 * 4个任务是顺序执行的，SingleThreadExecutor得到的是一个单个的线程，
 * 这个线程会保证你的任务执行完成，如果当前线程意外终止，会创建一个新线程继续执行任务，
 * 这和我们直接创建线程不同，也和newFixedThreadPool(1)不同。
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    for(int i = 1; i < 5; i++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行");
                    }
                }
            });
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池
    }
}
