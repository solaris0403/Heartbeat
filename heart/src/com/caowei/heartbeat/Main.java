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
        public void onHeartbeat() {
            serverDemo.receive();
            if (serverDemo.isAlive){
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        HeartbeatScheduler.getInstance().onReceiveHeartbeat();
                    }
                }, 3 * 1000);//超时10s
            }
        }

        @Override
        public void onTimeout() {
            System.out.println("重连服务器。。。");
            serverDemo.login();
            System.out.println("重连服务器成功。。。");
            HeartbeatScheduler.getInstance().start();
        }
    }
}