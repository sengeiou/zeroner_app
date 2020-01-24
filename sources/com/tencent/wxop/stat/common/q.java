package com.tencent.wxop.stat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class q {
    private static SharedPreferences a = null;

    public static int a(Context context, String str, int i) {
        return a(context).getInt(l.a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString()), i);
    }

    public static long a(Context context, String str, long j) {
        return a(context).getLong(l.a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString()), j);
    }

    static synchronized SharedPreferences a(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (q.class) {
            SharedPreferences sharedPreferences2 = context.getSharedPreferences(".mta-wxop", 0);
            a = sharedPreferences2;
            if (sharedPreferences2 == null) {
                a = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences = a;
        }
        return sharedPreferences;
    }

    public static String a(Context context, String str, String str2) {
        return a(context).getString(l.a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString()), str2);
    }

    public static void b(Context context, String str, int i) {
        String a2 = l.a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString());
        Editor edit = a(context).edit();
        edit.putInt(a2, i);
        edit.commit();
    }

    public static void b(Context context, String str, long j) {
        String a2 = l.a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString());
        Editor edit = a(context).edit();
        edit.putLong(a2, j);
        edit.commit();
    }

    public static void b(Context context, String str, String str2) {
        String a2 = l.a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString());
        Editor edit = a(context).edit();
        edit.putString(a2, str2);
        edit.commit();
    }
}
