package com.iwown.device_module.common.sql.heart;

import org.litepal.crud.DataSupport;

public class TB_swimrate_data extends DataSupport {
    private int _uploaded;
    private int age;
    private String data_from;
    private int day;
    private String detail_data;
    private long end_time;
    private int month;
    private String reserved;
    private int sport_type;
    private long start_time;
    private long time_stamp;
    private long uid;
    private int week;
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

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age2) {
        this.age = age2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public String toString() {
        return "TB_heartrate_data{uid=" + this.uid + ", data_from='" + this.data_from + '\'' + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", week=" + this.week + ", detail_data='" + this.detail_data + '\'' + ", _uploaded=" + this._uploaded + ", reserved='" + this.reserved + '\'' + ", start_time=" + this.start_time + ", end_time=" + this.end_time + ", time_stamp=" + this.time_stamp + '}';
    }
}
