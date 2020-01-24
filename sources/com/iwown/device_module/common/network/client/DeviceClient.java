package com.iwown.device_module.common.network.client;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.BaseNetUrl;
import com.iwown.data_link.af.AfResultBean;
import com.iwown.data_link.blood.BpPreUpload;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.weight.WifiScaleRWResp;
import com.iwown.data_link.weight.WifiScaleReq;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.iwown.device_module.common.network.CallbackWrapper;
import com.iwown.device_module.common.network.LogInterceptor;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.api.DeviceApi;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.config.BaseNetParams;
import com.iwown.device_module.common.network.config.BaseNetParams.App_Info;
import com.iwown.device_module.common.network.data.req.DeviceSettingsSend;
import com.iwown.device_module.common.network.data.req.FactoryVersion;
import com.iwown.device_module.common.network.data.req.FamilyNoAccountAddRequest;
import com.iwown.device_module.common.network.data.req.FamilyNoAccountDelRequest;
import com.iwown.device_module.common.network.data.req.FwUpdate;
import com.iwown.device_module.common.network.data.req.SDFileSend;
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
import com.iwown.device_module.common.network.data.resp.BandScaleInfo;
import com.iwown.device_module.common.network.data.resp.BloodpressureCode;
import com.iwown.device_module.common.network.data.resp.DeviceSettingRemote;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode;
import com.iwown.device_module.common.network.data.resp.F1SleepData;
import com.iwown.device_module.common.network.data.resp.FamilyNoAccount;
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
import com.iwown.device_module.common.network.utils.HttpLogUtils;
import com.iwown.device_module.common.network.utils.HttpLogUtils.Level;
import com.iwown.device_module.common.sql.weight.TB_S2WifiConfig;
import com.iwown.device_module.common.sql.weight.TB_WeightUser;
import com.iwown.device_module.common.sql.weight.TB_rawWeightData;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.Utils;
import com.iwown.device_module.device_operation.bean.ModeItems;
import com.iwown.device_module.device_setting.wifi_scale.data.WifiScaleData;
import com.iwown.device_module.device_setting.wifi_scale.eventbus.EventbusFinish;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceClient {
    public static String App_Name;
    public static int App_Version;
    public static String BaseLogUpUrl;
    public static String BaseNggserviceUrl;
    public static String BaseServer1;
    public static String BaseServer2;
    public static String BaseServer3;
    public static String NggServer;
    private final String AF = "AFRESULT";
    private final String Device = "device";
    private final String Log_Up = "Log";
    private final String NGG = "nggservice";
    private final String Other = "device";
    private final String Sport = "Sport";
    private final String User = "User";
    MyCallback callback;
    private DeviceApi deviceApi;
    private final String healthy_log_up = "healthy_log";
    private DeviceApi logUpApi;
    private DeviceApi otherApi;
    private DeviceApi sportApi;
    private DeviceApi userApi;

    static {
        App_Name = App_Info.app_name;
        App_Version = App_Info.version;
        BaseServer1 = BaseNetParams.Base_API_AMAZON_PROD;
        BaseServer2 = BaseNetParams.Base_API_AMAZON_PROD;
        BaseServer3 = BaseNetParams.Base_API_AMAZON_PROD;
        NggServer = BaseNetParams.AMAZON_ngs_Dev;
        BaseLogUpUrl = BaseNetUrl.Log_P1_Upload_API_AMAZON_DEV;
        BaseNggserviceUrl = "";
        if (AppConfigUtil.isHealthy()) {
            BaseServer1 = "https://api1.iwown.com/venus/";
            BaseServer2 = "https://api2.iwown.com/venus/";
            BaseServer3 = "https://api3.iwown.com/venus/";
            BaseLogUpUrl = "http://api2.iwown.com:7790/";
            App_Name = App_Info.app_name_healthy;
            App_Version = App_Info.version_healthy;
            NggServer = BaseNetUrl.API1_ngs_Dev;
            return;
        }
        BaseServer1 = BaseNetParams.Base_API_AMAZON_PROD;
        BaseServer2 = BaseNetParams.Base_API_AMAZON_PROD;
        BaseServer3 = BaseNetParams.Base_API_AMAZON_PROD;
        BaseLogUpUrl = BaseNetParams.Log_P1_Upload_API_AMAZON_PROD;
        BaseNggserviceUrl = BaseNetParams.AMAZON_ngs_Dev;
        App_Name = App_Info.app_name;
        App_Version = App_Info.version;
        NggServer = BaseNetParams.AMAZON_ngs_Dev;
    }

    private DeviceClient() {
    }

    public DeviceClient(MyCallback callback2) {
        this.callback = callback2;
    }

    private DeviceApi getApiService(String urlType) {
        String BaseDeviceUrl = BaseServer3 + "deviceservice/";
        String BaseSportUrl = BaseServer1 + "sportservice/";
        String BaseUserUrl = BaseServer1 + "userservice/";
        if ("device".equals(urlType)) {
            if (this.deviceApi == null) {
                this.deviceApi = (DeviceApi) new Builder().baseUrl(BaseDeviceUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.deviceApi;
        } else if ("Sport".equals(urlType)) {
            if (this.sportApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.sportApi = (DeviceApi) new Builder().baseUrl(BaseSportUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.sportApi;
        } else if ("User".equals(urlType)) {
            if (this.userApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.userApi = (DeviceApi) new Builder().baseUrl(BaseUserUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.userApi;
        } else if ("Log".equals(urlType)) {
            if (this.logUpApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.logUpApi = (DeviceApi) new Builder().baseUrl(BaseLogUpUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.logUpApi;
        } else if ("nggservice".equals(urlType)) {
            if (this.logUpApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.logUpApi = (DeviceApi) new Builder().baseUrl(NggServer).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.logUpApi;
        } else if ("AFRESULT".equals(urlType)) {
            if (this.logUpApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.logUpApi = (DeviceApi) new Builder().baseUrl("http://api1.iwown.com:8889/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.logUpApi;
        } else {
            if (this.otherApi == null) {
                new HttpLogUtils().setLevel(Level.CUSTOM);
                this.otherApi = (DeviceApi) new Builder().baseUrl(BaseDeviceUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
            }
            return this.otherApi;
        }
    }

    private DeviceApi getNgsApi() {
        new HttpLogUtils().setLevel(Level.CUSTOM);
        return (DeviceApi) new Builder().baseUrl(NggServer).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
    }

    private DeviceApi getHealthyLogApi() {
        new HttpLogUtils().setLevel(Level.CUSTOM);
        String healthtLog = BaseServer2 + "fileservice/";
        return (DeviceApi) new Builder().baseUrl(healthtLog).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).build()).build().create(DeviceApi.class);
    }

    public void remoteDeviceSetting(DeviceSettingsSend req) {
        getApiService("device").downDevSettingsRepo(req.getApp(), req.getApp_platform(), req.getModel(), req.getVersion()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<DeviceSettingsDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(DeviceSettingsDownCode code) throws Exception {
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(code);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void getWifiScaleData(long weightDate, String scaleid, int type) {
        getApiService("Sport").getWifiScaleData(ContextUtil.getLUID(), weightDate, scaleid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<WifiScaleRWResp>() {
            /* access modifiers changed from: protected */
            public void onSuccess(WifiScaleRWResp resp) {
                try {
                    if (resp.getRetCode() == 0 || resp.getRetCode() == 10404) {
                        WifiScaleData.getInstance().saveRawWeight(resp);
                        if (DeviceClient.this.callback != null) {
                            DeviceClient.this.callback.onSuccess(Integer.valueOf(resp.getRetCode()));
                        }
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void archiveData(final WifiScaleReq body, int size) {
        getApiService("Sport").postArchiveData(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                WifiScaleData.getInstance().saveArchiveData(body, retCode.getRetCode());
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                }
            }
        });
    }

    public void cleanWifi(long uid, String scaleid, ScaleCleanWifi wifi) {
        getApiService("device").postCleanWifi(uid, scaleid, wifi).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upScaleData(long uid, List<T_Weight> list) {
        getApiService("Sport").postWeightProfile(uid, list).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                KLog.i("retcode" + retCode.getRetCode());
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.i("retcode" + e.toString());
            }
        });
    }

    public void obsoleteData(final ScaleObsoleteReq req, final int type) {
        getApiService("Sport").postObsoleteData(req).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (retCode.getRetCode() == 0) {
                    for (long valueOf : req.getWeightid()) {
                        DataSupport.deleteAll(TB_rawWeightData.class, "weightid=?", String.valueOf(valueOf));
                    }
                    if (type == 2) {
                        EventBus.getDefault().post(new EventbusFinish(5));
                    } else if (type == 1) {
                        EventBus.getDefault().post(new EventbusFinish(3));
                    }
                } else {
                    EventBus.getDefault().post(new EventbusFinish(5));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void scaleSetUnit(int unit, String scaleid) {
        getApiService("device").scaleSetUnit(unit, scaleid, new ScaleCleanWifi()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (retCode != null) {
                    try {
                        if (DeviceClient.this.callback != null) {
                            DeviceClient.this.callback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void bindScaleInfo(final long uid) {
        getApiService("device").bindScaleInfo(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<BandScaleInfo>() {
            /* access modifiers changed from: protected */
            public void onSuccess(BandScaleInfo scaleInfo) throws Exception {
                if (scaleInfo != null) {
                    try {
                        if (scaleInfo.getData() != null) {
                            S2WifiUtils.updateMac(scaleInfo.getData().getUid(), scaleInfo.getData().getMac());
                            NetFactory.getInstance().getClient(null).scaleSetUnit(2, S2WifiUtils.wifiScaleMac(uid + ""));
                            HealthDataEventBus.updateHealthWeightEvent();
                            if (DeviceClient.this.callback != null) {
                                DeviceClient.this.callback.onSuccess(Integer.valueOf(scaleInfo.getRetCode()));
                            }
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void unBindScale(ScaleCleanWifi wifi) {
        getApiService("device").unBindScale(wifi).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (retCode != null && retCode.getRetCode() == 0) {
                    DataSupport.deleteAll(TB_S2WifiConfig.class, "uid=?", String.valueOf(ContextUtil.getUID()));
                    EventBus.getDefault().post(new EventbusFinish(1));
                    EventBus.getDefault().post(new EventbusFinish(14));
                    if (DeviceClient.this.callback != null) {
                        DeviceClient.this.callback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void commitNoAccountProfile(FamilyNoAccountAddRequest request) {
        getApiService("User").postFamilyNoAccountAdd(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<FamilyReturnMessage>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FamilyReturnMessage familyReturnMessage) throws Exception {
                if (familyReturnMessage != null) {
                    try {
                        if (DeviceClient.this.callback != null) {
                            DeviceClient.this.callback.onSuccess(Integer.valueOf(familyReturnMessage.getRetCode()));
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void editNoAccountProfile(FamilyNoAccountAddRequest request) {
        getApiService("User").postFamilyNoAccountEdit(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<FamilyReturnMessage>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FamilyReturnMessage familyReturnMessage) throws Exception {
                if (familyReturnMessage != null) {
                    try {
                        if (DeviceClient.this.callback != null) {
                            DeviceClient.this.callback.onSuccess(Integer.valueOf(familyReturnMessage.getRetCode()));
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void deleteNoAccountProfile(final long familyUid) {
        FamilyNoAccountDelRequest account = new FamilyNoAccountDelRequest();
        account.setUid(ContextUtil.getLUID());
        account.setFamilyUid(familyUid);
        getApiService("User").postFamilyNoAccountDelete(account).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<FamilyReturnMessage>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FamilyReturnMessage familyReturnMessage) throws Exception {
                if (familyReturnMessage != null) {
                    try {
                        DataSupport.deleteAll(TB_WeightUser.class, "uid=?", String.valueOf(familyUid));
                        if (DeviceClient.this.callback != null) {
                            DeviceClient.this.callback.onSuccess(Integer.valueOf(familyReturnMessage.getRetCode()));
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    private void initScaleUsers() {
        int sex;
        DataSupport.deleteAll(TB_WeightUser.class, new String[0]);
        UserInfo info = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
        KLog.i("ScaleUserInfo" + JsonUtils.toJson(info));
        if (info != null && info != null && !TextUtils.isEmpty(info.nickName)) {
            TB_WeightUser user = new TB_WeightUser();
            user.setUid(info.uid);
            if (info.isMale) {
                sex = 1;
            } else {
                sex = 0;
            }
            user.setGender(sex);
            user.setHeight((float) info.height);
            user.setBirthday(info.birthday);
            user.setName(info.nickName);
            user.saveOrUpdate("uid=?", String.valueOf(ContextUtil.getUID()));
        }
    }

    public void getNoAccountList() {
        initScaleUsers();
        getApiService("User").getFamilyNoAccountList(ContextUtil.getLUID()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<FamilyNoAccountList>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FamilyNoAccountList familyNoAccountList) throws Exception {
                if (familyNoAccountList != null && familyNoAccountList.getRetCode() == 0) {
                    for (FamilyNoAccount account : familyNoAccountList.getData()) {
                        TB_WeightUser user = new TB_WeightUser();
                        user.setUid(account.getFamilyUid());
                        user.setGender(account.getGender());
                        user.setHeight(account.getHeight());
                        user.setBirthday(account.getBirthday());
                        user.setName(account.getRelation());
                        user.saveOrUpdate("uid=?", String.valueOf(account.getFamilyUid()));
                    }
                    if (DeviceClient.this.callback != null) {
                        DeviceClient.this.callback.onSuccess(Integer.valueOf(familyNoAccountList.getRetCode()));
                    }
                } else if (familyNoAccountList != null && DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(Integer.valueOf(familyNoAccountList.getRetCode()));
                }
            }
        });
    }

    public void checkFirmwareUpdate(FwUpdate fwupdateSend) {
        getApiService("device").fwupdateRepo(fwupdateSend).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<FwUpdateReturnMessage>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FwUpdateReturnMessage fwUpdateReturnMessage) throws Exception {
                if (fwUpdateReturnMessage != null && DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(fwUpdateReturnMessage);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void uploadUpgradeRepo(Upgrade upgradeSend) {
        getApiService("device").uploadUpgradeRepo(upgradeSend).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void DownLoadUpgradeRepo(long uid) {
        getApiService("device").downUpgradeRepo(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<FirmwareDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(FirmwareDownCode firmwareDownCode) throws Exception {
                if (DeviceClient.this.callback != null && firmwareDownCode != null) {
                    DeviceClient.this.callback.onSuccess(firmwareDownCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void uploadDeviceRef(DeviceSettingRemote deviceSetting) {
        if (deviceSetting != null && !TextUtils.isEmpty(deviceSetting.getModel()) && deviceSetting.getSetting() != null && deviceSetting.getSetting().size() > 0) {
            getApiService("device").uploadPref(deviceSetting).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
                /* access modifiers changed from: protected */
                public void onSuccess(RetCode retCode) throws Exception {
                    if (DeviceClient.this.callback != null && retCode != null) {
                        DeviceClient.this.callback.onSuccess(Integer.valueOf(retCode.getRetCode()));
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    if (DeviceClient.this.callback != null) {
                        DeviceClient.this.callback.onFail(e);
                    }
                }
            });
        }
    }

    public void downloadDeviceRef(long uid, String model) {
        getApiService("device").downloadPref(uid, model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<PrefServerResponse>() {
            /* access modifiers changed from: protected */
            public void onSuccess(PrefServerResponse prefServerResponse) throws Exception {
                if (DeviceClient.this.callback != null && prefServerResponse != null) {
                    KLog.i("prefServerResponse" + JsonUtils.toJson(prefServerResponse));
                    DeviceClient.this.callback.onSuccess(prefServerResponse);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSd_61_File(long uid, String recordDate, String source, File file) {
        getApiService("Log").uploadSDFile_61_data(uid, recordDate, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSd_68_File(long uid, String recordDate, String source, File file) {
        getApiService("Log").uploadSDFile_68_data(uid, recordDate, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void downSleepData(String date, boolean isHeartOn) {
        getApiService("Sport").down61SleepData(ContextUtil.getLUID(), date, ContextUtil.getDeviceNameNoClear(), isHeartOn ? 1 : 0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<F1SleepData>() {
            /* access modifiers changed from: protected */
            public void onSuccess(F1SleepData f1SleepData) throws Exception {
                if (DeviceClient.this.callback != null && f1SleepData != null) {
                    DeviceClient.this.callback.onSuccess(f1SleepData);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upGnssDataToServer(String date, File file) {
        String str = date;
        getApiService("Log").uploadSDFile_62_data(ContextUtil.getLUID(), str, ContextUtil.getDeviceNameNoClear(), Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void deviceClassId_1(int app, String region) {
        if (region == null || region.equalsIgnoreCase("")) {
            region = "us";
        }
        getApiService("device").class1modelist(app, region).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ModeTypes>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ModeTypes modeTypes) throws Exception {
                if (modeTypes != null && modeTypes.getRetCode() == 0) {
                    PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Types, JsonUtils.toJson(modeTypes));
                } else if (TextUtils.isEmpty(PrefUtil.getString(SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Types))) {
                    PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Types, Utils.getFromAssets(SyncData.context, "modesdklist1default.txt"));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (TextUtils.isEmpty(PrefUtil.getString(SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Types))) {
                    PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Types, Utils.getFromAssets(SyncData.context, "modesdklist1default.txt"));
                }
            }
        });
    }

    public void deviceClassIdDetail(int app, String region) {
        if (region == null || region.equalsIgnoreCase("")) {
            region = "us";
        }
        getApiService("device").class2modelist(app, region).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ModeItems>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ModeItems modeItems) throws Exception {
                if (modeItems != null) {
                    try {
                        if (modeItems.getRetCode() == 0) {
                            PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Content, JsonUtils.toJson(modeItems));
                            PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Time, new DateUtil().getUnixTimestamp() + 600);
                            return;
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                        return;
                    }
                }
                if (TextUtils.isEmpty(PrefUtil.getString(SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Content))) {
                    PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Content, Utils.getFromAssets(SyncData.context, "modesdklist2default.txt"));
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                ThrowableExtension.printStackTrace(e);
                if (TextUtils.isEmpty(PrefUtil.getString(SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Content))) {
                    PrefUtil.save((Context) SyncData.context, SharedPreferencesAction.APP_SDK_UPDATE_Content, Utils.getFromAssets(SyncData.context, "modesdklist2default.txt"));
                }
            }
        });
    }

    public void deviceUploadFactoryVersion(FactoryVersion factoryVersion) {
        getApiService("device").uploadFactoryVersion(factoryVersion).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
            }
        });
    }

    public void upSdFile(SDFileSend sdFileSend) {
        File file = new File(sdFileSend.getFilePath());
        if (file.exists()) {
            getApiService("Log").uploadSDFileRepo(sdFileSend.getUid(), sdFileSend.getFileType(), sdFileSend.getDate(), RequestBody.create(MediaType.parse("multipart/form-data"), file)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
                /* access modifiers changed from: protected */
                public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                    if (DeviceClient.this.callback != null && upSDFileCode != null) {
                        DeviceClient.this.callback.onSuccess(upSDFileCode);
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    if (DeviceClient.this.callback != null) {
                        DeviceClient.this.callback.onFail(e);
                    }
                }
            });
        }
    }

    public void upUserDevice(UserDeviceReq userDeviceReq) {
        getApiService("device").uploadUserDevice(userDeviceReq).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<RetCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(RetCode retCode) throws Exception {
            }
        });
    }

    public void upSportHrFile(long uid, String st, String ed, String source, File file) {
        getApiService("Log").upSportHrFile(uid, st, ed, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSportR1File(long uid, String st, String ed, String source, File file) {
        getApiService("Log").upSportR1File(uid, st, ed, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSportGpsFile(long uid, String st, String ed, String source, File file) {
        getApiService("Log").upSportGpsFile(uid, st, ed, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSportGpsSegment(long uid, SportGpsSend send) {
        getNgsApi().uploadGpsSegment(uid, send).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSportBallSegment(long uid, SportBallSend send) {
        getNgsApi().uploadBallSegment(uid, send).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSportOtherSegment(long uid, SportOtherSend send) {
        getNgsApi().uploadOtherSegment(uid, send).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSportSwimSegment(long uid, SportSwimSend send) {
        getNgsApi().uploadSwimSegment(uid, send).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void upSd_ecg_File(long uid, String st, String ed, String source, File file) {
        getApiService("Log").uploadSDFile_ecg_data(uid, st, ed, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode upSDFileCode) throws Exception {
                if (DeviceClient.this.callback != null && upSDFileCode != null) {
                    DeviceClient.this.callback.onSuccess(upSDFileCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void updateSportGpsUrl(UpSportGpsUrl gpsUrl) {
        getNgsApi().updateSportGpsUrl(gpsUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void updateSportBallUrl(UpSportBallUrl ballUrl) {
        getNgsApi().updateSportBallUrl(ballUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void updateSportOtherUrl(UpSportBallUrl otherUrl) {
        getNgsApi().updateSportOtherUrl(otherUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ReturnCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ReturnCode retCode) throws Exception {
                if (DeviceClient.this.callback != null && retCode != null) {
                    DeviceClient.this.callback.onSuccess(retCode);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void get28Data(long uid, int ds, String st) {
        getApiService("Sport").sportDownRepo(uid, ds, st).observeOn(Schedulers.io()).map(new Function<SportDownCode, SportDownCode>() {
            public SportDownCode apply(SportDownCode code) throws Exception {
                return code;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<SportDownCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(SportDownCode code) throws Exception {
                KLog.e("no2855-->下载28成功-->");
                if (DeviceClient.this.callback != null && code != null) {
                    DeviceClient.this.callback.onSuccess(code);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("no2855-->下载28失败-->");
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void downAndSaveFile(String url, final String save_dir_path, final String file_name) {
        getApiService("Log").downloadFile(url, "identity").subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(new Function<ResponseBody, ResponseBody>() {
            public ResponseBody apply(ResponseBody body) throws Exception {
                if (body == null) {
                    KLog.e("no2855下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                long length = body.contentLength();
                KLog.e("no2855content-length: " + length);
                if (length <= 0) {
                    KLog.e("no2855下载的文件没有内容");
                    throw new Exception("No content in this file from server");
                }
                FileUtils.writeInputStreamToDisk2(save_dir_path, file_name, body.byteStream());
                return body;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody body) throws Exception {
                KLog.i("no2855文件下载并存储成功：" + save_dir_path + file_name);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(body);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                KLog.e("文件下载或存储失败");
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e);
                }
            }
        });
    }

    public void uploadBloodpressure(BpPreUpload prebloodData) {
        getApiService("nggservice").uploadBloodpressure(prebloodData).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody responseBody) throws Exception {
                try {
                    KLog.e("血压校准，上传服务器  成功");
                    Editor editor = SyncData.context.getSharedPreferences("bloodhistory", 0).edit();
                    editor.putInt("Isuoload", 1);
                    editor.commit();
                } catch (Exception e) {
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                try {
                    KLog.e("血压校准，上传服务器  失败");
                    Editor editor = SyncData.context.getSharedPreferences("bloodhistory", 0).edit();
                    editor.putInt("Isuoload", 0);
                    editor.commit();
                } catch (Exception e2) {
                }
            }
        });
    }

    public void downBloodpressure(long uid) {
        getApiService("nggservice").downloadBloodpressure(uid).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<BloodpressureCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(BloodpressureCode bpPreDown1) throws Exception {
                if (DeviceClient.this.callback != null) {
                    KLog.e("下载血压校准  11");
                    DeviceClient.this.callback.onSuccess(bpPreDown1);
                }
            }

            public void onError(Throwable e1) {
                super.onError(e1);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e1);
                }
            }
        });
    }

    public void getAfResult(RequestBody rriList) {
        getApiService("AFRESULT").getAfResult(rriList).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<AfResultBean>() {
            /* access modifiers changed from: protected */
            public void onSuccess(AfResultBean code) throws Exception {
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(code);
                }
            }

            public void onError(Throwable e1) {
                super.onError(e1);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e1);
                }
            }
        });
    }

    public void updateAfData(long uid, String recordDate, String source, File file) {
        getApiService("Log").uploadAfData(uid, recordDate, source, Part.createFormData("aFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<UpSDFileCode>() {
            /* access modifiers changed from: protected */
            public void onSuccess(UpSDFileCode code) throws Exception {
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onSuccess(code);
                }
            }

            public void onError(Throwable e1) {
                super.onError(e1);
                if (DeviceClient.this.callback != null) {
                    DeviceClient.this.callback.onFail(e1);
                }
            }
        });
    }
}
