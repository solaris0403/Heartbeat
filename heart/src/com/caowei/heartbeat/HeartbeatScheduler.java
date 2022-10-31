package com.caowei.heartbeat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 第一版，固定心跳
 */
public class HeartbeatScheduler {
    private static volatile HeartbeatScheduler sInstance;

    private HeartbeatScheduler() {
    }

    public static HeartbeatScheduler getInstance() {
        if (sInstance == null) {
            synchronized (HeartbeatScheduler.class) {
                if (sInstance == null) {
                    sInstance = new HeartbeatScheduler();
                }
            }
        }
        return sInstance;
    }

    private HeartbeatCallback mHeartbeatCallback;

    public void init(HeartbeatCallback callback) {
        mHeartbeatCallback = callback;
    }

    private final HeartTimer mHeartTimer = new HeartTimer();

    /**
     * 开始心跳
     */
    public synchronized void start() {
        long start = System.currentTimeMillis();
        stop();
        HeartTimerTask heartTimerTask = new HeartTimerTask() {
            @Override
            public void heartbeat() {
                //心跳间隔到达
                System.out.println("心跳触发:" + DateUtil.getCurrentTime());
                mHeartbeatCallback.onHeartbeat();
            }

            @Override
            public void timeout() {
                System.out.println("心跳超时:" + DateUtil.getCurrentTime());
                //只需要告诉业务超时，至于业务是什么操作不关心
                mHeartbeatCallback.onTimeout();
            }
        };
        mHeartTimer.schedule(heartTimerTask);
        System.out.println("重启心跳:" + DateUtil.getCurrentTime() + " 耗时：" + (System.currentTimeMillis() - start));
    }

    /**
     * 收到普通消息只需要重置心跳
     */
    public synchronized void onReceive() {
        System.out.println("收到消息:" + DateUtil.getCurrentTime());
        start();
    }

    /**
     * 收到心跳消息则需要调整心跳策略
     */
    public synchronized void onReceiveHeartbeat() {
        // TODO: 2022/10/31 需要对心跳消息进行id判断
        mHeartTimer.onReceiveHeartbeat();
        onReceive();
    }

    /**
     * 停止当前心跳逻辑，是否要保持进度？？？
     */
    public void stop() {
        mHeartTimer.cancel();
    }
}
