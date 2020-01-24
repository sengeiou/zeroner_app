package com.inuker.bluetooth.library.search;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;

public class BluetoothSearchTask implements Callback {
    private static final int MSG_SEARCH_TIMEOUT = 34;
    private BluetoothSearcher mBluetoothSearcher;
    private Handler mHandler = new Handler(Looper.myLooper(), this);
    private int searchDuration;
    private int searchType;

    public BluetoothSearchTask(SearchTask task) {
        setSearchType(task.getSearchType());
        setSearchDuration(task.getSearchDuration());
    }

    public void setSearchType(int searchType2) {
        this.searchType = searchType2;
    }

    public void setSearchDuration(int searchDuration2) {
        this.searchDuration = searchDuration2;
    }

    public boolean isBluetoothLeSearch() {
        return this.searchType == 2;
    }

    public boolean isBluetoothClassicSearch() {
        return this.searchType == 1;
    }

    private BluetoothSearcher getBluetoothSearcher() {
        if (this.mBluetoothSearcher == null) {
            this.mBluetoothSearcher = BluetoothSearcher.newInstance(this.searchType);
        }
        return this.mBluetoothSearcher;
    }

    public void start(BluetoothSearchResponse response) {
        getBluetoothSearcher().startScanBluetooth(response);
        this.mHandler.sendEmptyMessageDelayed(34, (long) this.searchDuration);
    }

    public void cancel() {
        this.mHandler.removeCallbacksAndMessages(null);
        getBluetoothSearcher().cancelScanBluetooth();
    }

    public String toString() {
        String type;
        String str = "";
        if (isBluetoothLeSearch()) {
            type = "Ble";
        } else if (isBluetoothClassicSearch()) {
            type = "classic";
        } else {
            type = "unknown";
        }
        if (this.searchDuration >= 1000) {
            return String.format("%s search (%ds)", new Object[]{type, Integer.valueOf(this.searchDuration / 1000)});
        }
        return String.format("%s search (%.1fs)", new Object[]{type, Double.valueOf((1.0d * ((double) this.searchDuration)) / 1000.0d)});
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 34:
                getBluetoothSearcher().stopScanBluetooth();
                break;
        }
        return true;
    }
}
