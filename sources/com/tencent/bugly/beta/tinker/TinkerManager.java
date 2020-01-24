package com.tencent.bugly.beta.tinker;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.tencent.bugly.beta.tinker.TinkerReport.Reporter;
import com.tencent.bugly.beta.tinker.TinkerUtils.ScreenState;
import com.tencent.tinker.lib.library.TinkerLoadLibrary;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerApplicationHelper;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Properties;

/* compiled from: BUGLY */
public class TinkerManager {
    public static final String MF_FILE = "YAPATCH.MF";
    public static final String PATCH_DIR = "dex";
    public static final String PATCH_NAME = "patch.apk";
    private static final String TAG = "Tinker.TinkerManager";
    public static String apkOriginalBuildNum = "";
    private static boolean isInstalled = false;
    public static String patchCurBuildNum = "";
    public static boolean patchRestartOnScreenOff = true;
    static TinkerPatchResultListener patchResultListener;
    private static UncaughtExceptionHandler systemExceptionHandler;
    private static TinkerManager tinkerManager = new TinkerManager();
    public static TinkerReport tinkerReport;
    private static TinkerUncaughtExceptionHandler uncaughtExceptionHandler;
    static LoadReporter userLoadReporter;
    static PatchListener userPatchListener;
    static PatchReporter userPatchReporter;
    static AbstractPatch userUpgradePatchProcessor;
    private Application application;
    private ApplicationLike applicationLike;
    /* access modifiers changed from: private */
    public TinkerListener tinkerListener;

    /* compiled from: BUGLY */
    public interface TinkerListener {
        void onApplyFailure(String str);

        void onApplySuccess(String str);

        void onDownloadFailure(String str);

        void onDownloadSuccess(String str);

        void onPatchRollback();

        void onPatchStart();
    }

    /* compiled from: BUGLY */
    public interface TinkerPatchResultListener {
        void onPatchResult(PatchResult patchResult);
    }

    public static boolean isPatchRestartOnScreenOff() {
        return patchRestartOnScreenOff;
    }

    public static void setPatchRestartOnScreenOff(boolean patchRestartOnScreenOff2) {
        patchRestartOnScreenOff = patchRestartOnScreenOff2;
    }

    public static TinkerManager getInstance() {
        return tinkerManager;
    }

    private void setTinkerApplicationLike(ApplicationLike applicationLike2) {
        this.applicationLike = applicationLike2;
        if (applicationLike2 != null) {
            this.application = applicationLike2.getApplication();
        }
    }

    public static ApplicationLike getTinkerApplicationLike() {
        return getInstance().applicationLike;
    }

    public static Application getApplication() {
        return getInstance().application;
    }

    public static void registJavaCrashHandler() {
        if (uncaughtExceptionHandler == null) {
            systemExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            uncaughtExceptionHandler = new TinkerUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    }

    public static void unregistJavaCrashHandler() {
        if (systemExceptionHandler != null) {
            Thread.setDefaultUncaughtExceptionHandler(systemExceptionHandler);
        }
    }

    public static void setUpgradeRetryEnable(boolean enable) {
        UpgradePatchRetry.getInstance(getTinkerApplicationLike().getApplication()).setRetryEnable(enable);
    }

    private static void installDefaultTinker(ApplicationLike appLike) {
        if (isInstalled) {
            TinkerLog.w(TAG, "install tinker, but has installed, ignore", new Object[0]);
        } else if (appLike == null) {
            TinkerLog.e(TAG, "Tinker ApplicationLike is null", new Object[0]);
        } else {
            getInstance().setTinkerApplicationLike(appLike);
            registJavaCrashHandler();
            setUpgradeRetryEnable(true);
            tinkerReport = new TinkerReport();
            TinkerLog.setTinkerLogImp(new TinkerLogger());
            if (TinkerInstaller.install(appLike, new TinkerLoadReporter(appLike.getApplication()), new TinkerPatchReporter(appLike.getApplication()), new TinkerPatchListener(appLike.getApplication()), TinkerResultService.class, new UpgradePatch()) != null) {
                isInstalled = true;
            }
        }
    }

    public static void installTinker(Object tinkerApplicationLikeObject) {
        if (tinkerApplicationLikeObject == null) {
            TinkerLog.e(TAG, "Tinker ApplicationLike is null", new Object[0]);
        } else if (tinkerApplicationLikeObject instanceof ApplicationLike) {
            installDefaultTinker((ApplicationLike) tinkerApplicationLikeObject);
        } else {
            TinkerLog.e(TAG, "NOT tinker ApplicationLike object", new Object[0]);
        }
    }

    public static void installTinker(Object applicationLike2, Object loadReporter, Object patchReporter, Object patchListener, TinkerPatchResultListener tinkerPatchResultListener, Object upgradePatchProcessor) {
        if (loadReporter != null) {
            if (loadReporter instanceof LoadReporter) {
                userLoadReporter = (LoadReporter) loadReporter;
            } else {
                TinkerLog.e(TAG, "NOT LoadReporter object", new Object[0]);
                return;
            }
        }
        if (patchReporter != null) {
            if (patchReporter instanceof PatchReporter) {
                userPatchReporter = (PatchReporter) patchReporter;
            } else {
                TinkerLog.e(TAG, "NOT PatchReporter object", new Object[0]);
                return;
            }
        }
        if (patchListener != null) {
            if (patchListener instanceof PatchListener) {
                userPatchListener = (PatchListener) patchListener;
            } else {
                TinkerLog.e(TAG, "NOT PatchListener object", new Object[0]);
                return;
            }
        }
        if (tinkerPatchResultListener != null) {
            if (tinkerPatchResultListener instanceof TinkerPatchResultListener) {
                patchResultListener = tinkerPatchResultListener;
            } else {
                TinkerLog.e(TAG, "NOT TinkerPatchResultListener object", new Object[0]);
                return;
            }
        }
        if (upgradePatchProcessor != null) {
            if (upgradePatchProcessor instanceof AbstractPatch) {
                userUpgradePatchProcessor = (AbstractPatch) upgradePatchProcessor;
            } else {
                TinkerLog.e(TAG, "NOT AbstractPatch object", new Object[0]);
                return;
            }
        }
        installTinker(applicationLike2);
    }

    public static void loadArmLibrary(Context context, String libName) {
        TinkerLoadLibrary.loadArmLibrary(context, libName);
    }

    public static void loadArmV7Library(Context context, String libName) {
        TinkerLoadLibrary.loadArmV7Library(context, libName);
    }

    public static void loadLibraryFromTinker(Context context, String relativePath, String libname) {
        TinkerLoadLibrary.loadLibraryFromTinker(context, relativePath, libname);
    }

    public void applyPatch(Context context, String patchFilePath) {
        if (!isInstalled) {
            TinkerLog.w(TAG, "Tinker has not been installed.", new Object[0]);
            return;
        }
        if (this.tinkerListener != null) {
            this.tinkerListener.onPatchStart();
        }
        TinkerInstaller.onReceiveUpgradePatch(context, patchFilePath);
    }

    public static String getTinkerId() {
        if (Tinker.with(getApplication()).isTinkerLoaded()) {
            HashMap packageConfigs = TinkerApplicationHelper.getPackageConfigs(getTinkerApplicationLike());
            if (packageConfigs != null) {
                return String.valueOf(packageConfigs.get(ShareConstants.TINKER_ID)).replace("tinker_id_", "");
            }
            return "";
        }
        String manifestTinkerID = ShareTinkerInternals.getManifestTinkerID(getApplication());
        if (!TextUtils.isEmpty(manifestTinkerID)) {
            return manifestTinkerID.replace("tinker_id_", "");
        }
        return "";
    }

    public static String getNewTinkerId() {
        HashMap packageConfigs = TinkerApplicationHelper.getPackageConfigs(getTinkerApplicationLike());
        if (packageConfigs != null) {
            return String.valueOf(packageConfigs.get(ShareConstants.NEW_TINKER_ID)).replace("tinker_id_", "");
        }
        return "";
    }

    public void cleanPatch(boolean now) {
        onPatchRollback(now);
    }

    public static boolean isTinkerManagerInstalled() {
        return isInstalled;
    }

    public void setTinkerListener(TinkerListener tinkerListener2) {
        this.tinkerListener = tinkerListener2;
    }

    public TinkerListener getTinkerListener() {
        return this.tinkerListener;
    }

    public void setTinkerReport(Reporter reporter) {
        if (tinkerReport != null) {
            tinkerReport.setReporter(reporter);
        }
    }

    public void onDownloadSuccess(String patchFilePath, boolean canAutoPatch) {
        try {
            TinkerLog.d(TAG, "onDownloadSuccess.", new Object[0]);
            if (this.tinkerListener != null) {
                this.tinkerListener.onDownloadSuccess(patchFilePath);
            }
            applyPatch(patchFilePath, canAutoPatch);
        } catch (Exception e) {
            TinkerLog.e(TAG, "apply patch failed", new Object[0]);
        }
    }

    public void applyPatch(String patchFilePath, boolean canAutoPatch) {
        try {
            File file = new File(this.applicationLike.getApplication().getDir("dex", 0).getAbsolutePath(), PATCH_NAME);
            File file2 = null;
            if (checkNewPatch(patchFilePath)) {
                TinkerLog.d(TAG, "has new patch.", new Object[0]);
                file2 = new File(patchFilePath);
                TinkerUtils.copy(file2, file);
            }
            if (!file.exists()) {
                TinkerLog.d(TAG, "patch not exist, just return.", new Object[0]);
            } else if (file2 != null && canAutoPatch) {
                TinkerLog.d(TAG, "starting patch.", new Object[0]);
                applyPatch((Context) this.applicationLike.getApplication(), file2.getAbsolutePath());
            }
        } catch (Exception e) {
            TinkerLog.e(TAG, e.getMessage(), new Object[0]);
        }
    }

    public boolean checkNewPatch(String patchFilePath) {
        File file;
        boolean z;
        boolean z2 = true;
        TinkerLog.d(TAG, "check if has new patch.", new Object[0]);
        apkOriginalBuildNum = getTinkerId();
        patchCurBuildNum = getNewTinkerId();
        if (TextUtils.isEmpty(patchFilePath)) {
            file = null;
            z = false;
        } else {
            File file2 = new File(patchFilePath);
            if (!file2.exists()) {
                file = file2;
                z = false;
            } else {
                file = file2;
                z = true;
            }
        }
        if (z) {
            byte[] readJarEntry = TinkerUtils.readJarEntry(file, MF_FILE);
            if (readJarEntry == null) {
                return false;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readJarEntry);
            try {
                Properties properties = new Properties();
                properties.load(byteArrayInputStream);
                if (properties.getProperty(HttpHeaders.FROM) == null || properties.getProperty("To") == null) {
                    TinkerLog.e(TAG, "From/To is null", new Object[0]);
                    return false;
                } else if (apkOriginalBuildNum == null) {
                    TinkerLog.e(TAG, "patchCurBuildNum is null", new Object[0]);
                    return false;
                } else if (apkOriginalBuildNum.equalsIgnoreCase(properties.getProperty(HttpHeaders.FROM))) {
                    patchCurBuildNum = properties.getProperty("To");
                } else {
                    TinkerLog.e(TAG, "orign buildno invalid", new Object[0]);
                    z2 = false;
                }
            } catch (Exception e) {
                TinkerLog.e(TAG, "get properties failed", new Object[0]);
                z2 = false;
            }
        } else {
            z2 = z;
        }
        return z2;
    }

    public File getPatchDirectory(Context context) {
        return SharePatchFileUtil.getPatchDirectory(context);
    }

    public void onDownloadFailure(String msg) {
        if (this.tinkerListener != null) {
            this.tinkerListener.onDownloadFailure(msg);
        }
    }

    public void onApplySuccess(String msg) {
        if (this.tinkerListener != null) {
            this.tinkerListener.onApplySuccess(msg);
        }
    }

    public void onApplyFailure(String msg) {
        if (this.tinkerListener != null) {
            this.tinkerListener.onApplyFailure(msg);
        }
    }

    public void onPatchRollback(boolean now) {
        if (!Tinker.with(getApplication()).isTinkerLoaded()) {
            TinkerLog.w("Tinker.PatchRequestCallback", "TinkerPatchRequestCallback: onPatchRollback, tinker is not loaded, just return", new Object[0]);
            return;
        }
        if (now) {
            TinkerLog.i(TAG, "delete patch now", new Object[0]);
            TinkerUtils.rollbackPatch(getApplication());
        } else {
            TinkerLog.i(TAG, "tinker wait screen to restart process", new Object[0]);
            new ScreenState(getApplication(), new IOnScreenOff() {
                public void onScreenOff() {
                    TinkerUtils.rollbackPatch(TinkerManager.getApplication());
                }
            });
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (TinkerManager.this.tinkerListener != null) {
                    TinkerManager.this.tinkerListener.onPatchRollback();
                }
            }
        });
    }
}
