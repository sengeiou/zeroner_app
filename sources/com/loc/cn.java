package com.loc;

import android.content.Context;

/* compiled from: WiFiUplateStrategy */
public final class cn extends cm {
    private Context b;
    private boolean c = false;

    public cn(Context context) {
        this.b = context;
        this.c = false;
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        return p.r(this.b) == 1 || this.c;
    }
}
