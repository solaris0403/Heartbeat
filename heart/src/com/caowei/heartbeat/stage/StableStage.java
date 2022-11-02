package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.HeartConfig;
import com.caowei.heartbeat.Heartbeat;

public class StableStage extends Stage {
    public StableStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        super.success();
        //成功回次，连续失败置0
        heartbeat.failedCount.set(0);
        int successCount = heartbeat.successCount.incrementAndGet();
        //多次成功，调整心跳，返回探测阶段
        if (successCount >= HeartConfig.STABLE_SUCCESS_COUNT){
            heartbeat.successCount.set(0);
            heartbeat.cur_heart += HeartConfig.HEART_DETECT_STEP;
            heartbeat.heart_type = HeartConfig.HEART_TYPE_DETECT;
        }
    }

    @Override
    protected void failed() {
        super.failed();
        int failedCount = heartbeat.failedCount.incrementAndGet();
        //连续失败，返回探测阶段
        if (failedCount >= HeartConfig.STABLE_FAILED_COUNT){
            heartbeat.failedCount.set(0);
            heartbeat.successCount.set(0);
            heartbeat.heart_type = HeartConfig.HEART_TYPE_DETECT;
        }
    }
}
