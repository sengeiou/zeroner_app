package com.iwown.sport_module.gps.data;

import org.litepal.crud.DataSupport;

public class TB_sport_all_other extends DataSupport {
    private float calorie;
    private float calorie_net;
    private int down;
    private int duration;
    private int duration_net;
    private int times;
    private int times_net;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getTimes() {
        return this.times;
    }

    public void setTimes(int times2) {
        this.times = times2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration2) {
        this.duration = duration2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public int getTimes_net() {
        return this.times_net;
    }

    public void setTimes_net(int times_net2) {
        this.times_net = times_net2;
    }

    public int getDuration_net() {
        return this.duration_net;
    }

    public void setDuration_net(int duration_net2) {
        this.duration_net = duration_net2;
    }

    public float getCalorie_net() {
        return this.calorie_net;
    }

    public void setCalorie_net(float calorie_net2) {
        this.calorie_net = calorie_net2;
    }

    public int getDown() {
        return this.down;
    }

    public void setDown(int down2) {
        this.down = down2;
    }
}
