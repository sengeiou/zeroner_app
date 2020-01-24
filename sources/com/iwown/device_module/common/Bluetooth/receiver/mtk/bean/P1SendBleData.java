package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import com.iwown.lib_common.date.DateUtil;

public class P1SendBleData {
    public int dataType;
    public String date = "";
    public int day;
    public int endIndex;
    public int month;
    public int startIndex;
    public int year;

    public P1SendBleData(int year2, int month2, int day2, int startIndex2, int endIndex2, int dataType2) {
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.startIndex = startIndex2;
        this.endIndex = endIndex2;
        this.dataType = dataType2;
        this.date = new DateUtil(year2, month2, day2).getSyyyyMMddDate();
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
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

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(int startIndex2) {
        this.startIndex = startIndex2;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void setEndIndex(int endIndex2) {
        this.endIndex = endIndex2;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType2) {
        this.dataType = dataType2;
    }
}
