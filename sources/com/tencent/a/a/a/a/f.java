package com.tencent.a.a.a.a;

import android.content.Context;

public abstract class f {
    protected Context a = null;

    protected f(Context context) {
        this.a = context;
    }

    public final void a(c cVar) {
        if (cVar != null) {
            String cVar2 = cVar.toString();
            if (a()) {
                a(h.g(cVar2));
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(String str);

    /* access modifiers changed from: protected */
    public abstract boolean a();

    /* access modifiers changed from: protected */
    public abstract String b();

    public final c o() {
        String str = a() ? h.f(b()) : null;
        if (str != null) {
            return c.e(str);
        }
        return null;
    }
}
