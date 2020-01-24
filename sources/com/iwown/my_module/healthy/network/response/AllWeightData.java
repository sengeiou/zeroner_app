package com.iwown.my_module.healthy.network.response;

public class AllWeightData {
    private float bmi;
    private long uid;
    private float weight;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public float getBmi() {
        return this.bmi;
    }

    public void setBmi(float bmi2) {
        this.bmi = bmi2;
    }
}
