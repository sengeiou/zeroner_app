package com.tencent.bugly.beta.global;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.tencent.bugly.beta.utils.c;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.p;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.io.File;
import java.util.Map;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: BUGLY */
public class a {
    public static int a(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 0;
            }
            if (activeNetworkInfo.getType() == 1) {
                return 1;
            }
            if (activeNetworkInfo.getType() == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
                if (telephonyManager != null) {
                    switch (telephonyManager.getNetworkType()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            return 2;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            return 3;
                        case 13:
                            return 4;
                        default:
                            return 0;
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            if (!an.a(e)) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public static int a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static BitmapDrawable a(Bitmap bitmap, int i, int i2, float f) {
        int i3 = (int) (((float) (e.E.B.widthPixels * e.E.B.heightPixels)) * 0.8f);
        if (bitmap == null || i * i2 > i3) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, i, i2);
        RectF rectF = new RectF(rect);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawRoundRect(rectF, f, f, paint);
        canvas.drawRect(0.0f, f, (float) i, (float) i2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), rect, paint);
        return new BitmapDrawable(createBitmap);
    }

    public static Bitmap a(Context context, int i, Object... objArr) {
        File file;
        int i2;
        int ceil;
        int min;
        int i3;
        if (objArr == null || objArr.length <= 0 || (i != 0 && i != 1)) {
            return null;
        }
        if (i == 0) {
            try {
                if (TextUtils.isEmpty(objArr[0])) {
                    return null;
                }
                File file2 = new File(objArr[0]);
                if (!file2.exists() || file2.length() > PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
                    return null;
                }
                i2 = 0;
                file = file2;
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        } else if (i == 1) {
            i2 = objArr[0].intValue();
            file = null;
        } else {
            i2 = 0;
            file = null;
        }
        int i4 = e.E.B.widthPixels;
        Options options = null;
        if (i4 > 0) {
            options = new Options();
            options.inJustDecodeBounds = true;
            if (i == 0) {
                BitmapFactory.decodeFile(file.getPath(), options);
            } else if (i == 1) {
                BitmapFactory.decodeResource(context.getResources(), i2, options);
            }
            int i5 = (int) ((((float) options.outHeight) / ((float) options.outWidth)) * ((float) i4));
            int min2 = Math.min(i4, i5);
            double d = (double) options.outWidth;
            double d2 = (double) options.outHeight;
            if (i4 * i5 == -1) {
                ceil = 1;
            } else {
                ceil = (int) Math.ceil(Math.sqrt(((d * d2) / ((double) i4)) * ((double) i5)));
            }
            if (min2 == -1) {
                min = 128;
            } else {
                min = (int) Math.min(Math.floor(d / ((double) min2)), Math.floor(d2 / ((double) min2)));
            }
            if (min < ceil) {
            }
            if (i4 * i5 == -1 && min2 == -1) {
                min = 1;
            } else if (min2 == -1) {
                min = ceil;
            }
            if (min <= 8) {
                i3 = 1;
                while (i3 < min) {
                    i3 <<= 1;
                }
            } else {
                i3 = ((min + 7) / 8) * 8;
            }
            options.inSampleSize = i3;
            options.inJustDecodeBounds = false;
            options.inInputShareable = true;
            options.inPurgeable = true;
        }
        if (i == 0) {
            return BitmapFactory.decodeFile(file.getPath(), options);
        }
        if (i == 1) {
            return BitmapFactory.decodeResource(context.getResources(), i2, options);
        }
        return null;
    }

    public static boolean a(Context context, File file, String str) {
        if (file != null) {
            try {
                if (file.exists() && file.getName().endsWith(ShareConstants.PATCH_SUFFIX)) {
                    String a = ap.a(file, MessageDigestAlgorithms.MD5);
                    if (TextUtils.isEmpty(str) || TextUtils.equals(str.toUpperCase(), a)) {
                        Runtime.getRuntime().exec("chmod 777 " + file.getAbsolutePath());
                        Intent intent = new Intent("android.intent.action.VIEW");
                        String str2 = "application/vnd.android.package-archive";
                        if (VERSION.SDK_INT >= 24) {
                            String str3 = "android.support.v4.content.FileProvider";
                            intent.addFlags(1);
                            if (Class.forName(str3) == null) {
                                an.e("can't find class android.support.v4.content.FileProvider", new Object[0]);
                                return false;
                            }
                            Uri uri = (Uri) ap.a(str3, "getUriForFile", null, new Class[]{Context.class, String.class, File.class}, new Object[]{context, com.tencent.bugly.crashreport.common.info.a.a(context).d + ".fileProvider", file});
                            if (uri == null) {
                                an.e("file location is " + file.toString(), new Object[0]);
                                an.e("install failed, contentUri is null!", new Object[0]);
                                return false;
                            }
                            an.c("contentUri is " + uri, new Object[0]);
                            intent.setDataAndType(uri, str2);
                        } else {
                            intent.setDataAndType(Uri.fromFile(file), str2);
                        }
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                        a("installApkMd5", a);
                        return true;
                    }
                    an.a("md5 error [file md5: %s] [target md5: %s]", a, str);
                    return false;
                }
            } catch (Exception e) {
                if (!an.b(e)) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }
        return false;
    }

    public static boolean a(File file, String str, String str2) {
        if (file != null) {
            try {
                if (file.exists()) {
                    String a = ap.a(file, str2);
                    if (!TextUtils.isEmpty(str) && TextUtils.equals(str.toUpperCase(), a)) {
                        return true;
                    }
                    an.a("checkFileUniqueId failed [file  uniqueId %s] [target uniqueId %s]", a, str);
                    return false;
                }
            } catch (Exception e) {
                an.e("checkFileUniqueId exception", new Object[0]);
            }
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0043 A[SYNTHETIC, Splitter:B:30:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0048 A[Catch:{ IOException -> 0x004c }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0066 A[SYNTHETIC, Splitter:B:47:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x006b A[Catch:{ IOException -> 0x006f }] */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r6, java.io.File r7) {
        /*
            r3 = 0
            r0 = 0
            r1 = 0
            r2 = 0
            if (r6 == 0) goto L_0x0012
            boolean r4 = r6.exists()     // Catch:{ Exception -> 0x007d, all -> 0x0062 }
            if (r4 == 0) goto L_0x0012
            boolean r4 = r6.isDirectory()     // Catch:{ Exception -> 0x007d, all -> 0x0062 }
            if (r4 == 0) goto L_0x0022
        L_0x0012:
            if (r3 == 0) goto L_0x0017
            r1.close()     // Catch:{ IOException -> 0x001d }
        L_0x0017:
            if (r3 == 0) goto L_0x001c
            r2.close()     // Catch:{ IOException -> 0x001d }
        L_0x001c:
            return r0
        L_0x001d:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x001c
        L_0x0022:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x007d, all -> 0x0062 }
            r4.<init>(r6)     // Catch:{ Exception -> 0x007d, all -> 0x0062 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0080, all -> 0x0074 }
            r1 = 0
            r2.<init>(r7, r1)     // Catch:{ Exception -> 0x0080, all -> 0x0074 }
            r1 = 1048576(0x100000, float:1.469368E-39)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x003c, all -> 0x0076 }
        L_0x0031:
            int r3 = r4.read(r1)     // Catch:{ Exception -> 0x003c, all -> 0x0076 }
            if (r3 <= 0) goto L_0x0051
            r5 = 0
            r2.write(r1, r5, r3)     // Catch:{ Exception -> 0x003c, all -> 0x0076 }
            goto L_0x0031
        L_0x003c:
            r1 = move-exception
            r3 = r4
        L_0x003e:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0079 }
            if (r3 == 0) goto L_0x0046
            r3.close()     // Catch:{ IOException -> 0x004c }
        L_0x0046:
            if (r2 == 0) goto L_0x001c
            r2.close()     // Catch:{ IOException -> 0x004c }
            goto L_0x001c
        L_0x004c:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x001c
        L_0x0051:
            r0 = 1
            if (r4 == 0) goto L_0x0057
            r4.close()     // Catch:{ IOException -> 0x005d }
        L_0x0057:
            if (r2 == 0) goto L_0x001c
            r2.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x001c
        L_0x005d:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x001c
        L_0x0062:
            r0 = move-exception
            r4 = r3
        L_0x0064:
            if (r4 == 0) goto L_0x0069
            r4.close()     // Catch:{ IOException -> 0x006f }
        L_0x0069:
            if (r3 == 0) goto L_0x006e
            r3.close()     // Catch:{ IOException -> 0x006f }
        L_0x006e:
            throw r0
        L_0x006f:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x006e
        L_0x0074:
            r0 = move-exception
            goto L_0x0064
        L_0x0076:
            r0 = move-exception
            r3 = r2
            goto L_0x0064
        L_0x0079:
            r0 = move-exception
            r4 = r3
            r3 = r2
            goto L_0x0064
        L_0x007d:
            r1 = move-exception
            r2 = r3
            goto L_0x003e
        L_0x0080:
            r1 = move-exception
            r2 = r3
            r3 = r4
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.global.a.a(java.io.File, java.io.File):boolean");
    }

    public static Bitmap a(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void a(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (!file2.delete()) {
                        an.e("cannot delete file:%s", file2.getAbsolutePath());
                    }
                }
            }
        }
    }

    public static synchronized <T extends Parcelable> boolean a(String str, T t) {
        boolean z = false;
        synchronized (a.class) {
            if (t != null) {
                byte[] a = ap.a((Parcelable) t);
                if (a != null && p.a.a(1002, str, a)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public static synchronized <T extends Parcelable> T a(String str, Creator<T> creator) {
        T t;
        synchronized (a.class) {
            Map c = p.a.c();
            if (c == null) {
                t = null;
            } else {
                byte[] bArr = (byte[]) c.get(str);
                if (bArr == null || bArr.length <= 0) {
                    t = null;
                } else {
                    t = (Parcelable) ap.a(bArr, creator);
                }
            }
        }
        return t;
    }

    public static synchronized boolean a(String str) {
        boolean c;
        synchronized (a.class) {
            c = p.a.c(str);
        }
        return c;
    }

    public static String b(String str) {
        return new c(str).a();
    }

    public static void a(String str, String str2) {
        if (e.E.A != null) {
            e.E.A.edit().putString(str, str2).apply();
        }
    }

    public static void a(String str, boolean z) {
        if (e.E.A != null) {
            e.E.A.edit().putBoolean(str, z).apply();
        }
    }

    public static String b(String str, String str2) {
        if (e.E.A != null) {
            return e.E.A.getString(str, str2);
        }
        return str2;
    }

    public static boolean b(String str, boolean z) {
        if (e.E.A != null) {
            return e.E.A.getBoolean(str, z);
        }
        return z;
    }

    public static String a(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null || applicationInfo.metaData == null) {
                return null;
            }
            Object obj = applicationInfo.metaData.get(str);
            if (obj != null) {
                return String.valueOf(obj);
            }
            return null;
        } catch (Exception e) {
            an.c(a.class, "getManifestMetaDataValue exception:" + e.getMessage(), new Object[0]);
            return null;
        }
    }
}
