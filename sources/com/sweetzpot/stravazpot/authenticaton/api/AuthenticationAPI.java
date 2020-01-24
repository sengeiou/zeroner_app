package com.sweetzpot.stravazpot.authenticaton.api;

import com.sweetzpot.stravazpot.authenticaton.model.AppCredentials;
import com.sweetzpot.stravazpot.authenticaton.request.AuthenticationRequest;
import com.sweetzpot.stravazpot.authenticaton.request.DeauthorizationRequest;
import com.sweetzpot.stravazpot.authenticaton.rest.AuthenticationRest;
import com.sweetzpot.stravazpot.common.api.Config;
import com.sweetzpot.stravazpot.common.api.StravaAPI;

public class AuthenticationAPI extends StravaAPI {
    public AuthenticationAPI(Config config) {
        super(config);
    }

    public AuthenticationRequest getTokenForApp(AppCredentials appCredentials) {
        return new AuthenticationRequest(appCredentials, (AuthenticationRest) getAPI(AuthenticationRest.class), this);
    }

    public DeauthorizationRequest deauthorize() {
        return new DeauthorizationRequest((AuthenticationRest) getAPI(AuthenticationRest.class), this);
    }
}
