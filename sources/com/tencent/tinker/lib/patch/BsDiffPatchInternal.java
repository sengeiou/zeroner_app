package com.tencent.tinker.lib.patch;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.SystemClock;
import com.tencent.tinker.bsdiff.BSPatch;
import com.tencent.tinker.commons.util.StreamUtil;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.shareutil.ShareBsDiffPatchInfo;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BsDiffPatchInternal extends BasePatchInternal {
    private static final String TAG = "Tinker.BsDiffPatchInternal";

    protected static boolean tryRecoverLibraryFiles(Tinker manager, ShareSecurityCheck checker, Context context, String patchVersionDirectory, File patchFile) {
        if (!manager.isEnabledForNativeLib()) {
            TinkerLog.w(TAG, "patch recover, library is not enabled", new Object[0]);
            return true;
        }
        String libMeta = (String) checker.getMetaContentMap().get(ShareConstants.SO_META_FILE);
        if (libMeta == null) {
            TinkerLog.w(TAG, "patch recover, library is not contained", new Object[0]);
            return true;
        }
        long begin = SystemClock.elapsedRealtime();
        boolean result = patchLibraryExtractViaBsDiff(context, patchVersionDirectory, libMeta, patchFile);
        TinkerLog.i(TAG, "recover lib result:%b, cost:%d", Boolean.valueOf(result), Long.valueOf(SystemClock.elapsedRealtime() - begin));
        return result;
    }

    private static boolean patchLibraryExtractViaBsDiff(Context context, String patchVersionDirectory, String meta, File patchFile) {
        return extractBsDiffInternals(context, patchVersionDirectory + "/" + ShareConstants.SO_PATH + "/", meta, patchFile, 5);
    }

    private static boolean extractBsDiffInternals(Context context, String dir, String meta, File patchFile, int type) {
        String patchRealPath;
        InputStream oldStream;
        InputStream newStream;
        ArrayList<ShareBsDiffPatchInfo> patchList = new ArrayList<>();
        ShareBsDiffPatchInfo.parseDiffPatchInfo(meta, patchList);
        if (patchList.isEmpty()) {
            TinkerLog.w(TAG, "extract patch list is empty! type:%s:", ShareTinkerInternals.getTypeString(type));
            return true;
        }
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Tinker manager = Tinker.with(context);
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            TinkerLog.w(TAG, "applicationInfo == null!!!!", new Object[0]);
            return false;
        }
        ZipFile apk = null;
        ZipFile patch = null;
        try {
            ZipFile apk2 = new ZipFile(applicationInfo.sourceDir);
            try {
                ZipFile zipFile = new ZipFile(patchFile);
                try {
                    Iterator it = patchList.iterator();
                    while (it.hasNext()) {
                        ShareBsDiffPatchInfo info = (ShareBsDiffPatchInfo) it.next();
                        long start = System.currentTimeMillis();
                        if (info.path.equals("")) {
                            patchRealPath = info.name;
                        } else {
                            patchRealPath = info.path + "/" + info.name;
                        }
                        String fileMd5 = info.md5;
                        if (!SharePatchFileUtil.checkIfMd5Valid(fileMd5)) {
                            TinkerLog.w(TAG, "meta file md5 mismatch, type:%s, name: %s, md5: %s", ShareTinkerInternals.getTypeString(type), info.name, info.md5);
                            manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                            SharePatchFileUtil.closeZip(apk2);
                            SharePatchFileUtil.closeZip(zipFile);
                            return false;
                        }
                        File extractedFile = new File(dir + (info.path + "/" + info.name));
                        if (!extractedFile.exists()) {
                            extractedFile.getParentFile().mkdirs();
                        } else if (fileMd5.equals(SharePatchFileUtil.getMD5(extractedFile))) {
                            TinkerLog.w(TAG, "bsdiff file %s is already exist, and md5 match, just continue", extractedFile.getPath());
                        } else {
                            TinkerLog.w(TAG, "have a mismatch corrupted dex " + extractedFile.getPath(), new Object[0]);
                            extractedFile.delete();
                        }
                        String patchFileMd5 = info.patchMd5;
                        ZipEntry patchFileEntry = zipFile.getEntry(patchRealPath);
                        if (patchFileEntry == null) {
                            TinkerLog.w(TAG, "patch entry is null. path:" + patchRealPath, new Object[0]);
                            manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.name, type);
                            SharePatchFileUtil.closeZip(apk2);
                            SharePatchFileUtil.closeZip(zipFile);
                            return false;
                        } else if (patchFileMd5.equals("0")) {
                            if (!extract(zipFile, patchFileEntry, extractedFile, fileMd5, false)) {
                                TinkerLog.w(TAG, "Failed to extract file " + extractedFile.getPath(), new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.name, type);
                                SharePatchFileUtil.closeZip(apk2);
                                SharePatchFileUtil.closeZip(zipFile);
                                return false;
                            }
                        } else if (!SharePatchFileUtil.checkIfMd5Valid(patchFileMd5)) {
                            TinkerLog.w(TAG, "meta file md5 mismatch, type:%s, name: %s, md5: %s", ShareTinkerInternals.getTypeString(type), info.name, patchFileMd5);
                            manager.getPatchReporter().onPatchPackageCheckFail(patchFile, BasePatchInternal.getMetaCorruptedCode(type));
                            SharePatchFileUtil.closeZip(apk2);
                            SharePatchFileUtil.closeZip(zipFile);
                            return false;
                        } else {
                            ZipEntry rawApkFileEntry = apk2.getEntry(patchRealPath);
                            if (rawApkFileEntry == null) {
                                TinkerLog.w(TAG, "apk entry is null. path:" + patchRealPath, new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.name, type);
                                SharePatchFileUtil.closeZip(apk2);
                                SharePatchFileUtil.closeZip(zipFile);
                                return false;
                            }
                            String rawApkCrc = info.rawCrc;
                            String rawEntryCrc = String.valueOf(rawApkFileEntry.getCrc());
                            if (!rawEntryCrc.equals(rawApkCrc)) {
                                TinkerLog.e(TAG, "apk entry %s crc is not equal, expect crc: %s, got crc: %s", patchRealPath, rawApkCrc, rawEntryCrc);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.name, type);
                                SharePatchFileUtil.closeZip(apk2);
                                SharePatchFileUtil.closeZip(zipFile);
                                return false;
                            }
                            oldStream = null;
                            newStream = null;
                            oldStream = apk2.getInputStream(rawApkFileEntry);
                            newStream = zipFile.getInputStream(patchFileEntry);
                            BSPatch.patchFast(oldStream, newStream, extractedFile);
                            StreamUtil.closeQuietly(oldStream);
                            StreamUtil.closeQuietly(newStream);
                            if (!SharePatchFileUtil.verifyFileMd5(extractedFile, fileMd5)) {
                                TinkerLog.w(TAG, "Failed to recover diff file " + extractedFile.getPath(), new Object[0]);
                                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, extractedFile, info.name, type);
                                SharePatchFileUtil.safeDeleteFile(extractedFile);
                                SharePatchFileUtil.closeZip(apk2);
                                SharePatchFileUtil.closeZip(zipFile);
                                return false;
                            }
                            TinkerLog.w(TAG, "success recover bsdiff file: %s, use time: %d", extractedFile.getPath(), Long.valueOf(System.currentTimeMillis() - start));
                        }
                    }
                    SharePatchFileUtil.closeZip(apk2);
                    SharePatchFileUtil.closeZip(zipFile);
                    return true;
                } catch (Throwable th) {
                    th = th;
                    patch = zipFile;
                    apk = apk2;
                    SharePatchFileUtil.closeZip(apk);
                    SharePatchFileUtil.closeZip(patch);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                apk = apk2;
                SharePatchFileUtil.closeZip(apk);
                SharePatchFileUtil.closeZip(patch);
                throw th;
            }
        } catch (Throwable th3) {
            e = th3;
            TinkerRuntimeException tinkerRuntimeException = new TinkerRuntimeException("patch " + ShareTinkerInternals.getTypeString(type) + " extract failed (" + e.getMessage() + ").", e);
            throw tinkerRuntimeException;
        }
    }
}
