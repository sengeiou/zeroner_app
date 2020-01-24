package com.iwown.device_module.device_setting.configure;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.DeviceSettingsSend;
import com.iwown.device_module.common.network.data.req.UserDeviceReq;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.sql.DevicePref;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.socks.library.KLog;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class DeviceSettingsBiz {
    private static DeviceSettingsBiz instance = null;

    private DeviceSettingsBiz() {
    }

    public static DeviceSettingsBiz getInstance() {
        if (instance == null) {
            synchronized (DeviceSettingsBiz.class) {
                if (instance == null) {
                    instance = new DeviceSettingsBiz();
                }
            }
        }
        return instance;
    }

    public TB_DeviceSettings queryDevSettings() {
        FMdeviceInfo fMdeviceInfo = DeviceUtils.getDeviceInfo();
        KLog.e("DeviceSettingsBiz", "查询用的关键字信息--" + fMdeviceInfo.getSwversion() + "/" + fMdeviceInfo.getModel() + "/" + AppConfigUtil.APP_TYPE + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + fMdeviceInfo.toString());
        if (TextUtils.isEmpty(fMdeviceInfo.getModel())) {
            CommandOperation.getModel();
            return null;
        } else if (fMdeviceInfo.getSwversion() == null) {
            return null;
        } else {
            String model = fMdeviceInfo.getModel();
            if (model != null) {
                model = model.toUpperCase();
            }
            List<TB_DeviceSettings> tb_deviceSettingsList = DataSupport.where("model= ? and device_fw_version= ? and app= ? ", model, fMdeviceInfo.getSwversion(), AppConfigUtil.APP_TYPE + "").find(TB_DeviceSettings.class);
            KLog.i(model + "---" + fMdeviceInfo.getSwversion() + "---" + AppConfigUtil.APP_TYPE);
            if (tb_deviceSettingsList.size() != 0) {
                return (TB_DeviceSettings) tb_deviceSettingsList.get(0);
            }
            KLog.i("==========" + tb_deviceSettingsList.size());
            return null;
        }
    }

    public TB_DeviceSettings queryDevSettings(String model) {
        List<TB_DeviceSettings> tb_deviceSettingsList = DataSupport.where("model=? and app=?", model + "", AppConfigUtil.APP_TYPE + "").find(TB_DeviceSettings.class);
        if (tb_deviceSettingsList.size() == 0) {
            return null;
        }
        return (TB_DeviceSettings) tb_deviceSettingsList.get(0);
    }

    public boolean supportSomeSetting(int set_index) {
        TB_DeviceSettings settings = getInstance().queryDevSettings();
        if (settings == null) {
            return false;
        }
        for (SettingBean setting : (List) new Gson().fromJson(settings.getSetting(), new TypeToken<List<SettingBean>>() {
        }.getType())) {
            if (setting.getType() == set_index) {
                return true;
            }
        }
        return false;
    }

    public int getSupportValueInt(int set_index) {
        TB_DeviceSettings settings = getInstance().queryDevSettings();
        if (settings == null) {
            return -1;
        }
        for (SettingBean setting : (List) new Gson().fromJson(settings.getSetting(), new TypeToken<List<SettingBean>>() {
        }.getType())) {
            if (setting.getType() == set_index) {
                return setting.getValueInt();
            }
        }
        return -1;
    }

    public int getDevPlatform(String model) {
        TB_DeviceSettings settings = queryDevSettings(model);
        if (settings == null) {
            return -1;
        }
        return settings.getDevice_platform();
    }

    public String getSuffix(String model) {
        TB_DeviceSettings settings = queryDevSettings(model);
        if (settings == null) {
            return "";
        }
        return settings.getSuffix();
    }

    public int getModelDfu(String model) {
        TB_DeviceSettings settings = queryDevSettings(model);
        if (settings == null) {
            return 100;
        }
        return settings.getModel_dfu();
    }

    public void remoteDevice() {
        FMdeviceInfo info = DeviceUtils.getDeviceInfo();
        if (TextUtils.isEmpty(info.getModel()) || TextUtils.isEmpty(info.getSwversion())) {
            KLog.e(info.getModel() + "model is null" + info.getSwversion() + "version is null");
            return;
        }
        DeviceSettingsSend dss = new DeviceSettingsSend();
        dss.setApp(AppConfigUtil.APP_TYPE);
        dss.setModel(info.getModel());
        dss.setVersion(info.getSwversion());
        dss.setApp_platform(1);
        remoteDeviceSettings(dss);
    }

    public void remoteDeviceSettings(DeviceSettingsSend req) {
        NetFactory.getInstance().getClient(new MyCallback<DeviceSettingsDownCode>() {
            public void onSuccess(DeviceSettingsDownCode deviceSettingsDownCode) {
                if (deviceSettingsDownCode != null && deviceSettingsDownCode.getRetCode() == 0) {
                    TB_DeviceSettings deviceSettings = new TB_DeviceSettings();
                    DataBean dataBean = deviceSettingsDownCode.getData();
                    deviceSettings.setApp(dataBean.getApp());
                    deviceSettings.setApp_platform(dataBean.getApp_platform());
                    deviceSettings.setModel(dataBean.getModel());
                    deviceSettings.setModel_dfu(dataBean.getModel_dfu());
                    deviceSettings.setServer_fw_version(dataBean.getVersion());
                    deviceSettings.setDevice_fw_version(DeviceUtils.getDeviceInfo().getSwversion());
                    deviceSettings.setSuffix(dataBean.getSuffix());
                    deviceSettings.setDevice_platform(dataBean.getDevice_platform());
                    deviceSettings.setSetting(new Gson().toJson((Object) dataBean.getSetting()));
                    deviceSettings.setCommand(dataBean.getCommand());
                    deviceSettings.setModel_wechat(dataBean.getModel_wechat());
                    deviceSettings.setData_from(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
                    deviceSettings.saveOrUpdate("model=? and app=? and data_from=?", dataBean.getModel() + "", dataBean.getApp() + "", PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
                    EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Model_Down));
                    DeviceUtils.networkModelInit();
                    DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
                    if (devicePref != null || PrefUtil.getBoolean(ContextUtil.app, FirmwareAction.Firmware_Command_To_Device)) {
                        KLog.i("devicePref" + JsonUtils.toJson(devicePref));
                        return;
                    }
                    KLog.i("DevicePref:start to download pref");
                    PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Command_To_Device, true);
                    DevicePrefBiz.getInstance().downloadDevicePref(dataBean.getSetting());
                }
            }

            public void onFail(Throwable e) {
                KLog.e(e.toString());
            }
        }).remoteDeviceSetting(req);
    }

    public void upUserDevice(UserDeviceReq userDeviceReq) {
        NetFactory.getInstance().getClient(null).upUserDevice(userDeviceReq);
    }
}
