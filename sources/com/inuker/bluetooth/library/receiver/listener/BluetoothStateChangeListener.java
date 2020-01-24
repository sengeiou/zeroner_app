package com.inuker.bluetooth.library.receiver.listener;

import com.inuker.bluetooth.library.BluetoothClientImpl;

public abstract class BluetoothStateChangeListener extends BluetoothReceiverListener {
    /* access modifiers changed from: protected */
    public abstract void onBluetoothStateChanged(int i, int i2);

    public void onInvoke(Object... args) {
        int prevState = args[0].intValue();
        int curState = args[1].intValue();
        if (curState == 10 || curState == 13) {
            BluetoothClientImpl.getInstance(null).stopSearch();
        }
        onBluetoothStateChanged(prevState, curState);
    }

    public String getName() {
        return BluetoothStateChangeListener.class.getSimpleName();
    }
}
