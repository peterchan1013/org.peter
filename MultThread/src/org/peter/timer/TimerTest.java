package org.peter.timer;
/**
 * Created by peter-pc on 16-11-27.
 */
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
public class TimerTest {
    static class MyTimerTask1 extends TimerTask {
        public void run() {
            System.out.println("boom!!!");
            new Timer().schedule(new MyTimerTask2(), 2000);
        }
    }
    static class MyTimerTask2 extends TimerTask {
        public void run() {
            System.out.println("boom!!!");
            new Timer().schedule(new MyTimerTask1(), 3000);
        }
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask2(), 2000);
        //print seconds
        while(true) {
            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
