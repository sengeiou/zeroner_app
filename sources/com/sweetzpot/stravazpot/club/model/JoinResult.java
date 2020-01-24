package com.sweetzpot.stravazpot.club.model;

import com.google.gson.annotations.SerializedName;

public class JoinResult {
    @SerializedName("active")
    boolean active;
    @SerializedName("membership")
    Membership membership;
    @SerializedName("success")
    boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isActive() {
        return this.active;
    }

    public Membership getMembership() {
        return this.membership;
    }
}
