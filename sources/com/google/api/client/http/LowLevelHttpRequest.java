package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.IOException;

public abstract class LowLevelHttpRequest {
    private String contentEncoding;
    private long contentLength = -1;
    private String contentType;
    private StreamingContent streamingContent;

    public abstract void addHeader(String str, String str2) throws IOException;

    public abstract LowLevelHttpResponse execute() throws IOException;

    public final void setContentLength(long contentLength2) throws IOException {
        this.contentLength = contentLength2;
    }

    public final long getContentLength() {
        return this.contentLength;
    }

    public final void setContentEncoding(String contentEncoding2) throws IOException {
        this.contentEncoding = contentEncoding2;
    }

    public final String getContentEncoding() {
        return this.contentEncoding;
    }

    public final void setContentType(String contentType2) throws IOException {
        this.contentType = contentType2;
    }

    public final String getContentType() {
        return this.contentType;
    }

    public final void setStreamingContent(StreamingContent streamingContent2) throws IOException {
        this.streamingContent = streamingContent2;
    }

    public final StreamingContent getStreamingContent() {
        return this.streamingContent;
    }

    public void setTimeout(int connectTimeout, int readTimeout) throws IOException {
    }
}
