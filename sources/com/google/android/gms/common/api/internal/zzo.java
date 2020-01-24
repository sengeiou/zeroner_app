package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzo extends LifecycleCallback implements OnCancelListener {
    protected volatile boolean mStarted;
    protected final GoogleApiAvailability zzfmy;
    protected final AtomicReference<zzp> zzfol;
    private final Handler zzfom;

    protected zzo(zzcf zzcf) {
        this(zzcf, GoogleApiAvailability.getInstance());
    }

    private zzo(zzcf zzcf, GoogleApiAvailability googleApiAvailability) {
        super(zzcf);
        this.zzfol = new AtomicReference<>(null);
        this.zzfom = new Handler(Looper.getMainLooper());
        this.zzfmy = googleApiAvailability;
    }

    private static int zza(@Nullable zzp zzp) {
        if (zzp == null) {
            return -1;
        }
        return zzp.zzahe();
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onActivityResult(int r7, int r8, android.content.Intent r9) {
        /*
            r6 = this;
            r5 = 18
            r1 = 13
            r2 = 1
            r3 = 0
            java.util.concurrent.atomic.AtomicReference<com.google.android.gms.common.api.internal.zzp> r0 = r6.zzfol
            java.lang.Object r0 = r0.get()
            com.google.android.gms.common.api.internal.zzp r0 = (com.google.android.gms.common.api.internal.zzp) r0
            switch(r7) {
                case 1: goto L_0x0034;
                case 2: goto L_0x0018;
                default: goto L_0x0011;
            }
        L_0x0011:
            r1 = r3
        L_0x0012:
            if (r1 == 0) goto L_0x005b
            r6.zzahd()
        L_0x0017:
            return
        L_0x0018:
            com.google.android.gms.common.GoogleApiAvailability r1 = r6.zzfmy
            android.app.Activity r4 = r6.getActivity()
            int r4 = r1.isGooglePlayServicesAvailable(r4)
            if (r4 != 0) goto L_0x0069
            r1 = r2
        L_0x0025:
            if (r0 == 0) goto L_0x0017
            com.google.android.gms.common.ConnectionResult r2 = r0.zzahf()
            int r2 = r2.getErrorCode()
            if (r2 != r5) goto L_0x0012
            if (r4 != r5) goto L_0x0012
            goto L_0x0017
        L_0x0034:
            r4 = -1
            if (r8 != r4) goto L_0x0039
            r1 = r2
            goto L_0x0012
        L_0x0039:
            if (r8 != 0) goto L_0x0011
            if (r9 == 0) goto L_0x0044
            java.lang.String r2 = "<<ResolutionFailureErrorDetail>>"
            int r1 = r9.getIntExtra(r2, r1)
        L_0x0044:
            com.google.android.gms.common.api.internal.zzp r2 = new com.google.android.gms.common.api.internal.zzp
            com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult
            r5 = 0
            r4.<init>(r1, r5)
            int r0 = zza(r0)
            r2.<init>(r4, r0)
            java.util.concurrent.atomic.AtomicReference<com.google.android.gms.common.api.internal.zzp> r0 = r6.zzfol
            r0.set(r2)
            r0 = r2
            r1 = r3
            goto L_0x0012
        L_0x005b:
            if (r0 == 0) goto L_0x0017
            com.google.android.gms.common.ConnectionResult r1 = r0.zzahf()
            int r0 = r0.zzahe()
            r6.zza(r1, r0)
            goto L_0x0017
        L_0x0069:
            r1 = r3
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzo.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), zza((zzp) this.zzfol.get()));
        zzahd();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzfol.set(bundle.getBoolean("resolving_error", false) ? new zzp(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zzp zzp = (zzp) this.zzfol.get();
        if (zzp != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zzp.zzahe());
            bundle.putInt("failed_status", zzp.zzahf().getErrorCode());
            bundle.putParcelable("failed_resolution", zzp.zzahf().getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(ConnectionResult connectionResult, int i);

    /* access modifiers changed from: protected */
    public abstract void zzagz();

    /* access modifiers changed from: protected */
    public final void zzahd() {
        this.zzfol.set(null);
        zzagz();
    }

    public final void zzb(ConnectionResult connectionResult, int i) {
        zzp zzp = new zzp(connectionResult, i);
        if (this.zzfol.compareAndSet(null, zzp)) {
            this.zzfom.post(new zzq(this, zzp));
        }
    }
}
