package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.IdentityHashMap;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class SerializeConfig {
    public static final SerializeConfig globalInstance = new SerializeConfig();
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<ObjectSerializer> serializers = new IdentityHashMap<>(1024);
    protected String typeKey = "@type";

    public static final SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public ObjectSerializer registerIfNotExists(Class<?> clazz) {
        return registerIfNotExists(clazz, clazz.getModifiers(), false, true, true, true);
    }

    public ObjectSerializer registerIfNotExists(Class<?> clazz, int classModifers, boolean fieldOnly, boolean jsonTypeSupport, boolean jsonFieldSupport, boolean fieldGenericSupport) {
        ObjectSerializer serializer = (ObjectSerializer) this.serializers.get(clazz);
        if (serializer != null) {
            return serializer;
        }
        ObjectSerializer serializer2 = new JavaBeanSerializer(clazz, classModifers, null, fieldOnly, jsonTypeSupport, jsonFieldSupport, fieldGenericSupport, this.propertyNamingStrategy);
        this.serializers.put(clazz, serializer2);
        return serializer2;
    }

    public SerializeConfig() {
        this.serializers.put(Boolean.class, BooleanCodec.instance);
        this.serializers.put(Character.class, MiscCodec.instance);
        this.serializers.put(Byte.class, IntegerCodec.instance);
        this.serializers.put(Short.class, IntegerCodec.instance);
        this.serializers.put(Integer.class, IntegerCodec.instance);
        this.serializers.put(Long.class, IntegerCodec.instance);
        this.serializers.put(Float.class, NumberCodec.instance);
        this.serializers.put(Double.class, NumberCodec.instance);
        this.serializers.put(Number.class, NumberCodec.instance);
        this.serializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.serializers.put(BigInteger.class, BigDecimalCodec.instance);
        this.serializers.put(String.class, StringCodec.instance);
        this.serializers.put(Object[].class, ArrayCodec.instance);
        this.serializers.put(Class.class, MiscCodec.instance);
        this.serializers.put(SimpleDateFormat.class, MiscCodec.instance);
        this.serializers.put(Locale.class, MiscCodec.instance);
        this.serializers.put(Currency.class, MiscCodec.instance);
        this.serializers.put(TimeZone.class, MiscCodec.instance);
        this.serializers.put(UUID.class, MiscCodec.instance);
        this.serializers.put(URI.class, MiscCodec.instance);
        this.serializers.put(URL.class, MiscCodec.instance);
        this.serializers.put(Pattern.class, MiscCodec.instance);
        this.serializers.put(Charset.class, MiscCodec.instance);
    }

    /* JADX WARNING: type inference failed for: r11v1, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARNING: type inference failed for: r11v2 */
    /* JADX WARNING: type inference failed for: r10v0, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARNING: type inference failed for: r11v3, types: [com.alibaba.fastjson.serializer.EnumSerializer, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v7, types: [com.alibaba.fastjson.serializer.DateCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v8, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r11v9, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r11v10, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r11v11 */
    /* JADX WARNING: type inference failed for: r11v12, types: [com.alibaba.fastjson.serializer.ArraySerializer, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v13, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r11v14, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r11v15, types: [java.lang.Object, com.alibaba.fastjson.serializer.MiscCodec] */
    /* JADX WARNING: type inference failed for: r11v16, types: [com.alibaba.fastjson.serializer.DateCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v17, types: [com.alibaba.fastjson.serializer.CollectionCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v18, types: [java.lang.Object, com.alibaba.fastjson.serializer.ListSerializer] */
    /* JADX WARNING: type inference failed for: r11v19, types: [com.alibaba.fastjson.serializer.CollectionCodec, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v20 */
    /* JADX WARNING: type inference failed for: r11v22, types: [com.alibaba.fastjson.serializer.ObjectSerializer] */
    /* JADX WARNING: type inference failed for: r11v23, types: [com.alibaba.fastjson.serializer.MapSerializer, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r11v24 */
    /* JADX WARNING: type inference failed for: r11v26 */
    /* JADX WARNING: type inference failed for: r11v28 */
    /* JADX WARNING: type inference failed for: r11v29 */
    /* JADX WARNING: type inference failed for: r11v30 */
    /* JADX WARNING: type inference failed for: r11v31 */
    /* JADX WARNING: type inference failed for: r11v32 */
    /* JADX WARNING: type inference failed for: r11v33 */
    /* JADX WARNING: type inference failed for: r11v34 */
    /* JADX WARNING: type inference failed for: r11v35 */
    /* JADX WARNING: type inference failed for: r11v36 */
    /* JADX WARNING: type inference failed for: r11v37 */
    /* JADX WARNING: type inference failed for: r11v38 */
    /* JADX WARNING: type inference failed for: r11v39 */
    /* JADX WARNING: type inference failed for: r11v40 */
    /* JADX WARNING: type inference failed for: r11v41 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 15 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.fastjson.serializer.ObjectSerializer get(java.lang.Class<?> r18) {
        /*
            r17 = this;
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            r0 = r18
            java.lang.Object r11 = r12.get(r0)
            com.alibaba.fastjson.serializer.ObjectSerializer r11 = (com.alibaba.fastjson.serializer.ObjectSerializer) r11
            if (r11 != 0) goto L_0x0034
            java.lang.Class<java.util.Map> r12 = java.util.Map.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x0036
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MapSerializer r11 = new com.alibaba.fastjson.serializer.MapSerializer
            r11.<init>()
            r0 = r18
            r12.put(r0, r11)
        L_0x0026:
            if (r11 != 0) goto L_0x0034
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            r0 = r18
            java.lang.Object r11 = r12.get(r0)
            com.alibaba.fastjson.serializer.ObjectSerializer r11 = (com.alibaba.fastjson.serializer.ObjectSerializer) r11
        L_0x0034:
            r10 = r11
        L_0x0035:
            return r10
        L_0x0036:
            java.lang.Class<java.util.AbstractSequentialList> r12 = java.util.AbstractSequentialList.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x004c
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.CollectionCodec r11 = com.alibaba.fastjson.serializer.CollectionCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x004c:
            java.lang.Class<java.util.List> r12 = java.util.List.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x0065
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.ListSerializer r11 = new com.alibaba.fastjson.serializer.ListSerializer
            r11.<init>()
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x0065:
            java.lang.Class<java.util.Collection> r12 = java.util.Collection.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x007b
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.CollectionCodec r11 = com.alibaba.fastjson.serializer.CollectionCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x007b:
            java.lang.Class<java.util.Date> r12 = java.util.Date.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x0091
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.DateCodec r11 = com.alibaba.fastjson.serializer.DateCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x0091:
            java.lang.Class<com.alibaba.fastjson.JSONAware> r12 = com.alibaba.fastjson.JSONAware.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x00a7
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x00a7:
            java.lang.Class<com.alibaba.fastjson.serializer.JSONSerializable> r12 = com.alibaba.fastjson.serializer.JSONSerializable.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x00be
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x00be:
            java.lang.Class<com.alibaba.fastjson.JSONStreamAware> r12 = com.alibaba.fastjson.JSONStreamAware.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x00d5
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x00d5:
            boolean r12 = r18.isEnum()
            if (r12 != 0) goto L_0x00eb
            java.lang.Class r8 = r18.getSuperclass()
            if (r8 == 0) goto L_0x00fb
            java.lang.Class<java.lang.Object> r12 = java.lang.Object.class
            if (r8 == r12) goto L_0x00fb
            boolean r12 = r8.isEnum()
            if (r12 == 0) goto L_0x00fb
        L_0x00eb:
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.EnumSerializer r11 = new com.alibaba.fastjson.serializer.EnumSerializer
            r11.<init>()
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x00fb:
            boolean r12 = r18.isArray()
            if (r12 == 0) goto L_0x011b
            java.lang.Class r3 = r18.getComponentType()
            r0 = r17
            com.alibaba.fastjson.serializer.ObjectSerializer r2 = r0.get(r3)
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.ArraySerializer r11 = new com.alibaba.fastjson.serializer.ArraySerializer
            r11.<init>(r3, r2)
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x011b:
            java.lang.Class<java.lang.Throwable> r12 = java.lang.Throwable.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x0145
            com.alibaba.fastjson.serializer.JavaBeanSerializer r7 = new com.alibaba.fastjson.serializer.JavaBeanSerializer
            r0 = r17
            com.alibaba.fastjson.PropertyNamingStrategy r12 = r0.propertyNamingStrategy
            r0 = r18
            r7.<init>(r0, r12)
            int r12 = r7.features
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName
            int r13 = r13.mask
            r12 = r12 | r13
            r7.features = r12
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            r11 = r7
            r0 = r18
            r12.put(r0, r7)
            goto L_0x0026
        L_0x0145:
            java.lang.Class<java.util.TimeZone> r12 = java.util.TimeZone.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x015c
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x015c:
            java.lang.Class<java.nio.charset.Charset> r12 = java.nio.charset.Charset.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x0173
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x0173:
            java.lang.Class<java.util.Enumeration> r12 = java.util.Enumeration.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x018a
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x018a:
            java.lang.Class<java.util.Calendar> r12 = java.util.Calendar.class
            r0 = r18
            boolean r12 = r12.isAssignableFrom(r0)
            if (r12 == 0) goto L_0x01a1
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            com.alibaba.fastjson.serializer.DateCodec r11 = com.alibaba.fastjson.serializer.DateCodec.instance
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x01a1:
            r4 = 0
            r5 = 0
            java.lang.Class[] r13 = r18.getInterfaces()
            int r14 = r13.length
            r12 = 0
        L_0x01a9:
            if (r12 >= r14) goto L_0x01c8
            r6 = r13[r12]
            java.lang.String r15 = r6.getName()
            java.lang.String r16 = "net.sf.cglib.proxy.Factory"
            boolean r15 = r15.equals(r16)
            if (r15 != 0) goto L_0x01c7
            java.lang.String r15 = r6.getName()
            java.lang.String r16 = "org.springframework.cglib.proxy.Factory"
            boolean r15 = r15.equals(r16)
            if (r15 == 0) goto L_0x01e1
        L_0x01c7:
            r4 = 1
        L_0x01c8:
            if (r4 != 0) goto L_0x01cc
            if (r5 == 0) goto L_0x01f3
        L_0x01cc:
            java.lang.Class r9 = r18.getSuperclass()
            r0 = r17
            com.alibaba.fastjson.serializer.ObjectSerializer r10 = r0.get(r9)
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            r0 = r18
            r12.put(r0, r10)
            goto L_0x0035
        L_0x01e1:
            java.lang.String r15 = r6.getName()
            java.lang.String r16 = "javassist.util.proxy.ProxyObject"
            boolean r15 = r15.equals(r16)
            if (r15 == 0) goto L_0x01f0
            r5 = 1
            goto L_0x01c8
        L_0x01f0:
            int r12 = r12 + 1
            goto L_0x01a9
        L_0x01f3:
            java.lang.String r1 = r18.getName()
            java.lang.String r12 = "android.net.Uri$"
            boolean r12 = r1.startsWith(r12)
            if (r12 == 0) goto L_0x020d
            com.alibaba.fastjson.serializer.MiscCodec r11 = com.alibaba.fastjson.serializer.MiscCodec.instance
        L_0x0202:
            r0 = r17
            com.alibaba.fastjson.util.IdentityHashMap<com.alibaba.fastjson.serializer.ObjectSerializer> r12 = r0.serializers
            r0 = r18
            r12.put(r0, r11)
            goto L_0x0026
        L_0x020d:
            com.alibaba.fastjson.serializer.JavaBeanSerializer r11 = new com.alibaba.fastjson.serializer.JavaBeanSerializer
            r0 = r17
            com.alibaba.fastjson.PropertyNamingStrategy r12 = r0.propertyNamingStrategy
            r0 = r18
            r11.<init>(r0, r12)
            goto L_0x0202
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeConfig.get(java.lang.Class):com.alibaba.fastjson.serializer.ObjectSerializer");
    }

    public boolean put(Type key, ObjectSerializer value) {
        return this.serializers.put(key, value);
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey2) {
        this.typeKey = typeKey2;
    }
}
