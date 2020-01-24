package com.google.common.reflect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import kotlin.text.Typography;

final class Types {
    /* access modifiers changed from: private */
    public static final Joiner COMMA_JOINER = Joiner.on(", ").useForNull("null");
    /* access modifiers changed from: private */
    public static final Function<Type, String> TYPE_TO_STRING = new Function<Type, String>() {
        public String apply(Type from) {
            return Types.toString(from);
        }
    };

    private enum ClassOwnership {
        OWNED_BY_ENCLOSING_CLASS {
            /* access modifiers changed from: 0000 */
            @Nullable
            public Class<?> getOwnerType(Class<?> rawType) {
                return rawType.getEnclosingClass();
            }
        },
        LOCAL_CLASS_HAS_NO_OWNER {
            /* access modifiers changed from: 0000 */
            @Nullable
            public Class<?> getOwnerType(Class<?> rawType) {
                if (rawType.isLocalClass()) {
                    return null;
                }
                return rawType.getEnclosingClass();
            }
        };
        
        static final ClassOwnership JVM_BEHAVIOR = null;

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract Class<?> getOwnerType(Class<?> cls);

        static {
            JVM_BEHAVIOR = detectJvmBehavior();
        }

        private static ClassOwnership detectJvmBehavior() {
            ClassOwnership[] arr$;
            ParameterizedType parameterizedType = (ParameterizedType) new AnonymousClass1LocalClass<String>() {
            }.getClass().getGenericSuperclass();
            for (ClassOwnership behavior : values()) {
                if (behavior.getOwnerType(AnonymousClass1LocalClass.class) == parameterizedType.getOwnerType()) {
                    return behavior;
                }
            }
            throw new AssertionError();
        }
    }

    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        GenericArrayTypeImpl(Type componentType2) {
            this.componentType = JavaVersion.CURRENT.usedInGenericType(componentType2);
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public String toString() {
            return Types.toString(this.componentType) + "[]";
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof GenericArrayType)) {
                return false;
            }
            return Objects.equal(getGenericComponentType(), ((GenericArrayType) obj).getGenericComponentType());
        }
    }

    enum JavaVersion {
        JAVA6 {
            /* access modifiers changed from: 0000 */
            public GenericArrayType newArrayType(Type componentType) {
                return new GenericArrayTypeImpl(componentType);
            }

            /* access modifiers changed from: 0000 */
            public Type usedInGenericType(Type type) {
                Preconditions.checkNotNull(type);
                if (!(type instanceof Class)) {
                    return type;
                }
                Class<?> cls = (Class) type;
                if (cls.isArray()) {
                    return new GenericArrayTypeImpl(cls.getComponentType());
                }
                return type;
            }
        },
        JAVA7 {
            /* access modifiers changed from: 0000 */
            public Type newArrayType(Type componentType) {
                if (componentType instanceof Class) {
                    return Types.getArrayClass((Class) componentType);
                }
                return new GenericArrayTypeImpl(componentType);
            }

            /* access modifiers changed from: 0000 */
            public Type usedInGenericType(Type type) {
                return (Type) Preconditions.checkNotNull(type);
            }
        };
        
        static final JavaVersion CURRENT = null;

        /* access modifiers changed from: 0000 */
        public abstract Type newArrayType(Type type);

        /* access modifiers changed from: 0000 */
        public abstract Type usedInGenericType(Type type);

        /* access modifiers changed from: 0000 */
        public final ImmutableList<Type> usedInGenericType(Type[] types) {
            Builder<Type> builder = ImmutableList.builder();
            for (Type type : types) {
                builder.add((Object) usedInGenericType(type));
            }
            return builder.build();
        }
    }

    static final class NativeTypeVariableEquals<X> {
        static final boolean NATIVE_TYPE_VARIABLE_ONLY;

        NativeTypeVariableEquals() {
        }

        static {
            boolean z = false;
            if (!NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X", new Type[0]))) {
                z = true;
            }
            NATIVE_TYPE_VARIABLE_ONLY = z;
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> argumentsList;
        private final Type ownerType;
        private final Class<?> rawType;

        ParameterizedTypeImpl(@Nullable Type ownerType2, Class<?> rawType2, Type[] typeArguments) {
            Preconditions.checkNotNull(rawType2);
            Preconditions.checkArgument(typeArguments.length == rawType2.getTypeParameters().length);
            Types.disallowPrimitiveType(typeArguments, "type parameter");
            this.ownerType = ownerType2;
            this.rawType = rawType2;
            this.argumentsList = JavaVersion.CURRENT.usedInGenericType(typeArguments);
        }

        public Type[] getActualTypeArguments() {
            return Types.toArray(this.argumentsList);
        }

        public Type getRawType() {
            return this.rawType;
        }

        public Type getOwnerType() {
            return this.ownerType;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (this.ownerType != null) {
                builder.append(Types.toString(this.ownerType)).append('.');
            }
            builder.append(this.rawType.getName()).append(Typography.less).append(Types.COMMA_JOINER.join(Iterables.transform(this.argumentsList, Types.TYPE_TO_STRING))).append(Typography.greater);
            return builder.toString();
        }

        public int hashCode() {
            return ((this.ownerType == null ? 0 : this.ownerType.hashCode()) ^ this.argumentsList.hashCode()) ^ this.rawType.hashCode();
        }

        public boolean equals(Object other) {
            if (!(other instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType that = (ParameterizedType) other;
            if (!getRawType().equals(that.getRawType()) || !Objects.equal(getOwnerType(), that.getOwnerType()) || !Arrays.equals(getActualTypeArguments(), that.getActualTypeArguments())) {
                return false;
            }
            return true;
        }
    }

    private static final class TypeVariableImpl<D extends GenericDeclaration> implements TypeVariable<D> {
        private final ImmutableList<Type> bounds;
        private final D genericDeclaration;
        private final String name;

        TypeVariableImpl(D genericDeclaration2, String name2, Type[] bounds2) {
            Types.disallowPrimitiveType(bounds2, "bound for type variable");
            this.genericDeclaration = (GenericDeclaration) Preconditions.checkNotNull(genericDeclaration2);
            this.name = (String) Preconditions.checkNotNull(name2);
            this.bounds = ImmutableList.copyOf((E[]) bounds2);
        }

        public Type[] getBounds() {
            return Types.toArray(this.bounds);
        }

        public D getGenericDeclaration() {
            return this.genericDeclaration;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public int hashCode() {
            return this.genericDeclaration.hashCode() ^ this.name.hashCode();
        }

        public boolean equals(Object obj) {
            if (NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) {
                if (!(obj instanceof TypeVariableImpl)) {
                    return false;
                }
                TypeVariableImpl<?> that = (TypeVariableImpl) obj;
                if (!this.name.equals(that.getName()) || !this.genericDeclaration.equals(that.getGenericDeclaration()) || !this.bounds.equals(that.bounds)) {
                    return false;
                }
                return true;
            } else if (!(obj instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable<?> that2 = (TypeVariable) obj;
                if (!this.name.equals(that2.getName()) || !this.genericDeclaration.equals(that2.getGenericDeclaration())) {
                    return false;
                }
                return true;
            }
        }
    }

    static final class WildcardTypeImpl implements WildcardType, Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> lowerBounds;
        private final ImmutableList<Type> upperBounds;

        WildcardTypeImpl(Type[] lowerBounds2, Type[] upperBounds2) {
            Types.disallowPrimitiveType(lowerBounds2, "lower bound for wildcard");
            Types.disallowPrimitiveType(upperBounds2, "upper bound for wildcard");
            this.lowerBounds = JavaVersion.CURRENT.usedInGenericType(lowerBounds2);
            this.upperBounds = JavaVersion.CURRENT.usedInGenericType(upperBounds2);
        }

        public Type[] getLowerBounds() {
            return Types.toArray(this.lowerBounds);
        }

        public Type[] getUpperBounds() {
            return Types.toArray(this.upperBounds);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WildcardType)) {
                return false;
            }
            WildcardType that = (WildcardType) obj;
            if (!this.lowerBounds.equals(Arrays.asList(that.getLowerBounds())) || !this.upperBounds.equals(Arrays.asList(that.getUpperBounds()))) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }

        public String toString() {
            StringBuilder builder = new StringBuilder("?");
            Iterator i$ = this.lowerBounds.iterator();
            while (i$.hasNext()) {
                builder.append(" super ").append(Types.toString((Type) i$.next()));
            }
            for (Type upperBound : Types.filterUpperBounds(this.upperBounds)) {
                builder.append(" extends ").append(Types.toString(upperBound));
            }
            return builder.toString();
        }
    }

    static Type newArrayType(Type componentType) {
        boolean z;
        boolean z2 = true;
        if (!(componentType instanceof WildcardType)) {
            return JavaVersion.CURRENT.newArrayType(componentType);
        }
        WildcardType wildcard = (WildcardType) componentType;
        Type[] lowerBounds = wildcard.getLowerBounds();
        if (lowerBounds.length <= 1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Wildcard cannot have more than one lower bounds.");
        if (lowerBounds.length == 1) {
            return supertypeOf(newArrayType(lowerBounds[0]));
        }
        Type[] upperBounds = wildcard.getUpperBounds();
        if (upperBounds.length != 1) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Wildcard should have only one upper bound.");
        return subtypeOf(newArrayType(upperBounds[0]));
    }

    static ParameterizedType newParameterizedTypeWithOwner(@Nullable Type ownerType, Class<?> rawType, Type... arguments) {
        boolean z;
        if (ownerType == null) {
            return newParameterizedType(rawType, arguments);
        }
        Preconditions.checkNotNull(arguments);
        if (rawType.getEnclosingClass() != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Owner type for unenclosed %s", rawType);
        return new ParameterizedTypeImpl(ownerType, rawType, arguments);
    }

    static ParameterizedType newParameterizedType(Class<?> rawType, Type... arguments) {
        return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(rawType), rawType, arguments);
    }

    static <D extends GenericDeclaration> TypeVariable<D> newArtificialTypeVariable(D declaration, String name, Type... bounds) {
        if (bounds.length == 0) {
            bounds = new Type[]{Object.class};
        }
        return new TypeVariableImpl(declaration, name, bounds);
    }

    @VisibleForTesting
    static WildcardType subtypeOf(Type upperBound) {
        return new WildcardTypeImpl(new Type[0], new Type[]{upperBound});
    }

    @VisibleForTesting
    static WildcardType supertypeOf(Type lowerBound) {
        return new WildcardTypeImpl(new Type[]{lowerBound}, new Type[]{Object.class});
    }

    static String toString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    @Nullable
    static Type getComponentType(Type type) {
        Preconditions.checkNotNull(type);
        final AtomicReference<Type> result = new AtomicReference<>();
        new TypeVisitor() {
            /* access modifiers changed from: 0000 */
            public void visitTypeVariable(TypeVariable<?> t) {
                result.set(Types.subtypeOfComponentType(t.getBounds()));
            }

            /* access modifiers changed from: 0000 */
            public void visitWildcardType(WildcardType t) {
                result.set(Types.subtypeOfComponentType(t.getUpperBounds()));
            }

            /* access modifiers changed from: 0000 */
            public void visitGenericArrayType(GenericArrayType t) {
                result.set(t.getGenericComponentType());
            }

            /* access modifiers changed from: 0000 */
            public void visitClass(Class<?> t) {
                result.set(t.getComponentType());
            }
        }.visit(type);
        return (Type) result.get();
    }

    /* access modifiers changed from: private */
    @Nullable
    public static Type subtypeOfComponentType(Type[] bounds) {
        for (Type bound : bounds) {
            Type componentType = getComponentType(bound);
            if (componentType != null) {
                if (componentType instanceof Class) {
                    Class<?> componentClass = (Class) componentType;
                    if (componentClass.isPrimitive()) {
                        return componentClass;
                    }
                }
                return subtypeOf(componentType);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static Type[] toArray(Collection<Type> types) {
        return (Type[]) types.toArray(new Type[types.size()]);
    }

    /* access modifiers changed from: private */
    public static Iterable<Type> filterUpperBounds(Iterable<Type> bounds) {
        return Iterables.filter(bounds, Predicates.not(Predicates.equalTo(Object.class)));
    }

    /* access modifiers changed from: private */
    public static void disallowPrimitiveType(Type[] types, String usedAs) {
        Type[] arr$;
        boolean z;
        for (Type type : types) {
            if (type instanceof Class) {
                Class<?> cls = (Class) type;
                if (!cls.isPrimitive()) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkArgument(z, "Primitive type '%s' used as %s", cls, usedAs);
            }
        }
    }

    static Class<?> getArrayClass(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    private Types() {
    }
}
