package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_http_manage extends DataSupport {
    private String data_from;
    private String date;
    private long date_time;
    private int day;
    private int month;
    private String type;
    private long uid;
    private long up_time;
    private int upload;
    private int year;

    public long getDate_time() {
        return this.date_time;
    }

    public void setDate_time(long date_time2) {
        this.date_time = date_time2;
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

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int getUpload() {
        return this.upload;
    }

    public void setUpload(int upload2) {
        this.upload = upload2;
    }

    public long getUp_time() {
        return this.up_time;
    }

    public void setUp_time(long up_time2) {
        this.up_time = up_time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }
}
