package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSamplePublisher<T> extends Flowable<T> {
    final boolean emitLast;
    final Publisher<?> other;
    final Publisher<T> source;

    static final class SampleMainEmitLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        volatile boolean done;
        final AtomicInteger wip = new AtomicInteger();

        SampleMainEmitLast(Subscriber<? super T> actual, Publisher<?> other) {
            super(actual, other);
        }

        /* access modifiers changed from: 0000 */
        public void completeMain() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                emit();
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void completeOther() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                emit();
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void run() {
            if (this.wip.getAndIncrement() == 0) {
                do {
                    boolean d = this.done;
                    emit();
                    if (d) {
                        this.actual.onComplete();
                        return;
                    }
                } while (this.wip.decrementAndGet() != 0);
            }
        }
    }

    static final class SampleMainNoLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        SampleMainNoLast(Subscriber<? super T> actual, Publisher<?> other) {
            super(actual, other);
        }

        /* access modifiers changed from: 0000 */
        public void completeMain() {
            this.actual.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void completeOther() {
            this.actual.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void run() {
            emit();
        }
    }

    static abstract class SamplePublisherSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3517602651313910099L;
        final Subscriber<? super T> actual;
        final AtomicReference<Subscription> other = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();
        Subscription s;
        final Publisher<?> sampler;

        /* access modifiers changed from: 0000 */
        public abstract void completeMain();

        /* access modifiers changed from: 0000 */
        public abstract void completeOther();

        /* access modifiers changed from: 0000 */
        public abstract void run();

        SamplePublisherSubscriber(Subscriber<? super T> actual2, Publisher<?> other2) {
            this.actual = actual2;
            this.sampler = other2;
        }

        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
                if (this.other.get() == null) {
                    this.sampler.subscribe(new SamplerSubscriber(this));
                    s2.request(LongCompanionObject.MAX_VALUE);
                }
            }
        }

        public void onNext(T t) {
            lazySet(t);
        }

        public void onError(Throwable t) {
            SubscriptionHelper.cancel(this.other);
            this.actual.onError(t);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            completeMain();
        }

        /* access modifiers changed from: 0000 */
        public void setOther(Subscription o) {
            SubscriptionHelper.setOnce(this.other, o, LongCompanionObject.MAX_VALUE);
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.other);
            this.s.cancel();
        }

        public void error(Throwable e) {
            this.s.cancel();
            this.actual.onError(e);
        }

        public void complete() {
            this.s.cancel();
            completeOther();
        }

        /* access modifiers changed from: 0000 */
        public void emit() {
            T value = getAndSet(null);
            if (value == null) {
                return;
            }
            if (this.requested.get() != 0) {
                this.actual.onNext(value);
                BackpressureHelper.produced(this.requested, 1);
                return;
            }
            cancel();
            this.actual.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
    }

    static final class SamplerSubscriber<T> implements FlowableSubscriber<Object> {
        final SamplePublisherSubscriber<T> parent;

        SamplerSubscriber(SamplePublisherSubscriber<T> parent2) {
            this.parent = parent2;
        }

        public void onSubscribe(Subscription s) {
            this.parent.setOther(s);
        }

        public void onNext(Object t) {
            this.parent.run();
        }

        public void onError(Throwable t) {
            this.parent.error(t);
        }

        public void onComplete() {
            this.parent.complete();
        }
    }

    public FlowableSamplePublisher(Publisher<T> source2, Publisher<?> other2, boolean emitLast2) {
        this.source = source2;
        this.other = other2;
        this.emitLast = emitLast2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        SerializedSubscriber<T> serial = new SerializedSubscriber<>(s);
        if (this.emitLast) {
            this.source.subscribe(new SampleMainEmitLast(serial, this.other));
        } else {
            this.source.subscribe(new SampleMainNoLast(serial, this.other));
        }
    }
}
