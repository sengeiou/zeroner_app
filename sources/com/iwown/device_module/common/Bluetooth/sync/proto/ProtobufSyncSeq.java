package com.iwown.device_module.common.Bluetooth.sync.proto;

public class ProtobufSyncSeq {
    private int currentDay;
    private int endSeq;
    private int startSeq;
    private int totalSeq;
    private int type;

    public int getTotalSeq() {
        return this.totalSeq;
    }

    public void setTotalSeq(int totalSeq2) {
        this.totalSeq = totalSeq2;
    }

    public int getStartSeq() {
        return this.startSeq;
    }

    public void setStartSeq(int startSeq2) {
        this.startSeq = startSeq2;
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public void setCurrentDay(int currentDay2) {
        this.currentDay = currentDay2;
    }

    public int getEndSeq() {
        return this.endSeq;
    }

    public void setEndSeq(int endSeq2) {
        this.endSeq = endSeq2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public ProtobufSyncSeq(int totalSeq2, int startSeq2, int currentDay2, int endSeq2, int type2) {
        this.totalSeq = totalSeq2;
        this.startSeq = startSeq2;
        this.currentDay = currentDay2;
        this.endSeq = endSeq2;
        this.type = type2;
    }
}
