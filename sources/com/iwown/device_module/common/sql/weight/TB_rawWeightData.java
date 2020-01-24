package com.iwown.device_module.common.sql.weight;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class TB_rawWeightData extends DataSupport {
    private int impedance;
    private String macaddr;
    private int unit;
    private float weight;
    @Column(unique = true)
    private int weightid;
    private String weightime;

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

    public String getWeightime() {
        return this.weightime;
    }

    public void setWeightime(String weightime2) {
        this.weightime = weightime2;
    }

    public int getWeightid() {
        return this.weightid;
    }

    public void setWeightid(int weightid2) {
        this.weightid = weightid2;
    }
}
