package com.inuker.bluetooth.library.connect.request;

import com.inuker.bluetooth.library.connect.IBleConnectDispatcher;

public interface IBleRequest {
    void cancel();

    void process(IBleConnectDispatcher iBleConnectDispatcher);
}
