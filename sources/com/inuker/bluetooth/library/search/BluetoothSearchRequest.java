package com.inuker.bluetooth.library.search;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import java.util.ArrayList;
import java.util.List;

public class BluetoothSearchRequest implements Callback {
    private static final int MSG_DEVICE_FOUND = 18;
    private static final int MSG_START_SEARCH = 17;
    private static final int SCAN_INTERVAL = 100;
    private BluetoothSearchTask mCurrentTask;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private BluetoothSearchResponse mSearchResponse;
    private List<BluetoothSearchTask> mSearchTaskList = new ArrayList();

    private class BluetoothSearchTaskResponse implements BluetoothSearchResponse {
        BluetoothSearchTask task;

        BluetoothSearchTaskResponse(BluetoothSearchTask task2) {
            this.task = task2;
        }

        public void onSearchStarted() {
            BluetoothLog.v(String.format("%s onSearchStarted", new Object[]{this.task}));
        }

        public void onDeviceFounded(SearchResult device) {
            BluetoothLog.v(String.format("onDeviceFounded %s", new Object[]{device}));
            BluetoothSearchRequest.this.notifyDeviceFounded(device);
        }

        public void onSearchStopped() {
            BluetoothLog.v(String.format("%s onSearchStopped", new Object[]{this.task}));
            BluetoothSearchRequest.this.mHandler.sendEmptyMessageDelayed(17, 100);
        }

        public void onSearchCanceled() {
            BluetoothLog.v(String.format("%s onSearchCanceled", new Object[]{this.task}));
        }
    }

    public BluetoothSearchRequest(SearchRequest request) {
        for (SearchTask task : request.getTasks()) {
            this.mSearchTaskList.add(new BluetoothSearchTask(task));
        }
        this.mHandler = new Handler(Looper.myLooper(), this);
    }

    public void setSearchResponse(BluetoothSearchResponse response) {
        this.mSearchResponse = response;
    }

    public void start() {
        if (this.mSearchResponse != null) {
            this.mSearchResponse.onSearchStarted();
        }
        notifyConnectedBluetoothDevices();
        this.mHandler.sendEmptyMessageDelayed(17, 100);
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 17:
                scheduleNewSearchTask();
                break;
            case 18:
                SearchResult device = (SearchResult) msg.obj;
                if (this.mSearchResponse != null) {
                    this.mSearchResponse.onDeviceFounded(device);
                    break;
                }
                break;
        }
        return true;
    }

    private void scheduleNewSearchTask() {
        if (this.mSearchTaskList.size() > 0) {
            this.mCurrentTask = (BluetoothSearchTask) this.mSearchTaskList.remove(0);
            this.mCurrentTask.start(new BluetoothSearchTaskResponse(this.mCurrentTask));
            return;
        }
        this.mCurrentTask = null;
        if (this.mSearchResponse != null) {
            this.mSearchResponse.onSearchStopped();
        }
    }

    public void cancel() {
        if (this.mCurrentTask != null) {
            this.mCurrentTask.cancel();
            this.mCurrentTask = null;
        }
        this.mSearchTaskList.clear();
        if (this.mSearchResponse != null) {
            this.mSearchResponse.onSearchCanceled();
        }
        this.mSearchResponse = null;
    }

    private void notifyConnectedBluetoothDevices() {
        boolean hasBleTask = false;
        boolean hasBscTask = false;
        for (BluetoothSearchTask task : this.mSearchTaskList) {
            if (task.isBluetoothLeSearch()) {
                hasBleTask = true;
            } else if (task.isBluetoothClassicSearch()) {
                hasBscTask = true;
            } else {
                throw new IllegalArgumentException("unknown search task type!");
            }
        }
        if (hasBleTask) {
            notifyConnectedBluetoothLeDevices();
        }
        if (hasBscTask) {
            notifyBondedBluetoothClassicDevices();
        }
    }

    private void notifyConnectedBluetoothLeDevices() {
        for (BluetoothDevice device : BluetoothUtils.getConnectedBluetoothLeDevices()) {
            notifyDeviceFounded(new SearchResult(device));
        }
    }

    private void notifyBondedBluetoothClassicDevices() {
        for (BluetoothDevice device : BluetoothUtils.getBondedBluetoothClassicDevices()) {
            notifyDeviceFounded(new SearchResult(device));
        }
    }

    /* access modifiers changed from: private */
    public void notifyDeviceFounded(SearchResult device) {
        this.mHandler.obtainMessage(18, device).sendToTarget();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BluetoothSearchTask task : this.mSearchTaskList) {
            sb.append(task.toString() + ", ");
        }
        return sb.toString();
    }
}
