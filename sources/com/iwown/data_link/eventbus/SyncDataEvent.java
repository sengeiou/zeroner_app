package com.iwown.data_link.eventbus;

public class SyncDataEvent {
    private boolean isStop;
    private int mDay;
    private int progress;
    private int totalDay;
    private String type;

    public int getTotalDay() {
        return this.totalDay;
    }

    public void setTotalDay(int totalDay2) {
        this.totalDay = totalDay2;
    }

    public int getmDay() {
        return this.mDay;
    }

    public void setmDay(int mDay2) {
        this.mDay = mDay2;
    }

    public boolean isStop() {
        return this.isStop;
    }

    public void setStop(boolean stop) {
        this.isStop = stop;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public SyncDataEvent() {
    }

    public SyncDataEvent(int progress2, boolean isStop2) {
        this.progress = progress2;
        this.isStop = isStop2;
    }

    public SyncDataEvent(int progress2, boolean isStop2, String type2) {
        this.progress = progress2;
        this.isStop = isStop2;
        this.type = type2;
    }

    public SyncDataEvent(int progress2, boolean isStop2, int totalDay2, int mDay2) {
        this.progress = progress2;
        this.isStop = isStop2;
        this.totalDay = totalDay2;
        this.mDay = mDay2;
    }

    public SyncDataEvent(int progress2, boolean isStop2, int totalDay2, int mDay2, String type2) {
        this.progress = progress2;
        this.isStop = isStop2;
        this.totalDay = totalDay2;
        this.mDay = mDay2;
        this.type = type2;
    }
}
