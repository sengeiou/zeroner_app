package com.iwown.device_module.common.network.api;

import com.iwown.data_link.af.AfResultBean;
import com.iwown.data_link.blood.BpPreUpload;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.data_link.weight.WifiScaleReq;
import com.iwown.device_module.common.network.data.req.FactoryVersion;
import com.iwown.device_module.common.network.data.req.FamilyNoAccountAddRequest;
import com.iwown.device_module.common.network.data.req.FamilyNoAccountDelRequest;
import com.iwown.device_module.common.network.data.req.FwUpdate;
import com.iwown.device_module.common.network.data.req.ScaleCleanWifi;
import com.iwown.device_module.common.network.data.req.ScaleObsoleteReq;
import com.iwown.device_module.common.network.data.req.SportBallSend;
import com.iwown.device_module.common.network.data.req.SportGpsSend;
import com.iwown.device_module.common.network.data.req.SportOtherSend;
import com.iwown.device_module.common.network.data.req.SportSwimSend;
import com.iwown.device_module.common.network.data.req.T_Weight;
import com.iwown.device_module.common.network.data.req.UpSportBallUrl;
import com.iwown.device_module.common.network.data.req.UpSportGpsUrl;
import com.iwown.device_module.common.network.data.req.Upgrade;
import com.iwown.device_module.common.network.data.req.UserDeviceReq;
import com.iwown.device_module.common.network.data.resp.AllSportBallCode;
import com.iwown.device_module.common.network.data.resp.AllSportGpsCode;
import com.iwown.device_module.common.network.data.resp.AllSportOtherCode;
import com.iwown.device_module.common.network.data.resp.BandScaleInfo;
import com.iwown.device_module.common.network.data.resp.BloodpressureCode;
import com.iwown.device_module.common.network.data.resp.DeviceSettingRemote;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode;
import com.iwown.device_module.common.network.data.resp.F1SleepData;
import com.iwown.device_module.common.network.data.resp.FamilyNoAccountList;
import com.iwown.device_module.common.network.data.resp.FamilyReturnMessage;
import com.iwown.device_module.common.network.data.resp.FirmwareDownCode;
import com.iwown.device_module.common.network.data.resp.FwUpdateReturnMessage;
import com.iwown.device_module.common.network.data.resp.ModeTypes;
import com.iwown.device_module.common.network.data.resp.PrefServerResponse;
import com.iwown.device_module.common.network.data.resp.RetCode;
import com.iwown.device_module.common.network.data.resp.ReturnCode;
import com.iwown.device_module.common.network.data.resp.SportDownCode;
import com.iwown.device_module.common.network.data.resp.UpSDFileCode;
import com.iwown.device_module.device_operation.bean.ModeItems;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DeviceApi {
    @GET("cloudevice/bindedweightscale")
    Observable<BandScaleInfo> bindScaleInfo(@Query("uid") long j);

    @GET("cloudevice/class1modelist")
    Observable<ModeTypes> class1modelist(@Query("app") int i, @Query("region") String str);

    @GET("cloudevice/class2modelist")
    Observable<ModeItems> class2modelist(@Query("app") int i, @Query("region") String str);

    @GET("calculation/sleeping")
    Observable<F1SleepData> down61SleepData(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2, @Query("heartSwitch") int i);

    @GET("device/downloadSetting")
    Observable<DeviceSettingsDownCode> downDevSettingsRepo(@Query("app") int i, @Query("app_platform") int i2, @Query("model") String str, @Query("version") String str2);

    @GET("device/downloadUpgrade")
    Observable<FirmwareDownCode> downUpgradeRepo(@Query("uid") long j);

    @GET("sport/ball/stat")
    Observable<AllSportBallCode> downloadAllSportBall(@Query("uid") long j);

    @GET("sport/gps/stat")
    Observable<AllSportGpsCode> downloadAllSportGps(@Query("uid") long j);

    @GET("sport/other/stat")
    Observable<AllSportOtherCode> downloadAllSportOther(@Query("uid") long j);

    @GET("health/bloodpressure/correction/download")
    Observable<BloodpressureCode> downloadBloodpressure(@Query("uid") long j);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String str, @Header("Accept-Encoding") String str2);

    @GET("device/downloadUserSetting")
    Observable<PrefServerResponse> downloadPref(@Query("uid") long j, @Query("model") String str);

    @POST("device/fwupdate")
    Observable<FwUpdateReturnMessage> fwupdateRepo(@Body FwUpdate fwUpdate);

    @GET("data61_download_url")
    Observable<UpSDFileCode> get61FileUrl(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2);

    @GET("gnss_download_url")
    Observable<UpSDFileCode> get62FileUrl(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2);

    @POST("af/online")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<AfResultBean> getAfResult(@Body RequestBody requestBody);

    @GET("user/family/noAccount/list")
    Observable<FamilyNoAccountList> getFamilyNoAccountList(@Query("uid") long j);

    @GET("weight/rawdata/v2")
    Observable<WifiScaleRWResp> getWifiScaleData(@Query("uid") long j, @Query("weightDate") long j2, @Query("scaleid") String str);

    @POST("weight/archive")
    Observable<RetCode> postArchiveData(@Body WifiScaleReq wifiScaleReq);

    @POST("cloudevice/cleanwifi")
    Observable<RetCode> postCleanWifi(@Query("uid") long j, @Query("scaleid") String str, @Body ScaleCleanWifi scaleCleanWifi);

    @POST("user/family/noAccount/add")
    Observable<FamilyReturnMessage> postFamilyNoAccountAdd(@Body FamilyNoAccountAddRequest familyNoAccountAddRequest);

    @POST("user/family/noAccount/delete")
    Observable<FamilyReturnMessage> postFamilyNoAccountDelete(@Body FamilyNoAccountDelRequest familyNoAccountDelRequest);

    @POST("user/family/noAccount/edit")
    Observable<FamilyReturnMessage> postFamilyNoAccountEdit(@Body FamilyNoAccountAddRequest familyNoAccountAddRequest);

    @POST("weight/obsolete")
    Observable<RetCode> postObsoleteData(@Body ScaleObsoleteReq scaleObsoleteReq);

    @POST("sport/weight/upload")
    Observable<RetCode> postWeightProfile(@Query("uid") long j, @Body List<T_Weight> list);

    @POST("cloudevice/setunit")
    Observable<RetCode> scaleSetUnit(@Query("unit") int i, @Query("scaleid") String str, @Body ScaleCleanWifi scaleCleanWifi);

    @GET("sport/sports/download")
    Observable<SportDownCode> sportDownRepo(@Query("uid") long j, @Query("ds") int i, @Query("st") String str);

    @POST("cloudevice/unbind/weightscale")
    Observable<RetCode> unBindScale(@Body ScaleCleanWifi scaleCleanWifi);

    @POST("upload_gps_segment")
    @Multipart
    Observable<UpSDFileCode> upSportGpsFile(@Query("uid") long j, @Query("st") String str, @Query("ed") String str2, @Query("source") String str3, @Part MultipartBody.Part part);

    @POST("upload_hr_segment")
    @Multipart
    Observable<UpSDFileCode> upSportHrFile(@Query("uid") long j, @Query("st") String str, @Query("ed") String str2, @Query("source") String str3, @Part MultipartBody.Part part);

    @POST("upload_gait_segment")
    @Multipart
    Observable<UpSDFileCode> upSportR1File(@Query("uid") long j, @Query("st") String str, @Query("ed") String str2, @Query("source") String str3, @Part MultipartBody.Part part);

    @POST("sport/ball/addition/batchupload")
    Observable<ReturnCode> updateSportBallUrl(@Body UpSportBallUrl upSportBallUrl);

    @POST("sport/gps/addition/batchupload")
    Observable<ReturnCode> updateSportGpsUrl(@Body UpSportGpsUrl upSportGpsUrl);

    @POST("sport/other/addition/batchupload")
    Observable<ReturnCode> updateSportOtherUrl(@Body UpSportBallUrl upSportBallUrl);

    @POST("upload_sport_rri")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<UpSDFileCode> uploadAfData(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2, @Part MultipartBody.Part part);

    @POST("sport/ball/batchupload")
    Observable<ReturnCode> uploadBallSegment(@Query("uid") long j, @Body SportBallSend sportBallSend);

    @POST("health/bloodpressure/correction/upload")
    Observable<ResponseBody> uploadBloodpressure(@Body BpPreUpload bpPreUpload);

    @POST("device/factoryVersion")
    Observable<RetCode> uploadFactoryVersion(@Body FactoryVersion factoryVersion);

    @POST("sport/gps/batchupload")
    Observable<ReturnCode> uploadGpsSegment(@Query("uid") long j, @Body SportGpsSend sportGpsSend);

    @POST("sport/other/batchupload")
    Observable<ReturnCode> uploadOtherSegment(@Query("uid") long j, @Body SportOtherSend sportOtherSend);

    @POST("device/uploadUserSetting ")
    Observable<RetCode> uploadPref(@Body DeviceSettingRemote deviceSettingRemote);

    @POST("upload_historyLog")
    @Multipart
    Observable<UpSDFileCode> uploadSDFileRepo(@Query("uid") long j, @Query("type") int i, @Query("date") String str, @Part("file\"; filename=\".txt\"") RequestBody requestBody);

    @POST("sportrawupload")
    @Multipart
    Observable<UpSDFileCode> uploadSDFile_61_data(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2, @Part MultipartBody.Part part);

    @POST("sportrawupload_gnss")
    @Multipart
    Observable<UpSDFileCode> uploadSDFile_62_data(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2, @Part MultipartBody.Part part);

    @POST("sportrawupload_gait")
    @Multipart
    Observable<UpSDFileCode> uploadSDFile_68_data(@Query("uid") long j, @Query("recordDate") String str, @Query("source") String str2, @Part MultipartBody.Part part);

    @POST("upload_ecg_segment")
    @Multipart
    Observable<UpSDFileCode> uploadSDFile_ecg_data(@Query("uid") long j, @Query("st") String str, @Query("ed") String str2, @Query("source") String str3, @Part MultipartBody.Part part);

    @POST("sport/swim/upload")
    Observable<ReturnCode> uploadSwimSegment(@Query("uid") long j, @Body SportSwimSend sportSwimSend);

    @POST("device/uploadUpgrade")
    Observable<RetCode> uploadUpgradeRepo(@Body Upgrade upgrade);

    @POST("device/upload/userdevice")
    Observable<RetCode> uploadUserDevice(@Body UserDeviceReq userDeviceReq);
}
