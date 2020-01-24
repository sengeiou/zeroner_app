package com.inuker.bluetooth.library.receiver;

import android.content.Context;
import android.content.Intent;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.receiver.listener.BleConnectStatusChangeListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import java.util.Arrays;
import java.util.List;

public class BleConnectStatusChangeReceiver extends AbsBluetoothReceiver {
    private static final String[] ACTIONS = {Constants.ACTION_CONNECT_STATUS_CHANGED};

    protected BleConnectStatusChangeReceiver(IReceiverDispatcher dispatcher) {
        super(dispatcher);
    }

    public static BleConnectStatusChangeReceiver newInstance(IReceiverDispatcher dispatcher) {
        return new BleConnectStatusChangeReceiver(dispatcher);
    }

    /* access modifiers changed from: 0000 */
    public List<String> getActions() {
        return Arrays.asList(ACTIONS);
    }

    /* access modifiers changed from: 0000 */
    public boolean onReceive(Context context, Intent intent) {
        String mac = intent.getStringExtra(Constants.EXTRA_MAC);
        int status = intent.getIntExtra(Constants.EXTRA_STATUS, 0);
        BluetoothLog.v(String.format("onConnectStatusChanged for %s, status = %d", new Object[]{mac, Integer.valueOf(status)}));
        onConnectStatusChanged(mac, status);
        return true;
    }

    private void onConnectStatusChanged(String mac, int status) {
        for (BluetoothReceiverListener listener : getListeners(BleConnectStatusChangeListener.class)) {
            listener.invoke(mac, Integer.valueOf(status));
        }
    }
}
