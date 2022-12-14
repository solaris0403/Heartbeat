package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.HeartConfig;
import com.caowei.heartbeat.Heartbeat;

/**
 * 在成功和失败之间使用二分法定位最小成功心跳
 */
public class LocateStage extends Stage {
    public LocateStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        super.success();
        if (heartbeat.cur_failed_heart - heartbeat.cur_success_heart > HeartConfig.CRITICAL){
            heartbeat.cur_heart = (heartbeat.cur_success_heart + heartbeat.cur_failed_heart) / 2;
        }else{
            heartbeat.heart_type = HeartConfig.HEART_TYPE_DETECT;
        }
    }

    @Override
    protected void failed() {
        super.failed();
        if (heartbeat.cur_failed_heart - heartbeat.cur_success_heart > HeartConfig.CRITICAL){
            heartbeat.cur_heart = (heartbeat.cur_success_heart + heartbeat.cur_failed_heart) / 2;
        }else{
            heartbeat.cur_heart = heartbeat.cur_success_heart;
            heartbeat.heart_type = HeartConfig.HEART_TYPE_DETECT;
        }
    }
}
