package com.youzan.sdk.loader.http.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface HttpStatus {
    public static final int STATUS_FAILURE = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_WAITING = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }
}
