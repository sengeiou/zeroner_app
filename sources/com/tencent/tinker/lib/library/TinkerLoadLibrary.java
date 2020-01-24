package com.tencent.tinker.lib.library;

import android.content.Context;
import android.os.Build.VERSION;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerApplicationHelper;
import com.tencent.tinker.lib.tinker.TinkerLoadResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TinkerLoadLibrary {
    private static final String TAG = "Tinker.LoadLibrary";

    private static final class V14 {
        private V14() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader classLoader, File folder) throws Throwable {
            Object dexPathList = ShareReflectUtil.findField((Object) classLoader, "pathList").get(classLoader);
            Field nativeLibDirField = ShareReflectUtil.findField(dexPathList, "nativeLibraryDirectories");
            File[] origNativeLibDirs = (File[]) nativeLibDirField.get(dexPathList);
            List<File> newNativeLibDirList = new ArrayList<>(origNativeLibDirs.length + 1);
            newNativeLibDirList.add(folder);
            for (File origNativeLibDir : origNativeLibDirs) {
                if (!folder.equals(origNativeLibDir)) {
                    newNativeLibDirList.add(origNativeLibDir);
                }
            }
            nativeLibDirField.set(dexPathList, newNativeLibDirList.toArray(new File[0]));
        }
    }

    private static final class V23 {
        private V23() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader classLoader, File folder) throws Throwable {
            Object dexPathList = ShareReflectUtil.findField((Object) classLoader, "pathList").get(classLoader);
            List<File> origLibDirs = (List) ShareReflectUtil.findField(dexPathList, "nativeLibraryDirectories").get(dexPathList);
            if (origLibDirs == null) {
                origLibDirs = new ArrayList<>(2);
            }
            Iterator<File> libDirIt = origLibDirs.iterator();
            while (true) {
                if (!libDirIt.hasNext()) {
                    break;
                }
                if (folder.equals((File) libDirIt.next())) {
                    libDirIt.remove();
                    break;
                }
            }
            origLibDirs.add(0, folder);
            List<File> origSystemLibDirs = (List) ShareReflectUtil.findField(dexPathList, "systemNativeLibraryDirectories").get(dexPathList);
            if (origSystemLibDirs == null) {
                origSystemLibDirs = new ArrayList<>(2);
            }
            List<File> newLibDirs = new ArrayList<>(origLibDirs.size() + origSystemLibDirs.size() + 1);
            newLibDirs.addAll(origLibDirs);
            newLibDirs.addAll(origSystemLibDirs);
            ShareReflectUtil.findField(dexPathList, "nativeLibraryPathElements").set(dexPathList, (Object[]) ShareReflectUtil.findMethod(dexPathList, "makePathElements", (Class<?>[]) new Class[]{List.class, File.class, List.class}).invoke(dexPathList, new Object[]{newLibDirs, null, new ArrayList<>()}));
        }
    }

    private static final class V25 {
        private V25() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader classLoader, File folder) throws Throwable {
            Object dexPathList = ShareReflectUtil.findField((Object) classLoader, "pathList").get(classLoader);
            List<File> origLibDirs = (List) ShareReflectUtil.findField(dexPathList, "nativeLibraryDirectories").get(dexPathList);
            if (origLibDirs == null) {
                origLibDirs = new ArrayList<>(2);
            }
            Iterator<File> libDirIt = origLibDirs.iterator();
            while (true) {
                if (!libDirIt.hasNext()) {
                    break;
                }
                if (folder.equals((File) libDirIt.next())) {
                    libDirIt.remove();
                    break;
                }
            }
            origLibDirs.add(0, folder);
            List<File> origSystemLibDirs = (List) ShareReflectUtil.findField(dexPathList, "systemNativeLibraryDirectories").get(dexPathList);
            if (origSystemLibDirs == null) {
                origSystemLibDirs = new ArrayList<>(2);
            }
            List<File> newLibDirs = new ArrayList<>(origLibDirs.size() + origSystemLibDirs.size() + 1);
            newLibDirs.addAll(origLibDirs);
            newLibDirs.addAll(origSystemLibDirs);
            ShareReflectUtil.findField(dexPathList, "nativeLibraryPathElements").set(dexPathList, (Object[]) ShareReflectUtil.findMethod(dexPathList, "makePathElements", (Class<?>[]) new Class[]{List.class}).invoke(dexPathList, new Object[]{newLibDirs}));
        }
    }

    private static final class V4 {
        private V4() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader classLoader, File folder) throws Throwable {
            String addPath = folder.getPath();
            Field pathField = ShareReflectUtil.findField((Object) classLoader, "libPath");
            String[] origLibPathSplit = ((String) pathField.get(classLoader)).split(":");
            StringBuilder newLibPaths = new StringBuilder(addPath);
            for (String origLibPath : origLibPathSplit) {
                if (origLibPath != null && !addPath.equals(origLibPath)) {
                    newLibPaths.append(':').append(origLibPath);
                }
            }
            pathField.set(classLoader, newLibPaths.toString());
            Field libraryPathElementsFiled = ShareReflectUtil.findField((Object) classLoader, "libraryPathElements");
            List<String> libraryPathElements = (List) libraryPathElementsFiled.get(classLoader);
            Iterator<String> libPathElementIt = libraryPathElements.iterator();
            while (true) {
                if (libPathElementIt.hasNext()) {
                    if (addPath.equals((String) libPathElementIt.next())) {
                        libPathElementIt.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
            libraryPathElements.add(0, addPath);
            libraryPathElementsFiled.set(classLoader, libraryPathElements);
        }
    }

    public static void loadArmLibrary(Context context, String libName) {
        if (libName == null || libName.isEmpty() || context == null) {
            throw new TinkerRuntimeException("libName or context is null!");
        } else if (!Tinker.with(context).isEnabledForNativeLib() || !loadLibraryFromTinker(context, "lib/armeabi", libName)) {
            System.loadLibrary(libName);
        }
    }

    public static void loadArmLibraryWithoutTinkerInstalled(ApplicationLike appLike, String libName) {
        if (libName == null || libName.isEmpty() || appLike == null) {
            throw new TinkerRuntimeException("libName or appLike is null!");
        } else if (!TinkerApplicationHelper.isTinkerEnableForNativeLib(appLike) || !TinkerApplicationHelper.loadLibraryFromTinker(appLike, "lib/armeabi", libName)) {
            System.loadLibrary(libName);
        }
    }

    public static void loadArmV7Library(Context context, String libName) {
        if (libName == null || libName.isEmpty() || context == null) {
            throw new TinkerRuntimeException("libName or context is null!");
        } else if (!Tinker.with(context).isEnabledForNativeLib() || !loadLibraryFromTinker(context, "lib/armeabi-v7a", libName)) {
            System.loadLibrary(libName);
        }
    }

    public static void loadArmV7LibraryWithoutTinkerInstalled(ApplicationLike appLike, String libName) {
        if (libName == null || libName.isEmpty() || appLike == null) {
            throw new TinkerRuntimeException("libName or appLike is null!");
        } else if (!TinkerApplicationHelper.isTinkerEnableForNativeLib(appLike) || !TinkerApplicationHelper.loadLibraryFromTinker(appLike, "lib/armeabi-v7a", libName)) {
            System.loadLibrary(libName);
        }
    }

    public static boolean loadLibraryFromTinker(Context context, String relativePath, String libName) throws UnsatisfiedLinkError {
        Tinker tinker = Tinker.with(context);
        if (!libName.startsWith(ShareConstants.SO_PATH)) {
            libName = ShareConstants.SO_PATH + libName;
        }
        if (!libName.endsWith(".so")) {
            libName = libName + ".so";
        }
        String relativeLibPath = relativePath + "/" + libName;
        if (tinker.isEnabledForNativeLib() && tinker.isTinkerLoaded()) {
            TinkerLoadResult loadResult = tinker.getTinkerLoadResultIfPresent();
            if (loadResult.libs != null) {
                for (String name : loadResult.libs.keySet()) {
                    if (name.equals(relativeLibPath)) {
                        String patchLibraryPath = loadResult.libraryDirectory + "/" + name;
                        File library = new File(patchLibraryPath);
                        if (!library.exists()) {
                            continue;
                        } else if (!tinker.isTinkerLoadVerify() || SharePatchFileUtil.verifyFileMd5(library, (String) loadResult.libs.get(name))) {
                            System.load(patchLibraryPath);
                            TinkerLog.i(TAG, "loadLibraryFromTinker success:" + patchLibraryPath, new Object[0]);
                            return true;
                        } else {
                            tinker.getLoadReporter().onLoadFileMd5Mismatch(library, 5);
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean installNavitveLibraryABI(Context context, String currentABI) {
        Tinker tinker = Tinker.with(context);
        if (!tinker.isTinkerLoaded()) {
            TinkerLog.i(TAG, "tinker is not loaded, just return", new Object[0]);
            return false;
        }
        TinkerLoadResult loadResult = tinker.getTinkerLoadResultIfPresent();
        if (loadResult.libs == null) {
            TinkerLog.i(TAG, "tinker libs is null, just return", new Object[0]);
            return false;
        }
        File soDir = new File(loadResult.libraryDirectory, "lib/" + currentABI);
        if (!soDir.exists()) {
            TinkerLog.e(TAG, "current libraryABI folder is not exist, path: %s", soDir.getPath());
            return false;
        }
        ClassLoader classLoader = context.getClassLoader();
        if (classLoader == null) {
            TinkerLog.e(TAG, "classloader is null", new Object[0]);
            return false;
        }
        TinkerLog.i(TAG, "before hack classloader:" + classLoader.toString(), new Object[0]);
        try {
            installNativeLibraryPath(classLoader, soDir);
            TinkerLog.i(TAG, "after hack classloader:" + classLoader.toString(), new Object[0]);
            return true;
        } catch (Throwable th) {
            TinkerLog.i(TAG, "after hack classloader:" + classLoader.toString(), new Object[0]);
            throw th;
        }
    }

    public static boolean installNativeLibraryABIWithoutTinkerInstalled(ApplicationLike appLike, String currentABI) {
        String currentVersion = TinkerApplicationHelper.getCurrentVersion(appLike);
        if (ShareTinkerInternals.isNullOrNil(currentVersion)) {
            TinkerLog.e(TAG, "failed to get current patch version.", new Object[0]);
            return false;
        }
        File patchDirectory = SharePatchFileUtil.getPatchDirectory(appLike.getApplication());
        if (patchDirectory == null) {
            TinkerLog.e(TAG, "failed to get current patch directory.", new Object[0]);
            return false;
        }
        File libPath = new File(new File(patchDirectory.getAbsolutePath() + "/" + SharePatchFileUtil.getPatchVersionDirectory(currentVersion)).getAbsolutePath() + "/lib/lib/" + currentABI);
        if (!libPath.exists()) {
            TinkerLog.e(TAG, "tinker lib path [%s] is not exists.", libPath);
            return false;
        }
        ClassLoader classLoader = appLike.getApplication().getClassLoader();
        if (classLoader == null) {
            TinkerLog.e(TAG, "classloader is null", new Object[0]);
            return false;
        }
        TinkerLog.i(TAG, "before hack classloader:" + classLoader.toString(), new Object[0]);
        try {
            Method installNativeLibraryPathMethod = TinkerLoadLibrary.class.getDeclaredMethod("installNativeLibraryPath", new Class[]{ClassLoader.class, File.class});
            installNativeLibraryPathMethod.setAccessible(true);
            installNativeLibraryPathMethod.invoke(null, new Object[]{classLoader, libPath});
            TinkerLog.i(TAG, "after hack classloader:" + classLoader.toString(), new Object[0]);
            return true;
        } catch (Throwable th) {
            TinkerLog.i(TAG, "after hack classloader:" + classLoader.toString(), new Object[0]);
            throw th;
        }
    }

    private static void installNativeLibraryPath(ClassLoader classLoader, File folder) throws Throwable {
        if (folder == null || !folder.exists()) {
            TinkerLog.e(TAG, "installNativeLibraryPath, folder %s is illegal", folder);
        } else if ((VERSION.SDK_INT == 25 && VERSION.PREVIEW_SDK_INT != 0) || VERSION.SDK_INT > 25) {
            try {
                V25.install(classLoader, folder);
            } catch (Throwable throwable) {
                TinkerLog.e(TAG, "installNativeLibraryPath, v25 fail, sdk: %d, error: %s, try to fallback to V23", Integer.valueOf(VERSION.SDK_INT), throwable.getMessage());
                V23.install(classLoader, folder);
            }
        } else if (VERSION.SDK_INT >= 23) {
            try {
                V23.install(classLoader, folder);
            } catch (Throwable throwable2) {
                TinkerLog.e(TAG, "installNativeLibraryPath, v23 fail, sdk: %d, error: %s, try to fallback to V14", Integer.valueOf(VERSION.SDK_INT), throwable2.getMessage());
                V14.install(classLoader, folder);
            }
        } else if (VERSION.SDK_INT >= 14) {
            V14.install(classLoader, folder);
        } else {
            V4.install(classLoader, folder);
        }
    }
}
