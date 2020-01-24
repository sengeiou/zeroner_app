package com.iwown.ble_module.proto.model;

import java.util.List;

public class ProtoBufHisEPGData extends ProtoBufParent {
    private int day;
    private List<Integer> ecg_data;
    private int hour;
    private int minute;
    private int month;
    private List<Integer> ppg_data;
    private int second;
    private int seq;
    private int year;

    public List<Integer> getEcg_data() {
        return this.ecg_data;
    }

    public void setEcg_data(List<Integer> ecg_data2) {
        this.ecg_data = ecg_data2;
    }

    public List<Integer> getPpg_data() {
        return this.ppg_data;
    }

    public void setPpg_data(List<Integer> ppg_data2) {
        this.ppg_data = ppg_data2;
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
}
