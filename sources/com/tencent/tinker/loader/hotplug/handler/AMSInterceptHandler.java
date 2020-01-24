package com.tencent.tinker.loader.hotplug.handler;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.tinker.loader.hotplug.ActivityStubManager;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.tinker.loader.hotplug.IncrementComponentManager;
import com.tencent.tinker.loader.hotplug.interceptor.ServiceBinderInterceptor.BinderInvocationHandler;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.lang.reflect.Method;

public class AMSInterceptHandler implements BinderInvocationHandler {
    private static final int INTENT_SENDER_ACTIVITY;
    private static final String TAG = "Tinker.AMSIntrcptHndlr";
    private static final int[] TRANSLUCENT_ATTR_ID = {16842840};
    private final Context mContext;

    static {
        int val = 2;
        if (VERSION.SDK_INT < 27) {
            try {
                val = ((Integer) ShareReflectUtil.findField(ActivityManager.class, "INTENT_SENDER_ACTIVITY").get(null)).intValue();
            } catch (Throwable thr) {
                ThrowableExtension.printStackTrace(thr);
                val = 2;
            }
        }
        INTENT_SENDER_ACTIVITY = val;
    }

    public AMSInterceptHandler(Context context) {
        while (context instanceof ContextWrapper) {
            Context baseCtx = ((ContextWrapper) context).getBaseContext();
            if (baseCtx == null) {
                break;
            }
            context = baseCtx;
        }
        this.mContext = context;
    }

    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("startActivity".equals(methodName)) {
            return handleStartActivity(target, method, args);
        }
        if ("startActivities".equals(methodName)) {
            return handleStartActivities(target, method, args);
        }
        if ("startActivityAndWait".equals(methodName)) {
            return handleStartActivity(target, method, args);
        }
        if ("startActivityWithConfig".equals(methodName)) {
            return handleStartActivity(target, method, args);
        }
        if ("startActivityAsUser".equals(methodName)) {
            return handleStartActivity(target, method, args);
        }
        if ("getIntentSender".equals(methodName)) {
            return handleGetIntentSender(target, method, args);
        }
        return method.invoke(target, args);
    }

    private Object handleStartActivity(Object target, Method method, Object[] args) throws Throwable {
        int intentIdx = -1;
        int i = 0;
        while (true) {
            if (i >= args.length) {
                break;
            } else if (args[i] instanceof Intent) {
                intentIdx = i;
                break;
            } else {
                i++;
            }
        }
        if (intentIdx != -1) {
            Intent newIntent = new Intent((Intent) args[intentIdx]);
            processActivityIntent(newIntent);
            args[intentIdx] = newIntent;
        }
        return method.invoke(target, args);
    }

    private Object handleStartActivities(Object target, Method method, Object[] args) throws Throwable {
        int intentArrIdx = -1;
        int i = 0;
        while (true) {
            if (i >= args.length) {
                break;
            } else if (args[i] instanceof Intent[]) {
                intentArrIdx = i;
                break;
            } else {
                i++;
            }
        }
        if (intentArrIdx != -1) {
            Intent[] oldIntentArr = args[intentArrIdx];
            for (int i2 = 0; i2 < oldIntentArr.length; i2++) {
                Intent newIntent = new Intent(oldIntentArr[i2]);
                processActivityIntent(newIntent);
                oldIntentArr[i2] = newIntent;
            }
        }
        return method.invoke(target, args);
    }

    private Object handleGetIntentSender(Object target, Method method, Object[] args) throws Throwable {
        int intentArrIdx = -1;
        int i = 0;
        while (true) {
            if (i >= args.length) {
                break;
            } else if (args[i] instanceof Intent[]) {
                intentArrIdx = i;
                break;
            } else {
                i++;
            }
        }
        if (intentArrIdx != -1 && args[0].intValue() == INTENT_SENDER_ACTIVITY) {
            Intent[] oldIntentArr = args[intentArrIdx];
            for (int i2 = 0; i2 < oldIntentArr.length; i2++) {
                Intent newIntent = new Intent(oldIntentArr[i2]);
                processActivityIntent(newIntent);
                oldIntentArr[i2] = newIntent;
            }
        }
        return method.invoke(target, args);
    }

    private void processActivityIntent(Intent intent) {
        String origPackageName = null;
        String origClassName = null;
        if (intent.getComponent() != null) {
            origPackageName = intent.getComponent().getPackageName();
            origClassName = intent.getComponent().getClassName();
        } else {
            ResolveInfo rInfo = this.mContext.getPackageManager().resolveActivity(intent, 0);
            if (rInfo == null) {
                rInfo = IncrementComponentManager.resolveIntent(intent);
            }
            if (!(rInfo == null || rInfo.filter == null || !rInfo.filter.hasCategory("android.intent.category.DEFAULT"))) {
                origPackageName = rInfo.activityInfo.packageName;
                origClassName = rInfo.activityInfo.name;
            }
        }
        if (IncrementComponentManager.isIncrementActivity(origClassName)) {
            ActivityInfo origInfo = IncrementComponentManager.queryActivityInfo(origClassName);
            storeAndReplaceOriginalComponentName(intent, origPackageName, origClassName, ActivityStubManager.assignStub(origClassName, origInfo.launchMode, hasTransparentTheme(origInfo)));
        }
    }

    private void storeAndReplaceOriginalComponentName(Intent intent, String origPackageName, String origClassName, String stubClassName) {
        ComponentName origComponentName = new ComponentName(origPackageName, origClassName);
        ShareIntentUtil.fixIntentClassLoader(intent, this.mContext.getClassLoader());
        intent.putExtra(EnvConsts.INTENT_EXTRA_OLD_COMPONENT, origComponentName);
        intent.setComponent(new ComponentName(origPackageName, stubClassName));
    }

    private boolean hasTransparentTheme(ActivityInfo activityInfo) {
        boolean z = false;
        int theme = activityInfo.getThemeResource();
        Theme themeObj = this.mContext.getResources().newTheme();
        themeObj.applyStyle(theme, true);
        TypedArray ta = null;
        try {
            ta = themeObj.obtainStyledAttributes(TRANSLUCENT_ATTR_ID);
            z = ta.getBoolean(0, false);
            if (ta != null) {
                ta.recycle();
            }
        } catch (Throwable th) {
            if (ta != null) {
                ta.recycle();
            }
            throw th;
        }
        return z;
    }
}
