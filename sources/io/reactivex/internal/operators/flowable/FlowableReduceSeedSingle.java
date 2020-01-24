package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableReduceSeedSingle<T, R> extends Single<R> {
    final BiFunction<R, ? super T, R> reducer;
    final R seed;
    final Publisher<T> source;

    static final class ReduceSeedObserver<T, R> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super R> actual;
        final BiFunction<R, ? super T, R> reducer;
        Subscription s;
        R value;

        ReduceSeedObserver(SingleObserver<? super R> actual2, BiFunction<R, ? super T, R> reducer2, R value2) {
            this.actual = actual2;
            this.value = value2;
            this.reducer = reducer2;
        }

        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
                s2.request(LongCompanionObject.MAX_VALUE);
            }
        }

        public void onNext(T value2) {
            R v = this.value;
            if (v != null) {
                try {
                    this.value = ObjectHelper.requireNonNull(this.reducer.apply(v, value2), "The reducer returned a null value");
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    this.s.cancel();
                    onError(ex);
                }
            }
        }

        public void onError(Throwable e) {
            if (this.value != null) {
                this.value = null;
                this.s = SubscriptionHelper.CANCELLED;
                this.actual.onError(e);
                return;
            }
            RxJavaPlugins.onError(e);
        }

        public void onComplete() {
            R v = this.value;
            if (v != null) {
                this.value = null;
                this.s = SubscriptionHelper.CANCELLED;
                this.actual.onSuccess(v);
            }
        }

        public void dispose() {
            this.s.cancel();
            this.s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableReduceSeedSingle(Publisher<T> source2, R seed2, BiFunction<R, ? super T, R> reducer2) {
        this.source = source2;
        this.seed = seed2;
        this.reducer = reducer2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> observer) {
        this.source.subscribe(new ReduceSeedObserver(observer, this.reducer, this.seed));
    }
}
