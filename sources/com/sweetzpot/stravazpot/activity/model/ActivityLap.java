package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.common.model.Speed;
import com.sweetzpot.stravazpot.common.model.Time;
import java.util.Date;

public class ActivityLap {
    @SerializedName("id")
    private int ID;
    @SerializedName("activity")
    private Activity activity;
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("average_cadence")
    private float averageCadence;
    @SerializedName("average_heartrate")
    private float averageHeartRate;
    @SerializedName("average_speed")
    private Speed averageSpeed;
    @SerializedName("average_watts")
    private float averageWatts;
    @SerializedName("device_watts")
    private boolean deviceWatts;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("elapsed_time")
    private Time elapsedTime;
    @SerializedName("end_index")
    private int endIndex;
    @SerializedName("has_heartrate")
    private boolean hasHeartRate;
    @SerializedName("lap_index")
    private int lapIndex;
    @SerializedName("max_heartrate")
    private float maxHeartRate;
    @SerializedName("max_speed")
    private Speed maxSpeed;
    @SerializedName("moving_time")
    private Time movingTime;
    @SerializedName("name")
    private String name;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("start_date_local")
    private Date startDateLocal;
    @SerializedName("start_index")
    private int startIndex;
    @SerializedName("total_elevation_gain")
    private Distance totalElevationGain;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getName() {
        return this.name;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Time getElapsedTime() {
        return this.elapsedTime;
    }

    public Time getMovingTime() {
        return this.movingTime;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getStartDateLocal() {
        return this.startDateLocal;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public Distance getTotalElevationGain() {
        return this.totalElevationGain;
    }

    public Speed getAverageSpeed() {
        return this.averageSpeed;
    }

    public Speed getMaxSpeed() {
        return this.maxSpeed;
    }

    public float getAverageCadence() {
        return this.averageCadence;
    }

    public float getAverageWatts() {
        return this.averageWatts;
    }

    public boolean isDeviceWatts() {
        return this.deviceWatts;
    }

    public boolean hasHeartRate() {
        return this.hasHeartRate;
    }

    public float getMaxHeartRate() {
        return this.maxHeartRate;
    }

    public float getAverageHeartRate() {
        return this.averageHeartRate;
    }

    public int getLapIndex() {
        return this.lapIndex;
    }
}
