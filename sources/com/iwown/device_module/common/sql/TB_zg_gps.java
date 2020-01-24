package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_zg_gps extends DataSupport {
    private String data_from;
    private int day;
    private double lat;
    private double lon;
    private int month;
    private long time;
    private long uid;
    private int year;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat2) {
        this.lat = lat2;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon2) {
        this.lon = lon2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
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
}
