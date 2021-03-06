package com.tencent.open.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: ProGuard */
public class h {
    public static String a(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static int a(String str, String str2) {
        if (str == null && str2 == null) {
            return 0;
        }
        if (str != null && str2 == null) {
            return 1;
        }
        if (str == null && str2 != null) {
            return -1;
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int i = 0;
        while (i < split.length && i < split2.length) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt < parseInt2) {
                    return -1;
                }
                if (parseInt > parseInt2) {
                    return 1;
                }
                i++;
            } catch (NumberFormatException e) {
                return str.compareTo(str2);
            }
        }
        if (split.length > i) {
            return 1;
        }
        if (split2.length > i) {
            return -1;
        }
        return 0;
    }

    public static boolean a(Context context, String str, String str2) {
        f.a("openSDK_LOG.SystemUtils", "OpenUi, validateAppSignatureForPackage");
        try {
            for (Signature charsString : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (j.f(charsString.toCharsString()).equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String b(Context context, String str) {
        Throwable e;
        String str2;
        f.a("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString");
        String str3 = "";
        try {
            String packageName = context.getPackageName();
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            instance.update(signatureArr[0].toByteArray());
            String a = j.a(instance.digest());
            instance.reset();
            f.a("openSDK_LOG.SystemUtils", "-->sign: " + a);
            instance.update(j.i(packageName + "_" + a + "_" + str + ""));
            str2 = j.a(instance.digest());
            try {
                instance.reset();
                f.a("openSDK_LOG.SystemUtils", "-->signEncryped: " + str2);
            } catch (Exception e2) {
                e = e2;
                ThrowableExtension.printStackTrace(e);
                f.b("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString error", e);
                return str2;
            }
        } catch (Exception e3) {
            Throwable th = e3;
            str2 = str3;
            e = th;
        }
        return str2;
    }

    public static boolean a(Context context, Intent intent) {
        if (context == null || intent == null || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
            return false;
        }
        return true;
    }

    public static String a(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static int c(Context context, String str) {
        return a(a(context, "com.tencent.mobileqq"), str);
    }

    public static int d(Context context, String str) {
        return a(a(context, Constants.PACKAGE_TIM), str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00be A[SYNTHETIC, Splitter:B:34:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c3 A[SYNTHETIC, Splitter:B:37:0x00c3] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cf A[SYNTHETIC, Splitter:B:43:0x00cf] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d4 A[SYNTHETIC, Splitter:B:46:0x00d4] */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    @android.annotation.SuppressLint({"SdCardPath"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r9, java.lang.String r10, int r11) {
        /*
            r2 = 0
            r1 = 1
            r0 = 0
            java.lang.String r3 = "openSDK_LOG.SystemUtils"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "-->extractSecureLib, libName: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            com.tencent.open.a.f.c(r3, r4)
            android.content.Context r4 = com.tencent.open.utils.e.a()
            if (r4 != 0) goto L_0x002d
            java.lang.String r1 = "openSDK_LOG.SystemUtils"
            java.lang.String r2 = "-->extractSecureLib, global context is null. "
            com.tencent.open.a.f.c(r1, r2)
        L_0x002c:
            return r0
        L_0x002d:
            java.lang.String r3 = "secure_lib"
            android.content.SharedPreferences r5 = r4.getSharedPreferences(r3, r0)
            java.io.File r3 = new java.io.File
            java.io.File r6 = r4.getFilesDir()
            r3.<init>(r6, r10)
            boolean r6 = r3.exists()
            if (r6 != 0) goto L_0x0080
            java.io.File r6 = r3.getParentFile()
            if (r6 == 0) goto L_0x0052
            boolean r6 = r6.mkdirs()
            if (r6 == 0) goto L_0x0052
            r3.createNewFile()     // Catch:{ IOException -> 0x007b }
        L_0x0052:
            android.content.res.AssetManager r3 = r4.getAssets()     // Catch:{ Exception -> 0x00b1, all -> 0x00cb }
            java.io.InputStream r3 = r3.open(r9)     // Catch:{ Exception -> 0x00b1, all -> 0x00cb }
            r6 = 0
            java.io.FileOutputStream r2 = r4.openFileOutput(r10, r6)     // Catch:{ Exception -> 0x00e4 }
            a(r3, r2)     // Catch:{ Exception -> 0x00e4 }
            android.content.SharedPreferences$Editor r4 = r5.edit()     // Catch:{ Exception -> 0x00e4 }
            java.lang.String r5 = "version"
            r4.putInt(r5, r11)     // Catch:{ Exception -> 0x00e4 }
            r4.commit()     // Catch:{ Exception -> 0x00e4 }
            if (r3 == 0) goto L_0x0074
            r3.close()     // Catch:{ IOException -> 0x00d8 }
        L_0x0074:
            if (r2 == 0) goto L_0x0079
            r2.close()     // Catch:{ IOException -> 0x00da }
        L_0x0079:
            r0 = r1
            goto L_0x002c
        L_0x007b:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0052
        L_0x0080:
            java.lang.String r3 = "version"
            int r3 = r5.getInt(r3, r0)
            java.lang.String r6 = "openSDK_LOG.SystemUtils"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "-->extractSecureLib, libVersion: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r11)
            java.lang.String r8 = " | oldVersion: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.String r7 = r7.toString()
            com.tencent.open.a.f.c(r6, r7)
            if (r11 != r3) goto L_0x0052
            r0 = r1
            goto L_0x002c
        L_0x00b1:
            r1 = move-exception
            r3 = r2
        L_0x00b3:
            java.lang.String r4 = "openSDK_LOG.SystemUtils"
            java.lang.String r5 = "-->extractSecureLib, when copy lib execption."
            com.tencent.open.a.f.b(r4, r5, r1)     // Catch:{ all -> 0x00e2 }
            if (r3 == 0) goto L_0x00c1
            r3.close()     // Catch:{ IOException -> 0x00dc }
        L_0x00c1:
            if (r2 == 0) goto L_0x002c
            r2.close()     // Catch:{ IOException -> 0x00c8 }
            goto L_0x002c
        L_0x00c8:
            r1 = move-exception
            goto L_0x002c
        L_0x00cb:
            r0 = move-exception
            r3 = r2
        L_0x00cd:
            if (r3 == 0) goto L_0x00d2
            r3.close()     // Catch:{ IOException -> 0x00de }
        L_0x00d2:
            if (r2 == 0) goto L_0x00d7
            r2.close()     // Catch:{ IOException -> 0x00e0 }
        L_0x00d7:
            throw r0
        L_0x00d8:
            r0 = move-exception
            goto L_0x0074
        L_0x00da:
            r0 = move-exception
            goto L_0x0079
        L_0x00dc:
            r1 = move-exception
            goto L_0x00c1
        L_0x00de:
            r1 = move-exception
            goto L_0x00d2
        L_0x00e0:
            r1 = move-exception
            goto L_0x00d7
        L_0x00e2:
            r0 = move-exception
            goto L_0x00cd
        L_0x00e4:
            r1 = move-exception
            goto L_0x00b3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.h.a(java.lang.String, java.lang.String, int):boolean");
    }

    private static long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        long j = 0;
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr, 0, bArr.length);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
                j += (long) read;
            } else {
                f.c("openSDK_LOG.SystemUtils", "-->copy, copyed size is: " + j);
                return j;
            }
        }
    }

    public static int a(String str) {
        if ("shareToQQ".equals(str)) {
            return Constants.REQUEST_QQ_SHARE;
        }
        if ("shareToQzone".equals(str)) {
            return Constants.REQUEST_QZONE_SHARE;
        }
        if ("addToQQFavorites".equals(str)) {
            return Constants.REQUEST_QQ_FAVORITES;
        }
        if ("sendToMyComputer".equals(str)) {
            return Constants.REQUEST_SEND_TO_MY_COMPUTER;
        }
        if ("shareToTroopBar".equals(str)) {
            return Constants.REQUEST_SHARE_TO_TROOP_BAR;
        }
        if ("action_login".equals(str)) {
            return Constants.REQUEST_LOGIN;
        }
        if ("action_request".equals(str)) {
            return Constants.REQUEST_API;
        }
        return -1;
    }

    public static String a(int i) {
        if (i == 10103) {
            return "shareToQQ";
        }
        if (i == 10104) {
            return "shareToQzone";
        }
        if (i == 10105) {
            return "addToQQFavorites";
        }
        if (i == 10106) {
            return "sendToMyComputer";
        }
        if (i == 10107) {
            return "shareToTroopBar";
        }
        if (i == 11101) {
            return "action_login";
        }
        if (i == 10100) {
            return "action_request";
        }
        return null;
    }
}
