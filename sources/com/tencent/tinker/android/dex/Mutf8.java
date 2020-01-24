package com.tencent.tinker.android.dex;

import com.google.common.primitives.UnsignedBytes;
import com.tencent.tinker.android.dex.util.ByteInput;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.UTFDataFormatException;

public final class Mutf8 {
    private Mutf8() {
    }

    public static String decode(ByteInput in, char[] out) throws UTFDataFormatException {
        int s = 0;
        while (true) {
            char a = (char) (in.readByte() & UnsignedBytes.MAX_VALUE);
            if (a == 0) {
                return new String(out, 0, s);
            }
            out[s] = a;
            if (a < 128) {
                s++;
            } else if ((a & 224) == 192) {
                int b = in.readByte() & 255;
                if ((b & Opcodes.AND_LONG_2ADDR) != 128) {
                    throw new UTFDataFormatException("bad second byte");
                }
                int s2 = s + 1;
                out[s] = (char) (((a & 31) << 6) | (b & 63));
                s = s2;
            } else if ((a & 240) == 224) {
                int b2 = in.readByte() & 255;
                int c = in.readByte() & 255;
                if ((b2 & Opcodes.AND_LONG_2ADDR) == 128 && (c & Opcodes.AND_LONG_2ADDR) == 128) {
                    int s3 = s + 1;
                    out[s] = (char) (((a & 15) << 12) | ((b2 & 63) << 6) | (c & 63));
                    s = s3;
                }
            } else {
                throw new UTFDataFormatException("bad byte");
            }
        }
        throw new UTFDataFormatException("bad second or third byte");
    }

    public static long countBytes(String s, boolean shortLength) throws UTFDataFormatException {
        long result = 0;
        int length = s.length();
        int i = 0;
        while (i < length) {
            char ch = s.charAt(i);
            if (ch != 0 && ch <= 127) {
                result++;
            } else if (ch <= 2047) {
                result += 2;
            } else {
                result += 3;
            }
            if (!shortLength || result <= 65535) {
                i++;
            } else {
                throw new UTFDataFormatException("String more than 65535 UTF bytes long");
            }
        }
        return result;
    }

    public static void encode(byte[] dst, int offset, String s) {
        int offset2;
        int length = s.length();
        int i = 0;
        int offset3 = offset;
        while (i < length) {
            char ch = s.charAt(i);
            if (ch != 0 && ch <= 127) {
                offset2 = offset3 + 1;
                dst[offset3] = (byte) ch;
            } else if (ch <= 2047) {
                int offset4 = offset3 + 1;
                dst[offset3] = (byte) (((ch >> 6) & 31) | Opcodes.AND_LONG_2ADDR);
                int offset5 = offset4 + 1;
                dst[offset4] = (byte) ((ch & '?') | 128);
                offset2 = offset5;
            } else {
                int offset6 = offset3 + 1;
                dst[offset3] = (byte) (((ch >> 12) & 15) | Opcodes.SHL_INT_LIT8);
                int offset7 = offset6 + 1;
                dst[offset6] = (byte) (((ch >> 6) & 63) | 128);
                offset2 = offset7 + 1;
                dst[offset7] = (byte) ((ch & '?') | 128);
            }
            i++;
            offset3 = offset2;
        }
    }

    public static byte[] encode(String s) throws UTFDataFormatException {
        byte[] result = new byte[((int) countBytes(s, true))];
        encode(result, 0, s);
        return result;
    }
}
