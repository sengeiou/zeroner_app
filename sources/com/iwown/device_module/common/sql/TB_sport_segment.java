package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_sport_segment extends DataSupport {
    private double calorie;
    private String data_from;
    private int day;
    private float distance;
    private int duration;
    private long end_time;
    private int month;
    private int sport_type;
    private long start_time;
    private int step;
    private long uid;
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

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(double calorie2) {
        this.calorie = calorie2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration2) {
        this.duration = duration2;
    }
}
