package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import kotlin.text.Typography;

final class SimpleToken extends Token {
    private final short bitCount;
    private final short value;

    SimpleToken(Token previous, int value2, int bitCount2) {
        super(previous);
        this.value = (short) value2;
        this.bitCount = (short) bitCount2;
    }

    /* access modifiers changed from: 0000 */
    public void appendTo(BitArray bitArray, byte[] text) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public String toString() {
        return Typography.less + Integer.toBinaryString((1 << this.bitCount) | (this.value & ((1 << this.bitCount) - 1)) | (1 << this.bitCount)).substring(1) + Typography.greater;
    }
}
