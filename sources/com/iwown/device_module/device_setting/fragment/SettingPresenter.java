package com.iwown.device_module.device_setting.fragment;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.eventbus.ApgsFinishEvent;
import com.iwown.data_link.eventbus.BluetoothStatus;
import com.iwown.data_link.eventbus.ConnectingEvent;
import com.iwown.data_link.eventbus.LogOutEventBus;
import com.iwown.data_link.eventbus.OnkeyDown;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothCallbackReceiver;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.FwUpdate;
import com.iwown.device_module.common.network.data.resp.DeviceSettingRemote;
import com.iwown.device_module.common.network.data.resp.FwUpdateReturnDetail;
import com.iwown.device_module.common.network.data.resp.FwUpdateReturnMessage;
import com.iwown.device_module.common.network.data.resp.PrefServerResponse;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.sql.DevicePref;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.Utils;
import com.iwown.device_module.device_firmware_upgrade.Util;
import com.iwown.device_module.device_setting.configure.DevicePrefBiz;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.SettingDefault;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.device_module.device_setting.fragment.SettingContract.Presenter;
import com.iwown.device_module.device_setting.fragment.SettingContract.View;
import com.socks.library.KLog;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SettingPresenter implements Presenter {
    public static final int Bluetooth_Error_Open_Code = 10250;
    private static final int MIN_DELAY_TIME = 3000;
    private static long lastClickTime;
    /* access modifiers changed from: private */
    public int errorCount;
    SettingReceiver receiver;
    View view;

    private class SettingReceiver extends BluetoothCallbackReceiver {
        private SettingReceiver() {
        }

        public void connectStatue(boolean isConnect) {
            super.connectStatue(isConnect);
            if (!isConnect) {
                if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                    MTKBle.getInstance().setConnected(false);
                    KLog.i(String.format("---*set mtkble connect status to false", new Object[0]));
                }
                SettingPresenter.this.errorCount = SettingPresenter.this.errorCount + 1;
                if (SettingPresenter.this.errorCount > 1) {
                    SettingPresenter.this.view.connectStatue(isConnect);
                    SettingPresenter.this.errorCount = 0;
                }
                if (!BluetoothOperation.isEnabledBluetooth()) {
                    SettingPresenter.this.view.connectStatue(isConnect);
                    SettingPresenter.this.errorCount = 0;
                }
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                MTKBle.getInstance().setConnected(true);
                KLog.i(String.format("---*set mtkble connect status to true", new Object[0]));
            }
        }

        public void onBluetoothInit() {
            super.onBluetoothInit();
            SettingPresenter.this.errorCount = 0;
            SettingPresenter.this.view.connectStatue(true);
        }
    }

    public SettingPresenter(View view2) {
        view2.setPresenter(this);
        this.view = view2;
        this.receiver = new SettingReceiver();
    }

    public SettingPresenter() {
    }

    public int getBatteryValue() {
        try {
            return DeviceUtils.getBattery();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public DeviceSettingLocal localDeviceSetting() {
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
                    return (DeviceSettingLocal) JsonUtils.fromJson(info3.getContent(), DeviceSettingLocal.class);
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
        return new DeviceSettingLocal();
    }

    public void saveLocalDeviceSetting(DeviceSettingLocal setting) {
        if (setting != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                KLog.i(String.format("---*setting json:%s", new Object[]{JsonUtils.toJson(setting)}));
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            } else if (BluetoothOperation.isZg()) {
                if (AppConfigUtil.isHealthy() && !PrefUtil.getBoolean(ContextUtil.app, UserAction.HEALTHY_LANGUAGE)) {
                    setting.setLanguage(1);
                }
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Device_Setting_Other, JsonUtils.toJson(setting));
            }
            DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
            KLog.i("devicePref" + JsonUtils.toJson(devicePref));
            if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
                ArrayList<SettingDefault> settingPref = JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class);
                if (settingPref != null && settingPref.size() > 0) {
                    DevicePrefBiz.getInstance().updatePrefToLocal(settingPref, setting, devicePref, null, null);
                }
            }
        }
    }

    public void unbindDevice() {
        GlobalDataUpdater.setPrevBindDevice(ContextUtil.app, ContextUtil.getDeviceNameCurr());
        BluetoothOperation.unbindDevice(false);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Alias_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.UNBind_Device_Button, false);
    }

    public void uploadDevicePref(DeviceSettingRemote deviceSettingRemote) {
        if (PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Device_Pref_Upload_Time) < System.currentTimeMillis()) {
            NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                public void onSuccess(Integer integer) {
                    PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Device_Pref_Upload_Time, System.currentTimeMillis() + 300000);
                }

                public void onFail(Throwable e) {
                    KLog.e(e.toString());
                }
            }).uploadDeviceRef(deviceSettingRemote);
        }
    }

    public void downloadDevicePref() {
        NetFactory.getInstance().getClient(new MyCallback<PrefServerResponse>() {
            public void onSuccess(PrefServerResponse prefServerResponse) {
                if (prefServerResponse != null) {
                    try {
                        if (prefServerResponse.getRetCode() == 0 && prefServerResponse.getData() != null) {
                            DeviceSettingRemote remote = prefServerResponse.getData();
                            DevicePref devicePref = new DevicePref();
                            devicePref.setUid(remote.getUid());
                            devicePref.setModel(remote.getModel());
                            devicePref.setSetting(JsonUtils.toJson(remote.getSetting()));
                            DevicePrefBiz.getInstance().saveDevicePref(devicePref);
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onFail(Throwable e) {
                KLog.e(e.toString());
            }
        }).downloadDeviceRef(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
    }

    public void subscribe() {
        LocalBroadcastManager.getInstance(ContextUtil.app).registerReceiver(this.receiver, BaseActionUtils.getIntentFilter());
        EventBus.getDefault().register(this);
    }

    public void unsubscribe() {
        LocalBroadcastManager.getInstance(ContextUtil.app).unregisterReceiver(this.receiver);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateConfigUi(UpdateConfigUI ui) {
        if (ui.getAction().equals(UpdateConfigUI.Config_Model_Down) || ui.getAction().equals(UpdateConfigUI.Config_Battery_Update) || ui.getAction().equals(UpdateConfigUI.Config_Device_Fragment_Visable)) {
            this.view.updateConfigUi(ui.getAction());
        } else if (ui.getAction().equals(UpdateConfigUI.Config_Device_Firmware_Version)) {
            updateVersionOnlyUpgradeSuccess();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userLogOut(LogOutEventBus logOutEventBus) {
        KLog.i("===========LogOutEventBus===================");
        PrefUtil.save((Context) ContextUtil.app, UserAction.User_Phone_App_Background, false);
        unbindDevice();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void connectingEvent(ConnectingEvent connectingEvent) {
        this.view.connectingView(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bluetoothStatus(BluetoothStatus bluetoothStatus) {
        this.view.connectingView(2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onkeyDown(OnkeyDown event) {
        this.view.keyDownDismissDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncOver(SyncDataEvent event) {
        if (event.isStop()) {
            KLog.i("=========Sync data over============");
            if (DeviceUtils.getDeviceInfo().getModel() == null) {
                return;
            }
            if (BluetoothOperation.isMtk() && MtkSync.getInstance().isUpEpo()) {
                return;
            }
            if (ContextUtil.isFirmwareUp) {
                KLog.e("ContextUtil.isFirmwareUp" + ContextUtil.isFirmwareUp);
            } else if (!BluetoothOperation.isProtoBuf() && new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, UserAction.Bluetooth_Check_Firmware_Update_Time) > 0) {
                KLog.e("checkFirmwareVersion()");
                checkFirmwareVersion();
                PrefUtil.save((Context) ContextUtil.app, UserAction.Bluetooth_Check_Firmware_Update_Time, new DateUtil().getUnixTimestamp() + 600);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncOver(ApgsFinishEvent event) {
        if (new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, UserAction.Bluetooth_Check_Firmware_Update_Time) > 0) {
            KLog.e("checkFirmwareVersion()");
            checkFirmwareVersion();
            PrefUtil.save((Context) ContextUtil.app, UserAction.Bluetooth_Check_Firmware_Update_Time, new DateUtil().getUnixTimestamp() + 600);
        }
    }

    public void checkFirmwareVersion() {
        FMdeviceInfo fMdeviceInfo = DeviceUtils.getDeviceInfo();
        FwUpdate fm = new FwUpdate();
        fm.setApp(AppConfigUtil.APP_TYPE);
        fm.setPlatform("Nordic");
        fm.setApp_version(Utils.getClientVersionCode(ContextUtil.app));
        fm.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(fMdeviceInfo.getModel()));
        fm.setDevice_type(Util.device_type(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)));
        fm.setApp_platform(1);
        fm.setModule(1);
        fm.setSkip(1);
        fm.setFw_version(fMdeviceInfo.getSwversion());
        NetFactory.getInstance().getClient(new MyCallback<FwUpdateReturnMessage>() {
            public void onSuccess(FwUpdateReturnMessage fwUpdateReturnMessage) {
                try {
                    PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_post_version, false);
                    if (fwUpdateReturnMessage != null) {
                        if (fwUpdateReturnMessage.getRetCode() == 0) {
                            SettingPresenter.this.view.upDateFirmwareUi(fwUpdateReturnMessage.getFirmware().getUpdate_information(), fwUpdateReturnMessage.getFirmware().getOpen_for_upgrade(), fwUpdateReturnMessage.getRetCode());
                        } else {
                            SettingPresenter.this.view.upDateFirmwareUi(null, 999, fwUpdateReturnMessage.getRetCode());
                        }
                    }
                    if (fwUpdateReturnMessage != null) {
                        try {
                            if (fwUpdateReturnMessage.getFirmware() != null) {
                                SettingPresenter.this.saveFwUpdateDetail(fwUpdateReturnMessage.getFirmware());
                                if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                                    PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device));
                                }
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                } catch (Exception e2) {
                    ThrowableExtension.printStackTrace(e2);
                }
            }

            public void onFail(Throwable e) {
                PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_post_version, false);
            }
        }).checkFirmwareUpdate(fm);
    }

    public void updateVersionOnlyUpgradeSuccess() {
        FMdeviceInfo fMdeviceInfo = DeviceUtils.getDeviceInfo();
        FwUpdate fm = new FwUpdate();
        fm.setApp(AppConfigUtil.APP_TYPE);
        fm.setPlatform("Nordic");
        fm.setApp_version(Utils.getClientVersionCode(ContextUtil.app));
        fm.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(fMdeviceInfo.getModel()));
        fm.setDevice_type(Util.device_type(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)));
        fm.setApp_platform(1);
        fm.setModule(1);
        fm.setSkip(1);
        fm.setFw_version(fMdeviceInfo.getSwversion());
        NetFactory.getInstance().getClient(new MyCallback<FwUpdateReturnMessage>() {
            public void onSuccess(FwUpdateReturnMessage fwUpdateReturnMessage) {
                if (fwUpdateReturnMessage != null) {
                    try {
                        if (fwUpdateReturnMessage.getRetCode() == 60001) {
                            SettingPresenter.this.view.updateFirmwareUpgradeSuccess();
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onFail(Throwable e) {
            }
        }).checkFirmwareUpdate(fm);
    }

    public void saveFwUpdateDetail(FwUpdateReturnDetail fw) {
        if (fw != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            }
        }
    }

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - lastClickTime >= 3000) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
