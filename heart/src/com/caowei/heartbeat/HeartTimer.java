package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartTimer {
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
        mHeartbeat.heart_id = 0;
        mTimer = new Timer();
        mHeartbeatTask = new TimerTask() {
            @Override
            public void run() {
                mHeartbeat.heart_id = System.currentTimeMillis();
                task.heartbeat(mHeartbeat.heart_id);
            }
        };
        mTimeoutTask = new TimerTask() {
            @Override
            public void run() {
                mHeartbeat.update(false);
                task.timeout(mHeartbeat.heart_id);
                mHeartbeat.heart_id = 0;
            }
        };
        mTimer.schedule(mHeartbeatTask, mHeartbeat.cur_heart);
        mTimer.schedule(mTimeoutTask, mHeartbeat.cur_heart + HeartConfig.TIMEOUT);
        System.out.println("开始执行心跳：" + mHeartbeat.toString());
    }

    public void cancel() {
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
        mHeartbeat.heart_id = 0;
    }

    /**
     * 心跳回执
     * @param id 心跳ID
     */
    public synchronized void onReceipt(long id){
        if (id != 0 && id == mHeartbeat.heart_id){
            mHeartbeat.update(true);
        }
    }
}
