package com.inuker.bluetooth.library.receiver.listener;

public abstract class BleConnectStatusChangeListener extends BluetoothReceiverListener {
    /* access modifiers changed from: protected */
    public abstract void onConnectStatusChanged(String str, int i);

    public void onInvoke(Object... args) {
        onConnectStatusChanged(args[0], args[1].intValue());
    }

    public String getName() {
        return BleConnectStatusChangeListener.class.getSimpleName();
    }
}
