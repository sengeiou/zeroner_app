package com.inuker.bluetooth.library.receiver.listener;

public abstract class BluetoothBondStateChangeListener extends BluetoothReceiverListener {
    /* access modifiers changed from: protected */
    public abstract void onBondStateChanged(String str, int i);

    public void onInvoke(Object... args) {
        onBondStateChanged(args[0], args[1].intValue());
    }

    public String getName() {
        return BluetoothBondStateChangeListener.class.getSimpleName();
    }
}
