package com.tencent.bugly.beta.upgrade;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.ah;
import com.tencent.bugly.proguard.bg;
import com.tencent.bugly.proguard.m;

/* compiled from: BUGLY */
public class BetaUploadStrategy implements Parcelable, Creator<BetaUploadStrategy> {
    public static final Creator<BetaUploadStrategy> CREATOR = new BetaUploadStrategy();
    public bg a;
    public long b;

    public BetaUploadStrategy() {
        this.a = new bg();
        this.a.b = true;
        this.a.c = true;
        if (e.E.Q) {
            this.a.d = StrategyBean.b;
            this.a.e = StrategyBean.b;
        } else {
            this.a.d = "http://android.bugly.qq.com/rqd/async";
            this.a.e = "http://android.bugly.qq.com/rqd/async";
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.a.h = currentTimeMillis;
        this.b = currentTimeMillis;
    }

    public BetaUploadStrategy(Parcel in) {
        this.a = (bg) ah.a(in.createByteArray(), bg.class);
        this.b = in.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(ah.a((m) this.a));
        dest.writeLong(this.b);
    }

    /* renamed from: a */
    public BetaUploadStrategy createFromParcel(Parcel parcel) {
        return new BetaUploadStrategy(parcel);
    }

    /* renamed from: a */
    public BetaUploadStrategy[] newArray(int i) {
        return new BetaUploadStrategy[i];
    }
}
