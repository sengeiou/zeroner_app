package com.alibaba.android.arouter.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.alibaba.android.arouter.launcher.ARouter;

@TargetApi(14)
@Deprecated
public class AutowiredLifecycleCallback implements ActivityLifecycleCallbacks {
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ARouter.getInstance().inject(activity);
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
