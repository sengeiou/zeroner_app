package com.tencent.tinker.loader.hotplug.handler;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import com.tencent.tinker.loader.hotplug.IncrementComponentManager;
import com.tencent.tinker.loader.hotplug.interceptor.ServiceBinderInterceptor.BinderInvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PMSInterceptHandler implements BinderInvocationHandler {
    private static final String TAG = "Tinker.PMSIntrcptHndlr";

    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("getActivityInfo".equals(methodName)) {
            return handleGetActivityInfo(target, method, args);
        }
        if ("resolveIntent".equals(methodName)) {
            return handleResolveIntent(target, method, args);
        }
        return method.invoke(target, args);
    }

    private Object handleGetActivityInfo(Object target, Method method, Object[] args) throws Throwable {
        Class<?>[] methodExceptionTypes = method.getExceptionTypes();
        try {
            Object res = method.invoke(target, args);
            if (res != null) {
                return res;
            }
            ComponentName componentName = null;
            int compNameIdx = 0;
            while (true) {
                if (compNameIdx >= args.length) {
                    break;
                } else if (args[compNameIdx] instanceof ComponentName) {
                    Log.i(TAG, "locate componentName field of " + method.getName() + " done at idx: " + compNameIdx);
                    componentName = args[compNameIdx];
                    break;
                } else {
                    compNameIdx++;
                }
            }
            if (componentName != null) {
                return IncrementComponentManager.queryActivityInfo(componentName.getClassName());
            }
            Log.w(TAG, "failed to locate componentName field of " + method.getName() + ", notice any crashes or mistakes after resolve works.");
            return null;
        } catch (InvocationTargetException e) {
            Throwable targetThr = e.getTargetException();
            if (methodExceptionTypes == null || methodExceptionTypes.length <= 0) {
                String str = TAG;
                String str2 = "unexpected exception.";
                if (targetThr == 0) {
                    targetThr = e;
                }
                Log.e(str, str2, targetThr);
                return null;
            }
            if (targetThr == 0) {
                targetThr = e;
            }
            throw targetThr;
        } catch (Throwable thr) {
            Log.e(TAG, "unexpected exception.", thr);
            return null;
        }
    }

    private Object handleResolveIntent(Object target, Method method, Object[] args) throws Throwable {
        Class<?>[] methodExceptionTypes = method.getExceptionTypes();
        try {
            Object res = method.invoke(target, args);
            if (res != null) {
                return res;
            }
            Log.w(TAG, "failed to resolve activity in base package, try again in patch package.");
            Intent intent = null;
            int intentIdx = 0;
            while (true) {
                if (intentIdx >= args.length) {
                    break;
                } else if (args[intentIdx] instanceof Intent) {
                    Log.i(TAG, "locate intent field of " + method.getName() + " done at idx: " + intentIdx);
                    intent = args[intentIdx];
                    break;
                } else {
                    intentIdx++;
                }
            }
            if (intent != null) {
                return IncrementComponentManager.resolveIntent(intent);
            }
            Log.w(TAG, "failed to locate intent field of " + method.getName() + ", notice any crashes or mistakes after resolve works.");
            return null;
        } catch (InvocationTargetException e) {
            Throwable targetThr = e.getTargetException();
            if (methodExceptionTypes == null || methodExceptionTypes.length <= 0) {
                String str = TAG;
                String str2 = "unexpected exception.";
                if (targetThr == 0) {
                    targetThr = e;
                }
                Log.e(str, str2, targetThr);
                return null;
            }
            if (targetThr == 0) {
                targetThr = e;
            }
            throw targetThr;
        } catch (Throwable thr) {
            Log.e(TAG, "unexpected exception.", thr);
            return null;
        }
    }
}
