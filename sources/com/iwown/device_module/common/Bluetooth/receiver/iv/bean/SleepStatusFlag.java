package com.iwown.device_module.common.Bluetooth.receiver.iv.bean;

public class SleepStatusFlag {
    public static final int Deep = 1;
    public static final int Light = 2;
    public static final int Placement = 0;
    private int deepFlag;
    private int startTime;
    private int time;

    public SleepStatusFlag() {
    }

    public SleepStatusFlag(int time2, int deepFlag2, int startTime2) {
        this.time = time2;
        this.deepFlag = deepFlag2;
        this.startTime = startTime2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public int isDeepFlag() {
        return this.deepFlag;
    }

    public void setDeepFlag(int deepFlag2) {
        this.deepFlag = deepFlag2;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime2) {
        this.startTime = startTime2;
    }
}
