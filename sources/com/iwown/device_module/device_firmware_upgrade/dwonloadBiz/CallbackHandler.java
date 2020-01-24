package com.iwown.device_module.device_firmware_upgrade.dwonloadBiz;

import android.os.Handler;
import android.os.Message;

public class CallbackHandler extends Handler {
    public static final int ONFAILURE = 5;
    public static final int ONLOADING = 3;
    public static final int ONSTART = 1;
    public static final int ONSTOP = 2;
    public static final int ONSUCCESS = 4;

    public void onStart() {
    }

    public void onStop() {
    }

    public void onLoading(long curr, long total) {
    }

    public void onSuccess() {
    }

    public void onFailure(String strFail) {
    }

    public void onFailure(String strFail, int errorCode) {
    }

    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                onStart();
                return;
            case 2:
                onStop();
                return;
            case 3:
                Object[] objs = (Object[]) msg.obj;
                onLoading(((Long) objs[0]).longValue(), ((Long) objs[1]).longValue());
                return;
            case 4:
                onSuccess();
                return;
            case 5:
                Object[] objs2 = (Object[]) msg.obj;
                onFailure((String) objs2[0]);
                if (objs2.length >= 2) {
                    onFailure((String) objs2[0], ((Integer) objs2[1]).intValue());
                    return;
                } else {
                    onFailure((String) objs2[0], 0);
                    return;
                }
            default:
                return;
        }
    }
}
