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

    /**
     * 当前使用的心跳间隔
     */
    private static final int curHeart = 10 * 1000;
    /**
     * 发出心跳包之后的超时时间
     */
    private static final int timeout = 3 * 1000;

    private HeartbeatCallback mHeartbeatCallback;

    public void init(HeartbeatCallback callback) {
        mHeartbeatCallback = callback;
    }

    private HeartTimer mHeartTimer;
    private HeartTimerTask mHeartTimerTask;

    /**
     * 开始心跳
     */
    public synchronized void start() {
        stop();
        mHeartTimer = new HeartTimer();
        mHeartTimerTask = new HeartTimerTask() {
            @Override
            public void run() {
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
        mHeartTimer.schedule(mHeartTimerTask, curHeart, timeout);
        System.out.println("重启心跳:" + DateUtil.getCurrentTime());
    }

    /**
     * 由于心跳包需要回执来验证结果，有延迟，所以需要子在收到回执的时候主动调用该方法
     * 其实不用判断，因为超时已经决定了网络有问题，如果收到回执，那么说明网络是好的，需要重置计时器
     * 如果在超时之前收到这条回执，那么也要重置计时器，
     * 这个回执相当于都要重置计时器，所以该方法可以改名为onReceive，收到消息，手动重置计时器
     */
    public synchronized void onReceive() {
        System.out.println("收到消息:" + DateUtil.getCurrentTime());
        start();
    }

    /**
     * 停止当前心跳逻辑，是否要保持进度？？？
     */
    public void stop() {
        if (mHeartTimer != null) {
            mHeartTimer.cancel();
            mHeartTimer = null;
        }
        if (mHeartTimerTask != null) {
            mHeartTimerTask.cancel();
            mHeartTimerTask = null;
        }
    }
}
