package com.caowei.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

public class HeartTimer extends Timer {
    /**
     * 执行延迟任务
     *
     * @param task    task to be scheduled.
     * @param delay   delay in milliseconds before task is to be executed.
     * @param timeout time in milliseconds between successive task executions.
     */
    public void schedule(HeartTimerTask task, long delay, long timeout) {
        schedule(task, delay);
        schedule(new TimerTask() {
            @Override
            public void run() {
                task.timeout();
            }
        }, delay + timeout);
    }
}
