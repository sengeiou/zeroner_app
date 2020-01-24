package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class zzctx extends zzbfm {
    public static final Creator<zzctx> CREATOR = new zzcud();
    private static byte[][] zzfiz = new byte[0][];
    private static zzctx zzjwk = new zzctx("", null, zzfiz, zzfiz, zzfiz, zzfiz, null, null);
    private static final zzcuc zzjwt = new zzcty();
    private static final zzcuc zzjwu = new zzctz();
    private static final zzcuc zzjwv = new zzcua();
    private static final zzcuc zzjww = new zzcub();
    private String zzjwl;
    private byte[] zzjwm;
    private byte[][] zzjwn;
    private byte[][] zzjwo;
    private byte[][] zzjwp;
    private byte[][] zzjwq;
    private int[] zzjwr;
    private byte[][] zzjws;

    public zzctx(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.zzjwl = str;
        this.zzjwm = bArr;
        this.zzjwn = bArr2;
        this.zzjwo = bArr3;
        this.zzjwp = bArr4;
        this.zzjwq = bArr5;
        this.zzjwr = iArr;
        this.zzjws = bArr6;
    }

    private static void zza(StringBuilder sb, String str, int[] iArr) {
        sb.append(str);
        sb.append("=");
        if (iArr == null) {
            sb.append("null");
            return;
        }
        sb.append("(");
        int length = iArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(i2);
            i++;
            z = false;
        }
        sb.append(")");
    }

    private static void zza(StringBuilder sb, String str, byte[][] bArr) {
        sb.append(str);
        sb.append("=");
        if (bArr == null) {
            sb.append("null");
            return;
        }
        sb.append("(");
        int length = bArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            byte[] bArr2 = bArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append("'");
            sb.append(Base64.encodeToString(bArr2, 3));
            sb.append("'");
            i++;
            z = false;
        }
        sb.append(")");
    }

    private static List<String> zzb(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte[] encodeToString : bArr) {
            arrayList.add(Base64.encodeToString(encodeToString, 3));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> zzd(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzctx)) {
            return false;
        }
        zzctx zzctx = (zzctx) obj;
        return zzcuh.equals(this.zzjwl, zzctx.zzjwl) && Arrays.equals(this.zzjwm, zzctx.zzjwm) && zzcuh.equals(zzb(this.zzjwn), zzb(zzctx.zzjwn)) && zzcuh.equals(zzb(this.zzjwo), zzb(zzctx.zzjwo)) && zzcuh.equals(zzb(this.zzjwp), zzb(zzctx.zzjwp)) && zzcuh.equals(zzb(this.zzjwq), zzb(zzctx.zzjwq)) && zzcuh.equals(zzd(this.zzjwr), zzd(zzctx.zzjwr)) && zzcuh.equals(zzb(this.zzjws), zzb(zzctx.zzjws));
    }

    public final String toString() {
        String sb;
        StringBuilder sb2 = new StringBuilder("ExperimentTokens");
        sb2.append("(");
        if (this.zzjwl == null) {
            sb = "null";
        } else {
            String str = "'";
            String str2 = this.zzjwl;
            String str3 = "'";
            sb = new StringBuilder(String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append(str2).append(str3).toString();
        }
        sb2.append(sb);
        sb2.append(", ");
        byte[] bArr = this.zzjwm;
        sb2.append("direct");
        sb2.append("=");
        if (bArr == null) {
            sb2.append("null");
        } else {
            sb2.append("'");
            sb2.append(Base64.encodeToString(bArr, 3));
            sb2.append("'");
        }
        sb2.append(", ");
        zza(sb2, "GAIA", this.zzjwn);
        sb2.append(", ");
        zza(sb2, "PSEUDO", this.zzjwo);
        sb2.append(", ");
        zza(sb2, "ALWAYS", this.zzjwp);
        sb2.append(", ");
        zza(sb2, "OTHER", this.zzjwq);
        sb2.append(", ");
        zza(sb2, "weak", this.zzjwr);
        sb2.append(", ");
        zza(sb2, "directs", this.zzjws);
        sb2.append(")");
        return sb2.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzjwl, false);
        zzbfp.zza(parcel, 3, this.zzjwm, false);
        zzbfp.zza(parcel, 4, this.zzjwn, false);
        zzbfp.zza(parcel, 5, this.zzjwo, false);
        zzbfp.zza(parcel, 6, this.zzjwp, false);
        zzbfp.zza(parcel, 7, this.zzjwq, false);
        zzbfp.zza(parcel, 8, this.zzjwr, false);
        zzbfp.zza(parcel, 9, this.zzjws, false);
        zzbfp.zzai(parcel, zze);
    }
}
