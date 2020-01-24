package com.tencent.bugly.beta.global;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: BUGLY */
public class ResBean implements Parcelable, Creator<ResBean> {
    public static final Creator<ResBean> CREATOR = new ResBean();
    public static ResBean a;
    public static final String[] b = {"IMG_title", "VAL_style"};
    public final String c = "#273238";
    public final String d = "#757575";
    private Map<String, String> e = new ConcurrentHashMap();

    public ResBean() {
    }

    public ResBean(Parcel in) {
        try {
            for (String put : b) {
                this.e.put(put, in.readString());
            }
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    public String a(String str) {
        return (String) this.e.get(str);
    }

    public void a(String str, Object obj) {
        if (obj instanceof String) {
            this.e.put(str, (String) obj);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        for (String str : b) {
            dest.writeString((String) this.e.get(str));
        }
    }

    /* renamed from: a */
    public ResBean createFromParcel(Parcel parcel) {
        return new ResBean(parcel);
    }

    /* renamed from: a */
    public ResBean[] newArray(int i) {
        return new ResBean[0];
    }
}
