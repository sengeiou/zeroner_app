package com.inuker.bluetooth.library.receiver;

import android.content.Context;
import android.content.Intent;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.receiver.listener.BleCharacterChangeListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BleCharacterChangeReceiver extends AbsBluetoothReceiver {
    private static final String[] ACTIONS = {Constants.ACTION_CHARACTER_CHANGED};

    protected BleCharacterChangeReceiver(IReceiverDispatcher dispatcher) {
        super(dispatcher);
    }

    public static BleCharacterChangeReceiver newInstance(IReceiverDispatcher dispatcher) {
        return new BleCharacterChangeReceiver(dispatcher);
    }

    /* access modifiers changed from: 0000 */
    public List<String> getActions() {
        return Arrays.asList(ACTIONS);
    }

    /* access modifiers changed from: 0000 */
    public boolean onReceive(Context context, Intent intent) {
        onCharacterChanged(intent.getStringExtra(Constants.EXTRA_MAC), (UUID) intent.getSerializableExtra(Constants.EXTRA_SERVICE_UUID), (UUID) intent.getSerializableExtra(Constants.EXTRA_CHARACTER_UUID), intent.getByteArrayExtra(Constants.EXTRA_BYTE_VALUE));
        return true;
    }

    private void onCharacterChanged(String mac, UUID service, UUID character, byte[] value) {
        for (BluetoothReceiverListener listener : getListeners(BleCharacterChangeListener.class)) {
            listener.invoke(mac, service, character, value);
        }
    }
}
