package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class TodayTraningItem extends BaseTraningItem implements MultiItemEntity {
    private int activitys;
    private float cal;
    private int dev_type;
    private float distance;
    private long endTime;
    private long startTime;
    private int start_hour;
    private int start_min;
    private int start_sec;

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime2) {
        this.startTime = startTime2;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime2) {
        this.endTime = endTime2;
    }

    public int getDev_type() {
        return this.dev_type;
    }

    public void setDev_type(int dev_type2) {
        this.dev_type = dev_type2;
    }

    public int getStart_hour() {
        return this.start_hour;
    }

    public void setStart_hour(int start_hour2) {
        this.start_hour = start_hour2;
    }

    public int getStart_min() {
        return this.start_min;
    }

    public void setStart_min(int start_min2) {
        this.start_min = start_min2;
    }

    public int getStart_sec() {
        return this.start_sec;
    }

    public void setStart_sec(int start_sec2) {
        this.start_sec = start_sec2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public int getActivitys() {
        return this.activitys;
    }

    public void setActivitys(int activitys2) {
        this.activitys = activitys2;
    }

    public float getCal() {
        return this.cal;
    }

    public void setCal(float cal2) {
        this.cal = cal2;
    }

    public int getItemType() {
        return BaseTraningItem.DATA_TYPE_TODAY;
    }
}
