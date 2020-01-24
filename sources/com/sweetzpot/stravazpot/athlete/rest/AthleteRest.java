package com.sweetzpot.stravazpot.athlete.rest;

import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.athlete.model.Stats;
import com.sweetzpot.stravazpot.athlete.model.Zones;
import com.sweetzpot.stravazpot.common.model.Gender;
import com.sweetzpot.stravazpot.segment.model.SegmentEffort;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AthleteRest {
    @GET("athletes/{id}")
    Call<Athlete> getAthlete(@Path("id") Integer num);

    @GET("athletes/{id}/stats")
    Call<Stats> getAthleteStats(@Path("id") Integer num);

    @GET("athlete/zones")
    Call<Zones> getAthleteZones();

    @GET("athlete")
    Call<Athlete> getCurrentAthlete();

    @GET("athletes/{id}/koms")
    Call<List<SegmentEffort>> listAthleteKOMS(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @FormUrlEncoded
    @PUT("athlete")
    Call<Athlete> updateAthlete(@Field("city") String str, @Field("state") String str2, @Field("country") String str3, @Field("sex") Gender gender, @Field("weight") Float f);
}
