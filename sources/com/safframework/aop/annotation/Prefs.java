package com.safframework.aop.annotation;

import android.annotation.TargetApi;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TargetApi(14)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Prefs {
    String key();
}
