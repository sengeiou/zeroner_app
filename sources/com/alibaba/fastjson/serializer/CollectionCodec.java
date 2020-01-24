package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class CollectionCodec implements ObjectSerializer, ObjectDeserializer {
    public static final CollectionCodec instance = new CollectionCodec();

    private CollectionCodec() {
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
        SerializeWriter out = serializer.out;
        if (object != null) {
            Type elementType = null;
            if ((out.features & SerializerFeature.WriteClassName.mask) != 0) {
                elementType = TypeUtils.getCollectionItemType(fieldType);
            }
            Collection<?> collection = (Collection) object;
            SerialContext context = serializer.context;
            serializer.setContext(context, object, fieldName, 0);
            if ((out.features & SerializerFeature.WriteClassName.mask) != 0) {
                if (HashSet.class == collection.getClass()) {
                    out.append((CharSequence) "Set");
                } else if (TreeSet.class == collection.getClass()) {
                    out.append((CharSequence) "TreeSet");
                }
            }
            try {
                out.write(91);
                int i = 0;
                for (Object item : collection) {
                    try {
                        int i2 = i + 1;
                        if (i != 0) {
                            out.write(44);
                        }
                        if (item == null) {
                            out.writeNull();
                            i = i2;
                        } else {
                            Class<?> clazz = item.getClass();
                            if (clazz == Integer.class) {
                                out.writeInt(((Integer) item).intValue());
                                i = i2;
                            } else if (clazz == Long.class) {
                                out.writeLong(((Long) item).longValue());
                                if ((out.features & SerializerFeature.WriteClassName.mask) != 0) {
                                    out.write(76);
                                    i = i2;
                                } else {
                                    i = i2;
                                }
                            } else {
                                serializer.config.get(clazz).write(serializer, item, Integer.valueOf(i2 - 1), elementType);
                                i = i2;
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        int i3 = i;
                        serializer.context = context;
                        throw th;
                    }
                }
                out.write(93);
                serializer.context = context;
            } catch (Throwable th2) {
                th = th2;
                serializer.context = context;
                throw th;
            }
        } else if ((out.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0) {
            out.write("[]");
        } else {
            out.writeNull();
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Collection list;
        Type itemType;
        if (parser.lexer.token() == 8) {
            parser.lexer.nextToken(16);
            return null;
        } else if (type == JSONArray.class) {
            JSONArray array = new JSONArray();
            parser.parseArray((Collection) array);
            return array;
        } else {
            Type t = type;
            while (!(t instanceof Class)) {
                if (t instanceof ParameterizedType) {
                    t = ((ParameterizedType) t).getRawType();
                } else {
                    throw new JSONException("TODO");
                }
            }
            Class<?> rawClass = (Class) t;
            if (rawClass == AbstractCollection.class || rawClass == Collection.class) {
                list = new ArrayList();
            } else if (rawClass.isAssignableFrom(HashSet.class)) {
                list = new HashSet();
            } else if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
                list = new LinkedHashSet();
            } else if (rawClass.isAssignableFrom(TreeSet.class)) {
                list = new TreeSet();
            } else if (rawClass.isAssignableFrom(ArrayList.class)) {
                list = new ArrayList();
            } else if (rawClass.isAssignableFrom(EnumSet.class)) {
                if (type instanceof ParameterizedType) {
                    itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
                } else {
                    itemType = Object.class;
                }
                list = EnumSet.noneOf((Class) itemType);
            } else {
                try {
                    list = (Collection) rawClass.newInstance();
                } catch (Exception e) {
                    throw new JSONException("create instane error, class " + rawClass.getName());
                }
            }
            parser.parseArray(TypeUtils.getCollectionItemType(type), list, fieldName);
            return list;
        }
    }
}
