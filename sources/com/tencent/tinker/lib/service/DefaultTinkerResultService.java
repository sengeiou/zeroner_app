package com.tencent.tinker.lib.service;

import android.os.Process;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerLoadResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import java.io.File;

public class DefaultTinkerResultService extends AbstractResultService {
    private static final String TAG = "Tinker.DefaultTinkerResultService";

    public void onPatchResult(PatchResult result) {
        if (result == null) {
            TinkerLog.e(TAG, "DefaultTinkerResultService received null result!!!!", new Object[0]);
            return;
        }
        TinkerLog.i(TAG, "DefaultTinkerResultService received a result:%s ", result.toString());
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());
        if (result.isSuccess) {
            deleteRawPatchFile(new File(result.rawPatchFilePath));
            if (checkIfNeedKill(result)) {
                Process.killProcess(Process.myPid());
            } else {
                TinkerLog.i(TAG, "I have already install the newly patch version!", new Object[0]);
            }
        }
    }

    public void deleteRawPatchFile(File rawFile) {
        if (SharePatchFileUtil.isLegalFile(rawFile)) {
            TinkerLog.w(TAG, "deleteRawPatchFile rawFile path: %s", rawFile.getPath());
            String fileName = rawFile.getName();
            if (!fileName.startsWith(ShareConstants.PATCH_BASE_NAME) || !fileName.endsWith(ShareConstants.PATCH_SUFFIX)) {
                SharePatchFileUtil.safeDeleteFile(rawFile);
                return;
            }
            File parentFile = rawFile.getParentFile();
            if (!parentFile.getName().startsWith(ShareConstants.PATCH_BASE_NAME)) {
                SharePatchFileUtil.safeDeleteFile(rawFile);
            } else if (!parentFile.getParentFile().getName().equals(ShareConstants.PATCH_DIRECTORY_NAME)) {
                SharePatchFileUtil.safeDeleteFile(rawFile);
            }
        }
    }

    public boolean checkIfNeedKill(PatchResult result) {
        Tinker tinker = Tinker.with(getApplicationContext());
        if (tinker.isTinkerLoaded()) {
            TinkerLoadResult tinkerLoadResult = tinker.getTinkerLoadResultIfPresent();
            if (tinkerLoadResult != null) {
                String currentVersion = tinkerLoadResult.currentVersion;
                if (result.patchVersion != null && result.patchVersion.equals(currentVersion)) {
                    return false;
                }
            }
        }
        return true;
    }
}
