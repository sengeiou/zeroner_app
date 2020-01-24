package com.iwown.my_module.feedback.network;

import com.iwown.data_link.base.RetCode;
import com.iwown.my_module.feedback.network.response.AnswerCode;
import com.iwown.my_module.feedback.network.response.AnswerCustomCode;
import com.iwown.my_module.feedback.network.response.ImageCode;
import com.iwown.my_module.feedback.network.response.IvServiceCode;
import com.iwown.my_module.feedback.network.response.QuestionCode;
import com.iwown.my_module.feedback.network.response.SolveCode;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FeedbackService {
    @GET("chat")
    Call<AnswerCustomCode> getAnswerCustomRepo(@Query("sentence") String str, @Query("uid") long j, @Query("brand") String str2, @Query("contentType") int i);

    @GET("getquestionanswer")
    Call<AnswerCode> getAnswerRepo(@Query("answerCode") String str, @Query("uid") long j, @Query("brand") String str2);

    @GET("getservicereply")
    Call<IvServiceCode> getIvServiceRepo(@Query("uid") long j);

    @GET("getquestionlist")
    Call<QuestionCode> getQuestionRepo(@Query("deviceType") String str, @Query("brand") String str2, @Query("language") String str3);

    @GET("has/slove")
    Call<SolveCode> solvedRepo(@Query("chatRecordId") long j, @Query("uid") long j2, @Query("slove") int i);

    @GET("chatuserinfo")
    Call<RetCode> upUserInfoRepo(@Query("uid") String str, @Query("app") String str2, @Query("appVersion") String str3, @Query("phone") String str4, @Query("phoneVersion") String str5, @Query("phoneSystem") String str6, @Query("device") String str7, @Query("deviceVersion") String str8, @Query("country") String str9, @Query("city") String str10, @Query("brand") String str11);

    @POST("oaupload")
    @Multipart
    Call<ImageCode> uploadImageRepo(@Query("subpath") String str, @Query("filename") String str2, @Part("file\"; filename=\"image.jpg\"") RequestBody requestBody);

    @POST("oaupload")
    @Multipart
    Call<ImageCode> uploadImgVideo(@Query("subpath") String str, @Query("filename") String str2, @Part MultipartBody.Part part);
}
