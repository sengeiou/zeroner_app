package com.alibaba.json;

import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.parser.JSONLexer;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.parser.deserializer.ExtraProcessor;
import com.alibaba.json.parser.deserializer.ExtraTypeProvider;
import com.alibaba.json.parser.deserializer.FieldTypeResolver;
import com.alibaba.json.parser.deserializer.ParseProcess;
import com.alibaba.json.serializer.AfterFilter;
import com.alibaba.json.serializer.BeforeFilter;
import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.JavaBeanSerializer;
import com.alibaba.json.serializer.NameFilter;
import com.alibaba.json.serializer.ObjectSerializer;
import com.alibaba.json.serializer.PropertyFilter;
import com.alibaba.json.serializer.PropertyPreFilter;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializeFilter;
import com.alibaba.json.serializer.SerializeWriter;
import com.alibaba.json.serializer.SerializerFeature;
import com.alibaba.json.serializer.ValueFilter;
import com.alibaba.json.util.TypeUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

public abstract class JSON implements JSONStreamAware, JSONAware {
    public static int DEFAULT_GENERATE_FEATURE = ((((0 | SerializerFeature.QuoteFieldNames.mask) | SerializerFeature.SkipTransientField.mask) | SerializerFeature.WriteEnumUsingToString.mask) | SerializerFeature.SortField.mask);
    public static int DEFAULT_PARSER_FEATURE = (((0 | Feature.UseBigDecimal.mask) | Feature.SortFeidFastMatch.mask) | Feature.IgnoreNotMatch.mask);
    public static final String DEFAULT_TYPE_KEY = "@type";
    public static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String VERSION = "1.1.64";
    public static Locale defaultLocale = Locale.getDefault();
    public static TimeZone defaultTimeZone = TimeZone.getDefault();

    public static final Object parse(String text) {
        return parse(text, DEFAULT_PARSER_FEATURE);
    }

    public static final Object parse(String text, int features) {
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.global, features);
        Object value = parser.parse(null);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static final Object parse(byte[] input, Feature... features) {
        try {
            return parseObject(new String(input, "UTF-8"), features);
        } catch (UnsupportedEncodingException e) {
            throw new JSONException("UTF-8 not support", e);
        }
    }

    public static final Object parse(String text, Feature... features) {
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature feature : features) {
            featureValues |= feature.mask;
        }
        return parse(text, featureValues);
    }

    public static final JSONObject parseObject(String text, Feature... features) {
        return (JSONObject) parse(text, features);
    }

    public static final JSONObject parseObject(String text) {
        Object obj = parse(text);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        return (JSONObject) toJSON(obj);
    }

    public static final <T> T parseObject(String text, TypeReference<T> type, Feature... features) {
        return parseObject(text, type.type, ParserConfig.global, DEFAULT_PARSER_FEATURE, features);
    }

    public static final <T> T parseObject(String text, Class<T> clazz, Feature... features) {
        return parseObject(text, clazz, ParserConfig.global, DEFAULT_PARSER_FEATURE, features);
    }

    public static final <T> T parseObject(String text, Class<T> clazz, ParseProcess processor, Feature... features) {
        return parseObject(text, clazz, ParserConfig.global, processor, DEFAULT_PARSER_FEATURE, features);
    }

    public static final <T> T parseObject(String input, Type clazz, Feature... features) {
        return parseObject(input, clazz, ParserConfig.global, DEFAULT_PARSER_FEATURE, features);
    }

    public static final <T> T parseObject(String input, Type clazz, ParseProcess processor, Feature... features) {
        return parseObject(input, clazz, ParserConfig.global, processor, DEFAULT_PARSER_FEATURE, features);
    }

    public static final <T> T parseObject(String input, Type clazz, int featureValues, Feature... features) {
        if (input == null) {
            return null;
        }
        for (Feature feature : features) {
            featureValues |= feature.mask;
        }
        DefaultJSONParser parser = new DefaultJSONParser(input, ParserConfig.global, featureValues);
        T value = parser.parseObject(clazz);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static final <T> T parseObject(String input, Type clazz, ParserConfig config, int featureValues, Feature... features) {
        return parseObject(input, clazz, config, null, featureValues, features);
    }

    public static final <T> T parseObject(String input, Type clazz, ParserConfig config, ParseProcess processor, int featureValues, Feature... features) {
        if (input == null) {
            return null;
        }
        for (Feature feature : features) {
            featureValues |= feature.mask;
        }
        DefaultJSONParser parser = new DefaultJSONParser(input, config, featureValues);
        if (processor instanceof ExtraTypeProvider) {
            parser.getExtraTypeProviders().add((ExtraTypeProvider) processor);
        }
        if (processor instanceof ExtraProcessor) {
            parser.getExtraProcessors().add((ExtraProcessor) processor);
        }
        if (processor instanceof FieldTypeResolver) {
            parser.fieldTypeResolver = (FieldTypeResolver) processor;
        }
        T value = parser.parseObject(clazz);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static final <T> T parseObject(byte[] input, Type clazz, Feature... features) {
        try {
            return parseObject(new String(input, "UTF-8"), clazz, features);
        } catch (UnsupportedEncodingException e) {
            throw new JSONException("UTF-8 not support");
        }
    }

    public static final <T> T parseObject(char[] input, int length, Type clazz, Feature... features) {
        if (input == null || input.length == 0) {
            return null;
        }
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature feature : features) {
            featureValues |= feature.mask;
        }
        DefaultJSONParser parser = new DefaultJSONParser(input, length, ParserConfig.global, featureValues);
        T value = parser.parseObject(clazz);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static final <T> T parseObject(String text, Class<T> clazz) {
        return parseObject(text, clazz, new Feature[0]);
    }

    public static final JSONArray parseArray(String text) {
        JSONArray array;
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.global);
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken();
            array = null;
        } else if (token == 20) {
            array = null;
        } else {
            array = new JSONArray();
            parser.parseArray((Collection) array);
            parser.handleResovleTask(array);
        }
        parser.close();
        return array;
    }

    public static final <T> List<T> parseArray(String text, Class<T> clazz) {
        List<T> list;
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.global);
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken();
            list = null;
        } else if (token != 20 || !lexer.isBlankInput()) {
            list = new ArrayList<>();
            parser.parseArray(clazz, (Collection) list);
            parser.handleResovleTask(list);
        } else {
            list = null;
        }
        parser.close();
        return list;
    }

    public static final List<Object> parseArray(String text, Type[] types) {
        List<Object> list;
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.global);
        Object[] objectArray = parser.parseArray(types);
        if (objectArray == null) {
            list = null;
        } else {
            list = Arrays.asList(objectArray);
        }
        parser.handleResovleTask(list);
        parser.close();
        return list;
    }

    public static Object parse(String text, ParserConfig config) {
        return parse(text, config, DEFAULT_PARSER_FEATURE);
    }

    public static Object parse(String text, ParserConfig config, int features) {
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, config, features);
        Object value = parser.parse();
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static final String toJSONString(Object object) {
        return toJSONString(object, SerializeConfig.globalInstance, null, null, DEFAULT_GENERATE_FEATURE, new SerializerFeature[0]);
    }

    public static final String toJSONString(Object object, SerializerFeature... features) {
        return toJSONString(object, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final String toJSONString(Object object, int defaultFeatures, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, null, null, defaultFeatures, features);
    }

    public static final String toJSONStringWithDateFormat(Object object, String dateFormat, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, null, dateFormat, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final String toJSONString(Object object, SerializeFilter filter, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, new SerializeFilter[]{filter}, null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, filters, null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final byte[] toJSONBytes(Object object, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter(null, DEFAULT_GENERATE_FEATURE, features);
        try {
            new JSONSerializer(out, SerializeConfig.globalInstance).write(object);
            return out.toBytes("UTF-8");
        } finally {
            out.close();
        }
    }

    public static final String toJSONString(Object object, SerializeConfig config, SerializerFeature... features) {
        return toJSONString(object, config, null, null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final String toJSONString(Object object, SerializeConfig config, SerializeFilter filter, SerializerFeature... features) {
        return toJSONString(object, config, new SerializeFilter[]{filter}, null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final String toJSONString(Object object, SerializeConfig config, SerializeFilter[] filters, SerializerFeature... features) {
        return toJSONString(object, config, filters, null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final String toJSONStringZ(Object object, SerializeConfig mapping, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, null, null, 0, features);
    }

    public static final byte[] toJSONBytes(Object object, SerializeConfig config, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter(null, DEFAULT_GENERATE_FEATURE, features);
        try {
            new JSONSerializer(out, config).write(object);
            return out.toBytes("UTF-8");
        } finally {
            out.close();
        }
    }

    public static final String toJSONString(Object object, boolean prettyFormat) {
        if (!prettyFormat) {
            return toJSONString(object);
        }
        return toJSONString(object, SerializerFeature.PrettyFormat);
    }

    public static final void writeJSONStringTo(Object object, Writer writer, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter(writer, DEFAULT_GENERATE_FEATURE, features);
        try {
            new JSONSerializer(out, SerializeConfig.globalInstance).write(object);
        } finally {
            out.close();
        }
    }

    public String toString() {
        return toJSONString();
    }

    public String toJSONString() {
        SerializeWriter out = new SerializeWriter(null, DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
        try {
            new JSONSerializer(out, SerializeConfig.globalInstance).write((Object) this);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public void writeJSONString(Appendable appendable) {
        SerializeWriter out = new SerializeWriter(null, DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
        try {
            new JSONSerializer(out, SerializeConfig.globalInstance).write((Object) this);
            appendable.append(out.toString());
            out.close();
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        } catch (Throwable th) {
            out.close();
            throw th;
        }
    }

    public static final Object toJSON(Object javaObject) {
        return toJSON(javaObject, SerializeConfig.globalInstance);
    }

    @Deprecated
    public static final Object toJSON(Object javaObject, ParserConfig mapping) {
        return toJSON(javaObject, SerializeConfig.globalInstance);
    }

    public static Object toJSON(Object javaObject, SerializeConfig config) {
        Map innerMap;
        if (javaObject == null) {
            return null;
        }
        if (javaObject instanceof JSON) {
            return (JSON) javaObject;
        }
        if (javaObject instanceof Map) {
            Map<Object, Object> map = (Map) javaObject;
            int size = map.size();
            if (map instanceof LinkedHashMap) {
                innerMap = new LinkedHashMap(size);
            } else if (map instanceof TreeMap) {
                innerMap = new TreeMap();
            } else {
                innerMap = new HashMap(size);
            }
            JSONObject json = new JSONObject(innerMap);
            for (Entry<Object, Object> entry : map.entrySet()) {
                json.put(TypeUtils.castToString(entry.getKey()), toJSON(entry.getValue()));
            }
            return json;
        } else if (javaObject instanceof Collection) {
            Collection<Object> collection = (Collection) javaObject;
            JSONArray array = new JSONArray(collection.size());
            for (Object item : collection) {
                array.add(toJSON(item));
            }
            return array;
        } else {
            Class<?> clazz = javaObject.getClass();
            if (clazz.isEnum()) {
                return ((Enum) javaObject).name();
            }
            if (clazz.isArray()) {
                int len = Array.getLength(javaObject);
                JSONArray array2 = new JSONArray(len);
                for (int i = 0; i < len; i++) {
                    array2.add(toJSON(Array.get(javaObject, i)));
                }
                return array2;
            } else if (ParserConfig.isPrimitive(clazz)) {
                return javaObject;
            } else {
                ObjectSerializer serializer = config.get(clazz);
                if (!(serializer instanceof JavaBeanSerializer)) {
                    return null;
                }
                JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) serializer;
                JSONObject json2 = new JSONObject();
                try {
                    for (Entry<String, Object> entry2 : javaBeanSerializer.getFieldValuesMap(javaObject).entrySet()) {
                        json2.put((String) entry2.getKey(), toJSON(entry2.getValue()));
                    }
                    return json2;
                } catch (Exception e) {
                    JSONException jSONException = new JSONException("toJSON error", e);
                    throw jSONException;
                }
            }
        }
    }

    public static final <T> T toJavaObject(JSON json, Class<T> clazz) {
        return TypeUtils.cast((Object) json, clazz, ParserConfig.global);
    }

    public <T> T toJavaObject(Class<T> clazz) {
        return TypeUtils.cast((Object) this, clazz, ParserConfig.getGlobalInstance());
    }

    public static String toJSONString(Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter(null, defaultFeatures, features);
        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            for (SerializerFeature feature : features) {
                serializer.config(feature, true);
            }
            if (!(dateFormat == null || dateFormat.length() == 0)) {
                serializer.setDateFormat(dateFormat);
                serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            }
            if (filters != null) {
                for (SerializeFilter filter : filters) {
                    if (filter != null) {
                        if (filter instanceof PropertyPreFilter) {
                            serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
                        }
                        if (filter instanceof NameFilter) {
                            serializer.getNameFilters().add((NameFilter) filter);
                        }
                        if (filter instanceof ValueFilter) {
                            serializer.getValueFilters().add((ValueFilter) filter);
                        }
                        if (filter instanceof PropertyFilter) {
                            serializer.getPropertyFilters().add((PropertyFilter) filter);
                        }
                        if (filter instanceof BeforeFilter) {
                            serializer.getBeforeFilters().add((BeforeFilter) filter);
                        }
                        if (filter instanceof AfterFilter) {
                            serializer.getAfterFilters().add((AfterFilter) filter);
                        }
                    }
                }
            }
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }
}
