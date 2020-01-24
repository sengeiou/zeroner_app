package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzbhf;

public final class zzc {
    public static int zzx(Context context, String str) {
        PackageInfo zzy = zzy(context, str);
        if (zzy == null || zzy.applicationInfo == null) {
            return -1;
        }
        Bundle bundle = zzy.applicationInfo.metaData;
        if (bundle != null) {
            return bundle.getInt("com.google.android.gms.version", -1);
        }
        return -1;
    }

    @Nullable
    private static PackageInfo zzy(Context context, String str) {
        try {
            return zzbhf.zzdb(context).getPackageInfo(str, 128);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean zzz(Context context, String str) {
        "com.google.android.gms".equals(str);
        try {
            return (zzbhf.zzdb(context).getApplicationInfo(str, 0).flags & 2097152) != 0;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
