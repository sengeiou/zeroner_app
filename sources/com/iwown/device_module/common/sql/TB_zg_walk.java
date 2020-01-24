package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_zg_walk extends DataSupport {
    private String data_from;
    private int day;
    private int month;
    private long uid;
    private String walk;
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

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getWalk() {
        return this.walk;
    }

    public void setWalk(String walk2) {
        this.walk = walk2;
    }
}
