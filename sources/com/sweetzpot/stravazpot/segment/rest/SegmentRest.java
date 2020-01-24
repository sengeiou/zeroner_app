package com.sweetzpot.stravazpot.segment.rest;

import com.sweetzpot.stravazpot.common.model.Gender;
import com.sweetzpot.stravazpot.segment.model.AgeGroup;
import com.sweetzpot.stravazpot.segment.model.DateRange;
import com.sweetzpot.stravazpot.segment.model.ExploreResult;
import com.sweetzpot.stravazpot.segment.model.ExploreType;
import com.sweetzpot.stravazpot.segment.model.Leaderboard;
import com.sweetzpot.stravazpot.segment.model.Segment;
import com.sweetzpot.stravazpot.segment.model.SegmentEffort;
import com.sweetzpot.stravazpot.segment.model.WeightClass;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SegmentRest {
    @GET("segments/explore")
    Call<ExploreResult> exploreSegments(@Query("bounds") String str, @Query("activity_type") ExploreType exploreType, @Query("min_cat") Integer num, @Query("max_cat") Integer num2);

    @GET("athletes/{id}/segments/starred")
    Call<List<Segment>> getAthleteStarredSegments(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @GET("segments/starred")
    Call<List<Segment>> getMyStarredSegments(@Query("page") Integer num, @Query("per_page") Integer num2);

    @GET("segments/{id}")
    Call<Segment> getSegment(@Path("id") Integer num);

    @GET("segments/{id}/all_efforts")
    Call<List<SegmentEffort>> getSegmentEfforts(@Path("id") Integer num, @Query("athlete_id") Integer num2, @Query("start_date_local") String str, @Query("end_date_local") String str2, @Query("page") Integer num3, @Query("per_page") Integer num4);

    @GET("segments/{id}/leaderboard")
    Call<Leaderboard> getSegmentLeaderboard(@Path("id") Integer num, @Query("gender") Gender gender, @Query("age_group") AgeGroup ageGroup, @Query("weight_class") WeightClass weightClass, @Query("following") Boolean bool, @Query("club_id") Integer num2, @Query("date_range") DateRange dateRange, @Query("context_entries") Integer num3, @Query("page") Integer num4, @Query("per_page") Integer num5);

    @FormUrlEncoded
    @PUT("segments/{id}/starred")
    Call<Segment> starSegment(@Path("id") Integer num, @Field("starred") Boolean bool);
}
