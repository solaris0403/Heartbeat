package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartTimer {
    //等待回执、消息
    public volatile boolean isWaiting = false;
    private Timer mTimer;
    private TimerTask mHeartbeatTask;
    private TimerTask mTimeoutTask;

    public final Heartbeat mHeartbeat = new Heartbeat();

    /**
     * 执行延迟任务
     *
     * @param task    task to be scheduled.
     */
    public void schedule(HeartTimerTask task) {
        isWaiting = false;
        mTimer = new Timer();
        mHeartbeatTask = new TimerTask() {
            @Override
            public void run() {
                isWaiting = true;
                task.heartbeat();
            }
        };
        mTimeoutTask = new TimerTask() {
            @Override
            public void run() {
                isWaiting = false;
                mHeartbeat.failed();
                task.timeout();
            }
        };
        mTimer.schedule(mHeartbeatTask, mHeartbeat.cur_heart);
        mTimer.schedule(mTimeoutTask, mHeartbeat.cur_heart + mHeartbeat.timeout);
        System.out.println(mHeartbeat.toString());
    }

    public void cancel() {
        isWaiting = false;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mHeartbeatTask != null) {
            mHeartbeatTask.cancel();
            mHeartbeatTask = null;
        }
        if (mTimeoutTask != null) {
            mTimeoutTask.cancel();
            mTimeoutTask = null;
        }
    }

    public void onReceiveHeartbeat(){
        if (isWaiting){
            //等待回执中
            mHeartbeat.success();
        }
    }
}
