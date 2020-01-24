package com.hiflying.commons.utils;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class ByteTool {
    public static byte[] copyOfRange(byte[] data, int start, int end) {
        if (data == null || start > end || data.length < end) {
            return null;
        }
        byte[] dest = new byte[(end - start)];
        for (int i = 0; i < dest.length; i++) {
            dest[i] = data[start + i];
        }
        return dest;
    }

    public static String bytes2HexString(byte[] data) {
        String ret = "";
        for (byte b : data) {
            String hex = Integer.toHexString(b & UnsignedBytes.MAX_VALUE);
            if (hex.length() == 1) {
                hex = new StringBuilder(String.valueOf('0')).append(hex).toString();
            }
            ret = new StringBuilder(String.valueOf(ret)).append(hex.toUpperCase()).toString();
        }
        return ret;
    }

    public static String Byte2StringWithSpace(byte[] data) {
        String ret = "";
        for (byte b : data) {
            String hex = Integer.toHexString(b & UnsignedBytes.MAX_VALUE);
            if (hex.length() == 1) {
                hex = new StringBuilder(String.valueOf('0')).append(hex).toString();
            }
            ret = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(ret)).append(hex.toUpperCase()).toString())).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
        }
        return ret;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static int Byte2Int(byte[] bytes) {
        int s = 0;
        for (int i = 0; i < bytes.length; i++) {
            s |= (bytes[i] & UnsignedBytes.MAX_VALUE) << (((bytes.length - i) - 1) * 8);
        }
        return s;
    }

    public static byte[] Int2Byte(int x) {
        byte[] b = new byte[2];
        b[1] = (byte) (x & 255);
        b[0] = (byte) ((65280 & x) >> 8);
        return b;
    }

    public static void sleep(int times) {
        try {
            Thread.sleep((long) (times * 1000));
        } catch (InterruptedException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
