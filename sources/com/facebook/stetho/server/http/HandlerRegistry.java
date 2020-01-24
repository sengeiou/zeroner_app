package com.facebook.stetho.server.http;

import android.support.annotation.Nullable;
import java.util.ArrayList;

public class HandlerRegistry {
    private final ArrayList<HttpHandler> mHttpHandlers = new ArrayList<>();
    private final ArrayList<PathMatcher> mPathMatchers = new ArrayList<>();

    public synchronized void register(PathMatcher path, HttpHandler handler) {
        this.mPathMatchers.add(path);
        this.mHttpHandlers.add(handler);
    }

    public synchronized boolean unregister(PathMatcher path, HttpHandler handler) {
        boolean z;
        int index = this.mPathMatchers.indexOf(path);
        if (index < 0 || handler != this.mHttpHandlers.get(index)) {
            z = false;
        } else {
            this.mPathMatchers.remove(index);
            this.mHttpHandlers.remove(index);
            z = true;
        }
        return z;
    }

    @Nullable
    public synchronized HttpHandler lookup(String path) {
        HttpHandler httpHandler;
        int i = 0;
        int N = this.mPathMatchers.size();
        while (true) {
            if (i >= N) {
                httpHandler = null;
                break;
            } else if (((PathMatcher) this.mPathMatchers.get(i)).match(path)) {
                httpHandler = (HttpHandler) this.mHttpHandlers.get(i);
                break;
            } else {
                i++;
            }
        }
        return httpHandler;
    }
}
