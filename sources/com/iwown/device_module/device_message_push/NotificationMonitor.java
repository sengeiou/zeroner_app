package com.iwown.device_module.device_message_push;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.data_link.eventbus.DeviceUpdateWeatherEvent;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_message_push.NotificationBiz.SendListener;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;

public class NotificationMonitor extends NotificationListenerService implements SendListener {
    private String TAG = getClass().getSimpleName();
    private Context mContext;
    StatusBarNotification sbn;

    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        KLog.e(this.TAG, this.TAG + " is onCreate");
        this.mContext = this;
        NotificationBiz.getInstance(this.mContext).setListener(this);
        if (BluetoothOperation.isConnected()) {
            BluetoothOperation.postHeartData(0);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 3);
                channel.enableVibration(false);
                channel.enableLights(false);
                channel.setSound(null, null);
                ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
                startForeground(1, new Builder(getApplicationContext(), "11111").build());
            } else {
                startForeground(1, new Notification());
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        KLog.e(this.TAG + " is Destroy");
    }

    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    public void onNotificationPosted(StatusBarNotification sbn2) {
        KLog.e(this.TAG, "消息入口：ID :" + sbn2.getId() + "\t" + sbn2.getNotification().tickerText + "\t" + sbn2.getPackageName() + "thread:" + Thread.currentThread().getName());
        if (!"com.kunekt.healthy".equals(sbn2.getPackageName()) && !"com.healthy.iwownfit_pro".equals(sbn2.getPackageName())) {
            try {
                this.sbn = sbn2;
                NotificationBiz.getInstance(this.mContext).updateCurrentNotifications(getActiveNotifications());
            } catch (Exception e) {
                send2DeviceListener(20);
                ThrowableExtension.printStackTrace(e);
            }
            try {
                if (!BluetoothOperation.isConnected() && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device))) {
                    KLog.i("NotificationMonitor:connect device");
                    if (ContextUtil.isFirmwareUp) {
                        return;
                    }
                    if (new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Notification_Reconnect_Time) > 0 || sbn2.getId() == 553029) {
                        BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Notification_Reconnect_Time, new DateUtil().getUnixTimestamp() + 1800);
                    }
                } else if (sbn2.getId() == 553029) {
                    BluetoothOperation.postHeartData(0);
                }
                try {
                    if (!new DateUtil().getSyyyyMMddDate().equalsIgnoreCase(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Last_Epo_Time)) && BluetoothOperation.isMtk() && !MtkSync.getInstance().isSyncDataInfo() && ContextUtil.isBackground) {
                        KLog.e("后台消息推送同步EPO");
                        MtkCmdAssembler.getInstance().writeEpo();
                    }
                } catch (Exception e2) {
                    ThrowableExtension.printStackTrace(e2);
                }
                if (new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, FirmwareAction.Firmware_Weather_Update) > 1800 && ContextUtil.isBackground) {
                    EventBus.getDefault().post(new DeviceUpdateWeatherEvent());
                    PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Update, new DateUtil().getUnixTimestamp());
                }
            } catch (Exception e3) {
                ThrowableExtension.printStackTrace(e3);
            }
        }
    }

    public void onNotificationRemoved(StatusBarNotification sbn2) {
    }

    public void send2DeviceListener(int type) {
        if (type != 10 && !this.sbn.isOngoing()) {
            KLog.e("type!=NotificationBiz.SEND_TYPE_1");
            NotificationBiz.getInstance(this.mContext).storeNotification(this.sbn);
        }
    }
}
