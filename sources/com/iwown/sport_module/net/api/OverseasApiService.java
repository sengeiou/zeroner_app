package com.iwown.sport_module.net.api;

import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.blood.BloodDataUpload;
import com.iwown.data_link.blood.BpDownData1;
import com.iwown.data_link.blood.bpCoverageDown;
import com.iwown.data_link.ecg.EcgDataAiResult;
import com.iwown.data_link.ecg.EcgDataNoteNet;
import com.iwown.data_link.ecg.EcgDownList;
import com.iwown.data_link.ecg.EcgHasDataNet;
import com.iwown.data_link.ecg.EcgUploadList;
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
import com.iwown.data_link.sport_data.ReturnCode;
import com.iwown.data_link.sport_data.Sport28Code;
import com.iwown.data_link.sport_data.SportBallCode;
import com.iwown.data_link.sport_data.SportData;
import com.iwown.data_link.sport_data.SportDownCode;
import com.iwown.data_link.sport_data.SportGpsCode;
import com.iwown.data_link.sport_data.SportOtherCode;
import com.iwown.data_link.sport_data.SportSwimCode;
import com.iwown.data_link.sport_data.gps.GPSStatData;
import com.iwown.data_link.walk_29_data.WalkingDownCode;
import com.iwown.data_link.weight.MacBandS2Bean;
import com.iwown.data_link.weight.ScaleDataResp;
import com.iwown.data_link.weight.WeightData;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.sport_module.gps.data.GpsUpTotal;
import com.iwown.sport_module.net.response.AllSportBallCode;
import com.iwown.sport_module.net.response.AllSportGpsCode;
import com.iwown.sport_module.net.response.AllSportOtherCode;
import com.iwown.sport_module.net.response.AllSportSwimCode;
import com.iwown.sport_module.net.response.CheckAdCode;
import com.iwown.sport_module.net.response.DevSupportByNameCode;
import com.iwown.sport_module.net.response.GpsDownCode;
import com.iwown.sport_module.net.response.GpsRetCode;
import com.iwown.sport_module.net.response.MonthHas28DateCode;
import com.iwown.sport_module.net.response.Sport28MonthCode;
import com.iwown.sport_module.net.response.UpSDFileCode;
import com.iwown.sport_module.net.response.Weather24h0verseasRsp;
import com.iwown.sport_module.net.send.Sport28Send;
import com.iwown.sport_module.pojo.active.WalkData;
import com.iwown.sport_module.pojo.active.WalkHealthyData;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
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

public interface OverseasApiService {
    @POST("cloudevice/bind/weightscale")
    Observable<RetCode> bindWifiScale(@Body MacBandS2Bean macBandS2Bean);

    @GET("advertise/startpage")
    Observable<CheckAdCode> checkAdRepo(@Query("region") String str);

    @GET("sport/ecg/download")
    Observable<EcgDownList> downEcgData(@Query("uid") long j, @Query("year") String str, @Query("month") String str2, @Query("day") String str3);

    @GET("sport/ecg/hasdatabymonth")
    Observable<EcgHasDataNet> downEcgHasData(@Query("uid") long j, @Query("year") String str, @Query("month") String str2);

    @GET("sport/segment/coverage")
    Observable<Sport28MonthCode> downSport28HasData(@Query("uid") long j, @Query("year") int i, @Query("month") int i2);

    @GET("sport/ball/stat")
    Observable<AllSportBallCode> downloadAllSportBall(@Query("uid") long j);

    @GET("sport/gps/stat")
    Observable<AllSportGpsCode> downloadAllSportGps(@Query("uid") long j);

    @GET("sport/other/stat")
    Observable<AllSportOtherCode> downloadAllSportOther(@Query("uid") long j);

    @GET("sport/swim/stat")
    Observable<AllSportSwimCode> downloadAllSportSwim(@Query("uid") long j);

    @GET("health/bloodpressure/coverage")
    Call<bpCoverageDown> downloadBPcoverage(@Query("uid") long j, @Query("year") int i, @Query("month") int i2);

    @GET("health/bloodpressure/download/range")
    Call<BpDownData1> downloadBlood(@Query("uid") long j, @Query("start_time") String str, @Query("end_time") String str2);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String str, @Header("Accept-Encoding") String str2);

    @GET("sport/ball/download/page/type")
    Observable<SportBallCode> downloadPageSportBall(@Query("uid") long j, @Query("min_start_time") String str, @Query("size") int i);

    @GET("sport/gps/download/page/type")
    Observable<SportGpsCode> downloadPageSportGps(@Query("uid") long j, @Query("sport_type") int i, @Query("min_start_time") String str, @Query("size") int i2);

    @GET("sport/other/download/page/type")
    Observable<SportOtherCode> downloadPageSportOther(@Query("uid") long j, @Query("min_start_time") String str, @Query("size") int i);

    @GET("sport/swim/downloadbysize")
    Observable<SportSwimCode> downloadPageSportSwim(@Query("uid") long j, @Query("min_start_time") String str, @Query("size") int i);

    @GET("sport/segment/download/range")
    Observable<Sport28Code> downloadSport28(@Query("uid") long j, @Query("start_time") String str, @Query("end_time") String str2);

    @GET("data61_download_url")
    Observable<UpSDFileCode> get61FileUrl(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2);

    @GET("gnss_download_url")
    Observable<UpSDFileCode> get62FileUrl(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2);

    @GET("api/geocode/json")
    Observable<ResponseBody> getCity(@Query("latlng") String str, @Query("language") String str2, @Query("sensor") boolean z, @Query("key") String str3);

    @GET("sport/activity/hasdatadays")
    Observable<MonthHas28DateCode> getDateInMonthHas28(@Query("uid") long j, @Query("month") long j2, @Query("zone") int i);

    @GET("sport/fatigue/list")
    Observable<FatigueRetCode> getFatigue(@Query("uid") long j, @Query("pageSize") int i, @Query("benchmarkDate") String str);

    @GET("sport/gpssport/downloadinpage")
    Observable<GpsDownCode> getGpsPageRepo(@Query("uid") long j, @Query("pageIndex") int i, @Query("sportsType") int i2);

    @GET("sport/weight/download")
    Observable<ScaleDataResp> getScaleWeight(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("device/getSupports")
    Observable<DevSupportByNameCode> getSupportsByName();

    @POST("getweatherandroid")
    Observable<ResponseBody> getWeather(@Body RequestBody requestBody);

    @GET("accu/getweatherandroid")
    Observable<Weather24h0verseasRsp> getWeather_24h(@Query("uid") long j, @Query("lat") Object obj, @Query("lon") Object obj2, @Query("city") String str, @Query("key") String str2, @Query("timestamp") String str3, @Query("appversion") String str4, @Query("country") String str5);

    @GET("weight/rawdata/v2")
    Observable<WifiScaleRWResp> getWifiScaleRWData(@Query("uid") long j, @Query("weightDate") long j2, @Query("scaleid") String str);

    @GET("sport/heartrate/download")
    Observable<HeartDownCode> heartDownHealthyRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/heartrate_segment/download")
    Observable<HeartDownCode> heartDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/heartrate/data_status")
    Observable<HeartStatusData> heartDownstatus(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @GET("sport/heartrateHours/download_new")
    Observable<HeartHoursNewDownCode> heartHoursDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("sport/heartrateHours/upload")
    Observable<RetCode> heartHoursUpRepo(@Query("uid") long j, @Body List<HeartHoursData> list);

    @POST("sport/heartrate/upload")
    Observable<RetCode> heartUpHealthyRepo(@Query("uid") long j, @Body List<HeartDownData1> list);

    @POST("sport/heartrate_segment/upload")
    Observable<RetCode> heartUpRepo(@Query("uid") long j, @Body List<HeartDownData1> list);

    @POST("upload_gait_segment")
    @Multipart
    Observable<UpSDFileCode> r1DataRepo(@Query("uid") long j, @Query("st") String str, @Query("ed") String str2, @Query("source") String str3, @Part MultipartBody.Part part);

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

    @POST("sport/sports/upload/new5_0")
    Observable<RetCode> sportUpRepoChina(@Query("uid") long j, @Body List<SportData> list);

    @POST("sport/ecg/ai/update")
    Observable<ReturnCode> upDateEcgAi(@Body EcgDataAiResult ecgDataAiResult);

    @POST("sport/ecg/note/update")
    Observable<RetCode> upDateEcgNote(@Body EcgDataNoteNet ecgDataNoteNet);

    @POST("sport/gpssport/upload")
    Observable<RetCode> upGpsDetailRepo(@Query("uid") long j, @Body List<GpsUpTotal> list);

    @POST("sport/ecg/upload")
    Observable<RetCode> upLoadEcgData(@Body EcgUploadList ecgUploadList);

    @POST("upload_gps_data")
    @Multipart
    Observable<GpsRetCode> upPhoneGpsFile(@Query("uid") long j, @Part("file\"; filename=\"phone_gps.txt\"") RequestBody requestBody);

    @POST("health/bloodpressure/batchupload")
    Call<ResponseBody> uploadBlood(@Body BloodDataUpload bloodDataUpload);

    @POST("fileservice/upload_gps_data")
    @Multipart
    Call<ResponseBody> uploadFile(@Query("uid") long j, @Part("file\"; filename=\"bles.txt\"") RequestBody requestBody);

    @POST("/upload_gps_segment")
    @Multipart
    Observable<UpSDFileCode> uploadGpsFile(@Query("uid") long j, @Query("st") String str, @Query("ed") String str2, @Query("source") String str3, @Part MultipartBody.Part part);

    @POST("sport/segment/batchupload")
    Observable<ReturnCode> uploadSport28(@Body Sport28Send sport28Send);

    @GET("sport/walking/download")
    Observable<WalkingDownCode> walkDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("sport/walking/upload")
    Observable<RetCode> walkUpHealthyRepo(@Query("uid") long j, @Body List<WalkHealthyData> list);

    @POST("sport/walking/upload")
    Observable<RetCode> walkUpRepo(@Query("uid") long j, @Body List<WalkData> list);

    @POST("sport/weight/upload")
    Observable<RetCode> weightUpRepo(@Query("uid") long j, @Body List<WeightData> list);
}
