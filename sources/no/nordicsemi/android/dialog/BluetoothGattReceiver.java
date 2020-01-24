package no.nordicsemi.android.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothGattReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d("BluetoothGattReceiver", "onReceive");
    }
}
