package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzz extends zzbfm {
    public static final Creator<zzz> CREATOR = new zzaa();
    private int version;
    private int zzfzr;
    private int zzfzs;
    String zzfzt;
    IBinder zzfzu;
    Scope[] zzfzv;
    Bundle zzfzw;
    Account zzfzx;
    zzc[] zzfzy;

    public zzz(int i) {
        this.version = 3;
        this.zzfzs = zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzfzr = i;
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.accounts.Account] */
    /* JADX WARNING: type inference failed for: r0v4, types: [com.google.android.gms.common.internal.zzap] */
    /* JADX WARNING: type inference failed for: r0v5, types: [com.google.android.gms.common.internal.zzan] */
    /* JADX WARNING: type inference failed for: r0v6, types: [com.google.android.gms.common.internal.zzan] */
    /* JADX WARNING: type inference failed for: r0v7, types: [android.accounts.Account] */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.accounts.Account, com.google.android.gms.common.internal.zzap, com.google.android.gms.common.internal.zzan]
      uses: [android.accounts.Account, com.google.android.gms.common.internal.zzan]
      mth insns count: 30
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    zzz(int r3, int r4, int r5, java.lang.String r6, android.os.IBinder r7, com.google.android.gms.common.api.Scope[] r8, android.os.Bundle r9, android.accounts.Account r10, com.google.android.gms.common.zzc[] r11) {
        /*
            r2 = this;
            r0 = 0
            r2.<init>()
            r2.version = r3
            r2.zzfzr = r4
            r2.zzfzs = r5
            java.lang.String r1 = "com.google.android.gms"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "com.google.android.gms"
            r2.zzfzt = r1
        L_0x0018:
            r1 = 2
            if (r3 >= r1) goto L_0x0043
            if (r7 == 0) goto L_0x0023
            if (r7 != 0) goto L_0x002f
        L_0x001f:
            android.accounts.Account r0 = com.google.android.gms.common.internal.zza.zza(r0)
        L_0x0023:
            r2.zzfzx = r0
        L_0x0025:
            r2.zzfzv = r8
            r2.zzfzw = r9
            r2.zzfzy = r11
            return
        L_0x002c:
            r2.zzfzt = r6
            goto L_0x0018
        L_0x002f:
            java.lang.String r0 = "com.google.android.gms.common.internal.IAccountAccessor"
            android.os.IInterface r0 = r7.queryLocalInterface(r0)
            boolean r1 = r0 instanceof com.google.android.gms.common.internal.zzan
            if (r1 == 0) goto L_0x003d
            com.google.android.gms.common.internal.zzan r0 = (com.google.android.gms.common.internal.zzan) r0
            goto L_0x001f
        L_0x003d:
            com.google.android.gms.common.internal.zzap r0 = new com.google.android.gms.common.internal.zzap
            r0.<init>(r7)
            goto L_0x001f
        L_0x0043:
            r2.zzfzu = r7
            r2.zzfzx = r10
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzz.<init>(int, int, int, java.lang.String, android.os.IBinder, com.google.android.gms.common.api.Scope[], android.os.Bundle, android.accounts.Account, com.google.android.gms.common.zzc[]):void");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.version);
        zzbfp.zzc(parcel, 2, this.zzfzr);
        zzbfp.zzc(parcel, 3, this.zzfzs);
        zzbfp.zza(parcel, 4, this.zzfzt, false);
        zzbfp.zza(parcel, 5, this.zzfzu, false);
        zzbfp.zza(parcel, 6, (T[]) this.zzfzv, i, false);
        zzbfp.zza(parcel, 7, this.zzfzw, false);
        zzbfp.zza(parcel, 8, (Parcelable) this.zzfzx, i, false);
        zzbfp.zza(parcel, 10, (T[]) this.zzfzy, i, false);
        zzbfp.zzai(parcel, zze);
    }
}
