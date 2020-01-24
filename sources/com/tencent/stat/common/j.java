package com.tencent.stat.common;

class j extends h {
    static final /* synthetic */ boolean g = (!g.class.desiredAssertionStatus());
    private static final byte[] h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, Framer.EXIT_FRAME_PREFIX, 121, 122, 48, Framer.STDOUT_FRAME_PREFIX, Framer.STDERR_FRAME_PREFIX, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] i = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, Framer.EXIT_FRAME_PREFIX, 121, 122, 48, Framer.STDOUT_FRAME_PREFIX, Framer.STDERR_FRAME_PREFIX, 51, 52, 53, 54, 55, 56, 57, Framer.STDIN_FRAME_PREFIX, Framer.STDIN_REQUEST_FRAME_PREFIX};
    int c;
    public final boolean d;
    public final boolean e;
    public final boolean f;
    private final byte[] j;
    private int k;
    private final byte[] l;

    public j(int i2, byte[] bArr) {
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
    public boolean a(byte[] r12, int r13, int r14, boolean r15) {
        /*
            r11 = this;
            byte[] r6 = r11.l
            byte[] r7 = r11.a
            r1 = 0
            int r0 = r11.k
            int r8 = r14 + r13
            r2 = -1
            int r3 = r11.c
            switch(r3) {
                case 0: goto L_0x00a7;
                case 1: goto L_0x00aa;
                case 2: goto L_0x00cd;
                default: goto L_0x000f;
            }
        L_0x000f:
            r3 = r13
        L_0x0010:
            r4 = -1
            if (r2 == r4) goto L_0x023b
            r4 = 1
            int r5 = r2 >> 18
            r5 = r5 & 63
            byte r5 = r6[r5]
            r7[r1] = r5
            r1 = 2
            int r5 = r2 >> 12
            r5 = r5 & 63
            byte r5 = r6[r5]
            r7[r4] = r5
            r4 = 3
            int r5 = r2 >> 6
            r5 = r5 & 63
            byte r5 = r6[r5]
            r7[r1] = r5
            r1 = 4
            r2 = r2 & 63
            byte r2 = r6[r2]
            r7[r4] = r2
            int r0 = r0 + -1
            if (r0 != 0) goto L_0x023b
            boolean r0 = r11.f
            if (r0 == 0) goto L_0x023f
            r0 = 5
            r2 = 13
            r7[r1] = r2
        L_0x0042:
            int r1 = r0 + 1
            r2 = 10
            r7[r0] = r2
            r0 = 19
            r5 = r0
            r4 = r1
        L_0x004c:
            int r0 = r3 + 3
            if (r0 > r8) goto L_0x00f0
            byte r0 = r12[r3]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 16
            int r1 = r3 + 1
            byte r1 = r12[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << 8
            r0 = r0 | r1
            int r1 = r3 + 2
            byte r1 = r12[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            int r1 = r0 >> 18
            r1 = r1 & 63
            byte r1 = r6[r1]
            r7[r4] = r1
            int r1 = r4 + 1
            int r2 = r0 >> 12
            r2 = r2 & 63
            byte r2 = r6[r2]
            r7[r1] = r2
            int r1 = r4 + 2
            int r2 = r0 >> 6
            r2 = r2 & 63
            byte r2 = r6[r2]
            r7[r1] = r2
            int r1 = r4 + 3
            r0 = r0 & 63
            byte r0 = r6[r0]
            r7[r1] = r0
            int r3 = r3 + 3
            int r1 = r4 + 4
            int r0 = r5 + -1
            if (r0 != 0) goto L_0x023b
            boolean r0 = r11.f
            if (r0 == 0) goto L_0x0238
            int r0 = r1 + 1
            r2 = 13
            r7[r1] = r2
        L_0x009c:
            int r1 = r0 + 1
            r2 = 10
            r7[r0] = r2
            r0 = 19
            r5 = r0
            r4 = r1
            goto L_0x004c
        L_0x00a7:
            r3 = r13
            goto L_0x0010
        L_0x00aa:
            int r3 = r13 + 2
            if (r3 > r8) goto L_0x000f
            byte[] r2 = r11.j
            r3 = 0
            byte r2 = r2[r3]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 16
            int r3 = r13 + 1
            byte r4 = r12[r13]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 8
            r2 = r2 | r4
            int r13 = r3 + 1
            byte r3 = r12[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r2 = r2 | r3
            r3 = 0
            r11.c = r3
            r3 = r13
            goto L_0x0010
        L_0x00cd:
            int r3 = r13 + 1
            if (r3 > r8) goto L_0x000f
            byte[] r2 = r11.j
            r3 = 0
            byte r2 = r2[r3]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 16
            byte[] r3 = r11.j
            r4 = 1
            byte r3 = r3[r4]
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 8
            r2 = r2 | r3
            int r3 = r13 + 1
            byte r4 = r12[r13]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r2 = r2 | r4
            r4 = 0
            r11.c = r4
            goto L_0x0010
        L_0x00f0:
            if (r15 == 0) goto L_0x01fe
            int r0 = r11.c
            int r0 = r3 - r0
            int r1 = r8 + -1
            if (r0 != r1) goto L_0x015e
            r2 = 0
            int r0 = r11.c
            if (r0 <= 0) goto L_0x0156
            byte[] r0 = r11.j
            r1 = 1
            byte r0 = r0[r2]
            r2 = r3
        L_0x0105:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r3 = r0 << 4
            int r0 = r11.c
            int r0 = r0 - r1
            r11.c = r0
            int r1 = r4 + 1
            int r0 = r3 >> 6
            r0 = r0 & 63
            byte r0 = r6[r0]
            r7[r4] = r0
            int r0 = r1 + 1
            r3 = r3 & 63
            byte r3 = r6[r3]
            r7[r1] = r3
            boolean r1 = r11.d
            if (r1 == 0) goto L_0x0130
            int r1 = r0 + 1
            r3 = 61
            r7[r0] = r3
            int r0 = r1 + 1
            r3 = 61
            r7[r1] = r3
        L_0x0130:
            boolean r1 = r11.e
            if (r1 == 0) goto L_0x0146
            boolean r1 = r11.f
            if (r1 == 0) goto L_0x013f
            int r1 = r0 + 1
            r3 = 13
            r7[r0] = r3
            r0 = r1
        L_0x013f:
            int r1 = r0 + 1
            r3 = 10
            r7[r0] = r3
            r0 = r1
        L_0x0146:
            r3 = r2
            r4 = r0
        L_0x0148:
            boolean r0 = g
            if (r0 != 0) goto L_0x01f2
            int r0 = r11.c
            if (r0 == 0) goto L_0x01f2
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0156:
            int r1 = r3 + 1
            byte r0 = r12[r3]
            r10 = r2
            r2 = r1
            r1 = r10
            goto L_0x0105
        L_0x015e:
            int r0 = r11.c
            int r0 = r3 - r0
            int r1 = r8 + -2
            if (r0 != r1) goto L_0x01d6
            r2 = 0
            int r0 = r11.c
            r1 = 1
            if (r0 <= r1) goto L_0x01c9
            byte[] r0 = r11.j
            r1 = 1
            byte r0 = r0[r2]
        L_0x0171:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r9 = r0 << 10
            int r0 = r11.c
            if (r0 <= 0) goto L_0x01d0
            byte[] r0 = r11.j
            int r2 = r1 + 1
            byte r0 = r0[r1]
            r1 = r2
        L_0x0180:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 2
            r0 = r0 | r9
            int r2 = r11.c
            int r1 = r2 - r1
            r11.c = r1
            int r1 = r4 + 1
            int r2 = r0 >> 12
            r2 = r2 & 63
            byte r2 = r6[r2]
            r7[r4] = r2
            int r2 = r1 + 1
            int r4 = r0 >> 6
            r4 = r4 & 63
            byte r4 = r6[r4]
            r7[r1] = r4
            int r1 = r2 + 1
            r0 = r0 & 63
            byte r0 = r6[r0]
            r7[r2] = r0
            boolean r0 = r11.d
            if (r0 == 0) goto L_0x0235
            int r0 = r1 + 1
            r2 = 61
            r7[r1] = r2
        L_0x01b1:
            boolean r1 = r11.e
            if (r1 == 0) goto L_0x01c7
            boolean r1 = r11.f
            if (r1 == 0) goto L_0x01c0
            int r1 = r0 + 1
            r2 = 13
            r7[r0] = r2
            r0 = r1
        L_0x01c0:
            int r1 = r0 + 1
            r2 = 10
            r7[r0] = r2
            r0 = r1
        L_0x01c7:
            r4 = r0
            goto L_0x0148
        L_0x01c9:
            int r1 = r3 + 1
            byte r0 = r12[r3]
            r3 = r1
            r1 = r2
            goto L_0x0171
        L_0x01d0:
            int r2 = r3 + 1
            byte r0 = r12[r3]
            r3 = r2
            goto L_0x0180
        L_0x01d6:
            boolean r0 = r11.e
            if (r0 == 0) goto L_0x0148
            if (r4 <= 0) goto L_0x0148
            r0 = 19
            if (r5 == r0) goto L_0x0148
            boolean r0 = r11.f
            if (r0 == 0) goto L_0x0233
            int r0 = r4 + 1
            r1 = 13
            r7[r4] = r1
        L_0x01ea:
            int r4 = r0 + 1
            r1 = 10
            r7[r0] = r1
            goto L_0x0148
        L_0x01f2:
            boolean r0 = g
            if (r0 != 0) goto L_0x020e
            if (r3 == r8) goto L_0x020e
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x01fe:
            int r0 = r8 + -1
            if (r3 != r0) goto L_0x0214
            byte[] r0 = r11.j
            int r1 = r11.c
            int r2 = r1 + 1
            r11.c = r2
            byte r2 = r12[r3]
            r0[r1] = r2
        L_0x020e:
            r11.b = r4
            r11.k = r5
            r0 = 1
            return r0
        L_0x0214:
            int r0 = r8 + -2
            if (r3 != r0) goto L_0x020e
            byte[] r0 = r11.j
            int r1 = r11.c
            int r2 = r1 + 1
            r11.c = r2
            byte r2 = r12[r3]
            r0[r1] = r2
            byte[] r0 = r11.j
            int r1 = r11.c
            int r2 = r1 + 1
            r11.c = r2
            int r2 = r3 + 1
            byte r2 = r12[r2]
            r0[r1] = r2
            goto L_0x020e
        L_0x0233:
            r0 = r4
            goto L_0x01ea
        L_0x0235:
            r0 = r1
            goto L_0x01b1
        L_0x0238:
            r0 = r1
            goto L_0x009c
        L_0x023b:
            r5 = r0
            r4 = r1
            goto L_0x004c
        L_0x023f:
            r0 = r1
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.common.j.a(byte[], int, int, boolean):boolean");
    }
}
