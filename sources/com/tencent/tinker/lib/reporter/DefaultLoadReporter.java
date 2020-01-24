package com.tencent.tinker.lib.reporter;

import android.content.Context;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.tinker.TinkerLoadResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;

public class DefaultLoadReporter implements LoadReporter {
    private static final String TAG = "Tinker.DefaultLoadReporter";
    /* access modifiers changed from: protected */
    public final Context context;

    public DefaultLoadReporter(Context context2) {
        this.context = context2;
    }

    public void onLoadPatchListenerReceiveFail(File patchFile, int errorCode) {
        TinkerLog.i(TAG, "patch loadReporter onLoadPatchListenerReceiveFail: patch receive fail: %s, code: %d", patchFile.getAbsolutePath(), Integer.valueOf(errorCode));
    }

    public void onLoadPatchVersionChanged(String oldVersion, String newVersion, File patchDirectoryFile, String currentPatchName) {
        TinkerLog.i(TAG, "patch loadReporter onLoadPatchVersionChanged: patch version change from " + oldVersion + " to " + newVersion, new Object[0]);
        if (oldVersion != null && newVersion != null && !oldVersion.equals(newVersion) && Tinker.with(this.context).isMainProcess()) {
            TinkerLog.i(TAG, "onLoadPatchVersionChanged, try kill all other process", new Object[0]);
            ShareTinkerInternals.killAllOtherProcess(this.context);
            UpgradePatchRetry.getInstance(this.context).onPatchResetMaxCheck(newVersion);
            File[] files = patchDirectoryFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();
                    if (file.isDirectory() && !name.equals(currentPatchName)) {
                        SharePatchFileUtil.deleteDir(file);
                    }
                }
            }
        }
    }

    public void onLoadInterpret(int type, Throwable e) {
        TinkerLog.i(TAG, "patch loadReporter onLoadInterpret: type: %d, throwable: %s", Integer.valueOf(type), e);
        switch (type) {
            case 0:
                TinkerLog.i(TAG, "patch loadReporter onLoadInterpret ok", new Object[0]);
                break;
            case 1:
                TinkerLog.e(TAG, "patch loadReporter onLoadInterpret fail, can get instruction set from existed oat file", new Object[0]);
                break;
            case 2:
                TinkerLog.e(TAG, "patch loadReporter onLoadInterpret fail, command line to interpret return error", new Object[0]);
                break;
        }
        retryPatch();
    }

    public void onLoadFileNotFound(File file, int fileType, boolean isDirectory) {
        TinkerLog.i(TAG, "patch loadReporter onLoadFileNotFound: patch file not found: %s, fileType: %d, isDirectory: %b", file.getAbsolutePath(), Integer.valueOf(fileType), Boolean.valueOf(isDirectory));
        if (fileType == 4) {
            retryPatch();
        } else {
            checkAndCleanPatch();
        }
    }

    public void onLoadFileMd5Mismatch(File file, int fileType) {
        TinkerLog.i(TAG, "patch load Reporter onLoadFileMd5Mismatch: patch file md5 mismatch file: %s, fileType: %d", file.getAbsolutePath(), Integer.valueOf(fileType));
        checkAndCleanPatch();
    }

    public void onLoadPatchInfoCorrupted(String oldVersion, String newVersion, File patchInfoFile) {
        TinkerLog.i(TAG, "patch loadReporter onLoadPatchInfoCorrupted: patch info file damage: %s, from version: %s to version: %s", patchInfoFile.getAbsolutePath(), oldVersion, newVersion);
        checkAndCleanPatch();
    }

    public void onLoadResult(File patchDirectory, int loadCode, long cost) {
        TinkerLog.i(TAG, "patch loadReporter onLoadResult: patch load result, path:%s, code: %d, cost: %dms", patchDirectory.getAbsolutePath(), Integer.valueOf(loadCode), Long.valueOf(cost));
    }

    public void onLoadException(Throwable e, int errorCode) {
        switch (errorCode) {
            case -4:
                TinkerLog.i(TAG, "patch loadReporter onLoadException: patch load unCatch exception: %s", e);
                ShareTinkerInternals.setTinkerDisableWithSharedPreferences(this.context);
                TinkerLog.i(TAG, "unCaught exception disable tinker forever with sp", new Object[0]);
                String uncaughtString = SharePatchFileUtil.checkTinkerLastUncaughtCrash(this.context);
                if (!ShareTinkerInternals.isNullOrNil(uncaughtString)) {
                    SharePatchFileUtil.safeDeleteFile(SharePatchFileUtil.getPatchLastCrashFile(this.context));
                    TinkerLog.e(TAG, "tinker uncaught real exception:" + uncaughtString, new Object[0]);
                    break;
                }
                break;
            case -3:
                if (e.getMessage().contains(ShareConstants.CHECK_RES_INSTALL_FAIL)) {
                    TinkerLog.e(TAG, "patch loadReporter onLoadException: tinker res check fail:" + e.getMessage(), new Object[0]);
                } else {
                    TinkerLog.i(TAG, "patch loadReporter onLoadException: patch load resource exception: %s", e);
                }
                ShareTinkerInternals.setTinkerDisableWithSharedPreferences(this.context);
                TinkerLog.i(TAG, "res exception disable tinker forever with sp", new Object[0]);
                break;
            case -2:
                if (e.getMessage().contains(ShareConstants.CHECK_DEX_INSTALL_FAIL)) {
                    TinkerLog.e(TAG, "patch loadReporter onLoadException: tinker dex check fail:" + e.getMessage(), new Object[0]);
                } else {
                    TinkerLog.i(TAG, "patch loadReporter onLoadException: patch load dex exception: %s", e);
                }
                ShareTinkerInternals.setTinkerDisableWithSharedPreferences(this.context);
                TinkerLog.i(TAG, "dex exception disable tinker forever with sp", new Object[0]);
                break;
            case -1:
                TinkerLog.i(TAG, "patch loadReporter onLoadException: patch load unknown exception: %s", e);
                break;
        }
        TinkerLog.e(TAG, "tinker load exception, welcome to submit issue to us: https://github.com/Tencent/tinker/issues", new Object[0]);
        TinkerLog.printErrStackTrace(TAG, e, "tinker load exception", new Object[0]);
        Tinker.with(this.context).setTinkerDisable();
        checkAndCleanPatch();
    }

    public void onLoadPackageCheckFail(File patchFile, int errorCode) {
        TinkerLog.i(TAG, "patch loadReporter onLoadPackageCheckFail: load patch package check fail file path: %s, errorCode: %d", patchFile.getAbsolutePath(), Integer.valueOf(errorCode));
        checkAndCleanPatch();
    }

    public void checkAndCleanPatch() {
        Tinker tinker = Tinker.with(this.context);
        if (tinker.isMainProcess()) {
            TinkerLoadResult tinkerLoadResult = tinker.getTinkerLoadResultIfPresent();
            if (tinkerLoadResult.versionChanged) {
                SharePatchInfo sharePatchInfo = tinkerLoadResult.patchInfo;
                if (sharePatchInfo != null && !ShareTinkerInternals.isNullOrNil(sharePatchInfo.oldVersion)) {
                    TinkerLog.w(TAG, "checkAndCleanPatch, oldVersion %s is not null, try kill all other process", sharePatchInfo.oldVersion);
                    ShareTinkerInternals.killAllOtherProcess(this.context);
                }
            }
        }
        tinker.cleanPatch();
    }

    public boolean retryPatch() {
        Tinker tinker = Tinker.with(this.context);
        if (!tinker.isMainProcess()) {
            return false;
        }
        File patchVersionFile = tinker.getTinkerLoadResultIfPresent().patchVersionFile;
        if (patchVersionFile == null || !UpgradePatchRetry.getInstance(this.context).onPatchListenerCheck(SharePatchFileUtil.getMD5(patchVersionFile))) {
            return false;
        }
        TinkerLog.i(TAG, "try to repair oat file on patch process", new Object[0]);
        TinkerInstaller.onReceiveUpgradePatch(this.context, patchVersionFile.getAbsolutePath());
        return true;
    }
}
