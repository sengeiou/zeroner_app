package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;

/* compiled from: BUGLY */
public final class az extends m implements Cloneable {
    static ArrayList<String> c;
    public String a = "";
    public ArrayList<String> b = null;

    public void a(l lVar) {
        lVar.a(this.a, 0);
        if (this.b != null) {
            lVar.a((Collection<T>) this.b, 1);
        }
    }

    public void a(k kVar) {
        this.a = kVar.a(0, true);
        if (c == null) {
            c = new ArrayList<>();
            c.add("");
        }
        this.b = (ArrayList) kVar.a(c, 1, false);
    }

    public void a(StringBuilder sb, int i) {
    }
}
