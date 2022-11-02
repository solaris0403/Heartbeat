package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.HeartConfig;
import com.caowei.heartbeat.Heartbeat;

/**
 * 心跳探测阶段
 */
public class DetectStage extends Stage{
    public DetectStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        super.success();
        int successCount = heartbeat.successCount.incrementAndGet();
        if (successCount >= HeartConfig.DETECT_SUCCESS_COUNT){
            heartbeat.successCount.set(0);
            heartbeat.failedCount.set(0);
            heartbeat.heart_type = HeartConfig.HEART_TYPE_STABLE;
        }
    }

    @Override
    protected void failed() {
        super.failed();
        heartbeat.successCount.set(0);
        int failedCount = heartbeat.failedCount.incrementAndGet();
        if (failedCount >= HeartConfig.DETECT_FAILED_COUNT){
            heartbeat.failedCount.set(0);
            long down = heartbeat.cur_failed_heart - HeartConfig.HEART_DETECT_STEP;
            heartbeat.cur_heart = Math.max(down, HeartConfig.MIN_HEART);
        }
    }
}
