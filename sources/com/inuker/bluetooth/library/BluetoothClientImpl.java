package com.inuker.bluetooth.library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.inuker.bluetooth.library.IBluetoothService.Stub;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleReadResponse;
import com.inuker.bluetooth.library.connect.response.BleReadRssiResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.connect.response.BluetoothResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.receiver.BluetoothReceiver;
import com.inuker.bluetooth.library.receiver.listener.BleCharacterChangeListener;
import com.inuker.bluetooth.library.receiver.listener.BleConnectStatusChangeListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondStateChangeListener;
import com.inuker.bluetooth.library.receiver.listener.BluetoothStateChangeListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ListUtils;
import com.inuker.bluetooth.library.utils.proxy.ProxyBulk;
import com.inuker.bluetooth.library.utils.proxy.ProxyInterceptor;
import com.inuker.bluetooth.library.utils.proxy.ProxyUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class BluetoothClientImpl implements IBluetoothClient, ProxyInterceptor, Callback {
    private static final int MSG_INVOKE_PROXY = 1;
    private static final int MSG_REG_RECEIVER = 2;
    private static final String TAG = BluetoothClientImpl.class.getSimpleName();
    private static IBluetoothClient sInstance;
    private List<BluetoothBondListener> mBluetoothBondListeners;
    /* access modifiers changed from: private */
    public IBluetoothService mBluetoothService;
    private List<BluetoothStateListener> mBluetoothStateListeners;
    private HashMap<String, List<BleConnectStatusListener>> mConnectStatusListeners;
    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            BluetoothClientImpl.this.mBluetoothService = Stub.asInterface(service);
            BluetoothClientImpl.this.notifyBluetoothManagerReady();
        }

        public void onServiceDisconnected(ComponentName name) {
            BluetoothClientImpl.this.mBluetoothService = null;
        }
    };
    private Context mContext;
    private CountDownLatch mCountDownLatch;
    private HashMap<String, HashMap<String, List<BleNotifyResponse>>> mNotifyResponses;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread;

    private BluetoothClientImpl(Context context) {
        this.mContext = context.getApplicationContext();
        BluetoothContext.set(this.mContext);
        this.mWorkerThread = new HandlerThread(TAG);
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper(), this);
        this.mNotifyResponses = new HashMap<>();
        this.mConnectStatusListeners = new HashMap<>();
        this.mBluetoothStateListeners = new LinkedList();
        this.mBluetoothBondListeners = new LinkedList();
        this.mWorkerHandler.obtainMessage(2).sendToTarget();
    }

    public static IBluetoothClient getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BluetoothClientImpl.class) {
                if (sInstance == null) {
                    BluetoothClientImpl client = new BluetoothClientImpl(context);
                    sInstance = (IBluetoothClient) ProxyUtils.getProxy(client, IBluetoothClient.class, client);
                }
            }
        }
        return sInstance;
    }

    private IBluetoothService getBluetoothService() {
        if (this.mBluetoothService == null) {
            bindServiceSync();
        }
        return this.mBluetoothService;
    }

    private void bindServiceSync() {
        checkRuntime(true);
        this.mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent();
        intent.setClass(this.mContext, BluetoothService.class);
        if (this.mContext.bindService(intent, this.mConnection, 1)) {
            BluetoothLog.v(String.format("BluetoothService registered", new Object[0]));
            waitBluetoothManagerReady();
            return;
        }
        BluetoothLog.v(String.format("BluetoothService not registered", new Object[0]));
        this.mBluetoothService = BluetoothServiceImpl.getInstance();
    }

    public void connect(String mac, BleConnectOptions options, final BleConnectResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putParcelable(Constants.EXTRA_OPTIONS, options);
        safeCallBluetoothApi(1, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    data.setClassLoader(getClass().getClassLoader());
                    response.onResponse(code, (BleGattProfile) data.getParcelable(Constants.EXTRA_GATT_PROFILE));
                }
            }
        });
    }

    public void disconnect(String mac) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        safeCallBluetoothApi(2, args, null);
        clearNotifyListener(mac);
    }

    public void registerConnectStatusListener(String mac, BleConnectStatusListener listener) {
        checkRuntime(true);
        List<BleConnectStatusListener> listeners = (List) this.mConnectStatusListeners.get(mac);
        if (listeners == null) {
            listeners = new ArrayList<>();
            this.mConnectStatusListeners.put(mac, listeners);
        }
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unregisterConnectStatusListener(String mac, BleConnectStatusListener listener) {
        checkRuntime(true);
        List<BleConnectStatusListener> listeners = (List) this.mConnectStatusListeners.get(mac);
        if (listener != null && !ListUtils.isEmpty(listeners)) {
            listeners.remove(listener);
        }
    }

    public void read(String mac, UUID service, UUID character, final BleReadResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        safeCallBluetoothApi(3, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    response.onResponse(code, data.getByteArray(Constants.EXTRA_BYTE_VALUE));
                }
            }
        });
    }

    public void write(String mac, UUID service, UUID character, byte[] value, final BleWriteResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        args.putByteArray(Constants.EXTRA_BYTE_VALUE, value);
        safeCallBluetoothApi(4, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    response.onResponse(code);
                }
            }
        });
    }

    public void readDescriptor(String mac, UUID service, UUID character, UUID descriptor, final BleReadResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        args.putSerializable(Constants.EXTRA_DESCRIPTOR_UUID, descriptor);
        safeCallBluetoothApi(13, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    response.onResponse(code, data.getByteArray(Constants.EXTRA_BYTE_VALUE));
                }
            }
        });
    }

    public void writeDescriptor(String mac, UUID service, UUID character, UUID descriptor, byte[] value, final BleWriteResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        args.putSerializable(Constants.EXTRA_DESCRIPTOR_UUID, descriptor);
        args.putByteArray(Constants.EXTRA_BYTE_VALUE, value);
        safeCallBluetoothApi(14, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    response.onResponse(code);
                }
            }
        });
    }

    public void writeNoRsp(String mac, UUID service, UUID character, byte[] value, final BleWriteResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        args.putByteArray(Constants.EXTRA_BYTE_VALUE, value);
        safeCallBluetoothApi(5, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    response.onResponse(code);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveNotifyListener(String mac, UUID service, UUID character, BleNotifyResponse response) {
        checkRuntime(true);
        HashMap<String, List<BleNotifyResponse>> listenerMap = (HashMap) this.mNotifyResponses.get(mac);
        if (listenerMap == null) {
            listenerMap = new HashMap<>();
            this.mNotifyResponses.put(mac, listenerMap);
        }
        String key = generateCharacterKey(service, character);
        List<BleNotifyResponse> responses = (List) listenerMap.get(key);
        if (responses == null) {
            responses = new ArrayList<>();
            listenerMap.put(key, responses);
        }
        responses.add(response);
    }

    /* access modifiers changed from: private */
    public void removeNotifyListener(String mac, UUID service, UUID character) {
        checkRuntime(true);
        HashMap<String, List<BleNotifyResponse>> listenerMap = (HashMap) this.mNotifyResponses.get(mac);
        if (listenerMap != null) {
            listenerMap.remove(generateCharacterKey(service, character));
        }
    }

    /* access modifiers changed from: private */
    public void clearNotifyListener(String mac) {
        checkRuntime(true);
        this.mNotifyResponses.remove(mac);
    }

    private String generateCharacterKey(UUID service, UUID character) {
        return String.format("%s_%s", new Object[]{service, character});
    }

    public void notify(String mac, UUID service, UUID character, BleNotifyResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        final BleNotifyResponse bleNotifyResponse = response;
        final String str = mac;
        final UUID uuid = service;
        final UUID uuid2 = character;
        safeCallBluetoothApi(6, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (bleNotifyResponse != null) {
                    if (code == 0) {
                        BluetoothClientImpl.this.saveNotifyListener(str, uuid, uuid2, bleNotifyResponse);
                    }
                    bleNotifyResponse.onResponse(code);
                }
            }
        });
    }

    public void unnotify(String mac, UUID service, UUID character, BleUnnotifyResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        final String str = mac;
        final UUID uuid = service;
        final UUID uuid2 = character;
        final BleUnnotifyResponse bleUnnotifyResponse = response;
        safeCallBluetoothApi(7, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                BluetoothClientImpl.this.removeNotifyListener(str, uuid, uuid2);
                if (bleUnnotifyResponse != null) {
                    bleUnnotifyResponse.onResponse(code);
                }
            }
        });
    }

    public void indicate(String mac, UUID service, UUID character, BleNotifyResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putSerializable(Constants.EXTRA_SERVICE_UUID, service);
        args.putSerializable(Constants.EXTRA_CHARACTER_UUID, character);
        final BleNotifyResponse bleNotifyResponse = response;
        final String str = mac;
        final UUID uuid = service;
        final UUID uuid2 = character;
        safeCallBluetoothApi(10, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (bleNotifyResponse != null) {
                    if (code == 0) {
                        BluetoothClientImpl.this.saveNotifyListener(str, uuid, uuid2, bleNotifyResponse);
                    }
                    bleNotifyResponse.onResponse(code);
                }
            }
        });
    }

    public void unindicate(String mac, UUID service, UUID character, BleUnnotifyResponse response) {
        unnotify(mac, service, character, response);
    }

    public void readRssi(String mac, final BleReadRssiResponse response) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        safeCallBluetoothApi(8, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    response.onResponse(code, Integer.valueOf(data.getInt(Constants.EXTRA_RSSI, 0)));
                }
            }
        });
    }

    public void search(SearchRequest request, final SearchResponse response) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_REQUEST, request);
        safeCallBluetoothApi(11, args, new BluetoothResponse() {
            /* access modifiers changed from: protected */
            public void onAsyncResponse(int code, Bundle data) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (response != null) {
                    data.setClassLoader(getClass().getClassLoader());
                    switch (code) {
                        case 1:
                            response.onSearchStarted();
                            return;
                        case 2:
                            response.onSearchStopped();
                            return;
                        case 3:
                            response.onSearchCanceled();
                            return;
                        case 4:
                            response.onDeviceFounded((SearchResult) data.getParcelable(Constants.EXTRA_SEARCH_RESULT));
                            return;
                        default:
                            throw new IllegalStateException("unknown code");
                    }
                }
            }
        });
    }

    public void stopSearch() {
        safeCallBluetoothApi(12, null, null);
    }

    public void registerBluetoothStateListener(BluetoothStateListener listener) {
        checkRuntime(true);
        if (listener != null && !this.mBluetoothStateListeners.contains(listener)) {
            this.mBluetoothStateListeners.add(listener);
        }
    }

    public void unregisterBluetoothStateListener(BluetoothStateListener listener) {
        checkRuntime(true);
        if (listener != null) {
            this.mBluetoothStateListeners.remove(listener);
        }
    }

    public void registerBluetoothBondListener(BluetoothBondListener listener) {
        checkRuntime(true);
        if (listener != null && !this.mBluetoothBondListeners.contains(listener)) {
            this.mBluetoothBondListeners.add(listener);
        }
    }

    public void unregisterBluetoothBondListener(BluetoothBondListener listener) {
        checkRuntime(true);
        if (listener != null) {
            this.mBluetoothBondListeners.remove(listener);
        }
    }

    public void clearRequest(String mac, int type) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        args.putInt(Constants.EXTRA_TYPE, type);
        safeCallBluetoothApi(20, args, null);
    }

    public void refreshCache(String mac) {
        checkRuntime(true);
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_MAC, mac);
        safeCallBluetoothApi(21, args, null);
    }

    private void safeCallBluetoothApi(int code, Bundle args, BluetoothResponse response) {
        checkRuntime(true);
        try {
            IBluetoothService service = getBluetoothService();
            if (service != null) {
                if (args == null) {
                    args = new Bundle();
                }
                service.callBluetoothApi(code, args, response);
                return;
            }
            response.onResponse(-6, null);
        } catch (Throwable e) {
            BluetoothLog.e(e);
        }
    }

    public boolean onIntercept(Object object, Method method, Object[] args) {
        this.mWorkerHandler.obtainMessage(1, new ProxyBulk(object, method, args)).sendToTarget();
        return true;
    }

    /* access modifiers changed from: private */
    public void notifyBluetoothManagerReady() {
        if (this.mCountDownLatch != null) {
            this.mCountDownLatch.countDown();
            this.mCountDownLatch = null;
        }
    }

    private void waitBluetoothManagerReady() {
        try {
            this.mCountDownLatch.await();
        } catch (InterruptedException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                ProxyBulk.safeInvoke(msg.obj);
                break;
            case 2:
                registerBluetoothReceiver();
                break;
        }
        return true;
    }

    private void registerBluetoothReceiver() {
        checkRuntime(true);
        BluetoothReceiver.getInstance().register(new BluetoothStateChangeListener() {
            /* access modifiers changed from: protected */
            public void onBluetoothStateChanged(int prevState, int curState) {
                BluetoothClientImpl.this.checkRuntime(true);
                BluetoothClientImpl.this.dispatchBluetoothStateChanged(curState);
            }
        });
        BluetoothReceiver.getInstance().register(new BluetoothBondStateChangeListener() {
            /* access modifiers changed from: protected */
            public void onBondStateChanged(String mac, int bondState) {
                BluetoothClientImpl.this.checkRuntime(true);
                BluetoothClientImpl.this.dispatchBondStateChanged(mac, bondState);
            }
        });
        BluetoothReceiver.getInstance().register(new BleConnectStatusChangeListener() {
            /* access modifiers changed from: protected */
            public void onConnectStatusChanged(String mac, int status) {
                BluetoothClientImpl.this.checkRuntime(true);
                if (status == 32) {
                    BluetoothClientImpl.this.clearNotifyListener(mac);
                }
                BluetoothClientImpl.this.dispatchConnectionStatus(mac, status);
            }
        });
        BluetoothReceiver.getInstance().register(new BleCharacterChangeListener() {
            public void onCharacterChanged(String mac, UUID service, UUID character, byte[] value) {
                BluetoothClientImpl.this.checkRuntime(true);
                BluetoothClientImpl.this.dispatchCharacterNotify(mac, service, character, value);
            }
        });
    }

    /* access modifiers changed from: private */
    public void dispatchCharacterNotify(String mac, UUID service, UUID character, byte[] value) {
        checkRuntime(true);
        HashMap<String, List<BleNotifyResponse>> notifyMap = (HashMap) this.mNotifyResponses.get(mac);
        if (notifyMap != null) {
            List<BleNotifyResponse> responses = (List) notifyMap.get(generateCharacterKey(service, character));
            if (responses != null) {
                for (BleNotifyResponse response : responses) {
                    response.onNotify(service, character, value);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchConnectionStatus(String mac, int status) {
        checkRuntime(true);
        List<BleConnectStatusListener> listeners = (List) this.mConnectStatusListeners.get(mac);
        if (!ListUtils.isEmpty(listeners)) {
            for (BleConnectStatusListener listener : listeners) {
                listener.invokeSync(mac, Integer.valueOf(status));
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchBluetoothStateChanged(int currentState) {
        checkRuntime(true);
        if (currentState == 10 || currentState == 12) {
            for (BluetoothStateListener listener : this.mBluetoothStateListeners) {
                Object[] objArr = new Object[1];
                objArr[0] = Boolean.valueOf(currentState == 12);
                listener.invokeSync(objArr);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchBondStateChanged(String mac, int bondState) {
        checkRuntime(true);
        for (BluetoothBondListener listener : this.mBluetoothBondListeners) {
            listener.invokeSync(mac, Integer.valueOf(bondState));
        }
    }

    /* access modifiers changed from: private */
    public void checkRuntime(boolean async) {
        if (Looper.myLooper() != (async ? this.mWorkerHandler.getLooper() : Looper.getMainLooper())) {
            throw new RuntimeException();
        }
    }
}
