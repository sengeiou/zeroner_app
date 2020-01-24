package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.internal.zzcxd;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class zzbo<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zzu {
    private final zzh<O> zzfmf;
    /* access modifiers changed from: private */
    public final zze zzfpv;
    private boolean zzfrw;
    final /* synthetic */ zzbm zzfti;
    private final Queue<zza> zzftj = new LinkedList();
    private final zzb zzftk;
    private final zzae zzftl;
    private final Set<zzj> zzftm = new HashSet();
    private final Map<zzck<?>, zzcr> zzftn = new HashMap();
    private final int zzfto;
    private final zzcv zzftp;
    private ConnectionResult zzftq = null;

    @WorkerThread
    public zzbo(zzbm zzbm, GoogleApi<O> googleApi) {
        this.zzfti = zzbm;
        this.zzfpv = googleApi.zza(zzbm.mHandler.getLooper(), this);
        if (this.zzfpv instanceof zzbz) {
            this.zzftk = zzbz.zzals();
        } else {
            this.zzftk = this.zzfpv;
        }
        this.zzfmf = googleApi.zzagn();
        this.zzftl = new zzae();
        this.zzfto = googleApi.getInstanceId();
        if (this.zzfpv.zzaay()) {
            this.zzftp = googleApi.zza(zzbm.mContext, zzbm.mHandler);
        } else {
            this.zzftp = null;
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzaiw() {
        zzaiz();
        zzi(ConnectionResult.zzfkr);
        zzajb();
        for (zzcr zzcr : this.zzftn.values()) {
            try {
                zzcr.zzfnq.zzb(this.zzftk, new TaskCompletionSource());
            } catch (DeadObjectException e) {
                onConnectionSuspended(1);
                this.zzfpv.disconnect();
            } catch (RemoteException e2) {
            }
        }
        while (this.zzfpv.isConnected() && !this.zzftj.isEmpty()) {
            zzb((zza) this.zzftj.remove());
        }
        zzajc();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzaix() {
        zzaiz();
        this.zzfrw = true;
        this.zzftl.zzahw();
        this.zzfti.mHandler.sendMessageDelayed(Message.obtain(this.zzfti.mHandler, 9, this.zzfmf), this.zzfti.zzfry);
        this.zzfti.mHandler.sendMessageDelayed(Message.obtain(this.zzfti.mHandler, 11, this.zzfmf), this.zzfti.zzfrx);
        this.zzfti.zzftc = -1;
    }

    @WorkerThread
    private final void zzajb() {
        if (this.zzfrw) {
            this.zzfti.mHandler.removeMessages(11, this.zzfmf);
            this.zzfti.mHandler.removeMessages(9, this.zzfmf);
            this.zzfrw = false;
        }
    }

    private final void zzajc() {
        this.zzfti.mHandler.removeMessages(12, this.zzfmf);
        this.zzfti.mHandler.sendMessageDelayed(this.zzfti.mHandler.obtainMessage(12, this.zzfmf), this.zzfti.zzfta);
    }

    @WorkerThread
    private final void zzb(zza zza) {
        zza.zza(this.zzftl, zzaay());
        try {
            zza.zza(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.zzfpv.disconnect();
        }
    }

    @WorkerThread
    private final void zzi(ConnectionResult connectionResult) {
        for (zzj zzj : this.zzftm) {
            String str = null;
            if (connectionResult == ConnectionResult.zzfkr) {
                str = this.zzfpv.zzagi();
            }
            zzj.zza(this.zzfmf, connectionResult, str);
        }
        this.zzftm.clear();
    }

    @WorkerThread
    public final void connect() {
        zzbq.zza(this.zzfti.mHandler);
        if (!this.zzfpv.isConnected() && !this.zzfpv.isConnecting()) {
            if (this.zzfpv.zzagg() && this.zzfti.zzftc != 0) {
                this.zzfti.zzftc = this.zzfti.zzfmy.isGooglePlayServicesAvailable(this.zzfti.mContext);
                if (this.zzfti.zzftc != 0) {
                    onConnectionFailed(new ConnectionResult(this.zzfti.zzftc, null));
                    return;
                }
            }
            zzbu zzbu = new zzbu(this.zzfti, this.zzfpv, this.zzfmf);
            if (this.zzfpv.zzaay()) {
                this.zzftp.zza((zzcy) zzbu);
            }
            this.zzfpv.zza((zzj) zzbu);
        }
    }

    public final int getInstanceId() {
        return this.zzfto;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isConnected() {
        return this.zzfpv.isConnected();
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zzfti.mHandler.getLooper()) {
            zzaiw();
        } else {
            this.zzfti.mHandler.post(new zzbp(this));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006c, code lost:
        if (r5.zzfti.zzc(r6, r5.zzfto) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
        if (r6.getErrorCode() != 18) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
        r5.zzfrw = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007b, code lost:
        if (r5.zzfrw == false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007d, code lost:
        r5.zzfti.mHandler.sendMessageDelayed(android.os.Message.obtain(r5.zzfti.mHandler, 9, r5.zzfmf), r5.zzfti.zzfry);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009b, code lost:
        r2 = r5.zzfmf.zzagy();
        zzw(new com.google.android.gms.common.api.Status(17, new java.lang.StringBuilder(java.lang.String.valueOf(r2).length() + 38).append("API: ").append(r2).append(" is not available on this device.").toString()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r6) {
        /*
            r5 = this;
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti
            android.os.Handler r0 = r0.mHandler
            com.google.android.gms.common.internal.zzbq.zza(r0)
            com.google.android.gms.common.api.internal.zzcv r0 = r5.zzftp
            if (r0 == 0) goto L_0x0012
            com.google.android.gms.common.api.internal.zzcv r0 = r5.zzftp
            r0.zzajq()
        L_0x0012:
            r5.zzaiz()
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti
            r1 = -1
            r0.zzftc = r1
            r5.zzi(r6)
            int r0 = r6.getErrorCode()
            r1 = 4
            if (r0 != r1) goto L_0x002d
            com.google.android.gms.common.api.Status r0 = com.google.android.gms.common.api.internal.zzbm.zzfsz
            r5.zzw(r0)
        L_0x002c:
            return
        L_0x002d:
            java.util.Queue<com.google.android.gms.common.api.internal.zza> r0 = r5.zzftj
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0038
            r5.zzftq = r6
            goto L_0x002c
        L_0x0038:
            java.lang.Object r1 = com.google.android.gms.common.api.internal.zzbm.sLock
            monitor-enter(r1)
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti     // Catch:{ all -> 0x0060 }
            com.google.android.gms.common.api.internal.zzah r0 = r0.zzftf     // Catch:{ all -> 0x0060 }
            if (r0 == 0) goto L_0x0063
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti     // Catch:{ all -> 0x0060 }
            java.util.Set r0 = r0.zzftg     // Catch:{ all -> 0x0060 }
            com.google.android.gms.common.api.internal.zzh<O> r2 = r5.zzfmf     // Catch:{ all -> 0x0060 }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x0060 }
            if (r0 == 0) goto L_0x0063
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti     // Catch:{ all -> 0x0060 }
            com.google.android.gms.common.api.internal.zzah r0 = r0.zzftf     // Catch:{ all -> 0x0060 }
            int r2 = r5.zzfto     // Catch:{ all -> 0x0060 }
            r0.zzb(r6, r2)     // Catch:{ all -> 0x0060 }
            monitor-exit(r1)     // Catch:{ all -> 0x0060 }
            goto L_0x002c
        L_0x0060:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0060 }
            throw r0
        L_0x0063:
            monitor-exit(r1)     // Catch:{ all -> 0x0060 }
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti
            int r1 = r5.zzfto
            boolean r0 = r0.zzc(r6, r1)
            if (r0 != 0) goto L_0x002c
            int r0 = r6.getErrorCode()
            r1 = 18
            if (r0 != r1) goto L_0x0079
            r0 = 1
            r5.zzfrw = r0
        L_0x0079:
            boolean r0 = r5.zzfrw
            if (r0 == 0) goto L_0x009b
            com.google.android.gms.common.api.internal.zzbm r0 = r5.zzfti
            android.os.Handler r0 = r0.mHandler
            com.google.android.gms.common.api.internal.zzbm r1 = r5.zzfti
            android.os.Handler r1 = r1.mHandler
            r2 = 9
            com.google.android.gms.common.api.internal.zzh<O> r3 = r5.zzfmf
            android.os.Message r1 = android.os.Message.obtain(r1, r2, r3)
            com.google.android.gms.common.api.internal.zzbm r2 = r5.zzfti
            long r2 = r2.zzfry
            r0.sendMessageDelayed(r1, r2)
            goto L_0x002c
        L_0x009b:
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status
            r1 = 17
            com.google.android.gms.common.api.internal.zzh<O> r2 = r5.zzfmf
            java.lang.String r2 = r2.zzagy()
            java.lang.String r3 = java.lang.String.valueOf(r2)
            int r3 = r3.length()
            int r3 = r3 + 38
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "API: "
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r3 = " is not available on this device."
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r1, r2)
            r5.zzw(r0)
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzbo.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zzfti.mHandler.getLooper()) {
            zzaix();
        } else {
            this.zzfti.mHandler.post(new zzbq(this));
        }
    }

    @WorkerThread
    public final void resume() {
        zzbq.zza(this.zzfti.mHandler);
        if (this.zzfrw) {
            connect();
        }
    }

    @WorkerThread
    public final void signOut() {
        zzbq.zza(this.zzfti.mHandler);
        zzw(zzbm.zzfsy);
        this.zzftl.zzahv();
        for (zzck zzf : (zzck[]) this.zzftn.keySet().toArray(new zzck[this.zzftn.size()])) {
            zza((zza) new zzf(zzf, new TaskCompletionSource()));
        }
        zzi(new ConnectionResult(4));
        if (this.zzfpv.isConnected()) {
            this.zzfpv.zza((zzp) new zzbs(this));
        }
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (Looper.myLooper() == this.zzfti.mHandler.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.zzfti.mHandler.post(new zzbr(this, connectionResult));
        }
    }

    @WorkerThread
    public final void zza(zza zza) {
        zzbq.zza(this.zzfti.mHandler);
        if (this.zzfpv.isConnected()) {
            zzb(zza);
            zzajc();
            return;
        }
        this.zzftj.add(zza);
        if (this.zzftq == null || !this.zzftq.hasResolution()) {
            connect();
        } else {
            onConnectionFailed(this.zzftq);
        }
    }

    @WorkerThread
    public final void zza(zzj zzj) {
        zzbq.zza(this.zzfti.mHandler);
        this.zzftm.add(zzj);
    }

    public final boolean zzaay() {
        return this.zzfpv.zzaay();
    }

    public final zze zzahp() {
        return this.zzfpv;
    }

    @WorkerThread
    public final void zzaij() {
        zzbq.zza(this.zzfti.mHandler);
        if (this.zzfrw) {
            zzajb();
            zzw(this.zzfti.zzfmy.isGooglePlayServicesAvailable(this.zzfti.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
            this.zzfpv.disconnect();
        }
    }

    public final Map<zzck<?>, zzcr> zzaiy() {
        return this.zzftn;
    }

    @WorkerThread
    public final void zzaiz() {
        zzbq.zza(this.zzfti.mHandler);
        this.zzftq = null;
    }

    @WorkerThread
    public final ConnectionResult zzaja() {
        zzbq.zza(this.zzfti.mHandler);
        return this.zzftq;
    }

    @WorkerThread
    public final void zzajd() {
        zzbq.zza(this.zzfti.mHandler);
        if (this.zzfpv.isConnected() && this.zzftn.size() == 0) {
            if (this.zzftl.zzahu()) {
                zzajc();
            } else {
                this.zzfpv.disconnect();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final zzcxd zzaje() {
        if (this.zzftp == null) {
            return null;
        }
        return this.zzftp.zzaje();
    }

    @WorkerThread
    public final void zzh(@NonNull ConnectionResult connectionResult) {
        zzbq.zza(this.zzfti.mHandler);
        this.zzfpv.disconnect();
        onConnectionFailed(connectionResult);
    }

    @WorkerThread
    public final void zzw(Status status) {
        zzbq.zza(this.zzfti.mHandler);
        for (zza zzs : this.zzftj) {
            zzs.zzs(status);
        }
        this.zzftj.clear();
    }
}
