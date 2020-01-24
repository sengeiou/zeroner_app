package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

public class MyDay {
    private String date;
    private int day;
    private int month;
    private int year;

    public MyDay(int year2, int month2, int day2, String date2) {
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.date = date2;
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }
}
