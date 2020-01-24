package com.iwown.device_module.device_firmware_upgrade.service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.internal.view.SupportMenu;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.leprofiles.fmpserver.FmpServerAlertService;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.device_firmware_upgrade.NotificationActivity;
import com.socks.library.KLog;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import no.nordicsemi.android.dialog.BluetoothGattReceiver;
import no.nordicsemi.android.dialog.BluetoothGattSingleton;
import no.nordicsemi.android.dialog.SuotaManager;
import no.nordicsemi.android.dialog.async.DeviceConnectTask;
import no.nordicsemi.android.dialog.data.File;
import no.nordicsemi.android.dialog.data.Statics;

public class NewDfuService extends Service {
    public static final int ACTION_ABORT = 2;
    public static final int ACTION_PAUSE = 0;
    public static final int ACTION_RESUME = 1;
    public static final String BROADCAST_ACTION = "no.nordicsemi.android.dfu.broadcast.BROADCAST_ACTION";
    public static final String BROADCAST_ERROR = "no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR";
    public static final String BROADCAST_PROGRESS = "no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS";
    public static final int ERROR_CONNECTION_MASK = 16384;
    public static final int ERROR_CONNECTION_STATE_MASK = 32768;
    public static final int ERROR_DEVICE_DISCONNECTED = 4096;
    public static final int ERROR_FILE_ERROR = 4098;
    public static final int ERROR_FILE_INVALID = 4099;
    public static final int ERROR_FILE_IO_EXCEPTION = 4100;
    public static final int ERROR_FILE_NOT_FOUND = 4097;
    public static final int ERROR_MASK = 4096;
    public static final int ERROR_REMOTE_MASK = 8192;
    public static final int ERROR_SERVICE_DISCOVERY_NOT_STARTED = 4101;
    public static final int ERROR_TYPE_COMMUNICATION = 2;
    public static final int ERROR_TYPE_COMMUNICATION_STATE = 1;
    public static final int ERROR_TYPE_DFU_REMOTE = 3;
    public static final int ERROR_TYPE_OTHER = 0;
    public static final String EXTRA_ACTION = "no.nordicsemi.android.dfu.extra.EXTRA_ACTION";
    public static final String EXTRA_AVG_SPEED_B_PER_MS = "no.nordicsemi.android.dfu.extra.EXTRA_AVG_SPEED_B_PER_MS";
    public static final String EXTRA_DATA = "no.nordicsemi.android.dfu.extra.EXTRA_DATA";
    public static final String EXTRA_DEVICE = "no.NewDfuService.android.dfu.extra.EXTRA_DEVICE";
    public static final String EXTRA_DEVICE_ADDRESS = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS";
    public static final String EXTRA_DEVICE_NAME = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME";
    public static final String EXTRA_DISABLE_NOTIFICATION = "no.nordicsemi.android.dfu.extra.EXTRA_DISABLE_NOTIFICATION";
    public static final String EXTRA_ERROR_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE";
    public static final String EXTRA_FILE_PATH = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH";
    public static final String EXTRA_FILE_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE";
    public static final String EXTRA_PARTS_TOTAL = "no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL";
    public static final String EXTRA_PART_CURRENT = "no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT";
    public static final String EXTRA_PROGRESS = "no.nordicsemi.android.dfu.extra.EXTRA_PROGRESS";
    public static final String EXTRA_SPEED_B_PER_MS = "no.nordicsemi.android.dfu.extra.EXTRA_SPEED_B_PER_MS";
    public static final int NOTIFICATION_ID = 283;
    public static final int PROGRESS_ABORTED = -7;
    public static final int PROGRESS_COMPLETED = -6;
    public static final int PROGRESS_CONNECTING = -1;
    public static final int PROGRESS_DISCONNECTING = -5;
    public static final int PROGRESS_ENABLING_DFU_MODE = -3;
    public static final int PROGRESS_STARTING = -2;
    public static final int PROGRESS_VALIDATING = -4;
    public static final int TYPE_APPLICATION = 4;
    public static final int TYPE_BOOTLOADER = 2;
    public static final int TYPE_SOFT_DEVICE = 1;
    private BluetoothGattReceiver bluetoothGattReceiver;
    private BluetoothGattReceiver connectionStateReceiver;
    private long mBytesSent;
    private String mDeviceAddress;
    private String mDeviceName;
    private boolean mDisableNotification;
    private int mFileType;
    private Handler mHandler;
    private long mLastBytesSent;
    private int mLastProgress;
    private int mLastProgress1;
    private long mLastProgressTime;
    private int mPartCurrent;
    private int mPartsTotal;
    private long mStartTime;
    /* access modifiers changed from: private */
    public SuotaManager mSuotaManager;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler();
        this.bluetoothGattReceiver = new BluetoothGattReceiver() {
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                NewDfuService.this.mSuotaManager.processStep(intent);
            }
        };
        this.connectionStateReceiver = new BluetoothGattReceiver() {
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                NewDfuService.this.connectionStateChanged(intent.getIntExtra(FmpServerAlertService.INTENT_STATE, 0));
            }
        };
        registerReceiver(this.bluetoothGattReceiver, new IntentFilter(Statics.BLUETOOTH_GATT_UPDATE));
        registerReceiver(this.connectionStateReceiver, new IntentFilter(Statics.CONNECTION_STATE_UPDATE));
        try {
            if (VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
                channel.enableVibration(false);
                channel.enableLights(false);
                channel.setSound(null, null);
                ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
                startForeground(1, new Builder(getApplicationContext(), "11111").build());
                return;
            }
            startForeground(1, new Notification());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void connectionStateChanged(int connectionState) {
        if (connectionState == 0) {
            if (this.mLastProgress != -6) {
                updateProgressNotification(16384, 0, 0);
            }
            if (BluetoothGattSingleton.getGatt() != null) {
                BluetoothGattSingleton.getGatt().close();
            }
            if (this.mSuotaManager.isFinished() || !this.mSuotaManager.getError()) {
            }
            return;
        }
        if (connectionState == 2) {
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            try {
                BluetoothOperation.isConnected();
                BluetoothOperation.disconnect();
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(EXTRA_DEVICE);
                String filePath = intent.getStringExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH");
                this.mSuotaManager = new SuotaManager(this);
                this.mSuotaManager.setDevice(device);
                this.mSuotaManager.setFile(new File(new FileInputStream(filePath)));
                this.mDeviceAddress = device.getAddress();
                this.mDeviceName = device.getName();
                updateProgressNotification(-1, 0, 0);
                new DeviceConnectTask(this, device, this.mSuotaManager) {
                    /* access modifiers changed from: protected */
                    public void onProgressUpdate(BluetoothGatt... gatt) {
                        BluetoothGattSingleton.setGatt(gatt[0]);
                    }
                }.execute(new Void[0]);
            } catch (IOException e) {
                terminateConnection(4098);
                ThrowableExtension.printStackTrace(e);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startUpdate() {
        int fileBlockSize = 1;
        if (this.mSuotaManager.type == 1) {
            fileBlockSize = 240;
        }
        this.mSuotaManager.getFile().setFileBlockSize(fileBlockSize);
        Intent intent = new Intent();
        intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
        intent.putExtra("step", 1);
        sendBroadcast(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.bluetoothGattReceiver);
        unregisterReceiver(this.connectionStateReceiver);
    }

    @TargetApi(26)
    public void updateProgressNotification(int progress, int blockCounter, int numberOfBlocks) {
        String deviceName;
        if (this.mLastProgress != progress) {
            KLog.e("  progress  " + progress);
            this.mLastProgress = progress;
            if (progress < 4096) {
                sendProgressBroadcast(progress, blockCounter, numberOfBlocks);
            } else {
                sendErrorBroadcast(progress);
            }
            if (!this.mDisableNotification) {
                String deviceAddress = this.mDeviceAddress;
                if (this.mDeviceName != null) {
                    deviceName = this.mDeviceName;
                } else {
                    deviceName = getString(R.string.device_module_dfu_unknown_name);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(17301640).setOnlyAlertOnce(true);
                switch (progress) {
                    case -7:
                        builder.setOngoing(false).setContentTitle(getString(R.string.device_module_dfu_status_aborted)).setSmallIcon(17301641).setContentText(getString(R.string.device_module_dfu_status_aborted_msg)).setAutoCancel(true);
                        break;
                    case -6:
                        builder.setOngoing(false).setContentTitle(getString(R.string.device_module_dfu_status_completed)).setSmallIcon(17301641).setContentText(getString(R.string.device_module_dfu_status_completed_msg)).setAutoCancel(true).setColor(-16730086);
                        break;
                    case -5:
                        builder.setOngoing(true).setContentTitle(getString(R.string.device_module_dfu_status_disconnecting)).setContentText(getString(R.string.device_module_dfu_status_disconnecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                        break;
                    case -4:
                    case -3:
                    case -2:
                        break;
                    case -1:
                        builder.setOngoing(true).setContentTitle(getString(R.string.device_module_connecting)).setContentText(getString(R.string.device_module_dfu_status_connecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                        break;
                    default:
                        if (progress < 4096) {
                            String title = getString(R.string.device_module_dfu_status_uploading);
                            builder.setOngoing(true).setContentTitle(title).setContentText(getString(R.string.device_module_dfu_status_uploading_msg, new Object[]{deviceName})).setProgress(100, progress, false);
                            break;
                        } else {
                            builder.setOngoing(false).setContentTitle(getString(R.string.device_module_dfu_status_error)).setSmallIcon(17301641).setContentText(getString(R.string.device_module_dfu_status_error_msg)).setAutoCancel(true).setColor(SupportMenu.CATEGORY_MASK);
                            break;
                        }
                }
                Intent intent = new Intent(this, getNotificationTarget());
                intent.addFlags(268435456);
                intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
                intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", deviceName);
                intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PROGRESS", progress);
                builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
                if (!(progress == -7 || progress == -6 || progress >= 4096)) {
                    Intent abortIntent = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_ACTION");
                    abortIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ACTION", 2);
                    builder.addAction(R.mipmap.ic_action_notify_cancel, getString(R.string.device_module_dfu_action_abort), PendingIntent.getBroadcast(this, 1, abortIntent, 134217728));
                }
                NotificationManager manager = (NotificationManager) getSystemService("notification");
                try {
                    if (VERSION.SDK_INT >= 26) {
                        NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
                        channel.enableVibration(false);
                        channel.enableLights(false);
                        channel.setSound(null, null);
                        manager.createNotificationChannel(channel);
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                manager.notify(283, builder.build());
            }
        }
    }

    public void sendProgressBroadcast(int progress, int blockCounter, int numberOfBlocks) {
        float speed;
        float avgSpeed;
        long now = SystemClock.elapsedRealtime();
        if (now - this.mLastProgressTime != 0) {
            speed = ((float) (this.mBytesSent - this.mLastBytesSent)) / ((float) (now - this.mLastProgressTime));
        } else {
            speed = 0.0f;
        }
        if (now - this.mStartTime != 0) {
            avgSpeed = ((float) this.mBytesSent) / ((float) (now - this.mStartTime));
        } else {
            avgSpeed = 0.0f;
        }
        this.mLastProgressTime = now;
        this.mLastBytesSent = this.mBytesSent;
        Intent broadcast = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS");
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", progress);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.mDeviceAddress);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT", blockCounter);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL", numberOfBlocks);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_SPEED_B_PER_MS", speed);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_AVG_SPEED_B_PER_MS", avgSpeed);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }

    public void sendErrorBroadcast(int error) {
        Intent broadcast = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
        if ((error & 16384) > 0) {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", error & -16385);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 2);
        } else if ((32768 & error) > 0) {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", -32769 & error);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 1);
        } else if ((error & 8192) > 0) {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", error);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 3);
        } else {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", error);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 0);
        }
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.mDeviceAddress);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
        disconnect(BluetoothGattSingleton.getGatt());
    }

    /* access modifiers changed from: protected */
    public Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }

    private void disconnect(BluetoothGatt gatt) {
        if (gatt != null) {
            KLog.d("Disconnecting...");
            updateProgressNotification(-5, 0, 0);
            KLog.d("Disconnecting from the device...");
            KLog.d("gatt.disconnect()");
            gatt.disconnect();
            KLog.d("Disconnected");
        }
    }

    public void terminateConnection(int error) {
        BluetoothGatt gatt = BluetoothGattSingleton.getGatt();
        if (gatt != null) {
            disconnect(gatt);
            refreshDeviceCache(gatt, false);
            close(gatt);
            updateProgressNotification(error, 0, 0);
        }
    }

    private void refreshDeviceCache(BluetoothGatt gatt, boolean force) {
        if (force || gatt.getDevice().getBondState() == 10) {
            try {
                Method refresh = gatt.getClass().getMethod("refresh", new Class[0]);
                if (refresh != null) {
                    KLog.d("Refreshing result: " + ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue());
                }
            } catch (Exception e) {
                KLog.e("An exception occurred while refreshing device", e.toString());
            }
        }
    }

    private void close(BluetoothGatt gatt) {
        KLog.d("gatt.close()");
        gatt.close();
    }

    public void startUp() {
        startUpdate();
    }
}
