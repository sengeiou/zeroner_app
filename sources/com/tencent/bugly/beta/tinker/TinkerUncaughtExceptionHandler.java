package com.tencent.bugly.beta.tinker;

import android.content.SharedPreferences;
import android.os.SystemClock;
import com.tencent.tinker.lib.tinker.TinkerApplicationHelper;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: BUGLY */
public class TinkerUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private static final String DALVIK_XPOSED_CRASH = "Class ref in pre-verified class resolved to unexpected implementation";
    public static final int MAX_CRASH_COUNT = 3;
    private static final long QUICK_CRASH_ELAPSE = 10000;
    private static final String TAG = "Tinker.TinkerUncaughtExceptionHandler";
    private final UncaughtExceptionHandler ueh = Thread.getDefaultUncaughtExceptionHandler();

    public void uncaughtException(Thread thread, Throwable ex) {
        TinkerLog.e(TAG, "uncaughtException:" + ex.getMessage(), new Object[0]);
        tinkerFastCrashProtect();
        tinkerPreVerifiedCrashHandler(ex);
        this.ueh.uncaughtException(thread, ex);
    }

    private void tinkerPreVerifiedCrashHandler(Throwable ex) {
        boolean z;
        boolean z2;
        ApplicationLike tinkerApplicationLike = TinkerManager.getTinkerApplicationLike();
        if (tinkerApplicationLike == null || tinkerApplicationLike.getApplication() == null) {
            TinkerLog.w(TAG, "applicationlike is null", new Object[0]);
        } else if (!TinkerApplicationHelper.isTinkerLoadSuccess(tinkerApplicationLike)) {
            TinkerLog.w(TAG, "tinker is not loaded", new Object[0]);
        } else {
            boolean z3 = false;
            while (ex != null) {
                if (!z3) {
                    z = TinkerUtils.isXposedExists(ex);
                } else {
                    z = z3;
                }
                if (z) {
                    if (!(ex instanceof IllegalAccessError) || !ex.getMessage().contains(DALVIK_XPOSED_CRASH)) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2) {
                        TinkerReport.onXposedCrash();
                        TinkerLog.e(TAG, "have xposed: just clean tinker", new Object[0]);
                        ShareTinkerInternals.killAllOtherProcess(tinkerApplicationLike.getApplication());
                        TinkerApplicationHelper.cleanPatch(tinkerApplicationLike);
                        ShareTinkerInternals.setTinkerDisableWithSharedPreferences(tinkerApplicationLike.getApplication());
                        return;
                    }
                }
                ex = ex.getCause();
                z3 = z;
            }
        }
    }

    private boolean tinkerFastCrashProtect() {
        ApplicationLike tinkerApplicationLike = TinkerManager.getTinkerApplicationLike();
        if (tinkerApplicationLike == null || tinkerApplicationLike.getApplication() == null || !TinkerApplicationHelper.isTinkerLoadSuccess(tinkerApplicationLike) || SystemClock.elapsedRealtime() - tinkerApplicationLike.getApplicationStartElapsedTime() >= QUICK_CRASH_ELAPSE) {
            return false;
        }
        String currentVersion = TinkerApplicationHelper.getCurrentVersion(tinkerApplicationLike);
        if (ShareTinkerInternals.isNullOrNil(currentVersion)) {
            return false;
        }
        SharedPreferences sharedPreferences = tinkerApplicationLike.getApplication().getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, 0);
        int i = sharedPreferences.getInt(currentVersion, 0) + 1;
        if (i >= 3) {
            TinkerReport.onFastCrashProtect();
            TinkerApplicationHelper.cleanPatch(tinkerApplicationLike);
            TinkerLog.e(TAG, "tinker has fast crash more than %d, we just clean patch!", Integer.valueOf(i));
            return true;
        }
        sharedPreferences.edit().putInt(currentVersion, i).apply();
        TinkerLog.e(TAG, "tinker has fast crash %d times", Integer.valueOf(i));
        return false;
    }
}
