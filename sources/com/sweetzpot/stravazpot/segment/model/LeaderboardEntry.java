package com.sweetzpot.stravazpot.segment.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.Gender;
import com.sweetzpot.stravazpot.common.model.Time;
import java.util.Date;

public class LeaderboardEntry {
    @SerializedName("activity_id")
    private int activityID;
    @SerializedName("athlete_gender")
    private Gender athleteGender;
    @SerializedName("athlete_id")
    private int athleteID;
    @SerializedName("athlete_name")
    private String athleteName;
    @SerializedName("athlete_profile")
    private String athleteProfile;
    @SerializedName("average_hr")
    private float averageHeartRate;
    @SerializedName("average_watts")
    private float averageWatts;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("effort_id")
    private int effortID;
    @SerializedName("elapsed_time")
    private Time elapsedTime;
    @SerializedName("moving_time")
    private Time movingTime;
    @SerializedName("rank")
    private int rank;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("start_date_local")
    private Date startDateLocal;

    public String getAthleteName() {
        return this.athleteName;
    }

    public int getAthleteID() {
        return this.athleteID;
    }

    public Gender getAthleteGender() {
        return this.athleteGender;
    }

    public float getAverageHeartRate() {
        return this.averageHeartRate;
    }

    public float getAverageWatts() {
        return this.averageWatts;
    }

    public Distance getDistance() {
        return this.distance;
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

    public int getActivityID() {
        return this.activityID;
    }

    public int getEffortID() {
        return this.effortID;
    }

    public int getRank() {
        return this.rank;
    }

    public String getAthleteProfile() {
        return this.athleteProfile;
    }
}
