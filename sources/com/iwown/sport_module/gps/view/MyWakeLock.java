package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class MyWakeLock {
    private Context context;
    private WakeLock wakeLock;

    public MyWakeLock(Context context2) {
        this.context = context2;
    }

    public void acquireWakeLock() {
        try {
            if (this.wakeLock == null) {
                this.wakeLock = ((PowerManager) this.context.getSystemService("power")).newWakeLock(536870913, getClass().getCanonicalName());
                if (this.wakeLock != null) {
                    this.wakeLock.acquire();
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void releaseWakeLock() {
        if (this.wakeLock != null && this.wakeLock.isHeld()) {
            this.wakeLock.release();
            this.wakeLock = null;
        }
    }
}
