package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.AthleteAPI;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;

public class AthleteRequest {
    private final AthleteAPI api;
    private final int athleteID;
    private final AthleteRest restService;

    public AthleteRequest(int athleteID2, AthleteRest restService2, AthleteAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public Athlete execute() {
        return (Athlete) this.api.execute(this.restService.getAthlete(Integer.valueOf(this.athleteID)));
    }
}
