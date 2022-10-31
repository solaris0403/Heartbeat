package com.caowei.heartbeat;

import java.util.TimerTask;

public abstract class HeartTimerTask extends TimerTask {
    public abstract void timeout();
}
