package com.google.android.gms.internal;

import com.google.android.gms.internal.zzffu.zzb;
import com.google.android.gms.internal.zzffu.zzg;
import com.google.android.gms.internal.zzffu.zzh;
import java.io.IOException;

public final class zzfep extends zzffu<zzfep, zza> implements zzfhg {
    private static volatile zzfhk<zzfep> zzbbk;
    /* access modifiers changed from: private */
    public static final zzfep zzpff;
    private String zzlwa = "";
    private zzfes zzlwb = zzfes.zzpfg;

    public static final class zza extends com.google.android.gms.internal.zzffu.zza<zzfep, zza> implements zzfhg {
        private zza() {
            super(zzfep.zzpff);
        }

        /* synthetic */ zza(zzfeq zzfeq) {
            this();
        }
    }

    static {
        zzfep zzfep = new zzfep();
        zzpff = zzfep;
        zzfep.zza(zzg.zzphh, (Object) null, (Object) null);
        zzfep.zzpgr.zzbiy();
    }

    private zzfep() {
    }

    public static zzfep zzcvk() {
        return zzpff;
    }

    /* access modifiers changed from: protected */
    public final Object zza(int i, Object obj, Object obj2) {
        boolean zzb;
        boolean z = true;
        switch (zzfeq.zzbbi[i - 1]) {
            case 1:
                return new zzfep();
            case 2:
                return zzpff;
            case 3:
                return null;
            case 4:
                return new zza(null);
            case 5:
                zzh zzh = (zzh) obj;
                zzfep zzfep = (zzfep) obj2;
                this.zzlwa = zzh.zza(!this.zzlwa.isEmpty(), this.zzlwa, !zzfep.zzlwa.isEmpty(), zzfep.zzlwa);
                boolean z2 = this.zzlwb != zzfes.zzpfg;
                zzfes zzfes = this.zzlwb;
                if (zzfep.zzlwb == zzfes.zzpfg) {
                    z = false;
                }
                this.zzlwb = zzh.zza(z2, zzfes, z, zzfep.zzlwb);
                return this;
            case 6:
                zzffb zzffb = (zzffb) obj;
                if (((zzffm) obj2) != null) {
                    boolean z3 = false;
                    while (!z3) {
                        try {
                            int zzcvt = zzffb.zzcvt();
                            switch (zzcvt) {
                                case 0:
                                    z3 = true;
                                    break;
                                case 10:
                                    this.zzlwa = zzffb.zzcwa();
                                    break;
                                case 18:
                                    this.zzlwb = zzffb.zzcwb();
                                    break;
                                default:
                                    if ((zzcvt & 7) == 4) {
                                        zzb = false;
                                    } else {
                                        if (this.zzpgr == zzfio.zzczu()) {
                                            this.zzpgr = zzfio.zzczv();
                                        }
                                        zzb = this.zzpgr.zzb(zzcvt, zzffb);
                                    }
                                    if (zzb) {
                                        break;
                                    } else {
                                        z3 = true;
                                        break;
                                    }
                            }
                        } catch (zzfge e) {
                            throw new RuntimeException(e.zzi(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new zzfge(e2.getMessage()).zzi(this));
                        }
                    }
                    break;
                } else {
                    throw new NullPointerException();
                }
            case 7:
                break;
            case 8:
                if (zzbbk == null) {
                    synchronized (zzfep.class) {
                        if (zzbbk == null) {
                            zzbbk = new zzb(zzpff);
                        }
                    }
                }
                return zzbbk;
            case 9:
                return Byte.valueOf(1);
            case 10:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
        return zzpff;
    }

    public final void zza(zzffg zzffg) throws IOException {
        if (!this.zzlwa.isEmpty()) {
            zzffg.zzn(1, this.zzlwa);
        }
        if (!this.zzlwb.isEmpty()) {
            zzffg.zza(2, this.zzlwb);
        }
        this.zzpgr.zza(zzffg);
    }

    public final int zzho() {
        int i = this.zzpgs;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        if (!this.zzlwa.isEmpty()) {
            i2 = zzffg.zzo(1, this.zzlwa) + 0;
        }
        if (!this.zzlwb.isEmpty()) {
            i2 += zzffg.zzc(2, this.zzlwb);
        }
        int zzho = i2 + this.zzpgr.zzho();
        this.zzpgs = zzho;
        return zzho;
    }
}
