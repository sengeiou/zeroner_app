package com.sweetzpot.stravazpot.athlete.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.club.model.Club;
import com.sweetzpot.stravazpot.common.model.Gender;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.gear.model.Gear;
import java.util.Date;
import java.util.List;

public class Athlete {
    @SerializedName("id")
    private long ID;
    @SerializedName("athlete_type")
    private AthleteType athleteType;
    @SerializedName("bikes")
    private List<Gear> bikes;
    @SerializedName("city")
    private String city;
    @SerializedName("clubs")
    private List<Club> clubs;
    @SerializedName("country")
    private String country;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("date_preference")
    private String datePreference;
    @SerializedName("email")
    private String email;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("follower")
    private FriendStatus follower;
    @SerializedName("follower_count")
    private int followerCount;
    @SerializedName("friend")
    private FriendStatus friend;
    @SerializedName("friend_count")
    private int friendCount;
    @SerializedName("ftp")
    private int ftp;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("measurement_preference")
    private MeasurementPreference measurementPreference;
    @SerializedName("mutual_friend_count")
    private int mutualFriendCount;
    @SerializedName("premium")
    private boolean premium;
    @SerializedName("profile")
    private String profile;
    @SerializedName("profile_medium")
    private String profileMedium;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("sex")
    private Gender sex;
    @SerializedName("shoes")
    private List<Gear> shoes;
    @SerializedName("state")
    private String state;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("weight")
    private float weight;

    public long getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getProfileMedium() {
        return this.profileMedium;
    }

    public String getProfile() {
        return this.profile;
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

    public Gender getSex() {
        return this.sex;
    }

    public FriendStatus getFriend() {
        return this.friend;
    }

    public FriendStatus getFollower() {
        return this.follower;
    }

    public boolean isPremium() {
        return this.premium;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public int getFollowerCount() {
        return this.followerCount;
    }

    public int getFriendCount() {
        return this.friendCount;
    }

    public int getMutualFriendCount() {
        return this.mutualFriendCount;
    }

    public AthleteType getAthleteType() {
        return this.athleteType;
    }

    public String getDatePreference() {
        return this.datePreference;
    }

    public MeasurementPreference getMeasurementPreference() {
        return this.measurementPreference;
    }

    public String getEmail() {
        return this.email;
    }

    public int getFtp() {
        return this.ftp;
    }

    public float getWeight() {
        return this.weight;
    }

    public List<Club> getClubs() {
        return this.clubs;
    }

    public List<Gear> getBikes() {
        return this.bikes;
    }

    public List<Gear> getShoes() {
        return this.shoes;
    }
}
