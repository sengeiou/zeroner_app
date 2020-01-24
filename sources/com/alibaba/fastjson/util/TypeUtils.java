package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsLatitudeRef;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeUtils {
    public static boolean compatibleWithJavaBean = false;
    private static volatile Map<Class, String[]> kotlinIgnores;
    private static volatile boolean kotlinIgnores_error;
    private static volatile boolean kotlin_class_klass_error;
    private static volatile boolean kotlin_error;
    private static volatile Constructor kotlin_kclass_constructor;
    private static volatile Method kotlin_kclass_getConstructors;
    private static volatile Method kotlin_kfunction_getParameters;
    private static volatile Method kotlin_kparameter_getName;
    private static volatile Class kotlin_metadata;
    private static volatile boolean kotlin_metadata_error;
    private static final ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap(36, 0.75f, 1);
    private static boolean setAccessibleEnable = true;

    static {
        mappings.put("byte", Byte.TYPE);
        mappings.put("short", Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put("long", Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put("[B", byte[].class);
        mappings.put("[S", short[].class);
        mappings.put("[I", int[].class);
        mappings.put("[J", long[].class);
        mappings.put("[F", float[].class);
        mappings.put("[D", double[].class);
        mappings.put("[C", char[].class);
        mappings.put("[Z", boolean[].class);
        mappings.put("java.util.HashMap", HashMap.class);
        mappings.put("java.util.TreeMap", TreeMap.class);
        mappings.put("java.util.Date", Date.class);
        mappings.put("com.alibaba.fastjson.JSONObject", JSONObject.class);
        mappings.put("java.util.concurrent.ConcurrentHashMap", ConcurrentHashMap.class);
        mappings.put("java.text.SimpleDateFormat", SimpleDateFormat.class);
        mappings.put("java.lang.StackTraceElement", StackTraceElement.class);
        mappings.put("java.lang.RuntimeException", RuntimeException.class);
    }

    public static boolean isKotlin(Class clazz) {
        if (kotlin_metadata == null && !kotlin_metadata_error) {
            try {
                kotlin_metadata = Class.forName("kotlin.Metadata");
            } catch (Throwable th) {
                kotlin_metadata_error = true;
            }
        }
        if (kotlin_metadata == null) {
            return false;
        }
        return clazz.isAnnotationPresent(kotlin_metadata);
    }

    private static boolean isKotlinIgnore(Class clazz, String methodName) {
        boolean z = true;
        if (kotlinIgnores == null && !kotlinIgnores_error) {
            try {
                Map<Class, String[]> map = new HashMap<>();
                map.put(Class.forName("kotlin.ranges.CharRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.IntRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.LongRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.ClosedFloatRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.ClosedDoubleRange"), new String[]{"getEndInclusive", "isEmpty"});
                kotlinIgnores = map;
            } catch (Throwable th) {
                kotlinIgnores_error = true;
            }
        }
        if (kotlinIgnores == null) {
            return false;
        }
        String[] ignores = (String[]) kotlinIgnores.get(clazz);
        if (ignores == null) {
            return false;
        }
        if (Arrays.binarySearch(ignores, methodName) < 0) {
            z = false;
        }
        return z;
    }

    public static String[] getKoltinConstructorParameters(Class clazz) {
        if (kotlin_kclass_constructor == null && !kotlin_class_klass_error) {
            try {
                Class class_kotlin_kclass = Class.forName("kotlin.reflect.jvm.internal.KClassImpl");
                kotlin_kclass_constructor = class_kotlin_kclass.getConstructor(new Class[]{Class.class});
                kotlin_kclass_getConstructors = class_kotlin_kclass.getMethod("getConstructors", new Class[0]);
                kotlin_kfunction_getParameters = Class.forName("kotlin.reflect.KFunction").getMethod("getParameters", new Class[0]);
                kotlin_kparameter_getName = Class.forName("kotlin.reflect.KParameter").getMethod("getName", new Class[0]);
            } catch (Throwable th) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kclass_constructor == null) {
            return null;
        }
        if (kotlin_error) {
            return null;
        }
        Object constructor = null;
        try {
            Iterator iterator = ((Iterable) kotlin_kclass_getConstructors.invoke(kotlin_kclass_constructor.newInstance(new Object[]{clazz}), new Object[0])).iterator();
            while (iterator.hasNext()) {
                Object item = iterator.next();
                List parameters = (List) kotlin_kfunction_getParameters.invoke(item, new Object[0]);
                if (constructor == null || parameters.size() != 0) {
                    constructor = item;
                }
                iterator.hasNext();
            }
            List parameters2 = (List) kotlin_kfunction_getParameters.invoke(constructor, new Object[0]);
            String[] names = new String[parameters2.size()];
            for (int i = 0; i < parameters2.size(); i++) {
                names[i] = (String) kotlin_kparameter_getName.invoke(parameters2.get(i), new Object[0]);
            }
            return names;
        } catch (Throwable th2) {
            kotlin_error = true;
            return null;
        }
    }

    public static final String castToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static final Byte castToByte(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Byte.valueOf(((Number) value).byteValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal)) {
                return null;
            }
            return Byte.valueOf(Byte.parseByte(strVal));
        }
        throw new JSONException("can not cast to byte, value : " + value);
    }

    public static final Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() == 1) {
                return Character.valueOf(strVal.charAt(0));
            }
            throw new JSONException("can not cast to byte, value : " + value);
        }
        throw new JSONException("can not cast to byte, value : " + value);
    }

    public static final Short castToShort(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Short.valueOf(((Number) value).shortValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal)) {
                return null;
            }
            return Short.valueOf(Short.parseShort(strVal));
        }
        throw new JSONException("can not cast to short, value : " + value);
    }

    public static final BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }
        String strVal = value.toString();
        if (strVal.length() == 0 || "null".equals(strVal)) {
            return null;
        }
        return new BigDecimal(strVal);
    }

    public static final BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if ((value instanceof Float) || (value instanceof Double)) {
            return BigInteger.valueOf(((Number) value).longValue());
        }
        String strVal = value.toString();
        if (strVal.length() == 0 || "null".equals(strVal)) {
            return null;
        }
        return new BigInteger(strVal);
    }

    public static final Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equals(strVal)) {
                return null;
            }
            return Float.valueOf(Float.parseFloat(strVal));
        }
        throw new JSONException("can not cast to float, value : " + value);
    }

    public static final Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Double.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            return Double.valueOf(Double.parseDouble(strVal));
        }
        throw new JSONException("can not cast to double, value : " + value);
    }

    public static final Date castToDate(Object value) {
        String format;
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        long longValue = -1;
        if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale < -100 || scale > 100) {
                longValue = decimal.longValueExact();
            } else {
                longValue = decimal.longValue();
            }
        } else if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.indexOf(45) != -1) {
                if (strVal.length() == JSON.DEFFAULT_DATE_FORMAT.length()) {
                    format = JSON.DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else if (strVal.length() == 29 && strVal.charAt(26) == ':' && strVal.charAt(28) == '0') {
                    format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
                dateFormat.setTimeZone(JSON.defaultTimeZone);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    throw new JSONException("can not cast to Date, value : " + strVal);
                }
            } else if (strVal.length() == 0 || "null".equals(strVal)) {
                return null;
            } else {
                longValue = Long.parseLong(strVal);
            }
        }
        if (longValue >= 0) {
            return new Date(longValue);
        }
        throw new JSONException("can not cast to Date, value : " + value);
    }

    public static final Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale < -100 || scale > 100) {
                return Long.valueOf(decimal.longValueExact());
            }
            return Long.valueOf(decimal.longValue());
        } else if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        } else {
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0 || "null".equals(strVal)) {
                    return null;
                }
                try {
                    return Long.valueOf(Long.parseLong(strVal));
                } catch (NumberFormatException e) {
                    JSONLexer dateParser = new JSONLexer(strVal);
                    Calendar calendar = null;
                    if (dateParser.scanISO8601DateIfMatch(false)) {
                        calendar = dateParser.calendar;
                    }
                    dateParser.close();
                    if (calendar != null) {
                        return Long.valueOf(calendar.getTimeInMillis());
                    }
                }
            }
            throw new JSONException("can not cast to long, value : " + value);
        }
    }

    public static final Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale < -100 || scale > 100) {
                return Integer.valueOf(decimal.intValueExact());
            }
            return Integer.valueOf(decimal.intValue());
        } else if (value instanceof Number) {
            return Integer.valueOf(((Number) value).intValue());
        } else {
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0 || "null".equals(strVal)) {
                    return null;
                }
                return Integer.valueOf(Integer.parseInt(strVal));
            }
            throw new JSONException("can not cast to int, value : " + value);
        }
    }

    public static final byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            String string = (String) value;
            return JSONLexer.decodeFast(string, 0, string.length());
        }
        throw new JSONException("can not cast to int, value : " + value);
    }

    public static final Boolean castToBoolean(Object value) {
        boolean z = true;
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof BigDecimal) {
            if (((BigDecimal) value).intValueExact() != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        } else if (value instanceof Number) {
            if (((Number) value).intValue() != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        } else {
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0 || "null".equals(strVal)) {
                    return null;
                }
                if ("true".equalsIgnoreCase(strVal) || "1".equals(strVal)) {
                    return Boolean.TRUE;
                }
                if ("false".equalsIgnoreCase(strVal) || "0".equals(strVal)) {
                    return Boolean.FALSE;
                }
            }
            throw new JSONException("can not cast to int, value : " + value);
        }
    }

    public static final <T> T castToJavaBean(Object obj, Class<T> clazz) {
        return cast(obj, clazz, ParserConfig.global);
    }

    public static final <T> T cast(Object obj, Class<T> clazz, ParserConfig mapping) {
        return cast(obj, clazz, mapping, 0);
    }

    public static final <T> T cast(Object obj, Class<T> clazz, ParserConfig mapping, int features) {
        Calendar calendar;
        if (obj == null) {
            return null;
        }
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        } else if (clazz == obj.getClass()) {
            return obj;
        } else {
            if (!(obj instanceof Map)) {
                if (clazz.isArray()) {
                    if (obj instanceof Collection) {
                        Collection<Object> collection = (Collection) obj;
                        int index = 0;
                        Object array = Array.newInstance(clazz.getComponentType(), collection.size());
                        for (Object item : collection) {
                            Array.set(array, index, cast(item, clazz.getComponentType(), mapping));
                            index++;
                        }
                        return array;
                    } else if (clazz == byte[].class) {
                        return castToBytes(obj);
                    }
                }
                if (clazz.isAssignableFrom(obj.getClass())) {
                    return obj;
                }
                if (clazz == Boolean.TYPE || clazz == Boolean.class) {
                    return castToBoolean(obj);
                }
                if (clazz == Byte.TYPE || clazz == Byte.class) {
                    return castToByte(obj);
                }
                if ((clazz == Character.TYPE || clazz == Character.class) && (obj instanceof String)) {
                    String strVal = (String) obj;
                    if (strVal.length() == 1) {
                        return Character.valueOf(strVal.charAt(0));
                    }
                }
                if (clazz == Short.TYPE || clazz == Short.class) {
                    return castToShort(obj);
                }
                if (clazz == Integer.TYPE || clazz == Integer.class) {
                    return castToInt(obj);
                }
                if (clazz == Long.TYPE || clazz == Long.class) {
                    return castToLong(obj);
                }
                if (clazz == Float.TYPE || clazz == Float.class) {
                    return castToFloat(obj);
                }
                if (clazz == Double.TYPE || clazz == Double.class) {
                    return castToDouble(obj);
                }
                if (clazz == String.class) {
                    return castToString(obj);
                }
                if (clazz == BigDecimal.class) {
                    return castToBigDecimal(obj);
                }
                if (clazz == BigInteger.class) {
                    return castToBigInteger(obj);
                }
                if (clazz == Date.class) {
                    return castToDate(obj);
                }
                if (clazz.isEnum()) {
                    return castToEnum(obj, clazz, mapping);
                }
                if (Calendar.class.isAssignableFrom(clazz)) {
                    Date date = castToDate(obj);
                    if (clazz == Calendar.class) {
                        calendar = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    } else {
                        try {
                            calendar = (Calendar) clazz.newInstance();
                        } catch (Exception e) {
                            throw new JSONException("can not cast to : " + clazz.getName(), e);
                        }
                    }
                    calendar.setTime(date);
                    return calendar;
                }
                if (obj instanceof String) {
                    String strVal2 = (String) obj;
                    if (strVal2.length() == 0 || "null".equals(strVal2)) {
                        return null;
                    }
                    if (clazz == Currency.class) {
                        return Currency.getInstance(strVal2);
                    }
                }
                throw new JSONException("can not cast to : " + clazz.getName());
            } else if (clazz == Map.class) {
                return obj;
            } else {
                Map map = (Map) obj;
                if (clazz != Object.class || map.containsKey("@type")) {
                    return castToJavaBean((Map) obj, clazz, mapping, features);
                }
                return obj;
            }
        }
    }

    public static final <T> T castToEnum(Object obj, Class<T> clazz, ParserConfig mapping) {
        try {
            if (obj instanceof String) {
                String name = (String) obj;
                if (name.length() == 0) {
                    return null;
                }
                return Enum.valueOf(clazz, name);
            }
            if ((obj instanceof Integer) || (obj instanceof Long)) {
                int ordinal = ((Number) obj).intValue();
                Object[] values = clazz.getEnumConstants();
                if (ordinal < values.length) {
                    return values[ordinal];
                }
            }
            throw new JSONException("can not cast to : " + clazz.getName());
        } catch (Exception ex) {
            throw new JSONException("can not cast to : " + clazz.getName(), ex);
        }
    }

    public static final <T> T cast(Object obj, Type type, ParserConfig mapping) {
        if (obj == null) {
            return null;
        }
        if (type instanceof Class) {
            return cast(obj, (Class) type, mapping, 0);
        }
        if (type instanceof ParameterizedType) {
            return cast(obj, (ParameterizedType) type, mapping);
        }
        if (obj instanceof String) {
            String strVal = (String) obj;
            if (strVal.length() == 0 || "null".equals(strVal)) {
                return null;
            }
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : " + type);
    }

    public static final <T> T cast(Object obj, ParameterizedType type, ParserConfig mapping) {
        Collection collection;
        Object itemValue;
        Object itemValue2;
        Type rawTye = type.getRawType();
        if (rawTye == List.class || rawTye == ArrayList.class) {
            Type itemType = type.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List listObj = (List) obj;
                int listObjSize = listObj.size();
                ArrayList arrayList = new ArrayList(listObjSize);
                for (int i = 0; i < listObjSize; i++) {
                    Object item = listObj.get(i);
                    if (!(itemType instanceof Class)) {
                        itemValue2 = cast(item, itemType, mapping);
                    } else if (item == null || item.getClass() != JSONObject.class) {
                        itemValue2 = cast(item, (Class) itemType, mapping, 0);
                    } else {
                        itemValue2 = ((JSONObject) item).toJavaObject((Class) itemType, mapping, 0);
                    }
                    arrayList.add(itemValue2);
                }
                return arrayList;
            }
        }
        if (rawTye == Set.class || rawTye == HashSet.class || rawTye == TreeSet.class || rawTye == List.class || rawTye == ArrayList.class) {
            Type itemType2 = type.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                if (rawTye == Set.class || rawTye == HashSet.class) {
                    collection = new HashSet();
                } else if (rawTye == TreeSet.class) {
                    collection = new TreeSet();
                } else {
                    collection = new ArrayList();
                }
                for (Object item2 : (Iterable) obj) {
                    if (!(itemType2 instanceof Class)) {
                        itemValue = cast(item2, itemType2, mapping);
                    } else if (item2 == null || item2.getClass() != JSONObject.class) {
                        itemValue = cast(item2, (Class) itemType2, mapping, 0);
                    } else {
                        itemValue = ((JSONObject) item2).toJavaObject((Class) itemType2, mapping, 0);
                    }
                    collection.add(itemValue);
                }
                return collection;
            }
        }
        if (rawTye == Map.class || rawTye == HashMap.class) {
            Type keyType = type.getActualTypeArguments()[0];
            Type valueType = type.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                HashMap hashMap = new HashMap();
                for (Entry entry : ((Map) obj).entrySet()) {
                    hashMap.put(cast(entry.getKey(), keyType, mapping), cast(entry.getValue(), valueType, mapping));
                }
                return hashMap;
            }
        }
        if (obj instanceof String) {
            String strVal = (String) obj;
            if (strVal.length() == 0 || "null".equals(strVal)) {
                return null;
            }
        }
        if (type.getActualTypeArguments().length == 1 && (type.getActualTypeArguments()[0] instanceof WildcardType)) {
            return cast(obj, rawTye, mapping);
        }
        throw new JSONException("can not cast to : " + type);
    }

    public static final <T> T castToJavaBean(Map<String, Object> map, Class<T> clazz, ParserConfig config) {
        return castToJavaBean(map, clazz, config, 0);
    }

    public static final <T> T castToJavaBean(Map<String, Object> map, Class<T> clazz, ParserConfig config, int features) {
        JSONObject object;
        int lineNumber;
        if (clazz == StackTraceElement.class) {
            try {
                String declaringClass = (String) map.get("className");
                String methodName = (String) map.get("methodName");
                String fileName = (String) map.get("fileName");
                Number value = (Number) map.get("lineNumber");
                if (value == null) {
                    lineNumber = 0;
                } else if (value instanceof BigDecimal) {
                    lineNumber = ((BigDecimal) value).intValueExact();
                } else {
                    lineNumber = value.intValue();
                }
                StackTraceElement stackTraceElement = new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
                return stackTraceElement;
            } catch (Exception e) {
                JSONException jSONException = new JSONException(e.getMessage(), e);
                throw jSONException;
            }
        } else {
            Object iClassObject = map.get("@type");
            if (iClassObject instanceof String) {
                String className = (String) iClassObject;
                if (config == null) {
                    config = ParserConfig.global;
                }
                Class<?> loadClazz = config.checkAutoType(className, null, features);
                if (loadClazz == null) {
                    throw new ClassNotFoundException(className + " not found");
                } else if (!loadClazz.equals(clazz)) {
                    return castToJavaBean(map, loadClazz, config, features);
                }
            }
            if (clazz.isInterface()) {
                if (map instanceof JSONObject) {
                    object = (JSONObject) map;
                } else {
                    object = new JSONObject(map);
                }
                if (config == null) {
                    config = ParserConfig.getGlobalInstance();
                }
                if (config.getDeserializer(clazz) != null) {
                    return JSON.parseObject(JSON.toJSONString(object), clazz);
                }
                return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz}, object);
            } else if (clazz == String.class && (map instanceof JSONObject)) {
                return map.toString();
            } else {
                if (config == null) {
                    config = ParserConfig.global;
                }
                JavaBeanDeserializer javaBeanDeser = null;
                ObjectDeserializer deserizer = config.getDeserializer(clazz);
                if (deserizer instanceof JavaBeanDeserializer) {
                    javaBeanDeser = (JavaBeanDeserializer) deserizer;
                }
                if (javaBeanDeser != null) {
                    return javaBeanDeser.createInstance(map, config);
                }
                throw new JSONException("can not get javaBeanDeserializer");
            }
        }
    }

    public static Class<?> getClassFromMapping(String className) {
        return (Class) mappings.get(className);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader) {
        return loadClass(className, classLoader, false);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader, boolean cache) {
        if (className == null || className.length() == 0) {
            return null;
        }
        if (className.length() >= 256) {
            throw new JSONException("className too long. " + className);
        }
        Class<?> clazz = (Class) mappings.get(className);
        if (clazz != null) {
            return clazz;
        }
        if (className.charAt(0) == '[') {
            Class<?> componentType = loadClass(className.substring(1), classLoader, false);
            if (componentType == null) {
                return null;
            }
            return Array.newInstance(componentType, 0).getClass();
        } else if (className.startsWith("L") && className.endsWith(";")) {
            return loadClass(className.substring(1, className.length() - 1), classLoader, false);
        } else {
            if (classLoader != null) {
                try {
                    Class<?> clazz2 = classLoader.loadClass(className);
                    if (!cache) {
                        return clazz2;
                    }
                    mappings.put(className, clazz2);
                    return clazz2;
                } catch (Exception e) {
                }
            }
            try {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (!(contextClassLoader == null || contextClassLoader == classLoader)) {
                    Class<?> clazz3 = contextClassLoader.loadClass(className);
                    if (!cache) {
                        return clazz3;
                    }
                    mappings.put(className, clazz3);
                    return clazz3;
                }
            } catch (Exception e2) {
            }
            try {
                clazz = Class.forName(className);
                mappings.put(className, clazz);
                return clazz;
            } catch (Exception e3) {
                return clazz;
            }
        }
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, int modifiers, boolean fieldOnly, JSONType jsonType, Map<String, String> aliasMap, boolean sorted, boolean jsonFieldSupport, boolean fieldGenericSupport, PropertyNamingStrategy propertyNamingStrategy) {
        Field[] declaredFields;
        String propertyName;
        String propertyName2;
        Method[] declaredMethods;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        HashMap hashMap = new HashMap();
        Field[] declaredFields2 = clazz.getDeclaredFields();
        if (!fieldOnly) {
            boolean kotlin2 = isKotlin(clazz);
            ArrayList<Method> arrayList = new ArrayList<>();
            Class<?> cls = clazz;
            while (cls != null && cls != Object.class) {
                for (Method method : cls.getDeclaredMethods()) {
                    int modifier = method.getModifiers();
                    if ((modifier & 8) == 0 && (modifier & 2) == 0 && (modifier & 256) == 0 && (modifier & 4) == 0 && !method.getReturnType().equals(Void.TYPE) && method.getParameterTypes().length == 0 && method.getReturnType() != ClassLoader.class && method.getDeclaringClass() != Object.class) {
                        arrayList.add(method);
                    }
                }
                cls = cls.getSuperclass();
            }
            Constructor[] constructors = null;
            Annotation[][] paramAnnotationArrays = null;
            String[] paramNames = null;
            short[] paramNameMapping = null;
            for (Method method2 : arrayList) {
                String methodName = method2.getName();
                int ordinal = 0;
                int serialzeFeatures = 0;
                if (!methodName.equals("getMetaClass") || !method2.getReturnType().getName().equals("groovy.lang.MetaClass")) {
                    JSONField annotation = jsonFieldSupport ? (JSONField) method2.getAnnotation(JSONField.class) : null;
                    if (annotation == null && jsonFieldSupport) {
                        annotation = getSupperMethodAnnotation(clazz, method2);
                    }
                    if (!kotlin2 || !isKotlinIgnore(clazz, methodName)) {
                        if (annotation == null && kotlin2) {
                            if (constructors == null) {
                                constructors = clazz.getDeclaredConstructors();
                                if (constructors.length == 1) {
                                    paramAnnotationArrays = constructors[0].getParameterAnnotations();
                                    paramNames = getKoltinConstructorParameters(clazz);
                                    if (paramNames != null) {
                                        String[] paramNames_sorted = new String[paramNames.length];
                                        System.arraycopy(paramNames, 0, paramNames_sorted, 0, paramNames.length);
                                        Arrays.sort(paramNames_sorted);
                                        paramNameMapping = new short[paramNames.length];
                                        for (short p = 0; p < paramNames.length; p = (short) (p + 1)) {
                                            paramNameMapping[Arrays.binarySearch(paramNames_sorted, paramNames[p])] = p;
                                        }
                                        paramNames = paramNames_sorted;
                                    }
                                }
                            }
                            if (paramNames != null && paramNameMapping != null) {
                                if (methodName.startsWith("get")) {
                                    String propertyName3 = decapitalize(methodName.substring(3));
                                    int p2 = Arrays.binarySearch(paramNames, propertyName3);
                                    if (p2 < 0) {
                                        int i = 0;
                                        while (true) {
                                            if (i >= paramNames.length) {
                                                break;
                                            } else if (propertyName3.equalsIgnoreCase(paramNames[i])) {
                                                p2 = i;
                                                break;
                                            } else {
                                                i++;
                                            }
                                        }
                                    }
                                    if (p2 >= 0) {
                                        Annotation[] paramAnnotations = paramAnnotationArrays[paramNameMapping[p2]];
                                        if (paramAnnotations != null) {
                                            int length = paramAnnotations.length;
                                            int i2 = 0;
                                            while (true) {
                                                if (i2 >= length) {
                                                    break;
                                                }
                                                Annotation paramAnnotation = paramAnnotations[i2];
                                                if (paramAnnotation instanceof JSONField) {
                                                    annotation = (JSONField) paramAnnotation;
                                                    break;
                                                }
                                                i2++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        boolean fieldAnnotationExists = false;
                        if (annotation != null) {
                            if (annotation.serialize()) {
                                ordinal = annotation.ordinal();
                                serialzeFeatures = SerializerFeature.of(annotation.serialzeFeatures());
                                if (annotation.name().length() != 0) {
                                    String propertyName4 = annotation.name();
                                    if (aliasMap != null) {
                                        propertyName4 = (String) aliasMap.get(propertyName4);
                                        if (propertyName4 == null) {
                                        }
                                    }
                                    setAccessible(clazz, method2, modifiers);
                                    linkedHashMap.put(propertyName4, new FieldInfo(propertyName4, method2, null, clazz, null, ordinal, serialzeFeatures, annotation, null, true));
                                }
                            }
                        }
                        if (methodName.startsWith("get")) {
                            if (methodName.length() >= 4) {
                                if (!methodName.equals("getClass")) {
                                    char c3 = methodName.charAt(3);
                                    if (Character.isUpperCase(c3)) {
                                        if (compatibleWithJavaBean) {
                                            propertyName2 = decapitalize(methodName.substring(3));
                                        } else {
                                            propertyName2 = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                                        }
                                    } else if (c3 == '_') {
                                        propertyName2 = methodName.substring(4);
                                    } else if (c3 == 'f') {
                                        propertyName2 = methodName.substring(3);
                                    } else if (methodName.length() >= 5 && Character.isUpperCase(methodName.charAt(4))) {
                                        propertyName2 = decapitalize(methodName.substring(3));
                                    }
                                    if (!isJSONTypeIgnore(clazz, jsonType, propertyName2)) {
                                        Field field = getField(clazz, propertyName2, declaredFields2, hashMap);
                                        JSONField fieldAnnotation = null;
                                        if (field != null) {
                                            fieldAnnotation = jsonFieldSupport ? (JSONField) field.getAnnotation(JSONField.class) : null;
                                            if (fieldAnnotation != null) {
                                                if (fieldAnnotation.serialize()) {
                                                    ordinal = fieldAnnotation.ordinal();
                                                    serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                                                    if (fieldAnnotation.name().length() != 0) {
                                                        propertyName2 = fieldAnnotation.name();
                                                        fieldAnnotationExists = true;
                                                        if (aliasMap != null) {
                                                            propertyName2 = (String) aliasMap.get(propertyName2);
                                                            if (propertyName2 == null) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (propertyNamingStrategy != null && !fieldAnnotationExists) {
                                            propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                        }
                                        if (aliasMap != null) {
                                            propertyName2 = (String) aliasMap.get(propertyName2);
                                            if (propertyName2 == null) {
                                            }
                                        }
                                        setAccessible(clazz, method2, modifiers);
                                        linkedHashMap.put(propertyName2, new FieldInfo(propertyName2, method2, field, clazz, null, ordinal, serialzeFeatures, annotation, fieldAnnotation, fieldGenericSupport));
                                    }
                                }
                            }
                        }
                        if (methodName.startsWith("is") && methodName.length() >= 3) {
                            char c2 = methodName.charAt(2);
                            if (Character.isUpperCase(c2)) {
                                if (compatibleWithJavaBean) {
                                    propertyName = decapitalize(methodName.substring(2));
                                } else {
                                    propertyName = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
                                }
                            } else if (c2 == '_') {
                                propertyName = methodName.substring(3);
                            } else if (c2 == 'f') {
                                propertyName = methodName.substring(2);
                            }
                            if (!isJSONTypeIgnore(clazz, jsonType, propertyName)) {
                                Field field2 = getField(clazz, propertyName, declaredFields2, hashMap);
                                if (field2 == null) {
                                    field2 = getField(clazz, methodName, declaredFields2, hashMap);
                                }
                                JSONField fieldAnnotation2 = null;
                                if (field2 != null) {
                                    fieldAnnotation2 = jsonFieldSupport ? (JSONField) field2.getAnnotation(JSONField.class) : null;
                                    if (fieldAnnotation2 != null) {
                                        if (fieldAnnotation2.serialize()) {
                                            ordinal = fieldAnnotation2.ordinal();
                                            serialzeFeatures = SerializerFeature.of(fieldAnnotation2.serialzeFeatures());
                                            if (fieldAnnotation2.name().length() != 0) {
                                                propertyName = fieldAnnotation2.name();
                                                if (aliasMap != null) {
                                                    propertyName = (String) aliasMap.get(propertyName);
                                                    if (propertyName == null) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (propertyNamingStrategy != null) {
                                    propertyName = propertyNamingStrategy.translate(propertyName);
                                }
                                if (aliasMap != null) {
                                    propertyName = (String) aliasMap.get(propertyName);
                                    if (propertyName == null) {
                                    }
                                }
                                setAccessible(clazz, field2, modifiers);
                                setAccessible(clazz, method2, modifiers);
                                linkedHashMap.put(propertyName, new FieldInfo(propertyName, method2, field2, clazz, null, ordinal, serialzeFeatures, annotation, fieldAnnotation2, fieldGenericSupport));
                            }
                        }
                    }
                }
            }
        }
        ArrayList<Field> arrayList2 = new ArrayList<>(declaredFields2.length);
        for (Field f : declaredFields2) {
            if ((f.getModifiers() & 8) == 0 && !f.getName().equals("this$0") && (f.getModifiers() & 1) != 0) {
                arrayList2.add(f);
            }
        }
        Class<?> c = clazz.getSuperclass();
        while (c != null && c != Object.class) {
            for (Field f2 : c.getDeclaredFields()) {
                if ((f2.getModifiers() & 8) == 0 && (f2.getModifiers() & 1) != 0) {
                    arrayList2.add(f2);
                }
            }
            c = c.getSuperclass();
        }
        for (Field field3 : arrayList2) {
            JSONField fieldAnnotation3 = jsonFieldSupport ? (JSONField) field3.getAnnotation(JSONField.class) : null;
            int ordinal2 = 0;
            int serialzeFeatures2 = 0;
            String propertyName5 = field3.getName();
            if (fieldAnnotation3 != null) {
                if (fieldAnnotation3.serialize()) {
                    ordinal2 = fieldAnnotation3.ordinal();
                    serialzeFeatures2 = SerializerFeature.of(fieldAnnotation3.serialzeFeatures());
                    if (fieldAnnotation3.name().length() != 0) {
                        propertyName5 = fieldAnnotation3.name();
                    }
                }
            }
            if (aliasMap != null) {
                propertyName5 = (String) aliasMap.get(propertyName5);
                if (propertyName5 == null) {
                }
            }
            if (propertyNamingStrategy != null) {
                propertyName5 = propertyNamingStrategy.translate(propertyName5);
            }
            if (!linkedHashMap.containsKey(propertyName5)) {
                setAccessible(clazz, field3, modifiers);
                linkedHashMap.put(propertyName5, new FieldInfo(propertyName5, null, field3, clazz, null, ordinal2, serialzeFeatures2, null, fieldAnnotation3, fieldGenericSupport));
            }
        }
        ArrayList arrayList3 = new ArrayList();
        boolean containsAll = false;
        String[] orders = null;
        if (jsonType != null) {
            orders = jsonType.orders();
            if (orders == null || orders.length != linkedHashMap.size()) {
                containsAll = false;
            } else {
                containsAll = true;
                int length2 = orders.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        break;
                    } else if (!linkedHashMap.containsKey(orders[i3])) {
                        containsAll = false;
                        break;
                    } else {
                        i3++;
                    }
                }
            }
        }
        if (containsAll) {
            int length3 = orders.length;
            for (int i4 = 0; i4 < length3; i4++) {
                arrayList3.add((FieldInfo) linkedHashMap.get(orders[i4]));
            }
        } else {
            for (FieldInfo fieldInfo : linkedHashMap.values()) {
                arrayList3.add(fieldInfo);
            }
            if (sorted) {
                Collections.sort(arrayList3);
            }
        }
        return arrayList3;
    }

    public static JSONField getSupperMethodAnnotation(Class<?> clazz, Method method) {
        Method[] methods;
        Method[] methods2;
        for (Class<?> interfaceClass : clazz.getInterfaces()) {
            for (Method interfaceMethod : interfaceClass.getMethods()) {
                if (interfaceMethod.getName().equals(method.getName())) {
                    Class<?>[] interfaceParameterTypes = interfaceMethod.getParameterTypes();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (interfaceParameterTypes.length == parameterTypes.length) {
                        boolean match = true;
                        int i = 0;
                        while (true) {
                            if (i >= interfaceParameterTypes.length) {
                                break;
                            } else if (!interfaceParameterTypes[i].equals(parameterTypes[i])) {
                                match = false;
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (match) {
                            JSONField annotation = (JSONField) interfaceMethod.getAnnotation(JSONField.class);
                            if (annotation != null) {
                                return annotation;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) {
            return null;
        }
        if (Modifier.isAbstract(superClass.getModifiers())) {
            Class<?>[] types = method.getParameterTypes();
            for (Method interfaceMethod2 : superClass.getMethods()) {
                Class<?>[] interfaceTypes = interfaceMethod2.getParameterTypes();
                if (interfaceTypes.length == types.length && interfaceMethod2.getName().equals(method.getName())) {
                    boolean match2 = true;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= types.length) {
                            break;
                        } else if (!interfaceTypes[i2].equals(types[i2])) {
                            match2 = false;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (match2) {
                        JSONField annotation2 = (JSONField) interfaceMethod2.getAnnotation(JSONField.class);
                        if (annotation2 != null) {
                            return annotation2;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> clazz, JSONType jsonType, String propertyName) {
        boolean z;
        if (!(jsonType == null || jsonType.ignores() == null)) {
            for (String item : jsonType.ignores()) {
                if (propertyName.equalsIgnoreCase(item)) {
                    return true;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class || superClass == null || !isJSONTypeIgnore(superClass, (JSONType) superClass.getAnnotation(JSONType.class), propertyName)) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public static boolean isGenericParamType(Type type) {
        if (type instanceof ParameterizedType) {
            return true;
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Type superType = ((Class) type).getGenericSuperclass();
        if (superType == Object.class || !isGenericParamType(superType)) {
            return false;
        }
        return true;
    }

    public static Type getGenericParamType(Type type) {
        if (type instanceof Class) {
            return getGenericParamType(((Class) type).getGenericSuperclass());
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            return (Class) ((TypeVariable) type).getBounds()[0];
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getClass(upperBounds[0]);
            }
        }
        return Object.class;
    }

    public static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public static boolean setAccessible(Class<?> clazz, Member member, int classMofifiers) {
        if (member == null || !setAccessibleEnable) {
            return false;
        }
        Class<?> supperClass = clazz.getSuperclass();
        if ((supperClass == null || supperClass == Object.class) && (member.getModifiers() & 1) != 0 && (classMofifiers & 1) != 0) {
            return false;
        }
        try {
            ((AccessibleObject) member).setAccessible(true);
            return true;
        } catch (AccessControlException e) {
            setAccessibleEnable = false;
            return false;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName, Field[] declaredFields) {
        return getField(clazz, fieldName, declaredFields, null);
    }

    public static Field getField(Class<?> clazz, String fieldName, Field[] declaredFields, Map<Class<?>, Field[]> classFieldCache) {
        Field field = getField0(clazz, fieldName, declaredFields, classFieldCache);
        if (field == null) {
            field = getField0(clazz, "_" + fieldName, declaredFields, classFieldCache);
        }
        if (field == null) {
            field = getField0(clazz, "m_" + fieldName, declaredFields, classFieldCache);
        }
        if (field == null) {
            return getField0(clazz, "m" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), declaredFields, classFieldCache);
        }
        return field;
    }

    private static Field getField0(Class<?> clazz, String fieldName, Field[] declaredFields, Map<Class<?>, Field[]> classFieldCache) {
        Field[] superClassFields;
        for (Field item : declaredFields) {
            String itemName = item.getName();
            if (fieldName.equals(itemName)) {
                return item;
            }
            if (fieldName.length() > 2) {
                char c0 = fieldName.charAt(0);
                if (c0 >= 'a' && c0 <= 'z') {
                    char c1 = fieldName.charAt(1);
                    if (c1 >= 'A' && c1 <= 'Z' && fieldName.equalsIgnoreCase(itemName)) {
                        return item;
                    }
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null || superClass == Object.class) {
            return null;
        }
        if (classFieldCache != null) {
            superClassFields = (Field[]) classFieldCache.get(superClass);
        } else {
            superClassFields = null;
        }
        if (superClassFields == null) {
            superClassFields = superClass.getDeclaredFields();
            if (classFieldCache != null) {
                classFieldCache.put(superClass, superClassFields);
            }
        }
        return getField(superClass, fieldName, superClassFields, classFieldCache);
    }

    public static Type getCollectionItemType(Type fieldType) {
        Type itemType = null;
        if (fieldType instanceof ParameterizedType) {
            Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            if (actualTypeArgument instanceof WildcardType) {
                Type[] upperBounds = ((WildcardType) actualTypeArgument).getUpperBounds();
                if (upperBounds.length == 1) {
                    actualTypeArgument = upperBounds[0];
                }
            }
            itemType = actualTypeArgument;
        } else if (fieldType instanceof Class) {
            Class<?> clazz = (Class) fieldType;
            if (!clazz.getName().startsWith("java.")) {
                itemType = getCollectionItemType(clazz.getGenericSuperclass());
            }
        }
        if (itemType == null) {
            return Object.class;
        }
        return itemType;
    }

    public static Object defaultValue(Class<?> fieldType) {
        if (fieldType == Byte.TYPE) {
            return Byte.valueOf(0);
        }
        if (fieldType == Short.TYPE) {
            return Short.valueOf(0);
        }
        if (fieldType == Integer.TYPE) {
            return Integer.valueOf(0);
        }
        if (fieldType == Long.TYPE) {
            return Long.valueOf(0);
        }
        if (fieldType == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (fieldType == Double.TYPE) {
            return Double.valueOf(Utils.DOUBLE_EPSILON);
        }
        if (fieldType == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (fieldType == Character.TYPE) {
            return Character.valueOf('0');
        }
        return null;
    }

    public static boolean getArgument(Type[] typeArgs, TypeVariable[] typeVariables, Type[] arguments) {
        if (arguments == null || typeVariables.length == 0) {
            return false;
        }
        boolean changed = false;
        for (int i = 0; i < typeArgs.length; i++) {
            Type typeArg = typeArgs[i];
            if (typeArg instanceof ParameterizedType) {
                ParameterizedType p_typeArg = (ParameterizedType) typeArg;
                Type[] p_typeArg_args = p_typeArg.getActualTypeArguments();
                if (getArgument(p_typeArg_args, typeVariables, arguments)) {
                    typeArgs[i] = new ParameterizedTypeImpl(p_typeArg_args, p_typeArg.getOwnerType(), p_typeArg.getRawType());
                    changed = true;
                }
            } else if (typeArg instanceof TypeVariable) {
                for (int j = 0; j < typeVariables.length; j++) {
                    if (typeArg.equals(typeVariables[j])) {
                        typeArgs[i] = arguments[j];
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    public static double parseDouble(String str) {
        int len = str.length();
        if (len > 10) {
            return Double.parseDouble(str);
        }
        boolean negative = false;
        long longValue = 0;
        int scale = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (ch == '.') {
                if (scale != 0) {
                    return Double.parseDouble(str);
                }
                scale = (len - i) - 1;
            } else if (ch < '0' || ch > '9') {
                return Double.parseDouble(str);
            } else {
                longValue = (10 * longValue) + ((long) (ch - '0'));
            }
        }
        if (negative) {
            longValue = -longValue;
        }
        switch (scale) {
            case 0:
                return (double) longValue;
            case 1:
                return ((double) longValue) / 10.0d;
            case 2:
                return ((double) longValue) / 100.0d;
            case 3:
                return ((double) longValue) / 1000.0d;
            case 4:
                return ((double) longValue) / 10000.0d;
            case 5:
                return ((double) longValue) / 100000.0d;
            case 6:
                return ((double) longValue) / 1000000.0d;
            case 7:
                return ((double) longValue) / 1.0E7d;
            case 8:
                return ((double) longValue) / 1.0E8d;
            case 9:
                return ((double) longValue) / 1.0E9d;
            default:
                return Double.parseDouble(str);
        }
    }

    public static float parseFloat(String str) {
        int len = str.length();
        if (len >= 10) {
            return Float.parseFloat(str);
        }
        boolean negative = false;
        long longValue = 0;
        int scale = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (ch == '.') {
                if (scale != 0) {
                    return Float.parseFloat(str);
                }
                scale = (len - i) - 1;
            } else if (ch < '0' || ch > '9') {
                return Float.parseFloat(str);
            } else {
                longValue = (10 * longValue) + ((long) (ch - '0'));
            }
        }
        if (negative) {
            longValue = -longValue;
        }
        switch (scale) {
            case 0:
                return (float) longValue;
            case 1:
                return ((float) longValue) / 10.0f;
            case 2:
                return ((float) longValue) / 100.0f;
            case 3:
                return ((float) longValue) / 1000.0f;
            case 4:
                return ((float) longValue) / 10000.0f;
            case 5:
                return ((float) longValue) / 100000.0f;
            case 6:
                return ((float) longValue) / 1000000.0f;
            case 7:
                return ((float) longValue) / 1.0E7f;
            case 8:
                return ((float) longValue) / 1.0E8f;
            case 9:
                return ((float) longValue) / 1.0E9f;
            default:
                return Float.parseFloat(str);
        }
    }

    public static long fnv_64_lower(String key) {
        if (key == null) {
            return 0;
        }
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (!(ch == '_' || ch == '-')) {
                if (ch >= 'A' && ch <= 'Z') {
                    ch = (char) (ch + ' ');
                }
                hashCode = (hashCode ^ ((long) ch)) * 1099511628211L;
            }
        }
        return hashCode;
    }

    public static void addMapping(String className, Class<?> clazz) {
        mappings.put(className, clazz);
    }

    public static Type checkPrimitiveArray(GenericArrayType genericArrayType) {
        GenericArrayType genericArrayType2 = genericArrayType;
        Type genericComponentType = genericArrayType.getGenericComponentType();
        String prefix = "[";
        while (genericComponentType instanceof GenericArrayType) {
            genericComponentType = ((GenericArrayType) genericComponentType).getGenericComponentType();
            prefix = prefix + prefix;
        }
        if (!(genericComponentType instanceof Class)) {
            return genericArrayType2;
        }
        Class<?> ck = (Class) genericComponentType;
        if (!ck.isPrimitive()) {
            return genericArrayType2;
        }
        try {
            if (ck == Boolean.TYPE) {
                return Class.forName(prefix + "Z");
            }
            if (ck == Character.TYPE) {
                return Class.forName(prefix + "C");
            }
            if (ck == Byte.TYPE) {
                return Class.forName(prefix + "B");
            }
            if (ck == Short.TYPE) {
                return Class.forName(prefix + GpsLatitudeRef.SOUTH);
            }
            if (ck == Integer.TYPE) {
                return Class.forName(prefix + "I");
            }
            if (ck == Long.TYPE) {
                return Class.forName(prefix + "J");
            }
            if (ck == Float.TYPE) {
                return Class.forName(prefix + "F");
            }
            if (ck == Double.TYPE) {
                return Class.forName(prefix + "D");
            }
            return genericArrayType2;
        } catch (ClassNotFoundException e) {
            return genericArrayType2;
        }
    }
}
