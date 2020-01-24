package com.tencent.tinker.lib.tinker;

import android.content.Context;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.AbstractResultService;
import com.tencent.tinker.lib.tinker.Tinker.Builder;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerLog.TinkerLogImp;
import com.tencent.tinker.loader.app.ApplicationLike;

public class TinkerInstaller {
    private static final String TAG = "Tinker.TinkerInstaller";

    public static Tinker install(ApplicationLike applicationLike) {
        Tinker tinker = new Builder(applicationLike.getApplication()).build();
        Tinker.create(tinker);
        tinker.install(applicationLike.getTinkerResultIntent());
        return tinker;
    }

    public static Tinker install(ApplicationLike applicationLike, LoadReporter loadReporter, PatchReporter patchReporter, PatchListener listener, Class<? extends AbstractResultService> resultServiceClass, AbstractPatch upgradePatchProcessor) {
        Tinker tinker = new Builder(applicationLike.getApplication()).tinkerFlags(applicationLike.getTinkerFlags()).loadReport(loadReporter).listener(listener).patchReporter(patchReporter).tinkerLoadVerifyFlag(Boolean.valueOf(applicationLike.getTinkerLoadVerifyFlag())).build();
        Tinker.create(tinker);
        tinker.install(applicationLike.getTinkerResultIntent(), resultServiceClass, upgradePatchProcessor);
        return tinker;
    }

    public static void cleanPatch(Context context) {
        Tinker.with(context).cleanPatch();
    }

    public static void onReceiveUpgradePatch(Context context, String patchLocation) {
        Tinker.with(context).getPatchListener().onPatchReceived(patchLocation);
    }

    public static void setLogIml(TinkerLogImp imp) {
        TinkerLog.setTinkerLogImp(imp);
    }
}
