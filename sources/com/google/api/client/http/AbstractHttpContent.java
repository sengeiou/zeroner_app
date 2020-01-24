package com.google.api.client.http;

import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class AbstractHttpContent implements HttpContent {
    private long computedLength;
    private HttpMediaType mediaType;

    protected AbstractHttpContent(String mediaType2) {
        this(mediaType2 == null ? null : new HttpMediaType(mediaType2));
    }

    protected AbstractHttpContent(HttpMediaType mediaType2) {
        this.computedLength = -1;
        this.mediaType = mediaType2;
    }

    public long getLength() throws IOException {
        if (this.computedLength == -1) {
            this.computedLength = computeLength();
        }
        return this.computedLength;
    }

    public final HttpMediaType getMediaType() {
        return this.mediaType;
    }

    public AbstractHttpContent setMediaType(HttpMediaType mediaType2) {
        this.mediaType = mediaType2;
        return this;
    }

    /* access modifiers changed from: protected */
    public final Charset getCharset() {
        return (this.mediaType == null || this.mediaType.getCharsetParameter() == null) ? Charsets.UTF_8 : this.mediaType.getCharsetParameter();
    }

    public String getType() {
        if (this.mediaType == null) {
            return null;
        }
        return this.mediaType.build();
    }

    /* access modifiers changed from: protected */
    public long computeLength() throws IOException {
        return computeLength(this);
    }

    public boolean retrySupported() {
        return true;
    }

    public static long computeLength(HttpContent content) throws IOException {
        if (!content.retrySupported()) {
            return -1;
        }
        return IOUtils.computeLength(content);
    }
}
