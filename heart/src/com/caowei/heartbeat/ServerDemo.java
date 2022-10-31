package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

public class ServerDemo {
    private Timer timer;
    private TimerTask timerTask;
    public void start(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("NAT超时");
            }
        };
        timer.schedule(timerTask, 20 * 1000);
    }

    public void receive(){
        System.out.println("Server重置NAT");
        start();
    }
}
