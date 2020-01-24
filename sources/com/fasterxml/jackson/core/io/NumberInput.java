package com.fasterxml.jackson.core.io;

import kotlin.jvm.internal.LongCompanionObject;

public final class NumberInput {
    static final long L_BILLION = 1000000000;
    static final String MAX_LONG_STR = String.valueOf(LongCompanionObject.MAX_VALUE);
    static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

    public static int parseInt(char[] digitChars, int offset, int len) {
        int num = digitChars[offset] - '0';
        int len2 = len + offset;
        int offset2 = offset + 1;
        if (offset2 >= len2) {
            return num;
        }
        int num2 = (num * 10) + (digitChars[offset2] - '0');
        int offset3 = offset2 + 1;
        if (offset3 >= len2) {
            return num2;
        }
        int num3 = (num2 * 10) + (digitChars[offset3] - '0');
        int offset4 = offset3 + 1;
        if (offset4 >= len2) {
            return num3;
        }
        int num4 = (num3 * 10) + (digitChars[offset4] - '0');
        int offset5 = offset4 + 1;
        if (offset5 >= len2) {
            return num4;
        }
        int num5 = (num4 * 10) + (digitChars[offset5] - '0');
        int offset6 = offset5 + 1;
        if (offset6 >= len2) {
            return num5;
        }
        int num6 = (num5 * 10) + (digitChars[offset6] - '0');
        int offset7 = offset6 + 1;
        if (offset7 >= len2) {
            return num6;
        }
        int num7 = (num6 * 10) + (digitChars[offset7] - '0');
        int offset8 = offset7 + 1;
        if (offset8 >= len2) {
            return num7;
        }
        int num8 = (num7 * 10) + (digitChars[offset8] - '0');
        int offset9 = offset8 + 1;
        if (offset9 < len2) {
            return (num8 * 10) + (digitChars[offset9] - '0');
        }
        return num8;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int parseInt(java.lang.String r10) {
        /*
            r6 = 1
            r2 = 0
            r9 = 57
            r8 = 48
            char r0 = r10.charAt(r2)
            int r1 = r10.length()
            r7 = 45
            if (r0 != r7) goto L_0x0013
            r2 = r6
        L_0x0013:
            r4 = 1
            if (r2 == 0) goto L_0x0031
            if (r1 == r6) goto L_0x001c
            r6 = 10
            if (r1 <= r6) goto L_0x0021
        L_0x001c:
            int r3 = java.lang.Integer.parseInt(r10)
        L_0x0020:
            return r3
        L_0x0021:
            int r5 = r4 + 1
            char r0 = r10.charAt(r4)
        L_0x0027:
            if (r0 > r9) goto L_0x002b
            if (r0 >= r8) goto L_0x003a
        L_0x002b:
            int r3 = java.lang.Integer.parseInt(r10)
            r4 = r5
            goto L_0x0020
        L_0x0031:
            r6 = 9
            if (r1 <= r6) goto L_0x008b
            int r3 = java.lang.Integer.parseInt(r10)
            goto L_0x0020
        L_0x003a:
            int r3 = r0 + -48
            if (r5 >= r1) goto L_0x0086
            int r4 = r5 + 1
            char r0 = r10.charAt(r5)
            if (r0 > r9) goto L_0x0048
            if (r0 >= r8) goto L_0x004d
        L_0x0048:
            int r3 = java.lang.Integer.parseInt(r10)
            goto L_0x0020
        L_0x004d:
            int r6 = r3 * 10
            int r7 = r0 + -48
            int r3 = r6 + r7
            if (r4 >= r1) goto L_0x0087
            int r5 = r4 + 1
            char r0 = r10.charAt(r4)
            if (r0 > r9) goto L_0x005f
            if (r0 >= r8) goto L_0x0065
        L_0x005f:
            int r3 = java.lang.Integer.parseInt(r10)
            r4 = r5
            goto L_0x0020
        L_0x0065:
            int r6 = r3 * 10
            int r7 = r0 + -48
            int r3 = r6 + r7
            if (r5 >= r1) goto L_0x0086
        L_0x006d:
            r4 = r5
            int r5 = r4 + 1
            char r0 = r10.charAt(r4)
            if (r0 > r9) goto L_0x0078
            if (r0 >= r8) goto L_0x007e
        L_0x0078:
            int r3 = java.lang.Integer.parseInt(r10)
            r4 = r5
            goto L_0x0020
        L_0x007e:
            int r6 = r3 * 10
            int r7 = r0 + -48
            int r3 = r6 + r7
            if (r5 < r1) goto L_0x006d
        L_0x0086:
            r4 = r5
        L_0x0087:
            if (r2 == 0) goto L_0x0020
            int r3 = -r3
            goto L_0x0020
        L_0x008b:
            r5 = r4
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.io.NumberInput.parseInt(java.lang.String):int");
    }

    public static long parseLong(char[] digitChars, int offset, int len) {
        int len1 = len - 9;
        return ((long) parseInt(digitChars, offset + len1, 9)) + (((long) parseInt(digitChars, offset, len1)) * L_BILLION);
    }

    public static long parseLong(String str) {
        if (str.length() <= 9) {
            return (long) parseInt(str);
        }
        return Long.parseLong(str);
    }

    public static boolean inLongRange(char[] digitChars, int offset, int len, boolean negative) {
        String cmpStr = negative ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int cmpLen = cmpStr.length();
        if (len < cmpLen) {
            return true;
        }
        if (len > cmpLen) {
            return false;
        }
        int i = 0;
        while (i < cmpLen) {
            int diff = digitChars[offset + i] - cmpStr.charAt(i);
            if (diff == 0) {
                i++;
            } else if (diff >= 0) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public static boolean inLongRange(String numberStr, boolean negative) {
        String cmpStr = negative ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int cmpLen = cmpStr.length();
        int actualLen = numberStr.length();
        if (actualLen < cmpLen) {
            return true;
        }
        if (actualLen > cmpLen) {
            return false;
        }
        int i = 0;
        while (i < cmpLen) {
            int diff = numberStr.charAt(i) - cmpStr.charAt(i);
            if (diff == 0) {
                i++;
            } else if (diff >= 0) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public static int parseAsInt(String input, int defaultValue) {
        if (input == null) {
            return defaultValue;
        }
        String input2 = input.trim();
        int len = input2.length();
        if (len == 0) {
            return defaultValue;
        }
        int i = 0;
        if (0 < len) {
            char c = input2.charAt(0);
            if (c == '+') {
                input2 = input2.substring(1);
                len = input2.length();
            } else if (c == '-') {
                i = 0 + 1;
            }
        }
        while (i < len) {
            char c2 = input2.charAt(i);
            if (c2 > '9' || c2 < '0') {
                try {
                    return (int) parseDouble(input2);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            } else {
                i++;
            }
        }
        try {
            return Integer.parseInt(input2);
        } catch (NumberFormatException e2) {
            return defaultValue;
        }
    }

    public static long parseAsLong(String input, long defaultValue) {
        if (input == null) {
            return defaultValue;
        }
        String input2 = input.trim();
        int len = input2.length();
        if (len == 0) {
            return defaultValue;
        }
        int i = 0;
        if (0 < len) {
            char c = input2.charAt(0);
            if (c == '+') {
                input2 = input2.substring(1);
                len = input2.length();
            } else if (c == '-') {
                i = 0 + 1;
            }
        }
        while (i < len) {
            char c2 = input2.charAt(i);
            if (c2 > '9' || c2 < '0') {
                try {
                    return (long) parseDouble(input2);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            } else {
                i++;
            }
        }
        try {
            return Long.parseLong(input2);
        } catch (NumberFormatException e2) {
            return defaultValue;
        }
    }

    public static double parseAsDouble(String input, double defaultValue) {
        if (input == null) {
            return defaultValue;
        }
        String input2 = input.trim();
        if (input2.length() == 0) {
            return defaultValue;
        }
        try {
            return parseDouble(input2);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double parseDouble(String numStr) throws NumberFormatException {
        if (NASTY_SMALL_DOUBLE.equals(numStr)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(numStr);
    }
}
