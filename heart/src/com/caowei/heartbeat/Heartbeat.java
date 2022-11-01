package com.caowei.heartbeat;

import com.caowei.heartbeat.stage.DichotomyStage;
import com.caowei.heartbeat.stage.RiskStage;
import com.caowei.heartbeat.stage.StableStage;

import java.util.concurrent.atomic.AtomicInteger;

public class Heartbeat {
    public volatile long heart_id = 0;
    public byte heart_type = HeartConfig.HEART_TYPE_RISK;
    public long cur_heart = HeartConfig.MIN_HEART;
    public long cur_failed_heart = 0;
    public long cur_success_heart = 0;
    public long stable_success_heart = 0;
    public long stable_failed_heart = 0;

    public HeartTreeSet history_success_heart = new HeartTreeSet(20);
    public HeartTreeSet history_failed_heart = new HeartTreeSet(20);

    public AtomicInteger successCount = new AtomicInteger(0);
    public AtomicInteger failedCount = new AtomicInteger(0);

    //收到结果之后需要对心跳配置重新计算
    public void update(boolean result) {
        switch (heart_type) {
            case HeartConfig.HEART_TYPE_RISK:
                new RiskStage(this).onResult(result);
                break;
            case HeartConfig.HEART_TYPE_DICHOTOMY:
                new DichotomyStage(this).onResult(result);
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
                ", stable_success_heart=" + stable_success_heart +
                ", stable_failed_heart=" + stable_failed_heart +
                ", history_success_heart=" + history_success_heart +
                ", history_failed_heart=" + history_failed_heart +
                ", successCount=" + successCount +
                ", failedCount=" + failedCount +
                '}';
    }
}