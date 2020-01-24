package com.iwown.data_link.sport_data;

import java.util.List;

public class R1DataBean {
    private List<Integer> day;
    private List<Integer> month;
    private String tag;
    private List<Integer> year;

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag2) {
        this.tag = tag2;
    }

    public List<Integer> getYear() {
        return this.year;
    }

    public void setYear(List<Integer> year2) {
        this.year = year2;
    }

    public List<Integer> getMonth() {
        return this.month;
    }

    public void setMonth(List<Integer> month2) {
        this.month = month2;
    }

    public List<Integer> getDay() {
        return this.day;
    }

    public void setDay(List<Integer> day2) {
        this.day = day2;
    }
}
