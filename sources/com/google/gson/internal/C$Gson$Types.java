package com.google.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* renamed from: com.google.gson.internal.$Gson$Types reason: invalid class name */
public final class C$Gson$Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    /* renamed from: com.google.gson.internal.$Gson$Types$GenericArrayTypeImpl */
    /* compiled from: $Gson$Types */
    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        public GenericArrayTypeImpl(Type componentType2) {
            this.componentType = C$Gson$Types.canonicalize(componentType2);
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object o) {
            return (o instanceof GenericArrayType) && C$Gson$Types.equals(this, (GenericArrayType) o);
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return C$Gson$Types.typeToString(this.componentType) + "[]";
        }
    }

    /* renamed from: com.google.gson.internal.$Gson$Types$ParameterizedTypeImpl */
    /* compiled from: $Gson$Types */
    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
        private static final long serialVersionUID = 0;
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type ownerType2, Type rawType2, Type... typeArguments2) {
            boolean isStaticOrTopLevelClass;
            boolean z = false;
            if (rawType2 instanceof Class) {
                Class<?> rawTypeAsClass = (Class) rawType2;
                if (Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null) {
                    isStaticOrTopLevelClass = true;
                } else {
                    isStaticOrTopLevelClass = false;
                }
                if (ownerType2 != null || isStaticOrTopLevelClass) {
                    z = true;
                }
                C$Gson$Preconditions.checkArgument(z);
            }
            this.ownerType = ownerType2 == null ? null : C$Gson$Types.canonicalize(ownerType2);
            this.rawType = C$Gson$Types.canonicalize(rawType2);
            this.typeArguments = (Type[]) typeArguments2.clone();
            int length = this.typeArguments.length;
            for (int t = 0; t < length; t++) {
                C$Gson$Preconditions.checkNotNull(this.typeArguments[t]);
                C$Gson$Types.checkNotPrimitive(this.typeArguments[t]);
                this.typeArguments[t] = C$Gson$Types.canonicalize(this.typeArguments[t]);
            }
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
            return (other instanceof ParameterizedType) && C$Gson$Types.equals(this, (ParameterizedType) other);
        }

        public int hashCode() {
            return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ C$Gson$Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            int length = this.typeArguments.length;
            if (length == 0) {
                return C$Gson$Types.typeToString(this.rawType);
            }
            StringBuilder stringBuilder = new StringBuilder((length + 1) * 30);
            stringBuilder.append(C$Gson$Types.typeToString(this.rawType)).append("<").append(C$Gson$Types.typeToString(this.typeArguments[0]));
            for (int i = 1; i < length; i++) {
                stringBuilder.append(", ").append(C$Gson$Types.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    /* renamed from: com.google.gson.internal.$Gson$Types$WildcardTypeImpl */
    /* compiled from: $Gson$Types */
    private static final class WildcardTypeImpl implements WildcardType, Serializable {
        private static final long serialVersionUID = 0;
        private final Type lowerBound;
        private final Type upperBound;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            boolean z;
            boolean z2 = true;
            C$Gson$Preconditions.checkArgument(lowerBounds.length <= 1);
            if (upperBounds.length == 1) {
                z = true;
            } else {
                z = false;
            }
            C$Gson$Preconditions.checkArgument(z);
            if (lowerBounds.length == 1) {
                C$Gson$Preconditions.checkNotNull(lowerBounds[0]);
                C$Gson$Types.checkNotPrimitive(lowerBounds[0]);
                if (upperBounds[0] != Object.class) {
                    z2 = false;
                }
                C$Gson$Preconditions.checkArgument(z2);
                this.lowerBound = C$Gson$Types.canonicalize(lowerBounds[0]);
                this.upperBound = Object.class;
                return;
            }
            C$Gson$Preconditions.checkNotNull(upperBounds[0]);
            C$Gson$Types.checkNotPrimitive(upperBounds[0]);
            this.lowerBound = null;
            this.upperBound = C$Gson$Types.canonicalize(upperBounds[0]);
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        public Type[] getLowerBounds() {
            if (this.lowerBound == null) {
                return C$Gson$Types.EMPTY_TYPE_ARRAY;
            }
            return new Type[]{this.lowerBound};
        }

        public boolean equals(Object other) {
            return (other instanceof WildcardType) && C$Gson$Types.equals(this, (WildcardType) other);
        }

        public int hashCode() {
            return (this.lowerBound != null ? this.lowerBound.hashCode() + 31 : 1) ^ (this.upperBound.hashCode() + 31);
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + C$Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + C$Gson$Types.typeToString(this.upperBound);
        }
    }

    private C$Gson$Types() {
        throw new UnsupportedOperationException();
    }

    public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type... typeArguments) {
        return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
    }

    public static GenericArrayType arrayOf(Type componentType) {
        return new GenericArrayTypeImpl(componentType);
    }

    public static WildcardType subtypeOf(Type bound) {
        return new WildcardTypeImpl(bound instanceof WildcardType ? ((WildcardType) bound).getUpperBounds() : new Type[]{bound}, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type bound) {
        return new WildcardTypeImpl(new Type[]{Object.class}, bound instanceof WildcardType ? ((WildcardType) bound).getLowerBounds() : new Type[]{bound});
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class<?> c = (Class) type;
            return (Type) (c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type;
            return new ParameterizedTypeImpl(p.getOwnerType(), p.getRawType(), p.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (!(type instanceof WildcardType)) {
                return type;
            }
            WildcardType w = (WildcardType) type;
            return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
        }
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            C$Gson$Preconditions.checkArgument(rawType instanceof Class);
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return getRawType(((WildcardType) type).getUpperBounds()[0]);
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
        }
    }

    static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static boolean equals(Type a, Type b) {
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
            if (!equal(pa.getOwnerType(), pb.getOwnerType()) || !pa.getRawType().equals(pb.getRawType()) || !Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments())) {
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

    static int hashCodeOrZero(Object o) {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    public static String typeToString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
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

    static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
        C$Gson$Preconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
        return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
    }

    public static Type getArrayComponentType(Type array) {
        if (array instanceof GenericArrayType) {
            return ((GenericArrayType) array).getGenericComponentType();
        }
        return ((Class) array).getComponentType();
    }

    public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
        Type collectionType = getSupertype(context, contextRawType, Collection.class);
        if (collectionType instanceof WildcardType) {
            collectionType = ((WildcardType) collectionType).getUpperBounds()[0];
        }
        if (collectionType instanceof ParameterizedType) {
            return ((ParameterizedType) collectionType).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    public static Type[] getMapKeyAndValueTypes(Type context, Class<?> contextRawType) {
        if (context == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type mapType = getSupertype(context, contextRawType, Map.class);
        if (mapType instanceof ParameterizedType) {
            return ((ParameterizedType) mapType).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
        return resolve(context, contextRawType, toResolve, new HashSet());
    }

    /* JADX WARNING: type inference failed for: r12v2, types: [java.lang.reflect.GenericArrayType] */
    /* JADX WARNING: type inference failed for: r12v3 */
    /* JADX WARNING: type inference failed for: r11v11, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.lang.reflect.TypeVariable] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Type resolve(java.lang.reflect.Type r22, java.lang.Class<?> r23, java.lang.reflect.Type r24, java.util.Collection<java.lang.reflect.TypeVariable> r25) {
        /*
        L_0x0000:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.TypeVariable
            r20 = r0
            if (r20 == 0) goto L_0x0033
            r18 = r24
            java.lang.reflect.TypeVariable r18 = (java.lang.reflect.TypeVariable) r18
            r0 = r25
            r1 = r18
            boolean r20 = r0.contains(r1)
            if (r20 == 0) goto L_0x0019
            r11 = r24
        L_0x0018:
            return r11
        L_0x0019:
            r0 = r25
            r1 = r18
            r0.add(r1)
            r0 = r22
            r1 = r23
            r2 = r18
            java.lang.reflect.Type r24 = resolveTypeVariable(r0, r1, r2)
            r0 = r24
            r1 = r18
            if (r0 != r1) goto L_0x0000
            r11 = r24
            goto L_0x0018
        L_0x0033:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.Class
            r20 = r0
            if (r20 == 0) goto L_0x0060
            r20 = r24
            java.lang.Class r20 = (java.lang.Class) r20
            boolean r20 = r20.isArray()
            if (r20 == 0) goto L_0x0060
            r12 = r24
            java.lang.Class r12 = (java.lang.Class) r12
            java.lang.Class r6 = r12.getComponentType()
            r0 = r22
            r1 = r23
            r2 = r25
            java.lang.reflect.Type r9 = resolve(r0, r1, r6, r2)
            if (r6 != r9) goto L_0x005b
        L_0x0059:
            r11 = r12
            goto L_0x0018
        L_0x005b:
            java.lang.reflect.GenericArrayType r12 = arrayOf(r9)
            goto L_0x0059
        L_0x0060:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.GenericArrayType
            r20 = r0
            if (r20 == 0) goto L_0x0081
            r11 = r24
            java.lang.reflect.GenericArrayType r11 = (java.lang.reflect.GenericArrayType) r11
            java.lang.reflect.Type r6 = r11.getGenericComponentType()
            r0 = r22
            r1 = r23
            r2 = r25
            java.lang.reflect.Type r9 = resolve(r0, r1, r6, r2)
            if (r6 == r9) goto L_0x0018
            java.lang.reflect.GenericArrayType r11 = arrayOf(r9)
            goto L_0x0018
        L_0x0081:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.ParameterizedType
            r20 = r0
            if (r20 == 0) goto L_0x00dd
            r11 = r24
            java.lang.reflect.ParameterizedType r11 = (java.lang.reflect.ParameterizedType) r11
            java.lang.reflect.Type r15 = r11.getOwnerType()
            r0 = r22
            r1 = r23
            r2 = r25
            java.lang.reflect.Type r10 = resolve(r0, r1, r15, r2)
            if (r10 == r15) goto L_0x00cd
            r5 = 1
        L_0x009e:
            java.lang.reflect.Type[] r4 = r11.getActualTypeArguments()
            r17 = 0
            int r7 = r4.length
        L_0x00a5:
            r0 = r17
            if (r0 >= r7) goto L_0x00cf
            r20 = r4[r17]
            r0 = r22
            r1 = r23
            r2 = r20
            r3 = r25
            java.lang.reflect.Type r16 = resolve(r0, r1, r2, r3)
            r20 = r4[r17]
            r0 = r16
            r1 = r20
            if (r0 == r1) goto L_0x00ca
            if (r5 != 0) goto L_0x00c8
            java.lang.Object r4 = r4.clone()
            java.lang.reflect.Type[] r4 = (java.lang.reflect.Type[]) r4
            r5 = 1
        L_0x00c8:
            r4[r17] = r16
        L_0x00ca:
            int r17 = r17 + 1
            goto L_0x00a5
        L_0x00cd:
            r5 = 0
            goto L_0x009e
        L_0x00cf:
            if (r5 == 0) goto L_0x0018
            java.lang.reflect.Type r20 = r11.getRawType()
            r0 = r20
            java.lang.reflect.ParameterizedType r11 = newParameterizedTypeWithOwner(r10, r0, r4)
            goto L_0x0018
        L_0x00dd:
            r0 = r24
            boolean r0 = r0 instanceof java.lang.reflect.WildcardType
            r20 = r0
            if (r20 == 0) goto L_0x0145
            r11 = r24
            java.lang.reflect.WildcardType r11 = (java.lang.reflect.WildcardType) r11
            java.lang.reflect.Type[] r13 = r11.getLowerBounds()
            java.lang.reflect.Type[] r14 = r11.getUpperBounds()
            int r0 = r13.length
            r20 = r0
            r21 = 1
            r0 = r20
            r1 = r21
            if (r0 != r1) goto L_0x011a
            r20 = 0
            r20 = r13[r20]
            r0 = r22
            r1 = r23
            r2 = r20
            r3 = r25
            java.lang.reflect.Type r8 = resolve(r0, r1, r2, r3)
            r20 = 0
            r20 = r13[r20]
            r0 = r20
            if (r8 == r0) goto L_0x0018
            java.lang.reflect.WildcardType r11 = supertypeOf(r8)
            goto L_0x0018
        L_0x011a:
            int r0 = r14.length
            r20 = r0
            r21 = 1
            r0 = r20
            r1 = r21
            if (r0 != r1) goto L_0x0018
            r20 = 0
            r20 = r14[r20]
            r0 = r22
            r1 = r23
            r2 = r20
            r3 = r25
            java.lang.reflect.Type r19 = resolve(r0, r1, r2, r3)
            r20 = 0
            r20 = r14[r20]
            r0 = r19
            r1 = r20
            if (r0 == r1) goto L_0x0018
            java.lang.reflect.WildcardType r11 = subtypeOf(r19)
            goto L_0x0018
        L_0x0145:
            r11 = r24
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.C$Gson$Types.resolve(java.lang.reflect.Type, java.lang.Class, java.lang.reflect.Type, java.util.Collection):java.lang.reflect.Type");
    }

    static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
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

    private static int indexOf(Object[] array, Object toFind) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            if (toFind.equals(array[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static void checkNotPrimitive(Type type) {
        C$Gson$Preconditions.checkArgument(!(type instanceof Class) || !((Class) type).isPrimitive());
    }
}
