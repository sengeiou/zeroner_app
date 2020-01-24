package okhttp3;

import com.google.common.base.Ascii;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import kotlin.text.Typography;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

public final class MultipartBody extends RequestBody {
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    private static final byte[] COLONSPACE = {58, 32};
    private static final byte[] CRLF = {Ascii.CR, 10};
    private static final byte[] DASHDASH = {Framer.STDIN_FRAME_PREFIX, Framer.STDIN_FRAME_PREFIX};
    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    private final ByteString boundary;
    private long contentLength = -1;
    private final MediaType contentType;
    private final MediaType originalType;
    private final List<Part> parts;

    public static final class Part {
        final RequestBody body;
        @Nullable
        final Headers headers;

        public static Part create(RequestBody body2) {
            return create(null, body2);
        }

        public static Part create(@Nullable Headers headers2, RequestBody body2) {
            if (body2 == null) {
                throw new NullPointerException("body == null");
            } else if (headers2 != null && headers2.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            } else if (headers2 == null || headers2.get("Content-Length") == null) {
                return new Part(headers2, body2);
            } else {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
        }

        public static Part createFormData(String name, String value) {
            return createFormData(name, null, RequestBody.create((MediaType) null, value));
        }

        public static Part createFormData(String name, @Nullable String filename, RequestBody body2) {
            if (name == null) {
                throw new NullPointerException("name == null");
            }
            StringBuilder disposition = new StringBuilder("form-data; name=");
            MultipartBody.appendQuotedString(disposition, name);
            if (filename != null) {
                disposition.append("; filename=");
                MultipartBody.appendQuotedString(disposition, filename);
            }
            return create(Headers.of(HttpHeaders.CONTENT_DISPOSITION, disposition.toString()), body2);
        }

        private Part(@Nullable Headers headers2, RequestBody body2) {
            this.headers = headers2;
            this.body = body2;
        }

        @Nullable
        public Headers headers() {
            return this.headers;
        }

        public RequestBody body() {
            return this.body;
        }
    }

    public static final class Builder {
        private final ByteString boundary;
        private final List<Part> parts;
        private MediaType type;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        public Builder(String boundary2) {
            this.type = MultipartBody.MIXED;
            this.parts = new ArrayList();
            this.boundary = ByteString.encodeUtf8(boundary2);
        }

        public Builder setType(MediaType type2) {
            if (type2 == null) {
                throw new NullPointerException("type == null");
            } else if (!type2.type().equals("multipart")) {
                throw new IllegalArgumentException("multipart != " + type2);
            } else {
                this.type = type2;
                return this;
            }
        }

        public Builder addPart(RequestBody body) {
            return addPart(Part.create(body));
        }

        public Builder addPart(@Nullable Headers headers, RequestBody body) {
            return addPart(Part.create(headers, body));
        }

        public Builder addFormDataPart(String name, String value) {
            return addPart(Part.createFormData(name, value));
        }

        public Builder addFormDataPart(String name, @Nullable String filename, RequestBody body) {
            return addPart(Part.createFormData(name, filename, body));
        }

        public Builder addPart(Part part) {
            if (part == null) {
                throw new NullPointerException("part == null");
            }
            this.parts.add(part);
            return this;
        }

        public MultipartBody build() {
            if (!this.parts.isEmpty()) {
                return new MultipartBody(this.boundary, this.type, this.parts);
            }
            throw new IllegalStateException("Multipart body must have at least one part.");
        }
    }

    MultipartBody(ByteString boundary2, MediaType type, List<Part> parts2) {
        this.boundary = boundary2;
        this.originalType = type;
        this.contentType = MediaType.parse(type + "; boundary=" + boundary2.utf8());
        this.parts = Util.immutableList(parts2);
    }

    public MediaType type() {
        return this.originalType;
    }

    public String boundary() {
        return this.boundary.utf8();
    }

    public int size() {
        return this.parts.size();
    }

    public List<Part> parts() {
        return this.parts;
    }

    public Part part(int index) {
        return (Part) this.parts.get(index);
    }

    public MediaType contentType() {
        return this.contentType;
    }

    public long contentLength() throws IOException {
        long result = this.contentLength;
        if (result != -1) {
            return result;
        }
        long result2 = writeOrCountBytes(null, true);
        this.contentLength = result2;
        return result2;
    }

    public void writeTo(BufferedSink sink) throws IOException {
        writeOrCountBytes(sink, false);
    }

    /* JADX WARNING: type inference failed for: r19v1 */
    /* JADX WARNING: type inference failed for: r0v1, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v3, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v4, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v5, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v7, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v9, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v10, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v11, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v12, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v13, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v14, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v15, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v16, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r3v2, types: [okio.Buffer] */
    /* JADX WARNING: type inference failed for: r19v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 14 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long writeOrCountBytes(@javax.annotation.Nullable okio.BufferedSink r19, boolean r20) throws java.io.IOException {
        /*
            r18 = this;
            r4 = 0
            r3 = 0
            if (r20 == 0) goto L_0x000c
            okio.Buffer r3 = new okio.Buffer
            r3.<init>()
            r19 = r3
        L_0x000c:
            r12 = 0
            r0 = r18
            java.util.List<okhttp3.MultipartBody$Part> r15 = r0.parts
            int r14 = r15.size()
        L_0x0015:
            if (r12 >= r14) goto L_0x00c0
            r0 = r18
            java.util.List<okhttp3.MultipartBody$Part> r15 = r0.parts
            java.lang.Object r13 = r15.get(r12)
            okhttp3.MultipartBody$Part r13 = (okhttp3.MultipartBody.Part) r13
            okhttp3.Headers r11 = r13.headers
            okhttp3.RequestBody r2 = r13.body
            byte[] r15 = DASHDASH
            r0 = r19
            r0.write(r15)
            r0 = r18
            okio.ByteString r15 = r0.boundary
            r0 = r19
            r0.write(r15)
            byte[] r15 = CRLF
            r0 = r19
            r0.write(r15)
            if (r11 == 0) goto L_0x0065
            r9 = 0
            int r10 = r11.size()
        L_0x0043:
            if (r9 >= r10) goto L_0x0065
            java.lang.String r15 = r11.name(r9)
            r0 = r19
            okio.BufferedSink r15 = r0.writeUtf8(r15)
            byte[] r16 = COLONSPACE
            okio.BufferedSink r15 = r15.write(r16)
            java.lang.String r16 = r11.value(r9)
            okio.BufferedSink r15 = r15.writeUtf8(r16)
            byte[] r16 = CRLF
            r15.write(r16)
            int r9 = r9 + 1
            goto L_0x0043
        L_0x0065:
            okhttp3.MediaType r8 = r2.contentType()
            if (r8 == 0) goto L_0x0081
            java.lang.String r15 = "Content-Type: "
            r0 = r19
            okio.BufferedSink r15 = r0.writeUtf8(r15)
            java.lang.String r16 = r8.toString()
            okio.BufferedSink r15 = r15.writeUtf8(r16)
            byte[] r16 = CRLF
            r15.write(r16)
        L_0x0081:
            long r6 = r2.contentLength()
            r16 = -1
            int r15 = (r6 > r16 ? 1 : (r6 == r16 ? 0 : -1))
            if (r15 == 0) goto L_0x00b2
            java.lang.String r15 = "Content-Length: "
            r0 = r19
            okio.BufferedSink r15 = r0.writeUtf8(r15)
            okio.BufferedSink r15 = r15.writeDecimalLong(r6)
            byte[] r16 = CRLF
            r15.write(r16)
        L_0x009d:
            byte[] r15 = CRLF
            r0 = r19
            r0.write(r15)
            if (r20 == 0) goto L_0x00ba
            long r4 = r4 + r6
        L_0x00a7:
            byte[] r15 = CRLF
            r0 = r19
            r0.write(r15)
            int r12 = r12 + 1
            goto L_0x0015
        L_0x00b2:
            if (r20 == 0) goto L_0x009d
            r3.clear()
            r16 = -1
        L_0x00b9:
            return r16
        L_0x00ba:
            r0 = r19
            r2.writeTo(r0)
            goto L_0x00a7
        L_0x00c0:
            byte[] r15 = DASHDASH
            r0 = r19
            r0.write(r15)
            r0 = r18
            okio.ByteString r15 = r0.boundary
            r0 = r19
            r0.write(r15)
            byte[] r15 = DASHDASH
            r0 = r19
            r0.write(r15)
            byte[] r15 = CRLF
            r0 = r19
            r0.write(r15)
            if (r20 == 0) goto L_0x00e9
            long r16 = r3.size()
            long r4 = r4 + r16
            r3.clear()
        L_0x00e9:
            r16 = r4
            goto L_0x00b9
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.MultipartBody.writeOrCountBytes(okio.BufferedSink, boolean):long");
    }

    static StringBuilder appendQuotedString(StringBuilder target, String key) {
        target.append(Typography.quote);
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char ch = key.charAt(i);
            switch (ch) {
                case 10:
                    target.append("%0A");
                    break;
                case 13:
                    target.append("%0D");
                    break;
                case '\"':
                    target.append("%22");
                    break;
                default:
                    target.append(ch);
                    break;
            }
        }
        target.append(Typography.quote);
        return target;
    }
}
