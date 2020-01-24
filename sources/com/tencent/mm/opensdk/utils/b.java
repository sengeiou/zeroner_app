package com.tencent.mm.opensdk.utils;

import com.google.common.base.Ascii;
import java.security.MessageDigest;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public final class b {
    public static final String e(byte[] bArr) {
        byte[] digest;
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            instance.update(bArr);
            char[] cArr2 = new char[(r4 * 2)];
            int i = 0;
            for (byte b : instance.digest()) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b & Ascii.SI];
            }
            return new String(cArr2);
        } catch (Exception e) {
            return null;
        }
    }
}
