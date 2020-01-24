package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;
import java.util.List;

public class ListRelatedActivitiesRequest {
    private final int activityID;
    private final ActivityAPI api;
    private Integer page;
    private Integer perPage;
    private final ActivityRest restService;

    public ListRelatedActivitiesRequest(int activityID2, ActivityRest restService2, ActivityAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListRelatedActivitiesRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListRelatedActivitiesRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Activity> execute() {
        return (List) this.api.execute(this.restService.getRelatedActivities(Integer.valueOf(this.activityID), this.page, this.perPage));
    }
}
