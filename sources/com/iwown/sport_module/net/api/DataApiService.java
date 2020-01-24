package com.iwown.sport_module.net.api;

import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.fatigue.FatigueRetCode;
import com.iwown.data_link.fatigue.FatigueSend;
import com.iwown.data_link.heart.HeartHoursData;
import com.iwown.data_link.heart.HeartHoursNewDownCode;
import com.iwown.data_link.heart.HeartStatusData;
import com.iwown.data_link.heart.heart_sport.HeartDownCode;
import com.iwown.data_link.heart.heart_sport.HeartDownData1;
import com.iwown.data_link.sleep_data.SleepDownCode;
import com.iwown.data_link.sleep_data.SleepDownData1;
import com.iwown.data_link.sleep_data.SleepStatusData;
import com.iwown.data_link.sport_data.SportData;
import com.iwown.data_link.sport_data.SportDownCode;
import com.iwown.data_link.sport_data.gps.GPSStatData;
import com.iwown.data_link.walk_29_data.WalkingDownCode;
import com.iwown.data_link.weight.MacBandS2Bean;
import com.iwown.data_link.weight.ScaleDataResp;
import com.iwown.data_link.weight.WeightData;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.sport_module.gps.data.GpsUpTotal;
import com.iwown.sport_module.net.response.CheckAdCode;
import com.iwown.sport_module.net.response.GpsDownCode;
import com.iwown.sport_module.net.response.GpsRetCode;
import com.iwown.sport_module.net.response.MonthHas28DateCode;
import com.iwown.sport_module.net.response.UpSDFileCode;
import com.iwown.sport_module.pojo.active.WalkData;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DataApiService {
    @POST("cloudevice/bind/weightscale")
    Observable<RetCode> bindWifiScale(@Body MacBandS2Bean macBandS2Bean);

    @GET("advertise/startpage")
    Observable<CheckAdCode> checkAdRepo(@Query("region") String str);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String str, @Header("Accept-Encoding") String str2);

    @GET("data61_download_url")
    Observable<UpSDFileCode> get61FileUrl(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2);

    @GET("gnss_download_url")
    Observable<UpSDFileCode> get62FileUrl(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2);

    @GET("api/geocode/json")
    Observable<ResponseBody> getCity(@Query("latlng") String str, @Query("language") String str2, @Query("sensor") boolean z);

    @GET("sport/activity/hasdatadays")
    Observable<MonthHas28DateCode> getDateInMonthHas28(@Query("uid") long j, @Query("month") long j2, @Query("zone") int i);

    @GET("sport/fatigue/list")
    Observable<FatigueRetCode> getFatigue(@Query("uid") long j, @Query("pageSize") int i, @Query("benchmarkDate") String str);

    @GET("sport/gpssport/downloadinpage")
    Observable<GpsDownCode> getGpsPageRepo(@Query("uid") long j, @Query("pageIndex") int i, @Query("sportsType") int i2);

    @GET("sport/weight/download")
    Observable<ScaleDataResp> getScaleWeight(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("getweatherandroid")
    Observable<ResponseBody> getWeather(@Body RequestBody requestBody);

    @GET("weight/rawdata/v2")
    Observable<WifiScaleRWResp> getWifiScaleRWData(@Query("uid") long j, @Query("weightDate") long j2, @Query("scaleid") String str);

    @GET("sport/heartrate_segment/download")
    Observable<HeartDownCode> heartDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/heartrate/data_status")
    Observable<HeartStatusData> heartDownstatus(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/heartrateHours/download_new")
    Observable<HeartHoursNewDownCode> heartHoursDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("sport/heartrateHours/upload")
    Observable<RetCode> heartHoursUpRepo(@Query("uid") long j, @Body List<HeartHoursData> list);

    @POST("sport/heartrate_segment/upload")
    Observable<RetCode> heartUpRepo(@Query("uid") long j, @Body List<HeartDownData1> list);

    @POST("sport/fatigue/upload")
    Observable<RetCode> sendFatigue(@Body FatigueSend fatigueSend);

    @GET("sport/sleep/download")
    Observable<SleepDownCode> sleepDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/sleep/data_status")
    Observable<SleepStatusData> sleepDownstatus(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("sport/sleep/upload")
    Observable<RetCode> sleepUpRepo(@Query("uid") long j, @Body List<SleepDownData1> list);

    @GET("sport/sports/download")
    Observable<SportDownCode> sportDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/gpssport/downloadstat")
    Observable<GPSStatData> sportGPSStatDownRepo(@Query("uid") long j);

    @POST("sport/sports/upload")
    Observable<RetCode> sportUpRepo(@Query("uid") long j, @Body List<SportData> list);

    @POST("sport/gpssport/upload")
    Observable<RetCode> upGpsDetailRepo(@Query("uid") long j, @Body List<GpsUpTotal> list);

    @POST("upload_gps_data")
    @Multipart
    Observable<GpsRetCode> upPhoneGpsFile(@Query("uid") long j, @Part("file\"; filename=\"phone_gps.txt\"") RequestBody requestBody);

    @POST("fileservice/upload_gps_data")
    @Multipart
    Call<ResponseBody> uploadFile(@Query("uid") long j, @Part("file\"; filename=\"bles.txt\"") RequestBody requestBody);

    @GET("sport/walking/download")
    Observable<WalkingDownCode> walkDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("sport/walking/upload")
    Observable<RetCode> walkUpRepo(@Query("uid") long j, @Body List<WalkData> list);

    @POST("sport/weight/upload")
    Observable<RetCode> weightUpRepo(@Query("uid") long j, @Body List<WeightData> list);
}
