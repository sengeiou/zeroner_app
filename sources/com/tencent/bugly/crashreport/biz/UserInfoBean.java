package com.tencent.bugly.crashreport.biz;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* compiled from: BUGLY */
public class UserInfoBean implements Parcelable {
    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        /* renamed from: a */
        public UserInfoBean createFromParcel(Parcel parcel) {
            return new UserInfoBean(parcel);
        }

        /* renamed from: a */
        public UserInfoBean[] newArray(int i) {
            return new UserInfoBean[i];
        }
    };
    public long a;
    public int b;
    public String c;
    public String d;
    public long e;
    public long f;
    public long g;
    public long h;
    public long i;
    public String j;
    public long k = 0;
    public boolean l = false;
    public String m = "unknown";
    public String n;
    public int o;
    public int p = -1;
    public int q = -1;
    public Map<String, String> r = null;
    public Map<String, String> s = null;

    public UserInfoBean() {
    }

    public UserInfoBean(Parcel in) {
        boolean z = true;
        this.b = in.readInt();
        this.c = in.readString();
        this.d = in.readString();
        this.e = in.readLong();
        this.f = in.readLong();
        this.g = in.readLong();
        this.h = in.readLong();
        this.i = in.readLong();
        this.j = in.readString();
        this.k = in.readLong();
        if (in.readByte() != 1) {
            z = false;
        }
        this.l = z;
        this.m = in.readString();
        this.p = in.readInt();
        this.q = in.readInt();
        this.r = ap.b(in);
        this.s = ap.b(in);
        this.n = in.readString();
        this.o = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeLong(this.e);
        dest.writeLong(this.f);
        dest.writeLong(this.g);
        dest.writeLong(this.h);
        dest.writeLong(this.i);
        dest.writeString(this.j);
        dest.writeLong(this.k);
        dest.writeByte((byte) (this.l ? 1 : 0));
        dest.writeString(this.m);
        dest.writeInt(this.p);
        dest.writeInt(this.q);
        ap.b(dest, this.r);
        ap.b(dest, this.s);
        dest.writeString(this.n);
        dest.writeInt(this.o);
    }
}
