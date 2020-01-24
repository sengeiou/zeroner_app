package com.google.api.client.http;

import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractInputStreamContent implements HttpContent {
    private boolean closeInputStream = true;
    private String type;

    public abstract InputStream getInputStream() throws IOException;

    public AbstractInputStreamContent(String type2) {
        setType(type2);
    }

    public void writeTo(OutputStream out) throws IOException {
        IOUtils.copy(getInputStream(), out, this.closeInputStream);
        out.flush();
    }

    public String getType() {
        return this.type;
    }

    public final boolean getCloseInputStream() {
        return this.closeInputStream;
    }

    public AbstractInputStreamContent setType(String type2) {
        this.type = type2;
        return this;
    }

    public AbstractInputStreamContent setCloseInputStream(boolean closeInputStream2) {
        this.closeInputStream = closeInputStream2;
        return this;
    }
}
