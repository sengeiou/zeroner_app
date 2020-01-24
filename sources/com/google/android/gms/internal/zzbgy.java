package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.common.util.zzp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class zzbgy extends zzbgq {
    public static final Creator<zzbgy> CREATOR = new zzbgz();
    private final String mClassName;
    private final int zzeck;
    private final zzbgt zzgcm;
    private final Parcel zzgct;
    private final int zzgcu = 2;
    private int zzgcv;
    private int zzgcw;

    zzbgy(int i, Parcel parcel, zzbgt zzbgt) {
        this.zzeck = i;
        this.zzgct = (Parcel) zzbq.checkNotNull(parcel);
        this.zzgcm = zzbgt;
        if (this.zzgcm == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zzgcm.zzalz();
        }
        this.zzgcv = 2;
    }

    private static void zza(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"").append(zzo.zzgr(obj.toString())).append("\"");
                return;
            case 8:
                sb.append("\"").append(zzb.zzk((byte[]) obj)).append("\"");
                return;
            case 9:
                sb.append("\"").append(zzb.zzl((byte[]) obj));
                sb.append("\"");
                return;
            case 10:
                zzp.zza(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v36, types: [double[]] */
    /* JADX WARNING: type inference failed for: r0v37, types: [double[]] */
    /* JADX WARNING: type inference failed for: r0v41, types: [java.math.BigInteger[]] */
    /* JADX WARNING: type inference failed for: r0v42, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r0v45 */
    /* JADX WARNING: type inference failed for: r0v46 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.math.BigInteger[], double[]]
      uses: [double[], java.lang.Object[], ?[OBJECT, ARRAY][]]
      mth insns count: 159
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
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(java.lang.StringBuilder r8, com.google.android.gms.internal.zzbgo<?, ?> r9, android.os.Parcel r10, int r11) {
        /*
            r7 = this;
            r0 = 0
            r2 = 0
            boolean r1 = r9.zzgch
            if (r1 == 0) goto L_0x00cd
            java.lang.String r1 = "["
            r8.append(r1)
            int r1 = r9.zzgcg
            switch(r1) {
                case 0: goto L_0x001a;
                case 1: goto L_0x0035;
                case 2: goto L_0x0064;
                case 3: goto L_0x006c;
                case 4: goto L_0x0074;
                case 5: goto L_0x008b;
                case 6: goto L_0x0093;
                case 7: goto L_0x009b;
                case 8: goto L_0x00a3;
                case 9: goto L_0x00a3;
                case 10: goto L_0x00a3;
                case 11: goto L_0x00ac;
                default: goto L_0x0011;
            }
        L_0x0011:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unknown field type out."
            r0.<init>(r1)
            throw r0
        L_0x001a:
            int[] r0 = com.google.android.gms.internal.zzbfn.zzw(r10, r11)
            int r1 = r0.length
        L_0x001f:
            if (r2 >= r1) goto L_0x0042
            if (r2 == 0) goto L_0x0029
            java.lang.String r3 = ","
            r8.append(r3)
        L_0x0029:
            r3 = r0[r2]
            java.lang.String r3 = java.lang.Integer.toString(r3)
            r8.append(r3)
            int r2 = r2 + 1
            goto L_0x001f
        L_0x0035:
            int r1 = com.google.android.gms.internal.zzbfn.zza(r10, r11)
            int r3 = r10.dataPosition()
            if (r1 != 0) goto L_0x0049
        L_0x003f:
            com.google.android.gms.common.util.zza.zza(r8, (T[]) r0)
        L_0x0042:
            java.lang.String r0 = "]"
            r8.append(r0)
        L_0x0048:
            return
        L_0x0049:
            int r4 = r10.readInt()
            java.math.BigInteger[] r0 = new java.math.BigInteger[r4]
        L_0x004f:
            if (r2 >= r4) goto L_0x005f
            java.math.BigInteger r5 = new java.math.BigInteger
            byte[] r6 = r10.createByteArray()
            r5.<init>(r6)
            r0[r2] = r5
            int r2 = r2 + 1
            goto L_0x004f
        L_0x005f:
            int r1 = r1 + r3
            r10.setDataPosition(r1)
            goto L_0x003f
        L_0x0064:
            long[] r0 = com.google.android.gms.internal.zzbfn.zzx(r10, r11)
            com.google.android.gms.common.util.zza.zza(r8, r0)
            goto L_0x0042
        L_0x006c:
            float[] r0 = com.google.android.gms.internal.zzbfn.zzy(r10, r11)
            com.google.android.gms.common.util.zza.zza(r8, r0)
            goto L_0x0042
        L_0x0074:
            int r1 = com.google.android.gms.internal.zzbfn.zza(r10, r11)
            int r2 = r10.dataPosition()
            if (r1 != 0) goto L_0x0082
        L_0x007e:
            com.google.android.gms.common.util.zza.zza(r8, r0)
            goto L_0x0042
        L_0x0082:
            double[] r0 = r10.createDoubleArray()
            int r1 = r1 + r2
            r10.setDataPosition(r1)
            goto L_0x007e
        L_0x008b:
            java.math.BigDecimal[] r0 = com.google.android.gms.internal.zzbfn.zzz(r10, r11)
            com.google.android.gms.common.util.zza.zza(r8, (T[]) r0)
            goto L_0x0042
        L_0x0093:
            boolean[] r0 = com.google.android.gms.internal.zzbfn.zzv(r10, r11)
            com.google.android.gms.common.util.zza.zza(r8, r0)
            goto L_0x0042
        L_0x009b:
            java.lang.String[] r0 = com.google.android.gms.internal.zzbfn.zzaa(r10, r11)
            com.google.android.gms.common.util.zza.zza(r8, r0)
            goto L_0x0042
        L_0x00a3:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported"
            r0.<init>(r1)
            throw r0
        L_0x00ac:
            android.os.Parcel[] r1 = com.google.android.gms.internal.zzbfn.zzae(r10, r11)
            int r3 = r1.length
            r0 = r2
        L_0x00b2:
            if (r0 >= r3) goto L_0x0042
            if (r0 <= 0) goto L_0x00bc
            java.lang.String r4 = ","
            r8.append(r4)
        L_0x00bc:
            r4 = r1[r0]
            r4.setDataPosition(r2)
            java.util.Map r4 = r9.zzalx()
            r5 = r1[r0]
            r7.zza(r8, r4, r5)
            int r0 = r0 + 1
            goto L_0x00b2
        L_0x00cd:
            int r0 = r9.zzgcg
            switch(r0) {
                case 0: goto L_0x00db;
                case 1: goto L_0x00e4;
                case 2: goto L_0x00ed;
                case 3: goto L_0x00f6;
                case 4: goto L_0x00ff;
                case 5: goto L_0x0108;
                case 6: goto L_0x0111;
                case 7: goto L_0x011a;
                case 8: goto L_0x0135;
                case 9: goto L_0x0150;
                case 10: goto L_0x016a;
                case 11: goto L_0x01cf;
                default: goto L_0x00d2;
            }
        L_0x00d2:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unknown field type out"
            r0.<init>(r1)
            throw r0
        L_0x00db:
            int r0 = com.google.android.gms.internal.zzbfn.zzg(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x00e4:
            java.math.BigInteger r0 = com.google.android.gms.internal.zzbfn.zzk(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x00ed:
            long r0 = com.google.android.gms.internal.zzbfn.zzi(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x00f6:
            float r0 = com.google.android.gms.internal.zzbfn.zzl(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x00ff:
            double r0 = com.google.android.gms.internal.zzbfn.zzn(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x0108:
            java.math.BigDecimal r0 = com.google.android.gms.internal.zzbfn.zzp(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x0111:
            boolean r0 = com.google.android.gms.internal.zzbfn.zzc(r10, r11)
            r8.append(r0)
            goto L_0x0048
        L_0x011a:
            java.lang.String r0 = com.google.android.gms.internal.zzbfn.zzq(r10, r11)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = com.google.android.gms.common.util.zzo.zzgr(r0)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "\""
            r0.append(r1)
            goto L_0x0048
        L_0x0135:
            byte[] r0 = com.google.android.gms.internal.zzbfn.zzt(r10, r11)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = com.google.android.gms.common.util.zzb.zzk(r0)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "\""
            r0.append(r1)
            goto L_0x0048
        L_0x0150:
            byte[] r0 = com.google.android.gms.internal.zzbfn.zzt(r10, r11)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = com.google.android.gms.common.util.zzb.zzl(r0)
            r1.append(r0)
            java.lang.String r0 = "\""
            r8.append(r0)
            goto L_0x0048
        L_0x016a:
            android.os.Bundle r3 = com.google.android.gms.internal.zzbfn.zzs(r10, r11)
            java.util.Set r1 = r3.keySet()
            r1.size()
            java.lang.String r0 = "{"
            r8.append(r0)
            r0 = 1
            java.util.Iterator r4 = r1.iterator()
            r1 = r0
        L_0x0181:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x01c7
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            if (r1 != 0) goto L_0x0195
            java.lang.String r1 = ","
            r8.append(r1)
        L_0x0195:
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r5 = "\""
            r1.append(r5)
            java.lang.String r1 = ":"
            r8.append(r1)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = r3.getString(r0)
            java.lang.String r0 = com.google.android.gms.common.util.zzo.zzgr(r0)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "\""
            r0.append(r1)
            r1 = r2
            goto L_0x0181
        L_0x01c7:
            java.lang.String r0 = "}"
            r8.append(r0)
            goto L_0x0048
        L_0x01cf:
            android.os.Parcel r0 = com.google.android.gms.internal.zzbfn.zzad(r10, r11)
            r0.setDataPosition(r2)
            java.util.Map r1 = r9.zzalx()
            r7.zza(r8, r1, r0)
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbgy.zza(java.lang.StringBuilder, com.google.android.gms.internal.zzbgo, android.os.Parcel, int):void");
    }

    private final void zza(StringBuilder sb, Map<String, zzbgo<?, ?>> map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        for (Entry entry : map.entrySet()) {
            sparseArray.put(((zzbgo) entry.getValue()).zzgcj, entry);
        }
        sb.append('{');
        int zzd = zzbfn.zzd(parcel);
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            Entry entry2 = (Entry) sparseArray.get(65535 & readInt);
            if (entry2 != null) {
                if (z) {
                    sb.append(",");
                }
                String str = (String) entry2.getKey();
                zzbgo zzbgo = (zzbgo) entry2.getValue();
                sb.append("\"").append(str).append("\":");
                if (zzbgo.zzalw()) {
                    switch (zzbgo.zzgcg) {
                        case 0:
                            zzb(sb, zzbgo, zza(zzbgo, Integer.valueOf(zzbfn.zzg(parcel, readInt))));
                            break;
                        case 1:
                            zzb(sb, zzbgo, zza(zzbgo, zzbfn.zzk(parcel, readInt)));
                            break;
                        case 2:
                            zzb(sb, zzbgo, zza(zzbgo, Long.valueOf(zzbfn.zzi(parcel, readInt))));
                            break;
                        case 3:
                            zzb(sb, zzbgo, zza(zzbgo, Float.valueOf(zzbfn.zzl(parcel, readInt))));
                            break;
                        case 4:
                            zzb(sb, zzbgo, zza(zzbgo, Double.valueOf(zzbfn.zzn(parcel, readInt))));
                            break;
                        case 5:
                            zzb(sb, zzbgo, zza(zzbgo, zzbfn.zzp(parcel, readInt)));
                            break;
                        case 6:
                            zzb(sb, zzbgo, zza(zzbgo, Boolean.valueOf(zzbfn.zzc(parcel, readInt))));
                            break;
                        case 7:
                            zzb(sb, zzbgo, zza(zzbgo, zzbfn.zzq(parcel, readInt)));
                            break;
                        case 8:
                        case 9:
                            zzb(sb, zzbgo, zza(zzbgo, zzbfn.zzt(parcel, readInt)));
                            break;
                        case 10:
                            zzb(sb, zzbgo, zza(zzbgo, zzl(zzbfn.zzs(parcel, readInt))));
                            break;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException("Unknown field out type = " + zzbgo.zzgcg);
                    }
                } else {
                    zza(sb, zzbgo, parcel, readInt);
                }
                z = true;
            }
        }
        if (parcel.dataPosition() != zzd) {
            throw new zzbfo("Overread allowed size end=" + zzd, parcel);
        }
        sb.append('}');
    }

    private Parcel zzamb() {
        switch (this.zzgcv) {
            case 0:
                this.zzgcw = zzbfp.zze(this.zzgct);
                break;
            case 1:
                break;
        }
        zzbfp.zzai(this.zzgct, this.zzgcw);
        this.zzgcv = 2;
        return this.zzgct;
    }

    private final void zzb(StringBuilder sb, zzbgo<?, ?> zzbgo, Object obj) {
        if (zzbgo.zzgcf) {
            ArrayList arrayList = (ArrayList) obj;
            sb.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    sb.append(",");
                }
                zza(sb, zzbgo.zzgce, arrayList.get(i));
            }
            sb.append("]");
            return;
        }
        zza(sb, zzbgo.zzgce, obj);
    }

    private static HashMap<String, String> zzl(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    public String toString() {
        zzbq.checkNotNull(this.zzgcm, "Cannot convert to JSON on client side.");
        Parcel zzamb = zzamb();
        zzamb.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        zza(sb, this.zzgcm.zzgq(this.mClassName), zzamb);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbgt zzbgt;
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, zzamb(), false);
        switch (this.zzgcu) {
            case 0:
                zzbgt = null;
                break;
            case 1:
                zzbgt = this.zzgcm;
                break;
            case 2:
                zzbgt = this.zzgcm;
                break;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.zzgcu);
        }
        zzbfp.zza(parcel, 3, (Parcelable) zzbgt, i, false);
        zzbfp.zzai(parcel, zze);
    }

    public final Map<String, zzbgo<?, ?>> zzaav() {
        if (this.zzgcm == null) {
            return null;
        }
        return this.zzgcm.zzgq(this.mClassName);
    }

    public final Object zzgo(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final boolean zzgp(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }
}
