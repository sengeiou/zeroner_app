package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import javax.annotation.Nullable;

class DescendingImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final ImmutableSortedSet<E> forward;

    DescendingImmutableSortedSet(ImmutableSortedSet<E> forward2) {
        super(Ordering.from(forward2.comparator()).reverse());
        this.forward = forward2;
    }

    public int size() {
        return this.forward.size();
    }

    public UnmodifiableIterator<E> iterator() {
        return this.forward.descendingIterator();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return this.forward.tailSet(toElement, inclusive).descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this.forward.subSet(toElement, toInclusive, fromElement, fromInclusive).descendingSet();
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return this.forward.headSet(fromElement, inclusive).descendingSet();
    }

    @GwtIncompatible("NavigableSet")
    public ImmutableSortedSet<E> descendingSet() {
        return this.forward;
    }

    @GwtIncompatible("NavigableSet")
    public UnmodifiableIterator<E> descendingIterator() {
        return this.forward.iterator();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible("NavigableSet")
    public ImmutableSortedSet<E> createDescendingSet() {
        throw new AssertionError("should never be called");
    }

    public E lower(E element) {
        return this.forward.higher(element);
    }

    public E floor(E element) {
        return this.forward.ceiling(element);
    }

    public E ceiling(E element) {
        return this.forward.floor(element);
    }

    public E higher(E element) {
        return this.forward.lower(element);
    }

    /* access modifiers changed from: 0000 */
    public int indexOf(@Nullable Object target) {
        int index = this.forward.indexOf(target);
        return index == -1 ? index : (size() - 1) - index;
    }

    /* access modifiers changed from: 0000 */
    public boolean isPartialView() {
        return this.forward.isPartialView();
    }
}
