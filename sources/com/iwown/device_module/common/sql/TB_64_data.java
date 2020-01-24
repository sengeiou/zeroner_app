package com.iwown.device_module.common.sql;

import com.google.gson.Gson;
import org.litepal.crud.DataSupport;

public class TB_64_data extends DataSupport {
    private String cmd;
    private String data_from;
    private int day;
    private String ecg;
    private int hour;
    private int min;
    private int month;
    private int second;
    private int seq;
    private long time;
    private long uid;
    private int year;

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd2) {
        this.cmd = cmd2;
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

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
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

    public String getEcg() {
        return this.ecg;
    }

    public void setEcg(String ecg2) {
        this.ecg = ecg2;
    }

    public String toJson() {
        return new Gson().toJson((Object) this);
    }
}
