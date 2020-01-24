package com.inuker.bluetooth.library.receiver.listener;

public abstract class BluetoothBondListener extends BluetoothClientListener {
    public abstract void onBondStateChanged(String str, int i);

    public void onSyncInvoke(Object... args) {
        onBondStateChanged(args[0], args[1].intValue());
    }
}
