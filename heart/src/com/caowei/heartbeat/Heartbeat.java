package com.caowei.heartbeat;

import com.caowei.heartbeat.stage.DetectStage;
import com.caowei.heartbeat.stage.LocateStage;
import com.caowei.heartbeat.stage.RiseStage;
import com.caowei.heartbeat.stage.StableStage;

import java.util.concurrent.atomic.AtomicInteger;

public class Heartbeat {
    public volatile long heart_id = 0;
    public byte heart_type = HeartConfig.HEART_TYPE_RISE;
    public long cur_heart = HeartConfig.DEFAULT_HEART;
    public long cur_failed_heart = 0;
    public long cur_success_heart = 0;

    public HeartArray history_success_heart = new HeartArray(HeartConfig.HEART_HISTORY);
    public HeartArray history_failed_heart = new HeartArray(HeartConfig.HEART_HISTORY);

    public AtomicInteger successCount = new AtomicInteger(0);
    public AtomicInteger failedCount = new AtomicInteger(0);

    //收到结果之后需要对心跳配置重新计算
    public void update(boolean result) {
        switch (heart_type) {
            case HeartConfig.HEART_TYPE_RISE:
                new RiseStage(this).onResult(result);
                break;
            case HeartConfig.HEART_TYPE_LOCATE:
                new LocateStage(this).onResult(result);
                break;
            case HeartConfig.HEART_TYPE_DETECT:
                new DetectStage(this).onResult(result);
                break;
            case HeartConfig.HEART_TYPE_STABLE:
                new StableStage(this).onResult(result);
                break;
            default:
                break;
        }
        //调整之后需要保存
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "heart_id=" + heart_id +
                ", heart_type=" + heart_type +
                ", cur_heart=" + cur_heart +
                ", cur_failed_heart=" + cur_failed_heart +
                ", cur_success_heart=" + cur_success_heart +
                ", history_success_heart=" + history_success_heart +
                ", history_failed_heart=" + history_failed_heart +
                ", successCount=" + successCount +
                ", failedCount=" + failedCount +
                '}';
    }
}