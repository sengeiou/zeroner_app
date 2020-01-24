package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeArray extends Completable {
    final CompletableSource[] sources;

    static final class InnerCompletableObserver extends AtomicInteger implements CompletableObserver {
        private static final long serialVersionUID = -8360547806504310570L;
        final CompletableObserver actual;
        final AtomicBoolean once;
        final CompositeDisposable set;

        InnerCompletableObserver(CompletableObserver actual2, AtomicBoolean once2, CompositeDisposable set2, int n) {
            this.actual = actual2;
            this.once = once2;
            this.set = set2;
            lazySet(n);
        }

        public void onSubscribe(Disposable d) {
            this.set.add(d);
        }

        public void onError(Throwable e) {
            this.set.dispose();
            if (this.once.compareAndSet(false, true)) {
                this.actual.onError(e);
            } else {
                RxJavaPlugins.onError(e);
            }
        }

        public void onComplete() {
            if (decrementAndGet() == 0 && this.once.compareAndSet(false, true)) {
                this.actual.onComplete();
            }
        }
    }

    public CompletableMergeArray(CompletableSource[] sources2) {
        this.sources = sources2;
    }

    public void subscribeActual(CompletableObserver s) {
        CompositeDisposable set = new CompositeDisposable();
        InnerCompletableObserver shared = new InnerCompletableObserver(s, new AtomicBoolean(), set, this.sources.length + 1);
        s.onSubscribe(set);
        CompletableSource[] completableSourceArr = this.sources;
        int length = completableSourceArr.length;
        int i = 0;
        while (i < length) {
            CompletableSource c = completableSourceArr[i];
            if (!set.isDisposed()) {
                if (c == null) {
                    set.dispose();
                    shared.onError(new NullPointerException("A completable source is null"));
                    return;
                }
                c.subscribe(shared);
                i++;
            } else {
                return;
            }
        }
        shared.onComplete();
    }
}
