package com.iwown.device_module.device_operation.eventbus;

public class BleConnectStatue {
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_DISCONNECT = 1;
    public static final int STATE_START_CONNECT = 0;
    private int newState = 1;
    private boolean statue;

    public BleConnectStatue() {
    }

    public BleConnectStatue(boolean statue2) {
        int i = 1;
        this.statue = statue2;
        if (statue2) {
            i = 2;
        }
        this.newState = i;
    }

    public BleConnectStatue(int newState2) {
        boolean z = true;
        if (newState2 != 2) {
            z = false;
        }
        this.statue = z;
        this.newState = newState2;
    }

    public boolean isStatue() {
        return this.statue;
    }

    public void setStatue(boolean statue2) {
        this.statue = statue2;
    }

    public int getNewState() {
        return this.newState;
    }

    public void setNewState(int newState2) {
        this.newState = newState2;
    }

    public String toString() {
        return "BleConnectStatue{statue=" + this.statue + ", newState=" + this.newState + '}';
    }
}
