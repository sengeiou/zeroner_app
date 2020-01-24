package com.iwown.data_link.sleep_data;

import java.io.Serializable;
import java.util.List;

public class SleepDownData1 implements Serializable {
    private String action;
    private String data_from;
    private float deep_time;
    private long end_time;
    private int feel_type;
    private float light_time;
    private int score;
    private List<SleepDownData2> sleep_segment;
    private long start_time;
    private long uid;

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

    public float getDeep_time() {
        return this.deep_time;
    }

    public void setDeep_time(float deep_time2) {
        this.deep_time = deep_time2;
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

    public float getLight_time() {
        return this.light_time;
    }

    public void setLight_time(float light_time2) {
        this.light_time = light_time2;
    }

    public List<SleepDownData2> getSleep_segment() {
        return this.sleep_segment;
    }

    public void setSleep_segment(List<SleepDownData2> sleep_segment2) {
        this.sleep_segment = sleep_segment2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String toString() {
        return "SleepDownData1{uid=" + this.uid + ", data_from='" + this.data_from + '\'' + ", deep_time=" + this.deep_time + ", end_time=" + this.end_time + ", start_time=" + this.start_time + ", feel_type=" + this.feel_type + ", light_time=" + this.light_time + ", sleep_segment=" + this.sleep_segment + '}';
    }
}
