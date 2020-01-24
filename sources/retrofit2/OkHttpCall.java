package retrofit2;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

final class OkHttpCall<T> implements Call<T> {
    @Nullable
    private final Object[] args;
    private volatile boolean canceled;
    @GuardedBy("this")
    @Nullable
    private Throwable creationFailure;
    @GuardedBy("this")
    private boolean executed;
    @GuardedBy("this")
    @Nullable
    private Call rawCall;
    private final ServiceMethod<T, ?> serviceMethod;

    static final class ExceptionCatchingRequestBody extends ResponseBody {
        private final ResponseBody delegate;
        IOException thrownException;

        ExceptionCatchingRequestBody(ResponseBody delegate2) {
            this.delegate = delegate2;
        }

        public MediaType contentType() {
            return this.delegate.contentType();
        }

        public long contentLength() {
            return this.delegate.contentLength();
        }

        public BufferedSource source() {
            return Okio.buffer((Source) new ForwardingSource(this.delegate.source()) {
                public long read(Buffer sink, long byteCount) throws IOException {
                    try {
                        return super.read(sink, byteCount);
                    } catch (IOException e) {
                        ExceptionCatchingRequestBody.this.thrownException = e;
                        throw e;
                    }
                }
            });
        }

        public void close() {
            this.delegate.close();
        }

        /* access modifiers changed from: 0000 */
        public void throwIfCaught() throws IOException {
            if (this.thrownException != null) {
                throw this.thrownException;
            }
        }
    }

    static final class NoContentResponseBody extends ResponseBody {
        private final long contentLength;
        private final MediaType contentType;

        NoContentResponseBody(MediaType contentType2, long contentLength2) {
            this.contentType = contentType2;
            this.contentLength = contentLength2;
        }

        public MediaType contentType() {
            return this.contentType;
        }

        public long contentLength() {
            return this.contentLength;
        }

        public BufferedSource source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    OkHttpCall(ServiceMethod<T, ?> serviceMethod2, @Nullable Object[] args2) {
        this.serviceMethod = serviceMethod2;
        this.args = args2;
    }

    public OkHttpCall<T> clone() {
        return new OkHttpCall<>(this.serviceMethod, this.args);
    }

    public synchronized Request request() {
        Throwable e;
        Request request;
        Call call = this.rawCall;
        if (call != null) {
            request = call.request();
        } else if (this.creationFailure == null) {
            try {
                Call createRawCall = createRawCall();
                this.rawCall = createRawCall;
                request = createRawCall.request();
            } catch (RuntimeException e2) {
                e = e2;
            } catch (Error e3) {
                e = e3;
            } catch (IOException e4) {
                this.creationFailure = e4;
                throw new RuntimeException("Unable to create request.", e4);
            }
        } else if (this.creationFailure instanceof IOException) {
            throw new RuntimeException("Unable to create request.", this.creationFailure);
        } else if (this.creationFailure instanceof RuntimeException) {
            throw ((RuntimeException) this.creationFailure);
        } else {
            throw ((Error) this.creationFailure);
        }
        return request;
        Utils.throwIfFatal(e);
        this.creationFailure = e;
        throw e;
    }

    public void enqueue(final Callback<T> callback) {
        Call call;
        Throwable failure;
        Utils.checkNotNull(callback, "callback == null");
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            this.executed = true;
            call = this.rawCall;
            failure = this.creationFailure;
            if (call == null && failure == null) {
                try {
                    Call call2 = createRawCall();
                    this.rawCall = call2;
                    call = call2;
                } catch (Throwable t) {
                    Utils.throwIfFatal(t);
                    this.creationFailure = t;
                    failure = t;
                }
            }
        }
        if (failure != null) {
            callback.onFailure(this, failure);
            return;
        }
        if (this.canceled) {
            call.cancel();
        }
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response rawResponse) {
                try {
                    try {
                        callback.onResponse(OkHttpCall.this, OkHttpCall.this.parseResponse(rawResponse));
                    } catch (Throwable t) {
                        ThrowableExtension.printStackTrace(t);
                    }
                } catch (Throwable e) {
                    callFailure(e);
                }
            }

            public void onFailure(Call call, IOException e) {
                callFailure(e);
            }

            private void callFailure(Throwable e) {
                try {
                    callback.onFailure(OkHttpCall.this, e);
                } catch (Throwable t) {
                    ThrowableExtension.printStackTrace(t);
                }
            }
        });
    }

    public synchronized boolean isExecuted() {
        return this.executed;
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:36:0x0050=Splitter:B:36:0x0050, B:27:0x003d=Splitter:B:27:0x003d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public retrofit2.Response<T> execute() throws java.io.IOException {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r2 = r4.executed     // Catch:{ all -> 0x000e }
            if (r2 == 0) goto L_0x0011
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x000e }
            java.lang.String r3 = "Already executed."
            r2.<init>(r3)     // Catch:{ all -> 0x000e }
            throw r2     // Catch:{ all -> 0x000e }
        L_0x000e:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x000e }
            throw r2
        L_0x0011:
            r2 = 1
            r4.executed = r2     // Catch:{ all -> 0x000e }
            java.lang.Throwable r2 = r4.creationFailure     // Catch:{ all -> 0x000e }
            if (r2 == 0) goto L_0x0033
            java.lang.Throwable r2 = r4.creationFailure     // Catch:{ all -> 0x000e }
            boolean r2 = r2 instanceof java.io.IOException     // Catch:{ all -> 0x000e }
            if (r2 == 0) goto L_0x0023
            java.lang.Throwable r2 = r4.creationFailure     // Catch:{ all -> 0x000e }
            java.io.IOException r2 = (java.io.IOException) r2     // Catch:{ all -> 0x000e }
            throw r2     // Catch:{ all -> 0x000e }
        L_0x0023:
            java.lang.Throwable r2 = r4.creationFailure     // Catch:{ all -> 0x000e }
            boolean r2 = r2 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x000e }
            if (r2 == 0) goto L_0x002e
            java.lang.Throwable r2 = r4.creationFailure     // Catch:{ all -> 0x000e }
            java.lang.RuntimeException r2 = (java.lang.RuntimeException) r2     // Catch:{ all -> 0x000e }
            throw r2     // Catch:{ all -> 0x000e }
        L_0x002e:
            java.lang.Throwable r2 = r4.creationFailure     // Catch:{ all -> 0x000e }
            java.lang.Error r2 = (java.lang.Error) r2     // Catch:{ all -> 0x000e }
            throw r2     // Catch:{ all -> 0x000e }
        L_0x0033:
            okhttp3.Call r0 = r4.rawCall     // Catch:{ all -> 0x000e }
            if (r0 != 0) goto L_0x003d
            okhttp3.Call r0 = r4.createRawCall()     // Catch:{ IOException -> 0x004e, RuntimeException -> 0x0056, Error -> 0x0059 }
            r4.rawCall = r0     // Catch:{ IOException -> 0x004e, RuntimeException -> 0x0056, Error -> 0x0059 }
        L_0x003d:
            monitor-exit(r4)     // Catch:{ all -> 0x000e }
            boolean r2 = r4.canceled
            if (r2 == 0) goto L_0x0045
            r0.cancel()
        L_0x0045:
            okhttp3.Response r2 = r0.execute()
            retrofit2.Response r2 = r4.parseResponse(r2)
            return r2
        L_0x004e:
            r2 = move-exception
            r1 = r2
        L_0x0050:
            retrofit2.Utils.throwIfFatal(r1)     // Catch:{ all -> 0x000e }
            r4.creationFailure = r1     // Catch:{ all -> 0x000e }
            throw r1     // Catch:{ all -> 0x000e }
        L_0x0056:
            r2 = move-exception
            r1 = r2
            goto L_0x0050
        L_0x0059:
            r2 = move-exception
            r1 = r2
            goto L_0x0050
        */
        throw new UnsupportedOperationException("Method not decompiled: retrofit2.OkHttpCall.execute():retrofit2.Response");
    }

    private Call createRawCall() throws IOException {
        Call call = this.serviceMethod.toCall(this.args);
        if (call != null) {
            return call;
        }
        throw new NullPointerException("Call.Factory returned null.");
    }

    /* access modifiers changed from: 0000 */
    public Response<T> parseResponse(Response rawResponse) throws IOException {
        ResponseBody rawBody = rawResponse.body();
        Response rawResponse2 = rawResponse.newBuilder().body(new NoContentResponseBody(rawBody.contentType(), rawBody.contentLength())).build();
        int code = rawResponse2.code();
        if (code < 200 || code >= 300) {
            try {
                return Response.error(Utils.buffer(rawBody), rawResponse2);
            } finally {
                rawBody.close();
            }
        } else if (code == 204 || code == 205) {
            rawBody.close();
            return Response.success(null, rawResponse2);
        } else {
            ExceptionCatchingRequestBody catchingBody = new ExceptionCatchingRequestBody(rawBody);
            try {
                return Response.success(this.serviceMethod.toResponse(catchingBody), rawResponse2);
            } catch (RuntimeException e) {
                catchingBody.throwIfCaught();
                throw e;
            }
        }
    }

    public void cancel() {
        Call call;
        this.canceled = true;
        synchronized (this) {
            call = this.rawCall;
        }
        if (call != null) {
            call.cancel();
        }
    }

    public boolean isCanceled() {
        boolean z = true;
        if (!this.canceled) {
            synchronized (this) {
                if (this.rawCall == null || !this.rawCall.isCanceled()) {
                    z = false;
                }
            }
        }
        return z;
    }
}
