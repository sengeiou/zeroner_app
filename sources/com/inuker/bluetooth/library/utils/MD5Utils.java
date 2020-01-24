package com.inuker.bluetooth.library.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class MD5Utils {
    public static byte[] MD5_12(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            md5.update(text.getBytes(), 0, text.length());
            byte[] bytes = md5.digest();
            int length = bytes.length;
            if (length >= 12) {
                return Arrays.copyOfRange(bytes, (length / 2) - 6, (length / 2) + 6);
            }
            return ByteUtils.EMPTY_BYTES;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
