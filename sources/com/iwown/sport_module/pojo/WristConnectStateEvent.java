package com.iwown.sport_module.pojo;

public class WristConnectStateEvent {
    private boolean isConnected;

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    public WristConnectStateEvent(boolean isConnected2) {
        this.isConnected = isConnected2;
    }
}
