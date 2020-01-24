package com.sweetzpot.stravazpot.segment.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.activity.model.Achievement;
import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.common.model.Time;
import java.util.Date;
import java.util.List;

public class SegmentEffort {
    @SerializedName("id")
    private long ID;
    @SerializedName("achievements")
    private List<Achievement> achievements;
    @SerializedName("activity")
    private Activity activity;
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("average_cadence")
    private float averageCadence;
    @SerializedName("average_heartrate")
    private float averageHeartRate;
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
    @SerializedName("hidden")
    private boolean hidden;
    @SerializedName("kom_rank")
    private int komRank;
    @SerializedName("max_heartrate")
    private float maxHeartRate;
    @SerializedName("moving_time")
    private Time movingTime;
    @SerializedName("name")
    private String name;
    @SerializedName("pr_rank")
    private int prRank;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("segment")
    private Segment segment;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("start_date_local")
    private Date startDateLocal;
    @SerializedName("start_index")
    private int startIndex;

    public long getID() {
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

    public float getAverageCadence() {
        return this.averageCadence;
    }

    public float getAverageWatts() {
        return this.averageWatts;
    }

    public boolean isDeviceWatts() {
        return this.deviceWatts;
    }

    public float getAverageHeartRate() {
        return this.averageHeartRate;
    }

    public float getMaxHeartRate() {
        return this.maxHeartRate;
    }

    public Segment getSegment() {
        return this.segment;
    }

    public int getKomRank() {
        return this.komRank;
    }

    public int getPrRank() {
        return this.prRank;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public List<Achievement> getAchievements() {
        return this.achievements;
    }
}
