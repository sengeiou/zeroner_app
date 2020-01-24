package com.tencent.tinker.loader.hotplug.handler;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.tinker.loader.hotplug.IncrementComponentManager;
import com.tencent.tinker.loader.hotplug.interceptor.HandlerMessageInterceptor.MessageHandler;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MHMessageHandler implements MessageHandler {
    private static final int LAUNCH_ACTIVITY;
    private static final String TAG = "Tinker.MHMsgHndlr";
    private final Context mContext;

    static {
        int launchActivity = 100;
        if (VERSION.SDK_INT < 27) {
            try {
                launchActivity = ShareReflectUtil.findField(Class.forName("android.app.ActivityThread$H"), "LAUNCH_ACTIVITY").getInt(null);
            } catch (Throwable th) {
                launchActivity = 100;
            }
        }
        LAUNCH_ACTIVITY = launchActivity;
    }

    public MHMessageHandler(Context context) {
        while (context instanceof ContextWrapper) {
            Context baseCtx = ((ContextWrapper) context).getBaseContext();
            if (baseCtx == null) {
                break;
            }
            context = baseCtx;
        }
        this.mContext = context;
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            try {
                Object activityClientRecord = msg.obj;
                if (activityClientRecord == null) {
                    Log.w(TAG, "msg: [" + msg.what + "] has no 'obj' value.");
                } else {
                    Intent maybeHackedIntent = (Intent) ShareReflectUtil.findField(activityClientRecord, "intent").get(activityClientRecord);
                    if (maybeHackedIntent == null) {
                        Log.w(TAG, "cannot fetch intent from message received by mH.");
                    } else {
                        ShareIntentUtil.fixIntentClassLoader(maybeHackedIntent, this.mContext.getClassLoader());
                        ComponentName oldComponent = (ComponentName) maybeHackedIntent.getParcelableExtra(EnvConsts.INTENT_EXTRA_OLD_COMPONENT);
                        if (oldComponent == null) {
                            Log.w(TAG, "oldComponent was null, start " + maybeHackedIntent.getComponent() + " next.");
                        } else {
                            ActivityInfo aInfo = (ActivityInfo) ShareReflectUtil.findField(activityClientRecord, "activityInfo").get(activityClientRecord);
                            if (aInfo != null) {
                                ActivityInfo targetAInfo = IncrementComponentManager.queryActivityInfo(oldComponent.getClassName());
                                if (targetAInfo == null) {
                                    Log.e(TAG, "Failed to query target activity's info, perhaps the target is not hotpluged component. Target: " + oldComponent.getClassName());
                                } else {
                                    fixActivityScreenOrientation(activityClientRecord, targetAInfo.screenOrientation);
                                    fixStubActivityInfo(aInfo, targetAInfo);
                                    maybeHackedIntent.setComponent(oldComponent);
                                    maybeHackedIntent.removeExtra(EnvConsts.INTENT_EXTRA_OLD_COMPONENT);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable thr) {
                Log.e(TAG, "exception in handleMessage.", thr);
            }
        }
        return false;
    }

    private void fixStubActivityInfo(ActivityInfo stubAInfo, ActivityInfo targetAInfo) {
        copyInstanceFields(targetAInfo, stubAInfo);
    }

    private <T> void copyInstanceFields(T srcObj, T destObj) {
        Field[] fields;
        if (srcObj != null && destObj != null) {
            for (Class<?> infoClazz = srcObj.getClass(); !infoClazz.equals(Object.class); infoClazz = infoClazz.getSuperclass()) {
                for (Field field : infoClazz.getDeclaredFields()) {
                    if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        try {
                            field.set(destObj, field.get(srcObj));
                        } catch (Throwable th) {
                        }
                    }
                }
            }
        }
    }

    private void fixActivityScreenOrientation(Object activityClientRecord, int screenOrientation) {
        if (screenOrientation == -1) {
            screenOrientation = 2;
        }
        try {
            Object token = ShareReflectUtil.findField(activityClientRecord, "token").get(activityClientRecord);
            Object amn = ShareReflectUtil.findMethod(Class.forName("android.app.ActivityManagerNative"), "getDefault", (Class<?>[]) new Class[0]).invoke(null, new Object[0]);
            ShareReflectUtil.findMethod(amn, "setRequestedOrientation", (Class<?>[]) new Class[]{IBinder.class, Integer.TYPE}).invoke(amn, new Object[]{token, Integer.valueOf(screenOrientation)});
        } catch (Throwable thr) {
            Log.e(TAG, "Failed to fix screen orientation.", thr);
        }
    }
}
