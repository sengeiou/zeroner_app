package com.iwown.my_module.healthy.network;

import com.iwown.data_link.base.RetCode;
import com.iwown.my_module.healthy.network.request.PhoneFindPasswordSend;
import com.iwown.my_module.healthy.network.request.PhoneSend;
import com.iwown.my_module.healthy.network.request.QQWxSend;
import com.iwown.my_module.healthy.network.request.WeightSend;
import com.iwown.my_module.healthy.network.response.AllUserInfoRetCode;
import com.iwown.my_module.healthy.network.response.HealthyGoalCode;
import com.iwown.my_module.healthy.network.response.LoginCode;
import com.iwown.my_module.healthy.network.response.RegisterCode;
import com.iwown.my_module.model.request.ChangePasswordRequest;
import com.iwown.my_module.model.response.ReturnCode;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {
    @POST("authservice/auth/changepwd")
    Call<ReturnCode> changePwd(@Body ChangePasswordRequest changePasswordRequest);

    @GET("authservice/auth/exist/email")
    Call<RetCode> emailExists(@Query("email") String str);

    @GET("userservice/user/temporary/full")
    Call<AllUserInfoRetCode> getAllUserInfo(@Query("uid") long j);

    @GET("userservice/user/fitnessgoal")
    Call<HealthyGoalCode> getHealthyGoal(@Query("uid") long j);

    @GET("authservice/auth/exist/phone")
    Call<RetCode> phoneExists(@Query("phone") long j);

    @GET("authservice/auth/temporarypwd/{email}")
    @Headers({"Content-Type: application/json"})
    Call<RetCode> postEmailFindPassword(@Path("email") String str);

    @POST("authservice/auth/login")
    Call<LoginCode> postLogin(@Body PhoneSend phoneSend);

    @POST("authservice/auth/login/{qq}")
    Call<LoginCode> postLoginQQWx(@Path("qq") String str, @Body QQWxSend qQWxSend);

    @POST("sport/weight/data/upload")
    Call<RetCode> postMyWeightData(@Query("uid") long j, @Body List<WeightSend> list);

    @POST("authservice/auth/getbackpwd")
    Call<RetCode> postPhoneFindPassword(@Body PhoneFindPasswordSend phoneFindPasswordSend);

    @POST("authservice/auth/register")
    Call<RegisterCode> postRegister(@Body PhoneSend phoneSend);
}
