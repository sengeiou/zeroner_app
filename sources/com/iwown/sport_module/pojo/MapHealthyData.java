package com.iwown.sport_module.pojo;

import com.iwown.sport_module.util.Util;

public class MapHealthyData {
    private int active_time = 0;
    private int cal = 0;
    private float distance = 0.0f;
    private int hr = 0;
    private boolean isMertric = true;
    private int pace = 0;
    private int rate = 0;
    private float speed = 0.0f;
    private int stride = 0;
    private int total_step = 0;

    public boolean isMertric() {
        return this.isMertric;
    }

    public void setMertric(boolean mertric) {
        this.isMertric = mertric;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        if (this.isMertric) {
            this.distance = Util.doubleToFloat(2, (double) distance2);
        } else {
            this.distance = Util.doubleToFloat(2, Util.meterToMile((double) distance2));
        }
    }

    public int getActive_time() {
        return this.active_time;
    }

    public void setActive_time(int active_time2) {
        this.active_time = active_time2;
    }

    public int getPace() {
        return this.pace;
    }

    public void setPace(int pace2) {
        this.pace = pace2;
    }

    public int getCal() {
        return this.cal;
    }

    public void setCal(int cal2) {
        this.cal = cal2;
    }

    public int getHr() {
        return this.hr;
    }

    public void setHr(int hr2) {
        this.hr = hr2;
    }

    public int getTotal_step() {
        return this.total_step;
    }

    public void setTotal_step(int total_step2) {
        this.total_step = total_step2;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed2) {
        this.speed = speed2;
    }

    public int getStride() {
        return this.stride;
    }

    public void setStride(int stride2) {
        this.stride = stride2;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int rate2) {
        this.rate = rate2;
    }
}
