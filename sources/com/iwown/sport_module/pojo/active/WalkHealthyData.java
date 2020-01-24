package com.iwown.sport_module.pojo.active;

import com.iwown.lib_common.date.DateUtil;

public class WalkHealthyData {
    private float calorie;
    private String data_from;
    private String date;
    private long record_date;
    private float run_distance;
    private int steps;
    private long uid;
    private float walk_distance;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getRecord_date() {
        return this.record_date;
    }

    public void setRecord_date(long record_date2) {
        this.record_date = record_date2;
        this.date = new DateUtil(record_date2, true).getSyyyyMMddDate();
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public float getWalk_distance() {
        return this.walk_distance;
    }

    public void setWalk_distance(float walk_distance2) {
        this.walk_distance = walk_distance2;
    }

    public float getRun_distance() {
        return this.run_distance;
    }

    public void setRun_distance(float run_distance2) {
        this.run_distance = run_distance2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }
}
