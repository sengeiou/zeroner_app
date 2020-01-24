package com.inuker.bluetooth.library.connect;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.inuker.bluetooth.library.RuntimeChecker;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.request.BleConnectRequest;
import com.inuker.bluetooth.library.connect.request.BleIndicateRequest;
import com.inuker.bluetooth.library.connect.request.BleNotifyRequest;
import com.inuker.bluetooth.library.connect.request.BleReadDescriptorRequest;
import com.inuker.bluetooth.library.connect.request.BleReadRequest;
import com.inuker.bluetooth.library.connect.request.BleReadRssiRequest;
import com.inuker.bluetooth.library.connect.request.BleRefreshCacheRequest;
import com.inuker.bluetooth.library.connect.request.BleRequest;
import com.inuker.bluetooth.library.connect.request.BleUnnotifyRequest;
import com.inuker.bluetooth.library.connect.request.BleWriteDescriptorRequest;
import com.inuker.bluetooth.library.connect.request.BleWriteNoRspRequest;
import com.inuker.bluetooth.library.connect.request.BleWriteRequest;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ListUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class BleConnectDispatcher implements IBleConnectDispatcher, RuntimeChecker, Callback {
    private static final int MAX_REQUEST_COUNT = 100;
    private static final int MSG_SCHEDULE_NEXT = 18;
    private String mAddress;
    private List<BleRequest> mBleWorkList = new LinkedList();
    private BleRequest mCurrentRequest;
    private Handler mHandler;
    private IBleConnectWorker mWorker;

    public static BleConnectDispatcher newInstance(String mac) {
        return new BleConnectDispatcher(mac);
    }

    private BleConnectDispatcher(String mac) {
        this.mAddress = mac;
        this.mWorker = new BleConnectWorker(mac, this);
        this.mHandler = new Handler(Looper.myLooper(), this);
    }

    public void connect(BleConnectOptions options, BleGeneralResponse response) {
        addNewRequest(new BleConnectRequest(options, response));
    }

    public void disconnect() {
        checkRuntime();
        BluetoothLog.w(String.format("Process disconnect", new Object[0]));
        if (this.mCurrentRequest != null) {
            this.mCurrentRequest.cancel();
            this.mCurrentRequest = null;
        }
        for (BleRequest request : this.mBleWorkList) {
            request.cancel();
        }
        this.mBleWorkList.clear();
        this.mWorker.closeGatt();
    }

    public void refreshCache() {
        addNewRequest(new BleRefreshCacheRequest(null));
    }

    public void clearRequest(int clearType) {
        checkRuntime();
        BluetoothLog.w(String.format("clearRequest %d", new Object[]{Integer.valueOf(clearType)}));
        List<BleRequest> requestClear = new LinkedList<>();
        if (clearType == 0) {
            requestClear.addAll(this.mBleWorkList);
        } else {
            for (BleRequest request : this.mBleWorkList) {
                if (isRequestMatch(request, clearType)) {
                    requestClear.add(request);
                }
            }
        }
        for (BleRequest request2 : requestClear) {
            request2.cancel();
        }
        this.mBleWorkList.removeAll(requestClear);
    }

    private boolean isRequestMatch(BleRequest request, int requestType) {
        if ((requestType & 1) != 0) {
            return request instanceof BleReadRequest;
        }
        if ((requestType & 2) != 0) {
            if ((request instanceof BleWriteRequest) || (request instanceof BleWriteNoRspRequest)) {
                return true;
            }
            return false;
        } else if ((requestType & 4) != 0) {
            if ((request instanceof BleNotifyRequest) || (request instanceof BleUnnotifyRequest) || (request instanceof BleIndicateRequest)) {
                return true;
            }
            return false;
        } else if ((requestType & 8) != 0) {
            return request instanceof BleReadRssiRequest;
        } else {
            return false;
        }
    }

    public void read(UUID service, UUID character, BleGeneralResponse response) {
        addNewRequest(new BleReadRequest(service, character, response));
    }

    public void write(UUID service, UUID character, byte[] bytes, BleGeneralResponse response) {
        addNewRequest(new BleWriteRequest(service, character, bytes, response));
    }

    public void writeNoRsp(UUID service, UUID character, byte[] bytes, BleGeneralResponse response) {
        addNewRequest(new BleWriteNoRspRequest(service, character, bytes, response));
    }

    public void readDescriptor(UUID service, UUID character, UUID descriptor, BleGeneralResponse response) {
        addNewRequest(new BleReadDescriptorRequest(service, character, descriptor, response));
    }

    public void writeDescriptor(UUID service, UUID character, UUID descriptor, byte[] bytes, BleGeneralResponse response) {
        addNewRequest(new BleWriteDescriptorRequest(service, character, descriptor, bytes, response));
    }

    public void notify(UUID service, UUID character, BleGeneralResponse response) {
        addNewRequest(new BleNotifyRequest(service, character, response));
    }

    public void unnotify(UUID service, UUID character, BleGeneralResponse response) {
        addNewRequest(new BleUnnotifyRequest(service, character, response));
    }

    public void indicate(UUID service, UUID character, BleGeneralResponse response) {
        addNewRequest(new BleIndicateRequest(service, character, response));
    }

    public void unindicate(UUID service, UUID character, BleGeneralResponse response) {
        addNewRequest(new BleUnnotifyRequest(service, character, response));
    }

    public void readRemoteRssi(BleGeneralResponse response) {
        addNewRequest(new BleReadRssiRequest(response));
    }

    private void addNewRequest(BleRequest request) {
        checkRuntime();
        if (this.mBleWorkList.size() < 100) {
            request.setRuntimeChecker(this);
            request.setAddress(this.mAddress);
            request.setWorker(this.mWorker);
            this.mBleWorkList.add(request);
        } else {
            request.onResponse(-8);
        }
        scheduleNextRequest(10);
    }

    public void onRequestCompleted(BleRequest request) {
        checkRuntime();
        if (request != this.mCurrentRequest) {
            throw new IllegalStateException("request not match");
        }
        this.mCurrentRequest = null;
        scheduleNextRequest(10);
    }

    private void scheduleNextRequest(long delayInMillis) {
        this.mHandler.sendEmptyMessageDelayed(18, delayInMillis);
    }

    private void scheduleNextRequest() {
        if (this.mCurrentRequest == null && !ListUtils.isEmpty(this.mBleWorkList)) {
            this.mCurrentRequest = (BleRequest) this.mBleWorkList.remove(0);
            this.mCurrentRequest.process(this);
        }
    }

    public void checkRuntime() {
        if (Thread.currentThread() != this.mHandler.getLooper().getThread()) {
            throw new IllegalStateException("Thread Context Illegal");
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 18:
                scheduleNextRequest();
                break;
        }
        return true;
    }
}
