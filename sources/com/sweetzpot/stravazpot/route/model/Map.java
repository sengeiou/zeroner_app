package com.sweetzpot.stravazpot.route.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.ResourceState;

public class Map {
    @SerializedName("id")
    private String ID;
    @SerializedName("polyline")
    private String polyline;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("summary_polyline")
    private String summaryPolyline;

    public String getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getSummaryPolyline() {
        return this.summaryPolyline;
    }

    public String getPolyline() {
        return this.polyline;
    }
}
