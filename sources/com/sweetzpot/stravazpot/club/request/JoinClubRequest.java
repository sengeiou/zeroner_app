package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.model.JoinResult;
import com.sweetzpot.stravazpot.club.rest.ClubRest;

public class JoinClubRequest {
    private final ClubAPI api;
    private final int clubID;
    private final ClubRest restService;

    public JoinClubRequest(int clubID2, ClubRest restService2, ClubAPI api2) {
        this.clubID = clubID2;
        this.restService = restService2;
        this.api = api2;
    }

    public JoinResult execute() {
        return (JoinResult) this.api.execute(this.restService.joinClub(Integer.valueOf(this.clubID)));
    }
}
