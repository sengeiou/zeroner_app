package com.safframework.aop;

import com.safframework.aop.annotation.Trace;
import com.safframework.log.L;
import com.safframework.tony.common.utils.Preconditions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TraceAspect {
    private static final String POINTCUT_CONSTRUCTOR = "execution(@com.safframework.aop.annotation.Trace *.new(..))";
    private static final String POINTCUT_METHOD = "execution(@com.safframework.aop.annotation.Trace * *(..))";
    private static final int ns = 1000000;

    @Pointcut("execution(@com.safframework.aop.annotation.Trace * *(..))")
    public void methodAnnotatedWithTrace() {
    }

    @Pointcut("execution(@com.safframework.aop.annotation.Trace *.new(..))")
    public void constructorAnnotatedTrace() {
    }

    @Around("methodAnnotatedWithTrace() || constructorAnnotatedTrace()")
    public Object traceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Trace trace = (Trace) methodSignature.getMethod().getAnnotation(Trace.class);
        if (trace != null && !trace.enable()) {
            return joinPoint.proceed();
        }
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        if (Preconditions.isBlank(className)) {
            className = "Anonymous class";
        }
        L.i(className, buildLogMessage(methodName, stopWatch.getElapsedTime()));
        return proceed;
    }

    private static String buildLogMessage(String methodName, long methodDuration) {
        if (methodDuration > 10000000) {
            return String.format("%s() take %d ms", new Object[]{methodName, Long.valueOf(methodDuration / 1000000)});
        } else if (methodDuration > 1000000) {
            return String.format("%s() take %dms %dns", new Object[]{methodName, Long.valueOf(methodDuration / 1000000), Long.valueOf(methodDuration % 1000000)});
        } else {
            return String.format("%s() take %dns", new Object[]{methodName, Long.valueOf(methodDuration % 1000000)});
        }
    }
}
