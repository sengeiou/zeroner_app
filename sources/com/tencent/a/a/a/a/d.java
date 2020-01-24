package com.tencent.a.a.a.a;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

final class d extends f {
    public d(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void a(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to sharedPreferences");
            Editor edit = PreferenceManager.getDefaultSharedPreferences(this.a).edit();
            edit.putString(h.f("4kU71lN96TJUomD1vOU9lgj9Tw=="), str);
            edit.commit();
        }
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final String b() {
        String string;
        synchronized (this) {
            Log.i("MID", "read mid from sharedPreferences");
            string = PreferenceManager.getDefaultSharedPreferences(this.a).getString(h.f("4kU71lN96TJUomD1vOU9lgj9Tw=="), null);
        }
        return string;
    }
}
