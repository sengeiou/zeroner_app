package com.iwown.device_module.device_operation.timeout;

import android.os.Handler;
import android.os.Handler.Callback;

public class TimeoutHandler extends Handler {
    public TimeoutHandler(Callback callback) {
        super(callback);
    }
}
