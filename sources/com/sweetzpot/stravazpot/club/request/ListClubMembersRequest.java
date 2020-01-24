package com.sweetzpot.stravazpot.club.request;

import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.club.api.ClubAPI;
import com.sweetzpot.stravazpot.club.rest.ClubRest;
import java.util.List;

public class ListClubMembersRequest {
    private final ClubAPI api;
    private final int clubID;
    private Integer page;
    private Integer perPage;
    private final ClubRest restService;

    public ListClubMembersRequest(int clubID2, ClubRest restService2, ClubAPI api2) {
        this.clubID = clubID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListClubMembersRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListClubMembersRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Athlete> execute() {
        return (List) this.api.execute(this.restService.getClubMembers(Integer.valueOf(this.clubID), this.page, this.perPage));
    }
}
