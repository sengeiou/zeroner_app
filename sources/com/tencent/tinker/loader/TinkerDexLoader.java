package com.tencent.tinker.loader;

import android.annotation.TargetApi;
import android.content.Intent;
import android.util.Log;
import com.tencent.tinker.loader.TinkerDexOptimizer.ResultCallback;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareDexDiffPatchInfo;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class TinkerDexLoader {
    private static final String DEFAULT_DEX_OPTIMIZE_PATH = "odex";
    private static final String DEX_MEAT_FILE = "assets/dex_meta.txt";
    private static final String DEX_PATH = "dex";
    private static final String INTERPRET_DEX_OPTIMIZE_PATH = "interpet";
    private static final String TAG = "Tinker.TinkerDexLoader";
    private static HashSet<ShareDexDiffPatchInfo> classNDexInfo = new HashSet<>();
    private static boolean isVmArt = ShareTinkerInternals.isVmArt();
    private static final ArrayList<ShareDexDiffPatchInfo> loadDexList = new ArrayList<>();

    private TinkerDexLoader() {
    }

    @TargetApi(14)
    public static boolean loadTinkerJars(TinkerApplication application, String directory, String oatDir, Intent intentResult, boolean isSystemOTA) {
        if (!loadDexList.isEmpty() || !classNDexInfo.isEmpty()) {
            PathClassLoader classLoader = (PathClassLoader) TinkerDexLoader.class.getClassLoader();
            if (classLoader != null) {
                Log.i(TAG, "classloader: " + classLoader.toString());
                String dexPath = directory + "/" + "dex" + "/";
                ArrayList<File> legalFiles = new ArrayList<>();
                Iterator it = loadDexList.iterator();
                while (it.hasNext()) {
                    ShareDexDiffPatchInfo info = (ShareDexDiffPatchInfo) it.next();
                    if (!isJustArtSupportDex(info)) {
                        File file = new File(dexPath + info.realName);
                        if (application.isTinkerLoadVerifyFlag()) {
                            long start = System.currentTimeMillis();
                            if (!SharePatchFileUtil.verifyDexFileMd5(file, getInfoMd5(info))) {
                                ShareIntentUtil.setIntentReturnCode(intentResult, -13);
                                intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_MISMATCH_DEX_PATH, file.getAbsolutePath());
                                return false;
                            }
                            Log.i(TAG, "verify dex file:" + file.getPath() + " md5, use time: " + (System.currentTimeMillis() - start));
                        }
                        legalFiles.add(file);
                    }
                }
                if (isVmArt && !classNDexInfo.isEmpty()) {
                    File classNFile = new File(dexPath + ShareConstants.CLASS_N_APK_NAME);
                    long start2 = System.currentTimeMillis();
                    if (application.isTinkerLoadVerifyFlag()) {
                        Iterator it2 = classNDexInfo.iterator();
                        while (it2.hasNext()) {
                            ShareDexDiffPatchInfo info2 = (ShareDexDiffPatchInfo) it2.next();
                            if (!SharePatchFileUtil.verifyDexFileMd5(classNFile, info2.rawName, info2.destMd5InArt)) {
                                ShareIntentUtil.setIntentReturnCode(intentResult, -13);
                                intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_MISMATCH_DEX_PATH, classNFile.getAbsolutePath());
                                return false;
                            }
                        }
                    }
                    Log.i(TAG, "verify dex file:" + classNFile.getPath() + " md5, use time: " + (System.currentTimeMillis() - start2));
                    legalFiles.add(classNFile);
                }
                File optimizeDir = new File(directory + "/" + oatDir);
                if (isSystemOTA) {
                    final boolean[] parallelOTAResult = {true};
                    final Throwable[] parallelOTAThrowable = new Throwable[1];
                    try {
                        String targetISA = ShareTinkerInternals.getCurrentInstructionSet();
                        deleteOutOfDateOATFile(directory);
                        Log.w(TAG, "systemOTA, try parallel oat dexes, targetISA:" + targetISA);
                        optimizeDir = new File(directory + "/" + "interpet");
                        AnonymousClass1 r0 = new ResultCallback() {
                            long start;

                            public void onStart(File dexFile, File optimizedDir) {
                                this.start = System.currentTimeMillis();
                                Log.i(TinkerDexLoader.TAG, "start to optimize dex:" + dexFile.getPath());
                            }

                            public void onSuccess(File dexFile, File optimizedDir, File optimizedFile) {
                                Log.i(TinkerDexLoader.TAG, "success to optimize dex " + dexFile.getPath() + ", use time " + (System.currentTimeMillis() - this.start));
                            }

                            public void onFailed(File dexFile, File optimizedDir, Throwable thr) {
                                parallelOTAResult[0] = false;
                                parallelOTAThrowable[0] = thr;
                                Log.i(TinkerDexLoader.TAG, "fail to optimize dex " + dexFile.getPath() + ", use time " + (System.currentTimeMillis() - this.start));
                            }
                        };
                        TinkerDexOptimizer.optimizeAll(legalFiles, optimizeDir, true, targetISA, r0);
                        if (!parallelOTAResult[0]) {
                            Log.e(TAG, "parallel oat dexes failed");
                            intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_INTERPRET_EXCEPTION, parallelOTAThrowable[0]);
                            ShareIntentUtil.setIntentReturnCode(intentResult, -16);
                            return false;
                        }
                    } catch (Throwable throwable) {
                        Log.i(TAG, "getCurrentInstructionSet fail:" + throwable);
                        deleteOutOfDateOATFile(directory);
                        intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_INTERPRET_EXCEPTION, throwable);
                        ShareIntentUtil.setIntentReturnCode(intentResult, -15);
                        return false;
                    }
                }
                try {
                    SystemClassLoaderAdder.installDexes(application, classLoader, optimizeDir, legalFiles);
                    return true;
                } catch (Throwable e) {
                    Log.e(TAG, "install dexes failed");
                    intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_EXCEPTION, e);
                    ShareIntentUtil.setIntentReturnCode(intentResult, -14);
                    return false;
                }
            } else {
                Log.e(TAG, "classloader is null");
                ShareIntentUtil.setIntentReturnCode(intentResult, -12);
                return false;
            }
        } else {
            Log.w(TAG, "there is no dex to load");
            return true;
        }
    }

    public static boolean checkComplete(String directory, ShareSecurityCheck securityCheck, String oatDir, Intent intentResult) {
        String meta = (String) securityCheck.getMetaContentMap().get("assets/dex_meta.txt");
        if (meta == null) {
            return true;
        }
        loadDexList.clear();
        classNDexInfo.clear();
        ArrayList<ShareDexDiffPatchInfo> allDexInfo = new ArrayList<>();
        ShareDexDiffPatchInfo.parseDexDiffPatchInfo(meta, allDexInfo);
        if (allDexInfo.isEmpty()) {
            return true;
        }
        HashMap<String, String> dexes = new HashMap<>();
        ShareDexDiffPatchInfo testInfo = null;
        Iterator it = allDexInfo.iterator();
        while (it.hasNext()) {
            ShareDexDiffPatchInfo info = (ShareDexDiffPatchInfo) it.next();
            if (!isJustArtSupportDex(info)) {
                if (!ShareDexDiffPatchInfo.checkDexDiffPatchInfo(info)) {
                    intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_PACKAGE_PATCH_CHECK, -3);
                    ShareIntentUtil.setIntentReturnCode(intentResult, -8);
                    return false;
                } else if (isVmArt && info.rawName.startsWith(ShareConstants.TEST_DEX_NAME)) {
                    testInfo = info;
                } else if (!isVmArt || !ShareConstants.CLASS_N_PATTERN.matcher(info.realName).matches()) {
                    dexes.put(info.realName, getInfoMd5(info));
                    loadDexList.add(info);
                } else {
                    classNDexInfo.add(info);
                }
            }
        }
        if (isVmArt && (testInfo != null || !classNDexInfo.isEmpty())) {
            if (testInfo != null) {
                classNDexInfo.add(ShareTinkerInternals.changeTestDexToClassN(testInfo, classNDexInfo.size() + 1));
            }
            dexes.put(ShareConstants.CLASS_N_APK_NAME, "");
        }
        String dexDirectory = directory + "/" + "dex" + "/";
        File dexDir = new File(dexDirectory);
        if (!dexDir.exists() || !dexDir.isDirectory()) {
            ShareIntentUtil.setIntentReturnCode(intentResult, -9);
            return false;
        }
        File optimizeDexDirectoryFile = new File(directory + "/" + oatDir + "/");
        for (String name : dexes.keySet()) {
            File dexFile = new File(dexDirectory + name);
            if (!SharePatchFileUtil.isLegalFile(dexFile)) {
                intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_MISSING_DEX_PATH, dexFile.getAbsolutePath());
                ShareIntentUtil.setIntentReturnCode(intentResult, -10);
                return false;
            }
            File dexOptFile = new File(SharePatchFileUtil.optimizedPathFor(dexFile, optimizeDexDirectoryFile));
            if (!SharePatchFileUtil.isLegalFile(dexOptFile)) {
                intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_MISSING_DEX_PATH, dexOptFile.getAbsolutePath());
                ShareIntentUtil.setIntentReturnCode(intentResult, -11);
                return false;
            }
        }
        intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_DEXES_PATH, dexes);
        return true;
    }

    private static String getInfoMd5(ShareDexDiffPatchInfo info) {
        return isVmArt ? info.destMd5InArt : info.destMd5InDvm;
    }

    private static void deleteOutOfDateOATFile(String directory) {
        SharePatchFileUtil.deleteDir(directory + "/" + "odex" + "/");
        if (ShareTinkerInternals.isAfterAndroidO()) {
            SharePatchFileUtil.deleteDir(directory + "/" + "dex" + "/" + ShareConstants.ANDROID_O_DEX_OPTIMIZE_PATH + "/");
        }
    }

    private static boolean isJustArtSupportDex(ShareDexDiffPatchInfo dexDiffPatchInfo) {
        if (!isVmArt && dexDiffPatchInfo.destMd5InDvm.equals("0")) {
            return true;
        }
        return false;
    }
}
