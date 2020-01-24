package com.iwown.device_module.common.utils;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.lib_common.log.L;

public class BLEInitTask implements Runnable {
    private Runnable mRunnable;

    private BLEInitTask(Runnable runnable) {
        this.mRunnable = runnable;
    }

    public static void postRunnable(Runnable runnable) {
        SingleThreadUtil.getExecutorService().execute(new BLEInitTask(runnable));
    }

    public void run() {
        try {
            if (!BluetoothOperation.isIBleNotNull()) {
                L.file("蓝牙未初始化", 4);
                synchronized (ContextUtil.getObject()) {
                    ContextUtil.getObject().wait();
                }
            }
            L.file("蓝牙初始化完成", 4);
            this.mRunnable.run();
        } catch (InterruptedException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
