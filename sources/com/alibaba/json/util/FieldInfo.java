package com.alibaba.json.util;

import com.alibaba.json.JSONAware;
import com.alibaba.json.annotation.JSONField;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class FieldInfo implements Comparable<FieldInfo> {
    public final String[] alternateNames;
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final String format;
    public final boolean getOnly;
    public final boolean isEnum;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final long nameHashCode;
    private int ordinal = 0;

    public FieldInfo(String name2, Class<?> declaringClass2, Class<?> fieldClass2, Type fieldType2, Field field2, int ordinal2, int serialzeFeatures) {
        this.name = name2;
        this.declaringClass = declaringClass2;
        this.fieldClass = fieldClass2;
        this.fieldType = fieldType2;
        this.method = null;
        this.field = field2;
        this.ordinal = ordinal2;
        this.isEnum = fieldClass2.isEnum() && !JSONAware.class.isAssignableFrom(fieldClass2);
        this.fieldAnnotation = null;
        this.methodAnnotation = null;
        if (field2 != null) {
            int modifiers = field2.getModifiers();
            this.fieldAccess = (modifiers & 1) != 0 || this.method == null;
            this.fieldTransient = Modifier.isTransient(modifiers);
        } else {
            this.fieldAccess = false;
            this.fieldTransient = false;
        }
        this.getOnly = false;
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < name2.length(); i++) {
            hashCode = (hashCode ^ ((long) name2.charAt(i))) * 1099511628211L;
        }
        this.nameHashCode = hashCode;
        this.format = null;
        this.alternateNames = new String[0];
    }

    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r7v3, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r7v4, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r9v1, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r9v2, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r20v0 */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r7v6, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r7v8 */
    /* JADX WARNING: type inference failed for: r7v9 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r7v10 */
    /* JADX WARNING: type inference failed for: r7v11 */
    /* JADX WARNING: type inference failed for: r22v49, types: [java.lang.reflect.Type[]] */
    /* JADX WARNING: type inference failed for: r7v13 */
    /* JADX WARNING: type inference failed for: r7v14 */
    /* JADX WARNING: type inference failed for: r7v15 */
    /* JADX WARNING: type inference failed for: r7v16 */
    /* JADX WARNING: type inference failed for: r7v17 */
    /* JADX WARNING: type inference failed for: r7v18 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r7v19 */
    /* JADX WARNING: type inference failed for: r7v20 */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005f, code lost:
        r22 = r26.getReturnType();
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v0
      assigns: []
      uses: []
      mth insns count: 261
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public FieldInfo(java.lang.String r25, java.lang.reflect.Method r26, java.lang.reflect.Field r27, java.lang.Class<?> r28, java.lang.reflect.Type r29, int r30, int r31, com.alibaba.json.annotation.JSONField r32, com.alibaba.json.annotation.JSONField r33, boolean r34) {
        /*
            r24 = this;
            r24.<init>()
            r22 = 0
            r0 = r22
            r1 = r24
            r1.ordinal = r0
            r0 = r25
            r1 = r24
            r1.name = r0
            r0 = r26
            r1 = r24
            r1.method = r0
            r0 = r27
            r1 = r24
            r1.field = r0
            r0 = r30
            r1 = r24
            r1.ordinal = r0
            r0 = r32
            r1 = r24
            r1.methodAnnotation = r0
            r0 = r33
            r1 = r24
            r1.fieldAnnotation = r0
            com.alibaba.json.annotation.JSONField r2 = r24.getAnnotation()
            r8 = 0
            if (r2 == 0) goto L_0x00a6
            java.lang.String r8 = r2.format()
            java.lang.String r22 = r8.trim()
            int r22 = r22.length()
            if (r22 != 0) goto L_0x0045
            r8 = 0
        L_0x0045:
            java.lang.String[] r22 = r2.alternateNames()
            r0 = r22
            r1 = r24
            r1.alternateNames = r0
        L_0x004f:
            r0 = r24
            r0.format = r8
            if (r27 == 0) goto L_0x00bb
            int r14 = r27.getModifiers()
            if (r26 == 0) goto L_0x006d
            r22 = r14 & 1
            if (r22 == 0) goto L_0x00b5
            java.lang.Class r22 = r26.getReturnType()
            java.lang.Class r23 = r27.getType()
            r0 = r22
            r1 = r23
            if (r0 != r1) goto L_0x00b5
        L_0x006d:
            r22 = 1
        L_0x006f:
            r0 = r22
            r1 = r24
            r1.fieldAccess = r0
            r0 = r14 & 128(0x80, float:1.794E-43)
            r22 = r0
            if (r22 == 0) goto L_0x00b8
            r22 = 1
        L_0x007d:
            r0 = r22
            r1 = r24
            r1.fieldTransient = r0
        L_0x0083:
            r10 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            r12 = 0
        L_0x0089:
            int r22 = r25.length()
            r0 = r22
            if (r12 >= r0) goto L_0x00cc
            r0 = r25
            char r4 = r0.charAt(r12)
            long r0 = (long) r4
            r22 = r0
            long r10 = r10 ^ r22
            r22 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r10 = r10 * r22
            int r12 = r12 + 1
            goto L_0x0089
        L_0x00a6:
            r22 = 0
            r0 = r22
            java.lang.String[] r0 = new java.lang.String[r0]
            r22 = r0
            r0 = r22
            r1 = r24
            r1.alternateNames = r0
            goto L_0x004f
        L_0x00b5:
            r22 = 0
            goto L_0x006f
        L_0x00b8:
            r22 = 0
            goto L_0x007d
        L_0x00bb:
            r22 = 0
            r0 = r22
            r1 = r24
            r1.fieldAccess = r0
            r22 = 0
            r0 = r22
            r1 = r24
            r1.fieldTransient = r0
            goto L_0x0083
        L_0x00cc:
            r0 = r24
            r0.nameHashCode = r10
            if (r26 == 0) goto L_0x0195
            java.lang.Class[] r17 = r26.getParameterTypes()
            r0 = r17
            int r0 = r0.length
            r22 = r0
            r23 = 1
            r0 = r22
            r1 = r23
            if (r0 != r1) goto L_0x0177
            r22 = 0
            r6 = r17[r22]
            java.lang.Class<java.lang.Class> r22 = java.lang.Class.class
            r0 = r22
            if (r6 == r0) goto L_0x00f9
            java.lang.Class<java.lang.String> r22 = java.lang.String.class
            r0 = r22
            if (r6 == r0) goto L_0x00f9
            boolean r22 = r6.isPrimitive()
            if (r22 == 0) goto L_0x016a
        L_0x00f9:
            r7 = r6
        L_0x00fa:
            r22 = 0
            r0 = r22
            r1 = r24
            r1.getOnly = r0
        L_0x0102:
            java.lang.Class r22 = r26.getDeclaringClass()
            r0 = r22
            r1 = r24
            r1.declaringClass = r0
        L_0x010c:
            if (r28 == 0) goto L_0x0222
            java.lang.Class<java.lang.Object> r22 = java.lang.Object.class
            r0 = r22
            if (r6 != r0) goto L_0x0222
            boolean r0 = r7 instanceof java.lang.reflect.TypeVariable
            r22 = r0
            if (r22 == 0) goto L_0x0222
            r20 = r7
            java.lang.reflect.TypeVariable r20 = (java.lang.reflect.TypeVariable) r20
            r9 = 0
            r3 = 0
            r0 = r29
            boolean r0 = r0 instanceof java.lang.reflect.ParameterizedType
            r22 = r0
            if (r22 == 0) goto L_0x0130
            r18 = r29
            java.lang.reflect.ParameterizedType r18 = (java.lang.reflect.ParameterizedType) r18
            java.lang.reflect.Type[] r3 = r18.getActualTypeArguments()
        L_0x0130:
            r5 = r28
        L_0x0132:
            if (r5 == 0) goto L_0x01cf
            java.lang.Class<java.lang.Object> r22 = java.lang.Object.class
            r0 = r22
            if (r5 == r0) goto L_0x01cf
            r0 = r24
            java.lang.Class<?> r0 = r0.declaringClass
            r22 = r0
            r0 = r22
            if (r5 == r0) goto L_0x01cf
            java.lang.reflect.Type r19 = r5.getGenericSuperclass()
            r0 = r19
            boolean r0 = r0 instanceof java.lang.reflect.ParameterizedType
            r22 = r0
            if (r22 == 0) goto L_0x0165
            r15 = r19
            java.lang.reflect.ParameterizedType r15 = (java.lang.reflect.ParameterizedType) r15
            java.lang.reflect.Type[] r16 = r15.getActualTypeArguments()
            java.lang.reflect.TypeVariable[] r22 = r5.getTypeParameters()
            r0 = r16
            r1 = r22
            com.alibaba.json.util.TypeUtils.getArgument(r0, r1, r3)
            r3 = r16
        L_0x0165:
            java.lang.Class r5 = r5.getSuperclass()
            goto L_0x0132
        L_0x016a:
            if (r34 == 0) goto L_0x0175
            java.lang.reflect.Type[] r22 = r26.getGenericParameterTypes()
            r23 = 0
            r7 = r22[r23]
        L_0x0174:
            goto L_0x00fa
        L_0x0175:
            r7 = r6
            goto L_0x0174
        L_0x0177:
            java.lang.Class r6 = r26.getReturnType()
            java.lang.Class<java.lang.Class> r22 = java.lang.Class.class
            r0 = r22
            if (r6 != r0) goto L_0x018c
            r7 = r6
        L_0x0182:
            r22 = 1
            r0 = r22
            r1 = r24
            r1.getOnly = r0
            goto L_0x0102
        L_0x018c:
            if (r34 == 0) goto L_0x0193
            java.lang.reflect.Type r7 = r26.getGenericReturnType()
        L_0x0192:
            goto L_0x0182
        L_0x0193:
            r7 = r6
            goto L_0x0192
        L_0x0195:
            java.lang.Class r6 = r27.getType()
            boolean r22 = r6.isPrimitive()
            if (r22 != 0) goto L_0x01ab
            java.lang.Class<java.lang.String> r22 = java.lang.String.class
            r0 = r22
            if (r6 == r0) goto L_0x01ab
            boolean r22 = r6.isEnum()
            if (r22 == 0) goto L_0x01c6
        L_0x01ab:
            r7 = r6
        L_0x01ac:
            java.lang.Class r22 = r27.getDeclaringClass()
            r0 = r22
            r1 = r24
            r1.declaringClass = r0
            int r22 = r27.getModifiers()
            boolean r22 = java.lang.reflect.Modifier.isFinal(r22)
            r0 = r22
            r1 = r24
            r1.getOnly = r0
            goto L_0x010c
        L_0x01c6:
            if (r34 == 0) goto L_0x01cd
            java.lang.reflect.Type r7 = r27.getGenericType()
        L_0x01cc:
            goto L_0x01ac
        L_0x01cd:
            r7 = r6
            goto L_0x01cc
        L_0x01cf:
            if (r3 == 0) goto L_0x01f3
            r0 = r24
            java.lang.Class<?> r0 = r0.declaringClass
            r22 = r0
            java.lang.reflect.TypeVariable[] r21 = r22.getTypeParameters()
            r13 = 0
        L_0x01dc:
            r0 = r21
            int r0 = r0.length
            r22 = r0
            r0 = r22
            if (r13 >= r0) goto L_0x01f3
            r22 = r21[r13]
            r0 = r20
            r1 = r22
            boolean r22 = r0.equals(r1)
            if (r22 == 0) goto L_0x021c
            r9 = r3[r13]
        L_0x01f3:
            if (r9 == 0) goto L_0x0222
            java.lang.Class r22 = com.alibaba.json.util.TypeUtils.getClass(r9)
            r0 = r22
            r1 = r24
            r1.fieldClass = r0
            r0 = r24
            r0.fieldType = r9
            boolean r22 = r6.isEnum()
            if (r22 == 0) goto L_0x021f
            java.lang.Class<com.alibaba.json.JSONAware> r22 = com.alibaba.json.JSONAware.class
            r0 = r22
            boolean r22 = r0.isAssignableFrom(r6)
            if (r22 != 0) goto L_0x021f
            r22 = 1
        L_0x0215:
            r0 = r22
            r1 = r24
            r1.isEnum = r0
        L_0x021b:
            return
        L_0x021c:
            int r13 = r13 + 1
            goto L_0x01dc
        L_0x021f:
            r22 = 0
            goto L_0x0215
        L_0x0222:
            r9 = r7
            boolean r0 = r7 instanceof java.lang.Class
            r22 = r0
            if (r22 != 0) goto L_0x023f
            if (r29 == 0) goto L_0x0266
        L_0x022b:
            r0 = r28
            r1 = r29
            java.lang.reflect.Type r9 = getFieldType(r0, r1, r7)
            if (r9 == r7) goto L_0x023f
            boolean r0 = r9 instanceof java.lang.reflect.ParameterizedType
            r22 = r0
            if (r22 == 0) goto L_0x0269
            java.lang.Class r6 = com.alibaba.json.util.TypeUtils.getClass(r9)
        L_0x023f:
            r0 = r24
            r0.fieldType = r9
            r0 = r24
            r0.fieldClass = r6
            boolean r22 = r6.isArray()
            if (r22 != 0) goto L_0x0274
            boolean r22 = r6.isEnum()
            if (r22 == 0) goto L_0x0274
            java.lang.Class<com.alibaba.json.JSONAware> r22 = com.alibaba.json.JSONAware.class
            r0 = r22
            boolean r22 = r0.isAssignableFrom(r6)
            if (r22 != 0) goto L_0x0274
            r22 = 1
        L_0x025f:
            r0 = r22
            r1 = r24
            r1.isEnum = r0
            goto L_0x021b
        L_0x0266:
            r29 = r28
            goto L_0x022b
        L_0x0269:
            boolean r0 = r9 instanceof java.lang.Class
            r22 = r0
            if (r22 == 0) goto L_0x023f
            java.lang.Class r6 = com.alibaba.json.util.TypeUtils.getClass(r9)
            goto L_0x023f
        L_0x0274:
            r22 = 0
            goto L_0x025f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.json.util.FieldInfo.<init>(java.lang.String, java.lang.reflect.Method, java.lang.reflect.Field, java.lang.Class, java.lang.reflect.Type, int, int, com.alibaba.json.annotation.JSONField, com.alibaba.json.annotation.JSONField, boolean):void");
    }

    public static Type getFieldType(Class<?> clazz, Type type, Type fieldType2) {
        if (clazz == null || type == null) {
            return fieldType2;
        }
        if (fieldType2 instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) fieldType2).getGenericComponentType();
            Type componentTypeX = getFieldType(clazz, type, componentType);
            if (componentType != componentTypeX) {
                return Array.newInstance(TypeUtils.getClass(componentTypeX), 0).getClass();
            }
            return fieldType2;
        } else if (!TypeUtils.isGenericParamType(type)) {
            return fieldType2;
        } else {
            if (fieldType2 instanceof TypeVariable) {
                ParameterizedType paramType = (ParameterizedType) TypeUtils.getGenericParamType(type);
                Class<?> parameterizedClass = TypeUtils.getClass(paramType);
                TypeVariable<?> typeVar = (TypeVariable) fieldType2;
                for (int i = 0; i < parameterizedClass.getTypeParameters().length; i++) {
                    if (parameterizedClass.getTypeParameters()[i].getName().equals(typeVar.getName())) {
                        return paramType.getActualTypeArguments()[i];
                    }
                }
            }
            if (fieldType2 instanceof ParameterizedType) {
                ParameterizedType parameterizedFieldType = (ParameterizedType) fieldType2;
                Type[] arguments = parameterizedFieldType.getActualTypeArguments();
                boolean changed = false;
                TypeVariable<?>[] typeVariables = null;
                Type[] actualTypes = null;
                ParameterizedType paramType2 = null;
                if (type instanceof ParameterizedType) {
                    paramType2 = (ParameterizedType) type;
                    typeVariables = clazz.getTypeParameters();
                } else if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                    paramType2 = (ParameterizedType) clazz.getGenericSuperclass();
                    typeVariables = clazz.getSuperclass().getTypeParameters();
                }
                for (int i2 = 0; i2 < arguments.length && paramType2 != null; i2++) {
                    Type feildTypeArguement = arguments[i2];
                    if (feildTypeArguement instanceof TypeVariable) {
                        TypeVariable<?> typeVar2 = (TypeVariable) feildTypeArguement;
                        for (int j = 0; j < typeVariables.length; j++) {
                            if (typeVariables[j].getName().equals(typeVar2.getName())) {
                                if (actualTypes == null) {
                                    actualTypes = paramType2.getActualTypeArguments();
                                }
                                arguments[i2] = actualTypes[j];
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    ParameterizedTypeImpl parameterizedTypeImpl = new ParameterizedTypeImpl(arguments, parameterizedFieldType.getOwnerType(), parameterizedFieldType.getRawType());
                    return parameterizedTypeImpl;
                }
            }
            return fieldType2;
        }
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(FieldInfo o) {
        if (this.ordinal < o.ordinal) {
            return -1;
        }
        if (this.ordinal > o.ordinal) {
            return 1;
        }
        return this.name.compareTo(o.name);
    }

    public boolean equals(FieldInfo o) {
        if (o == this || compareTo(o) == 0) {
            return true;
        }
        return false;
    }

    public JSONField getAnnotation() {
        if (this.fieldAnnotation != null) {
            return this.fieldAnnotation;
        }
        return this.methodAnnotation;
    }

    public Object get(Object javaObject) throws IllegalAccessException, InvocationTargetException {
        if (this.fieldAccess) {
            return this.field.get(javaObject);
        }
        return this.method.invoke(javaObject, new Object[0]);
    }

    public void set(Object javaObject, Object value) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            this.method.invoke(javaObject, new Object[]{value});
            return;
        }
        this.field.set(javaObject, value);
    }
}
