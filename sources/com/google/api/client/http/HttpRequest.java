package com.google.api.client.http;

import com.google.api.client.util.Beta;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public final class HttpRequest {
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    public static final String USER_AGENT_SUFFIX = "Google-HTTP-Java-Client/1.23.0 (gzip)";
    public static final String VERSION = "1.23.0";
    @Beta
    @Deprecated
    private BackOffPolicy backOffPolicy;
    private int connectTimeout = 20000;
    private HttpContent content;
    private int contentLoggingLimit = 16384;
    private boolean curlLoggingEnabled = true;
    private HttpEncoding encoding;
    private HttpExecuteInterceptor executeInterceptor;
    private boolean followRedirects = true;
    private HttpHeaders headers = new HttpHeaders();
    @Beta
    private HttpIOExceptionHandler ioExceptionHandler;
    private boolean loggingEnabled = true;
    private int numRetries = 10;
    private ObjectParser objectParser;
    private int readTimeout = 20000;
    private String requestMethod;
    private HttpHeaders responseHeaders = new HttpHeaders();
    private HttpResponseInterceptor responseInterceptor;
    @Beta
    @Deprecated
    private boolean retryOnExecuteIOException = false;
    private Sleeper sleeper = Sleeper.DEFAULT;
    private boolean suppressUserAgentSuffix;
    private boolean throwExceptionOnExecuteError = true;
    private final HttpTransport transport;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    private GenericUrl url;

    HttpRequest(HttpTransport transport2, String requestMethod2) {
        this.transport = transport2;
        setRequestMethod(requestMethod2);
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public HttpRequest setRequestMethod(String requestMethod2) {
        Preconditions.checkArgument(requestMethod2 == null || HttpMediaType.matchesToken(requestMethod2));
        this.requestMethod = requestMethod2;
        return this;
    }

    public GenericUrl getUrl() {
        return this.url;
    }

    public HttpRequest setUrl(GenericUrl url2) {
        this.url = (GenericUrl) Preconditions.checkNotNull(url2);
        return this;
    }

    public HttpContent getContent() {
        return this.content;
    }

    public HttpRequest setContent(HttpContent content2) {
        this.content = content2;
        return this;
    }

    public HttpEncoding getEncoding() {
        return this.encoding;
    }

    public HttpRequest setEncoding(HttpEncoding encoding2) {
        this.encoding = encoding2;
        return this;
    }

    @Beta
    @Deprecated
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }

    @Beta
    @Deprecated
    public HttpRequest setBackOffPolicy(BackOffPolicy backOffPolicy2) {
        this.backOffPolicy = backOffPolicy2;
        return this;
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpRequest setContentLoggingLimit(int contentLoggingLimit2) {
        Preconditions.checkArgument(contentLoggingLimit2 >= 0, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = contentLoggingLimit2;
        return this;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public HttpRequest setLoggingEnabled(boolean loggingEnabled2) {
        this.loggingEnabled = loggingEnabled2;
        return this;
    }

    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }

    public HttpRequest setCurlLoggingEnabled(boolean curlLoggingEnabled2) {
        this.curlLoggingEnabled = curlLoggingEnabled2;
        return this;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public HttpRequest setConnectTimeout(int connectTimeout2) {
        Preconditions.checkArgument(connectTimeout2 >= 0);
        this.connectTimeout = connectTimeout2;
        return this;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public HttpRequest setReadTimeout(int readTimeout2) {
        Preconditions.checkArgument(readTimeout2 >= 0);
        this.readTimeout = readTimeout2;
        return this;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public HttpRequest setHeaders(HttpHeaders headers2) {
        this.headers = (HttpHeaders) Preconditions.checkNotNull(headers2);
        return this;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public HttpRequest setResponseHeaders(HttpHeaders responseHeaders2) {
        this.responseHeaders = (HttpHeaders) Preconditions.checkNotNull(responseHeaders2);
        return this;
    }

    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }

    public HttpRequest setInterceptor(HttpExecuteInterceptor interceptor) {
        this.executeInterceptor = interceptor;
        return this;
    }

    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }

    public HttpRequest setUnsuccessfulResponseHandler(HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler2) {
        this.unsuccessfulResponseHandler = unsuccessfulResponseHandler2;
        return this;
    }

    @Beta
    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }

    @Beta
    public HttpRequest setIOExceptionHandler(HttpIOExceptionHandler ioExceptionHandler2) {
        this.ioExceptionHandler = ioExceptionHandler2;
        return this;
    }

    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }

    public HttpRequest setResponseInterceptor(HttpResponseInterceptor responseInterceptor2) {
        this.responseInterceptor = responseInterceptor2;
        return this;
    }

    public int getNumberOfRetries() {
        return this.numRetries;
    }

    public HttpRequest setNumberOfRetries(int numRetries2) {
        Preconditions.checkArgument(numRetries2 >= 0);
        this.numRetries = numRetries2;
        return this;
    }

    public HttpRequest setParser(ObjectParser parser) {
        this.objectParser = parser;
        return this;
    }

    public final ObjectParser getParser() {
        return this.objectParser;
    }

    public boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public HttpRequest setFollowRedirects(boolean followRedirects2) {
        this.followRedirects = followRedirects2;
        return this;
    }

    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }

    public HttpRequest setThrowExceptionOnExecuteError(boolean throwExceptionOnExecuteError2) {
        this.throwExceptionOnExecuteError = throwExceptionOnExecuteError2;
        return this;
    }

    @Beta
    @Deprecated
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }

    @Beta
    @Deprecated
    public HttpRequest setRetryOnExecuteIOException(boolean retryOnExecuteIOException2) {
        this.retryOnExecuteIOException = retryOnExecuteIOException2;
        return this;
    }

    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }

    public HttpRequest setSuppressUserAgentSuffix(boolean suppressUserAgentSuffix2) {
        this.suppressUserAgentSuffix = suppressUserAgentSuffix2;
        return this;
    }

    /* JADX WARNING: type inference failed for: r0v31, types: [com.google.api.client.http.HttpContent] */
    /* JADX WARNING: type inference failed for: r31v0 */
    /* JADX WARNING: type inference failed for: r31v1 */
    /* JADX WARNING: type inference failed for: r31v2 */
    /* JADX WARNING: type inference failed for: r1v12, types: [com.google.api.client.util.StreamingContent] */
    /* JADX WARNING: type inference failed for: r31v3 */
    /* JADX WARNING: type inference failed for: r31v4 */
    /* JADX WARNING: type inference failed for: r1v13, types: [com.google.api.client.util.StreamingContent] */
    /* JADX WARNING: type inference failed for: r1v21, types: [com.google.api.client.util.StreamingContent] */
    /* JADX WARNING: type inference failed for: r31v5 */
    /* JADX WARNING: type inference failed for: r31v6 */
    /* JADX WARNING: type inference failed for: r31v7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x035a  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x046f  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0475  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x0481  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0358 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x030c A[SYNTHETIC, Splitter:B:89:0x030c] */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.api.client.http.HttpResponse execute() throws java.io.IOException {
        /*
            r40 = this;
            r30 = 0
            r0 = r40
            int r0 = r0.numRetries
            r34 = r0
            if (r34 < 0) goto L_0x035b
            r34 = 1
        L_0x000c:
            com.google.api.client.util.Preconditions.checkArgument(r34)
            r0 = r40
            int r0 = r0.numRetries
            r29 = r0
            r0 = r40
            com.google.api.client.http.BackOffPolicy r0 = r0.backOffPolicy
            r34 = r0
            if (r34 == 0) goto L_0x0026
            r0 = r40
            com.google.api.client.http.BackOffPolicy r0 = r0.backOffPolicy
            r34 = r0
            r34.reset()
        L_0x0026:
            r25 = 0
            r0 = r40
            java.lang.String r0 = r0.requestMethod
            r34 = r0
            com.google.api.client.util.Preconditions.checkNotNull(r34)
            r0 = r40
            com.google.api.client.http.GenericUrl r0 = r0.url
            r34 = r0
            com.google.api.client.util.Preconditions.checkNotNull(r34)
        L_0x003a:
            if (r25 == 0) goto L_0x003f
            r25.ignore()
        L_0x003f:
            r25 = 0
            r16 = 0
            r0 = r40
            com.google.api.client.http.HttpExecuteInterceptor r0 = r0.executeInterceptor
            r34 = r0
            if (r34 == 0) goto L_0x0058
            r0 = r40
            com.google.api.client.http.HttpExecuteInterceptor r0 = r0.executeInterceptor
            r34 = r0
            r0 = r34
            r1 = r40
            r0.intercept(r1)
        L_0x0058:
            r0 = r40
            com.google.api.client.http.GenericUrl r0 = r0.url
            r34 = r0
            java.lang.String r33 = r34.build()
            r0 = r40
            com.google.api.client.http.HttpTransport r0 = r0.transport
            r34 = r0
            r0 = r40
            java.lang.String r0 = r0.requestMethod
            r35 = r0
            r0 = r34
            r1 = r35
            r2 = r33
            com.google.api.client.http.LowLevelHttpRequest r22 = r0.buildRequest(r1, r2)
            java.util.logging.Logger r20 = com.google.api.client.http.HttpTransport.LOGGER
            r0 = r40
            boolean r0 = r0.loggingEnabled
            r34 = r0
            if (r34 == 0) goto L_0x035f
            java.util.logging.Level r34 = java.util.logging.Level.CONFIG
            r0 = r20
            r1 = r34
            boolean r34 = r0.isLoggable(r1)
            if (r34 == 0) goto L_0x035f
            r19 = 1
        L_0x0090:
            r18 = 0
            r13 = 0
            if (r19 == 0) goto L_0x00fe
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r34 = "-------------- REQUEST  --------------"
            r0 = r18
            r1 = r34
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r34.append(r35)
            r0 = r40
            java.lang.String r0 = r0.requestMethod
            r34 = r0
            r0 = r18
            r1 = r34
            java.lang.StringBuilder r34 = r0.append(r1)
            r35 = 32
            java.lang.StringBuilder r34 = r34.append(r35)
            r0 = r34
            r1 = r33
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r34.append(r35)
            r0 = r40
            boolean r0 = r0.curlLoggingEnabled
            r34 = r0
            if (r34 == 0) goto L_0x00fe
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r34 = "curl -v --compressed"
            r0 = r34
            r13.<init>(r0)
            r0 = r40
            java.lang.String r0 = r0.requestMethod
            r34 = r0
            java.lang.String r35 = "GET"
            boolean r34 = r34.equals(r35)
            if (r34 != 0) goto L_0x00fe
            java.lang.String r34 = " -X "
            r0 = r34
            java.lang.StringBuilder r34 = r13.append(r0)
            r0 = r40
            java.lang.String r0 = r0.requestMethod
            r35 = r0
            r34.append(r35)
        L_0x00fe:
            r0 = r40
            com.google.api.client.http.HttpHeaders r0 = r0.headers
            r34 = r0
            java.lang.String r24 = r34.getUserAgent()
            r0 = r40
            boolean r0 = r0.suppressUserAgentSuffix
            r34 = r0
            if (r34 != 0) goto L_0x011e
            if (r24 != 0) goto L_0x0363
            r0 = r40
            com.google.api.client.http.HttpHeaders r0 = r0.headers
            r34 = r0
            java.lang.String r35 = "Google-HTTP-Java-Client/1.23.0 (gzip)"
            r34.setUserAgent(r35)
        L_0x011e:
            r0 = r40
            com.google.api.client.http.HttpHeaders r0 = r0.headers
            r34 = r0
            r0 = r34
            r1 = r18
            r2 = r20
            r3 = r22
            com.google.api.client.http.HttpHeaders.serializeHeaders(r0, r1, r13, r2, r3)
            r0 = r40
            boolean r0 = r0.suppressUserAgentSuffix
            r34 = r0
            if (r34 != 0) goto L_0x0144
            r0 = r40
            com.google.api.client.http.HttpHeaders r0 = r0.headers
            r34 = r0
            r0 = r34
            r1 = r24
            r0.setUserAgent(r1)
        L_0x0144:
            r0 = r40
            com.google.api.client.http.HttpContent r0 = r0.content
            r31 = r0
            if (r31 == 0) goto L_0x0158
            r0 = r40
            com.google.api.client.http.HttpContent r0 = r0.content
            r34 = r0
            boolean r34 = r34.retrySupported()
            if (r34 == 0) goto L_0x03ad
        L_0x0158:
            r9 = 1
        L_0x0159:
            if (r31 == 0) goto L_0x028a
            r0 = r40
            com.google.api.client.http.HttpContent r0 = r0.content
            r34 = r0
            java.lang.String r12 = r34.getType()
            if (r19 == 0) goto L_0x0182
            com.google.api.client.util.LoggingStreamingContent r32 = new com.google.api.client.util.LoggingStreamingContent
            java.util.logging.Logger r34 = com.google.api.client.http.HttpTransport.LOGGER
            java.util.logging.Level r35 = java.util.logging.Level.CONFIG
            r0 = r40
            int r0 = r0.contentLoggingLimit
            r36 = r0
            r0 = r32
            r1 = r31
            r2 = r34
            r3 = r35
            r4 = r36
            r0.<init>(r1, r2, r3, r4)
            r31 = r32
        L_0x0182:
            r0 = r40
            com.google.api.client.http.HttpEncoding r0 = r0.encoding
            r34 = r0
            if (r34 != 0) goto L_0x03b0
            r8 = 0
            r0 = r40
            com.google.api.client.http.HttpContent r0 = r0.content
            r34 = r0
            long r10 = r34.getLength()
        L_0x0195:
            if (r19 == 0) goto L_0x026a
            if (r12 == 0) goto L_0x01eb
            java.lang.String r34 = "Content-Type: "
            java.lang.String r35 = java.lang.String.valueOf(r12)
            int r36 = r35.length()
            if (r36 == 0) goto L_0x03d8
            java.lang.String r17 = r34.concat(r35)
        L_0x01aa:
            r0 = r18
            r1 = r17
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r34.append(r35)
            if (r13 == 0) goto L_0x01eb
            java.lang.String r34 = java.lang.String.valueOf(r17)
            java.lang.String r34 = java.lang.String.valueOf(r34)
            java.lang.StringBuilder r35 = new java.lang.StringBuilder
            int r36 = r34.length()
            int r36 = r36 + 6
            r35.<init>(r36)
            java.lang.String r36 = " -H '"
            java.lang.StringBuilder r35 = r35.append(r36)
            r0 = r35
            r1 = r34
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = "'"
            java.lang.StringBuilder r34 = r34.append(r35)
            java.lang.String r34 = r34.toString()
            r0 = r34
            r13.append(r0)
        L_0x01eb:
            if (r8 == 0) goto L_0x023f
            java.lang.String r34 = "Content-Encoding: "
            java.lang.String r35 = java.lang.String.valueOf(r8)
            int r36 = r35.length()
            if (r36 == 0) goto L_0x03e3
            java.lang.String r17 = r34.concat(r35)
        L_0x01fe:
            r0 = r18
            r1 = r17
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r34.append(r35)
            if (r13 == 0) goto L_0x023f
            java.lang.String r34 = java.lang.String.valueOf(r17)
            java.lang.String r34 = java.lang.String.valueOf(r34)
            java.lang.StringBuilder r35 = new java.lang.StringBuilder
            int r36 = r34.length()
            int r36 = r36 + 6
            r35.<init>(r36)
            java.lang.String r36 = " -H '"
            java.lang.StringBuilder r35 = r35.append(r36)
            r0 = r35
            r1 = r34
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = "'"
            java.lang.StringBuilder r34 = r34.append(r35)
            java.lang.String r34 = r34.toString()
            r0 = r34
            r13.append(r0)
        L_0x023f:
            r34 = 0
            int r34 = (r10 > r34 ? 1 : (r10 == r34 ? 0 : -1))
            if (r34 < 0) goto L_0x026a
            java.lang.StringBuilder r34 = new java.lang.StringBuilder
            r35 = 36
            r34.<init>(r35)
            java.lang.String r35 = "Content-Length: "
            java.lang.StringBuilder r34 = r34.append(r35)
            r0 = r34
            java.lang.StringBuilder r34 = r0.append(r10)
            java.lang.String r17 = r34.toString()
            r0 = r18
            r1 = r17
            java.lang.StringBuilder r34 = r0.append(r1)
            java.lang.String r35 = com.google.api.client.util.StringUtils.LINE_SEPARATOR
            r34.append(r35)
        L_0x026a:
            if (r13 == 0) goto L_0x0274
            java.lang.String r34 = " -d '@-'"
            r0 = r34
            r13.append(r0)
        L_0x0274:
            r0 = r22
            r0.setContentType(r12)
            r0 = r22
            r0.setContentEncoding(r8)
            r0 = r22
            r0.setContentLength(r10)
            r0 = r22
            r1 = r31
            r0.setStreamingContent(r1)
        L_0x028a:
            if (r19 == 0) goto L_0x02cd
            java.lang.String r34 = r18.toString()
            r0 = r20
            r1 = r34
            r0.config(r1)
            if (r13 == 0) goto L_0x02cd
            java.lang.String r34 = " -- '"
            r0 = r34
            r13.append(r0)
            java.lang.String r34 = "'"
            java.lang.String r35 = "'\"'\"'"
            java.lang.String r34 = r33.replaceAll(r34, r35)
            r0 = r34
            r13.append(r0)
            java.lang.String r34 = "'"
            r0 = r34
            r13.append(r0)
            if (r31 == 0) goto L_0x02c2
            java.lang.String r34 = " << $$$"
            r0 = r34
            r13.append(r0)
        L_0x02c2:
            java.lang.String r34 = r13.toString()
            r0 = r20
            r1 = r34
            r0.config(r1)
        L_0x02cd:
            if (r9 == 0) goto L_0x03ee
            if (r29 <= 0) goto L_0x03ee
            r30 = 1
        L_0x02d3:
            r0 = r40
            int r0 = r0.connectTimeout
            r34 = r0
            r0 = r40
            int r0 = r0.readTimeout
            r35 = r0
            r0 = r22
            r1 = r34
            r2 = r35
            r0.setTimeout(r1, r2)
            com.google.api.client.http.LowLevelHttpResponse r23 = r22.execute()     // Catch:{ IOException -> 0x03ff }
            r27 = 0
            com.google.api.client.http.HttpResponse r26 = new com.google.api.client.http.HttpResponse     // Catch:{ all -> 0x03f2 }
            r0 = r26
            r1 = r40
            r2 = r23
            r0.<init>(r1, r2)     // Catch:{ all -> 0x03f2 }
            r27 = 1
            if (r27 != 0) goto L_0x0306
            java.io.InputStream r21 = r23.getContent()     // Catch:{ IOException -> 0x04b6 }
            if (r21 == 0) goto L_0x0306
            r21.close()     // Catch:{ IOException -> 0x04b6 }
        L_0x0306:
            r25 = r26
        L_0x0308:
            r28 = 0
            if (r25 == 0) goto L_0x046d
            boolean r34 = r25.isSuccessStatusCode()     // Catch:{ all -> 0x0478 }
            if (r34 != 0) goto L_0x046d
            r15 = 0
            r0 = r40
            com.google.api.client.http.HttpUnsuccessfulResponseHandler r0 = r0.unsuccessfulResponseHandler     // Catch:{ all -> 0x0478 }
            r34 = r0
            if (r34 == 0) goto L_0x032d
            r0 = r40
            com.google.api.client.http.HttpUnsuccessfulResponseHandler r0 = r0.unsuccessfulResponseHandler     // Catch:{ all -> 0x0478 }
            r34 = r0
            r0 = r34
            r1 = r40
            r2 = r25
            r3 = r30
            boolean r15 = r0.handleResponse(r1, r2, r3)     // Catch:{ all -> 0x0478 }
        L_0x032d:
            if (r15 != 0) goto L_0x0344
            int r34 = r25.getStatusCode()     // Catch:{ all -> 0x0478 }
            com.google.api.client.http.HttpHeaders r35 = r25.getHeaders()     // Catch:{ all -> 0x0478 }
            r0 = r40
            r1 = r34
            r2 = r35
            boolean r34 = r0.handleRedirect(r1, r2)     // Catch:{ all -> 0x0478 }
            if (r34 == 0) goto L_0x0435
            r15 = 1
        L_0x0344:
            r30 = r30 & r15
            if (r30 == 0) goto L_0x034b
            r25.ignore()     // Catch:{ all -> 0x0478 }
        L_0x034b:
            int r29 = r29 + -1
            r28 = 1
            if (r25 == 0) goto L_0x0356
            if (r28 != 0) goto L_0x0356
            r25.disconnect()
        L_0x0356:
            if (r30 != 0) goto L_0x003a
            if (r25 != 0) goto L_0x0481
            throw r16
        L_0x035b:
            r34 = 0
            goto L_0x000c
        L_0x035f:
            r19 = 0
            goto L_0x0090
        L_0x0363:
            r0 = r40
            com.google.api.client.http.HttpHeaders r0 = r0.headers
            r34 = r0
            java.lang.String r35 = java.lang.String.valueOf(r24)
            java.lang.String r35 = java.lang.String.valueOf(r35)
            java.lang.String r36 = "Google-HTTP-Java-Client/1.23.0 (gzip)"
            java.lang.String r36 = java.lang.String.valueOf(r36)
            java.lang.String r36 = java.lang.String.valueOf(r36)
            java.lang.StringBuilder r37 = new java.lang.StringBuilder
            int r38 = r35.length()
            int r38 = r38 + 1
            int r39 = r36.length()
            int r38 = r38 + r39
            r37.<init>(r38)
            r0 = r37
            r1 = r35
            java.lang.StringBuilder r35 = r0.append(r1)
            java.lang.String r37 = " "
            r0 = r35
            r1 = r37
            java.lang.StringBuilder r35 = r0.append(r1)
            java.lang.StringBuilder r35 = r35.append(r36)
            java.lang.String r35 = r35.toString()
            r34.setUserAgent(r35)
            goto L_0x011e
        L_0x03ad:
            r9 = 0
            goto L_0x0159
        L_0x03b0:
            r0 = r40
            com.google.api.client.http.HttpEncoding r0 = r0.encoding
            r34 = r0
            java.lang.String r8 = r34.getName()
            com.google.api.client.http.HttpEncodingStreamingContent r32 = new com.google.api.client.http.HttpEncodingStreamingContent
            r0 = r40
            com.google.api.client.http.HttpEncoding r0 = r0.encoding
            r34 = r0
            r0 = r32
            r1 = r31
            r2 = r34
            r0.<init>(r1, r2)
            if (r9 == 0) goto L_0x03d5
            long r10 = com.google.api.client.util.IOUtils.computeLength(r32)
        L_0x03d1:
            r31 = r32
            goto L_0x0195
        L_0x03d5:
            r10 = -1
            goto L_0x03d1
        L_0x03d8:
            java.lang.String r17 = new java.lang.String
            r0 = r17
            r1 = r34
            r0.<init>(r1)
            goto L_0x01aa
        L_0x03e3:
            java.lang.String r17 = new java.lang.String
            r0 = r17
            r1 = r34
            r0.<init>(r1)
            goto L_0x01fe
        L_0x03ee:
            r30 = 0
            goto L_0x02d3
        L_0x03f2:
            r34 = move-exception
            if (r27 != 0) goto L_0x03fe
            java.io.InputStream r21 = r23.getContent()     // Catch:{ IOException -> 0x03ff }
            if (r21 == 0) goto L_0x03fe
            r21.close()     // Catch:{ IOException -> 0x03ff }
        L_0x03fe:
            throw r34     // Catch:{ IOException -> 0x03ff }
        L_0x03ff:
            r14 = move-exception
        L_0x0400:
            r0 = r40
            boolean r0 = r0.retryOnExecuteIOException
            r34 = r0
            if (r34 != 0) goto L_0x0423
            r0 = r40
            com.google.api.client.http.HttpIOExceptionHandler r0 = r0.ioExceptionHandler
            r34 = r0
            if (r34 == 0) goto L_0x0422
            r0 = r40
            com.google.api.client.http.HttpIOExceptionHandler r0 = r0.ioExceptionHandler
            r34 = r0
            r0 = r34
            r1 = r40
            r2 = r30
            boolean r34 = r0.handleIOException(r1, r2)
            if (r34 != 0) goto L_0x0423
        L_0x0422:
            throw r14
        L_0x0423:
            r16 = r14
            java.util.logging.Level r34 = java.util.logging.Level.WARNING
            java.lang.String r35 = "exception thrown while executing request"
            r0 = r20
            r1 = r34
            r2 = r35
            r0.log(r1, r2, r14)
            goto L_0x0308
        L_0x0435:
            if (r30 == 0) goto L_0x0344
            r0 = r40
            com.google.api.client.http.BackOffPolicy r0 = r0.backOffPolicy     // Catch:{ all -> 0x0478 }
            r34 = r0
            if (r34 == 0) goto L_0x0344
            r0 = r40
            com.google.api.client.http.BackOffPolicy r0 = r0.backOffPolicy     // Catch:{ all -> 0x0478 }
            r34 = r0
            int r35 = r25.getStatusCode()     // Catch:{ all -> 0x0478 }
            boolean r34 = r34.isBackOffRequired(r35)     // Catch:{ all -> 0x0478 }
            if (r34 == 0) goto L_0x0344
            r0 = r40
            com.google.api.client.http.BackOffPolicy r0 = r0.backOffPolicy     // Catch:{ all -> 0x0478 }
            r34 = r0
            long r6 = r34.getNextBackOffMillis()     // Catch:{ all -> 0x0478 }
            r34 = -1
            int r34 = (r6 > r34 ? 1 : (r6 == r34 ? 0 : -1))
            if (r34 == 0) goto L_0x0344
            r0 = r40
            com.google.api.client.util.Sleeper r0 = r0.sleeper     // Catch:{ InterruptedException -> 0x04b4 }
            r34 = r0
            r0 = r34
            r0.sleep(r6)     // Catch:{ InterruptedException -> 0x04b4 }
        L_0x046a:
            r15 = 1
            goto L_0x0344
        L_0x046d:
            if (r25 != 0) goto L_0x0475
            r34 = 1
        L_0x0471:
            r30 = r30 & r34
            goto L_0x034b
        L_0x0475:
            r34 = 0
            goto L_0x0471
        L_0x0478:
            r34 = move-exception
            if (r25 == 0) goto L_0x0480
            if (r28 != 0) goto L_0x0480
            r25.disconnect()
        L_0x0480:
            throw r34
        L_0x0481:
            r0 = r40
            com.google.api.client.http.HttpResponseInterceptor r0 = r0.responseInterceptor
            r34 = r0
            if (r34 == 0) goto L_0x0496
            r0 = r40
            com.google.api.client.http.HttpResponseInterceptor r0 = r0.responseInterceptor
            r34 = r0
            r0 = r34
            r1 = r25
            r0.interceptResponse(r1)
        L_0x0496:
            r0 = r40
            boolean r0 = r0.throwExceptionOnExecuteError
            r34 = r0
            if (r34 == 0) goto L_0x04b3
            boolean r34 = r25.isSuccessStatusCode()
            if (r34 != 0) goto L_0x04b3
            com.google.api.client.http.HttpResponseException r34 = new com.google.api.client.http.HttpResponseException     // Catch:{ all -> 0x04ae }
            r0 = r34
            r1 = r25
            r0.<init>(r1)     // Catch:{ all -> 0x04ae }
            throw r34     // Catch:{ all -> 0x04ae }
        L_0x04ae:
            r34 = move-exception
            r25.disconnect()
            throw r34
        L_0x04b3:
            return r25
        L_0x04b4:
            r34 = move-exception
            goto L_0x046a
        L_0x04b6:
            r14 = move-exception
            r25 = r26
            goto L_0x0400
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.http.HttpRequest.execute():com.google.api.client.http.HttpResponse");
    }

    @Beta
    public Future<HttpResponse> executeAsync(Executor executor) {
        FutureTask<HttpResponse> future = new FutureTask<>(new Callable<HttpResponse>() {
            public HttpResponse call() throws Exception {
                return HttpRequest.this.execute();
            }
        });
        executor.execute(future);
        return future;
    }

    @Beta
    public Future<HttpResponse> executeAsync() {
        return executeAsync(Executors.newSingleThreadExecutor());
    }

    public boolean handleRedirect(int statusCode, HttpHeaders responseHeaders2) {
        String redirectLocation = responseHeaders2.getLocation();
        if (!getFollowRedirects() || !HttpStatusCodes.isRedirect(statusCode) || redirectLocation == null) {
            return false;
        }
        setUrl(new GenericUrl(this.url.toURL(redirectLocation)));
        if (statusCode == 303) {
            setRequestMethod("GET");
            setContent(null);
        }
        this.headers.setAuthorization((String) null);
        this.headers.setIfMatch(null);
        this.headers.setIfNoneMatch(null);
        this.headers.setIfModifiedSince(null);
        this.headers.setIfUnmodifiedSince(null);
        this.headers.setIfRange(null);
        return true;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public HttpRequest setSleeper(Sleeper sleeper2) {
        this.sleeper = (Sleeper) Preconditions.checkNotNull(sleeper2);
        return this;
    }
}
