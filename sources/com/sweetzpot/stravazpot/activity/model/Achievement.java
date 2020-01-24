package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;

public class Achievement {
    @SerializedName("rank")
    private int rank;
    @SerializedName("type")
    private String type;
    @SerializedName("type_id")
    private AchievementType typeID;

    public AchievementType getTypeID() {
        return this.typeID;
    }

    public String getType() {
        return this.type;
    }

    public int getRank() {
        return this.rank;
    }
}
