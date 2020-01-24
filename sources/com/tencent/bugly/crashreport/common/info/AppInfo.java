package com.tencent.bugly.crashreport.common.info;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: BUGLY */
public class AppInfo {
    public static final String[] a = "@buglyAllChannel@".split(",");
    public static final String[] b = "@buglyAllChannelPriority@".split(",");
    private static ActivityManager c;

    public static String a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageName();
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return "fail";
        }
    }

    public static PackageInfo b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(a(context), 0);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public static boolean a(Context context, String str) {
        if (context == null || str == null || str.trim().length() <= 0) {
            return false;
        }
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr == null) {
                return false;
            }
            for (String equals : strArr) {
                if (str.equals(equals)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            if (an.a(th)) {
                return false;
            }
            ThrowableExtension.printStackTrace(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x004b A[Catch:{ all -> 0x0066 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0054 A[SYNTHETIC, Splitter:B:24:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005e A[SYNTHETIC, Splitter:B:30:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r5, int r6) {
        /*
            r0 = 0
            r2 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            r3.<init>()     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            java.lang.String r4 = "/proc/"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            java.lang.String r4 = "/cmdline"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0043, all -> 0x005a }
            r2 = 512(0x200, float:7.175E-43)
            char[] r2 = new char[r2]     // Catch:{ Throwable -> 0x0068 }
            r1.read(r2)     // Catch:{ Throwable -> 0x0068 }
        L_0x0029:
            int r3 = r2.length     // Catch:{ Throwable -> 0x0068 }
            if (r0 >= r3) goto L_0x0030
            char r3 = r2[r0]     // Catch:{ Throwable -> 0x0068 }
            if (r3 != 0) goto L_0x0040
        L_0x0030:
            java.lang.String r3 = new java.lang.String     // Catch:{ Throwable -> 0x0068 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0068 }
            r2 = 0
            java.lang.String r0 = r3.substring(r2, r0)     // Catch:{ Throwable -> 0x0068 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ Throwable -> 0x0062 }
        L_0x003f:
            return r0
        L_0x0040:
            int r0 = r0 + 1
            goto L_0x0029
        L_0x0043:
            r0 = move-exception
            r1 = r2
        L_0x0045:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0066 }
            if (r2 != 0) goto L_0x004e
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0066 }
        L_0x004e:
            java.lang.String r0 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0066 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ Throwable -> 0x0058 }
            goto L_0x003f
        L_0x0058:
            r1 = move-exception
            goto L_0x003f
        L_0x005a:
            r0 = move-exception
            r1 = r2
        L_0x005c:
            if (r1 == 0) goto L_0x0061
            r1.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x0061:
            throw r0
        L_0x0062:
            r1 = move-exception
            goto L_0x003f
        L_0x0064:
            r1 = move-exception
            goto L_0x0061
        L_0x0066:
            r0 = move-exception
            goto L_0x005c
        L_0x0068:
            r0 = move-exception
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.AppInfo.a(android.content.Context, int):java.lang.String");
    }

    public static String c(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (packageManager == null || applicationInfo == null) {
                return null;
            }
            CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
            if (applicationLabel != null) {
                return applicationLabel.toString();
            }
            return null;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    public static Map<String, String> d(Context context) {
        HashMap hashMap;
        if (context == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                hashMap = new HashMap();
                Object obj = applicationInfo.metaData.get("BUGLY_DISABLE");
                if (obj != null) {
                    hashMap.put("BUGLY_DISABLE", obj.toString());
                }
                Object obj2 = applicationInfo.metaData.get("BUGLY_APPID");
                if (obj2 != null) {
                    hashMap.put("BUGLY_APPID", obj2.toString());
                }
                Object obj3 = applicationInfo.metaData.get("BUGLY_APP_CHANNEL");
                if (obj3 != null) {
                    hashMap.put("BUGLY_APP_CHANNEL", obj3.toString());
                }
                Object obj4 = applicationInfo.metaData.get("BUGLY_APP_VERSION");
                if (obj4 != null) {
                    hashMap.put("BUGLY_APP_VERSION", obj4.toString());
                }
                Object obj5 = applicationInfo.metaData.get("BUGLY_ENABLE_DEBUG");
                if (obj5 != null) {
                    hashMap.put("BUGLY_ENABLE_DEBUG", obj5.toString());
                }
                Object obj6 = applicationInfo.metaData.get("com.tencent.rdm.uuid");
                if (obj6 != null) {
                    hashMap.put("com.tencent.rdm.uuid", obj6.toString());
                }
            } else {
                hashMap = null;
            }
            return hashMap;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    public static List<String> a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            String str = (String) map.get("BUGLY_DISABLE");
            if (str == null || str.length() == 0) {
                return null;
            }
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            return Arrays.asList(split);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length > 0) {
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                if (instance == null) {
                    return null;
                }
                X509Certificate x509Certificate = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(bArr));
                if (x509Certificate == null) {
                    return null;
                }
                sb.append("Issuer|");
                Principal issuerDN = x509Certificate.getIssuerDN();
                if (issuerDN != null) {
                    sb.append(issuerDN.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("SerialNumber|");
                BigInteger serialNumber = x509Certificate.getSerialNumber();
                if (issuerDN != null) {
                    sb.append(serialNumber.toString(16));
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("NotBefore|");
                Date notBefore = x509Certificate.getNotBefore();
                if (issuerDN != null) {
                    sb.append(notBefore.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("NotAfter|");
                Date notAfter = x509Certificate.getNotAfter();
                if (issuerDN != null) {
                    sb.append(notAfter.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("SHA1|");
                String a2 = ap.a(MessageDigest.getInstance("SHA1").digest(x509Certificate.getEncoded()));
                if (a2 == null || a2.length() <= 0) {
                    sb.append("unknown");
                } else {
                    sb.append(a2.toString());
                }
                sb.append("\n");
                sb.append("MD5|");
                String a3 = ap.a(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(x509Certificate.getEncoded()));
                if (a3 == null || a3.length() <= 0) {
                    sb.append("unknown");
                } else {
                    sb.append(a3.toString());
                }
            } catch (CertificateException e) {
                if (!an.a(e)) {
                    ThrowableExtension.printStackTrace(e);
                }
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
        if (sb.length() == 0) {
            return "unknown";
        }
        return sb.toString();
    }

    public static String e(Context context) {
        String a2 = a(context);
        if (a2 == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a2, 64);
            if (packageInfo == null) {
                return null;
            }
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return null;
            }
            return a(packageInfo.signatures[0].toByteArray());
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean f(Context context) {
        if (context == null) {
            return false;
        }
        if (c == null) {
            c = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
        }
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            c.getMemoryInfo(memoryInfo);
            if (!memoryInfo.lowMemory) {
                return false;
            }
            an.c("Memory is low.", new Object[0]);
            return true;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return false;
        }
    }
}
