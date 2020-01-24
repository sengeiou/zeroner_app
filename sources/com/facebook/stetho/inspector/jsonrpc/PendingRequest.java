package com.facebook.stetho.inspector.jsonrpc;

import javax.annotation.Nullable;

public class PendingRequest {
    @Nullable
    public final PendingRequestCallback callback;
    public final long requestId;

    public PendingRequest(long requestId2, @Nullable PendingRequestCallback callback2) {
        this.requestId = requestId2;
        this.callback = callback2;
    }
}
