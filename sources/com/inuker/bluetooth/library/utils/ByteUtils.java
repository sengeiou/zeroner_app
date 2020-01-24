package com.inuker.bluetooth.library.utils;

import com.google.common.primitives.UnsignedBytes;
import java.util.Arrays;

public class ByteUtils {
    public static final int BYTE_MAX = 255;
    public static final byte[] EMPTY_BYTES = new byte[0];

    public static byte[] getNonEmptyByte(byte[] bytes) {
        return bytes != null ? bytes : EMPTY_BYTES;
    }

    public static String byteToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (!isEmpty(bytes)) {
            for (byte valueOf : bytes) {
                sb.append(String.format("%02X", new Object[]{Byte.valueOf(valueOf)}));
            }
        }
        return sb.toString();
    }

    public static byte[] trimLast(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            i--;
        }
        return Arrays.copyOfRange(bytes, 0, i + 1);
    }

    public static byte[] stringToBytes(String text) {
        int len = text.length();
        byte[] bytes = new byte[((len + 1) / 2)];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(text.substring(i, i + Math.min(2, len - i)), 16);
        }
        return bytes;
    }

    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    public static byte[] fromInt(int n) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) (n >>> (i * 8));
        }
        return bytes;
    }

    public static boolean byteEquals(byte[] lbytes, byte[] rbytes) {
        if (lbytes == null && rbytes == null) {
            return true;
        }
        if (lbytes == null || rbytes == null) {
            return false;
        }
        int llen = lbytes.length;
        if (llen != rbytes.length) {
            return false;
        }
        for (int i = 0; i < llen; i++) {
            if (lbytes[i] != rbytes[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] fillBeforeBytes(byte[] bytes, int len, byte fill) {
        byte[] result = bytes;
        int oldLen = bytes != null ? bytes.length : 0;
        if (oldLen < len) {
            result = new byte[len];
            int i = len - 1;
            int j = oldLen - 1;
            while (i >= 0) {
                if (j >= 0) {
                    result[i] = bytes[j];
                } else {
                    result[i] = fill;
                }
                i--;
                j--;
            }
        }
        return result;
    }

    public static byte[] cutBeforeBytes(byte[] bytes, byte cut) {
        if (isEmpty(bytes)) {
            return bytes;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != cut) {
                return Arrays.copyOfRange(bytes, i, bytes.length);
            }
        }
        return EMPTY_BYTES;
    }

    public static byte[] cutAfterBytes(byte[] bytes, byte cut) {
        if (isEmpty(bytes)) {
            return bytes;
        }
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] != cut) {
                return Arrays.copyOfRange(bytes, 0, i + 1);
            }
        }
        return EMPTY_BYTES;
    }

    public static byte[] getBytes(byte[] bytes, int start, int end) {
        byte[] newBytes = null;
        if (bytes != null && start >= 0 && start < bytes.length && end >= 0 && end < bytes.length && start <= end) {
            newBytes = new byte[((end - start) + 1)];
            for (int i = start; i <= end; i++) {
                newBytes[i - start] = bytes[i];
            }
        }
        return newBytes;
    }

    public static int ubyteToInt(byte b) {
        return b & UnsignedBytes.MAX_VALUE;
    }

    public static boolean isAllFF(byte[] bytes) {
        int len;
        if (bytes != null) {
            len = bytes.length;
        } else {
            len = 0;
        }
        for (int i = 0; i < len; i++) {
            if (ubyteToInt(bytes[i]) != 255) {
                return false;
            }
        }
        return true;
    }

    public static byte[] fromLong(long n) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) ((int) (n >>> (i * 8)));
        }
        return bytes;
    }

    public static void copy(byte[] lbytes, byte[] rbytes, int lstart, int rstart) {
        if (lbytes != null && rbytes != null && lstart >= 0) {
            int i = lstart;
            for (int j = rstart; j < rbytes.length && i < lbytes.length; j++) {
                lbytes[i] = rbytes[j];
                i++;
            }
        }
    }
}
