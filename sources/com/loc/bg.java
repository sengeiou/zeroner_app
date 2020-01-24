package com.loc;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;

/* compiled from: SPUtil */
public final class bg {
    private String a;

    public bg(String str) {
        if (TextUtils.isDigitsOnly(str)) {
            str = "SPUtil";
        }
        this.a = s.b(str);
    }

    public final void a(Context context, String str, boolean z) {
        try {
            Editor edit = context.getSharedPreferences(this.a, 0).edit();
            edit.putBoolean(str, z);
            if (edit == null) {
                return;
            }
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Throwable th) {
        }
    }

    public final boolean a(Context context, String str) {
        boolean z = true;
        if (context == null) {
            return z;
        }
        try {
            return context.getSharedPreferences(this.a, 0).getBoolean(str, true);
        } catch (Throwable th) {
            return z;
        }
    }
}
