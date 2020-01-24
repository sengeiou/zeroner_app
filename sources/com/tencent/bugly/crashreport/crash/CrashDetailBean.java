package com.tencent.bugly.crashreport.crash;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.proguard.ap;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
public class CrashDetailBean implements Parcelable, Comparable<CrashDetailBean> {
    public static final Creator<CrashDetailBean> CREATOR = new Creator<CrashDetailBean>() {
        /* renamed from: a */
        public CrashDetailBean createFromParcel(Parcel parcel) {
            return new CrashDetailBean(parcel);
        }

        /* renamed from: a */
        public CrashDetailBean[] newArray(int i) {
            return new CrashDetailBean[i];
        }
    };
    public String A = "";
    public String B = "";
    public long C = -1;
    public long D = -1;
    public long E = -1;
    public long F = -1;
    public long G = -1;
    public long H = -1;
    public String I = "";
    public String J = "";
    public String K = "";
    public String L = "";
    public String M = "";
    public long N = -1;
    public boolean O = false;
    public Map<String, String> P = null;
    public int Q = -1;
    public int R = -1;
    public Map<String, String> S = null;
    public Map<String, String> T = null;
    public byte[] U = null;
    public String V = null;
    public String W = null;
    public long a = -1;
    public int b = 0;
    public String c = UUID.randomUUID().toString();
    public boolean d = false;
    public String e = "";
    public String f = "";
    public String g = "";
    public Map<String, PlugInBean> h = null;
    public Map<String, PlugInBean> i = null;
    public boolean j = false;
    public boolean k = false;
    public int l = 0;
    public String m = "";
    public String n = "";
    public String o = "";
    public String p = "";
    public String q = "";
    public long r = -1;
    public String s = null;
    public int t = 0;
    public String u = "";
    public String v = "";
    public String w = null;
    public String x = null;
    public byte[] y = null;
    public Map<String, String> z = null;

    public CrashDetailBean() {
    }

    public CrashDetailBean(Parcel in) {
        boolean z2;
        boolean z3;
        boolean z4 = true;
        this.b = in.readInt();
        this.c = in.readString();
        this.d = in.readByte() == 1;
        this.e = in.readString();
        this.f = in.readString();
        this.g = in.readString();
        if (in.readByte() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.j = z2;
        if (in.readByte() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.k = z3;
        this.l = in.readInt();
        this.m = in.readString();
        this.n = in.readString();
        this.o = in.readString();
        this.p = in.readString();
        this.q = in.readString();
        this.r = in.readLong();
        this.s = in.readString();
        this.t = in.readInt();
        this.u = in.readString();
        this.v = in.readString();
        this.w = in.readString();
        this.z = ap.b(in);
        this.A = in.readString();
        this.B = in.readString();
        this.C = in.readLong();
        this.D = in.readLong();
        this.E = in.readLong();
        this.F = in.readLong();
        this.G = in.readLong();
        this.H = in.readLong();
        this.I = in.readString();
        this.J = in.readString();
        this.K = in.readString();
        this.L = in.readString();
        this.M = in.readString();
        this.N = in.readLong();
        if (in.readByte() != 1) {
            z4 = false;
        }
        this.O = z4;
        this.P = ap.b(in);
        this.h = ap.a(in);
        this.i = ap.a(in);
        this.Q = in.readInt();
        this.R = in.readInt();
        this.S = ap.b(in);
        this.T = ap.b(in);
        this.U = in.createByteArray();
        this.y = in.createByteArray();
        this.V = in.readString();
        this.W = in.readString();
        this.x = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i2;
        int i3;
        int i4 = 1;
        dest.writeInt(this.b);
        dest.writeString(this.c);
        dest.writeByte((byte) (this.d ? 1 : 0));
        dest.writeString(this.e);
        dest.writeString(this.f);
        dest.writeString(this.g);
        if (this.j) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        if (this.k) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        dest.writeByte((byte) i3);
        dest.writeInt(this.l);
        dest.writeString(this.m);
        dest.writeString(this.n);
        dest.writeString(this.o);
        dest.writeString(this.p);
        dest.writeString(this.q);
        dest.writeLong(this.r);
        dest.writeString(this.s);
        dest.writeInt(this.t);
        dest.writeString(this.u);
        dest.writeString(this.v);
        dest.writeString(this.w);
        ap.b(dest, this.z);
        dest.writeString(this.A);
        dest.writeString(this.B);
        dest.writeLong(this.C);
        dest.writeLong(this.D);
        dest.writeLong(this.E);
        dest.writeLong(this.F);
        dest.writeLong(this.G);
        dest.writeLong(this.H);
        dest.writeString(this.I);
        dest.writeString(this.J);
        dest.writeString(this.K);
        dest.writeString(this.L);
        dest.writeString(this.M);
        dest.writeLong(this.N);
        if (!this.O) {
            i4 = 0;
        }
        dest.writeByte((byte) i4);
        ap.b(dest, this.P);
        ap.a(dest, this.h);
        ap.a(dest, this.i);
        dest.writeInt(this.Q);
        dest.writeInt(this.R);
        ap.b(dest, this.S);
        ap.b(dest, this.T);
        dest.writeByteArray(this.U);
        dest.writeByteArray(this.y);
        dest.writeString(this.V);
        dest.writeString(this.W);
        dest.writeString(this.x);
    }

    /* renamed from: a */
    public int compareTo(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return 1;
        }
        long j2 = this.r - crashDetailBean.r;
        if (j2 > 0) {
            return 1;
        }
        if (j2 < 0) {
            return -1;
        }
        return 0;
    }
}
