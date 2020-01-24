package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JavaBeanSerializer implements ObjectSerializer {
    private static final char[] false_chars = {'f', 'a', 'l', 's', 'e'};
    private static final char[] true_chars = {'t', 'r', 'u', 'e'};
    protected int features;
    private final FieldSerializer[] getters;
    private final FieldSerializer[] sortedGetters;
    protected final String typeKey;
    protected final String typeName;

    public JavaBeanSerializer(Class<?> clazz) {
        this(clazz, (PropertyNamingStrategy) null);
    }

    public JavaBeanSerializer(Class<?> clazz, PropertyNamingStrategy propertyNamingStrategy) {
        this(clazz, clazz.getModifiers(), null, false, true, true, true, propertyNamingStrategy);
    }

    public JavaBeanSerializer(Class<?> clazz, String... aliasList) {
        this(clazz, clazz.getModifiers(), map(aliasList), false, true, true, true, null);
    }

    private static Map<String, String> map(String... aliasList) {
        Map<String, String> aliasMap = new HashMap<>();
        for (String alias : aliasList) {
            aliasMap.put(alias, alias);
        }
        return aliasMap;
    }

    public JavaBeanSerializer(Class<?> clazz, int classModifiers, Map<String, String> aliasMap, boolean fieldOnly, boolean jsonTypeSupport, boolean jsonFieldSupport, boolean fieldGenericSupport, PropertyNamingStrategy propertyNamingStrategy) {
        JSONType jsonType;
        this.features = 0;
        if (jsonTypeSupport) {
            jsonType = (JSONType) clazz.getAnnotation(JSONType.class);
        } else {
            jsonType = null;
        }
        String typeName2 = null;
        String typeKey2 = null;
        if (jsonType != null) {
            this.features = SerializerFeature.of(jsonType.serialzeFeatures());
            typeName2 = jsonType.typeName();
            if (typeName2.length() == 0) {
                typeName2 = null;
            } else {
                Class<?> supperClass = clazz.getSuperclass();
                while (supperClass != null && supperClass != Object.class) {
                    JSONType superJsonType = (JSONType) supperClass.getAnnotation(JSONType.class);
                    if (superJsonType != null) {
                        typeKey2 = superJsonType.typeKey();
                        if (typeKey2.length() != 0) {
                            break;
                        }
                        supperClass = supperClass.getSuperclass();
                    } else {
                        break;
                    }
                }
                for (Class<?> interfaceClass : clazz.getInterfaces()) {
                    JSONType superJsonType2 = (JSONType) interfaceClass.getAnnotation(JSONType.class);
                    if (superJsonType2 != null) {
                        typeKey2 = superJsonType2.typeKey();
                        if (typeKey2.length() != 0) {
                            break;
                        }
                    }
                }
                if (typeKey2 != null && typeKey2.length() == 0) {
                    typeKey2 = null;
                }
            }
            if (propertyNamingStrategy == null) {
                PropertyNamingStrategy typeNaming = jsonType.naming();
                if (typeNaming != PropertyNamingStrategy.CamelCase) {
                    propertyNamingStrategy = typeNaming;
                }
            }
        }
        this.typeName = typeName2;
        this.typeKey = typeKey2;
        List<FieldInfo> fieldInfoList = TypeUtils.computeGetters(clazz, classModifiers, fieldOnly, jsonType, aliasMap, false, jsonFieldSupport, fieldGenericSupport, propertyNamingStrategy);
        List<FieldSerializer> getterList = new ArrayList<>();
        for (FieldInfo fieldInfo : fieldInfoList) {
            getterList.add(new FieldSerializer(fieldInfo));
        }
        this.getters = (FieldSerializer[]) getterList.toArray(new FieldSerializer[getterList.size()]);
        String[] orders = null;
        if (jsonType != null) {
            orders = jsonType.orders();
        }
        if (orders == null || orders.length == 0) {
            FieldSerializer[] sortedGetters2 = new FieldSerializer[this.getters.length];
            System.arraycopy(this.getters, 0, sortedGetters2, 0, this.getters.length);
            Arrays.sort(sortedGetters2);
            if (Arrays.equals(sortedGetters2, this.getters)) {
                this.sortedGetters = this.getters;
            } else {
                this.sortedGetters = sortedGetters2;
            }
        } else {
            List<FieldInfo> fieldInfoList2 = TypeUtils.computeGetters(clazz, classModifiers, fieldOnly, jsonType, aliasMap, true, jsonFieldSupport, fieldGenericSupport, propertyNamingStrategy);
            List<FieldSerializer> getterList2 = new ArrayList<>();
            for (FieldInfo fieldInfo2 : fieldInfoList2) {
                getterList2.add(new FieldSerializer(fieldInfo2));
            }
            this.sortedGetters = (FieldSerializer[]) getterList2.toArray(new FieldSerializer[getterList2.size()]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:136:0x0377 A[Catch:{ Exception -> 0x06e0, all -> 0x0712 }] */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x047d A[Catch:{ Exception -> 0x06e0, all -> 0x0712 }, LOOP:5: B:178:0x0477->B:180:0x047d, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x0574 A[Catch:{ Exception -> 0x06e0, all -> 0x0712 }] */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x05cd A[Catch:{ Exception -> 0x06e0, all -> 0x0712 }] */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x06c8 A[Catch:{ Exception -> 0x06e0, all -> 0x0712 }] */
    /* JADX WARNING: Removed duplicated region for block: B:429:0x038c A[EDGE_INSN: B:429:0x038c->B:139:0x038c ?: BREAK  
    EDGE_INSN: B:429:0x038c->B:139:0x038c ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r73, java.lang.Object r74, java.lang.Object r75, java.lang.reflect.Type r76) throws java.io.IOException {
        /*
            r72 = this;
            r0 = r73
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r0.out
            r44 = r0
            if (r74 != 0) goto L_0x000c
            r44.writeNull()
        L_0x000b:
            return
        L_0x000c:
            r0 = r73
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.context
            r68 = r0
            if (r68 == 0) goto L_0x002c
            r0 = r73
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.context
            r68 = r0
            r0 = r68
            int r0 = r0.features
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect
            r0 = r69
            int r0 = r0.mask
            r69 = r0
            r68 = r68 & r69
            if (r68 != 0) goto L_0x0048
        L_0x002c:
            r0 = r73
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r0 = r0.references
            r68 = r0
            if (r68 == 0) goto L_0x0048
            r0 = r73
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r0 = r0.references
            r68 = r0
            r0 = r68
            r1 = r74
            boolean r68 = r0.containsKey(r1)
            if (r68 == 0) goto L_0x0048
            r73.writeReference(r74)
            goto L_0x000b
        L_0x0048:
            r0 = r44
            int r0 = r0.features
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.SortField
            r0 = r69
            int r0 = r0.mask
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x0201
            r0 = r72
            com.alibaba.fastjson.serializer.FieldSerializer[] r0 = r0.sortedGetters
            r28 = r0
        L_0x0060:
            r0 = r73
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.context
            r45 = r0
            r0 = r44
            int r0 = r0.features
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect
            r0 = r69
            int r0 = r0.mask
            r69 = r0
            r68 = r68 & r69
            if (r68 != 0) goto L_0x00bb
            com.alibaba.fastjson.serializer.SerialContext r68 = new com.alibaba.fastjson.serializer.SerialContext
            r0 = r72
            int r0 = r0.features
            r69 = r0
            r0 = r68
            r1 = r45
            r2 = r74
            r3 = r75
            r4 = r69
            r0.<init>(r1, r2, r3, r4)
            r0 = r68
            r1 = r73
            r1.context = r0
            r0 = r73
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r0 = r0.references
            r68 = r0
            if (r68 != 0) goto L_0x00a6
            java.util.IdentityHashMap r68 = new java.util.IdentityHashMap
            r68.<init>()
            r0 = r68
            r1 = r73
            r1.references = r0
        L_0x00a6:
            r0 = r73
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r0 = r0.references
            r68 = r0
            r0 = r73
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.context
            r69 = r0
            r0 = r68
            r1 = r74
            r2 = r69
            r0.put(r1, r2)
        L_0x00bb:
            r0 = r72
            int r0 = r0.features
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.BeanToArray
            r0 = r69
            int r0 = r0.mask
            r69 = r0
            r68 = r68 & r69
            if (r68 != 0) goto L_0x00df
            r0 = r44
            int r0 = r0.features
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.BeanToArray
            r0 = r69
            int r0 = r0.mask
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x0209
        L_0x00df:
            r66 = 1
        L_0x00e1:
            if (r66 == 0) goto L_0x020d
            r59 = 91
        L_0x00e5:
            if (r66 == 0) goto L_0x0211
            r18 = 93
        L_0x00e9:
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            int r39 = r68 + 1
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r39
            r1 = r68
            if (r0 <= r1) goto L_0x0111
            r0 = r44
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 != 0) goto L_0x0215
            r0 = r44
            r1 = r39
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x0111:
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68[r69] = r59     // Catch:{ Exception -> 0x06e0 }
            r0 = r39
            r1 = r44
            r1.count = r0     // Catch:{ Exception -> 0x06e0 }
            r0 = r28
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 <= 0) goto L_0x0144
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x0144
            r73.incrementIndent()     // Catch:{ Exception -> 0x06e0 }
            r73.println()     // Catch:{ Exception -> 0x06e0 }
        L_0x0144:
            r13 = 0
            r0 = r72
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 != 0) goto L_0x0193
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x021c
            if (r76 != 0) goto L_0x0193
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.NotWriteRootClassName     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x0193
            r0 = r73
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.context     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x021c
            r0 = r73
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.context     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.parent     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x021c
        L_0x0193:
            r30 = 1
        L_0x0195:
            if (r30 == 0) goto L_0x01d2
            java.lang.Class r41 = r74.getClass()     // Catch:{ Exception -> 0x06e0 }
            r0 = r41
            r1 = r76
            if (r0 == r1) goto L_0x01d2
            r0 = r72
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x0220
            r0 = r72
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
        L_0x01af:
            r69 = 0
            r0 = r44
            r1 = r68
            r2 = r69
            r0.writeFieldName(r1, r2)     // Catch:{ Exception -> 0x06e0 }
            r0 = r72
            java.lang.String r0 = r0.typeName     // Catch:{ Exception -> 0x06e0 }
            r60 = r0
            if (r60 != 0) goto L_0x01ca
            java.lang.Class r68 = r74.getClass()     // Catch:{ Exception -> 0x06e0 }
            java.lang.String r60 = r68.getName()     // Catch:{ Exception -> 0x06e0 }
        L_0x01ca:
            r0 = r73
            r1 = r60
            r0.write(r1)     // Catch:{ Exception -> 0x06e0 }
            r13 = 1
        L_0x01d2:
            if (r13 == 0) goto L_0x022d
            r56 = 44
        L_0x01d6:
            r38 = r56
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.BeforeFilter> r0 = r0.beforeFilters     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x0230
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.BeforeFilter> r0 = r0.beforeFilters     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            java.util.Iterator r68 = r68.iterator()     // Catch:{ Exception -> 0x06e0 }
        L_0x01ea:
            boolean r69 = r68.hasNext()     // Catch:{ Exception -> 0x06e0 }
            if (r69 == 0) goto L_0x0230
            java.lang.Object r10 = r68.next()     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.BeforeFilter r10 = (com.alibaba.fastjson.serializer.BeforeFilter) r10     // Catch:{ Exception -> 0x06e0 }
            r0 = r73
            r1 = r74
            r2 = r38
            char r38 = r10.writeBefore(r0, r1, r2)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x01ea
        L_0x0201:
            r0 = r72
            com.alibaba.fastjson.serializer.FieldSerializer[] r0 = r0.getters
            r28 = r0
            goto L_0x0060
        L_0x0209:
            r66 = 0
            goto L_0x00e1
        L_0x020d:
            r59 = 123(0x7b, float:1.72E-43)
            goto L_0x00e5
        L_0x0211:
            r18 = 125(0x7d, float:1.75E-43)
            goto L_0x00e9
        L_0x0215:
            r44.flush()     // Catch:{ Exception -> 0x06e0 }
            r39 = 1
            goto L_0x0111
        L_0x021c:
            r30 = 0
            goto L_0x0195
        L_0x0220:
            r0 = r73
            com.alibaba.fastjson.serializer.SerializeConfig r0 = r0.config     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            goto L_0x01af
        L_0x022d:
            r56 = 0
            goto L_0x01d6
        L_0x0230:
            r68 = 44
            r0 = r38
            r1 = r68
            if (r0 != r1) goto L_0x02e4
            r13 = 1
        L_0x0239:
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.QuoteFieldNames     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x02e7
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 != 0) goto L_0x02e7
            r15 = 1
        L_0x025e:
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x02ea
            r61 = 1
        L_0x0272:
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.NotWriteDefaultValue     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x02ed
            r40 = 1
        L_0x0286:
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.PropertyFilter> r0 = r0.propertyFilters     // Catch:{ Exception -> 0x06e0 }
            r47 = r0
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.NameFilter> r0 = r0.nameFilters     // Catch:{ Exception -> 0x06e0 }
            r37 = r0
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.ValueFilter> r0 = r0.valueFilters     // Catch:{ Exception -> 0x06e0 }
            r64 = r0
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.PropertyPreFilter> r0 = r0.propertyPreFilters     // Catch:{ Exception -> 0x06e0 }
            r26 = r0
            r29 = 0
        L_0x02a0:
            r0 = r28
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r29
            r1 = r68
            if (r0 >= r1) goto L_0x09b2
            r24 = r28[r29]     // Catch:{ Exception -> 0x06e0 }
            r0 = r24
            com.alibaba.fastjson.util.FieldInfo r0 = r0.fieldInfo     // Catch:{ Exception -> 0x06e0 }
            r22 = r0
            r0 = r22
            java.lang.Class<?> r0 = r0.fieldClass     // Catch:{ Exception -> 0x06e0 }
            r21 = r0
            r0 = r22
            java.lang.String r0 = r0.name     // Catch:{ Exception -> 0x06e0 }
            r23 = r0
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.SkipTransientField     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x02f0
            r0 = r22
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x06e0 }
            r20 = r0
            if (r20 == 0) goto L_0x02f0
            r0 = r22
            boolean r0 = r0.fieldTransient     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x02f0
        L_0x02e1:
            int r29 = r29 + 1
            goto L_0x02a0
        L_0x02e4:
            r13 = 0
            goto L_0x0239
        L_0x02e7:
            r15 = 0
            goto L_0x025e
        L_0x02ea:
            r61 = 0
            goto L_0x0272
        L_0x02ed:
            r40 = 0
            goto L_0x0286
        L_0x02f0:
            r0 = r72
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x0308
            r0 = r72
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r23
            boolean r68 = r0.equals(r1)     // Catch:{ Exception -> 0x06e0 }
            if (r68 != 0) goto L_0x02e1
        L_0x0308:
            r9 = 1
            if (r26 == 0) goto L_0x032a
            java.util.Iterator r68 = r26.iterator()     // Catch:{ Exception -> 0x06e0 }
        L_0x030f:
            boolean r69 = r68.hasNext()     // Catch:{ Exception -> 0x06e0 }
            if (r69 == 0) goto L_0x032a
            java.lang.Object r25 = r68.next()     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.PropertyPreFilter r25 = (com.alibaba.fastjson.serializer.PropertyPreFilter) r25     // Catch:{ Exception -> 0x06e0 }
            r0 = r25
            r1 = r73
            r2 = r74
            r3 = r23
            boolean r69 = r0.apply(r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            if (r69 != 0) goto L_0x030f
            r9 = 0
        L_0x032a:
            if (r9 == 0) goto L_0x02e1
            r48 = 0
            r51 = 0
            r52 = 0
            r49 = 0
            r50 = 0
            r65 = 0
            r0 = r22
            boolean r0 = r0.fieldAccess     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x0409
            java.lang.Class r68 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x03c3
            r0 = r22
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r74
            int r51 = r0.getInt(r1)     // Catch:{ Exception -> 0x06e0 }
            r65 = 1
        L_0x0358:
            r8 = 1
            if (r47 == 0) goto L_0x0a50
            if (r65 == 0) goto L_0x0a4c
            java.lang.Class r68 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0415
            java.lang.Integer r48 = java.lang.Integer.valueOf(r51)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            r43 = r48
        L_0x036d:
            java.util.Iterator r68 = r47.iterator()     // Catch:{ Exception -> 0x06e0 }
        L_0x0371:
            boolean r69 = r68.hasNext()     // Catch:{ Exception -> 0x06e0 }
            if (r69 == 0) goto L_0x038c
            java.lang.Object r46 = r68.next()     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.PropertyFilter r46 = (com.alibaba.fastjson.serializer.PropertyFilter) r46     // Catch:{ Exception -> 0x06e0 }
            r0 = r46
            r1 = r74
            r2 = r23
            r3 = r43
            boolean r69 = r0.apply(r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            if (r69 != 0) goto L_0x0371
            r8 = 0
        L_0x038c:
            if (r8 == 0) goto L_0x02e1
            r32 = r23
            if (r37 == 0) goto L_0x045d
            if (r65 == 0) goto L_0x03a6
            if (r50 != 0) goto L_0x03a6
            java.lang.Class r68 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0439
            java.lang.Integer r48 = java.lang.Integer.valueOf(r51)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            r43 = r48
        L_0x03a6:
            java.util.Iterator r68 = r37.iterator()     // Catch:{ Exception -> 0x06e0 }
        L_0x03aa:
            boolean r69 = r68.hasNext()     // Catch:{ Exception -> 0x06e0 }
            if (r69 == 0) goto L_0x045d
            java.lang.Object r36 = r68.next()     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.NameFilter r36 = (com.alibaba.fastjson.serializer.NameFilter) r36     // Catch:{ Exception -> 0x06e0 }
            r0 = r36
            r1 = r74
            r2 = r32
            r3 = r43
            java.lang.String r32 = r0.process(r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x03aa
        L_0x03c3:
            java.lang.Class r68 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x03dd
            r0 = r22
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r74
            long r52 = r0.getLong(r1)     // Catch:{ Exception -> 0x06e0 }
            r65 = 1
            goto L_0x0358
        L_0x03dd:
            java.lang.Class r68 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x03f7
            r0 = r22
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r74
            boolean r49 = r0.getBoolean(r1)     // Catch:{ Exception -> 0x06e0 }
            r65 = 1
            goto L_0x0358
        L_0x03f7:
            r0 = r22
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r74
            java.lang.Object r48 = r0.get(r1)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            goto L_0x0358
        L_0x0409:
            r0 = r24
            r1 = r74
            java.lang.Object r48 = r0.getPropertyValue(r1)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            goto L_0x0358
        L_0x0415:
            java.lang.Class r68 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0427
            java.lang.Long r48 = java.lang.Long.valueOf(r52)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            r43 = r48
            goto L_0x036d
        L_0x0427:
            java.lang.Class r68 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0a4c
            java.lang.Boolean r48 = java.lang.Boolean.valueOf(r49)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            r43 = r48
            goto L_0x036d
        L_0x0439:
            java.lang.Class r68 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x044b
            java.lang.Long r48 = java.lang.Long.valueOf(r52)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            r43 = r48
            goto L_0x03a6
        L_0x044b:
            java.lang.Class r68 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x03a6
            java.lang.Boolean r48 = java.lang.Boolean.valueOf(r49)     // Catch:{ Exception -> 0x06e0 }
            r50 = 1
            r43 = r48
            goto L_0x03a6
        L_0x045d:
            if (r64 == 0) goto L_0x04b2
            if (r65 == 0) goto L_0x0a48
            if (r50 != 0) goto L_0x0a48
            java.lang.Class r68 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0490
            java.lang.Integer r48 = java.lang.Integer.valueOf(r51)     // Catch:{ Exception -> 0x06e0 }
            r43 = r48
            r50 = 1
        L_0x0473:
            java.util.Iterator r68 = r64.iterator()     // Catch:{ Exception -> 0x06e0 }
        L_0x0477:
            boolean r69 = r68.hasNext()     // Catch:{ Exception -> 0x06e0 }
            if (r69 == 0) goto L_0x04b4
            java.lang.Object r63 = r68.next()     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.ValueFilter r63 = (com.alibaba.fastjson.serializer.ValueFilter) r63     // Catch:{ Exception -> 0x06e0 }
            r0 = r63
            r1 = r74
            r2 = r23
            r3 = r48
            java.lang.Object r48 = r0.process(r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x0477
        L_0x0490:
            java.lang.Class r68 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x04a1
            java.lang.Long r48 = java.lang.Long.valueOf(r52)     // Catch:{ Exception -> 0x06e0 }
            r43 = r48
            r50 = 1
            goto L_0x0473
        L_0x04a1:
            java.lang.Class r68 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0a48
            java.lang.Boolean r48 = java.lang.Boolean.valueOf(r49)     // Catch:{ Exception -> 0x06e0 }
            r43 = r48
            r50 = 1
            goto L_0x0473
        L_0x04b2:
            r48 = r43
        L_0x04b4:
            if (r50 == 0) goto L_0x06bd
            if (r48 != 0) goto L_0x06bd
            r0 = r22
            int r0 = r0.serialzeFeatures     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r72
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 | r69
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r57 = r68 | r69
            java.lang.Class<java.lang.Boolean> r68 = java.lang.Boolean.class
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x05e4
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullBooleanAsFalse     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r14 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r34 = r14 | r68
            if (r66 != 0) goto L_0x04f6
            r68 = r57 & r34
            if (r68 != 0) goto L_0x04f6
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r34
            if (r68 == 0) goto L_0x02e1
        L_0x04f6:
            r68 = r57 & r14
            if (r68 != 0) goto L_0x0504
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r14
            if (r68 == 0) goto L_0x050a
        L_0x0504:
            r68 = 0
            java.lang.Boolean r48 = java.lang.Boolean.valueOf(r68)     // Catch:{ Exception -> 0x06e0 }
        L_0x050a:
            r69 = r48
        L_0x050c:
            if (r50 == 0) goto L_0x0572
            if (r69 == 0) goto L_0x0572
            if (r40 == 0) goto L_0x0572
            java.lang.Class r68 = java.lang.Byte.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 == r1) goto L_0x0542
            java.lang.Class r68 = java.lang.Short.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 == r1) goto L_0x0542
            java.lang.Class r68 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 == r1) goto L_0x0542
            java.lang.Class r68 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 == r1) goto L_0x0542
            java.lang.Class r68 = java.lang.Float.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 == r1) goto L_0x0542
            java.lang.Class r68 = java.lang.Double.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0556
        L_0x0542:
            r0 = r69
            boolean r0 = r0 instanceof java.lang.Number     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x0556
            r0 = r69
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            byte r68 = r68.byteValue()     // Catch:{ Exception -> 0x06e0 }
            if (r68 == 0) goto L_0x02e1
        L_0x0556:
            java.lang.Class r68 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0572
            r0 = r69
            boolean r0 = r0 instanceof java.lang.Boolean     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x0572
            r0 = r69
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            boolean r68 = r68.booleanValue()     // Catch:{ Exception -> 0x06e0 }
            if (r68 == 0) goto L_0x02e1
        L_0x0572:
            if (r13 == 0) goto L_0x05c7
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            int r39 = r68 + 1
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r39
            r1 = r68
            if (r0 <= r1) goto L_0x059c
            r0 = r44
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 != 0) goto L_0x06c1
            r0 = r44
            r1 = r39
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x059c:
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r71 = 44
            r68[r70] = r71     // Catch:{ Exception -> 0x06e0 }
            r0 = r39
            r1 = r44
            r1.count = r0     // Catch:{ Exception -> 0x06e0 }
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r70 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x06e0 }
            r0 = r70
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r68 = r68 & r70
            if (r68 == 0) goto L_0x05c7
            r73.println()     // Catch:{ Exception -> 0x06e0 }
        L_0x05c7:
            r0 = r32
            r1 = r23
            if (r0 == r1) goto L_0x06c8
            if (r66 != 0) goto L_0x05da
            r68 = 1
            r0 = r44
            r1 = r32
            r2 = r68
            r0.writeFieldName(r1, r2)     // Catch:{ Exception -> 0x06e0 }
        L_0x05da:
            r0 = r73
            r1 = r69
            r0.write(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x05e1:
            r13 = 1
            goto L_0x02e1
        L_0x05e4:
            java.lang.Class<java.lang.String> r68 = java.lang.String.class
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0621
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r14 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r34 = r14 | r68
            if (r66 != 0) goto L_0x060c
            r68 = r57 & r34
            if (r68 != 0) goto L_0x060c
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r34
            if (r68 == 0) goto L_0x02e1
        L_0x060c:
            r68 = r57 & r14
            if (r68 != 0) goto L_0x061a
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r14
            if (r68 == 0) goto L_0x061d
        L_0x061a:
            java.lang.String r48 = ""
        L_0x061d:
            r69 = r48
            goto L_0x050c
        L_0x0621:
            java.lang.Class<java.lang.Number> r68 = java.lang.Number.class
            r0 = r68
            r1 = r21
            boolean r68 = r0.isAssignableFrom(r1)     // Catch:{ Exception -> 0x06e0 }
            if (r68 == 0) goto L_0x0665
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullNumberAsZero     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r14 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r34 = r14 | r68
            if (r66 != 0) goto L_0x064d
            r68 = r57 & r34
            if (r68 != 0) goto L_0x064d
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r34
            if (r68 == 0) goto L_0x02e1
        L_0x064d:
            r68 = r57 & r14
            if (r68 != 0) goto L_0x065b
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r14
            if (r68 == 0) goto L_0x0661
        L_0x065b:
            r68 = 0
            java.lang.Integer r48 = java.lang.Integer.valueOf(r68)     // Catch:{ Exception -> 0x06e0 }
        L_0x0661:
            r69 = r48
            goto L_0x050c
        L_0x0665:
            java.lang.Class<java.util.Collection> r68 = java.util.Collection.class
            r0 = r68
            r1 = r21
            boolean r68 = r0.isAssignableFrom(r1)     // Catch:{ Exception -> 0x06e0 }
            if (r68 == 0) goto L_0x06a7
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r14 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r34 = r14 | r68
            if (r66 != 0) goto L_0x0691
            r68 = r57 & r34
            if (r68 != 0) goto L_0x0691
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r34
            if (r68 == 0) goto L_0x02e1
        L_0x0691:
            r68 = r57 & r14
            if (r68 != 0) goto L_0x069f
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r14
            if (r68 == 0) goto L_0x06a3
        L_0x069f:
            java.util.List r48 = java.util.Collections.emptyList()     // Catch:{ Exception -> 0x06e0 }
        L_0x06a3:
            r69 = r48
            goto L_0x050c
        L_0x06a7:
            if (r66 != 0) goto L_0x06bd
            r0 = r24
            boolean r0 = r0.writeNull     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 != 0) goto L_0x06bd
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x06e0 }
            r0 = r44
            r1 = r68
            boolean r68 = r0.isEnabled(r1)     // Catch:{ Exception -> 0x06e0 }
            if (r68 == 0) goto L_0x02e1
        L_0x06bd:
            r69 = r48
            goto L_0x050c
        L_0x06c1:
            r44.flush()     // Catch:{ Exception -> 0x06e0 }
            r39 = 1
            goto L_0x059c
        L_0x06c8:
            r0 = r43
            r1 = r69
            if (r0 == r1) goto L_0x071a
            if (r66 != 0) goto L_0x06d7
            r0 = r24
            r1 = r73
            r0.writePrefix(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x06d7:
            r0 = r73
            r1 = r69
            r0.write(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x06e0:
            r16 = move-exception
            java.lang.String r19 = "write javaBean error, fastjson version 1.1.71"
            if (r75 == 0) goto L_0x0706
            java.lang.StringBuilder r68 = new java.lang.StringBuilder     // Catch:{ all -> 0x0712 }
            r68.<init>()     // Catch:{ all -> 0x0712 }
            r0 = r68
            r1 = r19
            java.lang.StringBuilder r68 = r0.append(r1)     // Catch:{ all -> 0x0712 }
            java.lang.String r69 = ", fieldName : "
            java.lang.StringBuilder r68 = r68.append(r69)     // Catch:{ all -> 0x0712 }
            r0 = r68
            r1 = r75
            java.lang.StringBuilder r68 = r0.append(r1)     // Catch:{ all -> 0x0712 }
            java.lang.String r19 = r68.toString()     // Catch:{ all -> 0x0712 }
        L_0x0706:
            com.alibaba.fastjson.JSONException r68 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0712 }
            r0 = r68
            r1 = r19
            r2 = r16
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0712 }
            throw r68     // Catch:{ all -> 0x0712 }
        L_0x0712:
            r68 = move-exception
            r0 = r45
            r1 = r73
            r1.context = r0
            throw r68
        L_0x071a:
            if (r66 != 0) goto L_0x076c
            if (r15 == 0) goto L_0x07e3
            r0 = r24
            char[] r11 = r0.name_chars     // Catch:{ Exception -> 0x06e0 }
            r42 = 0
            int r0 = r11.length     // Catch:{ Exception -> 0x06e0 }
            r33 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            int r39 = r68 + r33
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r39
            r1 = r68
            if (r0 <= r1) goto L_0x074f
            r0 = r44
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 != 0) goto L_0x078c
            r0 = r44
            r1 = r39
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x074f:
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r0 = r42
            r1 = r68
            r2 = r70
            r3 = r33
            java.lang.System.arraycopy(r11, r0, r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            r0 = r39
            r1 = r44
            r1.count = r0     // Catch:{ Exception -> 0x06e0 }
        L_0x076c:
            if (r65 == 0) goto L_0x08c0
            if (r50 != 0) goto L_0x08c0
            java.lang.Class r68 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x0873
            r68 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = r51
            r1 = r68
            if (r0 != r1) goto L_0x07eb
            java.lang.String r68 = "-2147483648"
            r0 = r44
            r1 = r68
            r0.write(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x078c:
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            int r55 = r68 - r70
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r0 = r42
            r1 = r68
            r2 = r70
            r3 = r55
            java.lang.System.arraycopy(r11, r0, r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r44
            r1.count = r0     // Catch:{ Exception -> 0x06e0 }
            r44.flush()     // Catch:{ Exception -> 0x06e0 }
            int r33 = r33 - r55
            int r42 = r42 + r55
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r33
            r1 = r68
            if (r0 > r1) goto L_0x078c
            r39 = r33
            goto L_0x074f
        L_0x07e3:
            r0 = r24
            r1 = r73
            r0.writePrefix(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x076c
        L_0x07eb:
            if (r51 >= 0) goto L_0x084c
            r0 = r51
            int r0 = -r0
            r67 = r0
        L_0x07f2:
            r31 = 0
        L_0x07f4:
            int[] r68 = com.alibaba.fastjson.serializer.SerializeWriter.sizeTable     // Catch:{ Exception -> 0x06e0 }
            r68 = r68[r31]     // Catch:{ Exception -> 0x06e0 }
            r0 = r67
            r1 = r68
            if (r0 > r1) goto L_0x084f
            int r58 = r31 + 1
            if (r51 >= 0) goto L_0x0804
            int r58 = r58 + 1
        L_0x0804:
            r27 = 0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            int r39 = r68 + r58
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r39
            r1 = r68
            if (r0 <= r1) goto L_0x082e
            r0 = r44
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 != 0) goto L_0x0852
            r0 = r44
            r1 = r39
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x082e:
            if (r27 != 0) goto L_0x05e1
            r0 = r51
            long r0 = (long) r0     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r0 = r68
            r2 = r39
            r3 = r70
            com.alibaba.fastjson.serializer.SerializeWriter.getChars(r0, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            r0 = r39
            r1 = r44
            r1.count = r0     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x084c:
            r67 = r51
            goto L_0x07f2
        L_0x084f:
            int r31 = r31 + 1
            goto L_0x07f4
        L_0x0852:
            r0 = r58
            char[] r12 = new char[r0]     // Catch:{ Exception -> 0x06e0 }
            r0 = r51
            long r0 = (long) r0     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r2 = r58
            com.alibaba.fastjson.serializer.SerializeWriter.getChars(r0, r2, r12)     // Catch:{ Exception -> 0x06e0 }
            r68 = 0
            int r0 = r12.length     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r0 = r44
            r1 = r68
            r2 = r69
            r0.write(r12, r1, r2)     // Catch:{ Exception -> 0x06e0 }
            r27 = 1
            goto L_0x082e
        L_0x0873:
            java.lang.Class r68 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x088a
            r0 = r73
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r0.out     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            r1 = r52
            r0.writeLong(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x088a:
            java.lang.Class r68 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06e0 }
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x05e1
            if (r49 == 0) goto L_0x08aa
            r0 = r73
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r0.out     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            char[] r69 = true_chars     // Catch:{ Exception -> 0x06e0 }
            r70 = 0
            char[] r71 = true_chars     // Catch:{ Exception -> 0x06e0 }
            r0 = r71
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r71 = r0
            r68.write(r69, r70, r71)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x08aa:
            r0 = r73
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r0.out     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            char[] r69 = false_chars     // Catch:{ Exception -> 0x06e0 }
            r70 = 0
            char[] r71 = false_chars     // Catch:{ Exception -> 0x06e0 }
            r0 = r71
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r71 = r0
            r68.write(r69, r70, r71)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x08c0:
            if (r66 != 0) goto L_0x09a7
            java.lang.Class<java.lang.String> r68 = java.lang.String.class
            r0 = r21
            r1 = r68
            if (r0 != r1) goto L_0x092b
            r0 = r24
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r72
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r57 = r68 | r70
            if (r69 != 0) goto L_0x0909
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 != 0) goto L_0x08f8
            com.alibaba.fastjson.serializer.SerializerFeature r68 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x06e0 }
            r0 = r68
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r68 = r68 & r57
            if (r68 == 0) goto L_0x0904
        L_0x08f8:
            java.lang.String r68 = ""
            r0 = r44
            r1 = r68
            r0.writeString(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x0904:
            r44.writeNull()     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x0909:
            r0 = r69
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x06e0 }
            r54 = r0
            if (r61 == 0) goto L_0x091a
            r0 = r44
            r1 = r54
            r0.writeStringWithSingleQuote(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x091a:
            r68 = 0
            r69 = 1
            r0 = r44
            r1 = r54
            r2 = r68
            r3 = r69
            r0.writeStringWithDoubleQuote(r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x092b:
            r0 = r22
            boolean r0 = r0.isEnum     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x099c
            if (r69 == 0) goto L_0x0997
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r70 = com.alibaba.fastjson.serializer.SerializerFeature.WriteEnumUsingToString     // Catch:{ Exception -> 0x06e0 }
            r0 = r70
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r70 = r0
            r68 = r68 & r70
            if (r68 == 0) goto L_0x0984
            r0 = r69
            java.lang.Enum r0 = (java.lang.Enum) r0     // Catch:{ Exception -> 0x06e0 }
            r17 = r0
            java.lang.String r35 = r17.toString()     // Catch:{ Exception -> 0x06e0 }
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x0970
            r62 = 1
        L_0x0965:
            if (r62 == 0) goto L_0x0973
            r0 = r44
            r1 = r35
            r0.writeStringWithSingleQuote(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x0970:
            r62 = 0
            goto L_0x0965
        L_0x0973:
            r68 = 0
            r69 = 0
            r0 = r44
            r1 = r35
            r2 = r68
            r3 = r69
            r0.writeStringWithDoubleQuote(r1, r2, r3)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x0984:
            r0 = r69
            java.lang.Enum r0 = (java.lang.Enum) r0     // Catch:{ Exception -> 0x06e0 }
            r17 = r0
            int r68 = r17.ordinal()     // Catch:{ Exception -> 0x06e0 }
            r0 = r44
            r1 = r68
            r0.writeInt(r1)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x0997:
            r44.writeNull()     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x099c:
            r0 = r24
            r1 = r73
            r2 = r69
            r0.writeValue(r1, r2)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x09a7:
            r0 = r24
            r1 = r73
            r2 = r69
            r0.writeValue(r1, r2)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x05e1
        L_0x09b2:
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.AfterFilter> r0 = r0.afterFilters     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 == 0) goto L_0x09df
            if (r13 == 0) goto L_0x09dd
            r7 = 44
        L_0x09be:
            r0 = r73
            java.util.List<com.alibaba.fastjson.serializer.AfterFilter> r0 = r0.afterFilters     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            java.util.Iterator r68 = r68.iterator()     // Catch:{ Exception -> 0x06e0 }
        L_0x09c8:
            boolean r69 = r68.hasNext()     // Catch:{ Exception -> 0x06e0 }
            if (r69 == 0) goto L_0x09df
            java.lang.Object r6 = r68.next()     // Catch:{ Exception -> 0x06e0 }
            com.alibaba.fastjson.serializer.AfterFilter r6 = (com.alibaba.fastjson.serializer.AfterFilter) r6     // Catch:{ Exception -> 0x06e0 }
            r0 = r73
            r1 = r74
            char r7 = r6.writeAfter(r0, r1, r7)     // Catch:{ Exception -> 0x06e0 }
            goto L_0x09c8
        L_0x09dd:
            r7 = 0
            goto L_0x09be
        L_0x09df:
            r0 = r28
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 <= 0) goto L_0x09fe
            r0 = r44
            int r0 = r0.features     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            com.alibaba.fastjson.serializer.SerializerFeature r69 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x06e0 }
            r0 = r69
            int r0 = r0.mask     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68 = r68 & r69
            if (r68 == 0) goto L_0x09fe
            r73.decrementIdent()     // Catch:{ Exception -> 0x06e0 }
            r73.println()     // Catch:{ Exception -> 0x06e0 }
        L_0x09fe:
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            int r39 = r68 + 1
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r39
            r1 = r68
            if (r0 <= r1) goto L_0x0a26
            r0 = r44
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            if (r68 != 0) goto L_0x0a42
            r0 = r44
            r1 = r39
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x06e0 }
        L_0x0a26:
            r0 = r44
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x06e0 }
            r68 = r0
            r0 = r44
            int r0 = r0.count     // Catch:{ Exception -> 0x06e0 }
            r69 = r0
            r68[r69] = r18     // Catch:{ Exception -> 0x06e0 }
            r0 = r39
            r1 = r44
            r1.count = r0     // Catch:{ Exception -> 0x06e0 }
            r0 = r45
            r1 = r73
            r1.context = r0
            goto L_0x000b
        L_0x0a42:
            r44.flush()     // Catch:{ Exception -> 0x06e0 }
            r39 = 1
            goto L_0x0a26
        L_0x0a48:
            r48 = r43
            goto L_0x0473
        L_0x0a4c:
            r43 = r48
            goto L_0x036d
        L_0x0a50:
            r43 = r48
            goto L_0x038c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type):void");
    }

    public Map<String, Object> getFieldValuesMap(Object object) throws Exception {
        FieldSerializer[] fieldSerializerArr;
        Map<String, Object> map = new LinkedHashMap<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            map.put(getter.fieldInfo.name, getter.getPropertyValue(object));
        }
        return map;
    }
}
