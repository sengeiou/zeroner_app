package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.ActivityZone;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;
import java.util.List;

public class ListActivityZonesRequest {
    private final int activityID;
    private final ActivityAPI api;
    private final ActivityRest restService;

    public ListActivityZonesRequest(int activityID2, ActivityRest restService2, ActivityAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public List<ActivityZone> execute() {
        return (List) this.api.execute(this.restService.getActivityZones(Integer.valueOf(this.activityID)));
    }
}
