package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGattCharacteristic;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.ReadCharacterListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import java.util.UUID;

public class BleReadRequest extends BleRequest implements ReadCharacterListener {
    private UUID mCharacterUUID;
    private UUID mServiceUUID;

    public BleReadRequest(UUID service, UUID character, BleGeneralResponse response) {
        super(response);
        this.mServiceUUID = service;
        this.mCharacterUUID = character;
    }

    public void processRequest() {
        switch (getCurrentStatus()) {
            case 0:
                onRequestCompleted(-1);
                return;
            case 2:
                startRead();
                return;
            case 19:
                startRead();
                return;
            default:
                onRequestCompleted(-1);
                return;
        }
    }

    private void startRead() {
        if (!readCharacteristic(this.mServiceUUID, this.mCharacterUUID)) {
            onRequestCompleted(-1);
        } else {
            startRequestTiming();
        }
    }

    public void onCharacteristicRead(BluetoothGattCharacteristic characteristic, int status, byte[] value) {
        stopRequestTiming();
        if (status == 0) {
            putByteArray(Constants.EXTRA_BYTE_VALUE, value);
            onRequestCompleted(0);
            return;
        }
        onRequestCompleted(-1);
    }
}
