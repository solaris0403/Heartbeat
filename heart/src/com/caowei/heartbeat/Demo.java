package com.caowei.heartbeat;

import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

public class Demo {
    public static void main(String[] args) {
        JsonObject object = JsonUtils.getInstance().getHeartbeatConfig();
        System.out.println(object);
    }
}
