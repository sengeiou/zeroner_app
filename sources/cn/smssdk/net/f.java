package cn.smssdk.net;

import com.mob.commons.deviceinfo.DeviceInfoCollector;

class f implements Runnable {
    final /* synthetic */ b a;

    f(b bVar) {
        this.a = bVar;
    }

    public void run() {
        DeviceInfoCollector.startCollector(this.a.d);
    }
}
