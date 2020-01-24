package com.iwown.my_module.healthy.network.request;

public class WeightSend {
    private int age;
    private String data_from;
    private int gender;
    private int height;
    private String record_date;
    private long uid;
    private float weight;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getRecord_date() {
        return this.record_date;
    }

    public void setRecord_date(String record_date2) {
        this.record_date = record_date2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age2) {
        this.age = age2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }
}
