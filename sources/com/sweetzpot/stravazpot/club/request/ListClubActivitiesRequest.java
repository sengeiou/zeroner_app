package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.rest.ClubRest;
import java.util.List;

public class ListClubActivitiesRequest {
    private final ClubAPI api;
    private Integer before;
    private final int clubID;
    private Integer page;
    private Integer perPage;
    private final ClubRest restService;

    public ListClubActivitiesRequest(int clubID2, ClubRest restService2, ClubAPI api2) {
        this.clubID = clubID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListClubActivitiesRequest before(int before2) {
        this.before = Integer.valueOf(before2);
        return this;
    }

    public ListClubActivitiesRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListClubActivitiesRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Activity> execute() {
        return (List) this.api.execute(this.restService.getClubActivities(Integer.valueOf(this.clubID), this.before, this.page, this.perPage));
    }
}
