package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class PrimaryPhoto {
    @SerializedName("id")
    private int ID;
    @SerializedName("source")
    private PhotoSource source;
    @SerializedName("unique_id")
    private String uniqueID;
    @SerializedName("urls")
    private HashMap<String, String> urls;

    public int getID() {
        return this.ID;
    }

    public PhotoSource getSource() {
        return this.source;
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    public HashMap<String, String> getUrls() {
        return this.urls;
    }
}
