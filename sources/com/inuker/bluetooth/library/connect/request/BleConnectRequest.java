package com.inuker.bluetooth.library.connect.request;

import android.os.Message;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.ServiceDiscoverListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions.Builder;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;

public class BleConnectRequest extends BleRequest implements ServiceDiscoverListener {
    private static final int MSG_CONNECT = 1;
    private static final int MSG_CONNECT_TIMEOUT = 3;
    private static final int MSG_DISCOVER_SERVICE = 2;
    private static final int MSG_DISCOVER_SERVICE_TIMEOUT = 4;
    private static final int MSG_RETRY_DISCOVER_SERVICE = 5;
    private int mConnectCount;
    private BleConnectOptions mConnectOptions;
    private int mServiceDiscoverCount;

    public BleConnectRequest(BleConnectOptions options, BleGeneralResponse response) {
        super(response);
        if (options == null) {
            options = new Builder().build();
        }
        this.mConnectOptions = options;
    }

    public void processRequest() {
        processConnect();
    }

    private void processConnect() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mServiceDiscoverCount = 0;
        switch (getCurrentStatus()) {
            case 0:
                if (!doOpenNewGatt()) {
                    closeGatt();
                    return;
                } else {
                    this.mHandler.sendEmptyMessageDelayed(3, (long) this.mConnectOptions.getConnectTimeout());
                    return;
                }
            case 2:
                processDiscoverService();
                return;
            case 19:
                onConnectSuccess();
                return;
            default:
                return;
        }
    }

    private boolean doOpenNewGatt() {
        this.mConnectCount++;
        return openGatt();
    }

    private boolean doDiscoverService() {
        this.mServiceDiscoverCount++;
        return discoverService();
    }

    private void retryConnectIfNeeded() {
        if (this.mConnectCount < this.mConnectOptions.getConnectRetry() + 1) {
            retryConnectLater();
        } else {
            onRequestCompleted(-1);
        }
    }

    private void retryDiscoverServiceIfNeeded() {
        if (this.mServiceDiscoverCount < this.mConnectOptions.getServiceDiscoverRetry() + 1) {
            retryDiscoverServiceLater();
        } else {
            closeGatt();
        }
    }

    private void onServiceDiscoverFailed() {
        BluetoothLog.v(String.format("onServiceDiscoverFailed", new Object[0]));
        refreshDeviceCache();
        this.mHandler.sendEmptyMessage(5);
    }

    private void processDiscoverService() {
        BluetoothLog.v(String.format("processDiscoverService, status = %s", new Object[]{getStatusText()}));
        switch (getCurrentStatus()) {
            case 0:
                retryConnectIfNeeded();
                return;
            case 2:
                if (!doDiscoverService()) {
                    onServiceDiscoverFailed();
                    return;
                } else {
                    this.mHandler.sendEmptyMessageDelayed(4, (long) this.mConnectOptions.getServiceDiscoverTimeout());
                    return;
                }
            case 19:
                onConnectSuccess();
                return;
            default:
                return;
        }
    }

    private void retryConnectLater() {
        log(String.format("retry connect later", new Object[0]));
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    private void retryDiscoverServiceLater() {
        log(String.format("retry discover service later", new Object[0]));
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendEmptyMessageDelayed(2, 1000);
    }

    private void processConnectTimeout() {
        log(String.format("connect timeout", new Object[0]));
        this.mHandler.removeCallbacksAndMessages(null);
        closeGatt();
    }

    private void processDiscoverServiceTimeout() {
        log(String.format("service discover timeout", new Object[0]));
        this.mHandler.removeCallbacksAndMessages(null);
        closeGatt();
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                processConnect();
                break;
            case 2:
                processDiscoverService();
                break;
            case 3:
                processConnectTimeout();
                break;
            case 4:
                processDiscoverServiceTimeout();
                break;
            case 5:
                retryDiscoverServiceIfNeeded();
                break;
        }
        return super.handleMessage(msg);
    }

    public String toString() {
        return "BleConnectRequest{options=" + this.mConnectOptions + '}';
    }

    public void onConnectStatusChanged(boolean connectedOrDisconnected) {
        checkRuntime();
        this.mHandler.removeMessages(3);
        if (connectedOrDisconnected) {
            this.mHandler.sendEmptyMessageDelayed(2, 300);
            return;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        retryConnectIfNeeded();
    }

    public void onServicesDiscovered(int status, BleGattProfile profile) {
        checkRuntime();
        this.mHandler.removeMessages(4);
        if (status == 0) {
            onConnectSuccess();
        } else {
            onServiceDiscoverFailed();
        }
    }

    private void onConnectSuccess() {
        BleGattProfile profile = getGattProfile();
        if (profile != null) {
            putParcelable(Constants.EXTRA_GATT_PROFILE, profile);
        }
        onRequestCompleted(0);
    }
}
