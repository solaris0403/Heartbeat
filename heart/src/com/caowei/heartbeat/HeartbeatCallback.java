package com.caowei.heartbeat;

public interface HeartbeatCallback {
    String onHeartbeat(HeartbeatPacket packet);
    void onSuccess();
    void onFailed();
}
