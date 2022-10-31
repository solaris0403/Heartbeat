package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

public class ServerDemo {
    public volatile boolean isAlive = false;
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
                isAlive = false;
                System.out.println("NAT超时");
            }
        };
        timer.schedule(timerTask, 30 * 1000);
        isAlive = true;
    }

    public void receive(){
        if (isAlive){
            System.out.println("Server重置NAT");
            start();
        }
    }

    public void login(){
        start();
    }
}
