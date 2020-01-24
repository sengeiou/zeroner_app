package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import java.util.Date;

public class Comment {
    @SerializedName("id")
    private int ID;
    @SerializedName("activity_id")
    private int activityID;
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("text")
    private String text;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public int getActivityID() {
        return this.activityID;
    }

    public String getText() {
        return this.text;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
