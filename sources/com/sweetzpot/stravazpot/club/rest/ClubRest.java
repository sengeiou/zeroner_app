package com.sweetzpot.stravazpot.club.rest;

import com.sweetzpot.stravazpot.activity.model.Activity;
import com.sweetzpot.stravazpot.athlete.model.Athlete;
import com.sweetzpot.stravazpot.club.model.Announcement;
import com.sweetzpot.stravazpot.club.model.Club;
import com.sweetzpot.stravazpot.club.model.Event;
import com.sweetzpot.stravazpot.club.model.JoinResult;
import com.sweetzpot.stravazpot.club.model.LeaveResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClubRest {
    @GET("clubs/{id}")
    Call<Club> getClub(@Path("id") Integer num);

    @GET("clubs/{id}/activities")
    Call<List<Activity>> getClubActivities(@Path("id") Integer num, @Query("before") Integer num2, @Query("page") Integer num3, @Query("per_page") Integer num4);

    @GET("clubs/{id}/admins")
    Call<List<Athlete>> getClubAdmins(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @GET("clubs/{id}/announcements")
    Call<List<Announcement>> getClubAnnouncements(@Path("id") Integer num);

    @GET("clubs/{id}/group_events")
    Call<List<Event>> getClubGroupEvents(@Path("id") Integer num);

    @GET("clubs/{id}/members")
    Call<List<Athlete>> getClubMembers(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);

    @GET("athlete/clubs")
    Call<List<Club>> getMyClubs();

    @POST("clubs/{id}/join")
    Call<JoinResult> joinClub(@Path("id") Integer num);

    @POST("clubs/{id}/leave")
    Call<LeaveResult> leaveClub(@Path("id") Integer num);
}
