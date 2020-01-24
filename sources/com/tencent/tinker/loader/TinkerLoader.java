package com.tencent.tinker.loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.hotplug.ComponentHotplug;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;

public class TinkerLoader extends AbstractTinkerLoader {
    private static final String TAG = "Tinker.TinkerLoader";
    private SharePatchInfo patchInfo;

    public Intent tryLoad(TinkerApplication app) {
        Intent resultIntent = new Intent();
        long begin = SystemClock.elapsedRealtime();
        tryLoadPatchFilesInternal(app, resultIntent);
        ShareIntentUtil.setIntentPatchCostTime(resultIntent, SystemClock.elapsedRealtime() - begin);
        return resultIntent;
    }

    private void tryLoadPatchFilesInternal(TinkerApplication app, Intent resultIntent) {
        int tinkerFlag = app.getTinkerFlags();
        if (!ShareTinkerInternals.isTinkerEnabled(tinkerFlag)) {
            Log.w(TAG, "tryLoadPatchFiles: tinker is disable, just return");
            ShareIntentUtil.setIntentReturnCode(resultIntent, -1);
        } else if (ShareTinkerInternals.isInPatchProcess(app)) {
            Log.w(TAG, "tryLoadPatchFiles: we don't load patch with :patch process itself, just return");
            ShareIntentUtil.setIntentReturnCode(resultIntent, -1);
        } else {
            File patchDirectoryFile = SharePatchFileUtil.getPatchDirectory(app);
            if (patchDirectoryFile == null) {
                Log.w(TAG, "tryLoadPatchFiles:getPatchDirectory == null");
                ShareIntentUtil.setIntentReturnCode(resultIntent, -2);
                return;
            }
            String patchDirectoryPath = patchDirectoryFile.getAbsolutePath();
            if (!patchDirectoryFile.exists()) {
                Log.w(TAG, "tryLoadPatchFiles:patch dir not exist:" + patchDirectoryPath);
                ShareIntentUtil.setIntentReturnCode(resultIntent, -2);
                return;
            }
            File patchInfoFile = SharePatchFileUtil.getPatchInfoFile(patchDirectoryPath);
            if (!patchInfoFile.exists()) {
                Log.w(TAG, "tryLoadPatchFiles:patch info not exist:" + patchInfoFile.getAbsolutePath());
                ShareIntentUtil.setIntentReturnCode(resultIntent, -3);
                return;
            }
            File patchInfoLockFile = SharePatchFileUtil.getPatchInfoLockFile(patchDirectoryPath);
            this.patchInfo = SharePatchInfo.readAndCheckPropertyWithLock(patchInfoFile, patchInfoLockFile);
            if (this.patchInfo == null) {
                ShareIntentUtil.setIntentReturnCode(resultIntent, -4);
                return;
            }
            String oldVersion = this.patchInfo.oldVersion;
            String newVersion = this.patchInfo.newVersion;
            String oatDex = this.patchInfo.oatDir;
            if (oldVersion == null || newVersion == null || oatDex == null) {
                Log.w(TAG, "tryLoadPatchFiles:onPatchInfoCorrupted");
                ShareIntentUtil.setIntentReturnCode(resultIntent, -4);
                return;
            }
            resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_OLD_VERSION, oldVersion);
            resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_NEW_VERSION, newVersion);
            boolean mainProcess = ShareTinkerInternals.isInMainProcess(app);
            boolean versionChanged = !oldVersion.equals(newVersion);
            boolean oatModeChanged = oatDex.equals(ShareConstants.CHANING_DEX_OPTIMIZE_PATH) && mainProcess;
            String oatDex2 = ShareTinkerInternals.getCurrentOatMode(app, oatDex);
            resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_OAT_DIR, oatDex2);
            String version = oldVersion;
            if (versionChanged && mainProcess) {
                version = newVersion;
            }
            if (ShareTinkerInternals.isNullOrNil(version)) {
                Log.w(TAG, "tryLoadPatchFiles:version is blank, wait main process to restart");
                ShareIntentUtil.setIntentReturnCode(resultIntent, -5);
                return;
            }
            String patchName = SharePatchFileUtil.getPatchVersionDirectory(version);
            if (patchName == null) {
                Log.w(TAG, "tryLoadPatchFiles:patchName is null");
                ShareIntentUtil.setIntentReturnCode(resultIntent, -6);
                return;
            }
            String patchVersionDirectory = patchDirectoryPath + "/" + patchName;
            File file = new File(patchVersionDirectory);
            if (!file.exists()) {
                Log.w(TAG, "tryLoadPatchFiles:onPatchVersionDirectoryNotFound");
                ShareIntentUtil.setIntentReturnCode(resultIntent, -6);
                return;
            }
            String patchVersionFileRelPath = SharePatchFileUtil.getPatchVersionFile(version);
            File patchVersionFile = patchVersionFileRelPath != null ? new File(file.getAbsolutePath(), patchVersionFileRelPath) : null;
            if (!SharePatchFileUtil.isLegalFile(patchVersionFile)) {
                Log.w(TAG, "tryLoadPatchFiles:onPatchVersionFileNotFound");
                ShareIntentUtil.setIntentReturnCode(resultIntent, -7);
                return;
            }
            ShareSecurityCheck shareSecurityCheck = new ShareSecurityCheck(app);
            int returnCode = ShareTinkerInternals.checkTinkerPackage(app, tinkerFlag, patchVersionFile, shareSecurityCheck);
            if (returnCode != 0) {
                Log.w(TAG, "tryLoadPatchFiles:checkTinkerPackage");
                resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_PACKAGE_PATCH_CHECK, returnCode);
                ShareIntentUtil.setIntentReturnCode(resultIntent, -8);
                return;
            }
            resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_PACKAGE_CONFIG, shareSecurityCheck.getPackagePropertiesIfPresent());
            boolean isEnabledForDex = ShareTinkerInternals.isTinkerEnabledForDex(tinkerFlag);
            if (isEnabledForDex && !TinkerDexLoader.checkComplete(patchVersionDirectory, shareSecurityCheck, oatDex2, resultIntent)) {
                Log.w(TAG, "tryLoadPatchFiles:dex check fail");
            } else if (!ShareTinkerInternals.isTinkerEnabledForNativeLib(tinkerFlag) || TinkerSoLoader.checkComplete(patchVersionDirectory, shareSecurityCheck, resultIntent)) {
                boolean isEnabledForResource = ShareTinkerInternals.isTinkerEnabledForResource(tinkerFlag);
                Log.w(TAG, "tryLoadPatchFiles:isEnabledForResource:" + isEnabledForResource);
                if (!isEnabledForResource || TinkerResourceLoader.checkComplete(app, patchVersionDirectory, shareSecurityCheck, resultIntent)) {
                    boolean isSystemOTA = ShareTinkerInternals.isVmArt() && ShareTinkerInternals.isSystemOTA(this.patchInfo.fingerPrint) && VERSION.SDK_INT >= 21 && !ShareTinkerInternals.isAfterAndroidO();
                    resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_SYSTEM_OTA, isSystemOTA);
                    if ((mainProcess && versionChanged) || oatModeChanged) {
                        this.patchInfo.oldVersion = version;
                        this.patchInfo.oatDir = oatDex2;
                        if (!SharePatchInfo.rewritePatchInfoFileWithLock(patchInfoFile, this.patchInfo, patchInfoLockFile)) {
                            ShareIntentUtil.setIntentReturnCode(resultIntent, -19);
                            Log.w(TAG, "tryLoadPatchFiles:onReWritePatchInfoCorrupted");
                            return;
                        } else if (oatModeChanged) {
                            Log.i(TAG, "tryLoadPatchFiles:oatModeChanged, try to delete interpret optimize files");
                            SharePatchFileUtil.deleteDir(patchVersionDirectory + "/" + ShareConstants.INTERPRET_DEX_OPTIMIZE_PATH);
                        }
                    }
                    if (!checkSafeModeCount(app)) {
                        resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_EXCEPTION, new TinkerRuntimeException("checkSafeModeCount fail"));
                        ShareIntentUtil.setIntentReturnCode(resultIntent, -25);
                        Log.w(TAG, "tryLoadPatchFiles:checkSafeModeCount fail");
                        return;
                    }
                    if (isEnabledForDex) {
                        boolean loadTinkerJars = TinkerDexLoader.loadTinkerJars(app, patchVersionDirectory, oatDex2, resultIntent, isSystemOTA);
                        if (isSystemOTA) {
                            this.patchInfo.fingerPrint = Build.FINGERPRINT;
                            this.patchInfo.oatDir = loadTinkerJars ? ShareConstants.INTERPRET_DEX_OPTIMIZE_PATH : "odex";
                            oatModeChanged = false;
                            if (!SharePatchInfo.rewritePatchInfoFileWithLock(patchInfoFile, this.patchInfo, patchInfoLockFile)) {
                                ShareIntentUtil.setIntentReturnCode(resultIntent, -19);
                                Log.w(TAG, "tryLoadPatchFiles:onReWritePatchInfoCorrupted");
                                return;
                            }
                            resultIntent.putExtra(ShareIntentUtil.INTENT_PATCH_OAT_DIR, this.patchInfo.oatDir);
                        }
                        if (!loadTinkerJars) {
                            Log.w(TAG, "tryLoadPatchFiles:onPatchLoadDexesFail");
                            return;
                        }
                    }
                    if (!isEnabledForResource || TinkerResourceLoader.loadTinkerResources(app, patchVersionDirectory, resultIntent)) {
                        if (isEnabledForDex && isEnabledForResource) {
                            ComponentHotplug.install(app, shareSecurityCheck);
                        }
                        if (oatModeChanged) {
                            ShareTinkerInternals.killAllOtherProcess(app);
                            Log.i(TAG, "tryLoadPatchFiles:oatModeChanged, try to kill all other process");
                        }
                        ShareIntentUtil.setIntentReturnCode(resultIntent, 0);
                        Log.i(TAG, "tryLoadPatchFiles: load end, ok!");
                        return;
                    }
                    Log.w(TAG, "tryLoadPatchFiles:onPatchLoadResourcesFail");
                    return;
                }
                Log.w(TAG, "tryLoadPatchFiles:resource check fail");
            } else {
                Log.w(TAG, "tryLoadPatchFiles:native lib check fail");
            }
        }
    }

    private boolean checkSafeModeCount(TinkerApplication application) {
        String preferName = ShareConstants.TINKER_OWN_PREFERENCE_CONFIG + ShareTinkerInternals.getProcessName(application);
        SharedPreferences sp = application.getSharedPreferences(preferName, 0);
        int count = sp.getInt(ShareConstants.TINKER_SAFE_MODE_COUNT, 0) + 1;
        Log.w(TAG, "tinker safe mode preferName:" + preferName + " count:" + count);
        if (count >= 3) {
            sp.edit().putInt(ShareConstants.TINKER_SAFE_MODE_COUNT, 0).commit();
            return false;
        }
        application.setUseSafeMode(true);
        sp.edit().putInt(ShareConstants.TINKER_SAFE_MODE_COUNT, count).commit();
        return true;
    }
}
