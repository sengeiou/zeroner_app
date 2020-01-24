package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

@Beta
public abstract class AbstractListeningExecutorService implements ListeningExecutorService {
    public ListenableFuture<?> submit(Runnable task) {
        ListenableFutureTask<Void> ftask = ListenableFutureTask.create(task, null);
        execute(ftask);
        return ftask;
    }

    public <T> ListenableFuture<T> submit(Runnable task, @Nullable T result) {
        ListenableFutureTask<T> ftask = ListenableFutureTask.create(task, result);
        execute(ftask);
        return ftask;
    }

    public <T> ListenableFuture<T> submit(Callable<T> task) {
        ListenableFutureTask<T> ftask = ListenableFutureTask.create(task);
        execute(ftask);
        return ftask;
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        try {
            return MoreExecutors.invokeAnyImpl(this, tasks, false, 0);
        } catch (TimeoutException e) {
            throw new AssertionError();
        }
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return MoreExecutors.invokeAnyImpl(this, tasks, true, unit.toNanos(timeout));
    }

    /* JADX INFO: finally extract failed */
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        if (tasks == null) {
            throw new NullPointerException();
        }
        List<Future<T>> futures = new ArrayList<>(tasks.size());
        try {
            for (Callable<T> t : tasks) {
                ListenableFutureTask<T> f = ListenableFutureTask.create(t);
                futures.add(f);
                execute(f);
            }
            for (Future<T> f2 : futures) {
                if (!f2.isDone()) {
                    try {
                        f2.get();
                    } catch (CancellationException | ExecutionException e) {
                    }
                }
            }
            if (1 == 0) {
                for (Future<T> f3 : futures) {
                    f3.cancel(true);
                }
            }
            return futures;
        } catch (Throwable th) {
            if (0 == 0) {
                for (Future<T> f4 : futures) {
                    f4.cancel(true);
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r7 = r6.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a1, code lost:
        if (r7.hasNext() == false) goto L_0x00fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a3, code lost:
        r5 = (java.util.concurrent.Future) r7.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ad, code lost:
        if (r5.isDone() != false) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b3, code lost:
        if (r12 > 0) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b5, code lost:
        if (0 != 0) goto L_0x0117;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b7, code lost:
        r7 = r6.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00bf, code lost:
        if (r7.hasNext() == false) goto L_0x0117;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c1, code lost:
        ((java.util.concurrent.Future) r7.next()).cancel(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r5.get(r12, java.util.concurrent.TimeUnit.NANOSECONDS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e1, code lost:
        if (0 == 0) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e3, code lost:
        r7 = r6.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00eb, code lost:
        if (r7.hasNext() != false) goto L_0x00ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ed, code lost:
        ((java.util.concurrent.Future) r7.next()).cancel(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00fc, code lost:
        if (1 != 0) goto L_0x0117;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00fe, code lost:
        r7 = r6.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0106, code lost:
        if (r7.hasNext() == false) goto L_0x0117;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0108, code lost:
        ((java.util.concurrent.Future) r7.next()).cancel(true);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<T>> r21, long r22, java.util.concurrent.TimeUnit r24) throws java.lang.InterruptedException {
        /*
            r20 = this;
            if (r21 == 0) goto L_0x0004
            if (r24 != 0) goto L_0x000a
        L_0x0004:
            java.lang.NullPointerException r17 = new java.lang.NullPointerException
            r17.<init>()
            throw r17
        L_0x000a:
            r0 = r24
            r1 = r22
            long r12 = r0.toNanos(r1)
            java.util.ArrayList r6 = new java.util.ArrayList
            int r17 = r21.size()
            r0 = r17
            r6.<init>(r0)
            r4 = 0
            java.util.Iterator r7 = r21.iterator()     // Catch:{ all -> 0x0038 }
        L_0x0022:
            boolean r17 = r7.hasNext()     // Catch:{ all -> 0x0038 }
            if (r17 == 0) goto L_0x0053
            java.lang.Object r9 = r7.next()     // Catch:{ all -> 0x0038 }
            java.util.concurrent.Callable r9 = (java.util.concurrent.Callable) r9     // Catch:{ all -> 0x0038 }
            com.google.common.util.concurrent.ListenableFutureTask r17 = com.google.common.util.concurrent.ListenableFutureTask.create(r9)     // Catch:{ all -> 0x0038 }
            r0 = r17
            r6.add(r0)     // Catch:{ all -> 0x0038 }
            goto L_0x0022
        L_0x0038:
            r17 = move-exception
            if (r4 != 0) goto L_0x0116
            java.util.Iterator r7 = r6.iterator()
        L_0x003f:
            boolean r18 = r7.hasNext()
            if (r18 == 0) goto L_0x0116
            java.lang.Object r5 = r7.next()
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5
            r18 = 1
            r0 = r18
            r5.cancel(r0)
            goto L_0x003f
        L_0x0053:
            long r10 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0038 }
            java.util.Iterator r8 = r6.iterator()     // Catch:{ all -> 0x0038 }
        L_0x005b:
            boolean r17 = r8.hasNext()     // Catch:{ all -> 0x0038 }
            if (r17 == 0) goto L_0x0099
            java.lang.Object r17 = r8.next()     // Catch:{ all -> 0x0038 }
            java.lang.Runnable r17 = (java.lang.Runnable) r17     // Catch:{ all -> 0x0038 }
            java.lang.Runnable r17 = (java.lang.Runnable) r17     // Catch:{ all -> 0x0038 }
            r0 = r20
            r1 = r17
            r0.execute(r1)     // Catch:{ all -> 0x0038 }
            long r14 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0038 }
            long r18 = r14 - r10
            long r12 = r12 - r18
            r10 = r14
            r18 = 0
            int r17 = (r12 > r18 ? 1 : (r12 == r18 ? 0 : -1))
            if (r17 > 0) goto L_0x005b
            if (r4 != 0) goto L_0x0117
            java.util.Iterator r7 = r6.iterator()
        L_0x0085:
            boolean r17 = r7.hasNext()
            if (r17 == 0) goto L_0x0117
            java.lang.Object r5 = r7.next()
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5
            r17 = 1
            r0 = r17
            r5.cancel(r0)
            goto L_0x0085
        L_0x0099:
            java.util.Iterator r7 = r6.iterator()     // Catch:{ all -> 0x0038 }
        L_0x009d:
            boolean r17 = r7.hasNext()     // Catch:{ all -> 0x0038 }
            if (r17 == 0) goto L_0x00fb
            java.lang.Object r5 = r7.next()     // Catch:{ all -> 0x0038 }
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5     // Catch:{ all -> 0x0038 }
            boolean r17 = r5.isDone()     // Catch:{ all -> 0x0038 }
            if (r17 != 0) goto L_0x009d
            r18 = 0
            int r17 = (r12 > r18 ? 1 : (r12 == r18 ? 0 : -1))
            if (r17 > 0) goto L_0x00cf
            if (r4 != 0) goto L_0x0117
            java.util.Iterator r7 = r6.iterator()
        L_0x00bb:
            boolean r17 = r7.hasNext()
            if (r17 == 0) goto L_0x0117
            java.lang.Object r5 = r7.next()
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5
            r17 = 1
            r0 = r17
            r5.cancel(r0)
            goto L_0x00bb
        L_0x00cf:
            java.util.concurrent.TimeUnit r17 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ CancellationException -> 0x011a, ExecutionException -> 0x0118, TimeoutException -> 0x00e0 }
            r0 = r17
            r5.get(r12, r0)     // Catch:{ CancellationException -> 0x011a, ExecutionException -> 0x0118, TimeoutException -> 0x00e0 }
        L_0x00d6:
            long r14 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0038 }
            long r18 = r14 - r10
            long r12 = r12 - r18
            r10 = r14
            goto L_0x009d
        L_0x00e0:
            r16 = move-exception
            if (r4 != 0) goto L_0x0117
            java.util.Iterator r7 = r6.iterator()
        L_0x00e7:
            boolean r17 = r7.hasNext()
            if (r17 == 0) goto L_0x0117
            java.lang.Object r5 = r7.next()
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5
            r17 = 1
            r0 = r17
            r5.cancel(r0)
            goto L_0x00e7
        L_0x00fb:
            r4 = 1
            if (r4 != 0) goto L_0x0117
            java.util.Iterator r7 = r6.iterator()
        L_0x0102:
            boolean r17 = r7.hasNext()
            if (r17 == 0) goto L_0x0117
            java.lang.Object r5 = r7.next()
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5
            r17 = 1
            r0 = r17
            r5.cancel(r0)
            goto L_0x0102
        L_0x0116:
            throw r17
        L_0x0117:
            return r6
        L_0x0118:
            r17 = move-exception
            goto L_0x00d6
        L_0x011a:
            r17 = move-exception
            goto L_0x00d6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractListeningExecutorService.invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit):java.util.List");
    }
}
