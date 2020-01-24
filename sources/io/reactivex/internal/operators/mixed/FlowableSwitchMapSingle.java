package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Experimental
public final class FlowableSwitchMapSingle<T, R> extends Flowable<R> {
    final boolean delayErrors;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final Flowable<T> source;

    static final class SwitchMapSingleSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final SwitchMapSingleObserver<Object> INNER_DISPOSED = new SwitchMapSingleObserver<>(null);
        private static final long serialVersionUID = -5402190102429853762L;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        long emitted;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicReference<SwitchMapSingleObserver<R>> inner = new AtomicReference<>();
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final AtomicLong requested = new AtomicLong();
        Subscription upstream;

        static final class SwitchMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            private static final long serialVersionUID = 8042919737683345351L;
            volatile R item;
            final SwitchMapSingleSubscriber<?, R> parent;

            SwitchMapSingleObserver(SwitchMapSingleSubscriber<?, R> parent2) {
                this.parent = parent2;
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            public void onSuccess(R t) {
                this.item = t;
                this.parent.drain();
            }

            public void onError(Throwable e) {
                this.parent.innerError(this, e);
            }

            /* access modifiers changed from: 0000 */
            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }

        SwitchMapSingleSubscriber(Subscriber<? super R> downstream2, Function<? super T, ? extends SingleSource<? extends R>> mapper2, boolean delayErrors2) {
            this.downstream = downstream2;
            this.mapper = mapper2;
            this.delayErrors = delayErrors2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                s.request(LongCompanionObject.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            SwitchMapSingleObserver<R> current;
            SwitchMapSingleObserver<R> current2 = (SwitchMapSingleObserver) this.inner.get();
            if (current2 != null) {
                current2.dispose();
            }
            try {
                SingleSource<? extends R> ss = (SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null SingleSource");
                SwitchMapSingleObserver<R> observer = new SwitchMapSingleObserver<>(this);
                do {
                    current = (SwitchMapSingleObserver) this.inner.get();
                    if (current == INNER_DISPOSED) {
                        return;
                    }
                } while (!this.inner.compareAndSet(current, observer));
                ss.subscribe(observer);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.upstream.cancel();
                this.inner.getAndSet(INNER_DISPOSED);
                onError(ex);
            }
        }

        public void onError(Throwable t) {
            if (this.errors.addThrowable(t)) {
                if (!this.delayErrors) {
                    disposeInner();
                }
                this.done = true;
                drain();
                return;
            }
            RxJavaPlugins.onError(t);
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        /* access modifiers changed from: 0000 */
        public void disposeInner() {
            SwitchMapSingleObserver<R> current = (SwitchMapSingleObserver) this.inner.getAndSet(INNER_DISPOSED);
            if (current != null && current != INNER_DISPOSED) {
                current.dispose();
            }
        }

        public void request(long n) {
            BackpressureHelper.add(this.requested, n);
            drain();
        }

        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            disposeInner();
        }

        /* access modifiers changed from: 0000 */
        public void innerError(SwitchMapSingleObserver<R> sender, Throwable ex) {
            if (!this.inner.compareAndSet(sender, null) || !this.errors.addThrowable(ex)) {
                RxJavaPlugins.onError(ex);
                return;
            }
            if (!this.delayErrors) {
                this.upstream.cancel();
                disposeInner();
            }
            drain();
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (getAndIncrement() == 0) {
                int missed = 1;
                Subscriber<? super R> downstream2 = this.downstream;
                AtomicThrowable errors2 = this.errors;
                AtomicReference<SwitchMapSingleObserver<R>> inner2 = this.inner;
                AtomicLong requested2 = this.requested;
                long emitted2 = this.emitted;
                while (!this.cancelled) {
                    if (errors2.get() == null || this.delayErrors) {
                        boolean d = this.done;
                        SwitchMapSingleObserver<R> current = (SwitchMapSingleObserver) inner2.get();
                        boolean empty = current == null;
                        if (d && empty) {
                            Throwable ex = errors2.terminate();
                            if (ex != null) {
                                downstream2.onError(ex);
                                return;
                            } else {
                                downstream2.onComplete();
                                return;
                            }
                        } else if (empty || current.item == null || emitted2 == requested2.get()) {
                            this.emitted = emitted2;
                            missed = addAndGet(-missed);
                            if (missed == 0) {
                                return;
                            }
                        } else {
                            inner2.compareAndSet(current, null);
                            downstream2.onNext(current.item);
                            emitted2++;
                        }
                    } else {
                        downstream2.onError(errors2.terminate());
                        return;
                    }
                }
            }
        }
    }

    public FlowableSwitchMapSingle(Flowable<T> source2, Function<? super T, ? extends SingleSource<? extends R>> mapper2, boolean delayErrors2) {
        this.source = source2;
        this.mapper = mapper2;
        this.delayErrors = delayErrors2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> s) {
        this.source.subscribe((FlowableSubscriber<? super T>) new SwitchMapSingleSubscriber<Object>(s, this.mapper, this.delayErrors));
    }
}
