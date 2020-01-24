package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.model.Club;
import com.sweetzpot.stravazpot.club.rest.ClubRest;
import java.util.List;

public class ListMyClubsRequest {
    private final ClubAPI api;
    private final ClubRest restService;

    public ListMyClubsRequest(ClubRest restService2, ClubAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public List<Club> execute() {
        return (List) this.api.execute(this.restService.getMyClubs());
    }
}
