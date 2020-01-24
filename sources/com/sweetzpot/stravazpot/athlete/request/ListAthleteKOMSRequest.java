package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.AthleteAPI;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;
import com.sweetzpot.stravazpot.segment.model.SegmentEffort;
import java.util.List;

public class ListAthleteKOMSRequest {
    private final AthleteAPI api;
    private final int athleteID;
    private Integer page;
    private Integer perPage;
    private final AthleteRest restService;

    public ListAthleteKOMSRequest(int athleteID2, AthleteRest restService2, AthleteAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListAthleteKOMSRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListAthleteKOMSRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<SegmentEffort> execute() {
        return (List) this.api.execute(this.restService.listAthleteKOMS(Integer.valueOf(this.athleteID), this.page, this.perPage));
    }
}
