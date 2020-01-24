package com.inuker.bluetooth.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BluetoothReceiver extends BroadcastReceiver implements IBluetoothReceiver, Callback {
    private static final int MSG_REGISTER = 1;
    private static IBluetoothReceiver mReceiver;
    private AbsBluetoothReceiver[] RECEIVERS = {BluetoothStateReceiver.newInstance(this.mDispatcher), BluetoothBondReceiver.newInstance(this.mDispatcher), BleConnectStatusChangeReceiver.newInstance(this.mDispatcher), BleCharacterChangeReceiver.newInstance(this.mDispatcher)};
    private IReceiverDispatcher mDispatcher = new IReceiverDispatcher() {
        public List<BluetoothReceiverListener> getListeners(Class<?> clazz) {
            return (List) BluetoothReceiver.this.mListeners.get(clazz.getSimpleName());
        }
    };
    private Handler mHandler = new Handler(Looper.getMainLooper(), this);
    /* access modifiers changed from: private */
    public Map<String, List<BluetoothReceiverListener>> mListeners = new HashMap();

    public static IBluetoothReceiver getInstance() {
        if (mReceiver == null) {
            synchronized (BluetoothReceiver.class) {
                if (mReceiver == null) {
                    mReceiver = new BluetoothReceiver();
                }
            }
        }
        return mReceiver;
    }

    private BluetoothReceiver() {
        BluetoothUtils.registerReceiver(this, getIntentFilter());
    }

    private IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        for (AbsBluetoothReceiver receiver : this.RECEIVERS) {
            for (String action : receiver.getActions()) {
                filter.addAction(action);
            }
        }
        return filter;
    }

    public void onReceive(Context context, Intent intent) {
        int i = 0;
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                BluetoothLog.v(String.format("BluetoothReceiver onReceive: %s", new Object[]{action}));
                AbsBluetoothReceiver[] absBluetoothReceiverArr = this.RECEIVERS;
                int length = absBluetoothReceiverArr.length;
                while (i < length) {
                    AbsBluetoothReceiver receiver = absBluetoothReceiverArr[i];
                    if (!receiver.containsAction(action) || !receiver.onReceive(context, intent)) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void register(BluetoothReceiverListener listener) {
        this.mHandler.obtainMessage(1, listener).sendToTarget();
    }

    private void registerInner(BluetoothReceiverListener listener) {
        if (listener != null) {
            List<BluetoothReceiverListener> listeners = (List) this.mListeners.get(listener.getName());
            if (listeners == null) {
                listeners = new LinkedList<>();
                this.mListeners.put(listener.getName(), listeners);
            }
            listeners.add(listener);
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                registerInner((BluetoothReceiverListener) msg.obj);
                break;
        }
        return true;
    }
}
