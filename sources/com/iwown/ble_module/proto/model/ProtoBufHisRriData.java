package com.iwown.ble_module.proto.model;

import java.util.List;

public class ProtoBufHisRriData extends ProtoBufParent {
    private int day;
    private int hour;
    private int minute;
    private int month;
    private List<Integer> raw_data;
    private int second;
    private int seq;
    private int timestamp;
    private int year;

    public List<Integer> getRaw_data() {
        return this.raw_data;
    }

    public void setRaw_data(List<Integer> raw_data2) {
        this.raw_data = raw_data2;
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

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
    }

    public int getHisDataType() {
        return this.hisDataType;
    }

    public void setHisDataType(int hisDataType) {
        this.hisDataType = hisDataType;
    }

    public int getHisDataCase() {
        return this.hisDataCase;
    }

    public void setHisDataCase(int hisDataCase) {
        this.hisDataCase = hisDataCase;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(int timestamp2) {
        this.timestamp = timestamp2;
    }

    public String toString() {
        return "ProtoBufHisRriData{year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", seq=" + this.seq + ", raw_data=" + this.raw_data + ", hisDataType=" + this.hisDataType + ", hisDataCase=" + this.hisDataCase + '}';
    }
}
