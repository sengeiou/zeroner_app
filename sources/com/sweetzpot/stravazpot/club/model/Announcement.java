package com.sweetzpot.stravazpot.club.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import java.util.Date;

public class Announcement {
    @SerializedName("id")
    private int ID;
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("club_id")
    private int clubID;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("message")
    private String message;
    @SerializedName("resource_state")
    private ResourceState resourceState;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public int getClubID() {
        return this.clubID;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getMessage() {
        return this.message;
    }
}
