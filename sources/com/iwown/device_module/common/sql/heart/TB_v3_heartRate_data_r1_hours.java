package com.iwown.device_module.common.sql.heart;

import com.iwown.lib_common.date.DateUtil;
import org.litepal.crud.DataSupport;

public class TB_v3_heartRate_data_r1_hours extends DataSupport {
    private int _uploaded;
    private String data_from;
    private int day;
    private String detail_data;
    private int hours;
    private int month;
    private long record_date;
    private String reserved;
    private long time_stamp;
    private long uid;
    private int week;
    private int year;

    public int getHours() {
        return this.hours;
    }

    public TB_v3_heartRate_data_r1_hours setHours(int hours2) {
        this.hours = hours2;
        return this;
    }

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

    public long getRecord_date() {
        return this.record_date;
    }

    public void setRecord_date(long record_date2) {
        this.record_date = record_date2;
    }

    public String toString() {
        return "TB_heartrate_data{uid=" + this.uid + ", data_from='" + this.data_from + '\'' + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", week=" + this.week + ", hours=" + this.hours + ", detail_data='" + this.detail_data + '\'' + ", _uploaded=" + this._uploaded + ", reserved='" + this.reserved + '\'' + ", record_date=" + new DateUtil(this.record_date, false).getY_M_D() + '}';
    }
}
