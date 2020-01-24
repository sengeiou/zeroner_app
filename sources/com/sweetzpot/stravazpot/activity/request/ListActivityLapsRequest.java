package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.ActivityLap;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;
import java.util.List;

public class ListActivityLapsRequest {
    private final int activityID;
    private final ActivityAPI api;
    private final ActivityRest restService;

    public ListActivityLapsRequest(int activityID2, ActivityRest restService2, ActivityAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public List<ActivityLap> execute() {
        return (List) this.api.execute(this.restService.getActivityLaps(Integer.valueOf(this.activityID)));
    }
}
