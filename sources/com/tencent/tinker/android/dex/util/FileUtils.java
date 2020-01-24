package com.tencent.tinker.android.dex.util;

import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtils {
    private FileUtils() {
    }

    public static byte[] readFile(String fileName) throws IOException {
        return readFile(new File(fileName));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a8 A[SYNTHETIC, Splitter:B:28:0x00a8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readFile(java.io.File r11) throws java.io.IOException {
        /*
            boolean r8 = r11.exists()
            if (r8 != 0) goto L_0x0020
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r10 = ": file not found"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0020:
            boolean r8 = r11.isFile()
            if (r8 != 0) goto L_0x0040
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r10 = ": not a file"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0040:
            boolean r8 = r11.canRead()
            if (r8 != 0) goto L_0x0060
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r10 = ": file not readable"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0060:
            long r6 = r11.length()
            int r5 = (int) r6
            long r8 = (long) r5
            int r8 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x0084
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r10 = ": file too long"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0084:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>(r5)
            r3 = 0
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ all -> 0x00ba }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ all -> 0x00ba }
            r8.<init>(r11)     // Catch:{ all -> 0x00ba }
            r4.<init>(r8)     // Catch:{ all -> 0x00ba }
            r8 = 8192(0x2000, float:1.14794E-41)
            byte[] r1 = new byte[r8]     // Catch:{ all -> 0x00a4 }
            r2 = 0
        L_0x0099:
            int r2 = r4.read(r1)     // Catch:{ all -> 0x00a4 }
            if (r2 <= 0) goto L_0x00ac
            r8 = 0
            r0.write(r1, r8, r2)     // Catch:{ all -> 0x00a4 }
            goto L_0x0099
        L_0x00a4:
            r8 = move-exception
            r3 = r4
        L_0x00a6:
            if (r3 == 0) goto L_0x00ab
            r3.close()     // Catch:{ Exception -> 0x00b8 }
        L_0x00ab:
            throw r8
        L_0x00ac:
            if (r4 == 0) goto L_0x00b1
            r4.close()     // Catch:{ Exception -> 0x00b6 }
        L_0x00b1:
            byte[] r8 = r0.toByteArray()
            return r8
        L_0x00b6:
            r8 = move-exception
            goto L_0x00b1
        L_0x00b8:
            r9 = move-exception
            goto L_0x00ab
        L_0x00ba:
            r8 = move-exception
            goto L_0x00a6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.android.dex.util.FileUtils.readFile(java.io.File):byte[]");
    }

    public static byte[] readStream(InputStream is) throws IOException {
        return readStream(is, 32768);
    }

    public static byte[] readStream(InputStream is, int initSize) throws IOException {
        if (initSize <= 0) {
            initSize = 32768;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(initSize);
        byte[] buffer = new byte[8192];
        while (true) {
            int bytesRead = is.read(buffer);
            if (bytesRead <= 0) {
                return baos.toByteArray();
            }
            baos.write(buffer, 0, bytesRead);
        }
    }

    public static boolean hasArchiveSuffix(String fileName) {
        return fileName.endsWith(".zip") || fileName.endsWith(ShareConstants.JAR_SUFFIX) || fileName.endsWith(ShareConstants.PATCH_SUFFIX);
    }
}
