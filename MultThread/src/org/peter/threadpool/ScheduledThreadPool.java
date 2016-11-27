package org.peter.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter-pc on 16-11-27.
 * ScheduledThreadPool可以定时的或延时的执行任务
 */
public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(1);
        // 5秒后执行任务
        schedulePool.schedule(new Runnable() {
            public void run() {
                System.out.println("boom_1");
            }
        }, 5, TimeUnit.SECONDS);
        // 5秒后执行任务，以后每2秒执行一次
        schedulePool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("boom_2");
            }
        }, 5, 2, TimeUnit.SECONDS);
    }
}
