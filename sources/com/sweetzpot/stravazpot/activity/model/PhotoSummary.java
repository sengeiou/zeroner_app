package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;

public class PhotoSummary {
    @SerializedName("count")
    private int count;
    @SerializedName("primary")
    private PrimaryPhoto primaryPhoto;

    public int getCount() {
        return this.count;
    }

    public PrimaryPhoto getPrimaryPhoto() {
        return this.primaryPhoto;
    }
}
