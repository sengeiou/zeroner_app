package com.tencent.bugly.proguard;

import java.io.Serializable;

/* compiled from: BUGLY */
public class ab implements Serializable, Comparable<ab> {
    public long a;
    public String b;
    public long c;
    public int d;
    public String e;
    public String f;
    public long g;

    /* renamed from: a */
    public int compareTo(ab abVar) {
        return (int) (this.c - abVar.c);
    }
}
