package com.iwown.device_module.common.Bluetooth.receiver.iv.bean;

public class SleepSegment {
    private int et;
    private int st;
    private int type;

    public SleepSegment() {
    }

    public SleepSegment(int et2, int st2, int type2) {
        this.et = et2;
        this.st = st2;
        this.type = type2;
    }

    public int getEt() {
        return this.et;
    }

    public void setEt(int et2) {
        this.et = et2;
    }

    public int getSt() {
        return this.st;
    }

    public void setSt(int st2) {
        this.st = st2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String toString() {
        return "SleepDownData2{et=" + this.et + ", st=" + this.st + ", type=" + this.type + '}';
    }
}
