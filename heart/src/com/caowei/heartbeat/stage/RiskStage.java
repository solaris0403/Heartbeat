package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.Heartbeat;
import com.caowei.heartbeat.stage.Stage;

public class RiskStage extends Stage {
    public RiskStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        System.out.println("success");
        //当前成功心跳
        heartbeat.cur_success_heart = heartbeat.cur_heart;
        //下一次，二倍增涨
        heartbeat.cur_heart = heartbeat.cur_heart * 2;
    }

    @Override
    protected void failed() {
        System.out.println("failed");
        // TODO: 2022/10/31 如果第一次就失败？？？
        //失败，记录当前失败
        heartbeat.cur_failed_heart = heartbeat.cur_heart;
        //修改stage
        heartbeat.heart_type = 1;
        //下一次，二分开启
        heartbeat.cur_heart = (heartbeat.cur_failed_heart + heartbeat.cur_success_heart) / 2;
    }
}
