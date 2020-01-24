package com.iwown.device_module.common.sql.sleep;

import org.litepal.crud.DataSupport;

public class TB_Sleep_Status extends DataSupport {
    private String data_from;
    private String date;
    private int feel_type;
    private int score;
    private int time;
    private long uid;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score2) {
        this.score = score2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getFeel_type() {
        return this.feel_type;
    }

    public void setFeel_type(int feel_type2) {
        this.feel_type = feel_type2;
    }

    public String toString() {
        return "TB_Sleep_Status{uid=" + this.uid + ", time=" + this.time + ", data_from='" + this.data_from + '\'' + ", feel_type=" + this.feel_type + ", score=" + this.score + '}';
    }
}
