package com.tencent.tinker.lib.patch;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.tencent.tinker.commons.dexpatcher.DexPatchApplier;
import com.tencent.tinker.commons.util.StreamUtil;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.TinkerDexOptimizer;
import com.tencent.tinker.loader.TinkerDexOptimizer.ResultCallback;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo;
import com.tencent.tinker.loader.shareutil.ShareElfFile;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DexDiffPatchInternal extends BasePatchInternal {
    protected static final int MAX_WAIT_COUNT = 120;
    protected static final String TAG = "Tinker.DexDiffPatchInternal";
    protected static final int WAIT_ASYN_OAT_TIME = 10000;
    private static HashMap<ShareDexDiffPatchInfo, File> classNDexInfo = new HashMap<>();
    private static boolean isVmArt = ShareTinkerInternals.isVmArt();
    private static ArrayList<File> optFiles = new ArrayList<>();
    private static ArrayList<ShareDexDiffPatchInfo> patchList = new ArrayList<>();

    protected static boolean tryRecoverDexFiles(Tinker manager, ShareSecurityCheck checker, Context context, String patchVersionDirectory, File patchFile) {
        if (!manager.isEnabledForDex()) {
            TinkerLog.w(TAG, "patch recover, dex is not enabled", new Object[0]);
            return true;
        }
        String dexMeta = (String) checker.getMetaContentMap().get(ShareConstants.DEX_META_FILE);
        if (dexMeta == null) {
            TinkerLog.w(TAG, "patch recover, dex is not contained", new Object[0]);
            return true;
        }
        long begin = SystemClock.elapsedRealtime();
        boolean result = patchDexExtractViaDexDiff(context, patchVersionDirectory, dexMeta, patchFile);
        TinkerLog.i(TAG, "recover dex result:%b, cost:%d", Boolean.valueOf(result), Long.valueOf(SystemClock.elapsedRealtime() - begin));
        return result;
    }

    protected static boolean waitAndCheckDexOptFile(File patchFile, Tinker manager) {
        if (optFiles.isEmpty()) {
            return true;
        }
        int size = patchList.size() * 30;
        if (size > 120) {
            size = 120;
        }
        TinkerLog.i(TAG, "raw dex count: %d, dex opt dex count: %d, final wait times: %d", Integer.valueOf(patchList.size()), Integer.valueOf(optFiles.size()), Integer.valueOf(size));
        for (int i = 0; i < size; i++) {
            if (!checkAllDexOptFile(optFiles, i + 1)) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    TinkerLog.e(TAG, "thread sleep InterruptedException e:" + e, new Object[0]);
                }
            }
        }
        List<File> failDexFiles = new ArrayList<>();
        Iterator it = optFiles.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            TinkerLog.i(TAG, "check dex optimizer file exist: %s, size %d", file.getPath(), Long.valueOf(file.length()));
            if (!SharePatchFileUtil.isLegalFile(file)) {
                TinkerLog.e(TAG, "final parallel dex optimizer file %s is not exist, return false", file.getName());
                failDexFiles.add(file);
            }
        }
        if (!failDexFiles.isEmpty()) {
            manager.getPatchReporter().onPatchDexOptFail(patchFile, failDexFiles, new TinkerRuntimeException(ShareConstants.CHECK_DEX_OAT_EXIST_FAIL));
            return false;
        }
        if (VERSION.SDK_INT >= 21) {
            Throwable lastThrowable = null;
            Iterator it2 = optFiles.iterator();
            while (it2.hasNext()) {
                File file2 = (File) it2.next();
                TinkerLog.i(TAG, "check dex optimizer file format: %s, size %d", file2.getName(), Long.valueOf(file2.length()));
                try {
                    if (ShareElfFile.getFileTypeByMagic(file2) == 1) {
                        ShareElfFile elfFile = null;
                        try {
                            elfFile = new ShareElfFile(file2);
                        } catch (Throwable e2) {
                            TinkerLog.e(TAG, "final parallel dex optimizer file %s is not elf format, return false", file2.getName());
                            failDexFiles.add(file2);
                            lastThrowable = e2;
                        } finally {
                            StreamUtil.closeQuietly(elfFile);
                        }
                    }
                } catch (IOException e3) {
                }
            }
            if (!failDexFiles.isEmpty()) {
                manager.getPatchReporter().onPatchDexOptFail(patchFile, failDexFiles, lastThrowable == null ? new TinkerRuntimeException(ShareConstants.CHECK_DEX_OAT_FORMAT_FAIL) : new TinkerRuntimeException(ShareConstants.CHECK_DEX_OAT_FORMAT_FAIL, lastThrowable));
                return false;
            }
        }
        return true;
    }

    private static boolean patchDexExtractViaDexDiff(Context context, String patchVersionDirectory, String meta, File patchFile) {
        String dir = patchVersionDirectory + "/" + "dex" + "/";
        if (!extractDexDiffInternals(context, dir, meta, patchFile, 3)) {
            TinkerLog.w(TAG, "patch recover, extractDiffInternals fail", new Object[0]);
            return false;
        }
        File[] files = new File(dir).listFiles();
        List<File> legalFiles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    legalFiles.add(file);
                }
            }
        }
        return dexOptimizeDexFiles(context, legalFiles, patchVersionDirectory + "/" + "odex" + "/", patchFile);
    }

    private static boolean checkClassNDexFiles(String dexFilePath) {
        if (patchList.isEmpty() || !isVmArt) {
            return false;
        }
        ShareDexDiffPatchInfo testInfo = null;
        File testFile = null;
        Iterator it = patchList.iterator();
        while (it.hasNext()) {
            ShareDexDiffPatchInfo info = (ShareDexDiffPatchInfo) it.next();
            File dexFile = new File(dexFilePath + info.realName);
            if (ShareConstants.CLASS_N_PATTERN.matcher(dexFile.getName()).matches()) {
                classNDexInfo.put(info, dexFile);
            }
            if (info.rawName.startsWith(ShareConstants.TEST_DEX_NAME)) {
                testInfo = info;
                testFile = dexFile;
            }
        }
        if (testInfo != null) {
            classNDexInfo.put(ShareTinkerInternals.changeTestDexToClassN(testInfo, classNDexInfo.size() + 1), testFile);
        }
        File classNFile = new File(dexFilePath, ShareConstants.CLASS_N_APK_NAME);
        boolean result = true;
        if (classNFile.exists()) {
            Iterator it2 = classNDexInfo.keySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ShareDexDiffPatchInfo info2 = (ShareDexDiffPatchInfo) it2.next();
                if (!SharePatchFileUtil.verifyDexFileMd5(classNFile, info2.rawName, info2.destMd5InArt)) {
                    TinkerLog.e(TAG, "verify dex file md5 error, entry name; %s, file len: %d", info2.rawName, Long.valueOf(classNFile.length()));
                    result = false;
                    break;
                }
            }
            if (!result) {
                SharePatchFileUtil.safeDeleteFile(classNFile);
            }
        } else {
            result = false;
        }
        if (!result) {
            return result;
        }
        for (File dexFile2 : classNDexInfo.values()) {
            SharePatchFileUtil.safeDeleteFile(dexFile2);
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0164  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean mergeClassNDexFiles(android.content.Context r26, java.io.File r27, java.lang.String r28) {
        /*
            java.util.ArrayList<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo> r19 = patchList
            boolean r19 = r19.isEmpty()
            if (r19 != 0) goto L_0x000c
            boolean r19 = isVmArt
            if (r19 != 0) goto L_0x000e
        L_0x000c:
            r15 = 1
        L_0x000d:
            return r15
        L_0x000e:
            java.io.File r4 = new java.io.File
            java.lang.String r19 = "tinker_classN.apk"
            r0 = r28
            r1 = r19
            r4.<init>(r0, r1)
            java.util.HashMap<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo, java.io.File> r19 = classNDexInfo
            boolean r19 = r19.isEmpty()
            if (r19 == 0) goto L_0x0043
            java.lang.String r19 = "Tinker.DexDiffPatchInternal"
            java.lang.String r20 = "classNDexInfo size: %d, no need to merge classN dex files"
            r21 = 1
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            java.util.HashMap<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo, java.io.File> r23 = classNDexInfo
            int r23 = r23.size()
            java.lang.Integer r23 = java.lang.Integer.valueOf(r23)
            r21[r22] = r23
            com.tencent.tinker.lib.util.TinkerLog.w(r19, r20, r21)
            r15 = 1
            goto L_0x000d
        L_0x0043:
            long r16 = java.lang.System.currentTimeMillis()
            r15 = 1
            r12 = 0
            com.tencent.tinker.ziputils.ziputil.TinkerZipOutputStream r13 = new com.tencent.tinker.ziputils.ziputil.TinkerZipOutputStream     // Catch:{ Throwable -> 0x01d1 }
            java.io.BufferedOutputStream r19 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x01d1 }
            java.io.FileOutputStream r20 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x01d1 }
            r0 = r20
            r0.<init>(r4)     // Catch:{ Throwable -> 0x01d1 }
            r19.<init>(r20)     // Catch:{ Throwable -> 0x01d1 }
            r0 = r19
            r13.<init>(r0)     // Catch:{ Throwable -> 0x01d1 }
            java.util.HashMap<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo, java.io.File> r19 = classNDexInfo     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            java.util.Set r19 = r19.keySet()     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            java.util.Iterator r19 = r19.iterator()     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
        L_0x0066:
            boolean r20 = r19.hasNext()     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            if (r20 == 0) goto L_0x015e
            java.lang.Object r9 = r19.next()     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo r9 = (com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo) r9     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            java.util.HashMap<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo, java.io.File> r20 = classNDexInfo     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            r0 = r20
            java.lang.Object r5 = r0.get(r9)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            java.io.File r5 = (java.io.File) r5     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            boolean r0 = r9.isJarMode     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            r20 = r0
            if (r20 == 0) goto L_0x0144
            r7 = 0
            r10 = 0
            com.tencent.tinker.ziputils.ziputil.TinkerZipFile r8 = new com.tencent.tinker.ziputils.ziputil.TinkerZipFile     // Catch:{ all -> 0x0136 }
            r8.<init>(r5)     // Catch:{ all -> 0x0136 }
            java.lang.String r20 = "classes.dex"
            r0 = r20
            com.tencent.tinker.ziputils.ziputil.TinkerZipEntry r14 = r8.getEntry(r0)     // Catch:{ all -> 0x01d4 }
            com.tencent.tinker.ziputils.ziputil.TinkerZipEntry r11 = new com.tencent.tinker.ziputils.ziputil.TinkerZipEntry     // Catch:{ all -> 0x01d4 }
            java.lang.String r0 = r9.rawName     // Catch:{ all -> 0x01d4 }
            r20 = r0
            r0 = r20
            r11.<init>(r14, r0)     // Catch:{ all -> 0x01d4 }
            java.io.InputStream r10 = r8.getInputStream(r14)     // Catch:{ all -> 0x01d4 }
            com.tencent.tinker.ziputils.ziputil.TinkerZipUtil.extractTinkerEntry(r11, r10, r13)     // Catch:{ all -> 0x01d4 }
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r10)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r8)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            goto L_0x0066
        L_0x00ab:
            r18 = move-exception
            r12 = r13
        L_0x00ad:
            java.lang.String r19 = "Tinker.DexDiffPatchInternal"
            java.lang.String r20 = "merge classN file"
            r21 = 0
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x01ce }
            r21 = r0
            r0 = r19
            r1 = r18
            r2 = r20
            r3 = r21
            com.tencent.tinker.lib.util.TinkerLog.printErrStackTrace(r0, r1, r2, r3)     // Catch:{ all -> 0x01ce }
            r15 = 0
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r12)
        L_0x00ca:
            if (r15 == 0) goto L_0x011a
            java.util.HashMap<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo, java.io.File> r19 = classNDexInfo
            java.util.Set r19 = r19.keySet()
            java.util.Iterator r19 = r19.iterator()
        L_0x00d6:
            boolean r20 = r19.hasNext()
            if (r20 == 0) goto L_0x011a
            java.lang.Object r9 = r19.next()
            com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo r9 = (com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo) r9
            java.lang.String r0 = r9.rawName
            r20 = r0
            java.lang.String r0 = r9.destMd5InArt
            r21 = r0
            r0 = r20
            r1 = r21
            boolean r20 = com.tencent.tinker.loader.shareutil.SharePatchFileUtil.verifyDexFileMd5(r4, r0, r1)
            if (r20 != 0) goto L_0x00d6
            r15 = 0
            java.lang.String r19 = "Tinker.DexDiffPatchInternal"
            java.lang.String r20 = "verify dex file md5 error, entry name; %s, file len: %d"
            r21 = 2
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            java.lang.String r0 = r9.rawName
            r23 = r0
            r21[r22] = r23
            r22 = 1
            long r24 = r4.length()
            java.lang.Long r23 = java.lang.Long.valueOf(r24)
            r21[r22] = r23
            com.tencent.tinker.lib.util.TinkerLog.e(r19, r20, r21)
        L_0x011a:
            if (r15 == 0) goto L_0x0164
            java.util.HashMap<com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo, java.io.File> r19 = classNDexInfo
            java.util.Collection r19 = r19.values()
            java.util.Iterator r19 = r19.iterator()
        L_0x0126:
            boolean r20 = r19.hasNext()
            if (r20 == 0) goto L_0x0191
            java.lang.Object r5 = r19.next()
            java.io.File r5 = (java.io.File) r5
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.safeDeleteFile(r5)
            goto L_0x0126
        L_0x0136:
            r19 = move-exception
        L_0x0137:
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r10)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r7)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            throw r19     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
        L_0x013e:
            r19 = move-exception
            r12 = r13
        L_0x0140:
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r12)
            throw r19
        L_0x0144:
            com.tencent.tinker.ziputils.ziputil.TinkerZipEntry r6 = new com.tencent.tinker.ziputils.ziputil.TinkerZipEntry     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            java.lang.String r0 = r9.rawName     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            r20 = r0
            r0 = r20
            r6.<init>(r0)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            java.lang.String r0 = r9.newOrPatchedDexCrC     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            r20 = r0
            long r20 = java.lang.Long.parseLong(r20)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            r0 = r20
            com.tencent.tinker.ziputils.ziputil.TinkerZipUtil.extractLargeModifyFile(r6, r5, r0, r13)     // Catch:{ Throwable -> 0x00ab, all -> 0x013e }
            goto L_0x0066
        L_0x015e:
            com.tencent.tinker.commons.util.StreamUtil.closeQuietly(r13)
            r12 = r13
            goto L_0x00ca
        L_0x0164:
            java.lang.String r19 = "Tinker.DexDiffPatchInternal"
            java.lang.String r20 = "merge classN dex error, try delete temp file"
            r21 = 0
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            com.tencent.tinker.lib.util.TinkerLog.e(r19, r20, r21)
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.safeDeleteFile(r4)
            com.tencent.tinker.lib.tinker.Tinker r19 = com.tencent.tinker.lib.tinker.Tinker.with(r26)
            com.tencent.tinker.lib.reporter.PatchReporter r19 = r19.getPatchReporter()
            java.lang.String r20 = r4.getName()
            r21 = 7
            r0 = r19
            r1 = r27
            r2 = r20
            r3 = r21
            r0.onPatchTypeExtractFail(r1, r4, r2, r3)
        L_0x0191:
            java.lang.String r19 = "Tinker.DexDiffPatchInternal"
            java.lang.String r20 = "merge classN dex file %s, result: %b, size: %d, use: %dms"
            r21 = 4
            r0 = r21
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r0
            r22 = 0
            java.lang.String r23 = r4.getPath()
            r21[r22] = r23
            r22 = 1
            java.lang.Boolean r23 = java.lang.Boolean.valueOf(r15)
            r21[r22] = r23
            r22 = 2
            long r24 = r4.length()
            java.lang.Long r23 = java.lang.Long.valueOf(r24)
            r21[r22] = r23
            r22 = 3
            long r24 = java.lang.System.currentTimeMillis()
            long r24 = r24 - r16
            java.lang.Long r23 = java.lang.Long.valueOf(r24)
            r21[r22] = r23
            com.tencent.tinker.lib.util.TinkerLog.i(r19, r20, r21)
            goto L_0x000d
        L_0x01ce:
            r19 = move-exception
            goto L_0x0140
        L_0x01d1:
            r18 = move-exception
            goto L_0x00ad
        L_0x01d4:
            r19 = move-exception
            r7 = r8
            goto L_0x0137
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.lib.patch.DexDiffPatchInternal.mergeClassNDexFiles(android.content.Context, java.io.File, java.lang.String):boolean");
    }

    private static boolean dexOptimizeDexFiles(Context context, List<File> dexFiles, String optimizeDexDirectory, File patchFile) {
        Tinker manager = Tinker.with(context);
        optFiles.clear();
        if (dexFiles != null) {
            File optimizeDexDirectoryFile = new File(optimizeDexDirectory);
            if (optimizeDexDirectoryFile.exists() || optimizeDexDirectoryFile.mkdirs()) {
                for (File file : dexFiles) {
                    optFiles.add(new File(SharePatchFileUtil.optimizedPathFor(file, optimizeDexDirectoryFile)));
                }
                TinkerLog.i(TAG, "patch recover, try to optimize dex file count:%d, optimizeDexDirectory:%s", Integer.valueOf(dexFiles.size()), optimizeDexDirectory);
                final List<File> failOptDexFile = new Vector<>();
                final Throwable[] throwable = new Throwable[1];
                TinkerDexOptimizer.optimizeAll(dexFiles, optimizeDexDirectoryFile, new ResultCallback() {
                    long startTime;

                    public void onStart(File dexFile, File optimizedDir) {
                        this.startTime = System.currentTimeMillis();
                        TinkerLog.i(DexDiffPatchInternal.TAG, "start to parallel optimize dex %s, size: %d", dexFile.getPath(), Long.valueOf(dexFile.length()));
                    }

                    public void onSuccess(File dexFile, File optimizedDir, File optimizedFile) {
                        TinkerLog.i(DexDiffPatchInternal.TAG, "success to parallel optimize dex %s, opt file:%s, opt file size: %d, use time %d", dexFile.getPath(), optimizedFile.getPath(), Long.valueOf(optimizedFile.length()), Long.valueOf(System.currentTimeMillis() - this.startTime));
                    }

                    public void onFailed(File dexFile, File optimizedDir, Throwable thr) {
                        TinkerLog.i(DexDiffPatchInternal.TAG, "fail to parallel optimize dex %s use time %d", dexFile.getPath(), Long.valueOf(System.currentTimeMillis() - this.startTime));
                        failOptDexFile.add(dexFile);
                        throwable[0] = thr;
                    }
                });
                if (!failOptDexFile.isEmpty()) {
                    manager.getPatchReporter().onPatchDexOptFail(patchFile, failOptDexFile, throwable[0]);
                    return false;
                }
            } else {
                TinkerLog.w(TAG, "patch recover, make optimizeDexDirectoryFile fail", new Object[0]);
                return false;
            }
        }
        return true;
    }

    private static boolean checkAllDexOptFile(ArrayList<File> files, int count) {
        Iterator it = files.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (!SharePatchFileUtil.isLegalFile(file)) {
                TinkerLog.e(TAG, "parallel dex optimizer file %s is not exist, just wait %d times", file.getName(), Integer.valueOf(count));
                return false;
            }
        }
        return true;
    }

    private static boolean extractDexDiffInternals(Context context, String dir, String meta, File patchFile, int type) {
        ZipFile patch;
        ZipFile apk;
        String patchRealPath;
        patchList.clear();
        ShareDexDiffPatchInfo.parseDexDiffPatchInfo(meta, patchList);
        if (patchList.isEmpty()) {
            TinkerLog.w(TAG, "extract patch list is empty! type:%s:", ShareTinkerInternals.getTypeString(type));
            return true;
        }
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Tinker manager = Tinker.with(context);
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                TinkerLog.w(TAG, "applicationInfo == null!!!!", new Object[0]);
                SharePatchFileUtil.closeZip(null);
                SharePatchFileUtil.closeZip(null);
                return false;
            }
            apk = new ZipFile(applicationInfo.sourceDir);
            try {
                patch = new ZipFile(patchFile);
            } catch (Throwable th) {
                th = th;
                patch = null;
                SharePatchFileUtil.closeZip(apk);
                SharePatchFileUtil.closeZip(patch);
                throw th;
            }
            try {
                if (checkClassNDexFiles(dir)) {
                    TinkerLog.w(TAG, "class n dex file %s is already exist, and md5 match, just continue", ShareConstants.CLASS_N_APK_NAME);
                    SharePatchFileUtil.closeZip(apk);
                    SharePatchFileUtil.closeZip(patch);
                    return true;
                }
                Iterator it = patchList.iterator();
                while (it.hasNext()) {
                    ShareDexDiffPatchInfo info = (ShareDexDiffPatchInfo) it.next();
                    long start = System.currentTimeMillis();
                    if (info.path.equals("")) {
                        patchRealPath = info.rawName;
                    } else {
                        patchRealPath = info.path + "/" + info.rawName;
                    }
                    String dexDiffMd5 = info.dexDiffMd5;
                    String oldDexCrc = info.oldDexCrC;
                    if (isVmArt || !info.destMd5InDvm.equals("0")) {
                        String extractedFileMd5 = isVmArt ? info.destMd5InArt : info.destMd5InDvm;
                        if (!SharePatchFileUtil.checkIfMd5Valid(extractedFileMd5)) {
                            TinkerLog.w(TAG, "meta file md5 invalid, type:%s, name: %s, md5: %s", ShareTinkerInternals.getTypeString(type), info.rawName, extractedFileMd5);
                            manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                            SharePatchFileUtil.closeZip(apk);
                            SharePatchFileUtil.closeZip(patch);
                            return false;
                        }
                        File extractedFile = new File(dir + info.realName);
                        if (!extractedFile.exists()) {
                            extractedFile.getParentFile().mkdirs();
                        } else if (SharePatchFileUtil.verifyDexFileMd5(extractedFile, extractedFileMd5)) {
                            TinkerLog.w(TAG, "dex file %s is already exist, and md5 match, just continue", extractedFile.getPath());
                        } else {
                            TinkerLog.w(TAG, "have a mismatch corrupted dex " + extractedFile.getPath(), new Object[0]);
                            extractedFile.delete();
                        }
                        ZipEntry patchFileEntry = patch.getEntry(patchRealPath);
                        ZipEntry rawApkFileEntry = apk.getEntry(patchRealPath);
                        if (oldDexCrc.equals("0")) {
                            if (patchFileEntry == null) {
                                TinkerLog.w(TAG, "patch entry is null. path:" + patchRealPath, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                SharePatchFileUtil.closeZip(apk);
                                SharePatchFileUtil.closeZip(patch);
                                return false;
                            } else if (!extractDexFile(patch, patchFileEntry, extractedFile, info)) {
                                TinkerLog.w(TAG, "Failed to extract raw patch file " + extractedFile.getPath(), new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                SharePatchFileUtil.closeZip(apk);
                                SharePatchFileUtil.closeZip(patch);
                                return false;
                            }
                        } else if (dexDiffMd5.equals("0")) {
                            if (!isVmArt) {
                                continue;
                            } else if (rawApkFileEntry == null) {
                                TinkerLog.w(TAG, "apk entry is null. path:" + patchRealPath, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                SharePatchFileUtil.closeZip(apk);
                                SharePatchFileUtil.closeZip(patch);
                                return false;
                            } else {
                                String rawEntryCrc = String.valueOf(rawApkFileEntry.getCrc());
                                if (!rawEntryCrc.equals(oldDexCrc)) {
                                    TinkerLog.e(TAG, "apk entry %s crc is not equal, expect crc: %s, got crc: %s", patchRealPath, oldDexCrc, rawEntryCrc);
                                    manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                    SharePatchFileUtil.closeZip(apk);
                                    SharePatchFileUtil.closeZip(patch);
                                    return false;
                                }
                                extractDexFile(apk, rawApkFileEntry, extractedFile, info);
                                if (!SharePatchFileUtil.verifyDexFileMd5(extractedFile, extractedFileMd5)) {
                                    TinkerLog.w(TAG, "Failed to recover dex file when verify patched dex: " + extractedFile.getPath(), new Object[0]);
                                    manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                    SharePatchFileUtil.safeDeleteFile(extractedFile);
                                    SharePatchFileUtil.closeZip(apk);
                                    SharePatchFileUtil.closeZip(patch);
                                    return false;
                                }
                            }
                        } else if (patchFileEntry == null) {
                            TinkerLog.w(TAG, "patch entry is null. path:" + patchRealPath, new Object[0]);
                            manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                            SharePatchFileUtil.closeZip(apk);
                            SharePatchFileUtil.closeZip(patch);
                            return false;
                        } else if (!SharePatchFileUtil.checkIfMd5Valid(dexDiffMd5)) {
                            TinkerLog.w(TAG, "meta file md5 invalid, type:%s, name: %s, md5: %s", ShareTinkerInternals.getTypeString(type), info.rawName, dexDiffMd5);
                            manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                            SharePatchFileUtil.closeZip(apk);
                            SharePatchFileUtil.closeZip(patch);
                            return false;
                        } else if (rawApkFileEntry == null) {
                            TinkerLog.w(TAG, "apk entry is null. path:" + patchRealPath, new Object[0]);
                            manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                            SharePatchFileUtil.closeZip(apk);
                            SharePatchFileUtil.closeZip(patch);
                            return false;
                        } else {
                            String rawEntryCrc2 = String.valueOf(rawApkFileEntry.getCrc());
                            if (!rawEntryCrc2.equals(oldDexCrc)) {
                                TinkerLog.e(TAG, "apk entry %s crc is not equal, expect crc: %s, got crc: %s", patchRealPath, oldDexCrc, rawEntryCrc2);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                SharePatchFileUtil.closeZip(apk);
                                SharePatchFileUtil.closeZip(patch);
                                return false;
                            }
                            patchDexFile(apk, patch, rawApkFileEntry, patchFileEntry, info, extractedFile);
                            if (!SharePatchFileUtil.verifyDexFileMd5(extractedFile, extractedFileMd5)) {
                                TinkerLog.w(TAG, "Failed to recover dex file when verify patched dex: " + extractedFile.getPath(), new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.rawName, type);
                                SharePatchFileUtil.safeDeleteFile(extractedFile);
                                SharePatchFileUtil.closeZip(apk);
                                SharePatchFileUtil.closeZip(patch);
                                return false;
                            }
                            TinkerLog.w(TAG, "success recover dex file: %s, size: %d, use time: %d", extractedFile.getPath(), Long.valueOf(extractedFile.length()), Long.valueOf(System.currentTimeMillis() - start));
                        }
                    } else {
                        TinkerLog.w(TAG, "patch dex %s is only for art, just continue", patchRealPath);
                    }
                }
                if (!mergeClassNDexFiles(context, patchFile, dir)) {
                    SharePatchFileUtil.closeZip(apk);
                    SharePatchFileUtil.closeZip(patch);
                    return false;
                }
                SharePatchFileUtil.closeZip(apk);
                SharePatchFileUtil.closeZip(patch);
                return true;
            } catch (Throwable th2) {
                e = th2;
                TinkerRuntimeException tinkerRuntimeException = new TinkerRuntimeException("patch " + ShareTinkerInternals.getTypeString(type) + " extract failed (" + e.getMessage() + ").", e);
                throw tinkerRuntimeException;
            }
        } catch (Throwable th3) {
            th = th3;
            patch = null;
            apk = null;
            SharePatchFileUtil.closeZip(apk);
            SharePatchFileUtil.closeZip(patch);
            throw th;
        }
    }

    private static boolean extractDexToJar(ZipFile zipFile, ZipEntry entryFile, File extractTo, String targetMd5) throws IOException {
        BufferedInputStream bis;
        int numAttempts = 0;
        boolean isExtractionSuccessful = false;
        while (numAttempts < 2 && !isExtractionSuccessful) {
            numAttempts++;
            ZipOutputStream zos = null;
            BufferedInputStream bis2 = null;
            TinkerLog.i(TAG, "try Extracting " + extractTo.getPath(), new Object[0]);
            try {
                ZipOutputStream zos2 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(extractTo)));
                try {
                    bis = new BufferedInputStream(zipFile.getInputStream(entryFile));
                } catch (Throwable th) {
                    th = th;
                    zos = zos2;
                    StreamUtil.closeQuietly(bis2);
                    StreamUtil.closeQuietly(zos);
                    throw th;
                }
                try {
                    byte[] buffer = new byte[16384];
                    zos2.putNextEntry(new ZipEntry("classes.dex"));
                    for (int length = bis.read(buffer); length != -1; length = bis.read(buffer)) {
                        zos2.write(buffer, 0, length);
                    }
                    zos2.closeEntry();
                    StreamUtil.closeQuietly(bis);
                    StreamUtil.closeQuietly(zos2);
                    isExtractionSuccessful = SharePatchFileUtil.verifyDexFileMd5(extractTo, targetMd5);
                    TinkerLog.i(TAG, "isExtractionSuccessful: %b", Boolean.valueOf(isExtractionSuccessful));
                    if (!isExtractionSuccessful && (!extractTo.delete() || extractTo.exists())) {
                        TinkerLog.e(TAG, "Failed to delete corrupted dex " + extractTo.getPath(), new Object[0]);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bis2 = bis;
                    zos = zos2;
                    StreamUtil.closeQuietly(bis2);
                    StreamUtil.closeQuietly(zos);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                StreamUtil.closeQuietly(bis2);
                StreamUtil.closeQuietly(zos);
                throw th;
            }
        }
        return isExtractionSuccessful;
    }

    private static boolean extractDexFile(ZipFile zipFile, ZipEntry entryFile, File extractTo, ShareDexDiffPatchInfo dexInfo) throws IOException {
        String fileMd5 = isVmArt ? dexInfo.destMd5InArt : dexInfo.destMd5InDvm;
        String rawName = dexInfo.rawName;
        boolean isJarMode = dexInfo.isJarMode;
        if (!SharePatchFileUtil.isRawDexFile(rawName) || !isJarMode) {
            return extract(zipFile, entryFile, extractTo, fileMd5, true);
        }
        return extractDexToJar(zipFile, entryFile, extractTo, fileMd5);
    }

    private static void patchDexFile(ZipFile baseApk, ZipFile patchPkg, ZipEntry oldDexEntry, ZipEntry patchFileEntry, ShareDexDiffPatchInfo patchInfo, File patchedDexFile) throws IOException {
        ZipEntry entry;
        InputStream oldDexStream = null;
        InputStream patchFileStream = null;
        try {
            InputStream oldDexStream2 = new BufferedInputStream(baseApk.getInputStream(oldDexEntry));
            if (patchFileEntry != null) {
                try {
                    patchFileStream = new BufferedInputStream(patchPkg.getInputStream(patchFileEntry));
                } catch (Throwable th) {
                    th = th;
                    oldDexStream = oldDexStream2;
                }
            } else {
                patchFileStream = null;
            }
            boolean isRawDexFile = SharePatchFileUtil.isRawDexFile(patchInfo.rawName);
            if (!isRawDexFile || patchInfo.isJarMode) {
                ZipOutputStream zos = null;
                try {
                    ZipOutputStream zos2 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(patchedDexFile)));
                    try {
                        zos2.putNextEntry(new ZipEntry("classes.dex"));
                        if (!isRawDexFile) {
                            ZipInputStream zis = null;
                            try {
                                ZipInputStream zis2 = new ZipInputStream(oldDexStream2);
                                do {
                                    try {
                                        entry = zis2.getNextEntry();
                                        if (entry == null) {
                                            break;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        zis = zis2;
                                        StreamUtil.closeQuietly(zis);
                                        throw th;
                                    }
                                } while (!"classes.dex".equals(entry.getName()));
                                if (entry == null) {
                                    throw new TinkerRuntimeException("can't recognize zip dex format file:" + patchedDexFile.getAbsolutePath());
                                }
                                new DexPatchApplier((InputStream) zis2, patchFileStream).executeAndSaveTo((OutputStream) zos2);
                                StreamUtil.closeQuietly(zis2);
                            } catch (Throwable th3) {
                                th = th3;
                                StreamUtil.closeQuietly(zis);
                                throw th;
                            }
                        } else {
                            new DexPatchApplier(oldDexStream2, patchFileStream).executeAndSaveTo((OutputStream) zos2);
                        }
                        zos2.closeEntry();
                        StreamUtil.closeQuietly(zos2);
                    } catch (Throwable th4) {
                        th = th4;
                        zos = zos2;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    StreamUtil.closeQuietly(zos);
                    throw th;
                }
            } else {
                new DexPatchApplier(oldDexStream2, patchFileStream).executeAndSaveTo(patchedDexFile);
            }
            StreamUtil.closeQuietly(oldDexStream2);
            StreamUtil.closeQuietly(patchFileStream);
        } catch (Throwable th6) {
            th = th6;
            StreamUtil.closeQuietly(oldDexStream);
            StreamUtil.closeQuietly(patchFileStream);
            throw th;
        }
    }
}
