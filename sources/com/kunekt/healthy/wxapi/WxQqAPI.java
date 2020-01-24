package com.kunekt.healthy.wxapi;

import android.content.Context;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

public class WxQqAPI {
    private static IWXAPI iwxapi;
    private static Tencent tencent;

    public static void initIWXAPI(Context context) {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context.getApplicationContext(), "wx695ef7ad14cc332e", false);
            iwxapi.registerApp("wx695ef7ad14cc332e");
        }
    }

    public static void initQQAPI(Context context) {
        if (tencent == null) {
            tencent = Tencent.createInstance("1104800774", context.getApplicationContext());
        }
    }

    public static void initQQAndWxAPI(Context context) {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context.getApplicationContext(), "wx695ef7ad14cc332e", false);
            iwxapi.registerApp("wx695ef7ad14cc332e");
        }
        if (tencent == null) {
            tencent = Tencent.createInstance("1104800774", context.getApplicationContext());
        }
    }

    public static IWXAPI getIwxapi(Context context) {
        if (iwxapi == null) {
            initIWXAPI(context.getApplicationContext());
        }
        return iwxapi;
    }

    public static Tencent getTencent(Context context) {
        if (tencent == null) {
            initQQAPI(context.getApplicationContext());
        }
        return tencent;
    }
}
