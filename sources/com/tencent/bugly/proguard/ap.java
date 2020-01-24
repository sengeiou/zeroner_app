package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Process;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: BUGLY */
public class ap {
    private static Map<String, String> a = null;
    private static boolean b = false;

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            StringWriter stringWriter = new StringWriter();
            ThrowableExtension.printStackTrace(th, new PrintWriter(stringWriter));
            return stringWriter.getBuffer().toString();
        } catch (Throwable th2) {
            if (!an.a(th2)) {
                ThrowableExtension.printStackTrace(th2);
            }
            return "fail";
        }
    }

    public static String a() {
        return a(System.currentTimeMillis());
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(j));
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static String a(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static byte[] a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        an.c("rqdp{  enD:} %d %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            ax a2 = aw.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        try {
            ax a2 = aw.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            an.d("encrytype %d %s", Integer.valueOf(i), str);
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARNING: type inference failed for: r3v3, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r2v11, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0054 A[Catch:{ Throwable -> 0x005f }, LOOP:0: B:23:0x0054->B:25:0x005a, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0066 A[Catch:{ all -> 0x0087 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006b A[SYNTHETIC, Splitter:B:32:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0070 A[SYNTHETIC, Splitter:B:35:0x0070] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0082 A[Catch:{ Throwable -> 0x005f }, LOOP:1: B:38:0x007c->B:41:0x0082, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008a A[SYNTHETIC, Splitter:B:44:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f A[SYNTHETIC, Splitter:B:47:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009b A[EDGE_INSN: B:51:0x009b->B:52:? ?: BREAK  
    EDGE_INSN: B:51:0x009b->B:52:? ?: BREAK  
    EDGE_INSN: B:51:0x009b->B:52:? ?: BREAK  , SYNTHETIC, Splitter:B:51:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009b A[EDGE_INSN: B:51:0x009b->B:52:? ?: BREAK  
    EDGE_INSN: B:51:0x009b->B:52:? ?: BREAK  , SYNTHETIC, Splitter:B:51:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00aa A[SYNTHETIC, Splitter:B:54:0x00aa] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00af A[SYNTHETIC, Splitter:B:57:0x00af] */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.io.File r9, java.lang.String r10, java.lang.String r11) {
        /*
            r0 = 0
            r8 = 0
            if (r10 == 0) goto L_0x000a
            int r1 = r10.length()
            if (r1 != 0) goto L_0x000b
        L_0x000a:
            return r0
        L_0x000b:
            java.lang.String r1 = "rqdp{  ZF start}"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r1, r2)
            if (r9 == 0) goto L_0x00f6
            boolean r1 = r9.exists()     // Catch:{ Throwable -> 0x00e8, all -> 0x00da }
            if (r1 == 0) goto L_0x00f6
            boolean r1 = r9.canRead()     // Catch:{ Throwable -> 0x00e8, all -> 0x00da }
            if (r1 == 0) goto L_0x00f6
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00e8, all -> 0x00da }
            r2.<init>(r9)     // Catch:{ Throwable -> 0x00e8, all -> 0x00da }
            java.lang.String r11 = r9.getName()     // Catch:{ Throwable -> 0x00ed, all -> 0x00df }
            r3 = r2
        L_0x002b:
            java.lang.String r1 = "UTF-8"
            byte[] r1 = r10.getBytes(r1)     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            r4.<init>(r1)     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            r1.<init>()     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x00f2, all -> 0x00e4 }
            r5 = 8
            r2.setMethod(r5)     // Catch:{ Throwable -> 0x005f }
            java.util.zip.ZipEntry r5 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x005f }
            r5.<init>(r11)     // Catch:{ Throwable -> 0x005f }
            r2.putNextEntry(r5)     // Catch:{ Throwable -> 0x005f }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Throwable -> 0x005f }
            if (r3 == 0) goto L_0x007c
        L_0x0054:
            int r6 = r3.read(r5)     // Catch:{ Throwable -> 0x005f }
            if (r6 <= 0) goto L_0x007c
            r7 = 0
            r2.write(r5, r7, r6)     // Catch:{ Throwable -> 0x005f }
            goto L_0x0054
        L_0x005f:
            r1 = move-exception
        L_0x0060:
            boolean r4 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x0087 }
            if (r4 != 0) goto L_0x0069
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0087 }
        L_0x0069:
            if (r3 == 0) goto L_0x006e
            r3.close()     // Catch:{ IOException -> 0x00c6 }
        L_0x006e:
            if (r2 == 0) goto L_0x0073
            r2.close()     // Catch:{ IOException -> 0x00cb }
        L_0x0073:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r1, r2)
            goto L_0x000a
        L_0x007c:
            int r6 = r4.read(r5)     // Catch:{ Throwable -> 0x005f }
            if (r6 <= 0) goto L_0x009b
            r7 = 0
            r2.write(r5, r7, r6)     // Catch:{ Throwable -> 0x005f }
            goto L_0x007c
        L_0x0087:
            r0 = move-exception
        L_0x0088:
            if (r3 == 0) goto L_0x008d
            r3.close()     // Catch:{ IOException -> 0x00d0 }
        L_0x008d:
            if (r2 == 0) goto L_0x0092
            r2.close()     // Catch:{ IOException -> 0x00d5 }
        L_0x0092:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r1, r2)
            throw r0
        L_0x009b:
            r2.closeEntry()     // Catch:{ Throwable -> 0x005f }
            r2.flush()     // Catch:{ Throwable -> 0x005f }
            r2.finish()     // Catch:{ Throwable -> 0x005f }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x005f }
            if (r3 == 0) goto L_0x00ad
            r3.close()     // Catch:{ IOException -> 0x00bc }
        L_0x00ad:
            if (r2 == 0) goto L_0x00b2
            r2.close()     // Catch:{ IOException -> 0x00c1 }
        L_0x00b2:
            java.lang.String r1 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r1, r2)
            goto L_0x000a
        L_0x00bc:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00ad
        L_0x00c1:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00b2
        L_0x00c6:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x006e
        L_0x00cb:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0073
        L_0x00d0:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x008d
        L_0x00d5:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0092
        L_0x00da:
            r1 = move-exception
            r2 = r0
            r3 = r0
            r0 = r1
            goto L_0x0088
        L_0x00df:
            r1 = move-exception
            r3 = r2
            r2 = r0
            r0 = r1
            goto L_0x0088
        L_0x00e4:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L_0x0088
        L_0x00e8:
            r1 = move-exception
            r2 = r0
            r3 = r0
            goto L_0x0060
        L_0x00ed:
            r1 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0060
        L_0x00f2:
            r1 = move-exception
            r2 = r0
            goto L_0x0060
        L_0x00f6:
            r3 = r0
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(java.io.File, java.lang.String, java.lang.String):byte[]");
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        String str = "[Util] Zip %d bytes data with type %s";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        an.c(str, objArr);
        try {
            ar a2 = aq.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        String str = "[Util] Unzip %d bytes data with type %s";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        an.c(str, objArr);
        try {
            ar a2 = aq.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        byte[] bArr2 = null;
        if (bArr == null) {
            return bArr2;
        }
        try {
            return a(a(bArr, i), i2, str);
        } catch (Throwable th) {
            if (an.a(th)) {
                return bArr2;
            }
            ThrowableExtension.printStackTrace(th);
            return bArr2;
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        try {
            return b(b(bArr, i2, str), i);
        } catch (Exception e) {
            if (!an.a(e)) {
                ThrowableExtension.printStackTrace(e);
            }
            return null;
        }
    }

    public static long b() {
        try {
            return (86400000 * ((System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset())) / 86400000)) - ((long) TimeZone.getDefault().getRawOffset());
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return -1;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & UnsignedBytes.MAX_VALUE);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
            instance.update(bArr);
            return a(instance.digest());
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v7, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v9, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v1
      assigns: []
      uses: []
      mth insns count: 53
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x005a A[Catch:{ all -> 0x0076 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x005f A[SYNTHETIC, Splitter:B:38:0x005f] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x006d A[SYNTHETIC, Splitter:B:45:0x006d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x0054=Splitter:B:33:0x0054, B:15:0x002b=Splitter:B:15:0x002b} */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r6, java.lang.String r7) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x000f
            boolean r1 = r6.exists()
            if (r1 == 0) goto L_0x000f
            boolean r1 = r6.canRead()
            if (r1 != 0) goto L_0x0010
        L_0x000f:
            return r0
        L_0x0010:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x007a, NoSuchAlgorithmException -> 0x0052, all -> 0x0068 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x007a, NoSuchAlgorithmException -> 0x0052, all -> 0x0068 }
            java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r7)     // Catch:{ IOException -> 0x002a, NoSuchAlgorithmException -> 0x0078 }
            r3 = 102400(0x19000, float:1.43493E-40)
            byte[] r3 = new byte[r3]     // Catch:{ IOException -> 0x002a, NoSuchAlgorithmException -> 0x0078 }
        L_0x001e:
            int r4 = r2.read(r3)     // Catch:{ IOException -> 0x002a, NoSuchAlgorithmException -> 0x0078 }
            r5 = -1
            if (r4 == r5) goto L_0x003f
            r5 = 0
            r1.update(r3, r5, r4)     // Catch:{ IOException -> 0x002a, NoSuchAlgorithmException -> 0x0078 }
            goto L_0x001e
        L_0x002a:
            r1 = move-exception
        L_0x002b:
            boolean r3 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x0076 }
            if (r3 != 0) goto L_0x0034
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0076 }
        L_0x0034:
            if (r2 == 0) goto L_0x000f
            r2.close()     // Catch:{ IOException -> 0x003a }
            goto L_0x000f
        L_0x003a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x000f
        L_0x003f:
            byte[] r1 = r1.digest()     // Catch:{ IOException -> 0x002a, NoSuchAlgorithmException -> 0x0078 }
            java.lang.String r0 = a(r1)     // Catch:{ IOException -> 0x002a, NoSuchAlgorithmException -> 0x0078 }
            if (r2 == 0) goto L_0x000f
            r2.close()     // Catch:{ IOException -> 0x004d }
            goto L_0x000f
        L_0x004d:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x000f
        L_0x0052:
            r1 = move-exception
            r2 = r0
        L_0x0054:
            boolean r3 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x0076 }
            if (r3 != 0) goto L_0x005d
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0076 }
        L_0x005d:
            if (r2 == 0) goto L_0x000f
            r2.close()     // Catch:{ IOException -> 0x0063 }
            goto L_0x000f
        L_0x0063:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x000f
        L_0x0068:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x006b:
            if (r2 == 0) goto L_0x0070
            r2.close()     // Catch:{ IOException -> 0x0071 }
        L_0x0070:
            throw r0
        L_0x0071:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0070
        L_0x0076:
            r0 = move-exception
            goto L_0x006b
        L_0x0078:
            r1 = move-exception
            goto L_0x0054
        L_0x007a:
            r1 = move-exception
            r2 = r0
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(java.io.File, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x00ab A[Catch:{ all -> 0x0119 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00b0 A[SYNTHETIC, Splitter:B:48:0x00b0] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b5 A[SYNTHETIC, Splitter:B:51:0x00b5] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00f9 A[SYNTHETIC, Splitter:B:76:0x00f9] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00fe A[SYNTHETIC, Splitter:B:79:0x00fe] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r7, java.io.File r8, int r9) {
        /*
            r3 = 0
            r5 = 1000(0x3e8, float:1.401E-42)
            r1 = 0
            java.lang.String r0 = "rqdp{  ZF start}"
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.an.c(r0, r2)
            if (r7 == 0) goto L_0x0016
            if (r8 == 0) goto L_0x0016
            boolean r0 = r7.equals(r8)
            if (r0 == 0) goto L_0x0020
        L_0x0016:
            java.lang.String r0 = "rqdp{  err ZF 1R!}"
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.an.d(r0, r2)
            r0 = r1
        L_0x001f:
            return r0
        L_0x0020:
            boolean r0 = r7.exists()
            if (r0 == 0) goto L_0x002c
            boolean r0 = r7.canRead()
            if (r0 != 0) goto L_0x0036
        L_0x002c:
            java.lang.String r0 = "rqdp{  !sFile.exists() || !sFile.canRead(),pls check ,return!}"
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.an.d(r0, r2)
            r0 = r1
            goto L_0x001f
        L_0x0036:
            java.io.File r0 = r8.getParentFile()     // Catch:{ Throwable -> 0x0064 }
            if (r0 == 0) goto L_0x004d
            java.io.File r0 = r8.getParentFile()     // Catch:{ Throwable -> 0x0064 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x0064 }
            if (r0 != 0) goto L_0x004d
            java.io.File r0 = r8.getParentFile()     // Catch:{ Throwable -> 0x0064 }
            r0.mkdirs()     // Catch:{ Throwable -> 0x0064 }
        L_0x004d:
            boolean r0 = r8.exists()     // Catch:{ Throwable -> 0x0064 }
            if (r0 != 0) goto L_0x0056
            r8.createNewFile()     // Catch:{ Throwable -> 0x0064 }
        L_0x0056:
            boolean r0 = r8.exists()
            if (r0 == 0) goto L_0x0062
            boolean r0 = r8.canRead()
            if (r0 != 0) goto L_0x006f
        L_0x0062:
            r0 = r1
            goto L_0x001f
        L_0x0064:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)
            if (r2 != 0) goto L_0x0056
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0056
        L_0x006f:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x011c, all -> 0x00f4 }
            r4.<init>(r7)     // Catch:{ Throwable -> 0x011c, all -> 0x00f4 }
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x011f, all -> 0x0114 }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x011f, all -> 0x0114 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x011f, all -> 0x0114 }
            r6.<init>(r8)     // Catch:{ Throwable -> 0x011f, all -> 0x0114 }
            r0.<init>(r6)     // Catch:{ Throwable -> 0x011f, all -> 0x0114 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x011f, all -> 0x0114 }
            r0 = 8
            r2.setMethod(r0)     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            java.util.zip.ZipEntry r0 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            java.lang.String r3 = r7.getName()     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            r2.putNextEntry(r0)     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            if (r9 <= r5) goto L_0x00c3
        L_0x0096:
            byte[] r0 = new byte[r9]     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
        L_0x0098:
            int r3 = r4.read(r0)     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            if (r3 <= 0) goto L_0x00c5
            r5 = 0
            r2.write(r0, r5, r3)     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            goto L_0x0098
        L_0x00a3:
            r0 = move-exception
            r3 = r4
        L_0x00a5:
            boolean r4 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0119 }
            if (r4 != 0) goto L_0x00ae
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0119 }
        L_0x00ae:
            if (r3 == 0) goto L_0x00b3
            r3.close()     // Catch:{ IOException -> 0x00ea }
        L_0x00b3:
            if (r2 == 0) goto L_0x00b8
            r2.close()     // Catch:{ IOException -> 0x00ef }
        L_0x00b8:
            java.lang.String r0 = "rqdp{  ZF end}"
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.an.c(r0, r2)
            r0 = r1
            goto L_0x001f
        L_0x00c3:
            r9 = r5
            goto L_0x0096
        L_0x00c5:
            r2.flush()     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            r2.closeEntry()     // Catch:{ Throwable -> 0x00a3, all -> 0x0117 }
            r0 = 1
            if (r4 == 0) goto L_0x00d1
            r4.close()     // Catch:{ IOException -> 0x00e0 }
        L_0x00d1:
            if (r2 == 0) goto L_0x00d6
            r2.close()     // Catch:{ IOException -> 0x00e5 }
        L_0x00d6:
            java.lang.String r2 = "rqdp{  ZF end}"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.an.c(r2, r1)
            goto L_0x001f
        L_0x00e0:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x00d1
        L_0x00e5:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x00d6
        L_0x00ea:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00b3
        L_0x00ef:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00b8
        L_0x00f4:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x00f7:
            if (r4 == 0) goto L_0x00fc
            r4.close()     // Catch:{ IOException -> 0x010a }
        L_0x00fc:
            if (r2 == 0) goto L_0x0101
            r2.close()     // Catch:{ IOException -> 0x010f }
        L_0x0101:
            java.lang.String r2 = "rqdp{  ZF end}"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.an.c(r2, r1)
            throw r0
        L_0x010a:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x00fc
        L_0x010f:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0101
        L_0x0114:
            r0 = move-exception
            r2 = r3
            goto L_0x00f7
        L_0x0117:
            r0 = move-exception
            goto L_0x00f7
        L_0x0119:
            r0 = move-exception
            r4 = r3
            goto L_0x00f7
        L_0x011c:
            r0 = move-exception
            r2 = r3
            goto L_0x00a5
        L_0x011f:
            r0 = move-exception
            r2 = r3
            r3 = r4
            goto L_0x00a5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(java.io.File, java.io.File, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0085 A[Catch:{ all -> 0x00ed }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008a A[SYNTHETIC, Splitter:B:22:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x008f A[SYNTHETIC, Splitter:B:25:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00d5 A[SYNTHETIC, Splitter:B:54:0x00d5] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00da A[SYNTHETIC, Splitter:B:57:0x00da] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<java.lang.String> a(android.content.Context r6, java.lang.String r7) {
        /*
            r1 = 1
            r3 = 0
            r2 = 0
            boolean r0 = com.tencent.bugly.crashreport.common.info.AppInfo.f(r6)
            if (r0 == 0) goto L_0x001a
            java.util.ArrayList r0 = new java.util.ArrayList
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r2 = "unknown(low memory)"
            r1[r3] = r2
            java.util.List r1 = java.util.Arrays.asList(r1)
            r0.<init>(r1)
        L_0x0019:
            return r0
        L_0x001a:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.String r0 = "/system/bin/sh"
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            boolean r3 = r3.exists()     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            if (r3 == 0) goto L_0x0038
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            boolean r3 = r3.canExecute()     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            if (r3 != 0) goto L_0x003b
        L_0x0038:
            java.lang.String r0 = "sh"
        L_0x003b:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r5 = 0
            r4[r5] = r0     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r0 = 1
            java.lang.String r5 = "-c"
            r4[r0] = r5     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.util.List r0 = java.util.Arrays.asList(r4)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r3.add(r7)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.lang.Runtime r4 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r0 = 3
            java.lang.String[] r0 = new java.lang.String[r0]     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.lang.Object[] r0 = r3.toArray(r0)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.lang.Process r0 = r4.exec(r0)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            java.io.InputStream r5 = r0.getInputStream()     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x00f1, all -> 0x00d1 }
        L_0x0072:
            java.lang.String r3 = r4.readLine()     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
            if (r3 == 0) goto L_0x0094
            r1.add(r3)     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
            goto L_0x0072
        L_0x007c:
            r0 = move-exception
            r1 = r2
            r3 = r4
        L_0x007f:
            boolean r4 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x00ed }
            if (r4 != 0) goto L_0x0088
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00ed }
        L_0x0088:
            if (r3 == 0) goto L_0x008d
            r3.close()     // Catch:{ IOException -> 0x00c7 }
        L_0x008d:
            if (r1 == 0) goto L_0x0092
            r1.close()     // Catch:{ IOException -> 0x00cc }
        L_0x0092:
            r0 = r2
            goto L_0x0019
        L_0x0094:
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
            java.io.InputStream r0 = r0.getErrorStream()     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x007c, all -> 0x00e8 }
        L_0x00a2:
            java.lang.String r0 = r3.readLine()     // Catch:{ Throwable -> 0x00ac, all -> 0x00ea }
            if (r0 == 0) goto L_0x00b0
            r1.add(r0)     // Catch:{ Throwable -> 0x00ac, all -> 0x00ea }
            goto L_0x00a2
        L_0x00ac:
            r0 = move-exception
            r1 = r3
            r3 = r4
            goto L_0x007f
        L_0x00b0:
            if (r4 == 0) goto L_0x00b5
            r4.close()     // Catch:{ IOException -> 0x00bd }
        L_0x00b5:
            if (r3 == 0) goto L_0x00ba
            r3.close()     // Catch:{ IOException -> 0x00c2 }
        L_0x00ba:
            r0 = r1
            goto L_0x0019
        L_0x00bd:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00b5
        L_0x00c2:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00ba
        L_0x00c7:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x008d
        L_0x00cc:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0092
        L_0x00d1:
            r0 = move-exception
            r4 = r2
        L_0x00d3:
            if (r4 == 0) goto L_0x00d8
            r4.close()     // Catch:{ IOException -> 0x00de }
        L_0x00d8:
            if (r2 == 0) goto L_0x00dd
            r2.close()     // Catch:{ IOException -> 0x00e3 }
        L_0x00dd:
            throw r0
        L_0x00de:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00d8
        L_0x00e3:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00dd
        L_0x00e8:
            r0 = move-exception
            goto L_0x00d3
        L_0x00ea:
            r0 = move-exception
            r2 = r3
            goto L_0x00d3
        L_0x00ed:
            r0 = move-exception
            r2 = r1
            r4 = r3
            goto L_0x00d3
        L_0x00f1:
            r0 = move-exception
            r1 = r2
            r3 = r2
            goto L_0x007f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(android.content.Context, java.lang.String):java.util.ArrayList");
    }

    public static String b(Context context, String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        if (a == null) {
            a = new HashMap();
            ArrayList<String> a2 = a(context, "getprop");
            if (a2 != null && a2.size() > 0) {
                an.b(ap.class, "Successfully get 'getprop' list.", new Object[0]);
                Pattern compile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
                for (String matcher : a2) {
                    Matcher matcher2 = compile.matcher(matcher);
                    if (matcher2.find()) {
                        a.put(matcher2.group(1), matcher2.group(2));
                    }
                }
                an.b(ap.class, "System properties number: %d.", Integer.valueOf(a.size()));
            }
        }
        if (a.containsKey(str)) {
            return (String) a.get(str);
        }
        return "fail";
    }

    public static void b(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static void a(Context context, String str, String str2, int i) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        an.c("rqdp{  sv sd start} %s", str);
        if (str2 != null && str2.trim().length() > 0) {
            File file = new File(str);
            try {
                if (!file.exists()) {
                    if (file.getParentFile() != null) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fileOutputStream = null;
                if (file.length() >= ((long) i)) {
                    fileOutputStream2 = new FileOutputStream(file, false);
                } else {
                    fileOutputStream2 = new FileOutputStream(file, true);
                }
                fileOutputStream2.write(str2.getBytes("UTF-8"));
                fileOutputStream2.flush();
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
            an.c("rqdp{  sv sd end}", new Object[0]);
        }
    }

    public static boolean a(String str) {
        if (str == null || str.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    public static void b(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.isFile() && file.exists() && file.canWrite()) {
                file.delete();
            }
        }
    }

    public static byte[] c(long j) {
        try {
            return ("" + j).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static long c(byte[] bArr) {
        long j = -1;
        if (bArr == null) {
            return j;
        }
        try {
            return Long.parseLong(new String(bArr, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
            return j;
        }
    }

    public static Context a(Context context) {
        if (context == null) {
            return context;
        }
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }

    public static String b(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ThrowableExtension.printStackTrace(th, printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void a(Class<?> cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj2, obj);
        } catch (Exception e) {
        }
    }

    public static Object a(String str, String str2, Object obj, Class<?>[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, objArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void a(Parcel parcel, Map<String, PlugInBean> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putInt("pluginNum", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            bundle.putString("pluginKey" + i, (String) arrayList.get(i));
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bundle.putString("pluginVal" + i2 + "plugInId", ((PlugInBean) arrayList2.get(i2)).a);
            bundle.putString("pluginVal" + i2 + "plugInUUID", ((PlugInBean) arrayList2.get(i2)).c);
            bundle.putString("pluginVal" + i2 + "plugInVersion", ((PlugInBean) arrayList2.get(i2)).b);
        }
        parcel.writeBundle(bundle);
    }

    public static Map<String, PlugInBean> a(Parcel parcel) {
        HashMap hashMap;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int intValue = ((Integer) readBundle.get("pluginNum")).intValue();
        for (int i = 0; i < intValue; i++) {
            arrayList.add(readBundle.getString("pluginKey" + i));
        }
        for (int i2 = 0; i2 < intValue; i2++) {
            arrayList2.add(new PlugInBean(readBundle.getString("pluginVal" + i2 + "plugInId"), readBundle.getString("pluginVal" + i2 + "plugInVersion"), readBundle.getString("pluginVal" + i2 + "plugInUUID")));
        }
        if (arrayList == null || arrayList2 == null || arrayList.size() != arrayList2.size()) {
            an.e("map plugin parcel error!", new Object[0]);
            hashMap = null;
        } else {
            HashMap hashMap2 = new HashMap(arrayList.size());
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                hashMap2.put(arrayList.get(i3), PlugInBean.class.cast(arrayList2.get(i3)));
            }
            hashMap = hashMap2;
        }
        return hashMap;
    }

    public static void b(Parcel parcel, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", arrayList);
        bundle.putStringArrayList("values", arrayList2);
        parcel.writeBundle(bundle);
    }

    public static Map<String, String> b(Parcel parcel) {
        HashMap hashMap;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        ArrayList stringArrayList = readBundle.getStringArrayList("keys");
        ArrayList stringArrayList2 = readBundle.getStringArrayList("values");
        if (stringArrayList == null || stringArrayList2 == null || stringArrayList.size() != stringArrayList2.size()) {
            an.e("map parcel error!", new Object[0]);
            hashMap = null;
        } else {
            HashMap hashMap2 = new HashMap(stringArrayList.size());
            for (int i = 0; i < stringArrayList.size(); i++) {
                hashMap2.put(stringArrayList.get(i), stringArrayList2.get(i));
            }
            hashMap = hashMap2;
        }
        return hashMap;
    }

    public static byte[] a(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public static Parcel d(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        return obtain;
    }

    public static <T> T a(byte[] bArr, Creator<T> creator) {
        T t;
        Parcel d = d(bArr);
        try {
            t = creator.createFromParcel(d);
            if (d != null) {
                d.recycle();
            }
        } catch (Throwable th) {
            if (d != null) {
                d.recycle();
            }
            throw th;
        }
        return t;
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x011a A[SYNTHETIC, Splitter:B:55:0x011a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r7, int r8, java.lang.String r9) {
        /*
            r6 = 4
            r5 = 3
            r4 = 2
            r3 = 1
            r2 = 0
            java.lang.String r0 = "android.permission.READ_LOGS"
            boolean r0 = com.tencent.bugly.crashreport.common.info.AppInfo.a(r7, r0)
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = "no read_log permission!"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.an.d(r0, r1)
            r0 = 0
        L_0x0017:
            return r0
        L_0x0018:
            if (r9 != 0) goto L_0x00bf
            java.lang.String[] r0 = new java.lang.String[r6]
            java.lang.String r1 = "logcat"
            r0[r2] = r1
            java.lang.String r1 = "-d"
            r0[r3] = r1
            java.lang.String r1 = "-v"
            r0[r4] = r1
            java.lang.String r1 = "threadtime"
            r0[r5] = r1
        L_0x0030:
            r1 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0142 }
            java.lang.Process r2 = r2.exec(r0)     // Catch:{ Throwable -> 0x0142 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            java.io.InputStream r4 = r2.getInputStream()     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
        L_0x004c:
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            if (r1 == 0) goto L_0x00e0
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            java.lang.String r4 = "\n"
            r1.append(r4)     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            if (r8 <= 0) goto L_0x004c
            int r1 = r3.length()     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            if (r1 <= r8) goto L_0x004c
            r1 = 0
            int r4 = r3.length()     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            int r4 = r4 - r8
            r3.delete(r1, r4)     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            goto L_0x004c
        L_0x006e:
            r0 = move-exception
            r1 = r2
        L_0x0070:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0117 }
            if (r2 != 0) goto L_0x0079
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0117 }
        L_0x0079:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0117 }
            r2.<init>()     // Catch:{ all -> 0x0117 }
            java.lang.String r4 = "\n[error:"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0117 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0117 }
            java.lang.String r2 = "]"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0117 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0117 }
            if (r1 == 0) goto L_0x0017
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ IOException -> 0x010d }
            r2.close()     // Catch:{ IOException -> 0x010d }
        L_0x00a9:
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ IOException -> 0x0112 }
            r2.close()     // Catch:{ IOException -> 0x0112 }
        L_0x00b0:
            java.io.InputStream r1 = r1.getErrorStream()     // Catch:{ IOException -> 0x00b9 }
            r1.close()     // Catch:{ IOException -> 0x00b9 }
            goto L_0x0017
        L_0x00b9:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0017
        L_0x00bf:
            r0 = 6
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r1 = "logcat"
            r0[r2] = r1
            java.lang.String r1 = "-d"
            r0[r3] = r1
            java.lang.String r1 = "-v"
            r0[r4] = r1
            java.lang.String r1 = "threadtime"
            r0[r5] = r1
            java.lang.String r1 = "-s"
            r0[r6] = r1
            r1 = 5
            r0[r1] = r9
            goto L_0x0030
        L_0x00e0:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x006e, all -> 0x013f }
            if (r2 == 0) goto L_0x0017
            java.io.OutputStream r1 = r2.getOutputStream()     // Catch:{ IOException -> 0x0103 }
            r1.close()     // Catch:{ IOException -> 0x0103 }
        L_0x00ed:
            java.io.InputStream r1 = r2.getInputStream()     // Catch:{ IOException -> 0x0108 }
            r1.close()     // Catch:{ IOException -> 0x0108 }
        L_0x00f4:
            java.io.InputStream r1 = r2.getErrorStream()     // Catch:{ IOException -> 0x00fd }
            r1.close()     // Catch:{ IOException -> 0x00fd }
            goto L_0x0017
        L_0x00fd:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0017
        L_0x0103:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00ed
        L_0x0108:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00f4
        L_0x010d:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x00a9
        L_0x0112:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x00b0
        L_0x0117:
            r0 = move-exception
        L_0x0118:
            if (r1 == 0) goto L_0x012f
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ IOException -> 0x0130 }
            r2.close()     // Catch:{ IOException -> 0x0130 }
        L_0x0121:
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ IOException -> 0x0135 }
            r2.close()     // Catch:{ IOException -> 0x0135 }
        L_0x0128:
            java.io.InputStream r1 = r1.getErrorStream()     // Catch:{ IOException -> 0x013a }
            r1.close()     // Catch:{ IOException -> 0x013a }
        L_0x012f:
            throw r0
        L_0x0130:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0121
        L_0x0135:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0128
        L_0x013a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x012f
        L_0x013f:
            r0 = move-exception
            r1 = r2
            goto L_0x0118
        L_0x0142:
            r0 = move-exception
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(android.content.Context, int, java.lang.String):java.lang.String");
    }

    public static Map<String, String> a(int i, boolean z) {
        HashMap hashMap = new HashMap(12);
        Map allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces == null) {
            return null;
        }
        Thread thread = Looper.getMainLooper().getThread();
        if (!allStackTraces.containsKey(thread)) {
            allStackTraces.put(thread, thread.getStackTrace());
        }
        long id = Thread.currentThread().getId();
        StringBuilder sb = new StringBuilder();
        for (Entry entry : allStackTraces.entrySet()) {
            if (!z || id != ((Thread) entry.getKey()).getId()) {
                sb.setLength(0);
                if (!(entry.getValue() == null || ((StackTraceElement[]) entry.getValue()).length == 0)) {
                    StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) entry.getValue();
                    int length = stackTraceElementArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        StackTraceElement stackTraceElement = stackTraceElementArr[i2];
                        if (i > 0 && sb.length() >= i) {
                            sb.append("\n[Stack over limit size :" + i + " , has been cut!]");
                            break;
                        }
                        sb.append(stackTraceElement.toString()).append("\n");
                        i2++;
                    }
                    hashMap.put(((Thread) entry.getKey()).getName() + "(" + ((Thread) entry.getKey()).getId() + ")", sb.toString());
                }
            }
        }
        return hashMap;
    }

    public static boolean b(Context context) {
        try {
            if (VERSION.SDK_INT >= 14) {
                return a.b().a();
            }
            String packageName = context.getPackageName();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
            if (activityManager != null) {
                List runningTasks = activityManager.getRunningTasks(1);
                if (runningTasks == null || runningTasks.isEmpty() || !((RunningTaskInfo) runningTasks.get(0)).topActivity.getPackageName().equals(packageName)) {
                    return false;
                }
                return true;
            }
            return true;
        } catch (SecurityException e) {
            an.e("无法获取GET_TASK权限，将在通知栏提醒升级，如需弹窗提醒，请在AndroidManifest.xml中添加GET_TASKS权限：\n<uses-permission android:name=\"android.permission.GET_TASKS\" />\n", new Object[0]);
        } catch (Exception e2) {
            if (!an.b(e2)) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0034 A[SYNTHETIC, Splitter:B:17:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0053 A[Catch:{ Exception -> 0x0057 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] a(int r7) {
        /*
            r1 = 0
            java.lang.Class<com.tencent.bugly.proguard.ap> r3 = com.tencent.bugly.proguard.ap.class
            monitor-enter(r3)
            int r0 = r7 / 8
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            java.lang.String r6 = "/dev/urandom"
            r5.<init>(r6)     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0024, all -> 0x004f }
            r2.readFully(r0)     // Catch:{ Exception -> 0x0068 }
            if (r2 == 0) goto L_0x0022
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0022:
            monitor-exit(r3)
            return r0
        L_0x0024:
            r0 = move-exception
            r2 = r1
        L_0x0026:
            java.lang.String r4 = "Failed to read from /dev/urandom : %s"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0066 }
            r6 = 0
            r5[r6] = r0     // Catch:{ all -> 0x0066 }
            com.tencent.bugly.proguard.an.e(r4, r5)     // Catch:{ all -> 0x0066 }
            if (r2 == 0) goto L_0x0037
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0037:
            java.lang.String r0 = "AES"
            javax.crypto.KeyGenerator r0 = javax.crypto.KeyGenerator.getInstance(r0)     // Catch:{ Exception -> 0x0057 }
            java.security.SecureRandom r2 = new java.security.SecureRandom     // Catch:{ Exception -> 0x0057 }
            r2.<init>()     // Catch:{ Exception -> 0x0057 }
            r0.init(r7, r2)     // Catch:{ Exception -> 0x0057 }
            javax.crypto.SecretKey r0 = r0.generateKey()     // Catch:{ Exception -> 0x0057 }
            byte[] r0 = r0.getEncoded()     // Catch:{ Exception -> 0x0057 }
            goto L_0x0022
        L_0x004f:
            r0 = move-exception
            r2 = r1
        L_0x0051:
            if (r2 == 0) goto L_0x0056
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0056:
            throw r0     // Catch:{ Exception -> 0x0057 }
        L_0x0057:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.b(r0)     // Catch:{ all -> 0x0063 }
            if (r2 != 0) goto L_0x0061
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0063 }
        L_0x0061:
            r0 = r1
            goto L_0x0022
        L_0x0063:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0066:
            r0 = move-exception
            goto L_0x0051
        L_0x0068:
            r0 = move-exception
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(int):byte[]");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(int r4, byte[] r5, byte[] r6) {
        /*
            javax.crypto.spec.SecretKeySpec r0 = new javax.crypto.spec.SecretKeySpec     // Catch:{ Exception -> 0x003a }
            java.lang.String r1 = "AES"
            r0.<init>(r6, r1)     // Catch:{ Exception -> 0x003a }
            java.lang.String r1 = "AES/GCM/NoPadding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ Exception -> 0x003a }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x003a }
            r3 = 21
            if (r2 < r3) goto L_0x0019
            boolean r2 = b     // Catch:{ Exception -> 0x003a }
            if (r2 == 0) goto L_0x0026
        L_0x0019:
            javax.crypto.spec.IvParameterSpec r2 = new javax.crypto.spec.IvParameterSpec     // Catch:{ Exception -> 0x003a }
            r2.<init>(r6)     // Catch:{ Exception -> 0x003a }
            r1.init(r4, r0, r2)     // Catch:{ Exception -> 0x003a }
        L_0x0021:
            byte[] r0 = r1.doFinal(r5)     // Catch:{ Exception -> 0x003a }
        L_0x0025:
            return r0
        L_0x0026:
            int r2 = r1.getBlockSize()     // Catch:{ Exception -> 0x003a }
            javax.crypto.spec.GCMParameterSpec r3 = new javax.crypto.spec.GCMParameterSpec     // Catch:{ Exception -> 0x003a }
            int r2 = r2 * 8
            r3.<init>(r2, r6)     // Catch:{ Exception -> 0x003a }
            r1.init(r4, r0, r3)     // Catch:{ InvalidAlgorithmParameterException -> 0x0035 }
            goto L_0x0021
        L_0x0035:
            r0 = move-exception
            r1 = 1
            b = r1     // Catch:{ Exception -> 0x003a }
            throw r0     // Catch:{ Exception -> 0x003a }
        L_0x003a:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.b(r0)
            if (r1 != 0) goto L_0x0044
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
        L_0x0044:
            r0 = 0
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(int, byte[], byte[]):byte[]");
    }

    public static byte[] b(int i, byte[] bArr, byte[] bArr2) {
        Key generatePrivate;
        try {
            KeyFactory instance = KeyFactory.getInstance("RSA");
            if (i == 1) {
                generatePrivate = instance.generatePublic(new X509EncodedKeySpec(bArr2));
            } else {
                generatePrivate = instance.generatePrivate(new PKCS8EncodedKeySpec(bArr2));
            }
            Cipher instance2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance2.init(i, generatePrivate);
            return instance2.doFinal(bArr);
        } catch (Exception e) {
            if (!an.b(e)) {
                ThrowableExtension.printStackTrace(e);
            }
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        an.c("[Util] Try to lock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < j) {
                    return false;
                }
                an.c("[Util] Lock file (%s) is expired, unlock it.", str);
                c(context, str);
            }
            if (file.createNewFile()) {
                an.c("[Util] Successfully locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                return true;
            }
            an.c("[Util] Failed to locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return false;
        } catch (Throwable th) {
            an.a(th);
            return false;
        }
    }

    public static boolean c(Context context, String str) {
        an.c("[Util] Try to unlock file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (!file.exists()) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            an.c("[Util] Successfully unlocked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return true;
        } catch (Throwable th) {
            an.a(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0075 A[SYNTHETIC, Splitter:B:38:0x0075] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r6, int r7, boolean r8) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x000f
            boolean r1 = r6.exists()
            if (r1 == 0) goto L_0x000f
            boolean r1 = r6.canRead()
            if (r1 != 0) goto L_0x0010
        L_0x000f:
            return r0
        L_0x0010:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            r1.<init>()     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            r4.<init>(r6)     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            java.lang.String r5 = "utf-8"
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0080, all -> 0x0070 }
        L_0x0027:
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x0061 }
            if (r3 == 0) goto L_0x0048
            java.lang.StringBuilder r3 = r1.append(r3)     // Catch:{ Throwable -> 0x0061 }
            java.lang.String r4 = "\n"
            r3.append(r4)     // Catch:{ Throwable -> 0x0061 }
            if (r7 <= 0) goto L_0x0027
            int r3 = r1.length()     // Catch:{ Throwable -> 0x0061 }
            if (r3 <= r7) goto L_0x0027
            if (r8 == 0) goto L_0x0057
            int r3 = r1.length()     // Catch:{ Throwable -> 0x0061 }
            r1.delete(r7, r3)     // Catch:{ Throwable -> 0x0061 }
        L_0x0048:
            java.lang.String r0 = r1.toString()     // Catch:{ Throwable -> 0x0061 }
            if (r2 == 0) goto L_0x000f
            r2.close()     // Catch:{ Exception -> 0x0052 }
            goto L_0x000f
        L_0x0052:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x000f
        L_0x0057:
            r3 = 0
            int r4 = r1.length()     // Catch:{ Throwable -> 0x0061 }
            int r4 = r4 - r7
            r1.delete(r3, r4)     // Catch:{ Throwable -> 0x0061 }
            goto L_0x0027
        L_0x0061:
            r1 = move-exception
        L_0x0062:
            com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x007e }
            if (r2 == 0) goto L_0x000f
            r2.close()     // Catch:{ Exception -> 0x006b }
            goto L_0x000f
        L_0x006b:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x000f
        L_0x0070:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0073:
            if (r2 == 0) goto L_0x0078
            r2.close()     // Catch:{ Exception -> 0x0079 }
        L_0x0078:
            throw r0
        L_0x0079:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x0078
        L_0x007e:
            r0 = move-exception
            goto L_0x0073
        L_0x0080:
            r1 = move-exception
            r2 = r0
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ap.a(java.io.File, int, boolean):java.lang.String");
    }

    public static BufferedReader a(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (Throwable th) {
            an.a(th);
            return null;
        }
    }

    public static BufferedReader a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str, str2);
            if (!file.exists() || !file.canRead()) {
                return null;
            }
            return a(file);
        } catch (NullPointerException e) {
            an.a(e);
            return null;
        }
    }

    public static Thread a(Runnable runnable, String str) {
        try {
            Thread thread = new Thread(runnable);
            thread.setName(str);
            thread.start();
            return thread;
        } catch (Throwable th) {
            an.e("[Util] Failed to start a thread to execute task with message: %s", th.getMessage());
            return null;
        }
    }

    public static boolean a(Runnable runnable) {
        if (runnable != null) {
            am a2 = am.a();
            if (a2 != null) {
                return a2.a(runnable);
            }
            String[] split = runnable.getClass().getName().split("\\.");
            if (a(runnable, split[split.length - 1]) != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean c(String str) {
        if (a(str)) {
            return false;
        }
        if (str.length() > 255) {
            an.a("URL(%s)'s length is larger than 255.", str);
            return false;
        } else if (!str.toLowerCase().startsWith("http")) {
            an.a("URL(%s) is not start with \"http\".", str);
            return false;
        } else if (str.toLowerCase().contains("qq.com")) {
            return true;
        } else {
            an.a("URL(%s) does not contain \"qq.com\".", str);
            return false;
        }
    }

    public static SharedPreferences a(String str, Context context) {
        if (context != null) {
            return context.getSharedPreferences(str, 0);
        }
        return null;
    }

    public static void b(String str, String str2) {
        if (a.b() != null && a.b().M != null) {
            a.b().M.edit().putString(str, str2).apply();
        }
    }

    public static String c(String str, String str2) {
        if (a.b() == null || a.b().M == null) {
            return "";
        }
        return a.b().M.getString(str, str2);
    }

    public static String e(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            if (i != 0) {
                stringBuffer.append(':');
            }
            String hexString = Integer.toHexString(bArr[i] & UnsignedBytes.MAX_VALUE);
            if (hexString.length() == 1) {
                hexString = 0 + hexString;
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }
}
