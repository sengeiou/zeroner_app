package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;

/* compiled from: BUGLY */
public final class bc extends m implements Cloneable {
    static ArrayList<bb> b;
    public ArrayList<bb> a = null;

    public void a(l lVar) {
        lVar.a((Collection<T>) this.a, 0);
    }

    public void a(k kVar) {
        if (b == null) {
            b = new ArrayList<>();
            b.add(new bb());
        }
        this.a = (ArrayList) kVar.a(b, 0, true);
    }

    public void a(StringBuilder sb, int i) {
    }
}
