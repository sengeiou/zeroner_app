package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.activity.model.ActivityType;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;

public class UpdateActivityRequest {
    private final int activityID;
    private final ActivityAPI api;
    private Boolean commute;
    private String description;
    private String gearID;
    private Boolean isPrivate;
    private String name;
    private final ActivityRest restService;
    private Boolean trainer;
    private ActivityType type;

    public UpdateActivityRequest(int activityID2, ActivityRest restService2, ActivityAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public UpdateActivityRequest changeName(String name2) {
        this.name = name2;
        return this;
    }

    public UpdateActivityRequest changeType(ActivityType type2) {
        this.type = type2;
        return this;
    }

    public UpdateActivityRequest changePrivate(Boolean isPrivate2) {
        this.isPrivate = isPrivate2;
        return this;
    }

    public UpdateActivityRequest changeCommute(Boolean commute2) {
        this.commute = commute2;
        return this;
    }

    public UpdateActivityRequest changeTrainer(Boolean trainer2) {
        this.trainer = trainer2;
        return this;
    }

    public UpdateActivityRequest changeGearID(String gearID2) {
        this.gearID = gearID2;
        return this;
    }

    public UpdateActivityRequest changeDescription(String description2) {
        this.description = description2;
        return this;
    }

    public Activity execute() {
        return (Activity) this.api.execute(this.restService.updateActivity(Integer.valueOf(this.activityID), this.name, this.type, this.isPrivate, this.commute, this.trainer, this.gearID, this.description));
    }
}
