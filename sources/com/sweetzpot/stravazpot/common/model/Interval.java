package com.sweetzpot.stravazpot.common.model;

import com.google.gson.annotations.SerializedName;

public class Interval<T> {
    @SerializedName("max")
    private T max;
    @SerializedName("min")
    private T min;

    public T getMin() {
        return this.min;
    }

    public T getMax() {
        return this.max;
    }
}
