package com.facebook.stetho.websocket;

class MaskingHelper {
    MaskingHelper() {
    }

    public static void unmask(byte[] key, byte[] data, int offset, int count) {
        int index = 0;
        while (true) {
            int index2 = index;
            int count2 = count;
            int offset2 = offset;
            count = count2 - 1;
            if (count2 > 0) {
                offset = offset2 + 1;
                index = index2 + 1;
                data[offset2] = (byte) (data[offset2] ^ key[index2 % key.length]);
            } else {
                return;
            }
        }
    }
}
