package com.loc;

import java.io.File;

/* compiled from: FileNumUpdateStrategy */
public final class ci extends cm {
    private int b;
    private String c;

    public ci(int i, String str, cm cmVar) {
        super(cmVar);
        this.b = i;
        this.c = str;
    }

    private static int a(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                return 0;
            }
            return file.list().length;
        } catch (Throwable th) {
            aj.b(th, "fus", "gfn");
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        return a(this.c) >= this.b;
    }
}
