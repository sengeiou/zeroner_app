package com.inuker.bluetooth.library.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.inuker.bluetooth.library.BluetoothContext;
import com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener;
import com.inuker.bluetooth.library.utils.ListUtils;
import java.util.Collections;
import java.util.List;

public abstract class AbsBluetoothReceiver {
    protected Context mContext = BluetoothContext.get();
    protected IReceiverDispatcher mDispatcher;
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    /* access modifiers changed from: 0000 */
    public abstract List<String> getActions();

    /* access modifiers changed from: 0000 */
    public abstract boolean onReceive(Context context, Intent intent);

    protected AbsBluetoothReceiver(IReceiverDispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    /* access modifiers changed from: 0000 */
    public boolean containsAction(String action) {
        List<String> actions = getActions();
        if (ListUtils.isEmpty(actions) || TextUtils.isEmpty(action)) {
            return false;
        }
        return actions.contains(action);
    }

    /* access modifiers changed from: protected */
    public List<BluetoothReceiverListener> getListeners(Class<?> clazz) {
        List<BluetoothReceiverListener> listeners = this.mDispatcher.getListeners(clazz);
        return listeners != null ? listeners : Collections.EMPTY_LIST;
    }
}
