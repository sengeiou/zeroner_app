package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.Coordinates;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import java.util.Date;
import java.util.HashMap;

public class Photo {
    @SerializedName("id")
    private int ID;
    @SerializedName("activity_id")
    private int activityID;
    @SerializedName("caption")
    private String caption;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("location")
    private Coordinates location;
    @SerializedName("ref")
    private String ref;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("source")
    private PhotoSource source;
    @SerializedName("type")
    private String type;
    @SerializedName("uid")
    private String uid;
    @SerializedName("unique_id")
    private String uniqueID;
    @SerializedName("uploaded_at")
    private Date uploadedAt;
    @SerializedName("urls")
    private HashMap<String, String> urls;

    public String getUniqueID() {
        return this.uniqueID;
    }

    public int getID() {
        return this.ID;
    }

    public int getActivityID() {
        return this.activityID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public HashMap<String, String> getUrls() {
        return this.urls;
    }

    public String getCaption() {
        return this.caption;
    }

    public PhotoSource getSource() {
        return this.source;
    }

    public Date getUploadedAt() {
        return this.uploadedAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public String getRef() {
        return this.ref;
    }

    public String getUid() {
        return this.uid;
    }

    public String getType() {
        return this.type;
    }
}
