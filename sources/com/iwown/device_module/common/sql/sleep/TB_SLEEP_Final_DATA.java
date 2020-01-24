package com.iwown.device_module.common.sql.sleep;

import org.litepal.crud.DataSupport;

public class TB_SLEEP_Final_DATA extends DataSupport {
    private String action;
    private String data_from;
    private String date;
    private float deepSleepTime;
    private String email;
    private long end_time;
    private int feel_type;
    private int id;
    private float lightSleepTime;
    private int month = 0;
    private int score;
    private String sleep_segment;
    private long start_time;
    private long uid;
    private int upload;
    private int year = 0;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score2) {
        this.score = score2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
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

    public float getDeepSleepTime() {
        return this.deepSleepTime;
    }

    public void setDeepSleepTime(float deepSleepTime2) {
        this.deepSleepTime = deepSleepTime2;
    }

    public float getLightSleepTime() {
        return this.lightSleepTime;
    }

    public void setLightSleepTime(float lightSleepTime2) {
        this.lightSleepTime = lightSleepTime2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public String getSleep_segment() {
        return this.sleep_segment;
    }

    public void setSleep_segment(String sleep_segment2) {
        this.sleep_segment = sleep_segment2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }

    public int getFeel_type() {
        return this.feel_type;
    }

    public void setFeel_type(int feel_type2) {
        this.feel_type = feel_type2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public int getUpload() {
        return this.upload;
    }

    public void setUpload(int upload2) {
        this.upload = upload2;
    }

    public String toString() {
        return "TB_SLEEP_Final_DATA{year=" + this.year + ", month=" + this.month + ", deepSleepTime=" + this.deepSleepTime + ", lightSleepTime=" + this.lightSleepTime + ", date='" + this.date + '\'' + ", email='" + this.email + '\'' + ", uid=" + this.uid + ", sleep_segment='" + this.sleep_segment + '\'' + ", end_time=" + this.end_time + ", start_time=" + this.start_time + ", feel_type=" + this.feel_type + ", data_from='" + this.data_from + '\'' + '}';
    }
}
