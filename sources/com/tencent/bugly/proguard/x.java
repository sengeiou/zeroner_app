package com.tencent.bugly.proguard;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: BUGLY */
public final class x extends m implements Cloneable {
    static ArrayList<w> b;
    static final /* synthetic */ boolean c = (!x.class.desiredAssertionStatus());
    public ArrayList<w> a = null;

    public x() {
    }

    public x(ArrayList<w> arrayList) {
        this.a = arrayList;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return n.a((Object) this.a, (Object) ((x) o).a);
    }

    public int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public Object clone() {
        boolean z = false;
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            if (c) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a((Collection<T>) this.a, 0);
    }

    public void a(k kVar) {
        if (b == null) {
            b = new ArrayList<>();
            b.add(new w());
        }
        this.a = (ArrayList) kVar.a(b, 0, true);
    }

    public void a(StringBuilder sb, int i) {
        new i(sb, i).a((Collection<T>) this.a, "events");
    }
}
