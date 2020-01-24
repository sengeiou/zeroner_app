package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGattCharacteristic;
import com.inuker.bluetooth.library.connect.listener.WriteCharacterListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import java.util.UUID;

public class BleWriteNoRspRequest extends BleRequest implements WriteCharacterListener {
    private byte[] mBytes;
    private UUID mCharacterUUID;
    private UUID mServiceUUID;

    public BleWriteNoRspRequest(UUID service, UUID character, byte[] bytes, BleGeneralResponse response) {
        super(response);
        this.mServiceUUID = service;
        this.mCharacterUUID = character;
        this.mBytes = bytes;
    }

    public void processRequest() {
        switch (getCurrentStatus()) {
            case 0:
                onRequestCompleted(-1);
                return;
            case 2:
                startWrite();
                return;
            case 19:
                startWrite();
                return;
            default:
                onRequestCompleted(-1);
                return;
        }
    }

    private void startWrite() {
        if (!writeCharacteristicWithNoRsp(this.mServiceUUID, this.mCharacterUUID, this.mBytes)) {
            onRequestCompleted(-1);
        } else {
            startRequestTiming();
        }
    }

    public void onCharacteristicWrite(BluetoothGattCharacteristic characteristic, int status, byte[] value) {
        stopRequestTiming();
        if (status == 0) {
            onRequestCompleted(0);
        } else {
            onRequestCompleted(-1);
        }
    }
}
