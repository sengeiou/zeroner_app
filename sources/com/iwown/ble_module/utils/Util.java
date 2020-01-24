package com.iwown.ble_module.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.cli.HelpFormatter;

public class Util {
    private static String SDPATH = (Environment.getExternalStorageDirectory() + "/");
    private static long[] crc32Table = new long[256];

    static {
        for (int i = 0; i < 256; i++) {
            long crcValue = (long) i;
            for (int j = 0; j < 8; j++) {
                if ((crcValue & 1) == 1) {
                    crcValue = (crcValue >> 1) ^ 3988292384L;
                } else {
                    crcValue >>= 1;
                }
            }
            crc32Table[i] = crcValue;
        }
    }

    public static String ascii2String(byte[] bytes2) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes2.length; i++) {
            if (ascii2Char(bytes2[i]) != 0) {
                sb.append(ascii2Char(bytes2[i]));
            }
        }
        return sb.toString();
    }

    public static char ascii2Char(int ASCII) {
        return (char) ASCII;
    }

    public static File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdirs();
        return dir;
    }

    public static File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002b A[SYNTHETIC, Splitter:B:17:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0037 A[SYNTHETIC, Splitter:B:23:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString(java.lang.String r5, java.lang.String r6) {
        /*
            r1 = 0
            r2 = 0
            java.io.File r1 = creatSDFile(r5)     // Catch:{ Exception -> 0x0025 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0025 }
            r4 = 1
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0025 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            r3.write(r6)     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            r3.flush()     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            if (r3 == 0) goto L_0x001d
            r3.close()     // Catch:{ Exception -> 0x001f }
        L_0x001d:
            r2 = r3
        L_0x001e:
            return r1
        L_0x001f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x001e
        L_0x0025:
            r0 = move-exception
        L_0x0026:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0034 }
            if (r2 == 0) goto L_0x001e
            r2.close()     // Catch:{ Exception -> 0x002f }
            goto L_0x001e
        L_0x002f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x001e
        L_0x0034:
            r4 = move-exception
        L_0x0035:
            if (r2 == 0) goto L_0x003a
            r2.close()     // Catch:{ Exception -> 0x003b }
        L_0x003a:
            throw r4
        L_0x003b:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x003a
        L_0x0040:
            r4 = move-exception
            r2 = r3
            goto L_0x0035
        L_0x0043:
            r0 = move-exception
            r2 = r3
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.utils.Util.write2SDFromString(java.lang.String, java.lang.String):java.io.File");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f A[SYNTHETIC, Splitter:B:16:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b A[SYNTHETIC, Splitter:B:22:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r1 = 0
            r2 = 0
            creatSDDir(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r4.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = creatSDFile(r4)     // Catch:{ Exception -> 0x0039 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0039 }
            r4 = 1
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0039 }
            r3.write(r7)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            r3.flush()     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            if (r3 == 0) goto L_0x0031
            r3.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0031:
            r2 = r3
        L_0x0032:
            return r1
        L_0x0033:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x0032
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x0032
        L_0x0043:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0032
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004e:
            throw r4
        L_0x004f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004e
        L_0x0054:
            r4 = move-exception
            r2 = r3
            goto L_0x0049
        L_0x0057:
            r0 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.utils.Util.write2SDFromString(java.lang.String, java.lang.String, java.lang.String):java.io.File");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036 A[SYNTHETIC, Splitter:B:14:0x0036] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0052 A[SYNTHETIC, Splitter:B:29:0x0052] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromInput(java.lang.String r7, java.lang.String r8, java.io.InputStream r9) {
        /*
            r2 = 0
            r3 = 0
            creatSDDir(r7)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x005e }
            r5.<init>()     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x005e }
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ Exception -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x005e }
            java.io.File r2 = creatSDFile(r5)     // Catch:{ Exception -> 0x005e }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x005e }
            r5 = 1
            r4.<init>(r2, r5)     // Catch:{ Exception -> 0x005e }
            r5 = 128(0x80, float:1.794E-43)
            byte[] r0 = new byte[r5]     // Catch:{ Exception -> 0x002f, all -> 0x005b }
        L_0x0024:
            int r5 = r9.read(r0)     // Catch:{ Exception -> 0x002f, all -> 0x005b }
            r6 = -1
            if (r5 == r6) goto L_0x003a
            r4.write(r0)     // Catch:{ Exception -> 0x002f, all -> 0x005b }
            goto L_0x0024
        L_0x002f:
            r1 = move-exception
            r3 = r4
        L_0x0031:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x004f }
            if (r3 == 0) goto L_0x0039
            r3.close()     // Catch:{ Exception -> 0x004a }
        L_0x0039:
            return r2
        L_0x003a:
            r4.flush()     // Catch:{ Exception -> 0x002f, all -> 0x005b }
            if (r4 == 0) goto L_0x0042
            r4.close()     // Catch:{ Exception -> 0x0044 }
        L_0x0042:
            r3 = r4
            goto L_0x0039
        L_0x0044:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            r3 = r4
            goto L_0x0039
        L_0x004a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0039
        L_0x004f:
            r5 = move-exception
        L_0x0050:
            if (r3 == 0) goto L_0x0055
            r3.close()     // Catch:{ Exception -> 0x0056 }
        L_0x0055:
            throw r5
        L_0x0056:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0055
        L_0x005b:
            r5 = move-exception
            r3 = r4
            goto L_0x0050
        L_0x005e:
            r1 = move-exception
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.utils.Util.write2SDFromInput(java.lang.String, java.lang.String, java.io.InputStream):java.io.File");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0031 A[SYNTHETIC, Splitter:B:19:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x004f A[SYNTHETIC, Splitter:B:33:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File wirteData2File(java.lang.String r9, java.lang.String r10, java.io.InputStream r11) {
        /*
            r2 = 0
            r5 = 0
            java.io.File r4 = creatSDDir(r9)     // Catch:{ Exception -> 0x005f }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x005f }
            r3.<init>(r4, r10)     // Catch:{ Exception -> 0x005f }
            boolean r7 = r3.exists()     // Catch:{ Exception -> 0x0061, all -> 0x0058 }
            if (r7 != 0) goto L_0x0014
            r3.createNewFile()     // Catch:{ Exception -> 0x0061, all -> 0x0058 }
        L_0x0014:
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0061, all -> 0x0058 }
            r7 = 1
            r6.<init>(r3, r7)     // Catch:{ Exception -> 0x0061, all -> 0x0058 }
            r7 = 128(0x80, float:1.794E-43)
            byte[] r0 = new byte[r7]     // Catch:{ Exception -> 0x0029, all -> 0x005b }
        L_0x001e:
            int r7 = r11.read(r0)     // Catch:{ Exception -> 0x0029, all -> 0x005b }
            r8 = -1
            if (r7 == r8) goto L_0x0035
            r6.write(r0)     // Catch:{ Exception -> 0x0029, all -> 0x005b }
            goto L_0x001e
        L_0x0029:
            r1 = move-exception
            r5 = r6
            r2 = r3
        L_0x002c:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x004c }
            if (r5 == 0) goto L_0x0034
            r5.close()     // Catch:{ Exception -> 0x0047 }
        L_0x0034:
            return r2
        L_0x0035:
            r6.flush()     // Catch:{ Exception -> 0x0029, all -> 0x005b }
            if (r6 == 0) goto L_0x003d
            r6.close()     // Catch:{ Exception -> 0x0040 }
        L_0x003d:
            r5 = r6
            r2 = r3
            goto L_0x0034
        L_0x0040:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            r5 = r6
            r2 = r3
            goto L_0x0034
        L_0x0047:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0034
        L_0x004c:
            r7 = move-exception
        L_0x004d:
            if (r5 == 0) goto L_0x0052
            r5.close()     // Catch:{ Exception -> 0x0053 }
        L_0x0052:
            throw r7
        L_0x0053:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0052
        L_0x0058:
            r7 = move-exception
            r2 = r3
            goto L_0x004d
        L_0x005b:
            r7 = move-exception
            r5 = r6
            r2 = r3
            goto L_0x004d
        L_0x005f:
            r1 = move-exception
            goto L_0x002c
        L_0x0061:
            r1 = move-exception
            r2 = r3
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.utils.Util.wirteData2File(java.lang.String, java.lang.String, java.io.InputStream):java.io.File");
    }

    public static InputStream StringTOInputStream(String in) throws Exception {
        return new ByteArrayInputStream(in.getBytes("utf-8"));
    }

    public static byte[] concat(byte[] a, byte[] b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        byte[] c = new byte[(a.length + b.length)];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static String bytesToString(byte[] bytes) {
        return bytesToString(bytes, true);
    }

    public static String bytesToString(byte[] bytes, boolean needSpace) {
        String format;
        if (bytes == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(bytes.length);
        if (needSpace) {
            format = "%02X ";
        } else {
            format = "%02X";
        }
        for (byte byteChar : bytes) {
            stringBuilder.append(String.format(format, new Object[]{Byte.valueOf(byteChar)}));
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static boolean isApplicationBroughtToBackground(Context context) {
        List<RunningTaskInfo> tasks = ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningTasks(1);
        if (tasks.isEmpty() || ((RunningTaskInfo) tasks.get(0)).topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }

    public static String isDevNameNULl(byte[] scanReord) {
        String name = "Device-XXXX";
        int i = 0;
        while (i < scanReord.length) {
            try {
                byte len = scanReord[i];
                if (len == 0) {
                    return name;
                }
                byte type = scanReord[i + 1];
                byte[] bytes = new byte[(len - 1)];
                for (int j = 0; j < len - 1; j++) {
                    bytes[j] = scanReord[i + 2 + j];
                }
                byte[] bytes2 = new byte[(len - 1)];
                int idx = 0;
                do {
                    if (len - 1 <= 0 || (len - 1) % 2 != 0) {
                        bytes2[idx] = bytes[idx];
                        idx++;
                    } else {
                        bytes2[idx] = bytes[idx + 1];
                        bytes2[idx + 1] = bytes[idx];
                        idx += 2;
                    }
                } while (idx < bytes.length);
                int i2 = i + len;
                if (type == 9) {
                    return ascii2String(bytes2);
                }
                i = i2 + 1;
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                return name;
            }
        }
        return name;
    }

    public static byte form_Header(int grp, int cmd) {
        return (byte) (((((byte) grp) & Ascii.SI) << 4) | (((byte) cmd) & Ascii.SI));
    }

    public static long date2TimeStamp(int year, int month, int day, int hour, int min) {
        try {
            return Long.parseLong(String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + hour + ":" + min + ":00").getTime() / 1000));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static long date2TimeStamp(int year, int month, int day, int hour, int min, int sec) {
        try {
            return Long.parseLong(String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + hour + ":" + min + ":" + sec).getTime() / 1000));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f A[SYNTHETIC, Splitter:B:16:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b A[SYNTHETIC, Splitter:B:22:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString_1(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r1 = 0
            r2 = 0
            creatSDDir(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r4.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = creatSDFile(r4)     // Catch:{ Exception -> 0x0039 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0039 }
            r4 = 1
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0039 }
            r3.write(r7)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            r3.flush()     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            if (r3 == 0) goto L_0x0031
            r3.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0031:
            r2 = r3
        L_0x0032:
            return r1
        L_0x0033:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x0032
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x0032
        L_0x0043:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0032
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004e:
            throw r4
        L_0x004f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004e
        L_0x0054:
            r4 = move-exception
            r2 = r3
            goto L_0x0049
        L_0x0057:
            r0 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.utils.Util.write2SDFromString_1(java.lang.String, java.lang.String, java.lang.String):java.io.File");
    }

    public static byte int2byte(int integer) {
        return (byte) (integer & 255);
    }

    public static int bytesToInt(byte[] bytes) {
        if (bytes.length == 1) {
            return bytes[0] & 255;
        }
        if (bytes.length == 4) {
            return (bytes[0] & 255) | ((bytes[1] << 8) & 65280) | ((bytes[2] << 16) & 16711680) | ((bytes[3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK);
        }
        if (bytes.length == 2) {
            return (bytes[0] & 255) | ((bytes[1] << 8) & 65280);
        }
        if (bytes.length == 3) {
            return (bytes[0] & 255) | ((bytes[1] << 8) & 65280) | ((bytes[2] << 16) & 16711680);
        }
        return 0;
    }

    public static int byteToInt(byte bytes) {
        return bytes & UnsignedBytes.MAX_VALUE;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static int crc16Modem(byte[] bytes) {
        boolean bit;
        boolean c15;
        int crc = 0;
        for (byte index : bytes) {
            for (int i = 0; i < 8; i++) {
                if (((index >> (7 - i)) & 1) == 1) {
                    bit = true;
                } else {
                    bit = false;
                }
                if (((crc >> 15) & 1) == 1) {
                    c15 = true;
                } else {
                    c15 = false;
                }
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= 4129;
                }
            }
        }
        return crc & 65535;
    }

    public static long CRC_32(byte[] bytes) {
        long resultCrcValue = 4294967295L;
        for (byte b : bytes) {
            resultCrcValue = crc32Table[(int) ((((long) b) ^ resultCrcValue) & 255)] ^ (resultCrcValue >> 8);
        }
        return resultCrcValue ^ 4294967295L;
    }

    public static byte[] getMergeHeadLenBytes(int typeH, int typeL, byte[] bytes) {
        return writeWristBandData(crc16Modem(bytes), typeH, typeL, bytes);
    }

    private static byte[] writeWristBandData(int cyc, int typeH, int typeL, byte[] data) {
        return concat(new byte[]{68, 84, (byte) (data.length & 255), (byte) ((data.length & 65280) >> 8), (byte) (cyc & 255), (byte) ((cyc & 65280) >> 8), (byte) typeL, (byte) typeH}, data);
    }

    public static int getTimeZone() {
        return TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000;
    }

    public static byte[] fileToByteStr(String path) {
        byte[] data = null;
        try {
            KLog.e("no2855 文件agps路径 " + path);
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                KLog.e("no2855 文件内容不存在? ");
                return null;
            }
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            KLog.d("no2855 文件字节大小: " + data.length);
            return data;
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
