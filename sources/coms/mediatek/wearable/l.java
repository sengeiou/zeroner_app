package coms.mediatek.wearable;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import coms.mediatek.wearable.leprofiles.LeServerManager;

class l extends BroadcastReceiver {
    private Context a;
    private a b;

    l() {
    }

    public void a() {
        try {
            Log.d("[wearable]WearableReceiver", "[unregister] begin");
            this.a.unregisterReceiver(this);
        } catch (Exception e) {
        }
    }

    public void a(Context context, a aVar) {
        Log.d("[wearable]WearableReceiver", "[register] begin");
        this.a = context;
        this.b = aVar;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        if (WearableConfig.h()) {
            Log.d("[wearable]WearableReceiver", "register, support a2dp/hfp auto reconnect");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        }
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        context.registerReceiver(this, intentFilter, null, null);
    }

    public void onReceive(Context context, Intent intent) {
        Log.d("[wearable]WearableReceiver", "[onReceive] intent=" + intent.toString());
        String action = intent.getAction();
        if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            Log.d("[wearable]WearableReceiver", "[onReceive] connection state=" + intExtra);
            if (intExtra == 12) {
                if ((this.b instanceof d) && VERSION.SDK_INT >= 18) {
                    LeServerManager.onBluetoothStatusChange(true);
                }
                this.b.a(true);
            } else if (intExtra == 10) {
                if ((this.b instanceof d) && VERSION.SDK_INT >= 18) {
                    LeServerManager.onBluetoothStatusChange(false);
                }
                this.b.a(false);
            }
        } else if ("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED".equals(action) || "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
            int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Log.d("[wearable]WearableReceiver", "[onReceive] profile connect=" + intExtra2 + " DeviceName=" + bluetoothDevice.getName());
            if (intExtra2 == 1 || intExtra2 == 2) {
                this.b.a(bluetoothDevice, intExtra2);
            }
        } else if ("android.bluetooth.device.action.FOUND".equals(action)) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            if (bluetoothDevice2 == null) {
                return;
            }
            if (VERSION.SDK_INT < 18) {
                Log.d("[wearable]WearableReceiver", "[onReceive] android.os.Build.VERSION.SDK_INT<18 " + bluetoothDevice2.getName());
                this.b.a(bluetoothDevice2);
                return;
            }
            Log.d("[wearable]WearableReceiver", "[onReceive] found BluetoothDevice=" + bluetoothDevice2.getAddress() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + bluetoothDevice2.getName() + " type=" + bluetoothDevice2.getType());
            if (bluetoothDevice2.getType() != 2) {
                this.b.a(bluetoothDevice2);
            }
        } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
            Log.d("[wearable]WearableReceiver", "[onScanDevice] found finish");
        }
    }
}
