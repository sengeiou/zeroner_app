package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;
import java.util.List;

public final class ListSerializer implements ObjectSerializer {
    /* JADX INFO: finally extract failed */
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
        SerializeWriter out = serializer.out;
        boolean writeClassName = (out.features & SerializerFeature.WriteClassName.mask) != 0;
        Type elementType = null;
        if (writeClassName) {
            elementType = TypeUtils.getCollectionItemType(fieldType);
        }
        if (object != null) {
            List<?> list = (List) object;
            int size = list.size();
            if (size == 0) {
                out.append((CharSequence) "[]");
                return;
            }
            SerialContext context = serializer.context;
            if ((out.features & SerializerFeature.DisableCircularReferenceDetect.mask) == 0) {
                SerialContext serialContext = new SerialContext(context, object, fieldName, 0);
                serializer.context = serialContext;
                if (serializer.references == null) {
                    serializer.references = new IdentityHashMap();
                }
                serializer.references.put(object, serializer.context);
            }
            try {
                if ((out.features & SerializerFeature.PrettyFormat.mask) != 0) {
                    out.write(91);
                    serializer.incrementIndent();
                    for (int i = 0; i < size; i++) {
                        Object item = list.get(i);
                        if (i != 0) {
                            out.write(44);
                        }
                        serializer.println();
                        if (item == null) {
                            serializer.out.writeNull();
                        } else if (serializer.references == null || !serializer.references.containsKey(item)) {
                            ObjectSerializer itemSerializer = serializer.config.get(item.getClass());
                            serializer.context = new SerialContext(context, object, fieldName, 0);
                            itemSerializer.write(serializer, item, Integer.valueOf(i), elementType);
                        } else {
                            serializer.writeReference(item);
                        }
                    }
                    serializer.decrementIdent();
                    serializer.println();
                    out.write(93);
                    serializer.context = context;
                    return;
                }
                int newcount = out.count + 1;
                if (newcount > out.buf.length) {
                    if (out.writer == null) {
                        out.expandCapacity(newcount);
                    } else {
                        out.flush();
                        newcount = 1;
                    }
                }
                out.buf[out.count] = '[';
                out.count = newcount;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    Object item2 = list.get(i2);
                    if (i2 != 0) {
                        int newcount2 = out.count + 1;
                        if (newcount2 > out.buf.length) {
                            if (out.writer == null) {
                                out.expandCapacity(newcount2);
                            } else {
                                out.flush();
                                newcount2 = 1;
                            }
                        }
                        out.buf[out.count] = ',';
                        out.count = newcount2;
                    }
                    if (item2 == null) {
                        out.append((CharSequence) "null");
                    } else {
                        Class<?> clazz = item2.getClass();
                        if (clazz == Integer.class) {
                            out.writeInt(((Integer) item2).intValue());
                        } else if (clazz == Long.class) {
                            long val = ((Long) item2).longValue();
                            if (writeClassName) {
                                out.writeLong(val);
                                out.write(76);
                            } else {
                                out.writeLong(val);
                            }
                        } else if (clazz == String.class) {
                            String itemStr = (String) item2;
                            if ((out.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                                out.writeStringWithSingleQuote(itemStr);
                            } else {
                                out.writeStringWithDoubleQuote(itemStr, 0, true);
                            }
                        } else {
                            if ((out.features & SerializerFeature.DisableCircularReferenceDetect.mask) == 0) {
                                serializer.context = new SerialContext(context, object, fieldName, 0);
                            }
                            if (serializer.references == null || !serializer.references.containsKey(item2)) {
                                serializer.config.get(item2.getClass()).write(serializer, item2, Integer.valueOf(i2), elementType);
                            } else {
                                serializer.writeReference(item2);
                            }
                        }
                    }
                }
                int newcount3 = out.count + 1;
                if (newcount3 > out.buf.length) {
                    if (out.writer == null) {
                        out.expandCapacity(newcount3);
                    } else {
                        out.flush();
                        newcount3 = 1;
                    }
                }
                out.buf[out.count] = ']';
                out.count = newcount3;
                serializer.context = context;
            } catch (Throwable th) {
                serializer.context = context;
                throw th;
            }
        } else if ((out.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0) {
            out.write("[]");
        } else {
            out.writeNull();
        }
    }
}
