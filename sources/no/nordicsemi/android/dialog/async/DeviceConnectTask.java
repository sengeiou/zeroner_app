package no.nordicsemi.android.dialog.async;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Method;
import no.nordicsemi.android.dialog.Callback;
import no.nordicsemi.android.dialog.SuotaManager;
import no.nordicsemi.android.dialog.data.Statics;

public class DeviceConnectTask extends AsyncTask<Void, BluetoothGatt, Boolean> {
    public static final String TAG = "DeviceGattTask";
    private Callback callback;
    public Context context;
    private final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;

    public DeviceConnectTask(Context context2, BluetoothDevice device, SuotaManager suotaManager) {
        Log.d(TAG, "init");
        this.context = context2;
        this.callback = new Callback(context2, suotaManager, this);
        BluetoothSocket tmp = null;
        this.mmDevice = device;
        try {
            tmp = device.createRfcommSocketToServiceRecord(Statics.SPOTA_MEM_DEV_UUID);
        } catch (IOException e) {
        }
        this.mmSocket = tmp;
    }

    public void cancel() {
        try {
            this.mmSocket.close();
        } catch (IOException e) {
        }
    }

    private boolean refreshDeviceCache(BluetoothGatt gatt) {
        BluetoothGatt localBluetoothGatt = gatt;
        try {
            Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (localMethod != null) {
                return ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "An exception occured while refreshing device");
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground(Void... params) {
        BluetoothGatt gatt = this.mmDevice.connectGatt(this.context, false, this.callback);
        refreshDeviceCache(gatt);
        if (gatt != null) {
            gatt.connect();
        }
        return Boolean.valueOf(true);
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(BluetoothGatt... values) {
        super.onProgressUpdate(values);
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
        cancel();
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        super.onCancelled();
        cancel();
    }

    public void publishProgess(BluetoothGatt gatt) {
        publishProgress(new BluetoothGatt[]{gatt});
    }
}
