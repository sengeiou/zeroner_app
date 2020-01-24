package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGattDescriptor;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.ReadDescriptorListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import java.util.UUID;

public class BleReadDescriptorRequest extends BleRequest implements ReadDescriptorListener {
    private UUID mCharacterUUID;
    private UUID mDescriptorUUID;
    private UUID mServiceUUID;

    public BleReadDescriptorRequest(UUID service, UUID character, UUID descriptor, BleGeneralResponse response) {
        super(response);
        this.mServiceUUID = service;
        this.mCharacterUUID = character;
        this.mDescriptorUUID = descriptor;
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
        if (!readDescriptor(this.mServiceUUID, this.mCharacterUUID, this.mDescriptorUUID)) {
            onRequestCompleted(-1);
        } else {
            startRequestTiming();
        }
    }

    public void onDescriptorRead(BluetoothGattDescriptor descriptor, int status, byte[] value) {
        stopRequestTiming();
        if (status == 0) {
            putByteArray(Constants.EXTRA_BYTE_VALUE, value);
            onRequestCompleted(0);
            return;
        }
        onRequestCompleted(-1);
    }
}
