package com.youzan.sdk.tool;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.youzan.sdk.YouzanException;

/* compiled from: SchemeIntent */
public final class e {

    /* renamed from: ʻ reason: contains not printable characters */
    private static final String f355 = "mailto";

    /* renamed from: ʼ reason: contains not printable characters */
    private static final String f356 = "geo";

    /* renamed from: ʽ reason: contains not printable characters */
    private static final String f357 = "网页请求打开应用";

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f358 = "weixin";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final String f359 = "alipays";

    /* renamed from: ˎ reason: contains not printable characters */
    private static final String f360 = "mqqwpa";

    /* renamed from: ˏ reason: contains not printable characters */
    private static final String f361 = "sms";

    /* renamed from: ͺ reason: contains not printable characters */
    private static final String f362 = "打开";

    /* renamed from: ι reason: contains not printable characters */
    private static final String f363 = "系统未安装相应应用";

    /* renamed from: ᐝ reason: contains not printable characters */
    private static final String f364 = "tel";

    /* renamed from: ˊ reason: contains not printable characters */
    private static boolean m113(Intent intent, Activity activity) throws YouzanException {
        Activity target = activity.getParent();
        if (target == null) {
            target = activity;
        }
        try {
            intent.setFlags(536870912);
            return target.startActivityIfNeeded(intent, -1);
        } catch (ActivityNotFoundException e) {
            throw new YouzanException(f363);
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static void m111(Intent intent, Context context) throws YouzanException {
        try {
            intent.setFlags(276824064);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            throw new YouzanException(f363);
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static boolean m114(@NonNull String scheme) {
        return f361.equalsIgnoreCase(scheme) || f364.equalsIgnoreCase(scheme) || f355.equalsIgnoreCase(scheme) || f356.equalsIgnoreCase(scheme);
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public static boolean m116(@NonNull String scheme) {
        return f358.equalsIgnoreCase(scheme) || f359.equalsIgnoreCase(scheme) || f360.equalsIgnoreCase(scheme);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static boolean m112(@NonNull Context context, Uri uri) {
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme) || !m114(scheme)) {
            return false;
        }
        m110(context, uri, f357);
        return true;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public static boolean m115(@NonNull Context context, Uri uri) {
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme) || !m116(scheme)) {
            return false;
        }
        try {
            Intent intent = Intent.parseUri(uri.toString(), 1);
            if (context instanceof Activity) {
                return m113(intent, (Activity) context);
            }
            m111(intent, context);
            return true;
        } catch (YouzanException e) {
            Toast.makeText(context, e.getMsg(), 0).show();
        } catch (Throwable th) {
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static void m110(final Context context, final Uri uri, String msg) {
        new Builder(context).setMessage(msg).setPositiveButton(f362, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(uri);
                if (!(context instanceof Activity)) {
                    intent.setFlags(276824064);
                }
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        }).setNegativeButton(17039360, null).setIcon(17301543).show();
    }
}
