package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class ProtoBuf_index_80 extends DataSupport {
    private String data_from;
    private int day;
    private int end_idx;
    private int hour;
    private int indexType;
    private int isFinish;
    private int min;
    private int month;
    private int second;
    private int start_idx;
    private int time;
    private long uid;
    private int year;

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

    public int getStart_idx() {
        return this.start_idx;
    }

    public void setStart_idx(int start_idx2) {
        this.start_idx = start_idx2;
    }

    public int getEnd_idx() {
        return this.end_idx;
    }

    public void setEnd_idx(int end_idx2) {
        this.end_idx = end_idx2;
    }

    public int getIndexType() {
        return this.indexType;
    }

    public void setIndexType(int indexType2) {
        this.indexType = indexType2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min2) {
        this.min = min2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public int getIsFinish() {
        return this.isFinish;
    }

    public void setIsFinish(int isFinish2) {
        this.isFinish = isFinish2;
    }

    public String toString() {
        return "ProtoBuf_index_80{uid=" + this.uid + ", data_from='" + this.data_from + '\'' + ", start_idx=" + this.start_idx + ", end_idx=" + this.end_idx + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", min=" + this.min + ", second=" + this.second + ", time=" + this.time + ", indexType=" + this.indexType + '}';
    }
}
