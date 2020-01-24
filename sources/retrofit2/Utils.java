package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import okio.Buffer;

final class Utils {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private static final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        GenericArrayTypeImpl(Type componentType2) {
            this.componentType = componentType2;
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object o) {
            return (o instanceof GenericArrayType) && Utils.equals(this, (GenericArrayType) o);
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return Utils.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        ParameterizedTypeImpl(@Nullable Type ownerType2, Type rawType2, Type... typeArguments2) {
            boolean z;
            boolean z2 = true;
            if (rawType2 instanceof Class) {
                if (ownerType2 == null) {
                    z = true;
                } else {
                    z = false;
                }
                if (((Class) rawType2).getEnclosingClass() != null) {
                    z2 = false;
                }
                if (z != z2) {
                    throw new IllegalArgumentException();
                }
            }
            for (Type typeArgument : typeArguments2) {
                Utils.checkNotNull(typeArgument, "typeArgument == null");
                Utils.checkNotPrimitive(typeArgument);
            }
            this.ownerType = ownerType2;
            this.rawType = rawType2;
            this.typeArguments = (Type[]) typeArguments2.clone();
        }

        public Type[] getActualTypeArguments() {
            return (Type[]) this.typeArguments.clone();
        }

        public Type getRawType() {
            return this.rawType;
        }

        public Type getOwnerType() {
            return this.ownerType;
        }

        public boolean equals(Object other) {
            return (other instanceof ParameterizedType) && Utils.equals(this, (ParameterizedType) other);
        }

        public int hashCode() {
            return (this.ownerType != null ? this.ownerType.hashCode() : 0) ^ (this.rawType.hashCode() ^ Arrays.hashCode(this.typeArguments));
        }

        public String toString() {
            if (this.typeArguments.length == 0) {
                return Utils.typeToString(this.rawType);
            }
            StringBuilder result = new StringBuilder((this.typeArguments.length + 1) * 30);
            result.append(Utils.typeToString(this.rawType));
            result.append("<").append(Utils.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; i++) {
                result.append(", ").append(Utils.typeToString(this.typeArguments[i]));
            }
            return result.append(">").toString();
        }
    }

    private static final class WildcardTypeImpl implements WildcardType {
        private final Type lowerBound;
        private final Type upperBound;

        WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            if (lowerBounds.length > 1) {
                throw new IllegalArgumentException();
            } else if (upperBounds.length != 1) {
                throw new IllegalArgumentException();
            } else if (lowerBounds.length == 1) {
                if (lowerBounds[0] == null) {
                    throw new NullPointerException();
                }
                Utils.checkNotPrimitive(lowerBounds[0]);
                if (upperBounds[0] != Object.class) {
                    throw new IllegalArgumentException();
                }
                this.lowerBound = lowerBounds[0];
                this.upperBound = Object.class;
            } else if (upperBounds[0] == null) {
                throw new NullPointerException();
            } else {
                Utils.checkNotPrimitive(upperBounds[0]);
                this.lowerBound = null;
                this.upperBound = upperBounds[0];
            }
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        public Type[] getLowerBounds() {
            if (this.lowerBound == null) {
                return Utils.EMPTY_TYPE_ARRAY;
            }
            return new Type[]{this.lowerBound};
        }

        public boolean equals(Object other) {
            return (other instanceof WildcardType) && Utils.equals(this, (WildcardType) other);
        }

        public int hashCode() {
            return (this.lowerBound != null ? this.lowerBound.hashCode() + 31 : 1) ^ (this.upperBound.hashCode() + 31);
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + Utils.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + Utils.typeToString(this.upperBound);
        }
    }

    private Utils() {
    }

    static Class<?> getRawType(Type type) {
        checkNotNull(type, "type == null");
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
            throw new IllegalArgumentException();
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return getRawType(((WildcardType) type).getUpperBounds()[0]);
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
        }
    }

    static boolean equals(Type a, Type b) {
        boolean z = true;
        if (a == b) {
            return true;
        }
        if (a instanceof Class) {
            return a.equals(b);
        }
        if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType pa = (ParameterizedType) a;
            ParameterizedType pb = (ParameterizedType) b;
            Type ownerA = pa.getOwnerType();
            Type ownerB = pb.getOwnerType();
            if ((ownerA != ownerB && (ownerA == null || !ownerA.equals(ownerB))) || !pa.getRawType().equals(pb.getRawType()) || !Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments())) {
                z = false;
            }
            return z;
        } else if (a instanceof GenericArrayType) {
            if (!(b instanceof GenericArrayType)) {
                return false;
            }
            return equals(((GenericArrayType) a).getGenericComponentType(), ((GenericArrayType) b).getGenericComponentType());
        } else if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }
            WildcardType wa = (WildcardType) a;
            WildcardType wb = (WildcardType) b;
            if (!Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds()) || !Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds())) {
                z = false;
            }
            return z;
        } else if (!(a instanceof TypeVariable) || !(b instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable<?> va = (TypeVariable) a;
            TypeVariable<?> vb = (TypeVariable) b;
            if (va.getGenericDeclaration() != vb.getGenericDeclaration() || !va.getName().equals(vb.getName())) {
                z = false;
            }
            return z;
        }
    }

    static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }
        if (toResolve.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                }
                if (toResolve.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
                }
            }
        }
        if (!rawType.isInterface()) {
            while (rawType != Object.class) {
                Class<?> rawSupertype = rawType.getSuperclass();
                if (rawSupertype == toResolve) {
                    return rawType.getGenericSuperclass();
                }
                if (toResolve.isAssignableFrom(rawSupertype)) {
                    return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
                }
                rawType = rawSupertype;
            }
        }
        return toResolve;
    }

    private static int indexOf(Object[] array, Object toFind) {
        for (int i = 0; i < array.length; i++) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    static String typeToString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
        if (supertype.isAssignableFrom(contextRawType)) {
            return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARNING: type inference failed for: r11v2, types: [retrofit2.Utils$GenericArrayTypeImpl] */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r10v11, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.lang.reflect.TypeVariable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.reflect.Type resolve(java.lang.reflect.Type r22, java.lang.Class<?> r23, java.lang.reflect.Type r24) {
        /*
        L_0x0000:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.TypeVariable
            r19 = r0
            if (r19 == 0) goto L_0x001f
            r17 = r24
            java.lang.reflect.TypeVariable r17 = (java.lang.reflect.TypeVariable) r17
            r0 = r22
            r1 = r23
            r2 = r17
            java.lang.reflect.Type r24 = resolveTypeVariable(r0, r1, r2)
            r0 = r24
            r1 = r17
            if (r0 != r1) goto L_0x0000
            r10 = r24
        L_0x001e:
            return r10
        L_0x001f:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.Class
            r19 = r0
            if (r19 == 0) goto L_0x004b
            r19 = r24
            java.lang.Class r19 = (java.lang.Class) r19
            boolean r19 = r19.isArray()
            if (r19 == 0) goto L_0x004b
            r11 = r24
            java.lang.Class r11 = (java.lang.Class) r11
            java.lang.Class r5 = r11.getComponentType()
            r0 = r22
            r1 = r23
            java.lang.reflect.Type r8 = resolve(r0, r1, r5)
            if (r5 != r8) goto L_0x0045
        L_0x0043:
            r10 = r11
            goto L_0x001e
        L_0x0045:
            retrofit2.Utils$GenericArrayTypeImpl r11 = new retrofit2.Utils$GenericArrayTypeImpl
            r11.<init>(r8)
            goto L_0x0043
        L_0x004b:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.GenericArrayType
            r19 = r0
            if (r19 == 0) goto L_0x006b
            r10 = r24
            java.lang.reflect.GenericArrayType r10 = (java.lang.reflect.GenericArrayType) r10
            java.lang.reflect.Type r5 = r10.getGenericComponentType()
            r0 = r22
            r1 = r23
            java.lang.reflect.Type r8 = resolve(r0, r1, r5)
            if (r5 == r8) goto L_0x001e
            retrofit2.Utils$GenericArrayTypeImpl r10 = new retrofit2.Utils$GenericArrayTypeImpl
            r10.<init>(r8)
            goto L_0x001e
        L_0x006b:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.ParameterizedType
            r19 = r0
            if (r19 == 0) goto L_0x00c6
            r10 = r24
            java.lang.reflect.ParameterizedType r10 = (java.lang.reflect.ParameterizedType) r10
            java.lang.reflect.Type r14 = r10.getOwnerType()
            r0 = r22
            r1 = r23
            java.lang.reflect.Type r9 = resolve(r0, r1, r14)
            if (r9 == r14) goto L_0x00b1
            r4 = 1
        L_0x0086:
            java.lang.reflect.Type[] r3 = r10.getActualTypeArguments()
            r16 = 0
            int r6 = r3.length
        L_0x008d:
            r0 = r16
            if (r0 >= r6) goto L_0x00b3
            r19 = r3[r16]
            r0 = r22
            r1 = r23
            r2 = r19
            java.lang.reflect.Type r15 = resolve(r0, r1, r2)
            r19 = r3[r16]
            r0 = r19
            if (r15 == r0) goto L_0x00ae
            if (r4 != 0) goto L_0x00ac
            java.lang.Object r3 = r3.clone()
            java.lang.reflect.Type[] r3 = (java.lang.reflect.Type[]) r3
            r4 = 1
        L_0x00ac:
            r3[r16] = r15
        L_0x00ae:
            int r16 = r16 + 1
            goto L_0x008d
        L_0x00b1:
            r4 = 0
            goto L_0x0086
        L_0x00b3:
            if (r4 == 0) goto L_0x001e
            retrofit2.Utils$ParameterizedTypeImpl r19 = new retrofit2.Utils$ParameterizedTypeImpl
            java.lang.reflect.Type r20 = r10.getRawType()
            r0 = r19
            r1 = r20
            r0.<init>(r9, r1, r3)
            r10 = r19
            goto L_0x001e
        L_0x00c6:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.WildcardType
            r19 = r0
            if (r19 == 0) goto L_0x015c
            r10 = r24
            java.lang.reflect.WildcardType r10 = (java.lang.reflect.WildcardType) r10
            java.lang.reflect.Type[] r12 = r10.getLowerBounds()
            java.lang.reflect.Type[] r13 = r10.getUpperBounds()
            int r0 = r12.length
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0120
            r19 = 0
            r19 = r12[r19]
            r0 = r22
            r1 = r23
            r2 = r19
            java.lang.reflect.Type r7 = resolve(r0, r1, r2)
            r19 = 0
            r19 = r12[r19]
            r0 = r19
            if (r7 == r0) goto L_0x001e
            retrofit2.Utils$WildcardTypeImpl r10 = new retrofit2.Utils$WildcardTypeImpl
            r19 = 1
            r0 = r19
            java.lang.reflect.Type[] r0 = new java.lang.reflect.Type[r0]
            r19 = r0
            r20 = 0
            java.lang.Class<java.lang.Object> r21 = java.lang.Object.class
            r19[r20] = r21
            r20 = 1
            r0 = r20
            java.lang.reflect.Type[] r0 = new java.lang.reflect.Type[r0]
            r20 = r0
            r21 = 0
            r20[r21] = r7
            r0 = r19
            r1 = r20
            r10.<init>(r0, r1)
            goto L_0x001e
        L_0x0120:
            int r0 = r13.length
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x001e
            r19 = 0
            r19 = r13[r19]
            r0 = r22
            r1 = r23
            r2 = r19
            java.lang.reflect.Type r18 = resolve(r0, r1, r2)
            r19 = 0
            r19 = r13[r19]
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x001e
            retrofit2.Utils$WildcardTypeImpl r10 = new retrofit2.Utils$WildcardTypeImpl
            r19 = 1
            r0 = r19
            java.lang.reflect.Type[] r0 = new java.lang.reflect.Type[r0]
            r19 = r0
            r20 = 0
            r19[r20] = r18
            java.lang.reflect.Type[] r20 = EMPTY_TYPE_ARRAY
            r0 = r19
            r1 = r20
            r10.<init>(r0, r1)
            goto L_0x001e
        L_0x015c:
            r10 = r24
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: retrofit2.Utils.resolve(java.lang.reflect.Type, java.lang.Class, java.lang.reflect.Type):java.lang.reflect.Type");
    }

    private static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class<?> declaredByRaw = declaringClassOf(unknown);
        if (declaredByRaw == null) {
            return unknown;
        }
        Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
        if (!(declaredBy instanceof ParameterizedType)) {
            return unknown;
        }
        return ((ParameterizedType) declaredBy).getActualTypeArguments()[indexOf(declaredByRaw.getTypeParameters(), unknown)];
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static void checkNotPrimitive(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException();
        }
    }

    static <T> T checkNotNull(@Nullable T object, String message) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException(message);
    }

    static boolean isAnnotationPresent(Annotation[] annotations, Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    static ResponseBody buffer(ResponseBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.source().readAll(buffer);
        return ResponseBody.create(body.contentType(), body.contentLength(), buffer);
    }

    static <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        } else if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    static Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException("Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        Type paramType = types[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    static boolean hasUnresolvableType(@Nullable Type type) {
        if (type instanceof Class) {
            return false;
        }
        if (type instanceof ParameterizedType) {
            for (Type typeArgument : ((ParameterizedType) type).getActualTypeArguments()) {
                if (hasUnresolvableType(typeArgument)) {
                    return true;
                }
            }
            return false;
        } else if (type instanceof GenericArrayType) {
            return hasUnresolvableType(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (type instanceof TypeVariable) {
                return true;
            }
            if (type instanceof WildcardType) {
                return true;
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
        }
    }

    static Type getCallResponseType(Type returnType) {
        if (returnType instanceof ParameterizedType) {
            return getParameterUpperBound(0, (ParameterizedType) returnType);
        }
        throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
    }

    static void throwIfFatal(Throwable t) {
        if (t instanceof VirtualMachineError) {
            throw ((VirtualMachineError) t);
        } else if (t instanceof ThreadDeath) {
            throw ((ThreadDeath) t);
        } else if (t instanceof LinkageError) {
            throw ((LinkageError) t);
        }
    }
}
