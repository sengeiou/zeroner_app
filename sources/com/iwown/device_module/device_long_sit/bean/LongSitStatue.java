package com.iwown.device_module.device_long_sit.bean;

public class LongSitStatue {
    private boolean doNotDisturb;
    private int endHour = 18;
    private boolean longSeat;
    private byte repeat = -1;
    private int startHour = 8;

    public boolean isLongSeat() {
        return this.longSeat;
    }

    public void setLongSeat(boolean longSeat2) {
        this.longSeat = longSeat2;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour2) {
        this.startHour = startHour2;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public void setEndHour(int endHour2) {
        this.endHour = endHour2;
    }

    public byte getRepeat() {
        return this.repeat;
    }

    public void setRepeat(byte repeat2) {
        this.repeat = repeat2;
    }

    public boolean isDoNotDisturb() {
        return this.doNotDisturb;
    }

    public void setDoNotDisturb(boolean doNotDisturb2) {
        this.doNotDisturb = doNotDisturb2;
    }
}
