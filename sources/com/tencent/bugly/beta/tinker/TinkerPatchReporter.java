package com.tencent.bugly.beta.tinker;

import android.content.Context;
import android.content.Intent;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import java.io.File;
import java.util.List;

/* compiled from: BUGLY */
public class TinkerPatchReporter extends DefaultPatchReporter {
    private final PatchReporter userPatchReporter = TinkerManager.userPatchReporter;

    public TinkerPatchReporter(Context context) {
        super(context);
    }

    public void onPatchServiceStart(Intent intent) {
        super.onPatchServiceStart(intent);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchServiceStart(intent);
            return;
        }
        TinkerReport.onApplyPatchServiceStart();
        UpgradePatchRetry.getInstance(this.context).onPatchServiceStart(intent);
    }

    public void onPatchDexOptFail(File patchFile, List<File> dexFiles, Throwable t) {
        super.onPatchDexOptFail(patchFile, dexFiles, t);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchDexOptFail(patchFile, dexFiles, t);
        } else {
            TinkerReport.onApplyDexOptFail(t);
        }
    }

    public void onPatchException(File patchFile, Throwable e) {
        super.onPatchException(patchFile, e);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchException(patchFile, e);
        } else {
            TinkerReport.onApplyCrash(e);
        }
    }

    public void onPatchInfoCorrupted(File patchFile, String oldVersion, String newVersion) {
        super.onPatchInfoCorrupted(patchFile, oldVersion, newVersion);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchInfoCorrupted(patchFile, oldVersion, newVersion);
        } else {
            TinkerReport.onApplyInfoCorrupted();
        }
    }

    public void onPatchPackageCheckFail(File patchFile, int errorCode) {
        super.onPatchPackageCheckFail(patchFile, errorCode);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchPackageCheckFail(patchFile, errorCode);
        } else {
            TinkerReport.onApplyPackageCheckFail(errorCode);
        }
    }

    public void onPatchResult(File patchFile, boolean success, long cost) {
        super.onPatchResult(patchFile, success, cost);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchResult(patchFile, success, cost);
            return;
        }
        TinkerReport.onApplied(cost, success);
        UpgradePatchRetry.getInstance(this.context).onPatchServiceResult();
    }

    public void onPatchTypeExtractFail(File patchFile, File extractTo, String filename, int fileType) {
        super.onPatchTypeExtractFail(patchFile, extractTo, filename, fileType);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchTypeExtractFail(patchFile, extractTo, filename, fileType);
        } else {
            TinkerReport.onApplyExtractFail(fileType);
        }
    }

    public void onPatchVersionCheckFail(File patchFile, SharePatchInfo oldPatchInfo, String patchFileVersion) {
        super.onPatchVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion);
        if (this.userPatchReporter != null) {
            this.userPatchReporter.onPatchVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion);
        } else {
            TinkerReport.onApplyVersionCheckFail();
        }
    }
}
