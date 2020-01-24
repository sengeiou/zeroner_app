package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;

public class GetActivityRequest {
    private final int activityID;
    private final ActivityAPI api;
    private Boolean includeAllEfforts;
    private final ActivityRest restService;

    public GetActivityRequest(int activityID2, ActivityRest restService2, ActivityAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetActivityRequest includeAllEfforts(boolean includeAllEfforts2) {
        this.includeAllEfforts = Boolean.valueOf(includeAllEfforts2);
        return this;
    }

    public Activity execute() {
        return (Activity) this.api.execute(this.restService.getActivity(Integer.valueOf(this.activityID), this.includeAllEfforts));
    }
}
