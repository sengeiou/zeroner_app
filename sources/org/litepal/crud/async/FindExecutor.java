package org.litepal.crud.async;

import org.litepal.crud.callback.FindCallback;

public class FindExecutor extends AsyncExecutor {
    private FindCallback cb;

    public void listen(FindCallback callback) {
        this.cb = callback;
        execute();
    }

    public FindCallback getListener() {
        return this.cb;
    }
}
