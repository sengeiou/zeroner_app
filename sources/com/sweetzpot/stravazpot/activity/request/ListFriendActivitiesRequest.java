package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.ActivityAPI;
import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.activity.rest.ActivityRest;
import com.sweetzpot.stravazpot.common.model.Time;
import java.util.List;

public class ListFriendActivitiesRequest {
    private final ActivityAPI api;
    private Time before;
    private Integer page;
    private Integer perPage;
    private final ActivityRest restService;

    public ListFriendActivitiesRequest(ActivityRest restService2, ActivityAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public ListFriendActivitiesRequest before(Time before2) {
        this.before = before2;
        return this;
    }

    public ListFriendActivitiesRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListFriendActivitiesRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Activity> execute() {
        return (List) this.api.execute(this.restService.getFriendsActivities(this.before, this.page, this.perPage));
    }
}
