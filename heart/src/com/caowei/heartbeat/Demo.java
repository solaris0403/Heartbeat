package com.caowei.heartbeat;

import com.google.gson.JsonObject;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class Demo {
    public static void main(String[] args) {
//        JsonObject object = JsonUtils.getInstance().getHeartbeatConfig();
//        System.out.println(object);
        Set<Long> set = new TreeSet<>();
        set.add(30L);
        set.add(20L);
        set.add(10L);
        set.add(50L);
        set.add(90L);
        System.out.println(set.size());
    }
}
