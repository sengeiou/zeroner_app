package com.iwown.device_module.device_firmware_upgrade.service;

import android.app.Activity;
import com.iwown.device_module.device_firmware_upgrade.NotificationActivity;
import no.nordicsemi.android.dfu.DfuBaseService;

public class DfuService extends DfuBaseService {
    /* access modifiers changed from: protected */
    public Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }
}
