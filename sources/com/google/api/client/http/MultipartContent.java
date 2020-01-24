package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class MultipartContent extends AbstractHttpContent {
    static final String NEWLINE = "\r\n";
    private static final String TWO_DASHES = "--";
    private ArrayList<Part> parts = new ArrayList<>();

    public static final class Part {
        HttpContent content;
        HttpEncoding encoding;
        HttpHeaders headers;

        public Part() {
            this(null);
        }

        public Part(HttpContent content2) {
            this(null, content2);
        }

        public Part(HttpHeaders headers2, HttpContent content2) {
            setHeaders(headers2);
            setContent(content2);
        }

        public Part setContent(HttpContent content2) {
            this.content = content2;
            return this;
        }

        public HttpContent getContent() {
            return this.content;
        }

        public Part setHeaders(HttpHeaders headers2) {
            this.headers = headers2;
            return this;
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

        public Part setEncoding(HttpEncoding encoding2) {
            this.encoding = encoding2;
            return this;
        }

        public HttpEncoding getEncoding() {
            return this.encoding;
        }
    }

    public MultipartContent() {
        super(new HttpMediaType("multipart/related").setParameter("boundary", "__END_OF_PART__"));
    }

    public void writeTo(OutputStream out) throws IOException {
        long contentLength;
        Writer writer = new OutputStreamWriter(out, getCharset());
        String boundary = getBoundary();
        Iterator i$ = this.parts.iterator();
        while (i$.hasNext()) {
            Part part = (Part) i$.next();
            HttpHeaders headers = new HttpHeaders().setAcceptEncoding(null);
            if (part.headers != null) {
                headers.fromHttpHeaders(part.headers);
            }
            headers.setContentEncoding(null).setUserAgent(null).setContentType(null).setContentLength(null).set("Content-Transfer-Encoding", (Object) null);
            HttpContent content = part.content;
            StreamingContent streamingContent = 0;
            if (content != 0) {
                headers.set("Content-Transfer-Encoding", (Object) Arrays.asList(new String[]{"binary"}));
                headers.setContentType(content.getType());
                HttpEncoding encoding = part.encoding;
                if (encoding == null) {
                    contentLength = content.getLength();
                    streamingContent = content;
                } else {
                    headers.setContentEncoding(encoding.getName());
                    StreamingContent streamingContent2 = new HttpEncodingStreamingContent(content, encoding);
                    contentLength = AbstractHttpContent.computeLength(content);
                    streamingContent = streamingContent2;
                }
                if (contentLength != -1) {
                    headers.setContentLength(Long.valueOf(contentLength));
                }
            }
            writer.write("--");
            writer.write(boundary);
            writer.write(NEWLINE);
            HttpHeaders.serializeHeadersForMultipartRequests(headers, null, null, writer);
            if (streamingContent != 0) {
                writer.write(NEWLINE);
                writer.flush();
                streamingContent.writeTo(out);
            }
            writer.write(NEWLINE);
        }
        writer.write("--");
        writer.write(boundary);
        writer.write("--");
        writer.write(NEWLINE);
        writer.flush();
    }

    public boolean retrySupported() {
        Iterator i$ = this.parts.iterator();
        while (i$.hasNext()) {
            if (!((Part) i$.next()).content.retrySupported()) {
                return false;
            }
        }
        return true;
    }

    public MultipartContent setMediaType(HttpMediaType mediaType) {
        super.setMediaType(mediaType);
        return this;
    }

    public final Collection<Part> getParts() {
        return Collections.unmodifiableCollection(this.parts);
    }

    public MultipartContent addPart(Part part) {
        this.parts.add(Preconditions.checkNotNull(part));
        return this;
    }

    public MultipartContent setParts(Collection<Part> parts2) {
        this.parts = new ArrayList<>(parts2);
        return this;
    }

    public MultipartContent setContentParts(Collection<? extends HttpContent> contentParts) {
        this.parts = new ArrayList<>(contentParts.size());
        for (HttpContent contentPart : contentParts) {
            addPart(new Part(contentPart));
        }
        return this;
    }

    public final String getBoundary() {
        return getMediaType().getParameter("boundary");
    }

    public MultipartContent setBoundary(String boundary) {
        getMediaType().setParameter("boundary", (String) Preconditions.checkNotNull(boundary));
        return this;
    }
}
