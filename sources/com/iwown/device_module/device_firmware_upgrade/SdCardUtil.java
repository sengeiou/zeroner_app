package com.iwown.device_module.device_firmware_upgrade;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SdCardUtil {
    private static final String TAG = SdCardUtil.class.getSimpleName();

    public static class SDCardInfo {
        public long availableBlocks;
        public long availableBytes;
        public long blockByteSize;
        public long freeBlocks;
        public long freeBytes;
        public boolean isExist;
        public long totalBlocks;
        public long totalBytes;

        public String toString() {
            return "SDCardInfo{isExist=" + this.isExist + ", totalBlocks=" + this.totalBlocks + ", freeBlocks=" + this.freeBlocks + ", availableBlocks=" + this.availableBlocks + ", blockByteSize=" + this.blockByteSize + ", totalBytes=" + this.totalBytes + ", freeBytes=" + this.freeBytes + ", availableBytes=" + this.availableBytes + '}';
        }
    }

    public boolean isSdCardAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static StatFs getStatFs(String path) {
        return new StatFs(path);
    }

    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();
    }

    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d0 A[SYNTHETIC, Splitter:B:30:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0111 A[SYNTHETIC, Splitter:B:43:0x0111] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getSDCardPath() {
        /*
            java.lang.String r2 = "cat /proc/mounts"
            r7 = 0
            java.lang.Runtime r6 = java.lang.Runtime.getRuntime()
            r0 = 0
            java.lang.Process r5 = r6.exec(r2)     // Catch:{ Exception -> 0x011d }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x011d }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x011d }
            java.io.BufferedInputStream r11 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x011d }
            java.io.InputStream r12 = r5.getInputStream()     // Catch:{ Exception -> 0x011d }
            r11.<init>(r12)     // Catch:{ Exception -> 0x011d }
            r10.<init>(r11)     // Catch:{ Exception -> 0x011d }
            r1.<init>(r10)     // Catch:{ Exception -> 0x011d }
        L_0x0020:
            java.lang.String r4 = r1.readLine()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            if (r4 == 0) goto L_0x00fc
            java.lang.String r10 = TAG     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r12 = 0
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r13.<init>()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r14 = "proc/mounts:   "
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.StringBuilder r13 = r13.append(r4)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11[r12] = r13     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            com.socks.library.KLog.i(r10, r11)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r10 = "sdcard"
            boolean r10 = r4.contains(r10)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            if (r10 == 0) goto L_0x009b
            java.lang.String r10 = ".android_secure"
            boolean r10 = r4.contains(r10)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            if (r10 == 0) goto L_0x009b
            java.lang.String r10 = " "
            java.lang.String[] r9 = r4.split(r10)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            int r10 = r9.length     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11 = 5
            if (r10 < r11) goto L_0x009b
            r10 = 1
            r10 = r9[r10]     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r11 = "/.android_secure"
            java.lang.String r12 = ""
            java.lang.String r7 = r10.replace(r11, r12)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r10 = TAG     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r12 = 0
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r13.<init>()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r14 = "find sd card path:   "
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.StringBuilder r13 = r13.append(r7)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11[r12] = r13     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            com.socks.library.KLog.i(r10, r11)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            if (r1 == 0) goto L_0x0093
            r1.close()     // Catch:{ IOException -> 0x0096 }
        L_0x0093:
            r0 = r1
            r8 = r7
        L_0x0095:
            return r8
        L_0x0096:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0093
        L_0x009b:
            int r10 = r5.waitFor()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            if (r10 == 0) goto L_0x0020
            int r10 = r5.exitValue()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11 = 1
            if (r10 != r11) goto L_0x0020
            java.lang.String r10 = TAG     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r12 = 0
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r13.<init>()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.StringBuilder r13 = r13.append(r2)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r14 = " 命令执行失败"
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            r11[r12] = r13     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            com.socks.library.KLog.e(r10, r11)     // Catch:{ Exception -> 0x00c9, all -> 0x011a }
            goto L_0x0020
        L_0x00c9:
            r3 = move-exception
            r0 = r1
        L_0x00cb:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)     // Catch:{ all -> 0x010e }
            if (r0 == 0) goto L_0x00d3
            r0.close()     // Catch:{ IOException -> 0x0109 }
        L_0x00d3:
            java.io.File r10 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r7 = r10.getPath()
            java.lang.String r10 = TAG
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r12 = 0
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "not find sd card path return default:   "
            java.lang.StringBuilder r13 = r13.append(r14)
            java.lang.StringBuilder r13 = r13.append(r7)
            java.lang.String r13 = r13.toString()
            r11[r12] = r13
            com.socks.library.KLog.i(r10, r11)
            r8 = r7
            goto L_0x0095
        L_0x00fc:
            if (r1 == 0) goto L_0x0101
            r1.close()     // Catch:{ IOException -> 0x0103 }
        L_0x0101:
            r0 = r1
            goto L_0x00d3
        L_0x0103:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            r0 = r1
            goto L_0x00d3
        L_0x0109:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x00d3
        L_0x010e:
            r10 = move-exception
        L_0x010f:
            if (r0 == 0) goto L_0x0114
            r0.close()     // Catch:{ IOException -> 0x0115 }
        L_0x0114:
            throw r10
        L_0x0115:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0114
        L_0x011a:
            r10 = move-exception
            r0 = r1
            goto L_0x010f
        L_0x011d:
            r3 = move-exception
            goto L_0x00cb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_firmware_upgrade.SdCardUtil.getSDCardPath():java.lang.String");
    }

    public static ArrayList<String> getSDCardPathEx() {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                KLog.i(TAG, "mount:  " + line);
                if (!line.contains("secure") && !line.contains("asec")) {
                    if (line.contains("fat")) {
                        String[] columns = line.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                        if (columns.length > 1) {
                            list.add("*" + columns[1]);
                        }
                    } else if (line.contains("fuse")) {
                        String[] columns2 = line.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                        if (columns2.length > 1) {
                            list.add(columns2[1]);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        return list;
    }

    @TargetApi(18)
    public static long getAvailableSize(String path) {
        try {
            StatFs stat = new StatFs(new File(path).getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    @TargetApi(18)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        if ("mounted".equals(Environment.getExternalStorageState())) {
            sd.isExist = true;
            if (VERSION.SDK_INT >= 18) {
                StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
                sd.totalBlocks = sf.getBlockCountLong();
                sd.blockByteSize = sf.getBlockSizeLong();
                sd.availableBlocks = sf.getAvailableBlocksLong();
                sd.availableBytes = sf.getAvailableBytes();
                sd.freeBlocks = sf.getFreeBlocksLong();
                sd.freeBytes = sf.getFreeBytes();
                sd.totalBytes = sf.getTotalBytes();
            }
        }
        KLog.i(TAG, sd.toString());
        return sd;
    }
}
