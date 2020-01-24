package com.google.api.client.http.javanet;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Preconditions;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

final class NetHttpRequest extends LowLevelHttpRequest {
    private final HttpURLConnection connection;

    NetHttpRequest(HttpURLConnection connection2) {
        this.connection = connection2;
        connection2.setInstanceFollowRedirects(false);
    }

    public void addHeader(String name, String value) {
        this.connection.addRequestProperty(name, value);
    }

    public void setTimeout(int connectTimeout, int readTimeout) {
        this.connection.setReadTimeout(readTimeout);
        this.connection.setConnectTimeout(connectTimeout);
    }

    public LowLevelHttpResponse execute() throws IOException {
        HttpURLConnection connection2 = this.connection;
        if (getStreamingContent() != null) {
            String contentType = getContentType();
            if (contentType != null) {
                addHeader("Content-Type", contentType);
            }
            String contentEncoding = getContentEncoding();
            if (contentEncoding != null) {
                addHeader(HttpHeaders.CONTENT_ENCODING, contentEncoding);
            }
            long contentLength = getContentLength();
            if (contentLength >= 0) {
                addHeader("Content-Length", Long.toString(contentLength));
            }
            String requestMethod = connection2.getRequestMethod();
            if ("POST".equals(requestMethod) || HttpMethods.PUT.equals(requestMethod)) {
                connection2.setDoOutput(true);
                if (contentLength < 0 || contentLength > 2147483647L) {
                    connection2.setChunkedStreamingMode(0);
                } else {
                    connection2.setFixedLengthStreamingMode((int) contentLength);
                }
                OutputStream out = connection2.getOutputStream();
                try {
                    getStreamingContent().writeTo(out);
                    try {
                    } catch (IOException exception) {
                        if (0 == 0) {
                            throw exception;
                        }
                    }
                } finally {
                    try {
                        out.close();
                    } catch (IOException exception2) {
                        if (1 == 0) {
                            throw exception2;
                        }
                    }
                }
            } else {
                Preconditions.checkArgument(contentLength == 0, "%s with non-zero content length is not supported", requestMethod);
            }
        }
        boolean successfulConnection = false;
        try {
            connection2.connect();
            successfulConnection = true;
            return new NetHttpResponse(connection2);
        } finally {
            if (!successfulConnection) {
                connection2.disconnect();
            }
        }
    }
}
