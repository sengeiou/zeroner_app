package com.google.api.client.http;

import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpResponse {
    private InputStream content;
    private final String contentEncoding;
    private int contentLoggingLimit;
    private boolean contentRead;
    private final String contentType;
    private boolean loggingEnabled;
    private final HttpMediaType mediaType;
    private final HttpRequest request;
    LowLevelHttpResponse response;
    private final int statusCode;
    private final String statusMessage;

    HttpResponse(HttpRequest request2, LowLevelHttpResponse response2) throws IOException {
        boolean loggable;
        StringBuilder sb;
        HttpMediaType httpMediaType = null;
        this.request = request2;
        this.contentLoggingLimit = request2.getContentLoggingLimit();
        this.loggingEnabled = request2.isLoggingEnabled();
        this.response = response2;
        this.contentEncoding = response2.getContentEncoding();
        int code = response2.getStatusCode();
        if (code < 0) {
            code = 0;
        }
        this.statusCode = code;
        String message = response2.getReasonPhrase();
        this.statusMessage = message;
        Logger logger = HttpTransport.LOGGER;
        if (!this.loggingEnabled || !logger.isLoggable(Level.CONFIG)) {
            loggable = false;
        } else {
            loggable = true;
        }
        StringBuilder logbuf = null;
        if (loggable) {
            logbuf = new StringBuilder();
            logbuf.append("-------------- RESPONSE --------------").append(StringUtils.LINE_SEPARATOR);
            String statusLine = response2.getStatusLine();
            if (statusLine != null) {
                logbuf.append(statusLine);
            } else {
                logbuf.append(this.statusCode);
                if (message != null) {
                    logbuf.append(' ').append(message);
                }
            }
            logbuf.append(StringUtils.LINE_SEPARATOR);
        }
        HttpHeaders responseHeaders = request2.getResponseHeaders();
        if (loggable) {
            sb = logbuf;
        } else {
            sb = null;
        }
        responseHeaders.fromHttpResponse(response2, sb);
        String contentType2 = response2.getContentType();
        if (contentType2 == null) {
            contentType2 = request2.getResponseHeaders().getContentType();
        }
        this.contentType = contentType2;
        if (contentType2 != null) {
            httpMediaType = new HttpMediaType(contentType2);
        }
        this.mediaType = httpMediaType;
        if (loggable) {
            logger.config(logbuf.toString());
        }
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpResponse setContentLoggingLimit(int contentLoggingLimit2) {
        Preconditions.checkArgument(contentLoggingLimit2 >= 0, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = contentLoggingLimit2;
        return this;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public HttpResponse setLoggingEnabled(boolean loggingEnabled2) {
        this.loggingEnabled = loggingEnabled2;
        return this;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public String getContentType() {
        return this.contentType;
    }

    public HttpMediaType getMediaType() {
        return this.mediaType;
    }

    public HttpHeaders getHeaders() {
        return this.request.getResponseHeaders();
    }

    public boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public HttpTransport getTransport() {
        return this.request.getTransport();
    }

    public HttpRequest getRequest() {
        return this.request;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.InputStream getContent() throws java.io.IOException {
        /*
            r7 = this;
            boolean r5 = r7.contentRead
            if (r5 != 0) goto L_0x0041
            com.google.api.client.http.LowLevelHttpResponse r5 = r7.response
            java.io.InputStream r3 = r5.getContent()
            if (r3 == 0) goto L_0x003e
            r1 = 0
            java.lang.String r0 = r7.contentEncoding     // Catch:{ EOFException -> 0x0044, all -> 0x004b }
            if (r0 == 0) goto L_0x005a
            java.lang.String r5 = "gzip"
            boolean r5 = r0.contains(r5)     // Catch:{ EOFException -> 0x0044, all -> 0x004b }
            if (r5 == 0) goto L_0x005a
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch:{ EOFException -> 0x0044, all -> 0x004b }
            r4.<init>(r3)     // Catch:{ EOFException -> 0x0044, all -> 0x004b }
        L_0x001f:
            java.util.logging.Logger r2 = com.google.api.client.http.HttpTransport.LOGGER     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            boolean r5 = r7.loggingEnabled     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            if (r5 == 0) goto L_0x0058
            java.util.logging.Level r5 = java.util.logging.Level.CONFIG     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            boolean r5 = r2.isLoggable(r5)     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            if (r5 == 0) goto L_0x0058
            com.google.api.client.util.LoggingInputStream r3 = new com.google.api.client.util.LoggingInputStream     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            java.util.logging.Level r5 = java.util.logging.Level.CONFIG     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            int r6 = r7.contentLoggingLimit     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
            r3.<init>(r4, r2, r5, r6)     // Catch:{ EOFException -> 0x0055, all -> 0x0052 }
        L_0x0036:
            r7.content = r3     // Catch:{ EOFException -> 0x0044, all -> 0x004b }
            r1 = 1
            if (r1 != 0) goto L_0x003e
            r3.close()
        L_0x003e:
            r5 = 1
            r7.contentRead = r5
        L_0x0041:
            java.io.InputStream r5 = r7.content
            return r5
        L_0x0044:
            r5 = move-exception
        L_0x0045:
            if (r1 != 0) goto L_0x003e
            r3.close()
            goto L_0x003e
        L_0x004b:
            r5 = move-exception
        L_0x004c:
            if (r1 != 0) goto L_0x0051
            r3.close()
        L_0x0051:
            throw r5
        L_0x0052:
            r5 = move-exception
            r3 = r4
            goto L_0x004c
        L_0x0055:
            r5 = move-exception
            r3 = r4
            goto L_0x0045
        L_0x0058:
            r3 = r4
            goto L_0x0036
        L_0x005a:
            r4 = r3
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.http.HttpResponse.getContent():java.io.InputStream");
    }

    public void download(OutputStream outputStream) throws IOException {
        IOUtils.copy(getContent(), outputStream);
    }

    public void ignore() throws IOException {
        InputStream content2 = getContent();
        if (content2 != null) {
            content2.close();
        }
    }

    public void disconnect() throws IOException {
        ignore();
        this.response.disconnect();
    }

    public <T> T parseAs(Class<T> dataClass) throws IOException {
        if (!hasMessageBody()) {
            return null;
        }
        return this.request.getParser().parseAndClose(getContent(), getContentCharset(), dataClass);
    }

    private boolean hasMessageBody() throws IOException {
        int statusCode2 = getStatusCode();
        if (!getRequest().getRequestMethod().equals(HttpMethods.HEAD) && statusCode2 / 100 != 1 && statusCode2 != 204 && statusCode2 != 304) {
            return true;
        }
        ignore();
        return false;
    }

    public Object parseAs(Type dataType) throws IOException {
        if (!hasMessageBody()) {
            return null;
        }
        return this.request.getParser().parseAndClose(getContent(), getContentCharset(), dataType);
    }

    public String parseAsString() throws IOException {
        InputStream content2 = getContent();
        if (content2 == null) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(content2, out);
        return out.toString(getContentCharset().name());
    }

    public Charset getContentCharset() {
        return (this.mediaType == null || this.mediaType.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : this.mediaType.getCharsetParameter();
    }
}
