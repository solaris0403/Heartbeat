package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static Timer timer  = new Timer();
    public static void main(String[] args) {
        MyHeartbeat myHeartbeat = new MyHeartbeat();
        Heartbeat.getInstance().setHeartbeatCallback(myHeartbeat);
        Heartbeat.getInstance().start();
    }

    static class MyHeartbeat implements HeartbeatCallback{

        @Override
        public String onHeartbeat(HeartbeatPacket packet) {
            System.out.println("packet:" + packet.getMid());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Heartbeat.getInstance().onReceipt(packet);
                }
            }, 10 * 1000);
            return null;
        }

        @Override
        public void onSuccess() {
            System.out.println("onSuccess");
        }

        @Override
        public void onFailed() {
            System.out.println("onFailed");
        }
    }
}