package com.inuker.bluetooth.library.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

public abstract class Task extends AsyncTask<Void, Void, Void> {
    private static Handler mHandler;

    public abstract void doInBackground();

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... params) {
        doInBackground();
        return null;
    }

    private static Handler getHandler() {
        if (mHandler == null) {
            synchronized (Task.class) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }

    public void executeDelayed(final Executor executor, long delayInMillis) {
        getHandler().postDelayed(new Runnable() {
            public void run() {
                Task.this.executeOnExecutor(executor != null ? executor : AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            }
        }, delayInMillis);
    }

    public void execute(final Executor executor) {
        getHandler().post(new Runnable() {
            public void run() {
                Task.this.executeOnExecutor(executor != null ? executor : AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            }
        });
    }

    public static void execute(Task task, Executor executor) {
        if (task != null) {
            task.execute(executor);
        }
    }

    public static void executeDelayed(Task task, Executor executor, long delayInMillis) {
        if (task != null) {
            task.executeDelayed(executor, delayInMillis);
        }
    }

    public static void executeDelayed(final FutureTask task, final Executor executor, long delayInMillis) {
        if (task != null && executor != null) {
            getHandler().postDelayed(new Runnable() {
                public void run() {
                    executor.execute(task);
                }
            }, delayInMillis);
        }
    }
}
