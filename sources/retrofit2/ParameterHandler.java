package retrofit2;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.RequestBody;

abstract class ParameterHandler<T> {

    static final class Body<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;

        Body(Converter<T, RequestBody> converter2) {
            this.converter = converter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) {
            if (value == null) {
                throw new IllegalArgumentException("Body parameter value must not be null.");
            }
            try {
                builder.setBody((RequestBody) this.converter.convert(value));
            } catch (IOException e) {
                throw new RuntimeException("Unable to convert " + value + " to RequestBody", e);
            }
        }
    }

    static final class Field<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Field(String name2, Converter<T, String> valueConverter2, boolean encoded2) {
            this.name = (String) Utils.checkNotNull(name2, "name == null");
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                String fieldValue = (String) this.valueConverter.convert(value);
                if (fieldValue != null) {
                    builder.addFormField(this.name, fieldValue, this.encoded);
                }
            }
        }
    }

    static final class FieldMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Converter<T, String> valueConverter;

        FieldMap(Converter<T, String> valueConverter2, boolean encoded2) {
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException("Field map was null.");
            }
            for (Entry<String, T> entry : value.entrySet()) {
                String entryKey = (String) entry.getKey();
                if (entryKey == null) {
                    throw new IllegalArgumentException("Field map contained null key.");
                }
                T entryValue = entry.getValue();
                if (entryValue == null) {
                    throw new IllegalArgumentException("Field map contained null value for key '" + entryKey + "'.");
                }
                String fieldEntry = (String) this.valueConverter.convert(entryValue);
                if (fieldEntry == null) {
                    throw new IllegalArgumentException("Field map value '" + entryValue + "' converted to null by " + this.valueConverter.getClass().getName() + " for key '" + entryKey + "'.");
                }
                builder.addFormField(entryKey, fieldEntry, this.encoded);
            }
        }
    }

    static final class Header<T> extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        Header(String name2, Converter<T, String> valueConverter2) {
            this.name = (String) Utils.checkNotNull(name2, "name == null");
            this.valueConverter = valueConverter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                String headerValue = (String) this.valueConverter.convert(value);
                if (headerValue != null) {
                    builder.addHeader(this.name, headerValue);
                }
            }
        }
    }

    static final class HeaderMap<T> extends ParameterHandler<Map<String, T>> {
        private final Converter<T, String> valueConverter;

        HeaderMap(Converter<T, String> valueConverter2) {
            this.valueConverter = valueConverter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException("Header map was null.");
            }
            for (Entry<String, T> entry : value.entrySet()) {
                String headerName = (String) entry.getKey();
                if (headerName == null) {
                    throw new IllegalArgumentException("Header map contained null key.");
                }
                T headerValue = entry.getValue();
                if (headerValue == null) {
                    throw new IllegalArgumentException("Header map contained null value for key '" + headerName + "'.");
                }
                builder.addHeader(headerName, (String) this.valueConverter.convert(headerValue));
            }
        }
    }

    static final class Part<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final Headers headers;

        Part(Headers headers2, Converter<T, RequestBody> converter2) {
            this.headers = headers2;
            this.converter = converter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) {
            if (value != null) {
                try {
                    builder.addPart(this.headers, (RequestBody) this.converter.convert(value));
                } catch (IOException e) {
                    throw new RuntimeException("Unable to convert " + value + " to RequestBody", e);
                }
            }
        }
    }

    static final class PartMap<T> extends ParameterHandler<Map<String, T>> {
        private final String transferEncoding;
        private final Converter<T, RequestBody> valueConverter;

        PartMap(Converter<T, RequestBody> valueConverter2, String transferEncoding2) {
            this.valueConverter = valueConverter2;
            this.transferEncoding = transferEncoding2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException("Part map was null.");
            }
            for (Entry<String, T> entry : value.entrySet()) {
                String entryKey = (String) entry.getKey();
                if (entryKey == null) {
                    throw new IllegalArgumentException("Part map contained null key.");
                }
                T entryValue = entry.getValue();
                if (entryValue == null) {
                    throw new IllegalArgumentException("Part map contained null value for key '" + entryKey + "'.");
                }
                builder.addPart(Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"" + entryKey + "\"", "Content-Transfer-Encoding", this.transferEncoding), (RequestBody) this.valueConverter.convert(entryValue));
            }
        }
    }

    static final class Path<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Path(String name2, Converter<T, String> valueConverter2, boolean encoded2) {
            this.name = (String) Utils.checkNotNull(name2, "name == null");
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException("Path parameter \"" + this.name + "\" value must not be null.");
            }
            builder.addPathParam(this.name, (String) this.valueConverter.convert(value), this.encoded);
        }
    }

    static final class Query<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Query(String name2, Converter<T, String> valueConverter2, boolean encoded2) {
            this.name = (String) Utils.checkNotNull(name2, "name == null");
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                String queryValue = (String) this.valueConverter.convert(value);
                if (queryValue != null) {
                    builder.addQueryParam(this.name, queryValue, this.encoded);
                }
            }
        }
    }

    static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Converter<T, String> valueConverter;

        QueryMap(Converter<T, String> valueConverter2, boolean encoded2) {
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value == null) {
                throw new IllegalArgumentException("Query map was null.");
            }
            for (Entry<String, T> entry : value.entrySet()) {
                String entryKey = (String) entry.getKey();
                if (entryKey == null) {
                    throw new IllegalArgumentException("Query map contained null key.");
                }
                T entryValue = entry.getValue();
                if (entryValue == null) {
                    throw new IllegalArgumentException("Query map contained null value for key '" + entryKey + "'.");
                }
                String convertedEntryValue = (String) this.valueConverter.convert(entryValue);
                if (convertedEntryValue == null) {
                    throw new IllegalArgumentException("Query map value '" + entryValue + "' converted to null by " + this.valueConverter.getClass().getName() + " for key '" + entryKey + "'.");
                }
                builder.addQueryParam(entryKey, convertedEntryValue, this.encoded);
            }
        }
    }

    static final class QueryName<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Converter<T, String> nameConverter;

        QueryName(Converter<T, String> nameConverter2, boolean encoded2) {
            this.nameConverter = nameConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                builder.addQueryParam((String) this.nameConverter.convert(value), null, this.encoded);
            }
        }
    }

    static final class RawPart extends ParameterHandler<okhttp3.MultipartBody.Part> {
        static final RawPart INSTANCE = new RawPart();

        private RawPart() {
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable okhttp3.MultipartBody.Part value) {
            if (value != null) {
                builder.addPart(value);
            }
        }
    }

    static final class RelativeUrl extends ParameterHandler<Object> {
        RelativeUrl() {
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Object value) {
            Utils.checkNotNull(value, "@Url parameter is null.");
            builder.setRelativeUrl(value);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException;

    ParameterHandler() {
    }

    /* access modifiers changed from: 0000 */
    public final ParameterHandler<Iterable<T>> iterable() {
        return new ParameterHandler<Iterable<T>>() {
            /* access modifiers changed from: 0000 */
            public void apply(RequestBuilder builder, @Nullable Iterable<T> values) throws IOException {
                if (values != null) {
                    for (T value : values) {
                        ParameterHandler.this.apply(builder, value);
                    }
                }
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public final ParameterHandler<Object> array() {
        return new ParameterHandler<Object>() {
            /* access modifiers changed from: 0000 */
            public void apply(RequestBuilder builder, @Nullable Object values) throws IOException {
                if (values != null) {
                    int size = Array.getLength(values);
                    for (int i = 0; i < size; i++) {
                        ParameterHandler.this.apply(builder, Array.get(values, i));
                    }
                }
            }
        };
    }
}
