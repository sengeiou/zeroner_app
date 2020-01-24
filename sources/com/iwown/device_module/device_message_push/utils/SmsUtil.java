package com.iwown.device_module.device_message_push.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.List;

public class SmsUtil {
    public static String[] getSmsApps(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction("android.provider.Telephony.SMS_RECEIVED");
        List<ResolveInfo> receivers = pm.queryBroadcastReceivers(intent, 32);
        String[] result = new String[receivers.size()];
        for (int i = 0; i < receivers.size(); i++) {
            result[i] = ((ResolveInfo) receivers.get(i)).activityInfo.packageName;
        }
        return result;
    }

    public static String[] getSmsAppsTwo(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction("android.provider.Telephony.SMS_DELIVER");
        List<ResolveInfo> receivers = pm.queryBroadcastReceivers(intent, 32);
        String[] result = new String[receivers.size()];
        for (int i = 0; i < receivers.size(); i++) {
            result[i] = ((ResolveInfo) receivers.get(i)).activityInfo.packageName;
        }
        return result;
    }
}
