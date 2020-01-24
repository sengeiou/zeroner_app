package com.google.android.gms.internal;

import java.util.Iterator;

public abstract class zzbgq extends zzbgn implements zzbfq {
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        zzbgn zzbgn = (zzbgn) obj;
        for (zzbgo zzbgo : zzaav().values()) {
            if (zza(zzbgo)) {
                if (!zzbgn.zza(zzbgo)) {
                    return false;
                }
                if (!zzb(zzbgo).equals(zzbgn.zzb(zzbgo))) {
                    return false;
                }
            } else if (zzbgn.zza(zzbgo)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        Iterator it = zzaav().values().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            zzbgo zzbgo = (zzbgo) it.next();
            if (zza(zzbgo)) {
                i = zzb(zzbgo).hashCode() + (i2 * 31);
            } else {
                i = i2;
            }
        }
    }

    public Object zzgo(String str) {
        return null;
    }

    public boolean zzgp(String str) {
        return false;
    }
}
