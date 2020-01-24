package com.iwown.sport_module.gps.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SportTotalItem implements MultiItemEntity {
    private int activityCount;
    private long allTime;
    private float calorie;
    private int data_type;
    private int month;
    private float totalDistanceKm;
    private float totalDistanceMile;
    private int year;

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public float getTotalDistanceKm() {
        return this.totalDistanceKm;
    }

    public void setTotalDistanceKm(float totalDistanceKm2) {
        this.totalDistanceKm = totalDistanceKm2;
    }

    public float getTotalDistanceMile() {
        return this.totalDistanceMile;
    }

    public void setTotalDistanceMile(float totalDistanceMile2) {
        this.totalDistanceMile = totalDistanceMile2;
    }

    public int getActivityCount() {
        return this.activityCount;
    }

    public void setActivityCount(int activityCount2) {
        this.activityCount = activityCount2;
    }

    public int getItemType() {
        return 0;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public long getAllTime() {
        return this.allTime;
    }

    public void setAllTime(long allTime2) {
        this.allTime = allTime2;
    }
}
