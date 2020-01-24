package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfks extends zzfjm<zzfks> implements Cloneable {
    private int zzprf;
    private int zzprg;

    public zzfks() {
        this.zzprf = -1;
        this.zzprg = 0;
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzas */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzfks zza(com.google.android.gms.internal.zzfjj r7) throws java.io.IOException {
        /*
            r6 = this;
        L_0x0000:
            int r0 = r7.zzcvt()
            switch(r0) {
                case 0: goto L_0x000d;
                case 8: goto L_0x000e;
                case 16: goto L_0x0040;
                default: goto L_0x0007;
            }
        L_0x0007:
            boolean r0 = super.zza(r7, r0)
            if (r0 != 0) goto L_0x0000
        L_0x000d:
            return r6
        L_0x000e:
            int r1 = r7.getPosition()
            int r2 = r7.zzcvw()     // Catch:{ IllegalArgumentException -> 0x0035 }
            switch(r2) {
                case -1: goto L_0x003d;
                case 0: goto L_0x003d;
                case 1: goto L_0x003d;
                case 2: goto L_0x003d;
                case 3: goto L_0x003d;
                case 4: goto L_0x003d;
                case 5: goto L_0x003d;
                case 6: goto L_0x003d;
                case 7: goto L_0x003d;
                case 8: goto L_0x003d;
                case 9: goto L_0x003d;
                case 10: goto L_0x003d;
                case 11: goto L_0x003d;
                case 12: goto L_0x003d;
                case 13: goto L_0x003d;
                case 14: goto L_0x003d;
                case 15: goto L_0x003d;
                case 16: goto L_0x003d;
                case 17: goto L_0x003d;
                default: goto L_0x0019;
            }     // Catch:{ IllegalArgumentException -> 0x0035 }
        L_0x0019:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException -> 0x0035 }
            r4 = 43
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0035 }
            r5.<init>(r4)     // Catch:{ IllegalArgumentException -> 0x0035 }
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ IllegalArgumentException -> 0x0035 }
            java.lang.String r4 = " is not a valid enum NetworkType"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ IllegalArgumentException -> 0x0035 }
            java.lang.String r2 = r2.toString()     // Catch:{ IllegalArgumentException -> 0x0035 }
            r3.<init>(r2)     // Catch:{ IllegalArgumentException -> 0x0035 }
            throw r3     // Catch:{ IllegalArgumentException -> 0x0035 }
        L_0x0035:
            r2 = move-exception
            r7.zzmg(r1)
            r6.zza(r7, r0)
            goto L_0x0000
        L_0x003d:
            r6.zzprf = r2     // Catch:{ IllegalArgumentException -> 0x0035 }
            goto L_0x0000
        L_0x0040:
            int r1 = r7.getPosition()
            int r2 = r7.zzcvw()     // Catch:{ IllegalArgumentException -> 0x0067 }
            switch(r2) {
                case 0: goto L_0x006f;
                case 1: goto L_0x006f;
                case 2: goto L_0x006f;
                case 3: goto L_0x006f;
                case 4: goto L_0x006f;
                case 5: goto L_0x006f;
                case 6: goto L_0x006f;
                case 7: goto L_0x006f;
                case 8: goto L_0x006f;
                case 9: goto L_0x006f;
                case 10: goto L_0x006f;
                case 11: goto L_0x006f;
                case 12: goto L_0x006f;
                case 13: goto L_0x006f;
                case 14: goto L_0x006f;
                case 15: goto L_0x006f;
                case 16: goto L_0x006f;
                case 100: goto L_0x006f;
                default: goto L_0x004b;
            }     // Catch:{ IllegalArgumentException -> 0x0067 }
        L_0x004b:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException -> 0x0067 }
            r4 = 45
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0067 }
            r5.<init>(r4)     // Catch:{ IllegalArgumentException -> 0x0067 }
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ IllegalArgumentException -> 0x0067 }
            java.lang.String r4 = " is not a valid enum MobileSubtype"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ IllegalArgumentException -> 0x0067 }
            java.lang.String r2 = r2.toString()     // Catch:{ IllegalArgumentException -> 0x0067 }
            r3.<init>(r2)     // Catch:{ IllegalArgumentException -> 0x0067 }
            throw r3     // Catch:{ IllegalArgumentException -> 0x0067 }
        L_0x0067:
            r2 = move-exception
            r7.zzmg(r1)
            r6.zza(r7, r0)
            goto L_0x0000
        L_0x006f:
            r6.zzprg = r2     // Catch:{ IllegalArgumentException -> 0x0067 }
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfks.zza(com.google.android.gms.internal.zzfjj):com.google.android.gms.internal.zzfks");
    }

    /* access modifiers changed from: private */
    /* renamed from: zzdbc */
    public zzfks clone() {
        try {
            return (zzfks) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfks)) {
            return false;
        }
        zzfks zzfks = (zzfks) obj;
        if (this.zzprf != zzfks.zzprf) {
            return false;
        }
        if (this.zzprg != zzfks.zzprg) {
            return false;
        }
        return (this.zzpnc == null || this.zzpnc.isEmpty()) ? zzfks.zzpnc == null || zzfks.zzpnc.isEmpty() : this.zzpnc.equals(zzfks.zzpnc);
    }

    public final int hashCode() {
        return ((this.zzpnc == null || this.zzpnc.isEmpty()) ? 0 : this.zzpnc.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + this.zzprf) * 31) + this.zzprg) * 31);
    }

    public final void zza(zzfjk zzfjk) throws IOException {
        if (this.zzprf != -1) {
            zzfjk.zzaa(1, this.zzprf);
        }
        if (this.zzprg != 0) {
            zzfjk.zzaa(2, this.zzprg);
        }
        super.zza(zzfjk);
    }

    public final /* synthetic */ zzfjm zzdaf() throws CloneNotSupportedException {
        return (zzfks) clone();
    }

    public final /* synthetic */ zzfjs zzdag() throws CloneNotSupportedException {
        return (zzfks) clone();
    }

    /* access modifiers changed from: protected */
    public final int zzq() {
        int zzq = super.zzq();
        if (this.zzprf != -1) {
            zzq += zzfjk.zzad(1, this.zzprf);
        }
        return this.zzprg != 0 ? zzq + zzfjk.zzad(2, this.zzprg) : zzq;
    }
}
