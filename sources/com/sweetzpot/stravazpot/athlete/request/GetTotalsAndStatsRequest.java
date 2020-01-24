package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.AthleteAPI;
import com.sweetzpot.stravazpot.athlete.model.Stats;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;

public class GetTotalsAndStatsRequest {
    private final AthleteAPI api;
    private final int athleteID;
    private final AthleteRest restService;

    public GetTotalsAndStatsRequest(int athleteID2, AthleteRest restService2, AthleteAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public Stats execute() {
        return (Stats) this.api.execute(this.restService.getAthleteStats(Integer.valueOf(this.athleteID)));
    }
}
