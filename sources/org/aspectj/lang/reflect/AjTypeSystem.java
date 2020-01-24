package org.aspectj.lang.reflect;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.aspectj.internal.lang.reflect.AjTypeImpl;

public class AjTypeSystem {
    private static Map<Class, WeakReference<AjType>> ajTypes = Collections.synchronizedMap(new WeakHashMap());

    public static <T> AjType<T> getAjType(Class<T> fromClass) {
        WeakReference<AjType> weakRefToAjType = (WeakReference) ajTypes.get(fromClass);
        if (weakRefToAjType != null) {
            AjType<T> theAjType = (AjType) weakRefToAjType.get();
            if (theAjType != null) {
                return theAjType;
            }
            AjType<T> ajTypeImpl = new AjTypeImpl<>(fromClass);
            ajTypes.put(fromClass, new WeakReference(ajTypeImpl));
            return ajTypeImpl;
        }
        AjType<T> ajTypeImpl2 = new AjTypeImpl<>(fromClass);
        ajTypes.put(fromClass, new WeakReference(ajTypeImpl2));
        return ajTypeImpl2;
    }
}
