package com.tencent.tinker.loader.shareutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.tencent.tinker.loader.TinkerRuntimeException;
import java.io.File;
import java.security.cert.Certificate;
import java.util.HashMap;

public class ShareSecurityCheck {
    private static final String TAG = "Tinker.SecurityCheck";
    private static String mPublicKeyMd5 = null;
    private final Context mContext;
    private final HashMap<String, String> metaContentMap = new HashMap<>();
    private final HashMap<String, String> packageProperties = new HashMap<>();

    public ShareSecurityCheck(Context context) {
        this.mContext = context;
        if (mPublicKeyMd5 == null) {
            init(this.mContext);
        }
    }

    public HashMap<String, String> getMetaContentMap() {
        return this.metaContentMap;
    }

    public HashMap<String, String> getPackagePropertiesIfPresent() {
        String[] lines;
        if (!this.packageProperties.isEmpty()) {
            return this.packageProperties;
        }
        String property = (String) this.metaContentMap.get(ShareConstants.PACKAGE_META_FILE);
        if (property == null) {
            return null;
        }
        for (String line : property.split("\n")) {
            if (line != null && line.length() > 0 && !line.startsWith("#")) {
                String[] kv = line.split("=", 2);
                if (kv != null && kv.length >= 2) {
                    this.packageProperties.put(kv[0].trim(), kv[1].trim());
                }
            }
        }
        return this.packageProperties;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0097 A[SYNTHETIC, Splitter:B:36:0x0097] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean verifyPatchMetaSignature(java.io.File r15) {
        /*
            r14 = this;
            r8 = 1
            r7 = 0
            boolean r9 = com.tencent.tinker.loader.shareutil.SharePatchFileUtil.isLegalFile(r15)
            if (r9 != 0) goto L_0x0009
        L_0x0008:
            return r7
        L_0x0009:
            r4 = 0
            java.util.jar.JarFile r5 = new java.util.jar.JarFile     // Catch:{ Exception -> 0x0071 }
            r5.<init>(r15)     // Catch:{ Exception -> 0x0071 }
            java.util.Enumeration r2 = r5.entries()     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
        L_0x0013:
            boolean r9 = r2.hasMoreElements()     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            if (r9 == 0) goto L_0x005e
            java.lang.Object r3 = r2.nextElement()     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            java.util.jar.JarEntry r3 = (java.util.jar.JarEntry) r3     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            if (r3 == 0) goto L_0x0013
            java.lang.String r6 = r3.getName()     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            java.lang.String r9 = "META-INF/"
            boolean r9 = r6.startsWith(r9)     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            if (r9 != 0) goto L_0x0013
            java.lang.String r9 = "meta.txt"
            boolean r9 = r6.endsWith(r9)     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            if (r9 == 0) goto L_0x0013
            java.util.HashMap<java.lang.String, java.lang.String> r9 = r14.metaContentMap     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            java.lang.String r10 = com.tencent.tinker.loader.shareutil.SharePatchFileUtil.loadDigestes(r5, r3)     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            r9.put(r6, r10)     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            java.security.cert.Certificate[] r0 = r3.getCertificates()     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            if (r0 == 0) goto L_0x004c
            boolean r9 = r14.check(r15, r0)     // Catch:{ Exception -> 0x00aa, all -> 0x00a7 }
            if (r9 != 0) goto L_0x0013
        L_0x004c:
            if (r5 == 0) goto L_0x0008
            r5.close()     // Catch:{ IOException -> 0x0052 }
            goto L_0x0008
        L_0x0052:
            r1 = move-exception
            java.lang.String r8 = "Tinker.SecurityCheck"
            java.lang.String r9 = r15.getAbsolutePath()
            android.util.Log.e(r8, r9, r1)
            goto L_0x0008
        L_0x005e:
            if (r5 == 0) goto L_0x0063
            r5.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0063:
            r7 = r8
            goto L_0x0008
        L_0x0065:
            r1 = move-exception
            java.lang.String r7 = "Tinker.SecurityCheck"
            java.lang.String r9 = r15.getAbsolutePath()
            android.util.Log.e(r7, r9, r1)
            goto L_0x0063
        L_0x0071:
            r1 = move-exception
        L_0x0072:
            com.tencent.tinker.loader.TinkerRuntimeException r7 = new com.tencent.tinker.loader.TinkerRuntimeException     // Catch:{ all -> 0x0094 }
            java.lang.String r8 = "ShareSecurityCheck file %s, size %d verifyPatchMetaSignature fail"
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x0094 }
            r10 = 0
            java.lang.String r11 = r15.getAbsolutePath()     // Catch:{ all -> 0x0094 }
            r9[r10] = r11     // Catch:{ all -> 0x0094 }
            r10 = 1
            long r12 = r15.length()     // Catch:{ all -> 0x0094 }
            java.lang.Long r11 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0094 }
            r9[r10] = r11     // Catch:{ all -> 0x0094 }
            java.lang.String r8 = java.lang.String.format(r8, r9)     // Catch:{ all -> 0x0094 }
            r7.<init>(r8, r1)     // Catch:{ all -> 0x0094 }
            throw r7     // Catch:{ all -> 0x0094 }
        L_0x0094:
            r7 = move-exception
        L_0x0095:
            if (r4 == 0) goto L_0x009a
            r4.close()     // Catch:{ IOException -> 0x009b }
        L_0x009a:
            throw r7
        L_0x009b:
            r1 = move-exception
            java.lang.String r8 = "Tinker.SecurityCheck"
            java.lang.String r9 = r15.getAbsolutePath()
            android.util.Log.e(r8, r9, r1)
            goto L_0x009a
        L_0x00a7:
            r7 = move-exception
            r4 = r5
            goto L_0x0095
        L_0x00aa:
            r1 = move-exception
            r4 = r5
            goto L_0x0072
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.loader.shareutil.ShareSecurityCheck.verifyPatchMetaSignature(java.io.File):boolean");
    }

    private boolean check(File path, Certificate[] certs) {
        if (certs.length > 0) {
            int i = certs.length - 1;
            while (i >= 0) {
                try {
                    if (mPublicKeyMd5.equals(SharePatchFileUtil.getMD5(certs[i].getEncoded()))) {
                        return true;
                    }
                    i--;
                } catch (Exception e) {
                    Log.e(TAG, path.getAbsolutePath(), e);
                }
            }
        }
        return false;
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    private void init(Context context) {
        try {
            mPublicKeyMd5 = SharePatchFileUtil.getMD5(context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray());
            if (mPublicKeyMd5 == null) {
                throw new TinkerRuntimeException("get public key md5 is null");
            }
            SharePatchFileUtil.closeQuietly(null);
        } catch (Exception e) {
            throw new TinkerRuntimeException("ShareSecurityCheck init public key fail", e);
        } catch (Throwable th) {
            SharePatchFileUtil.closeQuietly(null);
            throw th;
        }
    }
}
