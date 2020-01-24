package com.iwown.device_module.callback;

import com.iwown.ble_module.iwown.bean.WristBand;

public interface IBluetoothListener {
    void connectStatue(boolean z);

    void noCallBack();

    void onBluetoothError();

    void onBluetoothInit();

    void onCharacteristicChange(String str);

    void onCommonSend(byte[] bArr);

    void onDataArrived(int i, int i2, String str);

    void onDiscoverCharacter(String str);

    void onDiscoverService(String str);

    void onPreConnect();

    void onScanResult(WristBand wristBand);
}
