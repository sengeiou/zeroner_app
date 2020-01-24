package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

@GwtIncompatible("java.util.ArrayDeque")
@Beta
public final class EvictingQueue<E> extends ForwardingQueue<E> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Queue<E> delegate;
    @VisibleForTesting
    final int maxSize;

    private EvictingQueue(int maxSize2) {
        boolean z;
        if (maxSize2 >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "maxSize (%s) must >= 0", Integer.valueOf(maxSize2));
        this.delegate = new LinkedList();
        this.maxSize = maxSize2;
    }

    public static <E> EvictingQueue<E> create(int maxSize2) {
        return new EvictingQueue<>(maxSize2);
    }

    public int remainingCapacity() {
        return this.maxSize - size();
    }

    /* access modifiers changed from: protected */
    public Queue<E> delegate() {
        return this.delegate;
    }

    public boolean offer(E e) {
        return add(e);
    }

    public boolean add(E e) {
        Preconditions.checkNotNull(e);
        if (this.maxSize != 0) {
            if (size() == this.maxSize) {
                this.delegate.remove();
            }
            this.delegate.add(e);
        }
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        return standardAddAll(collection);
    }

    public boolean contains(Object object) {
        return delegate().contains(Preconditions.checkNotNull(object));
    }

    public boolean remove(Object object) {
        return delegate().remove(Preconditions.checkNotNull(object));
    }
}
