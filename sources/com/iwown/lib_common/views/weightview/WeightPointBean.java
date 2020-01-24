package com.iwown.lib_common.views.weightview;

import com.iwown.lib_common.date.DateUtil;

public class WeightPointBean {
    public float real_weight;
    public float screenX;
    public float screenY;
    public String showTime;
    private long unix_time;
    public WeightShowData weightData;
    public float x;
    public float y;

    public WeightPointBean(float x2, float y2, float real_weight2, long unix_time2) {
        this.x = x2;
        this.y = y2;
        this.real_weight = real_weight2;
        this.unix_time = unix_time2;
        this.showTime = new DateUtil(unix_time2, true).getMMddDate();
    }
}
