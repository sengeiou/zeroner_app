package io.reactivex.internal.operators.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscription;

public final class FlowableIgnoreElementsCompletable<T> extends Completable implements FuseToFlowable<T> {
    final Flowable<T> source;

    static final class IgnoreElementsSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final CompletableObserver actual;
        Subscription s;

        IgnoreElementsSubscriber(CompletableObserver actual2) {
            this.actual = actual2;
        }

        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
                s2.request(LongCompanionObject.MAX_VALUE);
            }
        }

        public void onNext(T t) {
        }

        public void onError(Throwable t) {
            this.s = SubscriptionHelper.CANCELLED;
            this.actual.onError(t);
        }

        public void onComplete() {
            this.s = SubscriptionHelper.CANCELLED;
            this.actual.onComplete();
        }

        public void dispose() {
            this.s.cancel();
            this.s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableIgnoreElementsCompletable(Flowable<T> source2) {
        this.source = source2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver t) {
        this.source.subscribe((FlowableSubscriber<? super T>) new IgnoreElementsSubscriber<Object>(t));
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableIgnoreElements<T>(this.source));
    }
}
