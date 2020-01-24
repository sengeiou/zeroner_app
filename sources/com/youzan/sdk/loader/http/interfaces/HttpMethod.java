package com.youzan.sdk.loader.http.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface HttpMethod {
    public static final int GET = 1;
    public static final int POST = 2;
    public static final int UPLOAD = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Method {
    }
}
