package com.iwown.sport_module.sql;

import org.litepal.crud.DataSupport;

public class TB_29_history_data_monthly extends DataSupport {
    private String content;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content2) {
        this.content = content2;
    }
}
