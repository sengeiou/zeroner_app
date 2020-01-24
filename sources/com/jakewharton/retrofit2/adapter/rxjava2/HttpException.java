package com.jakewharton.retrofit2.adapter.rxjava2;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import retrofit2.Response;

public final class HttpException extends Exception {
    private final int code;
    private final String message;
    private final transient Response<?> response;

    private static String getMessage(Response<?> response2) {
        if (response2 != null) {
            return "HTTP " + response2.code() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + response2.message();
        }
        throw new NullPointerException("response == null");
    }

    public HttpException(Response<?> response2) {
        super(getMessage(response2));
        this.code = response2.code();
        this.message = response2.message();
        this.response = response2;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public Response<?> response() {
        return this.response;
    }
}
