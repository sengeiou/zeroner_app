package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.common.util.zzp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class zzbgn {
    protected static <O, I> I zza(zzbgo<I, O> zzbgo, Object obj) {
        return zzbgo.zzgcn != null ? zzbgo.convertBack(obj) : obj;
    }

    private static void zza(StringBuilder sb, zzbgo zzbgo, Object obj) {
        if (zzbgo.zzgce == 11) {
            sb.append(((zzbgn) zzbgo.zzgck.cast(obj)).toString());
        } else if (zzbgo.zzgce == 7) {
            sb.append("\"");
            sb.append(zzo.zzgr((String) obj));
            sb.append("\"");
        } else {
            sb.append(obj);
        }
    }

    private static void zza(StringBuilder sb, zzbgo zzbgo, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(sb, zzbgo, obj);
            }
        }
        sb.append("]");
    }

    public String toString() {
        Map zzaav = zzaav();
        StringBuilder sb = new StringBuilder(100);
        for (String str : zzaav.keySet()) {
            zzbgo zzbgo = (zzbgo) zzaav.get(str);
            if (zza(zzbgo)) {
                Object zza = zza(zzbgo, zzb(zzbgo));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"").append(str).append("\":");
                if (zza != null) {
                    switch (zzbgo.zzgcg) {
                        case 8:
                            sb.append("\"").append(zzb.zzk((byte[]) zza)).append("\"");
                            break;
                        case 9:
                            sb.append("\"").append(zzb.zzl((byte[]) zza)).append("\"");
                            break;
                        case 10:
                            zzp.zza(sb, (HashMap) zza);
                            break;
                        default:
                            if (!zzbgo.zzgcf) {
                                zza(sb, zzbgo, zza);
                                break;
                            } else {
                                zza(sb, zzbgo, (ArrayList) zza);
                                break;
                            }
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean zza(zzbgo zzbgo) {
        if (zzbgo.zzgcg != 11) {
            return zzgp(zzbgo.zzgci);
        }
        if (zzbgo.zzgch) {
            String str = zzbgo.zzgci;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        String str2 = zzbgo.zzgci;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    public abstract Map<String, zzbgo<?, ?>> zzaav();

    /* access modifiers changed from: protected */
    public Object zzb(zzbgo zzbgo) {
        String str = zzbgo.zzgci;
        if (zzbgo.zzgck == null) {
            return zzgo(zzbgo.zzgci);
        }
        zzgo(zzbgo.zzgci);
        zzbq.zza(true, "Concrete field shouldn't be value object: %s", zzbgo.zzgci);
        boolean z = zzbgo.zzgch;
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String substring = str.substring(1);
            return getClass().getMethod(new StringBuilder(String.valueOf(substring).length() + 4).append("get").append(upperCase).append(substring).toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zzgo(String str);

    /* access modifiers changed from: protected */
    public abstract boolean zzgp(String str);
}
