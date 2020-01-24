package com.google.api.client.testing.http;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.util.TestableByteArrayInputStream;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Beta
public class MockLowLevelHttpResponse extends LowLevelHttpResponse {
    private InputStream content;
    private String contentEncoding;
    private long contentLength = -1;
    private String contentType;
    private List<String> headerNames = new ArrayList();
    private List<String> headerValues = new ArrayList();
    private boolean isDisconnected;
    private String reasonPhrase;
    private int statusCode = 200;

    public MockLowLevelHttpResponse addHeader(String name, String value) {
        this.headerNames.add(Preconditions.checkNotNull(name));
        this.headerValues.add(Preconditions.checkNotNull(value));
        return this;
    }

    public MockLowLevelHttpResponse setContent(String stringContent) {
        return stringContent == null ? setZeroContent() : setContent(StringUtils.getBytesUtf8(stringContent));
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    public MockLowLevelHttpResponse setContent(byte[] byteContent) {
        if (byteContent == null) {
            return setZeroContent();
        }
        this.content = new TestableByteArrayInputStream(byteContent);
        setContentLength((long) byteContent.length);
        return this;
    }

    public MockLowLevelHttpResponse setZeroContent() {
        this.content = null;
        setContentLength(0);
        return this;
    }

    public InputStream getContent() throws IOException {
        return this.content;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public final String getContentType() {
        return this.contentType;
    }

    public int getHeaderCount() {
        return this.headerNames.size();
    }

    public String getHeaderName(int index) {
        return (String) this.headerNames.get(index);
    }

    public String getHeaderValue(int index) {
        return (String) this.headerValues.get(index);
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusLine() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.statusCode);
        if (this.reasonPhrase != null) {
            buf.append(this.reasonPhrase);
        }
        return buf.toString();
    }

    public final List<String> getHeaderNames() {
        return this.headerNames;
    }

    public MockLowLevelHttpResponse setHeaderNames(List<String> headerNames2) {
        this.headerNames = (List) Preconditions.checkNotNull(headerNames2);
        return this;
    }

    public final List<String> getHeaderValues() {
        return this.headerValues;
    }

    public MockLowLevelHttpResponse setHeaderValues(List<String> headerValues2) {
        this.headerValues = (List) Preconditions.checkNotNull(headerValues2);
        return this;
    }

    public MockLowLevelHttpResponse setContent(InputStream content2) {
        this.content = content2;
        return this;
    }

    public MockLowLevelHttpResponse setContentType(String contentType2) {
        this.contentType = contentType2;
        return this;
    }

    public MockLowLevelHttpResponse setContentEncoding(String contentEncoding2) {
        this.contentEncoding = contentEncoding2;
        return this;
    }

    public MockLowLevelHttpResponse setContentLength(long contentLength2) {
        this.contentLength = contentLength2;
        Preconditions.checkArgument(contentLength2 >= -1);
        return this;
    }

    public MockLowLevelHttpResponse setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
        return this;
    }

    public MockLowLevelHttpResponse setReasonPhrase(String reasonPhrase2) {
        this.reasonPhrase = reasonPhrase2;
        return this;
    }

    public void disconnect() throws IOException {
        this.isDisconnected = true;
        super.disconnect();
    }

    public boolean isDisconnected() {
        return this.isDisconnected;
    }
}
