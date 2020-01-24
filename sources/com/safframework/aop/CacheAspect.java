package com.safframework.aop;

import android.annotation.TargetApi;
import com.safframework.aop.annotation.Cacheable;
import com.safframework.cache.Cache;
import java.io.Serializable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@TargetApi(14)
@Aspect
public class CacheAspect {
    @Around("execution(!synthetic * *(..)) && onCacheMethod()")
    public Object doCacheMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return cacheMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.aop.annotation.Cacheable)||@annotation(com.safframework.aop.annotation.Cacheable)")
    public void onCacheMethod() {
    }

    private Object cacheMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Cacheable cacheable = (Cacheable) ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Cacheable.class);
        if (cacheable == null) {
            return joinPoint.proceed();
        }
        String key = cacheable.key();
        int expiry = cacheable.expiry();
        Object result = joinPoint.proceed();
        Cache cache = Cache.get(Utils.getContext());
        if (expiry > 0) {
            cache.put(key, (Serializable) result, expiry);
            return result;
        }
        cache.put(key, (Serializable) result);
        return result;
    }
}
