package com.tencent.tinker.loader.shareutil;

public final class ShareOatUtil {
    private static final String TAG = "Tinker.OatUtil";

    private enum InstructionSet {
        kNone,
        kArm,
        kArm64,
        kThumb2,
        kX86,
        kX86_64,
        kMips,
        kMips64
    }

    private ShareOatUtil() {
        throw new UnsupportedOperationException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0021 A[SYNTHETIC, Splitter:B:12:0x0021] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getOatFileInstructionSet(java.io.File r20) throws java.lang.Throwable {
        /*
            r5 = 0
            java.lang.String r11 = ""
            com.tencent.tinker.loader.shareutil.ShareElfFile r6 = new com.tencent.tinker.loader.shareutil.ShareElfFile     // Catch:{ all -> 0x016f }
            r0 = r20
            r6.<init>(r0)     // Catch:{ all -> 0x016f }
            java.lang.String r15 = ".rodata"
            com.tencent.tinker.loader.shareutil.ShareElfFile$SectionHeader r12 = r6.getSectionHeaderByName(r15)     // Catch:{ all -> 0x001d }
            if (r12 != 0) goto L_0x0025
            java.io.IOException r15 = new java.io.IOException     // Catch:{ all -> 0x001d }
            java.lang.String r16 = "Unable to find .rodata section."
            r15.<init>(r16)     // Catch:{ all -> 0x001d }
            throw r15     // Catch:{ all -> 0x001d }
        L_0x001d:
            r15 = move-exception
            r5 = r6
        L_0x001f:
            if (r5 == 0) goto L_0x0024
            r5.close()     // Catch:{ Exception -> 0x016c }
        L_0x0024:
            throw r15
        L_0x0025:
            java.nio.channels.FileChannel r3 = r6.getChannel()     // Catch:{ all -> 0x001d }
            long r0 = r12.shOffset     // Catch:{ all -> 0x001d }
            r16 = r0
            r0 = r16
            r3.position(r0)     // Catch:{ all -> 0x001d }
            r15 = 8
            byte[] r9 = new byte[r15]     // Catch:{ all -> 0x001d }
            java.nio.ByteBuffer r15 = java.nio.ByteBuffer.wrap(r9)     // Catch:{ all -> 0x001d }
            java.lang.String r16 = "Failed to read oat magic and version."
            r0 = r16
            com.tencent.tinker.loader.shareutil.ShareElfFile.readUntilLimit(r3, r15, r0)     // Catch:{ all -> 0x001d }
            r15 = 0
            byte r15 = r9[r15]     // Catch:{ all -> 0x001d }
            r16 = 111(0x6f, float:1.56E-43)
            r0 = r16
            if (r15 != r0) goto L_0x0066
            r15 = 1
            byte r15 = r9[r15]     // Catch:{ all -> 0x001d }
            r16 = 97
            r0 = r16
            if (r15 != r0) goto L_0x0066
            r15 = 2
            byte r15 = r9[r15]     // Catch:{ all -> 0x001d }
            r16 = 116(0x74, float:1.63E-43)
            r0 = r16
            if (r15 != r0) goto L_0x0066
            r15 = 3
            byte r15 = r9[r15]     // Catch:{ all -> 0x001d }
            r16 = 10
            r0 = r16
            if (r15 == r0) goto L_0x00ab
        L_0x0066:
            java.io.IOException r15 = new java.io.IOException     // Catch:{ all -> 0x001d }
            java.lang.String r16 = "Bad oat magic: %x %x %x %x"
            r17 = 4
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x001d }
            r17 = r0
            r18 = 0
            r19 = 0
            byte r19 = r9[r19]     // Catch:{ all -> 0x001d }
            java.lang.Byte r19 = java.lang.Byte.valueOf(r19)     // Catch:{ all -> 0x001d }
            r17[r18] = r19     // Catch:{ all -> 0x001d }
            r18 = 1
            r19 = 1
            byte r19 = r9[r19]     // Catch:{ all -> 0x001d }
            java.lang.Byte r19 = java.lang.Byte.valueOf(r19)     // Catch:{ all -> 0x001d }
            r17[r18] = r19     // Catch:{ all -> 0x001d }
            r18 = 2
            r19 = 2
            byte r19 = r9[r19]     // Catch:{ all -> 0x001d }
            java.lang.Byte r19 = java.lang.Byte.valueOf(r19)     // Catch:{ all -> 0x001d }
            r17[r18] = r19     // Catch:{ all -> 0x001d }
            r18 = 3
            r19 = 3
            byte r19 = r9[r19]     // Catch:{ all -> 0x001d }
            java.lang.Byte r19 = java.lang.Byte.valueOf(r19)     // Catch:{ all -> 0x001d }
            r17[r18] = r19     // Catch:{ all -> 0x001d }
            java.lang.String r16 = java.lang.String.format(r16, r17)     // Catch:{ all -> 0x001d }
            r15.<init>(r16)     // Catch:{ all -> 0x001d }
            throw r15     // Catch:{ all -> 0x001d }
        L_0x00ab:
            r14 = 4
            r13 = 3
            java.lang.String r10 = new java.lang.String     // Catch:{ all -> 0x001d }
            r15 = 4
            r16 = 3
            java.lang.String r17 = "ASCII"
            java.nio.charset.Charset r17 = java.nio.charset.Charset.forName(r17)     // Catch:{ all -> 0x001d }
            r0 = r16
            r1 = r17
            r10.<init>(r9, r15, r0, r1)     // Catch:{ all -> 0x001d }
            java.lang.Integer.parseInt(r10)     // Catch:{ NumberFormatException -> 0x0112 }
            r15 = 128(0x80, float:1.794E-43)
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r15)     // Catch:{ all -> 0x001d }
            java.nio.ByteOrder r15 = r6.getDataOrder()     // Catch:{ all -> 0x001d }
            r2.order(r15)     // Catch:{ all -> 0x001d }
            r8 = 12
            long r0 = r12.shOffset     // Catch:{ all -> 0x001d }
            r16 = r0
            r18 = 12
            long r16 = r16 + r18
            r0 = r16
            r3.position(r0)     // Catch:{ all -> 0x001d }
            r15 = 4
            r2.limit(r15)     // Catch:{ all -> 0x001d }
            java.lang.String r15 = "Failed to read isa num."
            com.tencent.tinker.loader.shareutil.ShareElfFile.readUntilLimit(r3, r2, r15)     // Catch:{ all -> 0x001d }
            int r7 = r2.getInt()     // Catch:{ all -> 0x001d }
            if (r7 < 0) goto L_0x00f6
            com.tencent.tinker.loader.shareutil.ShareOatUtil$InstructionSet[] r15 = com.tencent.tinker.loader.shareutil.ShareOatUtil.InstructionSet.values()     // Catch:{ all -> 0x001d }
            int r15 = r15.length     // Catch:{ all -> 0x001d }
            if (r7 < r15) goto L_0x012f
        L_0x00f6:
            java.io.IOException r15 = new java.io.IOException     // Catch:{ all -> 0x001d }
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x001d }
            r16.<init>()     // Catch:{ all -> 0x001d }
            java.lang.String r17 = "Bad isa num: "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x001d }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r7)     // Catch:{ all -> 0x001d }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x001d }
            r15.<init>(r16)     // Catch:{ all -> 0x001d }
            throw r15     // Catch:{ all -> 0x001d }
        L_0x0112:
            r4 = move-exception
            java.io.IOException r15 = new java.io.IOException     // Catch:{ all -> 0x001d }
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x001d }
            r16.<init>()     // Catch:{ all -> 0x001d }
            java.lang.String r17 = "Bad oat version: "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x001d }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r10)     // Catch:{ all -> 0x001d }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x001d }
            r15.<init>(r16)     // Catch:{ all -> 0x001d }
            throw r15     // Catch:{ all -> 0x001d }
        L_0x012f:
            int[] r15 = com.tencent.tinker.loader.shareutil.ShareOatUtil.AnonymousClass1.$SwitchMap$com$tencent$tinker$loader$shareutil$ShareOatUtil$InstructionSet     // Catch:{ all -> 0x001d }
            com.tencent.tinker.loader.shareutil.ShareOatUtil$InstructionSet[] r16 = com.tencent.tinker.loader.shareutil.ShareOatUtil.InstructionSet.values()     // Catch:{ all -> 0x001d }
            r16 = r16[r7]     // Catch:{ all -> 0x001d }
            int r16 = r16.ordinal()     // Catch:{ all -> 0x001d }
            r15 = r15[r16]     // Catch:{ all -> 0x001d }
            switch(r15) {
                case 1: goto L_0x0149;
                case 2: goto L_0x0149;
                case 3: goto L_0x0152;
                case 4: goto L_0x0156;
                case 5: goto L_0x015a;
                case 6: goto L_0x015e;
                case 7: goto L_0x0162;
                case 8: goto L_0x0166;
                default: goto L_0x0140;
            }     // Catch:{ all -> 0x001d }
        L_0x0140:
            java.io.IOException r15 = new java.io.IOException     // Catch:{ all -> 0x001d }
            java.lang.String r16 = "Should not reach here."
            r15.<init>(r16)     // Catch:{ all -> 0x001d }
            throw r15     // Catch:{ all -> 0x001d }
        L_0x0149:
            java.lang.String r11 = "arm"
        L_0x014c:
            if (r6 == 0) goto L_0x0151
            r6.close()     // Catch:{ Exception -> 0x016a }
        L_0x0151:
            return r11
        L_0x0152:
            java.lang.String r11 = "arm64"
            goto L_0x014c
        L_0x0156:
            java.lang.String r11 = "x86"
            goto L_0x014c
        L_0x015a:
            java.lang.String r11 = "x86_64"
            goto L_0x014c
        L_0x015e:
            java.lang.String r11 = "mips"
            goto L_0x014c
        L_0x0162:
            java.lang.String r11 = "mips64"
            goto L_0x014c
        L_0x0166:
            java.lang.String r11 = "none"
            goto L_0x014c
        L_0x016a:
            r15 = move-exception
            goto L_0x0151
        L_0x016c:
            r16 = move-exception
            goto L_0x0024
        L_0x016f:
            r15 = move-exception
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.loader.shareutil.ShareOatUtil.getOatFileInstructionSet(java.io.File):java.lang.String");
    }
}
