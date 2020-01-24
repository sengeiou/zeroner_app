package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.model.LeaveResult;
import com.sweetzpot.stravazpot.club.rest.ClubRest;

public class LeaveClubRequest {
    private final ClubAPI api;
    private final int clubID;
    private final ClubRest restService;

    public LeaveClubRequest(int clubID2, ClubRest restService2, ClubAPI api2) {
        this.clubID = clubID2;
        this.restService = restService2;
        this.api = api2;
    }

    public LeaveResult execute() {
        return (LeaveResult) this.api.execute(this.restService.leaveClub(Integer.valueOf(this.clubID)));
    }
}
