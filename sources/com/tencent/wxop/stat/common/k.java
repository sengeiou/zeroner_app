package com.tencent.wxop.stat.common;

class k extends i {
    static final /* synthetic */ boolean g = (!h.class.desiredAssertionStatus());
    private static final byte[] h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, Framer.EXIT_FRAME_PREFIX, 121, 122, 48, Framer.STDOUT_FRAME_PREFIX, Framer.STDERR_FRAME_PREFIX, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] i = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, Framer.EXIT_FRAME_PREFIX, 121, 122, 48, Framer.STDOUT_FRAME_PREFIX, Framer.STDERR_FRAME_PREFIX, 51, 52, 53, 54, 55, 56, 57, Framer.STDIN_FRAME_PREFIX, Framer.STDIN_REQUEST_FRAME_PREFIX};
    int c;
    public final boolean d;
    public final boolean e;
    public final boolean f;
    private final byte[] j;
    private int k;
    private final byte[] l;

    public k(int i2, byte[] bArr) {
        boolean z = true;
        this.a = bArr;
        this.d = (i2 & 1) == 0;
        this.e = (i2 & 2) == 0;
        if ((i2 & 4) == 0) {
            z = false;
        }
        this.f = z;
        this.l = (i2 & 8) == 0 ? h : i;
        this.j = new byte[2];
        this.c = 0;
        this.k = this.e ? 19 : -1;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(byte[] r11, int r12, int r13, boolean r14) {
        /*
            r10 = this;
            byte[] r6 = r10.l
            byte[] r7 = r10.a
            r4 = 0
            int r2 = r10.k
            int r8 = r13 + r12
            r0 = -1
            int r1 = r10.c
            switch(r1) {
                case 0: goto L_0x00a9;
                case 1: goto L_0x00ad;
                case 2: goto L_0x00d1;
                default: goto L_0x000f;
            }
        L_0x000f:
            r3 = r0
            r1 = r12
        L_0x0011:
            r0 = -1
            if (r3 == r0) goto L_0x0248
            r0 = 0
            int r4 = r3 >> 18
            r4 = r4 & 63
            byte r4 = r6[r4]
            r7[r0] = r4
            r0 = 1
            int r4 = r3 >> 12
            r4 = r4 & 63
            byte r4 = r6[r4]
            r7[r0] = r4
            r0 = 2
            int r4 = r3 >> 6
            r4 = r4 & 63
            byte r4 = r6[r4]
            r7[r0] = r4
            r4 = 3
            r0 = 4
            r3 = r3 & 63
            byte r3 = r6[r3]
            r7[r4] = r3
            int r2 = r2 + -1
            if (r2 != 0) goto L_0x0244
            boolean r2 = r10.f
            if (r2 == 0) goto L_0x0045
            r2 = 4
            r0 = 5
            r3 = 13
            r7[r2] = r3
        L_0x0045:
            int r4 = r0 + 1
            r2 = 10
            r7[r0] = r2
            r0 = 19
            r5 = r0
        L_0x004e:
            int r0 = r1 + 3
            if (r0 > r8) goto L_0x00f5
            byte r0 = r11[r1]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 16
            int r2 = r1 + 1
            byte r2 = r11[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 8
            r0 = r0 | r2
            int r2 = r1 + 2
            byte r2 = r11[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r0 = r0 | r2
            int r2 = r0 >> 18
            r2 = r2 & 63
            byte r2 = r6[r2]
            r7[r4] = r2
            int r2 = r4 + 1
            int r3 = r0 >> 12
            r3 = r3 & 63
            byte r3 = r6[r3]
            r7[r2] = r3
            int r2 = r4 + 2
            int r3 = r0 >> 6
            r3 = r3 & 63
            byte r3 = r6[r3]
            r7[r2] = r3
            int r2 = r4 + 3
            r0 = r0 & 63
            byte r0 = r6[r0]
            r7[r2] = r0
            int r2 = r1 + 3
            int r1 = r4 + 4
            int r0 = r5 + -1
            if (r0 != 0) goto L_0x023f
            boolean r0 = r10.f
            if (r0 == 0) goto L_0x023c
            int r0 = r1 + 1
            r3 = 13
            r7[r1] = r3
        L_0x009e:
            int r4 = r0 + 1
            r1 = 10
            r7[r0] = r1
            r0 = 19
            r1 = r2
            r5 = r0
            goto L_0x004e
        L_0x00a9:
            r3 = r0
            r1 = r12
            goto L_0x0011
        L_0x00ad:
            int r1 = r12 + 2
            if (r1 > r8) goto L_0x000f
            byte[] r0 = r10.j
            r1 = 0
            byte r0 = r0[r1]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 16
            int r1 = r12 + 1
            byte r3 = r11[r12]
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 8
            r0 = r0 | r3
            int r12 = r1 + 1
            byte r1 = r11[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            r1 = 0
            r10.c = r1
            r3 = r0
            r1 = r12
            goto L_0x0011
        L_0x00d1:
            int r1 = r12 + 1
            if (r1 > r8) goto L_0x000f
            byte[] r0 = r10.j
            r1 = 0
            byte r0 = r0[r1]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 16
            byte[] r1 = r10.j
            r3 = 1
            byte r1 = r1[r3]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << 8
            r0 = r0 | r1
            int r1 = r12 + 1
            byte r3 = r11[r12]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r0 = r0 | r3
            r3 = 0
            r10.c = r3
            r3 = r0
            goto L_0x0011
        L_0x00f5:
            if (r14 == 0) goto L_0x0202
            int r0 = r10.c
            int r0 = r1 - r0
            int r2 = r8 + -1
            if (r0 != r2) goto L_0x0161
            r3 = 0
            int r0 = r10.c
            if (r0 <= 0) goto L_0x015a
            byte[] r0 = r10.j
            r3 = 0
            r2 = 1
            byte r0 = r0[r3]
        L_0x010a:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r3 = r0 << 4
            int r0 = r10.c
            int r0 = r0 - r2
            r10.c = r0
            int r2 = r4 + 1
            int r0 = r3 >> 6
            r0 = r0 & 63
            byte r0 = r6[r0]
            r7[r4] = r0
            int r0 = r2 + 1
            r3 = r3 & 63
            byte r3 = r6[r3]
            r7[r2] = r3
            boolean r2 = r10.d
            if (r2 == 0) goto L_0x0135
            int r2 = r0 + 1
            r3 = 61
            r7[r0] = r3
            int r0 = r2 + 1
            r3 = 61
            r7[r2] = r3
        L_0x0135:
            boolean r2 = r10.e
            if (r2 == 0) goto L_0x014b
            boolean r2 = r10.f
            if (r2 == 0) goto L_0x0144
            int r2 = r0 + 1
            r3 = 13
            r7[r0] = r3
            r0 = r2
        L_0x0144:
            int r2 = r0 + 1
            r3 = 10
            r7[r0] = r3
            r0 = r2
        L_0x014b:
            r4 = r0
        L_0x014c:
            boolean r0 = g
            if (r0 != 0) goto L_0x01f6
            int r0 = r10.c
            if (r0 == 0) goto L_0x01f6
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x015a:
            int r2 = r1 + 1
            byte r0 = r11[r1]
            r1 = r2
            r2 = r3
            goto L_0x010a
        L_0x0161:
            int r0 = r10.c
            int r0 = r1 - r0
            int r2 = r8 + -2
            if (r0 != r2) goto L_0x01da
            r3 = 0
            int r0 = r10.c
            r2 = 1
            if (r0 <= r2) goto L_0x01cd
            byte[] r0 = r10.j
            r3 = 0
            r2 = 1
            byte r0 = r0[r3]
        L_0x0175:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r9 = r0 << 10
            int r0 = r10.c
            if (r0 <= 0) goto L_0x01d4
            byte[] r0 = r10.j
            int r3 = r2 + 1
            byte r0 = r0[r2]
            r2 = r3
        L_0x0184:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 2
            r0 = r0 | r9
            int r3 = r10.c
            int r2 = r3 - r2
            r10.c = r2
            int r2 = r4 + 1
            int r3 = r0 >> 12
            r3 = r3 & 63
            byte r3 = r6[r3]
            r7[r4] = r3
            int r3 = r2 + 1
            int r4 = r0 >> 6
            r4 = r4 & 63
            byte r4 = r6[r4]
            r7[r2] = r4
            int r2 = r3 + 1
            r0 = r0 & 63
            byte r0 = r6[r0]
            r7[r3] = r0
            boolean r0 = r10.d
            if (r0 == 0) goto L_0x0239
            int r0 = r2 + 1
            r3 = 61
            r7[r2] = r3
        L_0x01b5:
            boolean r2 = r10.e
            if (r2 == 0) goto L_0x01cb
            boolean r2 = r10.f
            if (r2 == 0) goto L_0x01c4
            int r2 = r0 + 1
            r3 = 13
            r7[r0] = r3
            r0 = r2
        L_0x01c4:
            int r2 = r0 + 1
            r3 = 10
            r7[r0] = r3
            r0 = r2
        L_0x01cb:
            r4 = r0
            goto L_0x014c
        L_0x01cd:
            int r2 = r1 + 1
            byte r0 = r11[r1]
            r1 = r2
            r2 = r3
            goto L_0x0175
        L_0x01d4:
            int r3 = r1 + 1
            byte r0 = r11[r1]
            r1 = r3
            goto L_0x0184
        L_0x01da:
            boolean r0 = r10.e
            if (r0 == 0) goto L_0x014c
            if (r4 <= 0) goto L_0x014c
            r0 = 19
            if (r5 == r0) goto L_0x014c
            boolean r0 = r10.f
            if (r0 == 0) goto L_0x0237
            int r0 = r4 + 1
            r2 = 13
            r7[r4] = r2
        L_0x01ee:
            int r4 = r0 + 1
            r2 = 10
            r7[r0] = r2
            goto L_0x014c
        L_0x01f6:
            boolean r0 = g
            if (r0 != 0) goto L_0x0212
            if (r1 == r8) goto L_0x0212
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0202:
            int r0 = r8 + -1
            if (r1 != r0) goto L_0x0218
            byte[] r0 = r10.j
            int r2 = r10.c
            int r3 = r2 + 1
            r10.c = r3
            byte r1 = r11[r1]
            r0[r2] = r1
        L_0x0212:
            r10.b = r4
            r10.k = r5
            r0 = 1
            return r0
        L_0x0218:
            int r0 = r8 + -2
            if (r1 != r0) goto L_0x0212
            byte[] r0 = r10.j
            int r2 = r10.c
            int r3 = r2 + 1
            r10.c = r3
            byte r3 = r11[r1]
            r0[r2] = r3
            byte[] r0 = r10.j
            int r2 = r10.c
            int r3 = r2 + 1
            r10.c = r3
            int r1 = r1 + 1
            byte r1 = r11[r1]
            r0[r2] = r1
            goto L_0x0212
        L_0x0237:
            r0 = r4
            goto L_0x01ee
        L_0x0239:
            r0 = r2
            goto L_0x01b5
        L_0x023c:
            r0 = r1
            goto L_0x009e
        L_0x023f:
            r5 = r0
            r4 = r1
            r1 = r2
            goto L_0x004e
        L_0x0244:
            r5 = r2
            r4 = r0
            goto L_0x004e
        L_0x0248:
            r5 = r2
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.common.k.a(byte[], int, int, boolean):boolean");
    }
}
