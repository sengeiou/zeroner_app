package com.youzan.sdk.loader.http;

import android.util.Base64;
import com.alibaba.android.arouter.utils.Consts;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: Auth */
final class a {

    /* renamed from: com.youzan.sdk.loader.http.a$a reason: collision with other inner class name */
    /* compiled from: Auth */
    static class C0012a {

        /* renamed from: ˊ reason: contains not printable characters */
        private static final String f23 = "https://open.koudaitong.com";

        /* renamed from: ˋ reason: contains not printable characters */
        private static final String f24 = "entry";

        /* renamed from: ˎ reason: contains not printable characters */
        private static final String f25 = "gw";

        /* renamed from: ˏ reason: contains not printable characters */
        private static final String f26 = "1.0.0";

        C0012a() {
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static String m22(String method) {
            return m24(m23(method));
        }

        /* renamed from: ˋ reason: contains not printable characters */
        private static String m23(String method) {
            if (method != null) {
                String[] sr = method.trim().split("\\.");
                if (sr.length >= 1) {
                    StringBuilder builder = new StringBuilder(sr.length - 1);
                    int i = 0;
                    while (i < sr.length - 1) {
                        builder.append(i == 0 ? sr[i] : Consts.DOT + sr[i]);
                        i++;
                    }
                    builder.append('/');
                    builder.append("1.0.0");
                    builder.append('/');
                    builder.append(sr[sr.length - 1]);
                    return builder.toString();
                }
            }
            return null;
        }

        /* renamed from: ˎ reason: contains not printable characters */
        private static String m24(String path) {
            StringBuilder builder = new StringBuilder(25);
            if (path != null) {
                builder.append(f23);
                builder.append('/');
                builder.append(f25);
                builder.append('/');
                builder.append(f24);
                builder.append('/');
                builder.append(path);
            }
            return builder.toString();
        }
    }

    /* compiled from: Auth */
    private static class b {

        /* renamed from: ˊ reason: contains not printable characters */
        private static final String f27 = "HmacSHA1";

        private b() {
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static String m26(String data, String key) {
            String hmac = m27(m28(data, key));
            if (hmac != null) {
                return hmac.replace('/', '_').replace('+', '-').trim();
            }
            return hmac;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static String m27(byte[] input) {
            if (input != null) {
                return new String(Base64.encode(input, 0));
            }
            return null;
        }

        /* renamed from: ˋ reason: contains not printable characters */
        public static byte[] m28(String data, String key) {
            byte[] hmac = null;
            if (data == null || key == null) {
                return hmac;
            }
            try {
                SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), f27);
                Mac mac = Mac.getInstance(f27);
                mac.init(signingKey);
                return mac.doFinal(data.getBytes());
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                ThrowableExtension.printStackTrace(e);
                return hmac;
            }
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public static String m25(String signContent) {
            String hashResult = "";
            try {
                MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
                md.update(signContent.getBytes("UTF-8"));
                byte[] byteData = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : byteData) {
                    sb.append(Integer.toString((b & UnsignedBytes.MAX_VALUE) + 256, 16).substring(1));
                }
                return sb.toString().toLowerCase();
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                ThrowableExtension.printStackTrace(e);
                return hashResult;
            }
        }
    }

    /* compiled from: Auth */
    public static class c {

        /* renamed from: ˊ reason: contains not printable characters */
        private static final String f28 = "https://open.youzan.com/api/oauthentry";

        /* renamed from: ˋ reason: contains not printable characters */
        private static final String f29 = "method";

        /* renamed from: ˊ reason: contains not printable characters */
        public static String m29(String method) {
            return String.format("%s?%s=%s", new Object[]{f28, f29, method});
        }
    }

    /* compiled from: Auth */
    public static class d {
        /* renamed from: ˊ reason: contains not printable characters */
        public static Map<String, String> m32(Map<String, String> param, String appId, String appSecret) {
            HashMap<String, String> commonParams = m31(appId);
            commonParams.putAll(param);
            commonParams.put("sign", m30(commonParams, appSecret));
            return commonParams;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        private static HashMap<String, String> m31(String appId) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            HashMap<String, String> params = new HashMap<>();
            params.put("app_id", appId);
            params.put("timestamp", dateFormat.format(new Date()));
            params.put("format", "json");
            params.put("sign_method", "md5");
            params.put("v", "1.0");
            return params;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        private static String m30(HashMap<String, String> param, String appSecret) {
            ArrayList<String> keyList = new ArrayList<>(param.keySet());
            Collections.sort(keyList);
            StringBuilder builder = new StringBuilder();
            builder.append(appSecret);
            Iterator it = keyList.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                builder.append(key);
                builder.append((String) param.get(key));
            }
            builder.append(appSecret);
            return b.m25(builder.toString());
        }
    }

    a() {
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static String m21(int authType, String method) {
        switch (authType) {
            case 2:
                return c.m29(method);
            default:
                return method;
        }
    }
}
