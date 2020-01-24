package com.iwown.ble_module.zg_ble.data.model.detail_sport.model;

import java.util.List;

public class ZgDetailWalkData {
    private List<Integer> data;
    private int day;
    private int month;
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

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public List<Integer> getData() {
        return this.data;
    }

    public void setData(List<Integer> data2) {
        this.data = data2;
    }
}
