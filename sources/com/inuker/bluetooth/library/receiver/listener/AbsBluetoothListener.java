package com.inuker.bluetooth.library.receiver.listener;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

public abstract class AbsBluetoothListener implements Callback {
    private static final int MSG_INVOKE = 1;
    private static final int MSG_SYNC_INVOKE = 2;
    private Handler mHandler;
    private Handler mSyncHandler;

    public abstract void onInvoke(Object... objArr);

    public abstract void onSyncInvoke(Object... objArr);

    public AbsBluetoothListener() {
        if (Looper.myLooper() == null) {
            throw new IllegalStateException();
        }
        this.mHandler = new Handler(Looper.myLooper(), this);
        this.mSyncHandler = new Handler(Looper.getMainLooper(), this);
    }

    public boolean handleMessage(Message msg) {
        Object[] args = (Object[]) msg.obj;
        switch (msg.what) {
            case 1:
                onInvoke(args);
                break;
            case 2:
                onSyncInvoke(args);
                break;
        }
        return true;
    }

    public final void invoke(Object... args) {
        this.mHandler.obtainMessage(1, args).sendToTarget();
    }

    public final void invokeSync(Object... args) {
        this.mSyncHandler.obtainMessage(2, args).sendToTarget();
    }
}
