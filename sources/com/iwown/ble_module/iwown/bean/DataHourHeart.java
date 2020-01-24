package com.iwown.ble_module.iwown.bean;

public class DataHourHeart {
    private int day;
    private int hour;
    private boolean last;
    private int month;
    private int nowAdd53;
    private int[] rates;
    private int year;

    public boolean isLast() {
        return this.last;
    }

    public void setLast(boolean last2) {
        this.last = last2;
    }

    public int getNowAdd53() {
        return this.nowAdd53;
    }

    public void setNowAdd53(int nowAdd532) {
        this.nowAdd53 = nowAdd532;
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

    public int[] getRates() {
        return this.rates;
    }

    public void setRates(int[] rates2) {
        this.rates = rates2;
    }
}
