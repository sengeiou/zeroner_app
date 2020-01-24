package com.safframework.aop;

import com.safframework.log.L;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class LogMethodAspect {
    @Around("execution(!synthetic * *(..)) && onLogMethod()")
    public Object doLogMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.aop.annotation.LogMethod)||@annotation(com.safframework.aop.annotation.LogMethod)")
    public void onLogMethod() {
    }

    private Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj;
        L.w(joinPoint.getSignature().toShortString() + " Args : " + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
        Object result = joinPoint.proceed();
        String type = ((MethodSignature) joinPoint.getSignature()).getReturnType().toString();
        StringBuilder append = new StringBuilder().append(joinPoint.getSignature().toShortString()).append(" Result : ");
        if ("void".equalsIgnoreCase(type)) {
            obj = "void";
        } else {
            obj = result;
        }
        L.w(append.append(obj).toString());
        return result;
    }
}
