package com.caowei.heartbeat.stage;

import com.caowei.heartbeat.Heartbeat;
import com.caowei.heartbeat.stage.Stage;

public class StableStage extends Stage {
    public StableStage(Heartbeat heartbeat) {
        super(heartbeat);
    }

    @Override
    protected void success() {

    }

    @Override
    protected void failed() {

    }
}
