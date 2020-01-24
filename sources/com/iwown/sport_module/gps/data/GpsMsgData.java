package com.iwown.sport_module.gps.data;

public class GpsMsgData {
    private float calorie;
    private float distance;
    private String pace;
    private int time;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public String getPace() {
        return this.pace;
    }

    public void setPace(String pace2) {
        this.pace = pace2;
    }

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
}
