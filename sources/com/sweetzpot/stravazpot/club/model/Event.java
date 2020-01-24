package com.sweetzpot.stravazpot.club.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.activity.model.ActivityType;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import java.util.Date;
import java.util.List;

public class Event {
    @SerializedName("id")
    private int ID;
    @SerializedName("activity_type")
    private ActivityType activityType;
    @SerializedName("address")
    private String address;
    @SerializedName("club_id")
    private int clubID;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("description")
    private String description;
    @SerializedName("private")
    private boolean isPrivate;
    @SerializedName("organizing_athlete")
    private Athlete organizingAthlete;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("route_id")
    private int routeID;
    @SerializedName("skill_levels")
    private SkillLevel skillLevel;
    @SerializedName("terrain")
    private Terrain terrain;
    @SerializedName("title")
    private String title;
    @SerializedName("upcoming_occurrences")
    private List<Date> upcomingOccurrences;
    @SerializedName("women_only")
    private boolean womenOnly;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getClubID() {
        return this.clubID;
    }

    public Athlete getOrganizingAthlete() {
        return this.organizingAthlete;
    }

    public ActivityType getActivityType() {
        return this.activityType;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public int getRouteID() {
        return this.routeID;
    }

    public boolean isWomenOnly() {
        return this.womenOnly;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public List<Date> getUpcomingOccurrences() {
        return this.upcomingOccurrences;
    }

    public String getAddress() {
        return this.address;
    }
}
