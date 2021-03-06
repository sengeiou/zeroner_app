package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

class FetchAction extends Action<Object> {
    private Callback callback;
    private final Object target = new Object();

    FetchAction(Picasso picasso, Request data, int memoryPolicy, int networkPolicy, Object tag, String key, Callback callback2) {
        super(picasso, null, data, memoryPolicy, networkPolicy, 0, null, key, tag, false);
        this.callback = callback2;
    }

    /* access modifiers changed from: 0000 */
    public void complete(Bitmap result, LoadedFrom from) {
        if (this.callback != null) {
            this.callback.onSuccess();
        }
    }

    /* access modifiers changed from: 0000 */
    public void error() {
        if (this.callback != null) {
            this.callback.onError();
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        super.cancel();
        this.callback = null;
    }

    /* access modifiers changed from: 0000 */
    public Object getTarget() {
        return this.target;
    }
}
