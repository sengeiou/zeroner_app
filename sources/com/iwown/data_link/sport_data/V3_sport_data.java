package com.iwown.data_link.sport_data;

import com.iwown.lib_common.json.JsonTool;
import java.io.Serializable;

public class V3_sport_data implements Serializable {
    private int _uploaded;
    private double calorie;
    private int complete_progress;
    private String data_from;
    private int day;
    private String detail_data;
    private int end_time;
    private long end_uxtime;
    public int index;
    private int month;
    private int reserved;
    private String sportCode;
    private int sport_type;
    private int start_time;
    private long start_uxtime;
    private long uid;
    private int week;
    private int year;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(double calorie2) {
        this.calorie = calorie2;
    }

    public int getComplete_progress() {
        return this.complete_progress;
    }

    public void setComplete_progress(int complete_progress2) {
        this.complete_progress = complete_progress2;
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

    public int getReserved() {
        return this.reserved;
    }

    public void setReserved(int reserved2) {
        this.reserved = reserved2;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public long getEnd_uxtime() {
        return this.end_uxtime;
    }

    public void setEnd_uxtime(long end_uxtime2) {
        this.end_uxtime = end_uxtime2;
    }

    public String getSportCode() {
        return this.sportCode;
    }

    public void setSportCode(String sportCode2) {
        this.sportCode = sportCode2;
    }

    public long getStart_uxtime() {
        return this.start_uxtime;
    }

    public void setStart_uxtime(long start_uxtime2) {
        this.start_uxtime = start_uxtime2;
    }

    public String toString() {
        return JsonTool.toJson(this);
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj == null || !(obj instanceof V3_sport_data)) {
            return false;
        }
        if (this.uid == ((V3_sport_data) obj).getUid() && ((V3_sport_data) obj).getData_from() != null && ((V3_sport_data) obj).getData_from().equals(this.data_from) && getStart_uxtime() == ((V3_sport_data) obj).getStart_uxtime() && this.sport_type == ((V3_sport_data) obj).getSport_type()) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}
