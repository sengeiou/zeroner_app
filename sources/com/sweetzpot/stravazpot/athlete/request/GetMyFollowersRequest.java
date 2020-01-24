package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.FriendAPI;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.rest.FriendRest;
import java.util.List;

public class GetMyFollowersRequest {
    private final FriendAPI api;
    private Integer page;
    private Integer perPage;
    private final FriendRest restService;

    public GetMyFollowersRequest(FriendRest restService2, FriendAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public GetMyFollowersRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public GetMyFollowersRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Athlete> execute() {
        return (List) this.api.execute(this.restService.getMyFollowers(this.page, this.perPage));
    }
}
