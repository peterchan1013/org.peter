package org.peter.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by weiyeche on 11/28/2016.
 * 利用java Condition解决读写问题
 */
public class ConditionBuffer {
    static final Lock lock = new ReentrantLock();//锁对象
    static final Condition notFull  = lock.newCondition();//写线程条件
    static final Condition notEmpty = lock.newCondition();//读线程条件

    static final Object[] items = new Object[5];//缓存队列
    static int putptr/*写索引*/, takeptr/*读索引*/, count/*队列中存在的数据个数*/;
    public static Runnable r_put = new Runnable() {
        @Override
        public void run() {
            try {
                put(Math.random());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    public static Runnable r_take = new Runnable() {
        @Override
        public void run() {
            try {
                take();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public static void main(String args[]){
        try {
            for (int i = 0; i < 10; i++) {
                new Thread(r_take).start();
                Thread.sleep(1000);
                new Thread(r_put).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)//如果队列满了
                notFull.await();//阻塞写线程
            items[putptr] = x;//赋值
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"给items的第 "+putptr+" 个元素赋值："+x);
            if (++putptr == items.length) putptr = 0;//如果写索引写到队列的最后一个位置了，那么置为0
            ++count;//个数++
            notEmpty.signal();//唤醒读线程
        } finally {
            lock.unlock();
        }
    }

    public static Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)//如果队列为空
                notEmpty.await();//阻塞读线程
            Object x = items[takeptr];//取值
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"取走items的第 "+takeptr+" 个值："+x);
            if (++takeptr == items.length) takeptr = 0;//如果读索引读到队列的最后一个位置了，那么置为0
            --count;//个数--
            notFull.signal();//唤醒写线程
            return x;
        } finally {
            lock.unlock();
        }
    }
}
