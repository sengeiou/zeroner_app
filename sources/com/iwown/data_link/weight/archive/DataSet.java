package com.iwown.data_link.weight.archive;

public class DataSet {
    private double bmi;
    private double bodyFat;
    private double boneWeight;
    private double calorie;
    private double impedance;
    private double muscule;
    private double visceralFat;
    private double water;
    private double weight;

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight2) {
        this.weight = weight2;
    }

    public double getWater() {
        return this.water;
    }

    public void setWater(double water2) {
        this.water = water2;
    }

    public double getBodyFat() {
        return this.bodyFat;
    }

    public void setBodyFat(double bodyFat2) {
        this.bodyFat = bodyFat2;
    }

    public double getBmi() {
        return this.bmi;
    }

    public void setBmi(double bmi2) {
        this.bmi = bmi2;
    }

    public double getBoneWeight() {
        return this.boneWeight;
    }

    public void setBoneWeight(double boneWeight2) {
        this.boneWeight = boneWeight2;
    }

    public double getMuscule() {
        return this.muscule;
    }

    public void setMuscule(double muscule2) {
        this.muscule = muscule2;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(double calorie2) {
        this.calorie = calorie2;
    }

    public double getVisceralFat() {
        return this.visceralFat;
    }

    public void setVisceralFat(double visceralFat2) {
        this.visceralFat = visceralFat2;
    }

    public double getImpedance() {
        return this.impedance;
    }

    public void setImpedance(double impedance2) {
        this.impedance = impedance2;
    }

    public String toString() {
        return "DataSet [weight=" + this.weight + ", impedance=" + this.impedance + ", water=" + this.water + ", bodyFat=" + this.bodyFat + ", bmi=" + this.bmi + ", boneWeight=" + this.boneWeight + ", muscule=" + this.muscule + ", calorie=" + this.calorie + ", visceralFat=" + this.visceralFat + "]";
    }
}
