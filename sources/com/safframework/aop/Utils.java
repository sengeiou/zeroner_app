package com.safframework.aop;

import android.annotation.TargetApi;
import android.content.Context;
import com.safframework.tony.common.reflect.Reflect;

public class Utils {
    @TargetApi(14)
    public static Context getContext() {
        return (Context) Reflect.on("android.app.ActivityThread").call("currentApplication").get();
    }
}
