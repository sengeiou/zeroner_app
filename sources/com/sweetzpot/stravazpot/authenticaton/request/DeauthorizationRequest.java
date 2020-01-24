package com.sweetzpot.stravazpot.authenticaton.request;

import com.sweetzpot.stravazpot.authenticaton.api.AuthenticationAPI;
import com.sweetzpot.stravazpot.authenticaton.rest.AuthenticationRest;

public class DeauthorizationRequest {
    private final AuthenticationAPI api;
    private final AuthenticationRest restService;

    public DeauthorizationRequest(AuthenticationRest restService2, AuthenticationAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public Void execute() {
        return (Void) this.api.execute(this.restService.deauthorize());
    }
}
