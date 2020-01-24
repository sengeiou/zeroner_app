package com.iwown.device_module.device_firmware_upgrade.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_firmware_upgrade.activity.HardwareUpdateSuccessActivity;
import com.iwown.device_module.device_firmware_upgrade.bean.FwUpdateInfo;
import com.iwown.device_module.device_firmware_upgrade.eventbus.WakeUpEvent;
import com.iwown.device_module.device_firmware_upgrade.thread.ThreadPoolExecutorProxyFactory;
import com.iwown.device_module.device_message_push.utils.ServiceUtils;
import com.socks.library.KLog;
import no.nordicsemi.android.dfu.DfuBaseService;
import no.nordicsemi.android.dfu.DfuLogListener;
import no.nordicsemi.android.dfu.DfuProgressListenerAdapter;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HardwareUpdateService extends Service {
    public static final int ERROR_TIME_OUT = 1000;
    DfuLogListener mDfuLogListener = new DfuLogListener() {
        public void onLogEvent(String deviceAddress, int level, String message) {
            if (level > 15) {
                KLog.e("dfu error : " + message);
            } else {
                KLog.e("dfu : " + message);
            }
        }
    };
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public boolean mIsSending = false;
    /* access modifiers changed from: private */
    public int mLastPercent;
    private final Object mLock = new Object();
    DfuProgressListenerAdapter mProgressListenerAdapter = new DfuProgressListenerAdapter() {
        public void onDeviceConnecting(String deviceAddress) {
            super.onDeviceConnecting(deviceAddress);
            KLog.e(DfuBaseService.NOTIFICATION_CHANNEL_DFU);
            HardwareUpdateService.this.judgeProgressRunnable();
            FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
            FwUpdateInfo.getInstance().setLastProgress(0);
            FwUpdateInfo.getInstance().setState(1);
            FwUpdateInfo.getInstance().save();
        }

        public void onDeviceConnected(String deviceAddress) {
            super.onDeviceConnected(deviceAddress);
            KLog.e(DfuBaseService.NOTIFICATION_CHANNEL_DFU);
            HardwareUpdateService.this.judgeProgressRunnable();
            FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
            FwUpdateInfo.getInstance().setLastProgress(0);
            FwUpdateInfo.getInstance().setState(1);
            FwUpdateInfo.getInstance().save();
        }

        public void onDfuProcessStarting(String deviceAddress) {
            super.onDfuProcessStarting(deviceAddress);
            HardwareUpdateService.this.judgeProgressRunnable();
            FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
            FwUpdateInfo.getInstance().setLastProgress(0);
            FwUpdateInfo.getInstance().setState(1);
            FwUpdateInfo.getInstance().save();
        }

        public void onDfuProcessStarted(String deviceAddress) {
            super.onDfuProcessStarted(deviceAddress);
            HardwareUpdateService.this.judgeProgressRunnable();
            FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
            FwUpdateInfo.getInstance().setLastProgress(0);
            FwUpdateInfo.getInstance().setState(1);
            FwUpdateInfo.getInstance().save();
        }

        public void onDfuAborted(String deviceAddress) {
            super.onDfuAborted(deviceAddress);
            HardwareUpdateService.this.judgeProgressRunnable();
            FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
            FwUpdateInfo.getInstance().setLastProgress(0);
            FwUpdateInfo.getInstance().setState(0);
            FwUpdateInfo.getInstance().save();
        }

        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            super.onProgressChanged(deviceAddress, percent, speed, avgSpeed, currentPart, partsTotal);
            HardwareUpdateService.this.mIsSending = true;
            HardwareUpdateService.this.judgeProgressRunnable();
            KLog.e("percent : " + percent + "    speed : " + speed);
            FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
            FwUpdateInfo.getInstance().setLastProgress(percent);
            FwUpdateInfo.getInstance().setState(1);
            FwUpdateInfo.getInstance().save();
            if (HardwareUpdateService.this.mLastPercent != percent) {
                if (percent == 0) {
                    HardwareUpdateService.this.wakeUp();
                }
                if (percent >= 98) {
                    KLog.e("升级进度 : " + percent);
                    if (percent >= 99) {
                        HardwareUpdateService.this.wakeUp();
                    }
                }
                HardwareUpdateService.this.mLastPercent = percent;
            }
        }

        public void onDfuCompleted(String deviceAddress) {
            super.onDfuCompleted(deviceAddress);
            KLog.e("升级完成");
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac));
            HardwareUpdateService.updateSuccess(HardwareUpdateService.this);
            FwUpdateInfo.getInstance().clear();
            HardwareUpdateService.this.stopSelf();
        }

        public void onError(String deviceAddress, int error, int errorType, String message) {
            super.onError(deviceAddress, error, errorType, message);
            FwUpdateInfo.getInstance().setState(0);
            FwUpdateInfo.getInstance().save();
            HardwareUpdateService.this.stopSelf();
        }
    };
    private Runnable mProgressTimeoutRunnable = new Runnable() {
        public void run() {
            Intent broadcast = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", 1000);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 0);
            LocalBroadcastManager.getInstance(ContextUtil.app).sendBroadcast(broadcast);
        }
    };
    private WakeLock mWl;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        KLog.e("onCreate");
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
        EventBus.getDefault().register(this);
        DfuServiceListenerHelper.registerProgressListener(this, this.mProgressListenerAdapter);
        DfuServiceListenerHelper.registerLogListener(this, this.mDfuLogListener);
        ThreadPoolExecutorProxyFactory.getThreadPoolExecutorProxy().execute(new Runnable() {
            public void run() {
                HardwareUpdateService.this.waitTime(300000);
                while (HardwareUpdateService.this.isDfuServiceAlive()) {
                    KLog.e(HardwareUpdateService.this.mIsSending + "  " + ServiceUtils.isServiceRunning(HardwareUpdateService.this, DfuService.class.getName()) + "  " + ServiceUtils.isServiceRunning(HardwareUpdateService.this, NewDfuService.class.getName()) + "  " + ServiceUtils.isServiceRunning(HardwareUpdateService.this, IwownFotaService.class.getName()));
                    HardwareUpdateService.this.mIsSending = false;
                    HardwareUpdateService.this.waitTime(300000);
                }
                KLog.e("stopSelf");
                HardwareUpdateService.this.stopSelf();
            }
        });
    }

    /* access modifiers changed from: private */
    public void waitTime(long timeout) {
        synchronized (this.mLock) {
            try {
                this.mLock.wait(timeout);
            } catch (InterruptedException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    private void notifyAllLock() {
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mIsSending = true;
        try {
            if (VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 3);
                channel.enableVibration(false);
                channel.enableLights(false);
                channel.setSound(null, null);
                ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
                startForeground(11111, new Builder(getApplicationContext(), "11111").build());
            } else {
                startForeground(11111, new Notification());
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        judgeProgressRunnable();
        notifyAllLock();
        return super.onStartCommand(intent, flags, startId);
    }

    /* access modifiers changed from: private */
    public boolean isDfuServiceAlive() {
        return this.mIsSending || ServiceUtils.isServiceRunning(this, DfuService.class.getName()) || ServiceUtils.isServiceRunning(this, NewDfuService.class.getName()) || ServiceUtils.isServiceRunning(this, IwownFotaService.class.getName());
    }

    private void releaseLock() {
        if (this.mWl != null) {
            try {
                this.mWl.release();
            } catch (Exception e) {
            }
            this.mWl = null;
        }
    }

    /* access modifiers changed from: private */
    public void judgeProgressRunnable() {
        removeRunnable();
        this.mHandler.postDelayed(this.mProgressTimeoutRunnable, 180000);
    }

    private void removeRunnable() {
        this.mHandler.removeCallbacks(this.mProgressTimeoutRunnable);
    }

    /* access modifiers changed from: private */
    public void wakeUp() {
        if (this.mWl == null) {
            this.mWl = ((PowerManager) getSystemService("power")).newWakeLock(268435462, "my_tag");
        }
        this.mWl.acquire();
    }

    public static void updateSuccess(Context context) {
        Intent intent = new Intent(ContextUtil.app, HardwareUpdateSuccessActivity.class);
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WakeUpEvent event) {
        if (event.getState() == 0) {
            wakeUp();
        } else {
            releaseLock();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        notifyAllLock();
        removeRunnable();
        releaseLock();
        super.onDestroy();
        KLog.e("onDestroy");
        DfuServiceListenerHelper.unregisterProgressListener(this, this.mProgressListenerAdapter);
        DfuServiceListenerHelper.unregisterLogListener(this, this.mDfuLogListener);
    }
}
