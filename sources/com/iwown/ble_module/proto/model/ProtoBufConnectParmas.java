package com.iwown.ble_module.proto.model;

public class ProtoBufConnectParmas {
    private int intervalMs;
    private int maxSize;
    private int mtu;
    private int timeoutMs;

    public int getMtu() {
        return this.mtu;
    }

    public void setMtu(int mtu2) {
        this.mtu = mtu2;
    }

    public int getIntervalMs() {
        return this.intervalMs;
    }

    public void setIntervalMs(int intervalMs2) {
        this.intervalMs = intervalMs2;
    }

    public int getTimeoutMs() {
        return this.timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs2) {
        this.timeoutMs = timeoutMs2;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize2) {
        this.maxSize = maxSize2;
    }
}
