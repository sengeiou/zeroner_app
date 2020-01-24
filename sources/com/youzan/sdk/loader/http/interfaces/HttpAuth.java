package com.youzan.sdk.loader.http.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface HttpAuth {
    public static final int Auth_NONE = 0;
    public static final int Auth_TOKEN = 2;
    public static final int Auth_TOKEN_URL = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {
    }
}
