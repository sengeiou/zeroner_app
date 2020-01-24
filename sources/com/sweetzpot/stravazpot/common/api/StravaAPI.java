package com.sweetzpot.stravazpot.common.api;

import com.sweetzpot.stravazpot.common.api.exception.StravaAPIException;
import com.sweetzpot.stravazpot.common.api.exception.StravaUnauthorizedException;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public abstract class StravaAPI {
    private static final int UNAUTHORIZED_CODE = 401;
    private final Config config;

    public StravaAPI(Config config2) {
        this.config = config2;
    }

    /* access modifiers changed from: protected */
    public <T> T getAPI(Class<T> apiRest) {
        return this.config.getRetrofit().create(apiRest);
    }

    public <T> T execute(Call<T> call) throws StravaAPIException, StravaUnauthorizedException {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            if (response.code() == 401) {
                throw new StravaUnauthorizedException();
            }
            throw new StravaAPIException("Response was not successful");
        } catch (IOException e) {
            throw new StravaAPIException("A network error happened contacting Strava API", e);
        }
    }
}
