package com.google.common.reflect;

import com.alibaba.android.arouter.utils.Consts;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Primitives;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract class TypeToken<T> extends TypeCapture<T> implements Serializable {
    /* access modifiers changed from: private */
    public final Type runtimeType;
    private transient TypeResolver typeResolver;

    private static abstract class TypeCollector<K> {
        static final TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new TypeCollector<TypeToken<?>>() {
            /* access modifiers changed from: 0000 */
            public Class<?> getRawType(TypeToken<?> type) {
                return type.getRawType();
            }

            /* access modifiers changed from: 0000 */
            public Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> type) {
                return type.getGenericInterfaces();
            }

            /* access modifiers changed from: 0000 */
            @Nullable
            public TypeToken<?> getSuperclass(TypeToken<?> type) {
                return type.getGenericSuperclass();
            }
        };
        static final TypeCollector<Class<?>> FOR_RAW_TYPE = new TypeCollector<Class<?>>() {
            /* access modifiers changed from: 0000 */
            public Class<?> getRawType(Class<?> type) {
                return type;
            }

            /* access modifiers changed from: 0000 */
            public Iterable<? extends Class<?>> getInterfaces(Class<?> type) {
                return Arrays.asList(type.getInterfaces());
            }

            /* access modifiers changed from: 0000 */
            @Nullable
            public Class<?> getSuperclass(Class<?> type) {
                return type.getSuperclass();
            }
        };

        private static class ForwardingTypeCollector<K> extends TypeCollector<K> {
            private final TypeCollector<K> delegate;

            ForwardingTypeCollector(TypeCollector<K> delegate2) {
                super();
                this.delegate = delegate2;
            }

            /* access modifiers changed from: 0000 */
            public Class<?> getRawType(K type) {
                return this.delegate.getRawType(type);
            }

            /* access modifiers changed from: 0000 */
            public Iterable<? extends K> getInterfaces(K type) {
                return this.delegate.getInterfaces(type);
            }

            /* access modifiers changed from: 0000 */
            public K getSuperclass(K type) {
                return this.delegate.getSuperclass(type);
            }
        }

        /* access modifiers changed from: 0000 */
        public abstract Iterable<? extends K> getInterfaces(K k);

        /* access modifiers changed from: 0000 */
        public abstract Class<?> getRawType(K k);

        /* access modifiers changed from: 0000 */
        @Nullable
        public abstract K getSuperclass(K k);

        private TypeCollector() {
        }

        /* access modifiers changed from: 0000 */
        public final TypeCollector<K> classesOnly() {
            return new ForwardingTypeCollector<K>(this) {
                /* access modifiers changed from: 0000 */
                public Iterable<? extends K> getInterfaces(K k) {
                    return ImmutableSet.of();
                }

                /* access modifiers changed from: 0000 */
                public ImmutableList<K> collectTypes(Iterable<? extends K> types) {
                    Builder<K> builder = ImmutableList.builder();
                    for (K type : types) {
                        if (!getRawType(type).isInterface()) {
                            builder.add((Object) type);
                        }
                    }
                    return super.collectTypes((Iterable<? extends K>) builder.build());
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public final ImmutableList<K> collectTypes(K type) {
            return collectTypes((Iterable<? extends K>) ImmutableList.of(type));
        }

        /* access modifiers changed from: 0000 */
        public ImmutableList<K> collectTypes(Iterable<? extends K> types) {
            Map<K, Integer> map = Maps.newHashMap();
            for (K type : types) {
                collectTypes(type, map);
            }
            return sortKeysByValue(map, Ordering.natural().reverse());
        }

        private int collectTypes(K type, Map<? super K, Integer> map) {
            Integer existing = (Integer) map.get(this);
            if (existing != null) {
                return existing.intValue();
            }
            int aboveMe = getRawType(type).isInterface() ? 1 : 0;
            for (K interfaceType : getInterfaces(type)) {
                aboveMe = Math.max(aboveMe, collectTypes(interfaceType, map));
            }
            K superclass = getSuperclass(type);
            if (superclass != null) {
                aboveMe = Math.max(aboveMe, collectTypes(superclass, map));
            }
            map.put(type, Integer.valueOf(aboveMe + 1));
            return aboveMe + 1;
        }

        private static <K, V> ImmutableList<K> sortKeysByValue(final Map<K, V> map, final Comparator<? super V> valueComparator) {
            return new Ordering<K>() {
                public int compare(K left, K right) {
                    return valueComparator.compare(map.get(left), map.get(right));
                }
            }.immutableSortedCopy(map.keySet());
        }
    }

    private final class ClassSet extends TypeSet {
        private static final long serialVersionUID = 0;
        private transient ImmutableSet<TypeToken<? super T>> classes;

        private ClassSet() {
            super();
        }

        /* access modifiers changed from: protected */
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> result = this.classes;
            if (result != null) {
                return result;
            }
            ImmutableSet<TypeToken<? super T>> result2 = FluentIterable.from((Iterable<E>) TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes(TypeToken.this)).filter((Predicate<? super E>) TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.classes = result2;
            return result2;
        }

        public TypeSet classes() {
            return this;
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf((Collection<? extends E>) TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes((Iterable<? extends K>) TypeToken.this.getImmediateRawTypes()));
        }

        public TypeSet interfaces() {
            throw new UnsupportedOperationException("classes().interfaces() not supported.");
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().classes();
        }
    }

    private final class InterfaceSet extends TypeSet {
        private static final long serialVersionUID = 0;
        private final transient TypeSet allTypes;
        private transient ImmutableSet<TypeToken<? super T>> interfaces;

        InterfaceSet(TypeSet allTypes2) {
            super();
            this.allTypes = allTypes2;
        }

        /* access modifiers changed from: protected */
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> result = this.interfaces;
            if (result != null) {
                return result;
            }
            ImmutableSet<TypeToken<? super T>> result2 = FluentIterable.from((Iterable<E>) this.allTypes).filter((Predicate<? super E>) TypeFilter.INTERFACE_ONLY).toSet();
            this.interfaces = result2;
            return result2;
        }

        public TypeSet interfaces() {
            return this;
        }

        public Set<Class<? super T>> rawTypes() {
            return FluentIterable.from((Iterable<E>) TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable<? extends K>) TypeToken.this.getImmediateRawTypes())).filter((Predicate<? super E>) new Predicate<Class<?>>() {
                public boolean apply(Class<?> type) {
                    return type.isInterface();
                }
            }).toSet();
        }

        public TypeSet classes() {
            throw new UnsupportedOperationException("interfaces().classes() not supported.");
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().interfaces();
        }
    }

    private static final class SimpleTypeToken<T> extends TypeToken<T> {
        private static final long serialVersionUID = 0;

        SimpleTypeToken(Type type) {
            super(type);
        }
    }

    private enum TypeFilter implements Predicate<TypeToken<?>> {
        IGNORE_TYPE_VARIABLE_OR_WILDCARD {
            public boolean apply(TypeToken<?> type) {
                return !(type.runtimeType instanceof TypeVariable) && !(type.runtimeType instanceof WildcardType);
            }
        },
        INTERFACE_ONLY {
            public boolean apply(TypeToken<?> type) {
                return type.getRawType().isInterface();
            }
        }
    }

    public class TypeSet extends ForwardingSet<TypeToken<? super T>> implements Serializable {
        private static final long serialVersionUID = 0;
        private transient ImmutableSet<TypeToken<? super T>> types;

        TypeSet() {
        }

        public TypeSet interfaces() {
            return new InterfaceSet(this);
        }

        public TypeSet classes() {
            return new ClassSet();
        }

        /* access modifiers changed from: protected */
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> filteredTypes = this.types;
            if (filteredTypes != null) {
                return filteredTypes;
            }
            ImmutableSet<TypeToken<? super T>> filteredTypes2 = FluentIterable.from((Iterable<E>) TypeCollector.FOR_GENERIC_TYPE.collectTypes(TypeToken.this)).filter((Predicate<? super E>) TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.types = filteredTypes2;
            return filteredTypes2;
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf((Collection<? extends E>) TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable<? extends K>) TypeToken.this.getImmediateRawTypes()));
        }
    }

    protected TypeToken() {
        this.runtimeType = capture();
        Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", this.runtimeType);
    }

    protected TypeToken(Class<?> declaringClass) {
        Type captured = super.capture();
        if (captured instanceof Class) {
            this.runtimeType = captured;
        } else {
            this.runtimeType = of(declaringClass).resolveType(captured).runtimeType;
        }
    }

    private TypeToken(Type type) {
        this.runtimeType = (Type) Preconditions.checkNotNull(type);
    }

    public static <T> TypeToken<T> of(Class<T> type) {
        return new SimpleTypeToken(type);
    }

    public static TypeToken<?> of(Type type) {
        return new SimpleTypeToken(type);
    }

    public final Class<? super T> getRawType() {
        return getRawType(this.runtimeType);
    }

    /* access modifiers changed from: private */
    public ImmutableSet<Class<? super T>> getImmediateRawTypes() {
        return getRawTypes(this.runtimeType);
    }

    public final Type getType() {
        return this.runtimeType;
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParam, TypeToken<X> typeArg) {
        return new SimpleTypeToken(new TypeResolver().where(ImmutableMap.of(new TypeVariableKey(typeParam.typeVariable), typeArg.runtimeType)).resolveType(this.runtimeType));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParam, Class<X> typeArg) {
        return where(typeParam, of(typeArg));
    }

    public final TypeToken<?> resolveType(Type type) {
        Preconditions.checkNotNull(type);
        TypeResolver resolver = this.typeResolver;
        if (resolver == null) {
            resolver = TypeResolver.accordingTo(this.runtimeType);
            this.typeResolver = resolver;
        }
        return of(resolver.resolveType(type));
    }

    /* access modifiers changed from: private */
    public Type[] resolveInPlace(Type[] types) {
        for (int i = 0; i < types.length; i++) {
            types[i] = resolveType(types[i]).getType();
        }
        return types;
    }

    private TypeToken<?> resolveSupertype(Type type) {
        TypeToken<?> supertype = resolveType(type);
        supertype.typeResolver = this.typeResolver;
        return supertype;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final TypeToken<? super T> getGenericSuperclass() {
        if (this.runtimeType instanceof TypeVariable) {
            return boundAsSuperclass(((TypeVariable) this.runtimeType).getBounds()[0]);
        }
        if (this.runtimeType instanceof WildcardType) {
            return boundAsSuperclass(((WildcardType) this.runtimeType).getUpperBounds()[0]);
        }
        Type superclass = getRawType().getGenericSuperclass();
        if (superclass == null) {
            return null;
        }
        return resolveSupertype(superclass);
    }

    @Nullable
    private TypeToken<? super T> boundAsSuperclass(Type bound) {
        TypeToken<?> token = of(bound);
        if (token.getRawType().isInterface()) {
            return null;
        }
        return token;
    }

    /* access modifiers changed from: 0000 */
    public final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
        if (this.runtimeType instanceof TypeVariable) {
            return boundsAsInterfaces(((TypeVariable) this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return boundsAsInterfaces(((WildcardType) this.runtimeType).getUpperBounds());
        }
        Builder<TypeToken<? super T>> builder = ImmutableList.builder();
        for (Type interfaceType : getRawType().getGenericInterfaces()) {
            builder.add((Object) resolveSupertype(interfaceType));
        }
        return builder.build();
    }

    private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] bounds) {
        Builder<TypeToken<? super T>> builder = ImmutableList.builder();
        for (Type bound : bounds) {
            TypeToken<? super T> boundType = of(bound);
            if (boundType.getRawType().isInterface()) {
                builder.add((Object) boundType);
            }
        }
        return builder.build();
    }

    public final TypeSet getTypes() {
        return new TypeSet<>();
    }

    public final TypeToken<? super T> getSupertype(Class<? super T> superclass) {
        Preconditions.checkArgument(superclass.isAssignableFrom(getRawType()), "%s is not a super class of %s", superclass, this);
        if (this.runtimeType instanceof TypeVariable) {
            return getSupertypeFromUpperBounds(superclass, ((TypeVariable) this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return getSupertypeFromUpperBounds(superclass, ((WildcardType) this.runtimeType).getUpperBounds());
        }
        if (superclass.isArray()) {
            return getArraySupertype(superclass);
        }
        return resolveSupertype(toGenericType(superclass).runtimeType);
    }

    public final TypeToken<? extends T> getSubtype(Class<?> subclass) {
        Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", this);
        if (this.runtimeType instanceof WildcardType) {
            return getSubtypeFromLowerBounds(subclass, ((WildcardType) this.runtimeType).getLowerBounds());
        }
        Preconditions.checkArgument(getRawType().isAssignableFrom(subclass), "%s isn't a subclass of %s", subclass, this);
        if (isArray()) {
            return getArraySubtype(subclass);
        }
        return of(resolveTypeArgsForSubclass(subclass));
    }

    public final boolean isAssignableFrom(TypeToken<?> type) {
        return isAssignableFrom(type.runtimeType);
    }

    public final boolean isAssignableFrom(Type type) {
        return isAssignable((Type) Preconditions.checkNotNull(type), this.runtimeType);
    }

    public final boolean isArray() {
        return getComponentType() != null;
    }

    public final boolean isPrimitive() {
        return (this.runtimeType instanceof Class) && ((Class) this.runtimeType).isPrimitive();
    }

    public final TypeToken<T> wrap() {
        if (isPrimitive()) {
            return of(Primitives.wrap((Class) this.runtimeType));
        }
        return this;
    }

    private boolean isWrapper() {
        return Primitives.allWrapperTypes().contains(this.runtimeType);
    }

    public final TypeToken<T> unwrap() {
        if (isWrapper()) {
            return of(Primitives.unwrap((Class) this.runtimeType));
        }
        return this;
    }

    @Nullable
    public final TypeToken<?> getComponentType() {
        Type componentType = Types.getComponentType(this.runtimeType);
        if (componentType == null) {
            return null;
        }
        return of(componentType);
    }

    public final Invokable<T, Object> method(Method method) {
        Preconditions.checkArgument(of(method.getDeclaringClass()).isAssignableFrom(this), "%s not declared by %s", method, this);
        return new MethodInvokable<T>(method) {
            /* access modifiers changed from: 0000 */
            public Type getGenericReturnType() {
                return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
            }

            /* access modifiers changed from: 0000 */
            public Type[] getGenericParameterTypes() {
                return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
            }

            /* access modifiers changed from: 0000 */
            public Type[] getGenericExceptionTypes() {
                return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
            }

            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            public String toString() {
                return getOwnerType() + Consts.DOT + super.toString();
            }
        };
    }

    public final Invokable<T, T> constructor(Constructor<?> constructor) {
        boolean z;
        if (constructor.getDeclaringClass() == getRawType()) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "%s not declared by %s", constructor, getRawType());
        return new ConstructorInvokable<T>(constructor) {
            /* access modifiers changed from: 0000 */
            public Type getGenericReturnType() {
                return TypeToken.this.resolveType(super.getGenericReturnType()).getType();
            }

            /* access modifiers changed from: 0000 */
            public Type[] getGenericParameterTypes() {
                return TypeToken.this.resolveInPlace(super.getGenericParameterTypes());
            }

            /* access modifiers changed from: 0000 */
            public Type[] getGenericExceptionTypes() {
                return TypeToken.this.resolveInPlace(super.getGenericExceptionTypes());
            }

            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            public String toString() {
                return getOwnerType() + "(" + Joiner.on(", ").join((Object[]) getGenericParameterTypes()) + ")";
            }
        };
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof TypeToken)) {
            return false;
        }
        return this.runtimeType.equals(((TypeToken) o).runtimeType);
    }

    public int hashCode() {
        return this.runtimeType.hashCode();
    }

    public String toString() {
        return Types.toString(this.runtimeType);
    }

    /* access modifiers changed from: protected */
    public Object writeReplace() {
        return of(new TypeResolver().resolveType(this.runtimeType));
    }

    /* access modifiers changed from: 0000 */
    public final TypeToken<T> rejectTypeVariables() {
        new TypeVisitor() {
            /* access modifiers changed from: 0000 */
            public void visitTypeVariable(TypeVariable<?> typeVariable) {
                throw new IllegalArgumentException(TypeToken.this.runtimeType + "contains a type variable and is not safe for the operation");
            }

            /* access modifiers changed from: 0000 */
            public void visitWildcardType(WildcardType type) {
                visit(type.getLowerBounds());
                visit(type.getUpperBounds());
            }

            /* access modifiers changed from: 0000 */
            public void visitParameterizedType(ParameterizedType type) {
                visit(type.getActualTypeArguments());
                visit(type.getOwnerType());
            }

            /* access modifiers changed from: 0000 */
            public void visitGenericArrayType(GenericArrayType type) {
                visit(type.getGenericComponentType());
            }
        }.visit(this.runtimeType);
        return this;
    }

    private static boolean isAssignable(Type from, Type to) {
        if (to.equals(from)) {
            return true;
        }
        if (to instanceof WildcardType) {
            return isAssignableToWildcardType(from, (WildcardType) to);
        }
        if (from instanceof TypeVariable) {
            return isAssignableFromAny(((TypeVariable) from).getBounds(), to);
        }
        if (from instanceof WildcardType) {
            return isAssignableFromAny(((WildcardType) from).getUpperBounds(), to);
        }
        if (from instanceof GenericArrayType) {
            return isAssignableFromGenericArrayType((GenericArrayType) from, to);
        }
        if (to instanceof Class) {
            return isAssignableToClass(from, (Class) to);
        }
        if (to instanceof ParameterizedType) {
            return isAssignableToParameterizedType(from, (ParameterizedType) to);
        }
        if (to instanceof GenericArrayType) {
            return isAssignableToGenericArrayType(from, (GenericArrayType) to);
        }
        return false;
    }

    private static boolean isAssignableFromAny(Type[] fromTypes, Type to) {
        for (Type from : fromTypes) {
            if (isAssignable(from, to)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAssignableToClass(Type from, Class<?> to) {
        return to.isAssignableFrom(getRawType(from));
    }

    private static boolean isAssignableToWildcardType(Type from, WildcardType to) {
        return isAssignable(from, supertypeBound(to)) && isAssignableBySubtypeBound(from, to);
    }

    private static boolean isAssignableBySubtypeBound(Type from, WildcardType to) {
        Type toSubtypeBound = subtypeBound(to);
        if (toSubtypeBound == null) {
            return true;
        }
        Type fromSubtypeBound = subtypeBound(from);
        if (fromSubtypeBound == null) {
            return false;
        }
        return isAssignable(toSubtypeBound, fromSubtypeBound);
    }

    private static boolean isAssignableToParameterizedType(Type from, ParameterizedType to) {
        Class<?> matchedClass = getRawType(to);
        if (!matchedClass.isAssignableFrom(getRawType(from))) {
            return false;
        }
        Type[] typeParams = matchedClass.getTypeParameters();
        Type[] toTypeArgs = to.getActualTypeArguments();
        TypeToken<?> fromTypeToken = of(from);
        for (int i = 0; i < typeParams.length; i++) {
            if (!matchTypeArgument(fromTypeToken.resolveType(typeParams[i]).runtimeType, toTypeArgs[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAssignableToGenericArrayType(Type from, GenericArrayType to) {
        if (from instanceof Class) {
            Class<?> fromClass = (Class) from;
            if (!fromClass.isArray()) {
                return false;
            }
            return isAssignable(fromClass.getComponentType(), to.getGenericComponentType());
        } else if (from instanceof GenericArrayType) {
            return isAssignable(((GenericArrayType) from).getGenericComponentType(), to.getGenericComponentType());
        } else {
            return false;
        }
    }

    private static boolean isAssignableFromGenericArrayType(GenericArrayType from, Type to) {
        if (to instanceof Class) {
            Class<?> toClass = (Class) to;
            if (toClass.isArray()) {
                return isAssignable(from.getGenericComponentType(), toClass.getComponentType());
            }
            if (toClass == Object.class) {
                return true;
            }
            return false;
        } else if (!(to instanceof GenericArrayType)) {
            return false;
        } else {
            return isAssignable(from.getGenericComponentType(), ((GenericArrayType) to).getGenericComponentType());
        }
    }

    private static boolean matchTypeArgument(Type from, Type to) {
        if (from.equals(to)) {
            return true;
        }
        if (to instanceof WildcardType) {
            return isAssignableToWildcardType(from, (WildcardType) to);
        }
        return false;
    }

    private static Type supertypeBound(Type type) {
        if (type instanceof WildcardType) {
            return supertypeBound((WildcardType) type);
        }
        return type;
    }

    private static Type supertypeBound(WildcardType type) {
        Type[] upperBounds = type.getUpperBounds();
        if (upperBounds.length == 1) {
            return supertypeBound(upperBounds[0]);
        }
        if (upperBounds.length == 0) {
            return Object.class;
        }
        throw new AssertionError("There should be at most one upper bound for wildcard type: " + type);
    }

    @Nullable
    private static Type subtypeBound(Type type) {
        if (type instanceof WildcardType) {
            return subtypeBound((WildcardType) type);
        }
        return type;
    }

    @Nullable
    private static Type subtypeBound(WildcardType type) {
        Type[] lowerBounds = type.getLowerBounds();
        if (lowerBounds.length == 1) {
            return subtypeBound(lowerBounds[0]);
        }
        if (lowerBounds.length == 0) {
            return null;
        }
        throw new AssertionError("Wildcard should have at most one lower bound: " + type);
    }

    @VisibleForTesting
    static Class<?> getRawType(Type type) {
        return (Class) getRawTypes(type).iterator().next();
    }

    @VisibleForTesting
    static ImmutableSet<Class<?>> getRawTypes(Type type) {
        Preconditions.checkNotNull(type);
        final ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
        new TypeVisitor() {
            /* access modifiers changed from: 0000 */
            public void visitTypeVariable(TypeVariable<?> t) {
                visit(t.getBounds());
            }

            /* access modifiers changed from: 0000 */
            public void visitWildcardType(WildcardType t) {
                visit(t.getUpperBounds());
            }

            /* access modifiers changed from: 0000 */
            public void visitParameterizedType(ParameterizedType t) {
                builder.add((Object) (Class) t.getRawType());
            }

            /* access modifiers changed from: 0000 */
            public void visitClass(Class<?> t) {
                builder.add((Object) t);
            }

            /* access modifiers changed from: 0000 */
            public void visitGenericArrayType(GenericArrayType t) {
                builder.add((Object) Types.getArrayClass(TypeToken.getRawType(t.getGenericComponentType())));
            }
        }.visit(type);
        return builder.build();
    }

    @VisibleForTesting
    static <T> TypeToken<? extends T> toGenericType(Class<T> cls) {
        if (cls.isArray()) {
            return of(Types.newArrayType(toGenericType(cls.getComponentType()).runtimeType));
        }
        TypeVariable<Class<T>>[] typeParams = cls.getTypeParameters();
        if (typeParams.length > 0) {
            return of((Type) Types.newParameterizedType(cls, typeParams));
        }
        return of(cls);
    }

    private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> supertype, Type[] upperBounds) {
        for (Type upperBound : upperBounds) {
            TypeToken<? super T> bound = of(upperBound);
            if (of(supertype).isAssignableFrom(bound)) {
                return bound.getSupertype(supertype);
            }
        }
        throw new IllegalArgumentException(supertype + " isn't a super type of " + this);
    }

    private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> subclass, Type[] lowerBounds) {
        Type[] arr$ = lowerBounds;
        if (0 < arr$.length) {
            return of(arr$[0]).getSubtype(subclass);
        }
        throw new IllegalArgumentException(subclass + " isn't a subclass of " + this);
    }

    private TypeToken<? super T> getArraySupertype(Class<? super T> supertype) {
        return of(newArrayClassOrGenericArrayType(((TypeToken) Preconditions.checkNotNull(getComponentType(), "%s isn't a super type of %s", supertype, this)).getSupertype(supertype.getComponentType()).runtimeType));
    }

    private TypeToken<? extends T> getArraySubtype(Class<?> subclass) {
        return of(newArrayClassOrGenericArrayType(getComponentType().getSubtype(subclass.getComponentType()).runtimeType));
    }

    private Type resolveTypeArgsForSubclass(Class<?> subclass) {
        if (this.runtimeType instanceof Class) {
            return subclass;
        }
        TypeToken<?> genericSubtype = toGenericType(subclass);
        return new TypeResolver().where(genericSubtype.getSupertype(getRawType()).runtimeType, this.runtimeType).resolveType(genericSubtype.runtimeType);
    }

    private static Type newArrayClassOrGenericArrayType(Type componentType) {
        return JavaVersion.JAVA7.newArrayType(componentType);
    }
}
