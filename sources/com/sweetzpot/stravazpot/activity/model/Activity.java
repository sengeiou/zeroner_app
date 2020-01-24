package com.sweetzpot.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.common.model.Coordinates;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import com.sweetzpot.stravazpot.common.model.Speed;
import com.sweetzpot.stravazpot.common.model.Temperature;
import com.sweetzpot.stravazpot.common.model.Time;
import com.sweetzpot.stravazpot.gear.model.Gear;
import com.sweetzpot.stravazpot.route.model.Map;
import com.sweetzpot.stravazpot.segment.model.SegmentEffort;
import java.util.Date;
import java.util.List;

public class Activity {
    @SerializedName("id")
    private int ID;
    @SerializedName("achievement_count")
    private int achievementCount;
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("athlete_count")
    private int athleteCount;
    @SerializedName("average_cadence")
    private float averageCadence;
    @SerializedName("average_heartrate")
    private float averageHeartRate;
    @SerializedName("average_speed")
    private Speed averageSpeed;
    @SerializedName("average_temp")
    private Temperature averageTemperature;
    @SerializedName("average_watts")
    private float averageWatts;
    @SerializedName("best_efforts")
    private List<SegmentEffort> bestEfforts;
    @SerializedName("calories")
    private float calories;
    @SerializedName("comment_count")
    private int commentCount;
    @SerializedName("commute")
    private boolean commute;
    @SerializedName("description")
    private String description;
    @SerializedName("device_name")
    private String deviceName;
    @SerializedName("device_watts")
    private boolean deviceWatts;
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("elapsed_time")
    private Time elapsedTime;
    @SerializedName("elev_high")
    private Distance elevationHigh;
    @SerializedName("elev_low")
    private Distance elevationLow;
    @SerializedName("embed_token")
    private String embedToken;
    @SerializedName("end_latlng")
    private Coordinates endCoordinates;
    @SerializedName("external_id")
    private String externalID;
    @SerializedName("flagged")
    private boolean flagged;
    @SerializedName("gear")
    private Gear gear;
    @SerializedName("gear_id")
    private String gearID;
    @SerializedName("has_heartrate")
    private boolean hasHeartRate;
    @SerializedName("has_kudoed")
    private boolean hasKudoed;
    @SerializedName("private")
    private boolean isPrivate;
    @SerializedName("kilojoules")
    private float kilojoules;
    @SerializedName("kudos_count")
    private int kudosCount;
    @SerializedName("manual")
    private boolean manual;
    @SerializedName("map")
    private Map map;
    @SerializedName("max_heartrate")
    private int maxHeartRate;
    @SerializedName("max_speed")
    private Speed maxSpeed;
    @SerializedName("max_watts")
    private int maxWatts;
    @SerializedName("moving_time")
    private Time movingTime;
    @SerializedName("name")
    private String name;
    @SerializedName("photo_count")
    private int photoCount;
    @SerializedName("photos")
    private PhotoSummary photos;
    @SerializedName("resource_state")
    private ResourceState resourceState;
    @SerializedName("segment_efforts")
    private List<SegmentEffort> segmentEfforts;
    @SerializedName("splits_metric")
    private List<Split> splitsMetric;
    @SerializedName("splits_standard")
    private List<Split> splitsStandard;
    @SerializedName("start_latlng")
    private Coordinates startCoordinates;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("start_date_local")
    private Date startDateLocal;
    @SerializedName("suffer_score")
    private int sufferScore;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("total_elevation_gain")
    private Distance totalElevationGain;
    @SerializedName("total_photo_count")
    private int totalPhotoCount;
    @SerializedName("trainer")
    private boolean trainer;
    @SerializedName("type")
    private ActivityType type;
    @SerializedName("upload_id")
    private int uploadID;
    @SerializedName("weighted_average_watts")
    private int weightedAverageWatts;
    @SerializedName("workout_type")
    private WorkoutType workoutType;

    public int getID() {
        return this.ID;
    }

    public ResourceState getResourceState() {
        return this.resourceState;
    }

    public String getExternalID() {
        return this.externalID;
    }

    public int getUploadID() {
        return this.uploadID;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public Time getMovingTime() {
        return this.movingTime;
    }

    public Time getElapsedTime() {
        return this.elapsedTime;
    }

    public Distance getTotalElevationGain() {
        return this.totalElevationGain;
    }

    public Distance getElevationHigh() {
        return this.elevationHigh;
    }

    public Distance getElevationLow() {
        return this.elevationLow;
    }

    public ActivityType getType() {
        return this.type;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getStartDateLocal() {
        return this.startDateLocal;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public Coordinates getStartCoordinates() {
        return this.startCoordinates;
    }

    public Coordinates getEndCoordinates() {
        return this.endCoordinates;
    }

    public int getAchievementCount() {
        return this.achievementCount;
    }

    public int getKudosCount() {
        return this.kudosCount;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public int getAthleteCount() {
        return this.athleteCount;
    }

    public int getPhotoCount() {
        return this.photoCount;
    }

    public int getTotalPhotoCount() {
        return this.totalPhotoCount;
    }

    public PhotoSummary getPhotos() {
        return this.photos;
    }

    public Map getMap() {
        return this.map;
    }

    public boolean isTrainer() {
        return this.trainer;
    }

    public boolean isCommute() {
        return this.commute;
    }

    public boolean isManual() {
        return this.manual;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getEmbedToken() {
        return this.embedToken;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public WorkoutType getWorkoutType() {
        return this.workoutType;
    }

    public String getGearID() {
        return this.gearID;
    }

    public Gear getGear() {
        return this.gear;
    }

    public Speed getAverageSpeed() {
        return this.averageSpeed;
    }

    public Speed getMaxSpeed() {
        return this.maxSpeed;
    }

    public float getAverageCadence() {
        return this.averageCadence;
    }

    public Temperature getAverageTemperature() {
        return this.averageTemperature;
    }

    public float getAverageWatts() {
        return this.averageWatts;
    }

    public int getMaxWatts() {
        return this.maxWatts;
    }

    public int getWeightedAverageWatts() {
        return this.weightedAverageWatts;
    }

    public float getKilojoules() {
        return this.kilojoules;
    }

    public boolean isDeviceWatts() {
        return this.deviceWatts;
    }

    public boolean isHasHeartRate() {
        return this.hasHeartRate;
    }

    public float getAverageHeartRate() {
        return this.averageHeartRate;
    }

    public int getMaxHeartRate() {
        return this.maxHeartRate;
    }

    public float getCalories() {
        return this.calories;
    }

    public int getSufferScore() {
        return this.sufferScore;
    }

    public boolean hasKudoed() {
        return this.hasKudoed;
    }

    public List<SegmentEffort> getSegmentEfforts() {
        return this.segmentEfforts;
    }

    public List<SegmentEffort> getBestEfforts() {
        return this.bestEfforts;
    }

    public List<Split> getSplitsMetric() {
        return this.splitsMetric;
    }

    public List<Split> getSplitsStandard() {
        return this.splitsStandard;
    }
}
