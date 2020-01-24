package com.loc;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.loc.aw.a;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/* compiled from: Utils */
public final class bc {
    static PublicKey a() {
        ByteArrayInputStream byteArrayInputStream;
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            byteArrayInputStream = new ByteArrayInputStream(q.b("MIIDRzCCAi+gAwIBAgIEeuDbsDANBgkqhkiG9w0BAQsFADBTMQswCQYDVQQGEwJjbjELMAkGA1UECBMCYmoxCzAJBgNVBAcTAmJqMQ0wCwYDVQQKEwRvcGVuMQ4wDAYDVQQLEwVnYW9kZTELMAkGA1UEAxMCUWkwIBcNMTYwODAxMDE0ODMwWhgPMjA3MTA1MDUwMTQ4MzBaMFMxCzAJBgNVBAYTAmNuMQswCQYDVQQIEwJiajELMAkGA1UEBxMCYmoxDTALBgNVBAoTBG9wZW4xDjAMBgNVBAsTBWdhb2RlMQswCQYDVQQDEwJRaTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKpL13mZm4q6AFP5csQE7130Lwq8m+HICy3rBARd9vbw5Cb1wFF96KdhC5P/aASlrPb+6MSyP1nE97p3ygKJWsgxExyvVuOvh1KUqOFuK15oY7JKTk6L4eLCbkBJZV2DLffpW0HGiRpmFG8LJR0sjNOoubSd5R/6XoBwyRglsyVHprjrK2qDRvT3Edgtfvxp4HnUzMsDD3CJRtgsaDw6ECyF7fhYKEz9I6OEEVsPlpbgzRmhSeFDL77/k1mhPve1ZyKGlPcxvSSdLSAlV0hzr5NKlujHll7BbouwDnr6l/0O44AzZ0V/ieft1iBkSLirnlm56uI/8jdh8ANrD1fW4ZUCAwEAAaMhMB8wHQYDVR0OBBYEFBzudtI5UKRvHGDV+VQRzItIj3PqMA0GCSqGSIb3DQEBCwUAA4IBAQBS2EGndgvIBnf7Ce4IhDbm7F5h4L+6TYGmT9acnQbEFY8oUoFblMDgg+cETT44jU/elwbJJVmKhj/WRQl+AdSALBAgDvxq1AcjlGg+c8H3pa2BWlrxNJP9MFLIEI5bA8m5og/Epjut50uemZ9ggoNmJeW0N/a6D8euhYJKOYngUQqDu6cwLj1Ec0ptwrNRbvRXXgzjfJMPE/ii4K/b8JZ+QN2d/bl7QEvKWBSzVueZifV659qAbMh6C9TCVstWWfV53Z3Vyt+duDNU5ed7aWao42Ppw4VHslrJW0V6BXDUhhzgXx28UWY78W7LmYGCtC8PfDId2+k4tPoTNPM6HHP5"));
            try {
                PublicKey publicKey = ((X509Certificate) instance.generateCertificate(byteArrayInputStream)).getPublicKey();
                try {
                    a((Closeable) byteArrayInputStream);
                    return publicKey;
                } catch (Throwable th) {
                    ThrowableExtension.printStackTrace(th);
                    return publicKey;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayInputStream = null;
            a((Closeable) byteArrayInputStream);
            throw th;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    static void a(List<az> list) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size() - 1) {
                int i3 = i2 + 1;
                while (true) {
                    int i4 = i3;
                    if (i4 >= list.size()) {
                        break;
                    }
                    az azVar = (az) list.get(i2);
                    az azVar2 = (az) list.get(i4);
                    if (b(azVar2.e(), azVar.e()) > 0) {
                        list.set(i2, azVar2);
                        list.set(i4, azVar);
                    }
                    i3 = i4 + 1;
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static boolean a(final Context context, ao aoVar, az azVar, av avVar, final v vVar) {
        String str = avVar.c;
        String str2 = avVar.d;
        final String str3 = avVar.e;
        if ("errorstatus".equals(azVar.f())) {
            try {
                if (!new File(aw.b(context, vVar.a(), vVar.b())).exists() && !TextUtils.isEmpty(aw.a(context, aoVar, vVar))) {
                    bb.b().a().submit(new Runnable() {
                        public final void run() {
                            try {
                                aw.a(context, vVar);
                            } catch (Throwable th) {
                            }
                        }
                    });
                }
            } catch (Throwable th) {
            }
            return true;
        }
        final String a = aw.a(context, avVar.b);
        if (!new File(a).exists()) {
            return false;
        }
        List b = aoVar.b(az.a(aw.a(context, str, str2), str, str2, str3), az.class);
        if (b != null && b.size() > 0) {
            return true;
        }
        aw.a(context, str, vVar.b());
        try {
            final Context context2 = context;
            final ao aoVar2 = aoVar;
            final v vVar2 = vVar;
            bb.b().a().submit(new Runnable() {
                public final void run() {
                    try {
                        aw.a(context2, aoVar2, vVar2, a, str3);
                        aw.a(context2, vVar2);
                    } catch (Throwable th) {
                        ag.a(th, "dDownLoad", "processDownloadedFile()");
                    }
                }
            });
        } catch (Throwable th2) {
        }
        return true;
    }

    static boolean a(Context context, av avVar, v vVar) {
        ao aoVar = new ao(context, ay.b());
        if (a(aoVar, avVar)) {
            return true;
        }
        az a = a.a(aoVar, avVar.b);
        if (a != null) {
            return a(context, aoVar, a, avVar, vVar);
        }
        return false;
    }

    static boolean a(Context context, boolean z) {
        if (!z) {
            if (!(p.r(context) == 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(ao aoVar, av avVar) {
        try {
            List a = a.a(aoVar, avVar.c, "used");
            if (a != null && a.size() > 0 && b(((az) a.get(0)).e(), avVar.e) > 0) {
                return true;
            }
        } catch (Throwable th) {
            ag.a(th, "dDownLoad", "isUsed");
        }
        return false;
    }

    static boolean a(ao aoVar, String str, String str2, v vVar) {
        az a = a.a(aoVar, str);
        return a != null && vVar.b().equals(a.d()) && a(str2, a.b());
    }

    static boolean a(av avVar) {
        return VERSION.SDK_INT >= avVar.g && VERSION.SDK_INT <= avVar.f;
    }

    static boolean a(v vVar, av avVar) {
        return vVar != null && vVar.a().equals(avVar.c) && vVar.b().equals(avVar.d);
    }

    static boolean a(String str, String str2) {
        String a = s.a(str);
        return a != null && a.equalsIgnoreCase(str2);
    }

    private static int b(String str, String str2) {
        int i = 0;
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int min = Math.min(split.length, split2.length);
            for (int i2 = 0; i2 < min; i2++) {
                i = split[i2].length() - split2[i2].length();
                if (i != 0) {
                    break;
                }
                i = split[i2].compareTo(split2[i2]);
                if (i != 0) {
                    break;
                }
            }
            return i != 0 ? i : split.length - split2.length;
        } catch (Exception e) {
            ag.a((Throwable) e, "Utils", "compareVersion");
            return -1;
        }
    }
}
