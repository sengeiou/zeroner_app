package com.iwown.device_module.common.sql;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class TB_v3_sport_total_data extends DataSupport {
    private int _uploaded;
    private String data_from;
    private int day;
    private int day_goal_calo;
    private String detail_data;
    private int month;
    private String reserved;
    @Column(ignore = false, unique = true)
    private long time_stamp;
    private float total_calorie;
    private int total_steps;
    private long uid;
    private int week;
    private int year;

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getTotal_steps() {
        return this.total_steps;
    }

    public void setTotal_steps(int total_steps2) {
        this.total_steps = total_steps2;
    }

    public float getTotal_calorie() {
        return this.total_calorie;
    }

    public void setTotal_calorie(float total_calorie2) {
        this.total_calorie = total_calorie2;
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

    public String getDetail_data() {
        return this.detail_data;
    }

    public void setDetail_data(String detail_data2) {
        this.detail_data = detail_data2;
    }

    public int getDay_goal_calo() {
        return this.day_goal_calo;
    }

    public void setDay_goal_calo(int day_goal_calo2) {
        this.day_goal_calo = day_goal_calo2;
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

    public long getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(long time_stamp2) {
        this.time_stamp = time_stamp2;
    }

    public String toString() {
        return "TB_v3_sport_total_data{uid=" + this.uid + ", total_steps=" + this.total_steps + ", total_calorie=" + this.total_calorie + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", week=" + this.week + ", detail_data='" + this.detail_data + '\'' + ", day_goal_calo=" + this.day_goal_calo + ", _uploaded=" + this._uploaded + ", reserved='" + this.reserved + '\'' + ", time_stamp=" + this.time_stamp + '}';
    }
}
