package com.tencent.bugly.beta.tinker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Formatter;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* compiled from: BUGLY */
public class TinkerUtils {
    public static final int ERROR_PATCH_CONDITION_NOT_SATISFIED = -24;
    public static final int ERROR_PATCH_CRASH_LIMIT = -23;
    public static final int ERROR_PATCH_GOOGLEPLAY_CHANNEL = -20;
    public static final int ERROR_PATCH_MEMORY_LIMIT = -22;
    public static final int ERROR_PATCH_ROM_SPACE = -21;
    public static final int MIN_MEMORY_HEAP_SIZE = 45;
    public static final String PLATFORM = "platform";
    private static final String TAG = "Tinker.TinkerUtils";
    private static boolean background = false;

    /* compiled from: BUGLY */
    public static class ScreenState {

        /* compiled from: BUGLY */
        interface IOnScreenOff {
            void onScreenOff();
        }

        ScreenState(Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.registerReceiver(new BroadcastReceiver() {
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    TinkerLog.i(TinkerUtils.TAG, "ScreenReceiver action [%s] ", action);
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                        context.unregisterReceiver(this);
                    }
                }
            }, intentFilter);
        }
    }

    public static boolean isGooglePlay() {
        return false;
    }

    public static boolean isBackground() {
        return background;
    }

    public static void setBackground(boolean back) {
        background = back;
    }

    public static int checkForPatchRecover(long roomSize, int maxMemory) {
        if (isGooglePlay()) {
            return -20;
        }
        if (maxMemory < 45) {
            return -22;
        }
        if (!checkRomSpaceEnough(roomSize)) {
            return -21;
        }
        return 0;
    }

    public static boolean isXposedExists(Throwable thr) {
        for (StackTraceElement className : thr.getStackTrace()) {
            String className2 = className.getClassName();
            if (className2 != null && className2.contains("de.robv.android.xposed.XposedBridge")) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x002c  */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean checkRomSpaceEnough(long r10) {
        /*
            r2 = 0
            java.io.File r0 = android.os.Environment.getDataDirectory()     // Catch:{ Exception -> 0x0032 }
            android.os.StatFs r4 = new android.os.StatFs     // Catch:{ Exception -> 0x0032 }
            java.lang.String r0 = r0.getPath()     // Catch:{ Exception -> 0x0032 }
            r4.<init>(r0)     // Catch:{ Exception -> 0x0032 }
            int r0 = r4.getAvailableBlocks()     // Catch:{ Exception -> 0x0032 }
            long r0 = (long) r0     // Catch:{ Exception -> 0x0032 }
            int r5 = r4.getBlockSize()     // Catch:{ Exception -> 0x0032 }
            long r6 = (long) r5
            long r0 = r0 * r6
            int r5 = r4.getBlockCount()     // Catch:{ Exception -> 0x0039 }
            long r6 = (long) r5     // Catch:{ Exception -> 0x0039 }
            int r4 = r4.getBlockSize()     // Catch:{ Exception -> 0x0039 }
            long r4 = (long) r4
            long r4 = r4 * r6
            r8 = r4
            r4 = r0
            r0 = r8
        L_0x0028:
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x0037
            int r0 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r0 <= 0) goto L_0x0037
            r0 = 1
        L_0x0031:
            return r0
        L_0x0032:
            r0 = move-exception
            r0 = r2
        L_0x0034:
            r4 = r0
            r0 = r2
            goto L_0x0028
        L_0x0037:
            r0 = 0
            goto L_0x0031
        L_0x0039:
            r4 = move-exception
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.tinker.TinkerUtils.checkRomSpaceEnough(long):boolean");
    }

    public static String getExceptionCauseString(Throwable ex) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        while (ex != null) {
            try {
                if (ex.getCause() == null) {
                    break;
                }
                ex = ex.getCause();
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                throw th;
            }
        }
        if (ex != null) {
            ThrowableExtension.printStackTrace(ex, printStream);
        }
        String visualString = toVisualString(byteArrayOutputStream.toString());
        try {
            byteArrayOutputStream.close();
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        return visualString;
    }

    private static String toVisualString(String src) {
        boolean z;
        if (src == null) {
            return null;
        }
        char[] charArray = src.toCharArray();
        if (charArray == null) {
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= charArray.length) {
                z = false;
                break;
            } else if (charArray[i] > 127) {
                charArray[i] = 0;
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return new String(charArray, 0, i);
        }
        return src;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0053 A[SYNTHETIC, Splitter:B:22:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0058 A[SYNTHETIC, Splitter:B:25:0x0058] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copy(java.io.File r5, java.io.File r6) throws java.io.IOException {
        /*
            r1 = 0
            r0 = 0
            if (r5 == 0) goto L_0x0006
            if (r6 != 0) goto L_0x0007
        L_0x0006:
            return r0
        L_0x0007:
            boolean r2 = r5.exists()
            if (r2 == 0) goto L_0x0006
            java.lang.String r0 = r6.getAbsolutePath()     // Catch:{ all -> 0x007f }
            r2 = 0
            java.lang.String r3 = java.io.File.separator     // Catch:{ all -> 0x007f }
            int r3 = r0.lastIndexOf(r3)     // Catch:{ all -> 0x007f }
            java.lang.String r0 = r0.substring(r2, r3)     // Catch:{ all -> 0x007f }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x007f }
            r2.<init>(r0)     // Catch:{ all -> 0x007f }
            boolean r0 = r2.exists()     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x002a
            r2.mkdirs()     // Catch:{ all -> 0x007f }
        L_0x002a:
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ all -> 0x007f }
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ all -> 0x007f }
            r0.<init>(r5)     // Catch:{ all -> 0x007f }
            r3.<init>(r0)     // Catch:{ all -> 0x007f }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0082 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ all -> 0x0082 }
            r0.<init>(r6)     // Catch:{ all -> 0x0082 }
            r2.<init>(r0)     // Catch:{ all -> 0x0082 }
            r0 = 5120(0x1400, float:7.175E-42)
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x004e }
        L_0x0042:
            int r1 = r3.read(r0)     // Catch:{ all -> 0x004e }
            r4 = -1
            if (r1 == r4) goto L_0x005c
            r4 = 0
            r2.write(r0, r4, r1)     // Catch:{ all -> 0x004e }
            goto L_0x0042
        L_0x004e:
            r0 = move-exception
            r1 = r2
            r2 = r3
        L_0x0051:
            if (r2 == 0) goto L_0x0056
            r2.close()     // Catch:{ IOException -> 0x0075 }
        L_0x0056:
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ IOException -> 0x007a }
        L_0x005b:
            throw r0
        L_0x005c:
            r2.flush()     // Catch:{ all -> 0x004e }
            r0 = 1
            if (r3 == 0) goto L_0x0065
            r3.close()     // Catch:{ IOException -> 0x0070 }
        L_0x0065:
            if (r2 == 0) goto L_0x0006
            r2.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x0006
        L_0x006b:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0006
        L_0x0070:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0065
        L_0x0075:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0056
        L_0x007a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x005b
        L_0x007f:
            r0 = move-exception
            r2 = r1
            goto L_0x0051
        L_0x0082:
            r0 = move-exception
            r2 = r3
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.tinker.TinkerUtils.copy(java.io.File, java.io.File):boolean");
    }

    public static String getSignature(Context context) {
        String str = "";
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            if (signatureArr != null && signatureArr.length > 0) {
                X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[0].toByteArray()));
                MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);
                return new String(x509Certificate.getEncoded());
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return str;
    }

    public static String bytesToHexString(byte[] bytes, boolean upper) {
        if (bytes == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(r2 * 2);
        Formatter formatter = new Formatter(stringBuffer);
        for (byte valueOf : bytes) {
            formatter.format("%02x", new Object[]{Byte.valueOf(valueOf)});
        }
        formatter.close();
        return upper ? stringBuffer.toString().toUpperCase() : stringBuffer.toString().toLowerCase();
    }

    public static byte[] readJarEntry(File file, String entryName) {
        if (file == null) {
            return null;
        }
        try {
            if (!file.exists() || TextUtils.isEmpty(entryName)) {
                return null;
            }
            return readJarEntry(new JarFile(file), entryName);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static byte[] readJarEntry(JarFile jarFile, String entryName) {
        if (jarFile == null) {
            return null;
        }
        try {
            if (TextUtils.isEmpty(entryName)) {
                return null;
            }
            return readJarEntry(jarFile, jarFile.getJarEntry(entryName));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static byte[] readJarEntry(JarFile jarFile, JarEntry entry) {
        byte[] bArr = null;
        if (jarFile == null || entry == null) {
            return bArr;
        }
        try {
            return readBytes(jarFile.getInputStream(entry));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return bArr;
        }
    }

    public static byte[] readBytes(InputStream in) {
        try {
            byte[] bArr = new byte[512];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = in.read(bArr);
                if (read == -1) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static void rollbackPatch(Context context) {
        Tinker.with(context).rollbackPatch();
    }
}
