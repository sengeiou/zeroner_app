package com.sweetzpot.stravazpot.authenticaton.request;

import com.sweetzpot.stravazpot.authenticaton.api.AuthenticationAPI;
import com.sweetzpot.stravazpot.authenticaton.model.AppCredentials;
import com.sweetzpot.stravazpot.authenticaton.model.LoginResult;
import com.sweetzpot.stravazpot.authenticaton.rest.AuthenticationRest;

public class AuthenticationRequest {
    private final AuthenticationAPI api;
    private final AppCredentials appCredentials;
    private String code;
    private final AuthenticationRest restService;

    public AuthenticationRequest(AppCredentials appCredentials2, AuthenticationRest restService2, AuthenticationAPI api2) {
        this.appCredentials = appCredentials2;
        this.restService = restService2;
        this.api = api2;
    }

    public AuthenticationRequest withCode(String code2) {
        this.code = code2;
        return this;
    }

    public LoginResult execute() {
        return (LoginResult) this.api.execute(this.restService.token(this.appCredentials.getClientID(), this.appCredentials.getClientSecret(), this.code));
    }
}
