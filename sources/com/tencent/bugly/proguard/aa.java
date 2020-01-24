package com.tencent.bugly.proguard;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

/* compiled from: BUGLY */
public final class aa extends m implements Cloneable {
    static bg c;
    static y d;
    static final /* synthetic */ boolean e = (!aa.class.desiredAssertionStatus());
    public bg a = null;
    public y b = null;

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        aa aaVar = (aa) o;
        if (!n.a((Object) this.a, (Object) aaVar.a) || !n.a((Object) this.b, (Object) aaVar.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            return 0;
        }
    }

    public Object clone() {
        boolean z = false;
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            if (e) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        if (this.a != null) {
            lVar.a((m) this.a, 0);
        }
        if (this.b != null) {
            lVar.a((m) this.b, 1);
        }
    }

    public void a(k kVar) {
        if (c == null) {
            c = new bg();
        }
        this.a = (bg) kVar.a((m) c, 0, false);
        if (d == null) {
            d = new y();
        }
        this.b = (y) kVar.a((m) d, 1, false);
    }

    public void a(StringBuilder sb, int i) {
        i iVar = new i(sb, i);
        iVar.a((m) this.a, "baseStrategy");
        iVar.a((m) this.b, "grayStrategy");
    }
}
