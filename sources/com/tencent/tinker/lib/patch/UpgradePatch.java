package com.tencent.tinker.lib.patch;

import android.content.Context;
import android.os.Build;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.io.IOException;

public class UpgradePatch extends AbstractPatch {
    private static final String TAG = "Tinker.UpgradePatch";

    public boolean tryPatch(Context context, String tempPatchPath, PatchResult patchResult) {
        SharePatchInfo newInfo;
        Tinker manager = Tinker.with(context);
        File patchFile = new File(tempPatchPath);
        if (!manager.isTinkerEnabled() || !ShareTinkerInternals.isTinkerEnableWithSharedPreferences(context)) {
            TinkerLog.e(TAG, "UpgradePatch tryPatch:patch is disabled, just return", new Object[0]);
            return false;
        } else if (!SharePatchFileUtil.isLegalFile(patchFile)) {
            TinkerLog.e(TAG, "UpgradePatch tryPatch:patch file is not found, just return", new Object[0]);
            return false;
        } else {
            ShareSecurityCheck shareSecurityCheck = new ShareSecurityCheck(context);
            int returnCode = ShareTinkerInternals.checkTinkerPackage(context, manager.getTinkerFlags(), patchFile, shareSecurityCheck);
            if (returnCode != 0) {
                TinkerLog.e(TAG, "UpgradePatch tryPatch:onPatchPackageCheckFail", new Object[0]);
                manager.getPatchReporter().onPatchPackageCheckFail(patchFile, returnCode);
                return false;
            }
            String patchMd5 = SharePatchFileUtil.getMD5(patchFile);
            if (patchMd5 == null) {
                TinkerLog.e(TAG, "UpgradePatch tryPatch:patch md5 is null, just return", new Object[0]);
                return false;
            }
            patchResult.patchVersion = patchMd5;
            TinkerLog.i(TAG, "UpgradePatch tryPatch:patchMd5:%s", patchMd5);
            String patchDirectory = manager.getPatchDirectory().getAbsolutePath();
            File patchInfoLockFile = SharePatchFileUtil.getPatchInfoLockFile(patchDirectory);
            File patchInfoFile = SharePatchFileUtil.getPatchInfoFile(patchDirectory);
            SharePatchInfo oldInfo = SharePatchInfo.readAndCheckPropertyWithLock(patchInfoFile, patchInfoLockFile);
            if (oldInfo == null) {
                newInfo = new SharePatchInfo("", patchMd5, Build.FINGERPRINT, "odex");
            } else if (oldInfo.oldVersion == null || oldInfo.newVersion == null || oldInfo.oatDir == null) {
                TinkerLog.e(TAG, "UpgradePatch tryPatch:onPatchInfoCorrupted", new Object[0]);
                manager.getPatchReporter().onPatchInfoCorrupted(patchFile, oldInfo.oldVersion, oldInfo.newVersion);
                return false;
            } else if (!SharePatchFileUtil.checkIfMd5Valid(patchMd5)) {
                TinkerLog.e(TAG, "UpgradePatch tryPatch:onPatchVersionCheckFail md5 %s is valid", patchMd5);
                manager.getPatchReporter().onPatchVersionCheckFail(patchFile, oldInfo, patchMd5);
                return false;
            } else {
                newInfo = new SharePatchInfo(oldInfo.oldVersion, patchMd5, Build.FINGERPRINT, oldInfo.oatDir.equals(ShareConstants.INTERPRET_DEX_OPTIMIZE_PATH) ? ShareConstants.CHANING_DEX_OPTIMIZE_PATH : oldInfo.oatDir);
            }
            String patchVersionDirectory = patchDirectory + "/" + SharePatchFileUtil.getPatchVersionDirectory(patchMd5);
            TinkerLog.i(TAG, "UpgradePatch tryPatch:patchVersionDirectory:%s", patchVersionDirectory);
            File destPatchFile = new File(patchVersionDirectory + "/" + SharePatchFileUtil.getPatchVersionFile(patchMd5));
            try {
                if (!patchMd5.equals(SharePatchFileUtil.getMD5(destPatchFile))) {
                    SharePatchFileUtil.copyFileUsingStream(patchFile, destPatchFile);
                    TinkerLog.w(TAG, "UpgradePatch copy patch file, src file: %s size: %d, dest file: %s size:%d", patchFile.getAbsolutePath(), Long.valueOf(patchFile.length()), destPatchFile.getAbsolutePath(), Long.valueOf(destPatchFile.length()));
                }
                if (!DexDiffPatchInternal.tryRecoverDexFiles(manager, shareSecurityCheck, context, patchVersionDirectory, destPatchFile)) {
                    TinkerLog.e(TAG, "UpgradePatch tryPatch:new patch recover, try patch dex failed", new Object[0]);
                    return false;
                } else if (!BsDiffPatchInternal.tryRecoverLibraryFiles(manager, shareSecurityCheck, context, patchVersionDirectory, destPatchFile)) {
                    TinkerLog.e(TAG, "UpgradePatch tryPatch:new patch recover, try patch library failed", new Object[0]);
                    return false;
                } else if (!ResDiffPatchInternal.tryRecoverResourceFiles(manager, shareSecurityCheck, context, patchVersionDirectory, destPatchFile)) {
                    TinkerLog.e(TAG, "UpgradePatch tryPatch:new patch recover, try patch resource failed", new Object[0]);
                    return false;
                } else if (!DexDiffPatchInternal.waitAndCheckDexOptFile(patchFile, manager)) {
                    TinkerLog.e(TAG, "UpgradePatch tryPatch:new patch recover, check dex opt file failed", new Object[0]);
                    return false;
                } else if (!SharePatchInfo.rewritePatchInfoFileWithLock(patchInfoFile, newInfo, patchInfoLockFile)) {
                    TinkerLog.e(TAG, "UpgradePatch tryPatch:new patch recover, rewrite patch info failed", new Object[0]);
                    manager.getPatchReporter().onPatchInfoCorrupted(patchFile, newInfo.oldVersion, newInfo.newVersion);
                    return false;
                } else {
                    TinkerLog.w(TAG, "UpgradePatch tryPatch: done, it is ok", new Object[0]);
                    return true;
                }
            } catch (IOException e) {
                TinkerLog.e(TAG, "UpgradePatch tryPatch:copy patch file fail from %s to %s", patchFile.getPath(), destPatchFile.getPath());
                manager.getPatchReporter().onPatchTypeExtractFail(patchFile, destPatchFile, patchFile.getName(), 1);
                return false;
            }
        }
    }
}
