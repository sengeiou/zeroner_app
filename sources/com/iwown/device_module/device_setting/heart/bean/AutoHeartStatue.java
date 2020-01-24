package com.iwown.device_module.device_setting.heart.bean;

public class AutoHeartStatue {
    private String device_name;
    private int heart_endTime;
    private int heart_startTime;
    private boolean heart_switch = true;

    public String getDevice_name() {
        return this.device_name;
    }

    public void setDevice_name(String device_name2) {
        this.device_name = device_name2;
    }

    public boolean isHeart_switch() {
        return this.heart_switch;
    }

    public void setHeart_switch(boolean heart_switch2) {
        this.heart_switch = heart_switch2;
    }

    public int getHeart_startTime() {
        return this.heart_startTime;
    }

    public void setHeart_startTime(int heart_startTime2) {
        this.heart_startTime = heart_startTime2;
    }

    public int getHeart_endTime() {
        return this.heart_endTime;
    }

    public void setHeart_endTime(int heart_endTime2) {
        this.heart_endTime = heart_endTime2;
    }
}
