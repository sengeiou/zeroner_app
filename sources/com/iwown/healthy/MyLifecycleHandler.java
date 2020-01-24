package com.iwown.healthy;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.data_link.AppManger;
import com.iwown.data_link.eventbus.ConnectingEvent;
import com.iwown.data_link.eventbus.FeedbackServiceEvent;
import com.iwown.data_link.eventbus.ShouldGetWeatherEvent;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_firmware_upgrade.activity.FirmwareUpgradeActivity;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;

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

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppManger.getAppManager().addActivity(activity);
        this.createed++;
        if (this.createed > this.destoryed) {
            if (PrefUtil.getBoolean((Context) activity, "visible", false) || !isBand(activity)) {
                KLog.i("现在就是开始状态 不需要发送同步命令");
            } else {
                KLog.e("发送数据");
                ContextUtil.isBackground = false;
                SyncData.getInstance().setIsSyncDataInfo(false);
                if (BluetoothOperation.isConnected()) {
                    if (BluetoothOperation.isIv()) {
                        ZeronerBackgroundThreadManager.getInstance().wakeUp();
                    } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                        MtkBackgroundThreadManager.getInstance().wakeUp();
                    } else if (BluetoothOperation.isZg()) {
                        TaskHandler.getInstance().getTaskConsumer().wakeUp();
                    }
                }
            }
            PrefUtil.save((Context) activity, "visible", true);
        }
    }

    public void onActivityDestroyed(Activity activity) {
        this.destoryed++;
        AppManger.getAppManager().finishActivity(activity);
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
            ContextUtil.isBackground = false;
            KLog.i("==onActivityStarted==");
            EventBus.getDefault().post(new ShouldGetWeatherEvent(10));
            EventBus.getDefault().post(new FeedbackServiceEvent(0));
            if (BluetoothOperation.isEnabledBluetooth(activity) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                KLog.i("==reconnect==");
                if (!BluetoothOperation.isConnected()) {
                    KLog.e("蓝牙进行重连");
                    if (DeviceInitUtils.getInstance().getmBle() == null || DeviceInitUtils.getInstance().getZgBle() == null) {
                        KLog.e("蓝牙进行重连 : IBle还未初始化完成延迟2s后进行重连");
                        BluetoothOperation.startScan(ContextUtil.app);
                        this.mHandler.postDelayed(new Runnable() {
                            public void run() {
                                MyLifecycleHandler.this.reConnectCount = MyLifecycleHandler.this.reConnectCount + 1;
                                if (MyLifecycleHandler.this.reConnectCount > 3) {
                                    MyLifecycleHandler.this.reConnectCount = 0;
                                } else if (DeviceInitUtils.getInstance().getmBle() == null || DeviceInitUtils.getInstance().getZgBle() == null) {
                                    KLog.e("蓝牙进行重连 : IBle还未初始化完成延迟2s后进行重连 " + MyLifecycleHandler.this.reConnectCount);
                                    MyLifecycleHandler.this.mHandler.postDelayed(this, 2000);
                                } else {
                                    KLog.i("no2855-->脸==============DeviceApplication.getInstance().getmBle()=!=null=================");
                                    BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                                    EventBus.getDefault().post(new ConnectingEvent());
                                }
                            }
                        }, 2000);
                        return;
                    }
                    KLog.i("no2855-->脸==============BluetoothOperation.connect==================");
                    BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                } else if (!(activity instanceof FirmwareUpgradeActivity)) {
                    KLog.e("==后台进入前台同步数据==");
                    CommandOperation.syncDeviceData(true);
                    ContextUtil.isFirmwareUp = false;
                    if (BluetoothOperation.isMtk()) {
                        CommandOperation.writeGpsParam2Dev();
                    }
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
