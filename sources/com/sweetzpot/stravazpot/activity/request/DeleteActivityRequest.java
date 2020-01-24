package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;

public class DeleteActivityRequest {
    private final int activityID;
    private final ActivityAPI api;
    private final ActivityRest restService;

    public DeleteActivityRequest(int activityID2, ActivityRest restService2, ActivityAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public void execute() {
        this.api.execute(this.restService.deleteActivity(Integer.valueOf(this.activityID)));
    }
}
