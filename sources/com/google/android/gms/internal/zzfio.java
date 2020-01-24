package com.google.android.gms.internal;

import com.google.android.gms.internal.zzffu.zzg;
import java.io.IOException;
import java.util.Arrays;

public final class zzfio {
    private static final zzfio zzpkp = new zzfio(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzpfc;
    private int zzpgs;
    private int[] zzpkq;
    private Object[] zzpkr;

    private zzfio() {
        this(0, new int[8], new Object[8], true);
    }

    private zzfio(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzpgs = -1;
        this.count = i;
        this.zzpkq = iArr;
        this.zzpkr = objArr;
        this.zzpfc = z;
    }

    static zzfio zzb(zzfio zzfio, zzfio zzfio2) {
        int i = zzfio.count + zzfio2.count;
        int[] copyOf = Arrays.copyOf(zzfio.zzpkq, i);
        System.arraycopy(zzfio2.zzpkq, 0, copyOf, zzfio.count, zzfio2.count);
        Object[] copyOf2 = Arrays.copyOf(zzfio.zzpkr, i);
        System.arraycopy(zzfio2.zzpkr, 0, copyOf2, zzfio.count, zzfio2.count);
        return new zzfio(i, copyOf, copyOf2, true);
    }

    private void zzc(int i, Object obj) {
        zzczl();
        if (this.count == this.zzpkq.length) {
            int i2 = (this.count < 4 ? 8 : this.count >> 1) + this.count;
            this.zzpkq = Arrays.copyOf(this.zzpkq, i2);
            this.zzpkr = Arrays.copyOf(this.zzpkr, i2);
        }
        this.zzpkq[this.count] = i;
        this.zzpkr[this.count] = obj;
        this.count++;
    }

    private final void zzczl() {
        if (!this.zzpfc) {
            throw new UnsupportedOperationException();
        }
    }

    public static zzfio zzczu() {
        return zzpkp;
    }

    static zzfio zzczv() {
        return new zzfio();
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof zzfio)) {
            return false;
        }
        zzfio zzfio = (zzfio) obj;
        if (this.count == zzfio.count) {
            int[] iArr = this.zzpkq;
            int[] iArr2 = zzfio.zzpkq;
            int i = this.count;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                } else if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                Object[] objArr = this.zzpkr;
                Object[] objArr2 = zzfio.zzpkr;
                int i3 = this.count;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    } else if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                if (z2) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.count + 527) * 31) + Arrays.hashCode(this.zzpkq)) * 31) + Arrays.deepHashCode(this.zzpkr);
    }

    public final void zza(zzffg zzffg) throws IOException {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.count) {
                int i3 = this.zzpkq[i2];
                int i4 = i3 >>> 3;
                switch (i3 & 7) {
                    case 0:
                        zzffg.zza(i4, ((Long) this.zzpkr[i2]).longValue());
                        break;
                    case 1:
                        zzffg.zzb(i4, ((Long) this.zzpkr[i2]).longValue());
                        break;
                    case 2:
                        zzffg.zza(i4, (zzfes) this.zzpkr[i2]);
                        break;
                    case 3:
                        zzffg.zzz(i4, 3);
                        ((zzfio) this.zzpkr[i2]).zza(zzffg);
                        zzffg.zzz(i4, 4);
                        break;
                    case 5:
                        zzffg.zzac(i4, ((Integer) this.zzpkr[i2]).intValue());
                        break;
                    default:
                        throw zzfge.zzcyf();
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzfji zzfji) {
        if (zzfji.zzcwv() == zzg.zzpho) {
            for (int i = this.count - 1; i >= 0; i--) {
                zzfji.zzb(this.zzpkq[i] >>> 3, this.zzpkr[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.count; i2++) {
            zzfji.zzb(this.zzpkq[i2] >>> 3, this.zzpkr[i2]);
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzb(int i, zzffb zzffb) throws IOException {
        int zzcvt;
        zzczl();
        int i2 = i >>> 3;
        switch (i & 7) {
            case 0:
                zzc(i, Long.valueOf(zzffb.zzcvv()));
                return true;
            case 1:
                zzc(i, Long.valueOf(zzffb.zzcvx()));
                return true;
            case 2:
                zzc(i, zzffb.zzcwb());
                return true;
            case 3:
                zzfio zzfio = new zzfio();
                do {
                    zzcvt = zzffb.zzcvt();
                    if (zzcvt != 0) {
                    }
                    zzffb.zzkp((i2 << 3) | 4);
                    zzc(i, zzfio);
                    return true;
                } while (zzfio.zzb(zzcvt, zzffb));
                zzffb.zzkp((i2 << 3) | 4);
                zzc(i, zzfio);
                return true;
            case 4:
                return false;
            case 5:
                zzc(i, Integer.valueOf(zzffb.zzcvy()));
                return true;
            default:
                throw zzfge.zzcyf();
        }
    }

    public final void zzbiy() {
        this.zzpfc = false;
    }

    public final int zzczw() {
        int i = this.zzpgs;
        if (i == -1) {
            i = 0;
            for (int i2 = 0; i2 < this.count; i2++) {
                i += zzffg.zzd(this.zzpkq[i2] >>> 3, (zzfes) this.zzpkr[i2]);
            }
            this.zzpgs = i;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public final void zzd(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzfhh.zzb(sb, i, String.valueOf(this.zzpkq[i2] >>> 3), this.zzpkr[i2]);
        }
    }

    public final int zzho() {
        int zzho;
        int i = this.zzpgs;
        if (i == -1) {
            i = 0;
            for (int i2 = 0; i2 < this.count; i2++) {
                int i3 = this.zzpkq[i2];
                int i4 = i3 >>> 3;
                switch (i3 & 7) {
                    case 0:
                        zzho = zzffg.zzd(i4, ((Long) this.zzpkr[i2]).longValue());
                        break;
                    case 1:
                        zzho = zzffg.zze(i4, ((Long) this.zzpkr[i2]).longValue());
                        break;
                    case 2:
                        zzho = zzffg.zzc(i4, (zzfes) this.zzpkr[i2]);
                        break;
                    case 3:
                        zzho = ((zzfio) this.zzpkr[i2]).zzho() + (zzffg.zzlg(i4) << 1);
                        break;
                    case 5:
                        zzho = zzffg.zzaf(i4, ((Integer) this.zzpkr[i2]).intValue());
                        break;
                    default:
                        throw new IllegalStateException(zzfge.zzcyf());
                }
                i += zzho;
            }
            this.zzpgs = i;
        }
        return i;
    }
}
