package com.iwown.device_module.common.sql.weight;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class TB_Weight extends DataSupport {
    private String address;
    private int bodyImpedance;
    private int dataId;
    private String date;
    private float height;
    private double htBMI;
    private int htBMR;
    private double htBodyfatPercentage;
    private double htBoneKg;
    private double htMuscleKg;
    private int htVFAL;
    private double htWaterPercentage;
    private String name;
    private String sWeight;
    private long timeStamp;
    private long uid;
    private float weight;
    @Column(ignore = true)
    private int weightUnit;

    public String getsWeight() {
        return this.sWeight;
    }

    public void setsWeight(String sWeight2) {
        this.sWeight = sWeight2;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp2) {
        this.timeStamp = timeStamp2;
    }

    public double getHtBMI() {
        return this.htBMI;
    }

    public void setHtBMI(double htBMI2) {
        this.htBMI = htBMI2;
    }

    public int getHtBMR() {
        return this.htBMR;
    }

    public void setHtBMR(int htBMR2) {
        this.htBMR = htBMR2;
    }

    public int getHtVFAL() {
        return this.htVFAL;
    }

    public void setHtVFAL(int htVFAL2) {
        this.htVFAL = htVFAL2;
    }

    public double getHtBoneKg() {
        return this.htBoneKg;
    }

    public void setHtBoneKg(double htBoneKg2) {
        this.htBoneKg = htBoneKg2;
    }

    public double getHtBodyfatPercentage() {
        return this.htBodyfatPercentage;
    }

    public void setHtBodyfatPercentage(double htBodyfatPercentage2) {
        this.htBodyfatPercentage = htBodyfatPercentage2;
    }

    public double getHtWaterPercentage() {
        return this.htWaterPercentage;
    }

    public void setHtWaterPercentage(double htWaterPercentage2) {
        this.htWaterPercentage = htWaterPercentage2;
    }

    public double getHtMuscleKg() {
        return this.htMuscleKg;
    }

    public void setHtMuscleKg(double htMuscleKg2) {
        this.htMuscleKg = htMuscleKg2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getDataId() {
        return this.dataId;
    }

    public void setDataId(int dataId2) {
        this.dataId = dataId2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public int getWeightUnit() {
        return this.weightUnit;
    }

    public void setWeightUnit(int weightUnit2) {
        this.weightUnit = weightUnit2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int getBodyImpedance() {
        return this.bodyImpedance;
    }

    public void setBodyImpedance(int bodyImpedance2) {
        this.bodyImpedance = bodyImpedance2;
    }
}
