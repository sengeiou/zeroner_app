package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelPeek<T> extends ParallelFlowable<T> {
    final Consumer<? super T> onAfterNext;
    final Action onAfterTerminated;
    final Action onCancel;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    final LongConsumer onRequest;
    final Consumer<? super Subscription> onSubscribe;
    final ParallelFlowable<T> source;

    static final class ParallelPeekSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        final ParallelPeek<T> parent;
        Subscription s;

        ParallelPeekSubscriber(Subscriber<? super T> actual2, ParallelPeek<T> parent2) {
            this.actual = actual2;
            this.parent = parent2;
        }

        public void request(long n) {
            try {
                this.parent.onRequest.accept(n);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                RxJavaPlugins.onError(ex);
            }
            this.s.request(n);
        }

        public void cancel() {
            try {
                this.parent.onCancel.run();
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                RxJavaPlugins.onError(ex);
            }
            this.s.cancel();
        }

        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.s, s2)) {
                this.s = s2;
                try {
                    this.parent.onSubscribe.accept(s2);
                    this.actual.onSubscribe(this);
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    s2.cancel();
                    this.actual.onSubscribe(EmptySubscription.INSTANCE);
                    onError(ex);
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.parent.onNext.accept(t);
                    this.actual.onNext(t);
                    try {
                        this.parent.onAfterNext.accept(t);
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        onError(ex);
                    }
                } catch (Throwable ex2) {
                    Exceptions.throwIfFatal(ex2);
                    onError(ex2);
                }
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            try {
                this.parent.onError.accept(t);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                t = new CompositeException(t, ex);
            }
            this.actual.onError(t);
            try {
                this.parent.onAfterTerminated.run();
            } catch (Throwable ex2) {
                Exceptions.throwIfFatal(ex2);
                RxJavaPlugins.onError(ex2);
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                try {
                    this.parent.onComplete.run();
                    this.actual.onComplete();
                    try {
                        this.parent.onAfterTerminated.run();
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        RxJavaPlugins.onError(ex);
                    }
                } catch (Throwable ex2) {
                    Exceptions.throwIfFatal(ex2);
                    this.actual.onError(ex2);
                }
            }
        }
    }

    public ParallelPeek(ParallelFlowable<T> source2, Consumer<? super T> onNext2, Consumer<? super T> onAfterNext2, Consumer<? super Throwable> onError2, Action onComplete2, Action onAfterTerminated2, Consumer<? super Subscription> onSubscribe2, LongConsumer onRequest2, Action onCancel2) {
        this.source = source2;
        this.onNext = (Consumer) ObjectHelper.requireNonNull(onNext2, "onNext is null");
        this.onAfterNext = (Consumer) ObjectHelper.requireNonNull(onAfterNext2, "onAfterNext is null");
        this.onError = (Consumer) ObjectHelper.requireNonNull(onError2, "onError is null");
        this.onComplete = (Action) ObjectHelper.requireNonNull(onComplete2, "onComplete is null");
        this.onAfterTerminated = (Action) ObjectHelper.requireNonNull(onAfterTerminated2, "onAfterTerminated is null");
        this.onSubscribe = (Consumer) ObjectHelper.requireNonNull(onSubscribe2, "onSubscribe is null");
        this.onRequest = (LongConsumer) ObjectHelper.requireNonNull(onRequest2, "onRequest is null");
        this.onCancel = (Action) ObjectHelper.requireNonNull(onCancel2, "onCancel is null");
    }

    public void subscribe(Subscriber<? super T>[] subscribers) {
        if (validate(subscribers)) {
            int n = subscribers.length;
            Subscriber<? super T>[] parents = new Subscriber[n];
            for (int i = 0; i < n; i++) {
                parents[i] = new ParallelPeekSubscriber<>(subscribers[i], this);
            }
            this.source.subscribe(parents);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }
}
