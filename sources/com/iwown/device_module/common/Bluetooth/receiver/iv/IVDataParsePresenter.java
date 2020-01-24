package com.iwown.device_module.common.Bluetooth.receiver.iv;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.fitness.data.Field;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.iwown.bean.DataDetailHeart;
import com.iwown.ble_module.iwown.bean.DataHourHeart;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.ble_module.iwown.bean.IWBleParams;
import com.iwown.ble_module.iwown.bean.IWDevSetting;
import com.iwown.ble_module.iwown.bean.KeyModel;
import com.iwown.ble_module.iwown.bean.StoredDataInfoDetail;
import com.iwown.ble_module.iwown.bean.StoredDataInfoTotal;
import com.iwown.ble_module.iwown.bean.TotalSportData;
import com.iwown.ble_module.iwown.bean.ZeronerDetailSportData;
import com.iwown.ble_module.iwown.bean.ZeronerDetailSportParse;
import com.iwown.ble_module.iwown.bean.ZeronerSleepParse;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.iwown.task.ZeronerBleWriteDataTask;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.eventbus.HaveGetModelEvent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothDataParseReceiver;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.CurrData_0x29;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.Detail_data;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_HeartRateHoursData_biz;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_sleepData_biz;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_sport_data_biz;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_sport_historyData_biz;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_sport_type_data_biz;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.iwown.device_module.common.network.data.req.DeviceSettingsSend;
import com.iwown.device_module.common.network.data.req.UserDeviceReq;
import com.iwown.device_module.common.sql.TB_iv_temporary;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.device_module.common.sql.TB_v3_sport_total_data;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.sql.sleep.TB_v3_sleep_data;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_operation.eventbus.BleConnectStatue;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;
import org.litepal.util.LogUtil;

public class IVDataParsePresenter {
    public static final int Type = 1;
    private static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new LinkedBlockingDeque());
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Runnable sleepTimeOut = new Runnable() {
        public void run() {
            KLog.e("28睡眠--", "28睡眠数据结束开始转为final表------------------->");
            for (int i = 0; i < 7; i++) {
                DateUtil dateUtil = new DateUtil();
                dateUtil.addDay(-i);
                List<TB_SLEEP_Final_DATA> final_datas = DataSupport.where("uid=? and date =? and data_from=?", ContextUtil.getUID(), dateUtil.getSyyyyMMddDate(), ContextUtil.getDeviceNameCurr()).find(TB_SLEEP_Final_DATA.class);
                List<TB_v3_sleep_data> sleepList = V3_sleepData_biz.deleteSoberSleepData(V3_sleepData_biz.querySleepData(ContextUtil.getUID(), ContextUtil.getDeviceNameCurr(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay()));
                if (final_datas.size() <= 0 && sleepList.size() > 0) {
                    V3_sleepData_biz.sleepDetail(ContextUtil.getLUID(), ContextUtil.getDeviceNameCurr(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());
                }
            }
            HealthDataEventBus.updateHealthSleepEvent();
            EventBus.getDefault().post(new ViewRefresh(true, 40));
        }
    };

    public static void parseProtocalData(Context context, int dataType, String data) {
        EventBus.getDefault().post(new BleConnectStatue(true));
        switch (dataType) {
            case 0:
                parse00Data(context, data);
                return;
            case 1:
                parse01Data(context, data);
                return;
            case 8:
                parse08Data(context, data);
                return;
            case 19:
                parse13Data(context, data);
                return;
            case 25:
                parse19Data(context, data);
                return;
            case 26:
                parse1AData(context, data);
                return;
            case 40:
                process28Data(context, dataType, data);
                return;
            case 41:
                parse29(context, dataType, data);
                return;
            case 64:
                parse40Data(context, data);
                return;
            case 81:
                parse51NotSave(context, data);
                return;
            case 83:
                parse53Data(context, data);
                return;
            default:
                return;
        }
    }

    private static void parse51NotSave(Context context, String data) {
        try {
            DataDetailHeart dataDetailHeart = (DataDetailHeart) JsonUtils.fromJson(data, DataDetailHeart.class);
            LogUtil.d("心率--", "心率0x51数据-" + data);
            KLog.i("心率数据" + JsonUtils.toJson(HeartRateDetial.parse(dataDetailHeart)));
            SyncData.getInstance().setNowAdd51(dataDetailHeart.getNowAdd51(), dataDetailHeart.isLast());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("51 心率解析异常");
        }
    }

    private static void parse53Data(Context context, String data) {
        DataHourHeart dataHourHeart = (DataHourHeart) JsonUtils.fromJson(data, DataHourHeart.class);
        LogUtil.d("53心率--", "心率0x53数据" + data);
        TB_v3_heartRate_data_hours hoursData = new TB_v3_heartRate_data_hours();
        hoursData.setUid(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
        String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        hoursData.setData_from(dataFrom);
        int y = dataHourHeart.getYear();
        int m = dataHourHeart.getMonth();
        int d = dataHourHeart.getDay();
        int hour = dataHourHeart.getHour();
        KLog.i("53index" + dataHourHeart.getNowAdd53() + "isLast" + dataHourHeart.isLast());
        SyncData.getInstance().setNowAdd53(dataHourHeart.getNowAdd53(), dataHourHeart.isLast());
        Calendar c = Calendar.getInstance();
        c.set(y, m - 1, d);
        int week = c.get(3);
        hoursData.setYear(y);
        hoursData.setMonth(m);
        hoursData.setDay(d);
        hoursData.setWeek(week);
        hoursData.setHours(hour);
        KLog.d("testHeart", "手环年月数据 is " + y + HelpFormatter.DEFAULT_OPT_PREFIX + m + HelpFormatter.DEFAULT_OPT_PREFIX + d + HelpFormatter.DEFAULT_OPT_PREFIX + hour);
        hoursData.set_uploaded(0);
        hoursData.setRecord_date(Util.date2TimeStamp(y, m, d, hour, 0));
        hoursData.setTime_stamp(((long) ((y * m) + (d * 25) + hour)) + hoursData.getUid());
        int[] time = dataHourHeart.getRates();
        KLog.d("53 last ffffno islast--> " + dataHourHeart.isLast());
        if (dataHourHeart.isLast()) {
            long uid = ContextUtil.getLUID();
            String dataFrom1 = ContextUtil.getDeviceNameNoClear();
            KLog.d("53 last ffffno2855--> " + uid + " == " + dataFrom1);
            DateUtil d1 = new DateUtil();
            List<TB_v3_heartRate_data_hours> list = V3_HeartRateHoursData_biz.query_data(uid + "", d1.getYear(), d1.getMonth(), d1.getDay(), d1.getHour());
            if (list == null || list.size() <= 0) {
                TB_v3_heartRate_data_hours hearts = new TB_v3_heartRate_data_hours();
                hearts.setUid(uid);
                hearts.setData_from(dataFrom1);
                hearts.setHours(d1.getHour());
                hearts.setYear(d1.getYear());
                hearts.setMonth(d1.getMonth());
                hearts.setWeek(d1.getWeekOfYear());
                hearts.setDay(d1.getDay());
                hearts.setDetail_data(JsonUtils.toJson(time));
                hearts.setRecord_date(Util.date2TimeStamp(d1.getYear(), d1.getMonth(), d1.getDay(), d1.getHour(), 0));
                hearts.setTime_stamp(((long) ((d1.getYear() * d1.getMonth()) + (d1.getDay() * 25) + d1.getHour())) + hoursData.getUid());
                hearts.saveOrUpdate("uid=? and record_date=?", hoursData.getUid() + "", Util.date2TimeStamp(d1.getYear(), d1.getMonth(), d1.getDay(), d1.getHour(), 0) + "");
            } else {
                TB_v3_heartRate_data_hours hearts2 = (TB_v3_heartRate_data_hours) list.get(0);
                hearts2.setHours(d1.getHour());
                hearts2.setDetail_data(JsonUtils.toJson(time));
                hearts2.setRecord_date(Util.date2TimeStamp(d1.getYear(), d1.getMonth(), d1.getDay(), d1.getHour(), 0));
                hearts2.saveOrUpdate("uid=? and record_date=?", hoursData.getUid() + "", Util.date2TimeStamp(d1.getYear(), d1.getMonth(), d1.getDay(), d1.getHour(), 0) + "");
            }
            EventBus.getDefault().post(new ViewRefresh(false, 83));
            HealthDataEventBus.updateHealthHeartEvent();
            parse51Data(uid, dataFrom1);
            return;
        }
        Gson gson = new Gson();
        if (V3_HeartRateHoursData_biz.queryDataExist(hoursData.getUid() + "", hoursData.getRecord_date(), dataFrom) < 1) {
            hoursData.setDetail_data(gson.toJson((Object) time));
            boolean save = hoursData.save();
            KLog.i(hoursData.toString());
            return;
        }
        hoursData.setDetail_data(gson.toJson((Object) time));
        hoursData.updateAll("uid=? and record_date=?", hoursData.getUid() + "", hoursData.getRecord_date() + "");
    }

    private static void parse51Data(long uid, String dataFrom) {
        KLog.e("心率--", "心率0x51数据-");
        int age = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).age;
        int maxHeart = 220 - age;
        List<TB_iv_temporary> iv_temporary = DataSupport.where("uid=? and data_from=? and has_hr=0", uid + "", dataFrom).find(TB_iv_temporary.class);
        if (iv_temporary != null && iv_temporary.size() > 0) {
            int count = iv_temporary.size();
            for (int i = 0; i < count; i++) {
                int r0 = 0;
                int r1 = 0;
                int r2 = 0;
                int r3 = 0;
                int r4 = 0;
                int r5 = 0;
                DateUtil dateUtil = new DateUtil(((TB_iv_temporary) iv_temporary.get(i)).getStart_time(), true);
                int y = dateUtil.getYear();
                int m = dateUtil.getMonth();
                int d = dateUtil.getDay();
                int sportType = ((TB_iv_temporary) iv_temporary.get(i)).getSport_type();
                long startTime = ((TB_iv_temporary) iv_temporary.get(i)).getStart_time();
                long endTime = ((TB_iv_temporary) iv_temporary.get(i)).getEnd_time();
                KLog.e("=========================startDate" + dateUtil.getY_M_D_H_M_S());
                DateUtil dateUtil2 = new DateUtil(endTime, true);
                KLog.e("=========================endDate" + dateUtil2.getY_M_D_H_M_S());
                int startHour = dateUtil.getHour();
                int startMin = dateUtil.getMinute();
                int endHour = dateUtil2.getHour();
                int endMin = dateUtil2.getMinute();
                ArrayList<Integer> hearts = new ArrayList<>();
                List<TB_v3_heartRate_data_hours> data = DataSupport.where("uid =? and data_from =? and hours >= ? and  hours<= ? and  year =? and month=? and day=?", uid + "", dataFrom, startHour + "", endHour + "", y + "", m + "", d + "").find(TB_v3_heartRate_data_hours.class);
                if (data != null && data.size() > 0) {
                    for (int j = 0; j < data.size(); j++) {
                        hearts.addAll(JsonUtils.getListJson(((TB_v3_heartRate_data_hours) data.get(j)).getDetail_data(), Integer.class));
                    }
                    KLog.e("===========hearts===============" + JsonUtils.toJson(hearts));
                    LinkedList<Integer> h5 = new LinkedList<>();
                    int nums = Math.min(hearts.size(), ((endHour - startHour) * 60) + endMin);
                    for (int j2 = startMin; j2 < nums; j2++) {
                        int heart = ((Integer) hearts.get(j2)).intValue();
                        if (heart < 30 || (heart > maxHeart && maxHeart != 0)) {
                            heart = 0;
                        }
                        h5.add(Integer.valueOf(heart));
                        int lever = getHeartLev(maxHeart, heart);
                        if (lever == 0) {
                            r0++;
                        } else if (lever == 1) {
                            r1++;
                        } else if (lever == 2) {
                            r2++;
                        } else if (lever == 3) {
                            r3++;
                        } else if (lever == 4) {
                            r4++;
                        } else if (lever == 5) {
                            r5++;
                        }
                    }
                    HeartRateDetial hrData = new HeartRateDetial();
                    hrData.setR0(r0);
                    hrData.setR1(r1);
                    hrData.setR2(r2);
                    hrData.setR3(r3);
                    hrData.setR4(r4);
                    hrData.setR5(r5);
                    TB_heartrate_data heart2 = new TB_heartrate_data();
                    heart2.setUid(uid);
                    heart2.setYear(y);
                    heart2.setMonth(m);
                    heart2.setDay(d);
                    heart2.setAge(age);
                    heart2.setStart_time(startTime);
                    heart2.setEnd_time(endTime);
                    heart2.setDetail_data(JsonUtils.toJson(hrData));
                    heart2.setData_from(dataFrom);
                    heart2.setSport_type(sportType);
                    heart2.setReserved(JsonUtils.toJson(h5));
                    heart2.saveOrUpdate("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom);
                    DataUtil.upGpsSportOneUrl("1", 1, uid, startTime, dataFrom, DataUtil.getSportDataTYpe(sportType));
                    final TB_heartrate_data tB_heartrate_data = heart2;
                    fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            DataUtil.saveHr2File(tB_heartrate_data, null, true, false);
                        }
                    });
                    KLog.e("==================================================" + heart2.toString());
                }
                ((TB_iv_temporary) iv_temporary.get(i)).setHas_hr(1);
                ((TB_iv_temporary) iv_temporary.get(i)).update(((TB_iv_temporary) iv_temporary.get(i)).getId());
            }
        }
        DateUtil d1 = new DateUtil();
        d1.addDay(-15);
        DataSupport.deleteAll(TB_iv_temporary.class, "start_time<?", d1.getUnixTimestamp() + "");
    }

    public static int getHeartLev(int maxHeart, int heart) {
        if (((double) heart) <= ((double) maxHeart) * 0.5d && heart >= 35) {
            return 0;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.6d && ((double) heart) > ((double) maxHeart) * 0.5d) {
            return 1;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.7d && ((double) heart) > ((double) maxHeart) * 0.6d) {
            return 2;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.8d && ((double) heart) > ((double) maxHeart) * 0.7d) {
            return 3;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.9d && ((double) heart) > ((double) maxHeart) * 0.8d) {
            return 4;
        }
        if (((double) heart) <= ((double) maxHeart) * 1.0d && ((double) heart) > ((double) maxHeart) * 0.9d) {
            return 5;
        }
        KLog.i("====getHeartLev====" + heart);
        return -1;
    }

    private static void parse13Data(Context context, String data) {
        boolean isNewProtocol = ((IWBleParams) JsonTool.fromJson(data, IWBleParams.class)).isNewProtocol();
        KLog.i("data,0x13" + data + "isNewProtocol" + isNewProtocol);
        PrefUtil.save(context, FirmwareAction.Firmware_New_Protocol, isNewProtocol);
        if (isNewProtocol) {
            BluetoothDataParseReceiver.setNewConnectProtocol();
        }
    }

    private static void parse08Data(Context context, String data) {
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Can_Support_08, true);
        StoredDataInfoTotal storedDataInfoTotal = (StoredDataInfoTotal) JsonUtils.fromJson(data, StoredDataInfoTotal.class);
        if (SyncData.getInstance().isSyncDataInfo()) {
            List<StoredDataInfoDetail> details = storedDataInfoTotal.getInfoList();
            SyncData.getInstance().clear();
            boolean isAdd53 = false;
            for (int i = 0; i < details.size(); i++) {
                StoredDataInfoDetail detail = (StoredDataInfoDetail) details.get(i);
                int range = detail.getMax_range();
                int startAdd = detail.getStart_index();
                int endAdd = detail.getEnd_index();
                switch (detail.getType()) {
                    case 40:
                        SyncData.getInstance().setStarAdd28(startAdd);
                        SyncData.getInstance().setRange28(range);
                        SyncData.getInstance().setEndAdd28(endAdd);
                        KLog.i("0x28-->start:" + startAdd + "  end:" + endAdd + "  range:" + range);
                        break;
                    case 81:
                        SyncData.getInstance().setStarAdd51(startAdd);
                        SyncData.getInstance().setRange51(range);
                        SyncData.getInstance().setEndAdd51(endAdd);
                        KLog.i("0x51-->start:" + startAdd + "  end:" + endAdd + "  range:" + range);
                        break;
                    case 83:
                        SyncData.getInstance().setStarAdd53(startAdd);
                        SyncData.getInstance().setRange53(range);
                        SyncData.getInstance().setEndAdd53(endAdd);
                        KLog.i("0x53-->start:" + startAdd + "  end:" + endAdd + "  range:" + range);
                        if (startAdd != endAdd) {
                            break;
                        } else {
                            isAdd53 = true;
                            break;
                        }
                }
                KLog.i("index : " + i + "  数据 : " + JsonUtils.toJson(detail));
            }
            SyncData.getInstance().initMap();
            SyncData.getInstance().save();
            SyncData.getInstance().syncData(2);
            SyncData.getInstance().syncData();
            if (isAdd53) {
                byte[] data10 = ZeronerSendBluetoothCmdImpl.getInstance().syncHeartRateHourData(1);
                DataBean dataBean10 = new DataBean();
                dataBean10.addData(data10);
                ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean10);
            }
        }
    }

    private static void parse1AData(Context context, String data) {
        KLog.i("parse1A" + data);
        DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Support_Sport, data);
        CommandOperation.i6S7(DeviceUtils.getDeviceInfo().getModel());
    }

    private static void parse40Data(Context context, String data) {
        int code = ((KeyModel) JsonUtils.fromJson(data, KeyModel.class)).getKeyCode();
        if (code == 1) {
            context.sendBroadcast(new Intent(KeyCodeAction.Action_Seleie_Data));
        } else if (code == 2) {
            ContextUtil.startSong();
        } else if (code == 9) {
            Intent intent1 = new Intent(KeyCodeAction.Action_Phone_Statue_Out);
            intent1.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            ContextUtil.app.sendBroadcast(intent1);
        } else if (code == 8) {
            Intent intent3 = new Intent(KeyCodeAction.Action_Phone_Mute);
            intent3.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent3);
        } else if (code == 12) {
            Intent intent4 = new Intent(KeyCodeAction.Action_Phone_Ring);
            intent4.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent4);
        } else if (code == 3) {
            Intent intent42 = new Intent(KeyCodeAction.Action_Voice_Start);
            intent42.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent42);
        } else if (code == 4) {
            Intent intent43 = new Intent(KeyCodeAction.Action_Voice_Start);
            intent43.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent43);
        }
    }

    private static void parse01Data(Context context, String data) {
        DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Battery, data);
        EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Battery_Update));
    }

    private static void parse00Data(Context context, String data) {
        if (!(BluetoothOperation.getWristBand() == null || BluetoothOperation.getWristBand().getName() == null || BluetoothOperation.getWristBand().getAddress() == null)) {
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name, BluetoothOperation.getWristBand().getName());
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address, BluetoothOperation.getWristBand().getAddress());
        }
        FMdeviceInfo info = (FMdeviceInfo) JsonUtils.fromJson(data, FMdeviceInfo.class);
        DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Information, data);
        KLog.i("firmware version:" + info.getSwversion());
        byte[] data12 = ZeronerSendBluetoothCmdImpl.getInstance().getDeviceStateDate();
        DataBean bean = new DataBean();
        bean.addData(data12);
        ZeronerBackgroundThreadManager.getInstance().addTask(new ZeronerBleWriteDataTask(context, bean));
        DeviceSettingsSend dss = new DeviceSettingsSend();
        dss.setApp(AppConfigUtil.APP_TYPE);
        dss.setModel(info.getModel());
        dss.setVersion(info.getSwversion());
        dss.setApp_platform(1);
        DeviceSettingsBiz.getInstance().remoteDeviceSettings(dss);
        UserDeviceReq req = new UserDeviceReq();
        req.setUid(ContextUtil.getLUID());
        req.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(info.getModel()));
        EventBus.getDefault().post(new HaveGetModelEvent(info.getModel()));
        req.setFw_version(info.getSwversion());
        DeviceSettingsBiz.getInstance().upUserDevice(req);
    }

    private static void process28Data(Context context, int dataType, String data1) {
        KLog.d("分段冻结运动数据");
        int type = JsonUtils.getInt(data1, "type");
        boolean isLast = JsonUtils.getBoolean(data1, "last");
        int index = JsonUtils.getInt(data1, "index");
        ZeronerSleepParse iwownSleepParse = null;
        ZeronerDetailSportParse iwownDetailSportParse = null;
        KLog.i("28index" + index + "isLast" + isLast);
        SyncData.getInstance().setNowAdd28(index, isLast);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        KLog.d("分段冻结运动数据");
        if (type == 1) {
            iwownSleepParse = (ZeronerSleepParse) JsonUtils.fromJson(data1, ZeronerSleepParse.class);
        } else if (type == 2) {
            iwownDetailSportParse = (ZeronerDetailSportParse) JsonUtils.fromJson(data1, ZeronerDetailSportParse.class);
        }
        mHandler.removeCallbacks(sleepTimeOut);
        mHandler.postDelayed(sleepTimeOut, 10000);
        if (type == 1) {
            LogUtil.d("28睡眠--", "收到28睡眠数据------------------->");
            TB_v3_sleep_data sleepEntity = TB_v3_sleep_data.parse(iwownSleepParse.getData(), context);
            if (V3_sleepData_biz.queryDataExixt(uid, sleepEntity.getYear(), sleepEntity.getMonth(), sleepEntity.getDay(), sleepEntity.getStart_time(), sleepEntity.getEnd_time(), sleepEntity.getActivity(), sleepEntity.getSleep_type()) <= 0) {
                sleepEntity.save();
            }
        } else if (type == 2) {
            ZeronerDetailSportData iwownDetailSportData = iwownDetailSportParse.getData();
            int year = iwownDetailSportData.getYear();
            int month = iwownDetailSportData.getMonth();
            int day = iwownDetailSportData.getDay();
            TB_v3_sport_data entity = TB_v3_sport_data.parse(iwownDetailSportData, context, 10000);
            if (V3_sport_data_biz.querySportTypeAndTimeExist(uid, entity.getSport_type(), entity.getStart_time(), entity.getEnd_time(), entity.getYear(), entity.getMonth(), entity.getDay())) {
                KLog.i("数据库中存大数据，大量重复数据插入请注意注意");
            } else {
                KLog.i("entity0x28--->" + entity.toString());
                entity.save();
                Detail_data ds = (Detail_data) JsonUtils.fromJson(entity.getDetail_data(), Detail_data.class);
                if (ds != null) {
                    DataUtil.saveBlueToGpsSport(uid, entity.getSport_type(), entity.getStart_uxtime(), entity.getEnd_uxtime(), (float) entity.getCalorie(), ds.getStep(), ds.getDistance(), entity.getData_from(), 0, (int) ((entity.getEnd_uxtime() - entity.getStart_uxtime()) - ((long) (ds.getActivity() * 60))), ds.getCount(), null);
                } else {
                    DataUtil.saveBlueToGpsSport(uid, entity.getSport_type(), entity.getStart_uxtime(), entity.getEnd_uxtime(), (float) entity.getCalorie(), 0, 0.0f, entity.getData_from(), 0, 0, 0, null);
                }
                DataUtil.saveIVSportTem(entity);
            }
        }
        if (isLast) {
            mHandler.removeCallbacks(sleepTimeOut);
            KLog.e("28睡眠--", "28睡眠数据结束开始转为final表------------------->");
            for (int i = 0; i < 7; i++) {
                DateUtil dateUtil = new DateUtil();
                dateUtil.addDay(-i);
                List<TB_SLEEP_Final_DATA> final_datas = DataSupport.where("uid=? and date =? and data_from=?", ContextUtil.getUID(), dateUtil.getSyyyyMMddDate(), ContextUtil.getDeviceNameCurr()).find(TB_SLEEP_Final_DATA.class);
                List<TB_v3_sleep_data> sleepList = V3_sleepData_biz.deleteSoberSleepData(V3_sleepData_biz.querySleepData(ContextUtil.getUID(), ContextUtil.getDeviceNameCurr(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay()));
                if (final_datas.size() <= 0 && sleepList.size() > 0) {
                    V3_sleepData_biz.sleepDetail(ContextUtil.getLUID(), ContextUtil.getDeviceNameCurr(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());
                }
            }
        }
        if (isLast) {
            HealthDataEventBus.updateHealthSleepEvent();
            EventBus.getDefault().post(new ViewRefresh(true, 40));
        }
    }

    private static void parse29(Context context, int dataType, String data) {
        KLog.i(" private static void parse29(Context context, int dataType, String data)" + data);
        TotalSportData totalSportData = (TotalSportData) JsonUtils.fromJson(data, TotalSportData.class);
        SyncData.getInstance().removeSync29DataTimeTask();
        if (SyncData.getInstance().isSyncDataInfo()) {
            SyncData.getInstance().judgeStopSyncData();
            if (totalSportData.isLast()) {
                SyncData.getInstance().stopSyncData(2);
            }
            EventBus.getDefault().post(new SyncDataEvent());
        }
        CurrData_0x29 currData = CurrData_0x29.parse(totalSportData, context);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        if (currData.isLive()) {
            DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Curr_0x29_Data, JsonUtils.toJson(currData));
            DateUtil date = new DateUtil(new Date());
            int y = date.getYear();
            int m = date.getMonth();
            int d = date.getDay();
            Calendar.getInstance().set(y, m - 1, d);
            V3_sport_historyData_biz.saveTBWalk(y, m, d, currData.getSportCalories(), currData.getSportDistances(), currData.getSportSteps());
            TB_v3_sport_total_data historyData = V3_sport_historyData_biz.query_data_by_timeStamp(uid + "", ((long) ((y * m) + d)) + uid);
            if (historyData != null) {
                Map<String, Integer> v3data = V3_sport_type_data_biz.queryDataFromTB(uid + "", y, m, d);
                historyData.setTotal_steps(((Integer) v3data.get("steps")).intValue());
                historyData.setTotal_calorie((float) ((Integer) v3data.get(Field.NUTRIENT_CALORIES)).intValue());
                historyData.updateAll("time_stamp=?", historyData.getTime_stamp() + "");
            }
        } else {
            int y2 = currData.getYear();
            int m2 = currData.getMonth();
            int d2 = currData.getDay();
            Calendar.getInstance().set(y2, m2 - 1, d2);
            V3_sport_historyData_biz.saveTBWalk(y2, m2, d2, currData.getSportCalories(), currData.getSportDistances(), currData.getSportSteps());
            TB_v3_sport_total_data historyData2 = V3_sport_historyData_biz.query_data_by_timeStamp(uid + "", ((long) ((y2 * m2) + d2)) + uid);
            if (historyData2 != null) {
                Map<String, Integer> v3data2 = V3_sport_type_data_biz.queryDataFromTB(uid + "", y2, m2, d2);
                historyData2.setTotal_steps(((Integer) v3data2.get("steps")).intValue());
                historyData2.setTotal_calorie((float) ((Integer) v3data2.get(Field.NUTRIENT_CALORIES)).intValue());
                historyData2.updateAll("time_stamp=?", historyData2.getTime_stamp() + "");
            }
        }
        EventBus.getDefault().post(currData);
    }

    private static void parse19Data(Context context, String data) {
        DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Settings_Default, data);
        IWDevSetting iwDevSetting = (IWDevSetting) JsonUtils.fromJson(data, IWDevSetting.class);
        if (DeviceUtils.getDeviceInfo().getModel().equalsIgnoreCase(WristbandModel.MODEL_I5PR) || DeviceUtils.getDeviceInfo().getModel().equalsIgnoreCase(WristbandModel.MODEL_I5PK) || DeviceUtils.getDeviceInfo().getModel().equalsIgnoreCase("v6") || DeviceUtils.getDeviceInfo().getModel().equalsIgnoreCase("I6DK")) {
            DeviceSettingLocal settingLocal = DeviceUtils.localDeviceSetting();
            settingLocal.setLanguage(iwDevSetting.getLanguage());
            DeviceUtils.saveLocalDeviceSetting(settingLocal);
            KLog.i("language:" + iwDevSetting.getLanguage());
        }
    }
}
