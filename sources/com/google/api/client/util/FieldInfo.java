package com.google.api.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

public class FieldInfo {
    private static final Map<Field, FieldInfo> CACHE = new WeakHashMap();
    private final Field field;
    private final boolean isPrimitive;
    private final String name;

    public static FieldInfo of(Enum<?> enumValue) {
        boolean z = true;
        try {
            FieldInfo result = of(enumValue.getClass().getField(enumValue.name()));
            if (result == null) {
                z = false;
            }
            Preconditions.checkArgument(z, "enum constant missing @Value or @NullValue annotation: %s", enumValue);
            return result;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.api.client.util.FieldInfo of(java.lang.reflect.Field r9) {
        /*
            r6 = 0
            if (r9 != 0) goto L_0x0005
            r0 = r6
        L_0x0004:
            return r0
        L_0x0005:
            java.util.Map<java.lang.reflect.Field, com.google.api.client.util.FieldInfo> r7 = CACHE
            monitor-enter(r7)
            java.util.Map<java.lang.reflect.Field, com.google.api.client.util.FieldInfo> r8 = CACHE     // Catch:{ all -> 0x004b }
            java.lang.Object r0 = r8.get(r9)     // Catch:{ all -> 0x004b }
            com.google.api.client.util.FieldInfo r0 = (com.google.api.client.util.FieldInfo) r0     // Catch:{ all -> 0x004b }
            boolean r2 = r9.isEnumConstant()     // Catch:{ all -> 0x004b }
            if (r0 != 0) goto L_0x0049
            if (r2 != 0) goto L_0x0022
            int r8 = r9.getModifiers()     // Catch:{ all -> 0x004b }
            boolean r8 = java.lang.reflect.Modifier.isStatic(r8)     // Catch:{ all -> 0x004b }
            if (r8 != 0) goto L_0x0049
        L_0x0022:
            if (r2 == 0) goto L_0x005d
            java.lang.Class<com.google.api.client.util.Value> r8 = com.google.api.client.util.Value.class
            java.lang.annotation.Annotation r5 = r9.getAnnotation(r8)     // Catch:{ all -> 0x004b }
            com.google.api.client.util.Value r5 = (com.google.api.client.util.Value) r5     // Catch:{ all -> 0x004b }
            if (r5 == 0) goto L_0x004e
            java.lang.String r1 = r5.value()     // Catch:{ all -> 0x004b }
        L_0x0032:
            java.lang.String r6 = "##default"
            boolean r6 = r6.equals(r1)     // Catch:{ all -> 0x004b }
            if (r6 == 0) goto L_0x003f
            java.lang.String r1 = r9.getName()     // Catch:{ all -> 0x004b }
        L_0x003f:
            com.google.api.client.util.FieldInfo r0 = new com.google.api.client.util.FieldInfo     // Catch:{ all -> 0x004b }
            r0.<init>(r9, r1)     // Catch:{ all -> 0x004b }
            java.util.Map<java.lang.reflect.Field, com.google.api.client.util.FieldInfo> r6 = CACHE     // Catch:{ all -> 0x004b }
            r6.put(r9, r0)     // Catch:{ all -> 0x004b }
        L_0x0049:
            monitor-exit(r7)     // Catch:{ all -> 0x004b }
            goto L_0x0004
        L_0x004b:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x004b }
            throw r6
        L_0x004e:
            java.lang.Class<com.google.api.client.util.NullValue> r8 = com.google.api.client.util.NullValue.class
            java.lang.annotation.Annotation r4 = r9.getAnnotation(r8)     // Catch:{ all -> 0x004b }
            com.google.api.client.util.NullValue r4 = (com.google.api.client.util.NullValue) r4     // Catch:{ all -> 0x004b }
            if (r4 == 0) goto L_0x005a
            r1 = 0
            goto L_0x0032
        L_0x005a:
            monitor-exit(r7)     // Catch:{ all -> 0x004b }
            r0 = r6
            goto L_0x0004
        L_0x005d:
            java.lang.Class<com.google.api.client.util.Key> r8 = com.google.api.client.util.Key.class
            java.lang.annotation.Annotation r3 = r9.getAnnotation(r8)     // Catch:{ all -> 0x004b }
            com.google.api.client.util.Key r3 = (com.google.api.client.util.Key) r3     // Catch:{ all -> 0x004b }
            if (r3 != 0) goto L_0x006a
            monitor-exit(r7)     // Catch:{ all -> 0x004b }
            r0 = r6
            goto L_0x0004
        L_0x006a:
            java.lang.String r1 = r3.value()     // Catch:{ all -> 0x004b }
            r6 = 1
            r9.setAccessible(r6)     // Catch:{ all -> 0x004b }
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.util.FieldInfo.of(java.lang.reflect.Field):com.google.api.client.util.FieldInfo");
    }

    FieldInfo(Field field2, String name2) {
        this.field = field2;
        this.name = name2 == null ? null : name2.intern();
        this.isPrimitive = Data.isPrimitive(getType());
    }

    public Field getField() {
        return this.field;
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public Type getGenericType() {
        return this.field.getGenericType();
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.field.getModifiers());
    }

    public boolean isPrimitive() {
        return this.isPrimitive;
    }

    public Object getValue(Object obj) {
        return getFieldValue(this.field, obj);
    }

    public void setValue(Object obj, Object value) {
        setFieldValue(this.field, obj, value);
    }

    public ClassInfo getClassInfo() {
        return ClassInfo.of(this.field.getDeclaringClass());
    }

    public <T extends Enum<T>> T enumValue() {
        return Enum.valueOf(this.field.getDeclaringClass(), this.field.getName());
    }

    public static Object getFieldValue(Field field2, Object obj) {
        try {
            return field2.get(obj);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void setFieldValue(Field field2, Object obj, Object value) {
        if (Modifier.isFinal(field2.getModifiers())) {
            Object finalValue = getFieldValue(field2, obj);
            if (value == null) {
                if (finalValue == null) {
                    return;
                }
            } else if (value.equals(finalValue)) {
                return;
            }
            String valueOf = String.valueOf(String.valueOf(finalValue));
            String valueOf2 = String.valueOf(String.valueOf(value));
            String valueOf3 = String.valueOf(String.valueOf(field2.getName()));
            String valueOf4 = String.valueOf(String.valueOf(obj.getClass().getName()));
            throw new IllegalArgumentException(new StringBuilder(valueOf.length() + 48 + valueOf2.length() + valueOf3.length() + valueOf4.length()).append("expected final value <").append(valueOf).append("> but was <").append(valueOf2).append("> on ").append(valueOf3).append(" field in ").append(valueOf4).toString());
        }
        try {
            field2.set(obj, value);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e2) {
            throw new IllegalArgumentException(e2);
        }
    }
}
