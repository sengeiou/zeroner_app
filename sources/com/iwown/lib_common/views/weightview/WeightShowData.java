package com.iwown.lib_common.views.weightview;

import com.iwown.lib_common.date.DateUtil;

public class WeightShowData {
    public int old_index;
    public float real_weight;
    public long time;

    public WeightShowData(long time2, float real_weight2) {
        this.real_weight = real_weight2;
        this.time = time2;
    }

    public String toString() {
        return "WeightShowData{old_index=" + this.old_index + ", real_weight=" + this.real_weight + ", time=" + new DateUtil(this.time, true).getY_M_D_H_M() + '}';
    }
}
