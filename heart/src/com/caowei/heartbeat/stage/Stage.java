package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.Heartbeat;

public abstract class Stage {
    protected Heartbeat heartbeat;

    public Stage(Heartbeat heartbeat) {
        this.heartbeat = heartbeat;
    }

    public void onResult(boolean result) {
        if (result) {
            success();
        } else {
            failed();
        }
    }

    protected abstract void success();

    protected abstract void failed();
}
