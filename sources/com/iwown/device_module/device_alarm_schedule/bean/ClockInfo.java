package com.iwown.device_module.device_alarm_schedule.bean;

public class ClockInfo {
    private int hour;
    private int id;
    private boolean isOpen = true;
    private int min;
    private byte repeat;
    private String title;

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min2) {
        this.min = min2;
    }

    public byte getRepeat() {
        return this.repeat;
    }

    public void setRepeat(byte repeat2) {
        this.repeat = repeat2;
    }

    public String toString() {
        return "ClockInfo{id=" + this.id + ", title='" + this.title + '\'' + ", hour=" + this.hour + ", min=" + this.min + ", repeat=" + this.repeat + ", isOpen=" + this.isOpen + '}';
    }
}
