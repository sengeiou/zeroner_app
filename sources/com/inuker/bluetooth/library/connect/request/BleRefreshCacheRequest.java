package com.inuker.bluetooth.library.connect.request;

import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;

public class BleRefreshCacheRequest extends BleRequest {
    public BleRefreshCacheRequest(BleGeneralResponse response) {
        super(response);
    }

    public void processRequest() {
        refreshDeviceCache();
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                BleRefreshCacheRequest.this.onRequestCompleted(0);
            }
        }, 3000);
    }
}
