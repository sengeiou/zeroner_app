package com.tencent.bugly.beta.tinker;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import java.io.File;

/* compiled from: BUGLY */
public class TinkerLoadReporter extends DefaultLoadReporter {
    private static final String TAG = "Tinker.TinkerLoadReporter";
    private final LoadReporter userLoadReporter = TinkerManager.userLoadReporter;

    public TinkerLoadReporter(Context context) {
        super(context);
    }

    public void onLoadPatchListenerReceiveFail(File patchFile, int errorCode) {
        super.onLoadPatchListenerReceiveFail(patchFile, errorCode);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadPatchListenerReceiveFail(patchFile, errorCode);
        } else {
            TinkerReport.onTryApplyFail(errorCode);
        }
    }

    public void onLoadResult(File patchDirectory, int loadCode, long cost) {
        super.onLoadResult(patchDirectory, loadCode, cost);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadResult(patchDirectory, loadCode, cost);
            return;
        }
        switch (loadCode) {
            case 0:
                TinkerReport.onLoaded(cost);
                break;
        }
        Looper.getMainLooper();
        Looper.myQueue().addIdleHandler(new IdleHandler() {
            public boolean queueIdle() {
                if (UpgradePatchRetry.getInstance(TinkerLoadReporter.this.context).onPatchRetryLoad()) {
                    TinkerReport.onReportRetryPatch();
                }
                return false;
            }
        });
    }

    public void onLoadException(Throwable e, int errorCode) {
        super.onLoadException(e, errorCode);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadException(e, errorCode);
        } else {
            TinkerReport.onLoadException(e, errorCode);
        }
    }

    public void onLoadFileMd5Mismatch(File file, int fileType) {
        super.onLoadFileMd5Mismatch(file, fileType);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadFileMd5Mismatch(file, fileType);
        } else {
            TinkerReport.onLoadFileMisMatch(fileType);
        }
    }

    public void onLoadFileNotFound(File file, int fileType, boolean isDirectory) {
        super.onLoadFileNotFound(file, fileType, isDirectory);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadFileNotFound(file, fileType, isDirectory);
            return;
        }
        TinkerLog.i(TAG, "patch loadReporter onLoadFileNotFound: patch file not found: %s, fileType:%d, isDirectory:%b", file.getAbsolutePath(), Integer.valueOf(fileType), Boolean.valueOf(isDirectory));
        if (fileType == 4) {
            Tinker with = Tinker.with(this.context);
            if (with.isMainProcess()) {
                File file2 = with.getTinkerLoadResultIfPresent().patchVersionFile;
                if (file2 != null) {
                    if (UpgradePatchRetry.getInstance(this.context).onPatchListenerCheck(SharePatchFileUtil.getMD5(file2))) {
                        TinkerLog.i(TAG, "try to repair oat file on patch process", new Object[0]);
                        TinkerInstaller.onReceiveUpgradePatch(this.context, file2.getAbsolutePath());
                    } else {
                        TinkerLog.i(TAG, "repair retry exceed must max time, just clean", new Object[0]);
                        checkAndCleanPatch();
                    }
                }
            }
        } else {
            checkAndCleanPatch();
        }
        TinkerReport.onLoadFileNotFound(fileType);
    }

    public void onLoadPackageCheckFail(File patchFile, int errorCode) {
        super.onLoadPackageCheckFail(patchFile, errorCode);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadPackageCheckFail(patchFile, errorCode);
        } else {
            TinkerReport.onLoadPackageCheckFail(errorCode);
        }
    }

    public void onLoadPatchInfoCorrupted(String oldVersion, String newVersion, File patchInfoFile) {
        super.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile);
        } else {
            TinkerReport.onLoadInfoCorrupted();
        }
    }

    public void onLoadInterpret(int type, Throwable e) {
        super.onLoadInterpret(type, e);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadInterpret(type, e);
        } else {
            TinkerReport.onLoadInterpretReport(type, e);
        }
    }

    public void onLoadPatchVersionChanged(String oldVersion, String newVersion, File patchDirectoryFile, String currentPatchName) {
        super.onLoadPatchVersionChanged(oldVersion, newVersion, patchDirectoryFile, currentPatchName);
        if (this.userLoadReporter != null) {
            this.userLoadReporter.onLoadPatchVersionChanged(oldVersion, newVersion, patchDirectoryFile, currentPatchName);
        }
    }
}
