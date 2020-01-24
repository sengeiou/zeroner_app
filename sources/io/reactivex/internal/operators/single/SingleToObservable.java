package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;

public final class SingleToObservable<T> extends Observable<T> {
    final SingleSource<? extends T> source;

    static final class SingleToObservableObserver<T> extends DeferredScalarDisposable<T> implements SingleObserver<T> {
        private static final long serialVersionUID = 3786543492451018833L;
        Disposable d;

        SingleToObservableObserver(Observer<? super T> actual) {
            super(actual);
        }

        public void onSubscribe(Disposable d2) {
            if (DisposableHelper.validate(this.d, d2)) {
                this.d = d2;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            complete(value);
        }

        public void onError(Throwable e) {
            error(e);
        }

        public void dispose() {
            super.dispose();
            this.d.dispose();
        }
    }

    public SingleToObservable(SingleSource<? extends T> source2) {
        this.source = source2;
    }

    public void subscribeActual(Observer<? super T> s) {
        this.source.subscribe(create(s));
    }

    @Experimental
    public static <T> SingleObserver<T> create(Observer<? super T> downstream) {
        return new SingleToObservableObserver(downstream);
    }
}
