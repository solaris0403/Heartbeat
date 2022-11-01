package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.Heartbeat;

public class Stage {
    protected Heartbeat heartbeat;

    public Stage(Heartbeat heartbeat) {
        this.heartbeat = heartbeat;
    }

    /**
     * 根据当前心跳结果调整下一次心跳数据
     * @param result
     */
    public void onResult(boolean result) {
        if (result) {
            success();
        } else {
            failed();
        }
    }

    protected void success() {
        heartbeat.history_success_heart.addHeartbeat(heartbeat.cur_heart);
        heartbeat.cur_success_heart = heartbeat.cur_heart;
    }

    protected void failed() {
        heartbeat.history_failed_heart.addHeartbeat(heartbeat.cur_heart);
        heartbeat.cur_failed_heart = heartbeat.cur_heart;
    }
}
