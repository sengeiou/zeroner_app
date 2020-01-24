package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;

public class HttpResponseException extends IOException {
    private static final long serialVersionUID = -1875819453475890043L;
    private final String content;
    private final transient HttpHeaders headers;
    private final int statusCode;
    private final String statusMessage;

    public static class Builder {
        String content;
        HttpHeaders headers;
        String message;
        int statusCode;
        String statusMessage;

        public Builder(int statusCode2, String statusMessage2, HttpHeaders headers2) {
            setStatusCode(statusCode2);
            setStatusMessage(statusMessage2);
            setHeaders(headers2);
        }

        public Builder(HttpResponse response) {
            this(response.getStatusCode(), response.getStatusMessage(), response.getHeaders());
            try {
                this.content = response.parseAsString();
                if (this.content.length() == 0) {
                    this.content = null;
                }
            } catch (IOException exception) {
                ThrowableExtension.printStackTrace(exception);
            }
            StringBuilder builder = HttpResponseException.computeMessageBuffer(response);
            if (this.content != null) {
                builder.append(StringUtils.LINE_SEPARATOR).append(this.content);
            }
            this.message = builder.toString();
        }

        public final String getMessage() {
            return this.message;
        }

        public Builder setMessage(String message2) {
            this.message = message2;
            return this;
        }

        public final int getStatusCode() {
            return this.statusCode;
        }

        public Builder setStatusCode(int statusCode2) {
            Preconditions.checkArgument(statusCode2 >= 0);
            this.statusCode = statusCode2;
            return this;
        }

        public final String getStatusMessage() {
            return this.statusMessage;
        }

        public Builder setStatusMessage(String statusMessage2) {
            this.statusMessage = statusMessage2;
            return this;
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

        public Builder setHeaders(HttpHeaders headers2) {
            this.headers = (HttpHeaders) Preconditions.checkNotNull(headers2);
            return this;
        }

        public final String getContent() {
            return this.content;
        }

        public Builder setContent(String content2) {
            this.content = content2;
            return this;
        }

        public HttpResponseException build() {
            return new HttpResponseException(this);
        }
    }

    public HttpResponseException(HttpResponse response) {
        this(new Builder(response));
    }

    protected HttpResponseException(Builder builder) {
        super(builder.message);
        this.statusCode = builder.statusCode;
        this.statusMessage = builder.statusMessage;
        this.headers = builder.headers;
        this.content = builder.content;
    }

    public final boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final String getStatusMessage() {
        return this.statusMessage;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public final String getContent() {
        return this.content;
    }

    public static StringBuilder computeMessageBuffer(HttpResponse response) {
        StringBuilder builder = new StringBuilder();
        int statusCode2 = response.getStatusCode();
        if (statusCode2 != 0) {
            builder.append(statusCode2);
        }
        String statusMessage2 = response.getStatusMessage();
        if (statusMessage2 != null) {
            if (statusCode2 != 0) {
                builder.append(' ');
            }
            builder.append(statusMessage2);
        }
        return builder;
    }
}
