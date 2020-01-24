package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.activity.model.ActivityType;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivityRequest {
    private final ActivityAPI api;
    private Boolean commute;
    private String description;
    private Distance distance;
    private Time elapsedTime;
    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private Boolean isPrivate;
    private final String name;
    private final ActivityRest restService;
    private String startDate;
    private Boolean trainer;
    private ActivityType type;

    public CreateActivityRequest(String name2, ActivityRest restService2, ActivityAPI api2) {
        this.name = name2;
        this.restService = restService2;
        this.api = api2;
    }

    public CreateActivityRequest ofType(ActivityType type2) {
        this.type = type2;
        return this;
    }

    public CreateActivityRequest startingOn(Date startDate2) {
        this.startDate = this.formatter.format(startDate2);
        return this;
    }

    public CreateActivityRequest withElapsedTime(Time elapsedTime2) {
        this.elapsedTime = elapsedTime2;
        return this;
    }

    public CreateActivityRequest withDescription(String description2) {
        this.description = description2;
        return this;
    }

    public CreateActivityRequest withDistance(Distance distance2) {
        this.distance = distance2;
        return this;
    }

    public CreateActivityRequest isPrivate(boolean isPrivate2) {
        this.isPrivate = Boolean.valueOf(isPrivate2);
        return this;
    }

    public CreateActivityRequest withTrainer(boolean trainer2) {
        this.trainer = Boolean.valueOf(trainer2);
        return this;
    }

    public CreateActivityRequest withCommute(boolean commute2) {
        this.commute = Boolean.valueOf(commute2);
        return this;
    }

    public Activity execute() {
        return (Activity) this.api.execute(this.restService.createActivity(this.name, this.type, this.startDate, this.elapsedTime, this.description, this.distance, boolToInt(this.isPrivate), boolToInt(this.trainer), boolToInt(this.commute)));
    }

    private Integer boolToInt(Boolean booleanValue) {
        if (booleanValue == null) {
            return null;
        }
        return Integer.valueOf(booleanValue.booleanValue() ? 1 : 0);
    }
}
