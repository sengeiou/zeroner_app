package com.sweetzpot.stravazpot.common.api;

import com.sweetzpot.stravazpot.authenticaton.model.Token;
import retrofit2.Retrofit;

public class StravaConfig extends Config {

    public static class Builder {
        private static final String STRAVA_BASE_URL = "https://www.strava.com/api/v3/";
        private String baseURL = STRAVA_BASE_URL;

        /* renamed from: debug reason: collision with root package name */
        private boolean f410debug = false;
        private String token;

        public Builder(String token2) {
            this.token = token2;
        }

        public Builder debug() {
            this.f410debug = true;
            return this;
        }

        public Builder baseURL(String baseURL2) {
            this.baseURL = baseURL2;
            return this;
        }

        public StravaConfig build() {
            return new StravaConfig(Config.createRetrofit(this.f410debug, this.baseURL, new AuthorizationInterceptor(this.token)));
        }
    }

    public StravaConfig(Retrofit retrofit) {
        super(retrofit);
    }

    public static Builder withToken(String token) {
        return new Builder(token);
    }

    public static Builder withToken(Token token) {
        return withToken(token.toString());
    }
}
