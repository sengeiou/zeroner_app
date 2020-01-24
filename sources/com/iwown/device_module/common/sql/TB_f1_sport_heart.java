package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_f1_sport_heart extends DataSupport {
    private String data_from;
    private int day;
    private String detail_data;
    private int end_time;
    private long end_uxtime;
    private int month;
    private int sport_type;
    private int start_time;
    private long start_uxtime;
    private long uid;
    private int year;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
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

    public int getStart_time() {
        return this.start_time;
    }

    public void setStart_time(int start_time2) {
        this.start_time = start_time2;
    }

    public int getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(int end_time2) {
        this.end_time = end_time2;
    }

    public long getStart_uxtime() {
        return this.start_uxtime;
    }

    public void setStart_uxtime(long start_uxtime2) {
        this.start_uxtime = start_uxtime2;
    }

    public long getEnd_uxtime() {
        return this.end_uxtime;
    }

    public void setEnd_uxtime(long end_uxtime2) {
        this.end_uxtime = end_uxtime2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public String getDetail_data() {
        return this.detail_data;
    }

    public void setDetail_data(String detail_data2) {
        this.detail_data = detail_data2;
    }
}
