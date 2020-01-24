package com.iwown.ble_module.mtk_ble.leprofiles.fmpserver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

class DefaultAlerter implements FmpServerAlerter {
    private static final boolean DBG = true;
    private static final String TAG = "DefaultAlerter";
    private static final boolean VDBG = true;
    private final Context mCtx;

    DefaultAlerter(Context ctx) {
        Log.v(TAG, TAG);
        this.mCtx = ctx;
        this.mCtx.startService(new Intent(this.mCtx, FmpServerAlertService.class));
    }

    public final boolean alert(int level) {
        Log.d(TAG, "alert: level = " + level);
        Intent intent = new Intent(this.mCtx, FmpServerAlertService.class);
        intent.putExtra(FmpServerAlertService.INTENT_STATE, level);
        this.mCtx.startService(intent);
        return true;
    }

    public final boolean uninit() {
        return true;
    }
}
