package com.iwown.device_module.device_firmware_upgrade;

import android.app.Activity;
import android.os.Bundle;

public class NotificationActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTaskRoot()) {
        }
        finish();
    }
}
