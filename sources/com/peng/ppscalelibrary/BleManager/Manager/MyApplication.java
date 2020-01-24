package com.peng.ppscalelibrary.BleManager.Manager;

import android.app.Application;
import com.inuker.bluetooth.library.BluetoothContext;

public class MyApplication extends Application {
    private static MyApplication instance;

    public void onCreate() {
        super.onCreate();
        instance = this;
        BluetoothContext.set(this);
    }

    public static Application getInstance() {
        return instance;
    }
}
