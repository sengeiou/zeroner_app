package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_sum_61_62_64 extends DataSupport {
    private String date;
    private long date_time;
    private int day;
    private int month;
    private String send_cmd;
    private int sum;
    private int type;
    private String type_str;
    private int year;

    public String getSend_cmd() {
        return this.send_cmd;
    }

    public void setSend_cmd(String send_cmd2) {
        this.send_cmd = send_cmd2;
    }

    public int getSum() {
        return this.sum;
    }

    public void setSum(int sum2) {
        this.sum = sum2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getType_str() {
        return this.type_str;
    }

    public void setType_str(String type_str2) {
        this.type_str = type_str2;
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

    public long getDate_time() {
        return this.date_time;
    }

    public void setDate_time(long date_time2) {
        this.date_time = date_time2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }
}
