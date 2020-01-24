package no.nordicsemi.android.dialog.data;

public class Uuid {
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<java.util.UUID> parseFromAdvertisementData(byte[] r13) {
        /*
            r12 = 2
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.nio.ByteBuffer r8 = java.nio.ByteBuffer.wrap(r13)
            java.nio.ByteOrder r9 = java.nio.ByteOrder.LITTLE_ENDIAN
            java.nio.ByteBuffer r0 = r8.order(r9)
        L_0x0010:
            int r8 = r0.remaining()
            if (r8 <= r12) goto L_0x001c
            byte r1 = r0.get()
            if (r1 != 0) goto L_0x001d
        L_0x001c:
            return r7
        L_0x001d:
            byte r6 = r0.get()
            switch(r6) {
                case 2: goto L_0x002f;
                case 3: goto L_0x002f;
                case 4: goto L_0x0024;
                case 5: goto L_0x0024;
                case 6: goto L_0x0051;
                case 7: goto L_0x0051;
                default: goto L_0x0024;
            }
        L_0x0024:
            int r8 = r0.position()
            int r8 = r8 + r1
            int r8 = r8 + -1
            r0.position(r8)
            goto L_0x0010
        L_0x002f:
            if (r1 < r12) goto L_0x0010
            java.lang.String r8 = "%08x-0000-1000-8000-00805f9b34fb"
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r10 = 0
            short r11 = r0.getShort()
            java.lang.Short r11 = java.lang.Short.valueOf(r11)
            r9[r10] = r11
            java.lang.String r8 = java.lang.String.format(r8, r9)
            java.util.UUID r8 = java.util.UUID.fromString(r8)
            r7.add(r8)
            int r8 = r1 + -2
            byte r1 = (byte) r8
            goto L_0x002f
        L_0x0051:
            r8 = 16
            if (r1 < r8) goto L_0x0010
            long r2 = r0.getLong()
            long r4 = r0.getLong()
            java.util.UUID r8 = new java.util.UUID
            r8.<init>(r4, r2)
            r7.add(r8)
            int r8 = r1 + -16
            byte r1 = (byte) r8
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dialog.data.Uuid.parseFromAdvertisementData(byte[]):java.util.List");
    }
}
