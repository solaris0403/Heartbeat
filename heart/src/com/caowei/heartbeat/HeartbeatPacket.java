package com.caowei.heartbeat;

import java.util.UUID;

public class HeartbeatPacket {
    private final String mid;
    private final long timestamp;

    public HeartbeatPacket() {
        this.mid = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        System.out.println("this.timestamp:"+this.timestamp);
    }

    public String getMid() {
        return mid;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
