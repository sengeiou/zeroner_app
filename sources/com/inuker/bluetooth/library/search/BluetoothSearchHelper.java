package com.inuker.bluetooth.library.search;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.inuker.bluetooth.library.utils.proxy.ProxyBulk;
import com.inuker.bluetooth.library.utils.proxy.ProxyInterceptor;
import com.inuker.bluetooth.library.utils.proxy.ProxyUtils;
import java.lang.reflect.Method;

public class BluetoothSearchHelper implements IBluetoothSearchHelper, ProxyInterceptor, Callback {
    private static IBluetoothSearchHelper sInstance;
    /* access modifiers changed from: private */
    public BluetoothSearchRequest mCurrentRequest;
    private Handler mHandler = new Handler(Looper.getMainLooper(), this);

    private class BluetoothSearchResponseImpl implements BluetoothSearchResponse {
        BluetoothSearchResponse response;

        BluetoothSearchResponseImpl(BluetoothSearchResponse response2) {
            this.response = response2;
        }

        public void onSearchStarted() {
            this.response.onSearchStarted();
        }

        public void onDeviceFounded(SearchResult device) {
            this.response.onDeviceFounded(device);
        }

        public void onSearchStopped() {
            this.response.onSearchStopped();
            BluetoothSearchHelper.this.mCurrentRequest = null;
        }

        public void onSearchCanceled() {
            this.response.onSearchCanceled();
            BluetoothSearchHelper.this.mCurrentRequest = null;
        }
    }

    private BluetoothSearchHelper() {
    }

    public static IBluetoothSearchHelper getInstance() {
        if (sInstance == null) {
            synchronized (BluetoothSearchHelper.class) {
                if (sInstance == null) {
                    BluetoothSearchHelper helper = new BluetoothSearchHelper();
                    sInstance = (IBluetoothSearchHelper) ProxyUtils.getProxy(helper, IBluetoothSearchHelper.class, helper);
                }
            }
        }
        return sInstance;
    }

    public void startSearch(BluetoothSearchRequest request, BluetoothSearchResponse response) {
        request.setSearchResponse(new BluetoothSearchResponseImpl(response));
        if (!BluetoothUtils.isBluetoothEnabled()) {
            request.cancel();
            return;
        }
        stopSearch();
        if (this.mCurrentRequest == null) {
            this.mCurrentRequest = request;
            this.mCurrentRequest.start();
        }
    }

    public void stopSearch() {
        if (this.mCurrentRequest != null) {
            this.mCurrentRequest.cancel();
            this.mCurrentRequest = null;
        }
    }

    public boolean onIntercept(Object object, Method method, Object[] args) {
        this.mHandler.obtainMessage(0, new ProxyBulk(object, method, args)).sendToTarget();
        return true;
    }

    public boolean handleMessage(Message msg) {
        ProxyBulk.safeInvoke(msg.obj);
        return true;
    }
}
