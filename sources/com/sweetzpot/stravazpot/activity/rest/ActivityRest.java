package com.sweetzpot.stravazpot.activity.rest;

import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.activity.model.ActivityLap;
import com.sweetzpot.stravazpot.activity.model.ActivityType;
import com.sweetzpot.stravazpot.activity.model.ActivityZone;
import com.sweetzpot.stravazpot.common.model.Distance;
import com.sweetzpot.stravazpot.common.model.Time;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ActivityRest {
    @FormUrlEncoded
    @POST("activities")
    Call<Activity> createActivity(@Field("name") String str, @Field("type") ActivityType activityType, @Field("start_date_local") String str2, @Field("elapsed_time") Time time, @Field("description") String str3, @Field("distance") Distance distance, @Field("private") Integer num, @Field("trainer") Integer num2, @Field("commute") Integer num3);

    @DELETE("activities/{id}")
    Call<Void> deleteActivity(@Path("id") Integer num);

    @GET("activities/{id}")
    Call<Activity> getActivity(@Path("id") Integer num, @Query("include_all_efforts") Boolean bool);

    @GET("activities/{id}/laps")
    Call<List<ActivityLap>> getActivityLaps(@Path("id") Integer num);

    @GET("activities/{id}/zones")
    Call<List<ActivityZone>> getActivityZones(@Path("id") Integer num);

    @GET("activities/following")
    Call<List<Activity>> getFriendsActivities(@Query("before") Time time, @Query("page") Integer num, @Query("per_page") Integer num2);

    @GET("athlete/activities")
    Call<List<Activity>> getMyActivities(@Query("before") Time time, @Query("after") Time time2, @Query("page") Integer num, @Query("per_page") Integer num2);

    @GET("activities/{id}/related")
    Call<List<Activity>> getRelatedActivities(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @FormUrlEncoded
    @PUT("activities/{id}")
    Call<Activity> updateActivity(@Path("id") Integer num, @Field("name") String str, @Field("type") ActivityType activityType, @Field("private") Boolean bool, @Field("commute") Boolean bool2, @Field("trainer") Boolean bool3, @Field("gear_id") String str2, @Field("description") String str3);
}
