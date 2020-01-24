package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.AthleteAPI;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;

public class CurrentAthleteRequest {
    private final AthleteAPI api;
    private final AthleteRest restService;

    public CurrentAthleteRequest(AthleteRest restService2, AthleteAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public Athlete execute() {
        return (Athlete) this.api.execute(this.restService.getCurrentAthlete());
    }
}
