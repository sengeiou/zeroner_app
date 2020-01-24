package com.tencent.bugly.beta.upgrade;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.tencent.bugly.proguard.ah;
import com.tencent.bugly.proguard.m;
import com.tencent.bugly.proguard.y;

/* compiled from: BUGLY */
public class BetaGrayStrategy implements Parcelable, Creator<BetaGrayStrategy> {
    public static final Creator<BetaGrayStrategy> CREATOR = new BetaGrayStrategy();
    public y a;
    public int b = 0;
    public long c = -1;
    public boolean d = false;
    public long e = -1;

    public BetaGrayStrategy(Parcel in) {
        boolean z;
        this.a = (y) ah.a(in.createByteArray(), y.class);
        this.b = in.readInt();
        this.c = in.readLong();
        if (1 == in.readByte()) {
            z = true;
        } else {
            z = false;
        }
        this.d = z;
        this.e = in.readLong();
    }

    public BetaGrayStrategy() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(ah.a((m) this.a));
        dest.writeInt(this.b);
        dest.writeLong(this.c);
        dest.writeByte((byte) (this.d ? 1 : 0));
        dest.writeLong(this.e);
    }

    /* renamed from: a */
    public BetaGrayStrategy createFromParcel(Parcel parcel) {
        return new BetaGrayStrategy(parcel);
    }

    /* renamed from: a */
    public BetaGrayStrategy[] newArray(int i) {
        return new BetaGrayStrategy[i];
    }
}
