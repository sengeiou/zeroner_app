package com.iwown.ble_module.iwown.utils;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

public class HexUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        if (data == null) {
            return null;
        }
        int l = data.length;
        char[] out = new char[(l << 1)];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int j2 = j + 1;
            out[j] = toDigits[(data[i] & 240) >>> 4];
            j = j2 + 1;
            out[j2] = toDigits[data[i] & Ascii.SI];
        }
        return out;
    }

    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    public static String formatHexString(byte[] data) {
        return formatHexString(data, false);
    }

    public static String formatHexString(byte[] data, boolean addSpace) {
        if (data == null || data.length < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & UnsignedBytes.MAX_VALUE);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
            if (addSpace) {
                sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        return sb.toString().trim();
    }

    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] out = new byte[(len >> 1)];
        int i = 0;
        int j = 0;
        while (j < len) {
            int j2 = j + 1;
            j = j2 + 1;
            out[i] = (byte) (((toDigit(data[j], j) << 4) | toDigit(data[j2], j2)) & 255);
            i++;
        }
        return out;
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit != -1) {
            return digit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
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

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String extractData(byte[] data, int position) {
        return formatHexString(new byte[]{data[position]});
    }

    public static byte int2byte(int integer) {
        return (byte) (integer & 255);
    }
}
