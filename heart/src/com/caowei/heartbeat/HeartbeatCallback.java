package com.caowei.heartbeat;

public interface HeartbeatCallback {
    void onHeartbeat(long id);
    void onTimeout(long id);
}
