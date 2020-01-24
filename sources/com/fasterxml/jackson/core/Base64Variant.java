package com.fasterxml.jackson.core;

import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.util.Arrays;
import kotlin.text.Typography;

public final class Base64Variant implements Serializable {
    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    static final char PADDING_CHAR_NONE = '\u0000';
    private static final long serialVersionUID = 1;
    private final transient int[] _asciiToBase64;
    private final transient byte[] _base64ToAsciiB;
    private final transient char[] _base64ToAsciiC;
    protected final transient int _maxLineLength;
    protected final String _name;
    protected final transient char _paddingChar;
    protected final transient boolean _usesPadding;

    public Base64Variant(String name, String base64Alphabet, boolean usesPadding, char paddingChar, int maxLineLength) {
        this._asciiToBase64 = new int[128];
        this._base64ToAsciiC = new char[64];
        this._base64ToAsciiB = new byte[64];
        this._name = name;
        this._usesPadding = usesPadding;
        this._paddingChar = paddingChar;
        this._maxLineLength = maxLineLength;
        int alphaLen = base64Alphabet.length();
        if (alphaLen != 64) {
            throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + alphaLen + ")");
        }
        base64Alphabet.getChars(0, alphaLen, this._base64ToAsciiC, 0);
        Arrays.fill(this._asciiToBase64, -1);
        for (int i = 0; i < alphaLen; i++) {
            char alpha = this._base64ToAsciiC[i];
            this._base64ToAsciiB[i] = (byte) alpha;
            this._asciiToBase64[alpha] = i;
        }
        if (usesPadding) {
            this._asciiToBase64[paddingChar] = -2;
        }
    }

    public Base64Variant(Base64Variant base, String name, int maxLineLength) {
        this(base, name, base._usesPadding, base._paddingChar, maxLineLength);
    }

    public Base64Variant(Base64Variant base, String name, boolean usesPadding, char paddingChar, int maxLineLength) {
        this._asciiToBase64 = new int[128];
        this._base64ToAsciiC = new char[64];
        this._base64ToAsciiB = new byte[64];
        this._name = name;
        byte[] srcB = base._base64ToAsciiB;
        System.arraycopy(srcB, 0, this._base64ToAsciiB, 0, srcB.length);
        char[] srcC = base._base64ToAsciiC;
        System.arraycopy(srcC, 0, this._base64ToAsciiC, 0, srcC.length);
        int[] srcV = base._asciiToBase64;
        System.arraycopy(srcV, 0, this._asciiToBase64, 0, srcV.length);
        this._usesPadding = usesPadding;
        this._paddingChar = paddingChar;
        this._maxLineLength = maxLineLength;
    }

    /* access modifiers changed from: protected */
    public Object readResolve() {
        return Base64Variants.valueOf(this._name);
    }

    public String getName() {
        return this._name;
    }

    public boolean usesPadding() {
        return this._usesPadding;
    }

    public boolean usesPaddingChar(char c) {
        return c == this._paddingChar;
    }

    public boolean usesPaddingChar(int ch) {
        return ch == this._paddingChar;
    }

    public char getPaddingChar() {
        return this._paddingChar;
    }

    public byte getPaddingByte() {
        return (byte) this._paddingChar;
    }

    public int getMaxLineLength() {
        return this._maxLineLength;
    }

    public int decodeBase64Char(char c) {
        int ch = c;
        if (ch <= 127) {
            return this._asciiToBase64[ch];
        }
        return -1;
    }

    public int decodeBase64Char(int ch) {
        if (ch <= 127) {
            return this._asciiToBase64[ch];
        }
        return -1;
    }

    public int decodeBase64Byte(byte b) {
        int ch = b;
        if (ch <= 127) {
            return this._asciiToBase64[ch];
        }
        return -1;
    }

    public char encodeBase64BitsAsChar(int value) {
        return this._base64ToAsciiC[value];
    }

    public int encodeBase64Chunk(int b24, char[] buffer, int ptr) {
        int ptr2 = ptr + 1;
        buffer[ptr] = this._base64ToAsciiC[(b24 >> 18) & 63];
        int ptr3 = ptr2 + 1;
        buffer[ptr2] = this._base64ToAsciiC[(b24 >> 12) & 63];
        int ptr4 = ptr3 + 1;
        buffer[ptr3] = this._base64ToAsciiC[(b24 >> 6) & 63];
        int ptr5 = ptr4 + 1;
        buffer[ptr4] = this._base64ToAsciiC[b24 & 63];
        return ptr5;
    }

    public void encodeBase64Chunk(StringBuilder sb, int b24) {
        sb.append(this._base64ToAsciiC[(b24 >> 18) & 63]);
        sb.append(this._base64ToAsciiC[(b24 >> 12) & 63]);
        sb.append(this._base64ToAsciiC[(b24 >> 6) & 63]);
        sb.append(this._base64ToAsciiC[b24 & 63]);
    }

    public int encodeBase64Partial(int bits, int outputBytes, char[] buffer, int outPtr) {
        int outPtr2 = outPtr + 1;
        buffer[outPtr] = this._base64ToAsciiC[(bits >> 18) & 63];
        int outPtr3 = outPtr2 + 1;
        buffer[outPtr2] = this._base64ToAsciiC[(bits >> 12) & 63];
        if (this._usesPadding) {
            int outPtr4 = outPtr3 + 1;
            buffer[outPtr3] = outputBytes == 2 ? this._base64ToAsciiC[(bits >> 6) & 63] : this._paddingChar;
            int outPtr5 = outPtr4 + 1;
            buffer[outPtr4] = this._paddingChar;
            return outPtr5;
        } else if (outputBytes != 2) {
            return outPtr3;
        } else {
            int outPtr6 = outPtr3 + 1;
            buffer[outPtr3] = this._base64ToAsciiC[(bits >> 6) & 63];
            return outPtr6;
        }
    }

    public void encodeBase64Partial(StringBuilder sb, int bits, int outputBytes) {
        sb.append(this._base64ToAsciiC[(bits >> 18) & 63]);
        sb.append(this._base64ToAsciiC[(bits >> 12) & 63]);
        if (this._usesPadding) {
            sb.append(outputBytes == 2 ? this._base64ToAsciiC[(bits >> 6) & 63] : this._paddingChar);
            sb.append(this._paddingChar);
        } else if (outputBytes == 2) {
            sb.append(this._base64ToAsciiC[(bits >> 6) & 63]);
        }
    }

    public byte encodeBase64BitsAsByte(int value) {
        return this._base64ToAsciiB[value];
    }

    public int encodeBase64Chunk(int b24, byte[] buffer, int ptr) {
        int ptr2 = ptr + 1;
        buffer[ptr] = this._base64ToAsciiB[(b24 >> 18) & 63];
        int ptr3 = ptr2 + 1;
        buffer[ptr2] = this._base64ToAsciiB[(b24 >> 12) & 63];
        int ptr4 = ptr3 + 1;
        buffer[ptr3] = this._base64ToAsciiB[(b24 >> 6) & 63];
        int ptr5 = ptr4 + 1;
        buffer[ptr4] = this._base64ToAsciiB[b24 & 63];
        return ptr5;
    }

    public int encodeBase64Partial(int bits, int outputBytes, byte[] buffer, int outPtr) {
        byte b;
        int outPtr2 = outPtr + 1;
        buffer[outPtr] = this._base64ToAsciiB[(bits >> 18) & 63];
        int outPtr3 = outPtr2 + 1;
        buffer[outPtr2] = this._base64ToAsciiB[(bits >> 12) & 63];
        if (this._usesPadding) {
            byte pb = (byte) this._paddingChar;
            int outPtr4 = outPtr3 + 1;
            if (outputBytes == 2) {
                b = this._base64ToAsciiB[(bits >> 6) & 63];
            } else {
                b = pb;
            }
            buffer[outPtr3] = b;
            int outPtr5 = outPtr4 + 1;
            buffer[outPtr4] = pb;
            return outPtr5;
        } else if (outputBytes != 2) {
            return outPtr3;
        } else {
            int outPtr6 = outPtr3 + 1;
            buffer[outPtr3] = this._base64ToAsciiB[(bits >> 6) & 63];
            return outPtr6;
        }
    }

    public String encode(byte[] input) {
        return encode(input, false);
    }

    public String encode(byte[] input, boolean addQuotes) {
        int inputEnd = input.length;
        StringBuilder sb = new StringBuilder((inputEnd >> 2) + inputEnd + (inputEnd >> 3));
        if (addQuotes) {
            sb.append(Typography.quote);
        }
        int chunksBeforeLF = getMaxLineLength() >> 2;
        int safeInputEnd = inputEnd - 3;
        int inputPtr = 0;
        while (inputPtr <= safeInputEnd) {
            int inputPtr2 = inputPtr + 1;
            int inputPtr3 = inputPtr2 + 1;
            int inputPtr4 = inputPtr3 + 1;
            encodeBase64Chunk(sb, (((input[inputPtr] << 8) | (input[inputPtr2] & 255)) << 8) | (input[inputPtr3] & 255));
            chunksBeforeLF--;
            if (chunksBeforeLF <= 0) {
                sb.append('\\');
                sb.append('n');
                chunksBeforeLF = getMaxLineLength() >> 2;
            }
            inputPtr = inputPtr4;
        }
        int inputLeft = inputEnd - inputPtr;
        if (inputLeft > 0) {
            int inputPtr5 = inputPtr + 1;
            int b24 = input[inputPtr] << 16;
            if (inputLeft == 2) {
                b24 |= (input[inputPtr5] & UnsignedBytes.MAX_VALUE) << 8;
                int i = inputPtr5 + 1;
            }
            encodeBase64Partial(sb, b24, inputLeft);
        }
        if (addQuotes) {
            sb.append(Typography.quote);
        }
        return sb.toString();
    }

    public String toString() {
        return this._name;
    }
}
