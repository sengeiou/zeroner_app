package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.KudosAPI;
import com.sweetzpot.stravazpot.activity.rest.KudosRest;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import java.util.List;

public class ListActivityKudoersRequest {
    private final int activityID;
    private final KudosAPI api;
    private Integer page;
    private Integer perPage;
    private final KudosRest restService;

    public ListActivityKudoersRequest(int activityID2, KudosRest restService2, KudosAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListActivityKudoersRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListActivityKudoersRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Athlete> execute() {
        return (List) this.api.execute(this.restService.getActivityKudos(Integer.valueOf(this.activityID), this.page, this.perPage));
    }
}
