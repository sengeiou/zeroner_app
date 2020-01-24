package org.litepal.crud.async;

import org.litepal.crud.callback.SaveCallback;

public class SaveExecutor extends AsyncExecutor {
    private SaveCallback cb;

    public void listen(SaveCallback callback) {
        this.cb = callback;
        execute();
    }

    public SaveCallback getListener() {
        return this.cb;
    }
}
