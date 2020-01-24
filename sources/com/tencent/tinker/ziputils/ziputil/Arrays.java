package com.tencent.tinker.ziputils.ziputil;

public class Arrays {
    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
    }
}
