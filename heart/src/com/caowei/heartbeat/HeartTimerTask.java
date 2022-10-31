package com.caowei.heartbeat;

import java.util.TimerTask;

public abstract class HeartTimerTask {
    public abstract void heartbeat();
    public abstract void timeout();
}
