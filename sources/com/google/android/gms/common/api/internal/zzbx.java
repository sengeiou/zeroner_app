package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class zzbx extends BroadcastReceiver {
    private Context mContext;
    private final zzby zzftx;

    public zzbx(zzby zzby) {
        this.zzftx = zzby;
    }

    public final void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        String str = null;
        if (data != null) {
            str = data.getSchemeSpecificPart();
        }
        if ("com.google.android.gms".equals(str)) {
            this.zzftx.zzahg();
            unregister();
        }
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    public final synchronized void unregister() {
        if (this.mContext != null) {
            this.mContext.unregisterReceiver(this);
        }
        this.mContext = null;
    }
}
