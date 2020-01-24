package com.tencent.tinker.ziputils.ziputil;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteOrder;

public final class Memory {
    private Memory() {
    }

    public static int peekInt(byte[] src, int offset, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int offset2 = offset + 1;
            int offset3 = offset2 + 1;
            int offset4 = offset3 + 1;
            int i = ((src[offset] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((src[offset2] & UnsignedBytes.MAX_VALUE) << 16) | ((src[offset3] & UnsignedBytes.MAX_VALUE) << 8) | ((src[offset4] & UnsignedBytes.MAX_VALUE) << 0);
            int i2 = offset4;
            return i;
        }
        int offset5 = offset + 1;
        int offset6 = offset5 + 1;
        int offset7 = offset6 + 1;
        int i3 = ((src[offset] & UnsignedBytes.MAX_VALUE) << 0) | ((src[offset5] & UnsignedBytes.MAX_VALUE) << 8) | ((src[offset6] & UnsignedBytes.MAX_VALUE) << 16) | ((src[offset7] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
        int i4 = offset7;
        return i3;
    }

    public static long peekLong(byte[] src, int offset, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int offset2 = offset + 1;
            int offset3 = offset2 + 1;
            int offset4 = offset3 + 1;
            int offset5 = offset4 + 1;
            int h = ((src[offset] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((src[offset2] & UnsignedBytes.MAX_VALUE) << 16) | ((src[offset3] & UnsignedBytes.MAX_VALUE) << 8) | ((src[offset4] & UnsignedBytes.MAX_VALUE) << 0);
            int offset6 = offset5 + 1;
            int offset7 = offset6 + 1;
            int offset8 = offset7 + 1;
            int l = ((src[offset5] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((src[offset6] & UnsignedBytes.MAX_VALUE) << 16) | ((src[offset7] & UnsignedBytes.MAX_VALUE) << 8) | ((src[offset8] & UnsignedBytes.MAX_VALUE) << 0);
            int i = offset8;
            return (((long) h) << 32) | (((long) l) & 4294967295L);
        }
        int offset9 = offset + 1;
        int offset10 = offset9 + 1;
        int offset11 = offset10 + 1;
        int offset12 = offset11 + 1;
        int l2 = ((src[offset] & UnsignedBytes.MAX_VALUE) << 0) | ((src[offset9] & UnsignedBytes.MAX_VALUE) << 8) | ((src[offset10] & UnsignedBytes.MAX_VALUE) << 16) | ((src[offset11] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
        int offset13 = offset12 + 1;
        int offset14 = offset13 + 1;
        int offset15 = offset14 + 1;
        long j = (((long) (((((src[offset12] & UnsignedBytes.MAX_VALUE) << 0) | ((src[offset13] & UnsignedBytes.MAX_VALUE) << 8)) | ((src[offset14] & UnsignedBytes.MAX_VALUE) << 16)) | ((src[offset15] & UnsignedBytes.MAX_VALUE) << Ascii.CAN))) << 32) | (((long) l2) & 4294967295L);
        int i2 = offset15;
        return j;
    }

    public static short peekShort(byte[] src, int offset, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            return (short) ((src[offset] << 8) | (src[offset + 1] & UnsignedBytes.MAX_VALUE));
        }
        return (short) ((src[offset + 1] << 8) | (src[offset] & UnsignedBytes.MAX_VALUE));
    }

    public static void pokeInt(byte[] dst, int offset, int value, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int offset2 = offset + 1;
            dst[offset] = (byte) ((value >> 24) & 255);
            int offset3 = offset2 + 1;
            dst[offset2] = (byte) ((value >> 16) & 255);
            int offset4 = offset3 + 1;
            dst[offset3] = (byte) ((value >> 8) & 255);
            dst[offset4] = (byte) ((value >> 0) & 255);
            int i = offset4;
            return;
        }
        int offset5 = offset + 1;
        dst[offset] = (byte) ((value >> 0) & 255);
        int offset6 = offset5 + 1;
        dst[offset5] = (byte) ((value >> 8) & 255);
        int offset7 = offset6 + 1;
        dst[offset6] = (byte) ((value >> 16) & 255);
        dst[offset7] = (byte) ((value >> 24) & 255);
        int i2 = offset7;
    }

    public static void pokeLong(byte[] dst, int offset, long value, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int i = (int) (value >> 32);
            int offset2 = offset + 1;
            dst[offset] = (byte) ((i >> 24) & 255);
            int offset3 = offset2 + 1;
            dst[offset2] = (byte) ((i >> 16) & 255);
            int offset4 = offset3 + 1;
            dst[offset3] = (byte) ((i >> 8) & 255);
            int offset5 = offset4 + 1;
            dst[offset4] = (byte) ((i >> 0) & 255);
            int i2 = (int) value;
            int offset6 = offset5 + 1;
            dst[offset5] = (byte) ((i2 >> 24) & 255);
            int offset7 = offset6 + 1;
            dst[offset6] = (byte) ((i2 >> 16) & 255);
            int offset8 = offset7 + 1;
            dst[offset7] = (byte) ((i2 >> 8) & 255);
            dst[offset8] = (byte) ((i2 >> 0) & 255);
            int i3 = offset8;
            return;
        }
        int i4 = (int) value;
        int offset9 = offset + 1;
        dst[offset] = (byte) ((i4 >> 0) & 255);
        int offset10 = offset9 + 1;
        dst[offset9] = (byte) ((i4 >> 8) & 255);
        int offset11 = offset10 + 1;
        dst[offset10] = (byte) ((i4 >> 16) & 255);
        int offset12 = offset11 + 1;
        dst[offset11] = (byte) ((i4 >> 24) & 255);
        int i5 = (int) (value >> 32);
        int offset13 = offset12 + 1;
        dst[offset12] = (byte) ((i5 >> 0) & 255);
        int offset14 = offset13 + 1;
        dst[offset13] = (byte) ((i5 >> 8) & 255);
        int offset15 = offset14 + 1;
        dst[offset14] = (byte) ((i5 >> 16) & 255);
        dst[offset15] = (byte) ((i5 >> 24) & 255);
        int i6 = offset15;
    }

    public static void pokeShort(byte[] dst, int offset, short value, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int offset2 = offset + 1;
            dst[offset] = (byte) ((value >> 8) & 255);
            dst[offset2] = (byte) ((value >> 0) & 255);
            int i = offset2;
            return;
        }
        int offset3 = offset + 1;
        dst[offset] = (byte) ((value >> 0) & 255);
        dst[offset3] = (byte) ((value >> 8) & 255);
        int i2 = offset3;
    }
}
