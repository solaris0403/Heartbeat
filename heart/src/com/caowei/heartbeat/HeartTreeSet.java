package com.caowei.heartbeat;

import java.util.TreeSet;

public class HeartTreeSet extends TreeSet<Long> {
    private int maxCount = 10;

    public HeartTreeSet(int maxCount) {
        this.maxCount = maxCount;
    }

    public void addHeartbeat(Long o){
        super.add(o);
        while (size() > maxCount){
            pollFirst();
        }
    }
}
