package com.loc;

import com.amap.api.location.AMapLocation;

@ap(a = "c")
/* compiled from: LastLocationInfo */
public class cz {
    @aq(a = "a2", b = 6)
    private String a;
    @aq(a = "a3", b = 5)
    private long b;
    @aq(a = "a4", b = 6)
    private String c;
    private AMapLocation d;

    public final AMapLocation a() {
        return this.d;
    }

    public final void a(long j) {
        this.b = j;
    }

    public final void a(AMapLocation aMapLocation) {
        this.d = aMapLocation;
    }

    public final void a(String str) {
        this.c = str;
    }

    public final String b() {
        return this.c;
    }

    public final void b(String str) {
        this.a = str;
    }

    public final String c() {
        return this.a;
    }

    public final long d() {
        return this.b;
    }
}