package org.peter.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peter-pc on 16-11-27.
 * 可见，4个任务是交替执行的，CachedThreadPool会创建一个缓存区，将初始化的线程缓存起来，
 * 如果线程有可用的，就使用之前创建好的线程，如果没有可用的，
 * 就新创建线程，终止并且从缓存中移除已有60秒未被使用的线程。
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
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
