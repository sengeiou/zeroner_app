package com.loc;

import java.util.HashMap;
import java.util.Map;
import no.nordicsemi.android.dfu.DfuBaseService;

/* compiled from: LogUpdateRequest */
public final class ai extends bq {
    private byte[] a;
    private String b = "1";

    public ai(byte[] bArr, String str) {
        this.a = (byte[]) bArr.clone();
        this.b = str;
    }

    public final Map<String, String> b() {
        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", DfuBaseService.MIME_TYPE_ZIP);
        hashMap.put("Content-Length", String.valueOf(this.a.length));
        return hashMap;
    }

    public final Map<String, String> b_() {
        return null;
    }

    public final String c() {
        String c = w.c(ae.c);
        byte[] a2 = w.a(ae.b);
        byte[] bArr = new byte[(a2.length + 50)];
        System.arraycopy(this.a, 0, bArr, 0, 50);
        System.arraycopy(a2, 0, bArr, 50, a2.length);
        return String.format(c, new Object[]{"1", this.b, "1", "open", s.a(bArr)});
    }

    public final byte[] d() {
        return this.a;
    }
}
