package com.iwown.sport_module.net.client;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.app_link.ModuleRouteAPPService;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.blood.BloodDataUpload;
import com.iwown.data_link.blood.BpDownData;
import com.iwown.data_link.blood.BpDownData1;
import com.iwown.data_link.blood.bpCoverageDown;
import com.iwown.data_link.ecg.EcgDataAiResult;
import com.iwown.data_link.ecg.EcgDataNoteNet;
import com.iwown.data_link.ecg.EcgDownList;
import com.iwown.data_link.ecg.EcgDownLoadNet;
import com.iwown.data_link.ecg.EcgHasDataNet;
import com.iwown.data_link.ecg.EcgHasDataNet.EcgHasData;
import com.iwown.data_link.ecg.EcgUploadList;
import com.iwown.data_link.ecg.EcgUploadNet;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.fatigue.FatigueRetCode;
import com.iwown.data_link.fatigue.FatigueSend;
import com.iwown.data_link.fatigue.ModuleRouteFatigueService;
import com.iwown.data_link.heart.HeartHoursData;
import com.iwown.data_link.heart.HeartHoursDownCode;
import com.iwown.data_link.heart.HeartHoursNewDownCode;
import com.iwown.data_link.heart.HeartStatusData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.heart.heart_sport.HeartDownCode;
import com.iwown.data_link.heart.heart_sport.HeartDownData1;
import com.iwown.data_link.heart.heart_sport.HeartUpSend;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDownCode;
import com.iwown.data_link.sleep_data.SleepStatusData;
import com.iwown.data_link.sleep_data.SleepUpNewSend;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.ReturnCode;
import com.iwown.data_link.sport_data.Sport28Code;
import com.iwown.data_link.sport_data.SportBallCode;
import com.iwown.data_link.sport_data.SportData;
import com.iwown.data_link.sport_data.SportDownCode;
import com.iwown.data_link.sport_data.SportGpsCode;
import com.iwown.data_link.sport_data.SportOtherCode;
import com.iwown.data_link.sport_data.SportSwimCode;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.walk_29_data.WalkingDownCode;
import com.iwown.data_link.weight.MacBandS2Bean;
import com.iwown.data_link.weight.ModuleRouteWeightService;
import com.iwown.data_link.weight.ScaleDataResp;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.device_module.common.BaseActionUtils.FilePath;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_setting.configure.WristbandModel.DownType;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.iwown.lib_common.log.SingleThreadUtil;
import com.iwown.lib_common.network.RetryWithDelay;
import com.iwown.lib_common.network.interceptor.LogInterceptor;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.sport_module.gps.data.GpsUpTotal;
import com.iwown.sport_module.gps.data.TB_location_down;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.net.HttpLogUtils;
import com.iwown.sport_module.net.HttpLogUtils.Level;
import com.iwown.sport_module.net.OverseasCallWrapper;
import com.iwown.sport_module.net.api.OverseasApiService;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.constant.ConstantsLive.APP_INFO;
import com.iwown.sport_module.net.constant.ConstantsLive.OverSeasBaseUrl;
import com.iwown.sport_module.net.exception.ServerException;
import com.iwown.sport_module.net.response.AllSportBallCode;
import com.iwown.sport_module.net.response.AllSportGpsCode;
import com.iwown.sport_module.net.response.AllSportOtherCode;
import com.iwown.sport_module.net.response.AllSportSwimCode;
import com.iwown.sport_module.net.response.CheckAdCode;
import com.iwown.sport_module.net.response.CheckAdCode.AdData;
import com.iwown.sport_module.net.response.DevSupportByNameCode;
import com.iwown.sport_module.net.response.DisposeSportAllData;
import com.iwown.sport_module.net.response.GpsDownCode;
import com.iwown.sport_module.net.response.GpsRetCode;
import com.iwown.sport_module.net.response.MonthHas28DateCode;
import com.iwown.sport_module.net.response.MonthHas28DateCode.RspInfoModel;
import com.iwown.sport_module.net.response.Sport28MonthCode;
import com.iwown.sport_module.net.response.Sport28MonthCode.Sport28Index;
import com.iwown.sport_module.net.response.SupportsByNameItem;
import com.iwown.sport_module.net.response.UpSDFileCode;
import com.iwown.sport_module.net.response.Weather24h0verseasRsp;
import com.iwown.sport_module.net.send.Sport28Send;
import com.iwown.sport_module.pojo.active.WalkData;
import com.iwown.sport_module.sql.TB_DevSupportsByName;
import com.iwown.sport_module.sql.TB_ad_url;
import com.iwown.sport_module.sql.TB_has28Days_monthly;
import com.iwown.sport_module.ui.ecg.bean.EcgAiResultEvent;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class OverseasClient extends BaseClient {
    public static final String APP_NAME_FOR_WEATHER = APP_INFO.APP_NAME_FOR_WEATHER;
    public static final int VERSION_INT = APP_INFO.VERSION_INT;
    private static OverseasClient instance = null;
    private final String DEVICE = "device";
    private final String FILE = "fileservice";
    private final String FIRMWARE = L.Bluetooth_Firmware_Upgrade;
    private final String LOCATION = "location";
    private final String LOG_UP = "log_up";
    private final String Log_Up = "LogFile";
    private final String NGG = "nggservice";
    private final String OTHERS = "others";
    private final String SPORT = DownType.SPORT;
    private final String USER = "user";
    private final String WAWA = "wawaservice";
    private final String WEATHER = "weather";
    private final String Weight_DEVICE = "weight_device";
    private final String Weight_SPORT = "weight_sport";
    private OverseasApiService devApiService;
    private OverseasApiService fileService;
    private OverseasApiService locationService;
    private OverseasApiService logUpApi;
    /* access modifiers changed from: private */
    public MyCallback mCallback = null;
    private OverseasApiService nggService;
    private OverseasApiService otherService;
    private OverseasApiService sportApiService;
    private OverseasApiService upFileApiService;
    private OverseasApiService userApiService;
    private OverseasApiService wawaService;
    private OverseasApiService weatherService;
    private OverseasApiService weightDeviceService;
    private OverseasApiService weightSportService;

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    public OverseasClient(MyCallback callback) {
        this.mCallback = callback;
    }

    /* access modifiers changed from: private */
    public OverseasApiService getApiService(String urlType) {
        if ("user".equals(urlType)) {
            if (this.userApiService == null) {
                HttpLogUtils httpLogUtils = new HttpLogUtils();
                httpLogUtils.setLevel(Level.CUSTOM);
                this.userApiService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "userservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils).build()).build().create(OverseasApiService.class);
            }
            return this.userApiService;
        } else if (DownType.SPORT.equals(urlType)) {
            if (this.sportApiService == null) {
                HttpLogUtils httpLogUtils2 = new HttpLogUtils();
                httpLogUtils2.setLevel(Level.CUSTOM);
                this.sportApiService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "sportservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils2).build()).build().create(OverseasApiService.class);
            }
            return this.sportApiService;
        } else if ("weight_sport".equals(urlType)) {
            if (this.weightSportService == null) {
                HttpLogUtils httpLogUtils3 = new HttpLogUtils();
                httpLogUtils3.setLevel(Level.CUSTOM);
                this.weightSportService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "sportservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils3).build()).build().create(OverseasApiService.class);
            }
            return this.weightSportService;
        } else if ("device".equals(urlType)) {
            if (this.devApiService == null) {
                HttpLogUtils httpLogUtils4 = new HttpLogUtils();
                httpLogUtils4.setLevel(Level.CUSTOM);
                this.devApiService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "deviceservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils4).build()).build().create(OverseasApiService.class);
            }
            return this.devApiService;
        } else if ("weight_device".equals(urlType)) {
            if (this.weightDeviceService == null) {
                HttpLogUtils httpLogUtils5 = new HttpLogUtils();
                httpLogUtils5.setLevel(Level.CUSTOM);
                this.weightDeviceService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "deviceservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils5).build()).build().create(OverseasApiService.class);
            }
            return this.weightDeviceService;
        } else if ("log_up".equals(urlType)) {
            if (this.upFileApiService == null) {
                HttpLogUtils httpLogUtils6 = new HttpLogUtils();
                httpLogUtils6.setLevel(Level.CUSTOM);
                this.upFileApiService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.LOG_UPLOAD_API).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils6).build()).build().create(OverseasApiService.class);
            }
            return this.upFileApiService;
        } else if ("weather".equals(urlType)) {
            if (this.weatherService == null) {
                HttpLogUtils httpLogUtils7 = new HttpLogUtils();
                httpLogUtils7.setLevel(Level.CUSTOM);
                this.weatherService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.WEATHER_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils7).build()).build().create(OverseasApiService.class);
            }
            return this.weatherService;
        } else if ("location".equals(urlType)) {
            if (this.locationService == null) {
                HttpLogUtils httpLogUtils8 = new HttpLogUtils();
                httpLogUtils8.setLevel(Level.CUSTOM);
                this.locationService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.GOOGLE_LOCATION).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils8).build()).build().create(OverseasApiService.class);
            }
            return this.locationService;
        } else if ("fileservice".equals(urlType)) {
            if (this.fileService == null) {
                HttpLogUtils httpLogUtils9 = new HttpLogUtils();
                httpLogUtils9.setLevel(Level.CUSTOM);
                this.fileService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "fileservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils9).build()).build().create(OverseasApiService.class);
            }
            return this.fileService;
        } else if ("wawaservice".equals(urlType)) {
            if (this.wawaService == null) {
                HttpLogUtils httpLogUtils10 = new HttpLogUtils();
                httpLogUtils10.setLevel(Level.CUSTOM);
                this.wawaService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API + "wawaservice/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils10).build()).build().create(OverseasApiService.class);
            }
            return this.wawaService;
        } else if ("nggservice".equals(urlType)) {
            if (this.nggService == null) {
                HttpLogUtils httpLogUtils11 = new HttpLogUtils();
                httpLogUtils11.setLevel(Level.CUSTOM);
                this.nggService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NGG_Url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils11).build()).build().create(OverseasApiService.class);
            }
            return this.nggService;
        } else if ("LogFile".equals(urlType)) {
            if (this.logUpApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.logUpApi = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.GETWAY).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(OverseasApiService.class);
            }
            return this.logUpApi;
        } else {
            if (this.otherService == null) {
                HttpLogUtils httpLogUtils12 = new HttpLogUtils();
                httpLogUtils12.setLevel(Level.CUSTOM);
                this.otherService = (OverseasApiService) new Builder().baseUrl(OverSeasBaseUrl.NEW_API).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().readTimeout(90, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils12).build()).build().create(OverseasApiService.class);
            }
            return this.otherService;
        }
    }

    public void getCity(String lat, String lng, String language) {
        String latLng = lat + "," + lng;
        KLog.i("--------===================================================");
        getApiService("location").getCity(latLng, language, true, AppConfigUtil.GoogleMapKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody body) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(body);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getWeather(RequestBody body) {
        getApiService("weather").getWeather(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody body) throws Exception {
                KLog.e("licl", "天气请求成功-->");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(body);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getWeather_24h(long uid, Object lat, Object lon, String city, String key, String timestamp, String appversion, String country) {
        getApiService("weather").getWeather_24h(uid, lat, lon, city, key, timestamp, appversion, country).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<Weather24h0verseasRsp>() {
            /* access modifiers changed from: protected */
            public void onSuccess(Weather24h0verseasRsp body) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(body);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void up28Data(long uid, List<SportData> sportData) {
        getApiService(DownType.SPORT).sportUpRepo(uid, sportData).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode code) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(code);
                }
                KLog.i("28上传成功------->");
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
                KLog.i("28上传失败------->");
            }
        });
    }

    public void up29Data(long uid, List<WalkData> walkData) {
        getApiService(DownType.SPORT).walkUpRepo(uid, walkData).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode code) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(code);
                }
                KLog.i("29上传成功------->");
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
                KLog.i("29上传失败------->");
            }
        });
    }

    public void down29Data(long uid, int ds, String st) {
        getApiService(DownType.SPORT).walkDownRepo(uid, ds, st).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<WalkingDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(WalkingDownCode code) throws Exception {
                KLog.e("29历史数据下载成功-->");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(code);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("29历史数据下载失败-->");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getDateInMonthHas28(long uid, int year, int month, int zone) {
        final long j = uid;
        final int i = year;
        final int i2 = month;
        getApiService(DownType.SPORT).getDateInMonthHas28(uid, (long) ((year * 100) + month), zone).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<MonthHas28DateCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(MonthHas28DateCode code) throws Exception {
                TB_has28Days_monthly tb_has28Days_monthly = new TB_has28Days_monthly();
                tb_has28Days_monthly.setUid(j);
                tb_has28Days_monthly.setYear(i);
                tb_has28Days_monthly.setMonth(i2);
                tb_has28Days_monthly.setInfo(JsonTool.toJson(code.getContent()));
                tb_has28Days_monthly.saveOrUpdate("uid=? and year=? and month=?", j + "", i + "", i2 + "");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(code);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if ((e instanceof ServerException) && ((ServerException) e).code() == 10404) {
                    TB_has28Days_monthly tb_has28Days_monthly = new TB_has28Days_monthly();
                    tb_has28Days_monthly.setUid(j);
                    tb_has28Days_monthly.setYear(i);
                    tb_has28Days_monthly.setMonth(i2);
                    tb_has28Days_monthly.setInfo("");
                    tb_has28Days_monthly.saveOrUpdate("uid=? and year=? and month=?", j + "", i + "", i2 + "");
                }
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void get62FileDown(long uid, String data_from, String recordDate, final String save_dir_path, final String file_name) {
        super.get62FileDown(uid, data_from, recordDate, save_dir_path, file_name);
        getApiService("log_up").get62FileUrl(uid, recordDate, data_from).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe((Observer<? super T>) new OverseasCallWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode code) throws Exception {
                if (code.getRetCode() == 10404) {
                    KLog.i("no2855获取62文件的下载 链接为空值");
                } else {
                    KLog.i("no2855获取62文件的下载链接成功-->" + code.getUrl());
                }
                OverseasClient.this.downAndSaveFile(code.getUrl(), save_dir_path, file_name);
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.i("no2855获取62文件的下载链接失败-->");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void get61FileDown(long uid, String data_from, String recordDate, final String save_dir_path, final String file_name) {
        super.get61FileDown(uid, data_from, recordDate, save_dir_path, file_name);
        getApiService("log_up").get61FileUrl(uid, recordDate, data_from).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe((Observer<? super T>) new OverseasCallWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode code) throws Exception {
                KLog.i("no2855获取61文件的下载链接成功-->" + code.getUrl());
                OverseasClient.this.downAndSaveFile(code.getUrl(), save_dir_path, file_name);
            }

            public void onError(Throwable e) {
                KLog.i("no2855获取61文件的下载链接失败-->");
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downLoadEcgFile(String url, long uid, String dataFrom, String date, EcgViewDataBean bean) {
        super.downLoadEcgFile(url, uid, dataFrom, date, bean);
        String str = FilePath.ECG_Data_Path;
        final String fileName = uid + HelpFormatter.DEFAULT_OPT_PREFIX + dataFrom + HelpFormatter.DEFAULT_OPT_PREFIX + date;
        final long j = uid;
        final String str2 = dataFrom;
        final String str3 = date;
        getApiService("others").downloadFile(url, "identity").subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(new Function<ResponseBody, ResponseBody>() {
            public ResponseBody apply(ResponseBody body) throws Exception {
                if (body == null) {
                    KLog.e("--------------下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                long length = body.contentLength();
                KLog.e("----------content-length: " + length);
                if (length <= 0) {
                    KLog.e("-----------------下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                FileUtils.writeInputStreamToDisk2(FilePath.ECG_Data_Path, fileName, body.byteStream());
                return body;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody responseBody) throws Exception {
                if (responseBody == null) {
                    KLog.e("licl", "下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                long length = responseBody.contentLength();
                KLog.e("licl", "content-length: " + length);
                if (length <= 0) {
                    KLog.e("licl", "下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + FilePath.ECG_Data_Path + fileName + ".txt";
                String zipPath = Environment.getExternalStorageDirectory().getAbsolutePath() + FilePath.ECG_Data_Path + fileName;
                File file = new File(path);
                if (file.exists()) {
                    KLog.e("no2855--> 下载的ecg 路径1111: ");
                    OverseasClient.this.readFileContent(j, str2, str3, path);
                    return;
                }
                File zipFile = new File(zipPath);
                if (zipFile.exists()) {
                    KLog.e("no2855--> 下载的ecg 路径2222: " + zipFile.getPath());
                    if (ZipUtil.unZip(zipFile, file)) {
                        KLog.e("no2855 解压成功了吧--> ");
                        OverseasClient.this.readFileContent(j, str2, str3, path);
                        return;
                    }
                    OverseasClient.this.readFileContent(j, str2, str3, zipPath);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    /* access modifiers changed from: private */
    public void readFileContent(long uid, String dataFrom, String date, String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            while (true) {
                try {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
            String str = sb.toString();
            if (!TextUtils.isEmpty(str)) {
                ModuleRouterEcgService.getInstance().updateEcgData(uid, dataFrom, date, str);
                EventBus.getDefault().post(new EcgAiResultEvent());
            }
        } catch (FileNotFoundException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    public void downAndSaveFile(String url, final String save_dir_path, final String file_name) {
        getApiService("others").downloadFile(url, "identity").subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(new Function<ResponseBody, ResponseBody>() {
            public ResponseBody apply(ResponseBody body) throws Exception {
                if (body == null) {
                    KLog.e("licl", "下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                long length = body.contentLength();
                KLog.e("licl", "content-length: " + length);
                if (length <= 0) {
                    KLog.e("licl", "下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                FileUtils.writeInputStreamToDisk2(save_dir_path, file_name, body.byteStream());
                return body;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody body) throws Exception {
                KLog.i("文件下载并存储成功：" + save_dir_path + file_name);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(body);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("文件下载或存储失败");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void upPhoneGpsFileAndUpGpsDetailData(long uid, File file, long time) {
        final long j = uid;
        final long j2 = time;
        getApiService("fileservice").upPhoneGpsFile(uid, RequestBody.create(MediaType.parse("multipart/form-data"), file)).subscribeOn(Schedulers.io()).doOnNext(new Consumer<GpsRetCode>() {
            public void accept(GpsRetCode code) throws Exception {
                KLog.e("licl", "phone_gps文件上传成功-->" + code.getUrl());
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<GpsRetCode, Observable<RetCode>>() {
            public Observable<RetCode> apply(GpsRetCode code) throws Exception {
                TB_location_history history = (TB_location_history) DataSupport.where("uid=? and time_id=?", j + "", j2 + "").findFirst(TB_location_history.class);
                history.setIs_upload(1);
                history.saveOrUpdate("uid=? and time_id=?", j + "", j2 + "");
                GpsUpTotal total = new GpsUpTotal();
                total.setUid(j);
                total.setData_from(2);
                total.setTimeStart(history.getTime_id());
                total.setTimeEnd(history.getEnd_time());
                total.setSports_type(history.getSport_type());
                total.setCalorie(history.getCalorie());
                total.setDistance(history.getDistance());
                total.setTrack_url(code.getUrl());
                total.setDuration(history.getTime());
                List<GpsUpTotal> totals = new ArrayList<>();
                totals.add(total);
                return OverseasClient.this.getApiService(DownType.SPORT).upGpsDetailRepo(j, totals);
            }
        }).retryWhen(new RetryWithDelay(2, 1000)).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode code) throws Exception {
                KLog.e("licl", "gps_统计数据也上传成功了");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(code);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                ThrowableExtension.printStackTrace(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getGpsPageData(long uid, final int pageIndex, int sportType) {
        getApiService(DownType.SPORT).getGpsPageRepo(uid, pageIndex, sportType).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).doOnNext(new Consumer<GpsDownCode>() {
            public void accept(GpsDownCode code) throws Exception {
                List<GpsUpTotal> content = code.getContent();
                Log.d("tesrt", "获取gps分页数据成功：" + JsonTool.toJson(code));
                if (content.size() > 0) {
                    for (GpsUpTotal gpsUpTotal : content) {
                        TB_location_down down = new TB_location_down();
                        down.setTime_id(gpsUpTotal.getTimeStart());
                        down.setSport_type(gpsUpTotal.getSports_type());
                        down.setTime((int) (gpsUpTotal.getTimeEnd() - gpsUpTotal.getTimeStart()));
                        down.setUid(gpsUpTotal.getUid());
                        down.setDistance(gpsUpTotal.getDistance());
                        down.setCalorie(gpsUpTotal.getCalorie());
                        down.setEnd_time(gpsUpTotal.getTimeEnd());
                        down.setGps_msg(gpsUpTotal.getTrack_url());
                        down.setFrom(gpsUpTotal.getData_from());
                        down.setPage(pageIndex);
                        down.setIs_upload(1);
                        down.saveOrUpdate("uid=? and time_id=?", gpsUpTotal.getUid() + "", gpsUpTotal.getTimeStart() + "");
                        if (!DataSupport.isExist(TB_location_history.class, "uid=? and time_id=?", down.getUid() + "", down.getTime_id() + "")) {
                            TB_location_history history = new TB_location_history();
                            history.setUid(down.getUid());
                            history.setCalorie(down.getCalorie());
                            history.setDistance(down.getDistance());
                            history.setSport_type(down.getSport_type());
                            history.setTime_id(down.getTime_id());
                            history.setTime(down.getTime());
                            history.setEnd_time(down.getEnd_time());
                            history.setIs_upload(1);
                            history.save();
                        }
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<GpsDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(GpsDownCode code) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(code);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getAdvertise(String lat, String lng) {
        getApiService("location").getCity(lat + "," + lng, "zh-CN", true, AppConfigUtil.GoogleMapKey).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).flatMap(new Function<ResponseBody, ObservableSource<CheckAdCode>>() {
            public ObservableSource<CheckAdCode> apply(ResponseBody body) throws Exception {
                JSONArray ja = new JSONObject(body.string()).getJSONArray("results");
                if (ja.length() > 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONArray ja2 = ja.getJSONObject(i).getJSONArray("address_components");
                        for (int j = 0; j < ja2.length(); j++) {
                            JSONObject jb3 = ja2.getJSONObject(j);
                            if ("country".equals(jb3.getJSONArray("types").getString(0))) {
                                try {
                                    if (TextUtils.equals("RU", jb3.optString("short_name"))) {
                                        ModuleRouteAPPService.getInstance().changeRuURL();
                                    }
                                } catch (Exception e) {
                                    ThrowableExtension.printStackTrace(e);
                                }
                                KLog.e("licl", "找到地区信息--" + jb3.getString("short_name"));
                                L.file("ad--找到地区信息--" + jb3.getString("short_name"), 3);
                                return OverseasClient.this.getApiService("wawaservice").checkAdRepo(jb3.getString("short_name"));
                            }
                        }
                        if (0 != 0) {
                            break;
                        }
                    }
                }
                KLog.e("licl", "没找到地区信息--无法继续请求广告了");
                L.file("ad--没找到地区信息--无法继续请求广告了", 3);
                return null;
            }
        }).map(new Function<CheckAdCode, Boolean>() {
            public Boolean apply(CheckAdCode adCode) throws Exception {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(((AdData) adCode.getData().get(0)).getExpire_date());
                long times = date.getTime() / 1000;
                long startTime = simpleDateFormat.parse(((AdData) adCode.getData().get(0)).getStart_date()).getTime() / 1000;
                long nowTime = System.currentTimeMillis() / 1000;
                KLog.e("licl", "expire: " + times + "start: " + startTime + "nowtime: " + nowTime);
                if (times <= nowTime) {
                    return Boolean.valueOf(false);
                }
                TB_ad_url myTb = (TB_ad_url) DataSupport.findFirst(TB_ad_url.class);
                if (myTb != null && myTb.getAdOneUrl().equals(((AdData) adCode.getData().get(0)).getRedirect())) {
                    return Boolean.valueOf(false);
                }
                DataSupport.deleteAll(TB_ad_url.class, new String[0]);
                TB_ad_url ad_url = new TB_ad_url();
                ad_url.setAdImgUrl(((AdData) adCode.getData().get(0)).getUrl());
                ad_url.setAdOneUrl(((AdData) adCode.getData().get(0)).getRedirect());
                ad_url.setAdTime(times);
                ad_url.setStart_time(startTime);
                ad_url.save();
                if (startTime <= nowTime) {
                    return Boolean.valueOf(true);
                }
                return Boolean.valueOf(false);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            public void accept(Boolean aBoolean) throws Exception {
                KLog.e("licl", "是否有广告: " + aBoolean);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(aBoolean);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable throwable) throws Exception {
                ThrowableExtension.printStackTrace(throwable);
            }
        });
    }

    public void downloadSleepByDate(long uid, int ds, String st) {
        super.downloadSleepByDate(uid, ds, st);
        getApiService(DownType.SPORT).sleepDownRepo(uid, ds, st).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SleepDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SleepDownCode sleepDownCode) throws Exception {
                KLog.i("睡眠下载成功------->");
                ModuleRouteSleepService.getInstance().saveSleep(sleepDownCode, true);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(sleepDownCode.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.i("睡眠数据下载失败------->" + e.getMessage());
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void uploadSleepData(SleepUpNewSend sleepUpNewSend) {
        super.uploadSleepData(sleepUpNewSend);
        if (sleepUpNewSend.getContent() != null && sleepUpNewSend.getContent().size() != 0) {
            getApiService(DownType.SPORT).sleepUpRepo(sleepUpNewSend.getUid(), sleepUpNewSend.getContent()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
                /* access modifiers changed from: protected */
                public void onSuccess(RetCode retCode) throws Exception {
                    if (retCode.getRetCode() != 0) {
                        KLog.e("睡眠数据上传 " + retCode.getRetCode());
                    } else if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    KLog.i("睡眠数据上传------->" + e.getMessage());
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onFail(e);
                    }
                }
            });
        }
    }

    public void downloadSleepStatusByDate(long uid, int ds, String st) {
        super.downloadSleepStatusByDate(uid, ds, st);
        getApiService(DownType.SPORT).sleepDownstatus(uid, ds, st).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SleepStatusData>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SleepStatusData sleepStatusData) throws Exception {
                KLog.d(" downloadSleepStatusByDate ok ");
                ModuleRouteSleepService.getInstance().updateSleepStatusData(sleepStatusData.getContent());
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(sleepStatusData.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.d(" downloadSleepStatusByDate error  " + e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadHeartStatusByDate(long uid, int ds, String st) {
        super.downloadHeartStatusByDate(uid, ds, st);
        getApiService(DownType.SPORT).heartDownstatus(uid, ds, st).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<HeartStatusData>() {
            /* access modifiers changed from: protected */
            public void onSuccess(HeartStatusData sleepStatusData) throws Exception {
                KLog.e(" downloadSleepStatusByDate ok ");
                if (sleepStatusData.getContent() != null) {
                    ModuleRouteHeartService.getInstance().saveHeartStatus(sleepStatusData.getContent());
                }
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(sleepStatusData.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e(" downloadSleepStatusByDate error  " + e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getWifiScaleData(long uid, int dt, String st) {
        super.getWifiScaleData(uid, dt, st);
        getApiService("weight_sport").getScaleWeight(uid, dt, st).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ScaleDataResp>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ScaleDataResp scaleDataResp) throws Exception {
                KLog.i("体重秤下载成功------->");
                ModuleRouteWeightService.getInstance().saveNetWeight(scaleDataResp);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(scaleDataResp.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.i("体重秤数据下载失败------->" + e.getMessage());
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getWifiScaleRWData(long uid, long weightDate, String scaleid) {
        super.getWifiScaleRWData(uid, weightDate, scaleid);
        getApiService("weight_sport").getWifiScaleRWData(uid, weightDate, scaleid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<WifiScaleRWResp>() {
            /* access modifiers changed from: protected */
            public void onSuccess(WifiScaleRWResp wifiScaleRWResp) throws Exception {
                KLog.i("getWifiScaleRWData download ok------->");
                if (wifiScaleRWResp.getRetCode() != 0 || wifiScaleRWResp.getData() == null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(0));
                    return;
                }
                ModuleRouteWeightService.getInstance().saveNetRWWeight(wifiScaleRWResp);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(wifiScaleRWResp.getData().size()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.i("getWifiScaleRWData download error------->" + e.getMessage());
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void bindWifiScale(MacBandS2Bean macBandS2Bean) {
        super.bindWifiScale(macBandS2Bean);
        getApiService("weight_device").bindWifiScale(macBandS2Bean).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                KLog.i("s2wifi bind ok------->");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.i("s2wifi  bind error------->" + e.getMessage());
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void getFatigueData(long uid, int pageSize, String benchmarkDate) {
        super.getFatigueData(uid, pageSize, benchmarkDate);
        getApiService(DownType.SPORT).getFatigue(uid, pageSize, benchmarkDate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<FatigueRetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FatigueRetCode fatigueRetCode) throws Exception {
                if (fatigueRetCode.getRetCode() == 0) {
                    ModuleRouteFatigueService.getIsnatnce().saveFatigueDatas(fatigueRetCode.getData());
                }
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(fatigueRetCode.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void sendFatigue(FatigueSend send) {
        super.sendFatigue(send);
        getApiService(DownType.SPORT).sendFatigue(send).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downHeartHoursData(long uid, int dt, String st, final DateUtil dateUtil) {
        super.downHeartHoursData(uid, dt, st, dateUtil);
        getApiService(DownType.SPORT).heartHoursDownRepo(uid, dt, st).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<HeartHoursNewDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(final HeartHoursNewDownCode heartHoursNewDownCode) throws Exception {
                if (heartHoursNewDownCode.getRetCode() == 0) {
                    KLog.e("heartHoursNewDownCode.getContent() " + heartHoursNewDownCode.getContent().size());
                    Iterator it = heartHoursNewDownCode.getContent().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        HeartHoursDownCode heartHoursDownCode = (HeartHoursDownCode) it.next();
                        if (DateUtil.isSameDay(new Date(), new Date(dateUtil.getTimestamp()))) {
                            KLog.e("找到今天的 存储 ");
                            List<HeartHoursDownCode> content = new ArrayList<>();
                            content.add(heartHoursDownCode);
                            ModuleRouteHeartService.getInstance().saveNetHoursData(content);
                            heartHoursNewDownCode.getContent().remove(heartHoursDownCode);
                            break;
                        }
                    }
                    SingleThreadUtil.getLogSingleThread().execute(new Runnable() {
                        public void run() {
                            KLog.e("子线程 存储其余的 " + heartHoursNewDownCode.getContent().size());
                            ModuleRouteHeartService.getInstance().saveNetHoursData(heartHoursNewDownCode.getContent());
                        }
                    });
                }
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(heartHoursNewDownCode.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("downHeartHoursData  " + OverseasClient.this.mCallback);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void heartDownRepo(long uid, int ds, String st, DateUtil dateUtil) {
        super.heartDownRepo(uid, ds, st, dateUtil);
        KLog.e("no2855-->heartDownRepo " + ds + " == " + st);
        getApiService(DownType.SPORT).heartDownRepo(uid, ds, st).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<HeartDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(HeartDownCode heartDownCode) throws Exception {
                KLog.d("no2855UpHeartHoursData  " + JsonUtils.toJson(heartDownCode));
                List<HeartDownData1> temp_dates = new ArrayList<>();
                for (HeartDownData1 heartDownData1 : heartDownCode.getContent()) {
                    temp_dates.add(heartDownData1);
                }
                ModuleRouteHeartService.getInstance().saveHeartSports51(temp_dates);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(heartDownCode.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void UpHeartHoursData(long uid, List<HeartHoursData> heartHoursDataList) {
        super.UpHeartHoursData(uid, heartHoursDataList);
        getApiService(DownType.SPORT).heartHoursUpRepo(uid, heartHoursDataList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                KLog.d("UpHeartHoursData  " + retCode);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void uploadHeartSportData(final HeartUpSend unUploadHeartSportsDatas) {
        super.uploadHeartSportData(unUploadHeartSportsDatas);
        if (unUploadHeartSportsDatas.getContent() != null && unUploadHeartSportsDatas.getContent().size() != 0) {
            getApiService(DownType.SPORT).heartUpRepo(unUploadHeartSportsDatas.getUid(), unUploadHeartSportsDatas.getContent()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
                /* access modifiers changed from: protected */
                public void onSuccess(RetCode retCode) throws Exception {
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                    }
                    if (retCode.getRetCode() == 0) {
                        ModuleRouteHeartService.getInstance().updateUnUpload1HeartSportDatas(unUploadHeartSportsDatas.getUid());
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onFail(e);
                    }
                }
            });
        }
    }

    public void uploadEcgSportData(List<EcgUploadNet> data) {
        super.uploadEcgSportData(data);
        if (data != null && data.size() > 0) {
            EcgUploadList list = new EcgUploadList();
            list.setData(data);
            getApiService("nggservice").upLoadEcgData(list).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
                /* access modifiers changed from: protected */
                public void onSuccess(RetCode retCode) throws Exception {
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onFail(e);
                    }
                }
            });
        }
    }

    public void getSupportsByName() {
        super.getSupportsByName();
        getApiService("device").getSupportsByName().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<DevSupportByNameCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(DevSupportByNameCode code) throws Exception {
                KLog.e("下载supportsByName列表成功。。。");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(code.getRetCode()));
                }
                List<SupportsByNameItem> supportsByNameItems = code.getList();
                if (supportsByNameItems != null && supportsByNameItems.size() != 0) {
                    for (SupportsByNameItem item : supportsByNameItems) {
                        TB_DevSupportsByName tb_devSupportsByName = new TB_DevSupportsByName();
                        tb_devSupportsByName.setName_key(item.getName_key());
                        tb_devSupportsByName.setDev_type(item.getDev_type());
                        tb_devSupportsByName.setSupports(item.getSupports());
                        tb_devSupportsByName.saveOrUpdate("name_key=?", item.getName_key());
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("下载supportsByName列表失败。。。");
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void uploadGpsFile(long uid, String st, String ed, String source, File file) {
        getApiService("log_up").uploadGpsFile(uid, st, ed, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (OverseasClient.this.mCallback != null && upSDFileCode != null) {
                    KLog.e("no2855--> 文件上传-->");
                    OverseasClient.this.mCallback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadPageSportGps(long uid, int sportType, String startTime, int size) {
        getApiService("nggservice").downloadPageSportGps(uid, sportType, startTime, size).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SportGpsCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SportGpsCode retCode) throws Exception {
                if (retCode != null && retCode.getReturnCode() == 0) {
                    ModuleRouteSportService.getInstance().saveSportGpsTBFromNet(retCode);
                }
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null || retCode.getData() == null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(0));
                } else {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getData().size()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadPageSportBall(long uid, String startTime, int size) {
        getApiService("nggservice").downloadPageSportBall(uid, startTime, size).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SportBallCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SportBallCode retCode) throws Exception {
                if (retCode != null && retCode.getReturnCode() == 0) {
                    ModuleRouteSportService.getInstance().saveSportBallTBFromNet(retCode);
                }
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null || retCode.getData() == null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(0));
                } else {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getData().size()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadPageSportOther(long uid, String startTime, int size) {
        getApiService("nggservice").downloadPageSportOther(uid, startTime, size).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SportOtherCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SportOtherCode retCode) throws Exception {
                if (retCode != null && retCode.getReturnCode() == 0) {
                    ModuleRouteSportService.getInstance().saveSportOtherTBFromNet(retCode);
                }
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null || retCode.getData() == null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(0));
                } else {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getData().size()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadPageSportSwim(long uid, String startTime, int size) {
        getApiService("nggservice").downloadPageSportSwim(uid, startTime, size).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SportSwimCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SportSwimCode retCode) throws Exception {
                if (retCode != null && retCode.getReturnCode() == 0) {
                    ModuleRouteSportService.getInstance().saveSportSwimTBFromNet(retCode);
                }
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null || retCode.getData() == null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(0));
                } else {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getData().size()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void uploadSport28(Sport28Send sport28Send) {
        getApiService("nggservice").uploadSport28(sport28Send).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void get28Data(long uid, int ds, String st, DateUtil dateUtil) {
        for (int i = 0; i < 3; i++) {
            getApiService(DownType.SPORT).sportDownRepo(uid, 10, dateUtil.getSyyyyMMddDate()).observeOn(Schedulers.io()).map(new Function<SportDownCode, SportDownCode>() {
                public SportDownCode apply(SportDownCode code) throws Exception {
                    return code;
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<SportDownCode>() {
                /* access modifiers changed from: protected */
                public void onSuccess(SportDownCode code) throws Exception {
                    KLog.e("下载28成功-->");
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onSuccess(code);
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    KLog.e("下载28失败-->");
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onFail(e);
                    }
                }
            });
            dateUtil.addDay(10);
        }
    }

    public void downloadSport28(long uid, DateUtil dateUtil) {
        dateUtil.addDay(1 - dateUtil.getDay());
        DateUtil dateToday = new DateUtil();
        for (int i = 0; i < 3 && dateUtil.getZeroTime() <= dateToday.getUnixTimestamp(); i++) {
            int num = 10;
            if (i == 2) {
                num = 11;
            }
            String st1 = dateUtil.getY_M_D_H_M_S();
            dateUtil.addDay(num);
            String ed1 = dateUtil.getY_M_D_H_M_S();
            KLog.e("no2855 28下载的数据时间: " + st1 + "  == " + ed1);
            getApiService("nggservice").downloadSport28(uid, st1, ed1).observeOn(Schedulers.io()).map(new Function<Sport28Code, Sport28Code>() {
                public Sport28Code apply(Sport28Code code) throws Exception {
                    ModuleRouteSportService.getInstance().downloadTBSport(code);
                    return code;
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<Sport28Code>() {
                /* access modifiers changed from: protected */
                public void onSuccess(Sport28Code code) throws Exception {
                    KLog.e("下载28成功-->");
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onSuccess(code);
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    KLog.e("下载28失败-->");
                    if (OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onFail(e);
                    }
                }
            });
        }
    }

    public void downloadAllSportGps(long uid) {
        getApiService("nggservice").downloadAllSportGps(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<AllSportGpsCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(AllSportGpsCode retCode) throws Exception {
                if (retCode != null && retCode.getReturnCode() == 0) {
                    new DisposeSportAllData().saveTBAllGps(retCode);
                }
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null) {
                    OverseasClient.this.mCallback.onFail(null);
                } else {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadAllSportBall(long uid) {
        getApiService("nggservice").downloadAllSportBall(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<AllSportBallCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(AllSportBallCode retCode) throws Exception {
                new DisposeSportAllData().saveTBAllBall(retCode);
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null) {
                    OverseasClient.this.mCallback.onFail(null);
                } else {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadAllSportOther(long uid) {
        getApiService("nggservice").downloadAllSportOther(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<AllSportOtherCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(AllSportOtherCode retCode) throws Exception {
                new DisposeSportAllData().saveTBAllOther(retCode);
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null) {
                    OverseasClient.this.mCallback.onFail(null);
                } else {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadAllSportSwim(long uid) {
        getApiService("nggservice").downloadAllSportSwim(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<AllSportSwimCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(AllSportSwimCode retCode) throws Exception {
                new DisposeSportAllData().saveTBAllSwim(retCode);
                if (OverseasClient.this.mCallback == null) {
                    return;
                }
                if (retCode == null) {
                    OverseasClient.this.mCallback.onFail(null);
                } else {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void updateEcgNote(EcgDataNoteNet net) {
        super.updateEcgNote(net);
        getApiService("nggservice").upDateEcgNote(net).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void updateEcgAiResult(EcgDataAiResult result) {
        super.updateEcgAiResult(result);
        getApiService("nggservice").upDateEcgAi(result).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(retCode.getReturnCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downLoadEcgData(final long uid, String year, String month, String day) {
        super.downLoadEcgData(uid, year, month, day);
        getApiService("nggservice").downEcgData(uid, year, month, day).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<EcgDownList>() {
            /* access modifiers changed from: protected */
            public void onSuccess(EcgDownList ecgDownList) throws Exception {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(Integer.valueOf(ecgDownList.getReturnCode()));
                }
                if (ecgDownList != null && ecgDownList.getReturnCode() == 0 && ecgDownList.getData() != null && ecgDownList.getData().size() > 0) {
                    for (int i = 0; i < ecgDownList.getData().size(); i++) {
                        try {
                            EcgDownLoadNet eln = (EcgDownLoadNet) ecgDownList.getData().get(i);
                            EcgViewDataBean bean = new EcgViewDataBean();
                            bean.setUid(eln.getUid());
                            bean.setNote(eln.getNote());
                            bean.setUrl(eln.getUrl());
                            bean.setData_from(eln.getData_from());
                            bean.setDate(eln.getStart_time());
                            bean.setHeartrate(eln.getHr());
                            bean.setAi_result(eln.getAi_result());
                            bean.set_uploaded(1);
                            bean.setUnixTime(new DateUtil(DateUtil.String2Date("yyyy-MM-dd HH:mm:ss", eln.getStart_time())).getUnixTimestamp());
                            OverseasClient.this.downLoadEcgFile(eln.getUrl(), uid, eln.getData_from(), eln.getStart_time(), bean);
                            ModuleRouterEcgService.getInstance().saveECGData(bean);
                            KLog.i("--------------------------" + JsonUtils.toJson(bean));
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }
                List<EcgViewDataBean> list = ModuleRouterEcgService.getInstance().queryEcgDataByUid(ContextUtil.getLUID());
                if (list != null && list.size() > 0) {
                    HealthDataEventBus.updateHealthEcg();
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void hasEcgDataNet(final long uid, String year, String month) {
        super.hasEcgDataNet(uid, year, month);
        getApiService("nggservice").downEcgHasData(uid, year, month).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<EcgHasDataNet>() {
            /* access modifiers changed from: protected */
            public void onSuccess(EcgHasDataNet ecgHasDataNet) throws Exception {
                if (ecgHasDataNet != null && ecgHasDataNet.getData().size() > 0) {
                    for (int i = 0; i < ecgHasDataNet.getData().size(); i++) {
                        DateUtil d1 = new DateUtil(DateUtil.String2Date("yyyy-MM-dd HH:mm:ss", ((EcgHasData) ecgHasDataNet.getData().get(i)).getDate()));
                        if (ModuleRouterEcgService.getInstance().checkHasDataByUid(uid, d1) <= 0) {
                            KLog.i("-----没有数据去下载数据------");
                            OverseasClient.this.downLoadEcgData(uid, d1.getYear() + "", d1.getMonth() + "", d1.getDay() + "");
                        }
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void hasSport28DataNet(long uid, int year, int month) {
        final long j = uid;
        final int i = year;
        final int i2 = month;
        getApiService("nggservice").downSport28HasData(uid, year, month).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<Sport28MonthCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(Sport28MonthCode code) throws Exception {
                if (code != null) {
                    boolean isOk = false;
                    TB_has28Days_monthly tb_has28Days_monthly = new TB_has28Days_monthly();
                    tb_has28Days_monthly.setUid(j);
                    tb_has28Days_monthly.setYear(i);
                    tb_has28Days_monthly.setMonth(i2);
                    if (code.getReturnCode() == 10404) {
                        isOk = true;
                        tb_has28Days_monthly.setInfo("");
                        tb_has28Days_monthly.saveOrUpdate("uid=? and year=? and month=?", j + "", i + "", i2 + "");
                    } else if (code.getReturnCode() == 0) {
                        isOk = true;
                        List<Sport28Index> sport28IndexList = code.getData().getSportDataIndex();
                        List<RspInfoModel> rspInfoModels = new ArrayList<>();
                        for (Sport28Index sport28Index : sport28IndexList) {
                            RspInfoModel rspInfoModel = new RspInfoModel();
                            rspInfoModel.setDate(sport28Index.getDate());
                            rspInfoModel.setFrom(sport28Index.getData_from());
                            if (!rspInfoModels.contains(rspInfoModel)) {
                                rspInfoModels.add(rspInfoModel);
                            }
                        }
                        tb_has28Days_monthly.setInfo(JsonTool.toJson(rspInfoModels));
                        tb_has28Days_monthly.saveOrUpdate("uid=? and year=? and month=?", j + "", i + "", i2 + "");
                    }
                    if (OverseasClient.this.mCallback == null) {
                        return;
                    }
                    if (isOk) {
                        OverseasClient.this.mCallback.onSuccess(code);
                    } else {
                        OverseasClient.this.mCallback.onFail(new Throwable());
                    }
                } else if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(new Throwable());
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void uploadR1Data(long uid, String st, String et, String data_from, File file) {
        getApiService("nggservice").r1DataRepo(uid, st, et, data_from, Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new OverseasCallWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode retCode) {
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (OverseasClient.this.mCallback != null) {
                    OverseasClient.this.mCallback.onFail(e);
                }
            }
        });
    }

    public void downloadAllBlood(long uid, String st, String et) {
        super.downloadAllBlood(uid, st, et);
        getApiService("nggservice").downloadBlood(uid, st, et).enqueue(new Callback<BpDownData1>() {
            public void onResponse(Call<BpDownData1> call, Response<BpDownData1> response) {
                try {
                    BpDownData1 bpDownData1 = (BpDownData1) response.body();
                    if (bpDownData1 != null && bpDownData1.getReturnCode() == 0 && bpDownData1.getData() != null && !bpDownData1.getData().isEmpty()) {
                        for (int i = 0; i < bpDownData1.getData().size(); i++) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date date = new Date();
                            try {
                                date = dateFormat.parse(((BpDownData) bpDownData1.getData().get(i)).getRecord_date().toString());
                            } catch (ParseException e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                            ModuleRouteSportService.getInstance().saveBloodData(UserConfig.getInstance().getNewUID(), ((BpDownData) bpDownData1.getData().get(i)).getData_from(), date.getTime() / 1000, ((BpDownData) bpDownData1.getData().get(i)).getDbp(), ((BpDownData) bpDownData1.getData().get(i)).getSbp());
                        }
                    }
                    if (OverseasClient.this.mCallback != null) {
                        KLog.e("l808 下载  血压数据 " + bpDownData1);
                        OverseasClient.this.mCallback.onSuccess(bpDownData1);
                        return;
                    }
                    OverseasClient.this.mCallback.onFail(null);
                } catch (Exception e2) {
                    OverseasClient.this.mCallback.onFail(e2);
                }
            }

            public void onFailure(Call<BpDownData1> call, Throwable t) {
                OverseasClient.this.mCallback.onFail(t);
                KLog.e("111 downloadBlood  data fail");
            }
        });
    }

    public void downloadBPcoverage(long uid, int year, int mouth) {
        super.downloadBPcoverage(uid, year, mouth);
        getApiService("nggservice").downloadBPcoverage(uid, year, mouth).enqueue(new Callback<bpCoverageDown>() {
            public void onResponse(Call<bpCoverageDown> call, Response<bpCoverageDown> response) {
                try {
                    if (response.body() != null && ((bpCoverageDown) response.body()).getReturnCode() == 0 && OverseasClient.this.mCallback != null) {
                        OverseasClient.this.mCallback.onSuccess(response.body());
                    }
                } catch (Exception e) {
                }
            }

            public void onFailure(Call<bpCoverageDown> call, Throwable t) {
                KLog.e("111 downloadBPcoverage  data fail");
            }
        });
    }

    public void uploadBlooddata(BloodDataUpload bloodDataUpload) {
        super.uploadBlooddata(bloodDataUpload);
        getApiService("nggservice").uploadBlood(bloodDataUpload).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (OverseasClient.this.mCallback != null) {
                    KLog.e("l808 uploadBlooddata  data succ");
                    OverseasClient.this.mCallback.onSuccess(response.body());
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                KLog.e("l808 uploadBlooddata  data fail");
            }
        });
    }
}
