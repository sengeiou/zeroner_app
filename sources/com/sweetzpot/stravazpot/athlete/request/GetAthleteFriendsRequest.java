package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.FriendAPI;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.rest.FriendRest;
import java.util.List;

public class GetAthleteFriendsRequest {
    private final FriendAPI api;
    private final int athleteID;
    private Integer page;
    private Integer perPage;
    private final FriendRest restService;

    public GetAthleteFriendsRequest(int athleteID2, FriendRest restService2, FriendAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetAthleteFriendsRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public GetAthleteFriendsRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Athlete> execute() {
        return (List) this.api.execute(this.restService.getFriends(Integer.valueOf(this.athleteID), this.page, this.perPage));
    }
}
