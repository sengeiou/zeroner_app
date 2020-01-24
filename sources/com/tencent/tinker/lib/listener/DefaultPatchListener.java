package com.tencent.tinker.lib.listener;

import android.content.Context;
import com.tencent.tinker.lib.service.TinkerPatchService;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerLoadResult;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;

public class DefaultPatchListener implements PatchListener {
    protected final Context context;

    public DefaultPatchListener(Context context2) {
        this.context = context2;
    }

    public int onPatchReceived(String path) {
        int returnCode = patchCheck(path, SharePatchFileUtil.getMD5(new File(path)));
        if (returnCode == 0) {
            TinkerPatchService.runPatchService(this.context, path);
        } else {
            Tinker.with(this.context).getLoadReporter().onLoadPatchListenerReceiveFail(new File(path), returnCode);
        }
        return returnCode;
    }

    /* access modifiers changed from: protected */
    public int patchCheck(String path, String patchMd5) {
        Tinker manager = Tinker.with(this.context);
        if (!manager.isTinkerEnabled() || !ShareTinkerInternals.isTinkerEnableWithSharedPreferences(this.context)) {
            return -1;
        }
        if (!SharePatchFileUtil.isLegalFile(new File(path))) {
            return -2;
        }
        if (manager.isPatchProcess()) {
            return -4;
        }
        if (TinkerServiceInternals.isTinkerPatchServiceRunning(this.context)) {
            return -3;
        }
        if (ShareTinkerInternals.isVmJit()) {
            return -5;
        }
        Tinker tinker = Tinker.with(this.context);
        if (tinker.isTinkerLoaded()) {
            TinkerLoadResult tinkerLoadResult = tinker.getTinkerLoadResultIfPresent();
            if (tinkerLoadResult != null && !tinkerLoadResult.useInterpretMode && patchMd5.equals(tinkerLoadResult.currentVersion)) {
                return -6;
            }
        }
        if (!UpgradePatchRetry.getInstance(this.context).onPatchListenerCheck(patchMd5)) {
            return -7;
        }
        return 0;
    }
}
