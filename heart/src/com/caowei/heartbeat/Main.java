package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static ServerDemo serverDemo = new ServerDemo();
    public static void main(String[] args) {
        serverDemo.start();
        HeartbeatScheduler.getInstance().init(new MyHeartbeat());
        HeartbeatScheduler.getInstance().start();
    }

    static class MyHeartbeat implements HeartbeatCallback {
        @Override
        public void onHeartbeat(long id) {
            serverDemo.receive(new TimerTask() {
                @Override
                public void run() {
                    HeartbeatScheduler.getInstance().onReceive(id);
                }
            });
        }

        @Override
        public void onTimeout(long id) {
            System.out.println("重连服务器。。。");
            serverDemo.login();
            System.out.println("重连服务器成功。。。");
            HeartbeatScheduler.getInstance().start();
        }
    }
}