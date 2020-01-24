package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableSkip<T> extends AbstractObservableWithUpstream<T, T> {
    final long n;

    static final class SkipObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        Disposable d;
        long remaining;

        SkipObserver(Observer<? super T> actual2, long n) {
            this.actual = actual2;
            this.remaining = n;
        }

        public void onSubscribe(Disposable d2) {
            if (DisposableHelper.validate(this.d, d2)) {
                this.d = d2;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.remaining != 0) {
                this.remaining--;
            } else {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void dispose() {
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }

    public ObservableSkip(ObservableSource<T> source, long n2) {
        super(source);
        this.n = n2;
    }

    public void subscribeActual(Observer<? super T> s) {
        this.source.subscribe(new SkipObserver(s, this.n));
    }
}
