package com.iwown.ble_module.mtk_ble;

import com.iwown.ble_module.iwown.bean.WristBand;

public interface IDataReceiveHandler {
    void connectStatue(boolean z);

    void onBluetoothError(int i);

    void onBluetoothInit();

    void onCharacteristicChange(String str);

    void onCommonSend(byte[] bArr);

    void onDataArrived(int i, int i2, String str);

    void onDiscoverCharacter(String str);

    void onDiscoverService(String str);

    void onNoCallback();

    void onPreConnect();

    void onScanResult(WristBand wristBand);
}
