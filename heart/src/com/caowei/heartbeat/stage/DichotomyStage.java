package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.Heartbeat;
import com.caowei.heartbeat.stage.Stage;

public class DichotomyStage extends Stage {
    public DichotomyStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        heartbeat.cur_success_heart = heartbeat.cur_heart;

        if (heartbeat.cur_failed_heart - heartbeat.cur_success_heart > 10){
            heartbeat.cur_heart = (heartbeat.cur_success_heart + heartbeat.cur_failed_heart) / 2;
        }else{
            heartbeat.heart_type = 2;
        }
    }

    @Override
    protected void failed() {
        heartbeat.cur_failed_heart = heartbeat.cur_heart;

        if (heartbeat.cur_failed_heart - heartbeat.cur_success_heart > 10){
            heartbeat.cur_heart = (heartbeat.cur_success_heart + heartbeat.cur_failed_heart) / 2;
        }else{
            heartbeat.cur_heart = heartbeat.cur_success_heart;
            heartbeat.heart_type = 2;
        }
    }
}
