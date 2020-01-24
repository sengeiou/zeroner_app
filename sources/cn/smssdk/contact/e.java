package cn.smssdk.contact;

import android.database.ContentObserver;
import android.os.Handler;

class e extends ContentObserver {
    final /* synthetic */ d a;

    e(d dVar, Handler handler) {
        this.a = dVar;
        super(handler);
    }

    public void onChange(boolean z) {
        this.a.g.a();
        if (this.a.f != null) {
            this.a.f.onContactChange(z);
        }
    }
}
