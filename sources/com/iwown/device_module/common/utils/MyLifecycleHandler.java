package com.iwown.device_module.common.utils;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.socks.library.KLog;

public class MyLifecycleHandler implements ActivityLifecycleCallbacks {
    private int activityCount;
    private int createed;
    private int destoryed;
    private boolean isBackground = true;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private int paused;
    /* access modifiers changed from: private */
    public int reConnectCount;
    private int resumed;
    private int started;
    private int stopped;
    private long updateTime;

    public boolean isBackground() {
        return this.isBackground;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.createed++;
        if (this.createed > this.destoryed) {
            if (PrefUtil.getBoolean((Context) activity, "visible", false) || !isBand(activity)) {
                KLog.i("现在就是开始状态 不需要发送同步命令");
            } else {
                KLog.e("发送数据");
                SyncData.getInstance().setIsSyncDataInfo(false);
                ContextUtil.isBackground = false;
                if (BluetoothOperation.isConnected()) {
                    ZeronerBackgroundThreadManager.getInstance().wakeUp();
                }
            }
            PrefUtil.save((Context) activity, "visible", true);
        }
    }

    public void onActivityDestroyed(Activity activity) {
        this.destoryed++;
        if (this.createed <= this.destoryed) {
            if (PrefUtil.getBoolean((Context) activity, "visible", false) && isBand(activity)) {
                ContextUtil.isBackground = true;
                if (BluetoothOperation.isConnected()) {
                    SyncData.getInstance().stopSyncDataAll();
                }
            }
            PrefUtil.save((Context) activity, "visible", false);
        }
    }

    public void onActivityResumed(Activity activity) {
        this.resumed++;
    }

    public void onActivityPaused(Activity activity) {
        this.paused++;
    }

    public static boolean isBand(Context context) {
        return !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)) || !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device));
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityStarted(Activity activity) {
        this.started++;
        if (this.started > 0 && this.isBackground) {
            this.isBackground = false;
            KLog.i("==onActivityStarted==");
            if (BluetoothOperation.isEnabledBluetooth(activity) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                KLog.i("==reconnect==");
                if (!BluetoothOperation.isConnected()) {
                    KLog.e("蓝牙进行重连");
                    if (DeviceInitUtils.getInstance().getmBle() == null) {
                        KLog.e("蓝牙进行重连 : IBle还未初始化完成延迟2s后进行重连");
                        this.mHandler.postDelayed(new Runnable() {
                            public void run() {
                                MyLifecycleHandler.this.reConnectCount = MyLifecycleHandler.this.reConnectCount + 1;
                                if (MyLifecycleHandler.this.reConnectCount > 3) {
                                    MyLifecycleHandler.this.reConnectCount = 0;
                                } else if (DeviceInitUtils.getInstance().getmBle() == null) {
                                    KLog.e("蓝牙进行重连 : IBle还未初始化完成延迟2s后进行重连 " + MyLifecycleHandler.this.reConnectCount);
                                    MyLifecycleHandler.this.mHandler.postDelayed(this, 2000);
                                } else {
                                    KLog.i("no2855-->脸==============DeviceApplication.getInstance().getmBle()==null=================");
                                    BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                                }
                            }
                        }, 2000);
                        return;
                    }
                    KLog.i("no2855-->脸==============BluetoothOperation.connect==================");
                    BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                }
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        this.started--;
        if (this.started <= 0 && !this.isBackground) {
            this.isBackground = true;
            ContextUtil.isBackground = true;
        }
    }
}
