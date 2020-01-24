package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzi extends zzo {
    private final SparseArray<zza> zzfnx = new SparseArray<>();

    class zza implements OnConnectionFailedListener {
        public final int zzfny;
        public final GoogleApiClient zzfnz;
        public final OnConnectionFailedListener zzfoa;

        public zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.zzfny = i;
            this.zzfnz = googleApiClient;
            this.zzfoa = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 27).append("beginFailureResolution for ").append(valueOf).toString());
            zzi.this.zzb(connectionResult, this.zzfny);
        }
    }

    private zzi(zzcf zzcf) {
        super(zzcf);
        this.zzfud.zza("AutoManageHelper", (LifecycleCallback) this);
    }

    public static zzi zza(zzce zzce) {
        zzcf zzb = zzb(zzce);
        zzi zzi = (zzi) zzb.zza("AutoManageHelper", zzi.class);
        return zzi != null ? zzi : new zzi(zzb);
    }

    @Nullable
    private final zza zzbs(int i) {
        if (this.zzfnx.size() <= i) {
            return null;
        }
        return (zza) this.zzfnx.get(this.zzfnx.keyAt(i));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zzfnx.size(); i++) {
            zza zzbs = zzbs(i);
            if (zzbs != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zzbs.zzfny);
                printWriter.println(":");
                zzbs.zzfnz.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.zzfnx);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 14).append("onStart ").append(z).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(valueOf).toString());
        if (this.zzfol.get() == null) {
            for (int i = 0; i < this.zzfnx.size(); i++) {
                zza zzbs = zzbs(i);
                if (zzbs != null) {
                    zzbs.zzfnz.connect();
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzfnx.size(); i++) {
            zza zzbs = zzbs(i);
            if (zzbs != null) {
                zzbs.zzfnz.disconnect();
            }
        }
    }

    public final void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzbq.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        zzbq.zza(this.zzfnx.indexOfKey(i) < 0, (Object) "Already managing a GoogleApiClient with id " + i);
        zzp zzp = (zzp) this.zzfol.get();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(zzp);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 49).append("starting AutoManage for client ").append(i).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(z).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(valueOf).toString());
        this.zzfnx.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && zzp == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf2).length() + 11).append("connecting ").append(valueOf2).toString());
            googleApiClient.connect();
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza2 = (zza) this.zzfnx.get(i);
        if (zza2 != null) {
            zzbr(i);
            OnConnectionFailedListener onConnectionFailedListener = zza2.zzfoa;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zzagz() {
        for (int i = 0; i < this.zzfnx.size(); i++) {
            zza zzbs = zzbs(i);
            if (zzbs != null) {
                zzbs.zzfnz.connect();
            }
        }
    }

    public final void zzbr(int i) {
        zza zza2 = (zza) this.zzfnx.get(i);
        this.zzfnx.remove(i);
        if (zza2 != null) {
            zza2.zzfnz.unregisterConnectionFailedListener(zza2);
            zza2.zzfnz.disconnect();
        }
    }
}
