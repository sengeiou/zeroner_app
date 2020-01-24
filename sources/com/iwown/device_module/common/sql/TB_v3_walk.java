package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_v3_walk extends DataSupport {
    private int _uploaded;
    private float calorie;
    private String data_from;
    private String date;
    private float distance;
    private long record_date;
    private int step;
    private long uid;

    public int get_uploaded() {
        return this._uploaded;
    }

    public void set_uploaded(int _uploaded2) {
        this._uploaded = _uploaded2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

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

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }
}
