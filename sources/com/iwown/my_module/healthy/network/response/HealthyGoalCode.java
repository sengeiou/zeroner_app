package com.iwown.my_module.healthy.network.response;

import com.iwown.my_module.healthy.network.request.GoalSend;

public class HealthyGoalCode {
    private GoalSend data;
    private int retCode;

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public GoalSend getData() {
        return this.data;
    }

    public void setData(GoalSend data2) {
        this.data = data2;
    }
}
