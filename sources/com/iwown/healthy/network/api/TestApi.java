package com.iwown.healthy.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface TestApi {
    @GET("/date_str.html")
    Observable<ResponseBody> getBaidu();
}
