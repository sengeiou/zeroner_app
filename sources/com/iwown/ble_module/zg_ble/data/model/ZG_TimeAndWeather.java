package com.iwown.ble_module.zg_ble.data.model;

import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.Arrays;

public class ZG_TimeAndWeather extends Result {
    int day = -1;
    int hour = -1;
    int minute = -1;
    int month = -1;
    int second = -1;
    int temperatrue = -1;
    int weather = -1;
    int week = -1;
    int year = -1;

    public int getWeather() {
        return this.weather;
    }

    public void setWeather(int weather2) {
        this.weather = weather2;
    }

    public int getTemperatrue() {
        return this.temperatrue;
    }

    public void setTemperatrue(int temperatrue2) {
        this.temperatrue = temperatrue2;
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

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week2) {
        this.week = week2;
    }

    public void parseData(byte[] datas) {
        this.year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        this.month = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
        this.day = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
        this.hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
        this.minute = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
        this.second = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
        this.week = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
        this.weather = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
        this.temperatrue = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
    }
}
