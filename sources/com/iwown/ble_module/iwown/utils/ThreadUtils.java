package com.iwown.ble_module.iwown.utils;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    private static final ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private static final Object mLock = new Object();
    /* access modifiers changed from: private */
    public static final HashMap<TimeTask, ScheduledFuture> mRunnableCache = new HashMap<>();

    public static abstract class TimeTask implements Runnable {
        /* access modifiers changed from: protected */
        public abstract void task();

        public void run() {
            task();
            ThreadUtils.mRunnableCache.remove(this);
        }
    }

    public static void postDelay(TimeTask runnable, long delay) {
        synchronized (mLock) {
            cancel(runnable);
            mRunnableCache.put(runnable, mExecutor.schedule(runnable, delay, TimeUnit.MILLISECONDS));
        }
    }

    public static void cancel(TimeTask runnable) {
        cancel(runnable, false);
    }

    public static void cancel(TimeTask runnable, boolean mayInterruptIfRunning) {
        synchronized (mLock) {
            ScheduledFuture future = (ScheduledFuture) mRunnableCache.get(runnable);
            if (future != null) {
                future.cancel(mayInterruptIfRunning);
                mRunnableCache.remove(runnable);
            }
        }
    }
}
