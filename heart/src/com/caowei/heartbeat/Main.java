package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Main {
    private static Timer timer  = new Timer();
    public static void main(String[] args) {
        MyHeartbeat myHeartbeat = new MyHeartbeat();
        Heartbeat.getInstance().setHeartbeatCallback(myHeartbeat);
        Heartbeat.getInstance().start();
    }

    static class MyHeartbeat implements HeartbeatCallback{

        @Override
        public String onHeartbeat() {
            final String mid = UUID.randomUUID().toString();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Heartbeat.getInstance().onReceipt(mid);
                }
            }, 6 * 1000);//超时10s
            return mid;
        }

        @Override
        public void onFailed() {
            System.out.println("onFailed");
            Heartbeat.getInstance().start();
        }
    }
}