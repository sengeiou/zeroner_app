package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbgt extends zzbfm {
    public static final Creator<zzbgt> CREATOR = new zzbgw();
    private int zzeck;
    private final HashMap<String, Map<String, zzbgo<?, ?>>> zzgco;
    private final ArrayList<zzbgu> zzgcp = null;
    private final String zzgcq;

    zzbgt(int i, ArrayList<zzbgu> arrayList, String str) {
        this.zzeck = i;
        HashMap<String, Map<String, zzbgo<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zzbgu zzbgu = (zzbgu) arrayList.get(i2);
            hashMap.put(zzbgu.className, zzbgu.zzama());
        }
        this.zzgco = hashMap;
        this.zzgcq = (String) zzbq.checkNotNull(str);
        zzaly();
    }

    private final void zzaly() {
        for (String str : this.zzgco.keySet()) {
            Map map = (Map) this.zzgco.get(str);
            for (String str2 : map.keySet()) {
                ((zzbgo) map.get(str2)).zza(this);
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.zzgco.keySet()) {
            sb.append(str).append(":\n");
            Map map = (Map) this.zzgco.get(str);
            for (String str2 : map.keySet()) {
                sb.append("  ").append(str2).append(": ");
                sb.append(map.get(str2));
            }
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        ArrayList arrayList = new ArrayList();
        for (String str : this.zzgco.keySet()) {
            arrayList.add(new zzbgu(str, (Map) this.zzgco.get(str)));
        }
        zzbfp.zzc(parcel, 2, arrayList, false);
        zzbfp.zza(parcel, 3, this.zzgcq, false);
        zzbfp.zzai(parcel, zze);
    }

    public final String zzalz() {
        return this.zzgcq;
    }

    public final Map<String, zzbgo<?, ?>> zzgq(String str) {
        return (Map) this.zzgco.get(str);
    }
}
