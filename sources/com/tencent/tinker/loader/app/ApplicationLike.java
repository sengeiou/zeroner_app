package com.tencent.tinker.loader.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;

public abstract class ApplicationLike implements ApplicationLifeCycle {
    private final Application application;
    private final long applicationStartElapsedTime;
    private final long applicationStartMillisTime;
    private final int tinkerFlags;
    private final boolean tinkerLoadVerifyFlag;
    private final Intent tinkerResultIntent;

    public ApplicationLike(Application application2, int tinkerFlags2, boolean tinkerLoadVerifyFlag2, long applicationStartElapsedTime2, long applicationStartMillisTime2, Intent tinkerResultIntent2) {
        this.application = application2;
        this.tinkerFlags = tinkerFlags2;
        this.tinkerLoadVerifyFlag = tinkerLoadVerifyFlag2;
        this.applicationStartElapsedTime = applicationStartElapsedTime2;
        this.applicationStartMillisTime = applicationStartMillisTime2;
        this.tinkerResultIntent = tinkerResultIntent2;
    }

    public Application getApplication() {
        return this.application;
    }

    public final Intent getTinkerResultIntent() {
        return this.tinkerResultIntent;
    }

    public final int getTinkerFlags() {
        return this.tinkerFlags;
    }

    public final boolean getTinkerLoadVerifyFlag() {
        return this.tinkerLoadVerifyFlag;
    }

    public long getApplicationStartElapsedTime() {
        return this.applicationStartElapsedTime;
    }

    public long getApplicationStartMillisTime() {
        return this.applicationStartMillisTime;
    }

    public void onCreate() {
    }

    public void onLowMemory() {
    }

    public void onTrimMemory(int level) {
    }

    public void onTerminate() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onBaseContextAttached(Context base) {
    }

    public Resources getResources(Resources resources) {
        return resources;
    }

    public ClassLoader getClassLoader(ClassLoader classLoader) {
        return classLoader;
    }

    public AssetManager getAssets(AssetManager assetManager) {
        return assetManager;
    }

    public Object getSystemService(String name, Object service) {
        return service;
    }

    public Context getBaseContext(Context base) {
        return base;
    }
}
