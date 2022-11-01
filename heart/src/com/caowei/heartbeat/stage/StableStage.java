package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.HeartConfig;
import com.caowei.heartbeat.Heartbeat;
import com.caowei.heartbeat.stage.Stage;

public class StableStage extends Stage {
    public StableStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        super.success();
        int successCount = heartbeat.successCount.incrementAndGet();
        if (successCount >= HeartConfig.MAX_SUCCESS_COUNT){
            //尝试增加时间
            heartbeat.successCount.set(0);
            heartbeat.failedCount.set(0);
            heartbeat.cur_heart += HeartConfig.HEART_ADJUST_STEP;
        }
    }

    @Override
    protected void failed() {
        super.failed();
        int failedCount = heartbeat.failedCount.incrementAndGet();
        if (failedCount >= HeartConfig.MAX_FAILED_COUNT){
            //尝试减少时间
            heartbeat.successCount.set(0);
            heartbeat.failedCount.set(0);
            long down = heartbeat.cur_heart - HeartConfig.HEART_ADJUST_STEP;
            heartbeat.cur_heart = Math.max(down, HeartConfig.MIN_HEART);
        }
    }
}
