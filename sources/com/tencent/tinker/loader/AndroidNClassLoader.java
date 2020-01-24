package com.tencent.tinker.loader;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

@TargetApi(14)
class AndroidNClassLoader extends PathClassLoader {
    private static final String TAG = "Tinker.NClassLoader";
    private static Object oldDexPathListHolder = null;
    private String applicationClassName;
    private final PathClassLoader originClassLoader;

    private AndroidNClassLoader(String dexPath, PathClassLoader parent, Application application) {
        super(dexPath, parent.getParent());
        this.originClassLoader = parent;
        String name = application.getClass().getName();
        if (name != null && !name.equals("android.app.Application")) {
            this.applicationClassName = name;
        }
    }

    private static Object recreateDexPathList(Object originalDexPathList, ClassLoader newDefiningContext, boolean createEmptyOne) throws Exception {
        Constructor<?> dexPathListConstructor = ShareReflectUtil.findConstructor(originalDexPathList, ClassLoader.class, String.class, String.class, File.class);
        if (createEmptyOne) {
            return dexPathListConstructor.newInstance(new Object[]{newDefiningContext, "", null, null});
        }
        Object[] dexElements = (Object[]) ShareReflectUtil.findField(originalDexPathList, "dexElements").get(originalDexPathList);
        List<File> nativeLibraryDirectories = (List) ShareReflectUtil.findField(originalDexPathList, "nativeLibraryDirectories").get(originalDexPathList);
        StringBuilder dexPathBuilder = new StringBuilder();
        Field dexFileField = ShareReflectUtil.findField(dexElements.getClass().getComponentType(), "dexFile");
        boolean isFirstItem = true;
        for (Object dexElement : dexElements) {
            DexFile dexFile = (DexFile) dexFileField.get(dexElement);
            if (dexFile != null) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    dexPathBuilder.append(File.pathSeparator);
                }
                dexPathBuilder.append(dexFile.getName());
            }
        }
        String dexPath = dexPathBuilder.toString();
        StringBuilder libraryPathBuilder = new StringBuilder();
        boolean isFirstItem2 = true;
        for (File libDir : nativeLibraryDirectories) {
            if (libDir != null) {
                if (isFirstItem2) {
                    isFirstItem2 = false;
                } else {
                    libraryPathBuilder.append(File.pathSeparator);
                }
                libraryPathBuilder.append(libDir.getAbsolutePath());
            }
        }
        return dexPathListConstructor.newInstance(new Object[]{newDefiningContext, dexPath, libraryPathBuilder.toString(), null});
    }

    private static AndroidNClassLoader createAndroidNClassLoader(PathClassLoader originalClassLoader, Application application) throws Exception {
        AndroidNClassLoader androidNClassLoader = new AndroidNClassLoader("", originalClassLoader, application);
        Field pathListField = ShareReflectUtil.findField((Object) originalClassLoader, "pathList");
        Object originPathList = pathListField.get(originalClassLoader);
        pathListField.set(androidNClassLoader, recreateDexPathList(originPathList, androidNClassLoader, false));
        oldDexPathListHolder = originPathList;
        pathListField.set(originalClassLoader, recreateDexPathList(originPathList, originalClassLoader, true));
        pathListField.set(originalClassLoader, recreateDexPathList(originPathList, originalClassLoader, false));
        return androidNClassLoader;
    }

    private static void reflectPackageInfoClassloader(Application application, ClassLoader reflectClassLoader) throws Exception {
        Context baseContext = (Context) ShareReflectUtil.findField((Object) application, "mBase").get(application);
        Object basePackageInfo = ShareReflectUtil.findField((Object) baseContext, "mPackageInfo").get(baseContext);
        ShareReflectUtil.findField(basePackageInfo, "mClassLoader").set(basePackageInfo, reflectClassLoader);
        if (VERSION.SDK_INT < 27) {
            Resources res = application.getResources();
            ShareReflectUtil.findField((Object) res, "mClassLoader").set(res, reflectClassLoader);
            Object drawableInflater = ShareReflectUtil.findField((Object) res, "mDrawableInflater").get(res);
            if (drawableInflater != null) {
                ShareReflectUtil.findField(drawableInflater, "mClassLoader").set(drawableInflater, reflectClassLoader);
            }
        }
        Thread.currentThread().setContextClassLoader(reflectClassLoader);
    }

    public static AndroidNClassLoader inject(PathClassLoader originClassLoader2, Application application) throws Exception {
        AndroidNClassLoader classLoader = createAndroidNClassLoader(originClassLoader2, application);
        reflectPackageInfoClassloader(application, classLoader);
        return classLoader;
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        if ((name == null || !name.startsWith("com.tencent.tinker.loader.") || name.equals(SystemClassLoaderAdder.CHECK_DEX_CLASS)) && (this.applicationClassName == null || !this.applicationClassName.equals(name))) {
            return super.findClass(name);
        }
        return this.originClassLoader.loadClass(name);
    }

    public String findLibrary(String name) {
        return super.findLibrary(name);
    }
}
