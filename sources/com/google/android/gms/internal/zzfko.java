package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfko extends zzfjm<zzfko> implements Cloneable {
    private String version;
    private int zziyd;
    private String zzpqg;

    public zzfko() {
        this.zziyd = 0;
        this.zzpqg = "";
        this.version = "";
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzdax */
    public zzfko clone() {
        try {
            return (zzfko) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfko)) {
            return false;
        }
        zzfko zzfko = (zzfko) obj;
        if (this.zziyd != zzfko.zziyd) {
            return false;
        }
        if (this.zzpqg == null) {
            if (zzfko.zzpqg != null) {
                return false;
            }
        } else if (!this.zzpqg.equals(zzfko.zzpqg)) {
            return false;
        }
        if (this.version == null) {
            if (zzfko.version != null) {
                return false;
            }
        } else if (!this.version.equals(zzfko.version)) {
            return false;
        }
        return (this.zzpnc == null || this.zzpnc.isEmpty()) ? zzfko.zzpnc == null || zzfko.zzpnc.isEmpty() : this.zzpnc.equals(zzfko.zzpnc);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.version == null ? 0 : this.version.hashCode()) + (((this.zzpqg == null ? 0 : this.zzpqg.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.zziyd) * 31)) * 31)) * 31;
        if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
            i = this.zzpnc.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfjs zza(zzfjj zzfjj) throws IOException {
        while (true) {
            int zzcvt = zzfjj.zzcvt();
            switch (zzcvt) {
                case 0:
                    break;
                case 8:
                    this.zziyd = zzfjj.zzcvw();
                    continue;
                case 18:
                    this.zzpqg = zzfjj.readString();
                    continue;
                case 26:
                    this.version = zzfjj.readString();
                    continue;
                default:
                    if (!super.zza(zzfjj, zzcvt)) {
                        break;
                    } else {
                        continue;
                    }
            }
        }
        return this;
    }

    public final void zza(zzfjk zzfjk) throws IOException {
        if (this.zziyd != 0) {
            zzfjk.zzaa(1, this.zziyd);
        }
        if (this.zzpqg != null && !this.zzpqg.equals("")) {
            zzfjk.zzn(2, this.zzpqg);
        }
        if (this.version != null && !this.version.equals("")) {
            zzfjk.zzn(3, this.version);
        }
        super.zza(zzfjk);
    }

    public final /* synthetic */ zzfjm zzdaf() throws CloneNotSupportedException {
        return (zzfko) clone();
    }

    public final /* synthetic */ zzfjs zzdag() throws CloneNotSupportedException {
        return (zzfko) clone();
    }

    /* access modifiers changed from: protected */
    public final int zzq() {
        int zzq = super.zzq();
        if (this.zziyd != 0) {
            zzq += zzfjk.zzad(1, this.zziyd);
        }
        if (this.zzpqg != null && !this.zzpqg.equals("")) {
            zzq += zzfjk.zzo(2, this.zzpqg);
        }
        return (this.version == null || this.version.equals("")) ? zzq : zzq + zzfjk.zzo(3, this.version);
    }
}
