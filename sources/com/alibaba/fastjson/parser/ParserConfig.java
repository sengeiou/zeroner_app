package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ArrayCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.NumberCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class ParserConfig {
    private static long[] denyList = {-7600952144447537354L, -4082057040235125754L, -2364987994247679115L, -676156662527871184L, -254670111376247151L, 1502845958873959152L, 4147696707147271408L, 5347909877633654828L, 5751393439502795295L, 7702607466162283393L};
    public static ParserConfig global = new ParserConfig();
    public boolean autoTypeSupport;
    public ClassLoader defaultClassLoader;
    private final IdentityHashMap<ObjectDeserializer> deserializers = new IdentityHashMap<>(1024);
    public PropertyNamingStrategy propertyNamingStrategy;
    public final SymbolTable symbolTable = new SymbolTable(16384);

    public static ParserConfig getGlobalInstance() {
        return global;
    }

    public ParserConfig() {
        this.deserializers.put(SimpleDateFormat.class, MiscCodec.instance);
        this.deserializers.put(Date.class, DateCodec.instance);
        this.deserializers.put(Calendar.class, DateCodec.instance);
        this.deserializers.put(Map.class, MapDeserializer.instance);
        this.deserializers.put(HashMap.class, MapDeserializer.instance);
        this.deserializers.put(LinkedHashMap.class, MapDeserializer.instance);
        this.deserializers.put(TreeMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
        this.deserializers.put(Collection.class, CollectionCodec.instance);
        this.deserializers.put(List.class, CollectionCodec.instance);
        this.deserializers.put(ArrayList.class, CollectionCodec.instance);
        this.deserializers.put(Object.class, JavaObjectDeserializer.instance);
        this.deserializers.put(String.class, StringCodec.instance);
        this.deserializers.put(Character.TYPE, MiscCodec.instance);
        this.deserializers.put(Character.class, MiscCodec.instance);
        this.deserializers.put(Byte.TYPE, NumberCodec.instance);
        this.deserializers.put(Byte.class, NumberCodec.instance);
        this.deserializers.put(Short.TYPE, NumberCodec.instance);
        this.deserializers.put(Short.class, NumberCodec.instance);
        this.deserializers.put(Integer.TYPE, IntegerCodec.instance);
        this.deserializers.put(Integer.class, IntegerCodec.instance);
        this.deserializers.put(Long.TYPE, IntegerCodec.instance);
        this.deserializers.put(Long.class, IntegerCodec.instance);
        this.deserializers.put(BigInteger.class, BigDecimalCodec.instance);
        this.deserializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.deserializers.put(Float.TYPE, NumberCodec.instance);
        this.deserializers.put(Float.class, NumberCodec.instance);
        this.deserializers.put(Double.TYPE, NumberCodec.instance);
        this.deserializers.put(Double.class, NumberCodec.instance);
        this.deserializers.put(Boolean.TYPE, BooleanCodec.instance);
        this.deserializers.put(Boolean.class, BooleanCodec.instance);
        this.deserializers.put(Class.class, MiscCodec.instance);
        this.deserializers.put(char[].class, ArrayCodec.instance);
        this.deserializers.put(Object[].class, ArrayCodec.instance);
        this.deserializers.put(UUID.class, MiscCodec.instance);
        this.deserializers.put(TimeZone.class, MiscCodec.instance);
        this.deserializers.put(Locale.class, MiscCodec.instance);
        this.deserializers.put(Currency.class, MiscCodec.instance);
        this.deserializers.put(URI.class, MiscCodec.instance);
        this.deserializers.put(URL.class, MiscCodec.instance);
        this.deserializers.put(Pattern.class, MiscCodec.instance);
        this.deserializers.put(Charset.class, MiscCodec.instance);
        this.deserializers.put(Number.class, NumberCodec.instance);
        this.deserializers.put(StackTraceElement.class, MiscCodec.instance);
        this.deserializers.put(Serializable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Cloneable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Comparable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Closeable.class, JavaObjectDeserializer.instance);
    }

    public ObjectDeserializer getDeserializer(Type type) {
        ObjectDeserializer derializer = (ObjectDeserializer) this.deserializers.get(type);
        if (derializer != null) {
            return derializer;
        }
        if (type instanceof Class) {
            return getDeserializer((Class) type, type);
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return getDeserializer((Class) rawType, type);
            }
            return getDeserializer(rawType);
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getDeserializer(upperBounds[0]);
            }
        }
        return JavaObjectDeserializer.instance;
    }

    public ObjectDeserializer getDeserializer(Class<?> clazz, Type type) {
        ObjectDeserializer deserializer;
        ObjectDeserializer deserializer2 = (ObjectDeserializer) this.deserializers.get(type);
        if (deserializer2 != null) {
            return deserializer2;
        }
        if (type == 0) {
            type = clazz;
        }
        ObjectDeserializer deserializer3 = (ObjectDeserializer) this.deserializers.get(type);
        if (deserializer3 != null) {
            return deserializer3;
        }
        if (!isPrimitive(clazz)) {
            JSONType annotation = (JSONType) clazz.getAnnotation(JSONType.class);
            if (annotation != null) {
                Class<?> mappingTo = annotation.mappingTo();
                if (mappingTo != Void.class) {
                    return getDeserializer(mappingTo, mappingTo);
                }
            }
        }
        if ((type instanceof WildcardType) || (type instanceof TypeVariable) || (type instanceof ParameterizedType)) {
            deserializer3 = (ObjectDeserializer) this.deserializers.get(clazz);
        }
        if (deserializer3 != null) {
            return deserializer3;
        }
        ObjectDeserializer deserializer4 = (ObjectDeserializer) this.deserializers.get(type);
        if (deserializer4 != null) {
            return deserializer4;
        }
        if (clazz.isEnum()) {
            deserializer = new EnumDeserializer(clazz);
        } else if (clazz.isArray()) {
            deserializer = ArrayCodec.instance;
        } else if (clazz == Set.class || clazz == HashSet.class || clazz == Collection.class || clazz == List.class || clazz == ArrayList.class) {
            deserializer = CollectionCodec.instance;
        } else if (Collection.class.isAssignableFrom(clazz)) {
            deserializer = CollectionCodec.instance;
        } else if (Map.class.isAssignableFrom(clazz)) {
            deserializer = MapDeserializer.instance;
        } else if (Throwable.class.isAssignableFrom(clazz)) {
            deserializer = new ThrowableDeserializer(this, clazz);
        } else if (clazz.getName().equals("android.net.Uri")) {
            deserializer = MiscCodec.instance;
        } else {
            deserializer = new JavaBeanDeserializer(this, clazz, type);
        }
        putDeserializer(type, deserializer);
        return deserializer;
    }

    public ObjectDeserializer registerIfNotExists(Class<?> clazz) {
        return registerIfNotExists(clazz, clazz.getModifiers(), false, true, true, true);
    }

    public ObjectDeserializer registerIfNotExists(Class<?> clazz, int classModifiers, boolean fieldOnly, boolean jsonTypeSupport, boolean jsonFieldSupport, boolean fieldGenericSupport) {
        ObjectDeserializer deserializer = (ObjectDeserializer) this.deserializers.get(clazz);
        if (deserializer != null) {
            return deserializer;
        }
        JavaBeanDeserializer javaBeanDeserializer = new JavaBeanDeserializer(this, clazz, clazz, JavaBeanInfo.build(clazz, classModifiers, clazz, fieldOnly, jsonTypeSupport, jsonFieldSupport, fieldGenericSupport, this.propertyNamingStrategy));
        putDeserializer(clazz, javaBeanDeserializer);
        return javaBeanDeserializer;
    }

    public boolean containsKey(Class clazz) {
        return this.deserializers.get(clazz) != null;
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) {
        Class<?> fieldClass = fieldInfo.fieldClass;
        if (fieldClass == List.class || fieldClass == ArrayList.class || (fieldClass.isArray() && !fieldClass.getComponentType().isPrimitive())) {
            return new ListTypeFieldDeserializer(mapping, clazz, fieldInfo);
        }
        return new DefaultFieldDeserializer(mapping, clazz, fieldInfo);
    }

    public void putDeserializer(Type type, ObjectDeserializer deserializer) {
        this.deserializers.put(type, deserializer);
    }

    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == BigInteger.class || clazz == BigDecimal.class || clazz == String.class || clazz == Date.class || clazz == java.sql.Date.class || clazz == Time.class || clazz == Timestamp.class;
    }

    public Class<?> checkAutoType(String typeName, Class<?> expectClass, int features) {
        if (typeName == null) {
            return null;
        }
        if (typeName.length() >= 128 || typeName.length() < 3) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        long h1 = (-3750763034362895579L ^ ((long) typeName.charAt(0))) * 1099511628211L;
        if (h1 == -5808493101479473382L) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        if ((((long) typeName.charAt(typeName.length() - 1)) ^ h1) * 1099511628211L == 655701488918567152L) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        long hash = (((((-3750763034362895579L ^ ((long) typeName.charAt(0))) * 1099511628211L) ^ ((long) typeName.charAt(1))) * 1099511628211L) ^ ((long) typeName.charAt(2))) * 1099511628211L;
        int i = 3;
        while (i < typeName.length()) {
            hash = (hash ^ ((long) typeName.charAt(i))) * 1099511628211L;
            if (Arrays.binarySearch(denyList, hash) < 0 || TypeUtils.getClassFromMapping(typeName) != null) {
                i++;
            } else {
                throw new JSONException("autoType is not support. " + typeName);
            }
        }
        Class<?> clazz = TypeUtils.getClassFromMapping(typeName);
        if (clazz != null) {
            return clazz;
        }
        Class<?> clazz2 = this.deserializers.findClass(typeName);
        if (clazz2 != null) {
            return clazz2;
        }
        Class<?> clazz3 = TypeUtils.loadClass(typeName, this.defaultClassLoader, false);
        if (clazz3 == null || expectClass == null || clazz3 == HashMap.class) {
            if (clazz3.isAnnotationPresent(JSONType.class)) {
                TypeUtils.addMapping(typeName, clazz3);
                return clazz3;
            }
            int mask = Feature.SupportAutoType.mask;
            if ((features & mask) == 0 && (JSON.DEFAULT_PARSER_FEATURE & mask) == 0 && !this.autoTypeSupport) {
                throw new JSONException("autoType is not support : " + typeName);
            }
            TypeUtils.addMapping(typeName, clazz3);
            return clazz3;
        } else if (expectClass.isAssignableFrom(clazz3)) {
            TypeUtils.addMapping(typeName, clazz3);
            return clazz3;
        } else {
            throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
        }
    }
}
