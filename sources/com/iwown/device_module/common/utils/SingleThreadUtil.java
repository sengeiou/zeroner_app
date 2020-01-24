package com.iwown.device_module.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadUtil {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final ExecutorService executor2 = Executors.newSingleThreadExecutor();

    public static ExecutorService getLogSingleThread() {
        return executor;
    }

    public static ExecutorService getExecutorService() {
        return executor2;
    }
}
