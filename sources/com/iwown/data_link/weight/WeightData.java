package com.iwown.data_link.weight;

public class WeightData {
    int impedance;
    String macaddr;
    int unit;
    float weight;
    int weightid;
    long weightime;

    public int getWeightid() {
        return this.weightid;
    }

    public void setWeightid(int weightid2) {
        this.weightid = weightid2;
    }

    public String getMacaddr() {
        return this.macaddr;
    }

    public void setMacaddr(String macaddr2) {
        this.macaddr = macaddr2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public int getImpedance() {
        return this.impedance;
    }

    public void setImpedance(int impedance2) {
        this.impedance = impedance2;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit2) {
        this.unit = unit2;
    }

    public long getWeightime() {
        return this.weightime;
    }

    public void setWeightime(long weightime2) {
        this.weightime = weightime2;
    }
}
