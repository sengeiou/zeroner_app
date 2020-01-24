package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Stack;

final class zzfhs {
    private final Stack<zzfes> zzpjm;

    private zzfhs() {
        this.zzpjm = new Stack<>();
    }

    private final void zzba(zzfes zzfes) {
        zzfes zzfes2 = zzfes;
        while (!zzfes2.zzcvo()) {
            if (zzfes2 instanceof zzfhq) {
                zzfhq zzfhq = (zzfhq) zzfes2;
                zzba(zzfhq.zzpji);
                zzfes2 = zzfhq.zzpjj;
            } else {
                String valueOf = String.valueOf(zzfes2.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 49).append("Has a new type of ByteString been created? Found ").append(valueOf).toString());
            }
        }
        int zzlz = zzlz(zzfes2.size());
        int i = zzfhq.zzpjg[zzlz + 1];
        if (this.zzpjm.isEmpty() || ((zzfes) this.zzpjm.peek()).size() >= i) {
            this.zzpjm.push(zzfes2);
            return;
        }
        int i2 = zzfhq.zzpjg[zzlz];
        zzfes zzfes3 = (zzfes) this.zzpjm.pop();
        while (!this.zzpjm.isEmpty() && ((zzfes) this.zzpjm.peek()).size() < i2) {
            zzfes3 = new zzfhq((zzfes) this.zzpjm.pop(), zzfes3);
        }
        zzfhq zzfhq2 = new zzfhq(zzfes3, zzfes2);
        while (!this.zzpjm.isEmpty()) {
            if (((zzfes) this.zzpjm.peek()).size() >= zzfhq.zzpjg[zzlz(zzfhq2.size()) + 1]) {
                break;
            }
            zzfhq2 = new zzfhq((zzfes) this.zzpjm.pop(), zzfhq2);
        }
        this.zzpjm.push(zzfhq2);
    }

    /* access modifiers changed from: private */
    public final zzfes zzc(zzfes zzfes, zzfes zzfes2) {
        zzba(zzfes);
        zzba(zzfes2);
        zzfes zzfes3 = (zzfes) this.zzpjm.pop();
        while (!this.zzpjm.isEmpty()) {
            zzfes3 = new zzfhq((zzfes) this.zzpjm.pop(), zzfes3);
        }
        return zzfes3;
    }

    private static int zzlz(int i) {
        int binarySearch = Arrays.binarySearch(zzfhq.zzpjg, i);
        return binarySearch < 0 ? (-(binarySearch + 1)) - 1 : binarySearch;
    }
}
