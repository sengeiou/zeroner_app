package com.tencent.tinker.ziputils.ziputil;

import java.io.IOException;
import java.io.InputStream;

public class TinkerZipUtil {
    private static final int BUFFER_SIZE = 16384;

    public static void extractTinkerEntry(TinkerZipFile apk, TinkerZipEntry zipEntry, TinkerZipOutputStream outputStream) throws IOException {
        InputStream in = null;
        try {
            in = apk.getInputStream(zipEntry);
            outputStream.putNextEntry(new TinkerZipEntry(zipEntry));
            byte[] buffer = new byte[16384];
            for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.closeEntry();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static void extractTinkerEntry(TinkerZipEntry zipEntry, InputStream inputStream, TinkerZipOutputStream outputStream) throws IOException {
        outputStream.putNextEntry(zipEntry);
        byte[] buffer = new byte[16384];
        int length = inputStream.read(buffer);
        while (length != -1) {
            outputStream.write(buffer, 0, length);
            length = inputStream.read(buffer);
        }
        outputStream.closeEntry();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void extractLargeModifyFile(com.tencent.tinker.ziputils.ziputil.TinkerZipEntry r8, java.io.File r9, long r10, com.tencent.tinker.ziputils.ziputil.TinkerZipOutputStream r12) throws java.io.IOException {
        /*
            r5 = 0
            com.tencent.tinker.ziputils.ziputil.TinkerZipEntry r4 = new com.tencent.tinker.ziputils.ziputil.TinkerZipEntry
            r4.<init>(r8)
            r4.setMethod(r5)
            long r6 = r9.length()
            r4.setSize(r6)
            long r6 = r9.length()
            r4.setCompressedSize(r6)
            r4.setCrc(r10)
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ all -> 0x004a }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x004a }
            r5.<init>(r9)     // Catch:{ all -> 0x004a }
            r2.<init>(r5)     // Catch:{ all -> 0x004a }
            com.tencent.tinker.ziputils.ziputil.TinkerZipEntry r5 = new com.tencent.tinker.ziputils.ziputil.TinkerZipEntry     // Catch:{ all -> 0x0051 }
            r5.<init>(r4)     // Catch:{ all -> 0x0051 }
            r12.putNextEntry(r5)     // Catch:{ all -> 0x0051 }
            r5 = 16384(0x4000, float:2.2959E-41)
            byte[] r0 = new byte[r5]     // Catch:{ all -> 0x0051 }
            int r3 = r2.read(r0)     // Catch:{ all -> 0x0051 }
        L_0x0035:
            r5 = -1
            if (r3 == r5) goto L_0x0041
            r5 = 0
            r12.write(r0, r5, r3)     // Catch:{ all -> 0x0051 }
            int r3 = r2.read(r0)     // Catch:{ all -> 0x0051 }
            goto L_0x0035
        L_0x0041:
            r12.closeEntry()     // Catch:{ all -> 0x0051 }
            if (r2 == 0) goto L_0x0049
            r2.close()
        L_0x0049:
            return
        L_0x004a:
            r5 = move-exception
        L_0x004b:
            if (r1 == 0) goto L_0x0050
            r1.close()
        L_0x0050:
            throw r5
        L_0x0051:
            r5 = move-exception
            r1 = r2
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.ziputils.ziputil.TinkerZipUtil.extractLargeModifyFile(com.tencent.tinker.ziputils.ziputil.TinkerZipEntry, java.io.File, long, com.tencent.tinker.ziputils.ziputil.TinkerZipOutputStream):void");
    }
}
