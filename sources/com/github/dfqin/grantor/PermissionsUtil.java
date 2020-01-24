package com.github.dfqin.grantor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import java.io.Serializable;
import java.util.HashMap;

public class PermissionsUtil {
    public static final String TAG = "PermissionGrantor";
    private static HashMap<String, PermissionListener> listenerMap = new HashMap<>();

    public static class TipInfo implements Serializable {
        private static final long serialVersionUID = 1;
        String cancel;
        String content;
        String ensure;
        String title;

        public TipInfo(@Nullable String title2, @Nullable String content2, @Nullable String cancel2, @Nullable String ensure2) {
            this.title = title2;
            this.content = content2;
            this.cancel = cancel2;
            this.ensure = ensure2;
        }
    }

    public static void requestPermission(Context context, PermissionListener listener, String... permission) {
        requestPermission(context, listener, permission, true, null);
    }

    public static void requestPermission(@NonNull Context context, @NonNull PermissionListener listener, @NonNull String[] permission, boolean showTip, @Nullable TipInfo tip) {
        if (listener == null) {
            Log.e(TAG, "listener is null");
        } else if (hasPermission(context, permission)) {
            listener.permissionGranted(permission);
        } else if (VERSION.SDK_INT < 23) {
            listener.permissionDenied(permission);
        } else {
            String key = String.valueOf(System.currentTimeMillis());
            listenerMap.put(key, listener);
            Intent intent = new Intent(context, PermissionActivity.class);
            intent.putExtra("permission", permission);
            intent.putExtra("key", key);
            intent.putExtra("showTip", showTip);
            intent.putExtra("tip", tip);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        if (permissions.length == 0) {
            return false;
        }
        for (String per : permissions) {
            if (PermissionChecker.checkSelfPermission(context, per) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGranted(@NonNull int... grantResult) {
        if (grantResult.length == 0) {
            return false;
        }
        for (int result : grantResult) {
            if (result != 0) {
                return false;
            }
        }
        return true;
    }

    public static void gotoSetting(@NonNull Context context) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    static PermissionListener fetchListener(String key) {
        return (PermissionListener) listenerMap.remove(key);
    }
}
