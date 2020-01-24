package com.inuker.bluetooth.library.utils;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.inuker.bluetooth.library.BluetoothContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothUtils {
    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothManager mBluetoothManager;
    private static Handler mHandler;

    public static Context getContext() {
        return BluetoothContext.get();
    }

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public static void post(Runnable runnable) {
        getHandler().post(runnable);
    }

    public static void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        registerGlobalReceiver(receiver, filter);
    }

    private static void registerGlobalReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        getContext().registerReceiver(receiver, filter);
    }

    public static void unregisterReceiver(BroadcastReceiver receiver) {
        unregisterGlobalReceiver(receiver);
    }

    private static void unregisterGlobalReceiver(BroadcastReceiver receiver) {
        getContext().unregisterReceiver(receiver);
    }

    public static void sendBroadcast(Intent intent) {
        sendGlobalBroadcast(intent);
    }

    public static void sendBroadcast(String action) {
        sendGlobalBroadcast(new Intent(action));
    }

    private static void sendGlobalBroadcast(Intent intent) {
        getContext().sendBroadcast(intent);
    }

    public static boolean isBleSupported() {
        return VERSION.SDK_INT >= 18 && getContext() != null && getContext().getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public static boolean isBluetoothEnabled() {
        return getBluetoothState() == 12;
    }

    public static int getBluetoothState() {
        BluetoothAdapter adapter = getBluetoothAdapter();
        if (adapter != null) {
            return adapter.getState();
        }
        return 0;
    }

    public static boolean openBluetooth() {
        BluetoothAdapter adapter = getBluetoothAdapter();
        if (adapter != null) {
            return adapter.enable();
        }
        return false;
    }

    public static boolean closeBluetooth() {
        BluetoothAdapter adapter = getBluetoothAdapter();
        if (adapter != null) {
            return adapter.disable();
        }
        return false;
    }

    public static BluetoothManager getBluetoothManager() {
        if (!isBleSupported()) {
            return null;
        }
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getContext().getSystemService("bluetooth");
        }
        return mBluetoothManager;
    }

    public static BluetoothAdapter getBluetoothAdapter() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return mBluetoothAdapter;
    }

    public static BluetoothDevice getRemoteDevice(String mac) {
        if (!TextUtils.isEmpty(mac)) {
            BluetoothAdapter adapter = getBluetoothAdapter();
            if (adapter != null) {
                return adapter.getRemoteDevice(mac);
            }
        }
        return null;
    }

    @TargetApi(18)
    public static List<BluetoothDevice> getConnectedBluetoothLeDevices() {
        List<BluetoothDevice> devices = new ArrayList<>();
        BluetoothManager manager = getBluetoothManager();
        if (manager != null) {
            devices.addAll(manager.getConnectedDevices(7));
        }
        return devices;
    }

    @TargetApi(18)
    public static int getConnectStatus(String mac) {
        BluetoothManager manager = getBluetoothManager();
        if (manager != null) {
            try {
                return manager.getConnectionState(getRemoteDevice(mac), 7);
            } catch (Throwable e) {
                BluetoothLog.e(e);
            }
        }
        return -1;
    }

    public static List<BluetoothDevice> getBondedBluetoothClassicDevices() {
        BluetoothAdapter adapter = getBluetoothAdapter();
        List<BluetoothDevice> devices = new ArrayList<>();
        if (adapter != null) {
            Set<BluetoothDevice> sets = adapter.getBondedDevices();
            if (sets != null) {
                devices.addAll(sets);
            }
        }
        return devices;
    }

    @TargetApi(18)
    public static boolean isDeviceConnected(String mac) {
        if (TextUtils.isEmpty(mac) || !isBleSupported()) {
            return false;
        }
        if (getBluetoothManager().getConnectionState(getBluetoothAdapter().getRemoteDevice(mac), 7) == 2) {
            return true;
        }
        return false;
    }

    public static boolean checkMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean refreshGattCache(BluetoothGatt gatt) {
        boolean result = false;
        if (gatt != null) {
            try {
                Method refresh = BluetoothGatt.class.getMethod("refresh", new Class[0]);
                if (refresh != null) {
                    refresh.setAccessible(true);
                    result = ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue();
                }
            } catch (Exception e) {
                BluetoothLog.e((Throwable) e);
            }
        }
        BluetoothLog.v(String.format("refreshDeviceCache return %b", new Object[]{Boolean.valueOf(result)}));
        return result;
    }
}
