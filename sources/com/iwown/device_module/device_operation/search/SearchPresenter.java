package com.iwown.device_module.device_operation.search;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.Utils;
import com.iwown.device_module.device_operation.bean.ModeItems;
import com.iwown.device_module.device_operation.search.DeviceSearchContract.Presenter;
import com.iwown.device_module.device_operation.search.DeviceSearchContract.View;
import com.iwown.device_module.device_operation.timeout.TimeoutHandler;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;

public class SearchPresenter implements Presenter, Callback {
    public static final int BleConnectFail = 102;
    public static final int BleScanStart = 103;
    public static final int BleScanTimeout = 101;
    public static final int ConnectTimeoutSecond = 90000;
    public static final int TimeoutSecond = 12000;
    TimeoutHandler handler = new TimeoutHandler(this);
    private ModeItems modeItems;
    View view;

    public SearchPresenter(View view2) {
        view2.setPresenter(this);
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public void startScan(Context context) {
        if (!BluetoothOperation.isEnabledBluetooth()) {
            this.view.bluetoothStatus();
            return;
        }
        this.modeItems = null;
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Scan_Refresh_Time, new DateUtil().getUnixTimestamp());
        this.handler.sendEmptyMessage(103);
        this.handler.sendEmptyMessageDelayed(101, 12000);
    }

    public void stopScan(Context context) {
        BluetoothOperation.stopScan(context);
    }

    public void connect(Context context, WristBand wristBand) {
        if (!BluetoothOperation.isEnabledBluetooth()) {
            this.view.bluetoothStatus();
            return;
        }
        BluetoothOperation.connect(context, wristBand);
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac, wristBand.getAddress());
        this.handler.sendEmptyMessageDelayed(102, 90000);
    }

    public void disconnect() {
        BluetoothOperation.disconnect();
    }

    public int getSDKTypeByDeviceName(Context context, int key_calssId, String name) {
        if (this.modeItems == null) {
            String modelClass = PrefUtil.getString(context, SharedPreferencesAction.APP_SDK_UPDATE_Content);
            if (TextUtils.isEmpty(modelClass)) {
                modelClass = Utils.getFromAssets(context, "modesdklist2default.txt");
            }
            this.modeItems = (ModeItems) new Gson().fromJson(modelClass, ModeItems.class);
        }
        return BluetoothOperation.getSDKTypeByDeviceName(key_calssId, name, this.modeItems);
    }

    public void searchSuccess() {
        this.handler.removeMessages(101);
    }

    public void connectStatue(boolean statue) {
        KLog.e("=======connectStatue========");
        this.handler.removeMessages(102);
        if (statue) {
            this.view.connectSuccess();
        } else {
            this.view.connectFail();
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 101:
                this.view.searchTimeout();
                break;
            case 102:
                KLog.e("=======handleMessageBleConnectFail========");
                this.view.connectFail();
                break;
            case 103:
                BluetoothOperation.startScan(ContextUtil.app);
                break;
        }
        return false;
    }

    public void tryRecycleAnimationDrawable(AnimationDrawable animationDrawables) {
        if (animationDrawables != null) {
            try {
                animationDrawables.stop();
                for (int i = 0; i < animationDrawables.getNumberOfFrames(); i++) {
                    Drawable frame = animationDrawables.getFrame(i);
                    if (frame instanceof BitmapDrawable) {
                        ((BitmapDrawable) frame).getBitmap().recycle();
                    }
                    frame.setCallback(null);
                }
                animationDrawables.setCallback(null);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }
}
