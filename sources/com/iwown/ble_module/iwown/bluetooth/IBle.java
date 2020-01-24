package com.iwown.ble_module.iwown.bluetooth;

public interface IBle {
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

    void switchStandHeartRate(boolean z);

    void unbindDevice();
}
