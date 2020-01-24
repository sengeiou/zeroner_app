package com.tencent.bugly.crashreport.common.strategy;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* compiled from: BUGLY */
public class StrategyBean implements Parcelable {
    public static final Creator<StrategyBean> CREATOR = new Creator<StrategyBean>() {
        /* renamed from: a */
        public StrategyBean createFromParcel(Parcel parcel) {
            return new StrategyBean(parcel);
        }

        /* renamed from: a */
        public StrategyBean[] newArray(int i) {
            return new StrategyBean[i];
        }
    };
    public static String a = "http://rqd.uu.qq.com/rqd/sync";
    public static String b = "http://android.bugly.qq.com/rqd/async";
    public static String c = "http://android.bugly.qq.com/rqd/async";
    public static String d;
    public long e;
    public long f;
    public boolean g;
    public boolean h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public long p;
    public long q;
    public String r;
    public String s;
    public String t;
    public String u;
    public Map<String, String> v;
    public int w;
    public long x;
    public long y;

    public StrategyBean() {
        this.e = -1;
        this.f = -1;
        this.g = true;
        this.h = true;
        this.i = true;
        this.j = true;
        this.k = false;
        this.l = true;
        this.m = true;
        this.n = true;
        this.o = true;
        this.q = 30000;
        this.r = b;
        this.s = c;
        this.t = a;
        this.w = 10;
        this.x = 300000;
        this.y = -1;
        this.f = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("S(").append("@L@L").append("@)");
        d = sb.toString();
        sb.setLength(0);
        sb.append("*^").append("@K#K").append("@!");
        this.u = sb.toString();
    }

    public StrategyBean(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8 = true;
        this.e = -1;
        this.f = -1;
        this.g = true;
        this.h = true;
        this.i = true;
        this.j = true;
        this.k = false;
        this.l = true;
        this.m = true;
        this.n = true;
        this.o = true;
        this.q = 30000;
        this.r = b;
        this.s = c;
        this.t = a;
        this.w = 10;
        this.x = 300000;
        this.y = -1;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("S(").append("@L@L").append("@)");
            d = sb.toString();
            this.f = in.readLong();
            this.g = in.readByte() == 1;
            if (in.readByte() == 1) {
                z = true;
            } else {
                z = false;
            }
            this.h = z;
            if (in.readByte() == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.i = z2;
            this.r = in.readString();
            this.s = in.readString();
            this.u = in.readString();
            this.v = ap.b(in);
            if (in.readByte() == 1) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.j = z3;
            if (in.readByte() == 1) {
                z4 = true;
            } else {
                z4 = false;
            }
            this.k = z4;
            if (in.readByte() == 1) {
                z5 = true;
            } else {
                z5 = false;
            }
            this.n = z5;
            if (in.readByte() == 1) {
                z6 = true;
            } else {
                z6 = false;
            }
            this.o = z6;
            this.q = in.readLong();
            if (in.readByte() == 1) {
                z7 = true;
            } else {
                z7 = false;
            }
            this.l = z7;
            if (in.readByte() != 1) {
                z8 = false;
            }
            this.m = z8;
            this.p = in.readLong();
            this.w = in.readInt();
            this.x = in.readLong();
            this.y = in.readLong();
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = 1;
        dest.writeLong(this.f);
        dest.writeByte((byte) (this.g ? 1 : 0));
        if (this.h) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        if (this.i) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        dest.writeByte((byte) i3);
        dest.writeString(this.r);
        dest.writeString(this.s);
        dest.writeString(this.u);
        ap.b(dest, this.v);
        if (this.j) {
            i4 = 1;
        } else {
            i4 = 0;
        }
        dest.writeByte((byte) i4);
        if (this.k) {
            i5 = 1;
        } else {
            i5 = 0;
        }
        dest.writeByte((byte) i5);
        if (this.n) {
            i6 = 1;
        } else {
            i6 = 0;
        }
        dest.writeByte((byte) i6);
        if (this.o) {
            i7 = 1;
        } else {
            i7 = 0;
        }
        dest.writeByte((byte) i7);
        dest.writeLong(this.q);
        if (this.l) {
            i8 = 1;
        } else {
            i8 = 0;
        }
        dest.writeByte((byte) i8);
        if (!this.m) {
            i9 = 0;
        }
        dest.writeByte((byte) i9);
        dest.writeLong(this.p);
        dest.writeInt(this.w);
        dest.writeLong(this.x);
        dest.writeLong(this.y);
    }
}
