package cn.smssdk.net;

import com.mob.commons.appcollector.RuntimeCollector;

class d implements Runnable {
    final /* synthetic */ b a;

    d(b bVar) {
        this.a = bVar;
    }

    public void run() {
        RuntimeCollector.startCollector(this.a.d);
    }
}
