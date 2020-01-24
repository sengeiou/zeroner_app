package com.iwown.ble_module.proto.model;

public class ProtoBufRealTimeData {
    private int battery_status;
    private float calorie;
    private boolean charging;
    private float distance;
    private int health_status;
    private boolean isBattery;
    private boolean isHearth;
    private boolean isKey;
    private boolean isTime;
    private int keyMode;
    private int level;
    private int mode_status;
    private int seconds;
    private int steps;
    private int time_status;

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int battery) {
        this.level = battery;
    }

    public boolean isCharging() {
        return this.charging;
    }

    public void setCharging(boolean charging2) {
        this.charging = charging2;
    }

    public boolean isBattery() {
        return this.isBattery;
    }

    public void setBattery(boolean battery) {
        this.isBattery = battery;
    }

    public boolean isHearth() {
        return this.isHearth;
    }

    public void setHearth(boolean hearth) {
        this.isHearth = hearth;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds2) {
        this.seconds = seconds2;
    }

    public boolean isTime() {
        return this.isTime;
    }

    public void setTime(boolean time) {
        this.isTime = time;
    }

    public boolean isKey() {
        return this.isKey;
    }

    public void setKey(boolean key) {
        this.isKey = key;
    }

    public int getKeyMode() {
        return this.keyMode;
    }

    public void setKeyMode(int keyMode2) {
        this.keyMode = keyMode2;
    }

    public int getBattery_status() {
        return this.battery_status;
    }

    public void setBattery_status(int battery_status2) {
        this.battery_status = battery_status2;
    }

    public int getHealth_status() {
        return this.health_status;
    }

    public void setHealth_status(int health_status2) {
        this.health_status = health_status2;
    }

    public int getTime_status() {
        return this.time_status;
    }

    public void setTime_status(int time_status2) {
        this.time_status = time_status2;
    }

    public int getMode_status() {
        return this.mode_status;
    }

    public void setMode_status(int mode_status2) {
        this.mode_status = mode_status2;
    }
}
