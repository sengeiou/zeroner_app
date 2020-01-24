package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfkn extends zzfjm<zzfkn> implements Cloneable {
    private String[] zzpqb;
    private String[] zzpqc;
    private int[] zzpqd;
    private long[] zzpqe;
    private long[] zzpqf;

    public zzfkn() {
        this.zzpqb = zzfjv.EMPTY_STRING_ARRAY;
        this.zzpqc = zzfjv.EMPTY_STRING_ARRAY;
        this.zzpqd = zzfjv.zzpnp;
        this.zzpqe = zzfjv.zzpnq;
        this.zzpqf = zzfjv.zzpnq;
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzdaw */
    public zzfkn clone() {
        try {
            zzfkn zzfkn = (zzfkn) super.clone();
            if (this.zzpqb != null && this.zzpqb.length > 0) {
                zzfkn.zzpqb = (String[]) this.zzpqb.clone();
            }
            if (this.zzpqc != null && this.zzpqc.length > 0) {
                zzfkn.zzpqc = (String[]) this.zzpqc.clone();
            }
            if (this.zzpqd != null && this.zzpqd.length > 0) {
                zzfkn.zzpqd = (int[]) this.zzpqd.clone();
            }
            if (this.zzpqe != null && this.zzpqe.length > 0) {
                zzfkn.zzpqe = (long[]) this.zzpqe.clone();
            }
            if (this.zzpqf != null && this.zzpqf.length > 0) {
                zzfkn.zzpqf = (long[]) this.zzpqf.clone();
            }
            return zzfkn;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfkn)) {
            return false;
        }
        zzfkn zzfkn = (zzfkn) obj;
        if (!zzfjq.equals((Object[]) this.zzpqb, (Object[]) zzfkn.zzpqb)) {
            return false;
        }
        if (!zzfjq.equals((Object[]) this.zzpqc, (Object[]) zzfkn.zzpqc)) {
            return false;
        }
        if (!zzfjq.equals(this.zzpqd, zzfkn.zzpqd)) {
            return false;
        }
        if (!zzfjq.equals(this.zzpqe, zzfkn.zzpqe)) {
            return false;
        }
        if (!zzfjq.equals(this.zzpqf, zzfkn.zzpqf)) {
            return false;
        }
        return (this.zzpnc == null || this.zzpnc.isEmpty()) ? zzfkn.zzpnc == null || zzfkn.zzpnc.isEmpty() : this.zzpnc.equals(zzfkn.zzpnc);
    }

    public final int hashCode() {
        return ((this.zzpnc == null || this.zzpnc.isEmpty()) ? 0 : this.zzpnc.hashCode()) + ((((((((((((getClass().getName().hashCode() + 527) * 31) + zzfjq.hashCode((Object[]) this.zzpqb)) * 31) + zzfjq.hashCode((Object[]) this.zzpqc)) * 31) + zzfjq.hashCode(this.zzpqd)) * 31) + zzfjq.hashCode(this.zzpqe)) * 31) + zzfjq.hashCode(this.zzpqf)) * 31);
    }

    public final /* synthetic */ zzfjs zza(zzfjj zzfjj) throws IOException {
        while (true) {
            int zzcvt = zzfjj.zzcvt();
            switch (zzcvt) {
                case 0:
                    break;
                case 10:
                    int zzb = zzfjv.zzb(zzfjj, 10);
                    int length = this.zzpqb == null ? 0 : this.zzpqb.length;
                    String[] strArr = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzpqb, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = zzfjj.readString();
                        zzfjj.zzcvt();
                        length++;
                    }
                    strArr[length] = zzfjj.readString();
                    this.zzpqb = strArr;
                    continue;
                case 18:
                    int zzb2 = zzfjv.zzb(zzfjj, 18);
                    int length2 = this.zzpqc == null ? 0 : this.zzpqc.length;
                    String[] strArr2 = new String[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzpqc, 0, strArr2, 0, length2);
                    }
                    while (length2 < strArr2.length - 1) {
                        strArr2[length2] = zzfjj.readString();
                        zzfjj.zzcvt();
                        length2++;
                    }
                    strArr2[length2] = zzfjj.readString();
                    this.zzpqc = strArr2;
                    continue;
                case 24:
                    int zzb3 = zzfjv.zzb(zzfjj, 24);
                    int length3 = this.zzpqd == null ? 0 : this.zzpqd.length;
                    int[] iArr = new int[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzpqd, 0, iArr, 0, length3);
                    }
                    while (length3 < iArr.length - 1) {
                        iArr[length3] = zzfjj.zzcvw();
                        zzfjj.zzcvt();
                        length3++;
                    }
                    iArr[length3] = zzfjj.zzcvw();
                    this.zzpqd = iArr;
                    continue;
                case 26:
                    int zzks = zzfjj.zzks(zzfjj.zzcwi());
                    int position = zzfjj.getPosition();
                    int i = 0;
                    while (zzfjj.zzcwk() > 0) {
                        zzfjj.zzcvw();
                        i++;
                    }
                    zzfjj.zzmg(position);
                    int length4 = this.zzpqd == null ? 0 : this.zzpqd.length;
                    int[] iArr2 = new int[(i + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzpqd, 0, iArr2, 0, length4);
                    }
                    while (length4 < iArr2.length) {
                        iArr2[length4] = zzfjj.zzcvw();
                        length4++;
                    }
                    this.zzpqd = iArr2;
                    zzfjj.zzkt(zzks);
                    continue;
                case 32:
                    int zzb4 = zzfjv.zzb(zzfjj, 32);
                    int length5 = this.zzpqe == null ? 0 : this.zzpqe.length;
                    long[] jArr = new long[(zzb4 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzpqe, 0, jArr, 0, length5);
                    }
                    while (length5 < jArr.length - 1) {
                        jArr[length5] = zzfjj.zzcvv();
                        zzfjj.zzcvt();
                        length5++;
                    }
                    jArr[length5] = zzfjj.zzcvv();
                    this.zzpqe = jArr;
                    continue;
                case 34:
                    int zzks2 = zzfjj.zzks(zzfjj.zzcwi());
                    int position2 = zzfjj.getPosition();
                    int i2 = 0;
                    while (zzfjj.zzcwk() > 0) {
                        zzfjj.zzcvv();
                        i2++;
                    }
                    zzfjj.zzmg(position2);
                    int length6 = this.zzpqe == null ? 0 : this.zzpqe.length;
                    long[] jArr2 = new long[(i2 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzpqe, 0, jArr2, 0, length6);
                    }
                    while (length6 < jArr2.length) {
                        jArr2[length6] = zzfjj.zzcvv();
                        length6++;
                    }
                    this.zzpqe = jArr2;
                    zzfjj.zzkt(zzks2);
                    continue;
                case 40:
                    int zzb5 = zzfjv.zzb(zzfjj, 40);
                    int length7 = this.zzpqf == null ? 0 : this.zzpqf.length;
                    long[] jArr3 = new long[(zzb5 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzpqf, 0, jArr3, 0, length7);
                    }
                    while (length7 < jArr3.length - 1) {
                        jArr3[length7] = zzfjj.zzcvv();
                        zzfjj.zzcvt();
                        length7++;
                    }
                    jArr3[length7] = zzfjj.zzcvv();
                    this.zzpqf = jArr3;
                    continue;
                case 42:
                    int zzks3 = zzfjj.zzks(zzfjj.zzcwi());
                    int position3 = zzfjj.getPosition();
                    int i3 = 0;
                    while (zzfjj.zzcwk() > 0) {
                        zzfjj.zzcvv();
                        i3++;
                    }
                    zzfjj.zzmg(position3);
                    int length8 = this.zzpqf == null ? 0 : this.zzpqf.length;
                    long[] jArr4 = new long[(i3 + length8)];
                    if (length8 != 0) {
                        System.arraycopy(this.zzpqf, 0, jArr4, 0, length8);
                    }
                    while (length8 < jArr4.length) {
                        jArr4[length8] = zzfjj.zzcvv();
                        length8++;
                    }
                    this.zzpqf = jArr4;
                    zzfjj.zzkt(zzks3);
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
        if (this.zzpqb != null && this.zzpqb.length > 0) {
            for (String str : this.zzpqb) {
                if (str != null) {
                    zzfjk.zzn(1, str);
                }
            }
        }
        if (this.zzpqc != null && this.zzpqc.length > 0) {
            for (String str2 : this.zzpqc) {
                if (str2 != null) {
                    zzfjk.zzn(2, str2);
                }
            }
        }
        if (this.zzpqd != null && this.zzpqd.length > 0) {
            for (int zzaa : this.zzpqd) {
                zzfjk.zzaa(3, zzaa);
            }
        }
        if (this.zzpqe != null && this.zzpqe.length > 0) {
            for (long zzf : this.zzpqe) {
                zzfjk.zzf(4, zzf);
            }
        }
        if (this.zzpqf != null && this.zzpqf.length > 0) {
            for (long zzf2 : this.zzpqf) {
                zzfjk.zzf(5, zzf2);
            }
        }
        super.zza(zzfjk);
    }

    public final /* synthetic */ zzfjm zzdaf() throws CloneNotSupportedException {
        return (zzfkn) clone();
    }

    public final /* synthetic */ zzfjs zzdag() throws CloneNotSupportedException {
        return (zzfkn) clone();
    }

    /* access modifiers changed from: protected */
    public final int zzq() {
        int i;
        int zzq = super.zzq();
        if (this.zzpqb == null || this.zzpqb.length <= 0) {
            i = zzq;
        } else {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzpqb) {
                if (str != null) {
                    i3++;
                    i2 += zzfjk.zztt(str);
                }
            }
            i = zzq + i2 + (i3 * 1);
        }
        if (this.zzpqc != null && this.zzpqc.length > 0) {
            int i4 = 0;
            int i5 = 0;
            for (String str2 : this.zzpqc) {
                if (str2 != null) {
                    i5++;
                    i4 += zzfjk.zztt(str2);
                }
            }
            i = i + i4 + (i5 * 1);
        }
        if (this.zzpqd != null && this.zzpqd.length > 0) {
            int i6 = 0;
            for (int zzlh : this.zzpqd) {
                i6 += zzfjk.zzlh(zzlh);
            }
            i = i + i6 + (this.zzpqd.length * 1);
        }
        if (this.zzpqe != null && this.zzpqe.length > 0) {
            int i7 = 0;
            for (long zzdi : this.zzpqe) {
                i7 += zzfjk.zzdi(zzdi);
            }
            i = i + i7 + (this.zzpqe.length * 1);
        }
        if (this.zzpqf == null || this.zzpqf.length <= 0) {
            return i;
        }
        int i8 = 0;
        for (long zzdi2 : this.zzpqf) {
            i8 += zzfjk.zzdi(zzdi2);
        }
        return i + i8 + (this.zzpqf.length * 1);
    }
}
