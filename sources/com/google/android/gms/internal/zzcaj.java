package com.google.android.gms.internal;

public final class zzcaj {
    private static final ThreadLocal<String> zzhfv = new ThreadLocal<>();

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0093, code lost:
        r0 = r0 ^ r4;
        r0 = (r0 ^ (r0 >>> 16)) * -2048144789;
        r0 = (r0 ^ (r0 >>> 13)) * -1028477387;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00b3, code lost:
        r1 = ((r3[r5 + 1] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) << 8) | r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return java.lang.Integer.toHexString(r0 ^ (r0 >>> 16));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zzho(java.lang.String r10) {
        /*
            r9 = 461845907(0x1b873593, float:2.2368498E-22)
            r8 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            r1 = 0
            java.lang.ThreadLocal<java.lang.String> r0 = zzhfv
            java.lang.Object r0 = r0.get()
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x001a
            java.lang.String r2 = "com.google"
            boolean r0 = r0.startsWith(r2)
            if (r0 == 0) goto L_0x001e
        L_0x001a:
            r0 = 1
        L_0x001b:
            if (r0 == 0) goto L_0x0020
        L_0x001d:
            return r10
        L_0x001e:
            r0 = r1
            goto L_0x001b
        L_0x0020:
            java.lang.ThreadLocal<java.lang.String> r0 = zzhfv
            java.lang.Object r0 = r0.get()
            java.lang.String r0 = (java.lang.String) r0
            if (r10 == 0) goto L_0x001d
            if (r0 == 0) goto L_0x001d
            int r2 = r10.length()
            int r3 = r0.length()
            int r2 = r2 + r3
            byte[] r3 = new byte[r2]
            byte[] r2 = r10.getBytes()
            int r4 = r10.length()
            java.lang.System.arraycopy(r2, r1, r3, r1, r4)
            byte[] r2 = r0.getBytes()
            int r4 = r10.length()
            int r0 = r0.length()
            java.lang.System.arraycopy(r2, r1, r3, r4, r0)
            int r4 = r3.length
            r0 = r4 & -4
            int r5 = r0 + 0
            r0 = r1
            r2 = r1
        L_0x0058:
            if (r0 >= r5) goto L_0x008d
            byte r6 = r3[r0]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r7 = r0 + 1
            byte r7 = r3[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r6 = r6 | r7
            int r7 = r0 + 2
            byte r7 = r3[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 16
            r6 = r6 | r7
            int r7 = r0 + 3
            byte r7 = r3[r7]
            int r7 = r7 << 24
            r6 = r6 | r7
            int r6 = r6 * r8
            int r7 = r6 << 15
            int r6 = r6 >>> 17
            r6 = r6 | r7
            int r6 = r6 * r9
            r2 = r2 ^ r6
            int r6 = r2 << 13
            int r2 = r2 >>> 19
            r2 = r2 | r6
            int r2 = r2 * 5
            r6 = -430675100(0xffffffffe6546b64, float:-2.5078068E23)
            int r2 = r2 + r6
            int r0 = r0 + 4
            goto L_0x0058
        L_0x008d:
            r0 = r4 & 3
            switch(r0) {
                case 1: goto L_0x00bc;
                case 2: goto L_0x00ca;
                case 3: goto L_0x00ab;
                default: goto L_0x0092;
            }
        L_0x0092:
            r0 = r2
        L_0x0093:
            r0 = r0 ^ r4
            int r1 = r0 >>> 16
            r0 = r0 ^ r1
            r1 = -2048144789(0xffffffff85ebca6b, float:-2.217365E-35)
            int r0 = r0 * r1
            int r1 = r0 >>> 13
            r0 = r0 ^ r1
            r1 = -1028477387(0xffffffffc2b2ae35, float:-89.34025)
            int r0 = r0 * r1
            int r1 = r0 >>> 16
            r0 = r0 ^ r1
            java.lang.String r10 = java.lang.Integer.toHexString(r0)
            goto L_0x001d
        L_0x00ab:
            int r0 = r5 + 2
            byte r0 = r3[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 16
        L_0x00b3:
            int r1 = r5 + 1
            byte r1 = r3[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << 8
            r1 = r1 | r0
        L_0x00bc:
            byte r0 = r3[r5]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            int r0 = r0 * r8
            int r1 = r0 << 15
            int r0 = r0 >>> 17
            r0 = r0 | r1
            int r0 = r0 * r9
            r0 = r0 ^ r2
            goto L_0x0093
        L_0x00ca:
            r0 = r1
            goto L_0x00b3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcaj.zzho(java.lang.String):java.lang.String");
    }
}
