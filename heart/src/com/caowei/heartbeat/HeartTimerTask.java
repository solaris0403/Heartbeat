package com.caowei.heartbeat;

import java.util.TimerTask;

public abstract class HeartTimerTask extends TimerTask {
    @Override
    public void run() {
        heartbeat();
    }
    public abstract void heartbeat();
    public abstract void timeout();
}
