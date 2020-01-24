package com.sweetzpot.stravazpot.gear.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.ResourceState;

public class Gear {
    @SerializedName("id")
    private String ID;
    @SerializedName("brand_name")
    private String brandName;
    @SerializedName("description")
    private String description;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("frame_type")
    private FrameType frameType;
    @SerializedName("model_name")
    private String modelName;
    @SerializedName("name")
    private String name;
    @SerializedName("primary")
    private boolean primary;
    @SerializedName("resource_state")
    private ResourceState resourceState;

    public String getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public boolean isPrimary() {
        return this.primary;
    }

    public String getName() {
        return this.name;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public FrameType getFrameType() {
        return this.frameType;
    }

    public String getDescription() {
        return this.description;
    }
}
