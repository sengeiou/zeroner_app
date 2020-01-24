package com.iwown.device_module.device_firmware_upgrade.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.iwown.device_module.device_firmware_upgrade.FotaUtils;
import com.socks.library.KLog;
import coms.mediatek.ctrl.fota.common.FotaOperator;
import coms.mediatek.ctrl.fota.common.FotaVersion;
import coms.mediatek.ctrl.fota.common.IFotaOperatorCallback;
import coms.mediatek.wearable.WearableManager;

public class IwownFotaService extends Service {
    private static int INTENT_NULL_ERRO = -22;
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    private String address = "";
    /* access modifiers changed from: private */
    public String file_path = "";
    private IFotaOperatorCallback mFotaCallback = new IFotaOperatorCallback() {
        public void onCustomerInfoReceived(String information) {
            Log.d(IwownFotaService.this.TAG, "[onCustomerInfoReceived] information : " + information);
        }

        public void onFotaVersionReceived(FotaVersion version) {
            Log.d(IwownFotaService.this.TAG, "[onFotaVersionReceived] version : " + version);
        }

        public void onStatusReceived(int status) {
            Log.d(IwownFotaService.this.TAG, "[onStatusReceived] status : " + status);
            switch (status) {
                case FotaUtils.READ_FILE_FAILED /*-101*/:
                case FotaUtils.FILE_NOT_FOUND_ERROR /*-100*/:
                case -7:
                case -6:
                case -5:
                    break;
                case -4:
                case -3:
                case -2:
                case -1:
                    Log.d(IwownFotaService.this.TAG, "[onStatusReceived] transfer error happened, set mTransferViaBTErrorHappened to be TRUE");
                    break;
                case 2:
                    Log.e(IwownFotaService.this.TAG, "[onStatusReceived] send succeed. update text view");
                    KLog.e("[onStatusReceived]  2");
                    return;
                case 3:
                    Log.e(IwownFotaService.this.TAG, "[onStatusReceived]  3");
                    KLog.e("[onStatusReceived]  3");
                    return;
                default:
                    return;
            }
            Log.d(IwownFotaService.this.TAG, "[onStatusReceived] update failed!");
            IwownFotaService.this.cancelTransTask();
            if (status == -7) {
            }
            Message msg2 = IwownFotaService.this.mHandler.obtainMessage();
            if (status == -100) {
                msg2.arg1 = -100;
            } else if (status == -101) {
                msg2.arg1 = FotaUtils.READ_FILE_FAILED;
            } else {
                msg2.arg1 = 4;
            }
            IwownFotaService.this.mTransferViaBTErrorHappened = true;
            IwownFotaService.this.sendErroBroadCast(status);
        }

        public void onConnectionStateChange(int newState) {
            Log.d(IwownFotaService.this.TAG, "[onConnectionStateChange] newState " + newState);
            if (newState == 2) {
                IwownFotaService.this.sendProgressBroadCast(-1);
                return;
            }
            if (newState == 6) {
                IwownFotaService.this.sendProgressBroadCast(-5);
            }
            if (newState == 4 || newState == 5 || !WearableManager.getInstance().isAvailable()) {
                IwownFotaService.this.cancelTransTask();
                IwownFotaService.this.sendErroBroadCast(newState);
            } else if (newState == 3) {
                IwownFotaService.this.mTransferTask.execute(new Void[0]);
                IwownFotaService.this.sendProgressBroadCast(-2);
            }
        }

        public void onProgress(int progress) {
            if (!IwownFotaService.this.mTransferViaBTErrorHappened) {
                Log.d(IwownFotaService.this.TAG, "[onProgress] progress : " + progress);
                Log.e("licl", "[onProgress] progress : " + progress);
                KLog.e("[onProgress] progress : " + progress);
                if (progress == 100) {
                    KLog.e("========mtk固件升级文件写入结束===========");
                    IwownFotaService.this.sendProgressBroadCast(-6);
                    IwownFotaService.this.cancelTransTask();
                    IwownFotaService.this.stopSelf();
                    return;
                }
                IwownFotaService.this.sendProgressBroadCast(progress);
                return;
            }
            Log.d(IwownFotaService.this.TAG, "[onProgress] mTransferViaBTErrorHappened is TRUE, no need to update progress");
            IwownFotaService.this.sendProgressBroadCast(-7);
        }
    };
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public AsyncTask<Void, Void, Void> mTransferTask = new AsyncTask<Void, Void, Void>() {
        /* access modifiers changed from: protected */
        public Void doInBackground(Void... parameters) {
            KLog.e("========mtk固件升级文件开始写入===========");
            Log.e("licl", "[doInBackground] begin Fota Transferring--" + IwownFotaService.this.file_path);
            if (!TextUtils.isEmpty(IwownFotaService.this.file_path)) {
                FotaOperator.getInstance(IwownFotaService.this).sendFotaFirmwareData(5, IwownFotaService.this.file_path);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.e("licl", "[mTransferTaks] onPostExecute called");
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            super.onCancelled();
            Log.e("licl", "[mTransferTaks] onCancelled is called, update UX");
            KLog.e("[mTransferTaks] onCancelled is called, update UX");
        }
    };
    /* access modifiers changed from: private */
    public boolean mTransferViaBTErrorHappened = false;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        FotaOperator.getInstance(this).registerFotaCallback(this.mFotaCallback);
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
            startForeground(1, new Builder(getApplicationContext(), "11111").build());
        } else {
            startForeground(1, new Notification());
        }
        KLog.e("========================================IwownFotaService --onCreate=============================");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            sendErroBroadCast(INTENT_NULL_ERRO);
            stopSelf();
        }
        KLog.e("IwownFotaService --onStartCommand");
        Log.e("licl", "IwownFotaService--onStartCommand");
        if (WearableManager.getInstance().isAvailable()) {
            Log.e("licl", "mtk手表已经连接上，直接去升级");
            KLog.e("mtk手表已经连接上，直接去升级");
            this.file_path = intent.getStringExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH");
            this.address = intent.getStringExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS");
            cancelTransTask();
            this.mTransferTask.execute(new Void[0]);
        } else {
            Log.e("licl", "mtk手表升级前进行连接");
            KLog.e("mtk手表升级前进行连接");
            if (WearableManager.getInstance().getRemoteDevice() == null) {
                WearableManager.getInstance().setRemoteDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(intent.getStringExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS")));
            }
            WearableManager.getInstance().connect();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        KLog.e("IwownFotaService --onDestroy");
        super.onDestroy();
        cancelTransTask();
        FotaOperator.getInstance(this).unregisterFotaCallback(this.mFotaCallback);
    }

    /* access modifiers changed from: private */
    public void sendProgressBroadCast(int progress) {
        Intent progressIntent = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS");
        progressIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", progress);
        progressIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.address);
        LocalBroadcastManager.getInstance(this).sendBroadcast(progressIntent);
    }

    /* access modifiers changed from: private */
    public void sendErroBroadCast(int status) {
        KLog.e("mtk固件升级出现错误 code:" + status);
        Intent errorIntent = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
        errorIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.address);
        errorIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(errorIntent);
        cancelTransTask();
    }

    public void cancelTransTask() {
        if (!this.mTransferTask.isCancelled() && this.mTransferTask.getStatus() == Status.RUNNING) {
            Log.d(this.TAG, "[onStatusReceived] cancel the transfer action");
            KLog.e("[onStatusReceived] cancel the transfer action");
            this.mTransferTask.cancel(true);
        }
    }
}
