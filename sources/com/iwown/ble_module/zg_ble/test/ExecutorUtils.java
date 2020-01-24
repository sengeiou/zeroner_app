package com.iwown.ble_module.zg_ble.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static ExecutorService getExecutorService() {
        return executor;
    }
}
