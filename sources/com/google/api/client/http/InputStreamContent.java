package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.io.InputStream;

public final class InputStreamContent extends AbstractInputStreamContent {
    private final InputStream inputStream;
    private long length = -1;
    private boolean retrySupported;

    public InputStreamContent(String type, InputStream inputStream2) {
        super(type);
        this.inputStream = (InputStream) Preconditions.checkNotNull(inputStream2);
    }

    public long getLength() {
        return this.length;
    }

    public boolean retrySupported() {
        return this.retrySupported;
    }

    public InputStreamContent setRetrySupported(boolean retrySupported2) {
        this.retrySupported = retrySupported2;
        return this;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public InputStreamContent setType(String type) {
        return (InputStreamContent) super.setType(type);
    }

    public InputStreamContent setCloseInputStream(boolean closeInputStream) {
        return (InputStreamContent) super.setCloseInputStream(closeInputStream);
    }

    public InputStreamContent setLength(long length2) {
        this.length = length2;
        return this;
    }
}
