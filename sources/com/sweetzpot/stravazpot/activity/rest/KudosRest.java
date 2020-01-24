package com.sweetzpot.stravazpot.activity.rest;

import com.sweetzpot.stravazpot.athlete.model.Athlete;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KudosRest {
    @GET("activities/{id}/kudos")
    Call<List<Athlete>> getActivityKudos(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);
}
