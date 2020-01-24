package com.iwown.sport_module.sql;

import org.litepal.crud.DataSupport;

public class TB_has28Days_monthly extends DataSupport {
    private String info;
    private int month;
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

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info2) {
        this.info = info2;
    }
}
