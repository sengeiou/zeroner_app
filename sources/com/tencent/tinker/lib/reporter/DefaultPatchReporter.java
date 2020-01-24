package com.tencent.tinker.lib.reporter;

import android.content.Context;
import android.content.Intent;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.util.List;

public class DefaultPatchReporter implements PatchReporter {
    private static final String TAG = "Tinker.DefaultPatchReporter";
    private static boolean shouldRetry = false;
    protected final Context context;

    public DefaultPatchReporter(Context context2) {
        this.context = context2;
    }

    public void onPatchServiceStart(Intent intent) {
        TinkerLog.i(TAG, "patchReporter onPatchServiceStart: patch service start", new Object[0]);
        shouldRetry = false;
        UpgradePatchRetry.getInstance(this.context).onPatchServiceStart(intent);
    }

    public void onPatchPackageCheckFail(File patchFile, int errorCode) {
        TinkerLog.i(TAG, "patchReporter onPatchPackageCheckFail: package check failed. path: %s, code: %d", patchFile.getAbsolutePath(), Integer.valueOf(errorCode));
        if (errorCode == -3 || errorCode == -4 || errorCode == -8) {
            Tinker.with(this.context).cleanPatchByVersion(patchFile);
        }
    }

    public void onPatchVersionCheckFail(File patchFile, SharePatchInfo oldPatchInfo, String patchFileVersion) {
        TinkerLog.i(TAG, "patchReporter onPatchVersionCheckFail: patch version exist. path: %s, version: %s", patchFile.getAbsolutePath(), patchFileVersion);
    }

    public void onPatchTypeExtractFail(File patchFile, File extractTo, String filename, int fileType) {
        TinkerLog.i(TAG, "patchReporter onPatchTypeExtractFail: file extract fail type: %s, path: %s, extractTo: %s, filename: %s", ShareTinkerInternals.getTypeString(fileType), patchFile.getPath(), extractTo.getPath(), filename);
        Tinker.with(this.context).cleanPatchByVersion(patchFile);
    }

    public void onPatchDexOptFail(File patchFile, List<File> dexFiles, Throwable t) {
        TinkerLog.i(TAG, "patchReporter onPatchDexOptFail: dex opt fail path: %s, dex size: %d", patchFile.getAbsolutePath(), Integer.valueOf(dexFiles.size()));
        TinkerLog.printErrStackTrace(TAG, t, "onPatchDexOptFail:", new Object[0]);
        if (t.getMessage().contains(ShareConstants.CHECK_DEX_OAT_EXIST_FAIL) || t.getMessage().contains(ShareConstants.CHECK_DEX_OAT_FORMAT_FAIL)) {
            shouldRetry = true;
            deleteOptFiles(dexFiles);
            return;
        }
        Tinker.with(this.context).cleanPatchByVersion(patchFile);
    }

    public void onPatchResult(File patchFile, boolean success, long cost) {
        TinkerLog.i(TAG, "patchReporter onPatchResult: patch all result path: %s, success: %b, cost: %d", patchFile.getAbsolutePath(), Boolean.valueOf(success), Long.valueOf(cost));
        if (!shouldRetry) {
            UpgradePatchRetry.getInstance(this.context).onPatchServiceResult();
        }
    }

    public void onPatchInfoCorrupted(File patchFile, String oldVersion, String newVersion) {
        TinkerLog.i(TAG, "patchReporter onPatchInfoCorrupted: patch info is corrupted. old: %s, new: %s", oldVersion, newVersion);
        Tinker.with(this.context).cleanPatch();
    }

    public void onPatchException(File patchFile, Throwable e) {
        TinkerLog.i(TAG, "patchReporter onPatchException: patch exception path: %s, throwable: %s", patchFile.getAbsolutePath(), e.getMessage());
        TinkerLog.e(TAG, "tinker patch exception, welcome to submit issue to us: https://github.com/Tencent/tinker/issues", new Object[0]);
        TinkerLog.printErrStackTrace(TAG, e, "tinker patch exception", new Object[0]);
        Tinker.with(this.context).setTinkerDisable();
        Tinker.with(this.context).cleanPatchByVersion(patchFile);
    }

    private void deleteOptFiles(List<File> dexFiles) {
        for (File file : dexFiles) {
            SharePatchFileUtil.safeDeleteFile(file);
        }
    }
}
