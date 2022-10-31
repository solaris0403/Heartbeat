package com.caowei.heartbeat;

public interface HeartbeatCallback {
    void onHeartbeat();
    void onTimeout();
}
