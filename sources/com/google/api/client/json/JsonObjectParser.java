package com.google.api.client.json;

import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonObjectParser implements ObjectParser {
    private final JsonFactory jsonFactory;
    private final Set<String> wrapperKeys;

    public static class Builder {
        final JsonFactory jsonFactory;
        Collection<String> wrapperKeys = Sets.newHashSet();

        public Builder(JsonFactory jsonFactory2) {
            this.jsonFactory = (JsonFactory) Preconditions.checkNotNull(jsonFactory2);
        }

        public JsonObjectParser build() {
            return new JsonObjectParser(this);
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final Collection<String> getWrapperKeys() {
            return this.wrapperKeys;
        }

        public Builder setWrapperKeys(Collection<String> wrapperKeys2) {
            this.wrapperKeys = wrapperKeys2;
            return this;
        }
    }

    public JsonObjectParser(JsonFactory jsonFactory2) {
        this(new Builder(jsonFactory2));
    }

    protected JsonObjectParser(Builder builder) {
        this.jsonFactory = builder.jsonFactory;
        this.wrapperKeys = new HashSet(builder.wrapperKeys);
    }

    public <T> T parseAndClose(InputStream in, Charset charset, Class<T> dataClass) throws IOException {
        return parseAndClose(in, charset, (Type) dataClass);
    }

    public Object parseAndClose(InputStream in, Charset charset, Type dataType) throws IOException {
        JsonParser parser = this.jsonFactory.createJsonParser(in, charset);
        initializeParser(parser);
        return parser.parse(dataType, true);
    }

    public <T> T parseAndClose(Reader reader, Class<T> dataClass) throws IOException {
        return parseAndClose(reader, (Type) dataClass);
    }

    public Object parseAndClose(Reader reader, Type dataType) throws IOException {
        JsonParser parser = this.jsonFactory.createJsonParser(reader);
        initializeParser(parser);
        return parser.parse(dataType, true);
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public Set<String> getWrapperKeys() {
        return Collections.unmodifiableSet(this.wrapperKeys);
    }

    /* JADX INFO: finally extract failed */
    private void initializeParser(JsonParser parser) throws IOException {
        boolean z = true;
        if (!this.wrapperKeys.isEmpty()) {
            try {
                if (parser.skipToKey(this.wrapperKeys) == null || parser.getCurrentToken() == JsonToken.END_OBJECT) {
                    z = false;
                }
                Preconditions.checkArgument(z, "wrapper key(s) not found: %s", this.wrapperKeys);
                if (0 != 0) {
                    parser.close();
                }
            } catch (Throwable th) {
                if (1 != 0) {
                    parser.close();
                }
                throw th;
            }
        }
    }
}
