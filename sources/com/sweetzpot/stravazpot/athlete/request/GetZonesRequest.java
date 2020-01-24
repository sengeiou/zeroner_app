package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.AthleteAPI;
import com.sweetzpot.stravazpot.athlete.model.Zones;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;

public class GetZonesRequest {
    private final AthleteAPI api;
    private final AthleteRest restService;

    public GetZonesRequest(AthleteRest restService2, AthleteAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public Zones execute() {
        return (Zones) this.api.execute(this.restService.getAthleteZones());
    }
}
