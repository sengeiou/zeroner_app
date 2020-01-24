package com.tencent.tinker.loader.shareutil;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ShareTinkerInternals {
    private static final String PATCH_PROCESS_NAME = ":patch";
    private static final String TAG = "Tinker.TinkerInternals";
    private static final boolean VM_IS_ART = isVmArt(System.getProperty("java.vm.version"));
    private static final boolean VM_IS_JIT = isVmJitInternal();
    private static String currentInstructionSet = null;
    private static Boolean isPatchProcess = null;
    private static String processName = null;
    private static String tinkerID = null;

    public static boolean isVmArt() {
        return VM_IS_ART || VERSION.SDK_INT >= 21;
    }

    public static boolean isVmJit() {
        return VM_IS_JIT && VERSION.SDK_INT < 24;
    }

    public static boolean isAfterAndroidO() {
        return VERSION.SDK_INT > 25;
    }

    public static String getCurrentInstructionSet() throws Exception {
        if (currentInstructionSet != null) {
            return currentInstructionSet;
        }
        currentInstructionSet = (String) Class.forName("dalvik.system.VMRuntime").getDeclaredMethod("getCurrentInstructionSet", new Class[0]).invoke(null, new Object[0]);
        Log.d(TAG, "getCurrentInstructionSet:" + currentInstructionSet);
        return currentInstructionSet;
    }

    public static boolean isSystemOTA(String lastFingerPrint) {
        String currentFingerprint = Build.FINGERPRINT;
        if (lastFingerPrint == null || lastFingerPrint.equals("") || currentFingerprint == null || currentFingerprint.equals("")) {
            Log.d(TAG, "fingerprint empty:" + lastFingerPrint + ",current:" + currentFingerprint);
            return false;
        } else if (lastFingerPrint.equals(currentFingerprint)) {
            Log.d(TAG, "same fingerprint:" + currentFingerprint);
            return false;
        } else {
            Log.d(TAG, "system OTA,fingerprint not equal:" + lastFingerPrint + "," + currentFingerprint);
            return true;
        }
    }

    public static ShareDexDiffPatchInfo changeTestDexToClassN(ShareDexDiffPatchInfo rawDexInfo, int index) {
        String newName;
        if (!rawDexInfo.rawName.startsWith(ShareConstants.TEST_DEX_NAME)) {
            return null;
        }
        if (index != 1) {
            newName = "classes" + index + ShareConstants.DEX_SUFFIX;
        } else {
            newName = "classes.dex";
        }
        return new ShareDexDiffPatchInfo(newName, rawDexInfo.path, rawDexInfo.destMd5InDvm, rawDexInfo.destMd5InArt, rawDexInfo.dexDiffMd5, rawDexInfo.oldDexCrC, rawDexInfo.newOrPatchedDexCrC, rawDexInfo.dexMode);
    }

    public static boolean isNullOrNil(String object) {
        if (object == null || object.length() <= 0) {
            return true;
        }
        return false;
    }

    public static int checkTinkerPackage(Context context, int tinkerFlag, File patchFile, ShareSecurityCheck securityCheck) {
        int returnCode = checkSignatureAndTinkerID(context, patchFile, securityCheck);
        if (returnCode == 0) {
            return checkPackageAndTinkerFlag(securityCheck, tinkerFlag);
        }
        return returnCode;
    }

    public static int checkSignatureAndTinkerID(Context context, File patchFile, ShareSecurityCheck securityCheck) {
        if (!securityCheck.verifyPatchMetaSignature(patchFile)) {
            return -1;
        }
        String oldTinkerId = getManifestTinkerID(context);
        if (oldTinkerId == null) {
            return -5;
        }
        HashMap<String, String> properties = securityCheck.getPackagePropertiesIfPresent();
        if (properties == null) {
            return -2;
        }
        String patchTinkerId = (String) properties.get(ShareConstants.TINKER_ID);
        if (patchTinkerId == null) {
            return -6;
        }
        if (oldTinkerId.equals(patchTinkerId)) {
            return 0;
        }
        Log.e(TAG, "tinkerId is not equal, base is " + oldTinkerId + ", but patch is " + patchTinkerId);
        return -7;
    }

    public static int checkPackageAndTinkerFlag(ShareSecurityCheck securityCheck, int tinkerFlag) {
        if (isTinkerEnabledAll(tinkerFlag)) {
            return 0;
        }
        HashMap<String, String> metaContentMap = securityCheck.getMetaContentMap();
        if (!isTinkerEnabledForDex(tinkerFlag) && metaContentMap.containsKey(ShareConstants.DEX_META_FILE)) {
            return -9;
        }
        if (!isTinkerEnabledForNativeLib(tinkerFlag) && metaContentMap.containsKey(ShareConstants.SO_META_FILE)) {
            return -9;
        }
        if (isTinkerEnabledForResource(tinkerFlag) || !metaContentMap.containsKey(ShareConstants.RES_META_FILE)) {
            return 0;
        }
        return -9;
    }

    public static Properties fastGetPatchPackageMeta(File patchFile) {
        InputStream inputStream;
        if (patchFile == null || !patchFile.isFile() || patchFile.length() == 0) {
            Log.e(TAG, "patchFile is illegal");
            return null;
        }
        ZipFile zipFile = null;
        try {
            ZipFile zipFile2 = new ZipFile(patchFile);
            try {
                ZipEntry packageEntry = zipFile2.getEntry(ShareConstants.PACKAGE_META_FILE);
                if (packageEntry == null) {
                    Log.e(TAG, "patch meta entry not found");
                    SharePatchFileUtil.closeZip(zipFile2);
                    return null;
                }
                inputStream = null;
                inputStream = zipFile2.getInputStream(packageEntry);
                Properties properties = new Properties();
                properties.load(inputStream);
                SharePatchFileUtil.closeQuietly(inputStream);
                SharePatchFileUtil.closeZip(zipFile2);
                return properties;
            } catch (IOException e) {
                e = e;
                zipFile = zipFile2;
                try {
                    Log.e(TAG, "fastGetPatchPackageMeta exception:" + e.getMessage());
                    SharePatchFileUtil.closeZip(zipFile);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    SharePatchFileUtil.closeZip(zipFile);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                zipFile = zipFile2;
                SharePatchFileUtil.closeZip(zipFile);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.e(TAG, "fastGetPatchPackageMeta exception:" + e.getMessage());
            SharePatchFileUtil.closeZip(zipFile);
            return null;
        }
    }

    public static String getManifestTinkerID(Context context) {
        if (tinkerID != null) {
            return tinkerID;
        }
        try {
            Object object = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get(ShareConstants.TINKER_ID);
            if (object != null) {
                tinkerID = String.valueOf(object);
            } else {
                tinkerID = null;
            }
            return tinkerID;
        } catch (Exception e) {
            Log.e(TAG, "getManifestTinkerID exception:" + e.getMessage());
            return null;
        }
    }

    public static boolean isTinkerEnabledForDex(int flag) {
        return (flag & 1) != 0;
    }

    public static boolean isTinkerEnabledForNativeLib(int flag) {
        return (flag & 2) != 0;
    }

    public static boolean isTinkerEnabledForResource(int flag) {
        return (flag & 4) != 0;
    }

    public static String getTypeString(int type) {
        switch (type) {
            case 1:
                return "patch_file";
            case 2:
                return "patch_info";
            case 3:
                return "dex";
            case 4:
                return "dex_opt";
            case 5:
                return ShareConstants.SO_PATH;
            case 6:
                return "resource";
            default:
                return "unknown";
        }
    }

    public static void setTinkerDisableWithSharedPreferences(Context context) {
        context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, 4).edit().putBoolean(getTinkerSharedPreferencesName(), false).commit();
    }

    public static boolean isTinkerEnableWithSharedPreferences(Context context) {
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, 4).getBoolean(getTinkerSharedPreferencesName(), true);
    }

    private static String getTinkerSharedPreferencesName() {
        return "tinker_enable_1.9.6";
    }

    public static boolean isTinkerEnabled(int flag) {
        return flag != 0;
    }

    public static boolean isTinkerEnabledAll(int flag) {
        return flag == 7;
    }

    public static boolean isInMainProcess(Context context) {
        String mainProcessName = null;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo != null) {
            mainProcessName = applicationInfo.processName;
        }
        if (isNullOrNil(mainProcessName)) {
            mainProcessName = context.getPackageName();
        }
        String processName2 = getProcessName(context);
        if (processName2 == null || processName2.length() == 0) {
            processName2 = "";
        }
        return mainProcessName.equals(processName2);
    }

    public static boolean isInPatchProcess(Context context) {
        if (isPatchProcess != null) {
            return isPatchProcess.booleanValue();
        }
        isPatchProcess = Boolean.valueOf(getProcessName(context).endsWith(PATCH_PROCESS_NAME));
        return isPatchProcess.booleanValue();
    }

    public static String getCurrentOatMode(Context context, String current) {
        if (!current.equals(ShareConstants.CHANING_DEX_OPTIMIZE_PATH)) {
            return current;
        }
        if (isInMainProcess(context)) {
            return "odex";
        }
        return ShareConstants.INTERPRET_DEX_OPTIMIZE_PATH;
    }

    public static void killAllOtherProcess(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
        if (am != null) {
            List<RunningAppProcessInfo> appProcessList = am.getRunningAppProcesses();
            if (appProcessList != null) {
                for (RunningAppProcessInfo ai : appProcessList) {
                    if (ai.uid == Process.myUid() && ai.pid != Process.myPid()) {
                        Process.killProcess(ai.pid);
                    }
                }
            }
        }
    }

    public static String getProcessName(Context context) {
        if (processName != null) {
            return processName;
        }
        processName = getProcessNameInternal(context);
        return processName;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d3 A[SYNTHETIC, Splitter:B:50:0x00d3] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00dc A[SYNTHETIC, Splitter:B:55:0x00dc] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getProcessNameInternal(android.content.Context r15) {
        /*
            r14 = 128(0x80, float:1.794E-43)
            int r8 = android.os.Process.myPid()
            if (r15 == 0) goto L_0x000a
            if (r8 > 0) goto L_0x000e
        L_0x000a:
            java.lang.String r11 = ""
        L_0x000d:
            return r11
        L_0x000e:
            r9 = 0
            java.lang.String r11 = "activity"
            java.lang.Object r0 = r15.getSystemService(r11)
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0
            if (r0 == 0) goto L_0x005a
            java.util.List r1 = r0.getRunningAppProcesses()
            if (r1 == 0) goto L_0x005a
            java.util.Iterator r11 = r1.iterator()     // Catch:{ Exception -> 0x003a }
        L_0x0024:
            boolean r12 = r11.hasNext()     // Catch:{ Exception -> 0x003a }
            if (r12 == 0) goto L_0x0035
            java.lang.Object r10 = r11.next()     // Catch:{ Exception -> 0x003a }
            android.app.ActivityManager$RunningAppProcessInfo r10 = (android.app.ActivityManager.RunningAppProcessInfo) r10     // Catch:{ Exception -> 0x003a }
            int r12 = r10.pid     // Catch:{ Exception -> 0x003a }
            if (r12 != r8) goto L_0x0024
            r9 = r10
        L_0x0035:
            if (r9 == 0) goto L_0x005a
            java.lang.String r11 = r9.processName
            goto L_0x000d
        L_0x003a:
            r3 = move-exception
            java.lang.String r11 = "Tinker.TinkerInternals"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "getProcessNameInternal exception:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r3.getMessage()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            android.util.Log.e(r11, r12)
            goto L_0x0035
        L_0x005a:
            byte[] r2 = new byte[r14]
            r5 = 0
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b2 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b2 }
            r11.<init>()     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r12 = "/proc/"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00b2 }
            java.lang.StringBuilder r11 = r11.append(r8)     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r12 = "/cmdline"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00b2 }
            r6.<init>(r11)     // Catch:{ Exception -> 0x00b2 }
            int r7 = r6.read(r2)     // Catch:{ Exception -> 0x00e5, all -> 0x00e2 }
            if (r7 <= 0) goto L_0x00a4
            r4 = 0
        L_0x0084:
            if (r4 >= r7) goto L_0x0091
            byte r11 = r2[r4]     // Catch:{ Exception -> 0x00e5, all -> 0x00e2 }
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r11 > r14) goto L_0x0090
            byte r11 = r2[r4]     // Catch:{ Exception -> 0x00e5, all -> 0x00e2 }
            if (r11 > 0) goto L_0x00a1
        L_0x0090:
            r7 = r4
        L_0x0091:
            java.lang.String r11 = new java.lang.String     // Catch:{ Exception -> 0x00e5, all -> 0x00e2 }
            r12 = 0
            r11.<init>(r2, r12, r7)     // Catch:{ Exception -> 0x00e5, all -> 0x00e2 }
            if (r6 == 0) goto L_0x000d
            r6.close()     // Catch:{ Exception -> 0x009e }
            goto L_0x000d
        L_0x009e:
            r12 = move-exception
            goto L_0x000d
        L_0x00a1:
            int r4 = r4 + 1
            goto L_0x0084
        L_0x00a4:
            if (r6 == 0) goto L_0x00a9
            r6.close()     // Catch:{ Exception -> 0x00af }
        L_0x00a9:
            r5 = r6
        L_0x00aa:
            java.lang.String r11 = ""
            goto L_0x000d
        L_0x00af:
            r11 = move-exception
            r5 = r6
            goto L_0x00aa
        L_0x00b2:
            r3 = move-exception
        L_0x00b3:
            java.lang.String r11 = "Tinker.TinkerInternals"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d9 }
            r12.<init>()     // Catch:{ all -> 0x00d9 }
            java.lang.String r13 = "getProcessNameInternal exception:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x00d9 }
            java.lang.String r13 = r3.getMessage()     // Catch:{ all -> 0x00d9 }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x00d9 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x00d9 }
            android.util.Log.e(r11, r12)     // Catch:{ all -> 0x00d9 }
            if (r5 == 0) goto L_0x00aa
            r5.close()     // Catch:{ Exception -> 0x00d7 }
            goto L_0x00aa
        L_0x00d7:
            r11 = move-exception
            goto L_0x00aa
        L_0x00d9:
            r11 = move-exception
        L_0x00da:
            if (r5 == 0) goto L_0x00df
            r5.close()     // Catch:{ Exception -> 0x00e0 }
        L_0x00df:
            throw r11
        L_0x00e0:
            r12 = move-exception
            goto L_0x00df
        L_0x00e2:
            r11 = move-exception
            r5 = r6
            goto L_0x00da
        L_0x00e5:
            r3 = move-exception
            r5 = r6
            goto L_0x00b3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.loader.shareutil.ShareTinkerInternals.getProcessNameInternal(android.content.Context):java.lang.String");
    }

    private static boolean isVmArt(String versionString) {
        if (versionString == null) {
            return false;
        }
        Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
        if (!matcher.matches()) {
            return false;
        }
        try {
            int major = Integer.parseInt(matcher.group(1));
            return major > 2 || (major == 2 && Integer.parseInt(matcher.group(2)) >= 1);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isVmJitInternal() {
        try {
            Method mthGet = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
            String jit = (String) mthGet.invoke(null, new Object[]{"dalvik.vm.usejit"});
            String jitProfile = (String) mthGet.invoke(null, new Object[]{"dalvik.vm.usejitprofiles"});
            if (!isNullOrNil(jit) && isNullOrNil(jitProfile) && jit.equals("true")) {
                return true;
            }
        } catch (Throwable e) {
            Log.e(TAG, "isVmJitInternal ex:" + e);
        }
        return false;
    }

    public static String getExceptionCauseString(Throwable ex) {
        if (ex == null) {
            return "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        Throwable t = ex;
        while (true) {
            try {
                Throwable cause = t.getCause();
                if (cause == null) {
                    ThrowableExtension.printStackTrace(t, ps);
                    return toVisualString(bos.toString());
                }
                t = cause;
            } finally {
                SharePatchFileUtil.closeQuietly(ps);
            }
        }
    }

    public static String toVisualString(String src) {
        boolean cutFlg = false;
        if (src == null) {
            return null;
        }
        char[] chr = src.toCharArray();
        if (chr == null) {
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= chr.length) {
                break;
            } else if (chr[i] > 127) {
                chr[i] = 0;
                cutFlg = true;
                break;
            } else {
                i++;
            }
        }
        if (cutFlg) {
            return new String(chr, 0, i);
        }
        return src;
    }
}
