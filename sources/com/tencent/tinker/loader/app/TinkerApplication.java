package com.tencent.tinker.loader.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemClock;
import com.tencent.tinker.loader.TinkerLoader;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.TinkerUncaughtHandler;
import com.tencent.tinker.loader.hotplug.ComponentHotplug;
import com.tencent.tinker.loader.hotplug.UnsupportedEnvironmentException;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

public abstract class TinkerApplication extends Application {
    private static final String INTENT_PATCH_EXCEPTION = "intent_patch_exception";
    private static final int TINKER_DISABLE = 0;
    private static final String TINKER_LOADER_METHOD = "tryLoad";
    private ApplicationLike applicationLike;
    private long applicationStartElapsedTime;
    private long applicationStartMillisTime;
    private final String delegateClassName;
    private final String loaderClassName;
    private final int tinkerFlags;
    private final boolean tinkerLoadVerifyFlag;
    private Intent tinkerResultIntent;
    private boolean useSafeMode;

    protected TinkerApplication(int tinkerFlags2) {
        this(tinkerFlags2, "com.tencent.tinker.loader.app.DefaultApplicationLike", TinkerLoader.class.getName(), false);
    }

    protected TinkerApplication(int tinkerFlags2, String delegateClassName2, String loaderClassName2, boolean tinkerLoadVerifyFlag2) {
        this.applicationLike = null;
        this.tinkerFlags = tinkerFlags2;
        this.delegateClassName = delegateClassName2;
        this.loaderClassName = loaderClassName2;
        this.tinkerLoadVerifyFlag = tinkerLoadVerifyFlag2;
    }

    protected TinkerApplication(int tinkerFlags2, String delegateClassName2) {
        this(tinkerFlags2, delegateClassName2, TinkerLoader.class.getName(), false);
    }

    private ApplicationLike createDelegate() {
        try {
            return (ApplicationLike) Class.forName(this.delegateClassName, false, getClassLoader()).getConstructor(new Class[]{Application.class, Integer.TYPE, Boolean.TYPE, Long.TYPE, Long.TYPE, Intent.class}).newInstance(new Object[]{this, Integer.valueOf(this.tinkerFlags), Boolean.valueOf(this.tinkerLoadVerifyFlag), Long.valueOf(this.applicationStartElapsedTime), Long.valueOf(this.applicationStartMillisTime), this.tinkerResultIntent});
        } catch (Throwable e) {
            throw new TinkerRuntimeException("createDelegate failed", e);
        }
    }

    private synchronized void ensureDelegate() {
        if (this.applicationLike == null) {
            this.applicationLike = createDelegate();
        }
    }

    private void onBaseContextAttached(Context base) {
        this.applicationStartElapsedTime = SystemClock.elapsedRealtime();
        this.applicationStartMillisTime = System.currentTimeMillis();
        loadTinker();
        ensureDelegate();
        this.applicationLike.onBaseContextAttached(base);
        if (this.useSafeMode) {
            getSharedPreferences(ShareConstants.TINKER_OWN_PREFERENCE_CONFIG + ShareTinkerInternals.getProcessName(this), 0).edit().putInt(ShareConstants.TINKER_SAFE_MODE_COUNT, 0).commit();
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Thread.setDefaultUncaughtExceptionHandler(new TinkerUncaughtHandler(this));
        onBaseContextAttached(base);
    }

    private void loadTinker() {
        if (this.tinkerFlags != 0) {
            this.tinkerResultIntent = new Intent();
            try {
                Class<?> tinkerLoadClass = Class.forName(this.loaderClassName, false, getClassLoader());
                this.tinkerResultIntent = (Intent) tinkerLoadClass.getMethod(TINKER_LOADER_METHOD, new Class[]{TinkerApplication.class}).invoke(tinkerLoadClass.getConstructor(new Class[0]).newInstance(new Object[0]), new Object[]{this});
            } catch (Throwable e) {
                ShareIntentUtil.setIntentReturnCode(this.tinkerResultIntent, -20);
                this.tinkerResultIntent.putExtra("intent_patch_exception", e);
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        ensureDelegate();
        try {
            ComponentHotplug.ensureComponentHotplugInstalled(this);
            this.applicationLike.onCreate();
        } catch (UnsupportedEnvironmentException e) {
            throw new TinkerRuntimeException("failed to make sure that ComponentHotplug logic is fine.", e);
        }
    }

    public void onTerminate() {
        super.onTerminate();
        if (this.applicationLike != null) {
            this.applicationLike.onTerminate();
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.applicationLike != null) {
            this.applicationLike.onLowMemory();
        }
    }

    @TargetApi(14)
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (this.applicationLike != null) {
            this.applicationLike.onTrimMemory(level);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.applicationLike != null) {
            this.applicationLike.onConfigurationChanged(newConfig);
        }
    }

    public Resources getResources() {
        Resources resources = super.getResources();
        if (this.applicationLike != null) {
            return this.applicationLike.getResources(resources);
        }
        return resources;
    }

    public ClassLoader getClassLoader() {
        ClassLoader classLoader = super.getClassLoader();
        if (this.applicationLike != null) {
            return this.applicationLike.getClassLoader(classLoader);
        }
        return classLoader;
    }

    public AssetManager getAssets() {
        AssetManager assetManager = super.getAssets();
        if (this.applicationLike != null) {
            return this.applicationLike.getAssets(assetManager);
        }
        return assetManager;
    }

    public Object getSystemService(String name) {
        Object service = super.getSystemService(name);
        if (this.applicationLike != null) {
            return this.applicationLike.getSystemService(name, service);
        }
        return service;
    }

    public Context getBaseContext() {
        Context base = super.getBaseContext();
        if (this.applicationLike != null) {
            return this.applicationLike.getBaseContext(base);
        }
        return base;
    }

    public void setUseSafeMode(boolean useSafeMode2) {
        this.useSafeMode = useSafeMode2;
    }

    public boolean isTinkerLoadVerifyFlag() {
        return this.tinkerLoadVerifyFlag;
    }

    public int getTinkerFlags() {
        return this.tinkerFlags;
    }
}
