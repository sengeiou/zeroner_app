package com.tencent.tinker.loader;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build.VERSION;
import android.util.Log;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.zip.ZipFile;

public class SystemClassLoaderAdder {
    public static final String CHECK_DEX_CLASS = "com.tencent.tinker.loader.TinkerTestDexLoad";
    public static final String CHECK_DEX_FIELD = "isPatch";
    private static final String TAG = "Tinker.ClassLoaderAdder";
    private static int sPatchDexCount = 0;

    private static final class V14 {
        private V14() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object dexPathList = ShareReflectUtil.findField((Object) loader, "pathList").get(loader);
            ShareReflectUtil.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) ShareReflectUtil.findMethod(dexPathList, "makeDexElements", (Class<?>[]) new Class[]{ArrayList.class, File.class}).invoke(dexPathList, new Object[]{files, optimizedDirectory});
        }
    }

    private static final class V19 {
        private V19() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            Object dexPathList = ShareReflectUtil.findField((Object) loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            ShareReflectUtil.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator it = suppressedExceptions.iterator();
                if (it.hasNext()) {
                    IOException e = (IOException) it.next();
                    Log.w(SystemClassLoaderAdder.TAG, "Exception in makeDexElement", e);
                    throw e;
                }
            }
        }

        /* access modifiers changed from: private */
        public static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            Method makeDexElements;
            try {
                makeDexElements = ShareReflectUtil.findMethod(dexPathList, "makeDexElements", (Class<?>[]) new Class[]{ArrayList.class, File.class, ArrayList.class});
            } catch (NoSuchMethodException e) {
                Log.e(SystemClassLoaderAdder.TAG, "NoSuchMethodException: makeDexElements(ArrayList,File,ArrayList) failure");
                try {
                    makeDexElements = ShareReflectUtil.findMethod(dexPathList, "makeDexElements", (Class<?>[]) new Class[]{List.class, File.class, List.class});
                } catch (NoSuchMethodException e1) {
                    Log.e(SystemClassLoaderAdder.TAG, "NoSuchMethodException: makeDexElements(List,File,List) failure");
                    throw e1;
                }
            }
            return (Object[]) makeDexElements.invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V23 {
        private V23() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            Object dexPathList = ShareReflectUtil.findField((Object) loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            ShareReflectUtil.expandFieldArray(dexPathList, "dexElements", makePathElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator it = suppressedExceptions.iterator();
                if (it.hasNext()) {
                    IOException e = (IOException) it.next();
                    Log.w(SystemClassLoaderAdder.TAG, "Exception in makePathElement", e);
                    throw e;
                }
            }
        }

        private static Object[] makePathElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            Method makePathElements;
            try {
                makePathElements = ShareReflectUtil.findMethod(dexPathList, "makePathElements", (Class<?>[]) new Class[]{List.class, File.class, List.class});
            } catch (NoSuchMethodException e) {
                Log.e(SystemClassLoaderAdder.TAG, "NoSuchMethodException: makePathElements(List,File,List) failure");
                try {
                    makePathElements = ShareReflectUtil.findMethod(dexPathList, "makePathElements", (Class<?>[]) new Class[]{ArrayList.class, File.class, ArrayList.class});
                } catch (NoSuchMethodException e2) {
                    Log.e(SystemClassLoaderAdder.TAG, "NoSuchMethodException: makeDexElements(ArrayList,File,ArrayList) failure");
                    try {
                        Log.e(SystemClassLoaderAdder.TAG, "NoSuchMethodException: try use v19 instead");
                        return V19.makeDexElements(dexPathList, files, optimizedDirectory, suppressedExceptions);
                    } catch (NoSuchMethodException e22) {
                        Log.e(SystemClassLoaderAdder.TAG, "NoSuchMethodException: makeDexElements(List,File,List) failure");
                        throw e22;
                    }
                }
            }
            return (Object[]) makePathElements.invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V4 {
        private V4() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = ShareReflectUtil.findField((Object) loader, "path");
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = (File) iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, SharePatchFileUtil.optimizedPathFor(additionalEntry, optimizedDirectory), 0);
            }
            pathField.set(loader, path.toString());
            ShareReflectUtil.expandFieldArray(loader, "mPaths", extraPaths);
            ShareReflectUtil.expandFieldArray(loader, "mFiles", extraFiles);
            ShareReflectUtil.expandFieldArray(loader, "mZips", extraZips);
            try {
                ShareReflectUtil.expandFieldArray(loader, "mDexs", extraDexs);
            } catch (Exception e) {
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void installDexes(Application application, PathClassLoader loader, File dexOptDir, List<File> files) throws Throwable {
        Log.i(TAG, "installDexes dexOptDir: " + dexOptDir.getAbsolutePath() + ", dex size:" + files.size());
        if (!files.isEmpty()) {
            List<File> files2 = createSortedAdditionalPathEntries(files);
            PathClassLoader pathClassLoader = loader;
            if (VERSION.SDK_INT >= 24 && !checkIsProtectedApp(files2)) {
                pathClassLoader = AndroidNClassLoader.inject(loader, application);
            }
            if (VERSION.SDK_INT >= 23) {
                V23.install(pathClassLoader, files2, dexOptDir);
            } else if (VERSION.SDK_INT >= 19) {
                V19.install(pathClassLoader, files2, dexOptDir);
            } else if (VERSION.SDK_INT >= 14) {
                V14.install(pathClassLoader, files2, dexOptDir);
            } else {
                V4.install(pathClassLoader, files2, dexOptDir);
            }
            sPatchDexCount = files2.size();
            Log.i(TAG, "after loaded classloader: " + pathClassLoader + ", dex size:" + sPatchDexCount);
            if (!checkDexInstall(pathClassLoader)) {
                uninstallPatchDex(pathClassLoader);
                throw new TinkerRuntimeException(ShareConstants.CHECK_DEX_INSTALL_FAIL);
            }
        }
    }

    public static void uninstallPatchDex(ClassLoader classLoader) throws Throwable {
        if (sPatchDexCount > 0) {
            if (VERSION.SDK_INT >= 14) {
                ShareReflectUtil.reduceFieldArray(ShareReflectUtil.findField((Object) classLoader, "pathList").get(classLoader), "dexElements", sPatchDexCount);
                return;
            }
            ShareReflectUtil.reduceFieldArray(classLoader, "mPaths", sPatchDexCount);
            ShareReflectUtil.reduceFieldArray(classLoader, "mFiles", sPatchDexCount);
            ShareReflectUtil.reduceFieldArray(classLoader, "mZips", sPatchDexCount);
            try {
                ShareReflectUtil.reduceFieldArray(classLoader, "mDexs", sPatchDexCount);
            } catch (Exception e) {
            }
        }
    }

    private static boolean checkDexInstall(ClassLoader classLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        boolean isPatch = ((Boolean) ShareReflectUtil.findField(Class.forName(CHECK_DEX_CLASS, true, classLoader), CHECK_DEX_FIELD).get(null)).booleanValue();
        Log.w(TAG, "checkDexInstall result:" + isPatch);
        return isPatch;
    }

    private static boolean checkIsProtectedApp(List<File> files) {
        if (!files.isEmpty()) {
            for (File file : files) {
                if (file != null && file.getName().startsWith(ShareConstants.CHANGED_CLASSES_DEX_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static List<File> createSortedAdditionalPathEntries(List<File> additionalPathEntries) {
        List<File> result = new ArrayList<>(additionalPathEntries);
        final Map<String, Boolean> matchesClassNPatternMemo = new HashMap<>();
        for (File file : result) {
            String name = file.getName();
            matchesClassNPatternMemo.put(name, Boolean.valueOf(ShareConstants.CLASS_N_PATTERN.matcher(name).matches()));
        }
        Collections.sort(result, new Comparator<File>() {
            public int compare(File lhs, File rhs) {
                if (lhs == null && rhs == null) {
                    return 0;
                }
                if (lhs == null) {
                    return -1;
                }
                if (rhs == null) {
                    return 1;
                }
                String lhsName = lhs.getName();
                String rhsName = rhs.getName();
                if (lhsName.equals(rhsName)) {
                    return 0;
                }
                String str = ShareConstants.TEST_DEX_NAME;
                if (lhsName.startsWith(ShareConstants.TEST_DEX_NAME)) {
                    return 1;
                }
                if (rhsName.startsWith(ShareConstants.TEST_DEX_NAME)) {
                    return -1;
                }
                boolean isLhsNameMatchClassN = ((Boolean) matchesClassNPatternMemo.get(lhsName)).booleanValue();
                boolean isRhsNameMatchClassN = ((Boolean) matchesClassNPatternMemo.get(rhsName)).booleanValue();
                if (isLhsNameMatchClassN && isRhsNameMatchClassN) {
                    int lhsDotPos = lhsName.indexOf(46);
                    int rhsDotPos = rhsName.indexOf(46);
                    int lhsId = lhsDotPos > 7 ? Integer.parseInt(lhsName.substring(7, lhsDotPos)) : 1;
                    int rhsId = rhsDotPos > 7 ? Integer.parseInt(rhsName.substring(7, rhsDotPos)) : 1;
                    if (lhsId == rhsId) {
                        return 0;
                    }
                    return lhsId < rhsId ? -1 : 1;
                } else if (isLhsNameMatchClassN) {
                    return -1;
                } else {
                    if (isRhsNameMatchClassN) {
                        return 1;
                    }
                    return lhsName.compareTo(rhsName);
                }
            }
        });
        return result;
    }
}
