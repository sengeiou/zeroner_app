package com.alibaba.json.serializer;

import com.alibaba.json.PropertyNamingStrategy;
import com.alibaba.json.annotation.JSONType;
import com.alibaba.json.util.FieldInfo;
import com.alibaba.json.util.TypeUtils;
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

    /* JADX WARNING: Removed duplicated region for block: B:136:0x0377 A[Catch:{ Exception -> 0x05d1, all -> 0x0601 }] */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x047f A[Catch:{ Exception -> 0x05d1, all -> 0x0601 }, LOOP:5: B:178:0x0479->B:180:0x047f, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:367:0x038c A[EDGE_INSN: B:367:0x038c->B:139:0x038c ?: BREAK  
    EDGE_INSN: B:367:0x038c->B:139:0x038c ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.json.serializer.JSONSerializer r70, java.lang.Object r71, java.lang.Object r72, java.lang.reflect.Type r73) throws java.io.IOException {
        /*
            r69 = this;
            r0 = r70
            com.alibaba.json.serializer.SerializeWriter r0 = r0.out
            r42 = r0
            if (r71 != 0) goto L_0x000c
            r42.writeNull()
        L_0x000b:
            return
        L_0x000c:
            r0 = r70
            com.alibaba.json.serializer.SerialContext r0 = r0.context
            r65 = r0
            if (r65 == 0) goto L_0x002c
            r0 = r70
            com.alibaba.json.serializer.SerialContext r0 = r0.context
            r65 = r0
            r0 = r65
            int r0 = r0.features
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.DisableCircularReferenceDetect
            r0 = r66
            int r0 = r0.mask
            r66 = r0
            r65 = r65 & r66
            if (r65 != 0) goto L_0x0048
        L_0x002c:
            r0 = r70
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.json.serializer.SerialContext> r0 = r0.references
            r65 = r0
            if (r65 == 0) goto L_0x0048
            r0 = r70
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.json.serializer.SerialContext> r0 = r0.references
            r65 = r0
            r0 = r65
            r1 = r71
            boolean r65 = r0.containsKey(r1)
            if (r65 == 0) goto L_0x0048
            r70.writeReference(r71)
            goto L_0x000b
        L_0x0048:
            r0 = r42
            int r0 = r0.features
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.SortField
            r0 = r66
            int r0 = r0.mask
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x0201
            r0 = r69
            com.alibaba.json.serializer.FieldSerializer[] r0 = r0.sortedGetters
            r27 = r0
        L_0x0060:
            r0 = r70
            com.alibaba.json.serializer.SerialContext r0 = r0.context
            r43 = r0
            r0 = r42
            int r0 = r0.features
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.DisableCircularReferenceDetect
            r0 = r66
            int r0 = r0.mask
            r66 = r0
            r65 = r65 & r66
            if (r65 != 0) goto L_0x00bb
            com.alibaba.json.serializer.SerialContext r65 = new com.alibaba.json.serializer.SerialContext
            r0 = r69
            int r0 = r0.features
            r66 = r0
            r0 = r65
            r1 = r43
            r2 = r71
            r3 = r72
            r4 = r66
            r0.<init>(r1, r2, r3, r4)
            r0 = r65
            r1 = r70
            r1.context = r0
            r0 = r70
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.json.serializer.SerialContext> r0 = r0.references
            r65 = r0
            if (r65 != 0) goto L_0x00a6
            java.util.IdentityHashMap r65 = new java.util.IdentityHashMap
            r65.<init>()
            r0 = r65
            r1 = r70
            r1.references = r0
        L_0x00a6:
            r0 = r70
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.json.serializer.SerialContext> r0 = r0.references
            r65 = r0
            r0 = r70
            com.alibaba.json.serializer.SerialContext r0 = r0.context
            r66 = r0
            r0 = r65
            r1 = r71
            r2 = r66
            r0.put(r1, r2)
        L_0x00bb:
            r0 = r69
            int r0 = r0.features
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.BeanToArray
            r0 = r66
            int r0 = r0.mask
            r66 = r0
            r65 = r65 & r66
            if (r65 != 0) goto L_0x00df
            r0 = r42
            int r0 = r0.features
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.BeanToArray
            r0 = r66
            int r0 = r0.mask
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x0209
        L_0x00df:
            r63 = 1
        L_0x00e1:
            if (r63 == 0) goto L_0x020d
            r56 = 91
        L_0x00e5:
            if (r63 == 0) goto L_0x0211
            r17 = 93
        L_0x00e9:
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            int r37 = r65 + 1
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r37
            r1 = r65
            if (r0 <= r1) goto L_0x0111
            r0 = r42
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 != 0) goto L_0x0215
            r0 = r42
            r1 = r37
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x0111:
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65[r66] = r56     // Catch:{ Exception -> 0x05d1 }
            r0 = r37
            r1 = r42
            r1.count = r0     // Catch:{ Exception -> 0x05d1 }
            r0 = r27
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 <= 0) goto L_0x0144
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x0144
            r70.incrementIndent()     // Catch:{ Exception -> 0x05d1 }
            r70.println()     // Catch:{ Exception -> 0x05d1 }
        L_0x0144:
            r13 = 0
            r0 = r69
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 != 0) goto L_0x0193
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x021c
            if (r73 != 0) goto L_0x0193
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.NotWriteRootClassName     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x0193
            r0 = r70
            com.alibaba.json.serializer.SerialContext r0 = r0.context     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x021c
            r0 = r70
            com.alibaba.json.serializer.SerialContext r0 = r0.context     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            com.alibaba.json.serializer.SerialContext r0 = r0.parent     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x021c
        L_0x0193:
            r29 = 1
        L_0x0195:
            if (r29 == 0) goto L_0x01d2
            java.lang.Class r39 = r71.getClass()     // Catch:{ Exception -> 0x05d1 }
            r0 = r39
            r1 = r73
            if (r0 == r1) goto L_0x01d2
            r0 = r69
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0220
            r0 = r69
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
        L_0x01af:
            r66 = 0
            r0 = r42
            r1 = r65
            r2 = r66
            r0.writeFieldName(r1, r2)     // Catch:{ Exception -> 0x05d1 }
            r0 = r69
            java.lang.String r0 = r0.typeName     // Catch:{ Exception -> 0x05d1 }
            r57 = r0
            if (r57 != 0) goto L_0x01ca
            java.lang.Class r65 = r71.getClass()     // Catch:{ Exception -> 0x05d1 }
            java.lang.String r57 = r65.getName()     // Catch:{ Exception -> 0x05d1 }
        L_0x01ca:
            r0 = r70
            r1 = r57
            r0.write(r1)     // Catch:{ Exception -> 0x05d1 }
            r13 = 1
        L_0x01d2:
            if (r13 == 0) goto L_0x022d
            r54 = 44
        L_0x01d6:
            r36 = r54
            r0 = r70
            java.util.List<com.alibaba.json.serializer.BeforeFilter> r0 = r0.beforeFilters     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0230
            r0 = r70
            java.util.List<com.alibaba.json.serializer.BeforeFilter> r0 = r0.beforeFilters     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            java.util.Iterator r65 = r65.iterator()     // Catch:{ Exception -> 0x05d1 }
        L_0x01ea:
            boolean r66 = r65.hasNext()     // Catch:{ Exception -> 0x05d1 }
            if (r66 == 0) goto L_0x0230
            java.lang.Object r10 = r65.next()     // Catch:{ Exception -> 0x05d1 }
            com.alibaba.json.serializer.BeforeFilter r10 = (com.alibaba.json.serializer.BeforeFilter) r10     // Catch:{ Exception -> 0x05d1 }
            r0 = r70
            r1 = r71
            r2 = r36
            char r36 = r10.writeBefore(r0, r1, r2)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x01ea
        L_0x0201:
            r0 = r69
            com.alibaba.json.serializer.FieldSerializer[] r0 = r0.getters
            r27 = r0
            goto L_0x0060
        L_0x0209:
            r63 = 0
            goto L_0x00e1
        L_0x020d:
            r56 = 123(0x7b, float:1.72E-43)
            goto L_0x00e5
        L_0x0211:
            r17 = 125(0x7d, float:1.75E-43)
            goto L_0x00e9
        L_0x0215:
            r42.flush()     // Catch:{ Exception -> 0x05d1 }
            r37 = 1
            goto L_0x0111
        L_0x021c:
            r29 = 0
            goto L_0x0195
        L_0x0220:
            r0 = r70
            com.alibaba.json.serializer.SerializeConfig r0 = r0.config     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            goto L_0x01af
        L_0x022d:
            r54 = 0
            goto L_0x01d6
        L_0x0230:
            r65 = 44
            r0 = r36
            r1 = r65
            if (r0 != r1) goto L_0x02e4
            r13 = 1
        L_0x0239:
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.QuoteFieldNames     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x02e7
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 != 0) goto L_0x02e7
            r14 = 1
        L_0x025e:
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x02ea
            r58 = 1
        L_0x0272:
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.NotWriteDefaultValue     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x02ed
            r38 = 1
        L_0x0286:
            r0 = r70
            java.util.List<com.alibaba.json.serializer.PropertyFilter> r0 = r0.propertyFilters     // Catch:{ Exception -> 0x05d1 }
            r45 = r0
            r0 = r70
            java.util.List<com.alibaba.json.serializer.NameFilter> r0 = r0.nameFilters     // Catch:{ Exception -> 0x05d1 }
            r35 = r0
            r0 = r70
            java.util.List<com.alibaba.json.serializer.ValueFilter> r0 = r0.valueFilters     // Catch:{ Exception -> 0x05d1 }
            r61 = r0
            r0 = r70
            java.util.List<com.alibaba.json.serializer.PropertyPreFilter> r0 = r0.propertyPreFilters     // Catch:{ Exception -> 0x05d1 }
            r25 = r0
            r28 = 0
        L_0x02a0:
            r0 = r27
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r28
            r1 = r65
            if (r0 >= r1) goto L_0x0899
            r23 = r27[r28]     // Catch:{ Exception -> 0x05d1 }
            r0 = r23
            com.alibaba.json.util.FieldInfo r0 = r0.fieldInfo     // Catch:{ Exception -> 0x05d1 }
            r21 = r0
            r0 = r21
            java.lang.Class<?> r0 = r0.fieldClass     // Catch:{ Exception -> 0x05d1 }
            r20 = r0
            r0 = r21
            java.lang.String r0 = r0.name     // Catch:{ Exception -> 0x05d1 }
            r22 = r0
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.SkipTransientField     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x02f0
            r0 = r21
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x05d1 }
            r19 = r0
            if (r19 == 0) goto L_0x02f0
            r0 = r21
            boolean r0 = r0.fieldTransient     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x02f0
        L_0x02e1:
            int r28 = r28 + 1
            goto L_0x02a0
        L_0x02e4:
            r13 = 0
            goto L_0x0239
        L_0x02e7:
            r14 = 0
            goto L_0x025e
        L_0x02ea:
            r58 = 0
            goto L_0x0272
        L_0x02ed:
            r38 = 0
            goto L_0x0286
        L_0x02f0:
            r0 = r69
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0308
            r0 = r69
            java.lang.String r0 = r0.typeKey     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r22
            boolean r65 = r0.equals(r1)     // Catch:{ Exception -> 0x05d1 }
            if (r65 != 0) goto L_0x02e1
        L_0x0308:
            r9 = 1
            if (r25 == 0) goto L_0x032a
            java.util.Iterator r65 = r25.iterator()     // Catch:{ Exception -> 0x05d1 }
        L_0x030f:
            boolean r66 = r65.hasNext()     // Catch:{ Exception -> 0x05d1 }
            if (r66 == 0) goto L_0x032a
            java.lang.Object r24 = r65.next()     // Catch:{ Exception -> 0x05d1 }
            com.alibaba.json.serializer.PropertyPreFilter r24 = (com.alibaba.json.serializer.PropertyPreFilter) r24     // Catch:{ Exception -> 0x05d1 }
            r0 = r24
            r1 = r70
            r2 = r71
            r3 = r22
            boolean r66 = r0.apply(r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            if (r66 != 0) goto L_0x030f
            r9 = 0
        L_0x032a:
            if (r9 == 0) goto L_0x02e1
            r46 = 0
            r49 = 0
            r50 = 0
            r47 = 0
            r48 = 0
            r62 = 0
            r0 = r21
            boolean r0 = r0.fieldAccess     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0409
            java.lang.Class r65 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x03c3
            r0 = r21
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r71
            int r49 = r0.getInt(r1)     // Catch:{ Exception -> 0x05d1 }
            r62 = 1
        L_0x0358:
            r8 = 1
            if (r45 == 0) goto L_0x0937
            if (r62 == 0) goto L_0x0933
            java.lang.Class r65 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0415
            java.lang.Integer r46 = java.lang.Integer.valueOf(r49)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            r41 = r46
        L_0x036d:
            java.util.Iterator r65 = r45.iterator()     // Catch:{ Exception -> 0x05d1 }
        L_0x0371:
            boolean r66 = r65.hasNext()     // Catch:{ Exception -> 0x05d1 }
            if (r66 == 0) goto L_0x038c
            java.lang.Object r44 = r65.next()     // Catch:{ Exception -> 0x05d1 }
            com.alibaba.json.serializer.PropertyFilter r44 = (com.alibaba.json.serializer.PropertyFilter) r44     // Catch:{ Exception -> 0x05d1 }
            r0 = r44
            r1 = r71
            r2 = r22
            r3 = r41
            boolean r66 = r0.apply(r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            if (r66 != 0) goto L_0x0371
            r8 = 0
        L_0x038c:
            if (r8 == 0) goto L_0x02e1
            r31 = r22
            if (r35 == 0) goto L_0x045d
            if (r62 == 0) goto L_0x03a6
            if (r48 != 0) goto L_0x03a6
            java.lang.Class r65 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0439
            java.lang.Integer r46 = java.lang.Integer.valueOf(r49)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            r41 = r46
        L_0x03a6:
            java.util.Iterator r65 = r35.iterator()     // Catch:{ Exception -> 0x05d1 }
        L_0x03aa:
            boolean r66 = r65.hasNext()     // Catch:{ Exception -> 0x05d1 }
            if (r66 == 0) goto L_0x045d
            java.lang.Object r34 = r65.next()     // Catch:{ Exception -> 0x05d1 }
            com.alibaba.json.serializer.NameFilter r34 = (com.alibaba.json.serializer.NameFilter) r34     // Catch:{ Exception -> 0x05d1 }
            r0 = r34
            r1 = r71
            r2 = r31
            r3 = r41
            java.lang.String r31 = r0.process(r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x03aa
        L_0x03c3:
            java.lang.Class r65 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x03dd
            r0 = r21
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r71
            long r50 = r0.getLong(r1)     // Catch:{ Exception -> 0x05d1 }
            r62 = 1
            goto L_0x0358
        L_0x03dd:
            java.lang.Class r65 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x03f7
            r0 = r21
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r71
            boolean r47 = r0.getBoolean(r1)     // Catch:{ Exception -> 0x05d1 }
            r62 = 1
            goto L_0x0358
        L_0x03f7:
            r0 = r21
            java.lang.reflect.Field r0 = r0.field     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r71
            java.lang.Object r46 = r0.get(r1)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            goto L_0x0358
        L_0x0409:
            r0 = r23
            r1 = r71
            java.lang.Object r46 = r0.getPropertyValue(r1)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            goto L_0x0358
        L_0x0415:
            java.lang.Class r65 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0427
            java.lang.Long r46 = java.lang.Long.valueOf(r50)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            r41 = r46
            goto L_0x036d
        L_0x0427:
            java.lang.Class r65 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0933
            java.lang.Boolean r46 = java.lang.Boolean.valueOf(r47)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            r41 = r46
            goto L_0x036d
        L_0x0439:
            java.lang.Class r65 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x044b
            java.lang.Long r46 = java.lang.Long.valueOf(r50)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            r41 = r46
            goto L_0x03a6
        L_0x044b:
            java.lang.Class r65 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x03a6
            java.lang.Boolean r46 = java.lang.Boolean.valueOf(r47)     // Catch:{ Exception -> 0x05d1 }
            r48 = 1
            r41 = r46
            goto L_0x03a6
        L_0x045d:
            if (r61 == 0) goto L_0x04ba
            if (r62 == 0) goto L_0x092f
            if (r48 != 0) goto L_0x092f
            java.lang.Class r65 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0494
            java.lang.Integer r46 = java.lang.Integer.valueOf(r49)     // Catch:{ Exception -> 0x05d1 }
            r41 = r46
            r48 = 1
            r66 = r46
        L_0x0475:
            java.util.Iterator r65 = r61.iterator()     // Catch:{ Exception -> 0x05d1 }
        L_0x0479:
            boolean r67 = r65.hasNext()     // Catch:{ Exception -> 0x05d1 }
            if (r67 == 0) goto L_0x04bc
            java.lang.Object r60 = r65.next()     // Catch:{ Exception -> 0x05d1 }
            com.alibaba.json.serializer.ValueFilter r60 = (com.alibaba.json.serializer.ValueFilter) r60     // Catch:{ Exception -> 0x05d1 }
            r0 = r60
            r1 = r71
            r2 = r22
            r3 = r66
            java.lang.Object r46 = r0.process(r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            r66 = r46
            goto L_0x0479
        L_0x0494:
            java.lang.Class r65 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x04a7
            java.lang.Long r46 = java.lang.Long.valueOf(r50)     // Catch:{ Exception -> 0x05d1 }
            r41 = r46
            r48 = 1
            r66 = r46
            goto L_0x0475
        L_0x04a7:
            java.lang.Class r65 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x092f
            java.lang.Boolean r46 = java.lang.Boolean.valueOf(r47)     // Catch:{ Exception -> 0x05d1 }
            r41 = r46
            r48 = 1
            r66 = r46
            goto L_0x0475
        L_0x04ba:
            r66 = r41
        L_0x04bc:
            if (r48 == 0) goto L_0x04dc
            if (r66 != 0) goto L_0x04dc
            if (r63 != 0) goto L_0x04dc
            r0 = r23
            boolean r0 = r0.writeNull     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 != 0) goto L_0x04dc
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r67 = com.alibaba.json.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x05d1 }
            r0 = r67
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            r65 = r65 & r67
            if (r65 == 0) goto L_0x02e1
        L_0x04dc:
            if (r48 == 0) goto L_0x0542
            if (r66 == 0) goto L_0x0542
            if (r38 == 0) goto L_0x0542
            java.lang.Class r65 = java.lang.Byte.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 == r1) goto L_0x0512
            java.lang.Class r65 = java.lang.Short.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 == r1) goto L_0x0512
            java.lang.Class r65 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 == r1) goto L_0x0512
            java.lang.Class r65 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 == r1) goto L_0x0512
            java.lang.Class r65 = java.lang.Float.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 == r1) goto L_0x0512
            java.lang.Class r65 = java.lang.Double.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0526
        L_0x0512:
            r0 = r66
            boolean r0 = r0 instanceof java.lang.Number     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0526
            r0 = r66
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            byte r65 = r65.byteValue()     // Catch:{ Exception -> 0x05d1 }
            if (r65 == 0) goto L_0x02e1
        L_0x0526:
            java.lang.Class r65 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0542
            r0 = r66
            boolean r0 = r0 instanceof java.lang.Boolean     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0542
            r0 = r66
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            boolean r65 = r65.booleanValue()     // Catch:{ Exception -> 0x05d1 }
            if (r65 == 0) goto L_0x02e1
        L_0x0542:
            if (r13 == 0) goto L_0x0597
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            int r37 = r65 + 1
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r37
            r1 = r65
            if (r0 <= r1) goto L_0x056c
            r0 = r42
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 != 0) goto L_0x05b4
            r0 = r42
            r1 = r37
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x056c:
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            r68 = 44
            r65[r67] = r68     // Catch:{ Exception -> 0x05d1 }
            r0 = r37
            r1 = r42
            r1.count = r0     // Catch:{ Exception -> 0x05d1 }
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r67 = com.alibaba.json.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x05d1 }
            r0 = r67
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            r65 = r65 & r67
            if (r65 == 0) goto L_0x0597
            r70.println()     // Catch:{ Exception -> 0x05d1 }
        L_0x0597:
            r0 = r31
            r1 = r22
            if (r0 == r1) goto L_0x05ba
            if (r63 != 0) goto L_0x05aa
            r65 = 1
            r0 = r42
            r1 = r31
            r2 = r65
            r0.writeFieldName(r1, r2)     // Catch:{ Exception -> 0x05d1 }
        L_0x05aa:
            r0 = r70
            r1 = r66
            r0.write(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x05b1:
            r13 = 1
            goto L_0x02e1
        L_0x05b4:
            r42.flush()     // Catch:{ Exception -> 0x05d1 }
            r37 = 1
            goto L_0x056c
        L_0x05ba:
            r0 = r41
            r1 = r66
            if (r0 == r1) goto L_0x0609
            if (r63 != 0) goto L_0x05c9
            r0 = r23
            r1 = r70
            r0.writePrefix(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x05c9:
            r0 = r70
            r1 = r66
            r0.write(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x05d1:
            r15 = move-exception
            java.lang.String r18 = "write javaBean error"
            if (r72 == 0) goto L_0x05f7
            java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x0601 }
            r65.<init>()     // Catch:{ all -> 0x0601 }
            r0 = r65
            r1 = r18
            java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x0601 }
            java.lang.String r66 = ", fieldName : "
            java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x0601 }
            r0 = r65
            r1 = r72
            java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x0601 }
            java.lang.String r18 = r65.toString()     // Catch:{ all -> 0x0601 }
        L_0x05f7:
            com.alibaba.json.JSONException r65 = new com.alibaba.json.JSONException     // Catch:{ all -> 0x0601 }
            r0 = r65
            r1 = r18
            r0.<init>(r1, r15)     // Catch:{ all -> 0x0601 }
            throw r65     // Catch:{ all -> 0x0601 }
        L_0x0601:
            r65 = move-exception
            r0 = r43
            r1 = r70
            r1.context = r0
            throw r65
        L_0x0609:
            if (r63 != 0) goto L_0x065b
            if (r14 == 0) goto L_0x06d2
            r0 = r23
            char[] r11 = r0.name_chars     // Catch:{ Exception -> 0x05d1 }
            r40 = 0
            int r0 = r11.length     // Catch:{ Exception -> 0x05d1 }
            r32 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            int r37 = r65 + r32
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r37
            r1 = r65
            if (r0 <= r1) goto L_0x063e
            r0 = r42
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 != 0) goto L_0x067b
            r0 = r42
            r1 = r37
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x063e:
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            r0 = r40
            r1 = r65
            r2 = r67
            r3 = r32
            java.lang.System.arraycopy(r11, r0, r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            r0 = r37
            r1 = r42
            r1.count = r0     // Catch:{ Exception -> 0x05d1 }
        L_0x065b:
            if (r62 == 0) goto L_0x07af
            if (r48 != 0) goto L_0x07af
            java.lang.Class r65 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0762
            r65 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = r49
            r1 = r65
            if (r0 != r1) goto L_0x06da
            java.lang.String r65 = "-2147483648"
            r0 = r42
            r1 = r65
            r0.write(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x067b:
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            int r53 = r65 - r67
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            r0 = r40
            r1 = r65
            r2 = r67
            r3 = r53
            java.lang.System.arraycopy(r11, r0, r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r42
            r1.count = r0     // Catch:{ Exception -> 0x05d1 }
            r42.flush()     // Catch:{ Exception -> 0x05d1 }
            int r32 = r32 - r53
            int r40 = r40 + r53
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r32
            r1 = r65
            if (r0 > r1) goto L_0x067b
            r37 = r32
            goto L_0x063e
        L_0x06d2:
            r0 = r23
            r1 = r70
            r0.writePrefix(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x065b
        L_0x06da:
            if (r49 >= 0) goto L_0x073b
            r0 = r49
            int r0 = -r0
            r64 = r0
        L_0x06e1:
            r30 = 0
        L_0x06e3:
            int[] r65 = com.alibaba.json.serializer.SerializeWriter.sizeTable     // Catch:{ Exception -> 0x05d1 }
            r65 = r65[r30]     // Catch:{ Exception -> 0x05d1 }
            r0 = r64
            r1 = r65
            if (r0 > r1) goto L_0x073e
            int r55 = r30 + 1
            if (r49 >= 0) goto L_0x06f3
            int r55 = r55 + 1
        L_0x06f3:
            r26 = 0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            int r37 = r65 + r55
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r37
            r1 = r65
            if (r0 <= r1) goto L_0x071d
            r0 = r42
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 != 0) goto L_0x0741
            r0 = r42
            r1 = r37
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x071d:
            if (r26 != 0) goto L_0x05b1
            r0 = r49
            long r0 = (long) r0     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r66
            r2 = r37
            r3 = r65
            com.alibaba.json.serializer.SerializeWriter.getChars(r0, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            r0 = r37
            r1 = r42
            r1.count = r0     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x073b:
            r64 = r49
            goto L_0x06e1
        L_0x073e:
            int r30 = r30 + 1
            goto L_0x06e3
        L_0x0741:
            r0 = r55
            char[] r12 = new char[r0]     // Catch:{ Exception -> 0x05d1 }
            r0 = r49
            long r0 = (long) r0     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r0 = r66
            r2 = r55
            com.alibaba.json.serializer.SerializeWriter.getChars(r0, r2, r12)     // Catch:{ Exception -> 0x05d1 }
            r65 = 0
            int r0 = r12.length     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r0 = r42
            r1 = r65
            r2 = r66
            r0.write(r12, r1, r2)     // Catch:{ Exception -> 0x05d1 }
            r26 = 1
            goto L_0x071d
        L_0x0762:
            java.lang.Class r65 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0779
            r0 = r70
            com.alibaba.json.serializer.SerializeWriter r0 = r0.out     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            r1 = r50
            r0.writeLong(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0779:
            java.lang.Class r65 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x05d1 }
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x05b1
            if (r47 == 0) goto L_0x0799
            r0 = r70
            com.alibaba.json.serializer.SerializeWriter r0 = r0.out     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            char[] r66 = true_chars     // Catch:{ Exception -> 0x05d1 }
            r67 = 0
            char[] r68 = true_chars     // Catch:{ Exception -> 0x05d1 }
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r68 = r0
            r65.write(r66, r67, r68)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0799:
            r0 = r70
            com.alibaba.json.serializer.SerializeWriter r0 = r0.out     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            char[] r66 = false_chars     // Catch:{ Exception -> 0x05d1 }
            r67 = 0
            char[] r68 = false_chars     // Catch:{ Exception -> 0x05d1 }
            r0 = r68
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r68 = r0
            r65.write(r66, r67, r68)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x07af:
            if (r63 != 0) goto L_0x088e
            java.lang.Class<java.lang.String> r65 = java.lang.String.class
            r0 = r20
            r1 = r65
            if (r0 != r1) goto L_0x0812
            if (r66 != 0) goto L_0x07f0
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 != 0) goto L_0x07df
            r0 = r23
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x07eb
        L_0x07df:
            java.lang.String r65 = ""
            r0 = r42
            r1 = r65
            r0.writeString(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x07eb:
            r42.writeNull()     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x07f0:
            r0 = r66
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x05d1 }
            r52 = r0
            if (r58 == 0) goto L_0x0801
            r0 = r42
            r1 = r52
            r0.writeStringWithSingleQuote(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0801:
            r65 = 0
            r66 = 1
            r0 = r42
            r1 = r52
            r2 = r65
            r3 = r66
            r0.writeStringWithDoubleQuote(r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0812:
            r0 = r21
            boolean r0 = r0.isEnum     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x0883
            if (r66 == 0) goto L_0x087e
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r67 = com.alibaba.json.serializer.SerializerFeature.WriteEnumUsingToString     // Catch:{ Exception -> 0x05d1 }
            r0 = r67
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r67 = r0
            r65 = r65 & r67
            if (r65 == 0) goto L_0x086b
            r0 = r66
            java.lang.Enum r0 = (java.lang.Enum) r0     // Catch:{ Exception -> 0x05d1 }
            r16 = r0
            java.lang.String r33 = r16.toString()     // Catch:{ Exception -> 0x05d1 }
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x0857
            r59 = 1
        L_0x084c:
            if (r59 == 0) goto L_0x085a
            r0 = r42
            r1 = r33
            r0.writeStringWithSingleQuote(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0857:
            r59 = 0
            goto L_0x084c
        L_0x085a:
            r65 = 0
            r66 = 0
            r0 = r42
            r1 = r33
            r2 = r65
            r3 = r66
            r0.writeStringWithDoubleQuote(r1, r2, r3)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x086b:
            r0 = r66
            java.lang.Enum r0 = (java.lang.Enum) r0     // Catch:{ Exception -> 0x05d1 }
            r16 = r0
            int r65 = r16.ordinal()     // Catch:{ Exception -> 0x05d1 }
            r0 = r42
            r1 = r65
            r0.writeInt(r1)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x087e:
            r42.writeNull()     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0883:
            r0 = r23
            r1 = r70
            r2 = r66
            r0.writeValue(r1, r2)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x088e:
            r0 = r23
            r1 = r70
            r2 = r66
            r0.writeValue(r1, r2)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x05b1
        L_0x0899:
            r0 = r70
            java.util.List<com.alibaba.json.serializer.AfterFilter> r0 = r0.afterFilters     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 == 0) goto L_0x08c6
            if (r13 == 0) goto L_0x08c4
            r7 = 44
        L_0x08a5:
            r0 = r70
            java.util.List<com.alibaba.json.serializer.AfterFilter> r0 = r0.afterFilters     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            java.util.Iterator r65 = r65.iterator()     // Catch:{ Exception -> 0x05d1 }
        L_0x08af:
            boolean r66 = r65.hasNext()     // Catch:{ Exception -> 0x05d1 }
            if (r66 == 0) goto L_0x08c6
            java.lang.Object r6 = r65.next()     // Catch:{ Exception -> 0x05d1 }
            com.alibaba.json.serializer.AfterFilter r6 = (com.alibaba.json.serializer.AfterFilter) r6     // Catch:{ Exception -> 0x05d1 }
            r0 = r70
            r1 = r71
            char r7 = r6.writeAfter(r0, r1, r7)     // Catch:{ Exception -> 0x05d1 }
            goto L_0x08af
        L_0x08c4:
            r7 = 0
            goto L_0x08a5
        L_0x08c6:
            r0 = r27
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 <= 0) goto L_0x08e5
            r0 = r42
            int r0 = r0.features     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            com.alibaba.json.serializer.SerializerFeature r66 = com.alibaba.json.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x05d1 }
            r0 = r66
            int r0 = r0.mask     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65 = r65 & r66
            if (r65 == 0) goto L_0x08e5
            r70.decrementIdent()     // Catch:{ Exception -> 0x05d1 }
            r70.println()     // Catch:{ Exception -> 0x05d1 }
        L_0x08e5:
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            int r37 = r65 + 1
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r65
            int r0 = r0.length     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r37
            r1 = r65
            if (r0 <= r1) goto L_0x090d
            r0 = r42
            java.io.Writer r0 = r0.writer     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            if (r65 != 0) goto L_0x0929
            r0 = r42
            r1 = r37
            r0.expandCapacity(r1)     // Catch:{ Exception -> 0x05d1 }
        L_0x090d:
            r0 = r42
            char[] r0 = r0.buf     // Catch:{ Exception -> 0x05d1 }
            r65 = r0
            r0 = r42
            int r0 = r0.count     // Catch:{ Exception -> 0x05d1 }
            r66 = r0
            r65[r66] = r17     // Catch:{ Exception -> 0x05d1 }
            r0 = r37
            r1 = r42
            r1.count = r0     // Catch:{ Exception -> 0x05d1 }
            r0 = r43
            r1 = r70
            r1.context = r0
            goto L_0x000b
        L_0x0929:
            r42.flush()     // Catch:{ Exception -> 0x05d1 }
            r37 = 1
            goto L_0x090d
        L_0x092f:
            r66 = r41
            goto L_0x0475
        L_0x0933:
            r41 = r46
            goto L_0x036d
        L_0x0937:
            r41 = r46
            goto L_0x038c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.serializer.JavaBeanSerializer.write(com.alibaba.json.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type):void");
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
