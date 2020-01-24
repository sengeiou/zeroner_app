package com.iwown.device_module.device_setting.configure;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import com.alibaba.android.arouter.utils.Consts;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.ble_module.iwown.bean.Power;
import com.iwown.ble_module.iwown.bean.SportType;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.model.ProtoBufHardwareInfo;
import com.iwown.ble_module.proto.model.ProtoBufRealTimeData;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import com.iwown.ble_module.zg_ble.data.model.DeviceSetting;
import com.iwown.ble_module.zg_ble.data.model.ZGHardwareInfo;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SETTING_INDEXS;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.resp.DeviceSettingRemote;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.sql.DevicePref;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_add_sport.bean.AddSport;
import com.iwown.device_module.device_add_sport.util.AddSportUtil;
import com.iwown.device_module.device_alarm_schedule.common.AlarmCommandUtil;
import com.iwown.device_module.device_alarm_schedule.common.AlarmManager;
import com.iwown.device_module.device_alarm_schedule.common.ScheduleCommandUtil;
import com.iwown.device_module.device_alarm_schedule.common.ScheduleManager;
import com.iwown.device_module.device_message_push.bean.MessagePushSwitchStatue;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.iwown.device_module.device_setting.heart.bean.HeartGuidanceStatue;
import com.iwown.device_module.device_setting.language.LanguageUtil;
import com.iwown.lib_common.PreUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DeviceUtils {
    public static FMdeviceInfo getDeviceInfo() {
        String version;
        FMdeviceInfo fMdeviceInfo = new FMdeviceInfo();
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo baseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Information);
                return (baseInfo == null || baseInfo.getContent() == null) ? fMdeviceInfo : (FMdeviceInfo) JsonUtils.fromJson(baseInfo.getContent(), FMdeviceInfo.class);
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo baseInfo2 = ZGDataParsePresenter.getZGBaseInfoByKey(ZG_BaseInfo.key_hardinfo);
                if (baseInfo2 == null || baseInfo2.getContent() == null) {
                    return fMdeviceInfo;
                }
                ZGHardwareInfo zgHardwareInfo = (ZGHardwareInfo) JsonUtils.fromJson(baseInfo2.getContent(), ZGHardwareInfo.class);
                int screenType = zgHardwareInfo.getDev_screen();
                if (screenType != 0) {
                    KLog.i("dev_version" + screenType + ".0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low());
                    version = screenType + ".0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low() + "";
                } else {
                    KLog.i("dev_version1.0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low());
                    version = "1.0." + zgHardwareInfo.getDev_version_high() + Consts.DOT + zgHardwareInfo.getDev_version_low() + "";
                }
                fMdeviceInfo.setSwversion(version);
                fMdeviceInfo.setModel(zgHardwareInfo.getModel().toUpperCase());
                return fMdeviceInfo;
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfo baseInfo3 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Information);
                if (baseInfo3 == null || baseInfo3.getContent() == null) {
                    return fMdeviceInfo;
                }
                com.iwown.ble_module.model.FMdeviceInfo info = (com.iwown.ble_module.model.FMdeviceInfo) JsonUtils.fromJson(baseInfo3.getContent(), com.iwown.ble_module.model.FMdeviceInfo.class);
                fMdeviceInfo.setModel(info.getModel());
                fMdeviceInfo.setSwversion(info.getSwversion());
                return fMdeviceInfo;
            } else if (!BluetoothOperation.isProtoBuf()) {
                return fMdeviceInfo;
            } else {
                PbBaseInfo baseInfo4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Information);
                if (baseInfo4 == null || baseInfo4.getContent() == null) {
                    return fMdeviceInfo;
                }
                ProtoBufHardwareInfo pbHardwareInfo = (ProtoBufHardwareInfo) JsonUtils.fromJson(baseInfo4.getContent(), ProtoBufHardwareInfo.class);
                fMdeviceInfo.setModel(pbHardwareInfo.getModel());
                fMdeviceInfo.setSwversion(pbHardwareInfo.getVersion());
                return fMdeviceInfo;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return fMdeviceInfo;
        }
    }

    public static DeviceSettingLocal localDeviceSetting() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Device_Setting_Other);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (DeviceSettingLocal) JsonUtils.fromJson(info.getContent(), DeviceSettingLocal.class);
                }
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Device_Setting_Other);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (DeviceSettingLocal) JsonUtils.fromJson(info2.getContent(), DeviceSettingLocal.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Device_Setting_Other);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    if (JsonUtils.hasKey(info3.getContent(), "language")) {
                        return (DeviceSettingLocal) JsonUtils.fromJson(info3.getContent(), DeviceSettingLocal.class);
                    }
                    DeviceSettingLocal deviceSettingLocal = (DeviceSettingLocal) JsonUtils.fromJson(info3.getContent(), DeviceSettingLocal.class);
                    deviceSettingLocal.setException(true);
                    return deviceSettingLocal;
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Device_Setting_Other);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (DeviceSettingLocal) JsonUtils.fromJson(info4.getContent(), DeviceSettingLocal.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        KLog.e("================return language type:");
        DeviceSettingLocal deviceSettingLocal2 = new DeviceSettingLocal();
        deviceSettingLocal2.setException(true);
        deviceSettingLocal2.setLanguage(LanguageUtil.getLocalLanguage());
        return deviceSettingLocal2;
    }

    public static void saveLocalDeviceSetting(DeviceSettingLocal setting) {
        if (setting != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            } else if (BluetoothOperation.isZg()) {
                if (AppConfigUtil.isHealthy() && !PrefUtil.getBoolean(ContextUtil.app, UserAction.HEALTHY_LANGUAGE)) {
                    setting.setLanguage(1);
                }
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            }
            DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), getDeviceInfo().getModel());
            if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
                ArrayList<SettingDefault> settingPref = JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class);
                if (settingPref != null && settingPref.size() > 0) {
                    DevicePrefBiz.getInstance().updatePrefToLocal(settingPref, setting, devicePref, null, null);
                }
            }
        }
    }

    public static AutoHeartStatue autoHeartStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info.getContent(), AutoHeartStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info2.getContent(), AutoHeartStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info3.getContent(), AutoHeartStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info4.getContent(), AutoHeartStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new AutoHeartStatue();
    }

    public static void saveAutoHeartStatue(AutoHeartStatue heart) {
        if (heart != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            }
            DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), getDeviceInfo().getModel());
            if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
                ArrayList<SettingDefault> settingPref = JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class);
                if (settingPref != null && settingPref.size() > 0) {
                    DevicePrefBiz.getInstance().updatePrefToLocal(settingPref, null, devicePref, heart, null);
                }
            }
        }
    }

    public static HeartGuidanceStatue heartGuidanceStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info.getContent(), HeartGuidanceStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info2.getContent(), HeartGuidanceStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info3.getContent(), HeartGuidanceStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info4.getContent(), HeartGuidanceStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new HeartGuidanceStatue();
    }

    public static MessagePushSwitchStatue messageSwitchStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info.getContent(), MessagePushSwitchStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info2.getContent(), MessagePushSwitchStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info3.getContent(), MessagePushSwitchStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info4.getContent(), MessagePushSwitchStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new MessagePushSwitchStatue();
    }

    public static int getBattery() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo device = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Battery);
                if (device != null && !TextUtils.isEmpty(device.getContent())) {
                    Power power = (Power) JsonUtils.fromJson(device.getContent(), Power.class);
                    if (power != null) {
                        return power.getPower();
                    }
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo zg_baseInfo = ZGDataParsePresenter.getZGBaseInfoByKey(ZG_BaseInfo.key_deviceset);
                if (zg_baseInfo != null && !TextUtils.isEmpty(zg_baseInfo.getContent())) {
                    DeviceSetting settings = (DeviceSetting) JsonTool.fromJson(zg_baseInfo.getContent(), DeviceSetting.class);
                    if (settings != null) {
                        return settings.getBatteryVolume();
                    }
                }
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfo device2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Battery);
                if (device2 != null && !TextUtils.isEmpty(device2.getContent())) {
                    Power power2 = (Power) JsonUtils.fromJson(device2.getContent(), Power.class);
                    if (power2 != null) {
                        return power2.getPower();
                    }
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo device3 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Battery);
                if (device3 != null && !TextUtils.isEmpty(device3.getContent())) {
                    ProtoBufRealTimeData protoBufRealTimeData = (ProtoBufRealTimeData) JsonTool.fromJson(device3.getContent(), ProtoBufRealTimeData.class);
                    if (protoBufRealTimeData.isBattery()) {
                        return protoBufRealTimeData.getLevel();
                    }
                }
            }
            CommandOperation.getBattery();
            return 0;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            CommandOperation.getBattery();
            return 0;
        }
    }

    public static void writeCommandToDevice(int settingType) {
        try {
            int languageType = LanguageUtil.getLanguageType(getDeviceInfo().getModel());
            KLog.e("---*writeCommandToDevice, language type:" + String.valueOf(languageType));
            if (BluetoothOperation.isZg()) {
                writeCommandToDeviceZG(settingType);
            } else if (BluetoothOperation.isProtoBuf()) {
                writeCommandToDevicePb(settingType);
            } else {
                if (settingType == 14) {
                    int weather = PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Weather_Int);
                    int tem = PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Temperature_Int);
                    int pm25 = PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_pm25);
                    if (PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Weather_Int) == -1 || PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Temperature_Int) == -1000) {
                        KLog.i("没有温度和天气");
                    } else {
                        CommandOperation.setWeather(weather, tem, pm25);
                    }
                } else if (settingType == 9999) {
                    SparseBooleanArray array = new SparseBooleanArray();
                    array.put(0, localDeviceSetting().isLed());
                    array.put(1, localDeviceSetting().isPalmingGesture());
                    array.put(2, localDeviceSetting().isUnit());
                    array.put(3, !localDeviceSetting().isTimeFormat());
                    array.put(4, true);
                    array.put(5, localDeviceSetting().isScreenColor());
                    array.put(6, localDeviceSetting().getLanguage() == -1);
                    array.put(7, true);
                    array.put(9, autoHeartStatue().isHeart_switch());
                    array.put(10, localDeviceSetting().isAutoRecognitionMotion());
                    array.put(11, localDeviceSetting().isWearingManager());
                    byte[] data = new byte[0];
                    if (BluetoothOperation.isIv()) {
                        data = ZeronerSendBluetoothCmdImpl.getInstance().setWristBandGestureAndLight(array, localDeviceSetting().getBackLightStartTime(), localDeviceSetting().getBackLightEndTime(), languageType, localDeviceSetting().isDateFormat() ? 1 : 0, localDeviceSetting().getPalmingGestureStart(), localDeviceSetting().getPalmingGestureEnd());
                    } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                        data = MtkCmdAssembler.getInstance().setWristBandGestureAndLight(array, localDeviceSetting().getBackLightStartTime(), localDeviceSetting().getBackLightEndTime(), languageType, localDeviceSetting().isDateFormat() ? 1 : 0, localDeviceSetting().getPalmingGestureStart(), localDeviceSetting().getPalmingGestureEnd());
                    }
                    int cmd_nums = data.length % 20 == 0 ? data.length / 20 : (data.length / 20) + 1;
                    for (int j = 0; j < cmd_nums; j++) {
                        byte[] data1 = Arrays.copyOfRange(data, j * 20, (j + 1) * 20 > data.length ? data.length : (j + 1) * 20);
                        String s = byte2str(data1);
                        if (s != null) {
                            KLog.e("---mtk setting write to device: " + s);
                        }
                        if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, data1);
                        } else if (BluetoothOperation.isIv()) {
                            DataBean dataBean3 = new DataBean();
                            dataBean3.addData(data1);
                            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean3);
                        }
                    }
                    if (BluetoothOperation.isMTKEarphone() && PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.EARPHONE) == 1) {
                        byte[] r1datas = MtkCmdAssembler.getInstance().setR1Switch(localDeviceSetting().isDouble_touch_switch(), localDeviceSetting().isWear_recognize_switch());
                        String ss = byte2str(r1datas);
                        if (ss != null) {
                            KLog.e("---r1 setting write to device: " + ss);
                        }
                        MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, r1datas);
                    }
                } else if (BluetoothOperation.isIv()) {
                    SparseBooleanArray array2 = new SparseBooleanArray();
                    array2.put(0, localDeviceSetting().isLed());
                    array2.put(1, localDeviceSetting().isPalmingGesture());
                    array2.put(2, localDeviceSetting().isUnit());
                    array2.put(3, !localDeviceSetting().isTimeFormat());
                    array2.put(4, true);
                    array2.put(5, localDeviceSetting().isScreenColor());
                    array2.put(6, localDeviceSetting().getLanguage() == -1);
                    array2.put(7, true);
                    array2.put(9, autoHeartStatue().isHeart_switch());
                    array2.put(10, localDeviceSetting().isAutoRecognitionMotion());
                    array2.put(11, localDeviceSetting().isWearingManager());
                    byte[] bArr = new byte[0];
                    if (getDeviceInfo().getModel().equalsIgnoreCase(WristbandModel.MODEL_I5PR) || getDeviceInfo().getModel().equalsIgnoreCase(WristbandModel.MODEL_I5PK) || getDeviceInfo().getModel().equalsIgnoreCase("v6") || getDeviceInfo().getModel().equalsIgnoreCase("I6DK")) {
                        if (AppConfigUtil.isHealthy()) {
                            languageType = 1;
                        } else {
                            KLog.i("------------------------" + getDeviceInfo().getModel().equalsIgnoreCase(WristbandModel.MODEL_I5PK));
                            if (getDeviceInfo().getModel().equalsIgnoreCase(WristbandModel.MODEL_I5PK)) {
                                languageType = 9;
                            } else {
                                languageType = 0;
                            }
                        }
                    }
                    byte[] data2 = ZeronerSendBluetoothCmdImpl.getInstance().setWristBandGestureAndLight(array2, localDeviceSetting().getBackLightStartTime(), localDeviceSetting().getBackLightEndTime(), languageType, localDeviceSetting().isDateFormat() ? 1 : 0, localDeviceSetting().getPalmingGestureStart(), localDeviceSetting().getPalmingGestureEnd());
                    int cmd_nums2 = data2.length % 20 == 0 ? data2.length / 20 : (data2.length / 20) + 1;
                    for (int j2 = 0; j2 < cmd_nums2; j2++) {
                        byte[] data12 = Arrays.copyOfRange(data2, j2 * 20, (j2 + 1) * 20 > data2.length ? data2.length : (j2 + 1) * 20);
                        String s2 = byte2str(data12);
                        if (s2 != null) {
                            KLog.e("---mtk setting write to device: " + s2);
                        }
                        DataBean dataBean32 = new DataBean();
                        dataBean32.addData(data12);
                        ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean32);
                    }
                } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                    MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getDeviceStateDate());
                }
                DeviceSettingRemote remote = new DeviceSettingRemote();
                remote.setUid(ContextUtil.getLUID());
                remote.setModel(getDeviceInfo().getModel());
                DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), getDeviceInfo().getModel());
                if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
                    remote.setSetting(JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class));
                }
                if (PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Device_Pref_Upload_Time) < System.currentTimeMillis()) {
                    NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                        public void onSuccess(Integer integer) {
                            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Device_Pref_Upload_Time, System.currentTimeMillis() + 300000);
                        }

                        public void onFail(Throwable e) {
                            KLog.e(e.toString());
                        }
                    }).uploadDeviceRef(remote);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private static void writeCommandToDevicePb(int settingType) {
        DeviceSettingLocal settingLocal = localDeviceSetting();
        AutoHeartStatue status = autoHeartStatue();
        HeartGuidanceStatue warming = heartGuidanceStatue();
        int type = settingLocal.getLanguage();
        switch (settingType) {
            case 1:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setLcdGsTime(settingLocal.isPalmingGesture(), settingLocal.getPalmingGestureStart(), settingLocal.getPalmingGestureEnd()));
                return;
            case 2:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setDistenceUnit(settingLocal.isUnit()));
                return;
            case 3:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setHourFormat(settingLocal.isTimeFormat()));
                return;
            case 7:
                ProtoBufSendBluetoothCmdImpl.getInstance().setLanguage(ContextUtil.app, type);
                return;
            case 9:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setDateFormat(settingLocal.isDateFormat()));
                return;
            case 11:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setAutoHeartrate(status.isHeart_switch()));
                return;
            case 13:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setHeartAlarm(warming.isHeart_guidance_switch(), warming.getMaxHeart(), warming.getMinHeart(), 30, 2));
                return;
            case 14:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setTemperatureUnit(settingLocal.isWeatherFormat()));
                return;
            case 24:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setAutoSport(settingLocal.isAutoRecognitionMotion()));
                return;
            case 27:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setHabitualHand(settingLocal.isWearingManager()));
                return;
            case 38:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setBpCaliConf(settingLocal.getBlood()));
                return;
            case 39:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationTime(settingLocal.isNoDisturb(), settingLocal.getStartNoDisturbTime(), settingLocal.getEndNoDisturbTime(), 0, 0));
                return;
            case 41:
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().set24af(settingLocal.isIs24AfSwitch()));
                return;
            case SETTING_INDEXS.All_Of_Them /*9999*/:
                KLog.e("==============All_Of_Them_start================" + type);
                HeartGuidanceStatue heartGuidanceStatue = heartGuidanceStatue();
                UserInfo user = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
                if (user.height == Utils.DOUBLE_EPSILON) {
                    if (user.isMale) {
                        user.height = 175.0d;
                    } else {
                        user.height = 165.0d;
                    }
                }
                if (user.weight == Utils.DOUBLE_EPSILON) {
                    if (user.isMale) {
                        user.weight = 70.0d;
                    } else {
                        user.weight = 50.0d;
                    }
                }
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setDeviceInfo(settingLocal.getLanguage(), settingLocal.isPalmingGesture(), settingLocal.getPalmingGestureStart(), settingLocal.getPalmingGestureEnd(), settingLocal.isUnit(), settingLocal.isWeatherFormat(), settingLocal.isTimeFormat(), settingLocal.isDateFormat(), status.isHeart_switch(), settingLocal.isAutoRecognitionMotion(), settingLocal.isWearingManager()));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setPeerInfo(heartGuidanceStatue.isHeart_guidance_switch(), heartGuidanceStatue.getMaxHeart(), heartGuidanceStatue.getMinHeart(), 30, 2, (int) user.height, (int) user.weight, user.isMale, user.age, 100, 100));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().set24af(settingLocal.isIs24AfSwitch()));
                KLog.e("==============All_Of_Them_start================" + type);
                return;
            default:
                return;
        }
    }

    public static void writeCommandToDeviceZG(int settingType) {
        int i;
        DeviceSettingLocal settingLocal = localDeviceSetting();
        AutoHeartStatue status = autoHeartStatue();
        HeartGuidanceStatue warming = heartGuidanceStatue();
        int type = settingLocal.getLanguage();
        switch (settingType) {
            case 1:
                ZGBaseUtils.setGesture(ContextUtil.app, settingLocal.isPalmingGesture() ? 1 : 0, settingLocal.getPalmingGestureStart(), settingLocal.getPalmingGestureEnd());
                return;
            case 2:
                ZGBaseUtils.setUnit(ContextUtil.app, settingLocal.isUnit() ? 1 : 0);
                return;
            case 3:
                ZGBaseUtils.setTimeDisplay(ContextUtil.app, settingLocal.isTimeFormat() ? 1 : 0);
                return;
            case 7:
                ZGBaseUtils.setLanguage(ContextUtil.app, type);
                return;
            case 11:
                ZGBaseUtils.setAutoHeart(ContextUtil.app, status.isHeart_switch() ? 1 : 0, status.getHeart_startTime(), status.getHeart_endTime());
                return;
            case 13:
                ZGBaseUtils.setHeartAlarm(ContextUtil.app, warming.isHeart_guidance_switch() ? 2 : 0, warming.getMaxHeart(), warming.getMinHeart());
                return;
            case 14:
                ZGBaseUtils.setTemperatureUnit(ContextUtil.app, settingLocal.isWeatherFormat() ? 1 : 0);
                return;
            case 25:
                ZGBaseUtils.setPhoneCallStatus(ContextUtil.app, settingLocal.isCallEnable() ? 1 : 0);
                ZGBaseUtils.setPhoneAlertTime(ContextUtil.app, settingLocal.getCallStart(), settingLocal.getCallEnd());
                return;
            case 30:
                ZGBaseUtils.setMsgNotificationSwitch(ContextUtil.app, settingLocal.isMsgEnable() ? 1 : 0);
                ZGBaseUtils.setNotifyMsgTime(ContextUtil.app, settingLocal.getMsgStart(), settingLocal.getMsgEnd());
                return;
            case 35:
                ZGBaseUtils.setWelcomePageContent(settingLocal.getWelcome_text());
                return;
            case 38:
                ZGBaseUtils.setWelcomePageContent();
                return;
            case SETTING_INDEXS.All_Of_Them /*9999*/:
                KLog.e("==============All_Of_Them_start================" + type);
                Application application = ContextUtil.app;
                int i2 = settingLocal.isCallEnable() ? 1 : 0;
                int callStart = settingLocal.getCallStart();
                int callEnd = settingLocal.getCallEnd();
                int i3 = settingLocal.isMsgEnable() ? 1 : 0;
                int msgStart = settingLocal.getMsgStart();
                int msgEnd = settingLocal.getMsgEnd();
                int i4 = settingLocal.isPalmingGesture() ? 1 : 0;
                int palmingGestureStart = settingLocal.getPalmingGestureStart();
                int palmingGestureEnd = settingLocal.getPalmingGestureEnd();
                int i5 = status.isHeart_switch() ? 1 : 0;
                int heart_startTime = status.getHeart_startTime();
                int heart_endTime = status.getHeart_endTime();
                int i6 = warming.isHeart_guidance_switch() ? 2 : 0;
                int maxHeart = warming.getMaxHeart();
                int minHeart = warming.getMinHeart();
                int i7 = settingLocal.isUnit() ? 1 : 0;
                int i8 = settingLocal.isWeatherFormat() ? 1 : 0;
                if (settingLocal.isTimeFormat()) {
                    i = 1;
                } else {
                    i = 0;
                }
                ZGBaseUtils.setAllOfThem(application, i2, callStart, callEnd, i3, msgStart, msgEnd, i4, palmingGestureStart, palmingGestureEnd, i5, heart_startTime, heart_endTime, type, i6, maxHeart, minHeart, i7, i8, i);
                ZGBaseUtils.setWelcomePageContent(settingLocal.getWelcome_text());
                KLog.e("==============All_Of_Them_end================");
                return;
            default:
                return;
        }
    }

    public static void sendSupportSportCommand(List<AddSport> addSports) {
        for (int i = 0; i < 7; i++) {
            ArrayList<Byte> list = new ArrayList<>();
            list.add(0, Byte.valueOf((byte) i));
            for (AddSport sport : addSports) {
                if (sport.getType() != 999) {
                    list.add(Byte.valueOf((byte) 32));
                    list.add(Byte.valueOf((byte) 3));
                    list.add(Byte.valueOf((byte) sport.getType()));
                }
            }
            list.add(Byte.valueOf((byte) 32));
            list.add(Byte.valueOf((byte) 3));
            list.add(Byte.valueOf(1));
            if (BluetoothOperation.isIv()) {
                byte[] totalData = ZeronerSendBluetoothCmdImpl.getInstance().setSportGole(list);
                int cmd_nums = totalData.length % 20 == 0 ? totalData.length / 20 : (totalData.length / 20) + 1;
                for (int j = 0; j < cmd_nums; j++) {
                    byte[] data1 = Arrays.copyOfRange(totalData, j * 20, (j + 1) * 20 > totalData.length ? totalData.length : (j + 1) * 20);
                    DataBean dataBean3 = new DataBean();
                    dataBean3.addData(data1);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean3);
                }
            } else if (BluetoothOperation.isMtk()) {
            }
        }
    }

    public static List<AddSport> defaultIcon() {
        String setting;
        int default_Icon = 4;
        TB_DeviceSettings settings = DeviceSettingsBiz.getInstance().queryDevSettings();
        Gson gson = new Gson();
        if (settings == null) {
            setting = "";
        } else {
            setting = settings.getSetting();
        }
        List<SettingBean> dev_settings = (List) gson.fromJson(setting, new TypeToken<List<SettingBean>>() {
        }.getType());
        if (dev_settings != null && dev_settings.size() > 0) {
            for (SettingBean setting2 : dev_settings) {
                switch (setting2.getType()) {
                    case 23:
                        default_Icon = setting2.getValueInt();
                        break;
                }
            }
        }
        ArrayList<AddSport> defaultList = new ArrayList<>();
        for (int i = 0; i < default_Icon; i++) {
            AddSport addSport = new AddSport();
            addSport.setType(999);
            addSport.setSportName("");
            addSport.setDrawableId(R.mipmap.circle_3x);
            defaultList.add(addSport);
        }
        try {
            DeviceBaseInfo deviceBaseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sports_Status);
            String content = null;
            if (!(deviceBaseInfo == null || deviceBaseInfo.getContent() == null)) {
                content = deviceBaseInfo.getContent();
            }
            ArrayList<AddSport> checked = JsonUtils.getListJson(content, AddSport.class);
            if (checked == null || checked.size() != default_Icon) {
                return defaultList;
            }
            return checked;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return defaultList;
        }
    }

    public static SportType supportSports() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo deviceBaseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sport);
                if (deviceBaseInfo != null && !TextUtils.isEmpty(deviceBaseInfo.getContent())) {
                    return (SportType) JsonUtils.fromJson(deviceBaseInfo.getContent(), SportType.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo deviceBaseInfo2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sport);
                if (deviceBaseInfo2 != null && !TextUtils.isEmpty(deviceBaseInfo2.getContent())) {
                    return (SportType) JsonUtils.fromJson(deviceBaseInfo2.getContent(), SportType.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo deviceBaseInfo3 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sport);
                if (deviceBaseInfo3 != null && !TextUtils.isEmpty(deviceBaseInfo3.getContent())) {
                    return (SportType) JsonUtils.fromJson(deviceBaseInfo3.getContent(), SportType.class);
                }
            }
            if (0 == 0) {
                if (BluetoothOperation.isIv()) {
                    byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().getSportType();
                    DataBean dataBean2 = new DataBean();
                    dataBean2.addData(data);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean2);
                } else if (BluetoothOperation.isMtk()) {
                    MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ZeronerSendBluetoothCmdImpl.getInstance().getSportType());
                } else if (BluetoothOperation.isProtoBuf()) {
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new SportType();
    }

    public static List<AddSport> unCheckSupportSports(SportType sportTypes) {
        ArrayList<AddSport> unAddedRecleList = new ArrayList<>();
        if (sportTypes == null) {
            return unAddedRecleList;
        }
        int[] sports = sportTypes.getTypes();
        if (sports == null || sports.length <= 0) {
            return unAddedRecleList;
        }
        for (int i = 0; i < sports.length; i++) {
            AddSport info = new AddSport();
            int type = sports[i];
            if (!(type == 1 || type == 4 || type == 129 || type == 132 || type == 135 || type == 136 || type == 137)) {
                String model = getDeviceInfo().getModel();
                if (TextUtils.isEmpty(model) || ((!WristbandModel.MODEL_I6NH.equalsIgnoreCase(model) && !WristbandModel.MODEL_I6H9.equalsIgnoreCase(model) && !WristbandModel.MODEL_I6HR.equalsIgnoreCase(model)) || type != 131)) {
                    info.setType(sports[i]);
                    if (AddSportUtil.getSporyImgOrName(0, type) != -1) {
                        info.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, type)));
                        info.setDrawableId(AddSportUtil.getSporyImgOrName(1, type));
                        unAddedRecleList.add(info);
                    }
                }
            }
        }
        try {
            DeviceBaseInfo deviceBaseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sports_Status_UnChecked);
            String content = null;
            if (!(deviceBaseInfo == null || deviceBaseInfo.getContent() == null)) {
                content = deviceBaseInfo.getContent();
            }
            if (content == null) {
                return unAddedRecleList;
            }
            ArrayList<AddSport> unChecked = JsonUtils.getListJson(content, AddSport.class);
            if (unChecked == null || unChecked.size() <= 0) {
                return unAddedRecleList;
            }
            return unChecked;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return unAddedRecleList;
        }
    }

    public static String byte2str(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void cleanDeviceAlarms() {
        if (BluetoothOperation.isIv() || BluetoothOperation.isMtk()) {
            for (int i = 0; i < 8; i++) {
                AlarmCommandUtil.closeAlarm(i);
            }
        } else if (BluetoothOperation.isZg()) {
            ZGBaseUtils.clearExtraAlarmSchedule();
            ZGBaseUtils.updateAlarmAndSchedule(ContextUtil.app);
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().clearAlarm());
        }
        List<TB_Alarmstatue> alarms = AlarmManager.getAllAlarmData();
        for (int i2 = 0; i2 < alarms.size(); i2++) {
            TB_Alarmstatue alarm = (TB_Alarmstatue) alarms.get(i2);
            if (alarm.getOpenState() > 0) {
                AlarmCommandUtil.writeAlarm(alarm.getAc_Idx(), alarm.getAc_Conf(), alarm.getAc_Hour(), alarm.getAc_Minute(), alarm.getAc_String());
            }
        }
    }

    public static void cleanSchedule() {
        if (ScheduleManager.getInstance() == null) {
            ScheduleManager.initData(ContextUtil.app);
        }
        ScheduleManager.getInstance().newScheduleBluetoothDataParseBiz();
        ScheduleManager.getInstance().registerReceiver();
        List<TB_schedulestatue> schedules = ScheduleManager.getInstance().getAllScheduleData();
        if (BluetoothOperation.isMtk()) {
            MtkCmdAssembler.getInstance().clearAllSchedule(ContextUtil.app);
        } else if (BluetoothOperation.isIv()) {
            ZeronerSendBluetoothCmdImpl.getInstance().clearAllSchedule(ContextUtil.app);
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().clearCalendar());
        }
        if (schedules != null && schedules.size() > 0) {
            for (int i = 0; i < schedules.size(); i++) {
                TB_schedulestatue tss = (TB_schedulestatue) schedules.get(i);
                DateUtil d1 = new DateUtil(tss.getYear(), tss.getMonth(), tss.getDay(), tss.getHour(), tss.getMinute());
                DateUtil d2 = new DateUtil(new Date());
                KLog.i("============" + (d1.getUnixTimestamp() >= d2.getUnixTimestamp()) + "===" + d1.getY_M_D_H_M_S() + "===" + d2.getY_M_D_H_M_S());
                if (d1.getUnixTimestamp() >= d2.getUnixTimestamp()) {
                    ScheduleCommandUtil.add((TB_schedulestatue) schedules.get(i));
                }
            }
        }
    }

    public static int getTimeZoneInt() {
        int timeZone = Math.round(((float) Calendar.getInstance().getTimeZone().getRawOffset()) / 3600000.0f);
        KLog.e("no2855 -->时区 " + timeZone);
        return timeZone;
    }

    public static void networkModelInit() {
        if (!BluetoothOperation.isConnected() && BluetoothOperation.isZg()) {
            ZGBaseUtils.networkModelInit();
        }
    }

    public static void getBloodlocal() {
        SharedPreferences read = PreUtil.getSharedPreferences("bloodhistory", ContextUtil.app);
        int Onesbp_lb = read.getInt("Onesbp_lb", 0);
        int Onedbp_lb = read.getInt("Onedbp_lb", 0);
        CommandOperation.setBoodSettingData(read.getInt("src_sbp", 0), read.getInt("src_dbp", 0), (Onesbp_lb + read.getInt("Twosbp_lb", 0)) / 2, (Onedbp_lb + read.getInt("Twodbp_lb", 0)) / 2);
    }
}
