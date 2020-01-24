package com.iwown.device_module.common.Bluetooth;

import android.content.Context;
import android.support.annotation.Size;
import android.text.TextUtils;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.model.Weather24h;
import com.iwown.ble_module.model.Weather7D;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.model.WeatherEvent;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.eventbus.DeviceUpdateWeatherEvent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.StartSyncDataEvent;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SETTING_INDEXS;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkToIvHandler;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MTKHeadSetSync;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufSync;
import com.iwown.device_module.common.Bluetooth.sync.zg.ZgSync;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_add_sport.bean.AddSport;
import com.iwown.device_module.device_add_sport.util.AddSportUtil;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CommandOperation {
    public static void initCommand() {
        boolean z = true;
        KLog.e(String.format("---sdk type:%d", new Object[]{Integer.valueOf(PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type))}));
        if (BluetoothOperation.getWristBand() != null) {
            KLog.e("protobuf initCommand");
            GlobalDataUpdater.setBindDevice(ContextUtil.app, BluetoothOperation.getWristBand().getName());
            KLog.i("===========initCommand device information:" + JsonUtils.toJson(BluetoothOperation.getWristBand()));
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name, BluetoothOperation.getWristBand().getName());
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address, BluetoothOperation.getWristBand().getAddress());
            KLog.i("ContextUtil.isBackground:" + ContextUtil.isBackground + "=========================deviceName:" + BluetoothOperation.getWristBand().getName());
            if (checkUid() || checkDevice()) {
                PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Epo_Time, "");
                PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_agps_Time, "");
                PrefUtil.save((Context) ContextUtil.app, UserAction.I6S7_BRACELET_KEY, "");
                clearAddSports();
                i6S7(DeviceUtils.getDeviceInfo().getModel());
                if (DeviceUtils.getDeviceInfo().getModel().equalsIgnoreCase("I6RU")) {
                    DeviceUtils.localDeviceSetting().setLanguage(8);
                    KLog.i("------------------------I6RU---");
                    EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Model_Down));
                }
            }
            KLog.e("protobuf checking");
            if (checkUid() || checkDevice() || !PrefUtil.getBoolean(ContextUtil.app, FirmwareAction.UNBind_Device_Button)) {
                DeviceUtils.cleanDeviceAlarms();
                DeviceUtils.cleanSchedule();
                DeviceUtils.defaultIcon();
            }
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.UNBind_Device_Button, true);
            if (!ContextUtil.isBackground) {
                String currDevice = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device);
                String lastDevice = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Last_Device_Address);
                long lastSettingTime = PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Last_Sync_Setting_Time);
                StringBuilder append = new StringBuilder().append("currDevice:").append(currDevice).append("=========================lastDevice:").append(lastDevice).append("====");
                if (lastSettingTime > System.currentTimeMillis()) {
                    z = false;
                }
                KLog.i(append.append(z).toString());
                if (BluetoothOperation.isZg()) {
                    int weatherType = PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Weather_Int);
                    int temperature = PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Temperature_Int);
                    int pm25 = PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_pm25);
                    if (weatherType == 0 && temperature == 0) {
                        setWeather(-1, 100, 0);
                    } else {
                        setWeather(weatherType, temperature, pm25);
                    }
                }
                KLog.e("protobuf前台运行");
                if (!currDevice.equals(lastDevice) || lastSettingTime <= System.currentTimeMillis() || !PrefUtil.getBoolean(ContextUtil.app, FirmwareAction.UNBind_Device_Button)) {
                    HealthDataEventBus.updateAllUI();
                    KLog.e("protobuf开始进行syncSetting");
                    syncSetting();
                }
                syncDeviceData(false);
            }
            GlobalDataUpdater.setPrevBindDevice(ContextUtil.app, ContextUtil.getDeviceNameCurr());
            if (BluetoothOperation.isZg()) {
                ZGBaseUtils.clearExtraAlarmSchedule();
            }
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Last_Device_Address, BluetoothOperation.getWristBand().getAddress());
        }
    }

    public static void syncSetting() {
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Sync_Setting_Time, System.currentTimeMillis() + 1800000);
        int goal = 10000;
        int calorie = 1000;
        try {
            goal = PrefUtil.getInt((Context) ContextUtil.app, UserAction.User_Step_Target, 10000);
            calorie = PrefUtil.getInt((Context) ContextUtil.app, UserAction.User_Step_Calorie, 1000);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (BluetoothOperation.isZg()) {
            BleDataOrderHandler.getInstance().setStride(ContextUtil.app, 60, 85);
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getHardwareFeatures()));
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getFirmwareInformation()));
            try {
                if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, UserAction.User_Target_Steps))) {
                    BleDataOrderHandler.getInstance().setStepsTarget(ContextUtil.app, Integer.parseInt(PrefUtil.getString(ContextUtil.app, UserAction.User_Target_Steps)));
                }
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
            if (new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Weather_Update) > 1800) {
                EventBus.getDefault().post(new DeviceUpdateWeatherEvent());
                KLog.e("===请求天气====");
                PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Update, new DateUtil().getUnixTimestamp());
            }
            setTarget(goal, calorie);
            ZGBaseUtils.networkModelInit();
            DeviceUtils.writeCommandToDevice(SETTING_INDEXS.All_Of_Them);
            return;
        }
        if (new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Weather_Update) > 1800) {
            EventBus.getDefault().post(new DeviceUpdateWeatherEvent());
            KLog.e("===请求天气====");
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Update, new DateUtil().getUnixTimestamp());
        }
        if (BluetoothOperation.isIv()) {
            byte[] power = ZeronerSendBluetoothCmdImpl.getInstance().getBattery();
            DataBean dataBean7 = new DataBean();
            dataBean7.addData(power);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean7);
            byte[] time = ZeronerSendBluetoothCmdImpl.getInstance().setTime();
            DataBean dataBean8 = new DataBean();
            dataBean8.addData(time);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean8);
            byte[] data7 = ZeronerSendBluetoothCmdImpl.getInstance().getFirmwareInformation();
            DataBean dataBean = new DataBean();
            dataBean.addData(data7);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
            byte[] data8 = ZeronerSendBluetoothCmdImpl.getInstance().getBle();
            DataBean dataBean1 = new DataBean();
            dataBean1.addData(data8);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean1);
            byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().getSportType();
            DataBean dataBean2 = new DataBean();
            dataBean2.addData(data);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean2);
            byte[] datas = ZeronerSendBluetoothCmdImpl.getInstance().setHeartRateParams(0, 0, 1);
            DataBean dataBean3 = new DataBean();
            dataBean3.addData(datas);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean3);
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
            byte[] data9 = ZeronerSendBluetoothCmdImpl.getInstance().setUserProfile((int) user.height, (int) user.weight, user.isMale, user.age, goal);
            DataBean dataBean9 = new DataBean();
            dataBean3.addData(data9);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean9);
            setTarget(goal, calorie);
            DeviceUtils.writeCommandToDevice(SETTING_INDEXS.All_Of_Them);
            return;
        }
        KLog.e(String.format("---*sdktype:%d", new Object[]{Integer.valueOf(PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type))}));
        if (BluetoothOperation.isMtk()) {
            KLog.e("---*debug is mtk");
            MtkToIvHandler.delete61Data();
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getDeviceStateDate());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getBattery());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getFirmwareInformation());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getBle());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setTime());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setHeartRateParams(0, 0, 1));
            MtkCmdAssembler.getInstance().writeP1Target(goal, calorie);
            UserInfo user2 = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
            if (user2.height == Utils.DOUBLE_EPSILON) {
                if (user2.isMale) {
                    user2.height = 175.0d;
                } else {
                    user2.height = 165.0d;
                }
            }
            if (user2.weight == Utils.DOUBLE_EPSILON) {
                if (user2.isMale) {
                    user2.weight = 70.0d;
                } else {
                    user2.weight = 50.0d;
                }
            }
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setUserProfile((int) user2.height, (int) user2.weight, user2.isMale, user2.age, 10000));
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getFirmwareProductTime());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getFirmwareProductInfo());
            writeGpsParam2Dev();
            setTarget(goal, calorie);
            DeviceUtils.getBloodlocal();
        }
        if (BluetoothOperation.isMTKEarphone()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getDeviceStateDate());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getBattery());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getFirmwareInformation());
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setTime());
            MtkCmdAssembler.getInstance().writeP1Target(goal, calorie);
            UserInfo user3 = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
            if (user3.height == Utils.DOUBLE_EPSILON) {
                if (user3.isMale) {
                    user3.height = 175.0d;
                } else {
                    user3.height = 165.0d;
                }
            }
            if (user3.weight == Utils.DOUBLE_EPSILON) {
                if (user3.isMale) {
                    user3.weight = 70.0d;
                } else {
                    user3.weight = 50.0d;
                }
            }
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setUserProfile((int) user3.height, (int) user3.weight, user3.isMale, user3.age, 10000));
        }
        if (BluetoothOperation.isProtoBuf()) {
            KLog.e("protobuf开始同步设置项");
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().getBattery());
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().getHardwareFeatures());
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setTime());
            setTarget(goal, calorie);
            writeGpsParam2Dev();
            DeviceUtils.getBloodlocal();
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationTime(DeviceUtils.localDeviceSetting().isNoDisturb(), DeviceUtils.localDeviceSetting().getStartNoDisturbTime(), DeviceUtils.localDeviceSetting().getEndNoDisturbTime(), 0, 0));
            DeviceUtils.writeCommandToDevice(SETTING_INDEXS.All_Of_Them);
        }
    }

    public static void syncDeviceData(boolean toSync) {
        if (TextUtils.isEmpty(DeviceUtils.getDeviceInfo().getModel())) {
            getModel();
        }
        if (isSync()) {
            KLog.e("---*already sync before");
            return;
        }
        EventBus.getDefault().post(new StartSyncDataEvent());
        KLog.e(String.format("---*syncDeviceData,sdk:%d", new Object[]{Integer.valueOf(PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type))}));
        if (BluetoothOperation.isIv()) {
            SyncData.getInstance().syncDataInfo();
        } else if (BluetoothOperation.isZg()) {
            ZgSync.getInstance().syncDataInfo();
        } else if (BluetoothOperation.isMtk()) {
            MtkSync.getInstance().syncDataInfo();
        } else if (BluetoothOperation.isMTKEarphone()) {
            MTKHeadSetSync.getInstance().syncDataInfo();
        } else if (BluetoothOperation.isProtoBuf()) {
            KLog.e("protobuf开始同步数据");
            ProtoBufSync.getInstance().syncData();
        }
    }

    public static boolean isSync() {
        if (BluetoothOperation.isIv()) {
            return SyncData.getInstance().isSyncDataInfo();
        }
        if (BluetoothOperation.isZg()) {
            return ZgSync.getInstance().isSync();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkSync.getInstance().isSyncDataInfo();
        }
        if (BluetoothOperation.isMTKEarphone()) {
            return MTKHeadSetSync.getInstance().isSyncDataInfo();
        }
        if (BluetoothOperation.isProtoBuf()) {
            return ProtoBufSync.getInstance().isSync();
        }
        return false;
    }

    public static void getBattery() {
        if (BluetoothOperation.isIv()) {
            byte[] power = ZeronerSendBluetoothCmdImpl.getInstance().getBattery();
            DataBean dataBean = new DataBean();
            dataBean.addData(power);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
        } else if (BluetoothOperation.isZg()) {
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getFirmwareInformation()));
        } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getBattery());
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().getBattery());
        }
    }

    public static void getModel() {
        if (BluetoothOperation.isIv()) {
            byte[] model = ZeronerSendBluetoothCmdImpl.getInstance().getFirmwareInformation();
            DataBean dataBean = new DataBean();
            dataBean.addData(model);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
        } else if (BluetoothOperation.isZg()) {
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getHardwareFeatures()));
        } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().getFirmwareInformation());
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().getHardwareFeatures());
        }
    }

    public static void setWeather(int weatherType, int temperatureInt, int pm2_5) {
        byte[] bytes;
        int unitType = 0;
        boolean weatherFormat = DeviceUtils.localDeviceSetting().isWeatherFormat();
        if (BluetoothOperation.isZg()) {
            KLog.e("ZG " + weatherType + "  " + temperatureInt);
            if (weatherType == -1 || temperatureInt == 100) {
                bytes = BleDataOrderHandler.getInstance().setTimeAndWeather();
            } else {
                bytes = BleDataOrderHandler.getInstance().setTimeAndWeather(weatherType, temperatureInt);
            }
            BleHandler.getInstance().addTaskMessage(new BleMessage(bytes));
            return;
        }
        if (weatherFormat) {
            unitType = 1;
            if (!BluetoothOperation.isProtoBuf()) {
                temperatureInt = ((int) (((double) temperatureInt) * 1.8d)) + 32;
            }
        }
        if (BluetoothOperation.isIv()) {
            if (weatherType != -1) {
                byte[] model = ZeronerSendBluetoothCmdImpl.getInstance().setWeather(unitType, temperatureInt, weatherType, pm2_5);
                DataBean dataBean = new DataBean();
                dataBean.addData(model);
                ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
            }
        } else if (BluetoothOperation.isMtk()) {
            if (weatherType != -1) {
                MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setWeather(unitType, temperatureInt, weatherType, pm2_5));
            }
        } else if (BluetoothOperation.isProtoBuf()) {
            DateUtil d = new DateUtil();
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setWeather((int) new DateUtil(d.getYear(), d.getMonth(), d.getDay(), d.getHour(), 0).getUnixTimestamp(), weatherType, 0, temperatureInt, temperatureInt, pm2_5));
        }
    }

    public static void weatherDays(Context context, int year, int month, int day, int hour, boolean isCentigrade, @Size(max = 24) List<Weather24h> weather24hs, @Size(max = 7) List<Weather7D> weather7DS, float temperature) {
        if (BluetoothOperation.isMtk()) {
            MtkCmdAssembler.getInstance().sendFutureWeather(context, year, month, day, hour, isCentigrade, weather24hs, weather7DS);
        } else if (BluetoothOperation.isProtoBuf()) {
            ArrayList arrayList = new ArrayList();
            DateUtil d = new DateUtil();
            int min = ((Weather24h) weather24hs.get(0)).getTemperature();
            int max = min;
            for (int i = 0; i < weather24hs.size(); i++) {
                Weather24h weather24h = (Weather24h) weather24hs.get(i);
                WeatherEvent weatherEvent = new WeatherEvent();
                weatherEvent.setWeatherDesc(weather24h.getWeather_type());
                if (i == 0) {
                    weatherEvent.setDegreeMax((int) temperature);
                    weatherEvent.setDegreeMin((int) temperature);
                } else {
                    weatherEvent.setDegreeMax(weather24h.getTemperature());
                    weatherEvent.setDegreeMin(weather24h.getTemperature());
                }
                weatherEvent.setPm2p5(weather24h.getPm_2_5());
                weatherEvent.setWeatherType(0);
                weatherEvent.setTimeMills((int) weather24h.getTime_stamp());
                arrayList.add(weatherEvent);
                if (((Weather24h) weather24hs.get(i)).getTemperature() > max) {
                    max = ((Weather24h) weather24hs.get(i)).getTemperature();
                }
                if (((Weather24h) weather24hs.get(i)).getTemperature() < min) {
                    min = ((Weather24h) weather24hs.get(i)).getTemperature();
                }
            }
            Weather24h weather24h2 = (Weather24h) weather24hs.get(0);
            WeatherEvent weatherEvent2 = new WeatherEvent();
            weatherEvent2.setWeatherType(1);
            weatherEvent2.setDegreeMax(max);
            weatherEvent2.setDegreeMin(min);
            weatherEvent2.setWeatherDesc(weather24h2.getWeather_type());
            weatherEvent2.setTimeMills((int) d.getZeroTime());
            arrayList.add(weatherEvent2);
            KLog.i("--9999--" + JsonUtils.toJson(arrayList));
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setWeather(arrayList));
        }
    }

    public static void setTarget(int steps, int kcal) {
        if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            MtkCmdAssembler.getInstance().writeP1Target(steps, kcal);
        } else if (BluetoothOperation.isIv()) {
            UserInfo user = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
            byte[] model = ZeronerSendBluetoothCmdImpl.getInstance().setUserProfile((int) user.height, (int) user.weight, user.isMale, user.getAge(), steps);
            DataBean dataBean = new DataBean();
            dataBean.addData(model);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
        } else if (BluetoothOperation.isZg()) {
            BleDataOrderHandler.getInstance().setStepsTarget(ContextUtil.app, steps);
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setGoalConf(kcal, steps, 10000));
        }
    }

    public static void setUserInfo() {
        UserInfo user = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
        int goal = PrefUtil.getInt((Context) ContextUtil.app, UserAction.User_Step_Target, 10000);
        if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setUserProfile((int) user.height, (int) user.weight, user.isMale, user.getAge(), goal));
        } else if (BluetoothOperation.isIv()) {
            byte[] model = ZeronerSendBluetoothCmdImpl.getInstance().setUserProfile((int) user.height, (int) user.weight, user.isMale, user.getAge(), goal);
            DataBean dataBean = new DataBean();
            dataBean.addData(model);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
        } else if (BluetoothOperation.isZg()) {
            BleDataOrderHandler.getInstance().setUserWeight(ContextUtil.app, (int) user.weight);
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setUserConf((int) user.height, (int) user.weight, user.isMale, user.getAge(), 100, 100));
        }
    }

    public static void setBoodSettingData(int srcSbp, int srcDbp, int dstSbp, int dstDbp) {
        if (BluetoothOperation.isZg()) {
            DeviceUtils.writeCommandToDevice(38);
        } else if (BluetoothOperation.isMtk()) {
            UserInfo user = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app);
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setUserProfileBlood((int) user.height, (int) user.weight, user.isMale, user.getAge(), PrefUtil.getInt((Context) ContextUtil.app, UserAction.User_Step_Target, 10000), srcSbp, srcDbp, dstSbp, dstDbp));
        } else if (BluetoothOperation.isProtoBuf()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setBpCaliConf(srcSbp, srcDbp, dstSbp, dstDbp, 0, 0));
        }
    }

    public static boolean checkUid() {
        if (GlobalUserDataFetcher.getPrevUid(ContextUtil.app) == 0 || GlobalUserDataFetcher.getPrevUid(ContextUtil.app) == GlobalUserDataFetcher.getCurrentUid(ContextUtil.app).longValue()) {
            return false;
        }
        GlobalDataUpdater.setPrevUid(ContextUtil.app, ContextUtil.getLUID());
        return true;
    }

    public static boolean checkDevice() {
        if (!TextUtils.isEmpty(GlobalUserDataFetcher.getPrevBindDevice(ContextUtil.app)) && !GlobalUserDataFetcher.getBindDevice(ContextUtil.app).equalsIgnoreCase(GlobalUserDataFetcher.getPrevBindDevice(ContextUtil.app))) {
            return true;
        }
        return false;
    }

    public static void clearAddSports() {
        DeviceUtils.sendSupportSportCommand(new ArrayList());
        DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Support_Sports_Status, JsonUtils.toJson(new ArrayList()));
    }

    public static void i6S7(String model) {
        if (model.equalsIgnoreCase("I6S7")) {
            String registerDate = ModuleRouteUserInfoService.getInstance().getRegisterDate(ContextUtil.app);
            try {
                if (new DateUtil(DateUtil.String2Date("yyyy-MM-dd", registerDate)).getUnixTimestamp() >= new DateUtil(DateUtil.String2Date("yyyy-MM-dd", "2018-12-17")).getUnixTimestamp()) {
                    KLog.i("====开车上路=====");
                    String key = PrefUtil.getString(ContextUtil.app, UserAction.I6S7_BRACELET_KEY);
                    String key1 = ContextUtil.getUID() + registerDate;
                    KLog.i("=====" + key + "====" + key1);
                    if (!key.equalsIgnoreCase(key1)) {
                        KLog.i("====add sport" + (!key.equalsIgnoreCase(key1)));
                        List<AddSport> list = new ArrayList<>();
                        AddSport add = new AddSport();
                        add.setDrawableId(AddSportUtil.getSporyImgOrName(2, 4));
                        add.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, 4)));
                        add.setType(4);
                        list.add(add);
                        AddSport add1 = new AddSport();
                        add1.setDrawableId(AddSportUtil.getSporyImgOrName(2, 129));
                        add1.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, 129)));
                        add1.setType(129);
                        list.add(add1);
                        AddSport add2 = new AddSport();
                        add2.setDrawableId(AddSportUtil.getSporyImgOrName(2, Opcodes.LONG_TO_INT));
                        add2.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, Opcodes.LONG_TO_INT)));
                        add2.setType(Opcodes.LONG_TO_INT);
                        list.add(add2);
                        AddSport add3 = new AddSport();
                        add3.setDrawableId(AddSportUtil.getSporyImgOrName(2, 135));
                        add3.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, 135)));
                        add3.setType(135);
                        list.add(add3);
                        AddSport add4 = new AddSport();
                        add4.setDrawableId(AddSportUtil.getSporyImgOrName(2, Opcodes.FLOAT_TO_LONG));
                        add4.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, Opcodes.FLOAT_TO_LONG)));
                        add4.setType(Opcodes.FLOAT_TO_LONG);
                        list.add(add4);
                        AddSport add5 = new AddSport();
                        add5.setDrawableId(AddSportUtil.getSporyImgOrName(2, Opcodes.FLOAT_TO_DOUBLE));
                        add5.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, Opcodes.FLOAT_TO_DOUBLE)));
                        add5.setType(Opcodes.FLOAT_TO_DOUBLE);
                        list.add(add5);
                        DeviceUtils.sendSupportSportCommand(list);
                        DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Support_Sports_Status, JsonUtils.toJson(list));
                        List<AddSport> unCheck = DeviceUtils.unCheckSupportSports(DeviceUtils.supportSports());
                        if (unCheck != null && unCheck.size() > 0) {
                            KLog.i("--------------" + JsonUtils.toJson(unCheck));
                            DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Support_Sports_Status_UnChecked, JsonUtils.toJson(unCheck));
                            PrefUtil.save((Context) ContextUtil.app, UserAction.I6S7_BRACELET_KEY, key1);
                        }
                    }
                }
            } catch (Exception e) {
                KLog.i("====add sport fail");
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public static void writeGpsParam2Dev() {
        try {
            double[] gps_infos = com.iwown.device_module.common.utils.Utils.getLocationInfo(ContextUtil.app);
            if (gps_infos == null) {
                KLog.i("licl", "location null");
                return;
            }
            String lat_str = com.iwown.device_module.common.utils.Utils.doubleToString(6, gps_infos[0]);
            String long_str = com.iwown.device_module.common.utils.Utils.doubleToString(6, gps_infos[1]);
            int time_zone = com.iwown.device_module.common.utils.Utils.getTimeZoneInt((float) gps_infos[1]);
            KLog.e("licl", time_zone + "");
            int altitude = (int) gps_infos[2];
            KLog.e("licl", time_zone + "/" + lat_str + "/" + long_str + "/" + altitude);
            if (BluetoothOperation.isMtk()) {
                MtkCmdAssembler.getInstance().writeGnssParams(time_zone, lat_str, long_str, altitude);
            } else if (BluetoothOperation.isProtoBuf()) {
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setGnssConf(altitude, long_str, lat_str));
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
