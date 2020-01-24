package com.sweetzpot.stravazpot.gear.request;

import com.sweetzpot.stravazpot.gear.api.GearAPI;
import com.sweetzpot.stravazpot.gear.model.Gear;
import com.sweetzpot.stravazpot.gear.rest.GearRest;

public class GetGearRequest {
    private final GearAPI api;
    private final String gearID;
    private final GearRest restService;

    public GetGearRequest(String gearID2, GearRest restService2, GearAPI api2) {
        this.gearID = gearID2;
        this.restService = restService2;
        this.api = api2;
    }

    public Gear execute() {
        return (Gear) this.api.execute(this.restService.getGear(this.gearID));
    }
}
