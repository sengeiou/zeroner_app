package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableFilter<T> extends AbstractFlowableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    static final class FilterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Predicate<? super T> filter;

        FilterConditionalSubscriber(ConditionalSubscriber<? super T> actual, Predicate<? super T> filter2) {
            super(actual);
            this.filter = filter2;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.s.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.actual.tryOnNext(null);
            }
            try {
                if (!this.filter.test(t) || !this.actual.tryOnNext(t)) {
                    return false;
                }
                return true;
            } catch (Throwable e) {
                fail(e);
                return true;
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Nullable
        public T poll() throws Exception {
            QueueSubscription<T> qs = this.qs;
            Predicate<? super T> f = this.filter;
            while (true) {
                T t = qs.poll();
                if (t == null) {
                    return null;
                }
                if (f.test(t)) {
                    return t;
                }
                if (this.sourceMode == 2) {
                    qs.request(1);
                }
            }
        }
    }

    static final class FilterSubscriber<T> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        final Predicate<? super T> filter;

        FilterSubscriber(Subscriber<? super T> actual, Predicate<? super T> filter2) {
            super(actual);
            this.filter = filter2;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.s.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            boolean z = true;
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.actual.onNext(null);
                return z;
            }
            try {
                boolean b = this.filter.test(t);
                if (!b) {
                    return b;
                }
                this.actual.onNext(t);
                return b;
            } catch (Throwable e) {
                fail(e);
                return z;
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Nullable
        public T poll() throws Exception {
            QueueSubscription<T> qs = this.qs;
            Predicate<? super T> f = this.filter;
            while (true) {
                T t = qs.poll();
                if (t == null) {
                    return null;
                }
                if (f.test(t)) {
                    return t;
                }
                if (this.sourceMode == 2) {
                    qs.request(1);
                }
            }
        }
    }

    public FlowableFilter(Flowable<T> source, Predicate<? super T> predicate2) {
        super(source);
        this.predicate = predicate2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        if (s instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new FilterConditionalSubscriber<Object>((ConditionalSubscriber) s, this.predicate));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new FilterSubscriber<Object>(s, this.predicate));
        }
    }
}
