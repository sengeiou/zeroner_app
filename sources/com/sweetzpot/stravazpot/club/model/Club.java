package com.sweetzpot.stravazpot.club.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.ResourceState;

public class Club {
    @SerializedName("id")
    private int ID;
    @SerializedName("admin")
    private boolean admin;
    @SerializedName("city")
    private String city;
    @SerializedName("club_type")
    private ClubType clubType;
    @SerializedName("country")
    private String country;
    @SerializedName("cover_photo")
    private String coverPhoto;
    @SerializedName("cover_photo_small")
    private String coverPhotoSmall;
    @SerializedName("description")
    private String description;
    @SerializedName("featured")
    private boolean featured;
    @SerializedName("following_count")
    private int followingCount;
    @SerializedName("private")
    private boolean isPrivate;
    @SerializedName("member_count")
    private int memberCount;
    @SerializedName("membership")
    private Membership membership;
    @SerializedName("name")
    private String name;
    @SerializedName("owner")
    private boolean owner;
    @SerializedName("profile")
    private String profile;
    @SerializedName("profile_medium")
    private String profileMedium;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("sport_type")
    private SportType sportType;
    @SerializedName("state")
    private String state;
    @SerializedName("url")
    private String url;
    @SerializedName("verified")
    private boolean verified;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getName() {
        return this.name;
    }

    public String getProfileMedium() {
        return this.profileMedium;
    }

    public String getProfile() {
        return this.profile;
    }

    public String getCoverPhoto() {
        return this.coverPhoto;
    }

    public String getCoverPhotoSmall() {
        return this.coverPhotoSmall;
    }

    public String getDescription() {
        return this.description;
    }

    public ClubType getClubType() {
        return this.clubType;
    }

    public SportType getSportType() {
        return this.sportType;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public int getMemberCount() {
        return this.memberCount;
    }

    public boolean isFeatured() {
        return this.featured;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public boolean isOwner() {
        return this.owner;
    }

    public int getFollowingCount() {
        return this.followingCount;
    }

    public String getUrl() {
        return this.url;
    }
}
