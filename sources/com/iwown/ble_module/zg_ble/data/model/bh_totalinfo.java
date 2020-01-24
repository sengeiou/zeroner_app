package com.iwown.ble_module.zg_ble.data.model;

import android.util.Log;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;

public class bh_totalinfo {
    private int calorie;
    private int day;
    private int distance;
    private int exerciseMinutes;
    private int heartType;
    private int latestHeart;
    private int month;
    private int sleepMinutes;
    private int sleepType;
    private int sportType;
    private int step;
    private int year;

    public static bh_totalinfo GetInstanceFromBytes(byte[] data) {
        Log.i("bh_totalinfo", String.valueOf(data.length));
        if (data.length < 16) {
            return null;
        }
        bh_totalinfo instance = new bh_totalinfo();
        instance.year = ByteUtil.bytesToInt(new byte[]{data[0], data[1]});
        instance.month = data[2];
        instance.day = data[3];
        instance.step = ByteUtil.bytesToInt(new byte[]{data[4], data[5], data[14]});
        instance.calorie = ByteUtil.bytesToInt(new byte[]{data[6], data[7]});
        instance.distance = ByteUtil.bytesToInt(new byte[]{data[8], data[9]}) * 10;
        instance.exerciseMinutes = ByteUtil.bytesToInt(new byte[]{data[10], data[11]});
        instance.sleepMinutes = ByteUtil.bytesToInt(new byte[]{data[12], data[13]});
        instance.latestHeart = data[15];
        return instance;
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

    public int getCalorie() {
        return this.calorie;
    }

    public void setCalorie(int calorie2) {
        this.calorie = calorie2;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance2) {
        this.distance = distance2;
    }

    public int getExerciseMinutes() {
        return this.exerciseMinutes;
    }

    public void setExerciseMinutes(int exerciseMinutes2) {
        this.exerciseMinutes = exerciseMinutes2;
    }

    public int getSleepMinutes() {
        return this.sleepMinutes;
    }

    public void setSleepMinutes(int sleepMinutes2) {
        this.sleepMinutes = sleepMinutes2;
    }

    public int getLatestHeart() {
        return this.latestHeart;
    }

    public void setLatestHeart(int latestHeart2) {
        this.latestHeart = latestHeart2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public int getSleepType() {
        return this.sleepType;
    }

    public void setSleepType(int sleepType2) {
        this.sleepType = sleepType2;
    }

    public int getSportType() {
        return this.sportType;
    }

    public void setSportType(int sportType2) {
        this.sportType = sportType2;
    }

    public int getHeartType() {
        return this.heartType;
    }

    public void setHeartType(int heartType2) {
        this.heartType = heartType2;
    }
}
