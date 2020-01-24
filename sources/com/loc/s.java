package com.loc;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: MD5 */
public final class s {
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v12, types: [java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r0v8, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v5
      assigns: []
      uses: []
      mth insns count: 60
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
    /* JADX WARNING: Removed duplicated region for block: B:45:0x007f A[SYNTHETIC, Splitter:B:45:0x007f] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r6) {
        /*
            r0 = 0
            r1 = 0
            boolean r2 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            if (r2 == 0) goto L_0x000e
            if (r0 == 0) goto L_0x000d
            r1.close()     // Catch:{ IOException -> 0x008e }
        L_0x000d:
            return r0
        L_0x000e:
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            boolean r2 = r3.isFile()     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            if (r2 == 0) goto L_0x001f
            boolean r2 = r3.exists()     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            if (r2 != 0) goto L_0x0030
        L_0x001f:
            if (r0 == 0) goto L_0x000d
            r1.close()     // Catch:{ IOException -> 0x0025 }
            goto L_0x000d
        L_0x0025:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "gfm"
        L_0x002c:
            com.loc.ag.a(r1, r2, r3)
            goto L_0x000d
        L_0x0030:
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            java.lang.String r2 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r2)     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0098, all -> 0x007a }
        L_0x0040:
            int r3 = r2.read(r1)     // Catch:{ Throwable -> 0x004c }
            r5 = -1
            if (r3 == r5) goto L_0x0064
            r5 = 0
            r4.update(r1, r5, r3)     // Catch:{ Throwable -> 0x004c }
            goto L_0x0040
        L_0x004c:
            r1 = move-exception
        L_0x004d:
            java.lang.String r3 = "MD5"
            java.lang.String r4 = "gfm"
            com.loc.ag.a(r1, r3, r4)     // Catch:{ all -> 0x0096 }
            if (r2 == 0) goto L_0x000d
            r2.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x000d
        L_0x005c:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "gfm"
            goto L_0x002c
        L_0x0064:
            byte[] r1 = r4.digest()     // Catch:{ Throwable -> 0x004c }
            java.lang.String r0 = com.loc.w.e(r1)     // Catch:{ Throwable -> 0x004c }
            if (r2 == 0) goto L_0x000d
            r2.close()     // Catch:{ IOException -> 0x0072 }
            goto L_0x000d
        L_0x0072:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "gfm"
            goto L_0x002c
        L_0x007a:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x007d:
            if (r2 == 0) goto L_0x0082
            r2.close()     // Catch:{ IOException -> 0x0083 }
        L_0x0082:
            throw r0
        L_0x0083:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "gfm"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0082
        L_0x008e:
            r1 = move-exception
            java.lang.String r2 = "MD5"
            java.lang.String r3 = "gfm"
            goto L_0x002c
        L_0x0096:
            r0 = move-exception
            goto L_0x007d
        L_0x0098:
            r1 = move-exception
            r2 = r0
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.s.a(java.lang.String):java.lang.String");
    }

    public static String a(byte[] bArr) {
        return w.e(a(bArr, MessageDigestAlgorithms.MD5));
    }

    public static byte[] a(byte[] bArr, String str) {
        boolean z = false;
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return instance.digest();
        } catch (Throwable th) {
            ag.a(th, MessageDigestAlgorithms.MD5, "gmb");
            return z;
        }
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        return w.e(d(str));
    }

    public static String c(String str) {
        return w.f(e(str));
    }

    private static byte[] d(String str) {
        try {
            return f(str);
        } catch (Throwable th) {
            ag.a(th, MessageDigestAlgorithms.MD5, "gmb");
            return new byte[0];
        }
    }

    private static byte[] e(String str) {
        try {
            return f(str);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return new byte[0];
        }
    }

    private static byte[] f(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
        instance.update(w.a(str));
        return instance.digest();
    }
}
