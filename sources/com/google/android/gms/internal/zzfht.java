package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

final class zzfht implements Iterator<zzfey> {
    private final Stack<zzfhq> zzpjn;
    private zzfey zzpjo;

    private zzfht(zzfes zzfes) {
        this.zzpjn = new Stack<>();
        this.zzpjo = zzbb(zzfes);
    }

    private final zzfey zzbb(zzfes zzfes) {
        zzfes zzfes2 = zzfes;
        while (zzfes2 instanceof zzfhq) {
            zzfhq zzfhq = (zzfhq) zzfes2;
            this.zzpjn.push(zzfhq);
            zzfes2 = zzfhq.zzpji;
        }
        return (zzfey) zzfes2;
    }

    private final zzfey zzczc() {
        while (!this.zzpjn.isEmpty()) {
            zzfey zzbb = zzbb(((zzfhq) this.zzpjn.pop()).zzpjj);
            if (!zzbb.isEmpty()) {
                return zzbb;
            }
        }
        return null;
    }

    public final boolean hasNext() {
        return this.zzpjo != null;
    }

    public final /* synthetic */ Object next() {
        if (this.zzpjo == null) {
            throw new NoSuchElementException();
        }
        zzfey zzfey = this.zzpjo;
        this.zzpjo = zzczc();
        return zzfey;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
