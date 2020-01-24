package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.Time;

public class Split {
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("elapsed_time")
    private Time elapsedTime;
    @SerializedName("elevation_difference")
    private Distance elevationDifference;
    @SerializedName("moving_time")
    private Time movingTime;
    @SerializedName("split")
    private int split;

    public Distance getDistance() {
        return this.distance;
    }

    public Time getElapsedTime() {
        return this.elapsedTime;
    }

    public Distance getElevationDifference() {
        return this.elevationDifference;
    }

    public Time getMovingTime() {
        return this.movingTime;
    }

    public int getSplit() {
        return this.split;
    }
}
