package com.tencent.wxop.stat.common;

class j extends i {
    private static final int[] c = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private static final int[] d = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private int e;
    private int f;
    private final int[] g;

    public j(int i, byte[] bArr) {
        this.a = bArr;
        this.g = (i & 8) == 0 ? c : d;
        this.e = 0;
        this.f = 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0108 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(byte[] r10, int r11, int r12, boolean r13) {
        /*
            r9 = this;
            int r0 = r9.e
            r1 = 6
            if (r0 != r1) goto L_0x0007
            r0 = 0
        L_0x0006:
            return r0
        L_0x0007:
            int r4 = r12 + r11
            int r2 = r9.e
            int r1 = r9.f
            r0 = 0
            byte[] r5 = r9.a
            int[] r6 = r9.g
            r3 = r2
            r2 = r11
        L_0x0014:
            if (r2 >= r4) goto L_0x0108
            if (r3 != 0) goto L_0x005d
        L_0x0018:
            int r7 = r2 + 4
            if (r7 > r4) goto L_0x005b
            byte r1 = r10[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r1 = r6[r1]
            int r1 = r1 << 18
            int r7 = r2 + 1
            byte r7 = r10[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r7 = r6[r7]
            int r7 = r7 << 12
            r1 = r1 | r7
            int r7 = r2 + 2
            byte r7 = r10[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r7 = r6[r7]
            int r7 = r7 << 6
            r1 = r1 | r7
            int r7 = r2 + 3
            byte r7 = r10[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r7 = r6[r7]
            r1 = r1 | r7
            if (r1 < 0) goto L_0x005b
            int r7 = r0 + 2
            byte r8 = (byte) r1
            r5[r7] = r8
            int r7 = r0 + 1
            int r8 = r1 >> 8
            byte r8 = (byte) r8
            r5[r7] = r8
            int r7 = r1 >> 16
            byte r7 = (byte) r7
            r5[r0] = r7
            int r0 = r0 + 3
            int r2 = r2 + 4
            goto L_0x0018
        L_0x005b:
            if (r2 >= r4) goto L_0x0108
        L_0x005d:
            int r11 = r2 + 1
            byte r2 = r10[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r2 = r6[r2]
            switch(r3) {
                case 0: goto L_0x006a;
                case 1: goto L_0x007a;
                case 2: goto L_0x008d;
                case 3: goto L_0x00b1;
                case 4: goto L_0x00ed;
                case 5: goto L_0x00ff;
                default: goto L_0x0068;
            }
        L_0x0068:
            r2 = r11
            goto L_0x0014
        L_0x006a:
            if (r2 < 0) goto L_0x0072
            int r1 = r3 + 1
            r3 = r1
            r1 = r2
            r2 = r11
            goto L_0x0014
        L_0x0072:
            r7 = -1
            if (r2 == r7) goto L_0x0068
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x007a:
            if (r2 < 0) goto L_0x0084
            int r1 = r1 << 6
            r1 = r1 | r2
            int r2 = r3 + 1
            r3 = r2
            r2 = r11
            goto L_0x0014
        L_0x0084:
            r7 = -1
            if (r2 == r7) goto L_0x0068
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x008d:
            if (r2 < 0) goto L_0x0098
            int r1 = r1 << 6
            r1 = r1 | r2
            int r2 = r3 + 1
            r3 = r2
            r2 = r11
            goto L_0x0014
        L_0x0098:
            r7 = -2
            if (r2 != r7) goto L_0x00a8
            int r2 = r0 + 1
            int r3 = r1 >> 4
            byte r3 = (byte) r3
            r5[r0] = r3
            r0 = 4
            r3 = r0
            r0 = r2
            r2 = r11
            goto L_0x0014
        L_0x00a8:
            r7 = -1
            if (r2 == r7) goto L_0x0068
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x00b1:
            if (r2 < 0) goto L_0x00ce
            int r1 = r1 << 6
            r1 = r1 | r2
            int r2 = r0 + 2
            byte r3 = (byte) r1
            r5[r2] = r3
            int r2 = r0 + 1
            int r3 = r1 >> 8
            byte r3 = (byte) r3
            r5[r2] = r3
            int r2 = r1 >> 16
            byte r2 = (byte) r2
            r5[r0] = r2
            int r0 = r0 + 3
            r2 = 0
            r3 = r2
            r2 = r11
            goto L_0x0014
        L_0x00ce:
            r7 = -2
            if (r2 != r7) goto L_0x00e4
            int r2 = r0 + 1
            int r3 = r1 >> 2
            byte r3 = (byte) r3
            r5[r2] = r3
            int r2 = r1 >> 10
            byte r2 = (byte) r2
            r5[r0] = r2
            int r0 = r0 + 2
            r2 = 5
            r3 = r2
            r2 = r11
            goto L_0x0014
        L_0x00e4:
            r7 = -1
            if (r2 == r7) goto L_0x0068
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x00ed:
            r7 = -2
            if (r2 != r7) goto L_0x00f6
            int r2 = r3 + 1
            r3 = r2
            r2 = r11
            goto L_0x0014
        L_0x00f6:
            r7 = -1
            if (r2 == r7) goto L_0x0068
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x00ff:
            r7 = -1
            if (r2 == r7) goto L_0x0068
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x0108:
            r2 = r1
            if (r13 != 0) goto L_0x0114
            r9.e = r3
            r9.f = r2
            r9.b = r0
            r0 = 1
            goto L_0x0006
        L_0x0114:
            switch(r3) {
                case 0: goto L_0x0117;
                case 1: goto L_0x011e;
                case 2: goto L_0x0124;
                case 3: goto L_0x012d;
                case 4: goto L_0x013c;
                default: goto L_0x0117;
            }
        L_0x0117:
            r9.e = r3
            r9.b = r0
            r0 = 1
            goto L_0x0006
        L_0x011e:
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        L_0x0124:
            int r1 = r0 + 1
            int r2 = r2 >> 4
            byte r2 = (byte) r2
            r5[r0] = r2
            r0 = r1
            goto L_0x0117
        L_0x012d:
            int r1 = r0 + 1
            int r4 = r2 >> 10
            byte r4 = (byte) r4
            r5[r0] = r4
            int r0 = r1 + 1
            int r2 = r2 >> 2
            byte r2 = (byte) r2
            r5[r1] = r2
            goto L_0x0117
        L_0x013c:
            r0 = 6
            r9.e = r0
            r0 = 0
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.common.j.a(byte[], int, int, boolean):boolean");
    }
}
