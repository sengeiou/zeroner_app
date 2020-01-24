package com.iwown.lib_common;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static final int BUFFER_SIZE = 2048;

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0056 A[SYNTHETIC, Splitter:B:15:0x0056] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0062 A[SYNTHETIC, Splitter:B:21:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zip(java.lang.String r14, java.lang.String r15) {
        /*
            r9 = 1
            r5 = 0
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            r10.<init>(r15)     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            r1.<init>(r10)     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.util.zip.ZipOutputStream r8 = new java.util.zip.ZipOutputStream     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            r8.<init>(r1)     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            r4.<init>(r14)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            r11 = 1
            compress(r4, r8, r10, r11)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            r10.<init>()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r11 = "no2855-->压缩完成，耗时："
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            long r12 = r2 - r6
            java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r11 = " ms"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            com.socks.library.KLog.d(r10)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            if (r8 == 0) goto L_0x004b
            r8.close()     // Catch:{ IOException -> 0x004d }
        L_0x004b:
            r5 = r8
        L_0x004c:
            return r9
        L_0x004d:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004b
        L_0x0052:
            r0 = move-exception
        L_0x0053:
            r9 = 0
            if (r5 == 0) goto L_0x004c
            r5.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x004c
        L_0x005a:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004c
        L_0x005f:
            r9 = move-exception
        L_0x0060:
            if (r5 == 0) goto L_0x0065
            r5.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0065:
            throw r9
        L_0x0066:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0065
        L_0x006b:
            r9 = move-exception
            r5 = r8
            goto L_0x0060
        L_0x006e:
            r0 = move-exception
            r5 = r8
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.ZipUtil.zip(java.lang.String, java.lang.String):boolean");
    }

    public static boolean unZip(File srcFile, File outputFile) {
        if (!unCommonlyZip(srcFile, outputFile)) {
            return unGzip(srcFile, outputFile);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043 A[SYNTHETIC, Splitter:B:17:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0091 A[SYNTHETIC, Splitter:B:32:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean unCommonlyZip(java.io.File r22, java.io.File r23) {
        /*
            long r14 = java.lang.System.currentTimeMillis()
            boolean r19 = r22.exists()
            if (r19 != 0) goto L_0x000d
            r19 = 0
        L_0x000c:
            return r19
        L_0x000d:
            r17 = 0
            java.lang.String r3 = r23.getPath()     // Catch:{ Exception -> 0x00da, all -> 0x00d8 }
            java.util.zip.ZipFile r18 = new java.util.zip.ZipFile     // Catch:{ Exception -> 0x00da, all -> 0x00d8 }
            r0 = r18
            r1 = r22
            r0.<init>(r1)     // Catch:{ Exception -> 0x00da, all -> 0x00d8 }
            java.util.Enumeration r7 = r18.entries()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
        L_0x0020:
            boolean r19 = r7.hasMoreElements()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            if (r19 == 0) goto L_0x00a0
            java.lang.Object r10 = r7.nextElement()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.util.zip.ZipEntry r10 = (java.util.zip.ZipEntry) r10     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            boolean r19 = r10.isDirectory()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            if (r19 == 0) goto L_0x004c
            r5 = r3
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r4.<init>(r5)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r4.mkdirs()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            goto L_0x0020
        L_0x003c:
            r6 = move-exception
            r17 = r18
        L_0x003f:
            r19 = 0
            if (r17 == 0) goto L_0x000c
            r17.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x000c
        L_0x0047:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r6)
            goto L_0x000c
        L_0x004c:
            java.io.File r16 = new java.io.File     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r0 = r16
            r0.<init>(r3)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.io.File r19 = r16.getParentFile()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            boolean r19 = r19.exists()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            if (r19 != 0) goto L_0x0064
            java.io.File r19 = r16.getParentFile()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r19.mkdirs()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
        L_0x0064:
            r16.createNewFile()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r0 = r18
            java.io.InputStream r12 = r0.getInputStream(r10)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r0 = r16
            r11.<init>(r0)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r19 = 2048(0x800, float:2.87E-42)
            r0 = r19
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x003c, all -> 0x008c }
        L_0x007a:
            int r13 = r12.read(r2)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r19 = -1
            r0 = r19
            if (r13 == r0) goto L_0x0095
            r19 = 0
            r0 = r19
            r11.write(r2, r0, r13)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            goto L_0x007a
        L_0x008c:
            r19 = move-exception
            r17 = r18
        L_0x008f:
            if (r17 == 0) goto L_0x0094
            r17.close()     // Catch:{ IOException -> 0x00d3 }
        L_0x0094:
            throw r19
        L_0x0095:
            if (r11 == 0) goto L_0x009a
            r11.close()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
        L_0x009a:
            if (r12 == 0) goto L_0x0020
            r12.close()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            goto L_0x0020
        L_0x00a0:
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r19.<init>()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.lang.String r20 = "no2855解压完成，耗时："
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            long r20 = r8 - r14
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.lang.String r20 = " ms"
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            java.lang.String r19 = r19.toString()     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            com.socks.library.KLog.d(r19)     // Catch:{ Exception -> 0x003c, all -> 0x008c }
            r19 = 1
            if (r18 == 0) goto L_0x000c
            r18.close()     // Catch:{ IOException -> 0x00cd }
            goto L_0x000c
        L_0x00cd:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r6)
            goto L_0x000c
        L_0x00d3:
            r6 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r6)
            goto L_0x0094
        L_0x00d8:
            r19 = move-exception
            goto L_0x008f
        L_0x00da:
            r6 = move-exception
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.ZipUtil.unCommonlyZip(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002e A[SYNTHETIC, Splitter:B:17:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0033 A[Catch:{ IOException -> 0x0050 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0058 A[SYNTHETIC, Splitter:B:38:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x005d A[Catch:{ IOException -> 0x0061 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean unGzip(java.io.File r10, java.io.File r11) {
        /*
            r8 = 0
            r6 = 0
            r3 = 0
            java.util.zip.GZIPInputStream r7 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x006d }
            java.io.FileInputStream r9 = new java.io.FileInputStream     // Catch:{ IOException -> 0x006d }
            r9.<init>(r10)     // Catch:{ IOException -> 0x006d }
            r7.<init>(r9)     // Catch:{ IOException -> 0x006d }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x006f, all -> 0x0066 }
            r4.<init>(r11)     // Catch:{ IOException -> 0x006f, all -> 0x0066 }
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r9]     // Catch:{ IOException -> 0x0023, all -> 0x0069 }
            r5 = -1
        L_0x0017:
            int r5 = r7.read(r0)     // Catch:{ IOException -> 0x0023, all -> 0x0069 }
            r9 = -1
            if (r5 == r9) goto L_0x0037
            r9 = 0
            r4.write(r0, r9, r5)     // Catch:{ IOException -> 0x0023, all -> 0x0069 }
            goto L_0x0017
        L_0x0023:
            r2 = move-exception
            r3 = r4
            r6 = r7
        L_0x0026:
            java.lang.String r9 = "no2855---> 文件解压失败"
            com.socks.library.KLog.e(r9)     // Catch:{ all -> 0x0055 }
            if (r6 == 0) goto L_0x0031
            r6.close()     // Catch:{ IOException -> 0x0050 }
        L_0x0031:
            if (r3 == 0) goto L_0x0036
            r3.close()     // Catch:{ IOException -> 0x0050 }
        L_0x0036:
            return r8
        L_0x0037:
            java.lang.String r9 = "no2855---> 文件解压成功"
            com.socks.library.KLog.e(r9)     // Catch:{ IOException -> 0x0023, all -> 0x0069 }
            r8 = 1
            if (r7 == 0) goto L_0x0043
            r7.close()     // Catch:{ IOException -> 0x004b }
        L_0x0043:
            if (r4 == 0) goto L_0x0048
            r4.close()     // Catch:{ IOException -> 0x004b }
        L_0x0048:
            r3 = r4
            r6 = r7
            goto L_0x0036
        L_0x004b:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0048
        L_0x0050:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0036
        L_0x0055:
            r8 = move-exception
        L_0x0056:
            if (r6 == 0) goto L_0x005b
            r6.close()     // Catch:{ IOException -> 0x0061 }
        L_0x005b:
            if (r3 == 0) goto L_0x0060
            r3.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0060:
            throw r8
        L_0x0061:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0060
        L_0x0066:
            r8 = move-exception
            r6 = r7
            goto L_0x0056
        L_0x0069:
            r8 = move-exception
            r3 = r4
            r6 = r7
            goto L_0x0056
        L_0x006d:
            r2 = move-exception
            goto L_0x0026
        L_0x006f:
            r2 = move-exception
            r6 = r7
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.ZipUtil.unGzip(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0056 A[SYNTHETIC, Splitter:B:15:0x0056] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0062 A[SYNTHETIC, Splitter:B:21:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean toZip(java.lang.String r14, java.lang.String r15) throws java.lang.RuntimeException {
        /*
            r9 = 1
            r5 = 0
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            r10.<init>(r15)     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            r1.<init>(r10)     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.util.zip.ZipOutputStream r8 = new java.util.zip.ZipOutputStream     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            r8.<init>(r1)     // Catch:{ Exception -> 0x0052, all -> 0x005f }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            r4.<init>(r14)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            r11 = 1
            compress(r4, r8, r10, r11)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            r10.<init>()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r11 = "no2855-->压缩完成，耗时："
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            long r12 = r2 - r6
            java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r11 = " ms"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            com.socks.library.KLog.d(r10)     // Catch:{ Exception -> 0x006e, all -> 0x006b }
            if (r8 == 0) goto L_0x004b
            r8.close()     // Catch:{ IOException -> 0x004d }
        L_0x004b:
            r5 = r8
        L_0x004c:
            return r9
        L_0x004d:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004b
        L_0x0052:
            r0 = move-exception
        L_0x0053:
            r9 = 0
            if (r5 == 0) goto L_0x004c
            r5.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x004c
        L_0x005a:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004c
        L_0x005f:
            r9 = move-exception
        L_0x0060:
            if (r5 == 0) goto L_0x0065
            r5.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0065:
            throw r9
        L_0x0066:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0065
        L_0x006b:
            r9 = move-exception
            r5 = r8
            goto L_0x0060
        L_0x006e:
            r0 = move-exception
            r5 = r8
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.ZipUtil.toZip(java.lang.String, java.lang.String):boolean");
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[2048];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            FileInputStream in = new FileInputStream(sourceFile);
            while (true) {
                int len = in.read(buf);
                if (len != -1) {
                    zos.write(buf, 0, len);
                } else {
                    zos.closeEntry();
                    in.close();
                    return;
                }
            }
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            } else if (KeepDirStructure) {
                zos.putNextEntry(new ZipEntry(name + "/"));
                zos.closeEntry();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a2 A[SYNTHETIC, Splitter:B:21:0x00a2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void unNewZip(java.io.File r22, java.lang.String r23) {
        /*
            long r14 = java.lang.System.currentTimeMillis()
            boolean r18 = r22.exists()
            if (r18 != 0) goto L_0x0028
            java.lang.RuntimeException r18 = new java.lang.RuntimeException
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = r22.getPath()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r20 = "所指文件不存在"
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r18.<init>(r19)
            throw r18
        L_0x0028:
            r16 = 0
            java.util.zip.ZipFile r17 = new java.util.zip.ZipFile     // Catch:{ Exception -> 0x0147 }
            r0 = r17
            r1 = r22
            r0.<init>(r1)     // Catch:{ Exception -> 0x0147 }
            java.util.Enumeration r8 = r17.entries()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
        L_0x0037:
            boolean r18 = r8.hasMoreElements()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            if (r18 == 0) goto L_0x0110
            java.lang.Object r9 = r8.nextElement()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.util.zip.ZipEntry r9 = (java.util.zip.ZipEntry) r9     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.io.PrintStream r18 = java.lang.System.out     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r19.<init>()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r20 = "no2855解压"
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r20 = r9.getName()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r19 = r19.toString()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18.println(r19)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            boolean r18 = r9.isDirectory()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            if (r18 == 0) goto L_0x00a6
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18.<init>()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r0 = r18
            r1 = r23
            java.lang.StringBuilder r18 = r0.append(r1)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r19 = "/"
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r19 = r9.getName()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r4 = r18.toString()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r3.mkdirs()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            goto L_0x0037
        L_0x008f:
            r5 = move-exception
            r16 = r17
        L_0x0092:
            java.lang.RuntimeException r18 = new java.lang.RuntimeException     // Catch:{ all -> 0x009f }
            java.lang.String r19 = "unzip error from ZipUtils"
            r0 = r18
            r1 = r19
            r0.<init>(r1, r5)     // Catch:{ all -> 0x009f }
            throw r18     // Catch:{ all -> 0x009f }
        L_0x009f:
            r18 = move-exception
        L_0x00a0:
            if (r16 == 0) goto L_0x00a5
            r16.close()     // Catch:{ IOException -> 0x0141 }
        L_0x00a5:
            throw r18
        L_0x00a6:
            java.io.File r13 = new java.io.File     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18.<init>()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r0 = r18
            r1 = r23
            java.lang.StringBuilder r18 = r0.append(r1)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r19 = "/"
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r19 = r9.getName()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r18 = r18.toString()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r0 = r18
            r13.<init>(r0)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.io.File r18 = r13.getParentFile()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            boolean r18 = r18.exists()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            if (r18 != 0) goto L_0x00de
            java.io.File r18 = r13.getParentFile()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18.mkdirs()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
        L_0x00de:
            r13.createNewFile()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r0 = r17
            java.io.InputStream r11 = r0.getInputStream(r9)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r10.<init>(r13)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18 = 2048(0x800, float:2.87E-42)
            r0 = r18
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
        L_0x00f2:
            int r12 = r11.read(r2)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18 = -1
            r0 = r18
            if (r12 == r0) goto L_0x0108
            r18 = 0
            r0 = r18
            r10.write(r2, r0, r12)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            goto L_0x00f2
        L_0x0104:
            r18 = move-exception
            r16 = r17
            goto L_0x00a0
        L_0x0108:
            r10.close()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r11.close()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            goto L_0x0037
        L_0x0110:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.io.PrintStream r18 = java.lang.System.out     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r19.<init>()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r20 = "no2855解压完成，耗时："
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            long r20 = r6 - r14
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r20 = " ms"
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            java.lang.String r19 = r19.toString()     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            r18.println(r19)     // Catch:{ Exception -> 0x008f, all -> 0x0104 }
            if (r17 == 0) goto L_0x013b
            r17.close()     // Catch:{ IOException -> 0x013c }
        L_0x013b:
            return
        L_0x013c:
            r5 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r5)
            goto L_0x013b
        L_0x0141:
            r5 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r5)
            goto L_0x00a5
        L_0x0147:
            r5 = move-exception
            goto L_0x0092
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.ZipUtil.unNewZip(java.io.File, java.lang.String):void");
    }
}
