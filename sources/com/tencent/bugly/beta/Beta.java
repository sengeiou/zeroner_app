package com.tencent.bugly.beta;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.a;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.global.ResBean;
import com.tencent.bugly.beta.global.d;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.beta.global.f;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.tinker.TinkerApplicationLike;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.tencent.bugly.beta.tinker.TinkerManager.TinkerPatchResultListener;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.bugly.beta.ui.h;
import com.tencent.bugly.beta.upgrade.BetaGrayStrategy;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.beta.upgrade.c;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.q;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.s;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.v;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* compiled from: BUGLY */
public class Beta extends a {
    public static final String TAG_CANCEL_BUTTON = "beta_cancel_button";
    public static final String TAG_CONFIRM_BUTTON = "beta_confirm_button";
    public static final String TAG_IMG_BANNER = "beta_upgrade_banner";
    public static final String TAG_TIP_MESSAGE = "beta_tip_message";
    public static final String TAG_TITLE = "beta_title";
    public static final String TAG_UPGRADE_FEATURE = "beta_upgrade_feature";
    public static final String TAG_UPGRADE_INFO = "beta_upgrade_info";
    private static DownloadTask a = null;
    public static String appChannel = null;
    public static int appVersionCode = Integer.MIN_VALUE;
    public static String appVersionName = null;
    public static boolean autoCheckUpgrade = true;
    public static boolean autoDownloadOn4g = false;
    public static boolean autoDownloadOnWifi = false;
    public static boolean autoInit = true;
    public static boolean autoInstallApk = true;
    public static BetaPatchListener betaPatchListener;
    public static boolean canAutoDownloadPatch = true;
    public static boolean canAutoPatch = true;
    public static List<Class<? extends Activity>> canNotShowUpgradeActs = Collections.synchronizedList(new ArrayList());
    public static boolean canNotifyUserRestart = false;
    public static boolean canShowApkInfo = true;
    public static List<Class<? extends Activity>> canShowUpgradeActs = Collections.synchronizedList(new ArrayList());
    public static int defaultBannerId;
    public static boolean dialogFullScreen = false;
    public static DownloadListener downloadListener;
    public static boolean enableHotfix = false;
    public static boolean enableNotification = true;
    public static long initDelay = 3000;
    public static String initProcessName = null;
    public static Beta instance = new Beta();
    public static int largeIconId;
    public static boolean setPatchRestartOnScreenOff = true;
    public static boolean showInterruptedStrategy = true;
    public static int smallIconId;
    public static List<String> soBlackList = Collections.synchronizedList(new ArrayList());
    public static File storageDir;
    public static String strNetworkTipsCancelBtn = "取消";
    public static String strNetworkTipsConfirmBtn = "继续下载";
    public static String strNetworkTipsMessage = "你已切换到移动网络，是否继续当前下载？";
    public static String strNetworkTipsTitle = "网络提示";
    public static String strNotificationClickToContinue = "继续下载";
    public static String strNotificationClickToInstall = "点击安装";
    public static String strNotificationClickToRetry = "点击重试";
    public static String strNotificationClickToView = "点击查看";
    public static String strNotificationDownloadError = "下载失败";
    public static String strNotificationDownloadSucc = "下载完成";
    public static String strNotificationDownloading = "正在下载";
    public static String strNotificationHaveNewVersion = "有新版本";
    public static String strToastCheckUpgradeError = "检查新版本失败，请稍后重试";
    public static String strToastCheckingUpgrade = "正在检查，请稍候...";
    public static String strToastYourAreTheLatestVersion = "你已经是最新版了";
    public static String strUpgradeDialogCancelBtn = "下次再说";
    public static String strUpgradeDialogContinueBtn = "继续";
    public static String strUpgradeDialogFeatureLabel = "更新说明";
    public static String strUpgradeDialogFileSizeLabel = "包大小";
    public static String strUpgradeDialogInstallBtn = "安装";
    public static String strUpgradeDialogRetryBtn = "重试";
    public static String strUpgradeDialogUpdateTimeLabel = "更新时间";
    public static String strUpgradeDialogUpgradeBtn = "立即更新";
    public static String strUpgradeDialogVersionLabel = "版本";
    public static int tipsDialogLayoutId;
    public static long upgradeCheckPeriod = 0;
    public static int upgradeDialogLayoutId;
    public static UILifecycleListener<UpgradeInfo> upgradeDialogLifecycleListener;
    public static UpgradeListener upgradeListener;
    public static UpgradeStateListener upgradeStateListener;

    public static Beta getInstance() {
        instance.id = 1002;
        instance.version = "1.3.5";
        instance.versionKey = "G10";
        return instance;
    }

    public static void checkUpgrade() {
        checkUpgrade(true, false);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void checkUpgrade(boolean r7, boolean r8) {
        /*
            r6 = 3
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0056 }
            java.lang.String r0 = r0.v     // Catch:{ Exception -> 0x0056 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x0062
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{ Exception -> 0x0056 }
            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch:{ Exception -> 0x0056 }
            if (r0 != r1) goto L_0x0035
            com.tencent.bugly.proguard.am r0 = com.tencent.bugly.proguard.am.a()     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.global.d r1 = new com.tencent.bugly.beta.global.d     // Catch:{ Exception -> 0x0056 }
            r2 = 19
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0056 }
            r4 = 0
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x0056 }
            r3[r4] = r5     // Catch:{ Exception -> 0x0056 }
            r4 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r8)     // Catch:{ Exception -> 0x0056 }
            r3[r4] = r5     // Catch:{ Exception -> 0x0056 }
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0056 }
            r0.a(r1)     // Catch:{ Exception -> 0x0056 }
        L_0x0034:
            return
        L_0x0035:
            com.tencent.bugly.beta.global.e r1 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0056 }
            monitor-enter(r1)     // Catch:{ Exception -> 0x0056 }
        L_0x0038:
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x0053 }
            java.lang.String r0 = r0.v     // Catch:{ all -> 0x0053 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0053 }
            if (r0 == 0) goto L_0x0061
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ InterruptedException -> 0x0048 }
            r0.wait()     // Catch:{ InterruptedException -> 0x0048 }
            goto L_0x0038
        L_0x0048:
            r0 = move-exception
            java.lang.String r0 = "wait error"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0053 }
            com.tencent.bugly.proguard.an.e(r0, r2)     // Catch:{ all -> 0x0053 }
            goto L_0x0038
        L_0x0053:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0053 }
            throw r0     // Catch:{ Exception -> 0x0056 }
        L_0x0056:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.b(r0)
            if (r1 != 0) goto L_0x0034
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0034
        L_0x0061:
            monitor-exit(r1)     // Catch:{ all -> 0x0053 }
        L_0x0062:
            if (r7 != 0) goto L_0x0077
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0056 }
            java.lang.String r0 = r0.v     // Catch:{ Exception -> 0x0056 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x00b2
            java.lang.String r0 = "[beta] BetaModule is uninitialized"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ Exception -> 0x0056 }
        L_0x0077:
            if (r7 == 0) goto L_0x0034
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0056 }
            java.lang.String r0 = r0.v     // Catch:{ Exception -> 0x0056 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x00ff
            java.lang.String r0 = "[beta] BetaModule is uninitialized"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r0 = upgradeStateListener     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x00ec
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ Exception -> 0x0056 }
            r1 = 18
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0056 }
            r3 = 0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r4 = upgradeStateListener     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r3 = 1
            r4 = -1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r3 = 2
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0034
        L_0x00b2:
            java.lang.String r0 = "st.bch"
            android.os.Parcelable$Creator<com.tencent.bugly.beta.upgrade.BetaGrayStrategy> r1 = com.tencent.bugly.beta.upgrade.BetaGrayStrategy.CREATOR     // Catch:{ Exception -> 0x0056 }
            android.os.Parcelable r0 = com.tencent.bugly.beta.global.a.a(r0, r1)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x00d8
            com.tencent.bugly.proguard.y r1 = r0.a     // Catch:{ Exception -> 0x0056 }
            if (r1 == 0) goto L_0x00d8
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0056 }
            long r4 = r0.e     // Catch:{ Exception -> 0x0056 }
            long r2 = r2 - r4
            com.tencent.bugly.beta.global.e r1 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0056 }
            long r4 = r1.c     // Catch:{ Exception -> 0x0056 }
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 > 0) goto L_0x00d8
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ Exception -> 0x0056 }
            int r0 = r0.p     // Catch:{ Exception -> 0x0056 }
            if (r0 != r6) goto L_0x00df
        L_0x00d8:
            com.tencent.bugly.beta.upgrade.c r0 = com.tencent.bugly.beta.upgrade.c.a     // Catch:{ Exception -> 0x0056 }
            r1 = 0
            r0.a(r7, r8, r1)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0077
        L_0x00df:
            com.tencent.bugly.beta.upgrade.c r0 = com.tencent.bugly.beta.upgrade.c.a     // Catch:{ Exception -> 0x0056 }
            r3 = 0
            r4 = 0
            java.lang.String r5 = ""
            r1 = r7
            r2 = r8
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0077
        L_0x00ec:
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ Exception -> 0x0056 }
            r1 = 5
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0056 }
            r3 = 0
            java.lang.String r4 = strToastCheckUpgradeError     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0034
        L_0x00ff:
            com.tencent.bugly.beta.upgrade.c r0 = com.tencent.bugly.beta.upgrade.c.a     // Catch:{ Exception -> 0x0056 }
            r1 = 1
            r0.a(r7, r8, r1)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r0 = upgradeStateListener     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x012c
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ Exception -> 0x0056 }
            r1 = 18
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0056 }
            r3 = 0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r4 = upgradeStateListener     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r3 = 1
            r4 = 2
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r3 = 2
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r7)     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0034
        L_0x012c:
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ Exception -> 0x0056 }
            r1 = 5
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0056 }
            r3 = 0
            java.lang.String r4 = strToastCheckingUpgrade     // Catch:{ Exception -> 0x0056 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0056 }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0056 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ Exception -> 0x0056 }
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.Beta.checkUpgrade(boolean, boolean):void");
    }

    public static UpgradeInfo getUpgradeInfo() {
        try {
            c.a.b = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);
            if (c.a.b != null) {
                return new UpgradeInfo(c.a.b.a);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static synchronized void init(Context context, boolean isDebug) {
        synchronized (Beta.class) {
            an.a("Beta init start....", new Object[0]);
            ac a2 = ac.a();
            int i = instance.id;
            int i2 = e.a + 1;
            e.a = i2;
            a2.a(i, i2);
            if (TextUtils.isEmpty(initProcessName)) {
                initProcessName = context.getPackageName();
            }
            an.a("Beta will init at: %s", initProcessName);
            String str = com.tencent.bugly.crashreport.common.info.a.b().e;
            an.a("current process: %s", str);
            if (TextUtils.equals(initProcessName, str)) {
                e eVar = e.E;
                if (!TextUtils.isEmpty(eVar.v)) {
                    an.d("Beta has been initialized [apkMD5 : %s]", eVar.v);
                } else {
                    an.a("current upgrade sdk version:1.3.5", new Object[0]);
                    eVar.D = isDebug;
                    if (upgradeCheckPeriod < 0) {
                        an.d("upgradeCheckPeriod cannot be negative", new Object[0]);
                    } else {
                        eVar.c = upgradeCheckPeriod;
                        an.a("setted upgradeCheckPeriod: %d", Long.valueOf(upgradeCheckPeriod));
                    }
                    if (initDelay < 0) {
                        an.d("initDelay cannot be negative", new Object[0]);
                    } else {
                        eVar.b = initDelay;
                        an.a("setted initDelay: %d", Long.valueOf(initDelay));
                    }
                    if (smallIconId != 0) {
                        try {
                            if (context.getResources().getDrawable(smallIconId) != null) {
                                eVar.f = smallIconId;
                                an.a("setted smallIconId: %d", Integer.valueOf(smallIconId));
                            }
                        } catch (Exception e) {
                            an.e("smallIconId is not available:\n %s", e.toString());
                        }
                    }
                    if (largeIconId != 0) {
                        try {
                            if (context.getResources().getDrawable(largeIconId) != null) {
                                eVar.g = largeIconId;
                                an.a("setted largeIconId: %d", Integer.valueOf(largeIconId));
                            }
                        } catch (Exception e2) {
                            an.e("largeIconId is not available:\n %s", e2.toString());
                        }
                    }
                    if (defaultBannerId != 0) {
                        try {
                            if (context.getResources().getDrawable(defaultBannerId) != null) {
                                eVar.h = defaultBannerId;
                                an.a("setted defaultBannerId: %d", Integer.valueOf(defaultBannerId));
                            }
                        } catch (Exception e3) {
                            an.e("defaultBannerId is not available:\n %s", e3.toString());
                        }
                    }
                    if (upgradeDialogLayoutId != 0) {
                        try {
                            XmlResourceParser layout = context.getResources().getLayout(upgradeDialogLayoutId);
                            if (layout != null) {
                                eVar.i = upgradeDialogLayoutId;
                                an.a("setted upgradeDialogLayoutId: %d", Integer.valueOf(upgradeDialogLayoutId));
                                layout.close();
                            }
                        } catch (Exception e4) {
                            an.e("upgradeDialogLayoutId is not available:\n %s", e4.toString());
                        }
                    }
                    if (tipsDialogLayoutId != 0) {
                        try {
                            XmlResourceParser layout2 = context.getResources().getLayout(tipsDialogLayoutId);
                            if (layout2 != null) {
                                eVar.j = tipsDialogLayoutId;
                                an.a("setted tipsDialogLayoutId: %d", Integer.valueOf(tipsDialogLayoutId));
                                layout2.close();
                            }
                        } catch (Exception e5) {
                            an.e("tipsDialogLayoutId is not available:\n %s", e5.toString());
                        }
                    }
                    if (upgradeDialogLifecycleListener != null) {
                        try {
                            eVar.k = upgradeDialogLifecycleListener;
                            an.a("setted upgradeDialogLifecycleListener:%s" + upgradeDialogLifecycleListener, new Object[0]);
                        } catch (Exception e6) {
                            an.e("upgradeDialogLifecycleListener is not available:\n %", e6.toString());
                        }
                    }
                    if (canShowUpgradeActs != null && !canShowUpgradeActs.isEmpty()) {
                        for (Class cls : canShowUpgradeActs) {
                            if (cls != null) {
                                eVar.m.add(cls);
                            }
                        }
                        an.a("setted canShowUpgradeActs: %s", eVar.m);
                    }
                    if (canNotShowUpgradeActs != null && !canNotShowUpgradeActs.isEmpty()) {
                        for (Class cls2 : canNotShowUpgradeActs) {
                            if (cls2 != null) {
                                eVar.n.add(cls2);
                            }
                        }
                        an.a("setted canNotShowUpgradeActs: %s", eVar.n);
                    }
                    eVar.d = autoCheckUpgrade;
                    String str2 = "autoCheckUpgrade %s";
                    Object[] objArr = new Object[1];
                    objArr[0] = eVar.d ? "is opened" : "is closed";
                    an.a(str2, objArr);
                    eVar.ad = autoInstallApk;
                    String str3 = "autoInstallApk %s";
                    Object[] objArr2 = new Object[1];
                    objArr2[0] = eVar.ad ? "is opened" : "is closed";
                    an.a(str3, objArr2);
                    eVar.T = autoDownloadOn4g;
                    String str4 = "autoDownloadOn4g %s";
                    Object[] objArr3 = new Object[1];
                    objArr3[0] = eVar.T ? "is opened" : "is closed";
                    an.a(str4, objArr3);
                    eVar.e = showInterruptedStrategy;
                    String str5 = "showInterruptedStrategy %s";
                    Object[] objArr4 = new Object[1];
                    objArr4[0] = eVar.e ? "is opened" : "is closed";
                    an.a(str5, objArr4);
                    String str6 = "isDIY %s";
                    Object[] objArr5 = new Object[1];
                    objArr5[0] = upgradeListener != null ? "is opened" : "is closed";
                    an.a(str6, objArr5);
                    if (storageDir != null) {
                        if (storageDir.exists() || storageDir.mkdirs()) {
                            eVar.l = storageDir;
                            an.a("setted storageDir: %s", storageDir.getAbsolutePath());
                        } else {
                            an.a("storageDir is not exists: %s", storageDir.getAbsolutePath());
                        }
                    }
                    if (eVar.p == null) {
                        eVar.p = s.a;
                    }
                    if (TextUtils.isEmpty(eVar.u)) {
                        eVar.u = com.tencent.bugly.crashreport.common.info.a.b().f();
                    }
                    eVar.R = enableNotification;
                    an.a("enableNotification %s", enableNotification + "");
                    eVar.S = autoDownloadOnWifi;
                    an.a("autoDownloadOnWifi %s", autoDownloadOnWifi + "");
                    eVar.U = canShowApkInfo;
                    an.a("canShowApkInfo %s", canShowApkInfo + "");
                    eVar.V = canAutoPatch;
                    an.a("canAutoPatch %s", canAutoPatch + "");
                    eVar.W = betaPatchListener;
                    eVar.x = appVersionName;
                    eVar.w = appVersionCode;
                    eVar.X = canNotifyUserRestart;
                    an.a("canNotifyUserRestart %s", canNotifyUserRestart + "");
                    eVar.Y = canAutoDownloadPatch;
                    an.a("canAutoDownloadPatch %s", canAutoDownloadPatch + "");
                    eVar.Z = enableHotfix;
                    an.a("enableHotfix %s", enableHotfix + "");
                    TinkerManager.setPatchRestartOnScreenOff(setPatchRestartOnScreenOff);
                    an.a("setPatchRestartOnScreenOff %s", setPatchRestartOnScreenOff + "");
                    if (soBlackList != null && !soBlackList.isEmpty()) {
                        for (String str7 : soBlackList) {
                            if (str7 != null) {
                                eVar.aa.add(str7);
                            }
                        }
                        an.a("setted soBlackList: %s", eVar.aa);
                    }
                    if (appChannel != null) {
                        eVar.P = appChannel;
                        an.a("Beta channel %s", appChannel);
                    }
                    eVar.a(context);
                    ResBean.a = (ResBean) com.tencent.bugly.beta.global.a.a("rb.bch", ResBean.CREATOR);
                    if (ResBean.a == null) {
                        ResBean.a = new ResBean();
                    }
                    c.a.e = upgradeListener;
                    c.a.f = upgradeStateListener;
                    c.a.d = downloadListener;
                    if (!(getStrategyTask() == null || downloadListener == null)) {
                        getStrategyTask().addListener(c.a.d);
                    }
                    if (enableHotfix) {
                        an.a("enableHotfix %s", "1");
                        ap.b("D4", "1");
                        r.a(context);
                    }
                    Resources resources = context.getResources();
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    Configuration configuration = resources.getConfiguration();
                    configuration.locale = Locale.getDefault();
                    if (configuration.locale.equals(Locale.US) || configuration.locale.equals(Locale.ENGLISH)) {
                        strToastYourAreTheLatestVersion = context.getResources().getString(R.string.strToastYourAreTheLatestVersion);
                        strToastCheckUpgradeError = context.getResources().getString(R.string.strToastCheckUpgradeError);
                        strToastCheckingUpgrade = context.getResources().getString(R.string.strToastCheckingUpgrade);
                        strNotificationDownloading = context.getResources().getString(R.string.strNotificationDownloading);
                        strNotificationClickToView = context.getResources().getString(R.string.strNotificationClickToView);
                        strNotificationClickToInstall = context.getResources().getString(R.string.strNotificationClickToInstall);
                        strNotificationClickToContinue = context.getResources().getString(R.string.strNotificationClickToContinue);
                        strNotificationClickToRetry = context.getResources().getString(R.string.strNotificationClickToRetry);
                        strNotificationDownloadSucc = context.getResources().getString(R.string.strNotificationDownloadSucc);
                        strNotificationDownloadError = context.getResources().getString(R.string.strNotificationDownloadError);
                        strNotificationHaveNewVersion = context.getResources().getString(R.string.strNotificationHaveNewVersion);
                        strNetworkTipsMessage = context.getResources().getString(R.string.strNetworkTipsMessage);
                        strNetworkTipsTitle = context.getResources().getString(R.string.strNetworkTipsTitle);
                        strNetworkTipsConfirmBtn = context.getResources().getString(R.string.strNetworkTipsConfirmBtn);
                        strNetworkTipsCancelBtn = context.getResources().getString(R.string.strNetworkTipsCancelBtn);
                        strUpgradeDialogVersionLabel = context.getResources().getString(R.string.strUpgradeDialogVersionLabel);
                        strUpgradeDialogFileSizeLabel = context.getResources().getString(R.string.strUpgradeDialogFileSizeLabel);
                        strUpgradeDialogUpdateTimeLabel = context.getResources().getString(R.string.strUpgradeDialogUpdateTimeLabel);
                        strUpgradeDialogFeatureLabel = context.getResources().getString(R.string.strUpgradeDialogFeatureLabel);
                        strUpgradeDialogUpgradeBtn = context.getResources().getString(R.string.strUpgradeDialogUpgradeBtn);
                        strUpgradeDialogInstallBtn = context.getResources().getString(R.string.strUpgradeDialogInstallBtn);
                        strUpgradeDialogRetryBtn = context.getResources().getString(R.string.strUpgradeDialogRetryBtn);
                        strUpgradeDialogContinueBtn = context.getResources().getString(R.string.strUpgradeDialogContinueBtn);
                        strUpgradeDialogCancelBtn = context.getResources().getString(R.string.strUpgradeDialogCancelBtn);
                    }
                    resources.updateConfiguration(configuration, displayMetrics);
                    am.a().a(new d(1, new Object[0]), eVar.b);
                    ac a3 = ac.a();
                    int i3 = instance.id;
                    int i4 = e.a - 1;
                    e.a = i4;
                    a3.a(i3, i4);
                    an.a("Beta init finished...", new Object[0]);
                }
            }
        }
    }

    public synchronized void init(Context context, boolean isDebug, BuglyStrategy buglyStrategy) {
        com.tencent.bugly.crashreport.common.info.a.b().c("G10", "1.3.5");
        if (autoInit) {
            init(context, isDebug);
        }
    }

    public String[] getTables() {
        return new String[]{"dl_1002", "ge_1002", "st_1002"};
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0178  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDbUpgrade(android.database.sqlite.SQLiteDatabase r10, int r11, int r12) {
        /*
            r9 = this;
            r8 = 0
        L_0x0001:
            if (r11 >= r12) goto L_0x00e0
            switch(r11) {
                case 10: goto L_0x0009;
                default: goto L_0x0006;
            }
        L_0x0006:
            int r11 = r11 + 1
            goto L_0x0001
        L_0x0009:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e1 }
            r0.<init>()     // Catch:{ Throwable -> 0x00e1 }
            r1 = 0
            r0.setLength(r1)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r1 = " CREATE TABLE  IF NOT EXISTS "
            java.lang.StringBuilder r1 = r0.append(r1)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "st_1002"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " ( "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "_id"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "integer"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " , "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "_tp"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "text"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " , "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "_tm"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "int"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " , "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "_dt"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "blob"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = ",primary key("
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "_id"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "_tp"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = " )) "
            r1.append(r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r1 = "create %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00e1 }
            r3 = 0
            java.lang.String r4 = r0.toString()     // Catch:{ Throwable -> 0x00e1 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x00e1 }
            com.tencent.bugly.proguard.an.c(r1, r2)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00e1 }
            r10.execSQL(r0)     // Catch:{ Throwable -> 0x00e1 }
        L_0x00c9:
            java.lang.String r3 = "_id = 1002"
            java.lang.String r1 = "t_pf"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r10
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x017e, all -> 0x0174 }
            if (r1 != 0) goto L_0x00ec
            if (r1 == 0) goto L_0x00e0
            r1.close()
        L_0x00e0:
            return
        L_0x00e1:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.b(r0)
            if (r1 != 0) goto L_0x00c9
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00c9
        L_0x00ec:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x015c }
            if (r0 == 0) goto L_0x016d
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x015c }
            r0.<init>()     // Catch:{ Throwable -> 0x015c }
            java.lang.String r2 = "_id"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Throwable -> 0x015c }
            long r2 = r1.getLong(r2)     // Catch:{ Throwable -> 0x015c }
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x011d
            java.lang.String r2 = "_id"
            java.lang.String r3 = "_id"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Throwable -> 0x015c }
            long r4 = r1.getLong(r3)     // Catch:{ Throwable -> 0x015c }
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x015c }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x015c }
        L_0x011d:
            java.lang.String r2 = "_tm"
            java.lang.String r3 = "_tm"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Throwable -> 0x015c }
            long r4 = r1.getLong(r3)     // Catch:{ Throwable -> 0x015c }
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x015c }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x015c }
            java.lang.String r2 = "_tp"
            java.lang.String r3 = "_tp"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Throwable -> 0x015c }
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Throwable -> 0x015c }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x015c }
            java.lang.String r2 = "_dt"
            java.lang.String r3 = "_dt"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Throwable -> 0x015c }
            byte[] r3 = r1.getBlob(r3)     // Catch:{ Throwable -> 0x015c }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x015c }
            java.lang.String r2 = "st_1002"
            r3 = 0
            r10.replace(r2, r3, r0)     // Catch:{ Throwable -> 0x015c }
            goto L_0x00ec
        L_0x015c:
            r0 = move-exception
        L_0x015d:
            boolean r2 = com.tencent.bugly.proguard.an.b(r0)     // Catch:{ all -> 0x017c }
            if (r2 != 0) goto L_0x0166
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x017c }
        L_0x0166:
            if (r1 == 0) goto L_0x0006
            r1.close()
            goto L_0x0006
        L_0x016d:
            if (r1 == 0) goto L_0x0006
            r1.close()
            goto L_0x0006
        L_0x0174:
            r0 = move-exception
            r1 = r8
        L_0x0176:
            if (r1 == 0) goto L_0x017b
            r1.close()
        L_0x017b:
            throw r0
        L_0x017c:
            r0 = move-exception
            goto L_0x0176
        L_0x017e:
            r0 = move-exception
            r1 = r8
            goto L_0x015d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.Beta.onDbUpgrade(android.database.sqlite.SQLiteDatabase, int, int):void");
    }

    public void onDbDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void registerDownloadListener(DownloadListener dl) {
        e.E.q = dl;
        if (e.E.q != null && c.a.c != null) {
            c.a.c.addListener(dl);
        }
    }

    public static void unregisterDownloadListener() {
        if (c.a.c != null) {
            c.a.c.removeListener(e.E.q);
        }
        e.E.q = null;
    }

    public static DownloadTask startDownload() {
        if (c.a.h == null || c.a.h.b[0] != c.a.c) {
            c.a.h = new d(13, c.a.c, c.a.b);
        }
        c.a.h.run();
        return c.a.c;
    }

    public static void cancelDownload() {
        if (!(c.a.i != null && c.a.i.b[0] == c.a.c && c.a.i.b[1] == c.a.b && ((Boolean) c.a.i.b[2]).booleanValue() == c.a.g)) {
            c.a.i = new d(14, c.a.c, c.a.b, Boolean.valueOf(c.a.g));
        }
        c.a.i.run();
    }

    public static DownloadTask getStrategyTask() {
        if (a == null) {
            c.a.b = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);
            if (c.a.b != null) {
                a = e.E.p.a(c.a.b.a.f.b, e.E.t.getAbsolutePath(), null, c.a.b.a.f.a);
                c.a.c = a;
            }
        }
        return c.a.c;
    }

    public static synchronized void showUpgradeDialog(String title, int upgradeType, String newFeature, long publishTime, int buildNo, int versioncode, String versionName, String downloadUrl, long fileSize, String fileMd5, String bannerUrl, int dialogStyle, DownloadListener listener, Runnable upgradeRunnable, Runnable cancelRunnable, boolean isManual) {
        synchronized (Beta.class) {
            HashMap hashMap = new HashMap();
            hashMap.put("IMG_title", bannerUrl);
            hashMap.put("VAL_style", String.valueOf(dialogStyle));
            y yVar = new y(title, newFeature, publishTime, 0, new v(e.E.u, 1, versioncode, versionName, buildNo, "", 1, "", fileMd5, "1.3.5", ""), new u(fileMd5, downloadUrl, "", fileSize, ""), (byte) upgradeType, 0, 0, null, "", hashMap, null, 1, System.currentTimeMillis(), 1);
            if (a != null && !a.getDownloadUrl().equals(downloadUrl)) {
                a.delete(true);
                a = null;
            }
            if (a == null) {
                a = e.E.p.a(yVar.f.b, e.E.t.getAbsolutePath(), null, yVar.f.a);
            }
            a.addListener(listener);
            h.v.a(yVar, a);
            h.v.r = upgradeRunnable;
            h.v.s = cancelRunnable;
            f.a.a(e.E.p, yVar.l);
            if (isManual) {
                f fVar = f.a;
                Object[] objArr = new Object[2];
                objArr[0] = h.v;
                objArr[1] = Boolean.valueOf(yVar.g == 2);
                fVar.a((Runnable) new d(2, objArr), 3000);
            } else {
                f fVar2 = f.a;
                Object[] objArr2 = new Object[2];
                objArr2[0] = h.v;
                objArr2[1] = Boolean.valueOf(yVar.g == 2);
                fVar2.a(new d(2, objArr2));
            }
        }
    }

    public static synchronized void onUpgradeReceived(String title, int upgradeType, String newFeature, long publishTime, int buildNo, int versioncode, String versionName, String downloadUrl, long fileSize, String fileMd5, String bannerUrl, int dialogStyle, int popTimes, long popInterval, String strategyId, boolean isManual, boolean isSilence, int result, String errMsg, long updateTime) {
        String str;
        synchronized (Beta.class) {
            HashMap hashMap = new HashMap();
            hashMap.put("IMG_title", bannerUrl);
            hashMap.put("VAL_style", String.valueOf(dialogStyle));
            v vVar = new v(e.E.u, 1, versioncode, versionName, buildNo, "", 1, "", fileMd5, "", "");
            u uVar = new u(fileMd5, downloadUrl, "", fileSize, "");
            y yVar = new y(title, newFeature, publishTime, 0, vVar, uVar, (byte) upgradeType, popTimes, popInterval, null, "", hashMap, strategyId, 1, updateTime, 1);
            c cVar = c.a;
            if (errMsg == null) {
                str = "";
            } else {
                str = errMsg;
            }
            cVar.a(isManual, isSilence, result, yVar, str);
        }
    }

    public static synchronized y getUpgradeStrategy() {
        y yVar;
        synchronized (Beta.class) {
            c.a.b = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);
            try {
                if (c.a.b != null) {
                    yVar = (y) c.a.b.a.clone();
                }
            } catch (Exception e) {
            }
            yVar = null;
        }
        return yVar;
    }

    public static synchronized void installApk(File apkFile) {
        synchronized (Beta.class) {
            try {
                y upgradeStrategy = getUpgradeStrategy();
                if (upgradeStrategy != null && com.tencent.bugly.beta.global.a.a(e.E.s, apkFile, upgradeStrategy.f.a)) {
                    p.a.a(new w("install", System.currentTimeMillis(), 0, 0, upgradeStrategy.e, upgradeStrategy.m, upgradeStrategy.p, null));
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        return;
    }

    public static synchronized void saveInstallEvent(boolean installResult) {
        synchronized (Beta.class) {
            try {
                y upgradeStrategy = getUpgradeStrategy();
                if (upgradeStrategy != null && installResult) {
                    com.tencent.bugly.beta.global.a.a("installApkMd5", upgradeStrategy.f.a);
                    p.a.a(new w("install", System.currentTimeMillis(), 0, 0, upgradeStrategy.e, upgradeStrategy.m, upgradeStrategy.p, null));
                    an.a("安装事件保存成功", new Object[0]);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        return;
    }

    public static void applyTinkerPatch(Context context, String patchFile) {
        TinkerManager.getInstance().applyPatch(context, patchFile);
    }

    public static void downloadPatch() {
        q.a.b = q.a.a(null);
        try {
            if (q.a.b != null) {
                q.a.a(0, q.a.b.a, true);
            }
        } catch (Exception e) {
        }
    }

    public static void applyDownloadedPatch() {
        if (new File(e.E.H.getAbsolutePath()).exists()) {
            TinkerManager.getInstance().applyPatch(e.E.H.getAbsolutePath(), true);
        } else {
            an.c(Beta.class, "[applyDownloadedPatch] patch file not exist", new Object[0]);
        }
    }

    public static void installTinker() {
        enableHotfix = true;
        installTinker(TinkerApplicationLike.getTinkerPatchApplicationLike());
    }

    public static void installTinker(Object applicationLike) {
        enableHotfix = true;
        TinkerManager.installTinker(applicationLike);
    }

    public static void installTinker(Object applicationLike, Object loadReporter, Object patchReporter, Object patchListener, TinkerPatchResultListener tinkerPatchResultListener, Object upgradePatchProcessor) {
        enableHotfix = true;
        TinkerManager.installTinker(applicationLike, loadReporter, patchReporter, patchListener, tinkerPatchResultListener, upgradePatchProcessor);
    }

    public static void cleanTinkerPatch(boolean now) {
        com.tencent.bugly.beta.global.a.a("IS_PATCH_ROLL_BACK", false);
        TinkerManager.getInstance().cleanPatch(now);
    }

    public static void loadArmLibrary(Context context, String libName) {
        TinkerManager.loadArmLibrary(context, libName);
    }

    public static void loadArmV7Library(Context context, String libName) {
        TinkerManager.loadArmV7Library(context, libName);
    }

    public static void loadLibraryFromTinker(Context context, String relativePath, String libname) {
        TinkerManager.loadLibraryFromTinker(context, relativePath, libname);
    }

    public static void loadLibrary(String libName) {
        e eVar = e.E;
        if (libName != null) {
            try {
                if (!libName.isEmpty()) {
                    if (com.tencent.bugly.beta.global.a.b("LoadSoFileResult", true)) {
                        com.tencent.bugly.beta.global.a.a("LoadSoFileResult", false);
                        String b = com.tencent.bugly.beta.global.a.b(libName, "");
                        boolean b2 = com.tencent.bugly.beta.global.a.b("PatchResult", false);
                        if (TextUtils.isEmpty(b) || !b2) {
                            System.loadLibrary(libName);
                        } else {
                            TinkerManager.loadLibraryFromTinker(eVar.s, "lib/" + b, libName);
                        }
                        com.tencent.bugly.beta.global.a.a("LoadSoFileResult", true);
                        return;
                    }
                    System.loadLibrary(libName);
                    com.tencent.bugly.beta.global.a.a("IS_PATCH_ROLL_BACK", true);
                    cleanTinkerPatch(true);
                    return;
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                com.tencent.bugly.beta.global.a.a("LoadSoFileResult", false);
                return;
            }
        }
        an.e("libName is invalid", new Object[0]);
    }

    public static void unInit() {
        if (com.tencent.bugly.beta.global.a.b("IS_PATCH_ROLL_BACK", false)) {
            com.tencent.bugly.beta.global.a.a("IS_PATCH_ROLL_BACK", false);
            TinkerManager.getInstance().cleanPatch(true);
        }
    }
}
