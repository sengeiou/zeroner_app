package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.model.Event;
import com.sweetzpot.stravazpot.club.rest.ClubRest;
import java.util.List;

public class ListClubGroupEventsRequest {
    private final ClubAPI api;
    private final int clubID;
    private final ClubRest restService;

    public ListClubGroupEventsRequest(int clubID2, ClubRest restService2, ClubAPI api2) {
        this.clubID = clubID2;
        this.restService = restService2;
        this.api = api2;
    }

    public List<Event> execute() {
        return (List) this.api.execute(this.restService.getClubGroupEvents(Integer.valueOf(this.clubID)));
    }
}
