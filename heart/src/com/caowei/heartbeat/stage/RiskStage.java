package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.HeartConfig;
import com.caowei.heartbeat.Heartbeat;
import com.caowei.heartbeat.stage.Stage;

public class RiskStage extends Stage {
    public RiskStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        super.success();
        //倍数增涨
        heartbeat.cur_heart = (long) (heartbeat.cur_success_heart * HeartConfig.RISK_MULTIPLE);
    }

    @Override
    protected void failed() {
        super.failed();
        if (heartbeat.cur_failed_heart > heartbeat.cur_success_heart){
            //进入下一阶段
            heartbeat.heart_type = HeartConfig.HEART_TYPE_DICHOTOMY;
            heartbeat.cur_heart = (heartbeat.cur_failed_heart + heartbeat.cur_success_heart) / 2;
        }else{
            //减少时间
            long down = (int) (heartbeat.cur_failed_heart / HeartConfig.RISK_MULTIPLE);
            heartbeat.cur_heart = Math.max(HeartConfig.MIN_HEART, down);
            System.out.println("RiskStage失败");
        }
    }
}
