package com.inuker.bluetooth.library.connect.response;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.inuker.bluetooth.library.IResponse.Stub;

public abstract class BluetoothResponse extends Stub implements Callback {
    private static final int MSG_RESPONSE = 1;
    private Handler mHandler;

    /* access modifiers changed from: protected */
    public abstract void onAsyncResponse(int i, Bundle bundle);

    protected BluetoothResponse() {
        if (Looper.myLooper() == null) {
            throw new RuntimeException();
        }
        this.mHandler = new Handler(Looper.myLooper(), this);
    }

    public void onResponse(int code, Bundle data) throws RemoteException {
        this.mHandler.obtainMessage(1, code, 0, data).sendToTarget();
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                onAsyncResponse(msg.arg1, (Bundle) msg.obj);
                break;
        }
        return true;
    }
}
