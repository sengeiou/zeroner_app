package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;

public class URLCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder {
    protected static final byte ESCAPE_CHAR = 37;
    static final int RADIX = 16;
    protected static final BitSet WWW_FORM_URL = new BitSet(256);
    @Deprecated
    protected String charset;

    static {
        for (int i = 97; i <= 122; i++) {
            WWW_FORM_URL.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            WWW_FORM_URL.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            WWW_FORM_URL.set(i3);
        }
        WWW_FORM_URL.set(45);
        WWW_FORM_URL.set(95);
        WWW_FORM_URL.set(46);
        WWW_FORM_URL.set(42);
        WWW_FORM_URL.set(32);
    }

    public URLCodec() {
        this("UTF-8");
    }

    public URLCodec(String charset2) {
        this.charset = charset2;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [byte[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] encodeUrl(java.util.BitSet r10, byte[] r11) {
        /*
            r9 = 16
            if (r11 != 0) goto L_0x0006
            r8 = 0
        L_0x0005:
            return r8
        L_0x0006:
            if (r10 != 0) goto L_0x000a
            java.util.BitSet r10 = WWW_FORM_URL
        L_0x000a:
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            r0 = r11
            int r7 = r0.length
            r6 = 0
        L_0x0012:
            if (r6 >= r7) goto L_0x004f
            byte r3 = r0[r6]
            r1 = r3
            if (r1 >= 0) goto L_0x001b
            int r1 = r1 + 256
        L_0x001b:
            boolean r8 = r10.get(r1)
            if (r8 == 0) goto L_0x002d
            r8 = 32
            if (r1 != r8) goto L_0x0027
            r1 = 43
        L_0x0027:
            r2.write(r1)
        L_0x002a:
            int r6 = r6 + 1
            goto L_0x0012
        L_0x002d:
            r8 = 37
            r2.write(r8)
            int r8 = r1 >> 4
            r8 = r8 & 15
            char r8 = java.lang.Character.forDigit(r8, r9)
            char r4 = java.lang.Character.toUpperCase(r8)
            r8 = r1 & 15
            char r8 = java.lang.Character.forDigit(r8, r9)
            char r5 = java.lang.Character.toUpperCase(r8)
            r2.write(r4)
            r2.write(r5)
            goto L_0x002a
        L_0x004f:
            byte[] r8 = r2.toByteArray()
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.net.URLCodec.encodeUrl(java.util.BitSet, byte[]):byte[]");
    }

    public static final byte[] decodeUrl(byte[] bytes) throws DecoderException {
        if (bytes == null) {
            return null;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int i = 0;
        while (i < bytes.length) {
            byte b = bytes[i];
            if (b == 43) {
                buffer.write(32);
            } else if (b == 37) {
                int i2 = i + 1;
                try {
                    int u = Utils.digit16(bytes[i2]);
                    i = i2 + 1;
                    buffer.write((char) ((u << 4) + Utils.digit16(bytes[i])));
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DecoderException("Invalid URL encoding: ", e);
                }
            } else {
                buffer.write(b);
            }
            i++;
        }
        return buffer.toByteArray();
    }

    public byte[] encode(byte[] bytes) {
        return encodeUrl(WWW_FORM_URL, bytes);
    }

    public byte[] decode(byte[] bytes) throws DecoderException {
        return decodeUrl(bytes);
    }

    public String encode(String str, String charset2) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(encode(str.getBytes(charset2)));
    }

    public String encode(String str) throws EncoderException {
        if (str == null) {
            return null;
        }
        try {
            return encode(str, getDefaultCharset());
        } catch (UnsupportedEncodingException e) {
            throw new EncoderException(e.getMessage(), e);
        }
    }

    public String decode(String str, String charset2) throws DecoderException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return new String(decode(StringUtils.getBytesUsAscii(str)), charset2);
    }

    public String decode(String str) throws DecoderException {
        if (str == null) {
            return null;
        }
        try {
            return decode(str, getDefaultCharset());
        } catch (UnsupportedEncodingException e) {
            throw new DecoderException(e.getMessage(), e);
        }
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
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be URL encoded");
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
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be URL decoded");
    }

    public String getDefaultCharset() {
        return this.charset;
    }

    @Deprecated
    public String getEncoding() {
        return this.charset;
    }
}
