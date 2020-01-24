package cn.smssdk.net;

import com.mob.commons.appcollector.PackageCollector;

class c implements Runnable {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public void run() {
        PackageCollector.startCollector(this.a.d);
    }
}
