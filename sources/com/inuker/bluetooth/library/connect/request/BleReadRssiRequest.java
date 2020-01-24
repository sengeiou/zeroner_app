package com.inuker.bluetooth.library.connect.request;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.ReadRssiListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;

public class BleReadRssiRequest extends BleRequest implements ReadRssiListener {
    public BleReadRssiRequest(BleGeneralResponse response) {
        super(response);
    }

    public void processRequest() {
        switch (getCurrentStatus()) {
            case 0:
                onRequestCompleted(-1);
                return;
            case 2:
                startReadRssi();
                return;
            case 19:
                startReadRssi();
                return;
            default:
                onRequestCompleted(-1);
                return;
        }
    }

    private void startReadRssi() {
        if (!readRemoteRssi()) {
            onRequestCompleted(-1);
        } else {
            startRequestTiming();
        }
    }

    public void onReadRemoteRssi(int rssi, int status) {
        stopRequestTiming();
        if (status == 0) {
            putIntExtra(Constants.EXTRA_RSSI, rssi);
            onRequestCompleted(0);
            return;
        }
        onRequestCompleted(-1);
    }
}
