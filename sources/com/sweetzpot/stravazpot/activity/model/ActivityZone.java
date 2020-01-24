package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.common.model.TimedInterval;
import java.util.List;

public class ActivityZone {
    @SerializedName("custom_zones")
    private boolean customZones;
    @SerializedName("distribution_buckets")
    private List<TimedInterval> distributionBuckets;
    @SerializedName("max")
    private int max;
    @SerializedName("points")
    private int points;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("score")
    private int score;
    @SerializedName("sensor_based")
    private boolean sensorBased;
    @SerializedName("type")
    private String type;

    public int getScore() {
        return this.score;
    }

    public List<TimedInterval> getDistributionBuckets() {
        return this.distributionBuckets;
    }

    public String getType() {
        return this.type;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public boolean isSensorBased() {
        return this.sensorBased;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean hasCustomZones() {
        return this.customZones;
    }

    public int getMax() {
        return this.max;
    }
}
