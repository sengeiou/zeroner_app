package com.iwown.lib_common.permissions;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.github.dfqin.grantor.PermissionsUtil.TipInfo;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class PermissionsUtils {

    public interface PermissinCallBack {
        void callBackFial();

        void callBackOk();
    }

    public static void handleCONTACTS(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "防问通讯录", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity, "android.permission.READ_CONTACTS")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.READ_CONTACTS"}, false, tip);
    }

    public static void handleSMS(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取短信权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity, "android.permission.READ_SMS")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.READ_SMS"}, false, tip);
    }

    public static void handleLOCATION(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取地理位置权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity.getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, false, tip);
    }

    public static void handleLOCATION(Activity activity, final PermissinCallBack callBack, boolean showTip) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取地理位置权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity.getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, false, tip);
    }

    public static void handleSTORAGE(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取存储权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity, "android.permission.READ_EXTERNAL_STORAGE")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, false, tip);
    }

    public static void handleSTORAGEWrite(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取存储权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, false, tip);
    }

    public static void handleCAMER(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取摄像头权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity, "android.permission.CAMERA")) {
            KLog.e("有摄像头权限");
            callBack.callBackOk();
            return;
        }
        KLog.e("没有摄像头权限");
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.CAMERA"}, false, tip);
    }

    public static void handlePhone(Activity activity, final PermissinCallBack callBack) {
        TipInfo tip = new TipInfo("注意:", "功能需要获取手机状态权限", "不让看", "打开权限");
        if (PermissionsUtil.hasPermission(activity, "android.permission.READ_PHONE_STATE")) {
            callBack.callBackOk();
            return;
        }
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            public void permissionGranted(@NonNull String[] permissions) {
                callBack.callBackOk();
            }

            public void permissionDenied(@NonNull String[] permissions) {
                callBack.callBackFial();
            }
        }, new String[]{"android.permission.READ_PHONE_STATE"}, false, tip);
    }

    public static void handPermission(FragmentActivity activity) {
        new RxPermissions(activity).requestEach("android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_CONTACTS", "android.permission.READ_SMS", "android.permission.READ_PHONE_STATE", "android.permission.READ_CALL_LOG", "android.permission.CALL_PHONE").subscribe(PermissionsUtils$$Lambda$0.$instance);
    }

    static final /* synthetic */ void lambda$handPermission$0$PermissionsUtils(Permission permission) throws Exception {
        if (!permission.granted && permission.shouldShowRequestPermissionRationale) {
        }
    }

    public static void handPermissionSTORAGE(FragmentActivity activity) {
        new RxPermissions(activity).requestEach("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").subscribe(PermissionsUtils$$Lambda$1.$instance);
    }

    static final /* synthetic */ void lambda$handPermissionSTORAGE$1$PermissionsUtils(Permission permission) throws Exception {
        if (!permission.granted && permission.shouldShowRequestPermissionRationale) {
        }
    }

    public static boolean hasPermission(Activity activity, @NonNull String... permissions) {
        return PermissionsUtil.hasPermission(activity, permissions);
    }
}
