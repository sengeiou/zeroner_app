package com.iwown.data_link.fatigue;

public class FatigueData {
    public String measuretime;
    public int value;

    public FatigueData(String measuretime2, int value2) {
        this.measuretime = measuretime2;
        this.value = value2;
    }

    public String getMeasuretime() {
        return this.measuretime;
    }

    public void setMeasuretime(String measuretime2) {
        this.measuretime = measuretime2;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value2) {
        this.value = value2;
    }
}
