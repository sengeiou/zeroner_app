package com.tencent.tinker.lib.tinker;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.util.HashMap;

public class TinkerLoadResult {
    private static final String TAG = "Tinker.TinkerLoadResult";
    public long costTime;
    public String currentVersion;
    public File dexDirectory;
    public HashMap<String, String> dexes;
    public File libraryDirectory;
    public HashMap<String, String> libs;
    public int loadCode;
    public String oatDir;
    public HashMap<String, String> packageConfig;
    public SharePatchInfo patchInfo;
    public File patchVersionDirectory;
    public File patchVersionFile;
    public File resourceDirectory;
    public File resourceFile;
    public boolean systemOTA;
    public boolean useInterpretMode;
    public boolean versionChanged;

    public boolean parseTinkerResult(Context context, Intent intentResult) {
        Tinker tinker = Tinker.with(context);
        this.loadCode = ShareIntentUtil.getIntentReturnCode(intentResult);
        this.costTime = ShareIntentUtil.getIntentPatchCostTime(intentResult);
        this.systemOTA = ShareIntentUtil.getBooleanExtra(intentResult, ShareIntentUtil.INTENT_PATCH_SYSTEM_OTA, false);
        this.oatDir = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_OAT_DIR);
        this.useInterpretMode = ShareConstants.INTERPRET_DEX_OPTIMIZE_PATH.equals(this.oatDir);
        boolean isMainProcess = tinker.isMainProcess();
        TinkerLog.i(TAG, "parseTinkerResult loadCode:%d, process name:%s, main process:%b, systemOTA:%b, fingerPrint:%s, oatDir:%s, useInterpretMode:%b", Integer.valueOf(this.loadCode), ShareTinkerInternals.getProcessName(context), Boolean.valueOf(isMainProcess), Boolean.valueOf(this.systemOTA), Build.FINGERPRINT, this.oatDir, Boolean.valueOf(this.useInterpretMode));
        String oldVersion = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_OLD_VERSION);
        String newVersion = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_NEW_VERSION);
        File patchDirectory = tinker.getPatchDirectory();
        File patchInfoFile = tinker.getPatchInfoFile();
        if (!(oldVersion == null || newVersion == null)) {
            if (isMainProcess) {
                this.currentVersion = newVersion;
            } else {
                this.currentVersion = oldVersion;
            }
            TinkerLog.i(TAG, "parseTinkerResult oldVersion:%s, newVersion:%s, current:%s", oldVersion, newVersion, this.currentVersion);
            String patchName = SharePatchFileUtil.getPatchVersionDirectory(this.currentVersion);
            if (!ShareTinkerInternals.isNullOrNil(patchName)) {
                this.patchVersionDirectory = new File(patchDirectory.getAbsolutePath() + "/" + patchName);
                this.patchVersionFile = new File(this.patchVersionDirectory.getAbsolutePath(), SharePatchFileUtil.getPatchVersionFile(this.currentVersion));
                this.dexDirectory = new File(this.patchVersionDirectory, "dex");
                this.libraryDirectory = new File(this.patchVersionDirectory, ShareConstants.SO_PATH);
                this.resourceDirectory = new File(this.patchVersionDirectory, ShareConstants.RES_PATH);
                this.resourceFile = new File(this.resourceDirectory, ShareConstants.RES_NAME);
            }
            SharePatchInfo sharePatchInfo = new SharePatchInfo(oldVersion, newVersion, Build.FINGERPRINT, this.oatDir);
            this.patchInfo = sharePatchInfo;
            this.versionChanged = !oldVersion.equals(newVersion);
        }
        Throwable exception = ShareIntentUtil.getIntentPatchException(intentResult);
        if (exception != null) {
            TinkerLog.i(TAG, "Tinker load have exception loadCode:%d", Integer.valueOf(this.loadCode));
            int errorCode = -1;
            switch (this.loadCode) {
                case ShareConstants.ERROR_LOAD_PATCH_UNCAUGHT_EXCEPTION /*-25*/:
                    errorCode = -4;
                    break;
                case -23:
                    errorCode = -3;
                    break;
                case -20:
                    errorCode = -1;
                    break;
                case -14:
                    errorCode = -2;
                    break;
            }
            tinker.getLoadReporter().onLoadException(exception, errorCode);
            return false;
        }
        switch (this.loadCode) {
            case ShareConstants.ERROR_LOAD_GET_INTENT_FAIL /*-10000*/:
                TinkerLog.e(TAG, "can't get the right intent return code", new Object[0]);
                throw new TinkerRuntimeException("can't get the right intent return code");
            case -24:
                if (this.resourceFile != null) {
                    TinkerLog.e(TAG, "patch resource file md5 is mismatch: %s", this.resourceFile.getAbsolutePath());
                    tinker.getLoadReporter().onLoadFileMd5Mismatch(this.resourceFile, 6);
                    break;
                } else {
                    TinkerLog.e(TAG, "resource file md5 mismatch, but patch resource file not found!", new Object[0]);
                    throw new TinkerRuntimeException("resource file md5 mismatch, but patch resource file not found!");
                }
            case -22:
                if (this.patchVersionDirectory != null) {
                    TinkerLog.e(TAG, "patch resource file not found:%s", this.resourceFile.getAbsolutePath());
                    tinker.getLoadReporter().onLoadFileNotFound(this.resourceFile, 6, false);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch resource file not found, warning why the path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch resource file not found, warning why the path is null!!!!");
                }
            case -21:
                if (this.patchVersionDirectory != null) {
                    TinkerLog.e(TAG, "patch resource file directory not found:%s", this.resourceDirectory.getAbsolutePath());
                    tinker.getLoadReporter().onLoadFileNotFound(this.resourceDirectory, 6, true);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch resource file directory not found, warning why the path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch resource file directory not found, warning why the path is null!!!!");
                }
            case ShareConstants.ERROR_LOAD_PATCH_REWRITE_PATCH_INFO_FAIL /*-19*/:
                TinkerLog.i(TAG, "rewrite patch info file corrupted", new Object[0]);
                tinker.getLoadReporter().onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile);
                break;
            case ShareConstants.ERROR_LOAD_PATCH_VERSION_LIB_FILE_NOT_EXIST /*-18*/:
                String libPath = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_MISSING_LIB_PATH);
                if (libPath != null) {
                    TinkerLog.e(TAG, "patch lib file not found:%s", libPath);
                    LoadReporter loadReporter = tinker.getLoadReporter();
                    File file = new File(libPath);
                    loadReporter.onLoadFileNotFound(file, 5, false);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch lib file not found, but path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch lib file not found, but path is null!!!!");
                }
            case ShareConstants.ERROR_LOAD_PATCH_VERSION_LIB_DIRECTORY_NOT_EXIST /*-17*/:
                if (this.patchVersionDirectory != null) {
                    TinkerLog.e(TAG, "patch lib file directory not found:%s", this.libraryDirectory.getAbsolutePath());
                    tinker.getLoadReporter().onLoadFileNotFound(this.libraryDirectory, 5, true);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch lib file directory not found, warning why the path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch lib file directory not found, warning why the path is null!!!!");
                }
            case ShareConstants.ERROR_LOAD_PATCH_OTA_INTERPRET_ONLY_EXCEPTION /*-16*/:
                tinker.getLoadReporter().onLoadInterpret(2, ShareIntentUtil.getIntentInterpretException(intentResult));
                break;
            case -15:
                tinker.getLoadReporter().onLoadInterpret(1, ShareIntentUtil.getIntentInterpretException(intentResult));
                break;
            case -13:
                String mismatchPath = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_MISMATCH_DEX_PATH);
                if (mismatchPath != null) {
                    TinkerLog.e(TAG, "patch dex file md5 is mismatch: %s", mismatchPath);
                    LoadReporter loadReporter2 = tinker.getLoadReporter();
                    File file2 = new File(mismatchPath);
                    loadReporter2.onLoadFileMd5Mismatch(file2, 3);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch dex file md5 is mismatch, but path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch dex file md5 is mismatch, but path is null!!!!");
                }
            case -12:
                TinkerLog.e(TAG, "patch dex load fail, classloader is null", new Object[0]);
                break;
            case -11:
                String dexOptPath = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_MISSING_DEX_PATH);
                if (dexOptPath != null) {
                    TinkerLog.e(TAG, "patch dex opt file not found:%s", dexOptPath);
                    LoadReporter loadReporter3 = tinker.getLoadReporter();
                    File file3 = new File(dexOptPath);
                    loadReporter3.onLoadFileNotFound(file3, 4, false);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch dex opt file not found, but path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch dex opt file not found, but path is null!!!!");
                }
            case -10:
                String dexPath = ShareIntentUtil.getStringExtra(intentResult, ShareIntentUtil.INTENT_PATCH_MISSING_DEX_PATH);
                if (dexPath != null) {
                    TinkerLog.e(TAG, "patch dex file not found:%s", dexPath);
                    LoadReporter loadReporter4 = tinker.getLoadReporter();
                    File file4 = new File(dexPath);
                    loadReporter4.onLoadFileNotFound(file4, 3, false);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch dex file not found, but path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch dex file not found, but path is null!!!!");
                }
            case -9:
                if (this.dexDirectory != null) {
                    TinkerLog.e(TAG, "patch dex file directory not found:%s", this.dexDirectory.getAbsolutePath());
                    tinker.getLoadReporter().onLoadFileNotFound(this.dexDirectory, 3, true);
                    break;
                } else {
                    TinkerLog.e(TAG, "patch dex file directory not found, warning why the path is null!!!!", new Object[0]);
                    throw new TinkerRuntimeException("patch dex file directory not found, warning why the path is null!!!!");
                }
            case -8:
                TinkerLog.i(TAG, "patch package check fail", new Object[0]);
                if (this.patchVersionFile != null) {
                    tinker.getLoadReporter().onLoadPackageCheckFail(this.patchVersionFile, intentResult.getIntExtra(ShareIntentUtil.INTENT_PATCH_PACKAGE_PATCH_CHECK, ShareConstants.ERROR_LOAD_GET_INTENT_FAIL));
                    break;
                } else {
                    throw new TinkerRuntimeException("error patch package check fail , but file is null");
                }
            case -7:
                TinkerLog.e(TAG, "patch version file not found, current version:%s", this.currentVersion);
                if (this.patchVersionFile != null) {
                    tinker.getLoadReporter().onLoadFileNotFound(this.patchVersionFile, 1, false);
                    break;
                } else {
                    throw new TinkerRuntimeException("error load patch version file not exist, but file is null");
                }
            case -6:
                TinkerLog.e(TAG, "patch version directory not found, current version:%s", this.currentVersion);
                tinker.getLoadReporter().onLoadFileNotFound(this.patchVersionDirectory, 1, true);
                break;
            case -5:
                TinkerLog.e(TAG, "path info blank, wait main process to restart", new Object[0]);
                break;
            case -4:
                TinkerLog.e(TAG, "path info corrupted", new Object[0]);
                tinker.getLoadReporter().onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile);
                break;
            case -3:
            case -2:
                TinkerLog.w(TAG, "can't find patch file, is ok, just return", new Object[0]);
                break;
            case -1:
                TinkerLog.w(TAG, "tinker is disable, just return", new Object[0]);
                break;
            case 0:
                TinkerLog.i(TAG, "oh yeah, tinker load all success", new Object[0]);
                tinker.setTinkerLoaded(true);
                this.dexes = ShareIntentUtil.getIntentPatchDexPaths(intentResult);
                this.libs = ShareIntentUtil.getIntentPatchLibsPaths(intentResult);
                this.packageConfig = ShareIntentUtil.getIntentPackageConfig(intentResult);
                if (this.useInterpretMode) {
                    tinker.getLoadReporter().onLoadInterpret(0, null);
                }
                if (isMainProcess && this.versionChanged) {
                    tinker.getLoadReporter().onLoadPatchVersionChanged(oldVersion, newVersion, patchDirectory, this.patchVersionDirectory.getName());
                }
                return true;
        }
        return false;
    }

    public String getPackageConfigByName(String name) {
        if (this.packageConfig != null) {
            return (String) this.packageConfig.get(name);
        }
        return null;
    }
}
