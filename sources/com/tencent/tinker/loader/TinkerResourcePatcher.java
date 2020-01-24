package com.tencent.tinker.loader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.ArrayMap;
import android.util.Log;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class TinkerResourcePatcher {
    private static final String TAG = "Tinker.ResourcePatcher";
    private static final String TEST_ASSETS_VALUE = "only_use_to_test_tinker_resource.txt";
    private static Method addAssetPathMethod = null;
    private static Field assetsFiled = null;
    private static Object currentActivityThread = null;
    private static Method ensureStringBlocksMethod = null;
    private static AssetManager newAssetManager = null;
    private static Field packagesFiled = null;
    private static Field publicSourceDirField = null;
    private static Collection<WeakReference<Resources>> references = null;
    private static Field resDir = null;
    private static Field resourcePackagesFiled = null;
    private static Field resourcesImplFiled = null;
    private static Field stringBlocksField = null;

    TinkerResourcePatcher() {
    }

    public static void isResourceCanPatch(Context context) throws Throwable {
        Class<?> loadedApkClass;
        Class<?> activityThread = Class.forName("android.app.ActivityThread");
        currentActivityThread = ShareReflectUtil.getActivityThread(context, activityThread);
        try {
            loadedApkClass = Class.forName("android.app.LoadedApk");
        } catch (ClassNotFoundException e) {
            loadedApkClass = Class.forName("android.app.ActivityThread$PackageInfo");
        }
        resDir = ShareReflectUtil.findField(loadedApkClass, "mResDir");
        packagesFiled = ShareReflectUtil.findField(activityThread, "mPackages");
        if (VERSION.SDK_INT < 27) {
            resourcePackagesFiled = ShareReflectUtil.findField(activityThread, "mResourcePackages");
        }
        AssetManager assets = context.getAssets();
        addAssetPathMethod = ShareReflectUtil.findMethod((Object) assets, "addAssetPath", (Class<?>[]) new Class[]{String.class});
        stringBlocksField = ShareReflectUtil.findField((Object) assets, "mStringBlocks");
        ensureStringBlocksMethod = ShareReflectUtil.findMethod((Object) assets, "ensureStringBlocks", (Class<?>[]) new Class[0]);
        newAssetManager = (AssetManager) ShareReflectUtil.findConstructor(assets, new Class[0]).newInstance(new Object[0]);
        if (VERSION.SDK_INT >= 19) {
            Class<?> resourcesManagerClass = Class.forName("android.app.ResourcesManager");
            Object resourcesManager = ShareReflectUtil.findMethod(resourcesManagerClass, "getInstance", (Class<?>[]) new Class[0]).invoke(null, new Object[0]);
            try {
                references = ((ArrayMap) ShareReflectUtil.findField(resourcesManagerClass, "mActiveResources").get(resourcesManager)).values();
            } catch (NoSuchFieldException e2) {
                references = (Collection) ShareReflectUtil.findField(resourcesManagerClass, "mResourceReferences").get(resourcesManager);
            }
        } else {
            references = ((HashMap) ShareReflectUtil.findField(activityThread, "mActiveResources").get(currentActivityThread)).values();
        }
        if (references == null) {
            throw new IllegalStateException("resource references is null");
        }
        Resources resources = context.getResources();
        if (VERSION.SDK_INT >= 24) {
            try {
                resourcesImplFiled = ShareReflectUtil.findField((Object) resources, "mResourcesImpl");
            } catch (Throwable th) {
                assetsFiled = ShareReflectUtil.findField((Object) resources, "mAssets");
            }
        } else {
            assetsFiled = ShareReflectUtil.findField((Object) resources, "mAssets");
        }
        try {
            publicSourceDirField = ShareReflectUtil.findField(ApplicationInfo.class, "publicSourceDir");
        } catch (NoSuchFieldException e3) {
        }
    }

    public static void monkeyPatchExistingResources(Context context, String externalResourceFile) throws Throwable {
        if (externalResourceFile != null) {
            ApplicationInfo appInfo = context.getApplicationInfo();
            for (Field field : VERSION.SDK_INT < 27 ? new Field[]{packagesFiled, resourcePackagesFiled} : new Field[]{packagesFiled}) {
                for (Entry<String, WeakReference<?>> entry : ((Map) field.get(currentActivityThread)).entrySet()) {
                    Object loadedApk = ((WeakReference) entry.getValue()).get();
                    if (loadedApk != null) {
                        if (appInfo.sourceDir.equals((String) resDir.get(loadedApk))) {
                            resDir.set(loadedApk, externalResourceFile);
                        }
                    }
                }
            }
            if (((Integer) addAssetPathMethod.invoke(newAssetManager, new Object[]{externalResourceFile})).intValue() == 0) {
                throw new IllegalStateException("Could not create new AssetManager");
            }
            stringBlocksField.set(newAssetManager, null);
            ensureStringBlocksMethod.invoke(newAssetManager, new Object[0]);
            for (WeakReference<Resources> wr : references) {
                Resources resources = (Resources) wr.get();
                if (resources != null) {
                    try {
                        assetsFiled.set(resources, newAssetManager);
                    } catch (Throwable th) {
                        Object resourceImpl = resourcesImplFiled.get(resources);
                        ShareReflectUtil.findField(resourceImpl, "mAssets").set(resourceImpl, newAssetManager);
                    }
                    clearPreloadTypedArrayIssue(resources);
                    resources.updateConfiguration(resources.getConfiguration(), resources.getDisplayMetrics());
                }
            }
            if (VERSION.SDK_INT >= 24) {
                try {
                    if (publicSourceDirField != null) {
                        publicSourceDirField.set(context.getApplicationInfo(), externalResourceFile);
                    }
                } catch (Throwable th2) {
                }
            }
            if (!checkResUpdate(context)) {
                throw new TinkerRuntimeException(ShareConstants.CHECK_RES_INSTALL_FAIL);
            }
        }
    }

    private static void clearPreloadTypedArrayIssue(Resources resources) {
        Log.w(TAG, "try to clear typedArray cache!");
        try {
            Object origTypedArrayPool = ShareReflectUtil.findField(Resources.class, "mTypedArrayPool").get(resources);
            do {
            } while (ShareReflectUtil.findMethod(origTypedArrayPool, "acquire", (Class<?>[]) new Class[0]).invoke(origTypedArrayPool, new Object[0]) != null);
        } catch (Throwable ignored) {
            Log.e(TAG, "clearPreloadTypedArrayIssue failed, ignore error: " + ignored);
        }
    }

    private static boolean checkResUpdate(Context context) {
        InputStream is = null;
        try {
            SharePatchFileUtil.closeQuietly(context.getAssets().open(TEST_ASSETS_VALUE));
            Log.i(TAG, "checkResUpdate success, found test resource assets file only_use_to_test_tinker_resource.txt");
            return true;
        } catch (Throwable th) {
            SharePatchFileUtil.closeQuietly(is);
            throw th;
        }
    }
}
