package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.model.Club;
import com.sweetzpot.stravazpot.club.rest.ClubRest;

public class GetClubRequest {
    private final ClubAPI api;
    private final int clubID;
    private final ClubRest restService;

    public GetClubRequest(int clubID2, ClubRest restService2, ClubAPI api2) {
        this.clubID = clubID2;
        this.restService = restService2;
        this.api = api2;
    }

    public Club execute() {
        return (Club) this.api.execute(this.restService.getClub(Integer.valueOf(this.clubID)));
    }
}
