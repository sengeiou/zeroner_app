package com.iwown.device_module.common.Bluetooth.receiver.proto;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.fitness.data.Field;
import com.iwown.ble_module.model.KeyModel;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.model.ProtoBufConnectParmas;
import com.iwown.ble_module.proto.model.ProtoBufFileUpdateInfo;
import com.iwown.ble_module.proto.model.ProtoBufHardwareInfo;
import com.iwown.ble_module.proto.model.ProtoBufHisEPGData;
import com.iwown.ble_module.proto.model.ProtoBufHisGnssData;
import com.iwown.ble_module.proto.model.ProtoBufHisGnssData.Gnss;
import com.iwown.ble_module.proto.model.ProtoBufHisHealthData;
import com.iwown.ble_module.proto.model.ProtoBufHisIndexTable;
import com.iwown.ble_module.proto.model.ProtoBufHisRriData;
import com.iwown.ble_module.proto.model.ProtoBufRealTimeData;
import com.iwown.ble_module.proto.model.ProtoBufSupportInfo;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.data_link.eventbus.HaveGetModelEvent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.CurrData_0x29;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_sport_historyData_biz;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.V3_sport_type_data_biz;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.LongitudeAndLatitude;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.SportDeviceNetWorkUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.ProtoBufSleepSqlUtils;
import com.iwown.device_module.common.Bluetooth.receiver.proto.utils.PbToIvHandler;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufIndex;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufSync;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufUpdate;
import com.iwown.device_module.common.network.data.req.DeviceSettingsSend;
import com.iwown.device_module.common.network.data.req.UserDeviceReq;
import com.iwown.device_module.common.sql.PbSupportInfo;
import com.iwown.device_module.common.sql.ProtoBuf_80_data;
import com.iwown.device_module.common.sql.ProtoBuf_index_80;
import com.iwown.device_module.common.sql.TB_62_data;
import com.iwown.device_module.common.sql.TB_64_data;
import com.iwown.device_module.common.sql.TB_BP_data;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.TB_rri_data;
import com.iwown.device_module.common.sql.TB_sport_ball;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.iwown.device_module.common.sql.TB_sport_other;
import com.iwown.device_module.common.sql.TB_v3_sport_total_data;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class ProtoBufDataParsePresenter {
    private static final int CASE_HIS_DATA = 4;
    private static final int CASE_INDEX_TABLE = 3;
    private static final int TYPE_HIS_ECG = 5;
    private static final int TYPE_HIS_GNSS = 4;
    private static final int TYPE_HIS_HEALTH = 3;
    private static final int TYPE_HIS_PPG = 6;
    private static final int TYPE_HIS_RRI = 7;
    public static final int Type = 4;
    /* access modifiers changed from: private */
    public static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(2, 2, 10, TimeUnit.SECONDS, new LinkedBlockingDeque());
    private static int index = 0;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Runnable sync80Timeout = new Runnable() {
        public void run() {
            KLog.e("yanxi...执行同步完成");
            ProtoBufDataParsePresenter.updateAllStatus(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
            ProtoBufDataParsePresenter.clearTime();
            ProtoBufSync.getInstance().progressFinish();
            ProtoBufDataParsePresenter.fixedThreadPool.execute(new Runnable() {
                public void run() {
                    ProtoBufDataParsePresenter.dispBp();
                    HealthDataEventBus.updateAllUI();
                    ProtoBufDataParsePresenter.upGpsToFileServer();
                }
            });
        }
    };

    public static void parseProtocolData(Context context, int dataType, String data) {
        KLog.i("dataType:" + dataType);
        String str = "";
        switch (dataType) {
            case 0:
                parse00Data(context, data);
                return;
            case 1:
                parse01Data(context, data);
                return;
            case 2:
                parse02Data(context, data);
                return;
            case 9:
                parse09Data(context, data);
                return;
            case 112:
                parse70Data(context, data);
                return;
            case 128:
                parse80Data(context, data);
                return;
            case Opcodes.ADD_INT /*144*/:
                parse90data(data);
                return;
            case 65535:
                parseFFData(context, data);
                return;
            default:
                return;
        }
    }

    private static void parse02Data(Context context, String data) {
        if (((KeyModel) JsonTool.fromJson(data, KeyModel.class)).getKeyCode() == 1) {
            Intent intent1 = new Intent(KeyCodeAction.Action_Phone_Statue_Out);
            intent1.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            ContextUtil.app.sendBroadcast(intent1);
        }
    }

    private static void parse09Data(Context context, String data) {
        String data_from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        ProtoBufSupportInfo info = (ProtoBufSupportInfo) JsonTool.fromJson(data, ProtoBufSupportInfo.class);
        PbSupportInfo dbInfos = new PbSupportInfo();
        dbInfos.setSupport_health(true);
        dbInfos.setData_from(data_from);
        dbInfos.setSupport_gnss(info.isSupport_gnss());
        dbInfos.setSupport_ppg(info.isSupport_ppg());
        dbInfos.setSupport_ecg(info.isSupport_ecg());
        dbInfos.setSupport_ppg(info.isSupport_ppg());
        dbInfos.setSupport_rri(info.isSupport_rri());
        dbInfos.saveOrUpdate("data_from=?", data_from);
        KLog.e("protobuf开始同步数据");
        if (!ProtoBufSync.getInstance().isSync()) {
            ProtoBufSync.getInstance().syncData();
        }
    }

    private static void parse70Data(Context context, String data) {
        ProtoBufRealTimeData protoBufRealTimeData = (ProtoBufRealTimeData) JsonTool.fromJson(data, ProtoBufRealTimeData.class);
        if (protoBufRealTimeData.isBattery()) {
            PbDeviceInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Battery, data);
            EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Battery_Update));
        } else if (protoBufRealTimeData.isHearth()) {
            CurrData_0x29 currData = new CurrData_0x29();
            currData.setTotalSteps(protoBufRealTimeData.getSteps() + "");
            currData.setTotalCalories(protoBufRealTimeData.getCalorie() + "");
            PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Curr_0x29_Data, JsonUtils.toJson(currData));
            DateUtil d = new DateUtil();
            DataUtil.saveTBWalk(d.getYear(), d.getMonth(), d.getDay(), protoBufRealTimeData.getCalorie() + "", protoBufRealTimeData.getDistance() + "", protoBufRealTimeData.getSteps() + "");
            TB_v3_sport_total_data historyData = V3_sport_historyData_biz.query_data_by_timeStamp(ContextUtil.getLUID() + "", ((long) ((d.getYear() * d.getMonth()) + d.getDay())) + ContextUtil.getLUID());
            if (historyData != null) {
                Map<String, Integer> v3data = V3_sport_type_data_biz.queryDataFromTB(ContextUtil.getLUID() + "", d.getYear(), d.getMonth(), d.getDay());
                historyData.setTotal_steps(((Integer) v3data.get("steps")).intValue());
                historyData.setTotal_calorie((float) ((Integer) v3data.get(Field.NUTRIENT_CALORIES)).intValue());
                historyData.updateAll("time_stamp=?", historyData.getTime_stamp() + "");
            }
            EventBus.getDefault().post(currData);
        } else if (protoBufRealTimeData.isKey()) {
            int code = protoBufRealTimeData.getKeyMode();
            KLog.i("=============" + code);
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
            } else {
                context.sendBroadcast(new Intent(KeyCodeAction.Action_Seleie_Data));
            }
        }
    }

    private static void parse01Data(Context context, String data) {
    }

    private static void parse00Data(Context context, String data) {
        if (!(BluetoothOperation.getWristBand() == null || BluetoothOperation.getWristBand().getName() == null || BluetoothOperation.getWristBand().getAddress() == null)) {
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name, BluetoothOperation.getWristBand().getName());
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address, BluetoothOperation.getWristBand().getAddress());
        }
        ProtoBufHardwareInfo info = (ProtoBufHardwareInfo) JsonUtils.fromJson(data, ProtoBufHardwareInfo.class);
        PbDeviceInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Information, data);
        KLog.i("firmware version:" + info.getVersion());
        DeviceSettingsSend dss = new DeviceSettingsSend();
        dss.setApp(AppConfigUtil.APP_TYPE);
        dss.setModel(info.getModel());
        dss.setVersion(info.getVersion());
        dss.setApp_platform(1);
        DeviceSettingsBiz.getInstance().remoteDeviceSettings(dss);
        UserDeviceReq req = new UserDeviceReq();
        req.setUid(ContextUtil.getLUID());
        req.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(info.getModel()));
        EventBus.getDefault().post(new HaveGetModelEvent(info.getModel()));
        req.setFw_version(info.getVersion());
        DeviceSettingsBiz.getInstance().upUserDevice(req);
    }

    private static synchronized void parse80Data(final Context context, final String data) {
        synchronized (ProtoBufDataParsePresenter.class) {
            int hisDataCase = JsonTool.getIntValue(data, "hisDataCase");
            final long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            final String from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
            if (hisDataCase == 3) {
                fixedThreadPool.execute(new Runnable() {
                    public void run() {
                        ProtoBufHisIndexTable i7BHisIndexTable = (ProtoBufHisIndexTable) JsonTool.fromJson(data, ProtoBufHisIndexTable.class);
                        KLog.d("protobuf-sync", data);
                        ProtoBufSync.getInstance().syncDetailData(context, ProtoBufIndex.parseIndex(i7BHisIndexTable));
                    }
                });
            } else if (hisDataCase == 4) {
                final int hisDataType = JsonTool.getIntValue(data, "hisDataType");
                if (hisDataType == 3) {
                    mHandler.removeCallbacks(sync80Timeout);
                    fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            ProtoBufHisHealthData i7BHisHealthData = (ProtoBufHisHealthData) JsonTool.fromJson(data, ProtoBufHisHealthData.class);
                            ProtoBuf_80_data data80 = new ProtoBuf_80_data();
                            data80.setUid(uid);
                            data80.setData_from(from);
                            data80.setYear(i7BHisHealthData.getYear());
                            data80.setMonth(i7BHisHealthData.getMonth());
                            data80.setDay(i7BHisHealthData.getDay());
                            data80.setHour(i7BHisHealthData.getHour());
                            data80.setMinute(i7BHisHealthData.getMinute());
                            data80.setSeq(i7BHisHealthData.getSeq());
                            data80.setSleepData(i7BHisHealthData.getSleep().toString());
                            data80.setCharge(i7BHisHealthData.getSleep().isCharge());
                            data80.setShutdown(i7BHisHealthData.getSleep().isShutdown());
                            data80.setType(i7BHisHealthData.getPedo().getType());
                            data80.setState(i7BHisHealthData.getPedo().getState());
                            data80.setCalorie((((float) i7BHisHealthData.getPedo().getCalorie()) * 1.0f) / 10.0f);
                            data80.setStep(i7BHisHealthData.getPedo().getStep());
                            data80.setDistance(((float) i7BHisHealthData.getPedo().getDistance()) / 10.0f);
                            data80.setMin_bpm(i7BHisHealthData.getHeartRate().getMin_bpm());
                            data80.setMax_bpm(i7BHisHealthData.getHeartRate().getMax_bpm());
                            data80.setAvg_bpm(i7BHisHealthData.getHeartRate().getAvg_bpm());
                            data80.setSDNN(i7BHisHealthData.getHrv().getSDNN());
                            data80.setRMSSD(i7BHisHealthData.getHrv().getRMSSD());
                            data80.setPNN50(i7BHisHealthData.getHrv().getPNN50());
                            data80.setMEAN(i7BHisHealthData.getHrv().getMEAN());
                            data80.setFatigue(i7BHisHealthData.getHrv().getFatigue());
                            data80.setSbp(i7BHisHealthData.getBp().getSbp());
                            data80.setDbp(i7BHisHealthData.getBp().getDbp());
                            data80.setBpTime(i7BHisHealthData.getBp().getTime());
                            data80.setTime(i7BHisHealthData.getTime());
                            data80.setSecond(i7BHisHealthData.getSecond());
                            data80.saveOrUpdate("uid=? and year=? and month=? and day=? and hour=? and minute=? and second=? and data_from=? and seq=?", data80.getUid() + "", data80.getYear() + "", data80.getMonth() + "", data80.getDay() + "", data80.getHour() + "", data80.getMinute() + "", data80.getSecond() + "", data80.getData_from(), data80.getSeq() + "");
                            ProtoBufDataParsePresenter.finishHealth(ProtoBufSync.getInstance().currentProgress(0, i7BHisHealthData.getSeq()), data80.getYear(), data80.getMonth(), data80.getDay(), i7BHisHealthData.getSeq(), uid, from);
                        }
                    });
                    mHandler.removeCallbacks(sync80Timeout);
                    mHandler.postDelayed(sync80Timeout, BootloaderScanner.TIMEOUT);
                } else if (hisDataType == 4) {
                    mHandler.removeCallbacks(sync80Timeout);
                    fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            ProtoBufHisGnssData gnssData = (ProtoBufHisGnssData) JsonTool.fromJson(data, ProtoBufHisGnssData.class);
                            KLog.d("gps数据" + data);
                            TB_62_data tb_62_data = new TB_62_data();
                            DateUtil dateUtil = new DateUtil((long) gnssData.getTime_stamp(), true);
                            tb_62_data.setYear(dateUtil.getYear());
                            tb_62_data.setMonth(dateUtil.getMonth());
                            tb_62_data.setDay(dateUtil.getDay());
                            tb_62_data.setData_from(from);
                            tb_62_data.setHour(dateUtil.getHour());
                            tb_62_data.setMin(dateUtil.getMinute());
                            tb_62_data.setTime(((long) gnssData.getTime_stamp()) * 1000);
                            tb_62_data.setUid(uid);
                            tb_62_data.setSeq(gnssData.getSeq());
                            if (gnssData.getFrequency() <= 0) {
                                tb_62_data.setFreq(1);
                            } else {
                                tb_62_data.setFreq(gnssData.getFrequency());
                            }
                            List<LongitudeAndLatitude> laData = new ArrayList<>();
                            for (Gnss gnss : gnssData.getGnssList()) {
                                LongitudeAndLatitude la = new LongitudeAndLatitude();
                                la.setLatitude((double) gnss.getLatitude());
                                la.setLongitude((double) gnss.getLongitude());
                                la.setAltitude((int) gnss.getAltitude());
                                la.setGps_speed((int) gnss.getSpeed());
                                laData.add(la);
                            }
                            tb_62_data.setGnssData(JsonTool.toJson(laData));
                            tb_62_data.saveOrUpdate("uid=? and time=? and data_from=? and seq=?", uid + "", tb_62_data.getTime() + "", gnssData.getSeq() + "", from);
                            ProtoBufDataParsePresenter.finishGps(ProtoBufSync.getInstance().currentProgress(1, gnssData.getSeq()), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), gnssData.getSeq(), uid, from);
                        }
                    });
                    mHandler.removeCallbacks(sync80Timeout);
                    mHandler.postDelayed(sync80Timeout, BootloaderScanner.TIMEOUT);
                } else if (hisDataType == 5 || hisDataType == 6) {
                    mHandler.removeCallbacks(sync80Timeout);
                    final String str = data;
                    fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            ProtoBufHisEPGData protoBufHisEPGData = (ProtoBufHisEPGData) JsonTool.fromJson(str, ProtoBufHisEPGData.class);
                            List<Integer> ecg_data = protoBufHisEPGData.getEcg_data();
                            List<Integer> ppg_data = protoBufHisEPGData.getPpg_data();
                            KLog.d("ecg和ppg数据");
                            if (ecg_data != null && ecg_data.size() > 0 && hisDataType == 5) {
                                KLog.d("ecg数据");
                                int[] ecgRealData = new int[ecg_data.size()];
                                for (int i = 0; i < ecg_data.size(); i++) {
                                    ecgRealData[i] = ((Integer) ecg_data.get(i)).intValue();
                                }
                                TB_64_data ecgData = new TB_64_data();
                                ecgData.setData_from(from);
                                ecgData.setUid(uid);
                                ecgData.setSeq(protoBufHisEPGData.getSeq());
                                ecgData.setYear(protoBufHisEPGData.getYear());
                                ecgData.setMonth(protoBufHisEPGData.getMonth());
                                ecgData.setDay(protoBufHisEPGData.getDay());
                                ecgData.setHour(protoBufHisEPGData.getHour());
                                ecgData.setMin(protoBufHisEPGData.getMinute());
                                ecgData.setSecond(protoBufHisEPGData.getSecond());
                                DateUtil dateUtil = new DateUtil(ecgData.getYear(), ecgData.getMonth(), ecgData.getDay(), ecgData.getHour(), ecgData.getMin(), ecgData.getSecond());
                                ecgData.setTime(dateUtil.getUnixTimestamp());
                                ecgData.setEcg(JsonTool.toJson(ecgRealData));
                                ecgData.saveOrUpdate("uid=? and data_from=?  and year=? and month=? and day=? and hour=? and min=? and second=? and seq=?", String.valueOf(uid), String.valueOf(from), String.valueOf(ecgData.getYear()), String.valueOf(ecgData.getMonth()), String.valueOf(ecgData.getDay()), String.valueOf(ecgData.getHour()), String.valueOf(ecgData.getMin()), String.valueOf(ecgData.getSecond()), String.valueOf(ecgData.getSeq()));
                                ProtoBufSync.getInstance().currentProgress(2, ecgData.getSeq());
                                ProtoBufDataParsePresenter.finishECG(ProtoBufSync.getInstance().currentProgress(2, ecgData.getSeq()), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), uid, from, ecgData.getSeq());
                            }
                            if (ppg_data != null && ppg_data.size() > 0 && hisDataType == 6) {
                                KLog.d("ppg数据");
                            }
                        }
                    });
                    mHandler.removeCallbacks(sync80Timeout);
                    mHandler.postDelayed(sync80Timeout, BootloaderScanner.TIMEOUT);
                } else if (hisDataType == 7) {
                    mHandler.removeCallbacks(sync80Timeout);
                    fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            ProtoBufHisRriData protoBufHisRriData = (ProtoBufHisRriData) JsonTool.fromJson(data, ProtoBufHisRriData.class);
                            List<Integer> raw_data = protoBufHisRriData.getRaw_data();
                            if (raw_data != null && raw_data.size() > 0) {
                                int[] rriRealData = new int[(raw_data.size() * 2)];
                                for (int i = 0; i < raw_data.size(); i++) {
                                    int ecg = ((Integer) raw_data.get(i)).intValue();
                                    int i2 = (short) (65535 & ecg);
                                    rriRealData[i * 2] = (short) ((ecg >> 16) & 65535);
                                    rriRealData[(i * 2) + 1] = i2;
                                }
                                DateUtil dateUtil = new DateUtil(protoBufHisRriData.getYear(), protoBufHisRriData.getMonth(), protoBufHisRriData.getDay(), protoBufHisRriData.getHour(), protoBufHisRriData.getMinute(), protoBufHisRriData.getSecond());
                                TB_rri_data rri_data = new TB_rri_data();
                                rri_data.setYear(protoBufHisRriData.getYear());
                                rri_data.setMonth(protoBufHisRriData.getMonth());
                                rri_data.setDay(protoBufHisRriData.getDay());
                                rri_data.setHour(protoBufHisRriData.getHour());
                                rri_data.setMinute(protoBufHisRriData.getMinute());
                                rri_data.setSecond(protoBufHisRriData.getSecond());
                                rri_data.setUid(uid);
                                rri_data.setData_from(from);
                                rri_data.setRawData(JsonTool.toJson(rriRealData));
                                rri_data.setSeq(protoBufHisRriData.getSeq());
                                rri_data.setTimeStamp(protoBufHisRriData.getTimestamp());
                                rri_data.setDate(dateUtil.getSyyyyMMddDate());
                                rri_data.saveOrUpdate("uid=? and data_from=?  and year=? and month=? and day=? and hour=? and minute=? and second=? and seq=?", String.valueOf(uid), String.valueOf(from), String.valueOf(rri_data.getYear()), String.valueOf(rri_data.getMonth()), String.valueOf(rri_data.getDay()), String.valueOf(rri_data.getHour()), String.valueOf(rri_data.getMinute()), String.valueOf(rri_data.getSecond()), String.valueOf(rri_data.getSeq()));
                                ProtoBufDataParsePresenter.finishRRI(ProtoBufSync.getInstance().currentProgress(4, protoBufHisRriData.getSeq()), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), uid, from, protoBufHisRriData.getSeq());
                                KLog.d(protoBufHisRriData.toString());
                            }
                        }
                    });
                    mHandler.removeCallbacks(sync80Timeout);
                    mHandler.postDelayed(sync80Timeout, BootloaderScanner.TIMEOUT);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void finishECG(int progress, int year, int month, int day, long uid, String dataFrom, int seq) {
        synchronized (ProtoBufDataParsePresenter.class) {
            if (progress >= 100) {
                DateUtil d = new DateUtil(year, month, day);
                KLog.e("更新ECG信息");
                updateStatus(uid, dataFrom, year, month, day, seq, 2);
                if (d.isToday()) {
                    ModuleRouterEcgService.getInstance().braceletToView(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
                    HealthDataEventBus.updateHealthEcg();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void finishRRI(int progress, int year, int month, int day, long uid, String dataFrom, int seq) {
        synchronized (ProtoBufDataParsePresenter.class) {
            if (progress >= 100) {
                DateUtil d = new DateUtil(year, month, day);
                KLog.e("更新RR1信息");
                saveRRIData(d.getSyyyyMMddDate());
                updateStatus(uid, dataFrom, year, month, day, seq, 4);
                HealthDataEventBus.updateAfEvent(d.getSyyyyMMddDate());
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void finishHealth(int progress, int year, int month, int day, int seq, long uid, String dataFrom) {
        synchronized (ProtoBufDataParsePresenter.class) {
            if (progress >= 100 && !(year == 0 && month == 0 && day == 0)) {
                KLog.d("protobuf-sync-finish----health:" + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day + HelpFormatter.DEFAULT_OPT_PREFIX + seq);
                updateStatus(uid, dataFrom, year, month, day, seq, 0);
                index++;
                if (index % 2 == 0) {
                    KLog.d("protobuf-sync-finish----计算睡眠:" + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day);
                    ProtoBufSleepSqlUtils.dispSleepData(year, month, day);
                }
                pbToOther(year, month, day);
                if (new DateUtil(year, month, day).isToday()) {
                    BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().getRealHealthData());
                    HealthDataEventBus.updateAllUI();
                }
            }
        }
    }

    private static void pbToOther(int year, int month, int day) {
        KLog.i("no2855时间y:" + year + "m:" + month + "d:" + day);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        List<ProtoBuf_80_data> data = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", uid + "", year + "", month + "", day + "", dataFrom).order("seq asc").find(ProtoBuf_80_data.class);
        PbToIvHandler.pbDataToHeart(uid, year, month, day, dataFrom, data);
        PbToIvHandler.pbFatigueDataToIv(uid, year, month, day, dataFrom, data);
        PbToIvHandler.sportAnd51HeartDataToIv(uid, year, month, day, dataFrom, data);
    }

    public static void pbToFatigue(long uid, int year, int month, int day, String dataFrom) {
        List<FatigueData> fatigueData = new ArrayList<>();
        DateUtil dateUtil = new DateUtil();
        for (ProtoBuf_80_data protoBuf80 : DataSupport.where("uid=? and data_from=? and fatigue!=0 and fatigue!=-1000", uid + "", dataFrom).find(ProtoBuf_80_data.class)) {
            fatigueData.add(new FatigueData(dateUtil.getHHmmDate(), (int) protoBuf80.getFatigue()));
        }
        if (fatigueData.size() > 0) {
            DataUtil.saveFatiueData(uid, new DateUtil(year, month, day).getTimestamp(), dataFrom, dateUtil.getY_M_D(), JsonTool.toJson(fatigueData));
        }
    }

    public static void clearTime() {
        index = 0;
    }

    public static void upGpsToFileServer() {
        final long uid = ContextUtil.getLUID();
        final String dataFrom = ContextUtil.getDeviceNameNoClear() + "";
        List<TB_mtk_statue> mtk_statues = DataSupport.where("uid=? and data_from=? and type=? and has_file=?", uid + "", dataFrom, "80", "0").find(TB_mtk_statue.class);
        KLog.e("no2855--> mtk_statues。查询条件 " + uid + " - " + dataFrom);
        if (mtk_statues != null && mtk_statues.size() > 0) {
            KLog.e("no2855--> mtk_statues。size: " + mtk_statues.size());
            for (TB_mtk_statue mtk_statue : mtk_statues) {
                DataUtil.writeMtkGps2TB(uid, dataFrom, mtk_statue.getYear(), mtk_statue.getMonth(), mtk_statue.getDay(), true);
                DataUtil.writeBlueGps2SD(uid, dataFrom, mtk_statue.getYear(), mtk_statue.getMonth(), mtk_statue.getDay(), true);
                mtk_statue.setHas_file(1);
                mtk_statue.update(mtk_statue.getId());
            }
        }
        DateUtil dateUtil = new DateUtil();
        dateUtil.addDay(-20);
        DataSupport.deleteAll(TB_mtk_statue.class, "date<?", dateUtil.getUnixTimestamp() + "");
        mHandler.postDelayed(new Runnable() {
            public void run() {
                ProtoBufDataParsePresenter.uploadMtkData(dataFrom, uid);
            }
        }, 20000);
    }

    /* access modifiers changed from: private */
    public static void uploadMtkData(String dataFrom, long uid) {
        List<TB_sport_gps_segment> sportGpsSegments = DataSupport.where("uid=? and data_from=? and upload=?", uid + "", dataFrom, "0").find(TB_sport_gps_segment.class);
        if (sportGpsSegments != null && sportGpsSegments.size() > 0) {
            for (TB_sport_gps_segment sportGpsSegment : sportGpsSegments) {
                if ("1".equals(sportGpsSegment.getGps_url())) {
                    boolean hasHr = "1".equals(sportGpsSegment.getHeart_url());
                    KLog.e("no2855--> 心率存在: " + hasHr);
                    SportDeviceNetWorkUtil.uploadGps(uid, sportGpsSegment.getStart_time(), sportGpsSegment.getEnd_time(), dataFrom, hasHr, sportGpsSegment.getSport_type());
                } else {
                    SportDeviceNetWorkUtil.uploadHr(true, uid, sportGpsSegment.getStart_time(), sportGpsSegment.getEnd_time(), dataFrom, sportGpsSegment.getSport_type());
                }
            }
        }
        List<TB_sport_ball> sport_balls = DataSupport.where("uid=? and data_from=? and upload_type=?", uid + "", dataFrom, "0").find(TB_sport_ball.class);
        if (sport_balls != null && sport_balls.size() > 0) {
            for (TB_sport_ball sportGpsSegment2 : sport_balls) {
                SportDeviceNetWorkUtil.uploadHr(false, uid, sportGpsSegment2.getStart_time(), sportGpsSegment2.getEnd_time(), dataFrom, sportGpsSegment2.getSport_type());
            }
        }
        List<TB_sport_other> sport_others = DataSupport.where("uid=? and data_from=? and upload_type=?", uid + "", dataFrom, "0").find(TB_sport_other.class);
        if (sport_others != null && sport_others.size() > 0) {
            for (TB_sport_other sportGpsSegment3 : sport_others) {
                SportDeviceNetWorkUtil.uploadHr(false, uid, sportGpsSegment3.getStart_time(), sportGpsSegment3.getEnd_time(), dataFrom, sportGpsSegment3.getSport_type());
            }
        }
    }

    private static void parse90data(String data) {
        if (ProtoBufUpdate.getInstance().isUpdate()) {
            ArrayList<ProtoBufFileUpdateInfo> fileUpdateInfos = JsonTool.getListJson(data, ProtoBufFileUpdateInfo.class);
            if (fileUpdateInfos == null || fileUpdateInfos.size() <= 0) {
                ProtoBufUpdate.getInstance().setUpdate(false);
                return;
            }
            int gpsFont = ProtoBufUpdate.getInstance().getFuType();
            if (fileUpdateInfos.size() > 1) {
                for (int i = 0; i < fileUpdateInfos.size(); i++) {
                    if (((ProtoBufFileUpdateInfo) fileUpdateInfos.get(i)).getFuType() == gpsFont) {
                        ProtoBufUpdate.getInstance().updateDetail(((ProtoBufFileUpdateInfo) fileUpdateInfos.get(i)).getType(), (ProtoBufFileUpdateInfo) fileUpdateInfos.get(i));
                    }
                }
                return;
            }
            ProtoBufUpdate.getInstance().updateDetail(((ProtoBufFileUpdateInfo) fileUpdateInfos.get(0)).getType(), (ProtoBufFileUpdateInfo) fileUpdateInfos.get(0));
            KLog.e("yanxi------>>> aGPS ---数据" + ((ProtoBufFileUpdateInfo) fileUpdateInfos.get(0)).getType());
        }
    }

    private static void parseFFData(Context context, String data) {
        ProtoBufConnectParmas connectParmas = (ProtoBufConnectParmas) JsonTool.fromJson(data, ProtoBufConnectParmas.class);
        PrefUtil.save(context, BaseActionUtils.PROTOBUF_MTU_INFO, connectParmas.getMtu());
        Log.d("mtu", connectParmas.getMtu() + "");
    }

    /* access modifiers changed from: private */
    public static void dispBp() {
        List<ProtoBuf_80_data> bp = DataSupport.where("uid=? and data_from=? and dbp>0 and dbp< 1000 and sbp>0 and sbp<1000", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear()).find(ProtoBuf_80_data.class);
        if (bp != null) {
            for (ProtoBuf_80_data data : bp) {
                TB_BP_data tb_bp_data = new TB_BP_data();
                tb_bp_data.setBpTime((long) data.getTime());
                tb_bp_data.setUid(ContextUtil.getLUID());
                tb_bp_data.setDataFrom(ContextUtil.getDeviceNameNoClear());
                tb_bp_data.setDbp(data.getDbp());
                tb_bp_data.setSbp(data.getSbp());
                tb_bp_data.saveOrUpdate("uid =? and dataFrom=? and bpTime=?", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear(), data.getTime() + "");
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void finishGps(int progress, int year, int month, int day, int seq, long uid, String dataFrom) {
        synchronized (ProtoBufDataParsePresenter.class) {
            if (progress == 100 && !(year == 0 && month == 0 && day == 0)) {
                updateStatus(uid, dataFrom, year, month, day, seq, 1);
                PbToIvHandler.saveGpsToBlue(uid, dataFrom, year, month, day);
            }
        }
    }

    private static void updateStatus(long uid, String dataFrom, int year, int month, int day, int seq, int indexType) {
        ProtoBuf_index_80 index80 = new ProtoBuf_index_80();
        index80.setIsFinish(1);
        index80.updateAll("uid=? and data_from=? and year=? and month=? and day=? and end_idx>=? and start_idx<? and indexType=?", uid + "", dataFrom, year + "", month + "", day + "", seq + "", seq + "", indexType + "");
    }

    /* access modifiers changed from: private */
    public static void updateAllStatus(long uid, String dataFrom) {
        ProtoBuf_index_80 index80 = new ProtoBuf_index_80();
        index80.setIsFinish(1);
        index80.updateAll("uid=? and data_from=?", uid + "", dataFrom);
    }

    private static void saveRRIData(String date) {
    }
}
