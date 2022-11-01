package com.caowei.heartbeat;

public abstract class HeartTimerTask {
    public abstract void heartbeat(long id);
    public abstract void timeout(long id);
}
