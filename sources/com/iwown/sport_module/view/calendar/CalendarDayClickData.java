package com.iwown.sport_module.view.calendar;

public class CalendarDayClickData {
    private int clickX;
    private int clickY;
    private int day;
    private int month;
    private int week;
    private int year;

    public CalendarDayClickData() {
    }

    public CalendarDayClickData(int x, int y, int year2, int month2, int day2, int week2) {
        this.clickX = x;
        this.clickY = y;
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.week = week2;
    }

    public void set(int x, int y, int year2, int month2, int day2, int week2) {
        this.clickX = x;
        this.clickY = y;
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.week = week2;
    }

    public void set(CalendarDayClickData data) {
        this.clickX = data.getClickX();
        this.clickY = data.getClickY();
        this.year = data.getYear();
        this.month = data.getMonth();
        this.day = data.getDay();
        this.week = data.getWeek();
    }

    public int getClickX() {
        return this.clickX;
    }

    public int getClickY() {
        return this.clickY;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getWeek() {
        return this.week;
    }
}
