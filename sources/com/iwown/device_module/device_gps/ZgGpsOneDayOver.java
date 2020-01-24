package com.iwown.device_module.device_gps;

public class ZgGpsOneDayOver extends ZgGpsParent {
    private int day;
    private int month;
    private boolean over;
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

    public boolean isOver() {
        return this.over;
    }

    public void setOver(boolean over2) {
        this.over = over2;
    }
}
