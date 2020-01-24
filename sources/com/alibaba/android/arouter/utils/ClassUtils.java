package com.alibaba.android.arouter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.util.Log;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassUtils {
    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String PREFS_FILE = "multidex.version";
    private static final String SECONDARY_FOLDER_NAME = ("code_cache" + File.separator + "secondary-dexes");
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, VERSION.SDK_INT < 11 ? 0 : 4);
    }

    public static Set<String> getFileNameByPackageName(Context context, final String packageName) throws NameNotFoundException, IOException, InterruptedException {
        final Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePaths(context);
        final CountDownLatch parserCtl = new CountDownLatch(paths.size());
        for (final String path : paths) {
            DefaultPoolExecutor.getInstance().execute(new Runnable() {
                public void run() {
                    DexFile dexfile;
                    DexFile dexfile2 = null;
                    try {
                        if (path.endsWith(ClassUtils.EXTRACTED_SUFFIX)) {
                            dexfile = DexFile.loadDex(path, path + ".tmp", 0);
                        } else {
                            dexfile = new DexFile(path);
                        }
                        Enumeration<String> dexEntries = dexfile.entries();
                        while (dexEntries.hasMoreElements()) {
                            String className = (String) dexEntries.nextElement();
                            if (className.startsWith(packageName)) {
                                classNames.add(className);
                            }
                        }
                        if (dexfile != null) {
                            try {
                                dexfile.close();
                            } catch (Throwable th) {
                            }
                        }
                        parserCtl.countDown();
                        return;
                    } catch (Throwable th2) {
                    }
                    parserCtl.countDown();
                }
            });
        }
        parserCtl.await();
        Log.d("ARouter::", "Filter " + classNames.size() + " classes by packageName <" + packageName + ">");
        return classNames;
    }

    public static List<String> getSourcePaths(Context context) throws NameNotFoundException, IOException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        File sourceApk = new File(applicationInfo.sourceDir);
        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
        if (!isVMMultidexCapable()) {
            int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);
            File dexDir = new File(applicationInfo.dataDir, SECONDARY_FOLDER_NAME);
            int secondaryNumber = 2;
            while (secondaryNumber <= totalDexNumber) {
                File extractedFile = new File(dexDir, extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX);
                if (extractedFile.isFile()) {
                    sourcePaths.add(extractedFile.getAbsolutePath());
                    secondaryNumber++;
                } else {
                    throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
                }
            }
        }
        if (ARouter.debuggable()) {
            sourcePaths.addAll(tryLoadInstantRunDexFile(applicationInfo));
        }
        return sourcePaths;
    }

    private static List<String> tryLoadInstantRunDexFile(ApplicationInfo applicationInfo) {
        File[] dexFile;
        List<String> instantRunSourcePaths = new ArrayList<>();
        if (VERSION.SDK_INT < 21 || applicationInfo.splitSourceDirs == null) {
            try {
                File instantRunFilePath = new File((String) Class.forName("com.android.tools.fd.runtime.Paths").getMethod("getDexFileDirectory", new Class[]{String.class}).invoke(null, new Object[]{applicationInfo.packageName}));
                if (instantRunFilePath.exists() && instantRunFilePath.isDirectory()) {
                    for (File file : instantRunFilePath.listFiles()) {
                        if (file != null && file.exists() && file.isFile() && file.getName().endsWith(ShareConstants.DEX_SUFFIX)) {
                            instantRunSourcePaths.add(file.getAbsolutePath());
                        }
                    }
                    Log.d("ARouter::", "Found InstantRun support");
                }
            } catch (Exception e) {
                Log.e("ARouter::", "InstantRun support error, " + e.getMessage());
            }
        } else {
            instantRunSourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            Log.d("ARouter::", "Found InstantRun support");
        }
        return instantRunSourcePaths;
    }

    private static boolean isVMMultidexCapable() {
        boolean isMultidexCapable = false;
        String vmName = null;
        try {
            if (isYunOS()) {
                vmName = "'YunOS'";
                isMultidexCapable = Integer.valueOf(System.getProperty("ro.build.version.sdk")).intValue() >= 21;
            } else {
                vmName = "'Android'";
                String versionString = System.getProperty("java.vm.version");
                if (versionString != null) {
                    Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
                    if (matcher.matches()) {
                        try {
                            int major = Integer.parseInt(matcher.group(1));
                            isMultidexCapable = major > 2 || (major == 2 && Integer.parseInt(matcher.group(2)) >= 1);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (Exception e2) {
        }
        Log.i("ARouter::", "VM with name " + vmName + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    private static boolean isYunOS() {
        try {
            String version = System.getProperty("ro.yunos.version");
            String vmName = System.getProperty("java.vm.name");
            if ((vmName == null || !vmName.toLowerCase().contains("lemur")) && (version == null || version.trim().length() <= 0)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
