package com.iwown.device_module.common.sql;

import android.support.annotation.NonNull;
import org.litepal.crud.DataSupport;

public class TB_rri_data extends DataSupport implements Comparable<TB_rri_data> {
    private String data_from;
    private String date;
    private int day;
    private int hour;
    private int isUpload;
    private int minute;
    private int month;
    private String rawData;
    private int second;
    private int seq;
    private int timeStamp;
    private long uid;
    private int year;

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

    public String getRawData() {
        return this.rawData;
    }

    public void setRawData(String rawData2) {
        this.rawData = rawData2;
    }

    public int getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(int timeStamp2) {
        this.timeStamp = timeStamp2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int compareTo(@NonNull TB_rri_data tb_rri_data) {
        if (this.seq > tb_rri_data.getSeq()) {
            return 1;
        }
        return -1;
    }

    public int getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(int isUpload2) {
        this.isUpload = isUpload2;
    }

    public String toString() {
        return "TB_rri_data{uid=" + this.uid + ", data_from='" + this.data_from + '\'' + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", timeStamp=" + this.timeStamp + ", seq=" + this.seq + ", rawData='" + this.rawData + '\'' + ", date='" + this.date + '\'' + '}';
    }
}
