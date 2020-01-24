package com.inuker.bluetooth.library.connect.listener;

import com.inuker.bluetooth.library.model.BleGattProfile;

public interface ServiceDiscoverListener extends GattResponseListener {
    void onServicesDiscovered(int i, BleGattProfile bleGattProfile);
}
