package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGattDescriptor;
import com.inuker.bluetooth.library.connect.listener.WriteDescriptorListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import java.util.UUID;

public class BleWriteDescriptorRequest extends BleRequest implements WriteDescriptorListener {
    private byte[] mBytes;
    private UUID mCharacterUUID;
    private UUID mDescriptorUUID;
    private UUID mServiceUUID;

    public BleWriteDescriptorRequest(UUID service, UUID character, UUID descriptor, byte[] bytes, BleGeneralResponse response) {
        super(response);
        this.mServiceUUID = service;
        this.mCharacterUUID = character;
        this.mDescriptorUUID = descriptor;
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
        if (!writeDescriptor(this.mServiceUUID, this.mCharacterUUID, this.mDescriptorUUID, this.mBytes)) {
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
