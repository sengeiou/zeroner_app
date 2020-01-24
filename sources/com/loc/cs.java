package com.loc;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: CellAgeEstimator */
public final class cs {
    private HashMap<Long, ct> a = new HashMap<>();
    private long b = 0;

    private static long a(int i, int i2) {
        return ((((long) i) & 65535) << 32) | (((long) i2) & 65535);
    }

    public final long a(ct ctVar) {
        long a2;
        if (ctVar == null || !ctVar.p) {
            return 0;
        }
        HashMap<Long, ct> hashMap = this.a;
        switch (ctVar.k) {
            case 1:
            case 3:
            case 4:
                a2 = a(ctVar.c, ctVar.d);
                break;
            case 2:
                a2 = a(ctVar.h, ctVar.i);
                break;
            default:
                a2 = 0;
                break;
        }
        ct ctVar2 = (ct) hashMap.get(Long.valueOf(a2));
        if (ctVar2 == null) {
            ctVar.m = dr.c();
            hashMap.put(Long.valueOf(a2), ctVar);
            return 0;
        } else if (ctVar2.j != ctVar.j) {
            ctVar.m = dr.c();
            hashMap.put(Long.valueOf(a2), ctVar);
            return 0;
        } else {
            ctVar.m = ctVar2.m;
            hashMap.put(Long.valueOf(a2), ctVar);
            return (dr.c() - ctVar2.m) / 1000;
        }
    }

    public final void a() {
        this.a.clear();
        this.b = 0;
    }

    public final void a(ArrayList<? extends ct> arrayList) {
        long j = 0;
        if (arrayList != null) {
            long c = dr.c();
            if (this.b <= 0 || c - this.b >= 60000) {
                HashMap<Long, ct> hashMap = this.a;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ct ctVar = (ct) arrayList.get(i);
                    if (ctVar.p) {
                        switch (ctVar.k) {
                            case 1:
                            case 3:
                            case 4:
                                j = a(ctVar.c, ctVar.d);
                                break;
                            case 2:
                                j = a(ctVar.h, ctVar.i);
                                break;
                        }
                        ct ctVar2 = (ct) hashMap.get(Long.valueOf(j));
                        if (ctVar2 != null) {
                            if (ctVar2.j == ctVar.j) {
                                ctVar.m = ctVar2.m;
                            } else {
                                ctVar.m = c;
                            }
                        }
                    }
                }
                hashMap.clear();
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ct ctVar3 = (ct) arrayList.get(i2);
                    if (ctVar3.p) {
                        switch (ctVar3.k) {
                            case 1:
                            case 3:
                            case 4:
                                j = a(ctVar3.c, ctVar3.d);
                                break;
                            case 2:
                                j = a(ctVar3.h, ctVar3.i);
                                break;
                        }
                        hashMap.put(Long.valueOf(j), ctVar3);
                    }
                }
                this.b = c;
            }
        }
    }
}
