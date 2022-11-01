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

    public synchronized void start() {
        long start = System.currentTimeMillis();
        stop();
        HeartTimerTask heartTimerTask = new HeartTimerTask() {
            @Override
            public void heartbeat(long id) {
                System.out.println("发送心跳:" + DateUtil.getCurrentTime());
                mHeartbeatCallback.onHeartbeat(id);
            }

            @Override
            public void timeout(long id) {
                System.out.println("心跳超时:" + DateUtil.getCurrentTime());
                //只需要告诉业务超时，至于业务是什么操作不关心
                mHeartbeatCallback.onTimeout(id);
            }
        };
        mHeartTimer.schedule(heartTimerTask);
        System.out.println("启动心跳:" + DateUtil.getCurrentTime() + " 耗时：" + (System.currentTimeMillis() - start));
    }

    /**
     * 收到普通消息只需要重置心跳
     */
    public synchronized void onReceive(long id) {
        System.out.println("收到消息:" + DateUtil.getCurrentTime());
        mHeartTimer.onReceipt(id);
        start();
    }

    /**
     * 停止当前心跳逻辑
     */
    public void stop() {
        mHeartTimer.cancel();
    }
}
