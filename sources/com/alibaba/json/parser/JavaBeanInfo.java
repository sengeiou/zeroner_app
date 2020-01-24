package com.alibaba.json.parser;

import com.alibaba.json.JSONException;
import com.alibaba.json.PropertyNamingStrategy;
import com.alibaba.json.annotation.JSONCreator;
import com.alibaba.json.annotation.JSONField;
import com.alibaba.json.annotation.JSONType;
import com.alibaba.json.serializer.SerializerFeature;
import com.alibaba.json.util.FieldInfo;
import com.alibaba.json.util.TypeUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JavaBeanInfo {
    final Constructor<?> creatorConstructor;
    public final String[] creatorConstructorParameters;
    final Constructor<?> defaultConstructor;
    final int defaultConstructorParameterSize;
    final Method factoryMethod;
    final FieldInfo[] fields;
    final JSONType jsonType;
    boolean ordered = false;
    public final int parserFeatures;
    final FieldInfo[] sortedFields;
    final boolean supportBeanToArray;
    public final String typeKey;
    public final String typeName;

    JavaBeanInfo(Class<?> clazz, Constructor<?> defaultConstructor2, Constructor<?> creatorConstructor2, Method factoryMethod2, FieldInfo[] fields2, FieldInfo[] sortedFields2, JSONType jsonType2, String[] creatorConstructorParameters2) {
        this.defaultConstructor = defaultConstructor2;
        this.creatorConstructor = creatorConstructor2;
        this.factoryMethod = factoryMethod2;
        this.fields = fields2;
        this.jsonType = jsonType2;
        if (creatorConstructorParameters2 == null || creatorConstructorParameters2.length != fields2.length) {
            this.creatorConstructorParameters = creatorConstructorParameters2;
        } else {
            this.creatorConstructorParameters = null;
        }
        int parserFeatures2 = 0;
        if (jsonType2 != null) {
            String typeName2 = jsonType2.typeName();
            if (typeName2.length() <= 0) {
                typeName2 = clazz.getName();
            }
            this.typeName = typeName2;
            String typeKey2 = jsonType2.typeKey();
            if (typeKey2.length() <= 0) {
                typeKey2 = null;
            }
            this.typeKey = typeKey2;
            for (Feature feature : jsonType2.parseFeatures()) {
                parserFeatures2 |= feature.mask;
            }
        } else {
            this.typeName = clazz.getName();
            this.typeKey = null;
        }
        this.parserFeatures = parserFeatures2;
        boolean supportBeanToArray2 = false;
        if (jsonType2 != null) {
            for (Feature feature2 : jsonType2.parseFeatures()) {
                if (feature2 == Feature.SupportArrayToBean) {
                    supportBeanToArray2 = true;
                }
            }
        }
        this.supportBeanToArray = supportBeanToArray2;
        FieldInfo[] sortedFields3 = computeSortedFields(fields2, sortedFields2);
        if (!Arrays.equals(fields2, sortedFields3)) {
            fields2 = sortedFields3;
        }
        this.sortedFields = fields2;
        int i = defaultConstructor2 != null ? defaultConstructor2.getParameterTypes().length : factoryMethod2 != null ? factoryMethod2.getParameterTypes().length : 0;
        this.defaultConstructorParameterSize = i;
    }

    private FieldInfo[] computeSortedFields(FieldInfo[] fields2, FieldInfo[] sortedFields2) {
        if (this.jsonType == null) {
            return sortedFields2;
        }
        String[] orders = this.jsonType.orders();
        if (!(orders == null || orders.length == 0)) {
            boolean containsAll = true;
            int i = 0;
            while (true) {
                if (i >= orders.length) {
                    break;
                }
                boolean got = false;
                int j = 0;
                while (true) {
                    if (j >= sortedFields2.length) {
                        break;
                    } else if (sortedFields2[j].name.equals(orders[i])) {
                        got = true;
                        break;
                    } else {
                        j++;
                    }
                }
                if (!got) {
                    containsAll = false;
                    break;
                }
                i++;
            }
            if (!containsAll) {
                return sortedFields2;
            }
            if (orders.length == fields2.length) {
                boolean orderMatch = true;
                int i2 = 0;
                while (true) {
                    if (i2 >= orders.length) {
                        break;
                    } else if (!sortedFields2[i2].name.equals(orders[i2])) {
                        orderMatch = false;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (orderMatch) {
                    return sortedFields2;
                }
                FieldInfo[] newSortedFields = new FieldInfo[sortedFields2.length];
                for (int i3 = 0; i3 < orders.length; i3++) {
                    int j2 = 0;
                    while (true) {
                        if (j2 >= sortedFields2.length) {
                            break;
                        } else if (sortedFields2[j2].name.equals(orders[i3])) {
                            newSortedFields[i3] = sortedFields2[j2];
                            break;
                        } else {
                            j2++;
                        }
                    }
                }
                FieldInfo[] sortedFields3 = newSortedFields;
                this.ordered = true;
                return newSortedFields;
            }
            FieldInfo[] newSortedFields2 = new FieldInfo[sortedFields2.length];
            for (int i4 = 0; i4 < orders.length; i4++) {
                int j3 = 0;
                while (true) {
                    if (j3 >= sortedFields2.length) {
                        break;
                    } else if (sortedFields2[j3].name.equals(orders[i4])) {
                        newSortedFields2[i4] = sortedFields2[j3];
                        break;
                    } else {
                        j3++;
                    }
                }
            }
            int fieldIndex = orders.length;
            for (int i5 = 0; i5 < sortedFields2.length; i5++) {
                boolean contains = false;
                int j4 = 0;
                while (true) {
                    if (j4 >= newSortedFields2.length || j4 >= fieldIndex) {
                        break;
                    } else if (newSortedFields2[i5].equals(sortedFields2[j4])) {
                        contains = true;
                        break;
                    } else {
                        j4++;
                    }
                }
                if (!contains) {
                    newSortedFields2[fieldIndex] = sortedFields2[i5];
                    fieldIndex++;
                }
            }
            this.ordered = true;
        }
        return sortedFields2;
    }

    static boolean addField(List<FieldInfo> fields2, FieldInfo field, boolean fieldOnly) {
        if (!fieldOnly) {
            int size = fields2.size();
            for (int i = 0; i < size; i++) {
                FieldInfo item = (FieldInfo) fields2.get(i);
                if (item.name.equals(field.name) && (!item.getOnly || field.getOnly)) {
                    return false;
                }
            }
        }
        fields2.add(field);
        return true;
    }

    public static JavaBeanInfo build(Class<?> clazz, int classModifiers, Type type, boolean fieldOnly, boolean jsonTypeSupport, boolean jsonFieldSupport, boolean fieldGenericSupport, PropertyNamingStrategy propertyNamingStrategy) {
        Method[] methods;
        Method[] declaredMethods;
        String propertyName;
        Field[] declaredFields;
        String propertyName2;
        Type[] getGenericParameterTypes;
        int ordinal;
        int serialzeFeatures;
        Type[] genericParameterTypes;
        Type[] getGenericParameterTypes2;
        JSONType jsonType2 = null;
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        boolean kotlin2 = TypeUtils.isKotlin(clazz);
        Constructor<?> defaultConstructor2 = null;
        if ((classModifiers & 1024) == 0 && (constructors.length == 1 || !kotlin2)) {
            try {
                defaultConstructor2 = clazz.getDeclaredConstructor(new Class[0]);
            } catch (Exception e) {
            }
            if (defaultConstructor2 == null && clazz.isMemberClass() && (classModifiers & 8) == 0) {
                int length = constructors.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Constructor<?> constructor = constructors[i];
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    if (parameterTypes.length == 1 && parameterTypes[0].equals(clazz.getDeclaringClass())) {
                        defaultConstructor2 = constructor;
                        break;
                    }
                    i++;
                }
            }
        }
        Constructor<?> creatorConstructor2 = null;
        String[] creatorConstructorParameters2 = null;
        Method factoryMethod2 = null;
        if (fieldOnly) {
            methods = null;
        } else {
            ArrayList arrayList2 = new ArrayList();
            Class<?> cls = clazz;
            while (cls != null && cls != Object.class) {
                for (Method method : cls.getDeclaredMethods()) {
                    int modifier = method.getModifiers();
                    if ((modifier & 8) != 0) {
                        if (!method.isAnnotationPresent(JSONCreator.class)) {
                            continue;
                        } else if (factoryMethod2 != null) {
                            throw new JSONException("multi-json creator");
                        } else {
                            factoryMethod2 = method;
                        }
                    } else if ((modifier & 2) == 0 && (modifier & 256) == 0 && (modifier & 4) == 0) {
                        arrayList2.add(method);
                    }
                }
                cls = cls.getSuperclass();
            }
            methods = new Method[arrayList2.size()];
            arrayList2.toArray(methods);
        }
        Field[] declaredFields2 = clazz.getDeclaredFields();
        boolean isInterfaceOrAbstract = clazz.isInterface() || (classModifiers & 1024) != 0;
        if (defaultConstructor2 == null || isInterfaceOrAbstract) {
            creatorConstructor2 = null;
            int length2 = constructors.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length2) {
                    break;
                }
                Constructor<?> constructor2 = constructors[i2];
                if (((JSONCreator) constructor2.getAnnotation(JSONCreator.class)) == null) {
                    i2++;
                } else if (0 != 0) {
                    throw new JSONException("multi-json creator");
                } else {
                    creatorConstructor2 = constructor2;
                }
            }
            if (creatorConstructor2 != null) {
                TypeUtils.setAccessible(clazz, creatorConstructor2, classModifiers);
                Type[] parameterTypes2 = creatorConstructor2.getParameterTypes();
                if (fieldGenericSupport) {
                    getGenericParameterTypes2 = creatorConstructor2.getGenericParameterTypes();
                } else {
                    getGenericParameterTypes2 = parameterTypes2;
                }
                Annotation[][] paramAnnotationArrays = creatorConstructor2.getParameterAnnotations();
                for (int i3 = 0; i3 < parameterTypes2.length; i3++) {
                    Annotation[] paramAnnotations = paramAnnotationArrays[i3];
                    JSONField fieldAnnotation = null;
                    int length3 = paramAnnotations.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length3) {
                            break;
                        }
                        Annotation paramAnnotation = paramAnnotations[i4];
                        if (paramAnnotation instanceof JSONField) {
                            fieldAnnotation = (JSONField) paramAnnotation;
                            break;
                        }
                        i4++;
                    }
                    if (fieldAnnotation == null) {
                        throw new JSONException("illegal json creator");
                    }
                    Class<?> fieldClass = parameterTypes2[i3];
                    Type fieldType = getGenericParameterTypes2[i3];
                    Field field = TypeUtils.getField(clazz, fieldAnnotation.name(), declaredFields2, hashMap);
                    if (field != null) {
                        TypeUtils.setAccessible(clazz, field, classModifiers);
                    }
                    addField(arrayList, new FieldInfo(fieldAnnotation.name(), clazz, fieldClass, fieldType, field, fieldAnnotation.ordinal(), SerializerFeature.of(fieldAnnotation.serialzeFeatures())), fieldOnly);
                }
                FieldInfo[] fields2 = new FieldInfo[arrayList.size()];
                arrayList.toArray(fields2);
                FieldInfo[] sortedFields2 = new FieldInfo[fields2.length];
                System.arraycopy(fields2, 0, sortedFields2, 0, fields2.length);
                Arrays.sort(sortedFields2);
                if (jsonTypeSupport) {
                    JSONType jsonType3 = (JSONType) clazz.getAnnotation(JSONType.class);
                }
                creatorConstructorParameters2 = new String[fields2.length];
                for (int i5 = 0; i5 < fields2.length; i5++) {
                    creatorConstructorParameters2[i5] = fields2[i5].name;
                }
            } else if (factoryMethod2 != null) {
                TypeUtils.setAccessible(clazz, factoryMethod2, classModifiers);
                Type[] parameterTypes3 = factoryMethod2.getParameterTypes();
                if (parameterTypes3.length > 0) {
                    if (fieldGenericSupport) {
                        genericParameterTypes = factoryMethod2.getGenericParameterTypes();
                    } else {
                        genericParameterTypes = parameterTypes3;
                    }
                    Annotation[][] paramAnnotationArrays2 = factoryMethod2.getParameterAnnotations();
                    for (int i6 = 0; i6 < parameterTypes3.length; i6++) {
                        Annotation[] paramAnnotations2 = paramAnnotationArrays2[i6];
                        JSONField fieldAnnotation2 = null;
                        int length4 = paramAnnotations2.length;
                        int i7 = 0;
                        while (true) {
                            if (i7 >= length4) {
                                break;
                            }
                            Annotation paramAnnotation2 = paramAnnotations2[i7];
                            if (paramAnnotation2 instanceof JSONField) {
                                fieldAnnotation2 = (JSONField) paramAnnotation2;
                                break;
                            }
                            i7++;
                        }
                        if (fieldAnnotation2 == null) {
                            throw new JSONException("illegal json creator");
                        }
                        addField(arrayList, new FieldInfo(fieldAnnotation2.name(), clazz, parameterTypes3[i6], genericParameterTypes[i6], TypeUtils.getField(clazz, fieldAnnotation2.name(), declaredFields2, hashMap), fieldAnnotation2.ordinal(), SerializerFeature.of(fieldAnnotation2.serialzeFeatures())), fieldOnly);
                    }
                    FieldInfo[] fields3 = new FieldInfo[arrayList.size()];
                    arrayList.toArray(fields3);
                    FieldInfo[] sortedFields3 = new FieldInfo[fields3.length];
                    System.arraycopy(fields3, 0, sortedFields3, 0, fields3.length);
                    Arrays.sort(sortedFields3);
                    if (Arrays.equals(fields3, sortedFields3)) {
                        sortedFields3 = fields3;
                    }
                    if (0 == 0) {
                        jsonType2 = jsonTypeSupport ? (JSONType) clazz.getAnnotation(JSONType.class) : null;
                    }
                    return new JavaBeanInfo(clazz, null, null, factoryMethod2, fields3, sortedFields3, jsonType2, null);
                }
            } else if (!isInterfaceOrAbstract) {
                if (!kotlin2 || constructors.length <= 0) {
                    throw new JSONException("default constructor not found. " + clazz);
                }
                String[] parameters = TypeUtils.getKoltinConstructorParameters(clazz);
                if (parameters != null) {
                    for (Constructor<?> constructor3 : constructors) {
                        Class<?>[] parameterTypes4 = constructor3.getParameterTypes();
                        if ((parameterTypes4.length <= 0 || !parameterTypes4[parameterTypes4.length - 1].getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) && (creatorConstructor2 == null || creatorConstructor2.getParameterTypes().length < parameterTypes4.length)) {
                            creatorConstructor2 = constructor3;
                        }
                    }
                    creatorConstructor2.setAccessible(true);
                    TypeUtils.setAccessible(clazz, creatorConstructor2, classModifiers);
                    Type[] parameterTypes5 = creatorConstructor2.getParameterTypes();
                    if (fieldGenericSupport) {
                        getGenericParameterTypes = creatorConstructor2.getGenericParameterTypes();
                    } else {
                        getGenericParameterTypes = parameterTypes5;
                    }
                    Annotation[][] paramAnnotationArrays3 = creatorConstructor2.getParameterAnnotations();
                    for (int i8 = 0; i8 < parameterTypes5.length; i8++) {
                        String paramName = parameters[i8];
                        Annotation[] paramAnnotations3 = paramAnnotationArrays3[i8];
                        JSONField fieldAnnotation3 = null;
                        int length5 = paramAnnotations3.length;
                        int i9 = 0;
                        while (true) {
                            if (i9 >= length5) {
                                break;
                            }
                            Annotation paramAnnotation3 = paramAnnotations3[i9];
                            if (paramAnnotation3 instanceof JSONField) {
                                fieldAnnotation3 = (JSONField) paramAnnotation3;
                                break;
                            }
                            i9++;
                        }
                        Class<?> fieldClass2 = parameterTypes5[i8];
                        Type fieldType2 = getGenericParameterTypes[i8];
                        Field field2 = TypeUtils.getField(clazz, paramName, declaredFields2, hashMap);
                        if (field2 != null && fieldAnnotation3 == null) {
                            fieldAnnotation3 = (JSONField) field2.getAnnotation(JSONField.class);
                        }
                        if (fieldAnnotation3 != null) {
                            ordinal = fieldAnnotation3.ordinal();
                            serialzeFeatures = SerializerFeature.of(fieldAnnotation3.serialzeFeatures());
                            String nameAnnotated = fieldAnnotation3.name();
                            if (nameAnnotated.length() != 0) {
                                paramName = nameAnnotated;
                            }
                        } else {
                            ordinal = 0;
                            serialzeFeatures = 0;
                        }
                        addField(arrayList, new FieldInfo(paramName, clazz, fieldClass2, fieldType2, field2, ordinal, serialzeFeatures), fieldOnly);
                    }
                    FieldInfo[] fields4 = new FieldInfo[arrayList.size()];
                    arrayList.toArray(fields4);
                    FieldInfo[] sortedFields4 = new FieldInfo[fields4.length];
                    System.arraycopy(fields4, 0, sortedFields4, 0, fields4.length);
                    Arrays.sort(sortedFields4);
                    creatorConstructorParameters2 = new String[fields4.length];
                    for (int i10 = 0; i10 < fields4.length; i10++) {
                        creatorConstructorParameters2[i10] = fields4[i10].name;
                    }
                } else {
                    throw new JSONException("default constructor not found. " + clazz);
                }
            }
        }
        if (defaultConstructor2 != null) {
            TypeUtils.setAccessible(clazz, defaultConstructor2, classModifiers);
        }
        if (!fieldOnly) {
            for (Method method2 : methods) {
                int ordinal2 = 0;
                int serialzeFeatures2 = 0;
                String methodName = method2.getName();
                if (methodName.length() >= 4) {
                    Class<?> returnType = method2.getReturnType();
                    if ((returnType == Void.TYPE || returnType == method2.getDeclaringClass()) && method2.getParameterTypes().length == 1) {
                        JSONField annotation = jsonFieldSupport ? (JSONField) method2.getAnnotation(JSONField.class) : null;
                        if (annotation == null && jsonFieldSupport) {
                            annotation = TypeUtils.getSupperMethodAnnotation(clazz, method2);
                        }
                        if (annotation != null) {
                            if (annotation.deserialize()) {
                                ordinal2 = annotation.ordinal();
                                serialzeFeatures2 = SerializerFeature.of(annotation.serialzeFeatures());
                                if (annotation.name().length() != 0) {
                                    addField(arrayList, new FieldInfo(annotation.name(), method2, null, clazz, type, ordinal2, serialzeFeatures2, annotation, null, fieldGenericSupport), fieldOnly);
                                    TypeUtils.setAccessible(clazz, method2, classModifiers);
                                }
                            }
                        }
                        if (methodName.startsWith("set")) {
                            char c3 = methodName.charAt(3);
                            if (Character.isUpperCase(c3)) {
                                if (TypeUtils.compatibleWithJavaBean) {
                                    propertyName2 = TypeUtils.decapitalize(methodName.substring(3));
                                } else {
                                    propertyName2 = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                                }
                            } else if (c3 == '_') {
                                propertyName2 = methodName.substring(4);
                            } else if (c3 == 'f') {
                                propertyName2 = methodName.substring(3);
                            } else if (methodName.length() >= 5 && Character.isUpperCase(methodName.charAt(4))) {
                                propertyName2 = TypeUtils.decapitalize(methodName.substring(3));
                            }
                            Field field3 = TypeUtils.getField(clazz, propertyName2, declaredFields2, hashMap);
                            if (field3 == null && method2.getParameterTypes()[0] == Boolean.TYPE) {
                                field3 = TypeUtils.getField(clazz, "is" + Character.toUpperCase(propertyName2.charAt(0)) + propertyName2.substring(1), declaredFields2, hashMap);
                            }
                            if (field3 != null) {
                                JSONField fieldAnnotation4 = jsonFieldSupport ? (JSONField) field3.getAnnotation(JSONField.class) : null;
                                if (fieldAnnotation4 != null) {
                                    ordinal2 = fieldAnnotation4.ordinal();
                                    serialzeFeatures2 = SerializerFeature.of(fieldAnnotation4.serialzeFeatures());
                                    if (fieldAnnotation4.name().length() != 0) {
                                        addField(arrayList, new FieldInfo(fieldAnnotation4.name(), method2, field3, clazz, type, ordinal2, serialzeFeatures2, annotation, fieldAnnotation4, fieldGenericSupport), fieldOnly);
                                    } else if (annotation == null) {
                                        annotation = fieldAnnotation4;
                                    }
                                }
                            }
                            if (propertyNamingStrategy != null) {
                                propertyName2 = propertyNamingStrategy.translate(propertyName2);
                            }
                            addField(arrayList, new FieldInfo(propertyName2, method2, null, clazz, type, ordinal2, serialzeFeatures2, annotation, null, fieldGenericSupport), fieldOnly);
                            TypeUtils.setAccessible(clazz, method2, classModifiers);
                        }
                    }
                }
            }
        }
        ArrayList<Field> arrayList3 = new ArrayList<>(declaredFields2.length);
        for (Field f : declaredFields2) {
            int modifiers = f.getModifiers();
            if ((modifiers & 8) == 0) {
                if ((modifiers & 16) != 0) {
                    Class<?> fieldType3 = f.getType();
                    if (!(Map.class.isAssignableFrom(fieldType3) || Collection.class.isAssignableFrom(fieldType3))) {
                    }
                }
                if ((f.getModifiers() & 1) != 0) {
                    arrayList3.add(f);
                }
            }
        }
        Class<?> c = clazz.getSuperclass();
        while (c != null && c != Object.class) {
            for (Field f2 : c.getDeclaredFields()) {
                int modifiers2 = f2.getModifiers();
                if ((modifiers2 & 8) == 0) {
                    if ((modifiers2 & 16) != 0) {
                        Class<?> fieldType4 = f2.getType();
                        if (!(Map.class.isAssignableFrom(fieldType4) || Collection.class.isAssignableFrom(fieldType4))) {
                        }
                    }
                    if ((modifiers2 & 1) != 0) {
                        arrayList3.add(f2);
                    }
                }
            }
            c = c.getSuperclass();
        }
        for (Field field4 : arrayList3) {
            String fieldName = field4.getName();
            boolean contains = false;
            int size = arrayList.size();
            for (int i11 = 0; i11 < size; i11++) {
                if (((FieldInfo) arrayList.get(i11)).name.equals(fieldName)) {
                    contains = true;
                }
            }
            if (!contains) {
                int ordinal3 = 0;
                int serialzeFeatures3 = 0;
                String propertyName3 = fieldName;
                JSONField fieldAnnotation5 = jsonFieldSupport ? (JSONField) field4.getAnnotation(JSONField.class) : null;
                if (fieldAnnotation5 != null) {
                    ordinal3 = fieldAnnotation5.ordinal();
                    serialzeFeatures3 = SerializerFeature.of(fieldAnnotation5.serialzeFeatures());
                    if (fieldAnnotation5.name().length() != 0) {
                        propertyName3 = fieldAnnotation5.name();
                    }
                }
                if (propertyNamingStrategy != null) {
                    propertyName3 = propertyNamingStrategy.translate(propertyName3);
                }
                TypeUtils.setAccessible(clazz, field4, classModifiers);
                addField(arrayList, new FieldInfo(propertyName3, null, field4, clazz, type, ordinal3, serialzeFeatures3, null, fieldAnnotation5, fieldGenericSupport), fieldOnly);
            }
        }
        if (!fieldOnly) {
            for (Method method3 : methods) {
                String methodName2 = method3.getName();
                if (methodName2.length() >= 4) {
                    if (methodName2.startsWith("get") && Character.isUpperCase(methodName2.charAt(3)) && method3.getParameterTypes().length == 0) {
                        Class<?> methodReturnType = method3.getReturnType();
                        if (Collection.class.isAssignableFrom(methodReturnType) || Map.class.isAssignableFrom(methodReturnType)) {
                            JSONField annotation2 = jsonFieldSupport ? (JSONField) method3.getAnnotation(JSONField.class) : null;
                            if (annotation2 != null) {
                                String annotationName = annotation2.name();
                                if (annotationName.length() > 0) {
                                    propertyName = annotationName;
                                    addField(arrayList, new FieldInfo(propertyName, method3, null, clazz, type, 0, 0, annotation2, null, fieldGenericSupport), fieldOnly);
                                    TypeUtils.setAccessible(clazz, method3, classModifiers);
                                }
                            }
                            propertyName = Character.toLowerCase(methodName2.charAt(3)) + methodName2.substring(4);
                            addField(arrayList, new FieldInfo(propertyName, method3, null, clazz, type, 0, 0, annotation2, null, fieldGenericSupport), fieldOnly);
                            TypeUtils.setAccessible(clazz, method3, classModifiers);
                        }
                    }
                }
            }
        }
        FieldInfo[] fields5 = new FieldInfo[arrayList.size()];
        arrayList.toArray(fields5);
        FieldInfo[] sortedFields5 = new FieldInfo[fields5.length];
        System.arraycopy(fields5, 0, sortedFields5, 0, fields5.length);
        Arrays.sort(sortedFields5);
        return new JavaBeanInfo(clazz, defaultConstructor2, creatorConstructor2, factoryMethod2, fields5, sortedFields5, jsonTypeSupport ? (JSONType) clazz.getAnnotation(JSONType.class) : null, creatorConstructorParameters2);
    }
}
