package com.iwown.sport_module.service;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.blood.BpDownData1;
import com.iwown.data_link.ecg.EcgUploadNet;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.fatigue.FatigueNet;
import com.iwown.data_link.fatigue.FatigueSend;
import com.iwown.data_link.fatigue.ModuleRouteFatigueService;
import com.iwown.data_link.heart.HeartHoursData;
import com.iwown.data_link.heart.HeartUploadBean;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepUpNewSend;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.sql.SportSqlHelper;
import com.iwown.sport_module.util.SPUtils;
import com.socks.library.KLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Route(path = "/sport/data_net_service")
public class DataNetService extends Service {
    public static final String Down_Blood_Action = "down_blood_action";
    public static final String Down_Ecg_Action_Today = "Down_Ecg_Action_Today";
    public static final String Down_Fatigue_Action_Today = "down_fatigue_action_today";
    public static final String Down_Heart_Action_Today = "down_heart_action_today";
    public static final String Down_Sleep_Action_Today = "down_sleep_action_today";
    public static final String Down_Weight_Action_Today = "down_weight_action_today";
    public static final String UPLOAD_All_Action = "upload_all_action";
    private static final String UPLOAD_Blood_Action = "upload_blood_action";
    public static final String UPLOAD_Ecg_Sport_Action = "UPLOAD_Ecg_Sport_Action";
    public static final String UPLOAD_Fatigue_Action = "upload_fatigue_action";
    public static final String UPLOAD_Heart_Action = "upload_heart_action";
    private static final String UPLOAD_Heart_SPORT_Action = "upload_heart_sport_action";
    public static final String UPLOAD_Sleep_Action = "upload_sleep_action";
    private long Min_20 = 1200000;
    private MyReceiver mReceiver;

    class MyReceiver extends BroadcastReceiver {
        MyReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            char c = 65535;
            switch (action.hashCode()) {
                case -2107301348:
                    if (action.equals(DataNetService.UPLOAD_Sleep_Action)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1920066787:
                    if (action.equals(DataNetService.Down_Sleep_Action_Today)) {
                        c = 6;
                        break;
                    }
                    break;
                case -834161736:
                    if (action.equals(DataNetService.UPLOAD_Heart_SPORT_Action)) {
                        c = 3;
                        break;
                    }
                    break;
                case -290195147:
                    if (action.equals(DataNetService.UPLOAD_Ecg_Sport_Action)) {
                        c = 5;
                        break;
                    }
                    break;
                case 521654765:
                    if (action.equals(DataNetService.Down_Fatigue_Action_Today)) {
                        c = 9;
                        break;
                    }
                    break;
                case 757364459:
                    if (action.equals(DataNetService.Down_Ecg_Action_Today)) {
                        c = 10;
                        break;
                    }
                    break;
                case 1130907538:
                    if (action.equals(DataNetService.UPLOAD_All_Action)) {
                        c = 4;
                        break;
                    }
                    break;
                case 1288402754:
                    if (action.equals(DataNetService.Down_Weight_Action_Today)) {
                        c = 8;
                        break;
                    }
                    break;
                case 1597372588:
                    if (action.equals(DataNetService.UPLOAD_Fatigue_Action)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1901770189:
                    if (action.equals(DataNetService.UPLOAD_Heart_Action)) {
                        c = 2;
                        break;
                    }
                    break;
                case 2012767758:
                    if (action.equals(DataNetService.Down_Heart_Action_Today)) {
                        c = 7;
                        break;
                    }
                    break;
                case 2115650712:
                    if (action.equals(DataNetService.Down_Blood_Action)) {
                        c = 11;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    DataNetService.this.uploadSleepData();
                    return;
                case 1:
                    DataNetService.this.uploadFatigueData();
                    return;
                case 2:
                    DataNetService.this.uploadHeartData();
                    return;
                case 3:
                    DataNetService.this.uploadHeartSportData();
                    return;
                case 4:
                    DataNetService.this.uploadAlltData();
                    return;
                case 5:
                    DataNetService.this.uploadEcgData();
                    return;
                case 6:
                    DataNetService.this.downloadSleepToday();
                    return;
                case 7:
                    DataNetService.this.downloadHeartToday();
                    return;
                case 8:
                    DataNetService.this.downloadWeightToday();
                    return;
                case 9:
                    DataNetService.this.downloadFatigueToday();
                    return;
                case 10:
                    DataNetService.this.downloadEcgDataToday();
                    return;
                case 11:
                    DataNetService.this.downloadBlood();
                    return;
                default:
                    return;
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        KLog.i("DataNetService start");
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_Sleep_Action);
        filter.addAction(UPLOAD_Heart_Action);
        filter.addAction(UPLOAD_Fatigue_Action);
        filter.addAction(UPLOAD_All_Action);
        filter.addAction(UPLOAD_Heart_SPORT_Action);
        filter.addAction(UPLOAD_Ecg_Sport_Action);
        filter.addAction(UPLOAD_Blood_Action);
        filter.addAction(Down_Sleep_Action_Today);
        filter.addAction(Down_Heart_Action_Today);
        filter.addAction(Down_Weight_Action_Today);
        filter.addAction(Down_Fatigue_Action_Today);
        filter.addAction(Down_Ecg_Action_Today);
        filter.addAction(Down_Blood_Action);
        this.mReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, filter);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.i("DataNetService onStartCommand");
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
            startForeground(1, new Builder(getApplicationContext(), "11111").build());
        } else {
            startForeground(1, new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    /* access modifiers changed from: private */
    public void downloadBlood() {
        DateUtil dateUtil = new DateUtil(System.currentTimeMillis(), false);
        dateUtil.setHour(23);
        dateUtil.setMinute(59);
        dateUtil.setSecond(59);
        Calendar now = Calendar.getInstance();
        now.add(5, -30);
        NetFactory.getInstance().getClient(new MyCallback<BpDownData1>() {
            public void onSuccess(BpDownData1 bpdownData1) {
                if (bpdownData1 != null && bpdownData1.getReturnCode() == 0) {
                    KLog.e("l808   BloodUIChange   --downloadBlood data suc1");
                    HealthDataEventBus.updateAllDataEvent();
                }
            }

            public void onFail(Throwable e) {
                KLog.e("downloadBlood data fai1");
            }
        }).downloadAllBlood(UserConfig.getInstance().getNewUID(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime()), dateUtil.getY_M_D_H_M_S().toString());
    }

    /* access modifiers changed from: private */
    public void uploadHeartSportData() {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
            }

            public void onFail(Throwable e) {
            }
        }).uploadHeartSportData(ModuleRouteHeartService.getInstance().getUnUploadHeartSportsDatas(UserConfig.getInstance().getNewUID()));
    }

    /* access modifiers changed from: private */
    public void downloadFatigueToday() {
        DateUtil dateUtil = new DateUtil(System.currentTimeMillis(), false);
        dateUtil.setHour(23);
        dateUtil.setMinute(59);
        dateUtil.setSecond(59);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                HealthDataEventBus.updateHealthFatigueEvent();
            }

            public void onFail(Throwable e) {
            }
        }).getFatigueData(UserConfig.getInstance().getNewUID(), 30, dateUtil.getY_M_D());
    }

    /* access modifiers changed from: private */
    public void downloadWeightToday() {
        DateUtil dateUtil = new DateUtil();
        dateUtil.addDay(-59);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SPUtils.save(DataNetService.this.getApplicationContext(), SPUtils.Weight_Data_Request_Time, System.currentTimeMillis());
                HealthDataEventBus.updateHealthWeightEvent();
            }

            public void onFail(Throwable e) {
            }
        }).getWifiScaleData(UserConfig.getInstance().getNewUID(), 60, dateUtil.getSyyyyMMddDate());
    }

    /* access modifiers changed from: private */
    public void downloadHeartToday() {
        DateUtil dateUtil = new DateUtil();
        dateUtil.addDay(-10);
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                if (integer.intValue() == 0) {
                    HealthDataEventBus.updateHealthHeartEvent();
                }
            }

            public void onFail(Throwable e) {
            }
        }).downHeartHoursData(UserConfig.getInstance().getNewUID(), 10, dateUtil.getSyyyyMMddDate(), new DateUtil());
    }

    /* access modifiers changed from: private */
    public void downloadEcgDataToday() {
        for (int i = 0; i < 10; i++) {
            DateUtil d = new DateUtil();
            d.addDay(-i);
            NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                public void onSuccess(Integer integer) {
                }

                public void onFail(Throwable e) {
                    KLog.i("---------------------" + e.toString());
                }
            }).downLoadEcgData(UserConfig.getInstance().getNewUID(), d.getYear() + "", d.getMonth() + "", d.getDay() + "");
        }
        List<EcgViewDataBean> list = ModuleRouterEcgService.getInstance().queryEcgDataByUid(ContextUtil.getLUID());
        if (list != null && list.size() > 0) {
            HealthDataEventBus.updateHealthEcg();
        }
    }

    /* access modifiers changed from: private */
    public void downloadSleepToday() {
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                if (integer.intValue() == 0) {
                    HealthDataEventBus.updateHealthSleepEvent();
                }
            }

            public void onFail(Throwable e) {
            }
        }).downloadSleepByDate(UserConfig.getInstance().getNewUID(), 0, new DateUtil().getSyyyyMMddDate());
    }

    /* access modifiers changed from: private */
    public void uploadAlltData() {
        uploadSleepData();
        uploadFatigueData();
        uploadHeartData();
        uploadHeartSportData();
        SportSqlHelper.getInstance().upLoadSportData();
        SPUtils.save((Context) this, SPUtils.ALL_Data_Upload_LAST_MS, System.currentTimeMillis());
    }

    public void uploadSleepData() {
        if (System.currentTimeMillis() - SPUtils.getLong(getApplicationContext(), SPUtils.Last_uploadSleepDataTime) <= 600000) {
            KLog.d("uploadSleepData time 10 min no upload");
            return;
        }
        final SleepUpNewSend sleepUpNewSend = ModuleRouteSleepService.getInstance().getUnUploadSleepDatas(UserConfig.getInstance().getNewUID());
        if (sleepUpNewSend == null || sleepUpNewSend.getContent() == null || sleepUpNewSend.getContent().size() == 0) {
            KLog.e("无数据");
        } else {
            NetFactory.getInstance().getClient(new MyCallback() {
                public void onSuccess(Object o) {
                    ModuleRouteSleepService.getInstance().updateFinalSleepUploadStatus(sleepUpNewSend, true);
                    SPUtils.save(DataNetService.this.getApplicationContext(), SPUtils.Last_uploadSleepDataTime, System.currentTimeMillis());
                }

                public void onFail(Throwable e) {
                }
            }).uploadSleepData(sleepUpNewSend);
        }
    }

    public void uploadHeartData() {
        final List<HeartHoursData> unUploadHeartDatas = ModuleRouteHeartService.getInstance().getUnUploadHeartDatas(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
        KLog.e(unUploadHeartDatas);
        if (unUploadHeartDatas != null && unUploadHeartDatas.size() != 0) {
            NetFactory.getInstance().getClient(new MyCallback() {
                public void onSuccess(Object o) {
                    Map<String, HeartUploadBean> maps = new LinkedHashMap<>();
                    for (HeartHoursData heartHoursData : unUploadHeartDatas) {
                        DateUtil dateUtil = new DateUtil(heartHoursData.getRecord_date(), true);
                        if (((HeartUploadBean) maps.get(heartHoursData.getData_from() + dateUtil.getY_M_D() + heartHoursData.getUid())) == null) {
                            HeartUploadBean heartUploadBean = new HeartUploadBean();
                            heartUploadBean.data_from = heartHoursData.getData_from();
                            heartUploadBean.uid = heartHoursData.getUid();
                            heartUploadBean.year = dateUtil.getYear();
                            heartUploadBean.month = dateUtil.getMonth();
                            heartUploadBean.day = dateUtil.getDay();
                            maps.put(heartHoursData.getData_from() + dateUtil.getY_M_D() + heartHoursData.getUid(), heartUploadBean);
                        }
                    }
                    ModuleRouteHeartService.getInstance().updateDataUploads(maps);
                    SPUtils.save(DataNetService.this.getApplicationContext(), SPUtils.Last_uploadHeartDataTime, System.currentTimeMillis());
                }

                public void onFail(Throwable e) {
                }
            }).UpHeartHoursData(UserConfig.getInstance().getNewUID(), unUploadHeartDatas);
        }
    }

    public void uploadFatigueData() {
        KLog.e("--uploadFatigueData-- ");
        if (System.currentTimeMillis() - SPUtils.getLong(getApplicationContext(), SPUtils.Last_uploadFatigueDataTime) <= 600000) {
            KLog.d("Last_uploadFatigueDataTime time 10 min no upload");
            return;
        }
        final FatigueSend unUploadFatigueDatas = ModuleRouteFatigueService.getIsnatnce().getUnUploadFatigueDatas(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
        KLog.e(" uploadFatigueData " + unUploadFatigueDatas);
        if (unUploadFatigueDatas != null && unUploadFatigueDatas.getDailyData() != null && unUploadFatigueDatas.getDailyData().size() != 0) {
            NetFactory.getInstance().getClient(new MyCallback<RetCode>() {
                public void onSuccess(RetCode o) {
                    if (o.getRetCode() == 0) {
                        SPUtils.save(DataNetService.this.getApplicationContext(), SPUtils.Last_uploadFatigueDataTime, System.currentTimeMillis());
                        for (int i = 0; i < unUploadFatigueDatas.getDailyData().size(); i++) {
                            int stTime = ((FatigueNet) unUploadFatigueDatas.getDailyData().get(i)).getRecordDate();
                            ModuleRouteFatigueService.getIsnatnce().updateFatigueDatas(UserConfig.getInstance().getNewUID(), ((FatigueNet) unUploadFatigueDatas.getDailyData().get(i)).getData_from(), stTime + "");
                        }
                    }
                }

                public void onFail(Throwable e) {
                }
            }).sendFatigue(unUploadFatigueDatas);
        }
    }

    public void uploadEcgData() {
        final List<EcgUploadNet> unUploadEcgDatas = ModuleRouterEcgService.getInstance().getUnUploadedData(UserConfig.getInstance().getNewUID());
        if (unUploadEcgDatas != null && unUploadEcgDatas.size() != 0) {
            NetFactory.getInstance().getClient(new MyCallback() {
                public void onSuccess(Object o) {
                    try {
                        if (((Integer) o).intValue() == 0) {
                            ModuleRouterEcgService.getInstance().updateDataAlreadyUploaded(unUploadEcgDatas);
                            SPUtils.save(DataNetService.this.getApplicationContext(), SPUtils.Last_uploadEcgDataTime, System.currentTimeMillis());
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }

                public void onFail(Throwable e) {
                }
            }).uploadEcgSportData(unUploadEcgDatas);
        }
    }
}
