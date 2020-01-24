package com.iwown.data_link.eventbus;

public class BleConnectStateEvent {
    private boolean isConnect;

    public BleConnectStateEvent(boolean isConnect2) {
        this.isConnect = isConnect2;
    }

    public boolean isConnect() {
        return this.isConnect;
    }

    public void setConnect(boolean connect) {
        this.isConnect = connect;
    }
}
