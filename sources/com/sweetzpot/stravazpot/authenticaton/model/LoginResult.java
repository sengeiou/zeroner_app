package com.sweetzpot.stravazpot.authenticaton.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.athlete.model.Athlete;

public class LoginResult {
    @SerializedName("athlete")
    private Athlete athlete;
    @SerializedName("access_token")
    private Token token;

    public Token getToken() {
        return this.token;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }
}
