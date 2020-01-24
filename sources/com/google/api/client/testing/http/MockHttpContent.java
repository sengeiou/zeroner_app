package com.google.api.client.testing.http;

import com.google.api.client.http.HttpContent;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

@Beta
public class MockHttpContent implements HttpContent {
    private byte[] content = new byte[0];
    private long length = -1;
    private String type;

    public long getLength() throws IOException {
        return this.length;
    }

    public String getType() {
        return this.type;
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.content);
        out.flush();
    }

    public boolean retrySupported() {
        return true;
    }

    public final byte[] getContent() {
        return this.content;
    }

    public MockHttpContent setContent(byte[] content2) {
        this.content = (byte[]) Preconditions.checkNotNull(content2);
        return this;
    }

    public MockHttpContent setLength(long length2) {
        Preconditions.checkArgument(length2 >= -1);
        this.length = length2;
        return this;
    }

    public MockHttpContent setType(String type2) {
        this.type = type2;
        return this;
    }
}
