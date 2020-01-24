package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SingleFlatMapPublisher<T, R> extends Flowable<R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final SingleSource<T> source;

    static final class SingleFlatMapPublisherObserver<S, T> extends AtomicLong implements SingleObserver<S>, FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 7759721921468635667L;
        final Subscriber<? super T> actual;
        Disposable disposable;
        final Function<? super S, ? extends Publisher<? extends T>> mapper;
        final AtomicReference<Subscription> parent = new AtomicReference<>();

        SingleFlatMapPublisherObserver(Subscriber<? super T> actual2, Function<? super S, ? extends Publisher<? extends T>> mapper2) {
            this.actual = actual2;
            this.mapper = mapper2;
        }

        public void onSubscribe(Disposable d) {
            this.disposable = d;
            this.actual.onSubscribe(this);
        }

        public void onSuccess(S value) {
            try {
                ((Publisher) ObjectHelper.requireNonNull(this.mapper.apply(value), "the mapper returned a null Publisher")).subscribe(this);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.actual.onError(e);
            }
        }

        public void onSubscribe(Subscription s) {
            SubscriptionHelper.deferredSetOnce(this.parent, this, s);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
        }

        public void request(long n) {
            SubscriptionHelper.deferredRequest(this.parent, this, n);
        }

        public void cancel() {
            this.disposable.dispose();
            SubscriptionHelper.cancel(this.parent);
        }
    }

    public SingleFlatMapPublisher(SingleSource<T> source2, Function<? super T, ? extends Publisher<? extends R>> mapper2) {
        this.source = source2;
        this.mapper = mapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> actual) {
        this.source.subscribe(new SingleFlatMapPublisherObserver(actual, this.mapper));
    }
}
