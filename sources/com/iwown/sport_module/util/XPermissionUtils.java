package com.iwown.sport_module.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class XPermissionUtils {
    public static int AUDIO = 3;
    public static int CAMERA = 2;
    public static int EXTERNAL = 4;
    public static int LOCATION = 1;
    public static int PHONE = 0;
    private static OnPermissionListener mOnPermissionListener;
    private static int mRequestCode = -1;

    public interface OnPermissionListener {
        void onPermissionDenied();

        void onPermissionGranted();
    }

    @TargetApi(23)
    public static void requestPermissions(Context context, int requestCode, String[] permissions, OnPermissionListener listener) {
        if (context instanceof Activity) {
            mOnPermissionListener = listener;
            List<String> deniedPermissions = getDeniedPermissions(context, permissions);
            if (deniedPermissions.size() > 0) {
                mRequestCode = requestCode;
                ((Activity) context).requestPermissions((String[]) deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (mOnPermissionListener != null) {
                mOnPermissionListener.onPermissionGranted();
            }
        } else {
            throw new RuntimeException("Context must be an Activity");
        }
    }

    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == -1) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode && mOnPermissionListener != null) {
            if (verifyPermissions(grantResults)) {
                mOnPermissionListener.onPermissionGranted();
            } else {
                mOnPermissionListener.onPermissionDenied();
            }
        }
    }

    public static boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != 0) {
                Log.d("权限什么的？ --》 ", "   " + grantResult);
                return false;
            }
        }
        return true;
    }

    public static boolean lacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context.getApplicationContext(), permission) == -1;
    }
}
