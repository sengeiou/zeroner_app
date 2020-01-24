package com.iwown.my_module.network;

import com.iwown.my_module.model.request.UploadPhotoCode;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FileService {
    @POST("fileservice/upload_icon")
    @Multipart
    Call<UploadPhotoCode> uploadImage(@Query("uid") long j, @Part("file\"; filename=\"image.jpg\"") RequestBody requestBody);
}
