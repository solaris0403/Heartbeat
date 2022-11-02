package com.caowei.heartbeat;

import java.util.ArrayList;
import java.util.Collections;

public class HeartArray extends ArrayList<Long> {
    private int maxCount = 20;

    public HeartArray(int maxCount) {
        this.maxCount = maxCount;
    }

    public void addHeartbeat(Long o) {
        //如果存在则直接返回
        if (contains(o)) {
            return;
        }
        add(o);
        Collections.sort(this);
        while (size() > maxCount) {
            remove(0);
        }
    }
}
