package com.iwown.my_module.healthy.network.response;

public class AllHealthData {
    private String birthday;
    private float bmi;
    private int gender;
    private float height;
    private float hipline;
    private long uid;
    private float waistline;
    private float whr;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday2) {
        this.birthday = birthday2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public float getBmi() {
        return this.bmi;
    }

    public void setBmi(float bmi2) {
        this.bmi = bmi2;
    }

    public float getWaistline() {
        return this.waistline;
    }

    public void setWaistline(float waistline2) {
        this.waistline = waistline2;
    }

    public float getHipline() {
        return this.hipline;
    }

    public void setHipline(float hipline2) {
        this.hipline = hipline2;
    }

    public float getWhr() {
        return this.whr;
    }

    public void setWhr(float whr2) {
        this.whr = whr2;
    }
}
