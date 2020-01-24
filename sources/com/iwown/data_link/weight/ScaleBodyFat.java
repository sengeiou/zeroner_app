package com.iwown.data_link.weight;

import com.iwown.lib_common.date.DateUtil;

public class ScaleBodyFat {
    private float bmi;
    private int body_age;
    private float bodyfat;
    private float bone_weight;
    private float calorie;
    private String data_from;
    private float impedance;
    private float muscule;
    private long record_date;
    private long uid;
    private int visceral_fat;
    private float water;
    private float weight;

    public float getImpedance() {
        return this.impedance;
    }

    public void setImpedance(float impedance2) {
        this.impedance = impedance2;
    }

    public int getVisceral_fat() {
        return this.visceral_fat;
    }

    public void setVisceral_fat(int visceral_fat2) {
        this.visceral_fat = visceral_fat2;
    }

    public float getBone_weight() {
        return this.bone_weight;
    }

    public void setBone_weight(float bone_weight2) {
        this.bone_weight = bone_weight2;
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

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public float getWater() {
        return this.water;
    }

    public void setWater(float water2) {
        this.water = water2;
    }

    public float getBodyfat() {
        return this.bodyfat;
    }

    public void setBodyfat(float bodyfat2) {
        this.bodyfat = bodyfat2;
    }

    public int getBody_age() {
        return this.body_age;
    }

    public void setBody_age(int body_age2) {
        this.body_age = body_age2;
    }

    public float getBmi() {
        return this.bmi;
    }

    public void setBmi(float bmi2) {
        this.bmi = bmi2;
    }

    public float getMuscule() {
        return this.muscule;
    }

    public void setMuscule(float muscule2) {
        this.muscule = muscule2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public String toString() {
        return "ScaleBodyFat{uid=" + this.uid + ", record_date=" + new DateUtil(this.record_date, true).getY_M_D_H_M_S() + ", weight=" + this.weight + ", data_from='" + this.data_from + '\'' + ", water=" + this.water + ", bodyfat=" + this.bodyfat + ", body_age=" + this.body_age + ", bmi=" + this.bmi + ", muscule=" + this.muscule + ", calorie=" + this.calorie + ", bone_weight=" + this.bone_weight + ", visceral_fat=" + this.visceral_fat + ", impedance=" + this.impedance + '}';
    }
}
