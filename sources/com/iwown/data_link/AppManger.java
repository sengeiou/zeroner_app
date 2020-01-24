package com.iwown.data_link;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.Iterator;
import java.util.Stack;

public class AppManger {
    private static Stack<Activity> activityStack;
    private static AppManger instance;

    private AppManger() {
    }

    public static AppManger getAppManager() {
        if (instance == null) {
            instance = new AppManger();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        if (activityStack == null || activityStack.size() == 0) {
            return null;
        }
        return (Activity) activityStack.lastElement();
    }

    public void getActivitys() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            KLog.e("  " + activityStack.get(i));
        }
        KLog.e(" getActivitys " + activityStack.size());
    }

    public void finishActivity() {
        ((Activity) activityStack.lastElement()).finish();
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        Iterator it = activityStack.iterator();
        while (it.hasNext()) {
            if (((Activity) it.next()).getClass().equals(cls)) {
                finishActivity();
            }
        }
    }

    public void finishAllActivity(Activity activity) {
        try {
            if (activityStack != null) {
                int size = activityStack.size();
                for (int i = 0; i < size; i++) {
                    if (!(activityStack.get(i) == null || activity == activityStack.get(i))) {
                        ((Activity) activityStack.get(i)).finish();
                    }
                }
                activityStack.clear();
                if (activity != null) {
                    activityStack.add(activity);
                }
                KLog.e("activityStack " + activityStack);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void finishAllActivity() {
        finishAllActivity(null);
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
