package com.safframework.aop.annotation;

import android.annotation.TargetApi;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@TargetApi(14)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    int expiry() default -1;

    String key();
}
