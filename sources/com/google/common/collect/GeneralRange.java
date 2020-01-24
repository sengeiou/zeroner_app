package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class GeneralRange<T> implements Serializable {
    private final Comparator<? super T> comparator;
    private final boolean hasLowerBound;
    private final boolean hasUpperBound;
    private final BoundType lowerBoundType;
    @Nullable
    private final T lowerEndpoint;
    private transient GeneralRange<T> reverse;
    private final BoundType upperBoundType;
    @Nullable
    private final T upperEndpoint;

    static <T extends Comparable> GeneralRange<T> from(Range<T> range) {
        Comparable comparable;
        Comparable comparable2;
        if (range.hasLowerBound()) {
            comparable = range.lowerEndpoint();
        } else {
            comparable = null;
        }
        BoundType lowerBoundType2 = range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN;
        if (range.hasUpperBound()) {
            comparable2 = range.upperEndpoint();
        } else {
            comparable2 = null;
        }
        return new GeneralRange<>(Ordering.natural(), range.hasLowerBound(), comparable, lowerBoundType2, range.hasUpperBound(), comparable2, range.hasUpperBound() ? range.upperBoundType() : BoundType.OPEN);
    }

    static <T> GeneralRange<T> all(Comparator<? super T> comparator2) {
        return new GeneralRange<>(comparator2, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
    }

    static <T> GeneralRange<T> downTo(Comparator<? super T> comparator2, @Nullable T endpoint, BoundType boundType) {
        return new GeneralRange<>(comparator2, true, endpoint, boundType, false, null, BoundType.OPEN);
    }

    static <T> GeneralRange<T> upTo(Comparator<? super T> comparator2, @Nullable T endpoint, BoundType boundType) {
        return new GeneralRange<>(comparator2, false, null, BoundType.OPEN, true, endpoint, boundType);
    }

    static <T> GeneralRange<T> range(Comparator<? super T> comparator2, @Nullable T lower, BoundType lowerType, @Nullable T upper, BoundType upperType) {
        return new GeneralRange<>(comparator2, true, lower, lowerType, true, upper, upperType);
    }

    private GeneralRange(Comparator<? super T> comparator2, boolean hasLowerBound2, @Nullable T lowerEndpoint2, BoundType lowerBoundType2, boolean hasUpperBound2, @Nullable T upperEndpoint2, BoundType upperBoundType2) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator2);
        this.hasLowerBound = hasLowerBound2;
        this.hasUpperBound = hasUpperBound2;
        this.lowerEndpoint = lowerEndpoint2;
        this.lowerBoundType = (BoundType) Preconditions.checkNotNull(lowerBoundType2);
        this.upperEndpoint = upperEndpoint2;
        this.upperBoundType = (BoundType) Preconditions.checkNotNull(upperBoundType2);
        if (hasLowerBound2) {
            comparator2.compare(lowerEndpoint2, lowerEndpoint2);
        }
        if (hasUpperBound2) {
            comparator2.compare(upperEndpoint2, upperEndpoint2);
        }
        if (hasLowerBound2 && hasUpperBound2) {
            int cmp = comparator2.compare(lowerEndpoint2, upperEndpoint2);
            if (cmp <= 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "lowerEndpoint (%s) > upperEndpoint (%s)", lowerEndpoint2, upperEndpoint2);
            if (cmp == 0) {
                if (lowerBoundType2 != BoundType.OPEN) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (upperBoundType2 == BoundType.OPEN) {
                    z3 = false;
                }
                Preconditions.checkArgument(z2 | z3);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Comparator<? super T> comparator() {
        return this.comparator;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasLowerBound() {
        return this.hasLowerBound;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasUpperBound() {
        return this.hasUpperBound;
    }

    /* access modifiers changed from: 0000 */
    public boolean isEmpty() {
        return (hasUpperBound() && tooLow(getUpperEndpoint())) || (hasLowerBound() && tooHigh(getLowerEndpoint()));
    }

    /* access modifiers changed from: 0000 */
    public boolean tooLow(@Nullable T t) {
        boolean z = true;
        if (!hasLowerBound()) {
            return false;
        }
        int cmp = this.comparator.compare(t, getLowerEndpoint());
        boolean z2 = cmp < 0;
        boolean z3 = cmp == 0;
        if (getLowerBoundType() != BoundType.OPEN) {
            z = false;
        }
        return z2 | (z3 & z);
    }

    /* access modifiers changed from: 0000 */
    public boolean tooHigh(@Nullable T t) {
        boolean z = true;
        if (!hasUpperBound()) {
            return false;
        }
        int cmp = this.comparator.compare(t, getUpperEndpoint());
        boolean z2 = cmp > 0;
        boolean z3 = cmp == 0;
        if (getUpperBoundType() != BoundType.OPEN) {
            z = false;
        }
        return z2 | (z3 & z);
    }

    /* access modifiers changed from: 0000 */
    public boolean contains(@Nullable T t) {
        return !tooLow(t) && !tooHigh(t);
    }

    /* access modifiers changed from: 0000 */
    public GeneralRange<T> intersect(GeneralRange<T> other) {
        Preconditions.checkNotNull(other);
        Preconditions.checkArgument(this.comparator.equals(other.comparator));
        boolean hasLowBound = this.hasLowerBound;
        T lowEnd = getLowerEndpoint();
        BoundType lowType = getLowerBoundType();
        if (!hasLowerBound()) {
            hasLowBound = other.hasLowerBound;
            lowEnd = other.getLowerEndpoint();
            lowType = other.getLowerBoundType();
        } else if (other.hasLowerBound()) {
            int cmp = this.comparator.compare(getLowerEndpoint(), other.getLowerEndpoint());
            if (cmp < 0 || (cmp == 0 && other.getLowerBoundType() == BoundType.OPEN)) {
                lowEnd = other.getLowerEndpoint();
                lowType = other.getLowerBoundType();
            }
        }
        boolean hasUpBound = this.hasUpperBound;
        T upEnd = getUpperEndpoint();
        BoundType upType = getUpperBoundType();
        if (!hasUpperBound()) {
            hasUpBound = other.hasUpperBound;
            upEnd = other.getUpperEndpoint();
            upType = other.getUpperBoundType();
        } else if (other.hasUpperBound()) {
            int cmp2 = this.comparator.compare(getUpperEndpoint(), other.getUpperEndpoint());
            if (cmp2 > 0 || (cmp2 == 0 && other.getUpperBoundType() == BoundType.OPEN)) {
                upEnd = other.getUpperEndpoint();
                upType = other.getUpperBoundType();
            }
        }
        if (hasLowBound && hasUpBound) {
            int cmp3 = this.comparator.compare(lowEnd, upEnd);
            if (cmp3 > 0 || (cmp3 == 0 && lowType == BoundType.OPEN && upType == BoundType.OPEN)) {
                lowEnd = upEnd;
                lowType = BoundType.OPEN;
                upType = BoundType.CLOSED;
            }
        }
        return new GeneralRange<>(this.comparator, hasLowBound, lowEnd, lowType, hasUpBound, upEnd, upType);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof GeneralRange)) {
            return false;
        }
        GeneralRange<?> r = (GeneralRange) obj;
        if (!this.comparator.equals(r.comparator) || this.hasLowerBound != r.hasLowerBound || this.hasUpperBound != r.hasUpperBound || !getLowerBoundType().equals(r.getLowerBoundType()) || !getUpperBoundType().equals(r.getUpperBoundType()) || !Objects.equal(getLowerEndpoint(), r.getLowerEndpoint()) || !Objects.equal(getUpperEndpoint(), r.getUpperEndpoint())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.comparator, getLowerEndpoint(), getLowerBoundType(), getUpperEndpoint(), getUpperBoundType());
    }

    /* access modifiers changed from: 0000 */
    public GeneralRange<T> reverse() {
        GeneralRange<T> result = this.reverse;
        if (result != null) {
            return result;
        }
        GeneralRange<T> result2 = new GeneralRange<>(Ordering.from(this.comparator).reverse(), this.hasUpperBound, getUpperEndpoint(), getUpperBoundType(), this.hasLowerBound, getLowerEndpoint(), getLowerBoundType());
        result2.reverse = this;
        this.reverse = result2;
        return result2;
    }

    public String toString() {
        return this.comparator + ":" + (this.lowerBoundType == BoundType.CLOSED ? '[' : '(') + (this.hasLowerBound ? this.lowerEndpoint : "-∞") + ',' + (this.hasUpperBound ? this.upperEndpoint : "∞") + (this.upperBoundType == BoundType.CLOSED ? ']' : ')');
    }

    /* access modifiers changed from: 0000 */
    public T getLowerEndpoint() {
        return this.lowerEndpoint;
    }

    /* access modifiers changed from: 0000 */
    public BoundType getLowerBoundType() {
        return this.lowerBoundType;
    }

    /* access modifiers changed from: 0000 */
    public T getUpperEndpoint() {
        return this.upperEndpoint;
    }

    /* access modifiers changed from: 0000 */
    public BoundType getUpperBoundType() {
        return this.upperBoundType;
    }
}
