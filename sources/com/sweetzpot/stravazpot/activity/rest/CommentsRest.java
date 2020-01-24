package com.sweetzpot.stravazpot.activity.rest;

import com.sweetzpot.stravazpot.activity.model.Comment;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentsRest {
    @GET("activities/{id}/comments")
    Call<List<Comment>> getActivityComments(@Path("id") Integer num, @Query("page") Integer num2, @Query("per_page") Integer num3);
}
