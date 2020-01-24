package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGattDescriptor;
import com.inuker.bluetooth.library.connect.listener.WriteDescriptorListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import java.util.UUID;

public class BleUnnotifyRequest extends BleRequest implements WriteDescriptorListener {
    private UUID mCharacterUUID;
    private UUID mServiceUUID;

    public BleUnnotifyRequest(UUID service, UUID character, BleGeneralResponse response) {
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
                closeNotify();
                return;
            case 19:
                closeNotify();
                return;
            default:
                onRequestCompleted(-1);
                return;
        }
    }

    private void closeNotify() {
        if (!setCharacteristicNotification(this.mServiceUUID, this.mCharacterUUID, false)) {
            onRequestCompleted(-1);
        } else {
            startRequestTiming();
        }
    }

    public void onDescriptorWrite(BluetoothGattDescriptor descriptor, int status) {
        stopRequestTiming();
        if (status == 0) {
            onRequestCompleted(0);
        } else {
            onRequestCompleted(-1);
        }
    }
}
