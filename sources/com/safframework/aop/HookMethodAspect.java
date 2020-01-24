package com.safframework.aop;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.safframework.aop.annotation.HookMethod;
import com.safframework.log.L;
import com.safframework.tony.common.reflect.Reflect;
import com.safframework.tony.common.reflect.ReflectException;
import com.safframework.tony.common.utils.Preconditions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class HookMethodAspect {
    private static final String POINTCUT_CONSTRUCTOR = "execution(@com.safframework.aop.annotation.HookMethod *.new(..))";
    private static final String POINTCUT_METHOD = "execution(@com.safframework.aop.annotation.HookMethod * *(..))";

    @Pointcut("execution(@com.safframework.aop.annotation.HookMethod * *(..))")
    public void methodAnnotatedWithHookMethod() {
    }

    @Pointcut("execution(@com.safframework.aop.annotation.HookMethod *.new(..))")
    public void constructorAnnotatedHookMethod() {
    }

    @Around("methodAnnotatedWithHookMethod() || constructorAnnotatedHookMethod()")
    public void hookMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        HookMethod hookMethod = (HookMethod) ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(HookMethod.class);
        if (hookMethod != null) {
            String beforeMethod = hookMethod.beforeMethod();
            String afterMethod = hookMethod.afterMethod();
            if (Preconditions.isNotBlank(beforeMethod)) {
                try {
                    Reflect.on(joinPoint.getTarget()).call(beforeMethod);
                } catch (ReflectException e) {
                    ThrowableExtension.printStackTrace(e);
                    L.e("no method " + beforeMethod);
                }
            }
            joinPoint.proceed();
            if (Preconditions.isNotBlank(afterMethod)) {
                try {
                    Reflect.on(joinPoint.getTarget()).call(afterMethod);
                } catch (ReflectException e2) {
                    ThrowableExtension.printStackTrace(e2);
                    L.e("no method " + afterMethod);
                }
            }
        }
    }
}
