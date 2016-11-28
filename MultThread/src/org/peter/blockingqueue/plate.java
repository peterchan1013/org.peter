package org.peter.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by weiyeche on 11/28/2016.
 */
public class plate {
    private BlockingQueue<Object> eggs = new ArrayBlockingQueue<Object>(5);
    public void put(int x){
        try {
            Thread.sleep(1000);
            eggs.put(x);
            System.out.println("put egg");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int get(){
        int x = 0;
        try {
            Thread.sleep(1000);
            x = (int)eggs.take();
            System.out.println("take egg");
        }catch (Exception e){
            e.printStackTrace();
        }
        return x;
    }

    static class put_egg extends Thread{
        private plate plate;
        public put_egg(plate plate){
            this.plate = plate;
        }
        @Override
        public void run() {
            super.run();
            plate.put(1);
        }
    }

    static class take_egg extends Thread{
        private plate plate;
        public  take_egg(plate plate){
            this.plate = plate;
        }

        @Override
        public void run() {
            super.run();
            plate.get();
        }
    }

    public static void main(String args[]){
        plate plate = new plate();
        for(int i = 0;i < 20;i++){
//            new Thread(new put_egg(plate)).start();
            new put_egg(plate).start();
        }
        for(int i = 0;i < 20;i++){
            new take_egg(plate).start();
        }
    }
}
