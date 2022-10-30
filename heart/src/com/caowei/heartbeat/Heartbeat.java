package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 第一版，固定心跳
 */
public class Heartbeat {
    private static volatile Heartbeat sInstance;

    private Heartbeat() {
    }

    public static Heartbeat getInstance() {
        if (sInstance == null) {
            synchronized (Heartbeat.class) {
                if (sInstance == null) {
                    sInstance = new Heartbeat();
                }
            }
        }
        return sInstance;
    }

    private static final int curHeart = 30 * 1000;
    private static final int timeout = 5 * 1000;
    private Timer mTimer = new Timer();

    private HeartbeatCallback mHeartbeatCallback;

    public void setHeartbeatCallback(HeartbeatCallback callback) {
        mHeartbeatCallback = callback;
    }

    private HeartbeatPacket mHeartbeatPacket;

    /**
     * 当服务器连接成功之后开始心跳
     */
    public void start() {
        stop();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //设置超时判断
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //如果超时，则返回失败
                        mHeartbeatCallback.onFailed();
                    }
                }, timeout);
                //当心跳间隔到达时，触发发送心跳，并返回心跳消息的id
                mHeartbeatCallback.onHeartbeat(new HeartbeatPacket());
            }
        }, curHeart);
    }

    /**
     * 由于心跳包需要回执来验证结果，有延迟，所以需要子在收到回执的时候主动调用该方法，并返回mid
     */
    public void onReceipt(HeartbeatPacket packet) {
        if (mHeartbeatPacket.getMid().equals(packet.getMid())
                && (System.currentTimeMillis() - mHeartbeatPacket.getTimestamp()) <= timeout) {
            //心跳成功
            start();
            System.out.println("onSuccess");
        } else {
            //心跳失败
            mHeartbeatCallback.onFailed();
        }
    }

    /**
     * 停止当前心跳逻辑，是否要保持进度？？？
     */
    public void stop() {
        mTimer.cancel();
        mTimer = new Timer();
    }
}
