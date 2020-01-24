package com.alibaba.android.arouter.core;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import java.lang.reflect.Field;

@Deprecated
public class InstrumentationHook extends Instrumentation {
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> targetActivity = cl.loadClass(className);
        Object instanceOfTarget = targetActivity.newInstance();
        if (ARouter.canAutoInject()) {
            String[] autoInjectParams = intent.getStringArrayExtra(ARouter.AUTO_INJECT);
            if (autoInjectParams != null && autoInjectParams.length > 0) {
                for (String paramsName : autoInjectParams) {
                    Object value = intent.getExtras().get(TextUtils.getLeft(paramsName));
                    if (value != null) {
                        try {
                            Field injectField = targetActivity.getDeclaredField(TextUtils.getLeft(paramsName));
                            injectField.setAccessible(true);
                            injectField.set(instanceOfTarget, value);
                        } catch (Exception e) {
                            ARouter.logger.error("ARouter::", "Inject values for activity error! [" + e.getMessage() + "]");
                        }
                    }
                }
            }
        }
        return (Activity) instanceOfTarget;
    }
}
