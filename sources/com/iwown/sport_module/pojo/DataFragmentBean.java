package com.iwown.sport_module.pojo;

public class DataFragmentBean {
    public float time_s;
    public float totlaKm;

    public DataFragmentBean(float time_s2, float totlaKm2) {
        this.time_s = time_s2;
        this.totlaKm = totlaKm2;
    }

    public String toString() {
        return "DataFragmentBean{time_s=" + this.time_s + ", totlaKm=" + this.totlaKm + '}';
    }
}
