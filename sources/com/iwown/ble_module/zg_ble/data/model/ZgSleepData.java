package com.iwown.ble_module.zg_ble.data.model;

import java.util.List;

public class ZgSleepData {
    private List<Sleep> data;
    private int day;
    private int deepSleep;
    private long endTime;
    private int lightSleep;
    private int month;
    private long startTime;
    private int totalSleep;
    private int wakeSleep;
    private int year;

    public static class Sleep {
        private int et;
        private int st;
        private int type;

        public int getSt() {
            return this.st;
        }

        public void setSt(int st2) {
            this.st = st2;
        }

        public int getEt() {
            return this.et;
        }

        public void setEt(int et2) {
            this.et = et2;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type2) {
            this.type = type2;
        }
    }

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

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

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

    public int getTotalSleep() {
        return this.totalSleep;
    }

    public void setTotalSleep(int totalSleep2) {
        this.totalSleep = totalSleep2;
    }

    public int getDeepSleep() {
        return this.deepSleep;
    }

    public void setDeepSleep(int deepSleep2) {
        this.deepSleep = deepSleep2;
    }

    public int getLightSleep() {
        return this.lightSleep;
    }

    public void setLightSleep(int lightSleep2) {
        this.lightSleep = lightSleep2;
    }

    public int getWakeSleep() {
        return this.wakeSleep;
    }

    public void setWakeSleep(int wakeSleep2) {
        this.wakeSleep = wakeSleep2;
    }

    public List<Sleep> getData() {
        return this.data;
    }

    public void setData(List<Sleep> data2) {
        this.data = data2;
    }
}
