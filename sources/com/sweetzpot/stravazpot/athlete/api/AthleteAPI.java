package com.sweetzpot.stravazpot.athlete.api;

import com.sweetzpot.stravazpot.athlete.request.AthleteRequest;
import com.sweetzpot.stravazpot.athlete.request.CurrentAthleteRequest;
import com.sweetzpot.stravazpot.athlete.request.GetTotalsAndStatsRequest;
import com.sweetzpot.stravazpot.athlete.request.GetZonesRequest;
import com.sweetzpot.stravazpot.athlete.request.ListAthleteKOMSRequest;
import com.sweetzpot.stravazpot.athlete.request.UpdateAthleteRequest;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class AthleteAPI extends StravaAPI {
    public AthleteAPI(StravaConfig config) {
        super(config);
    }

    public CurrentAthleteRequest retrieveCurrentAthlete() {
        return new CurrentAthleteRequest((AthleteRest) getAPI(AthleteRest.class), this);
    }

    public AthleteRequest retrieveAthlete(int athleteID) {
        return new AthleteRequest(athleteID, (AthleteRest) getAPI(AthleteRest.class), this);
    }

    public UpdateAthleteRequest updateAthlete() {
        return new UpdateAthleteRequest((AthleteRest) getAPI(AthleteRest.class), this);
    }

    public GetZonesRequest getAthleteZones() {
        return new GetZonesRequest((AthleteRest) getAPI(AthleteRest.class), this);
    }

    public GetTotalsAndStatsRequest getAthleteTotalsAndStats(int athleteID) {
        return new GetTotalsAndStatsRequest(athleteID, (AthleteRest) getAPI(AthleteRest.class), this);
    }

    public ListAthleteKOMSRequest listAthleteKOMS(int athleteID) {
        return new ListAthleteKOMSRequest(athleteID, (AthleteRest) getAPI(AthleteRest.class), this);
    }
}
