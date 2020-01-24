package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
    static final boolean DEFAULT_ESCAPE_HTML = true;
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    static final boolean DEFAULT_LENIENT = false;
    static final boolean DEFAULT_PRETTY_PRINT = false;
    static final boolean DEFAULT_SERIALIZE_NULLS = false;
    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private static final TypeToken<?> NULL_KEY_SURROGATE = TypeToken.get(Object.class);
    private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls;
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final List<TypeAdapterFactory> factories;
    private final FieldNamingStrategy fieldNamingStrategy;
    private final boolean generateNonExecutableJson;
    private final boolean htmlSafe;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final boolean lenient;
    private final boolean prettyPrinting;
    private final boolean serializeNulls;
    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache;

    static class FutureTypeAdapter<T> extends TypeAdapter<T> {
        private TypeAdapter<T> delegate;

        FutureTypeAdapter() {
        }

        public void setDelegate(TypeAdapter<T> typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = typeAdapter;
        }

        public T read(JsonReader in) throws IOException {
            if (this.delegate != null) {
                return this.delegate.read(in);
            }
            throw new IllegalStateException();
        }

        public void write(JsonWriter out, T value) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(out, value);
        }
    }

    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }

    Gson(Excluder excluder2, FieldNamingStrategy fieldNamingStrategy2, Map<Type, InstanceCreator<?>> instanceCreators, boolean serializeNulls2, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe2, boolean prettyPrinting2, boolean lenient2, boolean serializeSpecialFloatingPointValues, LongSerializationPolicy longSerializationPolicy, List<TypeAdapterFactory> typeAdapterFactories) {
        this.calls = new ThreadLocal<>();
        this.typeTokenCache = new ConcurrentHashMap();
        this.constructorConstructor = new ConstructorConstructor(instanceCreators);
        this.excluder = excluder2;
        this.fieldNamingStrategy = fieldNamingStrategy2;
        this.serializeNulls = serializeNulls2;
        this.generateNonExecutableJson = generateNonExecutableGson;
        this.htmlSafe = htmlSafe2;
        this.prettyPrinting = prettyPrinting2;
        this.lenient = lenient2;
        List<TypeAdapterFactory> factories2 = new ArrayList<>();
        factories2.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        factories2.add(ObjectTypeAdapter.FACTORY);
        factories2.add(excluder2);
        factories2.addAll(typeAdapterFactories);
        factories2.add(TypeAdapters.STRING_FACTORY);
        factories2.add(TypeAdapters.INTEGER_FACTORY);
        factories2.add(TypeAdapters.BOOLEAN_FACTORY);
        factories2.add(TypeAdapters.BYTE_FACTORY);
        factories2.add(TypeAdapters.SHORT_FACTORY);
        TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
        factories2.add(TypeAdapters.newFactory(Long.TYPE, Long.class, longAdapter));
        factories2.add(TypeAdapters.newFactory(Double.TYPE, Double.class, doubleAdapter(serializeSpecialFloatingPointValues)));
        factories2.add(TypeAdapters.newFactory(Float.TYPE, Float.class, floatAdapter(serializeSpecialFloatingPointValues)));
        factories2.add(TypeAdapters.NUMBER_FACTORY);
        factories2.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        factories2.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        factories2.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
        factories2.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
        factories2.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        factories2.add(TypeAdapters.CHARACTER_FACTORY);
        factories2.add(TypeAdapters.STRING_BUILDER_FACTORY);
        factories2.add(TypeAdapters.STRING_BUFFER_FACTORY);
        factories2.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        factories2.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        factories2.add(TypeAdapters.URL_FACTORY);
        factories2.add(TypeAdapters.URI_FACTORY);
        factories2.add(TypeAdapters.UUID_FACTORY);
        factories2.add(TypeAdapters.CURRENCY_FACTORY);
        factories2.add(TypeAdapters.LOCALE_FACTORY);
        factories2.add(TypeAdapters.INET_ADDRESS_FACTORY);
        factories2.add(TypeAdapters.BIT_SET_FACTORY);
        factories2.add(DateTypeAdapter.FACTORY);
        factories2.add(TypeAdapters.CALENDAR_FACTORY);
        factories2.add(TimeTypeAdapter.FACTORY);
        factories2.add(SqlDateTypeAdapter.FACTORY);
        factories2.add(TypeAdapters.TIMESTAMP_FACTORY);
        factories2.add(ArrayTypeAdapter.FACTORY);
        factories2.add(TypeAdapters.CLASS_FACTORY);
        factories2.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        factories2.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
        this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
        factories2.add(this.jsonAdapterFactory);
        factories2.add(TypeAdapters.ENUM_FACTORY);
        factories2.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy2, excluder2, this.jsonAdapterFactory));
        this.factories = Collections.unmodifiableList(factories2);
    }

    public Excluder excluder() {
        return this.excluder;
    }

    public FieldNamingStrategy fieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }

    public boolean serializeNulls() {
        return this.serializeNulls;
    }

    public boolean htmlSafe() {
        return this.htmlSafe;
    }

    private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
        if (serializeSpecialFloatingPointValues) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>() {
            public Double read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return Double.valueOf(in.nextDouble());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                Gson.checkValidFloatingPoint(value.doubleValue());
                out.value(value);
            }
        };
    }

    private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
        if (serializeSpecialFloatingPointValues) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>() {
            public Float read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return Float.valueOf((float) in.nextDouble());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                Gson.checkValidFloatingPoint((double) value.floatValue());
                out.value(value);
            }
        };
    }

    static void checkValidFloatingPoint(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>() {
            public Number read(JsonReader in) throws IOException {
                if (in.peek() != JsonToken.NULL) {
                    return Long.valueOf(in.nextLong());
                }
                in.nextNull();
                return null;
            }

            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.toString());
                }
            }
        };
    }

    private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
        return new TypeAdapter<AtomicLong>() {
            public void write(JsonWriter out, AtomicLong value) throws IOException {
                longAdapter.write(out, Long.valueOf(value.get()));
            }

            public AtomicLong read(JsonReader in) throws IOException {
                return new AtomicLong(((Number) longAdapter.read(in)).longValue());
            }
        }.nullSafe();
    }

    private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
        return new TypeAdapter<AtomicLongArray>() {
            public void write(JsonWriter out, AtomicLongArray value) throws IOException {
                out.beginArray();
                int length = value.length();
                for (int i = 0; i < length; i++) {
                    longAdapter.write(out, Long.valueOf(value.get(i)));
                }
                out.endArray();
            }

            public AtomicLongArray read(JsonReader in) throws IOException {
                List<Long> list = new ArrayList<>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(Long.valueOf(((Number) longAdapter.read(in)).longValue()));
                }
                in.endArray();
                int length = list.size();
                AtomicLongArray array = new AtomicLongArray(length);
                for (int i = 0; i < length; i++) {
                    array.set(i, ((Long) list.get(i)).longValue());
                }
                return array;
            }
        }.nullSafe();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.gson.reflect.TypeToken<T>, code=com.google.gson.reflect.TypeToken, for r11v0, types: [com.google.gson.reflect.TypeToken, com.google.gson.reflect.TypeToken<T>, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> com.google.gson.TypeAdapter<T> getAdapter(com.google.gson.reflect.TypeToken r11) {
        /*
            r10 = this;
            java.util.Map<com.google.gson.reflect.TypeToken<?>, com.google.gson.TypeAdapter<?>> r8 = r10.typeTokenCache
            if (r11 != 0) goto L_0x000f
            com.google.gson.reflect.TypeToken<?> r7 = NULL_KEY_SURROGATE
        L_0x0006:
            java.lang.Object r0 = r8.get(r7)
            com.google.gson.TypeAdapter r0 = (com.google.gson.TypeAdapter) r0
            if (r0 == 0) goto L_0x0011
        L_0x000e:
            return r0
        L_0x000f:
            r7 = r11
            goto L_0x0006
        L_0x0011:
            java.lang.ThreadLocal<java.util.Map<com.google.gson.reflect.TypeToken<?>, com.google.gson.Gson$FutureTypeAdapter<?>>> r7 = r10.calls
            java.lang.Object r6 = r7.get()
            java.util.Map r6 = (java.util.Map) r6
            r5 = 0
            if (r6 != 0) goto L_0x0027
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            java.lang.ThreadLocal<java.util.Map<com.google.gson.reflect.TypeToken<?>, com.google.gson.Gson$FutureTypeAdapter<?>>> r7 = r10.calls
            r7.set(r6)
            r5 = 1
        L_0x0027:
            java.lang.Object r4 = r6.get(r11)
            com.google.gson.Gson$FutureTypeAdapter r4 = (com.google.gson.Gson.FutureTypeAdapter) r4
            if (r4 == 0) goto L_0x0031
            r0 = r4
            goto L_0x000e
        L_0x0031:
            com.google.gson.Gson$FutureTypeAdapter r1 = new com.google.gson.Gson$FutureTypeAdapter     // Catch:{ all -> 0x007f }
            r1.<init>()     // Catch:{ all -> 0x007f }
            r6.put(r11, r1)     // Catch:{ all -> 0x007f }
            java.util.List<com.google.gson.TypeAdapterFactory> r7 = r10.factories     // Catch:{ all -> 0x007f }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ all -> 0x007f }
        L_0x003f:
            boolean r8 = r7.hasNext()     // Catch:{ all -> 0x007f }
            if (r8 == 0) goto L_0x0065
            java.lang.Object r3 = r7.next()     // Catch:{ all -> 0x007f }
            com.google.gson.TypeAdapterFactory r3 = (com.google.gson.TypeAdapterFactory) r3     // Catch:{ all -> 0x007f }
            com.google.gson.TypeAdapter r2 = r3.create(r10, r11)     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x003f
            r1.setDelegate(r2)     // Catch:{ all -> 0x007f }
            java.util.Map<com.google.gson.reflect.TypeToken<?>, com.google.gson.TypeAdapter<?>> r7 = r10.typeTokenCache     // Catch:{ all -> 0x007f }
            r7.put(r11, r2)     // Catch:{ all -> 0x007f }
            r6.remove(r11)
            if (r5 == 0) goto L_0x0063
            java.lang.ThreadLocal<java.util.Map<com.google.gson.reflect.TypeToken<?>, com.google.gson.Gson$FutureTypeAdapter<?>>> r7 = r10.calls
            r7.remove()
        L_0x0063:
            r0 = r2
            goto L_0x000e
        L_0x0065:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x007f }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x007f }
            r8.<init>()     // Catch:{ all -> 0x007f }
            java.lang.String r9 = "GSON cannot handle "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x007f }
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ all -> 0x007f }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x007f }
            r7.<init>(r8)     // Catch:{ all -> 0x007f }
            throw r7     // Catch:{ all -> 0x007f }
        L_0x007f:
            r7 = move-exception
            r6.remove(r11)
            if (r5 == 0) goto L_0x008a
            java.lang.ThreadLocal<java.util.Map<com.google.gson.reflect.TypeToken<?>, com.google.gson.Gson$FutureTypeAdapter<?>>> r8 = r10.calls
            r8.remove()
        L_0x008a:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.Gson.getAdapter(com.google.gson.reflect.TypeToken):com.google.gson.TypeAdapter");
    }

    public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type) {
        if (!this.factories.contains(skipPast)) {
            skipPast = this.jsonAdapterFactory;
        }
        boolean skipPastFound = false;
        for (TypeAdapterFactory factory : this.factories) {
            if (skipPastFound) {
                TypeAdapter<T> candidate = factory.create(this, type);
                if (candidate != null) {
                    return candidate;
                }
            } else if (factory == skipPast) {
                skipPastFound = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + type);
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> type) {
        return getAdapter(TypeToken.get(type));
    }

    public JsonElement toJsonTree(Object src) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        return toJsonTree(src, src.getClass());
    }

    public JsonElement toJsonTree(Object src, Type typeOfSrc) {
        JsonTreeWriter writer = new JsonTreeWriter();
        toJson(src, typeOfSrc, (JsonWriter) writer);
        return writer.get();
    }

    public String toJson(Object src) {
        if (src == null) {
            return toJson((JsonElement) JsonNull.INSTANCE);
        }
        return toJson(src, (Type) src.getClass());
    }

    public String toJson(Object src, Type typeOfSrc) {
        StringWriter writer = new StringWriter();
        toJson(src, typeOfSrc, (Appendable) writer);
        return writer.toString();
    }

    public void toJson(Object src, Appendable writer) throws JsonIOException {
        if (src != null) {
            toJson(src, (Type) src.getClass(), writer);
        } else {
            toJson((JsonElement) JsonNull.INSTANCE, writer);
        }
    }

    public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
        try {
            toJson(src, typeOfSrc, newJsonWriter(Streams.writerForAppendable(writer)));
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }

    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
        boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            adapter.write(writer, src);
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        } catch (Throwable th) {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
            throw th;
        }
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter writer = new StringWriter();
        toJson(jsonElement, (Appendable) writer);
        return writer.toString();
    }

    public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
        try {
            toJson(jsonElement, newJsonWriter(Streams.writerForAppendable(writer)));
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }

    public JsonWriter newJsonWriter(Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(JSON_NON_EXECUTABLE_PREFIX);
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }

    public JsonReader newJsonReader(Reader reader) {
        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(this.lenient);
        return jsonReader;
    }

    public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, writer);
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        } catch (Throwable th) {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
            throw th;
        }
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return Primitives.wrap(classOfT).cast(fromJson(json, (Type) classOfT));
    }

    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return fromJson((Reader) new StringReader(json), typeOfT);
    }

    public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        JsonReader jsonReader = newJsonReader(json);
        Object object = fromJson(jsonReader, (Type) classOfT);
        assertFullConsumption(object, jsonReader);
        return Primitives.wrap(classOfT).cast(object);
    }

    public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        JsonReader jsonReader = newJsonReader(json);
        T object = fromJson(jsonReader, typeOfT);
        assertFullConsumption(object, jsonReader);
        return object;
    }

    private static void assertFullConsumption(Object obj, JsonReader reader) {
        if (obj != null) {
            try {
                if (reader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            } catch (MalformedJsonException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IOException e2) {
                throw new JsonIOException((Throwable) e2);
            }
        }
    }

    public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        boolean isEmpty = true;
        boolean oldLenient = reader.isLenient();
        reader.setLenient(true);
        try {
            reader.peek();
            isEmpty = false;
            T object = getAdapter(TypeToken.get(typeOfT)).read(reader);
            reader.setLenient(oldLenient);
            return object;
        } catch (EOFException e) {
            if (isEmpty) {
                reader.setLenient(oldLenient);
                return null;
            }
            throw new JsonSyntaxException((Throwable) e);
        } catch (IllegalStateException e2) {
            throw new JsonSyntaxException((Throwable) e2);
        } catch (IOException e3) {
            throw new JsonSyntaxException((Throwable) e3);
        } catch (Throwable th) {
            reader.setLenient(oldLenient);
            throw th;
        }
    }

    public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        return Primitives.wrap(classOfT).cast(fromJson(json, (Type) classOfT));
    }

    public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return fromJson((JsonReader) new JsonTreeReader(json), typeOfT);
    }

    public String toString() {
        return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }
}
