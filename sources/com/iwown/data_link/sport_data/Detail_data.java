package com.iwown.data_link.sport_data;

import com.iwown.lib_common.json.JsonTool;

public class Detail_data {
    private static final long serialVersionUID = 1;
    private int activity;
    private int count;
    private float distance;
    private int step;

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public int getActivity() {
        return this.activity;
    }

    public void setActivity(int activity2) {
        this.activity = activity2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public String toString() {
        return JsonTool.toJson(this);
    }
}
