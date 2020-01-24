package com.google.api.client.util;

import com.github.mikephil.charting.utils.Utils;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
    public static final BigDecimal NULL_BIG_DECIMAL = new BigDecimal("0");
    public static final BigInteger NULL_BIG_INTEGER = new BigInteger("0");
    public static final Boolean NULL_BOOLEAN = new Boolean(true);
    public static final Byte NULL_BYTE = new Byte(0);
    private static final ConcurrentHashMap<Class<?>, Object> NULL_CACHE = new ConcurrentHashMap<>();
    public static final Character NULL_CHARACTER = new Character(0);
    public static final DateTime NULL_DATE_TIME = new DateTime(0);
    public static final Double NULL_DOUBLE = new Double(Utils.DOUBLE_EPSILON);
    public static final Float NULL_FLOAT = new Float(0.0f);
    public static final Integer NULL_INTEGER = new Integer(0);
    public static final Long NULL_LONG = new Long(0);
    public static final Short NULL_SHORT = new Short(0);
    public static final String NULL_STRING = new String();

    static {
        NULL_CACHE.put(Boolean.class, NULL_BOOLEAN);
        NULL_CACHE.put(String.class, NULL_STRING);
        NULL_CACHE.put(Character.class, NULL_CHARACTER);
        NULL_CACHE.put(Byte.class, NULL_BYTE);
        NULL_CACHE.put(Short.class, NULL_SHORT);
        NULL_CACHE.put(Integer.class, NULL_INTEGER);
        NULL_CACHE.put(Float.class, NULL_FLOAT);
        NULL_CACHE.put(Long.class, NULL_LONG);
        NULL_CACHE.put(Double.class, NULL_DOUBLE);
        NULL_CACHE.put(BigInteger.class, NULL_BIG_INTEGER);
        NULL_CACHE.put(BigDecimal.class, NULL_BIG_DECIMAL);
        NULL_CACHE.put(DateTime.class, NULL_DATE_TIME);
    }

    public static <T> T nullOf(Class<?> objClass) {
        Object obj;
        Object result;
        Object result2 = NULL_CACHE.get(objClass);
        if (result2 != null) {
            return result2;
        }
        synchronized (NULL_CACHE) {
            Object result3 = NULL_CACHE.get(objClass);
            if (result3 == null) {
                if (objClass.isArray()) {
                    int dims = 0;
                    Class<?> componentType = objClass;
                    do {
                        componentType = componentType.getComponentType();
                        dims++;
                    } while (componentType.isArray());
                    result = Array.newInstance(componentType, new int[dims]);
                } else if (objClass.isEnum()) {
                    FieldInfo fieldInfo = ClassInfo.of(objClass).getFieldInfo(null);
                    Preconditions.checkNotNull(fieldInfo, "enum missing constant with @NullValue annotation: %s", objClass);
                    result = fieldInfo.enumValue();
                } else {
                    result = Types.newInstance(objClass);
                }
                NULL_CACHE.put(objClass, result);
                obj = result;
            } else {
                obj = result3;
            }
        }
        return obj;
    }

    public static boolean isNull(Object object) {
        return object != null && object == NULL_CACHE.get(object.getClass());
    }

    public static Map<String, Object> mapOf(Object data) {
        if (data == null || isNull(data)) {
            return Collections.emptyMap();
        }
        if (data instanceof Map) {
            return (Map) data;
        }
        return new DataMap(data, false);
    }

    public static <T> T clone(T data) {
        T copy;
        if (data == null || isPrimitive(data.getClass())) {
            return data;
        }
        if (data instanceof GenericData) {
            return ((GenericData) data).clone();
        }
        Class<?> dataClass = data.getClass();
        if (dataClass.isArray()) {
            copy = Array.newInstance(dataClass.getComponentType(), Array.getLength(data));
        } else if (data instanceof ArrayMap) {
            copy = ((ArrayMap) data).clone();
        } else if ("java.util.Arrays$ArrayList".equals(dataClass.getName())) {
            Object[] arrayCopy = ((List) data).toArray();
            deepCopy(arrayCopy, arrayCopy);
            return Arrays.asList(arrayCopy);
        } else {
            copy = Types.newInstance(dataClass);
        }
        deepCopy(data, copy);
        return copy;
    }

    public static void deepCopy(Object src, Object dest) {
        Class<?> srcClass = src.getClass();
        Preconditions.checkArgument(srcClass == dest.getClass());
        if (srcClass.isArray()) {
            Preconditions.checkArgument(Array.getLength(src) == Array.getLength(dest));
            int index = 0;
            for (Object value : Types.iterableOf(src)) {
                int index2 = index + 1;
                Array.set(dest, index, clone(value));
                index = index2;
            }
        } else if (Collection.class.isAssignableFrom(srcClass)) {
            Collection<Object> srcCollection = (Collection) src;
            if (ArrayList.class.isAssignableFrom(srcClass)) {
                ((ArrayList) dest).ensureCapacity(srcCollection.size());
            }
            Collection<Object> destCollection = (Collection) dest;
            for (Object srcValue : srcCollection) {
                destCollection.add(clone(srcValue));
            }
        } else {
            boolean isGenericData = GenericData.class.isAssignableFrom(srcClass);
            if (isGenericData || !Map.class.isAssignableFrom(srcClass)) {
                ClassInfo classInfo = isGenericData ? ((GenericData) src).classInfo : ClassInfo.of(srcClass);
                for (String fieldName : classInfo.names) {
                    FieldInfo fieldInfo = classInfo.getFieldInfo(fieldName);
                    if (!fieldInfo.isFinal() && (!isGenericData || !fieldInfo.isPrimitive())) {
                        Object srcValue2 = fieldInfo.getValue(src);
                        if (srcValue2 != null) {
                            fieldInfo.setValue(dest, clone(srcValue2));
                        }
                    }
                }
            } else if (ArrayMap.class.isAssignableFrom(srcClass)) {
                ArrayMap<Object, Object> destMap = (ArrayMap) dest;
                ArrayMap<Object, Object> srcMap = (ArrayMap) src;
                int size = srcMap.size();
                for (int i = 0; i < size; i++) {
                    destMap.set(i, clone(srcMap.getValue(i)));
                }
            } else {
                Map<String, Object> destMap2 = (Map) dest;
                for (Entry<String, Object> srcEntry : ((Map) src).entrySet()) {
                    destMap2.put(srcEntry.getKey(), clone(srcEntry.getValue()));
                }
            }
        }
    }

    public static boolean isPrimitive(Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType) type);
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Class<?> typeClass = (Class) type;
        if (typeClass.isPrimitive() || typeClass == Character.class || typeClass == String.class || typeClass == Integer.class || typeClass == Long.class || typeClass == Short.class || typeClass == Byte.class || typeClass == Float.class || typeClass == Double.class || typeClass == BigInteger.class || typeClass == BigDecimal.class || typeClass == DateTime.class || typeClass == Boolean.class) {
            return true;
        }
        return false;
    }

    public static boolean isValueOfPrimitiveType(Object fieldValue) {
        return fieldValue == null || isPrimitive(fieldValue.getClass());
    }

    public static Object parsePrimitiveValue(Type type, String stringValue) {
        Class cls;
        if (type instanceof Class) {
            cls = (Class) type;
        } else {
            cls = null;
        }
        if (type == null || cls != null) {
            if (cls == Void.class) {
                return null;
            }
            if (stringValue == null || cls == null || cls.isAssignableFrom(String.class)) {
                return stringValue;
            }
            if (cls == Character.class || cls == Character.TYPE) {
                if (stringValue.length() == 1) {
                    return Character.valueOf(stringValue.charAt(0));
                }
                String valueOf = String.valueOf(String.valueOf(cls));
                throw new IllegalArgumentException(new StringBuilder(valueOf.length() + 37).append("expected type Character/char but got ").append(valueOf).toString());
            } else if (cls == Boolean.class || cls == Boolean.TYPE) {
                return Boolean.valueOf(stringValue);
            } else {
                if (cls == Byte.class || cls == Byte.TYPE) {
                    return Byte.valueOf(stringValue);
                }
                if (cls == Short.class || cls == Short.TYPE) {
                    return Short.valueOf(stringValue);
                }
                if (cls == Integer.class || cls == Integer.TYPE) {
                    return Integer.valueOf(stringValue);
                }
                if (cls == Long.class || cls == Long.TYPE) {
                    return Long.valueOf(stringValue);
                }
                if (cls == Float.class || cls == Float.TYPE) {
                    return Float.valueOf(stringValue);
                }
                if (cls == Double.class || cls == Double.TYPE) {
                    return Double.valueOf(stringValue);
                }
                if (cls == DateTime.class) {
                    return DateTime.parseRfc3339(stringValue);
                }
                if (cls == BigInteger.class) {
                    return new BigInteger(stringValue);
                }
                if (cls == BigDecimal.class) {
                    return new BigDecimal(stringValue);
                }
                if (cls.isEnum()) {
                    return ClassInfo.of(cls).getFieldInfo(stringValue).enumValue();
                }
            }
        }
        String valueOf2 = String.valueOf(String.valueOf(type));
        throw new IllegalArgumentException(new StringBuilder(valueOf2.length() + 35).append("expected primitive class, but got: ").append(valueOf2).toString());
    }

    public static Collection<Object> newCollectionInstance(Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType) type);
        }
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        Class cls = type instanceof Class ? (Class) type : null;
        if (type == null || (type instanceof GenericArrayType) || (cls != null && (cls.isArray() || cls.isAssignableFrom(ArrayList.class)))) {
            return new ArrayList();
        }
        if (cls == null) {
            String valueOf = String.valueOf(String.valueOf(type));
            throw new IllegalArgumentException(new StringBuilder(valueOf.length() + 39).append("unable to create new instance of type: ").append(valueOf).toString());
        } else if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        } else {
            if (cls.isAssignableFrom(TreeSet.class)) {
                return new TreeSet();
            }
            return (Collection) Types.newInstance(cls);
        }
    }

    public static Map<String, Object> newMapInstance(Class<?> mapClass) {
        if (mapClass == null || mapClass.isAssignableFrom(ArrayMap.class)) {
            return ArrayMap.create();
        }
        if (mapClass.isAssignableFrom(TreeMap.class)) {
            return new TreeMap();
        }
        return (Map) Types.newInstance(mapClass);
    }

    public static Type resolveWildcardTypeOrTypeVariable(List<Type> context, Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType) type);
        }
        while (type instanceof TypeVariable) {
            Type resolved = Types.resolveTypeVariable(context, (TypeVariable) type);
            if (resolved != null) {
                type = resolved;
            }
            if (type instanceof TypeVariable) {
                type = ((TypeVariable) type).getBounds()[0];
            }
        }
        return type;
    }
}
