package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Intent;
import java.util.Arrays;

public final class zzah {
    private final ComponentName mComponentName;
    private final String zzdrp;
    private final String zzgak;
    private final int zzgal;

    public zzah(ComponentName componentName, int i) {
        this.zzdrp = null;
        this.zzgak = null;
        this.mComponentName = (ComponentName) zzbq.checkNotNull(componentName);
        this.zzgal = 129;
    }

    public zzah(String str, String str2, int i) {
        this.zzdrp = zzbq.zzgm(str);
        this.zzgak = zzbq.zzgm(str2);
        this.mComponentName = null;
        this.zzgal = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzah)) {
            return false;
        }
        zzah zzah = (zzah) obj;
        return zzbg.equal(this.zzdrp, zzah.zzdrp) && zzbg.equal(this.zzgak, zzah.zzgak) && zzbg.equal(this.mComponentName, zzah.mComponentName) && this.zzgal == zzah.zzgal;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final String getPackage() {
        return this.zzgak;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzdrp, this.zzgak, this.mComponentName, Integer.valueOf(this.zzgal)});
    }

    public final String toString() {
        return this.zzdrp == null ? this.mComponentName.flattenToString() : this.zzdrp;
    }

    public final int zzalk() {
        return this.zzgal;
    }

    public final Intent zzall() {
        return this.zzdrp != null ? new Intent(this.zzdrp).setPackage(this.zzgak) : new Intent().setComponent(this.mComponentName);
    }
}
