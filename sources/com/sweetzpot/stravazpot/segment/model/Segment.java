package com.sweetzpot.stravazpot.segment.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.activity.model.ActivityType;
import com.sweetzpot.stravazpot.common.model.Coordinates;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.Percentage;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.route.model.Map;
import java.util.Date;

public class Segment {
    @SerializedName("id")
    private int ID;
    @SerializedName("activity_type")
    private ActivityType activityType;
    @SerializedName("athlete_count")
    private int athleteCount;
    @SerializedName("average_grade")
    private Percentage averageGrade;
    @SerializedName("city")
    private String city;
    @SerializedName("climb_category")
    private int climbCategory;
    @SerializedName("country")
    private String country;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("effort_count")
    private int effortCount;
    @SerializedName("elevation_high")
    private Distance elevationHigh;
    @SerializedName("elevation_low")
    private Distance elevationLow;
    @SerializedName("end_latlng")
    private Coordinates endCoordinates;
    @SerializedName("hazardous")
    private boolean hazardous;
    @SerializedName("private")
    private boolean isPrivate;
    @SerializedName("map")
    private Map map;
    @SerializedName("maximum_grade")
    private Percentage maximumGrade;
    @SerializedName("name")
    private String name;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("star_count")
    private int starCount;
    @SerializedName("starred")
    private boolean starred;
    @SerializedName("start_latlng")
    private Coordinates startCoordinates;
    @SerializedName("state")
    private String state;
    @SerializedName("total_elevation_gain")
    private Distance totalElevationGain;
    @SerializedName("updated_at")
    private Date updatedAt;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getName() {
        return this.name;
    }

    public ActivityType getActivityType() {
        return this.activityType;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public Percentage getAverageGrade() {
        return this.averageGrade;
    }

    public Percentage getMaximumGrade() {
        return this.maximumGrade;
    }

    public Distance getElevationHigh() {
        return this.elevationHigh;
    }

    public Distance getElevationLow() {
        return this.elevationLow;
    }

    public Coordinates getStartCoordinates() {
        return this.startCoordinates;
    }

    public Coordinates getEndCoordinates() {
        return this.endCoordinates;
    }

    public int getClimbCategory() {
        return this.climbCategory;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isStarred() {
        return this.starred;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public Distance getTotalElevationGain() {
        return this.totalElevationGain;
    }

    public Map getMap() {
        return this.map;
    }

    public int getEffortCount() {
        return this.effortCount;
    }

    public int getAthleteCount() {
        return this.athleteCount;
    }

    public boolean isHazardous() {
        return this.hazardous;
    }

    public int getStarCount() {
        return this.starCount;
    }
}
