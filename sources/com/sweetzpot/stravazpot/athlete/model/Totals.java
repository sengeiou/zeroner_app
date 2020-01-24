package com.sweetzpot.stravazpot.athlete.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.Time;

public class Totals {
    @SerializedName("achievement_count")
    private int achievementCount;
    @SerializedName("count")
    private int count;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("elapsed_time")
    private Time elapsedTime;
    @SerializedName("elevation_gain")
    private Distance elevationGain;
    @SerializedName("moving_time")
    private Time movingTime;

    public int getCount() {
        return this.count;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public Time getMovingTime() {
        return this.movingTime;
    }

    public Time getElapsedTime() {
        return this.elapsedTime;
    }

    public Distance getElevationGain() {
        return this.elevationGain;
    }

    public int getAchievementCount() {
        return this.achievementCount;
    }
}
