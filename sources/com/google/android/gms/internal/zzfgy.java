package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzfgy<K, V> extends LinkedHashMap<K, V> {
    private static final zzfgy zzpir;
    private boolean zzpfc = true;

    static {
        zzfgy zzfgy = new zzfgy();
        zzpir = zzfgy;
        zzfgy.zzpfc = false;
    }

    private zzfgy() {
    }

    private zzfgy(Map<K, V> map) {
        super(map);
    }

    private static int zzco(Object obj) {
        if (obj instanceof byte[]) {
            return zzffz.hashCode((byte[]) obj);
        }
        if (!(obj instanceof zzfga)) {
            return obj.hashCode();
        }
        throw new UnsupportedOperationException();
    }

    public static <K, V> zzfgy<K, V> zzcyp() {
        return zzpir;
    }

    private final void zzcyr() {
        if (!this.zzpfc) {
            throw new UnsupportedOperationException();
        }
    }

    public final void clear() {
        zzcyr();
        super.clear();
    }

    public final Set<Entry<K, V>> entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            r3 = 1
            r4 = 0
            boolean r0 = r7 instanceof java.util.Map
            if (r0 == 0) goto L_0x0062
            java.util.Map r7 = (java.util.Map) r7
            if (r6 == r7) goto L_0x0060
            int r0 = r6.size()
            int r1 = r7.size()
            if (r0 == r1) goto L_0x0019
            r0 = r4
        L_0x0015:
            if (r0 == 0) goto L_0x0062
            r0 = r3
        L_0x0018:
            return r0
        L_0x0019:
            java.util.Set r0 = r6.entrySet()
            java.util.Iterator r5 = r0.iterator()
        L_0x0021:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0060
            java.lang.Object r0 = r5.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getKey()
            boolean r1 = r7.containsKey(r1)
            if (r1 != 0) goto L_0x0039
            r0 = r4
            goto L_0x0015
        L_0x0039:
            java.lang.Object r1 = r0.getValue()
            java.lang.Object r0 = r0.getKey()
            java.lang.Object r2 = r7.get(r0)
            boolean r0 = r1 instanceof byte[]
            if (r0 == 0) goto L_0x005b
            boolean r0 = r2 instanceof byte[]
            if (r0 == 0) goto L_0x005b
            r0 = r1
            byte[] r0 = (byte[]) r0
            r1 = r2
            byte[] r1 = (byte[]) r1
            boolean r0 = java.util.Arrays.equals(r0, r1)
        L_0x0057:
            if (r0 != 0) goto L_0x0021
            r0 = r4
            goto L_0x0015
        L_0x005b:
            boolean r0 = r1.equals(r2)
            goto L_0x0057
        L_0x0060:
            r0 = r3
            goto L_0x0015
        L_0x0062:
            r0 = r4
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfgy.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i = 0;
        Iterator it = entrySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            Entry entry = (Entry) it.next();
            i = (zzco(entry.getValue()) ^ zzco(entry.getKey())) + i2;
        }
    }

    public final boolean isMutable() {
        return this.zzpfc;
    }

    public final V put(K k, V v) {
        zzcyr();
        zzffz.checkNotNull(k);
        zzffz.checkNotNull(v);
        return super.put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        zzcyr();
        for (Object next : map.keySet()) {
            zzffz.checkNotNull(next);
            zzffz.checkNotNull(map.get(next));
        }
        super.putAll(map);
    }

    public final V remove(Object obj) {
        zzcyr();
        return super.remove(obj);
    }

    public final void zza(zzfgy<K, V> zzfgy) {
        zzcyr();
        if (!zzfgy.isEmpty()) {
            putAll(zzfgy);
        }
    }

    public final void zzbiy() {
        this.zzpfc = false;
    }

    public final zzfgy<K, V> zzcyq() {
        return isEmpty() ? new zzfgy<>() : new zzfgy<>(this);
    }
}
