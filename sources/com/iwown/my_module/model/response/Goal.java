package com.iwown.my_module.model.response;

public class Goal {
    private float lose_weight_speed;
    private int retCode;
    private int target_step;
    private float target_weight;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public float getTarget_weight() {
        return this.target_weight;
    }

    public void setTarget_weight(float target_weight2) {
        this.target_weight = target_weight2;
    }

    public int getTarget_step() {
        return this.target_step;
    }

    public void setTarget_step(int target_step2) {
        this.target_step = target_step2;
    }

    public float getLose_weight_speed() {
        return this.lose_weight_speed;
    }

    public void setLose_weight_speed(float lose_weight_speed2) {
        this.lose_weight_speed = lose_weight_speed2;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }
}
