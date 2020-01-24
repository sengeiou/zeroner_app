package com.loc;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.loc.bh.a;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: LogEngine */
public final class bt {
    private static void a(bh bhVar, List<String> list) {
        if (bhVar != null) {
            try {
                for (String c : list) {
                    bhVar.c(c);
                }
                bhVar.close();
            } catch (Throwable th) {
                aj.b(th, "ofm", "dlo");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a7 A[SYNTHETIC, Splitter:B:48:0x00a7] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(com.loc.bs r8) {
        /*
            r2 = 0
            r7 = 1
            com.loc.cm r0 = r8.f     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            boolean r0 = r0.c()     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            if (r0 == 0) goto L_0x0075
            com.loc.cm r0 = r8.f     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            r1 = 1
            r0.a(r1)     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            java.lang.String r1 = r8.a     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            long r4 = r8.b     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            com.loc.bh r1 = com.loc.bh.a(r0, r4)     // Catch:{ Throwable -> 0x00b4, all -> 0x00a3 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0091 }
            r0.<init>()     // Catch:{ Throwable -> 0x0091 }
            byte[] r3 = a(r1, r8, r0)     // Catch:{ Throwable -> 0x0091 }
            if (r3 == 0) goto L_0x002b
            int r4 = r3.length     // Catch:{ Throwable -> 0x0091 }
            if (r4 != 0) goto L_0x0031
        L_0x002b:
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch:{ Throwable -> 0x00b0 }
        L_0x0030:
            return
        L_0x0031:
            com.loc.ai r4 = new com.loc.ai     // Catch:{ Throwable -> 0x0091 }
            java.lang.String r5 = r8.c     // Catch:{ Throwable -> 0x0091 }
            r4.<init>(r3, r5)     // Catch:{ Throwable -> 0x0091 }
            com.loc.bm.a()     // Catch:{ Throwable -> 0x0091 }
            byte[] r4 = com.loc.bm.b(r4)     // Catch:{ Throwable -> 0x0091 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0091 }
            java.lang.String r6 = new java.lang.String     // Catch:{ Throwable -> 0x0091 }
            r6.<init>(r4)     // Catch:{ Throwable -> 0x0091 }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0091 }
            java.lang.String r4 = "code"
            boolean r4 = r5.has(r4)     // Catch:{ Throwable -> 0x0091 }
            if (r4 == 0) goto L_0x00b7
            java.lang.String r4 = "code"
            int r4 = r5.getInt(r4)     // Catch:{ Throwable -> 0x0091 }
            if (r4 != r7) goto L_0x00b7
            com.loc.cm r4 = r8.f     // Catch:{ Throwable -> 0x0091 }
            if (r4 == 0) goto L_0x0067
            if (r3 == 0) goto L_0x0067
            com.loc.cm r4 = r8.f     // Catch:{ Throwable -> 0x0091 }
            int r3 = r3.length     // Catch:{ Throwable -> 0x0091 }
            r4.a(r3)     // Catch:{ Throwable -> 0x0091 }
        L_0x0067:
            com.loc.cm r3 = r8.f     // Catch:{ Throwable -> 0x0091 }
            int r3 = r3.b()     // Catch:{ Throwable -> 0x0091 }
            r4 = 2147483647(0x7fffffff, float:NaN)
            if (r3 >= r4) goto L_0x0080
            a(r1, r0)     // Catch:{ Throwable -> 0x0091 }
        L_0x0075:
            if (r2 == 0) goto L_0x0030
            r2.close()     // Catch:{ Throwable -> 0x007b }
            goto L_0x0030
        L_0x007b:
            r0 = move-exception
        L_0x007c:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0030
        L_0x0080:
            if (r1 == 0) goto L_0x0075
            r1.d()     // Catch:{ Throwable -> 0x0086 }
            goto L_0x0075
        L_0x0086:
            r0 = move-exception
            java.lang.String r3 = "ofm"
            java.lang.String r4 = "dlo"
            com.loc.aj.b(r0, r3, r4)     // Catch:{ Throwable -> 0x0091 }
            goto L_0x0075
        L_0x0091:
            r0 = move-exception
        L_0x0092:
            java.lang.String r2 = "leg"
            java.lang.String r3 = "uts"
            com.loc.aj.b(r0, r2, r3)     // Catch:{ all -> 0x00b2 }
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch:{ Throwable -> 0x00a1 }
            goto L_0x0030
        L_0x00a1:
            r0 = move-exception
            goto L_0x007c
        L_0x00a3:
            r0 = move-exception
            r1 = r2
        L_0x00a5:
            if (r1 == 0) goto L_0x00aa
            r1.close()     // Catch:{ Throwable -> 0x00ab }
        L_0x00aa:
            throw r0
        L_0x00ab:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00aa
        L_0x00b0:
            r0 = move-exception
            goto L_0x007c
        L_0x00b2:
            r0 = move-exception
            goto L_0x00a5
        L_0x00b4:
            r0 = move-exception
            r1 = r2
            goto L_0x0092
        L_0x00b7:
            r2 = r1
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bt.a(com.loc.bs):void");
    }

    public static void a(String str, byte[] bArr, bs bsVar) throws IOException, CertificateException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        bh bhVar;
        OutputStream outputStream = null;
        bh bhVar2 = null;
        OutputStream outputStream2 = null;
        try {
            if (a(bsVar.a, str)) {
                if (0 != 0) {
                    try {
                        outputStream2.close();
                    } catch (Throwable th) {
                        ThrowableExtension.printStackTrace(th);
                    }
                }
                if (0 != 0) {
                    try {
                        bhVar2.close();
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    return;
                }
            } else {
                File file = new File(bsVar.a);
                if (!file.exists()) {
                    file.mkdirs();
                }
                bhVar = bh.a(file, bsVar.b);
                try {
                    bhVar.a(bsVar.d);
                    byte[] b = bsVar.e.b(bArr);
                    a b2 = bhVar.b(str);
                    outputStream = b2.a();
                    outputStream.write(b);
                    b2.b();
                    bhVar.c();
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable th3) {
                            ThrowableExtension.printStackTrace(th3);
                        }
                    }
                    if (bhVar != null) {
                        try {
                            bhVar.close();
                            return;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable th6) {
                            ThrowableExtension.printStackTrace(th6);
                        }
                    }
                    if (bhVar != null) {
                        try {
                            bhVar.close();
                        } catch (Throwable th7) {
                            ThrowableExtension.printStackTrace(th7);
                        }
                    }
                    throw th;
                }
            }
            ThrowableExtension.printStackTrace(th);
        } catch (Throwable th8) {
            th = th8;
            bhVar = null;
        }
    }

    private static boolean a(String str, String str2) {
        try {
            return new File(str, str2 + ".0").exists();
        } catch (Throwable th) {
            aj.b(th, "leg", "fet");
            return false;
        }
    }

    private static byte[] a(bh bhVar, bs bsVar, List<String> list) {
        String[] list2;
        try {
            File b = bhVar.b();
            if (b != null && b.exists()) {
                int i = 0;
                for (String str : b.list()) {
                    if (str.contains(".0")) {
                        String str2 = str.split("\\.")[0];
                        byte[] a = bz.a(bhVar, str2);
                        i += a.length;
                        list.add(str2);
                        if (i > bsVar.f.b()) {
                            break;
                        }
                        bsVar.g.b(a);
                    }
                }
                return bsVar.g.a();
            }
        } catch (Throwable th) {
            aj.b(th, "leg", "gCo");
        }
        return new byte[0];
    }
}
