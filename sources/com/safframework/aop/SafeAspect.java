package com.safframework.aop;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.safframework.log.L;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SafeAspect {
    @Around("execution(!synthetic * *(..)) && onSafe()")
    public Object doSafeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return safeMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.aop.annotation.Safe)||@annotation(com.safframework.aop.annotation.Safe)")
    public void onSafe() {
    }

    private Object safeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            L.w(getStringFromException(e));
            return result;
        }
    }

    private static String getStringFromException(Throwable ex) {
        StringWriter errors = new StringWriter();
        ThrowableExtension.printStackTrace(ex, new PrintWriter(errors));
        return errors.toString();
    }
}
