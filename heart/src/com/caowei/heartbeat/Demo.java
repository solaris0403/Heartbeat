package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

public class Demo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("time 2");
            }
        }, 5 * 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("time 3");
            }
        }, 5 * 1000);
    }
}
