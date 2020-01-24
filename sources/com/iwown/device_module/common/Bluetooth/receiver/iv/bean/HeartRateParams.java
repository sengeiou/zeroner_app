package com.iwown.device_module.common.Bluetooth.receiver.iv.bean;

public class HeartRateParams extends Result {
    private static final long serialVersionUID = 1;
    private int heartrateExist;
    private int statue;
    private int strong;
    private int time;
    private int version;

    public int getHeartrateExist() {
        return this.heartrateExist;
    }

    public void setHeartrateExist(int heartrateExist2) {
        this.heartrateExist = heartrateExist2;
    }

    public int getStrong() {
        return this.strong;
    }

    public void setStrong(int strong2) {
        this.strong = strong2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public int getStatue() {
        return this.statue;
    }

    public void setStatue(int statue2) {
        this.statue = statue2;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version2) {
        this.version = version2;
    }
}
