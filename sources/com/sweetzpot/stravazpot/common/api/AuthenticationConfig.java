package com.sweetzpot.stravazpot.common.api;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

public class AuthenticationConfig extends Config {

    public static class Builder {
        private static final String STRAVA_AUTHORIZATION_URL = "https://www.strava.com";
        private String baseURL = STRAVA_AUTHORIZATION_URL;

        /* renamed from: debug reason: collision with root package name */
        private boolean f409debug = false;

        public Builder debug() {
            this.f409debug = true;
            return this;
        }

        public Builder baseURL(String baseURL2) {
            this.baseURL = baseURL2;
            return this;
        }

        public AuthenticationConfig build() {
            return new AuthenticationConfig(Config.createRetrofit(this.f409debug, this.baseURL, new Interceptor[0]));
        }
    }

    public static Builder create() {
        return new Builder();
    }

    public AuthenticationConfig(Retrofit retrofit) {
        super(retrofit);
    }
}
