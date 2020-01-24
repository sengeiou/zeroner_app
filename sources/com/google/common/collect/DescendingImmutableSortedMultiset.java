package com.google.common.collect;

import com.google.common.collect.Multiset.Entry;
import javax.annotation.Nullable;

final class DescendingImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
    private final transient ImmutableSortedMultiset<E> forward;

    DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> forward2) {
        this.forward = forward2;
    }

    public int count(@Nullable Object element) {
        return this.forward.count(element);
    }

    public Entry<E> firstEntry() {
        return this.forward.lastEntry();
    }

    public Entry<E> lastEntry() {
        return this.forward.firstEntry();
    }

    public int size() {
        return this.forward.size();
    }

    public ImmutableSortedSet<E> elementSet() {
        return this.forward.elementSet().descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public Entry<E> getEntry(int index) {
        return (Entry) this.forward.entrySet().asList().reverse().get(index);
    }

    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.forward;
    }

    public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        return this.forward.tailMultiset(upperBound, boundType).descendingMultiset();
    }

    public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        return this.forward.headMultiset(lowerBound, boundType).descendingMultiset();
    }

    /* access modifiers changed from: 0000 */
    public boolean isPartialView() {
        return this.forward.isPartialView();
    }
}
