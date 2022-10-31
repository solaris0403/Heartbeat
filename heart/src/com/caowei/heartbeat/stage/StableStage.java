package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.Heartbeat;
import com.caowei.heartbeat.stage.Stage;

public class StableStage extends Stage {
    public StableStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {
        heartbeat.stabledFailedCount.set(0);
        int stabledSuccessCount = heartbeat.stabledSuccessCount.incrementAndGet();
        if (stabledSuccessCount >= 3){
            //稳定成功,进入下一阶段
        }
    }

    @Override
    protected void failed() {
        heartbeat.stabledSuccessCount.set(0);
        int stabledFailedCount = heartbeat.stabledFailedCount.incrementAndGet();
        if (stabledFailedCount >= 3){
            //稳定失败，需要返回二分法重新调整
        }
    }
}
