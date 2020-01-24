package com.iwown.data_link.walk_29_data;

public class Content {
    private float calorie;
    private String data_from;
    private long record_date;
    private int run_distance;
    private int steps;
    private long uid;
    private double walk_distance;

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

    public double getWalk_distance() {
        return this.walk_distance;
    }

    public void setWalk_distance(double walk_distance2) {
        this.walk_distance = walk_distance2;
    }

    public int getRun_distance() {
        return this.run_distance;
    }

    public void setRun_distance(int run_distance2) {
        this.run_distance = run_distance2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }
}
