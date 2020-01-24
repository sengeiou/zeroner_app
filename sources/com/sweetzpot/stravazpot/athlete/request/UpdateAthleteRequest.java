package com.sweetzpot.stravazpot.athlete.request;

import com.sweetzpot.stravazpot.athlete.api.AthleteAPI;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.rest.AthleteRest;
import com.sweetzpot.stravazpot.common.model.Gender;

public class UpdateAthleteRequest {
    private final AthleteAPI api;
    private String city;
    private String country;
    private final AthleteRest restService;
    private Gender sex;
    private String state;
    private Float weight;

    public UpdateAthleteRequest(AthleteRest restService2, AthleteAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public UpdateAthleteRequest newCity(String city2) {
        this.city = city2;
        return this;
    }

    public UpdateAthleteRequest newState(String state2) {
        this.state = state2;
        return this;
    }

    public UpdateAthleteRequest newCountry(String country2) {
        this.country = country2;
        return this;
    }

    public UpdateAthleteRequest newSex(Gender sex2) {
        this.sex = sex2;
        return this;
    }

    public UpdateAthleteRequest newWeight(Float weight2) {
        this.weight = weight2;
        return this;
    }

    public Athlete execute() {
        return (Athlete) this.api.execute(this.restService.updateAthlete(this.city, this.state, this.country, this.sex, this.weight));
    }
}
