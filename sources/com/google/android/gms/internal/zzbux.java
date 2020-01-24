package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources.NotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzi;
import com.iwown.data_link.consts.UserConst;

public final class zzbux {
    private static int zzhef = -1;

    public static int zzdg(Context context) {
        boolean z = false;
        if (zzhef == -1) {
            if (zzi.zzcs(context)) {
                zzhef = 3;
            } else {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager.hasSystemFeature("com.google.android.tv") || packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback")) {
                    zzhef = 0;
                } else {
                    if (zzi.zza(context.getResources()) && !zzdh(context)) {
                        zzhef = 2;
                    } else {
                        if (!TextUtils.isEmpty(Build.PRODUCT) && Build.PRODUCT.startsWith("glass_")) {
                            z = true;
                        }
                        if (z) {
                            zzhef = 6;
                        } else {
                            zzhef = 1;
                        }
                    }
                }
            }
        }
        return zzhef;
    }

    private static boolean zzdh(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(UserConst.PHONE)).getPhoneType() != 0;
        } catch (NotFoundException e) {
            Log.wtf("Fitness", "Unable to determine type of device, assuming phone.", e);
            return true;
        }
    }
}
