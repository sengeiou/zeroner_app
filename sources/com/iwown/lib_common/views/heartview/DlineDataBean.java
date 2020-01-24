package com.iwown.lib_common.views.heartview;

public class DlineDataBean {
    public int direction;
    public boolean isBegin;
    public int realX;
    public int realY;
    public boolean showXTime;
    public long time;
    public int type;
    public int value;

    public DlineDataBean(long time2, int value2) {
        this.time = time2;
        this.value = value2;
    }

    public String toString() {
        return this.value + "";
    }
}
