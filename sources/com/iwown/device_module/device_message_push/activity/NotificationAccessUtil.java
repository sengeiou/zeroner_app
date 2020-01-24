package com.iwown.device_module.device_message_push.activity;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings.Secure;
import android.text.TextUtils;

public class NotificationAccessUtil {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";

    public static boolean isEnabled(Context context) {
        String pkgName = context.getPackageName();
        String flat = Secure.getString(context.getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            String[] names = flat.split(":");
            for (String unflattenFromString : names) {
                ComponentName cn2 = ComponentName.unflattenFromString(unflattenFromString);
                if (cn2 != null && TextUtils.equals(pkgName, cn2.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
