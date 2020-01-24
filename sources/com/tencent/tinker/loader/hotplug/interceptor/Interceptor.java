package com.tencent.tinker.loader.hotplug.interceptor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public abstract class Interceptor<T_TARGET> {
    private static final String TAG = "Tinker.Interceptor";
    private volatile boolean mInstalled = false;
    private T_TARGET mTarget = null;

    protected interface ITinkerHotplugProxy {
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract T_TARGET fetchTarget() throws Throwable;

    /* access modifiers changed from: protected */
    public abstract void inject(@Nullable T_TARGET t_target) throws Throwable;

    /* access modifiers changed from: protected */
    @NonNull
    public T_TARGET decorate(@Nullable T_TARGET target) throws Throwable {
        return target;
    }

    public synchronized void install() throws InterceptFailedException {
        try {
            T_TARGET target = fetchTarget();
            this.mTarget = target;
            T_TARGET decorated = decorate(target);
            if (decorated != target) {
                inject(decorated);
            } else {
                Log.w(TAG, "target: " + target + " was already hooked.");
            }
            this.mInstalled = true;
        } catch (Throwable thr) {
            this.mTarget = null;
            throw new InterceptFailedException(thr);
        }
    }

    public synchronized void uninstall() throws InterceptFailedException {
        if (this.mInstalled) {
            try {
                inject(this.mTarget);
                this.mTarget = null;
                this.mInstalled = false;
            } catch (Throwable thr) {
                throw new InterceptFailedException(thr);
            }
        }
    }
}
