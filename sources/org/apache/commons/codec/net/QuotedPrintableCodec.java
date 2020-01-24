package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;

public class QuotedPrintableCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder {
    private static final byte CR = 13;
    private static final byte ESCAPE_CHAR = 61;
    private static final byte LF = 10;
    private static final BitSet PRINTABLE_CHARS = new BitSet(256);
    private static final int SAFE_LENGTH = 73;
    private static final byte SPACE = 32;
    private static final byte TAB = 9;
    private final Charset charset;
    private final boolean strict;

    static {
        for (int i = 33; i <= 60; i++) {
            PRINTABLE_CHARS.set(i);
        }
        for (int i2 = 62; i2 <= 126; i2++) {
            PRINTABLE_CHARS.set(i2);
        }
        PRINTABLE_CHARS.set(9);
        PRINTABLE_CHARS.set(32);
    }

    public QuotedPrintableCodec() {
        this(Charsets.UTF_8, false);
    }

    public QuotedPrintableCodec(boolean strict2) {
        this(Charsets.UTF_8, strict2);
    }

    public QuotedPrintableCodec(Charset charset2) {
        this(charset2, false);
    }

    public QuotedPrintableCodec(Charset charset2, boolean strict2) {
        this.charset = charset2;
        this.strict = strict2;
    }

    public QuotedPrintableCodec(String charsetName) throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException {
        this(Charset.forName(charsetName), false);
    }

    private static final int encodeQuotedPrintable(int b, ByteArrayOutputStream buffer) {
        buffer.write(61);
        char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 15, 16));
        char hex2 = Character.toUpperCase(Character.forDigit(b & 15, 16));
        buffer.write(hex1);
        buffer.write(hex2);
        return 3;
    }

    private static int getUnsignedOctet(int index, byte[] bytes) {
        byte b = bytes[index];
        if (b < 0) {
            return b + 256;
        }
        return b;
    }

    private static int encodeByte(int b, boolean encode, ByteArrayOutputStream buffer) {
        if (encode) {
            return encodeQuotedPrintable(b, buffer);
        }
        buffer.write(b);
        return 1;
    }

    private static boolean isWhitespace(int b) {
        return b == 32 || b == 9;
    }

    public static final byte[] encodeQuotedPrintable(BitSet printable, byte[] bytes) {
        return encodeQuotedPrintable(printable, bytes, false);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] encodeQuotedPrintable(java.util.BitSet r10, byte[] r11, boolean r12) {
        /*
            if (r11 != 0) goto L_0x0004
            r9 = 0
        L_0x0003:
            return r9
        L_0x0004:
            if (r10 != 0) goto L_0x0008
            java.util.BitSet r10 = PRINTABLE_CHARS
        L_0x0008:
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            if (r12 == 0) goto L_0x00a8
            r8 = 1
            r5 = 0
        L_0x0011:
            int r9 = r11.length
            int r9 = r9 + -3
            if (r5 >= r9) goto L_0x0052
            int r1 = getUnsignedOctet(r5, r11)
            r9 = 73
            if (r8 >= r9) goto L_0x002f
            boolean r9 = r10.get(r1)
            if (r9 != 0) goto L_0x002d
            r9 = 1
        L_0x0025:
            int r9 = encodeByte(r1, r9, r2)
            int r8 = r8 + r9
        L_0x002a:
            int r5 = r5 + 1
            goto L_0x0011
        L_0x002d:
            r9 = 0
            goto L_0x0025
        L_0x002f:
            boolean r9 = r10.get(r1)
            if (r9 == 0) goto L_0x003b
            boolean r9 = isWhitespace(r1)
            if (r9 == 0) goto L_0x0050
        L_0x003b:
            r9 = 1
        L_0x003c:
            encodeByte(r1, r9, r2)
            r9 = 61
            r2.write(r9)
            r9 = 13
            r2.write(r9)
            r9 = 10
            r2.write(r9)
            r8 = 1
            goto L_0x002a
        L_0x0050:
            r9 = 0
            goto L_0x003c
        L_0x0052:
            int r9 = r11.length
            int r9 = r9 + -3
            int r1 = getUnsignedOctet(r9, r11)
            boolean r9 = r10.get(r1)
            if (r9 == 0) goto L_0x0069
            boolean r9 = isWhitespace(r1)
            if (r9 == 0) goto L_0x00a4
            r9 = 68
            if (r8 <= r9) goto L_0x00a4
        L_0x0069:
            r4 = 1
        L_0x006a:
            int r9 = encodeByte(r1, r4, r2)
            int r8 = r8 + r9
            r9 = 71
            if (r8 <= r9) goto L_0x0082
            r9 = 61
            r2.write(r9)
            r9 = 13
            r2.write(r9)
            r9 = 10
            r2.write(r9)
        L_0x0082:
            int r9 = r11.length
            int r5 = r9 + -2
        L_0x0085:
            int r9 = r11.length
            if (r5 >= r9) goto L_0x00c4
            int r1 = getUnsignedOctet(r5, r11)
            boolean r9 = r10.get(r1)
            if (r9 == 0) goto L_0x009d
            int r9 = r11.length
            int r9 = r9 + -2
            if (r5 <= r9) goto L_0x00a6
            boolean r9 = isWhitespace(r1)
            if (r9 == 0) goto L_0x00a6
        L_0x009d:
            r4 = 1
        L_0x009e:
            encodeByte(r1, r4, r2)
            int r5 = r5 + 1
            goto L_0x0085
        L_0x00a4:
            r4 = 0
            goto L_0x006a
        L_0x00a6:
            r4 = 0
            goto L_0x009e
        L_0x00a8:
            r0 = r11
            int r7 = r0.length
            r6 = 0
        L_0x00ab:
            if (r6 >= r7) goto L_0x00c4
            byte r3 = r0[r6]
            r1 = r3
            if (r1 >= 0) goto L_0x00b4
            int r1 = r1 + 256
        L_0x00b4:
            boolean r9 = r10.get(r1)
            if (r9 == 0) goto L_0x00c0
            r2.write(r1)
        L_0x00bd:
            int r6 = r6 + 1
            goto L_0x00ab
        L_0x00c0:
            encodeQuotedPrintable(r1, r2)
            goto L_0x00bd
        L_0x00c4:
            byte[] r9 = r2.toByteArray()
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.net.QuotedPrintableCodec.encodeQuotedPrintable(java.util.BitSet, byte[], boolean):byte[]");
    }

    public static final byte[] decodeQuotedPrintable(byte[] bytes) throws DecoderException {
        if (bytes == null) {
            return null;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int i = 0;
        while (i < bytes.length) {
            byte b = bytes[i];
            if (b == 61) {
                i++;
                try {
                    if (bytes[i] != 13) {
                        int u = Utils.digit16(bytes[i]);
                        i++;
                        buffer.write((char) ((u << 4) + Utils.digit16(bytes[i])));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DecoderException("Invalid quoted-printable encoding", e);
                }
            } else if (!(b == 13 || b == 10)) {
                buffer.write(b);
            }
            i++;
        }
        return buffer.toByteArray();
    }

    public byte[] encode(byte[] bytes) {
        return encodeQuotedPrintable(PRINTABLE_CHARS, bytes, this.strict);
    }

    public byte[] decode(byte[] bytes) throws DecoderException {
        return decodeQuotedPrintable(bytes);
    }

    public String encode(String str) throws EncoderException {
        return encode(str, getCharset());
    }

    public String decode(String str, Charset charset2) throws DecoderException {
        if (str == null) {
            return null;
        }
        return new String(decode(StringUtils.getBytesUsAscii(str)), charset2);
    }

    public String decode(String str, String charset2) throws DecoderException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return new String(decode(StringUtils.getBytesUsAscii(str)), charset2);
    }

    public String decode(String str) throws DecoderException {
        return decode(str, getCharset());
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return encode((byte[]) (byte[]) obj);
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be quoted-printable encoded");
    }

    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return decode((byte[]) (byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be quoted-printable decoded");
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }

    public String encode(String str, Charset charset2) {
        if (str == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(encode(str.getBytes(charset2)));
    }

    public String encode(String str, String charset2) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(encode(str.getBytes(charset2)));
    }
}
