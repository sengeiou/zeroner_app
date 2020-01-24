package com.google.android.gms.internal;

import com.google.android.gms.internal.zzffu.zzg;
import java.io.IOException;

public final class zzfkg extends zzffu<zzfkg, zza> implements zzfhg {
    private static volatile zzfhk<zzfkg> zzbbk;
    /* access modifiers changed from: private */
    public static final zzfkg zzppn;
    private int zzlwy;
    private int zzppk;
    private String zzppl = "";
    private zzfgd<zzfep> zzppm = zzcxo();

    public static final class zza extends com.google.android.gms.internal.zzffu.zza<zzfkg, zza> implements zzfhg {
        private zza() {
            super(zzfkg.zzppn);
        }

        /* synthetic */ zza(zzfkh zzfkh) {
            this();
        }
    }

    static {
        zzfkg zzfkg = new zzfkg();
        zzppn = zzfkg;
        zzfkg.zza(zzg.zzphh, (Object) null, (Object) null);
        zzfkg.zzpgr.zzbiy();
    }

    private zzfkg() {
    }

    public static zzfkg zzdap() {
        return zzppn;
    }

    public final int getCode() {
        return this.zzppk;
    }

    public final String getMessage() {
        return this.zzppl;
    }

    /* JADX WARNING: type inference failed for: r6v2, types: [java.lang.Byte] */
    /* JADX WARNING: type inference failed for: r6v3, types: [com.google.android.gms.internal.zzfhk<com.google.android.gms.internal.zzfkg>] */
    /* JADX WARNING: type inference failed for: r6v5, types: [com.google.android.gms.internal.zzfkg$zza] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: CFG modification limit reached, blocks count: 182 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object zza(int r7, java.lang.Object r8, java.lang.Object r9) {
        /*
            r6 = this;
            r0 = 0
            r2 = 0
            r1 = 1
            int[] r3 = com.google.android.gms.internal.zzfkh.zzbbi
            int r4 = r7 + -1
            r3 = r3[r4]
            switch(r3) {
                case 1: goto L_0x0012;
                case 2: goto L_0x0018;
                case 3: goto L_0x001b;
                case 4: goto L_0x0022;
                case 5: goto L_0x0028;
                case 6: goto L_0x0079;
                case 7: goto L_0x00f1;
                case 8: goto L_0x00f5;
                case 9: goto L_0x0111;
                case 10: goto L_0x0117;
                default: goto L_0x000c;
            }
        L_0x000c:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            r0.<init>()
            throw r0
        L_0x0012:
            com.google.android.gms.internal.zzfkg r6 = new com.google.android.gms.internal.zzfkg
            r6.<init>()
        L_0x0017:
            return r6
        L_0x0018:
            com.google.android.gms.internal.zzfkg r6 = zzppn
            goto L_0x0017
        L_0x001b:
            com.google.android.gms.internal.zzfgd<com.google.android.gms.internal.zzfep> r1 = r6.zzppm
            r1.zzbiy()
            r6 = r0
            goto L_0x0017
        L_0x0022:
            com.google.android.gms.internal.zzfkg$zza r6 = new com.google.android.gms.internal.zzfkg$zza
            r6.<init>(r0)
            goto L_0x0017
        L_0x0028:
            com.google.android.gms.internal.zzffu$zzh r8 = (com.google.android.gms.internal.zzffu.zzh) r8
            com.google.android.gms.internal.zzfkg r9 = (com.google.android.gms.internal.zzfkg) r9
            int r0 = r6.zzppk
            if (r0 == 0) goto L_0x0071
            r0 = r1
        L_0x0031:
            int r4 = r6.zzppk
            int r3 = r9.zzppk
            if (r3 == 0) goto L_0x0073
            r3 = r1
        L_0x0038:
            int r5 = r9.zzppk
            int r0 = r8.zza(r0, r4, r3, r5)
            r6.zzppk = r0
            java.lang.String r0 = r6.zzppl
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0075
            r0 = r1
        L_0x0049:
            java.lang.String r3 = r6.zzppl
            java.lang.String r4 = r9.zzppl
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x0077
        L_0x0053:
            java.lang.String r2 = r9.zzppl
            java.lang.String r0 = r8.zza(r0, r3, r1, r2)
            r6.zzppl = r0
            com.google.android.gms.internal.zzfgd<com.google.android.gms.internal.zzfep> r0 = r6.zzppm
            com.google.android.gms.internal.zzfgd<com.google.android.gms.internal.zzfep> r1 = r9.zzppm
            com.google.android.gms.internal.zzfgd r0 = r8.zza(r0, r1)
            r6.zzppm = r0
            com.google.android.gms.internal.zzffu$zzf r0 = com.google.android.gms.internal.zzffu.zzf.zzphb
            if (r8 != r0) goto L_0x0017
            int r0 = r6.zzlwy
            int r1 = r9.zzlwy
            r0 = r0 | r1
            r6.zzlwy = r0
            goto L_0x0017
        L_0x0071:
            r0 = r2
            goto L_0x0031
        L_0x0073:
            r3 = r2
            goto L_0x0038
        L_0x0075:
            r0 = r2
            goto L_0x0049
        L_0x0077:
            r1 = r2
            goto L_0x0053
        L_0x0079:
            com.google.android.gms.internal.zzffb r8 = (com.google.android.gms.internal.zzffb) r8
            com.google.android.gms.internal.zzffm r9 = (com.google.android.gms.internal.zzffm) r9
            if (r9 != 0) goto L_0x0086
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            r0.<init>()
            throw r0
        L_0x0085:
            r2 = r1
        L_0x0086:
            if (r2 != 0) goto L_0x00f1
            int r0 = r8.zzcvt()     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            switch(r0) {
                case 0: goto L_0x0085;
                case 8: goto L_0x0097;
                case 18: goto L_0x00ab;
                case 26: goto L_0x00c6;
                default: goto L_0x008f;
            }     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
        L_0x008f:
            boolean r0 = r6.zza(r0, r8)     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            if (r0 != 0) goto L_0x0086
            r2 = r1
            goto L_0x0086
        L_0x0097:
            int r0 = r8.zzcvw()     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            r6.zzppk = r0     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            goto L_0x0086
        L_0x009e:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ all -> 0x00a9 }
            com.google.android.gms.internal.zzfge r0 = r0.zzi(r6)     // Catch:{ all -> 0x00a9 }
            r1.<init>(r0)     // Catch:{ all -> 0x00a9 }
            throw r1     // Catch:{ all -> 0x00a9 }
        L_0x00a9:
            r0 = move-exception
            throw r0
        L_0x00ab:
            java.lang.String r0 = r8.zzcwa()     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            r6.zzppl = r0     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            goto L_0x0086
        L_0x00b2:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ all -> 0x00a9 }
            com.google.android.gms.internal.zzfge r2 = new com.google.android.gms.internal.zzfge     // Catch:{ all -> 0x00a9 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00a9 }
            r2.<init>(r0)     // Catch:{ all -> 0x00a9 }
            com.google.android.gms.internal.zzfge r0 = r2.zzi(r6)     // Catch:{ all -> 0x00a9 }
            r1.<init>(r0)     // Catch:{ all -> 0x00a9 }
            throw r1     // Catch:{ all -> 0x00a9 }
        L_0x00c6:
            com.google.android.gms.internal.zzfgd<com.google.android.gms.internal.zzfep> r0 = r6.zzppm     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            boolean r0 = r0.zzcvi()     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            if (r0 != 0) goto L_0x00de
            com.google.android.gms.internal.zzfgd<com.google.android.gms.internal.zzfep> r3 = r6.zzppm     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            int r0 = r3.size()     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            if (r0 != 0) goto L_0x00ee
            r0 = 10
        L_0x00d8:
            com.google.android.gms.internal.zzfgd r0 = r3.zzly(r0)     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            r6.zzppm = r0     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
        L_0x00de:
            com.google.android.gms.internal.zzfgd<com.google.android.gms.internal.zzfep> r3 = r6.zzppm     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            com.google.android.gms.internal.zzfep r0 = com.google.android.gms.internal.zzfep.zzcvk()     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            com.google.android.gms.internal.zzffu r0 = r8.zza(r0, r9)     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            com.google.android.gms.internal.zzfep r0 = (com.google.android.gms.internal.zzfep) r0     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            r3.add(r0)     // Catch:{ zzfge -> 0x009e, IOException -> 0x00b2 }
            goto L_0x0086
        L_0x00ee:
            int r0 = r0 << 1
            goto L_0x00d8
        L_0x00f1:
            com.google.android.gms.internal.zzfkg r6 = zzppn
            goto L_0x0017
        L_0x00f5:
            com.google.android.gms.internal.zzfhk<com.google.android.gms.internal.zzfkg> r0 = zzbbk
            if (r0 != 0) goto L_0x010a
            java.lang.Class<com.google.android.gms.internal.zzfkg> r1 = com.google.android.gms.internal.zzfkg.class
            monitor-enter(r1)
            com.google.android.gms.internal.zzfhk<com.google.android.gms.internal.zzfkg> r0 = zzbbk     // Catch:{ all -> 0x010e }
            if (r0 != 0) goto L_0x0109
            com.google.android.gms.internal.zzffu$zzb r0 = new com.google.android.gms.internal.zzffu$zzb     // Catch:{ all -> 0x010e }
            com.google.android.gms.internal.zzfkg r2 = zzppn     // Catch:{ all -> 0x010e }
            r0.<init>(r2)     // Catch:{ all -> 0x010e }
            zzbbk = r0     // Catch:{ all -> 0x010e }
        L_0x0109:
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
        L_0x010a:
            com.google.android.gms.internal.zzfhk<com.google.android.gms.internal.zzfkg> r6 = zzbbk
            goto L_0x0017
        L_0x010e:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            throw r0
        L_0x0111:
            java.lang.Byte r6 = java.lang.Byte.valueOf(r1)
            goto L_0x0017
        L_0x0117:
            r6 = r0
            goto L_0x0017
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfkg.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final void zza(zzffg zzffg) throws IOException {
        if (this.zzppk != 0) {
            zzffg.zzaa(1, this.zzppk);
        }
        if (!this.zzppl.isEmpty()) {
            zzffg.zzn(2, this.zzppl);
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.zzppm.size()) {
                zzffg.zza(3, (zzfhe) this.zzppm.get(i2));
                i = i2 + 1;
            } else {
                this.zzpgr.zza(zzffg);
                return;
            }
        }
    }

    public final int zzho() {
        int i = 0;
        int i2 = this.zzpgs;
        if (i2 != -1) {
            return i2;
        }
        int i3 = this.zzppk != 0 ? zzffg.zzad(1, this.zzppk) + 0 : 0;
        if (!this.zzppl.isEmpty()) {
            i3 += zzffg.zzo(2, this.zzppl);
        }
        while (true) {
            int i4 = i3;
            if (i < this.zzppm.size()) {
                i3 = zzffg.zzc(3, (zzfhe) this.zzppm.get(i)) + i4;
                i++;
            } else {
                int zzho = this.zzpgr.zzho() + i4;
                this.zzpgs = zzho;
                return zzho;
            }
        }
    }
}
