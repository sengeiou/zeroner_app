package com.tencent.tinker.lib.patch;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.SystemClock;
import com.tencent.tinker.bsdiff.BSPatch;
import com.tencent.tinker.commons.util.StreamUtil;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareResPatchInfo;
import com.tencent.tinker.loader.shareutil.ShareResPatchInfo.LargeModeInfo;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import com.tencent.tinker.ziputils.ziputil.TinkerZipEntry;
import com.tencent.tinker.ziputils.ziputil.TinkerZipFile;
import com.tencent.tinker.ziputils.ziputil.TinkerZipOutputStream;
import com.tencent.tinker.ziputils.ziputil.TinkerZipUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResDiffPatchInternal extends BasePatchInternal {
    protected static final String TAG = "Tinker.ResDiffPatchInternal";

    protected static boolean tryRecoverResourceFiles(Tinker manager, ShareSecurityCheck checker, Context context, String patchVersionDirectory, File patchFile) {
        if (!manager.isEnabledForResource()) {
            TinkerLog.w(TAG, "patch recover, resource is not enabled", new Object[0]);
            return true;
        }
        String resourceMeta = (String) checker.getMetaContentMap().get(ShareConstants.RES_META_FILE);
        if (resourceMeta == null || resourceMeta.length() == 0) {
            TinkerLog.w(TAG, "patch recover, resource is not contained", new Object[0]);
            return true;
        }
        long begin = SystemClock.elapsedRealtime();
        boolean result = patchResourceExtractViaResourceDiff(context, patchVersionDirectory, resourceMeta, patchFile);
        TinkerLog.i(TAG, "recover resource result:%b, cost:%d", Boolean.valueOf(result), Long.valueOf(SystemClock.elapsedRealtime() - begin));
        return result;
    }

    private static boolean patchResourceExtractViaResourceDiff(Context context, String patchVersionDirectory, String meta, File patchFile) {
        if (extractResourceDiffInternals(context, patchVersionDirectory + "/" + ShareConstants.RES_PATH + "/", meta, patchFile, 6)) {
            return true;
        }
        TinkerLog.w(TAG, "patch recover, extractDiffInternals fail", new Object[0]);
        return false;
    }

    private static boolean extractResourceDiffInternals(Context context, String dir, String meta, File patchFile, int type) {
        TinkerZipFile tinkerZipFile;
        ShareResPatchInfo resPatchInfo = new ShareResPatchInfo();
        ShareResPatchInfo.parseAllResPatchInfo(meta, resPatchInfo);
        TinkerLog.i(TAG, "res dir: %s, meta: %s", dir, resPatchInfo.toString());
        Tinker manager = Tinker.with(context);
        if (!SharePatchFileUtil.checkIfMd5Valid(resPatchInfo.resArscMd5)) {
            TinkerLog.w(TAG, "resource meta file md5 mismatch, type:%s, md5: %s", ShareTinkerInternals.getTypeString(type), resPatchInfo.resArscMd5);
            manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
            return false;
        }
        File directory = new File(dir);
        File tempResFileDirectory = new File(directory, "res_temp");
        File file = new File(directory, ShareConstants.RES_NAME);
        if (file.exists()) {
            if (SharePatchFileUtil.checkResourceArscMd5(file, resPatchInfo.resArscMd5)) {
                TinkerLog.w(TAG, "resource file %s is already exist, and md5 match, just return true", file.getPath());
                return true;
            }
            TinkerLog.w(TAG, "have a mismatch corrupted resource " + file.getPath(), new Object[0]);
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                TinkerLog.w(TAG, "applicationInfo == null!!!!", new Object[0]);
                return false;
            }
            String apkPath = applicationInfo.sourceDir;
            if (!checkAndExtractResourceLargeFile(context, apkPath, directory, tempResFileDirectory, patchFile, resPatchInfo, type)) {
                return false;
            }
            TinkerZipOutputStream out = null;
            TinkerZipFile oldApk = null;
            TinkerZipFile newApk = null;
            int totalEntryCount = 0;
            try {
                TinkerZipOutputStream tinkerZipOutputStream = new TinkerZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                try {
                    tinkerZipFile = new TinkerZipFile(apkPath);
                } catch (Throwable th) {
                    th = th;
                    out = tinkerZipOutputStream;
                    StreamUtil.closeQuietly(out);
                    StreamUtil.closeQuietly(oldApk);
                    StreamUtil.closeQuietly(newApk);
                    SharePatchFileUtil.deleteDir(tempResFileDirectory);
                    throw th;
                }
                try {
                    TinkerZipFile tinkerZipFile2 = new TinkerZipFile(patchFile);
                    try {
                        Enumeration<? extends TinkerZipEntry> entries = tinkerZipFile.entries();
                        while (entries.hasMoreElements()) {
                            TinkerZipEntry zipEntry = (TinkerZipEntry) entries.nextElement();
                            if (zipEntry == null) {
                                throw new TinkerRuntimeException("zipEntry is null when get from oldApk");
                            }
                            String name = zipEntry.getName();
                            if (!name.contains("../") && ShareResPatchInfo.checkFileInPattern(resPatchInfo.patterns, name) && !resPatchInfo.deleteRes.contains(name) && !resPatchInfo.modRes.contains(name) && !resPatchInfo.largeModRes.contains(name)) {
                                if (!name.equals(ShareConstants.RES_MANIFEST)) {
                                    TinkerZipUtil.extractTinkerEntry(tinkerZipFile, zipEntry, tinkerZipOutputStream);
                                    totalEntryCount++;
                                }
                            }
                        }
                        TinkerZipEntry manifestZipEntry = tinkerZipFile.getEntry(ShareConstants.RES_MANIFEST);
                        if (manifestZipEntry == null) {
                            TinkerLog.w(TAG, "manifest patch entry is null. path:AndroidManifest.xml", new Object[0]);
                            manager.getPatchReporter().onPatchTypeExtractFail(patchFile, file, ShareConstants.RES_MANIFEST, type);
                            StreamUtil.closeQuietly(tinkerZipOutputStream);
                            StreamUtil.closeQuietly(tinkerZipFile);
                            StreamUtil.closeQuietly(tinkerZipFile2);
                            SharePatchFileUtil.deleteDir(tempResFileDirectory);
                            return false;
                        }
                        TinkerZipUtil.extractTinkerEntry(tinkerZipFile, manifestZipEntry, tinkerZipOutputStream);
                        int totalEntryCount2 = totalEntryCount + 1;
                        Iterator it = resPatchInfo.largeModRes.iterator();
                        while (it.hasNext()) {
                            String name2 = (String) it.next();
                            TinkerZipEntry largeZipEntry = tinkerZipFile.getEntry(name2);
                            if (largeZipEntry == null) {
                                TinkerLog.w(TAG, "large patch entry is null. path:" + name2, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, file, name2, type);
                                StreamUtil.closeQuietly(tinkerZipOutputStream);
                                StreamUtil.closeQuietly(tinkerZipFile);
                                StreamUtil.closeQuietly(tinkerZipFile2);
                                SharePatchFileUtil.deleteDir(tempResFileDirectory);
                                return false;
                            }
                            LargeModeInfo largeModeInfo = (LargeModeInfo) resPatchInfo.largeModMap.get(name2);
                            TinkerZipUtil.extractLargeModifyFile(largeZipEntry, largeModeInfo.file, largeModeInfo.crc, tinkerZipOutputStream);
                            totalEntryCount2++;
                        }
                        Iterator it2 = resPatchInfo.addRes.iterator();
                        while (it2.hasNext()) {
                            String name3 = (String) it2.next();
                            TinkerZipEntry addZipEntry = tinkerZipFile2.getEntry(name3);
                            if (addZipEntry == null) {
                                TinkerLog.w(TAG, "add patch entry is null. path:" + name3, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, file, name3, type);
                                StreamUtil.closeQuietly(tinkerZipOutputStream);
                                StreamUtil.closeQuietly(tinkerZipFile);
                                StreamUtil.closeQuietly(tinkerZipFile2);
                                SharePatchFileUtil.deleteDir(tempResFileDirectory);
                                return false;
                            }
                            if (resPatchInfo.storeRes.containsKey(name3)) {
                                TinkerZipUtil.extractLargeModifyFile(addZipEntry, (File) resPatchInfo.storeRes.get(name3), addZipEntry.getCrc(), tinkerZipOutputStream);
                            } else {
                                TinkerZipUtil.extractTinkerEntry(tinkerZipFile2, addZipEntry, tinkerZipOutputStream);
                            }
                            totalEntryCount2++;
                        }
                        Iterator it3 = resPatchInfo.modRes.iterator();
                        while (it3.hasNext()) {
                            String name4 = (String) it3.next();
                            TinkerZipEntry modZipEntry = tinkerZipFile2.getEntry(name4);
                            if (modZipEntry == null) {
                                TinkerLog.w(TAG, "mod patch entry is null. path:" + name4, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, file, name4, type);
                                StreamUtil.closeQuietly(tinkerZipOutputStream);
                                StreamUtil.closeQuietly(tinkerZipFile);
                                StreamUtil.closeQuietly(tinkerZipFile2);
                                SharePatchFileUtil.deleteDir(tempResFileDirectory);
                                return false;
                            }
                            if (resPatchInfo.storeRes.containsKey(name4)) {
                                TinkerZipUtil.extractLargeModifyFile(modZipEntry, (File) resPatchInfo.storeRes.get(name4), modZipEntry.getCrc(), tinkerZipOutputStream);
                            } else {
                                TinkerZipUtil.extractTinkerEntry(tinkerZipFile2, modZipEntry, tinkerZipOutputStream);
                            }
                            totalEntryCount2++;
                        }
                        tinkerZipOutputStream.setComment(tinkerZipFile.getComment());
                        StreamUtil.closeQuietly(tinkerZipOutputStream);
                        StreamUtil.closeQuietly(tinkerZipFile);
                        StreamUtil.closeQuietly(tinkerZipFile2);
                        SharePatchFileUtil.deleteDir(tempResFileDirectory);
                        if (!SharePatchFileUtil.checkResourceArscMd5(file, resPatchInfo.resArscMd5)) {
                            TinkerLog.i(TAG, "check final new resource file fail path:%s, entry count:%d, size:%d", file.getAbsolutePath(), Integer.valueOf(totalEntryCount2), Long.valueOf(file.length()));
                            SharePatchFileUtil.safeDeleteFile(file);
                            manager.getPatchReporter().onPatchTypeExtractFail(patchFile, file, ShareConstants.RES_NAME, type);
                            return false;
                        }
                        TinkerLog.i(TAG, "final new resource file:%s, entry count:%d, size:%d", file.getAbsolutePath(), Integer.valueOf(totalEntryCount2), Long.valueOf(file.length()));
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        newApk = tinkerZipFile2;
                        oldApk = tinkerZipFile;
                        out = tinkerZipOutputStream;
                        StreamUtil.closeQuietly(out);
                        StreamUtil.closeQuietly(oldApk);
                        StreamUtil.closeQuietly(newApk);
                        SharePatchFileUtil.deleteDir(tempResFileDirectory);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    oldApk = tinkerZipFile;
                    out = tinkerZipOutputStream;
                    StreamUtil.closeQuietly(out);
                    StreamUtil.closeQuietly(oldApk);
                    StreamUtil.closeQuietly(newApk);
                    SharePatchFileUtil.deleteDir(tempResFileDirectory);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                StreamUtil.closeQuietly(out);
                StreamUtil.closeQuietly(oldApk);
                StreamUtil.closeQuietly(newApk);
                SharePatchFileUtil.deleteDir(tempResFileDirectory);
                throw th;
            }
        } catch (Throwable e) {
            throw new TinkerRuntimeException("patch " + ShareTinkerInternals.getTypeString(type) + " extract failed (" + e.getMessage() + ").", e);
        }
    }

    private static boolean checkAndExtractResourceLargeFile(Context context, String apkPath, File directory, File tempFileDirtory, File patchFile, ShareResPatchInfo resPatchInfo, int type) {
        InputStream oldStream;
        InputStream newStream;
        long start = System.currentTimeMillis();
        Tinker manager = Tinker.with(context);
        ZipFile apkFile = null;
        ZipFile patchZipFile = null;
        try {
            ZipFile apkFile2 = new ZipFile(apkPath);
            try {
                ZipEntry arscEntry = apkFile2.getEntry(ShareConstants.RES_ARSC);
                File arscFile = new File(directory, ShareConstants.RES_ARSC);
                if (arscEntry == null) {
                    TinkerLog.w(TAG, "resources apk entry is null. path:resources.arsc", new Object[0]);
                    manager.getPatchReporter().onPatchTypeExtractFail(patchFile, arscFile, ShareConstants.RES_ARSC, type);
                    SharePatchFileUtil.closeZip(apkFile2);
                    SharePatchFileUtil.closeZip(null);
                    return false;
                }
                String baseArscCrc = String.valueOf(arscEntry.getCrc());
                if (!baseArscCrc.equals(resPatchInfo.arscBaseCrc)) {
                    TinkerLog.e(TAG, "resources.arsc's crc is not equal, expect crc: %s, got crc: %s", resPatchInfo.arscBaseCrc, baseArscCrc);
                    manager.getPatchReporter().onPatchTypeExtractFail(patchFile, arscFile, ShareConstants.RES_ARSC, type);
                    SharePatchFileUtil.closeZip(apkFile2);
                    SharePatchFileUtil.closeZip(null);
                    return false;
                } else if (!resPatchInfo.largeModRes.isEmpty() || !resPatchInfo.storeRes.isEmpty()) {
                    ZipFile zipFile = new ZipFile(patchFile);
                    try {
                        for (String name : resPatchInfo.storeRes.keySet()) {
                            long storeStart = System.currentTimeMillis();
                            File destCopy = new File(tempFileDirtory, name);
                            SharePatchFileUtil.ensureFileDirectory(destCopy);
                            ZipEntry patchEntry = zipFile.getEntry(name);
                            if (patchEntry == null) {
                                TinkerLog.w(TAG, "store patch entry is null. path:" + name, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, destCopy, name, type);
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile2 = zipFile;
                                return false;
                            }
                            extract(zipFile, patchEntry, destCopy, null, false);
                            if (patchEntry.getSize() != destCopy.length()) {
                                TinkerLog.w(TAG, "resource meta file size mismatch, type:%s, name: %s, patch size: %d, file size; %d", ShareTinkerInternals.getTypeString(type), name, Long.valueOf(patchEntry.getSize()), Long.valueOf(destCopy.length()));
                                manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile3 = zipFile;
                                return false;
                            }
                            resPatchInfo.storeRes.put(name, destCopy);
                            TinkerLog.w(TAG, "success recover store file:%s, file size:%d, use time:%d", destCopy.getPath(), Long.valueOf(destCopy.length()), Long.valueOf(System.currentTimeMillis() - storeStart));
                        }
                        Iterator it = resPatchInfo.largeModRes.iterator();
                        while (it.hasNext()) {
                            String name2 = (String) it.next();
                            long largeStart = System.currentTimeMillis();
                            LargeModeInfo largeModeInfo = (LargeModeInfo) resPatchInfo.largeModMap.get(name2);
                            if (largeModeInfo == null) {
                                TinkerLog.w(TAG, "resource not found largeModeInfo, type:%s, name: %s", ShareTinkerInternals.getTypeString(type), name2);
                                manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile4 = zipFile;
                                return false;
                            }
                            File file = new File(tempFileDirtory, name2);
                            largeModeInfo.file = file;
                            SharePatchFileUtil.ensureFileDirectory(largeModeInfo.file);
                            if (!SharePatchFileUtil.checkIfMd5Valid(largeModeInfo.md5)) {
                                TinkerLog.w(TAG, "resource meta file md5 mismatch, type:%s, name: %s, md5: %s", ShareTinkerInternals.getTypeString(type), name2, largeModeInfo.md5);
                                manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile5 = zipFile;
                                return false;
                            }
                            ZipEntry patchEntry2 = zipFile.getEntry(name2);
                            if (patchEntry2 == null) {
                                TinkerLog.w(TAG, "large mod patch entry is null. path:" + name2, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, largeModeInfo.file, name2, type);
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile6 = zipFile;
                                return false;
                            }
                            ZipEntry baseEntry = apkFile2.getEntry(name2);
                            if (baseEntry == null) {
                                TinkerLog.w(TAG, "resources apk entry is null. path:" + name2, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, largeModeInfo.file, name2, type);
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile7 = zipFile;
                                return false;
                            }
                            oldStream = null;
                            newStream = null;
                            oldStream = apkFile2.getInputStream(baseEntry);
                            newStream = zipFile.getInputStream(patchEntry2);
                            BSPatch.patchFast(oldStream, newStream, largeModeInfo.file);
                            StreamUtil.closeQuietly(oldStream);
                            StreamUtil.closeQuietly(newStream);
                            if (!SharePatchFileUtil.verifyFileMd5(largeModeInfo.file, largeModeInfo.md5)) {
                                TinkerLog.w(TAG, "Failed to recover large modify file:%s", largeModeInfo.file.getPath());
                                SharePatchFileUtil.safeDeleteFile(largeModeInfo.file);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, largeModeInfo.file, name2, type);
                                SharePatchFileUtil.closeZip(apkFile2);
                                SharePatchFileUtil.closeZip(zipFile);
                                ZipFile zipFile8 = zipFile;
                                return false;
                            }
                            TinkerLog.w(TAG, "success recover large modify file:%s, file size:%d, use time:%d", largeModeInfo.file.getPath(), Long.valueOf(largeModeInfo.file.length()), Long.valueOf(System.currentTimeMillis() - largeStart));
                        }
                        TinkerLog.w(TAG, "success recover all large modify and store resources use time:%d", Long.valueOf(System.currentTimeMillis() - start));
                        SharePatchFileUtil.closeZip(apkFile2);
                        SharePatchFileUtil.closeZip(zipFile);
                        ZipFile zipFile9 = zipFile;
                        return true;
                    } catch (Throwable th) {
                        th = th;
                        patchZipFile = zipFile;
                        apkFile = apkFile2;
                        SharePatchFileUtil.closeZip(apkFile);
                        SharePatchFileUtil.closeZip(patchZipFile);
                        throw th;
                    }
                } else {
                    TinkerLog.i(TAG, "no large modify or store resources, just return", new Object[0]);
                    SharePatchFileUtil.closeZip(apkFile2);
                    SharePatchFileUtil.closeZip(null);
                    return true;
                }
            } catch (Throwable th2) {
                th = th2;
                apkFile = apkFile2;
                SharePatchFileUtil.closeZip(apkFile);
                SharePatchFileUtil.closeZip(patchZipFile);
                throw th;
            }
        } catch (Throwable th3) {
            e = th3;
            TinkerRuntimeException tinkerRuntimeException = new TinkerRuntimeException("patch " + ShareTinkerInternals.getTypeString(type) + " extract failed (" + e.getMessage() + ").", e);
            throw tinkerRuntimeException;
        }
    }
}
