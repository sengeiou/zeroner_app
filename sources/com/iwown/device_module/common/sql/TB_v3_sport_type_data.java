package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_v3_sport_type_data extends DataSupport {
    private int _uploaded;
    private float calorie;
    private int count;
    private int day;
    private float distance;
    private int month;
    private String reserved;
    private int sport_type;
    private int steps;
    private long time;
    private long uid;
    private int week;
    private int year;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
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

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week2) {
        this.week = week2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
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

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
    }

    public int get_uploaded() {
        return this._uploaded;
    }

    public void set_uploaded(int _uploaded2) {
        this._uploaded = _uploaded2;
    }

    public String getReserved() {
        return this.reserved;
    }

    public void setReserved(String reserved2) {
        this.reserved = reserved2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String toString() {
        return "TB_v3_sport_type_data{uid=" + this.uid + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", week=" + this.week + ", sport_type=" + this.sport_type + ", calorie=" + this.calorie + ", distance=" + this.distance + ", count=" + this.count + ", steps=" + this.steps + ", _uploaded=" + this._uploaded + ", reserved='" + this.reserved + '\'' + ", time=" + this.time + '}';
    }
}
