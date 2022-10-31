package com.caowei.heartbeat;

import com.caowei.heartbeat.stage.AdjustStage;
import com.caowei.heartbeat.stage.DichotomyStage;
import com.caowei.heartbeat.stage.RiskStage;
import com.caowei.heartbeat.stage.StableStage;
import com.google.gson.JsonObject;

import java.util.concurrent.atomic.AtomicInteger;

public class Heartbeat {
    //心跳类型，0：初始类型，该阶段需要快速上涨，1，该阶段需要二分法判断，2稳定阶段，3智能阶段
    public int heart_type = 0;

    //心跳包发出后超时时间
    public int timeout = 10 * 1000;
    //当前心跳
    public int cur_heart = 10 * 1000;
    public int max_heart = 100 * 1000;
    public int min_heart = 10 * 1000;

    public int max_success_heart = 0;
    public int min_success_heart = 0;

    public int min_failed_heart = 0;
    public int max_failed_heart = 0;

    public int cur_failed_heart = 0;
    public int cur_success_heart = 0;

    private final AtomicInteger stabledSuccessCount = new AtomicInteger(0); //心跳连续成功的次数
    private final AtomicInteger stabledFailedCount = new AtomicInteger(0); //心跳连续失败的次数

    public Heartbeat() {
        JsonObject object = JsonUtils.getInstance().getHeartbeatConfig();
    }

    /**
     * 如果心跳成功，则成功心跳+1，失败心跳置0
     */
    public void success() {
        adjust(true);
    }

    /**
     * 如果心跳失败，则失败心跳+1， 成功心跳置0
     */
    public void failed() {
        adjust(false);
    }

    private void adjust(boolean result) {
        switch (heart_type) {
            case 0:
                new RiskStage(this).onResult(result);
                break;
            case 1:
                new DichotomyStage(this).onResult(result);
                break;
            case 2:
                new StableStage(this).onResult(result);
                break;
            case 3:
                new AdjustStage(this).onResult(result);
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "heart_type=" + heart_type +
                ", timeout=" + timeout +
                ", cur_heart=" + cur_heart +
                ", max_heart=" + max_heart +
                ", min_heart=" + min_heart +
                ", max_success_heart=" + max_success_heart +
                ", min_success_heart=" + min_success_heart +
                ", min_failed_heart=" + min_failed_heart +
                ", max_failed_heart=" + max_failed_heart +
                ", cur_failed_heart=" + cur_failed_heart +
                ", cur_success_heart=" + cur_success_heart +
                ", stabledSuccessCount=" + stabledSuccessCount +
                ", stabledFailedCount=" + stabledFailedCount +
                '}';
    }
}
