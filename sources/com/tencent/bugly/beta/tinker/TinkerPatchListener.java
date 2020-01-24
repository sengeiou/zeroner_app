package com.tencent.bugly.beta.tinker;

import android.app.ActivityManager;
import android.content.Context;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.util.Properties;

/* compiled from: BUGLY */
public class TinkerPatchListener extends DefaultPatchListener {
    protected static final long NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN = 62914560;
    private static final String TAG = "Tinker.TinkerPatchListener";
    private final int maxMemory;
    private final PatchListener userPatchListener = TinkerManager.userPatchListener;

    public TinkerPatchListener(Context context) {
        super(context);
        this.maxMemory = ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getMemoryClass();
        TinkerLog.i(TAG, "application maxMemory:" + this.maxMemory, new Object[0]);
    }

    public int patchCheck(String path, String patchMd5) {
        boolean z = true;
        if (this.userPatchListener != null) {
            this.userPatchListener.onPatchReceived(path);
            return super.patchCheck(path, patchMd5);
        }
        File file = new File(path);
        TinkerLog.i(TAG, "receive a patch file: %s, file size:%d", path, Long.valueOf(SharePatchFileUtil.getFileOrDirectorySize(file)));
        int patchCheck = super.patchCheck(path, patchMd5);
        if (patchCheck == 0) {
            patchCheck = TinkerUtils.checkForPatchRecover(NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN, this.maxMemory);
        }
        if (patchCheck == 0 && this.context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, 0).getInt(patchMd5, 0) >= 3) {
            patchCheck = -23;
        }
        if (patchCheck == 0) {
            Properties fastGetPatchPackageMeta = ShareTinkerInternals.fastGetPatchPackageMeta(file);
            if (fastGetPatchPackageMeta == null) {
                patchCheck = -24;
            } else {
                TinkerLog.i(TAG, "get platform:" + fastGetPatchPackageMeta.getProperty("platform"), new Object[0]);
            }
        }
        if (patchCheck != 0) {
            z = false;
        }
        TinkerReport.onTryApply(z);
        return patchCheck;
    }
}
