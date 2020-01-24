package com.fasterxml.jackson.core.io;

import com.airbnb.lottie.utils.Utils;
import com.iwown.device_module.device_firmware_upgrade.FotaUtils;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.tencent.connect.common.Constants;

public final class NumberOutput {
    private static int BILLION = Utils.SECOND_IN_NANOS;
    static final char[] FULL_TRIPLETS = new char[ServiceErrorCode.UPLOAD_FILE_SO_BIG];
    static final byte[] FULL_TRIPLETS_B = new byte[ServiceErrorCode.UPLOAD_FILE_SO_BIG];
    static final char[] LEADING_TRIPLETS = new char[ServiceErrorCode.UPLOAD_FILE_SO_BIG];
    private static long MAX_INT_AS_LONG = 2147483647L;
    private static int MILLION = 1000000;
    private static long MIN_INT_AS_LONG = -2147483648L;
    private static final char NULL_CHAR = 0;
    static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
    private static long TEN_BILLION_L = 10000000000L;
    private static long THOUSAND_L = 1000;
    static final String[] sSmallIntStrs = {"0", "1", "2", "3", "4", "5", Constants.VIA_SHARE_TYPE_INFO, "7", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, "9", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ};
    static final String[] sSmallIntStrs2 = {"-1", "-2", "-3", "-4", "-5", "-6", "-7", FotaUtils.FOTA_VERSION_GET_FAILED, "-9", "-10"};

    static {
        char l1;
        char l2;
        int ix = 0;
        for (int i1 = 0; i1 < 10; i1++) {
            char f1 = (char) (i1 + 48);
            if (i1 == 0) {
                l1 = 0;
            } else {
                l1 = f1;
            }
            for (int i2 = 0; i2 < 10; i2++) {
                char f2 = (char) (i2 + 48);
                if (i1 == 0 && i2 == 0) {
                    l2 = 0;
                } else {
                    l2 = f2;
                }
                for (int i3 = 0; i3 < 10; i3++) {
                    char f3 = (char) (i3 + 48);
                    LEADING_TRIPLETS[ix] = l1;
                    LEADING_TRIPLETS[ix + 1] = l2;
                    LEADING_TRIPLETS[ix + 2] = f3;
                    FULL_TRIPLETS[ix] = f1;
                    FULL_TRIPLETS[ix + 1] = f2;
                    FULL_TRIPLETS[ix + 2] = f3;
                    ix += 4;
                }
            }
        }
        for (int i = 0; i < 4000; i++) {
            FULL_TRIPLETS_B[i] = (byte) FULL_TRIPLETS[i];
        }
    }

    public static int outputInt(int value, char[] buffer, int offset) {
        int offset2;
        int offset3;
        if (value < 0) {
            if (value == Integer.MIN_VALUE) {
                return outputLong((long) value, buffer, offset);
            }
            int offset4 = offset + 1;
            buffer[offset] = '-';
            value = -value;
            offset = offset4;
        }
        if (value < MILLION) {
            if (value >= 1000) {
                int thousands = value / 1000;
                offset3 = outputFullTriplet(value - (thousands * 1000), buffer, outputLeadingTriplet(thousands, buffer, offset));
            } else if (value < 10) {
                int offset5 = offset + 1;
                buffer[offset] = (char) (value + 48);
                offset3 = offset5;
            } else {
                offset3 = outputLeadingTriplet(value, buffer, offset);
            }
            return offset3;
        }
        boolean hasBillions = value >= BILLION;
        if (hasBillions) {
            value -= BILLION;
            if (value >= BILLION) {
                value -= BILLION;
                int offset6 = offset + 1;
                buffer[offset] = '2';
                offset = offset6;
            } else {
                int offset7 = offset + 1;
                buffer[offset] = '1';
                offset = offset7;
            }
        }
        int newValue = value / 1000;
        int ones = value - (newValue * 1000);
        int value2 = newValue;
        int newValue2 = newValue / 1000;
        int thousands2 = value2 - (newValue2 * 1000);
        if (hasBillions) {
            offset2 = outputFullTriplet(newValue2, buffer, offset);
        } else {
            offset2 = outputLeadingTriplet(newValue2, buffer, offset);
        }
        return outputFullTriplet(ones, buffer, outputFullTriplet(thousands2, buffer, offset2));
    }

    public static int outputInt(int value, byte[] buffer, int offset) {
        int offset2;
        int offset3;
        if (value < 0) {
            if (value == Integer.MIN_VALUE) {
                return outputLong((long) value, buffer, offset);
            }
            int offset4 = offset + 1;
            buffer[offset] = Framer.STDIN_FRAME_PREFIX;
            value = -value;
            offset = offset4;
        }
        if (value < MILLION) {
            if (value >= 1000) {
                int thousands = value / 1000;
                offset3 = outputFullTriplet(value - (thousands * 1000), buffer, outputLeadingTriplet(thousands, buffer, offset));
            } else if (value < 10) {
                int offset5 = offset + 1;
                buffer[offset] = (byte) (value + 48);
                offset3 = offset5;
            } else {
                offset3 = outputLeadingTriplet(value, buffer, offset);
            }
            return offset3;
        }
        boolean hasBillions = value >= BILLION;
        if (hasBillions) {
            value -= BILLION;
            if (value >= BILLION) {
                value -= BILLION;
                int offset6 = offset + 1;
                buffer[offset] = Framer.STDERR_FRAME_PREFIX;
                offset = offset6;
            } else {
                int offset7 = offset + 1;
                buffer[offset] = Framer.STDOUT_FRAME_PREFIX;
                offset = offset7;
            }
        }
        int newValue = value / 1000;
        int ones = value - (newValue * 1000);
        int value2 = newValue;
        int newValue2 = newValue / 1000;
        int thousands2 = value2 - (newValue2 * 1000);
        if (hasBillions) {
            offset2 = outputFullTriplet(newValue2, buffer, offset);
        } else {
            offset2 = outputLeadingTriplet(newValue2, buffer, offset);
        }
        return outputFullTriplet(ones, buffer, outputFullTriplet(thousands2, buffer, offset2));
    }

    public static int outputLong(long value, char[] buffer, int offset) {
        if (value < 0) {
            if (value > MIN_INT_AS_LONG) {
                return outputInt((int) value, buffer, offset);
            }
            if (value == Long.MIN_VALUE) {
                int len = SMALLEST_LONG.length();
                SMALLEST_LONG.getChars(0, len, buffer, offset);
                return offset + len;
            }
            int offset2 = offset + 1;
            buffer[offset] = '-';
            value = -value;
            offset = offset2;
        } else if (value <= MAX_INT_AS_LONG) {
            return outputInt((int) value, buffer, offset);
        }
        int origOffset = offset;
        int offset3 = offset + calcLongStrLength(value);
        int ptr = offset3;
        while (value > MAX_INT_AS_LONG) {
            ptr -= 3;
            long newValue = value / THOUSAND_L;
            outputFullTriplet((int) (value - (THOUSAND_L * newValue)), buffer, ptr);
            value = newValue;
        }
        int ivalue = (int) value;
        while (ivalue >= 1000) {
            ptr -= 3;
            int newValue2 = ivalue / 1000;
            outputFullTriplet(ivalue - (newValue2 * 1000), buffer, ptr);
            ivalue = newValue2;
        }
        outputLeadingTriplet(ivalue, buffer, origOffset);
        return offset3;
    }

    public static int outputLong(long value, byte[] buffer, int offset) {
        if (value < 0) {
            if (value > MIN_INT_AS_LONG) {
                return outputInt((int) value, buffer, offset);
            }
            if (value == Long.MIN_VALUE) {
                int len = SMALLEST_LONG.length();
                int i = 0;
                int offset2 = offset;
                while (i < len) {
                    int offset3 = offset2 + 1;
                    buffer[offset2] = (byte) SMALLEST_LONG.charAt(i);
                    i++;
                    offset2 = offset3;
                }
                int i2 = offset2;
                return offset2;
            }
            int offset4 = offset + 1;
            buffer[offset] = Framer.STDIN_FRAME_PREFIX;
            value = -value;
            offset = offset4;
        } else if (value <= MAX_INT_AS_LONG) {
            return outputInt((int) value, buffer, offset);
        }
        int origOffset = offset;
        int offset5 = offset + calcLongStrLength(value);
        int ptr = offset5;
        while (value > MAX_INT_AS_LONG) {
            ptr -= 3;
            long newValue = value / THOUSAND_L;
            outputFullTriplet((int) (value - (THOUSAND_L * newValue)), buffer, ptr);
            value = newValue;
        }
        int ivalue = (int) value;
        while (ivalue >= 1000) {
            ptr -= 3;
            int newValue2 = ivalue / 1000;
            outputFullTriplet(ivalue - (newValue2 * 1000), buffer, ptr);
            ivalue = newValue2;
        }
        outputLeadingTriplet(ivalue, buffer, origOffset);
        return offset5;
    }

    public static String toString(int value) {
        if (value < sSmallIntStrs.length) {
            if (value >= 0) {
                return sSmallIntStrs[value];
            }
            int v2 = (-value) - 1;
            if (v2 < sSmallIntStrs2.length) {
                return sSmallIntStrs2[v2];
            }
        }
        return Integer.toString(value);
    }

    public static String toString(long value) {
        if (value > 2147483647L || value < -2147483648L) {
            return Long.toString(value);
        }
        return toString((int) value);
    }

    public static String toString(double value) {
        return Double.toString(value);
    }

    private static int outputLeadingTriplet(int triplet, char[] buffer, int offset) {
        int digitOffset = triplet << 2;
        int digitOffset2 = digitOffset + 1;
        char c = LEADING_TRIPLETS[digitOffset];
        if (c != 0) {
            int offset2 = offset + 1;
            buffer[offset] = c;
            offset = offset2;
        }
        int digitOffset3 = digitOffset2 + 1;
        char c2 = LEADING_TRIPLETS[digitOffset2];
        if (c2 != 0) {
            int offset3 = offset + 1;
            buffer[offset] = c2;
            offset = offset3;
        }
        int offset4 = offset + 1;
        buffer[offset] = LEADING_TRIPLETS[digitOffset3];
        return offset4;
    }

    private static int outputLeadingTriplet(int triplet, byte[] buffer, int offset) {
        int digitOffset = triplet << 2;
        int digitOffset2 = digitOffset + 1;
        char c = LEADING_TRIPLETS[digitOffset];
        if (c != 0) {
            int offset2 = offset + 1;
            buffer[offset] = (byte) c;
            offset = offset2;
        }
        int digitOffset3 = digitOffset2 + 1;
        char c2 = LEADING_TRIPLETS[digitOffset2];
        if (c2 != 0) {
            int offset3 = offset + 1;
            buffer[offset] = (byte) c2;
            offset = offset3;
        }
        int offset4 = offset + 1;
        buffer[offset] = (byte) LEADING_TRIPLETS[digitOffset3];
        return offset4;
    }

    private static int outputFullTriplet(int triplet, char[] buffer, int offset) {
        int digitOffset = triplet << 2;
        int offset2 = offset + 1;
        int digitOffset2 = digitOffset + 1;
        buffer[offset] = FULL_TRIPLETS[digitOffset];
        int offset3 = offset2 + 1;
        int digitOffset3 = digitOffset2 + 1;
        buffer[offset2] = FULL_TRIPLETS[digitOffset2];
        int offset4 = offset3 + 1;
        buffer[offset3] = FULL_TRIPLETS[digitOffset3];
        return offset4;
    }

    private static int outputFullTriplet(int triplet, byte[] buffer, int offset) {
        int digitOffset = triplet << 2;
        int offset2 = offset + 1;
        int digitOffset2 = digitOffset + 1;
        buffer[offset] = FULL_TRIPLETS_B[digitOffset];
        int offset3 = offset2 + 1;
        int digitOffset3 = digitOffset2 + 1;
        buffer[offset2] = FULL_TRIPLETS_B[digitOffset2];
        int offset4 = offset3 + 1;
        buffer[offset3] = FULL_TRIPLETS_B[digitOffset3];
        return offset4;
    }

    private static int calcLongStrLength(long posValue) {
        int len = 10;
        for (long comp = TEN_BILLION_L; posValue >= comp && len != 19; comp = (comp << 3) + (comp << 1)) {
            len++;
        }
        return len;
    }
}