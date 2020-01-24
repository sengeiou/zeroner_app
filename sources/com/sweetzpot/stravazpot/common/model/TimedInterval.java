package com.sweetzpot.stravazpot.common.model;

import com.google.gson.annotations.SerializedName;

public class TimedInterval<T> {
    @SerializedName("max")
    private T max;
    @SerializedName("min")
    private T min;
    @SerializedName("time")
    private long time;

    public T getMin() {
        return this.min;
    }

    public T getMax() {
        return this.max;
    }

    public long getTime() {
        return this.time;
    }
}
