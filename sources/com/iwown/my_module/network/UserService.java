package com.iwown.my_module.network;

import com.iwown.my_module.healthy.network.request.AccountProfile;
import com.iwown.my_module.healthy.network.request.GoalSend;
import com.iwown.my_module.healthy.network.request.WeightSend;
import com.iwown.my_module.healthy.network.response.HealthyGoalCode;
import com.iwown.my_module.model.request.ChangePasswordRequest;
import com.iwown.my_module.model.request.LoginRequest;
import com.iwown.my_module.model.request.RegisterRequest;
import com.iwown.my_module.model.request.RetrievePwdRequest;
import com.iwown.my_module.model.request.UploadAccountInfoRequest;
import com.iwown.my_module.model.response.Goal;
import com.iwown.my_module.model.response.LoginResponse;
import com.iwown.my_module.model.response.MarketInfoResponse;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.model.response.TestMessage;
import com.iwown.my_module.model.response.UserInfo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @POST("userservice/user/changepwd")
    Call<ReturnCode> changePwd(@Body ChangePasswordRequest changePasswordRequest);

    @GET("userservice/user/exist/email")
    Call<ReturnCode> checkEmailExist(@Query("email") String str);

    @GET("userservice/user/goal")
    Call<Goal> getGoal(@Query("uid") long j);

    @GET("userservice/user/fitnessgoal")
    Call<HealthyGoalCode> getHealthyGoal(@Query("uid") long j);

    @GET("wawaservice/market/info")
    Call<MarketInfoResponse> getMarketInfo(@Query("app") int i);

    @GET("userservice/user/temporarypwd")
    Call<ReturnCode> getbackPwdByEmail(@Query("email") String str);

    @GET("userservice/user/temporarypwdDrViva")
    Call<ReturnCode> getbackPwdByEmailDrViva(@Query("email") String str);

    @POST("userservice/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("userservice/user/detail/update")
    Call<ReturnCode> postHealthyProfile(@Body UserInfo userInfo);

    @POST("sportservice/sport/weight/data/upload")
    Call<ReturnCode> postMyWeightData(@Query("uid") long j, @Body List<WeightSend> list);

    @POST("userservice/user/account/detail/update")
    Call<ReturnCode> postNickNameProfile(@Body AccountProfile accountProfile);

    @POST("userservice/user/register/v2")
    Call<LoginResponse> register(@Body RegisterRequest registerRequest);

    @POST("userservice/user/getbackpwd")
    Call<ReturnCode> retrievePwd(@Body RetrievePwdRequest retrievePwdRequest);

    @GET("userservice/user/test")
    Call<TestMessage> test();

    @POST("userservice/user/account/update")
    Call<ReturnCode> updateAccountInfo(@Body UploadAccountInfoRequest uploadAccountInfoRequest);

    @POST("userservice/user/goal/update")
    Call<ReturnCode> updateGoal(@Body Goal goal);

    @POST("userservice/user/fitnessgoal/update")
    Call<ReturnCode> updateHealthyGoal(@Body GoalSend goalSend);

    @POST("userservice/user/update")
    Call<ReturnCode> updateUserInfo(@Body UserInfo userInfo);

    @GET("userservice/user/info")
    Call<UserInfo> userInfo(@Query("uid") long j);

    @GET("userservice/user/validate/phone")
    Call<ReturnCode> validatePhoneAndEmail(@Query("email") String str, @Query("phone") String str2);
}
