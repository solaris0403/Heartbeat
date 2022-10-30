package com.caowei.heartbeat;

public interface HeartbeatCallback {
    /**
     * @return 心跳标识符
     */
    String onHeartbeat();
    void onTimeout();
}
