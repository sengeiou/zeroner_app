package com.tencent.tinker.loader.hotplug.interceptor;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.tinker.loader.hotplug.IncrementComponentManager;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.lang.reflect.Field;

public class TinkerHackInstrumentation extends Instrumentation {
    private static final String TAG = "Tinker.Instrumentation";
    private final Object mActivityThread;
    private final Field mInstrumentationField;
    private final Instrumentation mOriginal;

    public static TinkerHackInstrumentation create(Context context) {
        try {
            Object activityThread = ShareReflectUtil.getActivityThread(context, null);
            Field mInstrumentationField2 = ShareReflectUtil.findField(activityThread, "mInstrumentation");
            Instrumentation original = (Instrumentation) mInstrumentationField2.get(activityThread);
            if (original instanceof TinkerHackInstrumentation) {
                return (TinkerHackInstrumentation) original;
            }
            return new TinkerHackInstrumentation(original, activityThread, mInstrumentationField2);
        } catch (Throwable thr) {
            throw new TinkerRuntimeException("see next stacktrace", thr);
        }
    }

    public void install() throws IllegalAccessException {
        if (this.mInstrumentationField.get(this.mActivityThread) instanceof TinkerHackInstrumentation) {
            Log.w(TAG, "already installed, skip rest logic.");
        } else {
            this.mInstrumentationField.set(this.mActivityThread, this);
        }
    }

    public void uninstall() throws IllegalAccessException {
        this.mInstrumentationField.set(this.mActivityThread, this.mOriginal);
    }

    private TinkerHackInstrumentation(Instrumentation original, Object activityThread, Field instrumentationField) throws TinkerRuntimeException {
        this.mOriginal = original;
        this.mActivityThread = activityThread;
        this.mInstrumentationField = instrumentationField;
        try {
            copyAllFields(original);
        } catch (Throwable thr) {
            throw new TinkerRuntimeException(thr.getMessage(), thr);
        }
    }

    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws InstantiationException, IllegalAccessException {
        processIntent(context.getClassLoader(), intent);
        return super.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
    }

    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (processIntent(cl, intent)) {
            return super.newActivity(cl, intent.getComponent().getClassName(), intent);
        }
        return super.newActivity(cl, className, intent);
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        if (activity != null) {
            ActivityInfo targetAInfo = IncrementComponentManager.queryActivityInfo(activity.getClass().getName());
            if (targetAInfo != null) {
                fixActivityParams(activity, targetAInfo);
            }
        }
        super.callActivityOnCreate(activity, icicle);
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
        if (activity != null) {
            ActivityInfo targetAInfo = IncrementComponentManager.queryActivityInfo(activity.getClass().getName());
            if (targetAInfo != null) {
                fixActivityParams(activity, targetAInfo);
            }
        }
        super.callActivityOnCreate(activity, icicle, persistentState);
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        if (activity != null) {
            processIntent(activity.getClass().getClassLoader(), intent);
        }
        super.callActivityOnNewIntent(activity, intent);
    }

    private boolean processIntent(ClassLoader cl, Intent intent) {
        if (intent == null) {
            return false;
        }
        ShareIntentUtil.fixIntentClassLoader(intent, cl);
        ComponentName oldComponent = (ComponentName) intent.getParcelableExtra(EnvConsts.INTENT_EXTRA_OLD_COMPONENT);
        if (oldComponent == null) {
            Log.w(TAG, "oldComponent was null, start " + intent.getComponent() + " next.");
            return false;
        }
        String oldComponentName = oldComponent.getClassName();
        if (IncrementComponentManager.queryActivityInfo(oldComponentName) == null) {
            Log.e(TAG, "Failed to query target activity's info, perhaps the target is not hotpluged component. Target: " + oldComponentName);
            return false;
        }
        intent.setComponent(oldComponent);
        intent.removeExtra(EnvConsts.INTENT_EXTRA_OLD_COMPONENT);
        return true;
    }

    private void fixActivityParams(Activity target, ActivityInfo targetAInfo) {
        target.setRequestedOrientation(targetAInfo.screenOrientation);
        target.setTheme(targetAInfo.theme);
        try {
            ShareReflectUtil.findField((Object) target, "mActivityInfo").set(target, targetAInfo);
        } catch (Throwable thr) {
            throw new TinkerRuntimeException("see next stacktrace.", thr);
        }
    }

    private void copyAllFields(Instrumentation src) throws IllegalAccessException {
        Field[] fields = Instrumentation.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields[i].set(this, fields[i].get(src));
        }
    }
}
