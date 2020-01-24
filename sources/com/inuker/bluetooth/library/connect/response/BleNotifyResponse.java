package com.inuker.bluetooth.library.connect.response;

import java.util.UUID;

public interface BleNotifyResponse extends BleResponse {
    void onNotify(UUID uuid, UUID uuid2, byte[] bArr);
}
