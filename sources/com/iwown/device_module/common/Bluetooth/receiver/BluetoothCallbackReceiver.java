package com.iwown.device_module.common.Bluetooth.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.device_module.common.BaseActionUtils;

public class BluetoothCallbackReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        char c = 65535;
        switch (action.hashCode()) {
            case -1973296153:
                if (action.equals(BaseActionUtils.ON_BLUETOOTH_ERROR)) {
                    c = 6;
                    break;
                }
                break;
            case -1846766833:
                if (action.equals(BaseActionUtils.ON_SCAN_RESULT)) {
                    c = 0;
                    break;
                }
                break;
            case -599879671:
                if (action.equals(BaseActionUtils.ON_CONNECT_STATUE)) {
                    c = 3;
                    break;
                }
                break;
            case -291648436:
                if (action.equals(BaseActionUtils.ON_COMMON_SEND)) {
                    c = 2;
                    break;
                }
                break;
            case 69583715:
                if (action.equals(BaseActionUtils.BLE_PRE_CONNECT)) {
                    c = 7;
                    break;
                }
                break;
            case 213554993:
                if (action.equals(BaseActionUtils.ON_BLUETOOTH_INIT)) {
                    c = 1;
                    break;
                }
                break;
            case 993993672:
                if (action.equals(BaseActionUtils.ON_DATA_ARRIVED)) {
                    c = 4;
                    break;
                }
                break;
            case 1946267076:
                if (action.equals(BaseActionUtils.ON_CHARACTERISTIC_CHANGE)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                onScanResult((WristBand) extras.getParcelable(BaseActionUtils.BLE_SCAN_RESULT_DEVICE));
                return;
            case 1:
                onBluetoothInit();
                return;
            case 2:
                onCommonSend(extras.getByteArray(BaseActionUtils.BLE_COMMON_SEND));
                return;
            case 3:
                connectStatue(extras.getBoolean(BaseActionUtils.BLE_CONNECT_STATUE));
                return;
            case 4:
                onDataArrived(context, extras.getInt(BaseActionUtils.BLE_SDK_TYPE, 0), extras.getInt(BaseActionUtils.BLE_DATA_TYPE, 0), extras.getString(BaseActionUtils.BLE_ARRIVED_DATA));
                return;
            case 5:
                onCharacteristicChange();
                return;
            case 6:
                onBluetoothError();
                return;
            case 7:
                onPreConnect();
                return;
            default:
                return;
        }
    }

    public void onScanResult(WristBand device) {
    }

    public void onDataArrived(Context context, int ble_sdk_type, int dataType, String data) {
    }

    public void connectStatue(boolean isConnect) {
    }

    public void onCharacteristicChange() {
    }

    public void onBluetoothError() {
    }

    public void onBluetoothInit() {
    }

    public void onCommonSend(byte[] data) {
    }

    public void onPreConnect() {
    }
}
