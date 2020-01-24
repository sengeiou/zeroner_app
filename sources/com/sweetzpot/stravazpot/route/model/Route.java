package com.sweetzpot.stravazpot.route.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.segment.model.Segment;
import java.util.List;

public class Route {
    @SerializedName("id")
    private int ID;
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("description")
    private String description;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("elevation_gain")
    private Distance elevationGain;
    @SerializedName("estimated_moving_time")
    private int estimatedMovingTime;
    @SerializedName("private")
    private boolean isPrivate;
    @SerializedName("map")
    private Map map;
    @SerializedName("name")
    private String name;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("segments")
    private List<Segment> segments;
    @SerializedName("starred")
    private boolean starred;
    @SerializedName("sub_type")
    private RouteSubtype subtype;
    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("type")
    private RouteType type;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public Distance getElevationGain() {
        return this.elevationGain;
    }

    public Map getMap() {
        return this.map;
    }

    public RouteType getType() {
        return this.type;
    }

    public RouteSubtype getSubtype() {
        return this.subtype;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isStarred() {
        return this.starred;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public List<Segment> getSegments() {
        return this.segments;
    }

    public int getEstimatedMovingTime() {
        return this.estimatedMovingTime;
    }
}
