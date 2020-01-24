package com.iwown.sport_module.view.run;

import com.iwown.lib_common.views.utils.DataTimeUtils;

public class DlineDataBean {
    public int direction;
    public int realX;
    public int realY;
    public boolean showXTime;
    public long time;
    public int type;
    public float value;

    public DlineDataBean() {
    }

    public DlineDataBean(long time2, float value2) {
        this.time = time2;
        this.value = value2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value2) {
        this.value = value2;
    }

    public int getRealX() {
        return this.realX;
    }

    public void setRealX(int realX2) {
        this.realX = realX2;
    }

    public int getRealY() {
        return this.realY;
    }

    public void setRealY(int realY2) {
        this.realY = realY2;
    }

    public boolean isShowXTime() {
        return this.showXTime;
    }

    public void setShowXTime(boolean showXTime2) {
        this.showXTime = showXTime2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction2) {
        this.direction = direction2;
    }

    public String toString() {
        return "DlineDataBean{time=" + DataTimeUtils.getHM(this.time * 1000) + ", value=" + this.value + ", realX=" + this.realX + ", realY=" + this.realY + ", showXTime=" + this.showXTime + '}';
    }
}
