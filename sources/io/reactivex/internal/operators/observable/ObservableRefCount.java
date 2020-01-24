package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableRefCount<T> extends Observable<T> {
    RefConnection connection;
    final int n;
    final Scheduler scheduler;
    final ConnectableObservable<T> source;
    final long timeout;
    final TimeUnit unit;

    static final class RefConnection extends AtomicReference<Disposable> implements Runnable, Consumer<Disposable> {
        private static final long serialVersionUID = -4552101107598366241L;
        boolean connected;
        final ObservableRefCount<?> parent;
        long subscriberCount;
        Disposable timer;

        RefConnection(ObservableRefCount<?> parent2) {
            this.parent = parent2;
        }

        public void run() {
            this.parent.timeout(this);
        }

        public void accept(Disposable t) throws Exception {
            DisposableHelper.replace(this, t);
        }
    }

    static final class RefCountObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -7419642935409022375L;
        final Observer<? super T> actual;
        final RefConnection connection;
        final ObservableRefCount<T> parent;
        Disposable upstream;

        RefCountObserver(Observer<? super T> actual2, ObservableRefCount<T> parent2, RefConnection connection2) {
            this.actual = actual2;
            this.parent = parent2;
            this.connection = connection2;
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (compareAndSet(false, true)) {
                this.parent.terminated(this.connection);
                this.actual.onError(t);
                return;
            }
            RxJavaPlugins.onError(t);
        }

        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.parent.terminated(this.connection);
                this.actual.onComplete();
            }
        }

        public void dispose() {
            this.upstream.dispose();
            if (compareAndSet(false, true)) {
                this.parent.cancel(this.connection);
            }
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;
                this.actual.onSubscribe(this);
            }
        }
    }

    public ObservableRefCount(ConnectableObservable<T> source2) {
        this(source2, 1, 0, TimeUnit.NANOSECONDS, Schedulers.trampoline());
    }

    public ObservableRefCount(ConnectableObservable<T> source2, int n2, long timeout2, TimeUnit unit2, Scheduler scheduler2) {
        this.source = source2;
        this.n = n2;
        this.timeout = timeout2;
        this.unit = unit2;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> s) {
        RefConnection conn;
        boolean connect = false;
        synchronized (this) {
            conn = this.connection;
            if (conn == null) {
                conn = new RefConnection(this);
                this.connection = conn;
            }
            long c = conn.subscriberCount;
            if (c == 0 && conn.timer != null) {
                conn.timer.dispose();
            }
            conn.subscriberCount = c + 1;
            if (!conn.connected && c + 1 == ((long) this.n)) {
                connect = true;
                conn.connected = true;
            }
        }
        this.source.subscribe((Observer<? super T>) new RefCountObserver<Object>(s, this, conn));
        if (connect) {
            this.source.connect(conn);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel(io.reactivex.internal.operators.observable.ObservableRefCount.RefConnection r11) {
        /*
            r10 = this;
            r8 = 0
            monitor-enter(r10)
            io.reactivex.internal.operators.observable.ObservableRefCount$RefConnection r3 = r10.connection     // Catch:{ all -> 0x001b }
            if (r3 != 0) goto L_0x0009
            monitor-exit(r10)     // Catch:{ all -> 0x001b }
        L_0x0008:
            return
        L_0x0009:
            long r4 = r11.subscriberCount     // Catch:{ all -> 0x001b }
            r6 = 1
            long r0 = r4 - r6
            r11.subscriberCount = r0     // Catch:{ all -> 0x001b }
            int r3 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x0019
            boolean r3 = r11.connected     // Catch:{ all -> 0x001b }
            if (r3 != 0) goto L_0x001e
        L_0x0019:
            monitor-exit(r10)     // Catch:{ all -> 0x001b }
            goto L_0x0008
        L_0x001b:
            r3 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x001b }
            throw r3
        L_0x001e:
            long r4 = r10.timeout     // Catch:{ all -> 0x001b }
            int r3 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x0029
            r10.timeout(r11)     // Catch:{ all -> 0x001b }
            monitor-exit(r10)     // Catch:{ all -> 0x001b }
            goto L_0x0008
        L_0x0029:
            io.reactivex.internal.disposables.SequentialDisposable r2 = new io.reactivex.internal.disposables.SequentialDisposable     // Catch:{ all -> 0x001b }
            r2.<init>()     // Catch:{ all -> 0x001b }
            r11.timer = r2     // Catch:{ all -> 0x001b }
            monitor-exit(r10)     // Catch:{ all -> 0x001b }
            io.reactivex.Scheduler r3 = r10.scheduler
            long r4 = r10.timeout
            java.util.concurrent.TimeUnit r6 = r10.unit
            io.reactivex.disposables.Disposable r3 = r3.scheduleDirect(r11, r4, r6)
            r2.replace(r3)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableRefCount.cancel(io.reactivex.internal.operators.observable.ObservableRefCount$RefConnection):void");
    }

    /* access modifiers changed from: 0000 */
    public void terminated(RefConnection rc) {
        synchronized (this) {
            if (this.connection != null) {
                this.connection = null;
                if (rc.timer != null) {
                    rc.timer.dispose();
                }
                if (this.source instanceof Disposable) {
                    ((Disposable) this.source).dispose();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void timeout(RefConnection rc) {
        synchronized (this) {
            if (rc.subscriberCount == 0 && rc == this.connection) {
                this.connection = null;
                DisposableHelper.dispose(rc);
                if (this.source instanceof Disposable) {
                    ((Disposable) this.source).dispose();
                }
            }
        }
    }
}