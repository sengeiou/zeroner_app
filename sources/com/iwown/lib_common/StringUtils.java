package com.iwown.lib_common;

import android.text.Editable;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String PATTERN = "&|[︰-ﾠ]|‘’|“”";
    private static final Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static boolean isEmpty(Editable input) {
        try {
            return isEmpty(input.toString());
        } catch (NullPointerException e) {
            return true;
        }
    }

    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != 9 && c != 13 && c != 10) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    public static float toFloat(String str, float defValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static float toFloat(Object obj) {
        if (obj == null) {
            return 0.0f;
        }
        return toFloat(obj.toString(), 0.0f);
    }

    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            return false;
        }
    }

    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            for (String line = read.readLine(); line != null; line = read.readLine()) {
                res.append(line);
            }
            if (isr != null) {
                try {
                    isr.close();
                    isr.close();
                } catch (IOException e) {
                }
            }
            if (read != null) {
                read.close();
            }
            if (is != null) {
                is.close();
            }
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
            if (isr != null) {
                try {
                    isr.close();
                    isr.close();
                } catch (IOException e3) {
                }
            }
            if (read != null) {
                read.close();
            }
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (isr != null) {
                try {
                    isr.close();
                    isr.close();
                } catch (IOException e4) {
                    throw th;
                }
            }
            if (read != null) {
                read.close();
            }
            if (is != null) {
                is.close();
            }
            throw th;
        }
        return res.toString();
    }

    public static String implode(String separator, String[] arr) {
        StringBuffer strbuf = new StringBuffer();
        for (String append : arr) {
            strbuf.append(separator).append(append);
        }
        return strbuf.deleteCharAt(0).toString();
    }

    public static String[] explode(String separator, String text) {
        return text.replace('[', ' ').replace(']', ' ').trim().split(separator);
    }

    public static String toHexString(byte data) {
        return bytesToString(new byte[]{data});
    }

    public static String toHexString(byte[] bytes) {
        return bytesToString(bytes);
    }

    private static String bytesToString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length);
        for (byte byteChar : bytes) {
            stringBuilder.append(String.format("%02X", new Object[]{Byte.valueOf(byteChar)}));
        }
        return stringBuilder.toString();
    }

    public static String replaceSpecialtyStr(String str, String pattern, String replace) {
        if (isBlankOrNull(pattern)) {
            pattern = "\\s*|\t|\r|\n";
        }
        if (isBlankOrNull(replace)) {
            replace = "";
        }
        return Pattern.compile(pattern).matcher(str).replaceAll(replace);
    }

    public static boolean isBlankOrNull(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static String cleanBlankOrDigit(String str) {
        if (isBlankOrNull(str)) {
            return "null";
        }
        return Pattern.compile("\\d|\\s").matcher(str).replaceAll("");
    }

    public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF-8");
        byte[] bs = new byte[bytes.length];
        int i = 0;
        while (i < bytes.length) {
            short b = (short) bytes[i];
            if (b > 0) {
                int i2 = i + 1;
                bs[i] = bytes[i];
                i = i2;
            } else {
                short b2 = (short) (b + 256);
                if (((b2 ^ 192) >> 4) == 0) {
                    int i3 = i + 1;
                    bs[i] = bytes[i];
                    i = i3 + 1;
                    bs[i3] = bytes[i3];
                } else if (((b2 ^ 224) >> 4) == 0) {
                    int i4 = i + 1;
                    bs[i] = bytes[i];
                    int i5 = i4 + 1;
                    bs[i4] = bytes[i4];
                    int i6 = i5 + 1;
                    bs[i5] = bytes[i5];
                    i = i6;
                } else if (((b2 ^ 240) >> 4) == 0) {
                    i += 4;
                }
            }
        }
        return new String(bs, "utf-8").trim();
    }

    public static String toUtf8String(String s) {
        try {
            return bytesToString(s.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
            return "iWown";
        }
    }
}
