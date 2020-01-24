package com.inuker.bluetooth.library.receiver.listener;

import java.util.UUID;

public abstract class BleCharacterChangeListener extends BluetoothReceiverListener {
    /* access modifiers changed from: protected */
    public abstract void onCharacterChanged(String str, UUID uuid, UUID uuid2, byte[] bArr);

    public void onInvoke(Object... args) {
        onCharacterChanged(args[0], args[1], args[2], args[3]);
    }

    public String getName() {
        return BleCharacterChangeListener.class.getSimpleName();
    }
}
