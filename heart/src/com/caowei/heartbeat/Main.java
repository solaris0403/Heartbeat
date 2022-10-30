package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Main {
    private static Timer timer = new Timer();

    public static void main(String[] args) {
        Heartbeat.getInstance().init(new MyHeartbeat());
        Heartbeat.getInstance().start();
    }

    static class MyHeartbeat implements HeartbeatCallback {
        @Override
        public String onHeartbeat() {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Heartbeat.getInstance().onReceive();
                }
            }, 4 * 1000);//超时10s
            return null;
        }

        @Override
        public void onTimeout() {
            System.out.println("重连服务器。。。");
            System.out.println("重连服务器成功");
            Heartbeat.getInstance().start();
        }
    }
}