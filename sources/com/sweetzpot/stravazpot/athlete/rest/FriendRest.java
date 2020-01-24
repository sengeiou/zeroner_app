package com.sweetzpot.stravazpot.athlete.rest;

import com.sweetzpot.stravazpot.athlete.model.Athlete;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FriendRest {
    @GET("athletes/{id}/both-following")
    Call<List<Athlete>> getBothFollowing(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @GET("athletes/{id}/followers")
    Call<List<Athlete>> getFollowers(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @GET("athletes/{id}/friends")
    Call<List<Athlete>> getFriends(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @GET("athlete/followers")
    Call<List<Athlete>> getMyFollowers(@Query("page") Integer num, @Query("per_page") Integer num2);

    @GET("athlete/friends")
    Call<List<Athlete>> getMyFriends(@Query("page") Integer num, @Query("per_page") Integer num2);
}
