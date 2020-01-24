package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

final class zzai extends zzag implements Callback {
    /* access modifiers changed from: private */
    public final Context mApplicationContext;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public final HashMap<zzah, zzaj> zzgam = new HashMap<>();
    /* access modifiers changed from: private */
    public final zza zzgan;
    private final long zzgao;
    /* access modifiers changed from: private */
    public final long zzgap;

    zzai(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzgan = zza.zzamc();
        this.zzgao = BootloaderScanner.TIMEOUT;
        this.zzgap = 300000;
    }

    public final boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.zzgam) {
                    zzah zzah = (zzah) message.obj;
                    zzaj zzaj = (zzaj) this.zzgam.get(zzah);
                    if (zzaj != null && zzaj.zzalm()) {
                        if (zzaj.isBound()) {
                            zzaj.zzgj("GmsClientSupervisor");
                        }
                        this.zzgam.remove(zzah);
                    }
                }
                return true;
            case 1:
                synchronized (this.zzgam) {
                    zzah zzah2 = (zzah) message.obj;
                    zzaj zzaj2 = (zzaj) this.zzgam.get(zzah2);
                    if (zzaj2 != null && zzaj2.getState() == 3) {
                        String valueOf = String.valueOf(zzah2);
                        Log.wtf("GmsClientSupervisor", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Timeout waiting for ServiceConnection callback ").append(valueOf).toString(), new Exception());
                        ComponentName componentName = zzaj2.getComponentName();
                        if (componentName == null) {
                            componentName = zzah2.getComponentName();
                        }
                        zzaj2.onServiceDisconnected(componentName == null ? new ComponentName(zzah2.getPackage(), "unknown") : componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzah zzah, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzgam) {
            zzaj zzaj = (zzaj) this.zzgam.get(zzah);
            if (zzaj != null) {
                this.mHandler.removeMessages(0, zzah);
                if (!zzaj.zza(serviceConnection)) {
                    zzaj.zza(serviceConnection, str);
                    switch (zzaj.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzaj.getComponentName(), zzaj.getBinder());
                            break;
                        case 2:
                            zzaj.zzgi(str);
                            break;
                    }
                } else {
                    String valueOf = String.valueOf(zzah);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(valueOf).toString());
                }
            } else {
                zzaj = new zzaj(this, zzah);
                zzaj.zza(serviceConnection, str);
                zzaj.zzgi(str);
                this.zzgam.put(zzah, zzaj);
            }
            isBound = zzaj.isBound();
        }
        return isBound;
    }

    /* access modifiers changed from: protected */
    public final void zzb(zzah zzah, ServiceConnection serviceConnection, String str) {
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzgam) {
            zzaj zzaj = (zzaj) this.zzgam.get(zzah);
            if (zzaj == null) {
                String valueOf = String.valueOf(zzah);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Nonexistent connection status for service config: ").append(valueOf).toString());
            } else if (!zzaj.zza(serviceConnection)) {
                String valueOf2 = String.valueOf(zzah);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf2).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(valueOf2).toString());
            } else {
                zzaj.zzb(serviceConnection, str);
                if (zzaj.zzalm()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, zzah), this.zzgao);
                }
            }
        }
    }
}
