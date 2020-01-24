package com.iwown.ble_module.model;

public class R1HeartRateMinuteData {
    private int avg_hr;
    private int max_hr;
    private int min_hr;

    public int getMin_hr() {
        return this.min_hr;
    }

    public void setMin_hr(int min_hr2) {
        this.min_hr = min_hr2;
    }

    public int getMax_hr() {
        return this.max_hr;
    }

    public void setMax_hr(int max_hr2) {
        this.max_hr = max_hr2;
    }

    public int getAvg_hr() {
        return this.avg_hr;
    }

    public void setAvg_hr(int avg_hr2) {
        this.avg_hr = avg_hr2;
    }
}
