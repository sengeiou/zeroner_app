package com.sweetzpot.stravazpot.upload.rest;

import com.sweetzpot.stravazpot.upload.model.UploadStatus;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UploadRest {
    @GET("uploads/{id}")
    Call<UploadStatus> checkUploadStatus(@Path("id") int i);

    @POST("uploads")
    @Multipart
    Call<UploadStatus> upload(@Part("activity_type") RequestBody requestBody, @Part("name") RequestBody requestBody2, @Part("description") RequestBody requestBody3, @Part("private") Integer num, @Part("trainer") Integer num2, @Part("commute") Integer num3, @Part("data_type") RequestBody requestBody4, @Part("external_id") RequestBody requestBody5, @Part MultipartBody.Part part);
}
