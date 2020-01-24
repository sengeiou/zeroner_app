package com.sweetzpot.stravazpot.activity.api;

import com.sweetzpot.stravazpot.activity.request.CreateActivityRequest;
import com.sweetzpot.stravazpot.activity.request.DeleteActivityRequest;
import com.sweetzpot.stravazpot.activity.request.GetActivityRequest;
import com.sweetzpot.stravazpot.activity.request.ListActivityLapsRequest;
import com.sweetzpot.stravazpot.activity.request.ListActivityZonesRequest;
import com.sweetzpot.stravazpot.activity.request.ListFriendActivitiesRequest;
import com.sweetzpot.stravazpot.activity.request.ListMyActivitiesRequest;
import com.sweetzpot.stravazpot.activity.request.ListRelatedActivitiesRequest;
import com.sweetzpot.stravazpot.activity.request.UpdateActivityRequest;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class ActivityAPI extends StravaAPI {
    public ActivityAPI(StravaConfig config) {
        super(config);
    }

    public CreateActivityRequest createActivity(String name) {
        return new CreateActivityRequest(name, (ActivityRest) getAPI(ActivityRest.class), this);
    }

    public GetActivityRequest getActivity(int activityID) {
        return new GetActivityRequest(activityID, (ActivityRest) getAPI(ActivityRest.class), this);
    }

    public UpdateActivityRequest updateActivity(int activityID) {
        return new UpdateActivityRequest(activityID, (ActivityRest) getAPI(ActivityRest.class), this);
    }

    public DeleteActivityRequest deleteActivity(int activityID) {
        return new DeleteActivityRequest(activityID, (ActivityRest) getAPI(ActivityRest.class), this);
    }

    public ListMyActivitiesRequest listMyActivities() {
        return new ListMyActivitiesRequest((ActivityRest) getAPI(ActivityRest.class), this);
    }

    public ListFriendActivitiesRequest listFriendActivities() {
        return new ListFriendActivitiesRequest((ActivityRest) getAPI(ActivityRest.class), this);
    }

    public ListRelatedActivitiesRequest listRelatedActivities(int activityID) {
        return new ListRelatedActivitiesRequest(activityID, (ActivityRest) getAPI(ActivityRest.class), this);
    }

    public ListActivityZonesRequest listActivityZones(int activityID) {
        return new ListActivityZonesRequest(activityID, (ActivityRest) getAPI(ActivityRest.class), this);
    }

    public ListActivityLapsRequest listActivityLaps(int activityID) {
        return new ListActivityLapsRequest(activityID, (ActivityRest) getAPI(ActivityRest.class), this);
    }
}
