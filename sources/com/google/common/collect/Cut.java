package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    private static final long serialVersionUID = 0;
    final C endpoint;

    private static final class AboveAll extends Cut<Comparable<?>> {
        /* access modifiers changed from: private */
        public static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0;

        private AboveAll() {
            super(null);
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        /* access modifiers changed from: 0000 */
        public boolean isLessThan(Comparable<?> comparable) {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsLowerBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public void describeAsLowerBound(StringBuilder sb) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public void describeAsUpperBound(StringBuilder sb) {
            sb.append("+∞)");
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
            return domain.maxValue();
        }

        public int compareTo(Cut<Comparable<?>> o) {
            return o == this ? 0 : 1;
        }

        public String toString() {
            return "+∞";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    private static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public /* bridge */ /* synthetic */ int compareTo(Object x0) {
            return Cut.super.compareTo((Cut) x0);
        }

        AboveValue(C endpoint) {
            super((Comparable) Preconditions.checkNotNull(endpoint));
        }

        /* access modifiers changed from: 0000 */
        public boolean isLessThan(C value) {
            return Range.compareOrThrow(this.endpoint, value) < 0;
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            switch (boundType) {
                case CLOSED:
                    C next = domain.next(this.endpoint);
                    return next == null ? Cut.belowAll() : belowValue(next);
                case OPEN:
                    return this;
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            switch (boundType) {
                case CLOSED:
                    return this;
                case OPEN:
                    C next = domain.next(this.endpoint);
                    return next == null ? Cut.aboveAll() : belowValue(next);
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public void describeAsLowerBound(StringBuilder sb) {
            sb.append('(').append(this.endpoint);
        }

        /* access modifiers changed from: 0000 */
        public void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint).append(']');
        }

        /* access modifiers changed from: 0000 */
        public C leastValueAbove(DiscreteDomain<C> domain) {
            return domain.next(this.endpoint);
        }

        /* access modifiers changed from: 0000 */
        public C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return this.endpoint;
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> canonical(DiscreteDomain<C> domain) {
            C next = leastValueAbove(domain);
            return next != null ? belowValue(next) : Cut.aboveAll();
        }

        public int hashCode() {
            return this.endpoint.hashCode() ^ -1;
        }

        public String toString() {
            return "/" + this.endpoint + "\\";
        }
    }

    private static final class BelowAll extends Cut<Comparable<?>> {
        /* access modifiers changed from: private */
        public static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0;

        private BelowAll() {
            super(null);
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        /* access modifiers changed from: 0000 */
        public boolean isLessThan(Comparable<?> comparable) {
            return true;
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsUpperBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public void describeAsLowerBound(StringBuilder sb) {
            sb.append("(-∞");
        }

        /* access modifiers changed from: 0000 */
        public void describeAsUpperBound(StringBuilder sb) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
            return domain.minValue();
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> domain) {
            try {
                return Cut.belowValue(domain.minValue());
            } catch (NoSuchElementException e) {
                return this;
            }
        }

        public int compareTo(Cut<Comparable<?>> o) {
            return o == this ? 0 : -1;
        }

        public String toString() {
            return "-∞";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    private static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public /* bridge */ /* synthetic */ int compareTo(Object x0) {
            return Cut.super.compareTo((Cut) x0);
        }

        BelowValue(C endpoint) {
            super((Comparable) Preconditions.checkNotNull(endpoint));
        }

        /* access modifiers changed from: 0000 */
        public boolean isLessThan(C value) {
            return Range.compareOrThrow(this.endpoint, value) <= 0;
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }

        /* access modifiers changed from: 0000 */
        public BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            Cut<C> aboveValue;
            switch (boundType) {
                case CLOSED:
                    return this;
                case OPEN:
                    C previous = domain.previous(this.endpoint);
                    if (previous == null) {
                        aboveValue = Cut.belowAll();
                    } else {
                        aboveValue = new AboveValue<>(previous);
                    }
                    return aboveValue;
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            switch (boundType) {
                case CLOSED:
                    C previous = domain.previous(this.endpoint);
                    return previous == null ? Cut.aboveAll() : new AboveValue(previous);
                case OPEN:
                    return this;
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public void describeAsLowerBound(StringBuilder sb) {
            sb.append('[').append(this.endpoint);
        }

        /* access modifiers changed from: 0000 */
        public void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint).append(')');
        }

        /* access modifiers changed from: 0000 */
        public C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return this.endpoint;
        }

        /* access modifiers changed from: 0000 */
        public C greatestValueBelow(DiscreteDomain<C> domain) {
            return domain.previous(this.endpoint);
        }

        public int hashCode() {
            return this.endpoint.hashCode();
        }

        public String toString() {
            return "\\" + this.endpoint + "/";
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void describeAsLowerBound(StringBuilder sb);

    /* access modifiers changed from: 0000 */
    public abstract void describeAsUpperBound(StringBuilder sb);

    /* access modifiers changed from: 0000 */
    public abstract C greatestValueBelow(DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract boolean isLessThan(C c);

    /* access modifiers changed from: 0000 */
    public abstract C leastValueAbove(DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract BoundType typeAsLowerBound();

    /* access modifiers changed from: 0000 */
    public abstract BoundType typeAsUpperBound();

    /* access modifiers changed from: 0000 */
    public abstract Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain);

    Cut(@Nullable C endpoint2) {
        this.endpoint = endpoint2;
    }

    /* access modifiers changed from: 0000 */
    public Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    public int compareTo(Cut<C> that) {
        if (that == belowAll()) {
            return 1;
        }
        if (that == aboveAll()) {
            return -1;
        }
        int result = Range.compareOrThrow(this.endpoint, that.endpoint);
        return result == 0 ? Booleans.compare(this instanceof AboveValue, that instanceof AboveValue) : result;
    }

    /* access modifiers changed from: 0000 */
    public C endpoint() {
        return this.endpoint;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Cut)) {
            return false;
        }
        try {
            if (compareTo((Cut) obj) == 0) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    static <C extends Comparable> Cut<C> belowAll() {
        return BelowAll.INSTANCE;
    }

    static <C extends Comparable> Cut<C> aboveAll() {
        return AboveAll.INSTANCE;
    }

    static <C extends Comparable> Cut<C> belowValue(C endpoint2) {
        return new BelowValue(endpoint2);
    }

    static <C extends Comparable> Cut<C> aboveValue(C endpoint2) {
        return new AboveValue(endpoint2);
    }
}
