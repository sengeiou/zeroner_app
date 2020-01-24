package com.iwown.device_module.common.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.bluetooth.ZeronerBle;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.ble.ProtoBle;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.scan.Scanner;
import com.iwown.ble_module.zg_ble.bluetooth.ZGBle;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufSync;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufUpdate;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_operation.bean.ModeItems;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import coms.mediatek.wearable.WearableManager;
import java.util.UUID;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;

public class BluetoothOperation {
    public static boolean isIv() {
        if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type) == 1) {
            return true;
        }
        return false;
    }

    public static boolean isZg() {
        if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type) == 3) {
            return true;
        }
        return false;
    }

    public static boolean isMtk() {
        if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type) != 2 || PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.EARPHONE) == 1) {
            return false;
        }
        return true;
    }

    public static boolean isMTKEarphone() {
        if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type) != 2) {
            return false;
        }
        if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.EARPHONE) == 1) {
            return true;
        }
        return false;
    }

    public static boolean isProtoBuf() {
        if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type) == 4) {
            return true;
        }
        return false;
    }

    public static boolean isEnabledBluetooth() {
        return isEnabledBluetooth(ContextUtil.app);
    }

    public static boolean checkBluetooth(Activity activity, int requestCode) {
        BluetoothAdapter bluetoothAdapter = ((BluetoothManager) activity.getSystemService("bluetooth")).getAdapter();
        if (bluetoothAdapter == null) {
            return false;
        }
        if (bluetoothAdapter.isEnabled()) {
            return true;
        }
        activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), requestCode);
        return false;
    }

    public static boolean isEnabledBluetooth(Context context) {
        BluetoothAdapter adapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
        if (!context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return false;
        }
        if (adapter == null) {
            return false;
        }
        return adapter.isEnabled();
    }

    public static void startScan(Context context) {
        Scanner.getInstance(context).startScan(new UUID[0]);
    }

    public static void stopScan(Context context) {
        Scanner.getInstance(context).stopScan();
    }

    public static void connect(Context context, WristBand wristBand) {
        synchronized (BluetoothOperation.class) {
            try {
                if (!isEnabledBluetooth()) {
                    EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Device_Fragment_Visable));
                    KLog.i("蓝牙未打开");
                    return;
                }
                if (wristBand != null) {
                    if (!TextUtils.isEmpty(wristBand.getAddress())) {
                        if (isConnected()) {
                            KLog.i("连接着手环");
                            L.file("连接着手环", 4);
                            return;
                        }
                        KLog.i("wristBand" + wristBand.toString());
                        int sdk = wristBand.getSdkType();
                        if (sdk == -1) {
                            sdk = PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.User_Sdk_type);
                        }
                        KLog.i(String.format("---------SDKType connect %d", new Object[]{Integer.valueOf(sdk)}));
                        if (sdk == 1) {
                            ZeronerBle.getInstance().setWristBand(wristBand);
                            ZeronerBle.getInstance().connect();
                            setNeedReconnect(true);
                            KLog.i("wristBand" + wristBand.toString());
                        } else if (sdk == 3) {
                            ZGBle.getInstance().setWristBand(new com.iwown.ble_module.WristBand(wristBand.getName(), wristBand.getAddress()));
                            ZGBle.getInstance().connect();
                            setNeedReconnect(true);
                        } else if (sdk == 2) {
                            MTKBle.getInstance(context).setWristBand(new com.iwown.ble_module.WristBand(wristBand.getName(), wristBand.getAddress()));
                            MTKBle.getInstance(context).connect();
                            setNeedReconnect(true);
                        } else if (sdk == 4) {
                            ProtoBle.getInstance().setWristBand(new com.iwown.ble_module.WristBand(wristBand.getName(), wristBand.getAddress()));
                            ProtoBle.getInstance().connect();
                            setNeedReconnect(true);
                        }
                        return;
                    }
                }
                L.file("wristBand==null", 4);
                KLog.i("wristBand==null");
                return;
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public static boolean isConnected() {
        if (isIv()) {
            if (isIBleNotNull()) {
                return ZeronerBle.getInstance().isConnected();
            }
            return false;
        } else if (isZg()) {
            if (isIBleNotNull()) {
                return ZGBle.getInstance().isConnected();
            }
            return false;
        } else if (isMtk()) {
            return MTKBle.getInstance().isConnected();
        } else {
            if (isMTKEarphone()) {
                return MTKBle.getInstance().isConnected();
            }
            if (!isProtoBuf() || !isIBleNotNull()) {
                return false;
            }
            return ProtoBle.getInstance().isConnected();
        }
    }

    public static boolean isConnecting() {
        if (isIv()) {
            return ZeronerBle.getInstance().isConnecting();
        }
        if (isZg()) {
            return ZGBle.getInstance().isConnecting();
        }
        if (isMtk() || isMTKEarphone()) {
            return MTKBle.getInstance().isConnecting();
        }
        return false;
    }

    public static boolean isBind() {
        return !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
    }

    public static void disconnect() {
        if (isIv()) {
            ZeronerBle.getInstance().disconnect();
        } else if (isZg()) {
            ZGBle.getInstance().disconnect();
        } else if (isMtk()) {
            MTKBle.getInstance().disconnect();
        } else if (isMTKEarphone()) {
            MTKBle.getInstance().disconnect();
        } else if (isProtoBuf()) {
            ProtoBle.getInstance().disconnect();
        }
    }

    public static void setNeedReconnect(boolean isNeedReconnect) {
        if (isIv()) {
            if (isIBleNotNull()) {
                ZeronerBle.getInstance().setNeedReconnect(isNeedReconnect);
            }
        } else if (isZg()) {
            ZGBle.getInstance().setNeedReconnect(isNeedReconnect);
        } else if (isMtk()) {
            MTKBle.getInstance().setNeedReconnect(isNeedReconnect);
        } else if (isMTKEarphone()) {
            MTKBle.getInstance().setNeedReconnect(isNeedReconnect);
        } else if (isProtoBuf()) {
            ProtoBle.getInstance().setNeedReconnect(isNeedReconnect);
        }
    }

    public static boolean isIBleNotNull() {
        if (isIv()) {
            if (DeviceInitUtils.getInstance().getmBle() != null) {
                return true;
            }
            return false;
        } else if (isZg()) {
            if (DeviceInitUtils.getInstance().getZgBle() == null) {
                return false;
            }
            return true;
        } else if (isMtk() || isMTKEarphone() || !isProtoBuf() || DeviceInitUtils.getInstance().getProBle() != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void unBund() {
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Alias_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.UNBind_Device_Button, false);
        disconnect(false);
    }

    public static void disconnect(boolean isNeedReconnect) {
        if (isIv()) {
            if (isIBleNotNull()) {
                setNeedReconnect(isNeedReconnect);
                disconnect();
                setWristBand(null);
                ZeronerBackgroundThreadManager.getInstance().clearQueue();
            }
        } else if (isZg()) {
            ZGBle.getInstance().disconnect();
            if (!isNeedReconnect) {
                ZGBle.getInstance().setWristBandNull();
            }
            setNeedReconnect(isNeedReconnect);
        } else if (isMtk() || isMTKEarphone()) {
            setNeedReconnect(isNeedReconnect);
            disconnect();
            setWristBand(null);
            MtkBackgroundThreadManager.getInstance().clearQueue();
        } else if (isProtoBuf()) {
            setNeedReconnect(isNeedReconnect);
            disconnect();
            setWristBand(null);
            BackgroundThreadManager.getInstance().clearQueue();
            ProtoBufSync.getInstance().setSync(false);
            ProtoBufUpdate.getInstance().setUpdate(false);
        }
    }

    public static void setWristBand(WristBand dev) {
        if (isIv()) {
            if (isIBleNotNull()) {
                ZeronerBle.getInstance().setWristBand(dev);
            }
        } else if (isZg()) {
        } else {
            if (isMtk()) {
                if (dev == null) {
                    MTKBle.getInstance().setWristBand(null);
                } else {
                    MTKBle.getInstance().setWristBand(new com.iwown.ble_module.WristBand(dev.getName(), dev.getAddress()));
                }
            } else if (!isProtoBuf()) {
            } else {
                if (dev == null) {
                    ProtoBle.getInstance().setWristBand(null);
                } else {
                    ProtoBle.getInstance().setWristBand(new com.iwown.ble_module.WristBand(dev.getName(), dev.getAddress()));
                }
            }
        }
    }

    public static void unbindDevice(boolean isNeedReconnect) {
        KLog.i("===================================unbindDevice=========================" + isConnected());
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Sync_Setting_Time, 0);
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Update, 0);
        PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.APP_SDK_UPDATE_Time, 0);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, (String) null);
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Alias_Current_Device, (String) null);
        if (!isConnected()) {
            disconnect(false);
        } else if (isZg()) {
            TaskHandler.getInstance().clearTask();
            disconnect(false);
        } else if (isMtk() || isMTKEarphone()) {
            MtkSync.getInstance().stopSyncDataAll();
            setNeedReconnect(isNeedReconnect);
            postHeartData(1);
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setUnbind());
            disconnect(false);
        } else if (isIv()) {
            postHeartData(1);
            setNeedReconnect(isNeedReconnect);
            ZeronerBackgroundThreadManager.getInstance().clearQueue();
            SyncData.getInstance().stopSyncDataAll();
            byte[] bytes = ZeronerSendBluetoothCmdImpl.getInstance().setUnbind();
            DataBean bean = new DataBean();
            bean.addData(bytes);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, bean);
        } else if (isProtoBuf()) {
            disconnect(false);
        }
        PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.User_Sdk_type, 0);
    }

    public static void FirmwareUnbindDevice(boolean isNeedReconnect) {
        KLog.i("===================================FirmwareUnbindDevice=========================" + isConnected());
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Sync_Setting_Time, 0);
        if (!isConnected()) {
            disconnect(false);
        } else if (isZg()) {
            disconnect(false);
        } else if (isMtk() || isMTKEarphone()) {
            MtkSync.getInstance().stopSyncDataAll();
            setNeedReconnect(isNeedReconnect);
            postHeartData(1);
            disconnect(false);
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setUnbind());
        } else if (isIv()) {
            postHeartData(1);
            setNeedReconnect(isNeedReconnect);
            ZeronerBackgroundThreadManager.getInstance().clearQueue();
            SyncData.getInstance().stopSyncDataAll();
            byte[] bytes = ZeronerSendBluetoothCmdImpl.getInstance().setUnbind();
            DataBean bean = new DataBean();
            bean.addData(bytes);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, bean);
        }
    }

    public static void disconnectWhenUnbindTimeOut(boolean isNeedReconnect) {
        if (isZg()) {
            disconnect(isNeedReconnect);
        } else if (isMtk() || isMTKEarphone()) {
            MtkSync.getInstance().stopSyncDataAll();
            disconnect(isNeedReconnect);
            WearableManager.getInstance().setRemoteDevice(null);
            setNeedReconnect(isNeedReconnect);
        } else if (isIv()) {
            ZeronerBackgroundThreadManager.getInstance().clearQueue();
            SyncData.getInstance().stopSyncDataAll();
            disconnect(isNeedReconnect);
            setNeedReconnect(isNeedReconnect);
        }
    }

    public static String getDeviceAddress() {
        if (getWristBand() != null) {
            return getWristBand().getAddress();
        }
        return null;
    }

    public static WristBand getWristBand() {
        try {
            if (isIv()) {
                return ZeronerBle.getInstance().getWristBand();
            }
            if (isZg()) {
                return new WristBand(ZGBle.getInstance().getWristBand().getDev_name(), ZGBle.getInstance().getWristBand().getDev_addr());
            }
            if (isMtk()) {
                return new WristBand(MTKBle.getInstance().getWristBand().getDev_name(), MTKBle.getInstance().getWristBand().getDev_addr());
            }
            if (isMTKEarphone()) {
                return new WristBand(MTKBle.getInstance().getWristBand().getDev_name(), MTKBle.getInstance().getWristBand().getDev_addr());
            }
            if (isProtoBuf()) {
                return new WristBand(ProtoBle.getInstance().getWristBand().getDev_name(), ProtoBle.getInstance().getWristBand().getDev_addr());
            }
            return null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static int getSDKTypeByDeviceName(int key_calssId, String name, ModeItems modeItems) {
        if (modeItems == null) {
            return -1;
        }
        for (ModeItems.DataBean dataBean : modeItems.getData()) {
            if (dataBean.getClassid() == key_calssId && filterOk(dataBean.getKeyword(), name)) {
                return dataBean.getSdktype();
            }
        }
        return -1;
    }

    public static boolean filterOk(String keys, String name) {
        try {
            if (name.length() < 5) {
                return false;
            }
            String subString1 = name.substring(0, name.length() - 5);
            int mIndex = name.lastIndexOf(HelpFormatter.DEFAULT_OPT_PREFIX);
            String subString2 = "";
            if (mIndex != -1) {
                subString2 = name.substring(0, mIndex);
            }
            String[] split = keys.split(",");
            for (int i = 0; i < split.length; i++) {
                if (subString1.endsWith(split[i]) || subString2.endsWith(split[i])) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            KLog.i("filterOK:" + keys);
            return false;
        }
    }

    public static void postHeartData(int value) {
        long nowTime = System.currentTimeMillis();
        if (PrefUtil.getBoolean(ContextUtil.app, FirmwareAction.Firmware_New_Protocol) && nowTime - PrefUtil.getLong(ContextUtil.app, BleAction.Bluetooth_Sync_Heart_Beat_Time) > 60000) {
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Sync_Heart_Beat_Time, nowTime);
            byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().setHeartBeat(value);
            DataBean dataBean = new DataBean();
            dataBean.addData(data);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean);
        }
    }

    public static void switchStandardHeartRate(boolean enabled) {
        if (isMTKEarphone()) {
            MtkSync.getInstance().stopSyncDataAll();
            MtkSync.getInstance().stopSyncData(104);
            MTKBle.getInstance().switchStandardHeartRate(enabled);
        }
    }
}
