package com.inuker.bluetooth.library.connect.request;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.RuntimeChecker;
import com.inuker.bluetooth.library.connect.IBleConnectDispatcher;
import com.inuker.bluetooth.library.connect.IBleConnectWorker;
import com.inuker.bluetooth.library.connect.listener.GattResponseListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import java.util.UUID;

public abstract class BleRequest implements IBleConnectWorker, IBleRequest, Callback, GattResponseListener, RuntimeChecker {
    protected static final int MSG_REQUEST_TIMEOUT = 32;
    protected String mAddress;
    protected IBleConnectDispatcher mDispatcher;
    protected Bundle mExtra = new Bundle();
    private boolean mFinished;
    protected Handler mHandler = new Handler(Looper.myLooper(), this);
    protected boolean mRequestTimeout;
    protected BleGeneralResponse mResponse;
    protected Handler mResponseHandler = new Handler(Looper.getMainLooper());
    private RuntimeChecker mRuntimeChecker;
    protected IBleConnectWorker mWorker;

    public abstract void processRequest();

    public BleRequest(BleGeneralResponse response) {
        this.mResponse = response;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public void setWorker(IBleConnectWorker worker) {
        this.mWorker = worker;
    }

    public void onResponse(final int code) {
        if (!this.mFinished) {
            this.mFinished = true;
            this.mResponseHandler.post(new Runnable() {
                public void run() {
                    try {
                        if (BleRequest.this.mResponse != null) {
                            BleRequest.this.mResponse.onResponse(code, BleRequest.this.mExtra);
                        }
                    } catch (Throwable e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            });
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        return sb.toString();
    }

    public void putIntExtra(String key, int value) {
        this.mExtra.putInt(key, value);
    }

    public int getIntExtra(String key, int defaultValue) {
        return this.mExtra.getInt(key, defaultValue);
    }

    public void putByteArray(String key, byte[] bytes) {
        this.mExtra.putByteArray(key, bytes);
    }

    public void putParcelable(String key, Parcelable object) {
        this.mExtra.putParcelable(key, object);
    }

    public Bundle getExtra() {
        return this.mExtra;
    }

    /* access modifiers changed from: protected */
    public String getStatusText() {
        return Constants.getStatusText(getCurrentStatus());
    }

    public boolean readDescriptor(UUID service, UUID characteristic, UUID descriptor) {
        return this.mWorker.readDescriptor(service, characteristic, descriptor);
    }

    public boolean writeDescriptor(UUID service, UUID characteristic, UUID descriptor, byte[] value) {
        return this.mWorker.writeDescriptor(service, characteristic, descriptor, value);
    }

    public boolean openGatt() {
        return this.mWorker.openGatt();
    }

    public boolean discoverService() {
        return this.mWorker.discoverService();
    }

    public int getCurrentStatus() {
        return this.mWorker.getCurrentStatus();
    }

    public final void process(IBleConnectDispatcher dispatcher) {
        checkRuntime();
        this.mDispatcher = dispatcher;
        BluetoothLog.w(String.format("Process %s, status = %s", new Object[]{getClass().getSimpleName(), getStatusText()}));
        if (!BluetoothUtils.isBleSupported()) {
            onRequestCompleted(-4);
        } else if (!BluetoothUtils.isBluetoothEnabled()) {
            onRequestCompleted(-5);
        } else {
            try {
                registerGattResponseListener(this);
                processRequest();
            } catch (Throwable e) {
                BluetoothLog.e(e);
                onRequestCompleted(-10);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRequestCompleted(int code) {
        checkRuntime();
        log(String.format("request complete: code = %d", new Object[]{Integer.valueOf(code)}));
        this.mHandler.removeCallbacksAndMessages(null);
        clearGattResponseListener(this);
        onResponse(code);
        this.mDispatcher.onRequestCompleted(this);
    }

    public void closeGatt() {
        log(String.format("close gatt", new Object[0]));
        this.mWorker.closeGatt();
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 32:
                this.mRequestTimeout = true;
                closeGatt();
                break;
        }
        return true;
    }

    public void registerGattResponseListener(GattResponseListener listener) {
        this.mWorker.registerGattResponseListener(listener);
    }

    public void clearGattResponseListener(GattResponseListener listener) {
        this.mWorker.clearGattResponseListener(listener);
    }

    public boolean refreshDeviceCache() {
        return this.mWorker.refreshDeviceCache();
    }

    public boolean readCharacteristic(UUID service, UUID characteristic) {
        return this.mWorker.readCharacteristic(service, characteristic);
    }

    public boolean writeCharacteristic(UUID service, UUID character, byte[] value) {
        return this.mWorker.writeCharacteristic(service, character, value);
    }

    public boolean writeCharacteristicWithNoRsp(UUID service, UUID character, byte[] value) {
        return this.mWorker.writeCharacteristicWithNoRsp(service, character, value);
    }

    public boolean setCharacteristicNotification(UUID service, UUID character, boolean enable) {
        return this.mWorker.setCharacteristicNotification(service, character, enable);
    }

    public boolean setCharacteristicIndication(UUID service, UUID character, boolean enable) {
        return this.mWorker.setCharacteristicIndication(service, character, enable);
    }

    public boolean readRemoteRssi() {
        return this.mWorker.readRemoteRssi();
    }

    /* access modifiers changed from: protected */
    public void log(String msg) {
        BluetoothLog.v(String.format("%s %s >>> %s", new Object[]{getClass().getSimpleName(), getAddress(), msg}));
    }

    public void setRuntimeChecker(RuntimeChecker checker) {
        this.mRuntimeChecker = checker;
    }

    public void checkRuntime() {
        this.mRuntimeChecker.checkRuntime();
    }

    public void cancel() {
        checkRuntime();
        log(String.format("request canceled", new Object[0]));
        this.mHandler.removeCallbacksAndMessages(null);
        clearGattResponseListener(this);
        onResponse(-2);
    }

    /* access modifiers changed from: protected */
    public long getTimeoutInMillis() {
        return 30000;
    }

    public void onConnectStatusChanged(boolean connectedOrDisconnected) {
        if (!connectedOrDisconnected) {
            onRequestCompleted(this.mRequestTimeout ? -7 : -1);
        }
    }

    /* access modifiers changed from: protected */
    public void startRequestTiming() {
        this.mHandler.sendEmptyMessageDelayed(32, getTimeoutInMillis());
    }

    /* access modifiers changed from: protected */
    public void stopRequestTiming() {
        this.mHandler.removeMessages(32);
    }

    public BleGattProfile getGattProfile() {
        return this.mWorker.getGattProfile();
    }
}
