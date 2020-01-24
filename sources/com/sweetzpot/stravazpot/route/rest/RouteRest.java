package com.sweetzpot.stravazpot.route.rest;

import com.sweetzpot.stravazpot.route.model.Route;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RouteRest {
    @GET("athletes/{id}/routes")
    Call<List<Route>> getAthleteRoutes(@Path("id") Integer num);

    @GET("routes/{id}")
    Call<Route> getRoute(@Path("id") Integer num);
}
