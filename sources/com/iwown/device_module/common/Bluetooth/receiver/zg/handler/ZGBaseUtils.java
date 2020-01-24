package com.iwown.device_module.common.Bluetooth.receiver.zg.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler.ZGAlarmClockBean;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler.ZGSchedule;
import com.iwown.ble_module.zg_ble.task.AgpsBleMessage;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.data_link.BaseNetUrl;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.data.ZgWelcomeBlood;
import com.iwown.data_link.eventbus.AgpsEvent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkDataToServer;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.receiver.zg.dao.AgpsStatue;
import com.iwown.device_module.common.Bluetooth.sync.zg.ZgSync;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.resp.BloodpressureCode;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.configure.BloodUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.socks.library.KLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class ZGBaseUtils {
    public static int Heart = 100;
    public static int Over = 700;
    public static int Sleep = 300;
    public static int Sport = 200;
    public static int Sport_Gps = 800;
    private static final String TAG = ZGBaseUtils.class.getName();
    public static int Walking = 400;
    public static int Walking_2_Sport = ServiceErrorCode.YOU_AND_ME_IS_FRIEND;
    public static int alarm_mode1 = -1;
    public static int alarm_number1 = -1;
    private static Map<String, HashSet<String>> has_update_datas = new HashMap();
    public static boolean isDeviceOpenGps = false;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    public static int progress_date = 0;

    public static Set<String> getHashUpdatDateSets() {
        return (Set) has_update_datas.get(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
    }

    public static void postSyncDataEventZg(int data_type, int year, int month, int day) {
        KLog.e("no2855postSyncDataEventZg " + data_type + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + year + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + month + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + day);
        if (year == 0 && month == 0 && day == 0) {
            mHandler.removeCallbacksAndMessages(null);
            setProgress_date(0);
            if (data_type == Over) {
                ZGDataParsePresenter.updateZGBaseInfo(ZG_BaseInfo.key_data_last_day, new DateUtil().getY_M_D());
                has_update_datas.clear();
                HealthDataEventBus.updateAllDataEvent();
            }
            if (data_type == 0) {
                EventBus.getDefault().post(new SyncDataEvent(-1, true));
            } else {
                EventBus.getDefault().post(new SyncDataEvent(0, true));
            }
            KLog.e("");
            if (hasGps()) {
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        long uid = ContextUtil.getLUID();
                        String str = ContextUtil.getDeviceNameNoClear() + "";
                        MtkDataToServer.uploadMtkData(uid);
                    }
                }, 10000);
                if (PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device).contains("CR100")) {
                    CR100AGPSPresenter.getInstance().checkIsNeedAgpsUpdate();
                } else {
                    downloadAgpsFile();
                }
            }
        } else {
            DateUtil dateUtil = new DateUtil(year, month, day);
            if (data_type == Walking_2_Sport) {
                HashSet<String> strings = (HashSet) has_update_datas.get(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
                if (strings == null) {
                    strings = new HashSet<>();
                    has_update_datas.put(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), strings);
                }
                strings.add(dateUtil.getY_M_D());
                EventBus.getDefault().post(new SyncDataEvent((int) dateUtil.getUnixTimestamp(), false));
                KLog.e(dateUtil.getUnixTimestamp() + "------------------------------sync ok ===" + dateUtil.getY_M_D() + has_update_datas);
            } else if (data_type == Sport_Gps) {
                EventBus.getDefault().post(new SyncDataEvent((int) dateUtil.getUnixTimestamp(), false));
            }
            long zeroTime = dateUtil.getZeroTime();
            if (((long) progress_date) == zeroTime) {
                KLog.d("progress_date same no post progress");
            } else {
                setProgress_date((int) zeroTime);
                EventBus.getDefault().post(new SyncDataEvent((int) dateUtil.getUnixTimestamp(), false));
                KLog.e(dateUtil.getUnixTimestamp() + "============================sync ok ===" + dateUtil.getY_M_D());
            }
            KLog.e("no2855handler 操作处理");
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    KLog.e("38S未更新 当做同步完成");
                    ZGBaseUtils.postSyncDataEventZg(0, 0, 0, 0);
                    HealthDataEventBus.updateAllDataEvent();
                }
            }, 38000);
        }
    }

    public static void downloadAgpsFile() {
        if (!ZgSync.getInstance().isAgps()) {
            String savePath = LogPath.ZERONER;
            String str = "cep_pak.bin";
            boolean needDownload = true;
            File cepFile = new File(Environment.getExternalStorageDirectory() + LogPath.ZERONER + "cep_pak.bin");
            if (cepFile.exists()) {
                if (new DateUtil().getSyyyyMMddDate().equals(new DateUtil(cepFile.lastModified(), false).getSyyyyMMddDate())) {
                    needDownload = false;
                    KLog.e("no2855---> http已经是最新的cep文件了");
                }
            }
            if (!needDownload) {
                checkAgpsUp();
                return;
            }
            KLog.e("no2855---> http准备下载cep文件：：：：：s");
            NetFactory.getInstance().getClient(new MyCallback() {
                public void onSuccess(Object o) {
                    KLog.e("no2855---> 准备下载zgp：：：：：s文件下载成功");
                    L.file("no2855---> 准备下载zgp：：：：：s文件下载成功", 3);
                    ZGBaseUtils.checkAgpsUp();
                }

                public void onFail(Throwable e) {
                    L.file("no2855---> 准备下载zgp：：：：：s文件下载失败", 3);
                }
            }).downAndSaveFile(BaseNetUrl.agpsUrl, LogPath.ZERONER, "cep_pak.bin");
        }
    }

    private static void setProgress_date(int progress_date2) {
        progress_date = progress_date2;
    }

    public static void clearExtraAlarmSchedule() {
        List<TB_Alarmstatue> alarmList = getAlarms();
        if (alarmList.size() > 4) {
            for (int i = 4; i < alarmList.size(); i++) {
                ((TB_Alarmstatue) alarmList.get(i)).delete();
            }
        }
        List<TB_schedulestatue> schedules = getSchedules();
        if (schedules.size() > 4) {
            int index = schedules.size() - 4;
            for (int i2 = 0; i2 < index; i2++) {
                ((TB_schedulestatue) schedules.get(i2)).delete();
            }
        }
        KLog.e("clearExtraAlarmSchedule ok ");
    }

    public static List<TB_Alarmstatue> getAlarms() {
        return DataSupport.where("UID=?", PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid) + "").order("ac_idx asc").find(TB_Alarmstatue.class);
    }

    public static List<TB_schedulestatue> getSchedules() {
        List<TB_schedulestatue> list = new ArrayList<>();
        List<TB_schedulestatue> schedulestatues = DataSupport.where("UID=?", PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid) + "").find(TB_schedulestatue.class);
        if (schedulestatues != null && schedulestatues.size() > 0) {
            for (int i = 0; i < schedulestatues.size() && list.size() < 4; i++) {
                list.add(schedulestatues.get(i));
            }
        }
        return list;
    }

    public static int getSchedulesCount() {
        return DataSupport.where("UID=?", PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid) + "").count(TB_schedulestatue.class);
    }

    public static String getWeekRepeatByIwown(byte iwownWeekRepeat) {
        int[] weeks = new int[8];
        if (iwownWeekRepeat == 0) {
            return "10000000";
        }
        weeks[0] = 1;
        updateWeeksValue(weeks, iwownWeekRepeat, 2, 1);
        updateWeeksValue(weeks, iwownWeekRepeat, 4, 2);
        updateWeeksValue(weeks, iwownWeekRepeat, 8, 3);
        updateWeeksValue(weeks, iwownWeekRepeat, 16, 4);
        updateWeeksValue(weeks, iwownWeekRepeat, 32, 5);
        updateWeeksValue(weeks, iwownWeekRepeat, 64, 6);
        updateWeeksValue(weeks, iwownWeekRepeat, 1, 7);
        KLog.e("getWeekRepeatByIwown " + Arrays.toString(weeks));
        StringBuilder sb = new StringBuilder();
        for (int append : weeks) {
            sb.append(append);
        }
        return sb.toString();
    }

    private static void updateWeeksValue(int[] weeks, byte iwownWeekRepeat, byte weekValue, int index) {
        if ((iwownWeekRepeat & weekValue) > 0) {
            weeks[index] = 1;
        }
    }

    public static void updateAlarmAndSchedule(Context mContext) {
        List<ZGAlarmClockBean> alarms = new ArrayList<>();
        for (TB_Alarmstatue tb_alarmstatue : getAlarms()) {
            ZGAlarmClockBean zgAlarmClockBean = new ZGAlarmClockBean();
            zgAlarmClockBean.alarmHour = tb_alarmstatue.getAc_Hour();
            zgAlarmClockBean.alarmMinute = tb_alarmstatue.getAc_Minute();
            zgAlarmClockBean.alarmRingSetting = ZGAlarmClockScheduleHandler.getMode(tb_alarmstatue.getZg_mode(), tb_alarmstatue.getZg_number());
            KLog.e("alarm  " + tb_alarmstatue);
            String weekRepeatByIwown1 = "00000000";
            if (tb_alarmstatue.getOpenState() != 0) {
                weekRepeatByIwown1 = getWeekRepeatByIwown((byte) tb_alarmstatue.getAc_Conf());
            }
            zgAlarmClockBean.alarmSet = Integer.parseInt(weekRepeatByIwown1, 2);
            KLog.e("weekRepeatByIwown1 " + weekRepeatByIwown1 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ByteUtil.bytesToString1(new byte[]{(byte) zgAlarmClockBean.alarmSet}));
            zgAlarmClockBean.text = tb_alarmstatue.getAc_String();
            alarms.add(zgAlarmClockBean);
        }
        KLog.e("alram1s  " + alarms);
        List<TB_schedulestatue> schedules = getSchedules();
        List<ZGSchedule> scheduls = new ArrayList<>();
        scheduls.add(new ZGSchedule());
        scheduls.add(new ZGSchedule());
        scheduls.add(new ZGSchedule());
        scheduls.add(new ZGSchedule());
        int index = 0;
        for (int i = 0; i < schedules.size(); i++) {
            TB_schedulestatue tb_schedulestatue = (TB_schedulestatue) schedules.get(i);
            ZGSchedule zgSchedule = new ZGSchedule();
            zgSchedule.scheduler_action = 1;
            zgSchedule.scheringSetting = ZGAlarmClockScheduleHandler.getMode(tb_schedulestatue.getZg_mode(), tb_schedulestatue.getZg_number());
            zgSchedule.scheduler_year = tb_schedulestatue.getYear();
            zgSchedule.scheduler_month = tb_schedulestatue.getMonth();
            zgSchedule.scheduler_day = tb_schedulestatue.getDay();
            zgSchedule.scheduler_hour = tb_schedulestatue.getHour();
            zgSchedule.scheduler_minute = tb_schedulestatue.getMinute();
            zgSchedule.text = tb_schedulestatue.getText();
            scheduls.set(index, zgSchedule);
            index++;
        }
        BleDataOrderHandler.getInstance().setAlarmClockAndSchedule(ContextUtil.app, alarms, scheduls);
        KLog.d(TAG, "writeAlarm Zg " + schedules);
    }

    public static void setNotifyMsgTime(Context context, int startHour, int endHour) {
        BleDataOrderHandler.getInstance().setComingMessageHours(context, startHour, endHour);
        updateSendKeyTime(context, ZG_BaseInfo.key_push_alert_time, startHour, endHour);
    }

    public static void setPhoneAlertTime(Context context, int startHour, int endHour) {
        BleDataOrderHandler.getInstance().setComingCallHours(context, startHour, endHour);
        updateSendKeyTime(context, ZG_BaseInfo.key_phone_call_time, startHour, endHour);
    }

    public static void updateSendKeyTime(Context context, String key, int startHour, int endHour) {
        String start = startHour + ":00";
        if (startHour < 10) {
            start = "0" + startHour + ":00";
        }
        String end = endHour + ":00";
        if (endHour < 10) {
            end = "0" + endHour + ":00";
        }
        ZGDataParsePresenter.updateZGBaseInfo(key, start + HelpFormatter.DEFAULT_OPT_PREFIX + end);
    }

    public static boolean PushOrPhoneTimeisFilter(String key) {
        DateUtil dateUtil = new DateUtil();
        ZG_BaseInfo zgBaseInfoByKey = ZGDataParsePresenter.getZGBaseInfoByKey(key);
        if (zgBaseInfoByKey == null || TextUtils.isEmpty(zgBaseInfoByKey.getContent())) {
            return false;
        }
        try {
            String[] split = zgBaseInfoByKey.getContent().split(HelpFormatter.DEFAULT_OPT_PREFIX);
            String start = split[0];
            String end = split[1];
            String[] split1 = start.split(":");
            String[] split2 = end.split(":");
            int real_start = Integer.parseInt(split1[0]);
            int real_end = Integer.parseInt(split2[0]);
            KLog.e("PushOrPhoneTimeisFilter " + key + "  " + real_start + " > " + real_end + "  " + dateUtil.getHour());
            if (dateUtil.getHour() < real_start || dateUtil.getHour() > real_end) {
                return true;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return false;
    }

    public static int getEndHourByKey(String key) {
        int i = -1;
        ZG_BaseInfo zgBaseInfoByKey = ZGDataParsePresenter.getZGBaseInfoByKey(key);
        if (zgBaseInfoByKey == null || TextUtils.isEmpty(zgBaseInfoByKey.getContent())) {
            return i;
        }
        try {
            return Integer.parseInt(zgBaseInfoByKey.getContent().split(HelpFormatter.DEFAULT_OPT_PREFIX)[1].split(":")[0]);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return i;
        }
    }

    public static int getStartHourByKey(String key) {
        int i = -1;
        ZG_BaseInfo zgBaseInfoByKey = ZGDataParsePresenter.getZGBaseInfoByKey(key);
        if (zgBaseInfoByKey == null || TextUtils.isEmpty(zgBaseInfoByKey.getContent())) {
            return i;
        }
        try {
            return Integer.parseInt(zgBaseInfoByKey.getContent().split(HelpFormatter.DEFAULT_OPT_PREFIX)[0].split(":")[0]);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return i;
        }
    }

    public static float geKcal(int total_time_distance) {
        float weight = PrefUtil.getFloat(ContextUtil.app, UserAction.User_Weight);
        if (weight == 0.0f) {
            weight = 60.0f;
        }
        return ((((float) total_time_distance) * weight) * 1.036f) / 1000.0f;
    }

    public static boolean isSysnc() {
        if (progress_date == 0) {
            return false;
        }
        KLog.e("同步中.....");
        return true;
    }

    public static boolean hasGps() {
        boolean has = false;
        String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device);
        if (TextUtils.isEmpty(deviceName)) {
            has = false;
        } else if (deviceName.contains("Band23") || deviceName.contains("SW 650") || deviceName.contains("CR100")) {
            has = true;
        }
        KLog.d("no2855--> 是否需要同步gps：" + deviceName + " -- " + has);
        return has;
    }

    public static void checkSync() {
        boolean connected = BluetoothOperation.isConnected();
        KLog.e("no2855syncinitDataInfo  connected " + connected);
        if (connected) {
            KLog.e("syncinitDataInfo " + isSysnc());
            if (isSysnc()) {
                return;
            }
            if (hasGps()) {
                checkGpsStatue();
            } else {
                syncInitDataInfo(false);
            }
        }
    }

    public static void syncInitDataInfo(boolean hasGps) {
        KLog.e("no2855--> 准备同步数据;; " + hasGps);
        if (hasGps) {
            BleDataOrderHandler.getInstance().getTotalGps();
        }
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDataDate()));
        if (DeviceSettingsBiz.getInstance().supportSomeSetting(38)) {
            getBP();
        }
    }

    public static void setAllOfThem(Context context, int callStatus, int callStart, int callEnd, int MsgStatus, int msgStart, int msgEnd, int gestures, int gesStartHour, int gesEndHour, int heartOn, int heartStartHour, int heartEndHour, int language, int warmingOn, int heartHighAlarm, int heartLowAlarm, int unitType, int temType, int timeDisplay) {
        BleDataOrderHandler.getInstance().setAllOfThem(context, callStatus, callStart, callEnd, MsgStatus, msgStart, msgEnd, gestures, gesStartHour, gesEndHour, heartOn, heartStartHour, heartEndHour, language, warmingOn, heartHighAlarm, heartLowAlarm, unitType, temType, timeDisplay);
    }

    public static void setPhoneCallStatus(Context context, int status) {
        BleDataOrderHandler.getInstance().setCallNotificationSwitch(context, status);
    }

    public static void setMsgNotificationSwitch(Context context, int status) {
        KLog.e("setMsgNotificationSwitch " + status);
        BleDataOrderHandler.getInstance().setMsgNotificationSwitch(context, status);
    }

    public static void setWelcomePageContent(String welcome) {
        writeBloodBle(BloodUtil.getWelBloodBle(welcome));
    }

    public static void setWelcomePageContent() {
        writeBloodBle(BloodUtil.getWelBloodBle());
    }

    public static void setWelcomePageContent(int[] oldBd) {
        writeBloodBle(BloodUtil.getWelBloodBle(oldBd));
    }

    public static void DownloadBp() {
        SharedPreferences read = ContextUtil.app.getSharedPreferences("bloodhistory", 0);
        int Onesbp_lb = read.getInt("Onesbp_lb", 0);
        int Onedbp_lb = read.getInt("Onedbp_lb", 0);
        int Twosbp_lb = read.getInt("Twosbp_lb", 0);
        int Twidbp_lb = read.getInt("Twodbp_lb", 0);
        int src_sbp = read.getInt("src_sbp", 0);
        int src_dbp = read.getInt("src_dbp", 0);
        if (Onesbp_lb <= 0 || Onedbp_lb <= 0 || Twosbp_lb <= 0 || Twidbp_lb <= 0 || src_sbp <= 0 || src_dbp <= 0) {
            NetFactory.getInstance().getClient(new MyCallback<BloodpressureCode>() {
                public void onSuccess(BloodpressureCode bpPreDown1) {
                    if (bpPreDown1 != null) {
                        try {
                            if (bpPreDown1.getData() != null) {
                                KLog.i("l808 血压校准 服务器下载数据  存储");
                                Editor editor = ContextUtil.app.getSharedPreferences("bloodhistory", 0).edit();
                                editor.putInt("Onesbp_lb", bpPreDown1.getData().getStandard_sbp_1st());
                                editor.putInt("Onedbp_lb", bpPreDown1.getData().getStandard_dbp_1st());
                                editor.putInt("Twosbp_lb", bpPreDown1.getData().getStandard_sbp_2nd());
                                editor.putInt("Twodbp_lb", bpPreDown1.getData().getStandard_dbp_2nd());
                                editor.putInt("src_sbp", bpPreDown1.getData().getMeasured_sbp());
                                editor.putInt("src_dbp", bpPreDown1.getData().getMeasured_dbp());
                                editor.putInt("Isuoload", 1);
                                editor.commit();
                                BloodUtil.saveBlood(new int[]{bpPreDown1.getData().getStandard_sbp_1st(), bpPreDown1.getData().getStandard_dbp_1st(), bpPreDown1.getData().getStandard_sbp_2nd(), bpPreDown1.getData().getStandard_dbp_2nd(), bpPreDown1.getData().getMeasured_sbp(), bpPreDown1.getData().getMeasured_dbp()});
                                return;
                            }
                        } catch (Exception e) {
                            return;
                        }
                    }
                    KLog.i("l808 血压校准 服务器下载数据为空");
                }

                public void onFail(Throwable e) {
                    KLog.e("l808 血压校准 data downfai1");
                }
            }).downBloodpressure(UserConfig.getInstance().getNewUID());
        } else {
            KLog.e("l808 血压校准，本都数据不为空 不下载");
        }
    }

    private static void writeBloodBle(ZgWelcomeBlood zgWelcomeBlood) {
        int value = DeviceSettingsBiz.getInstance().getSupportValueInt(35);
        if (value == 1) {
            BleDataOrderHandler.getInstance().writeOldWelcomePageText(zgWelcomeBlood.getWelcome(), zgWelcomeBlood.getTimeZone(), zgWelcomeBlood.getHeight(), zgWelcomeBlood.getGender());
        } else if (value == 2) {
            BleDataOrderHandler.getInstance().writeWelcomePageText(zgWelcomeBlood.getWelcome(), zgWelcomeBlood.getTimeZone(), zgWelcomeBlood.getHeight(), zgWelcomeBlood.getGender(), zgWelcomeBlood.getBlood());
        } else if (zgWelcomeBlood.isOld()) {
            BleDataOrderHandler.getInstance().writeOldWelcomePageText(zgWelcomeBlood.getWelcome(), zgWelcomeBlood.getTimeZone(), zgWelcomeBlood.getHeight(), zgWelcomeBlood.getGender());
        } else {
            BleDataOrderHandler.getInstance().writeWelcomePageText(zgWelcomeBlood.getWelcome(), zgWelcomeBlood.getTimeZone(), zgWelcomeBlood.getHeight(), zgWelcomeBlood.getGender(), zgWelcomeBlood.getBlood());
        }
    }

    public static void getFirmwareInformation(Context context) {
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getFirmwareInformation()));
    }

    public static void setShakeModel(Context context) {
    }

    public static void setTimeDisplay(Context context, int display) {
        BleDataOrderHandler.getInstance().setTimeDisplay(context, display);
    }

    public static void setAlarmScheduleModeNumber(int alarm_mode, int alarm_number) {
        alarm_mode1 = alarm_mode;
        alarm_number1 = alarm_number;
    }

    public static void setGesture(Context context, int gestures, int startHour, int endHour) {
        BleDataOrderHandler.getInstance().setGesture(context, gestures, startHour, endHour);
    }

    public static void setAutoHeart(Context context, int heartOn, int startHour, int endHour) {
        BleDataOrderHandler.getInstance().heartDetection(context, heartOn, startHour, endHour);
    }

    public static void setLanguage(Context context, int language) {
        BleDataOrderHandler.getInstance().setLanguage(context, language);
    }

    public static void setHeartAlarm(Context context, int warmingOn, int heartHighAlarm, int heartLowAlarm) {
        BleDataOrderHandler.getInstance().setHeartAlarm(context, warmingOn, heartHighAlarm, heartLowAlarm);
    }

    public static void setUnit(Context context, int type) {
        BleDataOrderHandler.getInstance().setUnitSwitch(context, type);
    }

    public static void setTemperatureUnit(Context context, int type) {
        BleDataOrderHandler.getInstance().setTemperatureUnitSwitch(context, type);
    }

    public static int getMsgStatus() {
        try {
            return Integer.parseInt(ZGDataParsePresenter.getZGBaseInfoByKey(ZG_BaseInfo.key_message_notification).getContent());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void getTotalGps() {
        BleDataOrderHandler.getInstance().getTotalGps();
    }

    public static void getOneDayGps(int day) {
        BleDataOrderHandler.getInstance().getOneDayGps(day);
    }

    public static void checkAgpsUp() {
        if (AgpsStatue.blood) {
            AgpsStatue.blood = false;
            setWelcomePageContent();
        }
        BleDataOrderHandler.getInstance().checkAgpsIsUp();
    }

    public static void beginInitAgps() {
        AgpsStatue.isAgps = false;
        String path1 = Environment.getExternalStorageDirectory() + LogPath.ZERONER + "cep_pak.bin";
        AgpsStatue.clear();
        AgpsStatue.allAgps = Util.fileToByteStr(path1);
        if (AgpsStatue.allAgps == null || AgpsStatue.allAgps.length == 0) {
            ToastUtil.showToast("文件不存在 ，无法写入agps");
            return;
        }
        AgpsStatue.allPoint = AgpsStatue.allAgps.length;
        checkGpsStatue();
        ZgSync.getInstance().setAgpsTime();
    }

    public static void writeAgpsLength() {
        endAgps(false);
        BleDataOrderHandler.getInstance().writeAgpsLength(AgpsStatue.getAgpsLength());
    }

    public static void startAgps() {
        AgpsStatue.isAgps = true;
        if (AgpsStatue.getAgpsLength() > 0) {
            KLog.e("no28555--> 写入的agps 指令大小: " + (((float) AgpsStatue.allAgps.length) / 2048.0f));
            BleDataOrderHandler.getInstance().startAgps();
            return;
        }
        ZgSync.getInstance().setAgpsTime2Zero();
    }

    public static void endAgps(boolean isSave) {
        BleDataOrderHandler.getInstance().endAgps();
        if (isSave) {
            KLog.d("no2855--> agps升级成功;;");
            L.file("no2855--> agps升级成功", 3);
            ZgSync.getInstance().setAgpsTime2Zero();
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_agps_Time, new DateUtil().getSyyyyMMddDate());
            EventBus.getDefault().post(new AgpsEvent(100));
        }
    }

    public static void writeAgps() {
        if (ZgSync.getInstance().isAgps() && AgpsStatue.isAgps) {
            AgpsStatue.count256 = 0;
            AgpsStatue.count2048 = 0;
            if (AgpsStatue.isAgps) {
                writeAgps2048();
            } else {
                AgpsStatue.isAgps = false;
            }
        }
    }

    public static void writeAgps2048() {
        if (ZgSync.getInstance().isAgps()) {
            if (AgpsStatue.allAgps != null) {
                KLog.e("no2855下一个2048 --> " + AgpsStatue.allAgps.length + " === " + (AgpsStatue.count2048 * 2048) + " === " + ((AgpsStatue.count2048 * 2048) + 2048));
            }
            if (!isEnd()) {
                writeAgps256(Arrays.copyOfRange(AgpsStatue.allAgps, AgpsStatue.count2048 * 2048, (AgpsStatue.count2048 * 2048) + 2048));
            } else {
                AgpsStatue.isAgps = true;
            }
        }
    }

    public static boolean isEnd() {
        if (AgpsStatue.allAgps != null && AgpsStatue.count2048 * 2048 < AgpsStatue.allAgps.length) {
            return false;
        }
        return true;
    }

    public static void writeAgps256(byte[] data2048) {
        if (ZgSync.getInstance().isAgps()) {
            KLog.d("no2855--> 继续下发另一个256:" + AgpsStatue.count256);
            AgpsStatue.sum2048 = data2048.length;
            byte[] datas = Arrays.copyOfRange(data2048, AgpsStatue.count256 * 256, (AgpsStatue.count256 * 256) + 256);
            int sum = datas.length % 16 == 0 ? datas.length / 16 : (datas.length / 16) + 1;
            BleHandler.getInstance().clearTaskHandler();
            List<byte[]> writeByteList = new LinkedList<>();
            for (int i = 0; i < sum; i++) {
                writeByteList.add(BleDataOrderHandler.getInstance().writeAgps(AgpsStatue.sum2048, AgpsStatue.count2048 + 1, AgpsStatue.count256 + 1, i + 1, Arrays.copyOfRange(datas, i * 16, (i + 1) * 16)));
            }
            BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(writeByteList));
            AgpsStatue.progress = (int) ((((float) (datas.length + AgpsStatue.nowPoint)) / (((float) AgpsStatue.allPoint) * 1.0f)) * 100.0f);
            AgpsStatue.nowPoint += datas.length;
            EventBus.getDefault().post(new AgpsEvent(AgpsStatue.progress));
            ZgSync.getInstance().setAgpsTime();
            KLog.e("no2855 需要发送的单独总数量: " + datas.length + " -- " + sum + " --progress: " + AgpsStatue.progress);
        }
    }

    public static void writeAgps256Next(boolean isOk) {
        if (ZgSync.getInstance().isAgps()) {
            boolean isEnd = false;
            if (isOk) {
                if ((AgpsStatue.count256 + 1) * 256 >= AgpsStatue.sum2048) {
                    KLog.e("no2855--> 继续下发另一个256: is End");
                    AgpsStatue.count2048++;
                    isEnd = true;
                }
                if (isEnd) {
                    AgpsStatue.count256 = 0;
                } else {
                    AgpsStatue.count256++;
                }
            }
            if (!isEnd) {
                writeAgps2048();
            }
        }
    }

    public static void initAgpsData(boolean isOk) {
        if (isOk && (AgpsStatue.count256 + 1) * 256 >= AgpsStatue.sum2048) {
            KLog.e("no2855--> 继续下发另一个256: is End one");
            AgpsStatue.count2048++;
        }
        AgpsStatue.count256 = 0;
    }

    public static void checkGpsStatue() {
        KLog.e("no2855-->check--Gps--Statue");
        BleDataOrderHandler.getInstance().checkGpsStatue();
    }

    public static void checkAgpsStatue() {
        KLog.e("no2855-->check--Agps--Statue");
        BleDataOrderHandler.getInstance().checkAgpsStatue();
    }

    public static void readWelcomeBlood() {
        BleDataOrderHandler.getInstance().readWelcomeBlood();
    }

    public static void networkModelInit() {
        if (DeviceSettingsBiz.getInstance().supportSomeSetting(35)) {
            AgpsStatue.blood = true;
            readWelcomeBlood();
        } else if (hasGps()) {
            AgpsStatue.blood = true;
            readWelcomeBlood();
        }
        if (DeviceSettingsBiz.getInstance().supportSomeSetting(38)) {
            getBP();
        }
    }

    public static void getBP() {
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getBP()));
    }
}
