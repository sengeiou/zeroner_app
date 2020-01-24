package com.sweetzpot.stravazpot.authenticaton.rest;

import com.sweetzpot.stravazpot.authenticaton.model.LoginResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticationRest {
    @POST("/oauth/deauthorize")
    Call<Void> deauthorize();

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<LoginResult> token(@Field("client_id") int i, @Field("client_secret") String str, @Field("code") String str2);
}
