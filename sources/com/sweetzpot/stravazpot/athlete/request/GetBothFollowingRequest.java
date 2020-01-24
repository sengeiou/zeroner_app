package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.FriendAPI;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.rest.FriendRest;
import java.util.List;

public class GetBothFollowingRequest {
    private final FriendAPI api;
    private final int athleteID;
    private Integer page;
    private Integer perPage;
    private final FriendRest restService;

    public GetBothFollowingRequest(int athleteID2, FriendRest restService2, FriendAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetBothFollowingRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public GetBothFollowingRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Athlete> execute() {
        return (List) this.api.execute(this.restService.getBothFollowing(Integer.valueOf(this.athleteID), this.page, this.perPage));
    }
}
