package com.iwown.device_module.common.Bluetooth.receiver.zg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.android.arouter.utils.Consts;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.DeviceTime;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler.ResponseClock_ScheduleData;
import com.iwown.ble_module.zg_ble.data.model.AgpsType;
import com.iwown.ble_module.zg_ble.data.model.BleSpeed;
import com.iwown.ble_module.zg_ble.data.model.C100Bean;
import com.iwown.ble_module.zg_ble.data.model.DeviceSetting;
import com.iwown.ble_module.zg_ble.data.model.Result;
import com.iwown.ble_module.zg_ble.data.model.SevenDayStore;
import com.iwown.ble_module.zg_ble.data.model.ZGHardwareInfo;
import com.iwown.ble_module.zg_ble.data.model.ZGHeartData;
import com.iwown.ble_module.zg_ble.data.model.ZgAgpsData;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsStatue;
import com.iwown.ble_module.zg_ble.data.model.ZgSleepData;
import com.iwown.ble_module.zg_ble.data.model.bh_totalinfo;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailSportData;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailWalkData;
import com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse.BPData;
import com.iwown.data_link.eventbus.HaveGetModelEvent;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.bean.ZGFirmwareUpgrade;
import com.iwown.device_module.common.Bluetooth.receiver.zg.dao.AgpsStatue;
import com.iwown.device_module.common.Bluetooth.receiver.zg.dao.ZG_Sql_Utils;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.CR100AGPSPresenter;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGSysncFilter;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.Zg2IwownHandler;
import com.iwown.device_module.common.Bluetooth.sync.zg.ZgSync;
import com.iwown.device_module.common.network.data.req.DeviceSettingsSend;
import com.iwown.device_module.common.network.data.req.UserDeviceReq;
import com.iwown.device_module.common.sql.TB_BP_data;
import com.iwown.device_module.common.sql.TB_blue_gps;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_gps.ZgGpsDetailData;
import com.iwown.device_module.device_gps.ZgGpsDetailData.DetailData;
import com.iwown.device_module.device_gps.ZgGpsOneDayOver;
import com.iwown.device_module.device_setting.configure.BloodUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

public class ZGDataParsePresenter {
    public static final byte TYPE89_Heart_02 = 91;
    public static final byte TYPE89_Sleep_03 = 92;
    public static final byte TYPE89_Sport_04 = 93;
    public static final byte TYPE89_Total_81 = 89;
    public static final byte TYPE89_Walk_01 = 90;
    public static final int Type = 3;
    public static String dataFrom = null;
    private static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, new LinkedBlockingDeque());
    public static Runnable gpsTimeout = new Runnable() {
        public void run() {
            if (ZGDataParsePresenter.mUid == 0 || TextUtils.isEmpty(ZGDataParsePresenter.dataFrom)) {
                ZGDataParsePresenter.mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                ZGDataParsePresenter.dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
            }
            TB_mtk_statue mtk_statue = (TB_mtk_statue) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and type=?", String.valueOf(ZGDataParsePresenter.mUid), ZGDataParsePresenter.dataFrom, ZGDataParsePresenter.mYear + "", ZGDataParsePresenter.mMonth + "", ZGDataParsePresenter.mDay + "", "8902").findFirst(TB_mtk_statue.class);
            if (mtk_statue != null) {
                mtk_statue.setHas_tb(1);
                mtk_statue.update(mtk_statue.getId());
            }
            KLog.d("no2855-> 结束一天异常解析: " + ZGDataParsePresenter.mYear + HelpFormatter.DEFAULT_OPT_PREFIX + ZGDataParsePresenter.mMonth + HelpFormatter.DEFAULT_OPT_PREFIX + ZGDataParsePresenter.mDay);
        }
    };
    /* access modifiers changed from: private */
    public static int mDay;
    /* access modifiers changed from: private */
    public static int mMonth;
    public static long mUid = 0;
    /* access modifiers changed from: private */
    public static int mYear;
    public static Handler myHandler = new Handler(Looper.getMainLooper());

    public static void parseProtoclData(Context context, int dataType, String data) {
        switch (dataType) {
            case -126:
                parse82(context, dataType, data);
                return;
            case -125:
                parse83(context, dataType, data);
                return;
            case -124:
                KLog.e("parseProtoclData ZGAlarmClockSchedule > " + data);
                ResponseClock_ScheduleData responseClock_ScheduleData = (ResponseClock_ScheduleData) JsonUtils.fromJson(data, ResponseClock_ScheduleData.class);
                return;
            case -123:
                parse85(context, dataType, data);
                return;
            case -122:
                parse86(context, dataType, data);
                return;
            case -121:
                KLog.e("0x87 =============");
                if (((Result) JsonUtils.fromJson(data, Result.class)).getResult_code() == 0) {
                    Intent intent1 = new Intent(KeyCodeAction.Action_Phone_Statue_Out);
                    intent1.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
                    ContextUtil.app.sendBroadcast(intent1);
                    return;
                }
                return;
            case -120:
                KLog.e("no2855parseProtoclData SevenDayStore > " + data);
                mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
                Context context2 = context;
                ZGSysncFilter.initSyncCondition(context2, (SevenDayStore) JsonUtils.fromJson(data, SevenDayStore.class), mUid, dataFrom);
                return;
            case -118:
                ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Over, 0, 0, 0);
                saveGps2Sd();
                return;
            case -117:
                parse8b(data);
                return;
            case -116:
                parse8C(data);
                return;
            case -115:
                parse8D(data);
                return;
            case -114:
                parse8E(data);
                return;
            case -113:
                parse8F(data);
                return;
            case 6:
                EventBus.getDefault().post(new ZGFirmwareUpgrade(0));
                return;
            case 14:
                parse0E(data);
                return;
            case 15:
                parse0F(data);
                return;
            case 89:
                KLog.e("parseProtoclData bh_totalinfo > " + data);
                bh_totalinfo info = (bh_totalinfo) JsonUtils.fromJson(data, bh_totalinfo.class);
                ZGSysncFilter.syncTodayData(context, info);
                Zg2IwownHandler.converter2Walking(context, dataType, info);
                return;
            case 90:
                Context context3 = context;
                Zg2IwownHandler.getWalkingSport(context3, (ZgDetailWalkData) JsonUtils.fromJson(data, ZgDetailWalkData.class));
                return;
            case 91:
                KLog.e("parseProtoclData ZGHeartData > " + data);
                ZGHeartData zgHeartData = (ZGHeartData) JsonUtils.fromJson(data, ZGHeartData.class);
                Zg2IwownHandler.converter2HourHeart(context, dataType, zgHeartData);
                DateUtil dateUtil2 = new DateUtil();
                if (dateUtil2.getMonth() == zgHeartData.getMonth() && dateUtil2.getDay() == zgHeartData.getDay()) {
                    ZG_Sql_Utils.updateTotalinfoHeart(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
                    return;
                }
                return;
            case 92:
                KLog.e("parseProtoclData sync  ZgSleepData > " + data);
                ZgSleepData zgSleepData = (ZgSleepData) JsonUtils.fromJson(data, ZgSleepData.class);
                Zg2IwownHandler.converter2Sleep(context, dataType, zgSleepData);
                DateUtil dateUtil0 = new DateUtil();
                if (dateUtil0.getMonth() == zgSleepData.getMonth() && dateUtil0.getDay() == zgSleepData.getDay()) {
                    ZG_Sql_Utils.updateTotalinfoSleep(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
                    return;
                }
                return;
            case 93:
                KLog.e("parseProtoclData sync ZgDetailSportData > " + data);
                ZgDetailSportData zgDetailSportData = (ZgDetailSportData) JsonUtils.fromJson(data, ZgDetailSportData.class);
                Zg2IwownHandler.converter2sport(context, dataType, zgDetailSportData);
                Zg2IwownHandler.converterSportTo51Data(zgDetailSportData);
                Zg2IwownHandler.converterTrainingHeart(zgDetailSportData);
                DateUtil dateUtil1 = new DateUtil();
                if (dateUtil1.getMonth() == zgDetailSportData.getMonth() && dateUtil1.getDay() == zgDetailSportData.getDay()) {
                    ZG_Sql_Utils.updateTotalinfoSport(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
                    return;
                }
                return;
            default:
                return;
        }
    }

    private static void parse0F(String data) {
        C100Bean c100Bean = (C100Bean) JsonUtils.fromJson(data, C100Bean.class);
        if (c100Bean.getCode() == 0 || c100Bean.getCode() == 1) {
            CR100AGPSPresenter.getInstance().startAgps();
        }
    }

    private static void parse8b(String data) {
        List<BPData> bpData = JsonUtils.getListJson(data, BPData.class);
        if (bpData != null && bpData.size() > 0) {
            for (BPData bp : bpData) {
                DateUtil d = new DateUtil(bp.getYear(), bp.getMonth(), bp.getDay(), bp.getHour(), bp.getMinute());
                TB_BP_data tbp = (TB_BP_data) DataSupport.where("uid =? and dataFrom=? and bpTime=?", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear(), d.getUnixTimestamp() + "").findFirst(TB_BP_data.class);
                if (tbp == null) {
                    tbp = new TB_BP_data();
                }
                tbp.setUid(ContextUtil.getLUID());
                tbp.setDataFrom(ContextUtil.getDeviceNameNoClear());
                tbp.setDbp(bp.getDbp());
                tbp.setSbp(bp.getSbp());
                tbp.setBpTime(d.getUnixTimestamp());
                tbp.saveOrUpdate("uid =? and dataFrom=? and bpTime=?", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear(), d.getUnixTimestamp() + "");
            }
        }
    }

    private static void parse8E(String data) {
        ZgAgpsData zgAgpsData = (ZgAgpsData) JsonUtils.fromJson(data, ZgAgpsData.class);
        KLog.d("no2855--> 接受校验agps返回的数据: " + JsonUtils.toJson(zgAgpsData));
        if (zgAgpsData.getType() != 90) {
            ZGBaseUtils.beginInitAgps();
        } else if (zgAgpsData.getCode() == 1) {
            if (!ZgSync.getInstance().isAgps()) {
                ZGBaseUtils.checkAgpsStatue();
            }
        } else if (ZgSync.getInstance().isAgps()) {
            ZGBaseUtils.endAgps(true);
        }
    }

    private static void parse8F(String data) {
        AgpsType agpsType = (AgpsType) JsonTool.fromJson(data, AgpsType.class);
        if (agpsType.getData() == 90) {
            if (agpsType.getCode() == 0) {
                KLog.d("CR100 -- 不需要AGPS升级");
                return;
            }
            KLog.d("CR100 -- 需要AGPS升级");
            CR100AGPSPresenter.getInstance().downloadAgpsFile();
        } else if (agpsType.getData() != 3) {
        } else {
            if (agpsType.getStatusCode() == 1) {
                KLog.d("CR100 -- ubx");
            } else if (agpsType.getStatusCode() == 2) {
                KLog.d("CR100 -- sony");
            }
        }
    }

    private static void parse0E(String data) {
        ZgAgpsData zgAgpsData = (ZgAgpsData) JsonUtils.fromJson(data, ZgAgpsData.class);
        KLog.d("no2855--> 接受返回的数据: " + JsonUtils.toJson(zgAgpsData));
        if (zgAgpsData.getCode() == 90) {
            ZGBaseUtils.startAgps();
        } else if (zgAgpsData.getType() == 0) {
            if (zgAgpsData.getCode() == 1) {
                ZGBaseUtils.writeAgps();
            } else if (zgAgpsData.getCode() == 5) {
                ZGBaseUtils.endAgps(false);
                ZGBaseUtils.writeAgpsLength();
            }
        } else if (zgAgpsData.getType() != -2 && zgAgpsData.getCode() == 2) {
            if (128 >= zgAgpsData.getType() || zgAgpsData.getType() > 136) {
                if (zgAgpsData.getType() > 0) {
                    if (zgAgpsData.getWriteCode() == 2) {
                        ZGBaseUtils.writeAgps256Next(false);
                    } else {
                        ZGBaseUtils.writeAgps256Next(true);
                    }
                }
                KLog.e("no2855 2048个包发送结束: 计算结果: 128 == 136 == " + zgAgpsData.getType());
                return;
            }
            if (zgAgpsData.getType() < 136) {
                KLog.e("no2855 全部文件已结束");
            } else if (zgAgpsData.getWriteCode() == 0) {
                ZGBaseUtils.initAgpsData(true);
                ZGBaseUtils.writeAgps2048();
            } else if (zgAgpsData.getWriteCode() == 2) {
                ZGBaseUtils.initAgpsData(false);
                ZGBaseUtils.writeAgps2048();
            }
            KLog.e("no2855 2048个包发送结束: 11111");
        }
    }

    private static void parse8D(String data) {
        AgpsStatue.blood = false;
        if (BloodUtil.isSend0D(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), data)) {
            ZGBaseUtils.setWelcomePageContent();
        }
    }

    private static void parse86(Context context, int dataType, String data) {
        String version;
        ZGHardwareInfo zgHardwareInfo = (ZGHardwareInfo) JsonUtils.fromJson(data, ZGHardwareInfo.class);
        updateZGBaseInfo(ZG_BaseInfo.key_hardinfo, JsonUtils.toJson(zgHardwareInfo));
        String model = zgHardwareInfo.getModel().toUpperCase().trim().toUpperCase(Locale.US);
        ZGSysncFilter.model = model;
        int screenType = zgHardwareInfo.getDev_screen();
        if (screenType != 0) {
            KLog.i("dev_version" + screenType + ".0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low() + "=================" + model);
            version = screenType + ".0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low() + "";
        } else {
            KLog.i("dev_version1.0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low() + "=================" + model);
            version = "1.0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low() + "";
        }
        DeviceSettingsSend dss = new DeviceSettingsSend();
        dss.setApp(AppConfigUtil.APP_TYPE);
        dss.setModel(model);
        dss.setVersion(version);
        dss.setApp_platform(1);
        DeviceSettingsBiz.getInstance().remoteDeviceSettings(dss);
        EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Battery_Update));
        UserDeviceReq req = new UserDeviceReq();
        req.setUid(ContextUtil.getLUID());
        req.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(model));
        EventBus.getDefault().post(new HaveGetModelEvent(model));
        req.setFw_version(version);
        DeviceSettingsBiz.getInstance().upUserDevice(req);
        EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Device_Firmware_Version));
    }

    private static void parse83(Context context, int dataType, String data) {
        updateZGBaseInfo(ZG_BaseInfo.key_bleSpeed, JsonUtils.toJson((BleSpeed) JsonUtils.fromJson(data, BleSpeed.class)));
    }

    private static void parse85(Context context, int dataType, String data) {
        DeviceSetting deviceSetting = (DeviceSetting) JsonTool.fromJson(data, DeviceSetting.class);
        updateZGBaseInfo(ZG_BaseInfo.key_deviceset, JsonUtils.toJson(deviceSetting));
        ZGBaseUtils.updateSendKeyTime(context, ZG_BaseInfo.key_phone_call_time, deviceSetting.getComingCallStartHour(), deviceSetting.getComingCallEndHour());
        ZGBaseUtils.updateSendKeyTime(context, ZG_BaseInfo.key_push_alert_time, deviceSetting.getMessageStartHour(), deviceSetting.getMessageEndHour());
        KLog.e(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + deviceSetting.getSitlongStartHour() + "  " + deviceSetting.getSitlongEndHour());
        ZGBaseUtils.updateSendKeyTime(context, ZG_BaseInfo.key_sit_long_time, deviceSetting.getSitlongStartHour(), deviceSetting.getSitlongEndHour());
    }

    private static void parse82(Context context, int dataType, String data) {
        updateZGBaseInfo(ZG_BaseInfo.key_devicetime, JsonUtils.toJson((DeviceTime) JsonUtils.fromJson(data, DeviceTime.class)));
    }

    public static int getTimeZone() {
        Calendar cal = Calendar.getInstance();
        cal.add(14, -cal.get(15));
        return Long.valueOf((Long.valueOf(System.currentTimeMillis()).longValue() - Long.valueOf(cal.getTimeInMillis()).longValue()) / 3600000).intValue();
    }

    private static void parse8C(final String data) {
        int code = 0;
        try {
            code = new JSONObject(data).optInt("code");
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (code == 1) {
            KLog.e("no2855->8C数据:  " + data);
            mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
            ZGSysncFilter.setGpsTotalDay(data, mUid, dataFrom);
        } else if (code == 2) {
            myHandler.removeCallbacks(gpsTimeout);
            myHandler.postDelayed(gpsTimeout, 4000);
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    ZgGpsDetailData detailData = (ZgGpsDetailData) JsonUtils.fromJson(data, ZgGpsDetailData.class);
                    if (ZGDataParsePresenter.mUid == 0 || TextUtils.isEmpty(ZGDataParsePresenter.dataFrom)) {
                        ZGDataParsePresenter.mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                        ZGDataParsePresenter.dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
                    }
                    if (detailData != null && detailData.getDetailData() != null) {
                        if (!(detailData.getMonth() == 255 || detailData.getDay() == 255)) {
                            ZGDataParsePresenter.mYear = detailData.getYear();
                            ZGDataParsePresenter.mMonth = detailData.getMonth();
                            ZGDataParsePresenter.mDay = detailData.getDay();
                            for (DetailData detailData1 : detailData.getDetailData()) {
                                TB_blue_gps zg_gps = new TB_blue_gps();
                                DateUtil dateUtil = new DateUtil(detailData.getYear(), detailData.getMonth(), detailData.getDay(), detailData1.getHour(), detailData1.getMinute(), detailData1.getSecond());
                                zg_gps.setData_from(ZGDataParsePresenter.dataFrom);
                                zg_gps.setUid(ZGDataParsePresenter.mUid);
                                zg_gps.setTime(dateUtil.getUnixTimestamp());
                                zg_gps.setLat(detailData1.getLatitude());
                                zg_gps.setLon(detailData1.getLongitude());
                                zg_gps.saveOrUpdate("uid=? and data_from=? and time=?", ZGDataParsePresenter.mUid + "", ZGDataParsePresenter.dataFrom, zg_gps.getTime() + "");
                            }
                        }
                        ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Sport_Gps, ZGDataParsePresenter.mYear, ZGDataParsePresenter.mMonth, ZGDataParsePresenter.mDay);
                    }
                }
            });
        } else if (code == 3) {
            myHandler.removeCallbacks(gpsTimeout);
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    ZgGpsOneDayOver oneDayOver = (ZgGpsOneDayOver) JsonUtils.fromJson(data, ZgGpsOneDayOver.class);
                    if (ZGDataParsePresenter.mUid == 0 || TextUtils.isEmpty(ZGDataParsePresenter.dataFrom)) {
                        ZGDataParsePresenter.mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                        ZGDataParsePresenter.dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
                    }
                    TB_mtk_statue mtk_statue = (TB_mtk_statue) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and type=?", String.valueOf(ZGDataParsePresenter.mUid), ZGDataParsePresenter.dataFrom, oneDayOver.getYear() + "", oneDayOver.getMonth() + "", oneDayOver.getDay() + "", "8902").findFirst(TB_mtk_statue.class);
                    if (mtk_statue != null) {
                        mtk_statue.setHas_tb(1);
                        mtk_statue.update(mtk_statue.getId());
                    }
                    KLog.d("no2855-> 结束一天解析: " + oneDayOver.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + oneDayOver.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + oneDayOver.getDay());
                }
            });
        } else if (code == 90) {
            ZgGpsStatue gpsStatue = (ZgGpsStatue) JsonUtils.fromJson(data, ZgGpsStatue.class);
            if (gpsStatue == null) {
                gpsStatue = new ZgGpsStatue();
            }
            KLog.e("no2855 8c校验后: " + JsonUtils.toJson(gpsStatue));
            if (gpsStatue.getStatue() == 0) {
                if (ZgSync.getInstance().isAgps()) {
                    ZGBaseUtils.writeAgpsLength();
                    return;
                }
                ZGBaseUtils.endAgps(false);
                ZGBaseUtils.syncInitDataInfo(true);
            } else if (ZgSync.getInstance().isAgps()) {
                ZgSync.getInstance().setAgpsTime2Zero();
            }
        }
    }

    public static synchronized void updateZGBaseInfo(String key_devicetime, String content) {
        synchronized (ZGDataParsePresenter.class) {
            long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
            if (!TextUtils.isEmpty(deviceName)) {
                ZG_BaseInfo baseInfoByKey = ZG_Sql_Utils.getBaseInfoByKey(key_devicetime, uid, deviceName);
                if (baseInfoByKey == null) {
                    baseInfoByKey = new ZG_BaseInfo();
                    baseInfoByKey.setUid(uid + "");
                    baseInfoByKey.setData_form(deviceName);
                    baseInfoByKey.setKey(key_devicetime);
                }
                baseInfoByKey.setContent(content);
                baseInfoByKey.save();
            }
        }
    }

    public static synchronized ZG_BaseInfo getZGBaseInfoByKey(String key_devicetime) {
        ZG_BaseInfo baseInfoByKey;
        synchronized (ZGDataParsePresenter.class) {
            baseInfoByKey = ZG_Sql_Utils.getBaseInfoByKey(key_devicetime, PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
        }
        return baseInfoByKey;
    }

    public static void saveGps2Sd() {
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                List<TB_mtk_statue> mtk_statues = DataSupport.where("uid=? and data_from=? and type=? and has_file!=?", ZGDataParsePresenter.mUid + "", ZGDataParsePresenter.dataFrom, "8912", "1").order("date desc").find(TB_mtk_statue.class);
                KLog.e("no2855--> 8c入文件mtk_statues。查询条件 " + ZGDataParsePresenter.mUid + " - " + ZGDataParsePresenter.dataFrom);
                if (mtk_statues != null && mtk_statues.size() > 0) {
                    for (TB_mtk_statue mtk_statue : mtk_statues) {
                        long site = System.currentTimeMillis();
                        KLog.e("no2855--> 8c入文件:mtk_statues。size: " + mtk_statue.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + mtk_statue.getDay());
                        L.file("no2855 8c入文件: " + mtk_statue.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + mtk_statue.getDay(), 3);
                        DataUtil.writeBlueGps2SD(ZGDataParsePresenter.mUid, ZGDataParsePresenter.dataFrom, mtk_statue.getYear(), mtk_statue.getMonth(), mtk_statue.getDay(), true);
                        mtk_statue.setHas_file(1);
                        mtk_statue.update(mtk_statue.getId());
                        KLog.e("no2855--> 8c入文件:耗时: " + (System.currentTimeMillis() - site));
                    }
                }
            }
        });
    }
}
