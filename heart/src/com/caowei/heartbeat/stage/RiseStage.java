package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.HeartConfig;
import com.caowei.heartbeat.Heartbeat;

/**
 * 使用指定倍数增长，快速达到失败心跳
 */
public class RiseStage extends Stage {
    public RiseStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        super.success();
        //倍数增涨
        heartbeat.cur_heart = (long) (heartbeat.cur_success_heart * HeartConfig.RISE_MULTIPLE);
    }

    @Override
    protected void failed() {
        super.failed();
        if (heartbeat.cur_failed_heart > heartbeat.cur_success_heart){
            //进入下一阶段
            heartbeat.heart_type = HeartConfig.HEART_TYPE_LOCATE;
            heartbeat.cur_heart = (heartbeat.cur_failed_heart + heartbeat.cur_success_heart) / 2;
        }else{
            //减少时间
            long down = (int) (heartbeat.cur_failed_heart / HeartConfig.RISE_MULTIPLE);
            heartbeat.cur_heart = Math.max(HeartConfig.MIN_HEART, down);
            System.out.println("RiseStage失败");
        }
    }
}
