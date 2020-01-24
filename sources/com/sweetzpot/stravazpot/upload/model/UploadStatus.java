package com.sweetzpot.stravazpot.upload.model;

import com.google.gson.annotations.SerializedName;

public class UploadStatus {
    @SerializedName("activity_id")
    private Integer activityID;
    @SerializedName("error")
    private String error;
    @SerializedName("external_id")
    private String externalID;
    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private String status;

    public int getId() {
        return this.id;
    }

    public String getExternalID() {
        return this.externalID;
    }

    public String getError() {
        return this.error;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getActivityID() {
        return this.activityID;
    }
}
