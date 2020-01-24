package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Arrays;
import java.util.Iterator;

public final class MaybeZipIterable<T, R> extends Maybe<R> {
    final Iterable<? extends MaybeSource<? extends T>> sources;
    final Function<? super Object[], ? extends R> zipper;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) throws Exception {
            return ObjectHelper.requireNonNull(MaybeZipIterable.this.zipper.apply(new Object[]{t}), "The zipper returned a null value");
        }
    }

    public MaybeZipIterable(Iterable<? extends MaybeSource<? extends T>> sources2, Function<? super Object[], ? extends R> zipper2) {
        this.sources = sources2;
        this.zipper = zipper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> observer) {
        int n;
        MaybeSource[] maybeSourceArr = new MaybeSource[8];
        int n2 = 0;
        try {
            Iterator it = this.sources.iterator();
            while (true) {
                try {
                    n = n2;
                    if (it.hasNext()) {
                        MaybeSource<? extends T> source = (MaybeSource) it.next();
                        if (source == null) {
                            EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), observer);
                            int i = n;
                            return;
                        }
                        if (n == maybeSourceArr.length) {
                            maybeSourceArr = (MaybeSource[]) Arrays.copyOf(maybeSourceArr, (n >> 2) + n);
                        }
                        n2 = n + 1;
                        maybeSourceArr[n] = source;
                    } else if (n == 0) {
                        EmptyDisposable.complete(observer);
                        int i2 = n;
                        return;
                    } else if (n == 1) {
                        maybeSourceArr[0].subscribe(new MapMaybeObserver(observer, new SingletonArrayFunc()));
                        int i3 = n;
                        return;
                    } else {
                        ZipCoordinator<T, R> parent = new ZipCoordinator<>(observer, n, this.zipper);
                        observer.onSubscribe(parent);
                        int i4 = 0;
                        while (i4 < n) {
                            if (parent.isDisposed()) {
                                int i5 = n;
                                return;
                            } else {
                                maybeSourceArr[i4].subscribe(parent.observers[i4]);
                                i4++;
                            }
                        }
                        int i6 = n;
                        return;
                    }
                } catch (Throwable th) {
                    ex = th;
                    int i7 = n;
                    Exceptions.throwIfFatal(ex);
                    EmptyDisposable.error(ex, observer);
                    return;
                }
            }
        } catch (Throwable th2) {
            ex = th2;
        }
    }
}
