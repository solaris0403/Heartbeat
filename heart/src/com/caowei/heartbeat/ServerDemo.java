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
                System.out.println("服务器NAT超时。。。。。。");
            }
        };
        timer.schedule(timerTask, 42 * 1000);
        isAlive = true;
    }

    public void receive(TimerTask timerTask){
        if (isAlive){
            System.out.println("服务器重置NAT。。。");
            start();
            timer.schedule(timerTask, 3 * 1000);
        }
    }

    public void login(){
        start();
    }
}
