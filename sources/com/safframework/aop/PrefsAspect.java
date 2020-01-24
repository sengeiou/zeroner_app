package com.safframework.aop;

import android.annotation.TargetApi;
import com.safframework.aop.annotation.Prefs;
import com.safframework.prefs.AppPrefs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@TargetApi(14)
@Aspect
public class PrefsAspect {
    @Around("execution(!synthetic * *(..)) && onPrefsMethod()")
    public Object doPrefsMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return prefsMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.aop.annotation.Prefs)||@annotation(com.safframework.aop.annotation.Prefs)")
    public void onPrefsMethod() {
    }

    private Object prefsMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Prefs prefs = (Prefs) ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Prefs.class);
        if (prefs == null) {
            return joinPoint.proceed();
        }
        String key = prefs.key();
        Object result = joinPoint.proceed();
        if ("void".equalsIgnoreCase(((MethodSignature) joinPoint.getSignature()).getReturnType().toString())) {
            return result;
        }
        String className = ((MethodSignature) joinPoint.getSignature()).getReturnType().getCanonicalName();
        AppPrefs appPrefs = AppPrefs.get(Utils.getContext());
        if ("int".equals(className) || "java.lang.Integer".equals(className)) {
            appPrefs.putInt(key, ((Integer) result).intValue());
            return result;
        } else if ("boolean".equals(className) || "java.lang.Boolean".equals(className)) {
            appPrefs.putBoolean(key, ((Boolean) result).booleanValue());
            return result;
        } else if ("float".equals(className) || "java.lang.Float".equals(className)) {
            appPrefs.putFloat(key, ((Float) result).floatValue());
            return result;
        } else if ("long".equals(className) || "java.lang.Long".equals(className)) {
            appPrefs.putLong(key, ((Long) result).longValue());
            return result;
        } else if ("java.lang.String".equals(className)) {
            appPrefs.putString(key, (String) result);
            return result;
        } else {
            appPrefs.putObject(key, result);
            return result;
        }
    }
}
