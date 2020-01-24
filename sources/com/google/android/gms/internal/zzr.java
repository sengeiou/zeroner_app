package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.Collections;
import java.util.Map;

public abstract class zzr<T> implements Comparable<zzr<T>> {
    private final Object mLock;
    /* access modifiers changed from: private */
    public final zza zzaf;
    private final int zzag;
    private final String zzah;
    private final int zzai;
    private final zzx zzaj;
    private Integer zzak;
    private zzv zzal;
    private boolean zzam;
    private boolean zzan;
    private boolean zzao;
    private boolean zzap;
    private zzaa zzaq;
    private zzc zzar;
    private zzt zzas;

    public zzr(int i, String str, zzx zzx) {
        int i2;
        this.zzaf = zza.zzbl ? new zza() : null;
        this.zzam = true;
        this.zzan = false;
        this.zzao = false;
        this.zzap = false;
        this.zzar = null;
        this.mLock = new Object();
        this.zzag = i;
        this.zzah = str;
        this.zzaj = zzx;
        this.zzaq = new zzh();
        if (!TextUtils.isEmpty(str)) {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                String host = parse.getHost();
                if (host != null) {
                    i2 = host.hashCode();
                    this.zzai = i2;
                }
            }
        }
        i2 = 0;
        this.zzai = i2;
    }

    public /* synthetic */ int compareTo(Object obj) {
        zzr zzr = (zzr) obj;
        zzu zzu = zzu.NORMAL;
        zzu zzu2 = zzu.NORMAL;
        return zzu == zzu2 ? this.zzak.intValue() - zzr.zzak.intValue() : zzu2.ordinal() - zzu.ordinal();
    }

    public Map<String, String> getHeaders() throws zza {
        return Collections.emptyMap();
    }

    public final int getMethod() {
        return this.zzag;
    }

    public final String getUrl() {
        return this.zzah;
    }

    public String toString() {
        String str = "0x";
        String valueOf = String.valueOf(Integer.toHexString(this.zzai));
        String str2 = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        String str3 = "[ ] ";
        String str4 = this.zzah;
        String valueOf2 = String.valueOf(zzu.NORMAL);
        String valueOf3 = String.valueOf(this.zzak);
        return new StringBuilder(String.valueOf(str3).length() + 3 + String.valueOf(str4).length() + String.valueOf(str2).length() + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append(str3).append(str4).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(str2).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(valueOf2).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(valueOf3).toString();
    }

    public final zzr<?> zza(int i) {
        this.zzak = Integer.valueOf(i);
        return this;
    }

    public final zzr<?> zza(zzc zzc) {
        this.zzar = zzc;
        return this;
    }

    public final zzr<?> zza(zzv zzv) {
        this.zzal = zzv;
        return this;
    }

    /* access modifiers changed from: protected */
    public abstract zzw<T> zza(zzp zzp);

    /* access modifiers changed from: 0000 */
    public final void zza(zzt zzt) {
        synchronized (this.mLock) {
            this.zzas = zzt;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzw<?> zzw) {
        synchronized (this.mLock) {
            if (this.zzas != null) {
                this.zzas.zza(this, zzw);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zza(T t);

    public final void zzb(zzad zzad) {
        if (this.zzaj != null) {
            this.zzaj.zzd(zzad);
        }
    }

    public final void zzb(String str) {
        if (zza.zzbl) {
            this.zzaf.zza(str, Thread.currentThread().getId());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzc(String str) {
        if (this.zzal != null) {
            this.zzal.zzf(this);
        }
        if (zza.zzbl) {
            long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new zzs(this, str, id));
                return;
            }
            this.zzaf.zza(str, id);
            this.zzaf.zzc(toString());
        }
    }

    public final int zzd() {
        return this.zzai;
    }

    public final zzc zze() {
        return this.zzar;
    }

    public byte[] zzf() throws zza {
        return null;
    }

    public final boolean zzg() {
        return this.zzam;
    }

    public final int zzh() {
        return this.zzaq.zzb();
    }

    public final zzaa zzi() {
        return this.zzaq;
    }

    public final void zzj() {
        this.zzao = true;
    }

    public final boolean zzk() {
        return this.zzao;
    }

    /* access modifiers changed from: 0000 */
    public final void zzl() {
        synchronized (this.mLock) {
            if (this.zzas != null) {
                this.zzas.zza(this);
            }
        }
    }
}
