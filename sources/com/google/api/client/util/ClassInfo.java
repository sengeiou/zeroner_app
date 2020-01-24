package com.google.api.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.WeakHashMap;

public final class ClassInfo {
    private static final Map<Class<?>, ClassInfo> CACHE = new WeakHashMap();
    private static final Map<Class<?>, ClassInfo> CACHE_IGNORE_CASE = new WeakHashMap();
    private final Class<?> clazz;
    private final boolean ignoreCase;
    private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap = new IdentityHashMap<>();
    final List<String> names;

    public static ClassInfo of(Class<?> underlyingClass) {
        return of(underlyingClass, false);
    }

    public static ClassInfo of(Class<?> underlyingClass, boolean ignoreCase2) {
        ClassInfo classInfo;
        if (underlyingClass == null) {
            return null;
        }
        Map<Class<?>, ClassInfo> cache = ignoreCase2 ? CACHE_IGNORE_CASE : CACHE;
        synchronized (cache) {
            classInfo = (ClassInfo) cache.get(underlyingClass);
            if (classInfo == null) {
                classInfo = new ClassInfo(underlyingClass, ignoreCase2);
                cache.put(underlyingClass, classInfo);
            }
        }
        return classInfo;
    }

    public Class<?> getUnderlyingClass() {
        return this.clazz;
    }

    public final boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public FieldInfo getFieldInfo(String name) {
        if (name != null) {
            if (this.ignoreCase) {
                name = name.toLowerCase();
            }
            name = name.intern();
        }
        return (FieldInfo) this.nameToFieldInfoMap.get(name);
    }

    public Field getField(String name) {
        FieldInfo fieldInfo = getFieldInfo(name);
        if (fieldInfo == null) {
            return null;
        }
        return fieldInfo.getField();
    }

    public boolean isEnum() {
        return this.clazz.isEnum();
    }

    public Collection<String> getNames() {
        return this.names;
    }

    private ClassInfo(Class<?> srcClass, boolean ignoreCase2) {
        Field[] arr$;
        this.clazz = srcClass;
        this.ignoreCase = ignoreCase2;
        boolean z = !ignoreCase2 || !srcClass.isEnum();
        String valueOf = String.valueOf(String.valueOf(srcClass));
        Preconditions.checkArgument(z, new StringBuilder(valueOf.length() + 31).append("cannot ignore case on an enum: ").append(valueOf).toString());
        TreeSet<String> nameSet = new TreeSet<>(new Comparator<String>() {
            public int compare(String s0, String s1) {
                if (s0 == s1) {
                    return 0;
                }
                if (s0 == null) {
                    return -1;
                }
                if (s1 == null) {
                    return 1;
                }
                return s0.compareTo(s1);
            }
        });
        for (Field field : srcClass.getDeclaredFields()) {
            FieldInfo fieldInfo = FieldInfo.of(field);
            if (fieldInfo != null) {
                String fieldName = fieldInfo.getName();
                if (ignoreCase2) {
                    fieldName = fieldName.toLowerCase().intern();
                }
                FieldInfo conflictingFieldInfo = (FieldInfo) this.nameToFieldInfoMap.get(fieldName);
                boolean z2 = conflictingFieldInfo == null;
                String str = "two fields have the same %sname <%s>: %s and %s";
                Object[] objArr = new Object[4];
                objArr[0] = ignoreCase2 ? "case-insensitive " : "";
                objArr[1] = fieldName;
                objArr[2] = field;
                objArr[3] = conflictingFieldInfo == null ? null : conflictingFieldInfo.getField();
                Preconditions.checkArgument(z2, str, objArr);
                this.nameToFieldInfoMap.put(fieldName, fieldInfo);
                nameSet.add(fieldName);
            }
        }
        Class<?> superClass = srcClass.getSuperclass();
        if (superClass != null) {
            ClassInfo superClassInfo = of(superClass, ignoreCase2);
            nameSet.addAll(superClassInfo.names);
            for (Entry<String, FieldInfo> e : superClassInfo.nameToFieldInfoMap.entrySet()) {
                String name = (String) e.getKey();
                if (!this.nameToFieldInfoMap.containsKey(name)) {
                    this.nameToFieldInfoMap.put(name, e.getValue());
                }
            }
        }
        this.names = nameSet.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(nameSet));
    }

    public Collection<FieldInfo> getFieldInfos() {
        return Collections.unmodifiableCollection(this.nameToFieldInfoMap.values());
    }
}
