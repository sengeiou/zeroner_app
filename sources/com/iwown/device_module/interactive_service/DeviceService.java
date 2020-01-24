package com.iwown.device_module.interactive_service;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.ble_module.model.Weather24h;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.data_link.device.DeviceInfo;
import com.iwown.data_link.device.DeviceSetting;
import com.iwown.data_link.device.IDeviceService;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService.DeviceStatusListener;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothCallbackReceiver;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MTKHeadSetSync;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_log.LogUpUtils;
import com.iwown.device_module.device_message_push.utils.ServiceUtils;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_weather.Weather;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.litepal.crud.DataSupport;

@Route(path = "/device/info_service")
public class DeviceService implements IDeviceService {
    DeviceStatusListener listener;
    private BleStatueReceiver receiver;

    private class BleStatueReceiver extends BluetoothCallbackReceiver {
        private BleStatueReceiver() {
        }

        public void onBluetoothInit() {
            super.onBluetoothInit();
            if (DeviceService.this.listener != null) {
                DeviceService.this.listener.bluetoothInit();
            }
        }

        public void connectStatue(boolean isConnect) {
            super.connectStatue(isConnect);
            if (DeviceService.this.listener != null) {
                DeviceService.this.listener.deviceConnectStatus(isConnect);
            }
        }
    }

    public void init(Context context) {
        this.receiver = new BleStatueReceiver();
        LocalBroadcastManager.getInstance(context).registerReceiver(this.receiver, BaseActionUtils.getIntentFilter());
    }

    public DeviceInfo getDeviceInfo() {
        Context context = DeviceInitUtils.getInstance().getMyApplication();
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.dev_name = PrefUtil.getString(context, BleAction.Bluetooth_Device_Name);
        deviceInfo.mac = PrefUtil.getString(context, BleAction.Bluetooth_Device_Name);
        deviceInfo.sdk_type = PrefUtil.getInt(context, SharedPreferencesAction.User_Sdk_type);
        FMdeviceInfo fMdeviceInfo = DeviceUtils.getDeviceInfo();
        if (fMdeviceInfo != null) {
            deviceInfo.dev_modle = fMdeviceInfo.getModel();
        }
        return deviceInfo;
    }

    public boolean isWristConnected() {
        return BluetoothOperation.isConnected();
    }

    public boolean isNotificationServiceRun() {
        KLog.e("--------------ServiceUtils.ensureCollectorRunning()---------------" + ServiceUtils.ensureCollectorRunning());
        return ServiceUtils.ensureCollectorRunning();
    }

    public boolean isBind() {
        return BluetoothOperation.isBind();
    }

    public void setListener(DeviceStatusListener listener2) {
        this.listener = listener2;
    }

    public boolean isSupportRealTimeHeart() {
        return false;
    }

    public void controlRealTimeHr(boolean isOn) {
        BluetoothOperation.switchStandardHeartRate(isOn);
    }

    public boolean isSyncDataInfo() {
        return CommandOperation.isSync();
    }

    public void syncDataInfo(boolean toSync) {
        CommandOperation.syncDeviceData(toSync);
    }

    public void getPower() {
        CommandOperation.getBattery();
    }

    public boolean isZG() {
        return BluetoothOperation.isZg();
    }

    public boolean isMtk() {
        return BluetoothOperation.isMtk();
    }

    public boolean isIw() {
        return BluetoothOperation.isIv();
    }

    public boolean isMTKHeadset() {
        return BluetoothOperation.isMTKEarphone();
    }

    public boolean isProtoBuf() {
        return BluetoothOperation.isProtoBuf();
    }

    public boolean isSupport08() {
        return PrefUtil.getBoolean(ContextUtil.app, FirmwareAction.Firmware_Can_Support_08) || BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone();
    }

    public boolean isSupportHeart() {
        return DeviceSettingsBiz.getInstance().supportSomeSetting(21);
    }

    public boolean canEpoThisTime() {
        return false;
    }

    public void startEpo() {
    }

    public String getDeviceModel() {
        return DeviceUtils.getDeviceInfo().getModel();
    }

    public String getDeviceVersion() {
        return DeviceUtils.getDeviceInfo().getSwversion();
    }

    public void writeWeather(float temperature, int weather_type, String country, String pm_25, String futureWeather24Json) {
        KLog.e("-----------------" + temperature + "---------------" + weather_type + "-------------" + futureWeather24Json);
        if (weather_type != -1) {
            int pm25 = 0;
            try {
                if (!TextUtils.isEmpty(pm_25)) {
                    if (pm_25.equalsIgnoreCase("-1.0")) {
                        pm_25 = "1";
                    }
                }
                if (!TextUtils.isEmpty(pm_25)) {
                    pm25 = Integer.parseInt(pm_25);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            CommandOperation.setWeather(weather_type, (int) temperature, pm25);
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Int, weather_type);
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Temperature_Int, (int) temperature);
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_class_model_country, country);
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_pm25, pm25);
            if (new DateUtil().getUnixTimestamp() >= PrefUtil.getLong(ContextUtil.app, SharedPreferencesAction.APP_SDK_UPDATE_Time)) {
                NetFactory.getInstance().getClient(null).deviceClassId_1(AppConfigUtil.APP_TYPE, country);
                NetFactory.getInstance().getClient(null).deviceClassIdDetail(AppConfigUtil.APP_TYPE, country);
            }
            ArrayList listJson = JsonUtils.getListJson(futureWeather24Json, Weather.class);
            Collections.sort(listJson);
            boolean weatherFormat = DeviceUtils.localDeviceSetting().isWeatherFormat();
            if (listJson != null && listJson.size() > 0) {
                DateUtil d = new DateUtil(((Weather) listJson.get(0)).getTime_stamp(), true);
                List<Weather24h> weather24hs = new ArrayList<>();
                for (int i = 0; i < listJson.size(); i++) {
                    Weather weather = (Weather) listJson.get(i);
                    Weather24h w24i = new Weather24h();
                    w24i.setWeather_type(weather.getWeather_type());
                    if (!weatherFormat || BluetoothOperation.isProtoBuf()) {
                        w24i.setTemperature(weather.getTemperature());
                    } else {
                        w24i.setTemperature((int) ((((float) weather.getTemperature()) * 1.8f) + 32.0f));
                    }
                    w24i.setPm_2_5(weather.getPm_25());
                    w24i.setTime_stamp(weather.getTime_stamp());
                    weather24hs.add(w24i);
                    if ((BluetoothOperation.isProtoBuf() && weather24hs.size() >= 12) || weather24hs.size() >= 24) {
                        break;
                    }
                }
                if (weather24hs.size() > 0) {
                    KLog.e("-------------------------" + JsonUtils.toJson(weather24hs) + "===-----====" + (!weatherFormat));
                    try {
                        KLog.i("-------" + d.getY_M_D_H_M_S());
                        CommandOperation.weatherDays(ContextUtil.app, d.getYear(), d.getMonth(), d.getDay(), d.getHour(), !weatherFormat, weather24hs, null, temperature);
                    } catch (Exception e2) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                }
            }
        }
    }

    public void updateTargetStep(int step, float calorie) {
        CommandOperation.setTarget(step, (int) calorie);
        PrefUtil.save((Context) ContextUtil.app, UserAction.User_Step_Target, step);
        PrefUtil.save((Context) ContextUtil.app, UserAction.User_Step_Calorie, (int) calorie);
        HealthDataEventBus.updateHealthWeightEvent();
    }

    public void updateMeasureUnit(EnumMeasureUnit unit) {
        UserConfig.getInstance().setMertric(unit == EnumMeasureUnit.Metric);
        UserConfig.getInstance().save();
        HealthDataEventBus.updateMertricUIChange();
    }

    public void updateBaseUserInfo(int gender, float height, float weight, int age) {
        CommandOperation.setUserInfo();
        UserConfig.getInstance().initInfoFromOtherModule();
        int goal = PrefUtil.getInt((Context) ContextUtil.app, UserAction.User_Step_Target, 10000);
        int calorie = (int) ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).getGoalCaloria();
        KLog.i("==================================" + calorie);
        PrefUtil.save((Context) ContextUtil.app, UserAction.User_Step_Calorie, calorie);
        CommandOperation.setTarget(goal, calorie);
    }

    public void upTodayLogFile() {
        L.file(Utils.getPhoneInfo(ContextUtil.app), 4);
        LogUpUtils.upTodayBleLog();
    }

    public void userBindWifiScale(long uid) {
        NetFactory.getInstance().getClient(null).bindScaleInfo(uid);
    }

    public DeviceSetting getDeviceSetting(String model) {
        TB_DeviceSettings deviceSettings = (TB_DeviceSettings) DataSupport.where("model=?", model + "").findFirst(TB_DeviceSettings.class);
        if (deviceSettings != null) {
            return (DeviceSetting) JsonTool.fromJson(JsonTool.toJson(deviceSettings), DeviceSetting.class);
        }
        return null;
    }

    public String getDeviceAddress() {
        return ContextUtil.getDeviceAddressNoClear();
    }

    public void setGpsSportTime(long time) {
        MTKHeadSetSync.getInstance().setGpsSportTime(time);
    }

    public String getDeviceClient() {
        return PrefUtil.getString(DeviceInitUtils.getInstance().getMyApplication(), BleAction.Bluetooth_Device_Name_Current_Device);
    }

    public boolean supportSomeSetting(int set_index) {
        return DeviceSettingsBiz.getInstance().supportSomeSetting(set_index);
    }
}
