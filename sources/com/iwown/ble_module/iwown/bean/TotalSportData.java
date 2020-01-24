package com.iwown.ble_module.iwown.bean;

public class TotalSportData {
    private float calories;
    private int day;
    private float distance;
    private boolean last;
    private int month;
    private int sport_type;
    private int steps;
    private int year;

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public boolean isLast() {
        return this.last;
    }

    public void setLast(boolean last2) {
        this.last = last2;
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

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
    }

    public float getCalories() {
        return this.calories;
    }

    public void setCalories(float calories2) {
        this.calories = calories2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }
}
