package com.iwown.data_link.sport_data;

import com.iwown.lib_common.json.JsonTool;

public class P1_62_data {
    private String cmd;
    private int ctrl;
    private String data_from;
    private int day;
    private int freq;
    private String gnssData;
    private int hour;
    private int min;
    private int month;
    private int num;
    private int seq;
    private long time;
    private long uid;
    private int year;

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd2) {
        this.cmd = cmd2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getCtrl() {
        return this.ctrl;
    }

    public void setCtrl(int ctrl2) {
        this.ctrl = ctrl2;
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

    public int getFreq() {
        return this.freq;
    }

    public void setFreq(int freq2) {
        this.freq = freq2;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num2) {
        this.num = num2;
    }

    public String getGnssData() {
        return this.gnssData;
    }

    public void setGnssData(String gnssData2) {
        this.gnssData = gnssData2;
    }

    public String toString() {
        return JsonTool.toJson(this);
    }
}
