package com.iwown.ble_module.zg_ble.bluetooth;

public interface ZGIBle {
    boolean adapterEnabled();

    boolean connect();

    boolean connect(String str);

    void disconnect(String str, boolean z);

    boolean discoverServices(String str);

    boolean isConnected();

    boolean isConnecting();

    boolean isScanning();

    void setNeedReconnect(boolean z);

    void startScan(boolean z);

    void stopScan();

    void unbindDevice();
}
