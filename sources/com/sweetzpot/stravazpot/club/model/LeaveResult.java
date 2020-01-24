package com.sweetzpot.stravazpot.club.model;

import com.google.gson.annotations.SerializedName;

public class LeaveResult {
    @SerializedName("active")
    boolean active;
    @SerializedName("success")
    boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isActive() {
        return this.active;
    }
}
