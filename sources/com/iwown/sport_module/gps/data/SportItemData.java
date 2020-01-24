package com.iwown.sport_module.gps.data;

public class SportItemData {
    private float calorie = 0.0f;
    private int count = 0;
    private float distance = 0.0f;
    private int time = 0;

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public void clear() {
        this.distance = 0.0f;
        this.calorie = 0.0f;
        this.count = 0;
        this.time = 0;
    }
}
