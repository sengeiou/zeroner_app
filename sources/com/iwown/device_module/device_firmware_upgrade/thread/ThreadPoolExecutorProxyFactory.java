package com.iwown.device_module.device_firmware_upgrade.thread;

import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class ThreadPoolExecutorProxyFactory {
    static ThreadPoolExecutorProxy mDownLoadThreadPoolExecutorProxy;
    static ThreadPoolExecutorProxy mNormalThreadPoolExecutorProxy;

    public static ThreadPoolExecutorProxy getThreadPoolExecutorProxy() {
        if (mNormalThreadPoolExecutorProxy == null) {
            synchronized (ThreadPoolExecutorProxyFactory.class) {
                if (mNormalThreadPoolExecutorProxy == null) {
                    mNormalThreadPoolExecutorProxy = new ThreadPoolExecutorProxy(5, 5, BootloaderScanner.TIMEOUT);
                }
            }
        }
        return mNormalThreadPoolExecutorProxy;
    }

    public static ThreadPoolExecutorProxy getDownLoadThreadPoolExecutorProxy() {
        if (mDownLoadThreadPoolExecutorProxy == null) {
            synchronized (ThreadPoolExecutorProxyFactory.class) {
                if (mDownLoadThreadPoolExecutorProxy == null) {
                    mDownLoadThreadPoolExecutorProxy = new ThreadPoolExecutorProxy(3, 3, BootloaderScanner.TIMEOUT);
                }
            }
        }
        return mDownLoadThreadPoolExecutorProxy;
    }
}
